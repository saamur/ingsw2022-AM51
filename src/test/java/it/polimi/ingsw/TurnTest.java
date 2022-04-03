package it.polimi.ingsw;

import it.polimi.ingsw.islands.Island;
import it.polimi.ingsw.islands.IslandManager;
import it.polimi.ingsw.player.Player;
import it.polimi.ingsw.player.TowerColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

        for (int i = 0; i < hallStudentsBefore.length; i++) {
            if (i != Clan.DRAGONS.ordinal())
                assertEquals (hallStudentsBefore[i], hallStudentsAfter[i]);
            else
                assertEquals (hallStudentsBefore[i] - 1, hallStudentsAfter[i]);
        }

        for (int i = 0; i < islandStudentsBefore.length; i++) {
            if (i != Clan.DRAGONS.ordinal())
                assertEquals (islandStudentsBefore[i], islandStudentsAfter[i]);
            else
                assertEquals (islandStudentsBefore[i] + 1, islandStudentsAfter[i]);
        }

    }

}
