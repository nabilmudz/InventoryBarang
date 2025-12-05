package config;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnection {

    private static final Logger logger = Logger.getLogger(DBConnection.class.getName());

    private static DBConnection instance;
    private Connection connection;
    private String url;
    private String user;
    private String pass;

    // Constructor
    private DBConnection() {
        try {
            Properties props = new Properties();
            InputStream is = getClass().getClassLoader().getResourceAsStream("application.properties");

            if (is == null) {
                logger.warning("application.properties NOT FOUND.");
            } else {
                props.load(is);
            }

            this.url = props.getProperty("db.url");
            this.user = props.getProperty("db.user");
            this.pass = props.getProperty("db.password");

            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(this.url, this.user, this.pass);

            logger.info("Database connected successfully.");

        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to initialize database connection.", e);
        }
    }

    // Membuat Instance
    public static synchronized DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }

    // Membuat Koneksi Database
    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                logger.warning("Existing DB connection was closed. Reconnecting...");
                connection = DriverManager.getConnection(this.url, this.user, this.pass);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to reuse DB connection. Trying to reconnect...", e);
            try {
                connection = DriverManager.getConnection(this.url, this.user, this.pass);
            } catch (Exception ex) {
                logger.log(Level.SEVERE, "DB reconnection attempt failed!", ex);
            }
        }
        return connection;
    }
}
