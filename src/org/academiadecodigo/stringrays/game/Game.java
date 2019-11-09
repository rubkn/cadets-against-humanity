package org.academiadecodigo.stringrays.game;

import org.academiadecodigo.stringrays.constants.Messages;
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
    private Card blackCard;
    private Vector<Player> players;
    private Player czar;
    private Server server;

    public Game() {
        init();
    }

    public void init() {
        blackDeck = PopulateDeck.fillDeck(Constants.blackDeck);
        whiteDeck = PopulateDeck.fillDeck(Constants.whiteDeck);
        players = new Vector<>();
    }

    public void waitingForPlayers() {

        System.out.println("WAITING FOR PLAYERS...");

    }


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

    public void start() {

        checkPlayersAreReady();

        players.get(0).setCzar(true);

        while (!playerWon()) {
            System.out.println("\nStarting new round");
            newRound();
        }

    }

    private boolean playerWon() {
        boolean isWinner = false;
        for (Player player : players) {
            if (player.getScore() == Constants.ROUNDS_OR_SCORES_OR_O_CARAIO_TO_WIN) {
                isWinner = true;
                System.out.println("\n" + player.getNickname() + ": Maltinha, I won!");
            }
        }
        return isWinner;
    }

    private Card getBlackCard() {
        return blackDeck.getCard(randomFunction(0, blackDeck.getSizeDeck()));
    }


    public Player createPlayer() {
        //instance of new player
        Player newPlayer = new Player();

        //gives cards, to the new player
        for (int i = 0; i < Constants.PLAYER_HAND_SIZE; i++) {
            drawWhiteCard(newPlayer);
        }

        //adding player to the list of players in game
        players.add(newPlayer);

        return newPlayer;
    }

    private void drawWhiteCard(Player player) {
        if (!player.isCzar()) {
            player.draw(whiteDeck.getCard(randomFunction(0, whiteDeck.getSizeDeck())));
        }
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

    private void newRound() {

        blackCard = getBlackCard();
        playedCards = new HashMap<>();
        czarHand = new Hand();

        System.out.println("Black Card: " + blackCard.getMessage());

        startNewRound(blackCard);

        checkRoundWinner(czar.chooseWinner(blackCard, czarHand));

        playersDrawWhiteCards();

        setNextCzar();
    }

    private void checkRoundWinner(Card whiteCard) {
        playedCards.get(whiteCard).roundWon();
    }

    private void startNewRound(Card blackCard) {

        for (Player player : players) {

            if (!player.isCzar()) {
                Card whiteCardPlayed = player.chooseWhiteCard(blackCard);
                System.out.println(player.getNickname() + " White Card: " + whiteCardPlayed.getMessage());
                czarHand.addCard(whiteCardPlayed);
                playedCards.put(whiteCardPlayed, player);
                player.waitForOthers(Messages.PLAYER_TURN_WAIT);
                continue;
            }

            if (player.isCzar()) {
                setCzar(player);
                player.waitForOthers(Messages.CZAR_TURN_MESSAGE);
            }
        }
    }
    //after all players, already played
    //chooseWinner(0); //Need to review this one too

    private void chooseWinner(int index) {
        //czar.chooseWinCard(index, playedCards);
    }


    public int randomFunction(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public void setCzar(Player czar) {
        this.czar = czar;
    }
}
