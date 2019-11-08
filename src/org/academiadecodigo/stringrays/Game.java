package org.academiadecodigo.stringrays;

import org.academiadecodigo.stringrays.cards.Card;
import org.academiadecodigo.stringrays.cards.Deck;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Game {

    private Deck blackDeck;
    private Deck whiteDeck;

    public void init() {

        blackDeck = new Deck();
        whiteDeck = new Deck();

        try {
            setupDeck(blackDeck, "resources/black-cards.txt");
            setupDeck(whiteDeck, "resources/white-cards.txt");
        } catch (IOException exc) {
            exc.printStackTrace();
        }

        System.out.println(blackDeck.getCard(4).getMessage());

    }

    public void setupDeck(Deck deck, String path) throws IOException {

        File file = new File(path);

        BufferedReader bufferedReader;
        bufferedReader = new BufferedReader((new FileReader(file)));

        String cardMessage;
        while ((cardMessage = bufferedReader.readLine()) != null) {
            deck.addCard(new Card(cardMessage));
        }

        bufferedReader.close();
    }

}
