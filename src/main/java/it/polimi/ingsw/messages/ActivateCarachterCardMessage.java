package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.charactercards.CharacterID;

public record ActivateCarachterCardMessage(CharacterID characterCardID) implements Message{
    @Override
    public String getMessage(){
        return this.toString();
    }
}
