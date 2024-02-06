package BDD;

public class Users {
    private int id;
    private String nom;
    private int victoire;

    public Users(int id, String nom, int victoire){
        this.id = id;
        this.nom = nom;
        this.victoire = victoire;
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

    public int getVictoire(){ return victoire; }

    public void setVictoire(int victoire) { this.victoire = victoire; }
}
