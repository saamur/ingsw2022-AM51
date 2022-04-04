package it.polimi.ingsw;

import it.polimi.ingsw.charactercards.CharacterCard;
import it.polimi.ingsw.charactercards.CharacterCardCreator;
import it.polimi.ingsw.charactercards.CharacterID;
import it.polimi.ingsw.islands.IslandManager;
import it.polimi.ingsw.player.Card;
import it.polimi.ingsw.player.Player;
import it.polimi.ingsw.player.TowerColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class CharacterTurnTest {

    protected Turn turn;
    protected Player [] players;
    protected IslandManager islandManager;
    protected Bag bag;
    protected CharacterCard[] characterCards;

    @BeforeEach
    public void initialization() {
        players = new Player[3];
        bag = new Bag();
        players[0] = new Player("Giulia", TowerColor.BLACK, 3, bag);
        players[1] = new Player("Fede", TowerColor.GRAY, 3, bag);
        players[2] = new Player("Samu", TowerColor.WHITE, 3, bag);

        players[1].chooseCard(Card.CHEETAH);

        turn = new Turn(players[1], 3);

        islandManager = new IslandManager();

        CharacterCardCreator characterCardCreator = new CharacterCardCreator();
        characterCards = new CharacterCard[CharacterID.values().length];
        for (int i = 0; i < characterCards.length; i++)
            characterCards[i] = characterCardCreator.createCharacterCard(CharacterID.values()[i], bag);
    }

    protected void professorsInitialization() {

        int[][] students = { {0, 2, 5, 1, 0},
                             {0, 2, 7, 1, 2},
                             {0, 1, 5, 8, 2} };

        boolean[][] initialProfessors = { {false, true, false, false, false},
                                          {false, false, true, true, false},
                                          {false, false, false, false, true} };

        for (int i = 0; i < players.length; i++)
            players[i].getChamber().addStudents(students[i]);

        for (int i = 0; i < players.length; i++)
            for (int j = 0; j < Clan.values().length; j++)
                players[i].getChamber().setProfessor(Clan.values()[j], initialProfessors[i][j]);

    }

    @Test
    public void updateProfessorsTest() {

        professorsInitialization();

        boolean[][] expectedProfessors = { {false, true, false, false, false},
                                           {false, false, true, false, false},
                                           {false, false, false, true, true} };

        turn.updateProfessors(players);

        boolean[][] professors = new boolean[players.length][Clan.values().length];

        for (int i = 0; i < players.length; i++)
            professors[i] = players[i].getChamber().getProfessors();

        for (int i = 0; i < players.length; i++)
            assertArrayEquals(expectedProfessors[i], professors[i]);

    }

    @Test
    public void getMaxStepsMotherNatureTest() {

        assertEquals(Card.CHEETAH.getMaxStepsMotherNature(), turn.getMaxStepsMotherNature());

    }

}
