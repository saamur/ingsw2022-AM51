package it.polimi.ingsw;

import it.polimi.ingsw.model.Clan;
import it.polimi.ingsw.model.charactercards.CharacterCard;
import it.polimi.ingsw.model.charactercards.CharacterID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Class tests the Turn methods when an InfluenceCharacterCard has been activated
 */
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

        int islandIndex = 3;

        turn.setActivatedCharacterCard(characterCards[CharacterID.CENTAUR.ordinal()]);
        //Samu has conquered the island and has same influence but with 1 tower present
        turn.updateInfluence(islandManager, islandManager.getIsland(islandIndex), players);
        assertEquals("Fede", islandManager.getIsland(islandIndex).getControllingPlayer().getNickname());

    }

    @Test
    public void influenceTestKnight(){
        islandInitialization();

        int islandIndex = 3;

        turn.setActivatedCharacterCard(characterCards[CharacterID.KNIGHT.ordinal()]);
        islandManager.getIsland(islandIndex).addStudent(Clan.FAIRIES);
        turn.updateInfluence(islandManager, islandManager.getIsland(islandIndex), players);
        assertEquals("Fede", islandManager.getIsland(islandIndex).getControllingPlayer().getNickname()); //Fede is currPlayer
    }

    @Test
    public void influenceTestMushroomPicker(){
        islandInitialization();

        int islandIndex = 3;

        turn.setActivatedCharacterCard(characterCards[CharacterID.MUSHROOMPICKER.ordinal()]);
        turn.setCharacterClan(Clan.TOADS);
        turn.updateInfluence(islandManager, islandManager.getIsland(islandIndex), players);
        assertEquals("Samu", islandManager.getIsland(islandIndex).getControllingPlayer().getNickname());
    }
}
