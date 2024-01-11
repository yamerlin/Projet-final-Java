package game;

import client.Client;
import java.util.Scanner;

public class MainGame {
    public static void main(String[] args) {
        System.out.println("Hello world !");

        Game game = new Game();
        game.nouvellePartie();

        while(game.estVivant){
            Scanner myObj = new Scanner(System.in);
            myObj.nextLine();
            game.tirer(game.position, game.barillet);
        }

        System.out.println("Fin de partie");
    }
}
