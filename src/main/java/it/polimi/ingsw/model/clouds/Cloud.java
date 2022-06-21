package it.polimi.ingsw.model.clouds;

import it.polimi.ingsw.model.Clan;

import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;

/**
 * The Cloud class models the clouds of the game, with the attributes and the methods
 * needed for their management
 *
 */
public class Cloud implements Serializable {

    private Map<Clan, Integer> students;
    private boolean picked;

    public Cloud () {
        students = new EnumMap<>(Clan.class);
        for (Clan c : Clan.values())
            students.put(c, 0);
        picked = false;
    }

    /**
     * The method isEmpty returns true if the Cloud doesn't contain any student
     * @return  whether the Cloud doesn't contain any student
     */
    public boolean isEmpty() {

        for (Clan c : Clan.values())
            if (students.get(c) > 0)
                return false;

        return true;

    }

    public boolean isPicked(){
        return this.picked;
    }


    /**
     * The method pick returns the students contained in the Cloud, sets picked to true and empties the students array
     * @return  the students contained in the Cloud
     */
    public Map<Clan, Integer> pick() {

        Map<Clan, Integer> stud = null;

        if (!picked) {
            stud = students;
            students = new EnumMap<>(Clan.class);
            for (Clan c : Clan.values())
                students.put(c, 0);
            picked = true;
        }

        return stud;

    }

    /**
     * The method fill sets picked to false and puts the students given by parameter in the Cloud
     * @param stud  the students with which the Cloud is filled
     */
    public void fill (Map<Clan, Integer> stud) {
        students = stud;
        picked = false;
    }

    public Map<Clan, Integer> getStudents(){
        return new EnumMap<>(students);
    }


}
