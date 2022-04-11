package it.polimi.ingsw.charactercards;

import it.polimi.ingsw.Bag;
import it.polimi.ingsw.Clan;
import it.polimi.ingsw.Turn;
import it.polimi.ingsw.islands.Island;
import it.polimi.ingsw.player.Player;
import it.polimi.ingsw.player.TowerColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.EnumMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ProhibitionCharacterCardTest {
    Player[] players = new Player[3];
    Bag bag;
    CharacterCardCreator creator = new CharacterCardCreator();
    CharacterCard generalCard;

    @BeforeEach
    public void initialization(){
        bag = new Bag();
        players[0] = new Player("Fede", TowerColor.BLACK, 3, bag);
        players[1] = new Player("Giulia", TowerColor.WHITE, 3, bag);
        players[2] = new Player("Samu", TowerColor.GRAY, 3, bag);

        generalCard = creator.createCharacterCard(CharacterID.GRANDMA, bag);

        Map<Clan, Integer> addingStudentsFirstPlayer = new EnumMap<>(Clan.class);
        Map<Clan, Integer> addingStudentsSecondPlayer = new EnumMap<>(Clan.class);
        Map<Clan, Integer> addingStudentsThirdPlayer = new EnumMap<>(Clan.class);

        addingStudentsFirstPlayer.put(Clan.PIXIES, 1); //FIXME is there a better way??
        addingStudentsFirstPlayer.put(Clan.UNICORNS, 8);
        addingStudentsFirstPlayer.put(Clan.TOADS, 2);
        addingStudentsFirstPlayer.put(Clan.DRAGONS, 0);
        addingStudentsFirstPlayer.put(Clan.FAIRIES, 0);

        addingStudentsSecondPlayer.put(Clan.PIXIES, 2);
        addingStudentsSecondPlayer.put(Clan.UNICORNS, 8);
        addingStudentsSecondPlayer.put(Clan.TOADS, 3);
        addingStudentsSecondPlayer.put(Clan.DRAGONS, 4);
        addingStudentsSecondPlayer.put(Clan.FAIRIES, 0);

        addingStudentsThirdPlayer.put(Clan.PIXIES, 3);
        addingStudentsThirdPlayer.put(Clan.UNICORNS, 0);
        addingStudentsThirdPlayer.put(Clan.TOADS, 0);
        addingStudentsThirdPlayer.put(Clan.DRAGONS, 5);
        addingStudentsThirdPlayer.put(Clan.FAIRIES, 0);

        players[0].getChamber().addStudents(addingStudentsFirstPlayer);
        players[1].getChamber().addStudents(addingStudentsSecondPlayer);
        players[2].getChamber().addStudents(addingStudentsThirdPlayer);
    }


    /**
     * testProhibitionCharacterCard tests if the instantiation of GRANDMA card introduce 4 Prohibition Cards, and if the
     * isAvailable method check the presence of the prohibition cards
     * Is expected four Prohibition cards and a true result
     */

    @Test
    public void testProhibitionCharacterCard() {
        ProhibitionCharacterCard card = (ProhibitionCharacterCard)generalCard;
        int numProhibitionCards = card.getNumProhibitionCards();
        boolean available = card.isAvailable();
        assertEquals(4, numProhibitionCards);
        assertTrue(available);
    }

    /**
     * tests that the Prohibition Cards haven't an effect on Mother Nature steps
     * Is expected a 0 result
     */

    @Test
    public void testEffectStepsMotherNature(){
        ProhibitionCharacterCard card = (ProhibitionCharacterCard)generalCard;
        int effect = card.effectStepsMotherNature();
        assertEquals(0, effect);
    }


    /**
     * testAddProhibitionCard tests if the method add correctly a prohibition card on the GRANDMA card
     * Is expected 5 Prohibition card
     */
    @Test
    public void testAddProhibitionCard() {
        ProhibitionCharacterCard card = (ProhibitionCharacterCard)generalCard;
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
        ProhibitionCharacterCard card = (ProhibitionCharacterCard)generalCard;
        card.updateCost();
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
        ProhibitionCharacterCard card = (ProhibitionCharacterCard)generalCard;
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
        ProhibitionCharacterCard card = (ProhibitionCharacterCard)generalCard;
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
        Island island = new Island();
        ProhibitionCharacterCard card = (ProhibitionCharacterCard)generalCard;
        int[] effectOnInfluence = card.effectInfluence(players, players[0], island, null);
        for (int i = 0; i < Clan.values().length; i++)
            assertEquals(0, effectOnInfluence[i]);
    }

    /**
     * tests that a Prohibition card doesn't have any initial effect
     * A true result is expected
     */

    @Test
    public void testApplyInitialEffect(){
        Turn turn = new Turn(players[0], 3);
        ProhibitionCharacterCard card = (ProhibitionCharacterCard)generalCard;
        boolean initialEffect = card.applyInitialEffect(turn, players);
        assertTrue(initialEffect);
    }



}