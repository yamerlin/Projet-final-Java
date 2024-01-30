package server;

import common.Message;

import java.util.ArrayList;

public class Server {
    private int port;
    Game game;
    private ArrayList<ConnectedClient> clients;

    public Server(int port, int tailleBarillet) {
        this.port = port;
        this.clients = new ArrayList<ConnectedClient>();

        Thread threadConnection = new Thread(new Connection(this));
        threadConnection.start();

        game = new Game(tailleBarillet);
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

    public void addClient(ConnectedClient newClient){
        this.clients.add(newClient);
        broadcastMessage(new Message("Server",newClient.getId() + " vient de se connecter "),newClient.getId());
    }
    public void broadcastMessage(Message mess, int id){
        for (ConnectedClient client : clients) {
            if (client.getId() != id) {
                client.sendMessage(mess);
            }
        }
    }

    public void disconnectedClient(ConnectedClient disClient){
        disClient.closeClient();
        this.clients.remove(disClient.getId());
        broadcastMessage(new Message("",disClient.getId() + " vient de se deconnecter "), disClient.getId());
    }
}
