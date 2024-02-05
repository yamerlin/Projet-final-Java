package BDD;

public class Users {

    private int id;
    private String nom;

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
    public String toString() {
        return "Abonne [id=" + id + ", nom=" + nom + "]";
    }
}
