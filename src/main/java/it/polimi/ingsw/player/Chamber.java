package it.polimi.ingsw.player;

import it.polimi.ingsw.Clan;
import it.polimi.ingsw.StudentContainer;

import java.util.Arrays;

/**
 * Chamber class models the chamber of the school board, with the students, professors and coins
 * and the methods needed for their management
 *
 */
public class Chamber implements StudentContainer {

    private final int[] students;
    private final boolean[] professors;
    private int coins;
    private final int[] coinsGiven;

    public Chamber() {
        students = new int[Clan.values().length];
        professors = new boolean[Clan.values().length];
        coins = 1;
        coinsGiven = new int[Clan.values().length];
    }

    public int[] getStudents() {
        return students.clone();
    }

    public boolean[] getProfessors() {
        return professors.clone();
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

    /**
     * method addStudent adds a student of the Clan c in the students variable
     * @param c the clan of the student to add
     * @return  true if the student was added
     */
    public boolean addStudent(Clan c) {
        int[] stud = new int[Clan.values().length];
        stud[c.ordinal()] = 1;
        int[] addedStud = addStudents(stud);
        return Arrays.equals(stud, addedStud);
    }

    /**
     * method hasProfessor returns true if this Chamber contains the professor of the Clan given by parameter
     * @param clan  the Clan of the professor
     * @return      whether this Chamber contains the professor of the Clan clan
     */
    public boolean hasProfessor(Clan clan) {
        return professors[clan.ordinal()];
    }

    /**
     * method setProfessor sets the professor of the Clan given by parameter to the boolean value given by parameter
     * @param clan  the Clan of the professor to change
     * @param value the value to which to set the professor of Clan clan
     */
    public void setProfessor(Clan clan, boolean value) {
        professors[clan.ordinal()] = value;
    }

    /**
     * method getNumStudents returns the number of the student of the Clan giver by parameter contained in this Chamber
     * @param clan  the Clan of the students to get the number
     * @return the number of the students contained in this Chamber of Clan clan
     */
    public int getNumStudents (Clan clan) {
        return students[clan.ordinal()];
    }

    /**
     * method updateCoins increases the variable coins after an addition of students accordingly to the rules of the game
     */
    private void updateCoins() {
        for (int i = 0; i < coinsGiven.length; i++) {
            if (students[i]/3 > coinsGiven[i]) {
                coins += students[i] / 3 - coinsGiven[i];
                coinsGiven[i] = students[i] / 3;
            }
        }
    }

}
