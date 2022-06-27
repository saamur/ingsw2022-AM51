package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.messages.gamemessages.SetClanCharacterMessage;
import it.polimi.ingsw.model.Clan;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;


import java.net.URL;
import java.util.*;

import static it.polimi.ingsw.constants.GuiConstants.GAMEBOARD;
import static it.polimi.ingsw.model.Clan.*;

public class ActivateEffectController extends PageController implements Initializable {

    @FXML private ImageView pixies;
    @FXML private ImageView unicorns;
    @FXML private ImageView toads;
    @FXML private ImageView dragons;
    @FXML private ImageView fairies;

    @FXML private Button goToGameBoard;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        Map<Clan, ImageView> clans = new EnumMap<>(Clan.class);
        clans.put(PIXIES, pixies);
        clans.put(UNICORNS, unicorns);
        clans.put(TOADS, toads);
        clans.put(DRAGONS, dragons);
        clans.put(FAIRIES, fairies);
    }


    /**
     * Method is called when the client clicks on a clan
     * @see MouseEvent
     */
    public void selectClan(MouseEvent mouseEvent) {
        System.out.println(mouseEvent.getSource() + " has been chosen");
        Clan clan = Clan.valueOf(((javafx.scene.image.ImageView) mouseEvent.getSource()).getId().toUpperCase(Locale.ROOT));
        sendMessage(new SetClanCharacterMessage(clan));
    }


    /**
     * Method is connected to the "Go Back" Button in the fxml file, it opens the GameBoard scene
     * @param actionEvent @see ActionEvent
     */
    public void back(ActionEvent actionEvent) {
        gui.setCurrScene(GAMEBOARD);
    }
}
