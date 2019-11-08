package org.academiadecodigo.stringrays;

import org.academiadecodigo.stringrays.game.Game;
import org.academiadecodigo.stringrays.network.Server;

public class Main {

    public static void main(String[] args) {

        int portNumber = 1;
       //  Server server = new Server(portNumber);

        // server.start();

        Game game = new Game();

        for (int i = 0; i < 10; i++) {

            System.out.println(game.randomFunction(0,5));
        }
    }
}
