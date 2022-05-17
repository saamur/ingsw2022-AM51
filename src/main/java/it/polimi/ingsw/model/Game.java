package it.polimi.ingsw.model;

import it.polimi.ingsw.client.modeldata.*;
import it.polimi.ingsw.constants.GameConstants;
import it.polimi.ingsw.model.charactercards.CharacterCard;
import it.polimi.ingsw.model.charactercards.CharacterCardCreator;
import it.polimi.ingsw.model.charactercards.CharacterID;
import it.polimi.ingsw.model.charactercards.ProhibitionCharacterCard;
import it.polimi.ingsw.model.clouds.Cloud;
import it.polimi.ingsw.model.clouds.CloudManager;
import it.polimi.ingsw.exceptions.*;
import it.polimi.ingsw.model.islands.Island;
import it.polimi.ingsw.model.islands.IslandManager;
import it.polimi.ingsw.model.player.Card;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.TowerColor;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

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
    private final Player[] playersOrderActionPhase;
    private int indexCurrFirstPlayer;
    private int indexNextFirstPlayer;
    private int indexCurrPlayer;

    private Turn turn;

    private final boolean expertModeEnabled;
    private final CharacterCard[] availableCharacterCards;

    private boolean lastRound;
    private List<Player> winners;

    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);


    public Game (int numPlayers, String nicknameFirstPlayer, boolean expertModeEnabled) {

        gameState = GameState.INITIALIZATION;

        islandManager = new IslandManager();
        cloudManager = new CloudManager(numPlayers);
        bag = new Bag();

        players = new Player[numPlayers];
        players[0] = new Player(nicknameFirstPlayer, TowerColor.values()[0], numPlayers, bag);
        playersOrderActionPhase = new Player[numPlayers];
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
     * @throws WrongGamePhaseException          when it is called not during initialisation
     * @throws NicknameNotAvailableException    when the given nickname is already used by another Player
     */
    @Override
    public void addPlayer (String nickname) throws WrongGamePhaseException, NicknameNotAvailableException {

        if (gameState != GameState.INITIALIZATION)
            throw new WrongGamePhaseException("The game is not in the initialization phase");

        for (int i = 0; i <= indexCurrPlayer; i++)
            if (nickname.equals(players[i].getNickname()))
                throw new NicknameNotAvailableException("Nickname already used");

        indexCurrPlayer++;
        players[indexCurrPlayer] = new Player(nickname, TowerColor.values()[indexCurrPlayer], players.length, bag);

        if (indexCurrPlayer == players.length-1)
            start();

        //TODO fire?
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
     * method initRound calls calculateWin if lastRound is true, otherwise it sets gameState to PLANNING
     * and fills the Clouds
     */
    private void initRound() {

        if (lastRound) {
            calculateWin();
            return;
        }

        indexCurrFirstPlayer = indexNextFirstPlayer;
        gameState = GameState.PLANNING;
        indexCurrPlayer = indexCurrFirstPlayer;

        cloudManager.fillClouds(bag);
        pcs.firePropertyChange("filledClouds", null, CloudManagerData.createCloudManagerData(cloudManager));
        if (bag.isEmpty())
            lastRound = true;
    }


    /**
     * method sets currCard of the currentPlayer to card if the game phase is correct,
     * the choice is valid and playerNickname is the nickname of the current Player.
     * If every Player has chosen a Card the method calls createOrderActionPhase and initActionPhase
     * @param playerNickname    the nickname of the Player that called this method
     * @param card              the chosen Card
     * @throws WrongGamePhaseException      when it is called not during planning
     * @throws NonExistingPlayerException   when there is no Player with the given nickname
     * @throws WrongPlayerException         when it is not the turn of the player with the given nickname
     * @throws NotValidMoveException                 when the card choice is not valid
     */
    @Override
    public void chosenCard (String playerNickname, Card card) throws WrongGamePhaseException, NonExistingPlayerException, WrongPlayerException, NotValidMoveException {

        Player player = playerFromNickname(playerNickname);
        if (gameState != GameState.PLANNING) throw new WrongGamePhaseException("The game is not in the planning phase");
        if (player == null) throw new NonExistingPlayerException("There is no player with the given nickname");
        if (player != getCurrPlayer()) throw new WrongPlayerException("Not the turn of this player");
        if (!validCard(player, card)) throw new NotValidMoveException("This card cannot be chosen");

        player.chooseCard(card);
        pcs.firePropertyChange("chosenCard", null, PlayerData.createPlayerData(player));

        if (player.getDeck().getCards().isEmpty())
            lastRound = true;

        indexCurrPlayer = (indexCurrPlayer + 1) % players.length;

        if (indexCurrPlayer == indexCurrFirstPlayer) {
            createOrderActionPhase();
            initActionPhase();
        }

    }

    /**
     * method validCard calculates whether a Player can use a certain Card
     * based on the Cards used in this round by the other Players
     * @param player    the Player for which the method determines if the choice is valid
     * @param card      the Card chosen
     * @return          whether the choice is valid
     */
    private boolean validCard (Player player, Card card) {

        if (!player.getDeck().getCards().contains(card))
            return false;

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
     * method createOrderActionPhase fills the attribute playersOrderActionPhase and the attribute nextFirstPlayer
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
            playersOrderActionPhase[i] = order.get(i);

        for (int i = 0; i < players.length; i++)
            if (players[i] == playersOrderActionPhase[0]) {
                indexNextFirstPlayer = i;
                break;
            }

    }

    public Player[] getPlayersOrderActionPhase() {
        return playersOrderActionPhase;
    }

    /**
     * method initActionPhase sets gameState to ACTION, sets indexCurrPlayer to 0
     * and instantiates a Turn for the first Player of this phase
     */
    private void initActionPhase() {

        gameState = GameState.ACTION;
        indexCurrPlayer = 0;
        turn = new Turn(playersOrderActionPhase[0], players.length);

    }

    /**
     * method moveStudentToChamber moves a student of the specified Clan from the Hall to the Chamber of the current Player
     * @param playerNickname    the nickname of the Player that requested this move
     * @param clan              the Clan of the student to move
     * @throws WrongGamePhaseException      when it is called not during the action phase
     * @throws NonExistingPlayerException   when there is no Player with the given nickname
     * @throws WrongPlayerException         when it is not the turn of the player with the given nickname
     * @throws WrongTurnPhaseException      when it is called not during the student moving phase
     * @throws NotValidMoveException        when there is no student of the given clan in the hall of the current Player
     *                                      or the chamber has already the maximum number of students of the given clan
     */
    @Override
    public void moveStudentToChamber (String playerNickname, Clan clan) throws WrongGamePhaseException, NonExistingPlayerException, WrongPlayerException, WrongTurnPhaseException, NotValidMoveException {

        Player player = playerFromNickname(playerNickname);
        if (gameState != GameState.ACTION) throw new WrongGamePhaseException("The game is not in the action phase");
        if (player == null) throw new NonExistingPlayerException("There is no player with the given nickname");
        if (player != getCurrPlayer()) throw new WrongPlayerException("Not the turn of this player");

        turn.moveStudentToChamber(clan, players);
        pcs.firePropertyChange("modifiedPlayer", null, PlayerData.createPlayerData(player));

    }

    /**
     * method moveStudentToChamber moves a student of the specified Clan from the Hall of the current Player
     * to the Island with the specified index
     * @param playerNickname    the nickname of the Player that requested this move
     * @param clan              the Clan of the student to move
     * @param islandIndex       the index of the Island on which to move the student
     * @throws WrongGamePhaseException      when it is called not during the action phase
     * @throws NonExistingPlayerException   when there is no Player with the given nickname
     * @throws WrongPlayerException         when it is not the turn of the player with the given nickname
     * @throws NotValidIndexException       when there is no Island with the given index
     * @throws WrongTurnPhaseException      when it is called not during the student moving phase
     * @throws NotValidMoveException        when there is no student of the given clan in the hall of the current Player
     */
    @Override
    public void moveStudentToIsland (String playerNickname, Clan clan, int islandIndex) throws WrongGamePhaseException, NonExistingPlayerException, WrongPlayerException, NotValidIndexException, WrongTurnPhaseException, NotValidMoveException {

        Player player = playerFromNickname(playerNickname);
        Island island = islandManager.getIsland(islandIndex);
        if (gameState != GameState.ACTION) throw new WrongGamePhaseException("The game is not in the action phase");
        if (player == null) throw new NonExistingPlayerException("There is no player with the given nickname");
        if (player != getCurrPlayer()) throw new WrongPlayerException("Not the turn of this player");
        if (island == null) throw new NotValidIndexException("There is no island with the given index");

        turn.moveStudentToIsland(clan, island);
        pcs.firePropertyChange("modifiedIsland", null, IslandData.createIslandData(island, islandIndex)); //oppure players?
        pcs.firePropertyChange("modifiedPlayer", null , PlayerData.createPlayerData(player));
    }

    /**
     * method moveMotherNature moves Mother Nature on the Island with the specified index,
     * calls checkInfluence and updates the state of the Turn.
     * If it's the last round it calls endTurn
     * @param playerNickname    the nickname of the Player that requested this move
     * @param islandIndex       the index of the Island on which to move Mother Nature
     * @throws WrongGamePhaseException      when it is called not during the action phase
     * @throws NonExistingPlayerException   when there is no Player with the given nickname
     * @throws WrongPlayerException         when it is not the turn of the player with the given nickname
     * @throws WrongTurnPhaseException      when it is called not during the student moving phase
     * @throws NotValidIndexException       when there is no Island with the given index
     * @throws NotValidMoveException        when the Island with the given index is too distant to be reached by Mother Nature
     */
    @Override
    public void moveMotherNature (String playerNickname, int islandIndex) throws WrongGamePhaseException, NonExistingPlayerException, WrongPlayerException, WrongTurnPhaseException, NotValidIndexException, NotValidMoveException {

        Player player = playerFromNickname(playerNickname);
        Island island = islandManager.getIsland(islandIndex);
        if (gameState != GameState.ACTION) throw new WrongGamePhaseException("The game is not in the action phase");
        if (player == null) throw new NonExistingPlayerException("There is no player with the given nickname");
        if (player != getCurrPlayer()) throw new WrongPlayerException("Not the turn of this player");
        if (turn.getTurnState() != TurnState.MOTHER_MOVING) throw new WrongTurnPhaseException("The turn is not in the mother moving phase");
        if (island == null) throw new NotValidIndexException("There is no island with the given index");
        if (islandManager.distanceFromMotherNature(island) > turn.getMaxStepsMotherNature()) throw new NotValidMoveException("The selected island is too far from Mother Nature");

        islandManager.setMotherNaturePosition(island);
        checkInfluence(island);

        if (lastRound)
            turn.setTurnState(TurnState.END_TURN);
        else
            turn.setTurnState(TurnState.CLOUD_CHOOSING);

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

        if (getCurrPlayer().getNumberOfTowers() <= 0) {
            gameState = GameState.GAME_OVER;
            winners = new ArrayList<>();
            winners.add(players[indexCurrPlayer]);

            List<String> nicknameWinners = winners.stream().map(Player::getNickname).collect(Collectors.toList());
            pcs.firePropertyChange("gameOver", null, nicknameWinners);
        }
        else if (islandManager.getNumberOfIslands() <= GameConstants.MIN_NUM_ISLANDS)
            calculateWin();

    }

    /**
     * method chosenCloud, if the choice is valid, adds the students on the Cloud with the given index
     * to the Hall of the current Player
     * @param playerNickname    the nickname of the Player that requested this move
     * @param cloudIndex        the index of the chosen Cloud
     * @throws WrongGamePhaseException      when it is called not during the action phase
     * @throws NonExistingPlayerException   when there is no Player with the given nickname
     * @throws WrongPlayerException         when it is not the turn of the player with the given nickname
     * @throws NotValidIndexException       when there is no Cloud with the given index
     * @throws WrongTurnPhaseException      when it is called not during the cloud choosing phase
     * @throws NotValidMoveException        when the Cloud with the given index has already been picked
     */
    @Override
    public void chosenCloud (String playerNickname, int cloudIndex) throws WrongGamePhaseException, NonExistingPlayerException, WrongPlayerException, NotValidIndexException, WrongTurnPhaseException, NotValidMoveException {

        Player player = playerFromNickname(playerNickname);
        Cloud cloud = cloudManager.getCloud(cloudIndex);
        if (gameState != GameState.ACTION) throw new WrongGamePhaseException("The game is not in the action phase");
        if (player == null) throw new NonExistingPlayerException("There is no player with the given nickname");
        if (player != getCurrPlayer()) throw new WrongPlayerException("Not the turn of this player");
        if (cloud == null) throw new NotValidIndexException("There is no cloud with the given index");

        turn.chooseCloud(cloud);
        pcs.firePropertyChange("chosenCloud", null, CloudData.createCloudData(cloud, cloudIndex)); //TODO Non sono sicura sia giusto scritto così
        pcs.firePropertyChange("modifiedPlayer", null, PlayerData.createPlayerData(player));
    }

    /**
     * method endTurn calls InitRound if the current Player is the last one of this round phase,
     * otherwise it instantiates a new Turn for the next Player
     * @param playerNickname    the nickname of the Player that requested this move
     * @throws WrongGamePhaseException      when it is called not during the action phase
     * @throws NonExistingPlayerException   when there is no Player with the given nickname
     * @throws WrongPlayerException         when it is not the turn of the player with the given nickname
     * @throws WrongTurnPhaseException      when it is called not during the end turn phase
     */
    @Override
    public void endTurn(String playerNickname) throws WrongGamePhaseException, NonExistingPlayerException, WrongPlayerException, WrongTurnPhaseException {

        Player player = playerFromNickname(playerNickname);
        if (gameState != GameState.ACTION) throw new WrongGamePhaseException("The game is not in the action phase");
        if (player == null) throw new NonExistingPlayerException("There is no player with the given nickname");
        if (player != getCurrPlayer()) throw new WrongPlayerException("Not the turn of this player");
        if (turn.getTurnState() != TurnState.END_TURN) throw new WrongTurnPhaseException("The turn is not in the end turn phase");

        indexCurrPlayer++;

        if (gameState == GameState.GAME_OVER)
            return;

        if (indexCurrPlayer == players.length) {
            turn = null;
            initRound();
        }
        else
            turn = new Turn(playersOrderActionPhase[indexCurrPlayer], players.length);
        //TODO fire?
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

        List<String> nicknameWinners = winners.stream().map(Player::getNickname).collect(Collectors.toList());

        pcs.firePropertyChange("gameOver", null, nicknameWinners);

    }


    /**
     * method activateCharacterCard, if the move is valid,
     * activates the CharacterCard with that CharacterID for the current turn
     * and performs any potential initial effect
     * @param playerNickname    the nickname of the Player that requested this move
     * @param characterID       the CharacterID of the CharacterCard to activate
     * @throws ExpertModeNotEnabledException    when it is called in a not expert game
     * @throws WrongGamePhaseException          when it is called not during the action phase
     * @throws NonExistingPlayerException       when there is no Player with the given nickname
     * @throws WrongPlayerException             when it is not the turn of the player with the given nickname
     * @throws NotValidMoveException            when the selected character is not in this game or it is not available,
     *                                          another character card has been activated in this turn or the Player
     *                                          does not have enough coins to pay for it
     */
    @Override
    public void activateCharacterCard (String playerNickname, CharacterID characterID) throws ExpertModeNotEnabledException, WrongGamePhaseException, NonExistingPlayerException, WrongPlayerException, NotValidMoveException {

        Player player = playerFromNickname(playerNickname);
        if (!expertModeEnabled) throw new ExpertModeNotEnabledException("The expert mode is disabled");
        if (gameState != GameState.ACTION) throw new WrongGamePhaseException("The game is not in the action phase");
        if (player == null) throw new NonExistingPlayerException("There is no player with the given nickname");
        if (player != getCurrPlayer()) throw new WrongPlayerException("Not the turn of this player");
        CharacterCard characterCard = characterCardFromID(characterID);
        if (characterCard == null) throw new NotValidMoveException("The selected character card cannot be activated in this game");
        if (turn.getActivatedCharacterCard() != null) throw new NotValidMoveException("Another character card has been activated in this turn");
        if (player.getCoins() < characterCard.getCost()) throw new NotValidMoveException("There are not enough coins to pay for the selected character card");
        if (!characterCard.isAvailable()) throw new NotValidMoveException("The selected character card is not available");

        player.pay(characterCard.getCost());
        characterCard.updateCost();

        turn.setActivatedCharacterCard(characterCard);
        pcs.firePropertyChange("modifiedCharacter", null, CharacterCardData.createCharacterCardData(characterCard));
        pcs.firePropertyChange("modifiedPlayer", null, PlayerData.createPlayerData(player));
        characterCard.applyInitialEffect(turn, players);
    }

    /**
     * method applyCharacterEffect applies the effect of the CharacterCard currently active
     * on the Island with the specified index
     * @param playerNickname    the nickname of the Player that requested this move
     * @param islandIndex       the index of the Island on which the effect has to be applied
     * @throws ExpertModeNotEnabledException    when it is called in a not expert game
     * @throws WrongGamePhaseException          when it is called not during the action phase
     * @throws NonExistingPlayerException       when there is no Player with the given nickname
     * @throws WrongPlayerException             when it is not the turn of the player with the given nickname
     * @throws NotValidMoveException            when there is no activated character card, its effect has already been
     *                                          applied or the move is not valid
     * @throws NotValidIndexException           when there is no Island with the given index
     */
    @Override
    public void applyCharacterCardEffect (String playerNickname, int islandIndex) throws ExpertModeNotEnabledException, WrongGamePhaseException, NonExistingPlayerException, WrongPlayerException, NotValidMoveException, NotValidIndexException {

        Player player = playerFromNickname(playerNickname);
        Island island = islandManager.getIsland(islandIndex);
        if (!expertModeEnabled) throw new ExpertModeNotEnabledException("The expert mode is disabled");
        if (gameState != GameState.ACTION) throw new WrongGamePhaseException("The game is not in the action phase");
        if (player == null) throw new NonExistingPlayerException("There is no player with the given nickname");
        if (player != getCurrPlayer()) throw new WrongPlayerException("Not the turn of this player");
        if (turn.getActivatedCharacterCard() == null) throw new NotValidMoveException("There is no activated character card");
        if (turn.isCharacterEffectApplied()) throw new NotValidMoveException("The effect of this character card has already been applied");
        if (island == null) throw new NotValidIndexException("There is no island with the given index");

        boolean ok = turn.getActivatedCharacterCard().applyEffect(this, island);
        if (turn.getActivatedCharacterCard() instanceof ProhibitionCharacterCard){ //if card is GRANDMA
            pcs.firePropertyChange("modifiedCharacter", null, CharacterCardData.createCharacterCardData(turn.getActivatedCharacterCard()));
            pcs.firePropertyChange("modifiedIsland", null, IslandData.createIslandData(island, islandIndex));
        }

        if (!ok) throw new NotValidMoveException("This move is not valid");

        turn.characterEffectApplied();

    }

    /**
     * method setClanCharacter calls the method setCharacterClan on the current Turn, setting a Clan variable
     * necessary for some CharacterCards to perform their effects
     * @param playerNickname    the nickname of the Player that requested this move
     * @param clan              the Clan chosen by the Player
     * @throws ExpertModeNotEnabledException    when it is called in a not expert game
     * @throws WrongGamePhaseException          when it is called not during the action phase
     * @throws NonExistingPlayerException       when there is no Player with the given nickname
     * @throws WrongPlayerException             when it is not the turn of the player with the given nickname
     * @throws NotValidMoveException            when there is no activated character card, or the Clan for the effect
     *                                          of the character card has already been set
     */
    @Override
    public void setClanCharacter (String playerNickname, Clan clan) throws ExpertModeNotEnabledException, WrongGamePhaseException, NonExistingPlayerException, WrongPlayerException, NotValidMoveException {

        Player player = playerFromNickname(playerNickname);
        if (!expertModeEnabled) throw new ExpertModeNotEnabledException("The expert mode is disabled");
        if (gameState != GameState.ACTION) throw new WrongGamePhaseException("The game is not in the action phase");
        if (player == null) throw new NonExistingPlayerException("There is no player with the given nickname");
        if (player != getCurrPlayer()) throw new WrongPlayerException("Not the turn of this player");
        if (turn.getActivatedCharacterCard() == null) throw new NotValidMoveException("There is no activated character card");
        if (turn.getCharacterClan() != null) throw new NotValidMoveException("The clan for the effect of the selected character card has already been set");

        turn.setCharacterClan(clan);

        if (turn.getActivatedCharacterCard().getCharacterID() == CharacterID.THIEF) {
            applyCharacterCardEffect(playerNickname, -1, null, null);
        }

    }

    /**
     * method applyCharacterEffect applies the effect of the CharacterCard currently active with the given parameters
     * @param playerNickname    the nickname of the Player that requested this move
     * @param islandIndex       the index of the destination Island for the students moved (if necessary)
     * @param students1         the students to move to the destination (if necessary)
     * @param students2         the students to move from the destination, in case of an exchange of students (if necessary)
     * @throws ExpertModeNotEnabledException    when it is called in a not expert game
     * @throws WrongGamePhaseException          when it is called not during the action phase
     * @throws NonExistingPlayerException       when there is no Player with the given nickname
     * @throws WrongPlayerException             when it is not the turn of the player with the given nickname
     * @throws NotValidMoveException            when there is no activated character card, its effect has already been
     *                                          applied or the move is not valid
     */
    @Override
    public void applyCharacterCardEffect (String playerNickname, int islandIndex, Map<Clan, Integer> students1, Map<Clan, Integer> students2) throws ExpertModeNotEnabledException, WrongGamePhaseException, NonExistingPlayerException, WrongPlayerException, NotValidMoveException {

        Player player = playerFromNickname(playerNickname);
        if (!expertModeEnabled) throw new ExpertModeNotEnabledException("The expert mode is disabled");
        if (gameState != GameState.ACTION) throw new WrongGamePhaseException("The game is not in the action phase");
        if (player == null) throw new NonExistingPlayerException("There is no player with the given nickname");
        if (player != getCurrPlayer()) throw new WrongPlayerException("Not the turn of this player");
        if (turn.getActivatedCharacterCard() == null) throw new NotValidMoveException("There is no activated character card");
        if (turn.isCharacterEffectApplied()) throw new NotValidMoveException("The effect of this character card has already been applied");

        PlayerData oldPlayerData = PlayerData.createPlayerData(player);
        IslandData oldIslandData = IslandData.createIslandData(islandManager.getIsland(islandIndex), islandIndex);
        CharacterCardData oldCharacterCard = CharacterCardData.createCharacterCardData(turn.getActivatedCharacterCard());

        boolean ok = turn.getActivatedCharacterCard().applyEffect(this, islandManager.getIsland(islandIndex), students1, students2);

        pcs.firePropertyChange("modifiedPlayer", oldPlayerData, PlayerData.createPlayerData(player));
        pcs.firePropertyChange("modifiedIsland", oldIslandData, IslandData.createIslandData(islandManager.getIsland(islandIndex), islandIndex));
        pcs.firePropertyChange("modifiedCharacter", oldCharacterCard, CharacterCardData.createCharacterCardData(turn.getActivatedCharacterCard()));
        for (Player value : players) {
            pcs.firePropertyChange("modifiedPlayer", null, PlayerData.createPlayerData(value));
        }


        if (!ok) throw new NotValidMoveException("This move is not valid");

        turn.characterEffectApplied();

        if (bag.isEmpty())
            lastRound = true;

    }

    /**
     * method reassignProhibitionCard find the characterCard that can accept prohibition cards and adds it to it
     */
    private void reassignProhibitionCard() {
        for (CharacterCard card : availableCharacterCards) {
            if (card.getCharacterID() == CharacterID.GRANDMA) {                 //FIXME better with instanceof?
                ProhibitionCharacterCard c = (ProhibitionCharacterCard) card;
                c.addProhibitionCard();
                pcs.firePropertyChange("modifiedCharacter", null, CharacterCardData.createCharacterCardData(c));
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

    @Override
    public GameState getGameState() {
        return gameState;
    }

    public Player[] getPlayers() {
        return players.clone();
    }

    @Override
    public int getNumOfPlayers() {
        return players.length;
    }

    @Override
    public List<String> getPlayersNicknames () {
        return new ArrayList<>(Arrays.stream(players).filter(Objects::nonNull).map(Player::getNickname).toList());
    }

    public Bag getBag() {
        return bag;
    }

    public Turn getTurn() {
        return turn;
    }

    public Player getCurrPlayer (){
        if (gameState == GameState.PLANNING)
            return players[indexCurrPlayer];
        if (gameState == GameState.ACTION)
            return playersOrderActionPhase[indexCurrPlayer];
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

    @Override
    public boolean isExpertModeEnabled() {
        return expertModeEnabled;
    }

    @Override
    public boolean isLastRound(){ //FIXME Added for GameData
        return lastRound;
    }

    public CharacterCard[] getAvailableCharacterCards(){
        return availableCharacterCards;
    }

    @Override
    public void setListeners(PropertyChangeListener listener){
        islandManager.addPropertyChangeListener(listener);
        pcs.addPropertyChangeListener( listener);
        if(expertModeEnabled)
            for (CharacterCard c : availableCharacterCards)
                c.addPropertyChangeListener(listener);


    }

    @Override
    public void removeListeners(PropertyChangeListener listener){
        islandManager.removePropertyChangeListener(listener);
        pcs.removePropertyChangeListener(listener);
        if(expertModeEnabled)
            for (CharacterCard c : availableCharacterCards)
                c.removePropertyChangeListener(listener);


    }

    @Override
    public GameData getGameData() {
        return GameData.createGameData(this);
    }

    @Override
    public TurnState getTurnState() {
        if(turn == null)
            return null;
        return turn.getTurnState();
    }

    @Override
    public String getNicknameCurrPlayer() {
        return getCurrPlayer().getNickname();
    }

    @Override
    public CharacterID getActivatedCharacterCard() {
        if(turn == null || turn.getActivatedCharacterCard() == null)
            return null;
        return turn.getActivatedCharacterCard().getCharacterID();
    }
}
