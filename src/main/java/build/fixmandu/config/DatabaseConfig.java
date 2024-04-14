package build.fixmandu.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {

    private final String DB_URL = "jdbc:mysql://localhost/FixMandu";
    private final String DB_USER = "root";
    private final String DB_PASSWORD = "Saish@308181";

    private static final DatabaseConfig instance = new DatabaseConfig();

    public static DatabaseConfig getInstance() {
        return instance;
    }

    private DatabaseConfig() {

    }
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

    }
}


