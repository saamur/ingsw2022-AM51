package it.polimi.ingsw.client;

import it.polimi.ingsw.client.CLI.CLI;
import it.polimi.ingsw.client.GUI.GUI;
import it.polimi.ingsw.client.modeldata.ServerHandler;
import javafx.application.Platform;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

import static java.lang.Thread.currentThread;

public class Client {

    public static void main(String[] args){
        if(args.length != 0) {
            System.out.println("Welcome to the Eriantys board game!");
            System.out.println("First of all choose if you want to use the cli or the gui");


            Scanner scanner = new Scanner(System.in);
            String choice = scanner.nextLine().toUpperCase();

            if ("CLI".equals(choice)) {
                CLI cli = new CLI();
                Thread cliThread = new Thread(cli);
                cliThread.start();
            } else if ("GUI".equals(choice)) {
                GUI gui = new GUI();
                gui.launchGUI();
                System.exit(0);
            } else {
                System.out.println("This choice is not valid");
                System.out.println("The game will close");
                System.exit(-1);
            }
        } else {
            GUI gui = new GUI();
            gui.launchGUI();
            System.exit(0);
        }



    }

}
