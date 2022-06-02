package it.polimi.ingsw.controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * The class Server listens to the port given as argument and creates new sockets for the clients that connect to it
 *
 */
public class Server {

    public static void main(String[] args) {

        if (args.length != 1) {
            System.err.println("Usage: java Server.java <port number>");
            System.exit(1);
        }

        int portNumber = Integer.parseInt(args[0]);

        try (
                ServerSocket serverSocket =
                        new ServerSocket(Integer.parseInt(args[0]));

        ) {
            System.out.println("server online");
            while (true) {

                Socket clientSocket = serverSocket.accept();
                System.out.println("new client connected");
                Lobby.getInstance().addClient(clientSocket);

            }
        } catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                    + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }

        System.out.println("closing...");

    }

}
