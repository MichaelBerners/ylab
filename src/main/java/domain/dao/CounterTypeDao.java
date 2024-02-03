package domain.dao;

import util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CounterTypeDao {

    private static final CounterTypeDao INSTANCE = new CounterTypeDao();

    private CounterTypeDao(){}

    public static CounterTypeDao getInstance() {
        return INSTANCE;
    }

    public void create(String newType) {
        try (Connection connection = ConnectionManager.getConnection()){
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
