package client;

import BDD.ConnexionBdd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class ClientAuth {

    private Connection connexion;

    public int auth(String username) {

        int id = 0;
        try {

            connexion = ConnexionBdd.getConnection();
            PreparedStatement ps = connexion.prepareStatement("SELECT * from users");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String name = rs.getString("nom");
                if (Objects.equals(username, name)) {
                    System.out.println("Connecté an tant que: "+ name);
                    id = rs.getInt("id");
                    break;
                }

            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return id;
    }

}
