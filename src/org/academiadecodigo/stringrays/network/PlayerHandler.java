package org.academiadecodigo.stringrays.network;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.scanners.menu.MenuInputScanner;
import org.academiadecodigo.stringrays.game.Player;

import java.io.*;
import java.net.Socket;

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

        BufferedReader in;
        String message;

        try {

            in = new BufferedReader(new InputStreamReader(playerSocket.getInputStream()));

            message = in.readLine();

            if (message == null) {
                server.getPlayerHandlers().remove(this);
                close(playerSocket);
                Thread.currentThread().interrupt();
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private int promptCards(int numberOfCards) {
        MenuInputScanner scanner = new MenuInputScanner(player.getCardMessages());
        scanner.setError("NOT A VALID CARD INDEX");
        scanner.setMessage("\n CHOOSE A WHITE CARD TO PLAY: ");
        return prompt.getUserInput(scanner);
    }

    public void sendMessageToPlayer(String message) {

        PrintWriter out;

        try {
            out = new PrintWriter(playerSocket.getOutputStream(), true);

            out.println(message);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void close(Closeable closeable) {
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
