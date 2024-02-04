package domain.dao;

import lombok.Data;
import util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
@Data
public class CounterTypeDao {

    private final Connection connection;

    public void create(String newType) {
        try (connection){
            try (PreparedStatement preparedStatement =
                         connection.prepareStatement("insert into counter_types (countr_type) values (?)")) {
                preparedStatement.setString(1, newType);
                preparedStatement.executeUpdate();
                System.out.println(newType + " generated");
            }
        }
        catch (SQLException e) {
            System.out.println("SQL Exception : " + e.getMessage());
        }
    }
}
