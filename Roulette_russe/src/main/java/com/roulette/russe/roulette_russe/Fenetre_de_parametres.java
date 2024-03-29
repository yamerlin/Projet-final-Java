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
import server.Game;
import server.Server;

/**
 * Classe qui crée le menu de choix des paramètres de la partie grqphiquement.
 */
public class Fenetre_de_parametres {
    /**
     * Fenêtre principale.
     */
    public static Stage mainWindow;

    /**
     * Index utilisé pour garder en mémoire la position dans le tableau des images d'armes.
     */
    public int numImage;

    /**
     * Le serveur.
     */
    public Server server;

    /**
     * Id du joueur dans la bd.
     */
    public int idDb;

    /**
     * Constructeur qui initialise les arguments et crée ensuite la fenêtre de paramètres.
     * @param idDb Id du joueur dans la bd.
     */
    public Fenetre_de_parametres(int idDb){
        this.idDb = idDb;
        numImage = 0;
        this.creerFenetre();
    }

    /**
     * Méthode qui crée la fenêtre graphique de paramètres.
     */
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

        valider.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                int tailleBarillet = 0;

                //Paramètres de difficultés
                if(numImage == 0){
                    tailleBarillet = 8;
                }
                if(numImage == 1){
                    tailleBarillet = 6;
                }
                if(numImage == 2){
                    tailleBarillet = 5;
                }

                server = new Server(5000, tailleBarillet, numImage);

                //game = new Game(tailleBarillet);

                //Cacher la fenêtre de menu
                mainWindow.hide();

                //Instancier la fenêtre de jeu
                new MainGui(idDb);
            }
        });

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

        /**
         * Fonction pour défiler les images des armes vers la gauche.
         */
        gauche.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                if(numImage == 0){
                    numImage = 2;
                }
                else{
                    numImage = numImage - 1;
                }
                imageView.setImage(tableauImagesDesGun[numImage]);
            }
        });


        droite.setVisible(true);
        droite.setText("-->");
        droite.setStyle("-fx-font: 24 Helvetica;");

        /**
         * Fonction pour défiler les images des armes vers la droite.
         */
        droite.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                if(numImage == 2){
                    numImage = 0;
                }
                else{
                    numImage = numImage + 1;
                }
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
