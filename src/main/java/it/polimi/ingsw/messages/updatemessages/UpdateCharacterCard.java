package it.polimi.ingsw.messages.updatemessages;

import it.polimi.ingsw.messages.updatemessages.UpdateMessage;
import it.polimi.ingsw.model.charactercards.CharacterID;

public record UpdateCharacterCard(CharacterID characterID) implements UpdateMessage{

    @Override
    public String getMessage() {
        return toString();
    }
}
