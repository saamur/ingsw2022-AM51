package it.polimi.ingsw.messages.updatemessages;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.model.charactercards.CharacterID;

public record UpdateActivatedCard(CharacterID characterCardID) implements UpdateMessage {

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
