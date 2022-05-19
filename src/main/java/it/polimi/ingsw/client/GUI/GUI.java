package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.client.View;
import it.polimi.ingsw.client.modeldata.GameData;
import it.polimi.ingsw.client.modeldata.ServerHandler;
import it.polimi.ingsw.messages.AvailableGamesMessage;
import it.polimi.ingsw.messages.updatemessages.UpdateMessage;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.net.Socket;
import java.util.*;

public class GUI extends Application implements View{

    PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    PropertyChangeListener propertyChangeListener;
    Map<String, PageController> controllers = new HashMap<>();
    Map<String, Scene> scenes = new HashMap<>();
    private final String login = "login.fxml";
    private final String start = "start.fxml";
    private final String schoolBoards = "schoolboards.fxml";
    private final String gameBoard = "gameBoard.fxml";
    private final String islands = "islands.fxml";
    private final String deck = "deck.fxml";
    private final String characters = "characterCards.fxml";
    private ServerHandler serverHandler;
    private String currScene;
    private Stage stage;

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
        ((WaitingRoomController) controllers.get(login)).setNickname(nickname);
    }

    @Override
    public void setAvailableGamesMessage(AvailableGamesMessage availableGamesMessage) {
        if(!currScene.equals(login)){
            //FIXME non dovrebbe succedere
        } else {
            ((WaitingRoomController) controllers.get(login)).setAvailableGameMessage(availableGamesMessage); //qui non ho aggiunto login alla mappa
        }
    }

    @Override
    public void playerAddedToGame(String message) {

    }

    public void setStages() throws Exception{
        List<String> fileNames = new ArrayList<>(Arrays.asList(login, start, schoolBoards/*, gameBoard, islands, deck, characters*/));
        for (String file : fileNames){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/" + file)); //FIXME aggiungere bottone per create username?
            scenes.put(file, new Scene(loader.load()));
            controllers.put(file, loader.getController());
            controllers.get(file).setGui(this);
        }

        setCurrScene(start);

    }

    @Override
    public void setGameData(GameData gameData) {

    }

    @Override
    public void updateGameData(UpdateMessage updateMessage) {

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

