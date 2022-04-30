package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.NicknameNotAvailableException;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.GameInterface;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Lobby {

    private static Lobby instance;

    private final Map<ClientHandler, String> clientNicknames;
    private final List<Controller> runningGameControllers;
    private final List<NewGameController> openingNewGameControllers;
    private final List<RestoredGameController> openingRestoredGameControllers;

    private Lobby () {

        clientNicknames = new HashMap<>();
        runningGameControllers = new ArrayList<>();
        openingNewGameControllers = new ArrayList<>();
        openingRestoredGameControllers =new ArrayList<>();

    }

    public static Lobby getInstance() {
        if (instance == null)
            instance = new Lobby();
        return instance;
    }

    public void addClient(Socket socket) throws IOException {

        System.out.println("adding socket...");

        ClientHandler clientHandler = new ClientHandler(socket);

        Thread t = new Thread(clientHandler);
        t.start();

    }

    public synchronized Controller createNewController (String nickname, int numOfPlayers, boolean expertMode) {
        GameInterface game = new Game(numOfPlayers, nickname, expertMode);
        NewGameController controller = new NewGameController(game);
        openingNewGameControllers.add(controller);
        return controller;
    }

    public synchronized Controller addClientToController (String nickname, int controllerID) {

        for (NewGameController c : openingNewGameControllers) {
            if (c.getId() == controllerID) {
                c.addPlayer(nickname);
                if (c.isStarted()) {
                    runningGameControllers.add(c);
                    openingNewGameControllers.remove(c);
                }
                return c;
            }
        }

        for (RestoredGameController c : openingRestoredGameControllers) {
            if (c.getId() == controllerID) {
                c.addPlayer(nickname);
                if (c.isStarted()) {
                    runningGameControllers.add(c);
                    openingNewGameControllers.remove(c);
                }
                return c;
            }
        }

        return null;

    }

    //todo add createNewRestoredGameController

    public synchronized void registerNickname(ClientHandler clientHandler, String nickname) throws NicknameNotAvailableException {

        if (clientNicknames.containsValue(nickname))
            throw new NicknameNotAvailableException("This nickname is not available");

        clientNicknames.put(clientHandler, nickname);

    }

    public synchronized void unregisterNickname(ClientHandler clientHandler) {
        clientNicknames.remove(clientHandler);
    }

    public synchronized String getNicknameFromClientHandler (ClientHandler clientHandler) {
        return clientNicknames.get(clientHandler);
    }

}
