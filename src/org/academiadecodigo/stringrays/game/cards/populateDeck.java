package org.academiadecodigo.stringrays.game.cards;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class populateDeck {



    public static Deck fillDeck( String path) throws IOException {


            Deck deck = new Deck();
            File file = new File(path);

            BufferedReader bufferedReader;
            bufferedReader = new BufferedReader((new FileReader(file)));

            String cardMessage;
            while ((cardMessage = bufferedReader.readLine()) != null) {
                deck.addCard(new Card(cardMessage));
            }

            bufferedReader.close();
            return deck;
    }
}
