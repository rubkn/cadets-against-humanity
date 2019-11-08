package org.academiadecodigo.stringrays.network;

import org.academiadecodigo.stringrays.game.Game;


import java.util.Vector;

public class Server {

    private Prompt prompt;
    private int portNumber;
    private Vector<PlayerHandler> playerHandlers;

    public Server(int portNumber) {
        this.portNumber = portNumber;
    }

    private void init() {
    }

    public void start() {

    }

    //distribuir novos PlayerHandler
}
