package it.polimi.ingsw;

import java.util.Arrays;
import java.util.Random;

/**
 * Bag class models the game's bag from which the students are drawn
 *
 */
public class Bag implements StudentContainer {

    private final int[] students;
    private final Random random;

    public Bag () {

        students = new int[Clan.values().length];

        Arrays.fill(students, 24);

        random = new Random();
    }


    public int[] getStudents(){

        return students.clone();
    }

    /**
     * method draw selects a random student contained in the Bag, it removes it and returns its Clan
     * @return the Clan of a random student formerly in the Bag
     */
    public Clan draw() {

        if (isEmpty())
            return null;

        int totStudents = 0;

        for (int n : students)
            totStudents += n;

        int sum = 0;
        int rand = random.nextInt(totStudents);

        for (int i = 0; i < students.length; i++) {

            sum += students[i];

            if(rand < sum) {
                students[i]--;
                return Clan.values()[i];
            }

        }

        return null;

    }

    /**
     * mothod draw selects random students in the Bag and returns them
     * @param n the number of random students to draw
     * @return  the random students that have been drawn
     */
    public int[] draw(int n) {

        int[] stud = new int[Clan.values().length];

        for (int i = 0; i < n; i++) {
            Clan c = draw();
            if (c != null)
                stud[c.ordinal()]++;
        }

        return stud;

    }

    /**
     * method isEmpty returns true if the Bag doesn't contain any student
     * @return  whether the Bag is empty or not
     */
    public boolean isEmpty() {

        for(int n : students)
            if(n > 0)
                return false;

        return true;

    }

    @Override
    public int[] addStudents(int[] stud) {

        for (int i = 0; i < students.length; i++)
                students[i] += stud[i];

        return stud.clone();

    }

    @Override
    public int[] removeStudents(int[] stud) {
        int totStudents = Arrays.stream(stud).sum();
        return draw(totStudents);
    }
}
