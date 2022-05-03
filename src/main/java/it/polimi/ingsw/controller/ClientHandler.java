package it.polimi.ingsw.controller;

import it.polimi.ingsw.constants.ConnectionConstants;
import it.polimi.ingsw.exceptions.NicknameNotAvailableException;
import it.polimi.ingsw.messages.*;

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

    public ClientHandler (Socket socket) throws IOException {
        this.socket = socket;
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
        initialization = true;

    }

    @Override
    public void run() {

        boolean connected = true;

        Thread t = new Thread(() -> {
            while (true){
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

                if (o instanceof String) {
                    if (!o.equals("pong"))
                        System.err.println("this shouldn't happen");
                }

                else if (o instanceof NicknameMessage) {
                    if (Lobby.getInstance().getNicknameFromClientHandler(this) == null) {
                        try {
                            Lobby.getInstance().registerNickname(this, ((NicknameMessage) o).nickname());
                            out.writeObject(new GenericMessage("Welcome " + ((NicknameMessage) o).nickname()));
                            //TODO send available games
                        } catch (NicknameNotAvailableException e) {
                            out.writeObject(new ErrorMessage("Nickname \"" + ((NicknameMessage) o).nickname() + "\" is already taken"));
                        }
                    }
                    else {
                        out.writeObject(new ErrorMessage("You cannot change your nickname in this phase"));
                    }
                }
                else if (Lobby.getInstance().getNicknameFromClientHandler(this) == null)
                    out.writeObject(new ErrorMessage("You have to choose a nickname first"));
                else if (o instanceof NewGameMessage) {
                    if (initialization) {
                        controller = Lobby.getInstance().createNewController(Lobby.getInstance().getNicknameFromClientHandler(this), ((NewGameMessage) o).numOfPlayers(), ((NewGameMessage) o).expertMode());
                        controller.setPropertyChangeListener(this);
                        initialization = false;
                        out.writeObject(new GenericMessage("You have created a new game"));
                    }
                    else
                        out.writeObject(new ErrorMessage("This is not the right game phase"));
                }
                else if (o instanceof AddPlayerMessage) {
                    if (initialization) {
                        controller = Lobby.getInstance().addClientToController(Lobby.getInstance().getNicknameFromClientHandler(this), ((AddPlayerMessage) o).gameID());
                        if (controller != null) {
                            //todo add listeners to controller
                            initialization = false;
                            out.writeObject(new GenericMessage("You have been added to the game"));
                        }
                        else {
                            out.writeObject(new ErrorMessage("Something went wrong, choose another game"));
                            //todo send again games
                        }
                    }
                    else
                        out.writeObject(new ErrorMessage("This is not the right game phase"));
                }
                //TODO message to restore game
                else if (o instanceof Message) {
                    Message answer = controller.messageOnGame(Lobby.getInstance().getNicknameFromClientHandler(this), (Message) o);
                    out.writeObject(answer);
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

        Lobby.getInstance().unregisterNickname(this);

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Message update = (Message) evt.getNewValue();
        try {
            switch (evt.getPropertyName()) {
                case "message" -> out.writeObject(update);//TODO send update to view;
                default -> System.out.println("There will be more properties in the future...");
            }
        } catch(Exception e){
           return;
        }
    }
}
