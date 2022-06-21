package it.polimi.ingsw.model;

import it.polimi.ingsw.constants.GameConstants;

import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;
import java.util.Random;

/**
 * The Bag class models the game's bag from which the students are drawn
 *
 */
public class Bag implements Serializable, StudentContainer {

    private final Map<Clan, Integer> students;
    private final Random random;

    public Bag () {

        students = new EnumMap<>(Clan.class);
        for (Clan c : Clan.values())
            students.put(c, GameConstants.NUM_INITIAL_STUDENTS_PER_CLAN_BAG);

        random = new Random();
    }



    /**
     * The method draw selects a random student contained in the Bag, it removes it and returns its Clan
     * @return the Clan of a random student formerly in the Bag
     */
    public Clan draw() {

        if (isEmpty())
            return null;

        int totStudents = 0;

        for (Clan c : Clan.values())
            totStudents += students.get(c);

        int sum = 0;
        int rand = random.nextInt(totStudents);

        for (Clan c : Clan.values()) {

            sum += students.get(c);

            if (rand < sum) {
                students.put(c, students.get(c) - 1);
                return c;
            }

        }

        return null;

    }

    /**
     * The method draw selects random students in the Bag and returns them
     * @param n the number of random students to draw
     * @return  the random students that have been drawn
     */
    public Map<Clan, Integer> draw(int n) {

        Map<Clan, Integer> stud = new EnumMap<>(Clan.class);
        for (Clan c : Clan.values())
            stud.put(c, 0);

        for (int i = 0; i < n; i++) {
            Clan c = draw();
            if (c != null)
                stud.put(c, stud.get(c) + 1);
        }

        return stud;

    }

    /**
     * The method isEmpty returns true if the Bag doesn't contain any student
     * @return  whether the Bag is empty or not
     */
    public boolean isEmpty() {

        for (Clan c : Clan.values())
            if (students.get(c) > 0)
                return false;

        return true;

    }

    @Override
    public Map<Clan, Integer> addStudents(Map<Clan, Integer> stud) {
        for (Clan c : Clan.values())
            students.put(c, students.get(c) + stud.get(c));
        return new EnumMap<>(stud);
    }

    @Override
    public Map<Clan, Integer> removeStudents(Map<Clan, Integer> stud) {
        int totStudents = 0;
        for (Clan c : Clan.values())
            totStudents += stud.get(c);
        return draw(totStudents);
    }

    //for testing
    public Map<Clan, Integer> getStudents(){
        return new EnumMap<>(students);
    }
}
