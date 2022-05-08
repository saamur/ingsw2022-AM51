package it.polimi.ingsw.messages.gamemessages;

import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.messages.ErrorMessage;
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
    public Message performMove(String nickname, GameInterface game) {

        Message answer = null;

        try {
            game.moveMotherNature(nickname, islandIndex);
            answer = new GenericMessage("Mother nature moved");
        } catch (NotValidIndexException | WrongGamePhaseException | WrongPlayerException | WrongTurnPhaseException | NotValidMoveException e) {
            answer = new ErrorMessage(e.getMessage());
        } catch (NonExistingPlayerException e) {
            e.printStackTrace();
        }

        return answer;

    }

    @Override
    public String toString() {
        return "MoveMotherNatureMessage{" +
                "islandIndex=" + islandIndex +
                '}';
    }

}
