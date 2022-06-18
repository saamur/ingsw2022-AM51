package it.polimi.ingsw.model.islands;

import it.polimi.ingsw.model.Clan;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.StudentContainer;

import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

/**
 * The Island class models the islands of the game, with the attributes and the methods
 * needed for their management and merging
 *
 */
public class Island implements Serializable, StudentContainer {

    private int numberOfIslands;
    private final Map<Clan, Integer> students;
    private Player controllingPlayer;
    private int numberOfTowers;
    private int numProhibitionCards;

    public Island () {
        numberOfIslands = 1;
        students = new EnumMap<>(Clan.class);
        for (Clan c : Clan.values())
            students.put(c, 0);
        controllingPlayer = null;
        numberOfTowers = 0;
        numProhibitionCards = 0;
    }

    public int getNumberOfIslands() {
        return numberOfIslands;
    }

    public Map<Clan, Integer> getStudents() {
        return new EnumMap<>(students);
    }

    public Player getControllingPlayer() {
        return controllingPlayer;
    }

    public void setControllingPlayer(Player controllingPlayer) {
        this.controllingPlayer = controllingPlayer;
    }

    public int getNumberOfTowers() {
        return numberOfTowers;
    }

    public void setNumberOfTowers(int numberOfTowers) {
        this.numberOfTowers = numberOfTowers;
    }

    public int getNumProhibitionCards() {
        return numProhibitionCards;
    }

    @Override
    public Map<Clan, Integer> addStudents(Map<Clan, Integer> stud) {
        for (Clan c : Clan.values())
            students.put(c, students.get(c) + stud.get(c));
        return new EnumMap<>(stud);
    }

    @Override
    public Map<Clan, Integer> removeStudents(Map<Clan, Integer> stud) {

        Map<Clan, Integer> removedStudents = new EnumMap<>(Clan.class);

        for (Clan c : Clan.values()) {
            if (students.get(c) >= stud.get(c)) {
                removedStudents.put(c, stud.get(c));
                students.put(c, students.get(c) - stud.get(c));
            }
            else {
                removedStudents.put(c, students.get(c));
                students.put(c, 0);
            }
        }

        return removedStudents;

    }

    /**
     * The method addStudent adds a student of the Clan c in the students variable
     * @param c the clan of the student to add
     * @return  true if the student was added
     */
    public boolean addStudent(Clan c) {
        students.put(c, students.get(c) + 1);
        return true;
    }

    /**
     * The method addProhibitionCard increases numProhibitionCards variable
     */
    public void addProhibitionCard() {
        numProhibitionCards++;
    }

    /**
     * The method removeProhibitionCard decreases numProhibitionCards if it's greater then zero
     */
    public void removeProhibitionCard() {
        if(numProhibitionCards > 0)
            numProhibitionCards--;
    }

    /**
     * The method merge adds all variables of the parameter to those of the current object
     * @param mergingIsland the island to merge to this
     */
    public void merge (Island mergingIsland) {

        this.numberOfIslands += mergingIsland.numberOfIslands;
        addStudents(mergingIsland.students);
        this.numberOfTowers += mergingIsland.numberOfTowers;
        this.numProhibitionCards += mergingIsland.numProhibitionCards;

    }

}
