package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.client.GUI.controllers.*;
import it.polimi.ingsw.client.View;
import it.polimi.ingsw.client.modeldata.CharacterCardData;
import it.polimi.ingsw.client.modeldata.GameData;
import it.polimi.ingsw.client.ServerHandler;
import it.polimi.ingsw.client.modeldata.PlayerData;
import it.polimi.ingsw.messages.AvailableGamesMessage;
import it.polimi.ingsw.messages.gamemessages.ApplyCharacterCardEffectMessage1;
import it.polimi.ingsw.messages.gamemessages.ApplyCharacterCardEffectMessage2;
import it.polimi.ingsw.messages.gamemessages.SetClanCharacterMessage;
import it.polimi.ingsw.messages.updatemessages.*;
import it.polimi.ingsw.model.Clan;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.TurnState;
import it.polimi.ingsw.model.charactercards.CharacterID;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.*;

import static it.polimi.ingsw.constants.ConstantsGUI.*;

//TODO JavaDocs
public class GUI extends Application implements View{

    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    private final Map<String, PageController> controllers = new HashMap<>();
    private final Map<String, Scene> scenes = new HashMap<>();
    private ServerHandler serverHandler;
    private String currScene;
    private Stage stage;
    private String nickname;

    public Map<String, PageController> getControllers() {
        return controllers;
    }

    private volatile GameData gameData;

    private boolean gameChosen;

    public static void main(String[] args) {
        launch();
    }

    public void launchGUI() {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        setScenes();
        stage.setTitle("Eriantys");
        setCurrScene(CONNECTION);

    }

    public PropertyChangeSupport getListeners() {
        return pcs;
    }

    @Override
    public void setNickname(String nickname) {
        this.nickname = nickname;
        ((GameSelectionController) controllers.get(GAMESELECTION)).setNickname(nickname);
        ((SchoolBoardController) controllers.get(SCHOOLBOARDS)).setNickname(nickname);
        ((CharactersController) controllers.get(CHARACTERS)).setNickname(nickname);
        ((DeckController) controllers.get(DECK)).setNickname(nickname);
    }

    @Override
    public void setAvailableGamesMessage(AvailableGamesMessage availableGamesMessage) {
        if(!currScene.equals(GAMESELECTION)){
            //FIXME non dovrebbe succedere
        } else {
            ((GameSelectionController) controllers.get(GAMESELECTION)).setAvailableGameMessage(availableGamesMessage); //qui non ho aggiunto login alla mappa
        }
    }

    @Override
    public void playerAddedToGame(String message) {
        if(!gameChosen) {
            Platform.runLater(() -> setCurrScene(WAITINGROOM));
        }
    }

    public void setScenes() throws IOException {
        List<String> fileNames = new ArrayList<>(Arrays.asList(CONNECTION, GAMESELECTION, WAITINGROOM, GAMEBOARD, SCHOOLBOARDS, ISLANDS, CLOUDS, SINGLEISLAND, DECK, CHARACTERS, DISCONNECTION, ACTIVATEEFFECT , GAMEOVER));
        for (String file : fileNames){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/" + file));
            scenes.put(file, new Scene(loader.load()));
            controllers.put(file, loader.getController());
            controllers.get(file).setGui(this);
        }
    }

    @Override
    public void setGameData(GameData gameData) {
        this.gameData = gameData;

        Platform.runLater(() -> {
            synchronized (gameData) {
                ((GameBoardController) controllers.get(GAMEBOARD)).setGameData(gameData);
                ((IslandsPageController) controllers.get(ISLANDS)).updateIslands(gameData.getIslandManager());
                ((SchoolBoardController) controllers.get(SCHOOLBOARDS)).setSchoolBoards(gameData.getPlayerData(), gameData.isExpertModeEnabled());
                ((CloudController) controllers.get(CLOUDS)).setClouds(gameData.getCloudManager(), gameData.isExpertModeEnabled());
                ((DeckController) controllers.get(DECK)).setCards(gameData.getPlayerData(), gameData.isLastRound());
                if(gameData.isExpertModeEnabled()) {
                    System.out.println("getActivatedCharacterCard in setGameData:" + gameData.getActiveCharacterCard());
                    ((CharactersController) controllers.get(CHARACTERS)).setCharacterCards(gameData.getCharacterCardData());
                    ((CharactersController) controllers.get(CHARACTERS)).setActivatedCharacter(gameData.getActiveCharacterCard(), gameData.getCurrPlayer(), gameData.isActiveCharacterPunctualEffectApplied());
                    ((GameBoardController) controllers.get(GAMEBOARD)).setActivatedCharacter(gameData.getActiveCharacterCard());
                    if (gameData.getActiveCharacterCard() != null) {
                        Platform.runLater(() -> {
                            switch (gameData.getActiveCharacterCard()) {
                                case HERALD, GRANDMA -> {
                                    ((IslandsPageController) controllers.get(ISLANDS)).setActivatedCharacter(gameData.getActiveCharacterCard());
                                    setCurrScene(ISLANDS);
                                }
                                case JESTER -> {
                                    ((SchoolBoardController) controllers.get(SCHOOLBOARDS)).disableStudentMoving();
                                    Map<Clan, Integer> students = null;
                                    for (CharacterCardData character : gameData.getCharacterCardData())
                                        if (character.characterID() == CharacterID.JESTER)
                                            students = character.students();
                                    System.out.println("Students from GUI: " + students);

                                    ((SchoolBoardController) controllers.get(SCHOOLBOARDS)).setCharacter(CharacterID.JESTER, gameData.getCurrPlayer(), students);
                                    setCurrScene(SCHOOLBOARDS);
                                }
                                case MINSTREL -> {
                                    ((SchoolBoardController) controllers.get(SCHOOLBOARDS)).disableStudentMoving();
                                    ((SchoolBoardController) controllers.get(SCHOOLBOARDS)).setCharacter(CharacterID.MINSTREL, gameData.getCurrPlayer(), null);
                                    setCurrScene(SCHOOLBOARDS);
                                }
                                case MUSHROOMPICKER, THIEF ->
                                    setCurrScene(ACTIVATEEFFECT);
                            }
                        });
                    }
                }
            }
        });

        gameChosen = true;
        chooseScene();
    }

    @Override
    public void updateGameData(UpdateMessage updateMessage) {
        if(gameData != null){
            GameState oldGameState =  gameData.getGameState();
            TurnState oldTurnState = gameData.getTurnState();
            synchronized (gameData) {
                updateMessage.updateGameData(gameData);
            }
            Platform.runLater(() -> {
                synchronized (gameData) {
                    if (updateMessage instanceof UpdateChosenCard) {
                        ((DeckController) controllers.get(DECK)).setCards(gameData.getPlayerData(), gameData.isLastRound());
                    }else if (updateMessage instanceof UpdateCloudManager) {
                        if(!gameData.isLastRound())
                            ((CloudController) controllers.get(CLOUDS)).updateClouds(gameData.getCloudManager());
                        else
                            ((CloudController) controllers.get(CLOUDS)).lastRound();
                    } else if(updateMessage instanceof UpdateCharacterCard){
                        ((CharactersController) controllers.get(CHARACTERS)).updateCharacterCard(((UpdateCharacterCard) updateMessage).characterCard());
                        }else if (updateMessage instanceof UpdateCloud){
                        ((CloudController) controllers.get(CLOUDS)).updateCloud(((UpdateCloud) updateMessage).cloudData());
                    }else if (updateMessage instanceof UpdateMotherNaturePosition){
                        if(gameData.getCurrPlayer().equals(this.nickname)) {
                            ((IslandsPageController) controllers.get(ISLANDS)).movedMotherNature(true);
                        }
                        ((IslandsPageController) controllers.get(ISLANDS)).updateMotherNaturePosition(((UpdateMotherNaturePosition) updateMessage).islandIndex());
                    } else if (updateMessage instanceof  UpdatePlayer){
                        ((SchoolBoardController) controllers.get(SCHOOLBOARDS)).setSchoolBoard(((UpdatePlayer) updateMessage).modifiedPlayer());
                    } else if (updateMessage instanceof UpdateIslandManager || updateMessage instanceof  UpdateIsland){ //FIXME there should be no problems
                        ((IslandsPageController) controllers.get(ISLANDS)).updateIslands(gameData.getIslandManager());
                    } else if (updateMessage instanceof UpdateGamePhase) {
                        ((CloudController) controllers.get(CLOUDS)).updateTurnState(gameData.getTurnState());
                        ((CharactersController) controllers.get(CHARACTERS)).setActivatedCharacter(gameData.getActiveCharacterCard(), gameData.getCurrPlayer(), gameData.isActiveCharacterPunctualEffectApplied());
                        if(gameData.getCurrPlayer().equals(this.nickname)) {
                            if (gameData.getActiveCharacterCard() != null && !gameData.isActiveCharacterPunctualEffectApplied()) {
                                ((GameBoardController) controllers.get(GAMEBOARD)).setActivatedCharacter(gameData.getActiveCharacterCard());
                                Platform.runLater(() -> {
                                    switch (gameData.getActiveCharacterCard()) {
                                        case HERALD, GRANDMA -> {
                                            ((IslandsPageController) controllers.get(ISLANDS)).setActivatedCharacter(gameData.getActiveCharacterCard());
                                            setCurrScene(ISLANDS);
                                        }
                                        case JESTER -> {
                                            ((SchoolBoardController) controllers.get(SCHOOLBOARDS)).disableStudentMoving();
                                            Map<Clan, Integer> students = null;
                                            for (CharacterCardData character : gameData.getCharacterCardData())
                                                if (character.characterID() == CharacterID.JESTER)
                                                    students = character.students();

                                            ((SchoolBoardController) controllers.get(SCHOOLBOARDS)).setCharacter(CharacterID.JESTER, gameData.getCurrPlayer(), students);
                                            setCurrScene(SCHOOLBOARDS);
                                        }
                                        case MINSTREL -> {
                                            ((SchoolBoardController) controllers.get(SCHOOLBOARDS)).disableStudentMoving();
                                            ((SchoolBoardController) controllers.get(SCHOOLBOARDS)).setCharacter(CharacterID.MINSTREL, gameData.getCurrPlayer(), null);
                                            setCurrScene(SCHOOLBOARDS);
                                        }
                                        case MUSHROOMPICKER, THIEF -> setCurrScene(ACTIVATEEFFECT);
                                    }
                                });
                            } else if (gameData.getActiveCharacterCard() != null && gameData.isActiveCharacterPunctualEffectApplied()) {
                                ((GameBoardController) controllers.get(GAMEBOARD)).setActivatedCharacter(null);
                                switch (gameData.getActiveCharacterCard()) {
                                    case MINSTREL, JESTER -> {
                                        ((SchoolBoardController) controllers.get(SCHOOLBOARDS)).disableCharactersActions();
                                        chooseScene();
                                        return;
                                    }

                                }
                            }
                        }
                        if (gameData.getGameState().equals(GameState.PLANNING))
                            ((DeckController) controllers.get(DECK)).setCurrPlayer(gameData.getCurrPlayer());
                    }
                }
            });
            if(oldGameState != gameData.getGameState() || oldTurnState != gameData.getTurnState()) //TODO verify this is correct
                chooseScene();
        }
    }

    /**
     * This method determines the currScene to show according to the gameState and the turnState.
     * @see TurnState
     * @see GameState
     */
    //FIXME mettere 2sec di ritardo per vedere le modifiche?? Ad esempio madre natura che conquista isole
    private void chooseScene(){
        //FIXME have to double check when this method changes scene and when the controllers do
        Platform.runLater(() -> {
            if (gameData.getGameState().equals(GameState.PLANNING)) {
                ((DeckController) controllers.get(DECK)).setCurrPlayer(gameData.getCurrPlayer());
                setCurrScene(DECK);
                if(gameData.getCurrPlayer().equals(this.nickname))
                    ((GameBoardController) controllers.get(GAMEBOARD)).setInfoLabel("You have to choose a Card");

            } else if(gameData.getGameState().equals(GameState.ACTION)){
                if(gameData.getTurnState().equals(TurnState.STUDENT_MOVING)) {
                    ((SchoolBoardController) controllers.get(SCHOOLBOARDS)).setCurrPlayer(gameData.getCurrPlayer());
                    setCurrScene(SCHOOLBOARDS);
                    if(gameData.getCurrPlayer().equals(this.nickname))
                        ((GameBoardController) controllers.get(GAMEBOARD)).setInfoLabel("It's time for you to move your students");
                    else
                        ((GameBoardController) controllers.get(GAMEBOARD)).setInfoLabel("It's " + gameData.getCurrPlayer() + "'s turn");
                }
                else if(gameData.getTurnState().equals(TurnState.MOTHER_MOVING)) {
                    ((SchoolBoardController) controllers.get(SCHOOLBOARDS)).disableStudentMoving();
                        for(PlayerData player : gameData.getPlayerData()) {
                            if(gameData.getCurrPlayer().equals(this.nickname) && player.getNickname().equals(this.nickname)) {
                                ((IslandsPageController) controllers.get(ISLANDS)).setMotherMovingLabels(player.getCurrCard());
                                ((GameBoardController) controllers.get(GAMEBOARD)).setInfoLabel("You have to move Mother Nature");
                            } else {
                                ((GameBoardController) controllers.get(GAMEBOARD)).setInfoLabel("It's " + gameData.getCurrPlayer() + "'s turn");
                            }
                        }
                        setCurrScene(ISLANDS);
                    }
                else if(gameData.getTurnState().equals(TurnState.CLOUD_CHOOSING) || gameData.getTurnState().equals(TurnState.END_TURN)) {
                    if(gameData.getCurrPlayer().equals(this.nickname))
                        ((GameBoardController) controllers.get(CLOUDS)).setInfoLabel("You have to choose a cloud");
                    else
                        ((GameBoardController) controllers.get(GAMEBOARD)).setInfoLabel("It's " + gameData.getCurrPlayer() + "'s turn");
                    setCurrScene(CLOUDS);
                }
            }/* else if (gameData.getGameState().equals(GameState.GAME_OVER)){
                ((GameOverController) controllers.get(GAMEOVER)).setWinners(gameData);
                setCurrScene(GAMEOVER);
            }*/
        });
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener propertyChangeListener) {
        pcs.addPropertyChangeListener(propertyChangeListener);

    }

    @Override
    public void handleGenericMessage(String message) {

    }

    @Override
    public void handleErrorMessage(String message) {
        controllers.get(currScene).handleErrorMessage(message); //così ho il current controller
    }

    @Override
    public void handleGameOver(List<String> winnersNickname) {
        ((GameOverController) controllers.get(GAMEOVER)).setWinners(winnersNickname );
        Platform.runLater(() -> setCurrScene(GAMEOVER));
    }

    @Override
    public void handlePlayerDisconnected(String playerDisconnectedNickname) {
        Platform.runLater(() -> {
            ((DisconnectionController) controllers.get(DISCONNECTION)).setDisconnectedPlayer(playerDisconnectedNickname);
            setCurrScene(DISCONNECTION);
            //FIXME reconnection button disabled
            gameData = null;
            try{
                setScenes();
                //FIXME reconnect button reabled
            } catch (IOException e){
                //FIXME problem with loading images
                e.printStackTrace();
            }
            gameChosen = false;
        });
    }

    public void setServerHandler(ServerHandler serverHandler){
        this.serverHandler = serverHandler;
    }

    /**
     * This method changes the currScene to a new one depending on how the game is evolving
     * @param currScene the new scene to show
     */
    public void setCurrScene(String currScene){
        this.currScene = currScene;//mappa delle Scene

        stage.setScene(scenes.get(currScene));
        stage.centerOnScreen();
        stage.show();
    }

    public void disconnect() throws IOException{
        serverHandler.disconnect();
        gameData = null;
        setScenes(); //In this case resets the stages
    }

    public GameState getGamePhase(){
        return this.gameData.getGameState();
    }

    public TurnState getTurnState(){
        if(this.gameData.getTurnState() == null)
            return null;
        else
            return this.gameData.getTurnState();
    }

}

