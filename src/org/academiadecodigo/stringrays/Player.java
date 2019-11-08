package org.academiadecodigo.stringrays;


import java.util.ArrayList;

public class Player {

    private String nickname;


    private boolean isCzar = false;
    private boolean isWaiting = false;

    private ArrayList<Card> handDeck = new ArrayList<>();
    private WhiteDeck whiteDeck;

    public Player(String nickname){

        this.nickname = nickname;

    }


    public void draw(){

        //draw from the white deck and insert into player's hand
        handDeck.add(whiteDeck.getCard());

    }

    public Card choose(int index){

        //choose the index one of the cards from the hand deck
        //return the card to the game choices

        return handDeck.remove(index);
    }


    public Card play(int index){

        draw();

        return choose(index);
    }



    //////////////////////////////////////////////////////////////////////////////////////////
    public boolean isCzar() {
        return isCzar;
    }

    public void setCzar(boolean czar) {
        isCzar = czar;
    }

}
