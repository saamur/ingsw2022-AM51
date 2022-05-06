package it.polimi.ingsw.client.modeldata;

import it.polimi.ingsw.model.Clan;
import it.polimi.ingsw.model.player.Chamber;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

public record ChamberData(Map<Clan, Integer> students,
                          Map<Clan, Boolean> professors,
                          int coins,
                          Map<Clan, Integer> coinsGiven) implements Serializable {
    public static ChamberData createChamberData (Chamber chamber){
        return new ChamberData(chamber.getStudents(), chamber.getProfessors(), chamber.getCoins(), chamber.getCoinsGiven());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChamberData that = (ChamberData) o;
        return coins == that.coins && students.equals(that.students) && professors.equals(that.professors) && coinsGiven.equals(that.coinsGiven);
    }

    @Override
    public int hashCode() {
        return Objects.hash(students, professors, coins, coinsGiven);
    }

}
