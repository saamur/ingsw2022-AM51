package it.polimi.ingsw.player;

import it.polimi.ingsw.Bag;

public class Player {

    private final String nickname;
    private final Hall hall;
    private final Chamber chamber;
    private final TowerColor colorOfTowers;
    private int numberOfTowers;
    private final Deck deck;
    private Card currCard;

    public Player (String nickname, TowerColor colorOfTowers, int numPlayers, Bag bag) {

        this.nickname = nickname;
        hall = new Hall(bag.draw(numPlayers == 2 ? 7 : 9));
        chamber = new Chamber();
        this.colorOfTowers = colorOfTowers;
        numberOfTowers = numPlayers == 2 ? 8 : 6;
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

    public int getCoins() {
        return chamber.getCoins();
    }

    public void pay (int amount) {
        chamber.setCoins(chamber.getCoins() - amount);
    }

    public boolean chooseCard (Card c) {
        if (!deck.removeCard(c))
            return false;
        currCard = c;
        return true;
    }

    public void addTowers (int n) {
        numberOfTowers += n;
    }

    public void removeTowers (int n) {
        numberOfTowers -= n;
    }

}
