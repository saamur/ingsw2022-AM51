package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.NicknameNotAvailableException;
import it.polimi.ingsw.exceptions.NumberOfPlayerNotSupportedException;
import it.polimi.ingsw.messages.AvailableGamesMessage;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.GameInterface;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The Lobby class is a singleton that manages the creation of new ClientHandlers, new Controllers and new Games.
 * It also manages the nickname uniqueness of the players
 *
 */
public class Lobby {

    private static Lobby instance;

    private final Map<ClientHandler, String> clientNicknames;
    private final List<Controller> runningGameControllers;
    private final List<NewGameController> openingNewGameControllers;
    private final List<RestoredGameController> openingRestoredGameControllers;

    /**
     * Constructs an empty Lobby
     */
    private Lobby () {
        clientNicknames = new HashMap<>();
        runningGameControllers = new ArrayList<>();
        openingNewGameControllers = new ArrayList<>();
        openingRestoredGameControllers = new ArrayList<>();
    }

    /**
     * The getInstance method creates if needed and returns the only instance of this class
     * @return  the only instance of this class
     */
    public static Lobby getInstance() {
        if (instance == null)
            instance = new Lobby();
        return instance;
    }

    /**
     * The addClient method tries to create a new ClientHandler object with the given socket,
     * to create the corresponding thread and to make it start
     * @param socket    the Socket connected to the client for which to create the ClientHandler
     */
    public void addClient (Socket socket) {

        System.out.println("adding client...");

        try {
            ClientHandler clientHandler = new ClientHandler(socket);

            Thread t = new Thread(clientHandler);
            t.start();
        } catch (IOException e) {
            System.out.println("An error occurred while creating a ClientHandler");
            e.printStackTrace();
        }

    }

    /**
     * The createAvailableGamesMessage creates an AvailableGamesMessage object with the data of the available games for the player with the given nickname
     * @param nickname  the nickname of the player
     * @return  an AvailableGamesMessage object with the data of the available games for the player with the given nickname
     */
    public synchronized AvailableGamesMessage createAvailableGamesMessage (String nickname) {

        List<OpeningNewGameData> openingNewGameDataList = new ArrayList<>(openingNewGameControllers.stream()
                .map(NewGameController::createOpeningNewGameData)
                .toList());

        List<OpeningRestoredGameData> openingRestoredGameDataList = new ArrayList<>(openingRestoredGameControllers.stream()
                .map(RestoredGameController::createOpeningRestoredGameData)
                .filter(d -> d.missingNicknames().contains(nickname))
                .toList());

        List<SavedGameData> savedGameDataList = new ArrayList<>(SavedGameManager.getSavedGameList().stream()
                .filter(d -> d.nicknames().contains(nickname))
                //.filter(d -> d.nicknames().stream().noneMatch(clientNicknames::containsValue))       //fixme optional, remove it?
                .toList());

        return new AvailableGamesMessage(openingNewGameDataList, openingRestoredGameDataList, savedGameDataList);

    }

    /**
     * The broadcastAvailableGames method sends to all the clients that have not already chosen a game a message
     * with all the available games
     */
    private synchronized void broadcastAvailableGames () {

        List<String> clientsInController = new ArrayList<>();

        for(Controller c : runningGameControllers)
            clientsInController.addAll(c.getPlayersNicknames());

        for(Controller c : openingNewGameControllers)
            clientsInController.addAll(c.getPlayersNicknames());

        for(Controller c : openingRestoredGameControllers)
            clientsInController.addAll(c.getPlayersNicknames());

        List<ClientHandler> clientHandlers = clientNicknames.keySet().stream().filter(c -> !clientsInController.contains(clientNicknames.get(c))).toList();

        for (ClientHandler c : clientHandlers) {
            try {
                c.sendObject(createAvailableGamesMessage(clientNicknames.get(c)));
            } catch (IOException e) {

            }
        }

    }

    /**
     * the method createNewGameController tries to create a new game with the given parameters and the controller bound to it
     * @param nickname      the nickname of the player that wants to create the game
     * @param numOfPlayers  the number of players of the game to be created
     * @param expertMode    true if the game to be created will be an expert game, false otherwise
     * @return              the controller created, null if the parameters are not supported
     */
    public synchronized NewGameController createNewGameController (String nickname, int numOfPlayers, boolean expertMode) {
        NewGameController controller;
        try {
            GameInterface game = new Game(numOfPlayers, nickname, expertMode);
            controller = new NewGameController(game);
            openingNewGameControllers.add(controller);
            broadcastAvailableGames();
        } catch (NumberOfPlayerNotSupportedException e) {
            controller = null;
        }

        return controller;
    }

    /**
     * The method getOpeningController finds the Controller with the given ID and returns it
     * @param controllerID  the ID of the controller to find
     * @return              the found controller, null if there is no Controller with the given ID
     */
    public synchronized Controller getOpeningController (int controllerID) {

        System.out.println("get opening controller " + controllerID);

        for (NewGameController c : openingNewGameControllers)
            if (c.getId() == controllerID)
                return c;

        for (RestoredGameController c : openingRestoredGameControllers)
            if (c.getId() == controllerID)
                return c;

        return null;

    }

    /**
     * The method createNewRestoredGameController tries to restore the game represented by the given SavedGameData
     * and to create the controller bound to it
     * @param nickname      the nickname of the player that wants to restore the game
     * @param savedGameData the SavedGamaData that represents the game to be restored
     * @return              the newly created RestoredGameController bound to the restored game
     * @throws FileNotFoundException    if there is no saved game represented by the given SavedGameData
     */
    public synchronized RestoredGameController createNewRestoredGameController (String nickname, SavedGameData savedGameData) throws FileNotFoundException {
        GameInterface game = SavedGameManager.restoreGame(savedGameData.fileName());
        RestoredGameController controller = new RestoredGameController(game, savedGameData.localDateTime());
        openingRestoredGameControllers.add(controller);
        controller.addPlayer(nickname);
        broadcastAvailableGames();
        return controller;
    }

    /**
     * The method registerNickname associates the given nickname to the given ClientHandler if it is not already
     * associated with another ClientHandler
     * @param clientHandler the ClientHandler to which associate the given nickname
     * @param nickname      the nickname to associate to the given ClientHandler
     * @throws NicknameNotAvailableException    if the given nickname is already associated with another ClientHandler
     */
    public synchronized void registerNickname (ClientHandler clientHandler, String nickname) throws NicknameNotAvailableException {

        if (clientNicknames.containsValue(nickname))
            throw new NicknameNotAvailableException("This nickname is not available");

        clientNicknames.put(clientHandler, nickname);

    }

    /**
     * The method unregisterNickname removes the association of the given ClientHandler with its nickname
     * @param clientHandler the ClientHandler of which to remove the association with its nickname
     */
    public synchronized void unregisterNickname (ClientHandler clientHandler) {
        clientNicknames.remove(clientHandler);
    }

    /**
     * The method getNicknameFromClientHandler returns the nickname associated with the given ClientHandler
     * @param clientHandler the ClientHandler of which to return the associated nickname
     * @return              the nickname associated to the given ClientHandler
     */
    public synchronized String getNicknameFromClientHandler (ClientHandler clientHandler) {
        return clientNicknames.get(clientHandler);
    }

    /**
     * The method startControllerGame classifies the given Controller as one with a started game
     * @param controller    the Controller to be classified
     */
    public synchronized void startControllerGame (Controller controller) {
        runningGameControllers.add(controller);
        openingNewGameControllers.remove(controller);
        openingRestoredGameControllers.remove(controller);
        broadcastAvailableGames();
    }

    /**
     * The method removeController removes all references of the given Controller in this object
     * @param controller    the Controller to be removed
     */
    public synchronized void removeController (Controller controller) {
        runningGameControllers.remove(controller);
        openingNewGameControllers.remove(controller);
        openingRestoredGameControllers.remove(controller);
        if(!controller.isStarted())
            broadcastAvailableGames();
    }

}
