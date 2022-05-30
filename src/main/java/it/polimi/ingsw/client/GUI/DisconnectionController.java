package it.polimi.ingsw.client.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import static it.polimi.ingsw.constants.ConstantsGUI.CONNECTION;
import static it.polimi.ingsw.constants.ConstantsGUI.GAMESELECTION;

/**
 * The class DisconnectionController handles the disconnection.fxml file that corresponds to the disconnection page that appears when another player has disconnected.
 */
public class DisconnectionController extends PageController{

    @FXML
    Label disconnectionLabel;

    /**
     * This method is called by the GUI when the method {@link GUI#handlePlayerDisconnected(String) handlePlayerDisconnected} the ServerHandler.
     * This way the client is notified of the disconnection of other players.
     * @param nickname nickname of the player that has disconnected
     */
    public void setDisconnectedPlayer(String nickname){
        disconnectionLabel.setText(nickname + " has disconnected");
    }

    /**
     * When the reconnect Button is clicked in the disconnection.fxml file, this method sets the currScene of the GUI to connection.fxml so that the client can reconnect again.
     * @see GUI
     */
    public void goToConnection(){
        gui.setCurrScene(CONNECTION); //FIXME non dà il tempo di schiacciare, fa da solo
    }
}
