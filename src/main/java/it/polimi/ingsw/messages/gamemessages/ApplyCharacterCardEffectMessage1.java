package it.polimi.ingsw.messages.gamemessages;

import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.messages.ErrorMessage;
import it.polimi.ingsw.messages.GenericMessage;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.model.GameInterface;

/**
 * ApplyCharacterCardEffectMessage gets the message to activate the effect of an active card on a chosen island
 * @param islandIndex index of the island where to activate the effect
 */

public record ApplyCharacterCardEffectMessage1(int islandIndex) implements GameMessage {

    @Override
    public String getMessage(){
        return this.toString();
    }

    @Override
    public Message performMove(String nickname, GameInterface game) {

        Message answer = null;

        try {
            game.applyCharacterCardEffect(nickname, islandIndex);
            answer = new GenericMessage("the effect has been applied");
        } catch (NotValidIndexException | WrongGamePhaseException | ExpertModeNotEnabledException | WrongPlayerException | NotValidMoveException e) {
            answer = new ErrorMessage(e.getMessage());
        } catch (NonExistingPlayerException e) {
            e.printStackTrace();
        }

        return answer;

    }

    @Override
    public String toString() {
        return "ApplyCharacterCardEffectMessage1{" +
                "islandIndex=" + islandIndex +
                '}';
    }

}
