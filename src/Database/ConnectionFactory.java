package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    private String connectionUrl = "jdbc:mysql://localhost:3306/oceny?createDatabaseIfNotExist=true";
    private String dbUser = "root";
    private String dbPwd = "";

    private static ConnectionFactory connectionFactory = null;

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(connectionUrl, dbUser, dbPwd);
    }

    public static ConnectionFactory getInstance() {
        if (connectionFactory == null) {
            connectionFactory = new ConnectionFactory();
        }
        return connectionFactory;
    }
}
