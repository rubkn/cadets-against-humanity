package org.academiadecodigo.stringrays.game;

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

    public void start() {

    }

}
