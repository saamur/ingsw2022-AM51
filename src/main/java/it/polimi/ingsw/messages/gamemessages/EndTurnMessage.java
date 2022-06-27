package it.polimi.ingsw.messages.gamemessages;

import it.polimi.ingsw.exceptions.NonExistingPlayerException;
import it.polimi.ingsw.exceptions.WrongGamePhaseException;
import it.polimi.ingsw.exceptions.WrongPlayerException;
import it.polimi.ingsw.exceptions.WrongTurnPhaseException;
import it.polimi.ingsw.messages.GenericMessage;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.model.GameInterface;

/**
 * The EndTurnMessage record models the message that notify the end of a player's turn
 */
public record EndTurnMessage() implements GameMessage {

    @Override
    public String getMessage(){
        return toString();
    }

    @Override
    public Message performMove(String nickname, GameInterface game) throws WrongGamePhaseException, WrongPlayerException, WrongTurnPhaseException, NonExistingPlayerException {

        game.endTurn(nickname);
        return new GenericMessage("You have ended your turn");

    }

    @Override
    public String toString() {
        return "EndTurnMessage{}";
    }

}
