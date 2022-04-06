package it.polimi.ingsw.charactercards;

import it.polimi.ingsw.Game;
import it.polimi.ingsw.islands.Island;

import java.util.EnumMap;
import java.util.Map;

/**
 * ProhibitionCharacterCard class models the character cards
 * whose effect is to add prohibition cards on islands
 *
 */
public class ProhibitionCharacterCard extends CharacterCard {

    private static final Map<CharacterID, Integer> INITIAL_PROHIBITION_CARDS;

    static {
        INITIAL_PROHIBITION_CARDS = new EnumMap<>(CharacterID.class);
        INITIAL_PROHIBITION_CARDS.put(CharacterID.GRANDMA, 4);
    }

    private int numProhibitionCards;

    public ProhibitionCharacterCard(CharacterID characterID) {
        super(characterID);
        numProhibitionCards = INITIAL_PROHIBITION_CARDS.get(characterID);
    }

    @Override
    public boolean isAvailable() {
        return numProhibitionCards > 0;
    }

    @Override
    public boolean applyEffect(Game game, Island isl) {

        if (numProhibitionCards < 1)
            return false;

        numProhibitionCards--;
        isl.addProhibitionCard();

        return true;

    }

    /**
     * method addProhibitionCard increases the variable numProhibitionCards by 1
     */
    public void addProhibitionCard() {
        numProhibitionCards++;
    }

    public int getNumProhibitionCards() {
        return numProhibitionCards;
    }
}
