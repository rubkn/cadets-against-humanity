package org.academiadecodigo.stringrays.network;

import org.academiadecodigo.stringrays.constants.Constants;
import org.academiadecodigo.stringrays.constants.Messages;
import org.academiadecodigo.stringrays.game.Game;
import org.academiadecodigo.stringrays.game.cards.Card;
import org.academiadecodigo.stringrays.game.player.Player;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private Socket playerSocket;
    private Vector<PlayerHandler> playerHandlers;
    private Game game;


    public Server() {
        playerHandlers = new Vector<>();
        init();
    }

    private void init() {
        game = new Game();
        game.setServer(this);
    }

    public void start() {

        ExecutorService fixedPool = Executors.newFixedThreadPool(Constants.MAX_NUMBER_OF_PLAYERS);

        try {

            ServerSocket serverSocket = new ServerSocket(Constants.PORT_NUMBER);

            while (true) {

                playerSocket = serverSocket.accept();

                Player newPlayer = game.createPlayer();

                fixedPool.execute(new PlayerHandler(this, playerSocket, newPlayer));

                //game.notifyReady(newPlayer);
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    public void startNewRound(Card blackCard) {

        for (PlayerHandler playerHandler : playerHandlers) {

            if (!playerHandler.getPlayer().isCzar()) {
                //game.roundWhiteCards().put(playerHandler.getPlayer().chooseWhiteCard(blackCard), playerHandler.getPlayer());
                playerHandler.getPlayer().waitForOthers(Messages.PLAYER_TURN_WAIT);
                continue;
            }

            if (playerHandler.getPlayer().isCzar()) {
                //game.setCzar(playerHandler.getPlayer());
                playerHandler.getPlayer().waitForOthers(Messages.CZAR_TURN_MESSAGE);
            }
        }
    }

    public Vector<PlayerHandler> getPlayerHandlers() {
        return playerHandlers;
    }

    public void sendMessageToPlayer(Player player, String message) {
        for (PlayerHandler playerHandler : playerHandlers) {
            if (playerHandler.getPlayer().getNickname().equals(player.getNickname())) {
                playerHandler.sendMessageToPlayer(message);
            }
        }
    }

    public void broadcastMessage(String message) {
        for (PlayerHandler playerHandler : playerHandlers) {
            playerHandler.sendMessageToPlayer(message);
        }
    }
}