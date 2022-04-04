package it.polimi.ingsw;

/**
 * StudentContainer interface contains the necessary methods for managing
 * additions and deletions of students in the objects that contain them
 *
 */
public interface StudentContainer {
    /**
     * Method addStudents adds, if possible, all the students contained in the parameter,
     * if not, for every Clan, it adds all the students it can;
     * it returns an array with the students that were effectively added
     * @param students  the students to add
     * @return          the actual added students
     */
    int[] addStudents(int[] students);

    /**
     * Method removeStudents removes, if possible, all the students contained in the parameter,
     * if not, for every Clan, it removes all the students it can;
     * it returns an array with the students that were effectively removed
     * @param students  the students to remove
     * @return          the actual removed students
     */
    int[] removeStudents(int[] students);

}
