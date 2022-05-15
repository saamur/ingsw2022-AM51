package it.polimi.ingsw.client;

import it.polimi.ingsw.CLI;
import it.polimi.ingsw.client.modeldata.ServerHandler;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws IOException {

        if (args.length != 2) {
            System.err.println(
                    "Usage: java Client <host name> <port number>");
            System.exit(1);
        }

        System.out.println("Welcome to the Eriantys board game!");
        System.out.println("First of all choose if you want to use the cli or the gui");

        View view = null;

        Scanner scanner = new Scanner(System.in);
        String choice = scanner.nextLine().toUpperCase();

        if ("CLI".equals(choice)) {
            CLI cli = new CLI();
            Thread cliThread = new Thread(cli);
            cliThread.start();
            view = cli;
        }
        else if ("GUI".equals(choice)) {
            //todo
        }
        else {
            System.out.println("This choice is not valid");
            System.out.println("The game will close");
            System.exit(-1);
        }

        Socket socket = new Socket(args[0], Integer.parseInt(args[1]));

        ServerHandler serverHandler = new ServerHandler(socket, view);
        Thread serverHandlerThread = new Thread(serverHandler);
        view.addPropertyChangeListener(serverHandler);
        serverHandlerThread.start();

    }

}
