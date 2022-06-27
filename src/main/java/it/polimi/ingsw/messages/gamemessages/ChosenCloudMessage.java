package it.polimi.ingsw.messages.gamemessages;

import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.messages.GenericMessage;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.model.GameInterface;

/**
 * The ChosenCloudMessage record models a message to choose a cloud
 * @param cloudIndex index of the chosen cloud
 */
public record ChosenCloudMessage(int cloudIndex) implements GameMessage {

    @Override
    public String getMessage(){
        return this.toString();
    }

    @Override
    public Message performMove(String nickname, GameInterface game) throws NotValidIndexException, WrongGamePhaseException, WrongPlayerException, WrongTurnPhaseException, NonExistingPlayerException, NotValidMoveException {

        game.chosenCloud(nickname, cloudIndex);
        return new GenericMessage("Cloud chosen");

    }

    @Override
    public String toString() {
        return "ChosenCloudMessage{" +
                "cloudIndex=" + cloudIndex +
                '}';
    }

}
