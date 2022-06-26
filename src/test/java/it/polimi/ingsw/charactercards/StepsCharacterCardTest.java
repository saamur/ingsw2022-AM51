package it.polimi.ingsw.charactercards;

import it.polimi.ingsw.model.Bag;
import it.polimi.ingsw.model.charactercards.CharacterCardCreator;
import it.polimi.ingsw.model.charactercards.CharacterID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StepsCharacterCardTest extends CharacterCardTest {

    @BeforeEach
    public void initialization(){
        bag = new Bag();
        characterCard = CharacterCardCreator.createCharacterCard(CharacterID.POSTMAN, bag);
    }


    @Override
    @Test
    public void stepsTest(){
        int stepsToBeAdded = characterCard.effectStepsMotherNature();
        assertEquals(2, stepsToBeAdded);
    }
}
