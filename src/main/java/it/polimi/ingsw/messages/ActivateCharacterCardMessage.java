package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.charactercards.CharacterID;

public record ActivateCharacterCardMessage(CharacterID characterCardID) implements Message{

    /**
     *
     * @return
     */
    @Override
    public String getMessage(){
        return this.toString();
    }
}
