package it.polimi.ingsw.client.modeldata;

import it.polimi.ingsw.model.Clan;
import it.polimi.ingsw.model.player.Hall;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

/**
 * contains the information of the hall
 * @param students students in the hall
 */

public record HallData(Map<Clan, Integer> students) implements Serializable {
    public static HallData createHallData (Hall hall){
        return new HallData(hall.getStudents());
    }

    @Override
    public String toString() {
        return "HallData{" +
                "students=" + students +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HallData hallData = (HallData) o;
        return students.equals(hallData.students);
    }

    @Override
    public int hashCode() {
        return Objects.hash(students);
    }
}
