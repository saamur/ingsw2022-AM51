package it.polimi.ingsw;

import it.polimi.ingsw.islands.Island;
import it.polimi.ingsw.player.Card;
import it.polimi.ingsw.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
Game game;

public void setNotExpertGame(){
    game = new Game(2, "Giulia", false);

}

public void setNotExpertGameTwoPlayers(){
    game = new Game(2, "Giulia", false);
    game.addPlayer("Samu");

}

    /**
     * test check if the addPlayer method add the player in the game
     * Is expected a true result
     */

    @Test
    public void testAddPlayer(){
    setNotExpertGame();
    boolean addedSecondPlayer = game.addPlayer("Samu");
    String nicknameAddedPlayer = game.getPlayers()[1].getNickname();
    assertTrue(addedSecondPlayer);
    assertEquals("Samu", nicknameAddedPlayer);
    }

    /**
     * the test check that the addPlayer method doesn't add a third player into a two players game
     * Is expected a false result
     */

    @Test
    public void testAddTooMuchPlayer(){
        setNotExpertGameTwoPlayers();
        boolean addedThirdPlayer = game.addPlayer("Fede");
        assertFalse(addedThirdPlayer);
    }

    /**
     * the test check that the start method choose a player that is actually added in the game
     * Is expected that, in a game of two players, the indexNextFirstPlayer is between 0 and 1
     */

    @Test
    public void testInitialGameMethods(){
        setNotExpertGameTwoPlayers();
        int indexNextFirstPlayer = game.getIndexNextFirstPlayer();
        assertTrue(indexNextFirstPlayer==0||indexNextFirstPlayer==1);
        int indexCurrFirstPlayer = game.getIndexCurrFirstPlayer();
        int indexCurrPlayer =  game.getIndexCurrPlayer();
        assertEquals(indexNextFirstPlayer, indexCurrFirstPlayer);
        assertEquals(indexNextFirstPlayer, indexCurrPlayer);
    }

    /**
     * the test check if the chosenCard method return the currCard if the Player who choose the card is the currentPlayer
     * Is expected a false return if the Player, who is not the Current one, chose the card
     * Is expected a true return if the current Player chose the card, and the increase of the indexCurrPlayer
     */

    @Test
    public void testChosenCard(){
        setNotExpertGameTwoPlayers();
        if(game.getIndexCurrFirstPlayer()==0){
            boolean wrongChooseCard = game.chosenCard("Samu", Card.CAT);
            GameState invariantGameState = game.getGameState();
            assertEquals(GameState.PIANIFICATION, invariantGameState);
            boolean chooseCard = game.chosenCard("Giulia", Card.CHEETAH);
            assertTrue(chooseCard);
            assertFalse(wrongChooseCard);
        }
        else{
            boolean wrongChooseCard = game.chosenCard("Giulia", Card.CAT);
            GameState invariantGameState = game.getGameState();
            assertEquals(GameState.PIANIFICATION, invariantGameState);
            boolean chooseCard = game.chosenCard("Samu", Card.CHEETAH);
            assertTrue(chooseCard);
            assertFalse(wrongChooseCard);
        }
    }

    /**
     * the test check if, with a full deck, the player, who is the currPlayer, can't choose the same card played by
     * the other one
     * Is expected a false return
     */

    @Test
    public void testChooseTheSameCard(){
        setNotExpertGameTwoPlayers();
        if(game.getIndexCurrPlayer()==0){
            game.chosenCard("Giulia", Card.DOG);
            boolean chosen = game.chosenCard("Samu", Card.DOG);
            assertFalse(chosen);
        }
        else{
            game.chosenCard("Samu", Card.DOG);
            boolean chosen = game.chosenCard("Giulia", Card.DOG);
            assertFalse(chosen);
        }
    }


    /**
     * the test checks that the player cannot choose a card in a non PIANIFICATION fase of the game
     * Is expected a false return
     */

    @Test
    public void testNotCorrectPhaseToChooseCard(){
        setNotExpertGameTwoPlayers();
        if(game.getIndexCurrPlayer()==0){
            game.chosenCard("Giulia", Card.CHEETAH);
            game.chosenCard("Samu", Card.DOG);
            boolean chosen = game.chosenCard("Giulia", Card.DOG);
            assertFalse(chosen);
        }
        else{
            game.chosenCard("Samu", Card.CHEETAH);
            game.chosenCard("Giulia", Card.DOG);
            boolean chosen = game.chosenCard("Samu", Card.DOG);
            assertFalse(chosen);

        }

    }

    /**
     * the test checks that the Player cannot choose a Card that he has already chosen
     * Is expected a false result
     */

    @Test
    public void testAlreadyChosenCard(){
        setNotExpertGameTwoPlayers();
        game.getPlayers()[0].getDeck().removeCard(Card.CAT);
        game.getPlayers()[1].getDeck().removeCard(Card.CAT);
        boolean chosen;
        if(game.getIndexCurrPlayer()==0){
            chosen = game.chosenCard("Giulia", Card.CAT);

        }
        else{
            chosen = game.chosenCard("Samu", Card.CAT);
        }
        assertFalse(chosen);
    }



    /**
     * the test verifies that the order of the action phase is created in the right way, that is, starting from who has
     * chosen a card with a lower priority
     */

    @Test
    public void testOrderActionPhase(){
        setNotExpertGameTwoPlayers();
        Player[] playersOrder;
        if(game.getIndexCurrPlayer()==0){
            game.chosenCard("Giulia", Card.CHEETAH);
            game.chosenCard("Samu", Card.CAT);
            playersOrder = game.getPlayersOrder();
            ;
        }
        else{
            game.chosenCard("Samu", Card.CAT);
            game.chosenCard("Giulia", Card.CHEETAH);
            playersOrder = game.getPlayersOrder();
        }

        assertEquals("Giulia", playersOrder[0].getNickname());
        assertEquals("Samu", playersOrder[1].getNickname());
    }

    /**
     * the test check that it is possible to choose the same card of the other Player if it is the only one in the Deck
     * the test check that the Action order, in this particular case, is created taking into account who
     * first chose the card
     * Is expected a true result
     */
    public void setGameAndRemoveCards(){
        setNotExpertGameTwoPlayers();
        List<Card> toRemove = new ArrayList<>(Arrays.asList(Card.values()));
        toRemove.remove(Card.TURTLE);

        for (Card c : toRemove)
            game.getPlayers()[0].getDeck().removeCard(c);

        for (Card c : toRemove)
            game.getPlayers()[1].getDeck().removeCard(c);
    }
    @Test
    public void testOnlyOneLeftChooseCard(){
        setGameAndRemoveCards();
        boolean chosen;
        if(game.getIndexCurrPlayer()==0){
            game.chosenCard("Giulia", Card.TURTLE);
            chosen = game.chosenCard("Samu", Card.TURTLE);
            Player[] playersOrder = game.getPlayersOrder();
            assertEquals("Giulia", playersOrder[0].getNickname());
            assertEquals("Samu", playersOrder[1].getNickname());
        }
        else{
            game.chosenCard("Samu", Card.TURTLE);
            chosen = game.chosenCard("Giulia", Card.TURTLE);
        }
        assertTrue(chosen);
    }


    public void setPianification(){
        setNotExpertGameTwoPlayers();
        if(game.getIndexCurrPlayer()==0){
            game.chosenCard("Giulia", Card.CHEETAH);
            game.chosenCard("Samu", Card.CAT);
        }
        else{
            game.chosenCard("Samu", Card.CAT);
            game.chosenCard("Giulia", Card.CHEETAH);
        }

    }

    /**
     * the test check that the moveStudentToChamber remove a student from the Hall and add a student in the Chamber of
     * the Current Player
     */

    @Test
    public void testMoveStudentToChamber(){
        setPianification();
        game.getPlayers()[0].getHall().addStudent(Clan.PIXIES);
        int[] initialStudentsHall = game.getPlayers()[0].getHall().getStudents();
        int[] initialStudentsChamber = game.getPlayers()[0].getChamber().getStudents();
        boolean moved = game.moveStudentToChamber("Giulia", Clan.PIXIES);
        int[] finalStudentsHall = game.getPlayers()[0].getHall().getStudents();
        int[] finalStudentsChamber = game.getPlayers()[0].getChamber().getStudents();
        assertEquals(initialStudentsChamber[0]+1, finalStudentsChamber[0]);
        assertEquals(initialStudentsHall[0]-1, finalStudentsHall[0]);
        assertTrue(moved);
    }

    /**
     * the test check that a non-current player can't move a student to the Chamber
     * Is expected a false return
     */

    @Test
    public void testWrongPlayerMoveStudentToChamber(){
        setPianification();
        game.getPlayers()[1].getHall().addStudent(Clan.PIXIES);
        int[] initialStudentsHall = game.getPlayers()[1].getHall().getStudents();
        int[] initialStudentsChamber = game.getPlayers()[1].getChamber().getStudents();

        boolean moved = game.moveStudentToChamber("Samu", Clan.PIXIES);

        int[] finalStudentsHall = game.getPlayers()[1].getHall().getStudents();
        int[] finalStudentsChamber = game.getPlayers()[1].getChamber().getStudents();

        assertFalse(moved);
        assertEquals(initialStudentsChamber[0], finalStudentsChamber[0]);
        assertEquals(initialStudentsHall[0], finalStudentsHall[0]);
    }

    /**
     * tests that the current player can't move a student to Chamber if the student is not in the hall
     * Is expected a false return
     */

    @Test
    public void testNotStudentsToMoveToChamber(){
        setPianification();
        int numOfPixies = game.getPlayers()[0].getHall().getStudents()[0];
        for(int i=0; i<numOfPixies; i++){
            game.getPlayers()[0].getHall().removeStudent(Clan.PIXIES);
        }
        boolean moved = game.moveStudentToChamber("Giulia", Clan.PIXIES);
        assertFalse(moved);

    }

    /**
     * the test check that the method moveStudentToIsland move a chosen student from the hall to an Island
     */
    @Test
    public void testMoveStudentToIsland(){
        setPianification();
        game.getPlayers()[0].getHall().addStudent(Clan.PIXIES);
        int initialNumOfPixies = game.getIslandManager().getIsland(1).getStudents()[0];
        int initialPixiesInTheHall = game.getPlayers()[0].getHall().getStudents()[0];
        boolean moved = game.moveStudentToIsland("Giulia", Clan.PIXIES, 1);
        assertTrue(moved);
        int finalNumOfPixies = game.getIslandManager().getIsland(1).getStudents()[0];
        int finalPixiesInTheHall = game.getPlayers()[0].getHall().getStudents()[0];
        assertEquals(initialNumOfPixies+1, finalNumOfPixies);
        assertEquals(initialPixiesInTheHall-1, finalPixiesInTheHall);
    }

    /**
     * tests that a player, who is not the current one, can't move a student to an Island
     * Is expected a false return
     */

    @Test
    public void testWrongPlayerMoveStudentToIsland(){
        setPianification();
        game.getPlayers()[1].getHall().addStudent(Clan.PIXIES);
        int initialNumOfPixies = game.getIslandManager().getIsland(1).getStudents()[0];
        int initialPixiesInTheHall = game.getPlayers()[1].getHall().getStudents()[0];
        boolean moved = game.moveStudentToIsland("Samu", Clan.PIXIES, 1);
        assertFalse(moved);
        int finalNumOfPixies = game.getIslandManager().getIsland(1).getStudents()[0];
        int finalPixiesInTheHall = game.getPlayers()[1].getHall().getStudents()[0];
        assertEquals(initialNumOfPixies, finalNumOfPixies);
        assertEquals(initialPixiesInTheHall, finalPixiesInTheHall);
    }

    /**
     * tests that the current player can't move a student to an island if the student is not in the hall
     * Is expected a false return
     */

    @Test
    public void testNotStudentsToMoveToIsland(){
        setPianification();
        int numOfPixies = game.getPlayers()[0].getHall().getStudents()[0];
        for(int i=0; i<numOfPixies; i++){
            game.getPlayers()[0].getHall().removeStudent(Clan.PIXIES);
        }
        boolean moved = game.moveStudentToIsland("Giulia", Clan.PIXIES, 1);
        assertFalse(moved);
    }

    /**
     * tests if with the moveMotherNature method the current player moves Mother Nature on a chosen Island
     */

    @Test
    public void testMoveMotherNature(){
        setPianification();
        int[] addingStudents = {3, 0, 0, 0, 0};
        game.getPlayers()[0].getHall().addStudents(addingStudents);
        game.moveStudentToIsland("Giulia", Clan.PIXIES, 1);
        game.moveStudentToChamber("Giulia", Clan.PIXIES);
        game.moveStudentToChamber("Giulia", Clan.PIXIES);
        /*TurnState turnState = game.getTurn().getTurnState();
        assertEquals(TurnState.MOTHER_MOVING, turnState);*/
        Island motherNaturePosition = game.getIslandManager().getMotherNaturePosition();
        int index = game.getIslandManager().getIslands().indexOf(motherNaturePosition);
        boolean moved = game.moveMotherNature("Giulia", (index+1)%(game.getIslandManager().getIslands().size()));
        assertTrue(moved);
        Island NewMotherNaturePosition = game.getIslandManager().getMotherNaturePosition();
        int finalIndex = game.getIslandManager().getIslands().indexOf(NewMotherNaturePosition);
        assertEquals((index+1)%game.getIslandManager().getIslands().size(), finalIndex);
    }

    /** the test checks that the currPlayer cannot move the Mother Nature more than the Max steps written on the card he
     * played
     * A false return is expected
      */
    @Test
    public void testMoveMotherNatureTooMuch(){
        setPianification();
        int[] addingStudents = {3, 0, 0, 0, 0};
        game.getPlayers()[0].getHall().addStudents(addingStudents);
        game.moveStudentToIsland("Giulia", Clan.PIXIES, 1);
        game.moveStudentToChamber("Giulia", Clan.PIXIES);
        game.moveStudentToChamber("Giulia", Clan.PIXIES);
        Island motherNaturePosition = game.getIslandManager().getMotherNaturePosition();
        int index = game.getIslandManager().getIslands().indexOf(motherNaturePosition);
        boolean moved = game.moveMotherNature("Giulia", (index+2)%(game.getIslandManager().getIslands().size()));
        assertFalse(moved);
        int finalIndex = game.getIslandManager().getIslands().indexOf(motherNaturePosition);
        assertEquals(index, finalIndex);
    }













}