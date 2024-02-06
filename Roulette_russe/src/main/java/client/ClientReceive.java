package client;

import common.Message;

import java.io.EOFException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * Classe utilisée pour recevoir les messages du serveur.
 */
public class ClientReceive implements Runnable {

    /**
     * Variable qui stock le client.
     */
    private Client client;

    /**
     * Variable qui stock le socket.
     */
    private Socket socket;

    /**
     * Variable qui stock le flux de données entrantes.
     */
    private ObjectInputStream in;

    /**
     * Constructeur qui set les variables de la classe.
     * @param client
     * @param socket
     */
    public ClientReceive(Client client, Socket socket){
        this.client = client;
        this.socket = socket;
    }

    @Override
    public void run() {
        try{
            in = new ObjectInputStream(socket.getInputStream());
            boolean isActive = true ;
            while(isActive) {
                Message mess = (Message) in.readObject();
                if (mess != null) {
                    this.client.messageReceived(mess);
                } else {
                    isActive = false;
                }
            }
            client.disconnectedServer();
        }catch(EOFException fe){
                System.out.println(fe);
        }catch (Exception e){
            System.out.println(e);
        }
    }
}
