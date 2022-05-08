package it.polimi.ingsw.messages.gamemessages;

import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.messages.ErrorMessage;
import it.polimi.ingsw.messages.GenericMessage;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.model.Clan;
import it.polimi.ingsw.model.GameInterface;

/**
 * MoveStudentToIslandMessage receive a message to move a student to a chosen island
 * @param clan clan of the student to be moved
 * @param islandIndex the index of the island on which to place the student
 */

public record MoveStudentToIslandMessage(Clan clan, int islandIndex) implements GameMessage {

    @Override
    public String getMessage() {
        return toString();
    }

    @Override
    public Message performMove(String nickname, GameInterface game) {

        Message answer = null;

        try {
            game.moveStudentToIsland(nickname, clan, islandIndex);
            answer = new GenericMessage("Student moved");
        } catch (NotValidIndexException | WrongGamePhaseException | WrongPlayerException | WrongTurnPhaseException | NotValidMoveException e) {
            answer = new ErrorMessage(e.getMessage());
        } catch (NonExistingPlayerException e) {
            e.printStackTrace();
        }

        return answer;

    }

    @Override
    public String toString() {
        return "MoveStudentToIslandMessage{" +
                "clan=" + clan +
                ", islandIndex=" + islandIndex +
                '}';
    }

}
