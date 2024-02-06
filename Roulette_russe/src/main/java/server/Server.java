package server;

import common.Message;
import java.util.ArrayList;

public class Server {
    /**
     * Port sur lequel le serveur est joignable.
     */
    private int port;

    /**
     * Le jeu, la partie, associée à ce serveur.
     */
    Game game;

    /**
     * La liste des clients connectés sur le serveur.
     */
    private ArrayList<ConnectedClient> clients;

    /**
     * Création du serveur et du jeu.
     * @param port Port sur lequel le serveur est joignable.
     * @param tailleBarillet Taille du barillet (difficulté du jeu).
     * @param modelDuPistolet Modèle du pistolet.
     */
    public Server(int port, int tailleBarillet, int modelDuPistolet) {
        this.port = port;
        this.clients = new ArrayList<ConnectedClient>();

        //Lancer le thread de connection
        Thread threadConnection = new Thread(new Connection(this));
        threadConnection.start();

        //Créer un nouveau jeu.
        game = new Game(tailleBarillet, modelDuPistolet);
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public ArrayList<ConnectedClient> getClients() {
        return clients;
    }

    public void setClients(ArrayList<ConnectedClient> clients) {
        this.clients = clients;
    }

    public int getNumClients(){
        return this.clients.size();
    }

    /**
     * Ajouter un client à la liste des clients lors de sa connection.
     * @param newClient
     */
    public void addClient(ConnectedClient newClient){
        this.clients.add(newClient);
        broadcastMessage(new Message("Server",newClient.getId() + " vient de se connecter "),newClient.getId());
    }

    /**
     * Faire passer le message aux clients.
     * @param mess Le message.
     * @param id L'Id de l'envoyeur.
     */
    public void broadcastMessage(Message mess, int id){
        for (ConnectedClient client : clients) {
            if (mess.getSender().equals("TourJoueur")){
                client.sendMessage(mess);
            }
            //Vérifier si le client n'est pas également celui qui a envoyé le message.
            else if(client.getId() != id) {
                client.sendMessage(mess);
            }
        }
    }

    /**
     * Méthode qui gère la déconnection des clients
     * @param disClient
     */
    public void disconnectedClient(ConnectedClient disClient){
        disClient.closeClient();
        this.clients.remove(disClient.getId());
        broadcastMessage(new Message("",disClient.getId() + " vient de se deconnecter "), disClient.getId());
    }
}
