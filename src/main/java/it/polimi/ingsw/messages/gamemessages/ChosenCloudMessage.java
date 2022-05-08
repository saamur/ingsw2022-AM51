package it.polimi.ingsw.messages.gamemessages;

import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.messages.ErrorMessage;
import it.polimi.ingsw.messages.GenericMessage;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.model.GameInterface;

/**
 * ChosenCloudMessage gets a message to choose a cloud
 * @param cloudIndex index of the chosen cloud
 */
public record ChosenCloudMessage(int cloudIndex) implements GameMessage {

    @Override
    public String getMessage(){
        return this.toString();
    }

    @Override
    public Message performMove(String nickname, GameInterface game) {

        Message answer = null;

        try {
            game.chosenCloud(nickname, cloudIndex);
            answer = new GenericMessage("Cloud chosen");
        } catch (NotValidIndexException | WrongGamePhaseException | WrongPlayerException | WrongTurnPhaseException | NotValidMoveException e) {
            answer = new ErrorMessage(e.getMessage());
        } catch (NonExistingPlayerException e) {
            e.printStackTrace();
        }

        return answer;

    }

    @Override
    public String toString() {
        return "ChosenCloudMessage{" +
                "cloudIndex=" + cloudIndex +
                '}';
    }

}
