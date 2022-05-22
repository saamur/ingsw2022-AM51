package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.client.modeldata.IslandData;
import it.polimi.ingsw.client.modeldata.IslandManagerData;
import it.polimi.ingsw.model.Clan;
import it.polimi.ingsw.model.player.TowerColor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.*;

import static it.polimi.ingsw.constants.ConstantsGUI.*;
import static it.polimi.ingsw.model.Clan.*;

public class IslandsPageController extends PageController implements Initializable {
    List<AnchorPane> islands;
    List<ImageView> prohibitionCards;
    Map<Clan, List<ImageView>> clanColors = new EnumMap<>(Clan.class);

    @FXML AnchorPane anchorIsland0;
    @FXML AnchorPane anchorIsland1;
    @FXML AnchorPane anchorIsland2;
    @FXML AnchorPane anchorIsland3;
    @FXML AnchorPane anchorIsland4;
    @FXML AnchorPane anchorIsland5;
    @FXML AnchorPane anchorIsland6;
    @FXML AnchorPane anchorIsland7;
    @FXML AnchorPane anchorIsland8;
    @FXML AnchorPane anchorIsland9;
    @FXML AnchorPane anchorIsland10;
    @FXML AnchorPane anchorIsland11;

    @FXML ImageView prohibitionCard0;
    @FXML ImageView prohibitionCard1;
    @FXML ImageView prohibitionCard2;
    @FXML ImageView prohibitionCard3;
    @FXML ImageView prohibitionCard4;
    @FXML ImageView prohibitionCard5;
    @FXML ImageView prohibitionCard6;
    @FXML ImageView prohibitionCard7;
    @FXML ImageView prohibitionCard8;
    @FXML ImageView prohibitionCard9;
    @FXML ImageView prohibitionCard10;
    @FXML ImageView prohibitionCard11;

    @FXML ImageView pink0;
    @FXML ImageView pink1;
    @FXML ImageView pink2;
    @FXML ImageView pink3;
    @FXML ImageView pink4;
    @FXML ImageView pink5;
    @FXML ImageView pink6;
    @FXML ImageView pink7;
    @FXML ImageView pink8;
    @FXML ImageView pink9;
    @FXML ImageView pink10;
    @FXML ImageView pink11;
    List<ImageView> pink;

    @FXML ImageView green0;
    @FXML ImageView green1;
    @FXML ImageView green2;
    @FXML ImageView green3;
    @FXML ImageView green4;
    @FXML ImageView green5;
    @FXML ImageView green6;
    @FXML ImageView green7;
    @FXML ImageView green8;
    @FXML ImageView green9;
    @FXML ImageView green10;
    @FXML ImageView green11;
    List<ImageView> green;

    @FXML ImageView blue0;
    @FXML ImageView blue1;
    @FXML ImageView blue2;
    @FXML ImageView blue3;
    @FXML ImageView blue4;
    @FXML ImageView blue5;
    @FXML ImageView blue6;
    @FXML ImageView blue7;
    @FXML ImageView blue8;
    @FXML ImageView blue9;
    @FXML ImageView blue10;
    @FXML ImageView blue11;
    List<ImageView> blue;

    @FXML ImageView red0;
    @FXML ImageView red1;
    @FXML ImageView red2;
    @FXML ImageView red3;
    @FXML ImageView red4;
    @FXML ImageView red5;
    @FXML ImageView red6;
    @FXML ImageView red7;
    @FXML ImageView red8;
    @FXML ImageView red9;
    @FXML ImageView red10;
    @FXML ImageView red11;
    List<ImageView> red;

    @FXML ImageView yellow0;
    @FXML ImageView yellow1;
    @FXML ImageView yellow2;
    @FXML ImageView yellow3;
    @FXML ImageView yellow4;
    @FXML ImageView yellow5;
    @FXML ImageView yellow6;
    @FXML ImageView yellow7;
    @FXML ImageView yellow8;
    @FXML ImageView yellow9;
    @FXML ImageView yellow10;
    @FXML ImageView yellow11;
    List<ImageView> yellow;

    @FXML ImageView tower0;
    @FXML ImageView tower1;
    @FXML ImageView tower2;
    @FXML ImageView tower3;
    @FXML ImageView tower4;
    @FXML ImageView tower5;
    @FXML ImageView tower6;
    @FXML ImageView tower7;
    @FXML ImageView tower8;
    @FXML ImageView tower9;
    @FXML ImageView tower10;
    @FXML ImageView tower11;
    List<ImageView> towers;

    @FXML ImageView motherNature0;
    @FXML ImageView motherNature1;
    @FXML ImageView motherNature2;
    @FXML ImageView motherNature3;
    @FXML ImageView motherNature4;
    @FXML ImageView motherNature5;
    @FXML ImageView motherNature6;
    @FXML ImageView motherNature7;
    @FXML ImageView motherNature8;
    @FXML ImageView motherNature9;
    @FXML ImageView motherNature10;
    @FXML ImageView motherNature11;
    List<ImageView> motherNature;

    int motherNaturePosition;






    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        islands = new ArrayList<>(Arrays.asList(anchorIsland0, anchorIsland1, anchorIsland2, anchorIsland3, anchorIsland4, anchorIsland5, anchorIsland6, anchorIsland7, anchorIsland8, anchorIsland9, anchorIsland10, anchorIsland11));
        prohibitionCards = new ArrayList<>(Arrays.asList(prohibitionCard0, prohibitionCard1, prohibitionCard2, prohibitionCard3, prohibitionCard4, prohibitionCard5, prohibitionCard6, prohibitionCard7, prohibitionCard8, prohibitionCard9, prohibitionCard10, prohibitionCard11));
        pink = new ArrayList<>(Arrays.asList(pink0, pink1, pink2, pink3, pink4, pink5, pink6, pink7, pink8, pink9, pink10, pink11));
        green = new ArrayList<>(Arrays.asList(green0, green1, green2, green3, green4, green5, green6, green7, green8, green9, green10, green11));
        blue = new ArrayList<>(Arrays.asList(blue0, blue1, blue2, blue3, blue4, blue5, blue6, blue7, blue8, blue9, blue10, blue11));
        red = new ArrayList<>(Arrays.asList(red0, red1, red2, red3, red4, red5, red6, red7, red8, red9, red10, red11));
        yellow = new ArrayList<>(Arrays.asList(yellow0, yellow1, yellow2, yellow3, yellow4, yellow5, yellow6, yellow7, yellow8, yellow9, yellow10, yellow11));

        clanColors.put(PIXIES, yellow);
        clanColors.put(UNICORNS, blue);
        clanColors.put(TOADS, green);
        clanColors.put(DRAGONS, red);
        clanColors.put(FAIRIES, pink);

        towers = new ArrayList<>(Arrays.asList(tower0, tower1, tower2, tower3, tower4, tower5, tower6, tower7, tower8, tower9, tower10, tower11));
        motherNature = new ArrayList<>(Arrays.asList(motherNature0, motherNature1, motherNature2, motherNature3, motherNature4, motherNature5, motherNature6, motherNature7, motherNature8, motherNature9, motherNature10, motherNature11));

        for(int i=0; i<islands.size(); i++) {
            motherNature.get(i).setVisible(false);
            towers.get(i).setVisible(false);
            prohibitionCards.get(i).setVisible(false);
        }

    }

    public void back(ActionEvent event){
        gui.setCurrScene(GAMEBOARD);
    }


    public void selectIsland(MouseEvent mouseEvent) {
        System.out.println("selected island" + mouseEvent.getSource());
    }

    public void setIslands(IslandManagerData islandManager){
        List<IslandData> modelIslands = islandManager.getIslands();
        for(int i=0; i<modelIslands.size(); i++){ //FIXME GESTIRE I MERGE
            prepareIsland(islands.get(i), modelIslands.get(i), i);
        }
        this.motherNaturePosition = islandManager.getMotherNaturePosition();
        motherNature.get(motherNaturePosition).setVisible(true);
    }

    private void prepareIsland(AnchorPane guiIsland, IslandData modelIsland, int anchorIndex){
        if(modelIsland.numProhibitionCards() != 0){
            prohibitionCards.get(anchorIndex).setVisible(true); //should work
        }
        Map<Clan, Integer> studentsPresent = modelIsland.students();
        for(Clan clan : Clan.values()){
            clanColors.get(clan).get(anchorIndex).setVisible(studentsPresent.get(clan) != 0);
        }
        TowerColor towerColor = modelIsland.towerColor();
        if(towerColor != null){
            towers.get(anchorIndex).setImage(new Image(getClass().getResource(TOWERS_IMAGES.get(towerColor)).toExternalForm()));
            towers.get(anchorIndex).setVisible(true);
        }
    }
}
