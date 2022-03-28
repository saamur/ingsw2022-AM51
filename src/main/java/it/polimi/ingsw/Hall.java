package it.polimi.ingsw;

public class Hall implements StudentContainer {

    private final int[] students;

    public Hall(int[] students) {
        this.students = students;
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

}
