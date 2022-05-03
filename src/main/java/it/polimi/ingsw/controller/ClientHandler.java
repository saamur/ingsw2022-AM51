package it.polimi.ingsw.controller;

import it.polimi.ingsw.constants.ConnectionConstants;
import it.polimi.ingsw.exceptions.NicknameNotAvailableException;
import it.polimi.ingsw.messages.*;

import java.io.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import static java.lang.Thread.sleep;

public class ClientHandler implements Runnable, PropertyChangeListener {

    private final Socket socket;
    private final ObjectOutputStream out;
    private final ObjectInputStream in;
    private boolean initialization;
    private Controller controller;

    private volatile boolean ping;

    public ClientHandler (Socket socket) throws IOException {
        this.socket = socket;
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
        initialization = true;
        ping = true;
    }

    @Override
    public void run() {

        boolean connected = true;

        Thread t = new Thread(() -> {
            while (ping){
                try {
                    sleep(ConnectionConstants.PING_TIME);
                } catch (InterruptedException e) {
                    //e.printStackTrace();
                }
                try {
                    out.writeObject("ping");
                } catch (IOException e) {
                    //e.printStackTrace();
                }
            }
        });
        t.start();

        while (connected) {
            try {
                socket.setSoTimeout(ConnectionConstants.DISCONNECTION_TIMEOUT);
                Object o = in.readObject();
                System.out.println("Object received: " + o);

                if (o instanceof String) {
                    if (!o.equals("pong"))
                        System.err.println("this shouldn't happen");
                }

                else if (o instanceof NicknameMessage) {
                    if (Lobby.getInstance().getNicknameFromClientHandler(this) == null) {
                        try {
                            Lobby.getInstance().registerNickname(this, ((NicknameMessage) o).nickname());
                            sendObject(new GenericMessage("Welcome " + ((NicknameMessage) o).nickname()));
                            sendObject(Lobby.getInstance().createAvailableGamesMessage(((NicknameMessage) o).nickname()));
                        } catch (NicknameNotAvailableException e) {
                            sendObject(new ErrorMessage("Nickname \"" + ((NicknameMessage) o).nickname() + "\" is already taken"));
                        }
                    }
                    else {
                        sendObject(new ErrorMessage("You cannot change your nickname in this phase"));
                    }
                }
                else if (Lobby.getInstance().getNicknameFromClientHandler(this) == null)
                    sendObject(new ErrorMessage("You have to choose a nickname first"));
                else if (o instanceof NewGameMessage) {
                    if (initialization) {
                        controller = Lobby.getInstance().createNewController(Lobby.getInstance().getNicknameFromClientHandler(this), ((NewGameMessage) o).numOfPlayers(), ((NewGameMessage) o).expertMode());
                        controller.setPropertyChangeListener(this);
                        initialization = false;
                        sendObject(new GenericMessage("You have created a new game"));
                    }
                    else
                        sendObject(new ErrorMessage("This is not the right game phase"));
                }
                else if (o instanceof AddPlayerMessage) {
                    if (initialization) {
                        controller = Lobby.getInstance().addClientToController(Lobby.getInstance().getNicknameFromClientHandler(this), ((AddPlayerMessage) o).gameID());
                        if (controller != null) {
                            controller.setPropertyChangeListener(this);
                            initialization = false;
                            sendObject(new GenericMessage("You have been added to the game"));
                        }
                        else {
                            sendObject(new ErrorMessage("Something went wrong, choose another game"));
                            sendObject(Lobby.getInstance().createAvailableGamesMessage(Lobby.getInstance().getNicknameFromClientHandler(this)));
                        }
                    }
                    else
                        sendObject(new ErrorMessage("This is not the right game phase"));
                }
                else if (o instanceof RestoreGameMessage) {
                    if (initialization) {
                        controller = Lobby.getInstance().createNewRestoredGameController(Lobby.getInstance().getNicknameFromClientHandler(this), ((RestoreGameMessage) o).savedGameData());
                        if (controller != null) {
                            controller.setPropertyChangeListener(this);
                            initialization = false;
                            sendObject(new GenericMessage("You have reloaded the game"));
                        }
                        else {
                            sendObject(new ErrorMessage("Something went wrong, choose another game"));
                            sendObject(Lobby.getInstance().createAvailableGamesMessage(Lobby.getInstance().getNicknameFromClientHandler(this)));
                        }
                    }
                }
                else if (!initialization) {
                    sendObject(new ErrorMessage("This is not the correct game phase"));
                }
                else if (o instanceof Message) {
                    Message answer = controller.messageOnGame(Lobby.getInstance().getNicknameFromClientHandler(this), (Message) o);
                    sendObject(answer);
                }
                else
                    System.err.println("this shouldn't happen");

            } catch (IOException e) {           //non tutte le IOException in realtà
                System.out.println(Lobby.getInstance().getNicknameFromClientHandler(this) + " has disconnected");
                controller.clientDisconnected(Lobby.getInstance().getNicknameFromClientHandler(this));
                t.interrupt();
                connected = false;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }

        ping = false;

        Lobby.getInstance().unregisterNickname(this);

    }

    public synchronized void sendObject (Object o) throws IOException {
        out.writeObject(o);
        System.out.println("Sent object: " + o);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Message update = (Message) evt.getNewValue();
        try {
            switch (evt.getPropertyName()) {
                case "message" -> sendObject(update);//TODO send update to view;
                default -> System.out.println("There will be more properties in the future...");
            }
        } catch(Exception e){
           return;
        }
    }
}
