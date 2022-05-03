package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.Clan;

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
