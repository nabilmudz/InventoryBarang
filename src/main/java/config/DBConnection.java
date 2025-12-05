package config;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBConnection {

    private static DBConnection instance;
    private Connection connection;
    private String url;
    private String user;
    private String pass;

    private DBConnection() {
        try {
            Properties props = new Properties();
            InputStream is = getClass().getClassLoader().getResourceAsStream("application.properties");

            if (is == null) {
                System.out.println("application.properties NOT FOUND.");
            } else {
                props.load(is);
            }

            this.url = props.getProperty("db.url");
            this.user = props.getProperty("db.user");
            this.pass = props.getProperty("db.password");

            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(this.url, this.user, this.pass);

        } catch (Exception e) {
            System.out.println("Database connection failed.");
            e.printStackTrace();
        }
    }

    public static DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                // reconnect
                connection = DriverManager.getConnection(this.url, this.user, this.pass);
            }
        } catch (Exception e) {
            // attempt to recreate instance and connection
            try {
                connection = DriverManager.getConnection(this.url, this.user, this.pass);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return connection;
    }
}
