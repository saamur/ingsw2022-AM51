package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.client.modeldata.GameData;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;


import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class GameOverController extends PageController implements Initializable {

    @FXML Text winner1;
    @FXML Text winner2;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
    }

    public void setWinners(List<String> winners){
        winner1.setText(winners.get(0));
        if(winners.size() > 1){
            winner2.setText(winners.get(1));
        }
    }

}
