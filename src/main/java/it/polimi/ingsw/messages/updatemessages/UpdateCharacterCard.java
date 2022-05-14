package it.polimi.ingsw.messages.updatemessages;

import it.polimi.ingsw.client.modeldata.CharacterCardData;
import it.polimi.ingsw.client.modeldata.GameData;
import it.polimi.ingsw.messages.updatemessages.UpdateMessage;
import it.polimi.ingsw.model.charactercards.CharacterCard;
import it.polimi.ingsw.model.charactercards.CharacterID;

public record UpdateCharacterCard(CharacterCardData characterCard) implements UpdateMessage{

    @Override
    public String getMessage() {
        return toString();
    }

    @Override
    public void updateGameData(GameData gameData) {

        for (int i = 0; i < gameData.getCharacterCardData().length; i++)
            if (gameData.getCharacterCardData()[i].characterID() == characterCard.characterID())
                gameData.getCharacterCardData()[i] = characterCard;

    }

    @Override
    public String toString() {
        return "UpdateCharacterCard{" +
                "characterCard=" + characterCard +
                '}';
    }

}
