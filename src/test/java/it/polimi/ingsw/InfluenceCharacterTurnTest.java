package it.polimi.ingsw;

import it.polimi.ingsw.charactercards.CharacterCard;
import it.polimi.ingsw.charactercards.CharacterID;
import it.polimi.ingsw.charactercards.InfluenceCharacterCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class InfluenceCharacterTurnTest extends CharacterTurnTest{
    List<CharacterCard> influenceCards = new ArrayList<>();


    @Override
    @BeforeEach
    public void initialization(){
        super.initialization();
        influenceCards.add(characterCards[CharacterID.HERALD.ordinal()]);
        influenceCards.add(characterCards[CharacterID.CENTAUR.ordinal()]);
        influenceCards.add(characterCards[CharacterID.KNIGHT.ordinal()]);
        influenceCards.add(characterCards[CharacterID.MUSHROOMPICKER.ordinal()]);
    }

    @Override
    public void updateInfluenceTest(){
        influenceTestHerald();
        influenceTestCentaur();
        influenceTestKnight();
        influenceTestMushroomPicker();
    }

    @Test
    public void influenceTestHerald(){
        islandInitialization();
        turn.setActivatedCharacterCard(characterCards[CharacterID.HERALD.ordinal()]);
        super.updateInfluenceTest();

    }

    @Test
    public void influenceTestCentaur(){
        islandInitialization();
        turn.setActivatedCharacterCard(characterCards[CharacterID.CENTAUR.ordinal()]);
        //Samu has conquered the island and has same influence but with 1 tower present
        turn.updateInfluence(islandManager, islandManager.getIsland(3), players);
        assertEquals("Fede", islandManager.getIsland(3).getControllingPlayer().getNickname());

    }

    @Test
    public void influenceTestKnight(){
        islandInitialization();
        turn.setActivatedCharacterCard(characterCards[CharacterID.KNIGHT.ordinal()]);
        islandManager.getIsland(3).addStudent(Clan.FAIRIES);
        turn.updateInfluence(islandManager, islandManager.getIsland(3), players);
        assertEquals("Fede", islandManager.getIsland(3).getControllingPlayer().getNickname()); //Fede is currPlayer
    }

    @Test
    public void influenceTestMushroomPicker(){
        islandInitialization();
        turn.setActivatedCharacterCard(characterCards[CharacterID.MUSHROOMPICKER.ordinal()]);
        turn.setCharacterClan(Clan.TOADS);
        turn.updateInfluence(islandManager, islandManager.getIsland(3), players);
        assertEquals("Samu", islandManager.getIsland(3).getControllingPlayer().getNickname());
    }
}
