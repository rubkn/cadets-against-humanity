package org.academiadecodigo.stringrays.network;

import org.academiadecodigo.stringrays.constants.Constants;
import org.academiadecodigo.stringrays.game.Game;

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
        game.init();
    }

    public void start() {


        ExecutorService fixedPool = Executors.newFixedThreadPool(Constants.MAX_NUMBER_OF_PLAYERS);

        try {

            ServerSocket serverSocket = new ServerSocket(Constants.PORT_NUMBER);

            while (true) {

                playerSocket = serverSocket.accept();

                //TODO REMOVE new Player() AND USE Game.Class METHOD
                fixedPool.execute(new PlayerHandler(this, playerSocket, game.createPlayer()));
            }
        } catch (
                IOException e) {
            e.getStackTrace();
        }
    }

    public synchronized void broadcast(String message) {
        for (PlayerHandler playerHandler : playerHandlers) {
            playerHandler.sendMessageToPlayer(message);
        }
    }


    protected Vector getPlayerHandlers() {
        return playerHandlers;
    }
}