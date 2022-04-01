package it.polimi.ingsw.charactercards;

import it.polimi.ingsw.Bag;
import it.polimi.ingsw.Clan;
import it.polimi.ingsw.Game;
import it.polimi.ingsw.StudentContainer;
import it.polimi.ingsw.player.Player;

public class StudentMoverCharacterCard extends CharacterCard {

    private interface StudentMover {
        boolean move(Game game, StudentContainer destination, int[] students1, int[] students2);
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
        STUDENT_MOVERS[CharacterID.THIEF.ordinal()] = (g, d, s1, s2) -> {
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

        return STUDENT_MOVERS[getCharacterID().ordinal()].move(game, destination, students1, students2);

    }
}
