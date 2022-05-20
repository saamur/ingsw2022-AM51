package it.polimi.ingsw.messages.gamemessages;

import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.messages.ErrorMessage;
import it.polimi.ingsw.messages.GenericMessage;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.model.Clan;
import it.polimi.ingsw.model.GameInterface;

/**
 * MoveStudentToChamberMessage receive a message to move a student in the chamber
 * @param clan clan of the student to be moved
 */

public record MoveStudentToChamberMessage(Clan clan) implements GameMessage {

    @Override
    public String getMessage(){
        return this.toString();
    }

    @Override
    public Message performMove(String nickname, GameInterface game) throws WrongGamePhaseException, WrongPlayerException, WrongTurnPhaseException, NonExistingPlayerException, NotValidMoveException {

        game.moveStudentToChamber(nickname, clan);
        return new GenericMessage("Student moved");

    }

    @Override
    public String toString() {
        return "MoveStudentToChamberMessage{" +
                "clan=" + clan +
                '}';
    }

}
