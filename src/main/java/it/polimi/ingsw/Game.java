package it.polimi.ingsw;

import java.util.Random;

public class Game {

    private GameState gameState;

    private final IslandManager islandManager;
    private final CloudManager cloudManager;
    private final Bag bag;

    private final Player[] players;
    private Player[] playersOrder;
    private int indexCurrFirstPlayer;
    private int indexNextFirstPlayer;
    private int indexCurrPlayer;

    private Turn turn;

    private final boolean expertModeEnabled;
    private final CharacterCard[] characterCards;

    private boolean lastRound;
    private Player winner;

    public Game (int numPlayers, String nicknameFirstPlayer, boolean expertModeEnabled) {

        gameState = GameState.INITIALIZATION;

        islandManager = new IslandManager();
        cloudManager = new CloudManager(numPlayers);
        bag = new Bag();

        players = new Player[numPlayers];
        players[0] = new Player(nicknameFirstPlayer, TowerColor.values()[0], numPlayers, bag);
        indexCurrPlayer = 0;

        this.expertModeEnabled = expertModeEnabled;

        if (expertModeEnabled) {
            CharacterCardCreator creator = new CharacterCardCreator();
            Random random = new Random();
            characterCards = new CharacterCard[3];
            int i = 0;
            while (i < 3) {
                CharacterID c = CharacterID.values()[random.nextInt(CharacterID.values().length)];
                boolean ok = true;
                for (int j = 0; j < i; j++)
                    if (c == characterCards[j].getCharacterID()) {
                        ok = false;
                        break;
                    }
                if (ok) {
                    characterCards[i] = creator.createCharacterCard(c);
                    i++;
                }
            }
        }
        else
            characterCards = null;

        lastRound = false;
        winner = null;

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

    private void start () {

        Random random = new Random();
        indexNextFirstPlayer = random.nextInt(players.length);

        initRound();

    }

    private void initRound() {

        if (lastRound) {
            gameState = GameState.GAME_OVER;
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

    public boolean chosenCard (Player player, Card card) {

        if (player != players[indexCurrPlayer])
            return false;

        if (!validCard(player, card))
            return false;

        boolean ok = player.chooseCard(card);

        if (!ok)
            return false;           //never executed if everything works correctly

        indexCurrPlayer = (indexCurrPlayer + 1) % players.length;

        if (indexCurrPlayer == indexCurrFirstPlayer) {
            createOrderActionPhase();
            initActionPhase();
        }

        return true;

    }

    private boolean validCard (Player player, Card card) {
        //TODO
        return true;
    }

    private void createOrderActionPhase() {
        //TODO
    }

    private void initActionPhase() {
        //TODO
    }

    private void calculateWin() {
        //TODO
    }

}
