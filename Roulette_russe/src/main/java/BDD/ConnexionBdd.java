package BDD;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class ConnexionBdd {

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Properties properties = new Properties();


        try {
            properties.load(ConnexionBdd.class.getResourceAsStream("/bdd.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String driver = properties.getProperty("driver");
        String url = properties.getProperty("url");
        String username = properties.getProperty("username");
        String password = properties.getProperty("password");

        Class.forName(driver);
        Connection connection = DriverManager.getConnection(url, username, password);

        return connection;
    }
}

