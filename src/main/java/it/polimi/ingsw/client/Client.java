package it.polimi.ingsw.client;

import it.polimi.ingsw.client.cli.CLI;
import it.polimi.ingsw.client.gui.GUI;

public class Client {

    public static void main(String[] args){
        if(args.length == 1) {

            if ("CLI".equalsIgnoreCase(args[0])) {
                CLI cli = new CLI();
                Thread cliThread = new Thread(cli);
                cliThread.start();
            } else if ("GUI".equalsIgnoreCase(args[0])) {
                GUI gui = new GUI();
                gui.launchGUI();
                System.exit(0);
            } else {
                System.out.println("This argument is not valid");
                System.out.println("The game will close");
                System.exit(-1);
            }
        }
        else {
            System.out.println("An argument is needed");
            System.out.println("The game will close");
            System.exit(-1);
        }

    }

}
