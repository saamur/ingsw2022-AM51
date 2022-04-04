package it.polimi.ingsw;

import it.polimi.ingsw.CharacterTurnTest;
import it.polimi.ingsw.Clan;
import it.polimi.ingsw.charactercards.CharacterID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class FarmerTurnTest extends CharacterTurnTest {

    @Override
    @BeforeEach
    public void initialization() {
        super.initialization();
        turn.setActivatedCharacterCard(characterCards[CharacterID.FARMER.ordinal()]);
    }

    @Override
    @Test
    public void updateProfessorsTest() {

        professorsInitialization();

        boolean[][] expectedProfessors = { {false, false, false, false, false},
                                           {true, true, true, false, true},
                                           {false, false, false, true, false} };

        turn.updateProfessors(players);

        boolean[][] professors = new boolean[players.length][Clan.values().length];

        for (int i = 0; i < players.length; i++)
            professors[i] = players[i].getChamber().getProfessors();

        for (int i = 0; i < players.length; i++)
            assertArrayEquals(expectedProfessors[i], professors[i]);

    }

}
