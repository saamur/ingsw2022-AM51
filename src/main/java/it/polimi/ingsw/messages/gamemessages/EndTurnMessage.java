package it.polimi.ingsw.messages.gamemessages;

import it.polimi.ingsw.exceptions.NonExistingPlayerException;
import it.polimi.ingsw.exceptions.WrongGamePhaseException;
import it.polimi.ingsw.exceptions.WrongPlayerException;
import it.polimi.ingsw.exceptions.WrongTurnPhaseException;
import it.polimi.ingsw.messages.ErrorMessage;
import it.polimi.ingsw.messages.GenericMessage;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.model.GameInterface;

/**
 * EndTurnMessage gets the message that notify the end of a player's turn
 */
public record EndTurnMessage() implements GameMessage {

    @Override
    public String getMessage(){
        return toString();
    }

    @Override
    public Message performMove(String nickname, GameInterface game) {

        Message answer = null;

        try {
            game.endTurn(nickname);
            answer = new GenericMessage("You have ended your turn");
        } catch (WrongGamePhaseException | WrongPlayerException | WrongTurnPhaseException e) {
            answer = new ErrorMessage(e.getMessage());
        } catch (NonExistingPlayerException e) {
            e.printStackTrace();
        }

        return answer;

    }

    @Override
    public String toString() {
        return "EndTurnMessage{}";
    }

}