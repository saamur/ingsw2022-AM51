package it.polimi.ingsw.client.GUI;

import javafx.event.ActionEvent;

import static it.polimi.ingsw.constants.ConstantsGUI.GAMEBOARD;

public class CloudController extends PageController{

    public void back(ActionEvent event){
        gui.setCurrScene(GAMEBOARD);
    }
}
