package server;
import java.util.Random;

public class Game {
    int difficulté;
    int position;
    boolean[] barillet;
    boolean estVivant;
    int tour;

    Game(){
        position = 0;
        estVivant = true;
        tour = 0;
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
            estVivant = false;
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
