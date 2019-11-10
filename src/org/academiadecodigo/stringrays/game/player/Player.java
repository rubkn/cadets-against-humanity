package org.academiadecodigo.stringrays.game.player;

import org.academiadecodigo.stringrays.constants.Constants;
import org.academiadecodigo.stringrays.constants.Messages;
import org.academiadecodigo.stringrays.game.Game;
import org.academiadecodigo.stringrays.game.cards.Card;
import org.academiadecodigo.stringrays.game.cards.Hand;
import org.academiadecodigo.stringrays.network.PlayerHandler;

public class Player {

    private String nickname;
    private boolean isCzar;
    private int score = 0;
    private Hand hand;
    private PlayerHandler playerHandler;
    private boolean ready;
    private Game game;

    public Player() {
        this.hand = new Hand();
    }

    public void draw(Card card) {
        hand.addCard(card);

        //draw from the white deck and insert into player's hand
        //hand.addCard(whiteDeck.getCard());

    }

    /*
    public void chooseWhiteCard(Card blackCard) {

        if (isCzar) {
            playerHandler.sendMessageToPlayer(Messages.CZAR_TURN_MESSAGE);
            return;
        }

        int cardIndex = playerHandler.chooseCard(blackCard.getMessage(), getCardMessages(), Messages.PLAYER_TURN_MESSAGE);
        game.getPlayedCards().put(getCard(cardIndex - Constants.CONVERT_PROMPT_VIEW_INDEX), this);
    }

    public Card chooseWinner(Card blackCard, Hand czarHand) {
        int index = 1; //playerHandler.chooseCard(blackCard.getMessage(), getCardMessages(), Messages.PLAYER_TURN_MESSAGE);
        Card czarChosenCard = czarHand.getCard(index - Constants.CONVERT_PROMPT_VIEW_INDEX);
        System.out.println("Black Card: " + blackCard.getMessage());
        System.out.println("Czar " + getNickname() + " chose: " + czarChosenCard.getMessage());
        return czarChosenCard;
    }
    */

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

    public void setPlayerHandler(PlayerHandler playerHandler) {
        this.playerHandler = playerHandler;
    }

    public int getScore() {
        return score;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
