package it.polimi.ingsw.model;

import it.polimi.ingsw.model.charactercards.CharacterCard;
import it.polimi.ingsw.model.clouds.Cloud;
import it.polimi.ingsw.exceptions.NotValidMoveException;
import it.polimi.ingsw.exceptions.WrongTurnPhaseException;
import it.polimi.ingsw.model.islands.Island;
import it.polimi.ingsw.model.islands.IslandManager;
import it.polimi.ingsw.model.player.Player;

import java.io.Serializable;

/**
 * The Turn class contains the main logic for the managing of the turns and their phases,
 * with the attributes and methods needed; it's tightly connected with the Game class
 *
 * @see Game
 *
 */
public class Turn implements Serializable {

    private final Player currPlayer;
    private final int numStudentsToMove;
    private TurnState turnState;
    private int studentMoved;

    private CharacterCard activatedCharacterCard;
    private boolean characterPunctualEffectApplied;
    private Clan characterClan;

    public Turn (Player currPlayer, int numPlayers) {
        this.currPlayer = currPlayer;
        numStudentsToMove = numPlayers == 2 ? 3 : 4;
        turnState = TurnState.STUDENT_MOVING;
        studentMoved = 0;

        activatedCharacterCard = null;
        characterPunctualEffectApplied = false;
        characterClan = null;
    }

    /**
     * The method defaultPlayerProfessor calculates the Player that has to own the professor of the Clan given by parameter
     * @param players   all the players of the game
     * @param clan      the Clan of the professor to calculate its owner
     * @return          the Player that has to own the professor of the Clan clan, null in case of a tie
     */
    public static Player defaultPlayerProfessor (Player[] players, Clan clan) {

        int[] clanStud = new int[players.length];

        for (int i = 0; i < clanStud.length; i++)
            clanStud[i] = players[i].getChamber().getNumStudents(clan);

        int posMax = indexUniqueMax(clanStud);

        if (posMax != -1)
            return players[posMax];

        return null;

    }

    /**
     * The method indexUniqueMax calculates the index of the position in the array given by parameter
     * where is contained the greater value, if this is unique
     * @param array the array on which calculate that index
     * @return the index of the array given by parameter where is contained the greater value, if this is unique, -1 otherwise
     */
    private static int indexUniqueMax (int[] array) {

        int posMax = 0;
        boolean unique = true;

        for (int i = 1;  i < array.length; i++) {
            if (array[i] > array[posMax]){
                posMax = i;
                unique = true;
            }
            else if (array[i] == array[posMax]) {
                unique = false;
            }
        }

        if (unique)
            return posMax;

        return -1;

    }

    /**
     * The method moveStudentToIsland, if turnState is STUDENT_MOVING, tries to move a student
     * from the Hall of currPlayer to an Island.
     * If the maximum number of students moved has been reached turnState is updated to MOTHER_MOVING
     * @param clan      the Clan of the student to move
     * @param island    the Island on to which move the student
     * @throws WrongTurnPhaseException  when it is called not during the student moving phase
     * @throws NotValidMoveException    when there is no student of the given clan in the hall of the current Player
     */
    public void moveStudentToIsland(Clan clan, Island island) throws WrongTurnPhaseException, NotValidMoveException {

        if (turnState != TurnState.STUDENT_MOVING) throw new WrongTurnPhaseException("The turn is not in the student moving phase");

        boolean ok = currPlayer.getHall().removeStudent(clan);
        if(!ok) throw new NotValidMoveException("No " + clan.name().toLowerCase() + " in the hall");

        island.addStudent(clan);

        studentMoved++;
        if (studentMoved == numStudentsToMove)
            turnState = TurnState.MOTHER_MOVING;

    }

    /**
     * The method moveStudentToChamber, if turnState is STUDENT_MOVING, tries to move a student
     * from the Hall to the Chamber of currPlayer.
     * If the maximum number of students moved has been reached turnState is updated to MOTHER_MOVING
     * The ownership of the professors get updated
     * @param clan      the Clan of the student to move
     * @param players   all the players of the game
     * @throws WrongTurnPhaseException  when it is called not during the student moving phase
     * @throws NotValidMoveException    when there is no student of the given clan in the hall of the current Player
     *                                  or the chamber has already the maximum number of students of the given clan
     */
    public void moveStudentToChamber(Clan clan, Player[] players) throws WrongTurnPhaseException, NotValidMoveException {

        if (turnState != TurnState.STUDENT_MOVING) throw new WrongTurnPhaseException("The turn is not in the student moving phase");

        boolean ok = currPlayer.getHall().removeStudent(clan);
        if(!ok) throw new NotValidMoveException("No " + clan.name().toLowerCase() + " in the hall");

        ok = currPlayer.getChamber().addStudent(clan);
        if (!ok) {
            currPlayer.getHall().addStudent(clan);
            throw new NotValidMoveException("No places left for " + clan.name().toLowerCase() + " in the chamber");
        }

        updateProfessors(players);

        studentMoved++;
        if (studentMoved == numStudentsToMove)
            turnState = TurnState.MOTHER_MOVING;

    }

    /**
     * The method updateProfessors updates the ownership of the professors
     * @param players   the players of the game
     */
    public void updateProfessors (Player[] players) {

        for (Clan c : Clan.values()) {

            Player player;

            if (activatedCharacterCard == null)
                player = defaultPlayerProfessor(players, c);
            else
                player = activatedCharacterCard.effectPlayerProfessor(players, currPlayer, c);

            if (player != null)
                for (Player p : players)
                    p.getChamber().setProfessor(c, p == player);

        }

    }

    /**
     * The method getMaxStepsMotherNature calculates the maximum number of steps that Mother Nature can do in this turn
     * @return  the maximum number of steps that Mother Nature can do in this turn
     */
    public int getMaxStepsMotherNature() {

        int maxSteps = currPlayer.getCurrCard().getMaxStepsMotherNature();

        if (activatedCharacterCard != null)
            maxSteps += activatedCharacterCard.effectStepsMotherNature();

        return maxSteps;

    }

    /**
     * The method updateInfluence calculates the influences of the players on a given Island
     * and gives its control to the player with the maximum influence, if it exists
     * @param islandManager the islandManager of the game
     * @param island        the island on to which the method updates the control
     * @param players       all the players of the game
     */
    public void updateInfluence (IslandManager islandManager, Island island, Player[] players) {

        int[] influences = new int[players.length];

        for (int i = 0; i < players.length; i++) {

            for (Clan c : Clan.values())
                if(players[i].getChamber().hasProfessor(c))
                    influences[i] += island.getStudents().get(c);

            if (players[i] == island.getControllingPlayer())
                influences[i] += island.getNumberOfTowers();

        }

        if (activatedCharacterCard != null) {
            int[] characterInfluences = activatedCharacterCard.effectInfluence(players, currPlayer, island, characterClan);
            for (int i = 0; i < players.length; i++)
                influences[i] += characterInfluences[i];
        }

        int posMax = indexUniqueMax(influences);

        if(posMax != -1)
            islandManager.conquerIsland(players[posMax], island);

    }

    /**
     * The method chooseCloud, if turnState is CLOUD_CHOOSING, picks the students on the given cloud
     * and puts them in the Hall of the currPlayer, then sets turnState to END_TURN
     * @param cloud the Cloud from which the students will be taken
     * @throws WrongTurnPhaseException  when it is called not during the cloud choosing phase
     * @throws NotValidMoveException    when the given cloud has already been picked
     */
    public void chooseCloud (Cloud cloud) throws WrongTurnPhaseException, NotValidMoveException {

        if (turnState != TurnState.CLOUD_CHOOSING) throw new WrongTurnPhaseException("The turn is not in the cloud choosing phase");
        if (cloud.isPicked()) throw new NotValidMoveException("This cloud has already been picked");

        currPlayer.getHall().addStudents(cloud.pick());

        turnState = TurnState.END_TURN;

    }

    public TurnState getTurnState() {
        return turnState;
    }

    public void setTurnState(TurnState turnState) {
        this.turnState = turnState;
    }


    public CharacterCard getActivatedCharacterCard() {
        return activatedCharacterCard;
    }

    public void setActivatedCharacterCard(CharacterCard activatedCharacterCard) {
        this.activatedCharacterCard = activatedCharacterCard;
    }

    public boolean isCharacterPunctualEffectApplied() {
        return characterPunctualEffectApplied;
    }

    public void setCharacterClan(Clan characterClan) {
        this.characterClan = characterClan;
    }

    public Clan getCharacterClan() {
        return characterClan;
    }

    /**
     * The method characterPunctualEffectApplied saves in the variable characterPunctualEffectApplied that the effect of the Character
     * has been applied
     */
    public void characterPunctualEffectApplied() {
        characterPunctualEffectApplied = true;
    }

}
