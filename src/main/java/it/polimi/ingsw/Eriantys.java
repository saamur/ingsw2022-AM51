package it.polimi.ingsw;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.constants.ConnectionConstants;
import it.polimi.ingsw.controller.Server;

import java.util.Scanner;

/**
 * The class Eriantys is the main class of the game, from which both the client and the server are opened
 *
 */
public class Eriantys {

    public static void main(String[] args) {

        System.out.println("Welcome to the Eriantys board game!");

        if(args.length == 1){

            if ("SERVER".equalsIgnoreCase(args[0])) {
                Scanner scanner = new Scanner(System.in);

                int port = -1;
                do {
                    System.out.println("Insert server port, press enter for default (" + ConnectionConstants.DEFAULT_PORT + "):");
                    String line = scanner.nextLine();
                    if ("".equals(line))
                        port = ConnectionConstants.DEFAULT_PORT;
                    else {
                        try {
                            port = Integer.parseInt(line);
                            if (port < 0) {
                                System.out.println("This is not valid");
                                port = -1;
                            }
                        } catch (IllegalArgumentException e) {
                            System.out.println("This is not a number");
                        }
                    }
                } while (port == -1);

                String[] arguments = new String[1];
                arguments[0] = Integer.toString(port);
                Server.main(arguments);

            }

            else if ("CLI".equalsIgnoreCase(args[0]) || "GUI".equalsIgnoreCase(args[0])) {
                Client.main(args);
            }
        }
        else if (args.length == 0){
            String[] arguments = new String[1];
            arguments[0] = "GUI";
            Client.main(arguments);
        }
        else {
            System.out.println("Usage: java Eriantys.java <\"SERVER\" for the server, \"CLI\" for the CLI, nothing for the GUI>");
        }

    }

}
