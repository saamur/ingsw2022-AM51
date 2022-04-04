package it.polimi.ingsw.islands;

import it.polimi.ingsw.Clan;
import it.polimi.ingsw.player.Player;
import it.polimi.ingsw.StudentContainer;

public class Island implements StudentContainer {

    private int numberOfIslands;
    private final int[] students;
    private Player controllingPlayer;
    private int numberOfTowers;
    private int numProhibitionCards;

    public Island () {
        numberOfIslands = 1;
        students = new int[Clan.values().length];
        controllingPlayer = null;
        numberOfTowers = 0;
        numProhibitionCards = 0;
    }

    public int getNumberOfIslands() {
        return numberOfIslands;
    }

    public int[] getStudents() {
        return students.clone();
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
    public int[] addStudents(int[] stud) {

        for (int i = 0; i < students.length; i++)
            students[i] += stud[i];

        return stud.clone();

    }

    @Override
    public int[] removeStudents(int[] stud) {

        int[] removedStudents = new int[Clan.values().length];

        for (int i = 0; i < students.length; i++) {
            if (students[i] >= stud[i]){
                removedStudents[i] = stud[i];
                students[i] -= stud[i];
            }
            else {
                removedStudents[i] = students[i];
                students[i] = 0;
            }
        }

        return removedStudents;

    }

    /**
     * method addStudent adds a student of the Clan c in the students variable
     * @param c the clan of the student to add
     * @return  true if the student was added
     */
    public boolean addStudent(Clan c) {
        students[c.ordinal()]++;
        return true;
    }

    /**
     * method addProhibitionCard increases numProhibitionCards variable
     */
    public void addProhibitionCard() {
        numProhibitionCards++;
    }

    /**
     * method removeProhibitionCard decreases numProhibitionCards if it's greater then zero
     */
    public void removeProhibitionCard() {
        if(numProhibitionCards > 0)
            numProhibitionCards--;
    }

    /**
     * method merge adds all variables of the parameter to those of the current object
     * @param mergingIsland the island to merge to this
     */
    public void merge (Island mergingIsland) {

        this.numberOfIslands += mergingIsland.numberOfIslands;
        addStudents(mergingIsland.students);
        this.numberOfTowers += mergingIsland.numberOfTowers;
        this.numProhibitionCards += mergingIsland.numProhibitionCards;

    }

}
