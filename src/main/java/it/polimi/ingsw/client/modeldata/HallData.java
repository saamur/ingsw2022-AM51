package it.polimi.ingsw.client.modeldata;

import it.polimi.ingsw.model.Clan;
import it.polimi.ingsw.model.player.Hall;

import java.io.Serializable;
import java.util.Map;

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
}
