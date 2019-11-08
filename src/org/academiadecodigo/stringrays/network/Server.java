package org.academiadecodigo.stringrays.network;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.stringrays.constants.Constants;
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


    public void start() {

        ExecutorService fixedPool = Executors.newFixedThreadPool(Constants.MAX_NUMBER_OF_PLAYERS);

        try {

            ServerSocket serverSocket = new ServerSocket(Constants.PORT_NUMBER);

            while (true) {

                playerSocket = serverSocket.accept();

                //TODO REMOVE AND USE GAME METHOD

                fixedPool.execute(new PlayerHandler(this, playerSocket, new Player()));
            }
        } catch (
                IOException e) {
            e.getStackTrace();
        }
    }

    public synchronized void broadcast() {
        for (PlayerHandler playerHandler : playerHandlers) {
            if(!playerHandler.isCzar()) {

            }
        }
    }

    public void addPlayerHandler(PlayerHandler playerHandler) {
        playerHandlers.add(playerHandler);
    }
}