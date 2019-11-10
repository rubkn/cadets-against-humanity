package org.academiadecodigo.stringrays;

import org.academiadecodigo.stringrays.game.Game;
import org.academiadecodigo.stringrays.game.player.Player;
import org.academiadecodigo.stringrays.network.Server;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.execute(new Server());

    }
}
