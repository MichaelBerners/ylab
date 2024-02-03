package domain.dao;

import util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class UserAuditDao {
    private static final UserAuditDao INSTANCE = new UserAuditDao();

    private UserAuditDao(){}

    public static UserAuditDao getInstance() {
        return INSTANCE;
    }

    public void create(Long userId, String action) {
        try(Connection connection = ConnectionManager.getConnection()) {
            try (PreparedStatement preparedStatement =
                         connection.prepareStatement("inset into users_audit (user_id, action, date) values (?, ?, ?)")) {
                preparedStatement.setLong(1, userId);
                preparedStatement.setString(2, action);
                Timestamp date = new Timestamp(System.currentTimeMillis());
                preparedStatement.setTimestamp(3, date);
                preparedStatement.executeUpdate();
            }
        }
         catch (SQLException e) {
            System.out.println("SQL Exception : " + e.getMessage());
        }
    }
}
