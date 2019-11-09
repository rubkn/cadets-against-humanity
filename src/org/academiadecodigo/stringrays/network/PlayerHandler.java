package org.academiadecodigo.stringrays.network;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.scanners.menu.MenuInputScanner;
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
        server.getPlayerHandlers().add(this);
        System.out.println(server.getPlayerHandlers().size());
        init();
        while (!Thread.currentThread().isInterrupted()) {
            communicationServer();
        }
    }

    private void init() {
        try {
            prompt = new Prompt(playerSocket.getInputStream(), new PrintStream(playerSocket.getOutputStream(), true));
        } catch (IOException e) {
            e.printStackTrace();
        }
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

        private int promptCards (int numberOfCards) {
            MenuInputScanner scanner = new MenuInputScanner(player.getCardMessages());
            scanner.setError("NOT A VALID CARD INDEX");
            scanner.setMessage("\n CHOOSE A WHITE CARD TO PLAY: ");
            return prompt.getUserInput(scanner);
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
