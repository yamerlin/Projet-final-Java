package BDD;

public class Users {
    private int id;
    private String nom;
    private int victoires;

    public Users(int id, String nom, int victoires){
        this.id = id;
        this.nom = nom;
        this.victoires = victoires;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVictoires(){ return victoires; }

    public void setVictoires(int victoires) { this.victoires = victoires; }
}
