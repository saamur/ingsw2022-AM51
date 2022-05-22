package it.polimi.ingsw.client.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.List;

import static it.polimi.ingsw.constants.ConstantsGUI.*;

public class WaitingRoomController extends PageController{

    public void back(ActionEvent event){
        gui.setCurrScene(GAMESELECTION);
    }

}
