package it.polimi.ingsw.clouds;

import it.polimi.ingsw.Clan;

public class Cloud {

    private int[] students;
    private boolean picked;

    public Cloud () {
        students = new int[Clan.values().length];
        picked = false;
    }

    /**
     * method isEmpty returns true if the Cloud doesn't contain any student
     * @return  whether the Cloud contains any student
     */
    public boolean isEmpty() {
        for (int n : students)
            if (n != 0)
                return false;

        return true;
    }

    public boolean isPicked(){
        return this.picked;
    }

    public int[] getStudents() {
        return students.clone();
    }

    /**
     * method pick returns the students contained in the Cloud, sets picked to true and empties the students array
     * @return  the students contained in the Cloud
     */
    public int[] pick() {

        int[] stud = null;

        if (!picked) {
            stud = students;
            students = new int[Clan.values().length];
            picked = true;
        }

        return stud;

    }

    /**
     * method fill sets picked to false and puts the students given by parameter in the Cloud
     * @param stud  the students with which the Cloud is filled
     */
    public void fill (int[] stud) {
        students = stud;
        picked = false;
    }

}
