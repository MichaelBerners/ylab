import domain.exception.CounterReadingsException;
import domain.exception.UserException;
import in.controller.CounterReadingsController;
import in.controller.UserController;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import lombok.SneakyThrows;
import util.ConnectionManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * класс запускающий консольное приложение
 * для подачи показаний счетчиков отопления, горячей и холодной воды
 */
public class Runner {
    public static void main(String[] args) {
        migration();
        int number = 0;

        do {
            System.out.println("Справка :");
            System.out.println("1. регистрация");
            System.out.println("2. авторизация");
            System.out.println("3. подача показаний счетчиков");
            System.out.println("4. узнать актуальные показания счетчиков");
            System.out.println("5. узнать показания счетчиков за опредеенный месяц");
            System.out.println("6. просмотр всех поданых показаний");
            System.out.println("7. выход");
            Scanner scan = new Scanner(System.in);
            number = scan.nextInt();
            try {
                switch (number) {
                    case 1:
                        UserController.createNewUser();
                        break;
                    case 2:
                        UserController.authorizationUser();
                        break;
                    case 3:
                        CounterReadingsController.createNewCounterReadings();
                        break;
                    case 4:
                        CounterReadingsController.readActualReadings();
                        break;
                    case 5:
                        CounterReadingsController.readMonthReadings();
                        break;
                    case 6:
                        CounterReadingsController.readHistoryReadings();
                        break;
                }
            } catch (CounterReadingsException | UserException e) {
                System.out.println(e.getMessage());
            }
        }
        while (number >= 1 && number <= 6);
    }

    public static void migration() {
        try {
            Connection connection = ConnectionManager.getConnection();
            Database database =
                    DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            Liquibase liquibase =
                    new Liquibase("db/changelog/db.changelog.xml", new ClassLoaderResourceAccessor(), database);
            liquibase.update();
            System.out.println("Migration is completed successfully");
        }
        catch (LiquibaseException e) {
            System.out.println("SQL Exception in migration " + e.getMessage());
        }
    }

}
