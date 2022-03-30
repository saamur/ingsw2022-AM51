package it.polimi.ingsw;

public class Chamber implements StudentContainer {

    private final int[] students;
    private final boolean[] hasProfessor;
    private int coins;
    private final int[] coinsGiven;

    public Chamber() {
        students = new int[Clan.values().length];
        hasProfessor = new boolean[Clan.values().length];
        coins = 1;
        coinsGiven = new int[Clan.values().length];
    }

    @Override
    public int[] addStudents(int[] stud) {

        int[] addedStudents = new int[Clan.values().length];

        for (int i = 0; i < students.length; i++) {
            if (students[i] + stud[i] <= 10){
                addedStudents[i] = stud[i];
                students[i] += stud[i];
            }
            else {
                addedStudents[i] = 10 - students[i];
                students[i] = 10;
            }
        }

        updateCoins();

        return addedStudents;
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

    public boolean hasProfessor(Clan clan) {
        return hasProfessor[clan.ordinal()];
    }

    public void setProfessor(Clan clan, boolean value) {
        hasProfessor[clan.ordinal()] = value;
    }

    public int getNumStudents (Clan clan) {
        return students[clan.ordinal()];
    }

    private void updateCoins() {
        for (int i = 0; i < coinsGiven.length; i++) {
            coins += students[i]/3 - coinsGiven[i];
            coinsGiven[i] = Math.max(students[i] / 3, coinsGiven[i]);
        }
    }

}
