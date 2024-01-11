package server;
import java.util.Random;

public class Game {
    int difficulté;
    int position;
    boolean[] barillet;
    int tour;
    boolean joueur1Vivant;
    boolean joueur2Vivant;

    Game(){
        position = 0;
        tour = 0;
        joueur1Vivant = true;
        joueur2Vivant = true;
    }

    public void nouvellePartie(){
        System.out.println("Nouvelle partie");

        barillet = initBarillet();

        for(int i = 0; i < barillet.length; i++){
            System.out.println(barillet[i] + " ");
        }
    }

    public boolean[] initBarillet(){
        Random r = new Random();

        boolean[] barillet = {false,false,false,false,false,false};

        int x = r.nextInt(6);
        barillet[x] = true;

        return barillet;
    }

    public void tirer(int position, boolean[] barillet){
        if(barillet[position] == true){
            System.out.println("BAVAVANG");
            if (tour == 0){
                joueur1Vivant = false;
                System.out.println("Mort du joueur 1");
                System.out.println("Fin de partie");
            }
            else if(tour == 1){
                joueur2Vivant = false;
                System.out.println("Mort du joueur 2");
                System.out.println("Fin de partie");
            }
        }
        else{
            System.out.println("click");
            this.position = this.position + 1;
        }
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
