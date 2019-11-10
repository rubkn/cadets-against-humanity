package org.academiadecodigo.stringrays.game.cards;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Vector;

public class Deck {

    protected ArrayList<Card> deck;

    public Deck() {
        deck = new ArrayList<>();
    }

    public Card getCard(int index) {
        return deck.remove(index);
    }

    public void addCard(Card card) {
        deck.add(card);
    }

    public int getSizeDeck()
    {
        return deck.size();

    }







}
