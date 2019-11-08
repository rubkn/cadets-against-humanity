package org.academiadecodigo.stringrays.game;

import org.academiadecodigo.stringrays.constants.Constants;
import org.academiadecodigo.stringrays.game.cards.Card;
import org.academiadecodigo.stringrays.game.cards.Deck;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class Game {

    private Deck blackDeck;
    private Deck whiteDeck;
    private Vector<Player> players;

    public void init() {

        blackDeck = new Deck();
        whiteDeck = new Deck();
        players = new Vector<>();

        try {
            setupDeck(blackDeck, "resources/black-cards.txt");
            setupDeck(whiteDeck, "resources/white-cards.txt");


        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }

    private void setupDeck(Deck deck, String path) throws IOException {

        File file = new File(path);

        BufferedReader bufferedReader;
        bufferedReader = new BufferedReader((new FileReader(file)));

        String cardMessage;
        while ((cardMessage = bufferedReader.readLine()) != null) {
            deck.addCard(new Card(cardMessage));
        }

        bufferedReader.close();
    }

    private void createPlayer() {
        Player newPlayer = new Player();

        for (int i = 0; i < Constants.NUMBER_OF_CARDS_IN_PLAYER_HAND; i++) {
            giveCards(newPlayer);
        }

        players.add(new Player());
    }

    private void giveCards(Player player) {
        player.draw(whiteDeck.getCard(1));
        //TODO do randomizer for cards
    }

    private void selectCzar() {

        for (Player player : players) {

            if (player.isCzar()) {

                //verify and removes the actual czar
                int lastCzar = players.indexOf(player);
                player.setCzar(false);

                //if czar is the last index of the vector, sets czar for the first index (0)
                if (lastCzar == players.size()) {
                    players.firstElement().setCzar(true);
                    return;
                }

                //sets the next czar
                players.get(lastCzar + 1).setCzar(true);
                return;
            }
        }


    }

    public void start() {

    }

}
