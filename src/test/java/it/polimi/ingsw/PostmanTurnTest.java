package it.polimi.ingsw;

import it.polimi.ingsw.charactercards.CharacterID;
import it.polimi.ingsw.player.Card;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PostmanTurnTest extends CharacterTurnTest{

    @Override
    @BeforeEach
    public void initialization() {
        super.initialization();
        turn.setActivatedCharacterCard(characterCards[CharacterID.POSTMAN.ordinal()]);
    }

    @Override
    @Test
    public void getMaxStepsMotherNatureTest() {

        assertEquals(Card.CHEETAH.getMaxStepsMotherNature() + 2, turn.getMaxStepsMotherNature());

    }

}
