package client;

import BDD.ConnexionBdd;

import java.sql.*;
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
        Boolean nouveau = true;
        try {

            connexion = ConnexionBdd.getConnection();
            PreparedStatement ps = connexion.prepareStatement("SELECT * from users");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String name = rs.getString("nom");
                if (Objects.equals(username, name)) {
                    System.out.println("Connecté an tant que: "+ name);
                    id = rs.getInt("id");
                    nouveau = false;
                    break;
                }
            }
            if(nouveau==true){
                Statement stmt = connexion.createStatement();
                String sql="INSERT INTO `users` (`nom`,`victoires`) VALUES\n" + "('"+username+"',0);";
                stmt.execute(sql);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return id;
    }

}
