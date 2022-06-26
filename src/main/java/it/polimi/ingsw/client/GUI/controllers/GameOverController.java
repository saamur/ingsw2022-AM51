package it.polimi.ingsw.client.GUI.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;


import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class GameOverController extends PageController implements Initializable {

    @FXML private Text winner1;
    @FXML private Text winner2;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
    }

    /**
     * this method is called when the game is over and set the winners of the game
     * @param winners
     */
    public void setWinners(List<String> winners){
        winner1.setText(winners.get(0));
        if(winners.size() > 1){
            winner2.setText(winners.get(1));
        }
    }

}
