package org.academiadecodigo.stringrays.network;

import org.academiadecodigo.stringrays.constants.Constants;
import org.academiadecodigo.stringrays.game.Game;
import org.academiadecodigo.stringrays.game.GameStatus;

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
    private int numberOfReadyPlayers = 0;
    private ExecutorService fixedPool;

    public Server() {
        playerHandlers = new Vector<>();
        init();
    }

    private void init() {
        fixedPool = Executors.newFixedThreadPool(Constants.MAX_NUMBER_OF_PLAYERS);
        game = new Game();
        game.setServer(this);
        fixedPool.execute(game);
    }

    public void start() {

        try {

            ServerSocket serverSocket = new ServerSocket(Constants.PORT_NUMBER);

            while (!game.isGameStart()) {

                playerSocket = serverSocket.accept();

                if (!game.isGameStart()) {

                    fixedPool.execute(new PlayerHandler(this, playerSocket, game.createPlayer()));
                }
            }
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    public void gameReady() {
        numberOfReadyPlayers++;
        if (numberOfReadyPlayers >= Constants.MIN_NUMBER_OF_PLAYERS && numberOfReadyPlayers == playerHandlers.size()) {
            game.setGameStart(true);
        }
    }

    public Vector<PlayerHandler> getPlayerHandlers() {
        return playerHandlers;
    }

    public void broadcastMessage(String message) {
        for (PlayerHandler playerHandler : playerHandlers) {
            playerHandler.sendMessageToPlayer(message);
        }
    }

    public void broadcastNewRound() {
        for (PlayerHandler playerHandler : playerHandlers) {
            if (!playerHandler.getPlayer().isCzar()) {
                playerHandler.setStatus(GameStatus.PLAYER_TURN);
                continue;
            }
            playerHandler.setStatus(GameStatus.CZAR_WAITING);
        }
    }

    public void broadcastCzarRound() {
        for (PlayerHandler playerHandler : playerHandlers) {
            if (playerHandler.getPlayer().isCzar()) {
                playerHandler.setStatus(GameStatus.CZAR_TURN);
                continue;
            }
            playerHandler.setStatus(GameStatus.PLAYER_WAITING);
        }
    }

    public Game getGame() {
        return game;
    }

}