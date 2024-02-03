package domain.dao;

import domain.entity.User;
import domain.entity.UserRole;
import domain.exception.UserException;
import util.ConnectionManager;

import java.sql.*;

public final class UserDao {

    static UserDao INSTANCE = new UserDao();

    private UserDao() {
    }

    ;

    public static UserDao getInstance() {
        return INSTANCE;
    }

    public User create(String firstName, String lastName, String email, String password) {
        User result = null;
        try (Connection connection = ConnectionManager.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into users (user_role, first_name, last_name, email, password) values (?, ?, ?, ?, ?)",
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

    public User findUserByEmailAndPassword(String email, String password) {
        User result = null;
        try (Connection connection = ConnectionManager.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement("select * from users where email=? and password=?")) {
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
        if (result != null) {
            return result;
        } else throw new UserException("User not found");
    }

    public String getUserRoleByUserId(Long id) {
        String result = null;
        try (Connection connection = ConnectionManager.getConnection()) {
            try (PreparedStatement preparedStatement = connection.prepareStatement("select user_role from users where id =?")) {
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
