package it.polimi.ingsw.islands;

import it.polimi.ingsw.Clan;
import it.polimi.ingsw.player.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * IslandManager class manages the islands of the game, the position of Mother Nature and the merging of islands
 *
 */
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
        return new ArrayList<>(islands);
    }

    /**
     * method getIsland returns the island in the position of the parameter,
     * null if the index is out of bounds
     * @param index the index of the desired Island
     * @return      the Island in position index of the List islands
     */
    public Island getIsland(int index) {
        if (index < 0 || index >= islands.size())
            return null;
        return islands.get(index);
    }

    /**
     * method getNumberOfIslands returns the number of the islands currently present
     * @return  the number of the islands
     */
    public int getNumberOfIslands() {
        return islands.size();
    }

    /**
     * method distanceFromMotherNature calculates the steps needed by Mother Nature to reach the island given by parameter
     * @param isl   the Island for which the method calculates the distance
     * @return      the number of steps needed by Mother Nature to reach the island isl
     */
    public int distanceFromMotherNature(Island isl) {
        return (islands.indexOf(isl) + islands.size() - islands.indexOf(motherNaturePosition)) % islands.size();
    }

    public void setMotherNaturePosition(Island motherNaturePosition) {
        if(islands.contains(motherNaturePosition))
            this.motherNaturePosition = motherNaturePosition;
    }

    /**
     * method conquerIsland changes the controllingPlayer of isl to p,
     * moves the towers of the old and the new controllingPlayer as necessary
     * and merges isl with neighbour islands if required
     * @param p     the new controllingPlayer of isl
     * @param isl   the Island being conquered
     */
    public void conquerIsland (Player p, Island isl) {

        if (p != isl.getControllingPlayer()) {
            if (isl.getControllingPlayer() != null)
                isl.getControllingPlayer().addTowers(isl.getNumberOfTowers());

            if (p.getNumberOfTowers() >= isl.getNumberOfIslands()) {
                p.removeTowers(isl.getNumberOfIslands());
                isl.setNumberOfTowers(isl.getNumberOfIslands());
            }
            else {
                isl.setNumberOfTowers(p.getNumberOfTowers());
                p.removeTowers(p.getNumberOfTowers());
            }

            isl.setControllingPlayer(p);
            checkMerge(isl);
        }

    }

    /**
     * method checkMerge checks if the neighbouring islands of the one given by parameter are controlled by the same player,
     * in that case they get merged
     * @param isl   the island that could be merged with its neighbouring islands
     */
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
