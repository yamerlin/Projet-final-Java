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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.stage.Stage;

/**
 * Classe qui crée la fenêtre des statistiques des utilisateurs.
 */
public class Fenetre_de_statistiques {
    /**
     * Fenêtre principale.
     */
    public static Stage mainWindow;

    /**
     * Connexion
     */
    private Connection connexion;

    /**
     * Id du joueur dans la bd.
     */
    int id;

    /**
     * Nom du joueur dans la bd.
     */
    String username;

    /**
     * Constructeur qui initialise les arguments et crée ensuite la fenêtre de statistiques.
     * @param id Id du joueur dans la bd.
     * @param username Nom du joueur dans la bd.
     */
    public Fenetre_de_statistiques(int id,String username) {
        this.id = id;
        this.username=username;
        this.creerFenetre();
    }

    /**
     * Méthode qui crée la fenêtre graphique de statistiques.
     */
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

        /**
         * Méthode qui défini le comportement du bouton retour.
         */
        retour.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                //Cacher la fenêtre de menu
                mainWindow.hide();

                //Instancier la fenêtre de menu
                new Fenetre_de_menu(id,username);
            }
        });

        //Tableau d'affichage des statistiques

        TableView tableView = new TableView();
        TableColumn<Users, String> columnNom = new TableColumn<>("Nom");
        columnNom.setCellValueFactory(new PropertyValueFactory<>("nom"));

        TableColumn<Users, Integer> columnId = new TableColumn<>("Id");
        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Users, Integer> victoires = new TableColumn<>("Victoires");
        victoires.setCellValueFactory(new PropertyValueFactory<>("victoires"));

        columnNom.setPrefWidth(180);
        columnId.setPrefWidth(180);
        victoires.setPrefWidth(180);

        tableView.getColumns().add(columnId);
        tableView.getColumns().add(columnNom);
        tableView.getColumns().add(victoires);

        tableView.setFixedCellSize(25);
        tableView.setPrefSize(542,400);
        tableView.setLayoutX(129);
        tableView.setLayoutY(100);

        //Liste des utilisateurs présents dans la bd
        List<Users> users = StatsPlayer();

        //Les insérer tous dans le tableau de statistiques
        for ( Users user : users ) {
            System.out.println(user.getNom());
            tableView.getItems().add(new Users(user.getId(), user.getNom(), user.getVictoires()));
        }

        //Un dégradé bleu pour le fond
        root.setBackground(Background.fill(new LinearGradient(0.0, 0.0, 1.0, 1.0, true, CycleMethod.NO_CYCLE, new Stop[]{new Stop(0.0, Color.web("#2e86c1")), new Stop(1.0, Color.web("#fdfefe"))})));

        //Ajouter tous les éléments à la scène
        root.getChildren().addAll(new Node[]{retour, tableView});
    }

    /**
     * Méthode qui récupére grace à une commande SQL les utilisateurs dans la bd ainsi que leurs statistiques.
     * @return La liste des utilisateurs.
     */
    public List<Users> StatsPlayer(){
        List<Users> users= new ArrayList<>();
        try {
            //Connexion
            connexion = ConnexionBdd.getConnection();

            //Commande SQL
            PreparedStatement ps = connexion.prepareStatement("SELECT * FROM users ORDER BY users.victoires DESC LIMIT 15;");

            //Execution de la commande
            ResultSet rs = ps.executeQuery();

            //Ajout dans la liste
            while (rs.next()) {
                Users users1 = new Users(rs.getInt("id") , rs.getString("nom"), rs.getInt("victoires"));
                users.add(users1);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return users;
    }
}
