package it.polimi.ingsw;

public class Cloud {

    private int[] students;
    private boolean picked;

    public Cloud () {
        students = new int[Clan.values().length];
        picked = false;
    }


    public boolean isEmpty() {
        for (int n : students)
            if (n != 0)
                return false;

        return true;
    }

    public boolean getPicked(){
        return this.picked;
    }

    public int[] getStudents() {
        return students.clone();
    }

    public int[] pick() {

        int[] stud = null;

        if (!picked) {
            stud = students;
            students = new int[Clan.values().length];
            picked = true;
        }

        return stud;

    }

    public void fill (int[] stud) {
        students = stud;
        picked = false;
    }

}
