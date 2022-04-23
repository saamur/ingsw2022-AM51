package it.polimi.ingsw.charactercards;

import it.polimi.ingsw.Bag;
import it.polimi.ingsw.Clan;
import it.polimi.ingsw.TestUtil;
import it.polimi.ingsw.Turn;
import it.polimi.ingsw.islands.Island;
import it.polimi.ingsw.player.Player;
import it.polimi.ingsw.player.TowerColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;


import java.util.EnumMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ProfessorsCharacterCardTest {
    int numOfPlayers = 3;
    Player[] players = new Player[numOfPlayers];
    Bag bag;
    CharacterCardCreator creator = new CharacterCardCreator();
    CharacterCard generalCard;


    @BeforeEach
    public  void initialization(){
        bag = new Bag();
        players[0] = new Player("Fede", TowerColor.BLACK, numOfPlayers, bag);
        players[1] = new Player("Giulia", TowerColor.WHITE, numOfPlayers, bag);
        players[2] = new Player("Samu", TowerColor.GRAY, numOfPlayers, bag);


        generalCard = creator.createCharacterCard(CharacterID.FARMER, bag);
        Map<Clan, Integer> addingStudentsFirstPlayer = TestUtil.studentMapCreator(1, 8, 2, 0, 0);
        Map<Clan, Integer> addingStudentsSecondPlayer = TestUtil.studentMapCreator(2, 8, 3, 4, 0);
        Map<Clan, Integer> addingStudentsThirdPlayer = TestUtil.studentMapCreator(3, 0, 0, 5, 0);


        players[0].getChamber().addStudents(addingStudentsFirstPlayer);
        players[1].getChamber().addStudents(addingStudentsSecondPlayer);
        players[2].getChamber().addStudents(addingStudentsThirdPlayer);
    }

    /**
     * test check if the calculation of who has the Professor is correct, with no particular cases
     * Is expected that the third player win the Professor of the PIXIES
     */

    @Test
    public void testEffectPlayerProfessor(){
        ProfessorsCharacterCard card = (ProfessorsCharacterCard)generalCard;
        Player player = card.effectPlayerProfessor(players, players[0], Clan.PIXIES);
        assertEquals(players[2], player);
    }

    /**
     * the test checks if, with the same number of students, the current player gets hold of the professor
     * Is expected that the first player, the current one, win the Professor of the UNICORN
     */

    @Test
    public void testEffectPlayerProfessorWithParity(){
        ProfessorsCharacterCard card = (ProfessorsCharacterCard)generalCard;
        Player player = card.effectPlayerProfessor(players, players[1], Clan.UNICORNS);
        assertEquals(players[1], player);
    }


    /**
     * test check if two other players, different from the curr one, have more and equal number of professor
     * is expected that there are no changes
     */
    @Test
    public void testEffectPlayerProfessorParityWithOther(){
        ProfessorsCharacterCard card = (ProfessorsCharacterCard)generalCard;
        Player player = card.effectPlayerProfessor(players, players[2], Clan.UNICORNS);
        assertNull(player);
    }

    /**
     * the test check if the initial effect of the Farmer card was applied correctly
     * A true result is expected
     */


    @Test
    public void testApplyInitialEffect(){
        Turn turn = new Turn(players[0], numOfPlayers);
        ProfessorsCharacterCard card = (ProfessorsCharacterCard)generalCard;
        turn.setActivatedCharacterCard(card);
        boolean effect = card.applyInitialEffect(turn, players);
        Map<Clan, Boolean> hasProfessors = players[0].getChamber().getProfessors();
        assertTrue(effect);

        Map<Clan, Boolean> expectedHasProfessors = new EnumMap<>(Clan.class);
        expectedHasProfessors.put(Clan.PIXIES, false);
        expectedHasProfessors.put(Clan.UNICORNS, true);
        expectedHasProfessors.put(Clan.TOADS, false);
        expectedHasProfessors.put(Clan.DRAGONS, false);
        expectedHasProfessors.put(Clan.FAIRIES, true);

        for(int i=0; i<Clan.values().length; i++){
            assertEquals(expectedHasProfessors.get(Clan.values()[i]), hasProfessors.get(Clan.values()[i]));
        }
    }

    /**
     *the test verify that, once created, the card is available and can be activated
     * Is expected a true result
     */
    @Test
    public void testIsAvailable(){
        ProfessorsCharacterCard card = (ProfessorsCharacterCard)generalCard;
        boolean available = card.isAvailable();
        assertTrue(available);
    }

    /**
     * tests that the ProfessorCharacterCard doesn't modify the calculation of the influence
     * Is expected an array of zeros
     */

    @ParameterizedTest
    @MethodSource("effectInfluenceArguments")
    public void testEffectInfluence(Map<Clan, Integer> addingStudents){
        Island island = new Island();

        island.addStudents(addingStudents);
        ProfessorsCharacterCard card = (ProfessorsCharacterCard)generalCard;
        int[] effect = card.effectInfluence(players, players[0], island, null);
        for(int i=0; i<Clan.values().length; i++){
            assertEquals(0, effect[i]);
        }
    }

    private static Stream<Arguments> effectInfluenceArguments(){
        Map<Clan, Integer> addingStudents1 = TestUtil.studentMapCreator(1, 2, 3, 4, 5);
        Map<Clan, Integer> addingStudents2 = TestUtil.studentMapCreator(2, 3, 4, 5, 6);

        return Stream.of(
                Arguments.of(addingStudents1),
                Arguments.of(addingStudents2)
        );
    }


    /**
     * tests that the ProfessorCharacterCard doesn't add any Steps on the movement of Mother Nature
     * Is expeted a zero result
     */

    @Test
    public void testEffectStepsMotherNature(){
        ProfessorsCharacterCard card = (ProfessorsCharacterCard)generalCard;
        int steps = card.effectStepsMotherNature();
        assertEquals(0, steps);
    }
















}