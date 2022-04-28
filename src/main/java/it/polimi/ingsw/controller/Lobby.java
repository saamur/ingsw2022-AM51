package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.NicknameNotAvailableException;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Lobby {

    private static final String SAVED_GAMES_DIRECTORY = "SavedGames";
    private static final String SAVED_GAMES_INDEX = "index.txt";

    private static Lobby instance;

    private final Map<ClientHandler, String> clientNicknames;

    private Lobby () {

        clientNicknames = new HashMap<>();

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
