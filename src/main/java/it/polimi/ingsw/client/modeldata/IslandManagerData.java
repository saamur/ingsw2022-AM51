package it.polimi.ingsw.client.modeldata;

import it.polimi.ingsw.model.islands.Island;
import it.polimi.ingsw.model.islands.IslandManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class IslandManagerData implements Serializable {

    private final List<IslandData> islands;
    private int motherNaturePosition;

    public IslandManagerData(List<IslandData> islands, int motherNaturePosition) {
        this.islands = islands;
        this.motherNaturePosition = motherNaturePosition;
    }

    public static IslandManagerData createIslandManagerData(IslandManager islandManager) {
        List<Island> islands = islandManager.getIslands();
        List<IslandData> islandData = new ArrayList<>();
        for (int i = 0; i < islands.size(); i++) {
            islandData.add(i, IslandData.createIslandData(islands.get(i), i));
        }
        return new IslandManagerData(islandData, islands.indexOf(islandManager.getMotherNaturePosition()));
    }

    @Override
    public String toString() {
        return "IslandManagerData{" +
                "islands=" + islands +
                ", motherNaturePosition=" + motherNaturePosition +
                '}';
    }

    public List<IslandData> getIslands() {
        return islands;
    }

    public int getMotherNaturePosition() {
        return motherNaturePosition;
    }

    public void setMotherNaturePosition(int motherNaturePosition) {
        this.motherNaturePosition = motherNaturePosition;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (IslandManagerData) obj;
        return Objects.equals(this.islands, that.islands) &&
                this.motherNaturePosition == that.motherNaturePosition;
    }

    @Override
    public int hashCode() {
        return Objects.hash(islands, motherNaturePosition);
    }

}
