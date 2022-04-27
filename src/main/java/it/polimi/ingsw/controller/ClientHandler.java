package it.polimi.ingsw.controller;

import it.polimi.ingsw.ConnectionConstants;
import it.polimi.ingsw.exceptions.NicknameNotAvailableException;
import it.polimi.ingsw.messages.ErrorMessage;
import it.polimi.ingsw.messages.GenericMessage;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.NicknameMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import static java.lang.Thread.sleep;

public class ClientHandler implements Runnable{

    private final Socket socket;
    private final ObjectOutputStream out;
    private final ObjectInputStream in;
    private boolean initialization;
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

                if (o instanceof NicknameMessage) {
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
                //TODO messages to join in game
                else if (o instanceof Message) {
                    //TODO call on controller
                }
                else if (!(o instanceof String && o.equals("pong")))
                    System.err.println("this shouldn't happen");

            } catch (IOException e) {           //non tutte le IOException in realtà
                System.out.println(Lobby.getInstance().getNicknameFromClientHandler(this) + " has disconnected");
                //TODO notify other players and remove the nicknames from the lobby
                t.interrupt();
                connected = false;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

}
