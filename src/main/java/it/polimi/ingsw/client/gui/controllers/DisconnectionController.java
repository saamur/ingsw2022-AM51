package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.gui.GUI;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

import static it.polimi.ingsw.constants.GuiConstants.CONNECTION;

/**
 * The class DisconnectionController handles the disconnection.fxml file that corresponds to the disconnection page that appears when another player has disconnected.
 */
public class DisconnectionController extends PageController implements Initializable {

    @FXML
    private Label disconnectionLabel;

    @FXML
    private Button button;

    @FXML private Label waitingLabel;


    /**
     * This method is called by the GUI when the method {@link GUI#handlePlayerDisconnected(String) handlePlayerDisconnected} the ServerHandler.
     * This way the client is notified of the disconnection of other players.
     * @param nickname nickname of the player that has disconnected
     */
    public void setDisconnectedPlayer(String nickname){
        disconnectionLabel.setText(nickname + " has disconnected");
        waitingLabel.setVisible(true);
        button.setOpacity(0.5);
        button.setDisable(true);
    }

    /**
     * When the reconnect Button is clicked in the disconnection.fxml file, this method sets the currScene of the GUI to connection.fxml so that the client can reconnect again.
     * @see GUI
     */
    public void goToConnection(){
        gui.setCurrScene(CONNECTION);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        waitingLabel.setVisible(false);
        button.setOpacity(1);
        button.setDisable(false);

    }
}
