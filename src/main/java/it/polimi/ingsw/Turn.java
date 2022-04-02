package it.polimi.ingsw;

import it.polimi.ingsw.charactercards.CharacterCard;
import it.polimi.ingsw.clouds.Cloud;
import it.polimi.ingsw.islands.Island;
import it.polimi.ingsw.islands.IslandManager;
import it.polimi.ingsw.player.Player;

public class Turn {

    private final Player currPlayer;
    private final int numStudentsToMove;
    private TurnState turnState;
    private int studentMoved;

    private CharacterCard activatedCharacterCard;
    private boolean characterEffectApplied;
    private Clan characterClan;

    public Turn (Player currPlayer, int numPlayers) {
        this.currPlayer = currPlayer;
        numStudentsToMove = numPlayers == 2 ? 3 : 4;
        turnState = TurnState.STUDENT_MOVING;
        studentMoved = 0;

        activatedCharacterCard = null;
        characterEffectApplied = false;
        characterClan = null;
    }

    public static Player defaultPlayerProfessor (Player[] players, Clan clan) {

        int[] stud = new int[players.length];

        for (int i = 0; i < stud.length; i++)
            stud[i] = players[i].getChamber().getNumStudents(clan);

        int posMax = indexUniqueMax(stud);

        if (posMax != -1)
            return players[posMax];

        return null;

    }

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

    public TurnState getTurnState() {
        return turnState;
    }

    public void setTurnState(TurnState turnState) {
        this.turnState = turnState;
    }

    public boolean moveStudentToIsland(Clan clan, Island island) {

        if (turnState != TurnState.STUDENT_MOVING)
            return false;

        boolean ok = currPlayer.getHall().removeStudent(clan);
        if(!ok)
            return false;

        island.addStudent(clan);

        studentMoved++;
        if (studentMoved == numStudentsToMove)
            turnState = TurnState.MOTHER_MOVING;

        return true;

    }

    public boolean moveStudentToChamber(Clan clan, Player[] players) {

        if (turnState != TurnState.STUDENT_MOVING)
            return false;

        boolean ok = currPlayer.getHall().removeStudent(clan);
        if(!ok)
            return false;

        ok = currPlayer.getChamber().addStudent(clan);
        if (!ok) {
            currPlayer.getHall().addStudent(clan);
            return false;
        }

        updateProfessors(players);

        studentMoved++;
        if (studentMoved == numStudentsToMove)
            turnState = TurnState.MOTHER_MOVING;

        return true;

    }

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

    public int getMaxStepsMotherNature () {

        int maxSteps = currPlayer.getCurrCard().getMaxStepsMotherNature();

        if (activatedCharacterCard != null)
            maxSteps += activatedCharacterCard.effectStepsMotherNature();

        return maxSteps;
    }

    public void updateInfluence (IslandManager islandManager, Island island, Player[] players) {

        int[] influences = new int[players.length];

        for (int i = 0; i < players.length; i++) {

            for (Clan c : Clan.values())
                if(players[i].getChamber().hasProfessor(c))
                    influences[i] += island.getStudents()[c.ordinal()];

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

        turnState = TurnState.CLOUD_CHOOSING;           //FIXME not when called from a character, so to do in Game

    }

    public boolean chooseCloud (Cloud cloud) {

        if (turnState != TurnState.CLOUD_CHOOSING)
            return false;

        if (cloud.isPicked())
            return false;

        currPlayer.getHall().addStudents(cloud.pick());

        return true;

    }


    public CharacterCard getActivatedCharacterCard() {
        return activatedCharacterCard;
    }

    public boolean isCharacterEffectApplied() {
        return characterEffectApplied;
    }

    public void setActivatedCharacterCard(CharacterCard activatedCharacterCard) {
        this.activatedCharacterCard = activatedCharacterCard;
    }

    public void setCharacterClan(Clan characterClan) {
        this.characterClan = characterClan;
    }

    public Clan getCharacterClan() {
        return characterClan;
    }

    public void characterEffectApplied() {
        characterEffectApplied = true;
    }

}
