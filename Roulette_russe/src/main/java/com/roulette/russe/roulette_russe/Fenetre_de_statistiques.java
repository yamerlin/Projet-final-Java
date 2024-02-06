package com.roulette.russe.roulette_russe;

import java.io.Console;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import BDD.ConnexionBdd;
import BDD.Users;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.stage.Stage;

public class Fenetre_de_statistiques {
    public static Stage mainWindow;
    private Connection connexion;

    public Fenetre_de_statistiques() {
        this.creerFenetre();
    }

    public void creerFenetre() {
        mainWindow = new Stage();
        //Ajouter un titre
        mainWindow.setTitle("Roulette russe");

        //Créer la scene
        Pane root = new Pane();
        mainWindow.setScene(new Scene(root, 800.0, 650));
        mainWindow.setResizable(false);
        mainWindow.show();

        //Bouton pour revenir en arrière
        Button retour = new Button();
        retour.setPrefSize(150, 50);
        retour.setText("<-- Retour");
        retour.setStyle("-fx-font: 18 Helvetica;");
        retour.setLayoutX(10);
        retour.setLayoutY(590);

        //Un dégradé bleu pour le fond
        root.setBackground(Background.fill(new LinearGradient(0.0, 0.0, 1.0, 1.0, true, CycleMethod.NO_CYCLE, new Stop[]{new Stop(0.0, Color.web("#2e86c1")), new Stop(1.0, Color.web("#fdfefe"))})));

        //Ajouter tous les éléments à la scène
        root.getChildren().addAll(new Node[]{retour});
    }

    public List<Users> StatsPlayer(){
        List<Users> users= new ArrayList<>();
        try {
            connexion = ConnexionBdd.getConnection();

            PreparedStatement ps = connexion.prepareStatement("SELECT users.nom,scores.victoire FROM users JOIN scores ON users.id=scores.userId ORDER BY scores.victoire DESC;");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Users users1 = new Users();
                users1.setId(rs.getInt("id"));
                users1.setNom(rs.getString("nom"));
                users.add(users1);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return users;

    }
}
