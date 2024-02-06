package BDD;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * Classe qui crée une connexion à la base de donnée en récupérant les paramètres dans le fichier bdd.properties.
 */
public class ConnexionBdd {

    /**
     * Constructeur qui crée la connexion à la base de donnée en récupérant les paramètres dans le fichier bdd.properties.
     * @return la connection à la base de données.
     * @throws SQLException
     * @throws ClassNotFoundException
     */
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

