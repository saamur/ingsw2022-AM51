package it.polimi.ingsw;

import it.polimi.ingsw.model.Clan;
import it.polimi.ingsw.model.charactercards.CharacterID;
import it.polimi.ingsw.model.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * FarmerTurnTest tests the methods of the class Turn when the FARMER CharacterCard has been activated
 */
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
        Map<Clan, Player> expectedProfessors = TestUtil.professorMapCreator(players[1], players[1], players[1], players[2], players[1]);

        turn.updateProfessors(players);

        for (Player player : players)
            for (Clan c : Clan.values())
                assertEquals(player == expectedProfessors.get(c), player.getChamber().hasProfessor(c));

    }

}
