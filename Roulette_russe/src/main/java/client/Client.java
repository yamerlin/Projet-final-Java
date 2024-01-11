package client;

import common.Message;
import server.Game;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    public void setView(ClientPanel view) {
        this.view = view;
    }

    public ClientPanel view;
    private String address;
    private int port;
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public Client(String address, int port){
        //view = new ClientPanel();

        this.address = address;
        this.port = port;
        try{
            this.socket = new Socket(address, port);
            out = new ObjectOutputStream(socket.getOutputStream());

        }catch(Exception e){
            System.out.println(e);
        }

        //Thread threadClientSend = new Thread(new ClientSend(this.out,this.socket));
        //threadClientSend.start();

        Thread threadClientReceive = new Thread(new ClientReceive(this, this.socket));
        threadClientReceive.start();

        Joueur joueur = new Joueur();
    }

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

    public Message messageReceived(Message message){
        System.out.println(message);
        view.printNewMessage(message);
        return message;
    }

    public void sendMessage(Message message){
        try{
            this.out.writeObject(message);
            this.out.flush();
        }
        catch (Exception e){
        }
    }
}
