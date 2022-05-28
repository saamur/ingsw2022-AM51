package it.polimi.ingsw.client.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import static it.polimi.ingsw.constants.ConstantsGUI.CONNECTION;
import static it.polimi.ingsw.constants.ConstantsGUI.GAMESELECTION;

public class DisconnectionController extends PageController{

    @FXML
    Label disconnectionLabel;

    public void setDisconnectedPlayer(String nickname){
        disconnectionLabel.setText(nickname + " has disconnected");
    }

    //add timer??
    public void goToConnection(){
        gui.setCurrScene(CONNECTION);
    }
}
