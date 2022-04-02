package it.polimi.ingsw.charactercards;

import it.polimi.ingsw.Bag;

public class CharacterCardCreator {

    public CharacterCardCreator(){}

    public CharacterCard createCharacterCard(CharacterID characterID, Bag bag) {

        return switch (characterID) {
            case FARMER -> new ProfessorsCharacterCard(characterID);
            case GRANDMA -> new ProhibitionCharacterCard(characterID);
            case HERALD, CENTAUR, KNIGHT, MUSHROOMPICKER -> new InfluenceCharacterCard(characterID);
            case POSTMAN -> new StepsCharacterCard(characterID);
            case MONK, JESTER, MINISTREL, PRINCESS, THIEF -> new StudentMoverCharacterCard(characterID, bag);
        };

    }

}
