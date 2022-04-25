package it.polimi.ingsw.player;

import it.polimi.ingsw.Bag;
import it.polimi.ingsw.player.Card;
import it.polimi.ingsw.player.Player;
import it.polimi.ingsw.player.TowerColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

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
        int amount = 1;
        int originalCoins = player1.getChamber().getCoins();
        player1.pay(amount);
        int coins = player1.getCoins();
        assertEquals(originalCoins - amount, coins);
    }

    /**
     * Method chooseCardTest() tests the chooseCard() method.
     * The result is expected true for the first time the card gets selected, and false the second time.
     */
    @ParameterizedTest
    @EnumSource(Card.class)
    public void chooseCardTest(Card card){
        assertTrue(player1.chooseCard(card));
        assertEquals(card, player1.getCurrCard());
        assertFalse(player1.getDeck().getCards().contains(card));
        assertFalse(player1.chooseCard(card));
    }

    /**
     * Method removeTowersTest() calls removeTowers() and removes all the Towers present.
     * Expected result is zero.
     */
    @Test
    public void removeTowersTest(){
        assertEquals(6, player1.getNumberOfTowers());
        player1.removeTowers(6);
        assertEquals(0, player1.getNumberOfTowers());
    }

    /**
     * Method addTowersTest() tests method addTowers(int n).
     * After the method is called the number of towers is expected to increase by n.
     */
    @ParameterizedTest
    @CsvSource({"5, 0", "5, 3"})
    public void addTowersTest(int removeTowers, int addTowers){
        player1.removeTowers(removeTowers);
        int originalNumTowers = player1.getNumberOfTowers();
        player1.addTowers(addTowers);
        assertEquals(originalNumTowers + addTowers, player1.getNumberOfTowers());
    }


}
