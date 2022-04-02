package it.polimi.ingsw;

public interface StudentContainer {
    /**
     * Method addStudents adds, if possible, all the students contained in the parameter,
     * if not, for every Clan, it adds all the students it can;
     * it returns an array with the actual added students
     * @param students  the students to add
     * @return          the actual added students
     */
    int[] addStudents(int[] students);

    /**
     * Method removeStudents removes, if possible, all the students contained in the parameter,
     * if not, for every Clan, it removes all the students it can;
     * it returns an array with the actual removed students
     * @param students  the students to remove
     * @return          the actual removed students
     */
    int[] removeStudents(int[] students);

}
