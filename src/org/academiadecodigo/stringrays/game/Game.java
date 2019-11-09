package org.academiadecodigo.stringrays.game;

import org.academiadecodigo.stringrays.constants.Messages;
import org.academiadecodigo.stringrays.game.cards.Card;
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
    private Card blackCard;
    private Vector<Player> players;
    private Player czar;
    private Server server;
    private boolean playerWon;

    public Game() {
        init();
    }

    public void init() {
        blackDeck = PopulateDeck.fillDeck(Constants.blackDeck);
        whiteDeck = PopulateDeck.fillDeck(Constants.whiteDeck);
        players = new Vector<>();
    }

    public void waitingForPlayers() {

        if (players.size() >= Constants.MIN_NUMBER_OF_PLAYERS) {

            /*
            try {
                wait();
                notifyAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            */

            start();
        }
    }


    public synchronized void notifyReady(Player player) {

        player.setReady(true);

        try {
            for (Player playerFromList : players) {
                if (!playerFromList.isReady()) {
                    wait();
                }
            }
            notifyAll();
            waitingForPlayers();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void start() {

        while (!playerWon) {

            newRound();
        }
    }

    private Card getBlackCard() {
        return blackDeck.getCard(randomFunction(0, blackDeck.getSizeDeck()));
    }

    public Player createPlayer() {
        //instance of new player
        Player newPlayer = new Player();

        //gives cards, to the new player
        for (int i = 0; i < Constants.PLAYER_HAND_SIZE; i++) {
            giveCards(newPlayer);
        }

        //adding player to the list of players in game
        players.add(newPlayer);

        return newPlayer;
    }

    private void giveCards(Player player) {
        player.draw(whiteDeck.getCard(randomFunction(0, whiteDeck.getSizeDeck())));
    }


    private void setNextCzar() {
        for (Player player : players) {

            if (player.isCzar()) {

                //verify and removes the actual czar
                int lastCzar = players.indexOf(player);
                player.setCzar(false);

                //if czar is the last index of the vector, sets czar for the first index (0)
                if (lastCzar == players.size()) {
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

    public HashMap<Card, Player> getPlayedCards() {
        return playedCards;
    }

    private void newRound() {

        blackCard = getBlackCard();

        server.startNewRound(blackCard);

        czar.chooseWhiteCard(blackCard);

        setNextCzar();
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
