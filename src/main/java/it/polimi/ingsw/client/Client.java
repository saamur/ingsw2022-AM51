package it.polimi.ingsw.client;

import it.polimi.ingsw.constants.ConnectionConstants;
import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.model.Clan;
import it.polimi.ingsw.model.charactercards.CharacterID;
import it.polimi.ingsw.model.player.Card;

import javax.print.attribute.standard.Finishings;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.EnumMap;
import java.util.Map;

import static java.lang.Thread.sleep;

public class Client implements Runnable {

    private final Socket socket;
    private final ObjectOutputStream out;
    private final ObjectInputStream in;

    private AvailableGamesMessage availableGamesMessage;

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

        System.out.println("on");

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
                Message message;
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
                if(!(o instanceof String))
                    System.out.println("Message received: " + o);
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
                    availableGamesMessage = (AvailableGamesMessage) o;
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
        if(!(o instanceof String))
            System.out.println("Sent Message : "+ o);
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
            case "joingame":
                if (words.length != 2)
                    return null;
                try {
                    message = new AddPlayerMessage(Integer.parseInt(words[1]));
                } catch (IllegalArgumentException e) {
                    return null;
                }
                break;
            case "restoregame":
                if (availableGamesMessage == null || words.length != 2)
                    return null;
                int index;
                try {
                    index = Integer.parseInt(words[1]);
                }
                catch (NumberFormatException e) {
                    return null;
                }
                if (index >= availableGamesMessage.savedGameData().size())
                    return null;
                message = new RestoreGameMessage(availableGamesMessage.savedGameData().get(index));
                break;
            case "createnewgame":
                if (words.length != 3)
                    return null;
                try {
                    message = new NewGameMessage(Integer.parseInt(words[1]), Boolean.parseBoolean(words[2]));
                } catch (IllegalArgumentException e) {
                    return null;
                }
                break;
            case "chosencard":
                if (words.length != 2)
                    return null;
                try {
                    message = new ChosenCardMessage(Card.valueOf(words[1].toUpperCase()));
                } catch (IllegalArgumentException e) {
                    return null;
                }
                break;
            case "movestudenttochamber":
                if (words.length != 2)
                    return null;
                try {
                    message = new MoveStudentToChamberMessage(Clan.valueOf(words[1].toUpperCase()));
                } catch (IllegalArgumentException e) {
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
            case "movemothernature":
                if (words.length != 2)
                    return null;
                try {
                    message = new MoveMotherNatureMessage(Integer.parseInt(words[1]));
                } catch (NumberFormatException e) {
                    return null;
                }
                break;
            case "chosencloud":
                if (words.length != 2)
                    return null;
                try {
                    message = new ChosenCloudMessage(Integer.parseInt(words[1]));
                } catch (NumberFormatException e) {
                    return null;
                }
                break;
            case "endturn":
                if (words.length != 1)
                    return null;
                message = new EndTurnMessage();
                break;
            case "activatecharactercard":
                if (words.length != 2)
                    return null;
                try {
                    message = new ActivateCharacterCardMessage(CharacterID.valueOf(words[1].toUpperCase()));
                } catch (IllegalArgumentException e) {
                    return  null;
                }
                break;
            case "applycharactercardeffect":
                if (words.length == 2) {
                    try {
                        message = new ApplyCharacterCardEffectMessage1(Integer.parseInt(words[1]));
                    } catch (NumberFormatException e) {
                        return null;
                    }
                }
                else if (words.length == 12) {
                    try {
                        Map<Clan, Integer> students1 = new EnumMap<>(Clan.class);
                        Map<Clan, Integer> students2 = new EnumMap<>(Clan.class);
                        for (int i = 0; i < Clan.values().length; i++)
                            students1.put(Clan.values()[i], Integer.parseInt(words[2 + i]));
                        for (int i = 0; i < Clan.values().length; i++)
                            students2.put(Clan.values()[i], Integer.parseInt(words[7 + i]));
                        message = new ApplyCharacterCardEffectMessage2(Integer.parseInt(words[1]), students1, students2);
                    } catch (NumberFormatException e) {
                        return null;
                    }
                }
                break;
            case "setclancharactercard":
                if (words.length != 2)
                    return null;
                try {
                    message = new SetClanCharacterMessage(Clan.valueOf(words[1].toUpperCase()));
                } catch (IllegalArgumentException e) {
                    return  null;
                }
                break;

        }

        return message;

    }

}
