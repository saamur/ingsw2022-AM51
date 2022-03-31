package it.polimi.ingsw.islands;

import it.polimi.ingsw.Clan;
import it.polimi.ingsw.player.Player;
import it.polimi.ingsw.StudentContainer;

public class Island implements StudentContainer {

    private int numberOfIslands;
    private final int[] students;
    private Player controllingPlayer;
    private int numProhibitionCards;

    public Island () {
        numberOfIslands = 1;
        students = new int[Clan.values().length];
        controllingPlayer = null;
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

    public int getNumProhibitionCards() {
        return numProhibitionCards;
    }

    public void setControllingPlayer(Player controllingPlayer) {
        this.controllingPlayer = controllingPlayer;
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

    public boolean addStudent(Clan c) {
        students[c.ordinal()]++;
        return true;
    }

    public void addProhibitionCard() {
        numProhibitionCards++;
    }

    public void removeProhibitionCard() {
        if(numProhibitionCards > 0)
            numProhibitionCards--;
    }

    public void merge (Island mergingIsland) {

        this.numberOfIslands += mergingIsland.numberOfIslands;
        addStudents(mergingIsland.students);
        this.numProhibitionCards += mergingIsland.numProhibitionCards;

    }

}
