package org.academiadecodigo.stringrays.network;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.scanners.integer.IntegerRangeInputScanner;
import org.academiadecodigo.stringrays.game.player.Player;

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
        server.addPlayerHandler(this);
    }

    public boolean isCzar() {
        return player.isCzar();
    }

    private void connection() {

        IntegerRangeInputScanner scanner = new IntegerRangeInputScanner(1, 10);
        scanner.setMessage(Messages.VIEW_ACCOUNT_TRANSACTION_ACCOUNTID_MESSAGE);
        scanner.setError(Messages.VIEW_ACCOUNT_TRANSACTION_INVALID_ACCOUNT_ERROR);
        return prompt.getUserInput(scanner);
    }



    public void startMenu()
    {

    // BOAS VINDAS > ESCOLHER O NUMERO DE PLAYERS


    }

    public void gameEngine()
    {
            setCardsToPlayer();
            chooseCzar();

            // definir um numero maximo de jogadas para se ganhar

    }

    public void setCardsToPlayer()
    {
        // atribuir as cartas a cada um dos players

    }

    public void chooseCzar()
    {
       // escolher um czar random
    }

    public void showBlackCardtoPlayer()
    {
        // mostrar a carta ao player
    }

    public void receiveCardsFromPlayersAndShow()
    {
        // receber as cartas dos jogadores e mostra-las

    }

    public void receiceChooseFromCzar()
    {
        // receber a escolher do czar
    }








}
