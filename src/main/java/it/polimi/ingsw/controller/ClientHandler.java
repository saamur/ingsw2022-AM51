package it.polimi.ingsw.controller;

import it.polimi.ingsw.constants.ConnectionConstants;
import it.polimi.ingsw.exceptions.NicknameNotAvailableException;
import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.messages.gamemessages.GameMessage;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;


/**
 * class ClientHandler handles the connection with the client
 *
 */
public class ClientHandler implements Runnable, PropertyChangeListener {

    private final Socket socket;
    private final ObjectOutputStream out;
    private final ObjectInputStream in;
    private boolean initialization;
    private Controller controller;

    public ClientHandler (Socket socket) throws IOException {
        this.socket = socket;
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
        initialization = true;
    }

    @Override
    public void run() {

        boolean connected = true;

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    sendObject("ping");
                } catch (IOException e) {
                    //e.printStackTrace();
                }
            }
        }, 0, ConnectionConstants.PING_TIME);

        while (connected) {
            try {
                System.out.println(controller);
                socket.setSoTimeout(ConnectionConstants.DISCONNECTION_TIMEOUT);
                Object o = in.readObject();
                if(!(o instanceof String))
                    System.out.println("Object received from " + Lobby.getInstance().getNicknameFromClientHandler(this) + ": " + o);

                if ("pong".equals(o)) {
                }
                else if (o instanceof NicknameMessage) {
                    if (Lobby.getInstance().getNicknameFromClientHandler(this) == null) {
                        try {
                            Lobby.getInstance().registerNickname(this, ((NicknameMessage) o).nickname());
                            sendObject(new NicknameAcceptedMessage(((NicknameMessage) o).nickname()));
                            sendObject(Lobby.getInstance().createAvailableGamesMessage(((NicknameMessage) o).nickname()));
                        } catch (NicknameNotAvailableException e) {
                            sendObject(new ErrorMessage("Nickname \"" + ((NicknameMessage) o).nickname() + "\" is already taken"));
                        }
                    }
                    else {
                        sendObject(new ErrorMessage("You cannot change your message in this phase"));
                    }
                }
                else if (Lobby.getInstance().getNicknameFromClientHandler(this) == null) {
                    sendObject(new ErrorMessage("You have to choose a nickname first"));
                }
                else if (o instanceof NewGameMessage) {
                    if (initialization) {
                        controller = Lobby.getInstance().createNewGameController(Lobby.getInstance().getNicknameFromClientHandler(this), ((NewGameMessage) o).numOfPlayers(), ((NewGameMessage) o).expertMode());
                        if (controller != null) {
                            controller.setPropertyChangeListener(this);
                            initialization = false;
                            sendObject(new PlayerAddedToGameMessage("You have created a new game"));
                        }
                        else {
                            int n = ((NewGameMessage) o).numOfPlayers();
                            sendObject(new ErrorMessage("You cannot create a game with " + n + (n != 1 ? " players" : " player")));
                        }
                    }
                    else
                        sendObject(new ErrorMessage("This is not the right game phase"));
                }
                else if (o instanceof AddPlayerMessage) {
                    if (initialization) {
                        controller = Lobby.getInstance().getOpeningController(((AddPlayerMessage) o).gameID());
                        if (controller != null) {
                            controller.setPropertyChangeListener(this);
                            controller.addPlayer(Lobby.getInstance().getNicknameFromClientHandler(this));
                            initialization = false;
                            sendObject(new PlayerAddedToGameMessage("You have been added to the game"));
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
                            sendObject(new PlayerAddedToGameMessage("You have reloaded the game"));
                        }
                        else {
                            sendObject(new ErrorMessage("Something went wrong, choose another game"));
                            sendObject(Lobby.getInstance().createAvailableGamesMessage(Lobby.getInstance().getNicknameFromClientHandler(this)));
                        }
                    }
                }
                else if (initialization) {
                    sendObject(new ErrorMessage("This is not the correct game phase"));
                }
                else if (o instanceof GameMessage) {
                    sendObject(controller.messageOnGame(Lobby.getInstance().getNicknameFromClientHandler(this), (GameMessage) o));
                }
                else
                    System.err.println("This shouldn't happen");

            } catch (IOException e) {           //non tutte le IOException in realtà
                System.out.println(Lobby.getInstance().getNicknameFromClientHandler(this) + " has disconnected");
                if (controller != null)
                    controller.clientDisconnected(Lobby.getInstance().getNicknameFromClientHandler(this));
                connected = false;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }

        timer.cancel();
        Lobby.getInstance().unregisterNickname(this);

    }

    /**
     * method sendObject sends the given object through the socket
     * @param o the object that will be sent through the net
     * @throws IOException  if any error occurs with the socket
     */
    public synchronized void sendObject (Object o) throws IOException {
        out.writeObject(o);
        if(!(o instanceof String))
            System.out.println("Sent Message : " + o);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        try {
            sendObject(evt.getNewValue());
        } catch(Exception e){
            return;
        }
    }
}
