package it.polimi.ingsw.client.GUI;

import it.polimi.ingsw.client.modeldata.PlayerData;
import it.polimi.ingsw.constants.ConstantsGUI;
import it.polimi.ingsw.messages.gamemessages.ChosenCardMessage;
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
import java.util.EnumMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import static it.polimi.ingsw.constants.ConstantsGUI.GAMEBOARD;
import static it.polimi.ingsw.model.player.Card.*;

/**
 * Class DeckController is the class responsible for managing the deck.fxml file
 */
public class DeckController extends PageController implements Initializable {
    Card currCard;

    @FXML
    Label chooseAssistantLabel;
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


    Map<Card, ImageView> cards;
    boolean [] chosenCards = {false, false, false, false, false, false, false, false, false, false};

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

    }

    /**
     * The setCards method is used when a game is reopened
     * @param players The data of the players, to extract from the currCard information
     * @param nickname Nickname of the player using the GUI
     */
    public void setCards(PlayerData[] players, String nickname){
        for(PlayerData player : players){
            if(nickname.equals(player.getNickname())){
                for(Card card : Card.values()){
                    chosenCards[card.getPriority()-1] = player.getAvailableCards().contains(card);
                }
                currCard = player.getCurrCard();
                if(currCard != null) {
                    confirmCurrCard();
                }
            } else {
                if(player.getCurrCard() != null) {
                    setOtherPlayersCards(player.getCurrCard(), player.getNickname());
                }
            }
        }
    }

    /**
     * The method selectCard is called whenever an ImageView containing a card is selected.
     * It creates a message ChosenCardMessage that is sent to the ServerHandler via a firePropertyChange method.
     * After this method has ended either the {@link #confirmCurrCard() confirmCurrCard} method or the {@link #handleErrorMessage(String) handleErrorMessage} will be called, depending whether the choice is correct or not.
     * @param mouseEvent
     * @see ChosenCardMessage
     *
     */
    public void selectCard(MouseEvent mouseEvent) {
        System.out.println("The card: " + mouseEvent.getSource() + "has been chosen");
        currCard = Card.valueOf(((ImageView) mouseEvent.getSource()).getId().toUpperCase(Locale.ROOT));
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

    /**
     * The method confirmCurrCard is called after a card has been selected and after the ServerHandler sends confirmation that the chosen card is correct.
     */
    public void confirmCurrCard(){
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setSaturation(-1);
        Blend blend = new Blend(
                BlendMode.MULTIPLY,
                colorAdjust,
                new ColorInput(cards.get(currCard).getX(),
                        cards.get(currCard).getY(),
                        136,
                        200,
                        Color.GREEN)
        );
        cards.get(currCard).setEffect(blend);
        chosenCards[currCard.getPriority() - 1] = true;
        System.out.println("Accepted card: " + currCard);
    }

    /**
     * The handleErrorMessage in DeckController calls for the PageController method {@link PageController#handleErrorMessage(String) handleErrorMessage}. If the error message received refers to the chosen card being wrong it sets the currCard to null.
     * @param message
     */
    @Override
    public void handleErrorMessage(String message){
        super.handleErrorMessage(message);
        if(message.contains("already chosen") || message.contains("Not the turn of this player")){
            currCard = null;
        }
        System.out.println("Card should be null: " + currCard); //TODO delete
    }

    /**
     *  The method setOtherPlayersCards, shows the card chosen by other players in the current turn as not available.
     * @param card card chosen
     * @param nickname nickname of the player that has chosen the card
     */
    public void setOtherPlayersCards(Card card, String nickname){
        ImageView imageCard = cards.get(card);
        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setSaturation(-1);
        Blend blend = new Blend(
                BlendMode.MULTIPLY,
                colorAdjust,
                new ColorInput(imageCard.getX(),
                        imageCard.getY(),
                        136,
                        200,
                        Color.RED)
                );
       imageCard.setEffect(blend);
       //TODO setLabel with nickname of Player that has chosen the card
    }

    /**
     * The method resetCards modifies the Card effects so that only the cards chosen by the Player using the GUI have an effect
     * This way the cards chosen by other players in another turn are not marked.
     * This method is supposed to be called after a turn ends for all the players
     */
    public void resetCards(){
        ColorAdjust monochrome = new ColorAdjust();
        monochrome.setSaturation(-1);
        for(Card card : Card.values()){
            if(!chosenCards[card.getPriority() - 1]){
                cards.get(card).setEffect(null);
            } else{
                cards.get(card).setEffect(monochrome);
            }
        }

    }
    //TODO add showSelection()??
}
