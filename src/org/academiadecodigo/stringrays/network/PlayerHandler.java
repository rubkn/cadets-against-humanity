package org.academiadecodigo.stringrays.network;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.scanners.menu.MenuInputScanner;
import org.academiadecodigo.stringrays.constants.Messages;
import org.academiadecodigo.stringrays.game.player.Player;


import java.io.*;
import java.net.Socket;
import java.util.NoSuchElementException;

public class PlayerHandler implements Runnable {

    private Server server;
    private Socket playerSocket;
    private Prompt prompt;
    private Player player;

    public PlayerHandler(Server server, Socket playerSocket, Player player) {
        this.server = server;
        this.playerSocket = playerSocket;
        this.player = player;
    }

    @Override
    public void run() {
        System.out.println(server.getPlayerHandlers().size()); //TODO CHECK PLAYERHANDLER SIZE
        init();
        while (!Thread.currentThread().isInterrupted()) {
            communicationServer();
        }
    }

    private void init() {

        server.getPlayerHandlers().add(this);

        player.setPlayerHandler(this);

        try {
            prompt = new Prompt(playerSocket.getInputStream(), new PrintStream(playerSocket.getOutputStream(), true));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int chooseCard(String blackCard) {
        MenuInputScanner scanner = new MenuInputScanner(player.getCardMessages());
        scanner.setMessage("Black Card: " + blackCard + "\n\n" + Messages.PLAYER_TURN_MESSAGE);
        scanner.setError(Messages.MAIN_MENU_ERROR);
        return  prompt.getUserInput(scanner);
    }

    private void communicationServer() {

        int message;

        try {
            MenuInputScanner scanner = new MenuInputScanner(player.getCardMessages());
            scanner.setMessage("CHOOSE A CARD");
            message = prompt.getUserInput(scanner);

            System.out.println(message);

        } catch (NoSuchElementException e) {
            e.printStackTrace();
        } /*finally {
                server.getPlayerHandlers().remove(this);
                System.out.println("SAI DA LISTA");
                System.out.println(server.getPlayerHandlers().size());
                close(playerSocket);
                Thread.currentThread().interrupt();
            }*/
        }


        public void sendMessageToPlayer (String message){

            PrintWriter out;

            try {
                out = new PrintWriter(playerSocket.getOutputStream(), true);

                out.println(message);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void close (Closeable closeable){
            if (closeable == null) {
                return;
            }
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
