package it.polimi.ingsw.messages.gamemessages;

import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.messages.GenericMessage;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.model.Clan;
import it.polimi.ingsw.model.GameInterface;

/**
 * The MoveStudentToIslandMessage record models a message to move a student on a chosen island
 * @param clan clan of the student to be moved
 * @param islandIndex the index of the island on which to place the student
 */

public record MoveStudentToIslandMessage(Clan clan, int islandIndex) implements GameMessage {

    @Override
    public String getMessage() {
        return toString();
    }

    @Override
    public Message performMove(String nickname, GameInterface game) throws NotValidIndexException, WrongGamePhaseException, WrongPlayerException, WrongTurnPhaseException, NotValidMoveException, NonExistingPlayerException {

        game.moveStudentToIsland(nickname, clan, islandIndex);
        return new GenericMessage("Student moved");

    }

    @Override
    public String toString() {
        return "MoveStudentToIslandMessage{" +
                "clan=" + clan +
                ", islandIndex=" + islandIndex +
                '}';
    }

}
