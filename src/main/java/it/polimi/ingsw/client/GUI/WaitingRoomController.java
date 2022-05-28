package it.polimi.ingsw.client.GUI;

import javafx.event.ActionEvent;


import java.io.IOException;

import static it.polimi.ingsw.constants.ConstantsGUI.*;

public class WaitingRoomController extends PageController{

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
