package it.polimi.ingsw.model.charactercards;

import it.polimi.ingsw.model.Bag;

/**
 * CharacterCardCreator class contains the method needed to create
 * CharacterCard objects with the correct concrete subclass
 *
 */
public class CharacterCardCreator {

    public CharacterCardCreator(){}

    /**
     * method createCharacterCard instantiates a CharacterCard object with the given CharacterID
     * using the correct concrete subclass
     * @param characterID   the CharacterID of the CharacterCard to create
     * @param bag           the Bag from which the students will be taken (if needed)
     * @return              the created object
     */
    public CharacterCard createCharacterCard(CharacterID characterID, Bag bag) {

        return switch (characterID) {
            case MONK, JESTER, MINSTREL, PRINCESS, THIEF -> new StudentMoverCharacterCard(characterID, bag);
            case FARMER -> new ProfessorsCharacterCard(characterID);
            case HERALD, CENTAUR, KNIGHT, MUSHROOMPICKER -> new InfluenceCharacterCard(characterID);
            case POSTMAN -> new StepsCharacterCard(characterID);
            case GRANDMA -> new ProhibitionCharacterCard(characterID);
        };

    }

}
