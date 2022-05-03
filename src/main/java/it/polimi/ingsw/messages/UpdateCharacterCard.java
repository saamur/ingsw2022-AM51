package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.charactercards.CharacterID;

public record UpdateCharacterCard(CharacterID characterID) implements Message{

    @Override
    public String getMessage() {
        return toString();
    }
}
