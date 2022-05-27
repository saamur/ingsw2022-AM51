package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.client.View;
import it.polimi.ingsw.client.modeldata.GameData;
import it.polimi.ingsw.client.ServerHandler;
import it.polimi.ingsw.messages.AvailableGamesMessage;
import it.polimi.ingsw.messages.updatemessages.UpdateMessage;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;

import static it.polimi.ingsw.constants.ConstantsGUI.*;

public class GUI extends Application implements View{

    PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    PropertyChangeListener propertyChangeListener;
    Map<String, PageController> controllers = new HashMap<>();
    Map<String, Scene> scenes = new HashMap<>();
    private ServerHandler serverHandler;
    private String currScene;
    private Stage stage;

    private GameData gameData;

    private boolean gameChosen;

    public void launchGUI() {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        setStages();
        stage.setTitle("Eriantys");

    }

    public PropertyChangeSupport getListeners() {
        return pcs;
    }

    @Override
    public void setNickname(String nickname) {
        ((GameSelectionController) controllers.get(GAMESELECTION)).setNickname(nickname);
        ((SchoolBoardController) controllers.get(SCHOOLBOARDS)).setNickname(nickname);
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

    public void setStages() throws Exception{
        List<String> fileNames = new ArrayList<>(Arrays.asList(CONNECTION, GAMESELECTION, WAITINGROOM, GAMEBOARD, SCHOOLBOARDS, ISLANDS /*, CLOUDS, DECK, CHARACTERS*/));
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

        ((GameBoardController) controllers.get(GAMEBOARD)).setGameData(gameData);
        ((IslandsPageController) controllers.get(ISLANDS)).setIslands(gameData.getIslandManager());
        ((SchoolBoardController) controllers.get(SCHOOLBOARDS)).setSchoolBoards(gameData.getPlayerData());

        gameChosen = true;
        Platform.runLater(() -> setCurrScene(GAMEBOARD));
    }

    @Override
    public void updateGameData(UpdateMessage updateMessage) {
        //potrei fare updateMessage.updateGameData()...
        //ma poi dovrei fare l'update
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener propertyChangeListener) {
        pcs.addPropertyChangeListener(propertyChangeListener);

    }

    @Override
    public void displayEverything() {

    }

    @Override
    public void handleGenericMessage(String message) {
        //TODO display to GUI
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
            setCurrScene(DISCONNECTION); //TODO create
        });
    }

    public void setServerHandler(ServerHandler serverHandler){
        this.serverHandler = serverHandler;
    }

    public void setCurrScene(String currScene){
        this.currScene = currScene;//mappa delle Scene
        stage.setScene(scenes.get(currScene));
        stage.show();
    }

}

