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

//Bonjour

public class ClientPanel extends Parent {

    TextFlow receivedText;
    MenuBar menuBar = new MenuBar();
    Client client;
    public void setClient(Client client) {
        this.client = client;
    }

    public ClientPanel() {
        BorderPane pane = new BorderPane();

        VBox vboxJeu = new VBox();
        VBox vboxTchat = new VBox();

        Text text = new Text("Client Panel");
        vboxTchat.getChildren().add(text);

        Text jeu = new Text("Jeu");
        vboxJeu.getChildren().add(jeu);

        TextArea textToSend = new TextArea();
        ScrollPane scrollReceivedText = new ScrollPane();
        receivedText = new TextFlow();
        Button sendBtn = new Button();
        Button clearBtn = new Button();
        Button tireBtn = new Button();

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


        tireBtn.setPrefWidth(200);
        tireBtn.setPrefHeight(20);
        tireBtn.setVisible(true);
        tireBtn.setText("Tire");

        //Ajouter dans les vbox
        vboxTchat.getChildren().add(scrollReceivedText);
        vboxTchat.getChildren().add(textToSend);
        vboxTchat.getChildren().add(sendBtn);
        vboxTchat.getChildren().add(clearBtn);
        vboxTchat.setPrefWidth(300);
        vboxTchat.setAlignment(Pos.CENTER);
        vboxTchat.setPadding(new Insets(50,50,50,50));

        vboxJeu.setPrefWidth(900);
        vboxJeu.getChildren().add(tireBtn);
        vboxJeu.setAlignment(Pos.CENTER);

        //Ajouter dans le pane
        pane.setLeft(vboxJeu);
        pane.setRight(vboxTchat);
        pane.setTop(menuBar);


        //Ajouter dans la scene
        this.getChildren().add(pane);

        sendBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Message mess = new Message("Moi", textToSend.getText());
                printNewMessage(mess);
                textToSend.setText("");
                client.sendMessage(mess);
            }
        });

        tireBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Message mess = new Message("Tir", "tir");
                client.sendMessage(mess);
            }
        });

        clearBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                textToSend.setText("");
            }
        });

    }

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

}
