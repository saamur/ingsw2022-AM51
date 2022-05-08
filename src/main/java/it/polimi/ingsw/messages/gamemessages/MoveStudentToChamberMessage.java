package it.polimi.ingsw.messages.gamemessages;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.model.Clan;

/**
 * MoveStudentToChamberMessage receive a message to move a student in the chamber
 * @param clan clan of the student to be moved
 */

public record MoveStudentToChamberMessage(Clan clan) implements Message
{
    @Override
    public String getMessage(){
        return this.toString();
    }

    @Override
    public String toString() {
        return "MoveStudentToChamberMessage{" +
                "clan=" + clan +
                '}';
    }
}
