package it.polimi.ingsw;

import it.polimi.ingsw.islands.Island;
import it.polimi.ingsw.islands.IslandManager;
import it.polimi.ingsw.player.Player;
import it.polimi.ingsw.player.TowerColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class TurnTest {

    Turn turn;
    Player [] players;
    IslandManager islandManager;
    Bag bag;

    @BeforeEach
    public void initialization() {
        players = new Player[3];
        bag = new Bag();
        players[0] = new Player("Giulia", TowerColor.BLACK, 3, bag);
        players[1] = new Player("Fede", TowerColor.GRAY, 3, bag);
        players[2] = new Player("Samu", TowerColor.WHITE, 3, bag);

        turn = new Turn(players[0], 3);

        islandManager = new IslandManager();
    }

    /**
     * test checks if the student is correctly removed from the player's hall and added to the given island
     */
    @Test
    public void moveStudentToIslandTest () {

        Island island = islandManager.getIsland(3);
        players[0].getHall().addStudent(Clan.DRAGONS);
        int[] hallStudentsBefore = players[0].getHall().getStudents();
        int[] islandStudentsBefore = island.getStudents();

        boolean ok = turn.moveStudentToIsland(Clan.DRAGONS, island);

        assertTrue(ok);

        int[] hallStudentsAfter = players[0].getHall().getStudents();
        int[] islandStudentsAfter = island.getStudents();

        hallStudentsAfter[Clan.DRAGONS.ordinal()]++;
        islandStudentsAfter[Clan.DRAGONS.ordinal()]--;

        assertArrayEquals(hallStudentsBefore, hallStudentsAfter);
        assertArrayEquals(islandStudentsBefore, islandStudentsAfter);

    }

    /**
     * test checks if the student is correctly removed from the player's hall and added to the player's chamber
     */
    @Test
    public void moveStudentToChamberTest () {

        players[0].getHall().addStudent(Clan.DRAGONS);
        int[] hallStudentsBefore = players[0].getHall().getStudents();

        boolean ok = turn.moveStudentToChamber(Clan.DRAGONS, players);

        assertTrue(ok);

        int[] hallStudentsAfter = players[0].getHall().getStudents();
        int[] chamberStudentAfter = players[0].getChamber().getStudents();
        int[] chamberStudentExpected = {0, 0, 0, 1, 0};

        hallStudentsAfter[Clan.DRAGONS.ordinal()]++;

        assertArrayEquals(hallStudentsBefore, hallStudentsAfter);
        assertArrayEquals(chamberStudentExpected, chamberStudentAfter);

    }

    /**
     * method updateProfessorsTest tests if the professors are updated as expected
     * after adding students to the players' chambers
     */
    @Test
    public void updateProfessorsTest() {
        int[][] students = { {0, 2, 5, 2, 0},
                             {0, 2, 7, 1, 1},
                             {0, 1, 5, 8, 2} };
        boolean[][] expectedProfessors = { {false, false, false, false, false},
                                           {false, false, true, false, false},
                                           {false, false, false, true, true} };

        for (int i = 0; i < players.length; i++)
            players[i].getChamber().addStudents(students[i]);

        turn.updateProfessors(players);

        boolean[][] professors = new boolean[3][5];

        for (int i = 0; i < players.length; i++)
            professors[i] = players[i].getChamber().getProfessors();

        //System.out.println(Arrays.deepToString(professors));

        for (int i = 0; i < players.length; i++)
            assertArrayEquals(expectedProfessors[i], professors[i]);

    }

}
