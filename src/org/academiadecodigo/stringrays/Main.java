package org.academiadecodigo.stringrays;

import org.academiadecodigo.stringrays.constants.Constants;
import org.academiadecodigo.stringrays.network.Server;

public class Main {

    public static void main(String[] args) {

        System.out.println("====== Ca(r)dets Against Humanity Server Running ======\n" +
                "Tell others to connect utilizing Netcat to your IP on port " + Constants.PORT_NUMBER +
                "\nInstructions to join game on this computer: Open terminal and do \"nc localhost " +
                Constants.PORT_NUMBER + "\"");

        Server server = new Server();

        server.start();
    }
}
