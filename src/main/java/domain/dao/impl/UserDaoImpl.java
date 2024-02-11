package domain.dao.impl;

import domain.dao.UserDao;
import domain.dto.request.UserCreateRequest;
import domain.entity.User;
import domain.entity.UserRole;
import domain.exception.UserException;
import lombok.Data;

import java.sql.*;
import java.util.Optional;

@Data
public final class UserDaoImpl implements UserDao {

    private final Connection connection;

    /**
     * метод создания записи в таблице users
     *
     * @param userCreateRequest@return объект добавленного пользователя
     */
    @Override
    public User create(String firstName, String lastName, String email, String password) {
        User result = null;

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

        } catch (SQLException e) {
            System.out.println("SQL Exception : " + e.getMessage());
        }

        if(result != null) {
            return result;
        }
        else throw new UserException("the user has not been created");

    }


    /**
     * поиск записи в таблице users (по email и password)
     *
     * @param email    email
     * @param password пароль
     * @return объект найденного пользователя
     */
    @Override
    public Optional<User> findUserByEmailAndPassword(String email, String password) {
        Optional<User> result = Optional.empty();

        try (PreparedStatement preparedStatement = connection.prepareStatement("select * from counters_monitoring.users where email=? and password=?")) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            User findUser = new User();
            findUser.setId(resultSet.getLong("id"));
            UserRole userRole = UserRole.valueOf(resultSet.getString("user_role"));
            findUser.setFirstName(resultSet.getString("first_name"));
            findUser.setLastName(resultSet.getString("last_name"));
            findUser.setEmail(resultSet.getString("email"));
            findUser.setPassword(resultSet.getString("password"));

            result = Optional.ofNullable(findUser);
        } catch (SQLException e) {
            System.out.println("SQL Exception : " + e.getMessage());
        }
        return result;
    }

    /**
     * возвращает роль пользователя (ADMINISTRATOR, CLIENT)
     *
     * @param id id пользователя
     * @return роль пользователя
     */
    @Override
    public String getUserRoleByUserId(Long id) {
        String result = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement("select user_role from counters_monitoring.users where id =?")) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            result = resultSet.getString("user_role");

        } catch (SQLException e) {
            System.out.println("SQL Exception in migration " + e.getMessage());
        }

        if (result != null) {
            return result;
        } else throw new UserException("User not found");
    }
}
