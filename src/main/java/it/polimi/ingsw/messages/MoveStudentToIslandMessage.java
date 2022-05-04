package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.Clan;

/**
 * MoveStudentToIslandMessage receive a message to move a student to a chosen island
 * @param clan clan of the student to be moved
 * @param islandIndex the index of the island on which to place the student
 */

public record MoveStudentToIslandMessage(Clan clan, int islandIndex) implements Message{
    @Override
    public String getMessage() {
        return toString();
    }

    @Override
    public String toString() {
        return "MoveStudentToIslandMessage{" +
                "clan=" + clan +
                ", islandIndex=" + islandIndex +
                '}';
    }
}
