package it.polimi.ingsw.player;

import it.polimi.ingsw.Bag;

public class Player {

    private final String nickname;
    private final TowerColor colorOfTowers;
    private int numberOfTowers;
    private final Deck deck;
    private Card currCard;
    private final School school;

    public Player (String nickname, TowerColor colorOfTowers, int numPlayers, Bag bag) {

        this.nickname = nickname;
        this.colorOfTowers = colorOfTowers;
        numberOfTowers = numPlayers == 2 ? 8 : 6;
        deck = new Deck();
        currCard = null;
        school = new School(numPlayers,bag);

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
        return school.getHall();
    }

    public Chamber getChamber() {
        return school.getChamber();
    }

    public Deck getDeck() {
        return deck;
    }

    public int getCoins() {
        return school.getChamber().getCoins();
    }

    public void pay (int amount) {
        school.getChamber().setCoins(school.getChamber().getCoins() - amount);
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
