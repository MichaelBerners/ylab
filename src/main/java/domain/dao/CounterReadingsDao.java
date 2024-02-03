package domain.dao;

import domain.entity.CounterReadings;
import domain.exception.CounterReadingsException;
import domain.exception.UserException;
import util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CounterReadingsDao {

    private final static CounterReadingsDao INSTANCE = new CounterReadingsDao();

    private CounterReadingsDao() {
    }

    public static CounterReadingsDao getInstance() {
        return INSTANCE;
    }

    public void create(Long userId, String counterType, Double readings) {
        try (Connection connection = ConnectionManager.getConnection()) {
            try (PreparedStatement findCounterTypeByType =
                         connection.prepareStatement("select id from counter_types where name=?");
                 PreparedStatement insertCounterReadings
                         = connection.prepareStatement("insert into counter_readings (counter_type_id, user_id, year, month, readings)")) {
                findCounterTypeByType.setString(1, counterType);

                ResultSet counterTypeResultSet = findCounterTypeByType.executeQuery();
                if (counterTypeResultSet.first()) {
                    Long counterTypeId = counterTypeResultSet.getLong("id");
                    LocalDate localDate = LocalDate.now();
                    int year = localDate.getYear();
                    int month = localDate.getMonthValue();
                    insertCounterReadings.setLong(1, counterTypeId);
                    insertCounterReadings.setLong(2, userId);
                    insertCounterReadings.setInt(3, year);
                    insertCounterReadings.setInt(4, month);
                    insertCounterReadings.executeUpdate();
                }
                else throw new CounterReadingsException("Invalid counter type");
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception : " + e.getMessage());
        }
    }

    public List<CounterReadings> findActualCounterReadingsByUserId(Long userId) {
        List<CounterReadings> counterReadingsList = new ArrayList<>();
        try (Connection connection = ConnectionManager.getConnection()){
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "select counter_types.counter_type_id, max(counters_readings.year), " +
                            "max(counters_readings.month), counters_readings.readings from counters_readings" +
                            "inner join counter_types on counters_readings.id=counter_types.id " +
                            "where counters_readings.id=? group by (counter_types.counter_type, counters_readings.readings)")){
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    CounterReadings counterReadings = new CounterReadings();
                    counterReadings.setId(resultSet.getLong("id"));
                    counterReadings.setCounterType(resultSet.getNString("counter_type"));//!!!
                    counterReadings.setUserId(resultSet.getLong("user_id"));
                    counterReadings.setYear(resultSet.getInt("year"));
                    counterReadings.setMonth(resultSet.getInt("month"));
                    counterReadings.setReadings(resultSet.getDouble("readings"));

                    counterReadingsList.add(counterReadings);
                }
            }
        }
        catch (SQLException e) {
            System.out.println("SQL Exception : " + e.getMessage());
        }
        return counterReadingsList;
    }

    public List<CounterReadings> findCounterReadingsByUserIdAndMonth(Long userId, int month) {
        List<CounterReadings> counterReadingsList = new ArrayList<>();
        try (Connection connection = ConnectionManager.getConnection()){
            try(PreparedStatement preparedStatement = connection.prepareStatement(
                    "select counter_types.counter_type_id, counters_readings.year, " +
                            "counters_readings.month, counters_readings.readings from counters_readings" +
                            "inner join counter_types on counters_readings.id=counter_types.id " +
                            "where counters_readings.id=? AND moth = ? group by (counter_types.counter_type, counters_readings.readings)")){
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    CounterReadings counterReadings = new CounterReadings();
                    counterReadings.setId(resultSet.getLong("id"));
                    counterReadings.setCounterType(resultSet.getNString("counter_type"));//!!!
                    counterReadings.setUserId(resultSet.getLong("user_id"));
                    counterReadings.setYear(resultSet.getInt("year"));
                    counterReadings.setMonth(resultSet.getInt("month"));
                    counterReadings.setReadings(resultSet.getDouble("readings"));

                    counterReadingsList.add(counterReadings);
                }
            }

        }
        catch (SQLException e) {
            System.out.println("SQL Exception : " + e.getMessage());
        }
        return counterReadingsList;
    }

    public List<CounterReadings> findAllByUserId(Long userId) {
        List<CounterReadings> counterReadingsList = new ArrayList<>();
        try (Connection connection= ConnectionManager.getConnection()){
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "select counter_types.counter_type_id, counters_readings.year, " +
                            "counters_readings.month, counters_readings.readings from counters_readings" +
                            "inner join counter_types on counters_readings.id=counter_types.id " +
                            "where counters_readings.id=? group by (counter_types.counter_type, counters_readings.readings)")){
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    CounterReadings counterReadings = new CounterReadings();
                    counterReadings.setId(resultSet.getLong("id"));
                    counterReadings.setCounterType(resultSet.getNString("counter_type"));//!!!
                    counterReadings.setUserId(resultSet.getLong("user_id"));
                    counterReadings.setYear(resultSet.getInt("year"));
                    counterReadings.setMonth(resultSet.getInt("month"));
                    counterReadings.setReadings(resultSet.getDouble("readings"));

                    counterReadingsList.add(counterReadings);
                }

            }

        }
        catch (SQLException e) {
            System.out.println("SQL Exception : " + e.getMessage());
        }
        return counterReadingsList;
    }




//    public void create(Long userId, String counterType, Double readings) {
//        try (Connection connection = ConnectionManager.getConnection()) {
//            try (PreparedStatement findUserById =
//                         connection.prepareStatement("select id from users where id=?");
//                 PreparedStatement findCounterTypeByType =
//                         connection.prepareStatement("select id from counter_types where name=?");
//                 PreparedStatement insertCounterReadings
//                         = connection.prepareStatement("insert into counter_readings (counter_type_id, user_id, year, month, readings)")) {
//                findUserById.setLong(1, userId);
//                findCounterTypeByType.setString(1, counterType);
//                if (findUserById.execute()) {
//                    ResultSet counterTypeResultSet = findCounterTypeByType.executeQuery();
//                    if(counterTypeResultSet.first()) {
//                        Long counterTypeId = counterTypeResultSet.getLong("id");
//                        LocalDate localDate = LocalDate.now();
//                        int year = localDate.getYear();
//                        int month = localDate.getMonthValue();
//                        insertCounterReadings.setLong(1, counterTypeId);
//                        insertCounterReadings.setLong(2, userId);
//                        insertCounterReadings.setInt(3, year);
//                        insertCounterReadings.setInt(4, month);
//                        if(insertCounterReadings.executeUpdate() != 0){
//
//                        } throw new CounterReadingsException("The testimony can be submitted once a month!");
//                    } else throw new CounterReadingsException("Invalid counter type");
//                } else throw new UserException("User dos not exist");
//            }
//        } catch (SQLException e) {
//            System.out.println("SQL Exception : " + e.getMessage());
//        }
//    }

}


