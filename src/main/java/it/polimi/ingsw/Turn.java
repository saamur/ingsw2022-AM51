package it.polimi.ingsw;

public class Turn {

    private final Player currPlayer;
    private TurnState turnState;
    private int studentMoved;
    private CharacterCard activatedCharacterCard;
    private Clan characterClan;

    public Turn (Player currPlayer) {
        this.currPlayer = currPlayer;
        turnState = TurnState.STUDENT_MOVING;
        studentMoved = 0;
        activatedCharacterCard = null;
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

    private static int indexUniqueMax (int[] vet) {

        int posMax = 0;
        boolean unique = true;

        for (int i = 1;  i < vet.length; i++) {
            if (vet[i] > vet[posMax]){
                posMax = i;
                unique = true;
            }
            else if (vet[i] == vet[posMax]) {
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

    public CharacterCard getActivatedCharacterCard() {
        return activatedCharacterCard;
    }

    public boolean moveStudentToIsland(Clan c, Island island) {
        if (turnState != TurnState.STUDENT_MOVING)
            return false;

        island.addStudent(c);

        studentMoved++;
        if (studentMoved == 3)
            turnState = TurnState.MOTHER_MOVING;

        return true;

    }

    public boolean moveStudentToChamber(Clan c, Player[] players) {
        if (turnState != TurnState.STUDENT_MOVING)
            return false;

        boolean ok = currPlayer.getChamber().addStudent(c);
        if (!ok)
            return false;

        updateProfessors(players);

        studentMoved++;
        if (studentMoved == 3)
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

        if (turnState != TurnState.MOTHER_MOVING)           //FIXME better to check it in Game
            return;

        int[] influences = new int[players.length];

        for (int i = 0; i < players.length; i++) {

            for (Clan c : Clan.values())
                if(players[i].getChamber().hasProfessor(c))
                    influences[i] += island.getStudents()[c.ordinal()];

            if (players[i] == island.getControllingPlayer())
                influences[i] += island.getNumberOfIslands();

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

}
