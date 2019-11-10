package org.academiadecodigo.stringrays.game;

import org.academiadecodigo.stringrays.constants.Random;
import org.academiadecodigo.stringrays.game.cards.Card;
import org.academiadecodigo.stringrays.game.cards.Hand;
import org.academiadecodigo.stringrays.game.cards.PopulateDeck;
import org.academiadecodigo.stringrays.constants.Constants;
import org.academiadecodigo.stringrays.game.cards.Deck;
import org.academiadecodigo.stringrays.game.player.Player;
import org.academiadecodigo.stringrays.network.Server;

import java.util.ArrayList;
import java.util.HashMap;

public class Game implements Runnable {

    private Deck blackDeck;
    private Deck whiteDeck;
    private volatile HashMap<Card, Player> playedCards;
    private Hand czarHand;
    private volatile ArrayList<Player> players;
    private Player czar;
    private Server server;
    private Card blackCard;
    private volatile boolean gameStart;

    public void init() {
        blackDeck = PopulateDeck.fillDeck(Constants.blackDeck);
        whiteDeck = PopulateDeck.fillDeck(Constants.whiteDeck);
        players = new ArrayList<>();
    }

    public void waitingForInstructions() {
        if (gameStart) {
            start();
        }
    }


    public void start() {

        //checkPlayersAreReady();

        players.get(0).setCzar(true);

        while (!playerWon()) {
            System.out.println("\nStarting new round");
            newRound();
        }
    }

    public Player createPlayer() {
        //instance of new player
        Player newPlayer = new Player();

        //gives cards, to the new player
        for (int i = 0; i < Constants.PLAYER_HAND_SIZE; i++) {
            drawWhiteCard(newPlayer);
        }

        newPlayer.setGame(this);
        //adding player to the list of players in game
        players.add(newPlayer);

        return newPlayer;
    }

    private void newRound() {

        blackCard = getNewBlackCard();
        playedCards = new HashMap<>();
        czarHand = new Hand();

        System.out.println("Black Card: " + blackCard.getMessage());

        startNewRound();

        //checkRoundWinner(czar.chooseWinner(blackCard, czarHand));

        playersDrawWhiteCards();

        setNextCzar();
    }

    private void startNewRound() {

        server.broadcastNewRound();

        while (playedCards.size() < players.size() - 1) {
            //System.out.println("Waiting for players to play...");
        }

        server.broadcastCzarRound();

        while(czarHand.getSizeDeck() == players.size() - 2) {
          //fuck let's martelar
        }



        //check if all players minus czar have played a white card
        //while (playedCards.size() < players.size() - 1) {}

        /*for (Player player : players) {

            if (!player.isCzar()) {
                //Card whiteCardPlayed = player.chooseWhiteCard(blackCard);
                //czarHand.addCard(whiteCardPlayed);
                //playedCards.put(whiteCardPlayed, player);
                player.waitForOthers(Messages.PLAYER_TURN_WAIT);
                continue;
            }

            if (player.isCzar()) {
                czar = player;
                player.waitForOthers(Messages.CZAR_TURN_MESSAGE);
            }
        }*/
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
            drawWhiteCard(player);
        }
    }

    public void checkRoundWinner(Card czarCard) {
        playedCards.get(czarCard).roundWon();
    }

    private boolean playerWon() {

        boolean isWinner = false;

        for (Player player : players) {
            if (player.getScore() == Constants.SCORE_TO_WIN) {
                isWinner = true;
                System.out.println("\n" + player.getNickname() + ": Maltinha, before being the adult in the room... I won!");
            }
        }

        return isWinner;
    }

    public HashMap<Card, Player> getPlayedCards() {
        return playedCards;
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