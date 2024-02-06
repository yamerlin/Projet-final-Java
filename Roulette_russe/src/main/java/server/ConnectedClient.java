package server;

import com.roulette.russe.roulette_russe.Fenetre_de_parametres;
import common.Message;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Classe qui permet la connexion d'un client et l'envoie des messages de tir.
 */
public class ConnectedClient implements Runnable{
    /**
     * Compteur qui permet de donner un id unique à chacun des deux clients (o et 1).
     */
    private static int idCounter = 0;

    /**
     * Id du client.
     */
    private int id;

    /**
     * Le serveur.
     */
    private Server server;

    /**
     * Le socket
     */
    private Socket socket;

    /**
     * Flux de données sortantes.
     */
    private ObjectOutputStream out;

    /**
     * Flux de données sortantes.
     */
    private ObjectInputStream in;

    /**
     * Bouléen indiquant si la partie est finie ou non.
     */
    private boolean finDePartie;

    /**
     * Constructeur.
     * @param server Le serveur.
     * @param socket Le socket.
     */
    public ConnectedClient(Server server, Socket socket) {
        //Setter les paramètres
        idCounter++;
        this.id = idCounter;
        this.socket = socket;
        this.server = server;

        //Message nouvelle connexion
        try{
            out = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("Nouvelle connexion, id = " + id);

        }catch (Exception e){
            System.out.println(e);
        }
    }

    /**
     * Méthode qui envoie les id, les barillets et les messages de tir.
     */
    @Override
    public void run() {
        try{
            //Envoyer leur id aux clients
            Message info = new Message("Id", "" + id);
            this.out.writeObject(info);

            //Envoyer le barillet de l'arme aux clients
            for(int i=0; i<server.game.barillet.length; i++){
                Message barillet = new Message("Barillet", ""+server.game.barillet[i]);
                this.out.writeObject(barillet);
            }

            in = new ObjectInputStream(socket.getInputStream());
            boolean isActive = true;
            while (isActive) {
                Message mess = (Message) in.readObject();
                if (mess != null){
                    if(mess.getSender().equals("Tir")){
                        mess.setSender("EtatPartie");
                        //RECEPTION D'UN TIR
                        if(server.game.joueur1Vivant && server.game.joueur2Vivant && id == server.game.tour){
                            System.out.println("TIR");
                            finDePartie = server.game.tirer(server.game.position, server.game.barillet);
                            server.game.changeTour();

                            Message tourJoueur = new Message("TourJoueur", "" + server.game.tour);
                            server.broadcastMessage(tourJoueur, id);

                            mess.setContent("Le joueur " + id + " a tiré !!!");
                            server.broadcastMessage(mess, id);

                            if(finDePartie){
                                Message mort = new Message("EtatPartie", "Le joueur " + id + " est mort ! Tu remportes la partie !");
                                server.broadcastMessage(mort, id);

                                Message victoire = new Message("Victoire", "");
                                server.broadcastMessage(victoire, id);
                            }
                            else{
                                Message survie = new Message("EtatPartie", "Le joueur " + id + " a survécu !");
                                server.broadcastMessage(survie, id);
                            }
                        }
                        else if(server.game.joueur1Vivant && server.game.joueur2Vivant && id != server.game.tour){
                            System.out.println("Ce n'est pas ton tour c'est le tour du joueur " + server.game.tour);
                        }
                        else if(server.game.joueur1Vivant || server.game.joueur2Vivant){
                            System.out.println("La partie est terminée");
                        }
                    }
                    else{
                        mess.setSender(String.valueOf(id));
                        server.broadcastMessage(mess, id);
                    }
                }else {
                    server.disconnectedClient(this);
                    isActive = false;
                }
            }
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public ObjectOutputStream getOut() {
        return out;
    }

    public void setOut(ObjectOutputStream out) {
        this.out = out;
    }

    public ObjectInputStream getIn() {
        return in;
    }

    public void setIn(ObjectInputStream in) {
        this.in = in;
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public void sendMessage(Message mess){
        try{
            this.out.writeObject(mess);
            this.out.flush();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void closeClient(){
        try{
            this.in.close();
            this.out.close();
            this.socket.close();
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
