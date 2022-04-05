package it.polimi.ingsw.player;

import it.polimi.ingsw.Clan;
import it.polimi.ingsw.StudentContainer;

import java.util.EnumMap;
import java.util.Map;

/**
 * Hall class models the hall of the school board with its students
 *
 */
public class Hall implements StudentContainer {

    private final Map<Clan, Integer> students;

    public Hall(Map<Clan, Integer> students) {
        this.students = new EnumMap<>(students);
    }

    public Map<Clan, Integer> getStudents() {
        return new EnumMap<>(students);
    }

    @Override
    public Map<Clan, Integer> addStudents(Map<Clan, Integer> stud) {
        for (Clan c : Clan.values())
            students.put(c, students.get(c) + stud.get(c));
        return new EnumMap<>(stud);
    }

    /**
     * method addStudent adds a student of the Clan clan in the students variable
     * @param clan  the clan of the student to add
     * @return      true if the student was added
     */
    public boolean addStudent(Clan clan) {
        Map<Clan, Integer> stud = new EnumMap<>(Clan.class);
        for (Clan c : Clan.values())
            stud.put(c, c == clan ? 1 : 0);
        Map<Clan, Integer> addedStud = addStudents(stud);
        return stud.equals(addedStud);
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
     * method removeStudent removes a student of the Clan c in the students variable
     * @param clan  the clan of the student to remove
     * @return      true if the student was removed
     */
    public boolean removeStudent(Clan clan) {
        Map<Clan, Integer> stud = new EnumMap<>(Clan.class);
        for (Clan c : Clan.values())
            stud.put(c, c == clan ? 1 : 0);
        Map<Clan, Integer> removedStud = removeStudents(stud);
        return stud.equals(removedStud);
    }

}
