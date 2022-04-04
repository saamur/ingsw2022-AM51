package it.polimi.ingsw.charactercards;

/**
 * StepsCharacterCard class models the character cards
 * that have an impact on the calculus of the maximum number of steps that Mother Nature can take
 *
 */
public class StepsCharacterCard extends CharacterCard {

    private final static int[] ADDITIONAL_STEPS;

    static {

        ADDITIONAL_STEPS = new int[CharacterID.values().length];

        ADDITIONAL_STEPS[CharacterID.POSTMAN.ordinal()] = 2;

    }

    public StepsCharacterCard(CharacterID characterID) {
        super(characterID);
    }

    @Override
    public int effectStepsMotherNature() {
        return ADDITIONAL_STEPS[getCharacterID().ordinal()];
    }

}
