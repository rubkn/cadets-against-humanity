package org.academiadecodigo.stringrays;

import java.util.LinkedList;

public class WhiteDeck {

    private LinkedList<Card> cards = new LinkedList<>();


    public Card getCard(){
        return cards.pop();
    }


}
