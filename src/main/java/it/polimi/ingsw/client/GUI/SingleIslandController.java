package it.polimi.ingsw.client.GUI;


import it.polimi.ingsw.client.modeldata.IslandData;
import it.polimi.ingsw.constants.ConstantsGUI;
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
import static it.polimi.ingsw.constants.ConstantsGUI.getColorClan;
import static it.polimi.ingsw.model.Clan.*;

/**
 * Class SingleIslandController manages and populates the scene showing a single island, which corresponds to the file singleIsland.fxml.
 */
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

    /**
     * The method back is called when the Button "Go back" is clicked. After setting the GUI's currScene to ISLANDS, the island is reset so that next time the SINGLEISLAND scene is opened it will not have any data regarding other islands.
     * @param event
     */
    public void back(ActionEvent event){
        System.out.println("The back button has been clicked");
        gui.setCurrScene(ISLANDS);
        resetIsland();
    }

    /**
     * The setIsland method populates the island with the corresponding students. If motherNature is on this island it shows the image of mother nature, if there are towers or prohibitionCards it shows the image and the number.
     * @param modelIsland the IslandData that has all the information about the island that has been clicked
     * @param imageURL the island's image so that it can be reproduced correctly
     * @param hasMotherNature it's true if motherNature is on this island or false if not
     */
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
        if(modelIsland.numberOfTowers() != 0){
            tower.setVisible(true);
            tower.setImage(new Image(getClass().getResource(ConstantsGUI.getImagePathTower(modelIsland.towerColor())).toExternalForm()));
            towerLabel.setText("x" + modelIsland.numberOfTowers());
        }
        System.out.println("The island has been set");
    }

    /**
     * This method is called to set all the components to not visible.
     */
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
        System.out.println("The island has been reset");
    }


}
