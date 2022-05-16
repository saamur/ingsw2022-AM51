package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.client.View;
import it.polimi.ingsw.client.modeldata.GameData;
import it.polimi.ingsw.messages.AvailableGamesMessage;
import it.polimi.ingsw.messages.updatemessages.UpdateMessage;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

public class GUI extends Application implements View{

    PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {

        stage.setTitle("homepage");
        String path = "/login.fxml";
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path)); //FIXME aggiungere bottone per create username
        //loader.setController(controller);
        AnchorPane root = loader.load();
        Scene scene = new Scene(root);
        LoginController controller = loader.getController();
        controller.setGui(this);

        stage.setScene(scene);
        stage.show();
    }

    public PropertyChangeSupport getListeners() {
        return pcs;
    }

    @Override
    public void setNickname(String nickname) {

    }

    @Override
    public void setAvailableGamesMessage(AvailableGamesMessage availableGamesMessage) {

    }

    @Override
    public void setGameData(GameData gameData) {

    }

    @Override
    public void updateGameData(UpdateMessage updateMessage) {

    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener propertyChangeListener) {

    }

    @Override
    public void displayModel() {

    }

    @Override
    public void handleGenericMessage(String message) {

    }

    @Override
    public void handleErrorMessage(String message) {

    }

    @Override
    public void handleGameOver(List<String> winnersNickname) {

    }

    @Override
    public void handlePlayerDisconnected(String playerDisconnectedNickname) {

    }


}

