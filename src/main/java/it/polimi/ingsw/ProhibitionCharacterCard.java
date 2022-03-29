package it.polimi.ingsw;

public class ProhibitionCharacterCard extends CharacterCard {

    private int numProhibitionCards;

    public ProhibitionCharacterCard(CharacterID characterID) {

        super(characterID);

        if (getCharacterID() == CharacterID.GRANDMA) {
            numProhibitionCards = 4;
        }
        else {
            numProhibitionCards = 0;
        }

    }

    @Override
    public boolean applyEffect(Island isl) {

        if (numProhibitionCards < 1)
            return false;

        numProhibitionCards--;
        isl.addProhibitionCard();

        return true;

    }
}
