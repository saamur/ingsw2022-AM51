package it.polimi.ingsw;

public class School {

    private final Hall hall;
    private final Chamber chamber;

    public School (int numPlayers, Bag bag){
        hall = new Hall(bag.draw(numPlayers == 2 ? 7 : 9));
        chamber = new Chamber();
    }

    public Hall getHall() {
        return hall;
    }

    public Chamber getChamber() {
        return chamber;
    }

}
