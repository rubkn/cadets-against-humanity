package org.academiadecodigo.stringrays.game.player;

import org.academiadecodigo.stringrays.game.cards.Card;
import org.academiadecodigo.stringrays.game.cards.Hand;

public class Player {

    private String nickname;
    private boolean isCzar;
    private int score = 0;
    private Hand hand;
    private boolean ready;

    public Player() {
        this.hand = new Hand();
    }

    public void draw(Card card) {
        hand.addCard(card);
    }

    public void roundWon() {
        score++;
    }

    public Card getCard(int index) {
        return hand.getCard(index);
    }

    public boolean isCzar() {
        return isCzar;
    }

    public void setCzar(boolean czar) {
        isCzar = czar;
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

    public int getScore() {
        return score;
    }

    public void reset() {
        score = 0;
        isCzar = false;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public boolean isReady() {
        return ready;
    }
}
