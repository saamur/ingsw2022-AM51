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

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    public void updateProfessorsTest() {

        professorsInitialization();

        Map<Clan, Player> expectedProfessors = TestUtil.professorMapCreator(null, players[0], players[1], players[2], players[2]);


        turn.updateProfessors(players);

        for (Player player : players)
            for (Clan c : Clan.values())
                assertEquals(player == expectedProfessors.get(c), player.getChamber().hasProfessor(c));

    }

    @Test
    public void getMaxStepsMotherNatureTest() {

        assertEquals(Card.CHEETAH.getMaxStepsMotherNature(), turn.getMaxStepsMotherNature());

    }


    @Test
    public void updateInfluenceTest(){
        islandInitialization();
        islandManager.getIsland(3).addStudent(Clan.TOADS);
        turn.updateInfluence(islandManager, islandManager.getIsland(3), players); //dovrebbe prenderla Fede
        assertEquals("Fede", islandManager.getIsland(3).getControllingPlayer().getNickname()); //nelle classi figlie piuttosto vengono aggiunt studenti
    }

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
