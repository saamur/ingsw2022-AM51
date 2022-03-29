package it.polimi.ingsw;

public class Player {

    private final String nickname;
    private final TowerColor colorOfTowers;
    private int numberOfTowers;
    private Card currCard;
    private final School school;

    public Player (String nickname, TowerColor colorOfTowers, int numPlayers, Bag bag) {

        this.nickname = nickname;
        this.colorOfTowers = colorOfTowers;
        numberOfTowers = numPlayers == 2 ? 8 : 6;
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

    public void addTowers (int n) {
        numberOfTowers += n;
    }

    public void removeTowers (int n) {

        numberOfTowers -= n;

        if (numberOfTowers < 0)
            numberOfTowers = 0;

    }

}
