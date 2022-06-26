package it.polimi.ingsw.messages.gamemessages;

import it.polimi.ingsw.exceptions.NonExistingPlayerException;
import it.polimi.ingsw.exceptions.NotValidMoveException;
import it.polimi.ingsw.exceptions.WrongGamePhaseException;
import it.polimi.ingsw.exceptions.WrongPlayerException;
import it.polimi.ingsw.messages.GenericMessage;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.model.GameInterface;
import it.polimi.ingsw.model.player.Card;

/**
 * ChosenCardMessage gets a message to choose a card
 * @param card the chosen card
 */
public record ChosenCardMessage(Card card) implements GameMessage {

    @Override
    public String getMessage(){
        return this.toString();
    }

    @Override
    public Message performMove(String nickname, GameInterface game) throws WrongGamePhaseException, WrongPlayerException, NonExistingPlayerException, NotValidMoveException {

        game.chosenCard(nickname, card);
        return new GenericMessage("Card chosen");

    }

    @Override
    public String toString() {
        return "ChosenCardMessage{" +
                "card=" + card +
                '}';
    }

}
