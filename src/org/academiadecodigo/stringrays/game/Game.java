package org.academiadecodigo.stringrays.game;

import org.academiadecodigo.stringrays.constants.Messages;
import org.academiadecodigo.stringrays.game.cards.PopulateDeck;
import org.academiadecodigo.stringrays.constants.Constants;
import org.academiadecodigo.stringrays.game.cards.Deck;
import org.academiadecodigo.stringrays.game.player.Player;
import org.academiadecodigo.stringrays.network.Server;

import java.util.Vector;

public class Game {

    private Deck blackDeck;
    private Deck whiteDeck;
    private Deck playedCards;
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

        if (players.size() >= Constants.MIN_NUMBER_OF_PLAYERS) {
                start();
            }
    }

    private void start() {
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


    private void newRound() {
        for (Player player : players) {

            if (player.isCzar() || player.alreadyPlayed()) {
                continue;
            }

            playedCards.addCard(player.choose(0)); //Need to review this one
            player.setAlreadyPlayed(true);
        }

        //after all players, already played
        chooseWinner(0); //Need to review this one too
    }

    private void chooseWinner(int index) {
        czar.chooseWinCard(index, playedCards);
    }


    public int randomFunction(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public void setServer(Server server) {
        this.server = server;
    }
}
