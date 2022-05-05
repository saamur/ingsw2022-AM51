package it.polimi.ingsw;

import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.Clan;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.TurnState;
import it.polimi.ingsw.model.islands.Island;
import it.polimi.ingsw.model.islands.IslandManager;
import it.polimi.ingsw.model.player.Card;
import it.polimi.ingsw.model.player.Player;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
Game game;

    public void setNotExpertGame() {
        game = new Game(2, "Giulia", false);

}

    public void setNotExpertGameTwoPlayers(){
        game = new Game(2, "Giulia", false);
        try {
            game.addPlayer("Samu");
        } catch (WrongGamePhaseException | NicknameNotAvailableException e) {
            e.printStackTrace();
        }

    }

    public void setNotExpertGameThreePlayers(){
        game = new Game(3, "Giulia", false);
        try {
            game.addPlayer("Samu");
        } catch (WrongGamePhaseException | NicknameNotAvailableException e) {
            e.printStackTrace();
        }
        try {
            game.addPlayer("Fede");
        } catch (WrongGamePhaseException | NicknameNotAvailableException e) {
            e.printStackTrace();
        }

    }

    /**
     * test check if the addPlayer method add the player in the game
     * It is expected not to throw exceptions
     */

    @Test
    public void testAddPlayer() {
        setNotExpertGame();
        assertDoesNotThrow(() -> game.addPlayer("Samu"));
        String nicknameAddedPlayer = game.getPlayers()[1].getNickname();
        assertEquals("Samu", nicknameAddedPlayer);
    }

    /**
     * the test check that the addPlayer method doesn't add a third player into a two players game
     * It is expected to throw a WrongGamePhaseException
     */

    @Test
    public void testAddTooManyPlayers() {
        setNotExpertGameTwoPlayers();
        assertThrows(WrongGamePhaseException.class, () -> game.addPlayer("Fede"));
    }

    /**
     * the test check that the start method choose a player that is actually added in the game
     * Is expected that, in a game of two players, the indexNextFirstPlayer is between 0 and 1
     */

    @Test
    public void testInitialGameMethods() {
        setNotExpertGameTwoPlayers();
        int indexNextFirstPlayer = game.getIndexNextFirstPlayer();
        assertTrue(indexNextFirstPlayer == 0 || indexNextFirstPlayer == 1);
        int indexCurrFirstPlayer = game.getIndexCurrFirstPlayer();
        int indexCurrPlayer = game.getIndexCurrPlayer();
        assertEquals(indexNextFirstPlayer, indexCurrFirstPlayer);
        assertEquals(indexNextFirstPlayer, indexCurrPlayer);
    }

    /**
     * the test checks if the chosenCard method correctly throws a WrongPlayerException if and only if
     * the Player who choose the card is not the currentPlayer
     * The method is expected to throw a WrongPlayerException if the Player, who is not the Current one, chose the card
     * The method is expected not to throw any exception if the current Player chose the card, and the increase of the indexCurrPlayer
     */

    @Test
    public void testChosenCard() {
        setNotExpertGameTwoPlayers();
        if (game.getIndexCurrFirstPlayer() == 0) {
            assertThrows(WrongPlayerException.class, () -> game.chosenCard("Samu", Card.CAT));
            GameState invariantGameState = game.getGameState();
            assertEquals(GameState.PLANNING, invariantGameState);
            assertDoesNotThrow(() -> game.chosenCard("Giulia", Card.CHEETAH));
        } else {
            assertThrows(WrongPlayerException.class, () -> game.chosenCard("Giulia", Card.CAT));
            GameState invariantGameState = game.getGameState();
            assertEquals(GameState.PLANNING, invariantGameState);
            assertDoesNotThrow(() -> game.chosenCard("Samu", Card.CHEETAH));
        }
    }

    /**
     * the test check if, with a full deck, the player, who is the currPlayer, can't choose the same card played by
     * the other one
     * On the second time the method is expected to throw a NotValidMoveException
     */

    @Test
    public void testChooseTheSameCard() {
        setNotExpertGameTwoPlayers();
        if (game.getIndexCurrPlayer() == 0) {
            assertDoesNotThrow(() -> game.chosenCard("Giulia", Card.DOG));
            assertThrows(NotValidMoveException.class, () -> game.chosenCard("Samu", Card.DOG));
        } else {
            assertDoesNotThrow(() -> game.chosenCard("Samu", Card.DOG));
            assertThrows(NotValidMoveException.class, () -> game.chosenCard("Giulia", Card.DOG));
        }
    }


    /**
     * the test checks that the player cannot choose a card in a non PLANNING phase of the game
     * The method chosenCard is expected to throw a WrongGamePhaseException if gameState is not PLANNING
     */

    @Test
    public void testNotCorrectPhaseToChooseCard() {
        setNotExpertGameTwoPlayers();
        if (game.getIndexCurrPlayer() == 0) {
            assertDoesNotThrow(() -> game.chosenCard("Giulia", Card.CHEETAH));
            assertDoesNotThrow(() -> game.chosenCard("Samu", Card.DOG));
            assertNotSame(game.getGameState(), GameState.PLANNING);
            assertThrows(WrongGamePhaseException.class, () -> game.chosenCard("Giulia", Card.DOG));
        } else {
            assertDoesNotThrow(() -> game.chosenCard("Samu", Card.CHEETAH));
            assertDoesNotThrow(() -> game.chosenCard("Giulia", Card.DOG));
            assertNotSame(game.getGameState(), GameState.PLANNING);
            assertThrows(WrongGamePhaseException.class, () -> game.chosenCard("Samu", Card.DOG));
        }
    }

    /**
     * the test checks that the Player cannot choose a Card that he has already chosen
     * The method chosenCard is expected to throw a NotValidMoveException
     * @param card is the card of the deck on which we are going to test the method
     */


    @ParameterizedTest
    @EnumSource(Card.class)
    public void testAlreadyChosenCard(Card card) {
        setNotExpertGameTwoPlayers();
        game.getPlayers()[0].getDeck().removeCard(card);
        game.getPlayers()[1].getDeck().removeCard(card);
        if (game.getIndexCurrPlayer() == 0) {
            assertThrows(NotValidMoveException.class, () -> game.chosenCard("Giulia", card));

        } else {
            assertThrows(NotValidMoveException.class, () -> game.chosenCard("Samu", card));
        }
    }



    /**
     * the test verifies that the order of the action phase is created in the right way, that is, starting from who has
     * chosen a card with a lower priority
     */

    @Test
    public void testOrderActionPhase() {
        setNotExpertGameTwoPlayers();
        Player[] playersOrder;
        if (game.getIndexCurrPlayer() == 0) {
            assertDoesNotThrow(() -> game.chosenCard("Giulia", Card.CHEETAH));
            assertDoesNotThrow(() -> game.chosenCard("Samu", Card.CAT));
        } else {
            assertDoesNotThrow(() -> game.chosenCard("Samu", Card.CAT));
            assertDoesNotThrow(() -> game.chosenCard("Giulia", Card.CHEETAH));
        }
        playersOrder = game.getPlayersOrderActionPhase();

        assertEquals("Giulia", playersOrder[0].getNickname());
        assertEquals("Samu", playersOrder[1].getNickname());
    }

    public void setGameAndRemoveCards() {
        setNotExpertGameTwoPlayers();
        List<Card> toRemove = new ArrayList<>(Arrays.asList(Card.values()));
        toRemove.remove(Card.TURTLE);

        for (Card c : toRemove)
            game.getPlayers()[0].getDeck().removeCard(c);

        for (Card c : toRemove)
            game.getPlayers()[1].getDeck().removeCard(c);
    }

    /**
     * the test checks that it is possible to choose the same card of the other Player if it is the only one in the Deck
     * the test checks that the Action order, in this particular case, is created taking into account who
     * first chose the card
     * The method chosenCard is expected not to throw any exception, and the playersOrder is expected to have
     * in position 0 the first player who chose the card
     */
    @Test
    public void testOnlyOneLeftChooseCard() {
        setGameAndRemoveCards();
        if (game.getIndexCurrPlayer() == 0) {
            assertDoesNotThrow(() -> game.chosenCard("Giulia", Card.TURTLE));
            assertDoesNotThrow(() -> game.chosenCard("Samu", Card.TURTLE));
            Player[] playersOrder = game.getPlayersOrderActionPhase();
            assertEquals("Giulia", playersOrder[0].getNickname());
            assertEquals("Samu", playersOrder[1].getNickname());
        } else {
            assertDoesNotThrow(() -> game.chosenCard("Samu", Card.TURTLE));
            assertDoesNotThrow(() -> game.chosenCard("Giulia", Card.TURTLE));
            Player[] playersOrder = game.getPlayersOrderActionPhase();
            assertEquals("Samu", playersOrder[0].getNickname());
            assertEquals("Giulia", playersOrder[1].getNickname());
        }
    }


    public void setPlanning() {
        setNotExpertGameTwoPlayers();
        if (game.getIndexCurrPlayer() == 0) {
            assertDoesNotThrow(() -> game.chosenCard("Giulia", Card.CHEETAH));
            assertDoesNotThrow(() -> game.chosenCard("Samu", Card.CAT));
        } else {
            assertDoesNotThrow(() -> game.chosenCard("Samu", Card.CAT));
            assertDoesNotThrow(() -> game.chosenCard("Giulia", Card.CHEETAH));
        }

    }

    /**
     * the test checks that the moveStudentToChamber removes a student from the Hall and adds a student
     * in the Chamber of the Current Player
     * @param clan it is the clan of belonging of the students on which we are going to test the effect
     */

    @ParameterizedTest
    @EnumSource(Clan.class)
    public void testMoveStudentToChamber(Clan clan) {
        setPlanning();
        game.getPlayers()[0].getHall().addStudent(clan);
        Map<Clan, Integer> initialStudentsHall = game.getPlayers()[0].getHall().getStudents();
        Map<Clan, Integer> initialStudentsChamber = game.getPlayers()[0].getChamber().getStudents();

        assertDoesNotThrow(() -> game.moveStudentToChamber("Giulia", clan));

        Map<Clan, Integer> finalStudentsHall = game.getPlayers()[0].getHall().getStudents();
        Map<Clan, Integer> finalStudentsChamber = game.getPlayers()[0].getChamber().getStudents();

        assertEquals(initialStudentsChamber.get(clan) + 1, finalStudentsChamber.get(clan));
        assertEquals(initialStudentsHall.get(clan) - 1, finalStudentsHall.get(clan));
    }

    /**
     * the test check that a non-current player can't move a student to the Chamber
     * method moveStudentToChamber is expected to throw a WrongPlayerException
     * @param clan it is the clan of belonging of the students on which we are going to test the effect
     */


    @ParameterizedTest
    @EnumSource(Clan.class)
    public void testWrongPlayerMoveStudentToChamber(Clan clan) {
        setPlanning();
        game.getPlayers()[1].getHall().addStudent(clan);
        Map<Clan, Integer> initialStudentsHall = game.getPlayers()[1].getHall().getStudents();
        Map<Clan, Integer> initialStudentsChamber = game.getPlayers()[1].getChamber().getStudents();

        assertThrows(WrongPlayerException.class, () -> game.moveStudentToChamber("Samu", clan));

        Map<Clan, Integer> finalStudentsHall = game.getPlayers()[1].getHall().getStudents();
        Map<Clan, Integer> finalStudentsChamber = game.getPlayers()[1].getChamber().getStudents();

        assertEquals(initialStudentsChamber.get(clan), finalStudentsChamber.get(clan));
        assertEquals(initialStudentsHall.get(clan), finalStudentsHall.get(clan));
    }

    /**
     * tests that the current player can't move a student to Chamber if the student is not in the hall
     * method moveStudentToChamber is expected to throw a NotValidMoveException
     * @param clan it is the clan of belonging of the students on which we are going to test the method
     */

    @ParameterizedTest
    @EnumSource(Clan.class)
    public void testNotStudentsToMoveToChamber(Clan clan) {
        setPlanning();
        int numOfClanStudents = game.getPlayers()[0].getHall().getStudents().get(clan);
        for (int i = 0; i < numOfClanStudents; i++) {
            game.getPlayers()[0].getHall().removeStudent(clan);
        }
        assertThrows(NotValidMoveException.class, () -> game.moveStudentToChamber("Giulia", clan));

    }

    /**
     * the test check that the method moveStudentToIsland move a chosen student from the hall to an Island
     * @param clan it is the clan of belonging of the students on which we are going to test the method
     */
    @ParameterizedTest
    @EnumSource(Clan.class)
    public void testMoveStudentToIsland(Clan clan) {
        setPlanning();
        game.getPlayers()[0].getHall().addStudent(clan);
        int initialNumOfClanStudents = game.getIslandManager().getIsland(1).getStudents().get(clan);
        int initialClanStudentsInTheHall = game.getPlayers()[0].getHall().getStudents().get(clan);
        assertDoesNotThrow(() -> game.moveStudentToIsland("Giulia", clan, 1));
        int finalNumOfPixies = game.getIslandManager().getIsland(1).getStudents().get(clan);
        int finalPixiesInTheHall = game.getPlayers()[0].getHall().getStudents().get(clan);
        assertEquals(initialNumOfClanStudents + 1, finalNumOfPixies);
        assertEquals(initialClanStudentsInTheHall - 1, finalPixiesInTheHall);
    }

    /**
     * tests that a player, who is not the current one, can't move a student to an Island
     * The method moveStudentToIsland is expected to throw a WrongPlayerException
     * @param clan it is the clan of belonging of the students on which we are going to test the method
     */

    @ParameterizedTest
    @EnumSource(Clan.class)
    public void testWrongPlayerMoveStudentToIsland(Clan clan) {
        setPlanning();
        game.getPlayers()[1].getHall().addStudent(clan);
        int initialNumOfClanStudents = game.getIslandManager().getIsland(1).getStudents().get(clan);
        int initialClanStudentsInTheHall = game.getPlayers()[1].getHall().getStudents().get(clan);
        assertThrows(WrongPlayerException.class, () -> game.moveStudentToIsland("Samu", clan, 1));
        int finalNumOfClanStudents = game.getIslandManager().getIsland(1).getStudents().get(clan);
        int finalClanStudentsInTheHall = game.getPlayers()[1].getHall().getStudents().get(clan);
        assertEquals(initialNumOfClanStudents, finalNumOfClanStudents);
        assertEquals(initialClanStudentsInTheHall, finalClanStudentsInTheHall);
    }

    /**
     * tests that the current player can't move a student to an island if the student is not in the hall
     * The method moveStudentToIsland is expected to throw a NotValidMoveException
     * @param clan it is the clan of belonging of the students on which we are going to test the method
     */

    @ParameterizedTest
    @EnumSource(Clan.class)
    public void testNotStudentsToMoveToIsland(Clan clan) {
        setPlanning();
        int numOfClanStudents = game.getPlayers()[0].getHall().getStudents().get(clan);
        for (int i = 0; i < numOfClanStudents; i++) {
            game.getPlayers()[0].getHall().removeStudent(clan);
        }
        assertThrows(NotValidMoveException.class, () -> game.moveStudentToIsland("Giulia", clan, 1));
    }

    /**
     * tests if with the moveMotherNature method the current player moves Mother Nature on a chosen Island
     * @param clan it is the clan of belonging of the students on which we are going to test the method
     */

    @ParameterizedTest
    @EnumSource(Clan.class)
    public void testMoveMotherNature(Clan clan) {
        setPlanning();
        Map<Clan, Integer> addingStudents = TestUtil.studentMapCreator(0, 0, 0, 0, 0);
        addingStudents.put(clan, 3);
        game.getPlayers()[0].getHall().addStudents(addingStudents);
        assertDoesNotThrow(() -> game.moveStudentToIsland("Giulia", clan, 1));
        assertDoesNotThrow(() -> game.moveStudentToChamber("Giulia", clan));
        assertDoesNotThrow(() -> game.moveStudentToChamber("Giulia", clan));
        TurnState turnState = game.getTurn().getTurnState();
        assertEquals(TurnState.MOTHER_MOVING, turnState);
        Island motherNaturePosition = game.getIslandManager().getMotherNaturePosition();
        int index = game.getIslandManager().getIslands().indexOf(motherNaturePosition);
        assertDoesNotThrow(() -> game.moveMotherNature("Giulia", (index + 1) % (game.getIslandManager().getIslands().size())));
        Island NewMotherNaturePosition = game.getIslandManager().getMotherNaturePosition();
        int finalIndex = game.getIslandManager().getIslands().indexOf(NewMotherNaturePosition);
        assertEquals((index + 1) % game.getIslandManager().getIslands().size(), finalIndex);
    }

    /**
     * the test checks that the currPlayer cannot move the Mother Nature more than the Max steps written on the card he
     * played
     * A NotValidMoveException is expected to be thrown
     * @param clan it is the clan of belonging of the students on which we are going to test the method
     */
    @ParameterizedTest
    @EnumSource(Clan.class)
    public void testMoveMotherNatureTooMuch(Clan clan) {
        setPlanning();
        Map<Clan, Integer> addingStudents = TestUtil.studentMapCreator(0, 0, 0, 0, 0);
        addingStudents.put(clan, 3);
        game.getPlayers()[0].getHall().addStudents(addingStudents);
        assertDoesNotThrow(() -> game.moveStudentToIsland("Giulia", clan, 1));
        assertDoesNotThrow(() -> game.moveStudentToChamber("Giulia", clan));
        assertDoesNotThrow(() -> game.moveStudentToChamber("Giulia", clan));
        Island motherNaturePosition = game.getIslandManager().getMotherNaturePosition();
        int index = game.getIslandManager().getIslands().indexOf(motherNaturePosition);
        assertThrows(NotValidMoveException.class, () -> game.moveMotherNature("Giulia", (index + 2) % (game.getIslandManager().getIslands().size())));
        int finalIndex = game.getIslandManager().getIslands().indexOf(motherNaturePosition);
        assertEquals(index, finalIndex);
    }


    /**
     * test checks if calculateWin method works as expected in normal condition
     */
    @Test
    public void calculateWinTest() {

        setNotExpertGameThreePlayers();
        Player[] players = game.getPlayers();
        IslandManager islandManager = game.getIslandManager();

        islandManager.conquerIsland(players[2], islandManager.getIsland(11));
        islandManager.conquerIsland(players[2], islandManager.getIsland(10));
        islandManager.conquerIsland(players[1], islandManager.getIsland(9));
        islandManager.conquerIsland(players[1], islandManager.getIsland(8));
        islandManager.conquerIsland(players[1], islandManager.getIsland(7));
        islandManager.conquerIsland(players[1], islandManager.getIsland(6));
        islandManager.conquerIsland(players[0], islandManager.getIsland(5));
        islandManager.conquerIsland(players[0], islandManager.getIsland(4));
        islandManager.conquerIsland(players[0], islandManager.getIsland(3));
        islandManager.conquerIsland(players[2], islandManager.getIsland(2));
        islandManager.conquerIsland(players[2], islandManager.getIsland(1));
        islandManager.conquerIsland(players[0], islandManager.getIsland(0));

        int firstIndex = game.getIndexCurrFirstPlayer();
        for (int i = 0; i < 3; i++) {
            switch ((firstIndex+i)%3) {
                case 0 -> assertDoesNotThrow(() -> game.chosenCard("Giulia", Card.TURTLE));
                case 1 -> assertDoesNotThrow(() -> game.chosenCard("Samu", Card.LIZARD));
                case 2 -> assertDoesNotThrow(() -> game.chosenCard("Fede", Card.CAT));
            }
        }

        List<Player> expectedWinners = new ArrayList<>();
        expectedWinners.add(players[2]);

        Map<Clan, Integer> students = TestUtil.studentMapCreator(0, 0, 0, 4, 0);

        int islandIndex = 0;

        islandManager.getIsland(islandIndex).addStudents(students);
        players[2].getChamber().setProfessor(Clan.DRAGONS, true);

        game.checkInfluence(islandManager.getIsland(islandIndex));

        List<Player> winners = game.getWinners();

        assertTrue(winners.containsAll(expectedWinners) && expectedWinners.containsAll(winners));

    }

    /**
     * test checks if calculateWin method works correctly in case of a tie
     */
    @Test
    public void calculateWinTieTest() {

        setNotExpertGameThreePlayers();
        Player[] players = game.getPlayers();
        IslandManager islandManager = game.getIslandManager();

        islandManager.conquerIsland(players[2], islandManager.getIsland(11));
        islandManager.conquerIsland(players[2], islandManager.getIsland(10));
        islandManager.conquerIsland(players[1], islandManager.getIsland(9));
        islandManager.conquerIsland(players[1], islandManager.getIsland(8));
        islandManager.conquerIsland(players[1], islandManager.getIsland(7));
        islandManager.conquerIsland(players[1], islandManager.getIsland(6));
        islandManager.conquerIsland(players[1], islandManager.getIsland(5));
        islandManager.conquerIsland(players[0], islandManager.getIsland(4));
        islandManager.conquerIsland(players[0], islandManager.getIsland(3));
        islandManager.conquerIsland(players[2], islandManager.getIsland(2));
        islandManager.conquerIsland(players[2], islandManager.getIsland(1));
        islandManager.conquerIsland(players[0], islandManager.getIsland(0));

        int firstIndex = game.getIndexCurrFirstPlayer();
        for (int i = 0; i < 3; i++) {
            switch ((firstIndex+i)%3) {
                case 0 -> assertDoesNotThrow(() -> game.chosenCard("Giulia", Card.TURTLE));
                case 1 -> assertDoesNotThrow(() -> game.chosenCard("Samu", Card.LIZARD));
                case 2 -> assertDoesNotThrow(() -> game.chosenCard("Fede", Card.CAT));
            }
        }

        List<Player> expectedWinners = new ArrayList<>();
        expectedWinners.add(players[2]);
        expectedWinners.add(players[1]);

        Map<Clan, Integer> students = TestUtil.studentMapCreator(0, 0, 0, 4, 0);

        islandManager.getIsland(0).addStudents(students);
        players[2].getChamber().setProfessor(Clan.DRAGONS, true);
        players[1].getChamber().setProfessor(Clan.UNICORNS, true);

        game.checkInfluence(islandManager.getIsland(0));

        List<Player> winners = game.getWinners();

        assertTrue(winners.containsAll(expectedWinners) && expectedWinners.containsAll(winners));

    }

    /**
     * the test checks if the chosenCloud method let the current player pick a cloud which is not empty
     * It is expected not to throw any exception
     * @param clan it is the clan of belonging of the students on which we are going to test the method
     * @param islandIndex index of the island where the player decide to move a student
     * @param cloudIndex index of the chosen cloud
     */

    @ParameterizedTest
    @CsvSource(value = {"PIXIES, 1, 1", "DRAGONS, 3, 0"})
    public void testChosenCloud(Clan clan, int islandIndex, int cloudIndex) {
        setPlanning();
        Map<Clan, Integer> addingStudents = TestUtil.studentMapCreator(0, 0, 0, 0, 0);
        addingStudents.put(clan, 3);

        game.getPlayers()[0].getHall().addStudents(addingStudents);
        assertDoesNotThrow(() -> game.moveStudentToIsland("Giulia", clan, islandIndex));
        assertDoesNotThrow(() -> game.moveStudentToChamber("Giulia", clan));
        assertDoesNotThrow(() -> game.moveStudentToChamber("Giulia", clan));

        Island motherNaturePosition = game.getIslandManager().getMotherNaturePosition();
        int index = game.getIslandManager().getIslands().indexOf(motherNaturePosition);
        assertDoesNotThrow(() -> game.moveMotherNature("Giulia", (index + 1) % (game.getIslandManager().getIslands().size())));
        Map<Clan, Integer> initialStudentsInTheHall = game.getPlayers()[0].getHall().getStudents();
        Map<Clan, Integer> studentsInTheCloud = game.getCloudManager().getCloud(cloudIndex).getStudents();
        assertDoesNotThrow(() -> game.chosenCloud("Giulia", cloudIndex));
        Map<Clan, Integer> finalStudentsInTheHall = game.getPlayers()[0].getHall().getStudents();
        Map<Clan, Integer> expectedFinalStudentInTheHall = new EnumMap<>(Clan.class);
        for (Clan c : Clan.values()) {
            expectedFinalStudentInTheHall.put(c, initialStudentsInTheHall.get(c) + studentsInTheCloud.get(c));
        }
        for (Clan c : Clan.values()) {
            assertEquals(finalStudentsInTheHall.get(c), expectedFinalStudentInTheHall.get(c));

        }
    }

    /**
     * tests that two players cannot choose the same cloud
     * the method chosenCloud is expected to throw a NotValidMoveException
     * @param clan it is the clan of belonging of the students on which we are going to test the method
     * @param islandIndex index of the island where the player decide to move a student
     * @param cloudIndex index of the chosen cloud
     */
    @ParameterizedTest
    @CsvSource(value = {"PIXIES, 1, 1", "DRAGONS, 6, 0"})
    public void testAlreadyChosenCloud(Clan clan, int islandIndex, int cloudIndex){
        setPlanning();
        game.getCloudManager().getCloud(cloudIndex).pick();

        Map<Clan, Integer> addingStudents = TestUtil.studentMapCreator(0, 0, 0, 0, 0);
        addingStudents.put(clan, 3);

        game.getPlayers()[0].getHall().addStudents(addingStudents);
        assertDoesNotThrow(() -> game.moveStudentToIsland("Giulia", clan, islandIndex));
        assertDoesNotThrow(() -> game.moveStudentToChamber("Giulia", clan));
        assertDoesNotThrow(() -> game.moveStudentToChamber("Giulia", clan));
        Island motherNaturePosition = game.getIslandManager().getMotherNaturePosition();
        int index = game.getIslandManager().getIslands().indexOf(motherNaturePosition);
        assertDoesNotThrow(() -> game.moveMotherNature("Giulia", (index + 1) % (game.getIslandManager().getIslands().size())));
        assertThrows(NotValidMoveException.class, () -> game.chosenCloud("Giulia", cloudIndex));
    }

    /**
     * checks that a non-current player cannot choose a cloud
     * the method chosenCloud is expected to throw a WrongPlayerException
     * @param clan it is the clan of belonging of the students on which we are going to test the method
     * @param islandIndex index of the island where the player decide to move a student
     * @param cloudIndex index of the chosen cloud
     */

    @ParameterizedTest
    @CsvSource(value = {"PIXIES, 1, 1", "UNICORNS, 7, 0"})
    public void testWrongPlayerChooseCloud(Clan clan, int islandIndex, int cloudIndex){
        setPlanning();
        game.getCloudManager().getCloud(cloudIndex).pick();

        Map<Clan, Integer> addingStudents = TestUtil.studentMapCreator(0, 0, 0, 0, 0);
        addingStudents.put(clan, 3);

        game.getPlayers()[0].getHall().addStudents(addingStudents);
        assertDoesNotThrow(() -> game.moveStudentToIsland("Giulia", clan, islandIndex));
        assertDoesNotThrow(() -> game.moveStudentToChamber("Giulia", clan));
        assertDoesNotThrow(() -> game.moveStudentToChamber("Giulia", clan));
        Island motherNaturePosition = game.getIslandManager().getMotherNaturePosition();
        int index = game.getIslandManager().getIslands().indexOf(motherNaturePosition);
        assertDoesNotThrow(() -> game.moveMotherNature("Giulia", (index + 1) % (game.getIslandManager().getIslands().size())));
        assertThrows(WrongPlayerException.class, () -> game.chosenCloud("Samu", cloudIndex));
    }

    /**
     * checks if, after that the first Player ends his turn, the endTurn method instantiates a new turn
     * the method endTurn is expected not to throw any exception
     * @param clan it is the clan of belonging of the students on which we are going to test the method
     * @param islandIndex index of the island where the player decide to move a student
     * @param cloudIndex index of the chosen cloud
     */

    @ParameterizedTest
    @CsvSource(value = {"PIXIES, 1, 1", "FAIRIES, 6, 0"})
    public void testEndTurn(Clan clan, int islandIndex, int cloudIndex){
        setPlanning();

        Map<Clan, Integer> addingStudents = TestUtil.studentMapCreator(0, 0, 0, 0, 0);
        addingStudents.put(clan, 3);

        game.getPlayers()[0].getHall().addStudents(addingStudents);
        assertDoesNotThrow(() -> game.moveStudentToIsland("Giulia", clan, islandIndex));
        assertDoesNotThrow(() -> game.moveStudentToChamber("Giulia", clan));
        assertDoesNotThrow(() -> game.moveStudentToChamber("Giulia", clan));
        Island motherNaturePosition = game.getIslandManager().getMotherNaturePosition();
        int index = game.getIslandManager().getIslands().indexOf(motherNaturePosition);
        assertDoesNotThrow(() -> game.moveMotherNature("Giulia", (index + 1) % (game.getIslandManager().getIslands().size())));
        assertDoesNotThrow(() -> game.chosenCloud("Giulia", cloudIndex));

        assertDoesNotThrow(() -> game.endTurn("Giulia"));
    }

    /**
     * the test checks that it isn't possible to call the endTurn method in a non-valid position
     * the method endTurn is expected to throw a NotValidMoveException
     * @param clan it is the clan of belonging of the students on which we are going to test the method
     * @param islandIndex index of the island where the player decide to move a student
     */
    @ParameterizedTest
    @CsvSource(value = {"TOADS, 4", "UNICORNS, 9"})
    public void testEndTurnBeforeTime(Clan clan, int islandIndex) {
        setPlanning();

        Map<Clan, Integer> addingStudents = TestUtil.studentMapCreator(0, 0, 0, 0, 0);
        addingStudents.put(clan, 3);

        game.getPlayers()[0].getHall().addStudents(addingStudents);
        assertDoesNotThrow(() -> game.moveStudentToIsland("Giulia", clan, islandIndex));
        assertDoesNotThrow(() -> game.moveStudentToChamber("Giulia", clan));
        assertDoesNotThrow(() -> game.moveStudentToChamber("Giulia", clan));

        assertThrows(WrongTurnPhaseException.class, () -> game.endTurn("Giulia"));
    }

}