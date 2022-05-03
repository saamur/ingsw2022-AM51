package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.Bag;
import it.polimi.ingsw.constants.GameConstants;

import java.io.Serializable;
import java.util.Objects;

/**
 * Player class models the player of the game Eriantys with its nickname,
 * its school board and its deck of assistant cards
 *
 */
public class Player implements Serializable {

    private final String nickname;
    private final Hall hall;
    private final Chamber chamber;
    private final TowerColor colorOfTowers;
    private int numberOfTowers;
    private final Deck deck;
    private Card currCard;

    public Player (String nickname, TowerColor colorOfTowers, int numPlayers, Bag bag) {

        this.nickname = nickname;
        hall = new Hall(bag.draw(GameConstants.getNumInitialStudentsHall(numPlayers)));
        chamber = new Chamber();
        this.colorOfTowers = colorOfTowers;
        numberOfTowers = GameConstants.getNumInitialTowers(numPlayers);
        deck = new Deck();
        currCard = null;

    }

    public String getNickname() {
        return nickname;
    }

    public TowerColor getColorOfTowers() {
        return colorOfTowers;
    }

    public int getNumberOfTowers() {
        return numberOfTowers;
    }

    public Card getCurrCard() {
        return currCard;
    }

    public Hall getHall() {
        return hall;
    }

    public Chamber getChamber() {
        return chamber;
    }

    public Deck getDeck() {
        return deck;
    }

    /**
     * method getCoins returns the number of coins in the object chamber
     * @return  the number of coins in the object chamber
     */
    public int getCoins() {
        return chamber.getCoins();
    }

    /**
     * method pay decreases the coins in chamber by the number given by parameter
     * @param amount    the number of coins to remove from chamber
     */
    public void pay (int amount) {
        chamber.setCoins(chamber.getCoins() - amount);
    }

    /**
     * method chooseCard removes the Card given by parameter from the deck
     * and sets the currCard attribute to the same Card
     * @param c the Card to remove from the Deck and to which to set the variable currCard
     * @return  true if the Deck did contain c, false if it didn't
     */
    public boolean chooseCard (Card c) {
        if (!deck.removeCard(c))
            return false;
        currCard = c;
        return true;
    }

    /**
     * method addTowers increases the variable numberOfTowers
     * @param n the amount of towers to add
     */
    public void addTowers (int n) {
        numberOfTowers += n;
    }

    /**
     * method removeTowers decreases the variable numberOfTowers
     * @param n the amount of towers to remove
     */
    public void removeTowers (int n) { //potrei mettere qui pcs.fire ma riceve fire per ogni singolo cambiamento che avviene
        numberOfTowers -= n;
    }

    
}
