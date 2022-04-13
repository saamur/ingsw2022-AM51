package it.polimi.ingsw;

import it.polimi.ingsw.exceptions.NicknameNotAvailableException;
import it.polimi.ingsw.exceptions.NotValidMoveException;
import it.polimi.ingsw.exceptions.WrongGamePhaseException;
import it.polimi.ingsw.exceptions.WrongPlayerException;
import it.polimi.ingsw.islands.Island;
import it.polimi.ingsw.islands.IslandManager;
import it.polimi.ingsw.player.Card;
import it.polimi.ingsw.player.Player;
import org.junit.jupiter.api.Test;

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
     * the test checks that the player cannot choose a card in a non PLANNING fase of the game
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
     */

    @Test
    public void testAlreadyChosenCard() {
        setNotExpertGameTwoPlayers();
        game.getPlayers()[0].getDeck().removeCard(Card.CAT);
        game.getPlayers()[1].getDeck().removeCard(Card.CAT);
        if (game.getIndexCurrPlayer() == 0) {
            assertThrows(NotValidMoveException.class, () -> game.chosenCard("Giulia", Card.CAT));

        } else {
            assertThrows(NotValidMoveException.class, () -> game.chosenCard("Samu", Card.CAT));
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
            playersOrder = game.getPlayersOrder();
        } else {
            assertDoesNotThrow(() -> game.chosenCard("Samu", Card.CAT));
            assertDoesNotThrow(() -> game.chosenCard("Giulia", Card.CHEETAH));
            playersOrder = game.getPlayersOrder();
        }

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
            Player[] playersOrder = game.getPlayersOrder();
            assertEquals("Giulia", playersOrder[0].getNickname());
            assertEquals("Samu", playersOrder[1].getNickname());
        } else {
            assertDoesNotThrow(() -> game.chosenCard("Samu", Card.TURTLE));
            assertDoesNotThrow(() -> game.chosenCard("Giulia", Card.TURTLE));
            Player[] playersOrder = game.getPlayersOrder();
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
     */

    @Test
    public void testMoveStudentToChamber() {
        setPlanning();
        game.getPlayers()[0].getHall().addStudent(Clan.PIXIES);
        Map<Clan, Integer> initialStudentsHall = game.getPlayers()[0].getHall().getStudents();
        Map<Clan, Integer> initialStudentsChamber = game.getPlayers()[0].getChamber().getStudents();

        assertDoesNotThrow(() -> game.moveStudentToChamber("Giulia", Clan.PIXIES));

        Map<Clan, Integer> finalStudentsHall = game.getPlayers()[0].getHall().getStudents();
        Map<Clan, Integer> finalStudentsChamber = game.getPlayers()[0].getChamber().getStudents();

        assertEquals(initialStudentsChamber.get(Clan.values()[0]) + 1, finalStudentsChamber.get(Clan.values()[0]));
        assertEquals(initialStudentsHall.get(Clan.values()[0]) - 1, finalStudentsHall.get(Clan.values()[0]));
    }

    /**
     * the test check that a non-current player can't move a student to the Chamber
     * method moveStudentToChamber is expected to throw a WrongPlayerException
     */

    @Test
    public void testWrongPlayerMoveStudentToChamber() {
        setPlanning();
        game.getPlayers()[1].getHall().addStudent(Clan.PIXIES);
        Map<Clan, Integer> initialStudentsHall = game.getPlayers()[1].getHall().getStudents();
        Map<Clan, Integer> initialStudentsChamber = game.getPlayers()[1].getChamber().getStudents();

        assertThrows(WrongPlayerException.class, () -> game.moveStudentToChamber("Samu", Clan.PIXIES));

        Map<Clan, Integer> finalStudentsHall = game.getPlayers()[1].getHall().getStudents();
        Map<Clan, Integer> finalStudentsChamber = game.getPlayers()[1].getChamber().getStudents();

        assertEquals(initialStudentsChamber.get(Clan.values()[0]), finalStudentsChamber.get(Clan.values()[0]));
        assertEquals(initialStudentsHall.get(Clan.values()[0]), finalStudentsHall.get(Clan.values()[0]));
    }

    /**
     * tests that the current player can't move a student to Chamber if the student is not in the hall
     * method moveStudentToChamber is expected to throw a NotValidMoveException
     */

    @Test
    public void testNotStudentsToMoveToChamber() {
        setPlanning();
        int numOfPixies = game.getPlayers()[0].getHall().getStudents().get(Clan.values()[0]);
        for (int i = 0; i < numOfPixies; i++) {
            game.getPlayers()[0].getHall().removeStudent(Clan.PIXIES);
        }
        assertThrows(NotValidMoveException.class, () -> game.moveStudentToChamber("Giulia", Clan.PIXIES));

    }

    /**
     * the test check that the method moveStudentToIsland move a chosen student from the hall to an Island
     */
    @Test
    public void testMoveStudentToIsland() {
        setPlanning();
        game.getPlayers()[0].getHall().addStudent(Clan.PIXIES);
        int initialNumOfPixies = game.getIslandManager().getIsland(1).getStudents().get(Clan.values()[0]);
        int initialPixiesInTheHall = game.getPlayers()[0].getHall().getStudents().get(Clan.values()[0]);
        assertDoesNotThrow(() -> game.moveStudentToIsland("Giulia", Clan.PIXIES, 1));
        int finalNumOfPixies = game.getIslandManager().getIsland(1).getStudents().get(Clan.values()[0]);
        int finalPixiesInTheHall = game.getPlayers()[0].getHall().getStudents().get(Clan.values()[0]);
        assertEquals(initialNumOfPixies + 1, finalNumOfPixies);
        assertEquals(initialPixiesInTheHall - 1, finalPixiesInTheHall);
    }

    /**
     * tests that a player, who is not the current one, can't move a student to an Island
     * The method moveStudentToIsland is expected to throw a WrongPlayerException
     */

    @Test
    public void testWrongPlayerMoveStudentToIsland() {
        setPlanning();
        game.getPlayers()[1].getHall().addStudent(Clan.PIXIES);
        int initialNumOfPixies = game.getIslandManager().getIsland(1).getStudents().get(Clan.values()[0]);
        int initialPixiesInTheHall = game.getPlayers()[1].getHall().getStudents().get(Clan.values()[0]);
        assertThrows(WrongPlayerException.class, () -> game.moveStudentToIsland("Samu", Clan.PIXIES, 1));
        int finalNumOfPixies = game.getIslandManager().getIsland(1).getStudents().get(Clan.values()[0]);
        int finalPixiesInTheHall = game.getPlayers()[1].getHall().getStudents().get(Clan.values()[0]);
        assertEquals(initialNumOfPixies, finalNumOfPixies);
        assertEquals(initialPixiesInTheHall, finalPixiesInTheHall);
    }

    /**
     * tests that the current player can't move a student to an island if the student is not in the hall
     * The method moveStudentToIsland is expected to throw a NotValidMoveException
     */

    @Test
    public void testNotStudentsToMoveToIsland() {
        setPlanning();
        int numOfPixies = game.getPlayers()[0].getHall().getStudents().get(Clan.values()[0]);
        for (int i = 0; i < numOfPixies; i++) {
            game.getPlayers()[0].getHall().removeStudent(Clan.PIXIES);
        }
        assertThrows(NotValidMoveException.class, () -> game.moveStudentToIsland("Giulia", Clan.PIXIES, 1));
    }

    /**
     * tests if with the moveMotherNature method the current player moves Mother Nature on a chosen Island
     */

    @Test
    public void testMoveMotherNature() {
        setPlanning();
        Map<Clan, Integer> addingStudents = new EnumMap<>(Clan.class);
        addingStudents.put(Clan.PIXIES, 3);
        addingStudents.put(Clan.UNICORNS, 0);
        addingStudents.put(Clan.TOADS, 0);
        addingStudents.put(Clan.DRAGONS, 0);
        addingStudents.put(Clan.FAIRIES, 0);
        game.getPlayers()[0].getHall().addStudents(addingStudents);
        assertDoesNotThrow(() -> game.moveStudentToIsland("Giulia", Clan.PIXIES, 1));
        assertDoesNotThrow(() -> game.moveStudentToChamber("Giulia", Clan.PIXIES));
        assertDoesNotThrow(() -> game.moveStudentToChamber("Giulia", Clan.PIXIES));
        TurnState turnState = game.getTurn().getTurnState();
        assertEquals(TurnState.MOTHER_MOVING, turnState);
        Island motherNaturePosition = game.getIslandManager().getMotherNaturePosition();
        int index = game.getIslandManager().getIslands().indexOf(motherNaturePosition);
        boolean moved = game.moveMotherNature("Giulia", (index + 1) % (game.getIslandManager().getIslands().size()));
        assertTrue(moved);
        Island NewMotherNaturePosition = game.getIslandManager().getMotherNaturePosition();
        int finalIndex = game.getIslandManager().getIslands().indexOf(NewMotherNaturePosition);
        assertEquals((index + 1) % game.getIslandManager().getIslands().size(), finalIndex);
    }

    /**
     * the test checks that the currPlayer cannot move the Mother Nature more than the Max steps written on the card he
     * played
     * A false return is expected
     */
    @Test
    public void testMoveMotherNatureTooMuch() {
        setPlanning();
        Map<Clan, Integer> addingStudents = new EnumMap<>(Clan.class);
        addingStudents.put(Clan.PIXIES, 3);
        addingStudents.put(Clan.UNICORNS, 0);
        addingStudents.put(Clan.TOADS, 0);
        addingStudents.put(Clan.DRAGONS, 0);
        addingStudents.put(Clan.FAIRIES, 0);
        game.getPlayers()[0].getHall().addStudents(addingStudents);
        assertDoesNotThrow(() -> game.moveStudentToIsland("Giulia", Clan.PIXIES, 1));
        assertDoesNotThrow(() -> game.moveStudentToChamber("Giulia", Clan.PIXIES));
        assertDoesNotThrow(() -> game.moveStudentToChamber("Giulia", Clan.PIXIES));
        Island motherNaturePosition = game.getIslandManager().getMotherNaturePosition();
        int index = game.getIslandManager().getIslands().indexOf(motherNaturePosition);
        boolean moved = game.moveMotherNature("Giulia", (index + 2) % (game.getIslandManager().getIslands().size()));
        assertFalse(moved);
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

        Map<Clan, Integer> students = new EnumMap<>(Clan.class);
        students.put(Clan.PIXIES, 0);
        students.put(Clan.UNICORNS, 0);
        students.put(Clan.TOADS, 0);
        students.put(Clan.DRAGONS, 4);
        students.put(Clan.FAIRIES, 0);

        islandManager.getIsland(0).addStudents(students);
        players[2].getChamber().setProfessor(Clan.DRAGONS, true);

        game.checkInfluence(islandManager.getIsland(0));

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

        Map<Clan, Integer> students = new EnumMap<>(Clan.class);
        students.put(Clan.PIXIES, 0);
        students.put(Clan.UNICORNS, 0);
        students.put(Clan.TOADS, 0);
        students.put(Clan.DRAGONS, 4);
        students.put(Clan.FAIRIES, 0);

        islandManager.getIsland(0).addStudents(students);
        players[2].getChamber().setProfessor(Clan.DRAGONS, true);
        players[1].getChamber().setProfessor(Clan.UNICORNS, true);

        game.checkInfluence(islandManager.getIsland(0));

        List<Player> winners = game.getWinners();

        assertTrue(winners.containsAll(expectedWinners) && expectedWinners.containsAll(winners));

    }

    /**
     * the test checks if the chosenCloud method let the current player pick a cloud which is not empty
     * Is expected a True result
     */

    @Test
    public void testChosenCloud() {
        setPlanning();
        Map<Clan, Integer> addingStudents = new EnumMap<>(Clan.class);
        addingStudents.put(Clan.PIXIES, 3);
        addingStudents.put(Clan.UNICORNS, 0);
        addingStudents.put(Clan.TOADS, 0);
        addingStudents.put(Clan.DRAGONS, 0);
        addingStudents.put(Clan.FAIRIES, 0);

        game.getPlayers()[0].getHall().addStudents(addingStudents);
        assertDoesNotThrow(() -> game.moveStudentToIsland("Giulia", Clan.PIXIES, 1));
        assertDoesNotThrow(() -> game.moveStudentToChamber("Giulia", Clan.PIXIES));
        assertDoesNotThrow(() -> game.moveStudentToChamber("Giulia", Clan.PIXIES));
        Island motherNaturePosition = game.getIslandManager().getMotherNaturePosition();
        int index = game.getIslandManager().getIslands().indexOf(motherNaturePosition);
        game.moveMotherNature("Giulia", (index + 1) % (game.getIslandManager().getIslands().size()));
        Map<Clan, Integer> initialStudentsInTheHall = game.getPlayers()[0].getHall().getStudents();
        Map<Clan, Integer> studentsInTheCloud = game.getCloudManager().getCloud(1).getStudents();
        boolean chosen = game.chosenCloud("Giulia", 1);
        assertTrue(chosen);
        Map<Clan, Integer> finalStudentsInTheHall = game.getPlayers()[0].getHall().getStudents();
        Map<Clan, Integer> expectedFinalStudentInTheHall = new EnumMap<>(Clan.class);
        for (Clan c : Clan.values()) {
            expectedFinalStudentInTheHall.put(c, initialStudentsInTheHall.get(c) + studentsInTheCloud.get(c));
        }
        for (int i = 0; i < Clan.values().length; i++) {
            assertEquals(finalStudentsInTheHall.get(Clan.values()[i]), expectedFinalStudentInTheHall.get(Clan.values()[i]));

        }
    }

    /**
     * tests that two players cannot choose the same cloud
     * A false result is expected
     */

    @Test
    public void testAlreadyChosenCloud(){
        setPlanning();
        game.getCloudManager().getCloud(1).pick();

        Map<Clan, Integer> addingStudents = new EnumMap<>(Clan.class);
        addingStudents.put(Clan.PIXIES, 3);
        addingStudents.put(Clan.UNICORNS, 0);
        addingStudents.put(Clan.TOADS, 0);
        addingStudents.put(Clan.DRAGONS, 0);
        addingStudents.put(Clan.FAIRIES, 0);


        game.getPlayers()[0].getHall().addStudents(addingStudents);
        assertDoesNotThrow(() -> game.moveStudentToIsland("Giulia", Clan.PIXIES, 1));
        assertDoesNotThrow(() -> game.moveStudentToChamber("Giulia", Clan.PIXIES));
        assertDoesNotThrow(() -> game.moveStudentToChamber("Giulia", Clan.PIXIES));
        Island motherNaturePosition = game.getIslandManager().getMotherNaturePosition();
        int index = game.getIslandManager().getIslands().indexOf(motherNaturePosition);
        game.moveMotherNature("Giulia", (index + 1) % (game.getIslandManager().getIslands().size()));
        boolean chosen = game.chosenCloud("Giulia", 1);
        assertFalse(chosen);
    }

    /**
     * checks that a non-current player cannot choose a cloud
     * a false return is expected
     */

    @Test
    public void testWrongPlayerChooseCloud(){
        setPlanning();
        game.getCloudManager().getCloud(1).pick();

        Map<Clan, Integer> addingStudents = new EnumMap<>(Clan.class);
        addingStudents.put(Clan.PIXIES, 3);
        addingStudents.put(Clan.UNICORNS, 0);
        addingStudents.put(Clan.TOADS, 0);
        addingStudents.put(Clan.DRAGONS, 0);
        addingStudents.put(Clan.FAIRIES, 0);

        game.getPlayers()[0].getHall().addStudents(addingStudents);
        assertDoesNotThrow(() -> game.moveStudentToIsland("Giulia", Clan.PIXIES, 1));
        assertDoesNotThrow(() -> game.moveStudentToChamber("Giulia", Clan.PIXIES));
        assertDoesNotThrow(() -> game.moveStudentToChamber("Giulia", Clan.PIXIES));
        Island motherNaturePosition = game.getIslandManager().getMotherNaturePosition();
        int index = game.getIslandManager().getIslands().indexOf(motherNaturePosition);
        game.moveMotherNature("Giulia", (index + 1) % (game.getIslandManager().getIslands().size()));
        boolean chosen = game.chosenCloud("Samu", 1);
        assertFalse(chosen);
    }

    /**
     * check if, after that the first Player end his turn, the endTurn method instantiates a new turn
     * Is expected a true result
     */

    @Test
    public void testEndTurn(){
        setPlanning();

        Map<Clan, Integer> addingStudents = new EnumMap<>(Clan.class);
        addingStudents.put(Clan.PIXIES, 3);
        addingStudents.put(Clan.UNICORNS, 0);
        addingStudents.put(Clan.TOADS, 0);
        addingStudents.put(Clan.DRAGONS, 0);
        addingStudents.put(Clan.FAIRIES, 0);

        game.getPlayers()[0].getHall().addStudents(addingStudents);
        assertDoesNotThrow(() -> game.moveStudentToIsland("Giulia", Clan.PIXIES, 1));
        assertDoesNotThrow(() -> game.moveStudentToChamber("Giulia", Clan.PIXIES));
        assertDoesNotThrow(() -> game.moveStudentToChamber("Giulia", Clan.PIXIES));
        Island motherNaturePosition = game.getIslandManager().getMotherNaturePosition();
        int index = game.getIslandManager().getIslands().indexOf(motherNaturePosition);
        game.moveMotherNature("Giulia", (index + 1) % (game.getIslandManager().getIslands().size()));
        game.chosenCloud("Giulia", 1);
        boolean ended = game.endTurn("Giulia");
        assertTrue(ended);
    }

    /**
     * the test checks that it isn't possible to call the endTurn method in a non-valid position
     * Is expected a false return
     */
    @Test
    public void testEndTurnBeforeTime() {
        setPlanning();

        Map<Clan, Integer> addingStudents = new EnumMap<>(Clan.class);
        addingStudents.put(Clan.PIXIES, 3);
        addingStudents.put(Clan.UNICORNS, 0);
        addingStudents.put(Clan.TOADS, 0);
        addingStudents.put(Clan.DRAGONS, 0);
        addingStudents.put(Clan.FAIRIES, 0);

        game.getPlayers()[0].getHall().addStudents(addingStudents);
        assertDoesNotThrow(() -> game.moveStudentToIsland("Giulia", Clan.PIXIES, 1));
        assertDoesNotThrow(() -> game.moveStudentToChamber("Giulia", Clan.PIXIES));
        assertDoesNotThrow(() -> game.moveStudentToChamber("Giulia", Clan.PIXIES));
        boolean endedTurn = game.endTurn("Giulia");
        assertFalse(endedTurn);
    }

}