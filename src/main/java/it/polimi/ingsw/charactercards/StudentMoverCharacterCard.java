package it.polimi.ingsw.charactercards;

import it.polimi.ingsw.Bag;
import it.polimi.ingsw.Clan;
import it.polimi.ingsw.Game;
import it.polimi.ingsw.StudentContainer;
import it.polimi.ingsw.player.Player;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * StudentMoverCharacterCard class models the character cards
 * that move students between objects
 *
 */
public class StudentMoverCharacterCard extends CharacterCard implements StudentContainer{

    private interface StudentMover {
        boolean move(Game game, StudentContainer source, StudentContainer destination, int[] students1, int[] students2);
    }

    private static final int[] NUM_INITIAL_STUDENTS;
    private static final StudentMover[] STUDENT_MOVERS;

    static {
        NUM_INITIAL_STUDENTS = new int[CharacterID.values().length];
        NUM_INITIAL_STUDENTS[CharacterID.MONK.ordinal()] = 4;
        NUM_INITIAL_STUDENTS[CharacterID.JESTER.ordinal()] = 6;
        NUM_INITIAL_STUDENTS[CharacterID.MINISTREL.ordinal()] = 0;
        NUM_INITIAL_STUDENTS[CharacterID.PRINCESS.ordinal()] = 4;
        NUM_INITIAL_STUDENTS[CharacterID.THIEF.ordinal()] = 0;
    }

    static {
        STUDENT_MOVERS = new StudentMover[CharacterID.values().length];
        STUDENT_MOVERS[CharacterID.MONK.ordinal()] = (g, s, d, s1, s2) -> {
            if (IntStream.of(s1).sum() != 1)
                return false;
            int[] v = s.removeStudents(s1);
            if (IntStream.of(v).sum() != 1)
                return false;
            d.addStudents(s1);
            s.addStudents(g.getBag().draw(1));
            return true;
        };
        STUDENT_MOVERS[CharacterID.JESTER.ordinal()] = (g, s, d, s1, s2) -> {
            d = g.getCurrPlayer().getHall();
            if (IntStream.of(s1).sum() > 3)
                return false;
            if (IntStream.of(s1).sum() != IntStream.of(s2).sum())
                return false;
            int[] v1 = s.removeStudents(s1);
            int[] v2 = d.removeStudents(s2);
            if (!Arrays.equals(s1, v1) || !Arrays.equals(s2, v2)) {
                s.addStudents(v1);
                d.addStudents(v2);
                return false;
            }
            d.addStudents(s1);
            s.addStudents(s2);
            return true;
        };
        STUDENT_MOVERS[CharacterID.MINISTREL.ordinal()] = (g, s, d, s1, s2) -> {
            s = g.getCurrPlayer().getChamber();
            d = g.getCurrPlayer().getHall();
            if (IntStream.of(s1).sum() > 2)
                return false;
            if (IntStream.of(s1).sum() != IntStream.of(s2).sum())
                return false;
            int[] v1 = s.removeStudents(s1);
            int[] v2 = d.removeStudents(s2);
            if (!Arrays.equals(s1, v1) || !Arrays.equals(s2, v2)) {
                s.addStudents(v1);
                d.addStudents(v2);
                return false;
            }
            d.addStudents(s1);
            s.addStudents(s2);
            return true;
        };
        STUDENT_MOVERS[CharacterID.PRINCESS.ordinal()] = (g, s, d, s1, s2) -> {
            d = g.getCurrPlayer().getChamber();
            if (IntStream.of(s1).sum() != 1)
                return false;
            int[] v = s.removeStudents(s1);
            if (IntStream.of(v).sum() != 1)
                return false;
            v = d.addStudents(s1);
            if (IntStream.of(v).sum() != 1) {
                s.addStudents(s1);
                return false;
            }
            s.addStudents(g.getBag().draw(1));
            return true;
        };
        STUDENT_MOVERS[CharacterID.THIEF.ordinal()] = (g, s, d, s1, s2) -> {
            if (g.getTurn().getCharacterClan() == null)
                return false;
            int[] v = new int[Clan.values().length];
            v[g.getTurn().getCharacterClan().ordinal()] = 3;
            for (Player p : g.getPlayers())
                g.getBag().addStudents(p.getChamber().removeStudents(v));
            return true;
        };
    }

    private final int[] students;

    public StudentMoverCharacterCard (CharacterID characterID, Bag bag) {
        super(characterID);
        students = bag.draw(NUM_INITIAL_STUDENTS[characterID.ordinal()]);
    }

    @Override
    public boolean applyEffect(Game game, StudentContainer destination, int[] students1, int[] students2) {

        return STUDENT_MOVERS[getCharacterID().ordinal()].move(game, this, destination, students1, students2);

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
//added for tests
    public int[] getStudents() {
        return students.clone();
    }
}
