package client;

import common.Message;
import server.Game;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Classe Client, définit un joueur connecté au serveur.
 */
public class Client {
    /**
     * Method utilisé pour setter un client dans une intrface graphiqye, appelé par MainGui.
     * @param view
     */
    public void setView(ClientPanel view) {
        this.view = view;
    }

    public ClientPanel view;

    /**
     * Variable utilisée pour mémoriser l'adresse du serveur
     */
    private String address;

    /**
     * Variable utilisée pour mémoriser le port sur lequel se connecter sur le serveur
     */
    private int port;

    /**
     * Variable utilisée pour mémoriser le socket.
     */
    private Socket socket;

    /**
     * Variable utilisée pour mémoriser l'inputStream, c'est-à-dire le flux de données entrantes.
     */
    private ObjectInputStream in;

    /**
     * Variable utilisée pour mémoriser l'outputStream, c'est-à-dire le flux de données sortantes.
     */
    private ObjectOutputStream out;

    /**
     * Variable utilisée pour mémoriser l'id du client.
     */
    public int id;

    /**
     * Variable utilisée pour mémoriser le barillet, c'est-à-dire le tableau de booléens qui représentent les balles.
     */
    public boolean[] barillet = new boolean[8];

    /**
     * Variable utilisée pour mémoriser la position du barillet dans le canon dans l'arme, c'est-à-dire l'index dans le tableau barillet.
     */
    public int indexBarillet = 0;

    /**
     * Constructeur utilisé pour instancier un client.
     * @param address L'adresse ip du serveur.
     * @param port Le port du serveur.
     */
    public Client(String address, int port){
        //Enregistrer les paramètres
        this.address = address;
        this.port = port;

        //Création des flux de données
        try{
            this.socket = new Socket(address, port);
            out = new ObjectOutputStream(socket.getOutputStream());

        }catch(Exception e){
            System.out.println(e);
        }

        //Thread qui surveille la réception de messages
        Thread threadClientReceive = new Thread(new ClientReceive(this, this.socket));
        threadClientReceive.start();
    }

    /**
     * Methode qui gère la déconnexion d'un client.
     */
    public void disconnectedServer(){
        try{
            this.out.close();
            this.socket.close();
            if (this.in != null){
                this.in.close();
            }
            System.exit(0);
        }catch (Exception e){
            System.out.println(e);
        }
    }

    /**
     * Méthode utilisée pour recevoir des messages du serveur
     * @param message
     * @return
     */
    public Message messageReceived(Message message){
        System.out.println(message);
        //Vérifier si le message est un message concernant l'état de la partie
        if(message.getSender().equals("EtatPartie")){
            view.majEtatPartieText(message);
            view.majImageGun();
        }
        //Vérifier si le message est un message concernant le tour du joueur
        else if (message.getSender().equals("TourJoueur")) {
            view.majTourDuJoueur(message);
            view.avancerIndexBarrilet();
        }
        //Vérifier si le message est un message concernant l'id du joueur
        else if(message.getSender().equals("Id")){
            System.out.println("Received ID : " + message.getContent());
            this.id = Integer.parseInt(message.getContent());

            view.setId(this.id);
        }
        //Vérifier si le message est un message concernant le barillet
        else if (message.getSender().equals("Barillet")) {
            barillet[indexBarillet] = Boolean.parseBoolean(message.getContent());
            indexBarillet ++;

            view.setBarillet(barillet);
        }
        else{
            view.printNewMessage(message);
        }

        return message;
    }

    /**
     * Méthode utilisée pour envoyer un message
     * @param message
     */
    public void sendMessage(Message message){
        try{
            this.out.writeObject(message);
            this.out.flush();
        }
        catch (Exception e){
        }
    }
}
