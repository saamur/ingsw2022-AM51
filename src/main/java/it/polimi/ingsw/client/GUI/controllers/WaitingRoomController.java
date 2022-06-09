package it.polimi.ingsw.client.GUI.controllers;

import javafx.event.ActionEvent;


import java.io.IOException;

import static it.polimi.ingsw.constants.ConstantsGUI.*;

public class WaitingRoomController extends PageController{

    /**
     * The back methods is called when the Button is clicked. It disconnects the client and sets the GUI's scene to CONNECTION so that the client can connect again a choose another game.
     * @param event
     */
    public void back(ActionEvent event){
        try {
            gui.disconnect();
        } catch(IOException e){
            //FIXME If it happens it's a problem with the fxml files
            e.printStackTrace();
            return;
        }
        gui.setCurrScene(CONNECTION);

    }

}
