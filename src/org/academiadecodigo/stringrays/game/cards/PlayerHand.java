package org.academiadecodigo.stringrays.game.cards;

public class PlayerHand extends Deck {

    public String[] getCardMessages() {
        String[] cardMessages = new String[deck.size()];
        for (Card card : deck) {
            cardMessages[deck.indexOf(card)] = card.getMessage();
        }
        return cardMessages;
    }
}
