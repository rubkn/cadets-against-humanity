package org.academiadecodigo.stringrays.network;

import org.academiadecodigo.stringrays.constants.Constants;
import org.academiadecodigo.stringrays.game.Game;
import org.academiadecodigo.stringrays.game.GameStatus;
import org.academiadecodigo.stringrays.game.player.Player;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private Socket playerSocket;
    private ArrayList<PlayerHandler> playerHandlers;
    private Game game;
    private int numberOfReadyPlayers = 0;


    public Server() {
        playerHandlers = new ArrayList<>();
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

                fixedPool.execute(new PlayerHandler(this, playerSocket, game.createPlayer()));

                System.out.println("New player connected...");
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    public void gameReady() {
        numberOfReadyPlayers++;
        if (numberOfReadyPlayers == 3) {
            game.start();
        }
    }

    public ArrayList<PlayerHandler> getPlayerHandlers() {
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

    public void broadcastNewRound() {
        for (PlayerHandler playerHandler : playerHandlers) {
            if (playerHandler.getPlayer().isCzar()) {
                playerHandler.setStatus(GameStatus.CZAR_WAITING);
            } else {
                playerHandler.setStatus(GameStatus.PLAYER_TURN);
            }
        }
    }

    public Game getGame() {
        return game;
    }
}