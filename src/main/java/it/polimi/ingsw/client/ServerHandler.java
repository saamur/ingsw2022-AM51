package it.polimi.ingsw.client;

import it.polimi.ingsw.constants.ConnectionConstants;
import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.messages.updatemessages.UpdateMessage;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * The class ServerHandler handles the connection, the pings and the communication with the server
 *
 */
public class ServerHandler implements Runnable, PropertyChangeListener {

    private final Socket socket;
    private final ObjectOutputStream out;
    private final ObjectInputStream in;

    private final View view;
    private final AtomicBoolean connected;

    /**
     * Creates a ServerHandler with a connection to the given address and port
     * @param address   the IP address of the server
     * @param port      the port of the server
     * @param view      the View that needs the connection, on which the actions corresponding to the messages received will be performed
     * @throws IOException  when it is not possible to establish the connection with the server for any reason
     */
    public ServerHandler (String address, int port, View view) throws IOException {
        this.socket = new Socket(address, port);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
        this.view = view;
        connected = new AtomicBoolean(true);
    }

    @Override
    public void run() {

        while (connected.get()) {

            Object o;
            try {
                socket.setSoTimeout(ConnectionConstants.DISCONNECTION_TIMEOUT);
                o = in.readObject();

                if ("ping".equals(o)) {
                    sendObject("pong");
                }
                else if (o instanceof NicknameAcceptedMessage) {
                    view.setNickname(((NicknameAcceptedMessage) o).nickname());
                }
                else if (o instanceof AvailableGamesMessage) {
                    view.setAvailableGamesMessage ((AvailableGamesMessage) o);
                }
                else if (o instanceof PlayerAddedToGameMessage) {
                    view.playerAddedToGame(((PlayerAddedToGameMessage) o).message());
                }
                else if (o instanceof GameStartedMessage) {
                    view.setGameData(((GameStartedMessage) o).gameData());
                }
                else if (o instanceof GameOverMessage) {
                    view.handleGameOver(((GameOverMessage) o).winnersNickname());
                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            connected.set(false);
                        }
                    }, ConnectionConstants.DISCONNECTION_AFTER_GAME_OVER);
                }
                else if (o instanceof PlayerDisconnectedMessage) {
                    view.handlePlayerDisconnected(((PlayerDisconnectedMessage) o).disconnectedPlayerNickname());
                    break;
                }
                else if (o instanceof UpdateMessage) {
                    view.updateGameData((UpdateMessage) o);
                }
                else if (o instanceof GenericMessage) {
                    view.handleGenericMessage(((GenericMessage) o).message());
                }
                else if (o instanceof ErrorMessage) {
                    view.handleErrorMessage(((ErrorMessage) o).error());
                }
                else if (o instanceof Message) {
                    System.out.println(((Message) o).getMessage());
                    System.out.println("I don't think you will ever get here");
                }
                else
                    System.out.println("This shouldn't happen");

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                view.handleServerDisconnected();
                connected.set(false);
            }
        }

        try {
            socket.close();
        } catch (IOException e) {

        }

    }

    /**
     * The method sendObject sends the given object through the socket to the server
     * @param o the object that will be sent through the net
     * @throws IOException  if any error occurs with the socket
     */
    public synchronized void sendObject (Object o) throws IOException {
        out.writeObject(o);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        try {
            sendObject(evt.getNewValue());
        } catch(Exception e){
            return;
        }
    }

    /**
     * The method disconnect breaks the connection with the server
     */
    public void disconnect(){
        connected.set(false);
    }

}
