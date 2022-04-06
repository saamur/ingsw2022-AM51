package it.polimi.ingsw.charactercards;

import it.polimi.ingsw.Bag;
import it.polimi.ingsw.Clan;
import it.polimi.ingsw.Game;
import it.polimi.ingsw.StudentContainer;
import it.polimi.ingsw.player.Player;

import java.util.EnumMap;
import java.util.Map;

/**
 * StudentMoverCharacterCard class models the character cards
 * that move students between objects
 *
 */
public class StudentMoverCharacterCard extends CharacterCard implements StudentContainer{

    private interface StudentMover {
        boolean move(Game game, StudentContainer source, StudentContainer destination, Map<Clan, Integer> students1, Map<Clan, Integer> students2);
    }

    private static final Map<CharacterID, Integer> NUM_INITIAL_STUDENTS;
    private static final Map<CharacterID, StudentMover> STUDENT_MOVERS;

    static {
        NUM_INITIAL_STUDENTS = new EnumMap<>(CharacterID.class);
        NUM_INITIAL_STUDENTS.put(CharacterID.MONK, 4);
        NUM_INITIAL_STUDENTS.put(CharacterID.JESTER, 6);
        NUM_INITIAL_STUDENTS.put(CharacterID.MINISTREL, 0);
        NUM_INITIAL_STUDENTS.put(CharacterID.PRINCESS, 4);
        NUM_INITIAL_STUDENTS.put(CharacterID.THIEF, 0);
    }

    static {
        STUDENT_MOVERS = new EnumMap<>(CharacterID.class);
        STUDENT_MOVERS.put(CharacterID.MONK, (g, s, d, s1, s2) -> {
            if (s1.values().stream().mapToInt(i -> i).sum() != 1)
                return false;
            Map<Clan, Integer> m = s.removeStudents(s1);
            if (m.values().stream().mapToInt(i -> i).sum() != 1)
                return false;
            d.addStudents(s1);
            s.addStudents(g.getBag().draw(1));
            return true;
        });
        STUDENT_MOVERS.put(CharacterID.JESTER, (g, s, d, s1, s2) -> {
            d = g.getCurrPlayer().getHall();
            if (s1.values().stream().mapToInt(i -> i).sum() > 3)
                return false;
            if (s1.values().stream().mapToInt(i -> i).sum() != s2.values().stream().mapToInt(i -> i).sum())
                return false;
            Map<Clan, Integer> m1 = s.removeStudents(s1);
            Map<Clan, Integer> m2 = d.removeStudents(s2);
            if (!s1.equals(m1) || !s2.equals(m2)) {
                s.addStudents(m1);
                d.addStudents(m2);
                return false;
            }
            d.addStudents(s1);
            s.addStudents(s2);
            return true;
        });
        STUDENT_MOVERS.put(CharacterID.MINISTREL, (g, s, d, s1, s2) -> {
            s = g.getCurrPlayer().getChamber();
            d = g.getCurrPlayer().getHall();
            if (s1.values().stream().mapToInt(i -> i).sum() > 2)
                return false;
            if (s1.values().stream().mapToInt(i -> i).sum() != s2.values().stream().mapToInt(i -> i).sum())
                return false;
            Map<Clan, Integer> m1 = s.removeStudents(s1);
            Map<Clan, Integer> m2 = d.removeStudents(s2);
            if (!s1.equals(m1) || !s2.equals(m2)) {
                s.addStudents(m1);
                d.addStudents(m2);
                return false;
            }
            d.addStudents(s1);
            s.addStudents(s2);
            return true;
        });
        STUDENT_MOVERS.put(CharacterID.PRINCESS, (g, s, d, s1, s2) -> {
            d = g.getCurrPlayer().getChamber();
            if (s1.values().stream().mapToInt(i -> i).sum() != 1)
                return false;
            Map<Clan, Integer> m = s.removeStudents(s1);
            if (m.values().stream().mapToInt(i -> i).sum() != 1)
                return false;
            m = d.addStudents(s1);
            if (m.values().stream().mapToInt(i -> i).sum() != 1) {
                s.addStudents(s1);
                return false;
            }
            s.addStudents(g.getBag().draw(1));
            return true;
        });
        STUDENT_MOVERS.put(CharacterID.THIEF, (g, s, d, s1, s2) -> {
            if (g.getTurn().getCharacterClan() == null)
                return false;
            Map<Clan, Integer> m = new EnumMap<>(Clan.class);
            for (Clan c : Clan.values())
                m.put(c, c == g.getTurn().getCharacterClan() ? 3 : 0);
            for (Player p : g.getPlayers())
                g.getBag().addStudents(p.getChamber().removeStudents(m));
            return true;
        });
    }

    private final Map<Clan, Integer> students;

    public StudentMoverCharacterCard (CharacterID characterID, Bag bag) {
        super(characterID);
        students = bag.draw(NUM_INITIAL_STUDENTS.get(characterID));
    }

    @Override
    public boolean applyEffect(Game game, StudentContainer destination, Map<Clan, Integer> students1, Map<Clan, Integer> students2) {
        return STUDENT_MOVERS.get(getCharacterID()).move(game, this, destination, students1, students2);
    }

    @Override
    public Map<Clan, Integer> addStudents(Map<Clan, Integer> stud) {
        for (Clan c : Clan.values())
            students.put(c, students.get(c) + stud.get(c));
        return new EnumMap<>(stud);
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
    //added for tests
    public Map<Clan, Integer> getStudents() {
        return new EnumMap<>(students);
    }

}
