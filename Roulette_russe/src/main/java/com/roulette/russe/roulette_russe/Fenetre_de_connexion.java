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
import javafx.stage.Stage;

import java.io.IOException;

public class Fenetre_de_connexion extends Application {
    public static Stage mainWindow;
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
                //Cacher la fenêtre de menu
                mainWindow.hide();

                //Instancier la fenêtre de jeu
                new Fenetre_de_menu();
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
