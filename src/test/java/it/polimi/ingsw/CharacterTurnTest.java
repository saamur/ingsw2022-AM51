package it.polimi.ingsw;

import it.polimi.ingsw.model.Bag;
import it.polimi.ingsw.model.Clan;
import it.polimi.ingsw.model.Turn;
import it.polimi.ingsw.model.charactercards.CharacterCard;
import it.polimi.ingsw.model.charactercards.CharacterCardCreator;
import it.polimi.ingsw.model.charactercards.CharacterID;
import it.polimi.ingsw.model.islands.IslandManager;
import it.polimi.ingsw.model.player.Card;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.TowerColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class CharacterTurnTest tests the method of the Turn class when expert mode is enabled
 */
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

        characterCards = new CharacterCard[CharacterID.values().length];
        for (int i = 0; i < characterCards.length; i++)
            characterCards[i] = CharacterCardCreator.createCharacterCard(CharacterID.values()[i], bag);
    }

    /**
     * Sets the students of the players, and the initial professors of the players
     */
    protected void professorsInitialization() {

        List<Map<Clan, Integer>> students = new ArrayList<>();
        students.add(TestUtil.studentMapCreator(0, 2, 5, 1, 0));
        students.add(TestUtil.studentMapCreator(0, 2, 7, 1, 2));
        students.add(TestUtil.studentMapCreator(0, 1, 5, 8, 2));


        Map<Clan, Player> initialProfessors = TestUtil.professorMapCreator(null, players[0], players[1], players[1], players[2]);

        for (int i = 0; i < players.length; i++)
            players[i].getChamber().addStudents(students.get(i));
        for (Player player : players)
            for (Clan c : Clan.values())
                player.getChamber().setProfessor(c, player == initialProfessors.get(c));

    }

    /**
     * Method tests if the turn.updateProfessors(Player[]) method works as expected. If the FARMER Character is activated then the player that has activated it is expected to take control of the professors even when the number of students is even.
     * If the FARMER CharacterCard is not activated then normal rules apply.
     */
    @Test
    public void updateProfessorsTest() {

        professorsInitialization();

        Map<Clan, Player> expectedProfessors = TestUtil.professorMapCreator(null, players[0], players[1], players[2], players[2]);


        turn.updateProfessors(players);

        for (Player player : players)
            for (Clan c : Clan.values())
                assertEquals(player == expectedProfessors.get(c), player.getChamber().hasProfessor(c));

    }

    /**
     * Method tests if the turn.getMaxStepsMotherNature() method works as expected.
     * If the POSTMAN Character is activated then two additional steps are allowed to the player that has activated the card.
     * If the POSTMAN CharacterCard is not activated then normal rules apply.
     */
    @Test
    public void getMaxStepsMotherNatureTest() {

        assertEquals(Card.CHEETAH.getMaxStepsMotherNature(), turn.getMaxStepsMotherNature());

    }

    /**
     * Method tests if the turn.updateInfluence(IslandManager, Island, Player[]) works as expected.
     * If one of the CharacterCard that modifies the influence is activated then the method needs to work accordingly.
     * If those CharacterCards are not activated normal rules apply.
     */
    @Test
    public void updateInfluenceTest(){
        islandInitialization();
        islandManager.getIsland(3).addStudent(Clan.TOADS);
        turn.updateInfluence(islandManager, islandManager.getIsland(3), players); //dovrebbe prenderla Fede
        assertEquals("Fede", islandManager.getIsland(3).getControllingPlayer().getNickname()); //nelle classi figlie piuttosto vengono aggiunt studenti
    }

    /**
     * Method sets the islands
     */
    public void islandInitialization(){
        professorsInitialization();
        int islandIndex = 3;
        Map<Clan, Integer> studentsToBeAdded = TestUtil.studentMapCreator(0, 1, 2, 0, 1);


        islandManager.getIsland(islandIndex).removeStudents(islandManager.getIsland(islandIndex).getStudents());
        islandManager.getIsland(islandIndex).addStudents(studentsToBeAdded);
        islandManager.conquerIsland(players[2], islandManager.getIsland(islandIndex));

        //NORMAL INFLUENCE: {1, 2, 1+1(tower)}
    }
}
