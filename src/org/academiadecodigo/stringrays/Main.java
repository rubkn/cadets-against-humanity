package org.academiadecodigo.stringrays;

import org.academiadecodigo.stringrays.constants.Constants;
import org.academiadecodigo.stringrays.network.Server;

public class Main {

    public static void main(String[] args) {

        Server server = new Server();
        server.start();
    }
}
