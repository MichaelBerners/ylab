package domain.dao.impl;

import domain.dao.CounterReadingsDao;
import domain.dto.request.CounterReadingCreateRequest;
import domain.dto.request.CounterReadingYearMonthRequest;
import domain.entity.CounterReading;
import domain.exception.CounterReadingsException;
import lombok.Data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * класс описывающий взаимодействие с бд
 */
@Data
public class CounterReadingsDaoImpl implements CounterReadingsDao {

    private final Connection connection;

    /**
     * метод создания записи в таблице counter_readings
     *
     * @param counterReadingCreateRequest
     */
    @Override
    public void create(CounterReadingCreateRequest counterReadingCreateRequest) {
        try (PreparedStatement findCounterTypeByType =
                     connection.prepareStatement("select id from counters_monitoring.counter_types where counter_type=?");
             PreparedStatement insertCounterReadings
                     = connection.prepareStatement("insert into counters_monitoring.counters_readings " +
                     "(counter_types_id, user_id, year, month, readings) values (?, ?, ?, ?, ?)")) {
            findCounterTypeByType.setString(1, counterReadingCreateRequest.getCounterType());

            ResultSet counterTypeResultSet = findCounterTypeByType.executeQuery();
            if (counterTypeResultSet.next()) {
                Long counterTypeId = counterTypeResultSet.getLong("id");
                LocalDate localDate = LocalDate.now();
                int year = localDate.getYear();
                int month = localDate.getMonthValue();
                insertCounterReadings.setLong(1, counterTypeId);
                insertCounterReadings.setLong(2, counterReadingCreateRequest.getUserId());
                insertCounterReadings.setInt(3, year);
                insertCounterReadings.setInt(4, month);
                insertCounterReadings.setDouble(5, counterReadingCreateRequest.getReadings());
                insertCounterReadings.executeUpdate();
            } else throw new CounterReadingsException("Invalid counter type");
        } catch (SQLException e) {
            System.out.println("SQL Exception : " + e.getMessage());
        }
    }

    /**
     * метод возвращающий все записи из таблицы counter_readings (для определенного пользователя)
     *
     * @param userId id пользователя
     * @return список актуальных показаний счетчика
     */
    @Override
    public List<CounterReading> findActualCounterReadingsByUserId(Long userId) {
        List<CounterReading> result = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "(select CR.id, counter_type, user_id, year, month, readings\n" +
                        "from counters_monitoring.counters_readings as CR\n" +
                        "         join counters_monitoring.counter_types as CT on CR.counter_types_id = CT.id\n" +
                        "where CR.user_id=?\n" +
                        "  AND counter_type = 'COLD_WATER' order by (year, month) DESC limit 1)\n" +
                        "union\n" +
                        "(select CR.id, counter_type, user_id, year, month, readings\n" +
                        "from counters_monitoring.counters_readings as CR\n" +
                        "         join counters_monitoring.counter_types as CT on CR.counter_types_id = CT.id\n" +
                        "where CR.user_id=?\n" +
                        "  AND counter_type = 'HOT_WATER' order by (year, month) DESC limit 1)\n" +
                        "union\n" +
                        "(select CR.id, counter_type, user_id, year, month, readings\n" +
                        "from counters_monitoring.counters_readings as CR\n" +
                        "         join counters_monitoring.counter_types as CT on CR.counter_types_id = CT.id\n" +
                        "where CR.user_id = ?\n" +
                        "  AND counter_type = 'HEATING' order by (year, month) DESC limit 1)")) {
            preparedStatement.setLong(1, userId);
            preparedStatement.setLong(2, userId);
            preparedStatement.setLong(3, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                CounterReading counterReadings = new CounterReading();
                counterReadings.setId(resultSet.getLong("id"));
                counterReadings.setCounterType(resultSet.getString("counter_type"));
                counterReadings.setUserId(resultSet.getLong("user_id"));
                counterReadings.setYear(resultSet.getInt("year"));
                counterReadings.setMonth(resultSet.getInt("month"));
                counterReadings.setReadings(resultSet.getDouble("readings"));

                result.add(counterReadings);
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception : " + e.getMessage());
        }

        return result;
    }

    /**
     * метод возвращающий все записи из таблицы counter_readings (для определенного пользователя и даты)
     *
     * @param counterReadingYearMonthRequest@return список показаний счетчика за определенный год и месяц
     */
    @Override
    public List<CounterReading> findCounterReadingsByUserIdAndYearMonth(CounterReadingYearMonthRequest counterReadingYearMonthRequest) {
        List<CounterReading> result = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "select CR.id, counter_type, user_id, year, month, readings\n" +
                        "from counters_monitoring.counters_readings as CR\n" +
                        "         join counters_monitoring.counter_types as CT on CR.counter_types_id = CT.id\n" +
                        "where user_id = ?\n" +
                        "  AND year = ?\n" +
                        "  AND month = ?")) {
            preparedStatement.setLong(1, counterReadingYearMonthRequest.getUserId());
            preparedStatement.setInt(2, counterReadingYearMonthRequest.getYear());
            preparedStatement.setInt(3, counterReadingYearMonthRequest.getMonth());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                CounterReading counterReadings = new CounterReading();
                counterReadings.setId(resultSet.getLong("id"));
                counterReadings.setCounterType(resultSet.getString("counter_type"));
                counterReadings.setUserId(resultSet.getLong("user_id"));
                counterReadings.setYear(resultSet.getInt("year"));
                counterReadings.setMonth(resultSet.getInt("month"));
                counterReadings.setReadings(resultSet.getDouble("readings"));

                result.add(counterReadings);
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception : " + e.getMessage());
        }

        return result;
    }

    /**
     * метод возвращающий все записи из таблицы counter_readings (для определенного пользователя)
     *
     * @param userId id пользователя
     * @return список всех показаний счетчика
     */
    @Override
    public List<CounterReading> findAllByUserId(Long userId) {
        List<CounterReading> result = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "select CR.id, counter_type, user_id, year, month, readings\n" +
                        "from counters_monitoring.counters_readings as CR\n" +
                        "         join counters_monitoring.counter_types as CT on CR.counter_types_id = CT.id\n" +
                        "where user_id = ?")) {
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                CounterReading counterReadings = new CounterReading();
                counterReadings.setId(resultSet.getLong("id"));
                counterReadings.setCounterType(resultSet.getString("counter_type"));//!!!
                counterReadings.setUserId(resultSet.getLong("user_id"));
                counterReadings.setYear(resultSet.getInt("year"));
                counterReadings.setMonth(resultSet.getInt("month"));
                counterReadings.setReadings(resultSet.getDouble("readings"));

                result.add(counterReadings);
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception : " + e.getMessage());
        }

        return result;
    }
}


