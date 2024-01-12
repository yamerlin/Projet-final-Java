package com.roulette.russe.roulette_russe;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Fenetre_de_parametres {
    public static Stage mainWindow;

    public Fenetre_de_parametres(){
        this.creerFenetre();
    }

    public void creerFenetre(){
        mainWindow = new Stage();
        Group root = new Group();
        Scene scene = new Scene(root, 1200, 800);
        mainWindow.setTitle("Roulette russe");
        mainWindow.setScene(scene);
        mainWindow.show();
    }
}
