package domain.dao;

import domain.entity.User;
import domain.entity.UserRole;
import domain.exception.UserException;
import lombok.Data;
import util.ConnectionManager;

import java.sql.*;
@Data
public final class UserDao {

    private final Connection connection;

    /**
     * метод создания записи в таблице users
     * @param firstName имя пользователя
     * @param lastName фамилия пользователя
     * @param email email
     * @param password пароль
     * @return объект добавленного пользователя
     */
    public User create(String firstName, String lastName, String email, String password) {
        User result = null;
        try (connection) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into counters_monitoring.users (user_role, first_name, last_name, email, password) values (?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS)) {
                UserRole userRole = UserRole.CLIENT;
                preparedStatement.setString(1, userRole.name());
                preparedStatement.setString(2, firstName);
                preparedStatement.setString(3, lastName);
                preparedStatement.setString(4, email);
                preparedStatement.setString(5, password);
                preparedStatement.executeUpdate();

                result = new User();
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                generatedKeys.next();
                result.setId(generatedKeys.getLong("id"));
                result.setUserRole(userRole);
                result.setFirstName(firstName);
                result.setLastName(lastName);
                result.setEmail(email);
                result.setPassword(password);
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception : " + e.getMessage());
        }
        if (result != null) {
            return result;
        } else throw new UserException("user creation error!");
    }


    /**
     * поиск записи в таблице users (по email и password)
     * @param email email
     * @param password пароль
     * @return объект найденного пользователя
     */
    public User findUserByEmailAndPassword(String email, String password) {
        User result = new User();
        try (connection) {
            try (PreparedStatement preparedStatement = connection.prepareStatement("select * from counters_monitoring.users where email=? and password=?")) {
                preparedStatement.setString(1, email);
                preparedStatement.setString(2, password);
                ResultSet resultSet = preparedStatement.executeQuery();
                resultSet.next();
                result.setId(resultSet.getLong("id"));
                UserRole userRole = resultSet.getString("user_role").equals("CLIENT")
                        ? UserRole.CLIENT
                        : UserRole.ADMINISTRATOR;
                result.setUserRole(userRole);
                result.setFirstName(resultSet.getString("first_name"));
                result.setLastName(resultSet.getString("last_name"));
                result.setEmail(email);
                result.setPassword(password);
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception : " + e.getMessage());
        }
        return result;
    }

    /**
     * возвращает роль пользователя (ADMINISTRATOR, CLIENT)
     * @param id id пользователя
     * @return роль пользователя
     */
    public String getUserRoleByUserId(Long id) {
        String result = null;
        try (connection) {
            try (PreparedStatement preparedStatement = connection.prepareStatement("select user_role from counters_monitoring.users where id =?")) {
                preparedStatement.setLong(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();
                resultSet.next();
                result = resultSet.getString("user_role");

            }
        } catch (SQLException e) {
            System.out.println("SQL Exception in migration " + e.getMessage());
        }

        if (result != null) {
            return result;
        } else throw new UserException("User not found");
    }


}
