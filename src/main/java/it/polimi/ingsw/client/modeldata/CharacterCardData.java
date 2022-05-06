package it.polimi.ingsw.client.modeldata;

import it.polimi.ingsw.model.Clan;
import it.polimi.ingsw.model.charactercards.CharacterCard;
import it.polimi.ingsw.model.charactercards.CharacterID;
import it.polimi.ingsw.model.charactercards.ProhibitionCharacterCard;
import it.polimi.ingsw.model.charactercards.StudentMoverCharacterCard;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

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
