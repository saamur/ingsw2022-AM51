package it.polimi.ingsw.charactercards;

import java.util.EnumMap;
import java.util.Map;

/**
 * StepsCharacterCard class models the character cards
 * that have an impact on the calculus of the maximum number of steps that Mother Nature can take
 *
 */
public class StepsCharacterCard extends CharacterCard {

    private final static Map<CharacterID, Integer> ADDITIONAL_STEPS;

    static {
        ADDITIONAL_STEPS = new EnumMap<>(CharacterID.class);
        ADDITIONAL_STEPS.put(CharacterID.POSTMAN, 2);
    }

    public StepsCharacterCard(CharacterID characterID) {
        super(characterID);
    }

    @Override
    public int effectStepsMotherNature() {
        return ADDITIONAL_STEPS.get(getCharacterID());
    }

}
