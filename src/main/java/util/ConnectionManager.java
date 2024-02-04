package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Класс инкапсулирующий подключение к БД
 */
public class ConnectionManager {
    private static final String URL_KEY = "db.url";
    private static final String USER_NAME_KEY = "db.userName";
    private static final String PASSWORD_KEY = "db.password";

    private ConnectionManager(){};

    /**
     * метод получения соединения к БД
     * @return соединение к бд
     */
    public static Connection getConnection() {
        try {
            String s = PropertiesUtil.get(URL_KEY);
            String s1 = PropertiesUtil.get(USER_NAME_KEY);
            String s2 = PropertiesUtil.get(PASSWORD_KEY);
            System.out.println();
            Connection connection = DriverManager.getConnection(s, s1, s2);

            return connection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
