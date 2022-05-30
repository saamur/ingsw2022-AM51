package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.client.View;
import it.polimi.ingsw.client.modeldata.GameData;
import it.polimi.ingsw.client.ServerHandler;
import it.polimi.ingsw.messages.AvailableGamesMessage;
import it.polimi.ingsw.messages.updatemessages.*;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.TurnState;
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
        List<String> fileNames = new ArrayList<>(Arrays.asList(CONNECTION, GAMESELECTION, WAITINGROOM, GAMEBOARD, SCHOOLBOARDS, ISLANDS, CLOUDS, SINGLEISLAND, DECK, CHARACTERS, DISCONNECTION));
        for (String file : fileNames){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/" + file)); //FIXME aggiungere bottone per create username?
            scenes.put(file, new Scene(loader.load()));
            controllers.put(file, loader.getController());
            controllers.get(file).setGui(this);
        }

        setCurrScene(CONNECTION);

    }

    @Override
    public void setGameData(GameData gameData) {
        this.gameData = gameData;

        Platform.runLater(() -> {
            synchronized (gameData) {
                ((GameBoardController) controllers.get(GAMEBOARD)).setGameData(gameData);
                ((IslandsPageController) controllers.get(ISLANDS)).setIslands(gameData.getIslandManager());
                ((SchoolBoardController) controllers.get(SCHOOLBOARDS)).setSchoolBoards(gameData.getPlayerData());
                ((CloudController) controllers.get(CLOUDS)).setClouds(gameData.getCloudManager());
                ((DeckController) controllers.get(DECK)).setCards(gameData.getPlayerData(), gameData.isLastRound());
                if(gameData.isExpertModeEnabled()) {
                    System.out.println("getActivatedCharacterCard in setGameData:" + gameData.getActiveCharacterCard());
                    ((CharactersController) controllers.get(CHARACTERS)).setCharacterCards(gameData.getCharacterCardData());
                    ((CharactersController) controllers.get(CHARACTERS)).setActivatedCharacter(gameData.getActiveCharacterCard(), gameData.getCurrPlayer());
                }



            }
        });

        gameChosen = true;
        chooseScene();
    }

    @Override
    public void updateGameData(UpdateMessage updateMessage) {
        if(gameData != null){
            synchronized (gameData) {
                updateMessage.updateGameData(gameData);
            }
            Platform.runLater(() -> {
                synchronized (gameData) {
                    if (updateMessage instanceof UpdateCloudManager) {
                        ((CloudController) controllers.get(CLOUDS)).updateClouds(gameData.getCloudManager());
                    } else if (updateMessage instanceof UpdateGamePhase) {
                        ((CloudController) controllers.get(CLOUDS)).updateTurnState(gameData.getTurnState());
                        ((CharactersController) controllers.get(CHARACTERS)).setActivatedCharacter(gameData.getActiveCharacterCard(), gameData.getCurrPlayer());
                    } else if (updateMessage instanceof UpdateChosenCard) {
                        ((DeckController) controllers.get(DECK)).setCards(gameData.getPlayerData(), gameData.isLastRound());
                    }else if(updateMessage instanceof UpdateCharacterCard){
                        ((CharactersController) controllers.get(CHARACTERS)).updateCharacterCard(((UpdateCharacterCard) updateMessage).characterCard());
                    }else if (updateMessage instanceof UpdateCloud){
                        ((CloudController) controllers.get(CLOUDS)).updateCloud(((UpdateCloud) updateMessage).cloudData());
                    } else if (updateMessage instanceof  UpdatePlayer){
                        ((SchoolBoardController) controllers.get(SCHOOLBOARDS)).setSchoolBoard(((UpdatePlayer) updateMessage).modifiedPlayer());
                    } else if (updateMessage instanceof UpdateIslandManager || updateMessage instanceof  UpdateIsland){ //FIXME there should be no problems
                        ((IslandsPageController) controllers.get(ISLANDS)).updateIslands(((UpdateIslandManager) updateMessage).islandManagerData());
                    }
                }
            });
            chooseScene();
        }
    }

    /**
     * This method determines the currScene to show according to the gameState and the turnState.
     * @see TurnState
     * @see GameState
     */
    private void chooseScene(){
        //FIXME have to double check when this method changes scene and when the controllers do
        Platform.runLater(() -> {
            if (gameData.getGameState().equals(GameState.PLANNING)) {
                setCurrScene(DECK);
            } else if(gameData.getGameState().equals(GameState.ACTION)){
                if(gameData.getTurnState().equals(TurnState.STUDENT_MOVING))
                    setCurrScene(SCHOOLBOARDS);
                else if(gameData.getTurnState().equals(TurnState.MOTHER_MOVING))
                    setCurrScene(ISLANDS);
                else if(gameData.getTurnState().equals(TurnState.CLOUD_CHOOSING) || gameData.getTurnState().equals(TurnState.END_TURN))
                    setCurrScene(CLOUDS);
            } else if (gameData.getGameState().equals(GameState.GAME_OVER)){
                //setCurrScene(GAMEOVER);
            }
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

    }

    @Override
    public void handlePlayerDisconnected(String playerDisconnectedNickname) {
        Platform.runLater(() -> {
            ((DisconnectionController) controllers.get(DISCONNECTION)).setDisconnectedPlayer(playerDisconnectedNickname);
            setCurrScene(DISCONNECTION);
            try{
                setScenes(); //FIXME maybe do when click reconnect
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
     * @param currScene
     */
    public void setCurrScene(String currScene){
        this.currScene = currScene;//mappa delle Scene

        stage.setScene(scenes.get(currScene));
        stage.centerOnScreen();
        stage.show();
    }

    public void disconnect() throws IOException{
        serverHandler.disconnect();
        setScenes(); //In this case resets the stages
    }

}

