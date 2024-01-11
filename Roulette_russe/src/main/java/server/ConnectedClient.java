package server;

import common.Message;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ConnectedClient implements Runnable{
    private static int idCounter = 0;
    private int id;
    private Server server;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public ConnectedClient(Server server, Socket socket) {
        idCounter++;
        this.id = idCounter;
        this.socket = socket;
        this.server = server;
        try{
            out = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("Nouvelle connexion, id = " + id);
        }catch (Exception e){
            System.out.println(e);
        }
    }

    @Override
    public void run() {
        try{
            in = new ObjectInputStream(socket.getInputStream());
            boolean isActive = true;
            while (isActive) {
                Message mess = (Message) in.readObject();
                if (mess != null){
                    if(mess.getSender().equals("Tir")){
                        //RECEPTION D'UN TIR
                        if(server.game.joueur1Vivant && server.game.joueur2Vivant && id == server.game.tour){
                            System.out.println("TIR");
                            server.game.tirer(server.game.position, server.game.barillet);
                            server.game.changeTour();
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
