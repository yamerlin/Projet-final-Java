package server;
import java.util.Random;

public class Game {
    int difficulté;
    int position;
    boolean[] barillet;
    int tour;
    boolean joueur1Vivant;
    boolean joueur2Vivant;
    int tailleBarillet;

    public Game(int tailleBarillet){
        nouvellePartie(tailleBarillet);
    }

    public void nouvellePartie(int tailleBarillet){
        System.out.println("Nouvelle partie");

        this.position = 0;
        this.tour = 0;
        this.joueur1Vivant = true;
        this.joueur2Vivant = true;

        barillet = initBarillet(tailleBarillet);

        for(int i = 0; i < barillet.length; i++){
            System.out.println(barillet[i] + " ");
        }
    }

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

    public boolean tirer(int position, boolean[] barillet){
        boolean finDePartie = false;

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
        else{
            System.out.println("click");
            this.position = this.position + 1;
        }

        return finDePartie;
    }

    public void changeTour(){
        if(tour == 0){
            tour = 1;
        }
        else if(tour == 1){
            tour = 0;
        }
    }
}
