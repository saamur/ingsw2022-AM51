package it.polimi.ingsw.messages.gamemessages;

import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.messages.GenericMessage;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.model.GameInterface;

/**
 * MoveMotherNatureMessage receive a message to move mother nature in a chosen island
 * @param islandIndex the index of the island on which to place mother nature
 */

public record MoveMotherNatureMessage(int islandIndex) implements GameMessage {

    @Override
    public String getMessage(){
        return this.toString();
    }

    @Override
    public Message performMove(String nickname, GameInterface game) throws NotValidIndexException, WrongGamePhaseException, WrongPlayerException, WrongTurnPhaseException, NonExistingPlayerException, NotValidMoveException {

        game.moveMotherNature(nickname, islandIndex);
        return new GenericMessage("Mother nature moved");

    }

    @Override
    public String toString() {
        return "MoveMotherNatureMessage{" +
                "islandIndex=" + islandIndex +
                '}';
    }

}
