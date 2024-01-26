package com.roulette.russe.roulette_russe;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class Fenetre_de_parametres {
    public static Stage mainWindow;
    public int numImage;

    public Fenetre_de_parametres(){
        numImage = 0;
        this.creerFenetre();
    }

    public void creerFenetre(){
        mainWindow = new Stage();
        Group root = new Group();
        Scene scene = new Scene(root, 1200, 800);

        //Création des VBox
        VBox vboxTop = new VBox();
        vboxTop.setPrefWidth(1200);
        vboxTop.setPadding(new Insets(50,50,50,50));

        VBox vboxLeft = new VBox();
        vboxLeft.setPadding(new Insets(50,50,50,50));

        VBox vboxRight = new VBox();
        vboxRight.setPadding(new Insets(50,50,50,50));

        //Création du borderPane
        BorderPane pane = new BorderPane();

        //Titre choix du revolver
        Text choix_revolver = new Text("Choix du revolver");
        choix_revolver.setStyle("-fx-font: 30 Helvetica;");

        //Boutons valider
        Button valider = new Button();
        valider.setVisible(true);
        valider.setText("Valider");
        valider.setStyle("-fx-font: 24 Helvetica;");

        //Image gun
        Image image1 = new Image(this.getClass().getResource("Niveau 1 SW R8/SW R8.png").toExternalForm());
        Image image2 = new Image(this.getClass().getResource("Niveau 2 Colt Anaconda/Colt Anaconda.png").toExternalForm());
        Image image3 = new Image(this.getClass().getResource("Niveau 3 SW 500/SW 500.png").toExternalForm());

        Image[] tableauImagesDesGun = new Image[3];
        tableauImagesDesGun[0] = image1;
        tableauImagesDesGun[1] = image2;
        tableauImagesDesGun[2] = image3;

        ImageView imageView = new ImageView();
        imageView.setImage(tableauImagesDesGun[0]);
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(600);

        //Bouton pour défiler les images
        Button gauche = new Button();
        Button droite = new Button();

        gauche.setVisible(true);
        gauche.setText("<--");
        gauche.setStyle("-fx-font: 24 Helvetica;");

        gauche.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                if(numImage == 0){
                    numImage = 2;
                }
                else{
                    numImage = numImage - 1;
                }

                System.out.println(numImage);
                imageView.setImage(tableauImagesDesGun[numImage]);
            }
        });


        droite.setVisible(true);
        droite.setText("-->");
        droite.setStyle("-fx-font: 24 Helvetica;");

        droite.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                if(numImage == 2){
                    numImage = 0;
                }
                else{
                    numImage = numImage + 1;
                }

                System.out.println(numImage);
                imageView.setImage(tableauImagesDesGun[numImage]);
            }
        });

        //Ajouter dans les VBox
        vboxTop.setAlignment(Pos.CENTER);
        vboxTop.getChildren().add(choix_revolver);
        vboxTop.getChildren().add(valider);
        vboxTop.setSpacing(10);

        vboxLeft.setAlignment(Pos.CENTER);
        vboxLeft.getChildren().add(gauche);

        vboxRight.setAlignment(Pos.CENTER);
        vboxRight.getChildren().add(droite);


        //Ajouter dans le pane
        pane.setTop(vboxTop);
        pane.setCenter(imageView);
        pane.setLeft(vboxLeft);
        pane.setRight(vboxRight);

        //Montrer la fenetre
        mainWindow.setTitle("Roulette russe");
        mainWindow.setScene(scene);
        mainWindow.show();

        //Ajouter tous les éléments à la scène
        root.getChildren().addAll(new Node[]{pane});
    }
}
