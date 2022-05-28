package it.polimi.ingsw;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.GUI.GUI;
import it.polimi.ingsw.controller.Server;

import java.util.Scanner;

public class Eriantys {
    public static void main(String[] args) {
        if(args.length != 0){
            System.out.println("Welcome to the Eriantys board game!");
            System.out.println("First of all choose if you want to use the client or start the server");
            System.out.println("Type \"client\" to use the client or \"server\" to start the server");

            Scanner scanner = new Scanner(System.in);
            String choice = scanner.nextLine().toUpperCase();

            if("SERVER".equals(choice)){
                Server.main(args);
            } else if ("CLI".equals(choice)){
                Client.main(args);
            } else {
                System.out.println("The answer was not satisfying");
            }
        } else {
            GUI.main(null);
        }
    }
}
