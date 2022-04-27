package it.polimi.ingsw;

import it.polimi.ingsw.model.Clan;
import it.polimi.ingsw.model.player.Player;

import java.util.EnumMap;
import java.util.Map;

public class TestUtil {

    public static Map<Clan, Integer> studentMapCreator(int pixies, int unicorns, int toads, int dragons, int fairies) {

        EnumMap<Clan, Integer> students = new EnumMap<>(Clan.class);
        students.put(Clan.PIXIES, pixies);
        students.put(Clan.UNICORNS, unicorns);
        students.put(Clan.TOADS, toads);
        students.put(Clan.DRAGONS, dragons);
        students.put(Clan.FAIRIES, fairies);

        return students;

    }

    public static Map<Clan, Integer> coinMapCreator(int pixies, int unicorns, int toads, int dragons, int fairies) {

        EnumMap<Clan, Integer> students = new EnumMap<>(Clan.class);
        students.put(Clan.PIXIES, pixies);
        students.put(Clan.UNICORNS, unicorns);
        students.put(Clan.TOADS, toads);
        students.put(Clan.DRAGONS, dragons);
        students.put(Clan.FAIRIES, fairies);

        return students;

    }

    public static Map<Clan, Player> professorMapCreator(Player pixies, Player unicorns, Player toads, Player dragons, Player fairies) {

        EnumMap<Clan, Player> professors = new EnumMap<>(Clan.class);
        professors.put(Clan.PIXIES, pixies);
        professors.put(Clan.UNICORNS, unicorns);
        professors.put(Clan.TOADS, toads);
        professors.put(Clan.DRAGONS, dragons);
        professors.put(Clan.FAIRIES, fairies);

        return professors;

    }

    public static Map<Clan, Integer> professorsIndexMapCreator(Integer pixies, Integer unicorns, Integer toads, Integer dragons, Integer fairies) {

        EnumMap<Clan, Integer> professorIndexes = new EnumMap<>(Clan.class);
        professorIndexes.put(Clan.PIXIES, pixies);
        professorIndexes.put(Clan.UNICORNS, unicorns);
        professorIndexes.put(Clan.TOADS, toads);
        professorIndexes.put(Clan.DRAGONS, dragons);
        professorIndexes.put(Clan.FAIRIES, fairies);

        return professorIndexes;


    }

}
