package domain.dao.impl;

import domain.dao.CounterTypeDao;
import domain.entity.CounterType;
import domain.exception.CounterReadingsException;
import lombok.Data;

import java.sql.*;

@Data
public class CounterTypeDaoImpl implements CounterTypeDao {

    private final Connection connection;

    /**
     * метод создания записи в таблице counter_type
     *
     * @param newType тип счетчика
     */
    public CounterType create(String newType) {
        CounterType counterType = null;
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement("insert into counters_monitoring.counter_types (counter_type) values (?)",
                     Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, newType);
            preparedStatement.executeUpdate();
            counterType = new CounterType();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();
            counterType.setId(generatedKeys.getLong("id"));
            counterType.setCounterType(newType);

        } catch (SQLException e) {
            System.out.println("SQL Exception : " + e.getMessage());
        }

        if(counterType != null) {
            return counterType;
        }
        else throw new CounterReadingsException("the counter type has not been created");
    }
}
