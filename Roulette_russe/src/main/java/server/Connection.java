package server;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Classe qui gère les nouvelles connexions au serveur.
 */
public class Connection implements Runnable{
    /**
     * Serveur.
     */
    private Server server;

    /**
     * ServeurSocket.
     */
    private ServerSocket serverSocket;

    public Connection(Server server) {
        System.out.println("CONNECTION");

        this.server = server;
        try {
            this.serverSocket = new ServerSocket(server.getPort());
        }catch (Exception e){
            System.out.println(e);
        }
    }

    @Override
    public void run() {
        while (true){
            try{
                System.out.println("DETECTION CONNECTION");
                Socket sockNewClient = serverSocket.accept();
                ConnectedClient newClient = new ConnectedClient(server, sockNewClient);
                newClient.setId(server.getNumClients());
                server.addClient(newClient);

                Thread threadNewClient = new Thread(newClient);
                threadNewClient.start();
            }catch (Exception e){
                System.out.println(e);
            }
        }
    }
}
