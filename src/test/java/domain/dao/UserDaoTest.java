package domain.dao;

import domain.dao.impl.UserDaoImpl;
import domain.entity.User;
import domain.exception.UserException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import util.MigrationUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Testcontainers
class UserDaoTest {

//    @Container
//    static private PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:9.6.14")
//            .withDatabaseName("testDB")
//            .withUsername("user")
//            .withPassword("password");
//
//    static Connection connection;
//    static UserDaoImpl userDao;
//
//    @BeforeAll
//    static void initialization () {
//        postgreSQLContainer.start();
//        String url = postgreSQLContainer.getJdbcUrl();
//        String user = postgreSQLContainer.getUsername();
//        String password = postgreSQLContainer.getPassword();
//        try {
//            connection = DriverManager.getConnection(url, user, password);
//            userDao = new UserDaoImpl(connection);
//            MigrationUtil.getInstance().setMigration(connection);
//        } catch (SQLException e) {
//            System.out.println("SQL Exception : " + e.getMessage());
//        }
//    }
//
//    @Test
//    void createUser() {
//        String firstName = "Pavel";
//        String lastName = "Popov";
//        String email = "new@email.com";
//        String password = "new_password";
//        User user = userDao.create(firstName).orElseThrow(() -> new UserException("Exc"));
//        Assertions.assertThat(user.getFirstName()).isEqualTo(firstName);
//        Assertions.assertThat(user.getLastName()).isEqualTo(lastName);
//        Assertions.assertThat(user.getEmail()).isEqualTo(email);
//        Assertions.assertThat(user.getPassword()).isEqualTo(password);
//    }
//
//    @Test
//    void findUserByEmailAndPassword() {
//        String email = "ivanov@email.ru";
//        String password = "ivanpass";
//        User user = userDao.findUserByEmailAndPassword(email, password);
//        Assertions.assertThat(user).isNotNull();
//    }
//
//    @AfterAll
//    static void containerStop() {
//        postgreSQLContainer.stop();
//    }
}