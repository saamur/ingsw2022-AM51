package it.polimi.ingsw.charactercards.idea;

import it.polimi.ingsw.Bag;
import it.polimi.ingsw.charactercards.CharacterID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StepsCharacterIDEA extends CharacterCardTest{

    @BeforeEach
    public void initialization(){
        bag = new Bag();
        characterCard = characterCardCreator.createCharacterCard(CharacterID.POSTMAN, bag);
    }


    @Override
    @Test
    public void stepsTest(){
        int stepsToBeAdded = characterCard.effectStepsMotherNature();
        assertEquals(2, stepsToBeAdded);
    }
}
