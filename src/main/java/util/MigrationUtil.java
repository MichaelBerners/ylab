package util;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.sql.Connection;

public class MigrationUtil {

    private static MigrationUtil INSTANCE = new MigrationUtil();

    private MigrationUtil(){}

    public static MigrationUtil getInstance() {
        return INSTANCE;
    }

    public void setMigration(Connection connection) {
        try {
            Database database =
                    DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
            Liquibase liquibase =
                    new Liquibase("db/changelog/db.changelog.xml", new ClassLoaderResourceAccessor(), database);
            liquibase.update();
            System.out.println("Migration is completed successfully");
        }
        catch (LiquibaseException e) {
            System.out.println("Migration Exception : " + e.getMessage());
        }


    }

}
