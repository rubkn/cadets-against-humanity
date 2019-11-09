package org.academiadecodigo.stringrays;

import org.academiadecodigo.stringrays.game.Game;
import org.academiadecodigo.stringrays.game.player.Player;
import org.academiadecodigo.stringrays.network.PlayerHandler;
import org.academiadecodigo.stringrays.network.Server;

import java.io.IOException;
import java.net.ServerSocket;

public class Main {

    public static void main(String[] args) {

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
    }
}
