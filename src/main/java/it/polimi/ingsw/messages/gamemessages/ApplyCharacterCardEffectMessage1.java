package it.polimi.ingsw.messages.gamemessages;

import it.polimi.ingsw.exceptions.*;
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
    public Message performMove(String nickname, GameInterface game) throws NotValidIndexException, WrongGamePhaseException, ExpertModeNotEnabledException, WrongPlayerException, NonExistingPlayerException, NotValidMoveException {

        game.applyCharacterCardEffect(nickname, islandIndex);
        return new GenericMessage("the effect has been applied");

    }

    @Override
    public String toString() {
        return "ApplyCharacterCardEffectMessage1{" +
                "islandIndex=" + islandIndex +
                '}';
    }

}
