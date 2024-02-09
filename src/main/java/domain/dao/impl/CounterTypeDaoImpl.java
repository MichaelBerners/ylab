package domain.dao.impl;

import lombok.Data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Data
public class CounterTypeDaoImpl {

    private final Connection connection;

    /**
     * метод создания записи в таблице counter_type
     *
     * @param newType тип счетчика
     */
    public void create(String newType) {
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement("insert into counters_monitoring.counter_types (countr_type) values (?)")) {
            preparedStatement.setString(1, newType);
            preparedStatement.executeUpdate();
            System.out.println(newType + " generated");
        } catch (SQLException e) {
            System.out.println("SQL Exception : " + e.getMessage());
        }
    }
}
