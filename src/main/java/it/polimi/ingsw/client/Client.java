package it.polimi.ingsw.client;

import it.polimi.ingsw.constants.ConnectionConstants;
import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.model.Clan;

import javax.print.attribute.standard.Finishings;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

import static java.lang.Thread.sleep;

public class Client implements Runnable{

    private final Socket socket;
    private final ObjectOutputStream out;
    private final ObjectInputStream in;

    private Client (String hostName, int portNumber) throws IOException {
        socket = new Socket(hostName, portNumber);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
    }

    public static void main(String[] args) {

        String hostName;
        int portNumber;
        Client instance = null;

        if (args.length != 2) {
            System.err.println(
                    "Usage: java Client <host name> <port number>");
            System.exit(1);
        }

        hostName = args[0];
        portNumber = Integer.parseInt(args[1]);

        System.out.println("acceso");

        try {
            instance = new Client(hostName, portNumber);
            System.out.println("connected");
        } catch (IOException e) {
            System.out.println("An error has occurred");
            System.out.println("Closing...");
            System.exit(-1);
        }

        new Thread(instance).start();

    }

    @Override
    public void run() {

        Thread t = new Thread(() -> {
            BufferedReader stdIn =
                    new BufferedReader(
                            new InputStreamReader(System.in));
            while (true){
                Message message = null;
                try {
                    message = commandParser(stdIn.readLine());
                    if (message != null)
                        sendObject(message);
                    else
                        ;//todo avvisa
                } catch (IOException e) {
                    //e.printStackTrace();
                }

            }
        });
        t.start();

        boolean connected = true;

        while (connected) {

            Object o;
            try {
                socket.setSoTimeout(ConnectionConstants.DISCONNECTION_TIMEOUT);
                o = in.readObject();
                if (o instanceof String) {
                    if (o.equals("ping")) {
                        //System.out.println("ping ricevuto");
                        sendObject("pong");
                    }
                    else {
                        System.out.println("This shouldn't happen");
                        break;
                    }
                }
                else if (o instanceof AvailableGamesMessage) {
                    //todo print things
                }
                else if (o instanceof GameOverMessage) {
                    //todo print things
                    break;
                }
                else if (o instanceof PlayerDisconnectedMessage) {
                    System.out.println(((Message) o).getMessage());
                    break;
                }
                else if (o instanceof Message) {
                    System.out.println(((Message) o).getMessage());
                }

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                System.out.println("The server disconnected");
                connected = false;
            }

        }

        t.interrupt();

        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Closing...");

    }

    public synchronized void sendObject (Object o) throws IOException {
        out.writeObject(o);
    }


    public synchronized Message commandParser (String line) {

        Message message = null;

        String[] words = line.split(" ");
        if (words.length == 0)
            return null;

        switch (words[0].toLowerCase()) {
            case "nickname":
                if (words.length != 2)
                    return null;
                message = new NicknameMessage(words[1]);
                break;
            case "movestudenttochamber":
                if (words.length != 2)
                    return null;
                try {
                    message = new MoveStudentToChamberMessage(Clan.valueOf(words[1].toUpperCase()));
                }
                catch (IllegalArgumentException e) {
                    return null;
                }
                break;
            case "movestudenttoisland":
                if (words.length != 3)
                    return null;
                try {
                    message = new MoveStudentToIslandMessage(Clan.valueOf(words[1].toUpperCase()), Integer.parseInt(words[2]));
                } catch (Exception e) {
                    return null;
                }
                break;

        }

        return message;

    }

}
