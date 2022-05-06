package it.polimi.ingsw.client.modeldata;

import it.polimi.ingsw.model.Clan;
import it.polimi.ingsw.model.islands.Island;
import it.polimi.ingsw.model.player.TowerColor;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

public record IslandData(int numberOfIslands,
                         Map<Clan, Integer> students,
                         TowerColor towerColor,
                         int numberOfTowers,
                         int numProhibitionCards, int islandIndex) implements Serializable {
    public static IslandData createIslandData(Island island, int islandIndex){
        return new IslandData(island.getNumberOfIslands(), island.getStudents(), (island.getControllingPlayer() == null ? null : island.getControllingPlayer().getColorOfTowers()), island.getNumberOfTowers(), island.getNumProhibitionCards(), islandIndex);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IslandData that = (IslandData) o;
        return numberOfIslands == that.numberOfIslands && numberOfTowers == that.numberOfTowers && numProhibitionCards == that.numProhibitionCards && islandIndex == that.islandIndex && students.equals(that.students) && towerColor == that.towerColor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numberOfIslands, students, towerColor, numberOfTowers, numProhibitionCards, islandIndex);
    }

    @Override
    public String toString() {
        return "IslandData{" +
                "numberOfIslands=" + numberOfIslands +
                ", students=" + students +
                ", towerColor=" + towerColor +
                ", numberOfTowers=" + numberOfTowers +
                ", numProhibitionCards=" + numProhibitionCards +
                ", islandIndex=" + islandIndex +
                '}';
    }
}
