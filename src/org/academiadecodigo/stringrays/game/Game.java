package org.academiadecodigo.stringrays.game;
import org.academiadecodigo.stringrays.game.cards.PopulateDeck;
import org.academiadecodigo.stringrays.constants.Constants;
import org.academiadecodigo.stringrays.game.cards.Deck;
import org.academiadecodigo.stringrays.game.player.Player;

import java.io.IOException;
import java.util.Vector;

public class Game {

    private Deck blackDeck;
    private Deck whiteDeck;
    private Vector<Player> players;

    public void init() throws IOException {


        blackDeck = PopulateDeck.fillDeck(Constants.blackDeck);
        whiteDeck = PopulateDeck.fillDeck(Constants.whiteDeck);
        players = new Vector<>();
        createPlayer();
        players.get(randomFunction(0,players.size())).setCzar(true);



    }
    public void start() {


    }

    private void createPlayer() {
        Player newPlayer = new Player();
        for (int i = 0; i < Constants.PLAYER_HAND_SIZE; i++) {
            giveCards(newPlayer);
        }
        players.add(new Player());
    }

    private void giveCards(Player player) {

        player.draw(whiteDeck.getCard(randomFunction(0,whiteDeck.getSizeDeck())));

    }

    private void selectCzar() {

        for (Player player : players) {

            if (player.isCzar()) {

                //verify and removes the actual czar
                int lastCzar = players.indexOf(player);
                player.setCzar(false);

                //if czar is the last index of the vector, sets czar for the first index (0)
                if (lastCzar == players.size()) {
                    players.firstElement().setCzar(true);
                    return;
                }

                //sets the next czar
                players.get(lastCzar + 1).setCzar(true);
                return;
            }
        }


    }



    public int randomFunction ( int min, int max  )
    {
        return (int) ((Math.random() * (max - min)) + min);
    }

}
