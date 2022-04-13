package it.polimi.ingsw;

import it.polimi.ingsw.player.Player;

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

}
