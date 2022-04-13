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
        /*
        //FIXME come faccio con le mappe?
        int[][] students = { {0, 2, 5, 1, 0},
                             {0, 2, 7, 1, 2},
                             {0, 1, 5, 8, 2} };
        */
        /*
        Map<Clan, Integer> studentsPlayer0 = new EnumMap<>(Clan.class); //FIXME it is not elegant but it would be much longer using not using arrays
        for(int i=0; i<Clan.values().length; i++)
            studentsPlayer0.put(Clan.values()[i], students[0][i]);

        Map<Clan, Integer> studentsPlayer1 = new EnumMap<>(Clan.class);
        for(int i=0; i<Clan.values().length; i++)
            studentsPlayer0.put(Clan.values()[i], students[1][i]);

        Map<Clan, Integer> studentsPlayer2 = new EnumMap<>(Clan.class);
        for(int i=0; i<Clan.values().length; i++)
            studentsPlayer0.put(Clan.values()[i], students[2][i]);
        */

        Map<Clan, Player> initialProfessors = TestUtil.professorMapCreator(null, players[0], players[1], players[1], players[2]);
        /*
        boolean[][] initialProfessors = { {false, true, false, false, false},
                                          {false, false, true, true, false},
                                          {false, false, false, false, true} };

        Map<Clan, Boolean> professorsPlayer0 = new EnumMap<>(Clan.class); //FIXME it is not elegant but it would be much longer using not using arrays
        for(int i=0; i<Clan.values().length; i++)
            professorsPlayer0.put(Clan.values()[i], initialProfessors[0][i]);

        Map<Clan, Boolean> professorsPlayer1 = new EnumMap<>(Clan.class);
        for(int i=0; i<Clan.values().length; i++)
            professorsPlayer0.put(Clan.values()[i], initialProfessors[1][i]);

        Map<Clan, Boolean> professorsPlayer2 = new EnumMap<>(Clan.class);
        for(int i=0; i<Clan.values().length; i++)
            professorsPlayer0.put(Clan.values()[i], initialProfessors[2][i]);
        */
        for (int i = 0; i < players.length; i++)
            players[i].getChamber().addStudents(students.get(i));
        for (Player player : players)
            for (Clan c : Clan.values())
                player.getChamber().setProfessor(c, player == initialProfessors.get(c));
        /*
        for (int i = 0; i < players.length; i++)
            players[i].getChamber().addStudents(students[i]);//FIXME

        for (int i = 0; i < players.length; i++)
            for (int j = 0; j < Clan.values().length; j++)
                players[i].getChamber().setProfessor(Clan.values()[j], initialProfessors[i][j]);
        */
    }

    @Test
    public void updateProfessorsTest() {

        professorsInitialization();

        Map<Clan, Player> expectedProfessors = TestUtil.professorMapCreator(null, players[0], players[1], players[2], players[2]);
        /*
        boolean[][] expectedProfessors = { {false, true, false, false, false},
                                           {false, false, true, false, false},
                                           {false, false, false, true, true} };
        */

        turn.updateProfessors(players);

        for (Player player : players)
            for (Clan c : Clan.values())
                assertEquals(player == expectedProfessors.get(c), player.getChamber().hasProfessor(c));
        /*
        boolean[][] professors = new boolean[players.length][Clan.values().length];

        for (int i = 0; i < players.length; i++)
            professors[i] = players[i].getChamber().getProfessors();

        for (int i = 0; i < players.length; i++)
            assertArrayEquals(expectedProfessors[i], professors[i]);
        */
    }

    @Test
    public void getMaxStepsMotherNatureTest() {

        assertEquals(Card.CHEETAH.getMaxStepsMotherNature(), turn.getMaxStepsMotherNature());

    }

    //TODO non trovo un caso generico in cui senza torri vince uno ma senza colore vince un altro
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

        /*if (islandManager.getIsland(3).getStudents()[Clan.FAIRIES.ordinal()] == 1) {
            //If Samu already has a student (FAIRIES) on the Island, it will not add any more
            studentsToBeAdded[Clan.FAIRIES.ordinal()] = 0;
        }
        if(islandManager.getIsland(3).getStudents()[Clan.TOADS.ordinal()] == 1 || islandManager.getIsland(3).getStudents()[Clan.DRAGONS.ordinal()] == 1){
            //if Fede already has one of her students (TOADS or DRAGONS) on the island, it will only add one more
            studentsToBeAdded[Clan.TOADS.ordinal()] = 1;
        }
        if(islandManager.getIsland(3).getStudents()[Clan.UNICORNS.ordinal()] == 1){
            //If there is one Giulia's students (unicorns) already on the Island it will not add any more.
            studentsToBeAdded[Clan.TOADS.ordinal()] = 0;
        }*/
        islandManager.getIsland(islandIndex).removeStudents(islandManager.getIsland(islandIndex).getStudents());
        islandManager.getIsland(islandIndex).addStudents(studentsToBeAdded);
        islandManager.conquerIsland(players[2], islandManager.getIsland(islandIndex));

        //NORMAL INFLUENCE: {1, 2, 1+1(tower)}
    }
}
