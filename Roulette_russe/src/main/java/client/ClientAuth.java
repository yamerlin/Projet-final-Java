package client;

import BDD.ConnexionBdd;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class ClientAuth {

    /**
     * Variable utilisée pour créer une connexion à la base de données
     */
    private Connection connexion;

    /**
     * Méthode qui crée la connexion à la base de données puis récupère l'id de l'utilisateur correspondent au paramètre "username"
     * @param username Paramètre qui contient le nom de l'utilisateur à rechercher dans la base de données
     * @return Retourne l'id de l'utilisateur contenu dans la base de données
     */
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
