package it.polimi.ingsw;

public class StepsCharacterCard extends CharacterCard {

    private final int additionalSteps;

    public StepsCharacterCard(CharacterID characterID) {

        super(characterID);

        switch (getCharacterID()) {
            case POSTMAN:
                additionalSteps = 2;
                break;
            default:
                additionalSteps = 0;
        }

    }

    @Override
    public int effectStepsMotherNature() {
        return additionalSteps;
    }
}
