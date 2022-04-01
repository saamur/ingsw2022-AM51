package it.polimi.ingsw.player;

import it.polimi.ingsw.Clan;
import it.polimi.ingsw.StudentContainer;

import java.util.Arrays;

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

    public int[] getStudents() {
        return students.clone();
    }

    public boolean[] getHasProfessor() {
        return hasProfessor.clone();
    }

    public int getCoins() {
        return coins;
    }

    public int[] getCoinsGiven() {
        return coinsGiven.clone();
    }

    public void setCoins(int coins) {
        this.coins = coins;
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

    public boolean addStudent(Clan c) {
        int[] stud = new int[Clan.values().length];
        stud[c.ordinal()] = 1;
        int[] addedStud = addStudents(stud);
        return Arrays.equals(stud, addedStud);
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
            if (students[i]/3 > coinsGiven[i]) {
                coins += students[i] / 3 - coinsGiven[i];
                coinsGiven[i] = students[i] / 3;
            }
        }
    }

}
