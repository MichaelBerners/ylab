package domain.dao;

import lombok.Data;
import util.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
@Data
public class UserAuditDao {
    private final Connection connection;

    /**
     * метод создания новой записи в таблице users_audit
     * @param userId id пользователя
     * @param action действие пользователя
     */
    public void create(Long userId, String action) {
        try(connection) {
            try (PreparedStatement preparedStatement =
                         connection.prepareStatement("insert into counters_monitoring.users_audit (user_id, action, date) values (?, ?, ?)")) {
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
