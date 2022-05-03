package it.polimi.ingsw.messages;

import it.polimi.ingsw.model.charactercards.CharacterID;

/**
 * ActivateCharacterCardMessage gets a message to activate a character card
 * @param characterCardID ID of the card to activate
 */
public record ActivateCharacterCardMessage(CharacterID characterCardID) implements Message {

    @Override
    public String getMessage(){
        return this.toString();
    }

    @Override
    public String toString() {
        return "ActivateCharacterCardMessage{" +
                "characterCardID=" + characterCardID +
                '}';
    }

}
