package it.polimi.ingsw.messages.gamemessages;

import it.polimi.ingsw.messages.Message;

/**
 * MoveMotherNatureMessage receive a message to move mother nature in a chosen island
 * @param islandIndex the index of the island on which to place mother nature
 */

public record MoveMotherNatureMessage(int islandIndex) implements Message {
    @Override
    public String getMessage(){
        return this.toString();
    }

    @Override
    public String toString() {
        return "MoveMotherNatureMessage{" +
                "islandIndex=" + islandIndex +
                '}';
    }
}
