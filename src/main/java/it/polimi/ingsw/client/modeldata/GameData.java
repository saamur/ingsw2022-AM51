package it.polimi.ingsw.client.modeldata;

import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.TurnState;

import java.io.Serializable;
import java.util.List;

public class GameData implements Serializable {

    private IslandManagerData islandManager;
    private CloudManagerData cloudManager;
    private final PlayerData[] playerData;

    private GameState gameState;
    private TurnState turnState;

    private String currPlayer;

    private final boolean expertModeEnabled;
    private final CharacterCardData[] characterCardData;

    private boolean lastRound;
    private List<String> winnersNicknames;

    public GameData(IslandManagerData islandManager, CloudManagerData cloudManager, PlayerData[] playerData, GameState gameState, TurnState turnState, String currPlayer, boolean expertModeEnabled, CharacterCardData[] characterCardData, boolean lastRound) {
        this.islandManager = islandManager;
        this.cloudManager = cloudManager;
        this.playerData = playerData;
        this.gameState = gameState;
        this.turnState = turnState;
        this.currPlayer = currPlayer;
        this.expertModeEnabled = expertModeEnabled;
        this.characterCardData = characterCardData;
        this.lastRound = lastRound;
    }
}
