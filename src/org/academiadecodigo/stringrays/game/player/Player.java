package org.academiadecodigo.stringrays.game.player;

import org.academiadecodigo.stringrays.game.cards.Card;
import org.academiadecodigo.stringrays.game.cards.Deck;
import org.academiadecodigo.stringrays.game.cards.PlayerHand;
import org.academiadecodigo.stringrays.network.PlayerHandler;

public class Player {

    private String nickname;
    private boolean isCzar;
    private boolean alreadyPlayed;
    private int score = 0;
    private PlayerHand hand;
    private PlayerHandler playerHandler;

    public Player() {
        this.hand = new PlayerHand();
    }

    public void draw(Card card) {
        hand.addCard(card);

        //draw from the white deck and insert into player's hand
        //hand.addCard(whiteDeck.getCard());

    }

    public Card choose(int index) {

        return hand.getCard(index);
    }

    public Card chooseWinCard(int index, Deck playedCards) {
        return playedCards.getCard(index);
    }

    public boolean isCzar() {
        return isCzar;
    }

    public void setCzar(boolean czar) {
        isCzar = czar;
    }

    public boolean alreadyPlayed() {
        return alreadyPlayed;
    }


    public void setAlreadyPlayed(boolean alreadyPlayed) {
        this.alreadyPlayed = alreadyPlayed;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String[] getCardMessages() {
        return hand.getCardMessages();
    }

    public void setPlayerHandler(PlayerHandler playerHandler) {
        this.playerHandler = playerHandler;
    }
}
