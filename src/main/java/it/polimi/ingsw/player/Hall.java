package it.polimi.ingsw.player;

import it.polimi.ingsw.Clan;
import it.polimi.ingsw.StudentContainer;

import java.util.Arrays;

/**
 * Hall class models the hall of the school board with its students
 *
 */
public class Hall implements StudentContainer {

    private final int[] students;

    public Hall(int[] students) {
        this.students = students.clone();
    }

    public int[] getStudents() {
        return students.clone();
    }

    @Override
    public int[] addStudents(int[] stud) {

        for (int i = 0; i < students.length; i++)
            students[i] += stud[i];

        return stud.clone();

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
     * method removeStudent removes a student of the Clan c in the students variable
     * @param c the clan of the student to remove
     * @return  true if the student was removed
     */
    public boolean removeStudent(Clan c) {
        int[] stud = new int[Clan.values().length];
        stud[c.ordinal()] = 1;
        int[] removedStud = removeStudents(stud);
        return Arrays.equals(stud, removedStud);
    }

}
