package it.polimi.ingsw.messages.gamemessages;

import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.messages.GenericMessage;
import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.model.GameInterface;
import it.polimi.ingsw.model.charactercards.CharacterID;

/**
 * ActivateCharacterCardMessage gets a message to activate a character card
 * @param characterCardID ID of the card to activate
 */
public record ActivateCharacterCardMessage(CharacterID characterCardID) implements GameMessage {

    @Override
    public String getMessage(){
        return this.toString();
    }

    @Override
    public Message performMove(String nickname, GameInterface game) throws WrongGamePhaseException, ExpertModeNotEnabledException, WrongPlayerException, NonExistingPlayerException, NotValidMoveException {

        game.activateCharacterCard(nickname, characterCardID);
        return new GenericMessage("You have activated " + characterCardID.name().toLowerCase());

    }

    @Override
    public String toString() {
        return "ActivateCharacterCardMessage{" +
                "characterCardID=" + characterCardID +
                '}';
    }

}
