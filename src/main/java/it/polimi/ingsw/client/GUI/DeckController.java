package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.client.modeldata.PlayerData;
import it.polimi.ingsw.messages.gamemessages.ChosenCardMessage;
import it.polimi.ingsw.messages.updatemessages.UpdateChosenCard;
import it.polimi.ingsw.model.player.Card;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.ColorInput;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.*;

import static it.polimi.ingsw.constants.ConstantsGUI.GAMEBOARD;
import static it.polimi.ingsw.model.player.Card.*;

/**
 * Class DeckController is the class responsible for managing the deck.fxml file
 */
public class DeckController extends PageController implements Initializable {

    @FXML Label chooseAssistantLabel;
    @FXML ImageView cheetah;
    @FXML ImageView ostrich;
    @FXML ImageView cat;
    @FXML ImageView eagle;
    @FXML ImageView fox;
    @FXML ImageView lizard;
    @FXML ImageView octopus;
    @FXML ImageView dog;
    @FXML ImageView elephant;
    @FXML ImageView turtle;

    @FXML Label cheetahLabel;
    @FXML Label ostrichLabel;
    @FXML Label catLabel;
    @FXML Label eagleLabel;
    @FXML Label foxLabel;
    @FXML Label lizardLabel;
    @FXML Label octopusLabel;
    @FXML Label dogLabel;
    @FXML Label elephantLabel;
    @FXML Label turtleLabel;


    private Map<Card, ImageView> cards;
    private Map<Card, Label> labels;
    private String nickname;

    /**
     * The method initialize initializes the attribute cards associating every ImageView to the corresponding Card enum value
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cards = new EnumMap<>(Card.class);
        cards.put(CHEETAH, cheetah);
        cards.put(OSTRICH, ostrich);
        cards.put(CAT, cat);
        cards.put(EAGLE, eagle);
        cards.put(FOX, fox);
        cards.put(LIZARD, lizard);
        cards.put(OCTOPUS, octopus);
        cards.put(DOG, dog);
        cards.put(ELEPHANT, elephant);
        cards.put(TURTLE, turtle);

        labels = new EnumMap<>(Card.class);
        labels.put(CHEETAH, cheetahLabel);
        labels.put(OSTRICH, ostrichLabel);
        labels.put(CAT, catLabel);
        labels.put(EAGLE, eagleLabel);
        labels.put(FOX, foxLabel);
        labels.put(LIZARD, lizardLabel);
        labels.put(OCTOPUS, octopusLabel);
        labels.put(DOG, dogLabel);
        labels.put(ELEPHANT, elephantLabel);
        labels.put(TURTLE, turtleLabel);
    }

    /**
     * The setCards method is used when a game is reopened and when an UpdateChosenCard is sent to the client
     * @param players The data of the players, to extract from the currCard information
     * @see UpdateChosenCard
     */
    public void setCards(PlayerData[] players, boolean lastRound){

        Card thisPlayerCard = null;

        ColorAdjust monochrome = new ColorAdjust();
        monochrome.setSaturation(-1);

        for(PlayerData player : players){
            if(this.nickname.equals(player.getNickname())){
                for(Card card : Card.values()){
                    if(player.getAvailableCards().contains(card))
                        cards.get(card).setEffect(null);
                    else
                        cards.get(card).setEffect(monochrome);
                }
                thisPlayerCard = player.getCurrCard();

            }
        }

        for (Label l : labels.values()) {
            l.setText("");
            l.setVisible(false);
        }

        for (PlayerData player : players)
            if (!this.nickname.equals(player.getNickname()) && player.getCurrCard() != null)
                setOtherPlayersCard(player.getCurrCard(), player.getNickname(), lastRound);

        if(thisPlayerCard != null)
            setThisPlayerCard(thisPlayerCard);

    }

    private void setThisPlayerCard (Card card) {
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setSaturation(-1);
        Blend blend = new Blend(
                BlendMode.MULTIPLY,
                colorAdjust,
                new ColorInput(cards.get(card).getX(),
                        cards.get(card).getY(),
                        136,
                        200,
                        Color.GREEN)
        );
        cards.get(card).setEffect(blend);
        labels.get(card).setText(("".equals(labels.get(card).getText()) ?
                "chosen by you" :
                labels.get(card).getText() + " and you"));
        labels.get(card).setVisible(true);

    }

    /**
     *  The method setOtherPlayersCards, shows the card chosen by other players in the current turn as not available.
     * @param card card chosen
     * @param nickname nickname of the player that has chosen the card
     * @param lastRound indicates whether the current round is the last one, for which different rules applly
     */
    private void setOtherPlayersCard(Card card, String nickname, boolean lastRound){
        if(!lastRound) {
            ColorAdjust colorAdjust = new ColorAdjust();
            colorAdjust.setSaturation(-1);
            Blend blend = new Blend(
                    BlendMode.MULTIPLY,
                    colorAdjust,
                    new ColorInput(cards.get(card).getX(),
                            cards.get(card).getY(),
                            136,
                            200,
                            Color.RED)
            );
            cards.get(card).setEffect(blend);
        }
        labels.get(card).setText(("".equals(labels.get(card).getText()) ?
                "chosen by " + nickname :
                labels.get(card).getText() + " and " + nickname));
        labels.get(card).setVisible(true);
    }

    /**
     * The method selectCard is called whenever an ImageView containing a card is selected.
     * It creates a message ChosenCardMessage that is sent to the ServerHandler via a firePropertyChange method.
     * @param mouseEvent
     * @see ChosenCardMessage
     *
     */
    public void selectCard(MouseEvent mouseEvent) {
        System.out.println("The card: " + mouseEvent.getSource() + "has been chosen");
        Card currCard = Card.valueOf(((ImageView) mouseEvent.getSource()).getId().toUpperCase(Locale.ROOT));
        sendMessage(new ChosenCardMessage(currCard));
        System.out.println("Card to be accepted: " + currCard);
    }

    /**
     * The method is called when a specific button is clicked. It sets the currScene to GameBoard.fxml where the player can see the entire Game Board
     * @param actionEvent
     */
    public void goToGameBoard(ActionEvent actionEvent) {
        gui.setCurrScene(GAMEBOARD);
    }

    public void setNickname(String nickname){
        this.nickname = nickname;
    }

    public void setCurrPlayer(String currPlayer){
        //TODO controllare centratura corretta Label
        if (!this.nickname.equals(currPlayer)){
            chooseAssistantLabel.setText(currPlayer + " is choosing");
        } else {
            chooseAssistantLabel.setText("Choose an Assistant");
        }
    }
}

