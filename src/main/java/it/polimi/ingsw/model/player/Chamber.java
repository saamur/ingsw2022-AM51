package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.Clan;
import it.polimi.ingsw.constants.GameConstants;
import it.polimi.ingsw.model.StudentContainer;

import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;

/**
 * The Chamber class models the chamber of the school board, with the students, professors and coins
 * and the methods needed for their management
 *
 */
public class Chamber implements Serializable, StudentContainer {

    private final Map<Clan, Integer> students;
    private final Map<Clan, Boolean> professors;
    private int coins;
    private final Map<Clan, Integer> coinsGiven;

    /**
     * Constructs an empty Chamber with one coin
     */
    public Chamber() {
        students = new EnumMap<>(Clan.class);
        for (Clan c : Clan.values())
            students.put(c, 0);

        professors = new EnumMap<>(Clan.class);
        for (Clan c : Clan.values())
            professors.put(c, false);

        coins = 1;
        coinsGiven = new EnumMap<>(Clan.class);
        for (Clan c : Clan.values())
            coinsGiven.put(c, 0);
    }

    @Override
    public Map<Clan, Integer> addStudents(Map<Clan, Integer> stud) {

        Map<Clan, Integer> addedStudents = new EnumMap<>(Clan.class);

        for (Clan c : Clan.values()) {
            if (students.get(c) + stud.get(c) <= GameConstants.MAX_NUM_STUDENTS_PER_CLAN_CHAMBER) {
                addedStudents.put(c, stud.get(c));
                students.put(c, students.get(c) + stud.get(c));
            }
            else {
                addedStudents.put(c, GameConstants.MAX_NUM_STUDENTS_PER_CLAN_CHAMBER - students.get(c));
                students.put(c, GameConstants.MAX_NUM_STUDENTS_PER_CLAN_CHAMBER);
            }
        }

        updateCoins();

        return addedStudents;
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
     * The method addStudent adds a student of the Clan clan in the students variable
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

    /**
     * The method hasProfessor returns true if this Chamber contains the professor of the Clan given by parameter
     * @param clan  the Clan of the professor
     * @return      whether this Chamber contains the professor of the Clan clan
     */
    public boolean hasProfessor(Clan clan) {
        return professors.get(clan);
    }

    /**
     * The method setProfessor sets the professor of the Clan given by parameter to the boolean value given by parameter
     * @param clan  the Clan of the professor to change
     * @param value the value to which to set the professor of Clan clan
     */
    public void setProfessor(Clan clan, boolean value) {
        professors.put(clan, value);
    }

    /**
     * The method getNumStudents returns the number of the student of the Clan giver by parameter contained in this Chamber
     * @param clan  the Clan of the students to get the number
     * @return      the number of the students contained in this Chamber of Clan clan
     */
    public int getNumStudents (Clan clan) {
        return students.get(clan);
    }

    /**
     * The method updateCoins increases the variable coins after an addition of students accordingly to the rules of the game
     */
    private void updateCoins() {
        for (Clan c : Clan.values()) {
            if (students.get(c)/3 > coinsGiven.get(c)) {
                coins += students.get(c) / 3 - coinsGiven.get(c);
                coinsGiven.put(c, students.get(c)/3);
            }
        }
    }

    public Map<Clan, Integer> getStudents() {
        return new EnumMap<>(students);
    }

    public Map<Clan, Boolean> getProfessors() {
        return new EnumMap<>(professors);
    }

    public int getCoins() {
        return coins;
    }

    public Map<Clan, Integer> getCoinsGiven() {
        return new EnumMap<>(coinsGiven);
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

}
