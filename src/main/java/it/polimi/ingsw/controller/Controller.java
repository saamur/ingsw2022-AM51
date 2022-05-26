package it.polimi.ingsw.controller;

import it.polimi.ingsw.client.modeldata.*;
import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.messages.gamemessages.GameMessage;
import it.polimi.ingsw.messages.updatemessages.*;
import it.polimi.ingsw.model.GameInterface;
import it.polimi.ingsw.model.GameState;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.List;

public abstract class Controller implements PropertyChangeListener {

    private static int counter = 0;

    private final int id;

    protected final GameInterface game;
    protected boolean started;
    private boolean closing;

    //The class Controller is listened by the ClientHandlerClass
    protected final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public Controller(GameInterface game) {
        synchronized (Controller.class) {
            id = counter;
            counter++;
        }
        this.game = game;
        game.setListeners(this); //FIXME penso abbia senso metterlo nel costruttore
        started = false;
        closing = false;
    }

    public int getId() {
        return id;
    }

    public boolean isStarted() {
        return started;
    }

    public List<String> getPlayersNicknames () {
        return game.getPlayersNicknames();
    }

    public abstract void addPlayer (String nickname);

    public synchronized Message messageOnGame (String nickname, GameMessage message) {

        System.out.println("message on game");
        System.out.println(game.getGameState());
        System.out.println(game.getPlayersNicknames());
        System.out.println(started);

        if (!started)
            return new ErrorMessage("You cannot make this move now");

        Message answer = null;
        try {
            answer = message.performMove(nickname, game);
            pcs.firePropertyChange("updateGamePhase", null, new UpdateGamePhase(GamePhaseData.createGamePhaseData(game)));
        } catch (WrongGamePhaseException | ExpertModeNotEnabledException | WrongPlayerException | NotValidMoveException | NotValidIndexException | WrongTurnPhaseException e) {
            answer = new ErrorMessage(e.getMessage());
        } catch (NonExistingPlayerException e) {
            e.printStackTrace();
        }

        if (answer == null)
            answer = new ErrorMessage("An error has occurred");

        if (game.getGameState() != GameState.GAME_OVER)
            SavedGameManager.saveRunningGame(game, getId());
        else {
            closing = true;
            SavedGameManager.removeSavedRunningGame(getId());
            Lobby.getInstance().removeController(this);
        }

        return answer;

    }

    public synchronized void clientDisconnected (String nickname) {

        if (!closing) {

            closing = true;
            Message message = new PlayerDisconnectedMessage(nickname);

            pcs.firePropertyChange("disconnectedPlayer", null, message);

            SavedGameManager.removeSavedRunningGame(getId());
            if (game.getGameState() != GameState.INITIALIZATION && game.getGameState() != GameState.GAME_OVER) {
                game.removeListeners();
                try {
                    SavedGameManager.saveGame(game);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Lobby.getInstance().removeController(this);

        }

    }

    /**
     * The method propertyChange is called after the method fire is executed from a class to which the controller is listening to.
     * If the property name is "playerModified" the controller will send the updated Player (data?) to all of the players in the game.
     * @param evt is the PropertyChangeEvent that describes the object that has been modified
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Message update = null;
        switch(evt.getPropertyName()){ //FIXME aggiungere codice ai case, per ora solo temporaneo
            case "MotherNature" -> {
                int islandIndex = (Integer) evt.getNewValue();
                update = new UpdateMotherNaturePosition(islandIndex);
            }
            case "merge" -> {
                IslandManagerData islandManager = (IslandManagerData) evt.getNewValue();
                update = new UpdateIslandManager(islandManager);
            }
            case "chosenCloud" -> {
                CloudData cloud = (CloudData) evt.getNewValue();
                update = new UpdateCloud(cloud);
            }
            case "modifiedPlayer" -> {
                PlayerData modifiedPlayer = (PlayerData) evt.getNewValue();
                update = new UpdatePlayer(modifiedPlayer);
            }
            case "filledClouds" -> {
                CloudManagerData cloudManager = (CloudManagerData) evt.getNewValue();
                update = new UpdateCloudManager(cloudManager);
            }
            case "modifiedIsland" -> {
                IslandData modifiedIsland = (IslandData) evt.getNewValue();
                update = new UpdateIsland(modifiedIsland); //TODO Control all modifiedIsland fire are created with islandIndex and not island object
            }
            case "modifiedCharacter" -> {
                CharacterCardData characterCard = (CharacterCardData) evt.getNewValue();
                update = new UpdateCharacterCard(characterCard);
            }
            case "chosenCard" -> {
                PlayerData player = (PlayerData) evt.getNewValue();
                update = new UpdateChosenCard(player.getNickname(), player.getCurrCard());
             }
             case "gameOver" -> {
                List<String> nicknameWinners = (List<String>) evt.getNewValue();
                update = new GameOverMessage(nicknameWinners);
             }
        }
        pcs.firePropertyChange("message", null, update);
    }

    public void setPropertyChangeListener (PropertyChangeListener listener){
        pcs.addPropertyChangeListener(listener);
    }

    @Override
    public String toString() {
        return "Controller{" +
                "id=" + id +
                '}';
    }

}
