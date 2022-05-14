package it.polimi.ingsw.client;

import it.polimi.ingsw.CLI;
import it.polimi.ingsw.client.modeldata.ServerHandler;

import java.io.*;
import java.net.Socket;

public class Client {

    public static void main(String[] args) throws IOException {

        if (args.length != 2) {
            System.err.println(
                    "Usage: java Client <host name> <port number>");
            System.exit(1);
        }

        CLI cli = new CLI();
        Thread cliThread = new Thread(cli);
        cliThread.start();

        Socket socket = new Socket(args[0], Integer.parseInt(args[1]));

        ServerHandler serverHandler = new ServerHandler(socket, cli);
        Thread serverHandlerThread = new Thread(serverHandler);
        cli.addPropertyChangeListener(serverHandler);
        serverHandlerThread.start();

    }

}
