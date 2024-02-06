package com.roulette.russe.roulette_russe;

import java.io.Console;
import java.io.IOException;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Classe qui crée le menu.
 */
public class Fenetre_de_menu {
    public static Stage mainWindow;
    int id;
    String username;

    public Fenetre_de_menu(int id,String username) {
        this.id = id;
        this.username=username;
        this.creerFenetre();
    }

    public void creerFenetre(){
        mainWindow = new Stage();
        //Ajouter un titre
        mainWindow.setTitle("Roulette russe");

        //Message de Bienvenue

        Text bienvenue;
        bienvenue = new Text("Bienvenue "+ username);
        bienvenue.setLayoutX(325);
        bienvenue.setLayoutY(30);
        bienvenue.setStyle("-fx-font: 20 Helvetica;");

        //Créer la scene
        Pane root = new Pane();
        mainWindow.setScene(new Scene(root, 800.0, 650));
        mainWindow.setResizable(false);
        mainWindow.show();

        //Bouton pour rejoindre une partie
        Button rejoindre_partie = new Button();
        rejoindre_partie.setPrefSize(300, 100.0);
        rejoindre_partie.setText("Rejoindre partie");
        rejoindre_partie.setStyle("-fx-font: 24 Helvetica;");
        rejoindre_partie.setLayoutX(250);
        rejoindre_partie.setLayoutY(50.0);
        rejoindre_partie.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                //Cacher la fenêtre de menu
                mainWindow.hide();

                //Instancier la fenêtre de jeu
                new MainGui(id);
            }
        });

        //Bouton pour créer une partie
        Button creer_partie = new Button();
        creer_partie.setPrefSize(300, 100.0);
        creer_partie.setText("Créer partie");
        creer_partie.setStyle("-fx-font: 24 Helvetica;");
        creer_partie.setLayoutX(250);
        creer_partie.setLayoutY(200);
        creer_partie.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                //Cacher la fenêtre de menu
                mainWindow.hide();

                //Instancier la fenêtre de création de partie
                new Fenetre_de_parametres(id);
            }
        });

        //Bouton pour voir les statistiques
        Button stats = new Button();
        stats.setPrefSize(300, 100.0);
        stats.setText("Statistiques");
        stats.setStyle("-fx-font: 24 Helvetica;");
        stats.setLayoutX(250);
        stats.setLayoutY(350);
        stats.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                //Cacher la fenêtre de menu
                mainWindow.hide();

                //Instancier la fenêtre de statistiques
                new Fenetre_de_statistiques(id,username);
            }
        });

        //Bouton pour quitter le jeu
        Button quitter = new Button();
        quitter.setPrefSize(300, 100.0);
        quitter.setText("Quitter");
        quitter.setStyle("-fx-font: 24 Helvetica;");
        quitter.setLayoutX(250);
        quitter.setLayoutY(500);
        quitter.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                System.exit(0);
            }
        });

        //Un dégradé bleu pour le fond
        root.setBackground(Background.fill(new LinearGradient(0.0, 0.0, 1.0, 1.0, true, CycleMethod.NO_CYCLE, new Stop[]{new Stop(0.0, Color.web("#2e86c1")), new Stop(1.0, Color.web("#fdfefe"))})));

        //Ajouter tous les éléments à la scène
        root.getChildren().addAll(new Node[]{rejoindre_partie,creer_partie, stats, quitter,bienvenue});
    }
}
