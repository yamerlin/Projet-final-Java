package server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import BDD.ConnexionBdd;
import BDD.Users;

/**
 * Classe de jeu qui gère le déroulement d'une partie
 */
public class Game {

    /**
     * Position du canon sur le barillet.
     */
    int position;

    /**
     * Tableau de booléens qui représente le barillet.
     */
    boolean[] barillet;

    /**
     * Tour des joueurs.
     */
    int tour;

    /**
     * Booléen qui indique si J1 est vivant.
     */
    boolean joueur1Vivant;

    /**
     * Booléen qui indique si J2 est vivant.
     */
    boolean joueur2Vivant;

    /**
     * Booléen qui indique la taille du barillet.
     */
    int tailleBarillet;

    /**
     * Modèle du pistolet
     */
    private static int modelDuPistolet;

    /**
     * Constructeur qui initialise les arguments et crée ensuite une nouvelle partie.
     * @param tailleBarillet
     * @param modelDuPistolet
     */
    public Game(int tailleBarillet, int modelDuPistolet){
        this.modelDuPistolet = modelDuPistolet;
        nouvellePartie(tailleBarillet);
    }

    /**
     * Méthode qui crée une nouvelle partie.
     * @param tailleBarillet
     */
    public void nouvellePartie(int tailleBarillet){
        System.out.println("Nouvelle partie");

        //Setter les paramètres de la nouvelle partie
        this.position = 0;
        this.tour = 0;
        this.joueur1Vivant = true;
        this.joueur2Vivant = true;

        //Initialiser le barillet
        barillet = initBarillet(tailleBarillet);
    }

    /**
     * Méthode qui initialise le barillet.
     * @param tailleBarillet
     * @return Tableau de booléens qui constitue le barillet.
     */
    public boolean[] initBarillet(int tailleBarillet){
        this.tailleBarillet = tailleBarillet;
        boolean[] barillet = new boolean[tailleBarillet];

        //Remplir le barillet de balle vide
        for(int i = 0; i < barillet.length; i++){
            barillet[i] = false;
        }

        //Mettre une balle pleine de manière aléatoire
        Random r = new Random();
        int x = r.nextInt(barillet.length);
        barillet[x] = true;

        return barillet;
    }

    /**
     * Méthode qui effectue un tir.
     * @param position
     * @param barillet
     * @return Un booléen qui dit si la partie est terminé ou non.
     */
    public boolean tirer(int position, boolean[] barillet){
        boolean finDePartie = false;

        //Si la position du barillet correspond à celle de la balle alors le joueur perd.
        if(barillet[position] == true){
            System.out.println("BAVAVANG");
            if (tour == 0){
                joueur1Vivant = false;
                finDePartie = true;
                System.out.println("Mort du joueur 1");
                System.out.println("Fin de partie");
            }
            else if(tour == 1){
                joueur2Vivant = false;
                finDePartie = true;
                System.out.println("Mort du joueur 2");
                System.out.println("Fin de partie");
            }
        }
        //Sinon la partie continue.
        else{
            System.out.println("click");
            this.position = this.position + 1;
        }

        return finDePartie;
    }

    /**
     * Méthode qui permet de changer à qui c'est le tour.
     */
    public void changeTour(){
        if(tour == 0){
            tour = 1;
        }
        else if(tour == 1){
            tour = 0;
        }
    }
}
