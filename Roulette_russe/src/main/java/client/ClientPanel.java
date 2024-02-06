package client;

import common.Message;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;
import java.io.FileInputStream;

/**
 * Classe qui constitue l'interface graphique principale du jeu pour un client, elle est appelée par MainGui qui l'ajoute lui-même dans la fenêtre principale du programme.
 */
public class ClientPanel extends Parent {
    /**
     * Variable contenant l'animation quand un tir de pistolet est réalisé avec une balle dans le canon.
     */
    ImageView gifTir;

    /**
     * Variable contenant l'animation quand un tir de pistolet est réalisé sans balle dans le canon.
     */
    ImageView gifClick;

    /**
     * Variable contenant l'image de l'arme.
     */
    ImageView imageGun;

    /**
     * Variable contenant la vbox du jeu.
     */
    VBox vboxJeu;

    /**
     * Variable contenant la vbox du tchat.
     */
    VBox vboxTchat;

    /**
     * Variable utilisée pour stocker un message du tchat quand il est reçu.
     */
    TextFlow receivedText;

    /**
     * Variable textuelle pour afficher l'état de la partie (joueur mort ou vivant).
     */
    Text etatPartie;

    /**
     * Variable textuelle pour afficher à quel joueur est-ce le tour.
     */
    Text textTourDuJoueur;

    /**
     * Variable textuelle utilisée pour afficher l'Id du joueur.
     */
    Text textIdDuJoueur;

    /**
     * Variable numérique qui mémorise le tour du joueur.
     */
    int intTourDuJoueur;

    /**
     * Bar de menu qui contient une option pour quitter la partie à tout moment.
     */
    MenuBar menuBar = new MenuBar();
    Client client;

    /**
     * Variable numérique qui mémorise l'Id' du joueur.
     */
    int clientId;

    /**
     * Tableau de booléens qui constituent le barillet de l'arme.
     */
    boolean[] barillet = new boolean[8];

    /**
     * Variable numérique qui constitue la position du barillet dans le canon.
     */
    int indexBarrilet = 0;

    /**
     * Méthode utilisée pour associé un client à cette interface graphique
     * @param client
     */
    public void setClient(Client client) {
        this.client = client;
    }

    /**
     * Constructeur qui crée l'interface graphique
     * @param gifTir Animation de tir
     * @param gifClick Animation canon vide
     * @param imageGun Image de l'arme
     */
    public ClientPanel(ImageView gifTir, ImageView gifClick,ImageView imageGun) {
        this.gifTir = gifTir;
        this.gifClick = gifClick;
        this.imageGun = imageGun;

        BorderPane pane = new BorderPane();

        vboxJeu = new VBox();
        vboxTchat = new VBox();

        Text text = new Text("Tchat");
        vboxTchat.getChildren().add(text);

        Text jeu = new Text("Jeu");
        vboxJeu.getChildren().add(jeu);

        TextArea textToSend = new TextArea();
        ScrollPane scrollReceivedText = new ScrollPane();
        receivedText = new TextFlow();
        Button sendBtn = new Button();
        Button clearBtn = new Button();

        //Créer la bare de menu en haut de l'écran
        Menu menuHautDeLEcran = new Menu("Menu");
        MenuItem quitter = new MenuItem("Quitter");
        menuHautDeLEcran.getItems().addAll(quitter);
        menuBar.getMenus().add(menuHautDeLEcran);

        //Bouton quitter
        quitter.setOnAction(
                //Tuer le programme
                e -> {System.exit(0);}
        );

        //--------------------- Partie tchat ---------------------

        //scrollReceivedText.setPrefWidth(400);
        scrollReceivedText.setPrefHeight(350);
        //scrollReceivedText.setFitToWidth(true);

        receivedText.setPrefWidth(400);
        receivedText.setVisible(true);

        scrollReceivedText.setContent(receivedText);
        scrollReceivedText.vvalueProperty().bind(receivedText.heightProperty());


        //textToSend.setPrefWidth(400);
        textToSend.setPrefHeight(50);
        textToSend.setWrapText(true);


        sendBtn.setPrefWidth(350);
        sendBtn.setPrefHeight(20);
        sendBtn.setVisible(true);
        sendBtn.setText("Send");


        clearBtn.setPrefWidth(350);
        clearBtn.setPrefHeight(20);
        clearBtn.setVisible(true);
        clearBtn.setText("Clear");

        //--------------------- Partie jeu ---------------------

        Button tireBtn = new Button();
        etatPartie = new Text();
        textTourDuJoueur = new Text();
        textIdDuJoueur = new Text();

        tireBtn.setPrefWidth(200);
        tireBtn.setPrefHeight(20);
        tireBtn.setVisible(true);
        tireBtn.setText("Tir");

        etatPartie.setText("Début de la partie");

        textTourDuJoueur.setText("Tour du joueur 0");

        textIdDuJoueur.setText("Vous êtes le joueur " + this.clientId);

        //Ajouter dans les vbox
        vboxTchat.getChildren().add(scrollReceivedText);
        vboxTchat.getChildren().add(textToSend);
        vboxTchat.getChildren().add(sendBtn);
        vboxTchat.getChildren().add(clearBtn);
        vboxTchat.setPrefWidth(300);
        vboxTchat.setAlignment(Pos.CENTER);
        vboxTchat.setPadding(new Insets(50,50,50,50));

        vboxJeu.setPrefWidth(900);
        vboxJeu.getChildren().add(textIdDuJoueur);
        //vboxJeu.getChildren().add(gifTir());
        vboxJeu.getChildren().add(tireBtn);
        vboxJeu.getChildren().add(etatPartie);
        vboxJeu.getChildren().add(textTourDuJoueur);
        vboxJeu.getChildren().add(imageGun);
        vboxJeu.setAlignment(Pos.CENTER);

        //Ajouter dans le pane
        pane.setLeft(vboxJeu);
        pane.setRight(vboxTchat);
        pane.setTop(menuBar);




        //Ajouter dans la scene
        this.getChildren().add(pane);

        /**
         * Méthode associée au bouton send qui envoie le message du tchat.
         */
        sendBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Message mess = new Message("Moi", textToSend.getText());
                printNewMessage(mess);
                textToSend.setText("");
                client.sendMessage(mess);
            }
        });

        /**
         * Méthode associée au bouton tir qui envoie le tir d'un joueur au serveur.
         */
        tireBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //On vérifie que c'est bien au tour de ce joueur de tirer.
                if(intTourDuJoueur == clientId){
                    //Si oui on envoie le tir au serveur
                    Message tir = new Message("Tir", "tir");
                    client.sendMessage(tir);

                    //Si une balle est dans le canon
                    if(barillet[indexBarrilet]){
                        System.out.println("Client : tu meurt");
                        messageDeDefaite();
                        //Enlever les gifs précédents
                        vboxJeu.getChildren().remove(imageGun);
                        vboxJeu.getChildren().remove(gifClick);
                        //Afficher le gif du tir
                        vboxJeu.getChildren().add(gifTir);
                    }
                    //S'il n'y a pas de balle dans le cannon
                    else{
                        System.out.println("Client : tu survie");

                        //Enlever les gifs précédents
                        vboxJeu.getChildren().remove(imageGun);
                        vboxJeu.getChildren().remove(gifClick);
                        //Afficher le gif du canon vide
                        vboxJeu.getChildren().add(gifClick);
                    }
                }
                //Sinon, on lui indique que ce n'est pas à son tour de jouer.
                else{
                    System.out.println("Ce n'est pas ton tour");
                }
            }
        });

        /**
         * Méthode associée au bouton clear qui supprime le message en cours d'écriture dans la bare de tchat.
         */
        clearBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                textToSend.setText("");
            }
        });
    }

    /**
     * Méthode qui met à jour le message indiquant au joueur l'état de la partie.
     * @param mess Message à afficher
     */
    public void majEtatPartieText(Message mess) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                etatPartie.setText(mess.getContent());
            }
        });
    }

    /**
     * Méthode qui affiche un message de défaite à la place du message d'état de la partie.
     */
    public void messageDeDefaite() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                etatPartie.setText("Tu es mort ! Tu perds la partie !");
            }
        });
    }

    /**
     * Méthode qui met à jour le tour des joueurs quand il reçoit l'info du serveur lui indiquant que l'adversaire a tiré.
     * @param mess Message contenant le tour du joueur.
     */
    public void majTourDuJoueur(Message mess) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                intTourDuJoueur = Integer.parseInt(mess.getContent());
                textTourDuJoueur.setText("Tour du joueur " + mess.getContent());
            }
        });
    }

    /**
     * Méthode qui set l'id à afficher sur l'interface graphique, l'id est attribuée au client par le serveur lors de sa connexion.
     * @param id Id du joueur
     */
    public void majIdDuJoueur(int id) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                textIdDuJoueur.setText("Vous êtes le joueur " + id);
            }
        });
    }

    /**
     * Méthode qui permet l'affichage d'un message dans la zone de tchat.
     * @param mess Message à afficher
     */
    public void printNewMessage(Message mess) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Label text = new Label("\n" + mess.toString());
                text.setPrefWidth(receivedText.getPrefWidth() - 20);
                text.setAlignment(Pos.CENTER_LEFT);
                receivedText.getChildren().add(text);
            }
        });
    }

    /**
     * Méthode qui met à jour l'image du pistolet.
     */
    public void majImageGun() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                vboxJeu.getChildren().remove(imageGun);
                vboxJeu.getChildren().remove(gifClick);
                vboxJeu.getChildren().add(imageGun);
            }
        });
    }

    /**
     * Méthode qui met à jour l'id du joueur.
     * @param id
     */
    public void setId(int id){
        this.clientId = id;
        System.out.println("ID mis a jour :" + this.clientId);
        majIdDuJoueur(id);
    }

    /**
     * Méthode utilisée pour récupérer un barillet dans le client, le barrilet est envoyé aux clients par le serveur.
     * @param barillet Tableau de booléens représentant des balles dans un barillet.
     */
    public void setBarillet(boolean[] barillet){
        this.barillet = barillet;

        System.out.println("Barrilet du client set : ");
        for(int i=0; i<barillet.length; i++){
            System.out.println(barillet[i]+" ");
        }
    }

    /**
     * Méthode qui permet d'avancer le barrilet à l'emplacement suivant
     */
    public void avancerIndexBarrilet(){
        indexBarrilet++;
        System.out.println("Le barillet a avancé");
    }
}
