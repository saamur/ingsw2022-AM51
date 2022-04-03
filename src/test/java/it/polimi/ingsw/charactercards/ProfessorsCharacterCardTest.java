package it.polimi.ingsw.charactercards;

import it.polimi.ingsw.Bag;
import it.polimi.ingsw.Clan;
import it.polimi.ingsw.Turn;
import it.polimi.ingsw.islands.Island;
import it.polimi.ingsw.player.Player;
import it.polimi.ingsw.player.TowerColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ProfessorsCharacterCardTest {
    Player[] players = new Player[3];
    Bag bag;
    CharacterCardCreator creator = new CharacterCardCreator();
    CharacterCard generalCard;

    @BeforeEach
    public  void initialization(){
        bag = new Bag();
        players[0] = new Player("Fede", TowerColor.BLACK, 3, bag);
        players[1] = new Player("Giulia", TowerColor.WHITE, 3, bag);
        players[2] = new Player("Samu", TowerColor.GRAY, 3, bag);


        generalCard = creator.createCharacterCard(CharacterID.FARMER, bag);
        int[] addingStudentsFirstPlayer = {1, 8, 2, 0, 0};
        int[] addingStudentsSecondPlayer = {2, 8, 3, 4, 0};
        int[] addingStudentsThirdPlayer = {3, 0, 0, 5, 0};
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
        Turn turn = new Turn(players[0], 3);
        ProfessorsCharacterCard card = (ProfessorsCharacterCard)generalCard;
        turn.setActivatedCharacterCard(card);
        boolean effect = card.applyInitialEffect(turn, players);
        boolean[] hasProfessors = players[0].getChamber().getProfessors();
        assertTrue(effect);
        boolean[] expectedHasProfessors = {false, true, false, false, true};
        for(int i=0; i<Clan.values().length; i++){
            assertEquals(expectedHasProfessors[i], hasProfessors[i]);
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

    @Test
    public void testEffectInfluence(){
        Island island = new Island();
        int[] addingStudents = {1, 2, 3, 4, 5};
        island.addStudents(addingStudents);
        ProfessorsCharacterCard card = (ProfessorsCharacterCard)generalCard;
        int[] effect = card.effectInfluence(players, players[0], island, null);
        for(int i=0; i<Clan.values().length; i++){
            assertEquals(0, effect[i]);
        }
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