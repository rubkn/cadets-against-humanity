package org.academiadecodigo.stringrays;

import org.academiadecodigo.stringrays.constants.Constants;
import org.academiadecodigo.stringrays.network.Server;

public class Main {

    public static void main(String[] args) {

        //Game Engine Teste
        /*
        Game game = new Game();
        game.init();

        Player playerOne = game.createPlayer();
        Player playerTwo = game.createPlayer();
        Player playerThree = game.createPlayer();

        playerOne.setNickname("Ruben");
        playerTwo.setNickname("João");
        playerThree.setNickname("Ricardo");

        playerOne.setReady(true);
        playerTwo.setReady(true);
        playerThree.setReady(true);

        game.start();
        */

        System.out.println("====== Ca(r)dets Against Humanity Server Running ======\n" +
                "Tell others to connect utilizing Netcat to your IP on port " + Constants.PORT_NUMBER +
                "\nInstructions to join game on this computer: Open terminal and do \"nc localhost " +
                Constants.PORT_NUMBER + "\"");
        Server server = new Server();
        server.start();
    }
}
