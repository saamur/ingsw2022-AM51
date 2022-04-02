package it.polimi.ingsw.charactercards;

import it.polimi.ingsw.Bag;

public class CharacterCardCreator {

    public CharacterCardCreator(){}

    public CharacterCard createCharacterCard(CharacterID characterID, Bag bag) {

        switch (characterID) {
            case FARMER:
                return new ProfessorsCharacterCard(characterID);

            case GRANDMA:
                return new ProhibitionCharacterCard(characterID);

            case HERALD: case CENTAUR: case KNIGHT: case MUSHROOMPICKER:
                return new InfluenceCharacterCard(characterID);

            case POSTMAN:
                return new StepsCharacterCard(characterID);

            case MONK: case JESTER: case MINISTREL: case PRINCESS: case THIEF:
                return new StudentMoverCharacterCard(characterID, bag);
        }

        return null;

    }

}
