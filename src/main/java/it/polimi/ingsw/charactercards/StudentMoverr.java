package it.polimi.ingsw.charactercards;

import it.polimi.ingsw.StudentContainer;

import java.util.Arrays;

public class StudentMoverr {

    private final int numStudents;
    private final StudentContainer fixedSource;
    private final StudentContainer fixedDestination;
    private final int [] fixedStudents1;
    private final int [] fixedStudents2;
    private final boolean isExchange;
    private final boolean lessThenNumStudents;
    private final boolean lessFromSource;

    public StudentMoverr(int numStudents, StudentContainer fixedSource, StudentContainer fixedDestination, int[] fixedStudents1, int[] fixedStudents2, boolean isExchange, boolean lessThenNumStudents, boolean lessFromSource) {
        this.numStudents = numStudents;
        this.fixedSource = fixedSource;
        this.fixedDestination = fixedDestination;
        this.fixedStudents1 = fixedStudents1;
        this.fixedStudents2 = fixedStudents2;
        this.isExchange = isExchange;
        this.lessThenNumStudents = lessThenNumStudents;
        this.lessFromSource = lessFromSource;
    }

    public boolean move (StudentContainer src, StudentContainer dest, int[] std1, int [] std2) {

        StudentContainer source = fixedSource == null ? src : fixedSource;
        StudentContainer destination = fixedDestination == null ? dest : fixedDestination;
        int[] students1 = fixedStudents1 == null ? std1 : fixedStudents1;

        if (totStudents(students1) > numStudents)
            return false;

        if (!lessThenNumStudents && totStudents(students1) < numStudents)
            return false;

        if (isExchange)
            return exchange(source, destination, students1, std2);

        return move (source, destination, students1);

    }

    private int totStudents(int[] students) {

        int sum = 0;

        for (int n : students)
            sum += n;

        return sum;

    }

    private boolean move (StudentContainer source, StudentContainer destination, int[] students) {

        int[] tmpSource = source.removeStudents(students);

        if (!lessFromSource && totStudents(tmpSource) < totStudents((students))) {
            source.addStudents(tmpSource);
            return false;
        }

        int[] tmpDest = destination.addStudents(tmpSource);

        if (!Arrays.equals(tmpSource, tmpDest)) {
            destination.removeStudents(tmpDest);
            source.addStudents(tmpSource);
            return false;
        }

        return true;

    }

    private boolean exchange (StudentContainer source, StudentContainer destination, int[] students1, int[] std2) {

        int[] students2 = fixedStudents2 == null ? std2 : fixedStudents2;

        if (totStudents(students1) != totStudents(students2))
            return false;

        int[] tmpSource = source.removeStudents(students1);

        if (!Arrays.equals(tmpSource, students1)) {
            source.addStudents(tmpSource);
            return false;
        }

        int[] tmpDestination = source.removeStudents(students2);

        if (!Arrays.equals(tmpDestination, students2)) {
            destination.addStudents(tmpDestination);
            source.addStudents(tmpSource);
            return false;
        }

        tmpSource = source.addStudents(students2);

        if (!Arrays.equals(tmpSource, students2)) {
            source.removeStudents(tmpSource);
            destination.addStudents(students2);
            source.addStudents(students1);
            return false;
        }

        tmpDestination = destination.addStudents(students1);

        if (!Arrays.equals(tmpDestination, students1)) {
            destination.removeStudents(tmpDestination);
            source.removeStudents(tmpSource);
            destination.addStudents(students2);
            source.addStudents(students1);
            return false;
        }

        return true;

    }

}
