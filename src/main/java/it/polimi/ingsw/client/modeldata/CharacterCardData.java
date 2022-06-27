package it.polimi.ingsw.client.modeldata;

import it.polimi.ingsw.model.Clan;
import it.polimi.ingsw.model.charactercards.CharacterCard;
import it.polimi.ingsw.model.charactercards.CharacterID;
import it.polimi.ingsw.model.charactercards.ProhibitionCharacterCard;
import it.polimi.ingsw.model.charactercards.StudentMoverCharacterCard;

import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

/**
 * contains all the data of a specific character card
 * @param characterID ID of the character card
 * @param cost cost of the character card
 * @param numProhibitionCards number of prohibition card on the character
 * @param students students on the card
 */

public record CharacterCardData(CharacterID characterID,
                                int cost,
                                int numProhibitionCards,
                                Map<Clan, Integer> students) implements Serializable {
    public static CharacterCardData createCharacterCardData(CharacterCard characterCard){
        int numProhibitionCards = 0;
        if(characterCard instanceof ProhibitionCharacterCard){
            numProhibitionCards = ((ProhibitionCharacterCard) characterCard).getNumProhibitionCards();
        }
        Map<Clan, Integer> students;
        if (characterCard instanceof StudentMoverCharacterCard)
            students = ((StudentMoverCharacterCard) characterCard).getStudents();
        else {
            students = new EnumMap<>(Clan.class);
            for (Clan c : Clan.values())
                students.put(c, 0);
        }
        return new CharacterCardData(characterCard.getCharacterID(), characterCard.getCost(), numProhibitionCards, students);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CharacterCardData that = (CharacterCardData) o;
        return cost == that.cost && numProhibitionCards == that.numProhibitionCards && characterID == that.characterID && students.equals(that.students);
    }

    @Override
    public int hashCode() {
        return Objects.hash(characterID, cost, numProhibitionCards, students);
    }

    @Override
    public String toString() {
        return "CharacterCardData{" +
                "characterID=" + characterID +
                ", cost=" + cost +
                ", numProhibitionCards=" + numProhibitionCards +
                ", students=" + students +
                '}';
    }
}
