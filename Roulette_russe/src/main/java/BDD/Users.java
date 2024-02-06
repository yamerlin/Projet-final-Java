package BDD;

/**
 * Classe quo définie la structure d'un utilisateur dans la base de données.
 */
public class Users {
    /**
     * Id de l'utilisateur dans la bd.
     */
    private int id;

    /**
     * Nom rentré par l'utilisateur.
     */
    private String nom;

    /**
     * Nombre de victoires de l'utilisateur.
     */
    private int victoires;

    /**
     * Constructeur qui initialise les paramètres de l'utilisateur.
     * @param id Id de l'utilisateur dans la bd.
     * @param nom Nom rentré par l'utilisateur.
     * @param victoires Nombre de victoires de l'utilisateur.
     */
    public Users(int id, String nom, int victoires){
        this.id = id;
        this.nom = nom;
        this.victoires = victoires;
    }

    /**
     * Récupérer le nom de l'utilisateur.
     * @return string nom
     */
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Récupérer l'id de l'utilisateur.
     * @return int id
     */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * Récupérer le nombre de victoires de l'utilisateur.
     * @return int victoires
     */
    public int getVictoires(){ return victoires; }

    public void setVictoires(int victoires) { this.victoires = victoires; }
}
