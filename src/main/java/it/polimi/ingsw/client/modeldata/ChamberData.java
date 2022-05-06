package it.polimi.ingsw.client.modeldata;

import it.polimi.ingsw.model.Clan;
import it.polimi.ingsw.model.player.Chamber;

import java.io.Serializable;
import java.util.Map;

public record ChamberData(Map<Clan, Integer> students,
                          Map<Clan, Boolean> professors,
                          int coins,
                          Map<Clan, Integer> coinsGiven) implements Serializable {
    public static ChamberData createChamberData (Chamber chamber){
        return new ChamberData(chamber.getStudents(), chamber.getProfessors(), chamber.getCoins(), chamber.getCoinsGiven());
    }

    @Override
    public String toString() {
        return "ChamberData{" +
                "students=" + students +
                ", professors=" + professors +
                ", coins=" + coins +
                ", coinsGiven=" + coinsGiven +
                '}';
    }
}
