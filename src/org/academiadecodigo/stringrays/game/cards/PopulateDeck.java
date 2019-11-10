package org.academiadecodigo.stringrays.game.cards;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class PopulateDeck {

    public static Deck fillDeck(String path) {

        Deck deck = null;
        File file;

        try {
            deck = new Deck();
            file = new File(path);

            BufferedReader bufferedReader;
            bufferedReader = new BufferedReader((new FileReader(file)));

            String cardMessage;
            while ((cardMessage = bufferedReader.readLine()) != null) {
                deck.addCard(new Card(cardMessage));
            }

            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return deck;
    }
}
