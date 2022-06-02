package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.client.modeldata.CharacterCardData;
import it.polimi.ingsw.messages.gamemessages.SetClanCharacterMessage;
import it.polimi.ingsw.model.Clan;
import it.polimi.ingsw.model.charactercards.CharacterID;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;


import java.net.URL;
import java.util.*;

import static it.polimi.ingsw.constants.ConstantsGUI.GAMEBOARD;
import static it.polimi.ingsw.model.Clan.*;

public class ActivateEffectController extends PageController implements Initializable {

    @FXML private ImageView pixies;
    @FXML private ImageView unicorns;
    @FXML private ImageView toads;
    @FXML private ImageView dragons;
    @FXML private ImageView fairies;

    @FXML private Button goToGameBoard;


    private String nickname;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        Map<Clan, ImageView> clans = new EnumMap<>(Clan.class);
        clans.put(PIXIES, pixies);
        clans.put(UNICORNS, unicorns);
        clans.put(TOADS, toads);
        clans.put(DRAGONS, dragons);
        clans.put(FAIRIES, fairies);
    }



    public void setNickname(String nickname){
        this.nickname = nickname;
    }

    public void selectClan(javafx.scene.input.MouseEvent mouseEvent) {
        System.out.println(mouseEvent.getSource() + " has been chosen");
        Clan clan = Clan.valueOf(((javafx.scene.image.ImageView) mouseEvent.getSource()).getId().toUpperCase(Locale.ROOT));
        sendMessage(new SetClanCharacterMessage(clan));
    }

    public void back(ActionEvent actionEvent) {
        gui.setCurrScene(GAMEBOARD);
    }
}
