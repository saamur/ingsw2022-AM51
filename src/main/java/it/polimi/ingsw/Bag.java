package it.polimi.ingsw;

import java.util.Random;

public class Bag {

    private final int[] students;
    private final Random random;

    public Bag () {

        students = new int[Clan.values().length];

        for (int i = 0; i < students.length; i++)
            students[i] = 24;

        random = new Random();
    }


    public int[] getStudents(){

        return students.clone();
    }

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

    public int[] draw(int n) {

        int[] stud = new int[Clan.values().length];

        for (int i = 0; i < n; i++) {
            Clan c = draw();
            if (c != null)
                stud[c.ordinal()]++;
        }

        return stud;

    }

    public boolean isEmpty() {

        for(int n : students)
            if(n > 0)
                return false;

        return true;

    }

}
