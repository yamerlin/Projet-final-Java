package common;

import java.io.Serializable;

/**
 * Classe qui définie la structure d'un message entre client et serveur.
 */
public class Message implements Serializable {
    /**
     * L'envoyeur du message.
     */
    private String sender;

    /**
     * Le contenu du message.
     */
    private String content;


    /**
     * Constructeur qui crée un nouveau message.
     * @param sender L'envoyeur du message.
     * @param content Le contenu du message.
     */
    public Message(String sender, String content) {
        this.sender = sender;
        this.content = content;
    }

    /**
     * Méthode qui permet la récupération de l'envoyeur du message.
     * @return string envoyeur du message.
     */
    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    /**
     * Méthode qui permet la récupération du contenu du message.
     * @return string contenu du message.
     */
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Méthode qui converti tout le message en String.
     * @return
     */
    public String toString(){
        return this.sender + " - " + this.content;
    }
}
