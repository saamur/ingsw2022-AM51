package it.polimi.ingsw.client.GUI;


import it.polimi.ingsw.client.modeldata.IslandData;
import it.polimi.ingsw.model.Clan;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.EnumMap;
import java.util.Map;
import java.util.ResourceBundle;

import static it.polimi.ingsw.constants.ConstantsGUI.ISLANDS;
import static it.polimi.ingsw.model.Clan.*;

public class SingleIslandController extends PageController implements Initializable {
    @FXML
    ImageView island;

    @FXML ImageView yellow;
    @FXML ImageView blue;
    @FXML ImageView green;
    @FXML ImageView red;
    @FXML ImageView pink;
    @FXML ImageView motherNature;
    @FXML ImageView tower;
    @FXML ImageView prohibitionCard;

    @FXML Label prohibitionCardLabel;

    @FXML Label yellowLabel;
    @FXML Label blueLabel;
    @FXML Label greenLabel;
    @FXML Label redLabel;
    @FXML Label pinkLabel;
    @FXML Label towerLabel;

    Map<Clan, ImageView> clanColors = new EnumMap<>(Clan.class);
    Map<Clan, Label> clanLabels = new EnumMap<>(Clan.class);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clanColors.put(PIXIES, yellow);
        clanColors.put(UNICORNS, blue);
        clanColors.put(TOADS, green);
        clanColors.put(DRAGONS, red);
        clanColors.put(FAIRIES, pink);

        clanLabels.put(PIXIES, yellowLabel);
        clanLabels.put(UNICORNS, blueLabel);
        clanLabels.put(TOADS, greenLabel);
        clanLabels.put(DRAGONS, redLabel);
        clanLabels.put(FAIRIES, pinkLabel);

        resetIsland();
    }

    public void back(ActionEvent event){
        gui.setCurrScene(ISLANDS);
        resetIsland();
    }

    public void setIsland(IslandData modelIsland, String imageURL, boolean hasMotherNature){
        //TODO display towers/mothernature and students
        Map<Clan, Integer> students = modelIsland.students();
        island.setImage(new Image(imageURL));
        for(Clan clan : Clan.values()) {
            int numStudents = students.get(clan);
            if (numStudents > 0) {
                clanColors.get(clan).setVisible(true);
                clanLabels.get(clan).setText("x" + numStudents);
            }
        }
        int numProhibitionCards = modelIsland.numProhibitionCards();
        if(numProhibitionCards > 0){
            prohibitionCard.setVisible(true);
            prohibitionCardLabel.setText("x" + numProhibitionCards);
        }
        motherNature.setVisible(hasMotherNature);
    }

    public void resetIsland(){
        for(Clan c: Clan.values()){
            clanColors.get(c).setVisible(false);
            clanLabels.get(c).setText("");
        }
        motherNature.setVisible(false);
        tower.setVisible(false);
        towerLabel.setText("");
        prohibitionCard.setVisible(false);
        prohibitionCardLabel.setText("");
    }


}
