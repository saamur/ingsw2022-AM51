package it.polimi.ingsw;

import it.polimi.ingsw.charactercards.CharacterCard;
import it.polimi.ingsw.charactercards.CharacterCardCreator;
import it.polimi.ingsw.charactercards.CharacterID;
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

public class Game implements GameInterface {

    private static final Function<Player, Integer>[] winningScoreCalculators = new Function[2];

    static {
        winningScoreCalculators[0] = p -> (-1) * p.getNumberOfTowers();
        winningScoreCalculators[1] = p -> {
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
                    availableCharacterCards[i] = creator.createCharacterCard(c);
                    i++;
                }
            }
        }
        else
            availableCharacterCards = null;

        lastRound = false;
        winners = null;

    }

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

    private void start() {

        Random random = new Random();
        indexNextFirstPlayer = random.nextInt(players.length);

        initRound();

    }

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

    private void initActionPhase() {

        gameState = GameState.ACTION;
        indexCurrPlayer = 0;
        turn = new Turn(playersOrder[0], players.length);

    }

    public boolean moveStudentToChamber (String playerNickname, Clan clan) {

        Player player = playerFromNickname(playerNickname);
        if (player == null)
            return false;

        if (gameState != GameState.ACTION || player != players[indexCurrPlayer])
            return false;

        return turn.moveStudentToChamber(clan, players);

    }

    public boolean moveStudentToIsland (String playerNickname, Clan clan, int islandIndex) {

        Player player = playerFromNickname(playerNickname);
        Island island = islandManager.getIsland(islandIndex);
        if (player == null || island == null)
            return false;

        if (gameState != GameState.ACTION || player != players[indexCurrPlayer])
            return false;

        return turn.moveStudentToIsland(clan, island);

    }

    public boolean moveMotherNature (String playerNickname, int islandIndex) {

        Player player = playerFromNickname(playerNickname);
        Island island = islandManager.getIsland(islandIndex);
        if (player == null || island == null)
            return false;

        if (gameState != GameState.ACTION || player != players[indexCurrPlayer])
            return false;

        if (turn.getTurnState() != TurnState.MOTHER_MOVING)
            return false;

        if (islandManager.distanceFromMotherNature(island) <= turn.getMaxStepsMotherNature())
            return false;

        islandManager.setMotherNaturePosition(island);
        checkInfluence(island);
        turn.setTurnState(TurnState.CLOUD_CHOOSING);

        if (lastRound)
            endTurn();

        return true;

    }

    public void checkInfluence (Island island) {             //FIXME we have to find a better name

        turn.updateInfluence(islandManager, island, players);

        if (players[indexCurrPlayer].getNumberOfTowers() <= 0) {
            gameState = GameState.GAME_OVER;
            winners = new ArrayList<>();
            winners.add(players[indexCurrPlayer]);
        }
        else if (islandManager.getNumberOfIslands() <= 3)
            calculateWin();

    }

    public boolean chosenCloud (String playerNickname, int cloudIndex) {

        Player player = playerFromNickname(playerNickname);
        Cloud cloud = cloudManager.getCloud(cloudIndex);
        if (player == null || cloud == null)
            return false;

        if (gameState != GameState.ACTION || player != players[indexCurrPlayer])
            return false;

        boolean ok = turn.chooseCloud(cloud);

        if (!ok)
            return false;

        endTurn();

        return true;

    }

    private void endTurn() {

        indexCurrPlayer++;

        if (gameState == GameState.GAME_OVER)
            return;

        if (indexCurrPlayer == players.length)
            initRound();
        else
            turn = new Turn(playersOrder[indexCurrPlayer], players.length);

    }

    private void calculateWin() {

        gameState = GameState.GAME_OVER;

        List<Player> potentialWinners = new ArrayList<>(Arrays.asList(players));

        for (Function<Player, Integer> f : winningScoreCalculators) {

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



    public boolean activateCharacterCard (String playerNickname, CharacterID characterID) {

        Player player = playerFromNickname(playerNickname);
        CharacterCard characterCard = CharacterCardFromID(characterID);

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

    public void setClanCharacter (String playerNickname, Clan clan) {

        Player player = playerFromNickname(playerNickname);

        if (player == null)
            return;

        turn.setCharacterClan(clan);

        //FIXME thief should start

    }

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

        return true;

    }



    private Player playerFromNickname (String nickname) {
        for (Player p : players)
            if(p.getNickname().equals(nickname))
                return p;
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

    private CharacterCard CharacterCardFromID (CharacterID characterID) {
        for (CharacterCard c : availableCharacterCards)
            if (c.getCharacterID() == characterID)
                return c;
        return null;
    }

}
