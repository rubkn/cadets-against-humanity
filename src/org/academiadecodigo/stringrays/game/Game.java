package org.academiadecodigo.stringrays.game;

import org.academiadecodigo.stringrays.constants.Messages;
import org.academiadecodigo.stringrays.constants.Random;
import org.academiadecodigo.stringrays.game.cards.Card;
import org.academiadecodigo.stringrays.game.cards.Hand;
import org.academiadecodigo.stringrays.game.cards.PopulateDeck;
import org.academiadecodigo.stringrays.constants.Constants;
import org.academiadecodigo.stringrays.game.cards.Deck;
import org.academiadecodigo.stringrays.game.player.Player;
import org.academiadecodigo.stringrays.network.Server;

import java.util.HashMap;
import java.util.Vector;

public class Game {

    private Deck blackDeck;
    private Deck whiteDeck;
    private HashMap<Card, Player> playedCards;
    private Hand czarHand;
    private Vector<Player> players;
    private Player czar;
    private Server server;
    private Card blackCard;

    public Game() {
        init();
    }

    public void init() {
        blackDeck = PopulateDeck.fillDeck(Constants.blackDeck);
        whiteDeck = PopulateDeck.fillDeck(Constants.whiteDeck);
        players = new Vector<>();
    }

    /*
    public void checkPlayersAreReady() {

        boolean ready = true;

        for (Player playerFromList : players) {
            if (!playerFromList.isReady()) {
                ready = false;
            }
        }

        if (!ready) {
            checkPlayersAreReady();
            return;
        }

        return;
    }
    */

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

        startNewRound(blackCard);

        checkRoundWinner(czar.chooseWinner(blackCard, czarHand));

        playersDrawWhiteCards();

        setNextCzar();
    }

    private void startNewRound(Card blackCard) {

        server.broadcastRound();

        while (playedCards.size() < players.size() - 1) {
        }

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

                //if czar is the last index of the vector, sets czar for the first index (0)
                if (lastCzar == players.size() - 1) {
                    czar = players.firstElement();
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

    public void play(Card card, Player player){
        czarHand.addCard(card);
        playedCards.put(card, player);
    }
}