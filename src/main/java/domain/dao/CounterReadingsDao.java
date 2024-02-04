package domain.dao;

import domain.entity.CounterReadings;
import domain.exception.CounterReadingsException;
import domain.exception.UserException;
import lombok.Data;
import util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Data
public class CounterReadingsDao {

    private final Connection connection;

    public void create(Long userId, String counterType, Double readings) {
        try (connection) {
            try (PreparedStatement findCounterTypeByType =
                         connection.prepareStatement("select id from counters_monitoring.counter_types where counter_type=?");
                 PreparedStatement insertCounterReadings
                         = connection.prepareStatement("insert into counters_monitoring.counters_readings " +
                         "(counter_types_id, user_id, year, month, readings) values (?, ?, ?, ?, ?)")) {
                findCounterTypeByType.setString(1, counterType);

                ResultSet counterTypeResultSet = findCounterTypeByType.executeQuery();
                if (counterTypeResultSet.next()) {
                    Long counterTypeId = counterTypeResultSet.getLong("id");
                    LocalDate localDate = LocalDate.now();
                    int year = localDate.getYear();
                    int month = localDate.getMonthValue();
                    insertCounterReadings.setLong(1, counterTypeId);
                    insertCounterReadings.setLong(2, userId);
                    insertCounterReadings.setInt(3, year);
                    insertCounterReadings.setInt(4, month);
                    insertCounterReadings.setDouble(5, readings);
                    insertCounterReadings.executeUpdate();
                }
                else throw new CounterReadingsException("Invalid counter type");
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception : " + e.getMessage());
        }
    }

    public List<CounterReadings> findActualCounterReadingsByUserId(Long userId) {
        List<CounterReadings> result = new ArrayList<>();
        try (Connection connection = ConnectionManager.getConnection()){
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
                            "  AND counter_type = 'HEATING' order by (year, month) DESC limit 1)")){
                preparedStatement.setLong(1, userId);
                preparedStatement.setLong(2, userId);
                preparedStatement.setLong(3, userId);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    CounterReadings counterReadings = new CounterReadings();
                    counterReadings.setId(resultSet.getLong("id"));
                    counterReadings.setCounterType(resultSet.getString("counter_type"));
                    counterReadings.setUserId(resultSet.getLong("user_id"));
                    counterReadings.setYear(resultSet.getInt("year"));
                    counterReadings.setMonth(resultSet.getInt("month"));
                    counterReadings.setReadings(resultSet.getDouble("readings"));

                    result.add(counterReadings);
                }
            }
        }

        catch (SQLException e) {
            System.out.println("SQL Exception : " + e.getMessage());
        }

        return result;
    }

    public List<CounterReadings> findCounterReadingsByUserIdAndYearMonth(Long userId, int year, int month) {
        List<CounterReadings> result = new ArrayList<>();
        try (Connection connection = ConnectionManager.getConnection()){
            try(PreparedStatement preparedStatement = connection.prepareStatement(
                    "select CR.id, counter_type, user_id, year, month, readings\n" +
                            "from counters_monitoring.counters_readings as CR\n" +
                            "         join counters_monitoring.counter_types as CT on CR.counter_types_id = CT.id\n" +
                            "where user_id = ?\n" +
                            "  AND year = ?\n" +
                            "  AND month = ?")){
                preparedStatement.setLong(1, userId);
                preparedStatement.setInt(2, year);
                preparedStatement.setInt(3, month);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    CounterReadings counterReadings = new CounterReadings();
                    counterReadings.setId(resultSet.getLong("id"));
                    counterReadings.setCounterType(resultSet.getString("counter_type"));
                    counterReadings.setUserId(resultSet.getLong("user_id"));
                    counterReadings.setYear(resultSet.getInt("year"));
                    counterReadings.setMonth(resultSet.getInt("month"));
                    counterReadings.setReadings(resultSet.getDouble("readings"));

                    result.add(counterReadings);
                }
            }
        }
        catch (SQLException e) {
            System.out.println("SQL Exception : " + e.getMessage());
        }

        return result;
    }

    public List<CounterReadings> findAllByUserId(Long userId) {
        List<CounterReadings> result = new ArrayList<>();
        try (Connection connection= ConnectionManager.getConnection()){
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "select CR.id, counter_type, user_id, year, month, readings\n" +
                            "from counters_monitoring.counters_readings as CR\n" +
                            "         join counters_monitoring.counter_types as CT on CR.counter_types_id = CT.id\n" +
                            "where user_id = ?")){
                preparedStatement.setLong(1, userId);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    CounterReadings counterReadings = new CounterReadings();
                    counterReadings.setId(resultSet.getLong("id"));
                    counterReadings.setCounterType(resultSet.getString("counter_type"));//!!!
                    counterReadings.setUserId(resultSet.getLong("user_id"));
                    counterReadings.setYear(resultSet.getInt("year"));
                    counterReadings.setMonth(resultSet.getInt("month"));
                    counterReadings.setReadings(resultSet.getDouble("readings"));

                    result.add(counterReadings);
                }
            }
        }
        catch (SQLException e) {
            System.out.println("SQL Exception : " + e.getMessage());
        }

        return result;
    }


}


