package it.polimi.ingsw.charactercards;

import it.polimi.ingsw.Clan;
import it.polimi.ingsw.islands.Island;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProhibitionCharacterCardTest {
    /**
     * testProhibitionCharacterCard tests if the instantiation of GRANDMA card introduce 4 Prohibition Cards, and if the
     * isAvailable method check the presence of the prohibition cards
     * Is expected four Prohibition cards and a true result
     */

    @Test
    public void testProhibitionCharacterCard() {
        ProhibitionCharacterCard card = new ProhibitionCharacterCard(CharacterID.GRANDMA);
        int numProhibitionCards = card.getNumProhibitionCards();
        boolean available = card.isAvailable();
        assertEquals(4, numProhibitionCards);
        assertTrue(available);
    }

    /**
     * tests if a card other than GRANDMA is initialized with 0 prohibition card, and therefore the isAvailable method
     * returns false
     */

    @Test
    public void testNoProhibitionCard(){
        ProhibitionCharacterCard card = new ProhibitionCharacterCard(CharacterID.THIEF);
        int numOfProhibitionCards = card.getNumProhibitionCards();
        boolean available = card.isAvailable();
        assertEquals(0, numOfProhibitionCards);
        assertFalse(available);
    }

    /**
     * tests that the Prohibition Cards haven't an effect on Mother Nature steps
     * Is expected a 0 result
     */

    @Test
    public void testEffectStepsMotherNature(){
        ProhibitionCharacterCard card = new ProhibitionCharacterCard(CharacterID.GRANDMA);
        int effect = card.effectStepsMotherNature();
        assertEquals(0, effect);
    }


    /**
     * testAddProhibitionCard tests if the method add correctly a prohibition card on the GRANDMA card
     * Is expected 5 Prohibition card
     */
    @Test
    public void testAddProhibitionCard() {
        ProhibitionCharacterCard card = new ProhibitionCharacterCard(CharacterID.GRANDMA);
        card.addProhibitionCard();
        int numOfProhibitionCards = card.getNumProhibitionCards();
        assertEquals(5, numOfProhibitionCards);
    }

    /**
     * tests if the increaseCost method add one coin to the initial cost
     * Is expected that the card has a final cost of 3
     */

    @Test
    public void testIncreaseCost() {
        ProhibitionCharacterCard card = new ProhibitionCharacterCard(CharacterID.GRANDMA);
        card.increaseCost();
        int finalCost = card.getCost();
        assertEquals(3, finalCost);
    }

    /**
     * tests if applyEffect method return true and add a prohibition card on an Island
     * A true result is expected and a decrease of the number of prohibition card on the card itself
     */

    @Test
    public void testApplyEffect(){
        Island island = new Island();
        ProhibitionCharacterCard card = new ProhibitionCharacterCard(CharacterID.GRANDMA);
        boolean effectApplied = card.applyEffect(null, island);
        int numOfProhibitionCard = card.getNumProhibitionCards();
        assertEquals(3, numOfProhibitionCard);
        assertTrue(effectApplied);

    }

    /**
     * tests if after calling the applyEffect method 4 times, the fifth it return false, because of the absence of
     * Prohibition cards on the card
     * A false result is expected
     */

    @Test
    public void testApplyEffectFiveTimes(){
        Island island = new Island();
        ProhibitionCharacterCard card = new ProhibitionCharacterCard(CharacterID.GRANDMA);
        card.applyEffect(null, island);
        card.applyEffect(null, island);
        card.applyEffect(null, island);
        card.applyEffect(null, island);
        boolean appliedEffect = card.applyEffect(null, island);
        int numOfProhibitionCards = card.getNumProhibitionCards();
        assertFalse(appliedEffect);
        assertEquals(0, numOfProhibitionCards);

    }

    //it would be better to test different character cards and see the different effect they have after the call of these
    //methods. It is better to redo the following tests with the initialization of game and turn

    /**
     * check if the effectInfluence method called by a Prohibition Card return an array of zeros
     * Is expected that a Prohibition Card has no impact on the influence calculation
     */

    @Test
    public void testEffectInfluence() {
        ProhibitionCharacterCard card = new ProhibitionCharacterCard(CharacterID.GRANDMA);
        int[] effectOnInfluence = card.effectInfluence(null, null, null, null);
        for (int i = 0; i < Clan.values().length; i++)
            assertEquals(0, effectOnInfluence[i]);
    }

    /**
     * tests that a Prohibition card doesn't have any initial effect
     * A true result is expected
     */

    @Test
    public void testApplyInitialEffect(){
        ProhibitionCharacterCard card = new ProhibitionCharacterCard(CharacterID.GRANDMA);
        boolean initialEffect = card.applyInitialEffect(null, null);
        assertTrue(initialEffect);
    }



}