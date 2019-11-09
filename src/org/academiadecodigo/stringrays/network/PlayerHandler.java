package org.academiadecodigo.stringrays.network;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.scanners.menu.MenuInputScanner;
import org.academiadecodigo.bootcamp.scanners.string.StringInputScanner;
import org.academiadecodigo.stringrays.constants.Messages;
import org.academiadecodigo.stringrays.game.player.Player;


import java.io.*;
import java.net.Socket;

public class PlayerHandler implements Runnable {

    private Server server;
    private Socket playerSocket;
    private Prompt prompt;
    private PrintWriter out;
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
        chooseNickname();
        askIfReady();
        while (!Thread.currentThread().isInterrupted()) {
            waitingForInstructions();
        }
    }

    private void init() {

        server.getPlayerHandlers().add(this);

        player.setPlayerHandler(this);

        try {
            prompt = new Prompt(playerSocket.getInputStream(), new PrintStream(playerSocket.getOutputStream(), true));
            out = new PrintWriter(playerSocket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void waitingForInstructions() {

    }

    private void chooseNickname() { //TODO CHECK IF THE NICKNAME IS REPEATED
        out.println(Messages.LOGIN_WELCOME);
        StringInputScanner scanner = new StringInputScanner();
        scanner.setMessage(Messages.LOGIN_MESSAGE);
        player.setNickname(prompt.getUserInput(scanner));
    }

    public void askIfReady() {

        String[] options = {"YES"};

        MenuInputScanner scanner = new MenuInputScanner(options);

        scanner.setMessage(Messages.MAIN_MENU_READY);

        scanner.setError("GET READY, MOTHERFUCKER!");

        prompt.getUserInput(scanner);

        player.setReady(true);
    }

    public int chooseCard(String blackCard, String[] whiteCards, String message) {
        MenuInputScanner scanner = new MenuInputScanner(whiteCards);
        scanner.setMessage("Black Card: " + blackCard + "\n\n" + message);
        scanner.setError(Messages.INVALID_OPTION);
        return prompt.getUserInput(scanner);
    }

    public void sendMessageToPlayer(String message) {
        out.println(message);
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

    public Player getPlayer() {
        return player;
    }
}
