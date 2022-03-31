package it.polimi.ingsw.islands;

import it.polimi.ingsw.Clan;
import it.polimi.ingsw.player.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class IslandManager {

    private final List<Island> islands;
    private Island motherNaturePosition;

    public IslandManager() {

        islands = new ArrayList<>();
        for (int i = 0; i < 12; i++)
            islands.add(new Island());

        Random random = new Random();

        motherNaturePosition = islands.get(random.nextInt(islands.size()));

        List<Clan> stud = new ArrayList<>();
        stud.addAll(Arrays.asList(Clan.values()));
        stud.addAll(Arrays.asList(Clan.values()));

        for (int i = 0; i < 12; i++)
            if (islands.get(i) != motherNaturePosition && islands.get((i+6)%12) != motherNaturePosition)
                islands.get(i).addStudent(stud.remove(random.nextInt(stud.size())));

    }

    public Island getMotherNaturePosition() {
        return motherNaturePosition;
    }

    public List<Island> getIslands() {
        return islands;
    }

    public Island getIsland(int index) {
        if (index < 0 || index >= islands.size())
            return null;
        return islands.get(index);
    }

    public int getNumberOfIslands() {
        return islands.size();
    }

    public int distanceFromMotherNature(Island isl) {
        return (islands.indexOf(isl) + islands.size() - islands.indexOf(motherNaturePosition)) % islands.size();
    }

    public void setMotherNaturePosition(Island motherNaturePosition) {
        if(islands.contains(motherNaturePosition))
            this.motherNaturePosition = motherNaturePosition;
    }

    public void conquerIsland (Player p, Island isl) {

        if (p != isl.getControllingPlayer()) {
            if (isl.getControllingPlayer() != null)
                isl.getControllingPlayer().addTowers(isl.getNumberOfIslands());
            p.removeTowers(isl.getNumberOfIslands());
            isl.setControllingPlayer(p);
            checkMerge(isl);
        }

    }

    private void checkMerge(Island isl) {

        if (!islands.contains(isl) || islands.size() < 2)
            return;

        Island prev = islands.get((islands.indexOf(isl)-1+islands.size())%islands.size());
        Island next = islands.get((islands.indexOf(isl)+1)%islands.size());

        if (isl.getControllingPlayer() == prev.getControllingPlayer()) {
            isl.merge(prev);
            islands.remove(prev);
        }

        if (prev == next)
            return;

        if (isl.getControllingPlayer() == next.getControllingPlayer()) {
            isl.merge(next);
            islands.remove(next);
        }

    }

}
