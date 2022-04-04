package it.polimi.ingsw;

import it.polimi.ingsw.charactercards.CharacterCard;
import it.polimi.ingsw.charactercards.CharacterCardCreator;
import it.polimi.ingsw.charactercards.CharacterID;
import it.polimi.ingsw.charactercards.ProhibitionCharacterCard;
import it.polimi.ingsw.clouds.Cloud;
import it.polimi.ingsw.clouds.CloudManager;
import it.polimi.ingsw.islands.Island;
import it.polimi.ingsw.islands.IslandManager;
import it.polimi.ingsw.player.Card;
import it.polimi.ingsw.player.Player;
import it.polimi.ingsw.player.TowerColor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

/**
 * Game class contains the main logic of the game Eriantys with the necessary methods for the handling of its phases,
 * initialization and winners determination included
 *
 */
public class Game implements GameInterface {

    private static final Function<Player, Integer>[] WINNING_SCORE_CALCULATORS = new Function[2];

    static {
        WINNING_SCORE_CALCULATORS[0] = p -> (-1) * p.getNumberOfTowers();
        WINNING_SCORE_CALCULATORS[1] = p -> {
            int numProfessors = 0;
            for (Clan c : Clan.values())
                if (p.getChamber().hasProfessor(c))
                    numProfessors++;
            return numProfessors;
        };
    }

    private GameState gameState;

    private final IslandManager islandManager;
    private final CloudManager cloudManager;
    private final Bag bag;

    private final Player[] players;
    private final Player[] playersOrder;
    private int indexCurrFirstPlayer;
    private int indexNextFirstPlayer;
    private int indexCurrPlayer;

    private Turn turn;

    private final boolean expertModeEnabled;
    private final CharacterCard[] availableCharacterCards;

    private boolean lastRound;
    private List<Player> winners;


    public Game (int numPlayers, String nicknameFirstPlayer, boolean expertModeEnabled) {

        gameState = GameState.INITIALIZATION;

        islandManager = new IslandManager();
        cloudManager = new CloudManager(numPlayers);
        bag = new Bag();

        players = new Player[numPlayers];
        players[0] = new Player(nicknameFirstPlayer, TowerColor.values()[0], numPlayers, bag);
        playersOrder = new Player[numPlayers];
        indexCurrPlayer = 0;

        this.expertModeEnabled = expertModeEnabled;

        if (expertModeEnabled) {
            CharacterCardCreator creator = new CharacterCardCreator();
            Random random = new Random();
            availableCharacterCards = new CharacterCard[3];
            int i = 0;
            while (i < 3) {
                CharacterID c = CharacterID.values()[random.nextInt(CharacterID.values().length)];
                boolean ok = true;
                for (int j = 0; j < i; j++)
                    if (c == availableCharacterCards[j].getCharacterID()) {
                        ok = false;
                        break;
                    }
                if (ok) {
                    availableCharacterCards[i] = creator.createCharacterCard(c, bag);
                    i++;
                }
            }
        }
        else
            availableCharacterCards = null;

        lastRound = false;
        winners = null;

    }

     // added for tests


    public int getIndexCurrFirstPlayer() {
        return indexCurrFirstPlayer;
    }

    public int getIndexCurrPlayer() {
        return indexCurrPlayer;
    }

    public int getIndexNextFirstPlayer() {
        return indexNextFirstPlayer;
    }


    /**
     * method addPlayer adds a Player to the Game if there isn't already another with the same nickname
     * @param nickname  the nickname that will be assigned to the new Player
     * @return          whether the nickname wasn't already used and the Player was consequently added
     */
    @Override
    public boolean addPlayer (String nickname) {

        if (gameState != GameState.INITIALIZATION)
            return false;

        boolean ok = true;

        for (int i = 0; i <= indexCurrPlayer; i++) {
            if (nickname.equals(players[i].getNickname())) {
                ok = false;
                break;
            }
        }

        if (!ok)
            return false;

        indexCurrPlayer++;
        players[indexCurrPlayer] = new Player(nickname, TowerColor.values()[indexCurrPlayer], players.length, bag);

        if (indexCurrPlayer == players.length-1)
            start();

        return true;

    }

    /**
     * method start selects a random player to begin the game and calls initRound
     */
    private void start() {

        Random random = new Random();
        indexNextFirstPlayer = random.nextInt(players.length);

        initRound();

    }

    /**
     * method initRound calls calculateWin if lastRound is true, otherwise it sets gameState to PIANIFICATION
     * and fills the Clouds
     */
    private void initRound() {

        if (lastRound) {
            calculateWin();
            return;
        }

        indexCurrFirstPlayer = indexNextFirstPlayer;
        gameState = GameState.PIANIFICATION;
        indexCurrPlayer = indexCurrFirstPlayer;

        cloudManager.fillClouds(bag);

        if (bag.isEmpty())
            lastRound = true;

    }





    /**
     * method sets currCard of the currentPlayer to card if the game phase is correct,
     * the choice is valid and playerNickname is the nickname of the current Player.
     * If every Player has chosen a Card the method calls createOrderActionPhase and initActionPhase
     * @param playerNickname    the nickname of the Player that called this method
     * @param card              the chosen Card
     * @return                  whether currCard of the current Player was set to card or not
     */
    @Override
    public boolean chosenCard (String playerNickname, Card card) {

        Player player = playerFromNickname(playerNickname);
        if (player == null)
            return false;

        if (gameState != GameState.PIANIFICATION || player != players[indexCurrPlayer])
            return false;

        if (!validCard(player, card))
            return false;

        boolean ok = player.chooseCard(card);

        if (!ok)
            return false;                      //never executed if everything works correctly

        if (player.getDeck().getCards().isEmpty())
            lastRound = true;

        indexCurrPlayer = (indexCurrPlayer + 1) % players.length;

        if (indexCurrPlayer == indexCurrFirstPlayer) {
            createOrderActionPhase();
            initActionPhase();
        }

        return true;

    }

    public GameState getGameState() {
        return gameState;
    }

    /**
     * method validCard calculates whether a Player can use a certain Card
     * based on the Cards used in this round by the other Players
     * @param player    the Player for which the method determines if the choice is valid
     * @param card      the Card chosen
     * @return          whether the choice is valid
     */
    private boolean validCard (Player player, Card card) {

        List<Card> otherPlayersCards = new ArrayList<>();


        for (int i = indexCurrFirstPlayer; players[i%players.length] != player; i++)
            otherPlayersCards.add(players[i%players.length].getCurrCard());

        if (!otherPlayersCards.contains(card))
            return true;

        List<Card> remainingCards = player.getDeck().getCards();
        remainingCards.removeAll(otherPlayersCards);

        return remainingCards.isEmpty();

    }

    /**
     * method createOrderActionPhase fills the attribute playersOrder and the attribute nextFirstPlayer
     * based on the cards chosen by the Players in this round
     */
    private void createOrderActionPhase() {             //TODO needs deep testing

        List<Player> order = new ArrayList<>();

        for (int i = 0; i < players.length; i++) {

            int playerIndex = (indexCurrFirstPlayer+i) % players.length;
            int priorityPlayerCard = players[playerIndex].getCurrCard().getPriority();

            int pos = 0;
            while (pos < order.size() && priorityPlayerCard >= order.get(pos).getCurrCard().getPriority())
                pos++;

            order.add(pos, players[playerIndex]);

        }

        for (int i = 0; i < players.length; i++)
            playersOrder[i] = order.get(i);

        for (int i = 0; i < players.length; i++)
            if (players[i] == playersOrder[0]) {
                indexNextFirstPlayer = i;
                break;
            }

    }

    public Player[] getPlayersOrder() {
        return playersOrder;
    }

    /**
     * method initActionPhase sets gameState to ACTION, sets indexCurrPlayer to 0
     * and instantiates a Turn for the first Player of this phase
     */
    private void initActionPhase() {

        gameState = GameState.ACTION;
        indexCurrPlayer = 0;
        turn = new Turn(playersOrder[0], players.length);

    }

    /**
     * method moveStudentToChamber moves a student of the specified Clan from the Hall to the Chamber of the current Player
     * @param playerNickname    the nickname of the Player that requested this move
     * @param clan              the Clan of the student to move
     * @return                  whether the move was valid and the student was actually moved
     */
    @Override
    public boolean moveStudentToChamber (String playerNickname, Clan clan) {

        Player player = playerFromNickname(playerNickname);
        if (player == null)
            return false;

        if (gameState != GameState.ACTION || player != players[indexCurrPlayer])
            return false;

        return turn.moveStudentToChamber(clan, players);

    }

    /**
     * method moveStudentToChamber moves a student of the specified Clan from the Hall of the current Player
     * to the Island with the specified index
     * @param playerNickname    the nickname of the Player that requested this move
     * @param clan              the Clan of the student to move
     * @param islandIndex       the index of the Island on which to move the student
     * @return                  whether the move was valid and the student was actually moved
     */
    @Override
    public boolean moveStudentToIsland (String playerNickname, Clan clan, int islandIndex) {

        Player player = playerFromNickname(playerNickname);
        Island island = islandManager.getIsland(islandIndex);
        if (player == null || island == null)
            return false;

        if (gameState != GameState.ACTION || player != players[indexCurrPlayer])
            return false;

        return turn.moveStudentToIsland(clan, island);

    }

    /**
     * method moveMotherNature moves Mother Nature on the Island with the specified index,
     * calls checkInfluence and updates the state of the Turn.
     * If it's the last round it calls endTurn
     * @param playerNickname    the nickname of the Player that requested this move
     * @param islandIndex       the index of the Island on which to move Mother Nature
     * @return                  whether the move is valid and has been carried out
     */
    @Override
    public boolean moveMotherNature (String playerNickname, int islandIndex) {

        Player player = playerFromNickname(playerNickname);
        Island island = islandManager.getIsland(islandIndex);
        if (player == null || island == null)
            return false;

        if (gameState != GameState.ACTION || player != players[indexCurrPlayer])
            return false;

        if (turn.getTurnState() != TurnState.MOTHER_MOVING)
            return false;

        if (islandManager.distanceFromMotherNature(island) > turn.getMaxStepsMotherNature())
            return false;

        islandManager.setMotherNaturePosition(island);
        checkInfluence(island);

        if (lastRound)
            turn.setTurnState(TurnState.END_TURN);
        else
            turn.setTurnState(TurnState.CLOUD_CHOOSING);

        return true;

    }

    /**
     * method checkInfluence, if the given Island doesn't contain any prohibition card,
     * updates the controlling Player of that Island performing all its consequences
     * and check if a winning condition is met.
     * If the Island does contain a prohibition card it is removed and reassigned to the corresponding characterCard
     * @param island    the Island on which the controlling Player is updated
     */
    public void checkInfluence (Island island) {             //FIXME we have to find a better name

        if (island.getNumProhibitionCards() > 0) {
            island.removeProhibitionCard();
            reassignProhibitionCard();
            return;
        }

        turn.updateInfluence(islandManager, island, players);

        if (players[indexCurrPlayer].getNumberOfTowers() <= 0) {
            gameState = GameState.GAME_OVER;
            winners = new ArrayList<>();
            winners.add(players[indexCurrPlayer]);
        }
        else if (islandManager.getNumberOfIslands() <= 3)
            calculateWin();

    }

    /**
     * method chosenCloud, if the choice is valid, adds the students on the Cloud with the given index
     * to the Hall of the current Player
     * @param playerNickname    the nickname of the Player that requested this move
     * @param cloudIndex        the index of the chosen Cloud
     * @return                  whether the move is valid and has been carried out
     */
    @Override
    public boolean chosenCloud (String playerNickname, int cloudIndex) {

        Player player = playerFromNickname(playerNickname);
        Cloud cloud = cloudManager.getCloud(cloudIndex);
        if (player == null || cloud == null)
            return false;

        if (gameState != GameState.ACTION || player != players[indexCurrPlayer])
            return false;

        return turn.chooseCloud(cloud);

    }

    /**
     * method endTurn calls InitRound if the current Player is the last one of this round phase,
     * otherwise it instantiates a new Turn for the next Player
     */
    @Override
    public boolean endTurn(String playerNickname) {

        Player player = playerFromNickname(playerNickname);
        if (player == null)
            return false;

        if (gameState != GameState.ACTION || player != players[indexCurrPlayer])
            return false;

        if (turn.getTurnState() != TurnState.END_TURN)
            return false;

        indexCurrPlayer++;

        if (gameState == GameState.GAME_OVER)
            return true;

        if (indexCurrPlayer == players.length)
            initRound();
        else
            turn = new Turn(playersOrder[indexCurrPlayer], players.length);

        return true;

    }

    /**
     * method calculateWin sets the winners variable with the List of the winning Players
     * and sets gameState to GAME_OVER
     */
    private void calculateWin() {

        gameState = GameState.GAME_OVER;

        List<Player> potentialWinners = new ArrayList<>(Arrays.asList(players));

        for (Function<Player, Integer> f : WINNING_SCORE_CALCULATORS) {

            int i = 0;
            while(i+1 < potentialWinners.size()) {

                int score1 = f.apply(potentialWinners.get(i));
                int score2 = f.apply(potentialWinners.get(i+1));

                if (score1 > score2)
                    potentialWinners.remove(i+1);
                else if (score1 == score2)
                    i++;
                else {
                    potentialWinners.subList(0, i+1).clear();
                    i = 0;
                }

            }

            if (potentialWinners.size() == 1) {
                winners = potentialWinners;
                return;
            }

        }

        winners = potentialWinners;

    }


    /**
     * method activateCharacterCard, if the move is valid,
     * activates the CharacterCard with that CharacterID for the current turn
     * and performs any potential initial effect
     * @param playerNickname    the nickname of the Player that requested this move
     * @param characterID       the CharacterID of the CharacterCard to activate
     * @return                  whether the move is valid and the CharacterCard has been activated
     */
    @Override
    public boolean activateCharacterCard (String playerNickname, CharacterID characterID) {

        Player player = playerFromNickname(playerNickname);
        CharacterCard characterCard = characterCardFromID(characterID);

        if (player == null || characterCard == null)
            return false;

        if (!expertModeEnabled)
            return false;

        if (gameState != GameState.ACTION || player != players[indexCurrPlayer])
            return false;

        if (turn.getActivatedCharacterCard() != null)
            return false;

        if (player.getCoins() < characterCard.getCost())
            return false;

        if (!characterCard.isAvailable())                   //for example GRANDMA can't be used if there aren't prohibition cards on it
            return false;

        player.pay(characterCard.getCost());
        characterCard.increaseCost();

        turn.setActivatedCharacterCard(characterCard);
        characterCard.applyInitialEffect(turn, players);

        return true;

    }

    /**
     * method applyCharacterEffect applies the effect of the CharacterCard currently active
     * on the Island with the specified index
     * @param playerNickname    the nickname of the Player that requested this move
     * @param islandIndex       the index of the Island on which the effect has to be applied
     * @return                  whether the move is valid and has been carried out
     */
    @Override
    public boolean applyCharacterCardEffect (String playerNickname, int islandIndex) {

        Player player = playerFromNickname(playerNickname);
        Island island = islandManager.getIsland(islandIndex);
        if (player == null || island == null)
            return false;

        if (!expertModeEnabled)             //redundant
            return false;

        if (gameState != GameState.ACTION || player != players[indexCurrPlayer])
            return false;

        if (turn.getActivatedCharacterCard() == null)
            return false;

        if (turn.isCharacterEffectApplied())
            return false;

        boolean ok = turn.getActivatedCharacterCard().applyEffect(this, island);

        if (!ok)
            return false;

        turn.characterEffectApplied();

        return true;

    }

    /**
     * method setClanCharacter calls the method setCharacterClan on the current Turn, setting a Clan variable
     * necessary for some CharacterCards to perform their effects
     * @param playerNickname    the nickname of the Player that requested this move
     * @param clan              the Clan chosen by the Player
     * @return                  whether the move was valid and has been carried out
     */
    @Override
    public boolean setClanCharacter (String playerNickname, Clan clan) {

        Player player = playerFromNickname(playerNickname);

        if (player == null)
            return false;

        if (gameState != GameState.ACTION || player != players[indexCurrPlayer])
            return false;

        if (turn.isCharacterEffectApplied())
            return false;

        turn.setCharacterClan(clan);

        if (turn.getActivatedCharacterCard().getCharacterID() == CharacterID.THIEF) {
            return applyCharacterCardEffect(playerNickname, null, null, null);
        }

        return true;

    }

    /**
     * method applyCharacterEffect applies the effect of the CharacterCard currently active with the given parameters
     * @param playerNickname    the nickname of the Player that requested this move
     * @param destination       the destination of the students moved (if necessary)
     * @param students1         the students to move to the destination (if necessary)
     * @param students2         the students to move from the destination, in case of an exchange of students (if necessary)
     * @return                  whether the move was valid and has been carried out
     */
    @Override
    public boolean applyCharacterCardEffect (String playerNickname, StudentContainer destination, int[] students1, int[] students2) {

        Player player = playerFromNickname(playerNickname);
        if (player == null)
            return false;

        if (!expertModeEnabled)             //redundant
            return false;

        if (gameState != GameState.ACTION || player != players[indexCurrPlayer])
            return false;

        if (turn.getActivatedCharacterCard() == null)
            return false;

        if (turn.isCharacterEffectApplied())
            return false;

        boolean ok = turn.getActivatedCharacterCard().applyEffect(this, destination, students1, students2);

        if (!ok)
            return false;

        turn.characterEffectApplied();

        if (bag.isEmpty())
            lastRound = true;

        return true;

    }

    /**
     * method reassignProhibitionCard find the characterCard that can accept prohibition cards and adds it to it
     */
    private void reassignProhibitionCard() {
        for (CharacterCard card : availableCharacterCards) {
            if (card.getCharacterID() == CharacterID.GRANDMA) {                 //FIXME better with instanceof?
                ProhibitionCharacterCard c = (ProhibitionCharacterCard) card;
                c.addProhibitionCard();
                break;
            }
        }
    }


    /**
     * method playerFromNickname finds the Player with the given nickname and returns it
     * @param nickname  the nickname of the Player to find
     * @return          the Player with the given nickname, if present, null otherwise
     */
    private Player playerFromNickname (String nickname) {
        for (Player p : players)
            if(p.getNickname().equals(nickname))
                return p;
        return null;
    }

    /**
     * method characterCardFromID finds the characterCard with that with the given CharacterID and returns it
     * @param characterID   the CharacterID of the CharacterCard to find
     * @return              the CharacterCard with the given CharacterID, if available, null otherwise
     */
    private CharacterCard characterCardFromID(CharacterID characterID) {
        for (CharacterCard c : availableCharacterCards)
            if (c.getCharacterID() == characterID)
                return c;
        return null;
    }

    public Player[] getPlayers() {
        return players.clone();
    }

    public Bag getBag() {
        return bag;
    }

    public Turn getTurn() {
        return turn;
    }

    public Player getCurrPlayer (){
        if (gameState == GameState.PIANIFICATION)
            return players[indexCurrPlayer];
        if (gameState == GameState.ACTION)
            return playersOrder[indexCurrPlayer];
        return null;
    }

    public IslandManager getIslandManager() {
        return islandManager;
    }

    public List<Player> getWinners() {
        return winners;
    }
    //added for tests
    public CloudManager getCloudManager() {
        return cloudManager;
    }
}
