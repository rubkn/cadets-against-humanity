package org.academiadecodigo.stringrays.game;

import org.academiadecodigo.stringrays.constants.Colors;
import org.academiadecodigo.stringrays.constants.Messages;
import org.academiadecodigo.stringrays.constants.Random;
import org.academiadecodigo.stringrays.game.cards.Card;
import org.academiadecodigo.stringrays.game.cards.Hand;
import org.academiadecodigo.stringrays.game.cards.PopulateDeck;
import org.academiadecodigo.stringrays.constants.Constants;
import org.academiadecodigo.stringrays.game.cards.Deck;
import org.academiadecodigo.stringrays.game.player.Player;
import org.academiadecodigo.stringrays.network.Server;

import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

public class Game implements Runnable {

    private Deck blackDeck;
    private Deck whiteDeck;
    private volatile ConcurrentHashMap<Card, Player> playedCards;
    private volatile Hand czarHand;
    private volatile Vector<Player> players;
    private Player czar;
    private Player winner;
    private Server server;
    private Card blackCard;
    private Card czarCard;
    private volatile boolean gameStart;

    public void init() {
        blackDeck = PopulateDeck.fillDeck(Constants.blackDeck);
        whiteDeck = PopulateDeck.fillDeck(Constants.whiteDeck);
        players = new Vector<>();
    }

    public void waitingForInstructions() {
        if (gameStart) {
            start();
        }
    }


    public void start() {

        resetPlayers();

        players.get(0).setCzar(true);

        while (!playerWon()) {
            System.out.println(Messages.NEW_ROUND);
            server.broadcastMessage(Messages.NEW_ROUND);
            newRound();
        }

        start();
    }

    public Player createPlayer() {
        //instance of new player
        Player newPlayer = new Player();

        //gives cards, to the new player
        for (int i = 0; i < Constants.PLAYER_HAND_SIZE; i++) {
            drawWhiteCard(newPlayer);
        }

        //newPlayer.setGame(this);
        //adding player to the list of players in game
        players.add(newPlayer);

        return newPlayer;
    }

    private void resetPlayers() {
        for (Player player : players) {
            player.reset();
        }
    }

    private void newRound() {

        blackCard = getNewBlackCard();
        playedCards = new ConcurrentHashMap<>();
        czarHand = new Hand();

        System.out.println(Messages.BLACK_CARD + blackCard.getMessage());

        server.broadcastNewRound();

        while (playedCards.size() < players.size() - 1) {
            //waiting for players to play
        }

        server.broadcastCzarRound();

        while (czarHand.getSizeDeck() > players.size() - 2) {
            //waiting for czar to choose card
        }

        server.broadcastMessage("\n" + winner.getNickname() + Messages.PLAYER_WIN);

        server.broadcastMessage("\t" + Messages.BLACK_CARD + blackCard.getMessage() + "\n");

        server.broadcastMessage("\t" + Messages.WHITE_CARD + czarCard.getMessage());

        playersDrawWhiteCards();

        setNextCzar();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void drawWhiteCard(Player player) {
        if (!player.isCzar()) {
            player.draw(whiteDeck.getCard(Random.getRandomNumber(0, whiteDeck.getSizeDeck())));
        }
    }

    private Card getNewBlackCard() {
        return blackDeck.getCard(Random.getRandomNumber(0, blackDeck.getSizeDeck()));
    }

    private void setNextCzar() {

        for (Player player : players) {

            if (player.isCzar()) {

                //verify and removes the actual czar
                int lastCzar = players.indexOf(player);
                player.setCzar(false);

                //if czar is the last index of the ArrayList, sets czar for the first index (0)
                if (lastCzar == players.size() - 1) {
                    czar = players.get(0);
                    czar.setCzar(true);
                    return;
                }

                //sets the next czar
                czar = players.get(lastCzar + 1);
                czar.setCzar(true);
                return;
            }
        }
    }

    private void playersDrawWhiteCards() {
        for (Player player : players) {
            if (!player.isCzar()) {
                drawWhiteCard(player);
            }
        }
    }

    public void checkRoundWinner(Card czarCard) {
        this.czarCard = czarCard;
        winner = playedCards.get(czarCard);
        winner.roundWon();
    }

    private boolean playerWon() {

        boolean isWinner = false;

        for (Player player : players) {
            if (player.getScore() == Constants.SCORE_TO_WIN) {
                isWinner = true;
                System.out.println(Colors.GREEN + "\n" + player.getNickname() + Messages.PLAYER_WON + Colors.RESET);
            }
        }

        return isWinner;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public Card getBlackCard() {
        return blackCard;
    }

    public Hand getCzarHand() {
        return czarHand;
    }

    public void play(Card card, Player player) {
        czarHand.addCard(card);
        playedCards.put(card, player);
        System.out.println(czarHand.getSizeDeck());
    }

    public void setGameStart(boolean gameStart) {
        this.gameStart = gameStart;
    }

    @Override
    public void run() {
        init();
        while (true) {
            waitingForInstructions();
        }
    }
}