package com.roulette.russe.roulette_russe;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import client.ClientAuth;

/**
 * Ce programme est un jeu de roulette russe en multijoueur, via une interface graphique utilisant JavaFX <br>
 * Réalisé dans le cadre du cours de programmation en Java <br>
 * Auteur : MERLIN Yann, ALI KADA Mostefa<br>
 * Professeur : BATTARD Clément <br>
 * Date de remise : 06/02/2024 <br>
 * IUT Lyon 1 <br>
 *
 * @author Yann MERLIN
 * @version 1
 */

/**
 * Class Instanciée au début du programme, sert à créer la fenêtre principale de l'application, et à contenir la method START() qui est le point d'entrée de toute application JavaFX
 */
public class Fenetre_de_connexion extends Application {
    public static Stage mainWindow;
    Text pseudo;

    /**
     * Method START(), point d'entrée de toute application JavaFX
     *
     * @param mainWindow La fenêtre principale de l'application
     * @throws IOException
     */
    public void start(final Stage mainWindow) throws IOException {
        //Ajouter un titre
        mainWindow.setTitle("Roulette russe");

        //Créer la scene
        Pane root = new Pane();
        mainWindow.setScene(new Scene(root, 800.0, 650));
        mainWindow.setResizable(false);
        mainWindow.show();

        //Zone pour rentrer son nom
        TextArea nom = new TextArea();
        nom.setPrefSize(300, 30);
        nom.setLayoutX(250);
        nom.setLayoutY(200);
        nom.setStyle("-fx-font: 24 Helvetica;");

        //Bouton pour s'identifier
        Button identification = new Button();
        identification.setPrefSize(300, 100.0);
        identification.setText("S'identifier");
        identification.setStyle("-fx-font: 24 Helvetica;");
        identification.setLayoutX(250);
        identification.setLayoutY(300);
        identification.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {

                String username = nom.getText();

                if(username.equals("")){

                    pseudo = new Text("Veuillez entrer un pseudonyme");
                    pseudo.setLayoutX(250);
                    pseudo.setLayoutY(190);
                    pseudo.setStyle("-fx-font: 20 Helvetica;");
                    root.getChildren().add(pseudo);
                }
                else{

                    ClientAuth clientAuth= new ClientAuth();
                    int id = clientAuth.auth(username);
                    System.out.println(username);
                    System.out.println(id);

                    //Cacher la fenêtre de menu
                    mainWindow.hide();

                    //Instancier la fenêtre de jeu
                    new Fenetre_de_menu(id,username);
                }
            }
        });

        //Un dégradé bleu pour le fond
        root.setBackground(Background.fill(new LinearGradient(0.0, 0.0, 1.0, 1.0, true, CycleMethod.NO_CYCLE, new Stop[]{new Stop(0.0, Color.web("#2e86c1")), new Stop(1.0, Color.web("#fdfefe"))})));

        //Ajouter tous les éléments à la scène
        root.getChildren().addAll(new Node[]{identification, nom});
    }

    public static void main(String[] args) {
        launch(new String[0]);
    }
}
