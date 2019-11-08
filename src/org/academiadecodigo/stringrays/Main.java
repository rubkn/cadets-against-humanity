package org.academiadecodigo.stringrays;

import org.academiadecodigo.stringrays.network.Server;

public class Main {

    public static void main(String[] args) {

        int portNumber = 1;
        Server server = new Server(portNumber);

        server.start();
    }
}
