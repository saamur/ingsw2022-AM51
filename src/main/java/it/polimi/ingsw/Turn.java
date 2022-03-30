package it.polimi.ingsw;

public class Turn {

    private final Player player;
    private TurnState turnState;
    private CharacterCard activatedCharacterCard;

    public Turn (Player player) {
        this.player = player;
        turnState = TurnState.STUDENT_MOVING;
        activatedCharacterCard = null;
    }

    public static Player defaultPlayerProfessor (Player[] players, Player currPlayer, Clan clan) {

        int[] stud = new int[players.length];

        for (int i = 0; i < stud.length; i++)
            stud[i] = players[i].getChamber().getNumStudents(clan);

        int posMax = 0;
        boolean unique = true;

        for (int i = 1;  i < stud.length; i++) {
            if (stud[i] > stud[posMax]){
                posMax = i;
                unique = true;
            }
            else if (stud[i] == stud[posMax]) {
                unique = false;
            }
        }

        if (unique)
            return players[posMax];

        return null;

    }

}
