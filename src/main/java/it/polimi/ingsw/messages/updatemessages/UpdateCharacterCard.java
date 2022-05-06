package it.polimi.ingsw.messages.updatemessages;

import it.polimi.ingsw.client.modeldata.CharacterCardData;
import it.polimi.ingsw.messages.updatemessages.UpdateMessage;
import it.polimi.ingsw.model.charactercards.CharacterCard;
import it.polimi.ingsw.model.charactercards.CharacterID;

public record UpdateCharacterCard(CharacterCardData characterCard) implements UpdateMessage{

    @Override
    public String getMessage() {
        return toString();
    }

    @Override
    public String toString() {
        return "UpdateCharacterCard{" +
                "characterCard=" + characterCard +
                '}';
    }
}
