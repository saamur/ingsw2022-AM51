package it.polimi.ingsw.client.modeldata;

import it.polimi.ingsw.client.View;
import it.polimi.ingsw.constants.ConnectionConstants;
import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.messages.updatemessages.UpdateMessage;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerHandler implements Runnable, PropertyChangeListener {

    private final Socket socket;
    private final ObjectOutputStream out;
    private final ObjectInputStream in;

    private final View view;

    public ServerHandler (Socket socket, View view) throws IOException {
        this.socket = socket;
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
        this.view = view;
    }

    @Override
    public void run() {

        boolean connected = true;

        while (connected) {

            Object o;
            try {
                socket.setSoTimeout(ConnectionConstants.DISCONNECTION_TIMEOUT);
                o = in.readObject();
//                if (!(o instanceof String))
//                    System.out.println("Message received: " + o);

                if ("ping".equals(o)) {
                    sendObject("pong");
                }
                else if (o instanceof NicknameAcceptedMessage) {
                    view.setNickname(((NicknameAcceptedMessage) o).nickname());
                }
                else if (o instanceof AvailableGamesMessage) {
                    view.setAvailableGamesMessage ((AvailableGamesMessage) o);
                    //todo print things
                }
                else if (o instanceof GameStartedMessage) {
                    view.setGameData(((GameStartedMessage) o).gameData());
                    //todo make game start
                }
                else if (o instanceof GameOverMessage) {
                    view.handleGameOver(((GameOverMessage) o).winnersNickname());
                    break;
                }
                else if (o instanceof PlayerDisconnectedMessage) {
                    view.handlePlayerDisconnected(((PlayerDisconnectedMessage) o).disconnectedPlayerNickname());
                    System.out.println(((Message) o).getMessage());
                    break;
                }
                else if (o instanceof UpdateMessage) {
                    System.out.println("UpdateMessage: " + ((Message) o).getMessage());         //FIXME debug
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
                System.out.println("The server has disconnected");
                connected = false;
            }
        }

    }

    public synchronized void sendObject (Object o) throws IOException {
        out.writeObject(o);
        if(!(o instanceof String))
            System.out.println("Sent Message : "+ o);
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
