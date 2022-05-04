package it.polimi.ingsw.client.modeldata;

import it.polimi.ingsw.model.Clan;
import it.polimi.ingsw.model.charactercards.CharacterCard;
import it.polimi.ingsw.model.charactercards.CharacterID;
import it.polimi.ingsw.model.charactercards.ProhibitionCharacterCard;
import it.polimi.ingsw.model.charactercards.StudentMoverCharacterCard;

import java.io.Serializable;
import java.util.Map;

public record CharacterCardData(CharacterID characterID,
                                int cost,
                                int numProhibitionCards,
                                Map<Clan, Integer> students) implements Serializable {
    public static CharacterCardData createCharacterCardData(CharacterCard characterCard){
        int numProhibitionCards = 0;
        if(characterCard instanceof ProhibitionCharacterCard){
            numProhibitionCards = ((ProhibitionCharacterCard) characterCard).getNumProhibitionCards();
        }
        boolean studentMover = characterCard instanceof StudentMoverCharacterCard;
        return new CharacterCardData(characterCard.getCharacterID(), characterCard.getCost(), numProhibitionCards, studentMover ? ((StudentMoverCharacterCard) characterCard).getStudents(): null);
    }
}
