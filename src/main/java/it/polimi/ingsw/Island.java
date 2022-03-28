package it.polimi.ingsw;

public class Island {

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

    public void addStudents(int[] stud) {          //returned type to decide

        for (int i = 0; i < students.length; i++)
            students[i] += stud[i];

    }

    public void addStudent(Clan c) {
        students[c.ordinal()]++;
    }

    public void addProhibitionCard() {
        numProhibitionCards++;
    }

    public void removeProhibitionCard() {
        if(numProhibitionCards > 0)
            numProhibitionCards--;
    }

    public void conquer (Player p) {
        //TODO
    }

    public void merge (Island mergingIsland) {

        this.numberOfIslands += mergingIsland.numberOfIslands;
        addStudents(mergingIsland.students);
        this.numProhibitionCards += mergingIsland.numProhibitionCards;

    }

}
