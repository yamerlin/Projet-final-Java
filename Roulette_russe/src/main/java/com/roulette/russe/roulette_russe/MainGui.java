package com.roulette.russe.roulette_russe;

import client.Client;
import client.ClientPanel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class MainGui {

    public static Stage mainWindow;

    public MainGui(){
        this.creerFenetre();
    }

    public void creerFenetre() {
        Image gifTir = new Image(this.getClass().getResource("gunshotgif.gif").toExternalForm());
        ImageView imageViewGifTir = new ImageView(gifTir);

        Image gifClick = new Image(this.getClass().getResource("clickgif.gif").toExternalForm());
        ImageView imageViewGifClick = new ImageView(gifClick);

        Image imageGun = new Image(this.getClass().getResource("gunpic.gif").toExternalForm());
        ImageView imageViewGun = new ImageView(imageGun);

        mainWindow = new Stage();
        ClientPanel clientPanel = new ClientPanel(imageViewGifTir, imageViewGifClick, imageViewGun);
        Group root = new Group();
        root.getChildren().add(clientPanel);
        Scene scene = new Scene(root, 1200, 800);
        mainWindow.setTitle("Roulette russe");
        mainWindow.setScene(scene);
        mainWindow.show();

        Client client = new Client("127.0.0.1", 5000);
        client.setView(clientPanel);

        clientPanel.setClient(client);
    }


    /*public static void main(String[] args) {
        Application.launch(MainGui.class, args);
    }*/
}