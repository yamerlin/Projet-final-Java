package client;

import common.Message;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

//Bonjour

public class ClientPanel extends Parent {

    TextFlow receivedText;

    Client client;
    public void setClient(Client client) {
        this.client = client;
    }

    public ClientPanel() {


        Text text = new Text(100, 100, "Client Panel");
        this.getChildren().add(text);

        TextArea textToSend = new TextArea();
        ScrollPane scrollReceivedText = new ScrollPane();
        receivedText = new TextFlow();
        Button sendBtn = new Button();
        Button clearBtn = new Button();
        Button tireBtn = new Button();

        this.getChildren().add(scrollReceivedText);
        this.getChildren().add(textToSend);
        this.getChildren().add(clearBtn);
        this.getChildren().add(sendBtn);
        this.getChildren().add(tireBtn);

        scrollReceivedText.setLayoutX(100);
        scrollReceivedText.setLayoutY(50);
        scrollReceivedText.setPrefWidth(400);
        scrollReceivedText.setPrefHeight(350);

        receivedText.setPrefWidth(400);
        receivedText.setLayoutX(100);
        receivedText.setLayoutY(50);
        receivedText.setVisible(true);

        scrollReceivedText.setContent(receivedText);
        scrollReceivedText.vvalueProperty().bind(receivedText.heightProperty());

        textToSend.setLayoutX(100);
        textToSend.setLayoutY(400);
        textToSend.setPrefWidth(400);
        textToSend.setPrefHeight(100);

        sendBtn.setLayoutX(100);
        sendBtn.setLayoutY(500);
        sendBtn.setPrefWidth(200);
        sendBtn.setPrefHeight(20);
        sendBtn.setVisible(true);
        sendBtn.setText("Send");

        clearBtn.setLayoutX(300);
        clearBtn.setLayoutY(500);
        clearBtn.setPrefWidth(200);
        clearBtn.setPrefHeight(20);
        clearBtn.setVisible(true);
        clearBtn.setText("Clear");

        tireBtn.setLayoutX(200);
        tireBtn.setLayoutY(600);
        tireBtn.setPrefWidth(200);
        tireBtn.setPrefHeight(20);
        tireBtn.setVisible(true);
        tireBtn.setText("Tire");

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
