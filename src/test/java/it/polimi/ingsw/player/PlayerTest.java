package it.polimi.ingsw.player;

import it.polimi.ingsw.Bag;
import it.polimi.ingsw.player.Card;
import it.polimi.ingsw.player.Player;
import it.polimi.ingsw.player.TowerColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * PlayerTest tests Player.
 *
 * @link Player
 */
public class PlayerTest {
    Player player1;
    Bag bag;

    /**
     * Method createPlayer() initializes a new Bag and a new Player before each test
     */
    @BeforeEach
    public void createPlayer(){
        bag = new Bag();
        player1 = new Player("Samu", TowerColor.WHITE, 3, bag);
    }

    /**
     * Method payTest() tests the method pay(int amount).
     * The method is expected to decrease the number of coins in Chamber.
     */
    @Test
    public void payTest(){
        int originalCoins = player1.getChamber().getCoins();
        player1.pay(1);
        int coins = player1.getCoins();
        assertEquals(originalCoins - 1, coins);
    }

    /**
     * Method chooseCardTest() tests the chooseCard() method.
     * The result is expected true for the first time the card gets selected, and false the second time.
     */
    @Test
    public void chooseCardTest(){
        assertTrue(player1.chooseCard(Card.CAT));
        assertEquals(Card.CAT, player1.getCurrCard());
        assertFalse(player1.getDeck().getCards().contains(Card.CAT));
        assertFalse(player1.chooseCard(Card.CAT));
    }

    /**
     * Method removeTowersTest() calls removeTowers() after all of the towers have already been removed.
     * Expected result is zero.
     */
    @Test
    public void removeTowersTest(){
        assertEquals(6, player1.getNumberOfTowers());
        player1.removeTowers(6);
        assertEquals(0, player1.getNumberOfTowers());
        player1.removeTowers(1);
        assertEquals(0, player1.getNumberOfTowers());
    }

    /**
     * Method addTowersTest() tests method addTowers(int n).
     * After the method is called the number of towers is expected to increase by n.
     */
    @Test
    public void addTowersTest(){
        player1.removeTowers(5);
        int originalNumTowers = player1.getNumberOfTowers();
        player1.addTowers(3);
        assertEquals(originalNumTowers + 3, player1.getNumberOfTowers());
    }


}
