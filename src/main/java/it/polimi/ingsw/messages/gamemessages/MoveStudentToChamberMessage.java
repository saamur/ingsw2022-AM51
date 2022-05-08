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
    public Message performMove(String nickname, GameInterface game) {

        Message answer = null;

        try {
            game.moveStudentToChamber(nickname, clan);
            answer = new GenericMessage("Student moved");
        } catch (WrongGamePhaseException | WrongPlayerException | WrongTurnPhaseException | NotValidMoveException e) {
            answer = new ErrorMessage(e.getMessage());
        } catch (NonExistingPlayerException e) {
            e.printStackTrace();
        }

        return answer;

    }

    @Override
    public String toString() {
        return "MoveStudentToChamberMessage{" +
                "clan=" + clan +
                '}';
    }

}
