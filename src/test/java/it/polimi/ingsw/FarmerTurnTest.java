package it.polimi.ingsw;

import it.polimi.ingsw.CharacterTurnTest;
import it.polimi.ingsw.Clan;
import it.polimi.ingsw.charactercards.CharacterID;
import it.polimi.ingsw.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
