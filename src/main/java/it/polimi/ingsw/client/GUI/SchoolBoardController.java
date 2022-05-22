package it.polimi.ingsw.client.GUI;


import it.polimi.ingsw.client.modeldata.PlayerData;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

import static it.polimi.ingsw.constants.ConstantsGUI.*;

//FIXME USE computed_size
public class SchoolBoardController extends PageController implements Initializable {


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //FIXME mettere giocatori opzionali
    }

    //mettere nomi giocatori sulle tab
    //mettere giocatori nelle plancie?
    public void back(ActionEvent event){
        gui.setCurrScene(GAMEBOARD);
    }

    public void setSchoolBoards(PlayerData[] playersData){

    }


}
