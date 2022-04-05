package it.polimi.ingsw.charactercards;

import it.polimi.ingsw.*;
import it.polimi.ingsw.islands.Island;
import it.polimi.ingsw.player.Player;

import java.util.Map;

/**
 * CharacterCard abstract class models the character cards of the game and their effects
 *
 */
public abstract class CharacterCard {

    private final CharacterID characterID;
    private int cost;

    public CharacterCard (CharacterID characterID) {
        this.characterID = characterID;
        cost = characterID.getInitialCost();
    }

    public CharacterID getCharacterID() {
        return characterID;
    }

    /**
     * method isAvailable establishes if the card can be activated
     * @return  whether the card can be activated
     */
    public boolean isAvailable() {
        return true;
    }

    public int getCost() {
        return cost;
    }

    /**
     * method increaseCost increases the cost of this CharacterCard by 1
     */
    public void increaseCost() {
        cost++;
    }

    /**
     * method effectInfluence calculates an array that will be added to the array of influences of the basic rule
     * that represents the effect that this CharacterCard has on the calculation of the influence on an Island
     * @param players       all the players of the game
     * @param currPlayer    the current Player
     * @param island        the Island on with the influences are being calculated
     * @param clan          the Clan to remove from the calculation of the influence (if needed)
     * @return              an array of points to add to that calculated with the basic rule
     *                      in order to take in account the effect on the influences of this CharacterCard
     */
    public int[] effectInfluence(Player[] players, Player currPlayer, Island island, Clan clan) {
        return new int[Clan.values().length];
    }

    /**
     * method effectStepsMotherNature calculates the number of additional steps
     * on the movement of Mother Nature that this CharacterCard allows
     * @return  the number of additional steps that this CharacterCard allows
     */
    public int effectStepsMotherNature () {
        return 0;
    }

    /**
     * method effectPlayerProfessor calculates the Player that has to own the professor of the Clan given by parameter
     * accordingly with the effect on this of this CharacterCard
     * @param players   all the players of the game
     * @param clan      the Clan of the professor to calculate its owner
     * @return          the Player that has to own the professor of the Clan clan, null in case of a tie
     */
    public Player effectPlayerProfessor (Player[] players, Player currPlayer, Clan clan) {
        return Turn.defaultPlayerProfessor(players, clan);
    }

    /**
     * method applyInitialEffect applies the initial effect of this CharacterCard
     * @param turn      the current Turn
     * @param players   all the Players of this game
     * @return          whether the effect was applied correctly
     */
    public boolean applyInitialEffect (Turn turn, Player[] players) {
        return true;
    }

    /**
     * method applyEffect applies the effect of this CharacterCard
     * on the Island with the specified index
     * @param game      the Game
     * @param island    the Island on which the effect has to be applied
     * @return          whether the effect has been applied
     */
    public boolean applyEffect (Game game, Island island) {
        return false;
    }

    /**
     * method applyEffect applies the effect of this CharacterCard with the given parameters
     * @param game          the Game
     * @param destination   the destination of the students moved (if necessary)
     * @param students1     the students to move to the destination (if necessary)
     * @param students2     the students to move from the destination, in case of an exchange of students (if necessary)
     * @return              whether the effect has been applied
     */
    public boolean applyEffect (Game game, StudentContainer destination, Map<Clan, Integer> students1, Map<Clan, Integer> students2){
        return false;
    }

}
