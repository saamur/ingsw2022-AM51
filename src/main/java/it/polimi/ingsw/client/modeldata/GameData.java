package it.polimi.ingsw.client.modeldata;

import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.TurnState;
import it.polimi.ingsw.model.charactercards.CharacterID;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * contains all the data of the game
 */

public class GameData implements Serializable {

    private IslandManagerData islandManager;
    private CloudManagerData cloudManager;
    private final PlayerData[] playerData;

    private String currPlayer;

    private GameState gameState;
    private TurnState turnState;

    private boolean lastRound;

    private List<String> winnersNicknames;

    private final boolean expertModeEnabled;
    private final CharacterCardData[] characterCardData;

    private CharacterID activeCharacterCard;
    private boolean activeCharacterPunctualEffectApplied;

    public GameData(IslandManagerData islandManager,
                    CloudManagerData cloudManager,
                    PlayerData[] playerData,
                    String currPlayer,
                    GameState gameState,
                    TurnState turnState,
                    boolean lastRound,
                    boolean expertModeEnabled,
                    CharacterCardData[] characterCardData,
                    CharacterID activeCharacterCard,
                    boolean activeCharacterPunctualEffectApplied) {
        this.islandManager = islandManager;
        this.cloudManager = cloudManager;
        this.playerData = playerData;
        this.currPlayer = currPlayer;
        this.gameState = gameState;
        this.turnState = turnState;
        this.lastRound = lastRound;
        this.expertModeEnabled = expertModeEnabled;
        this.characterCardData = characterCardData;
        this.activeCharacterCard = activeCharacterCard;
        this.activeCharacterPunctualEffectApplied = activeCharacterPunctualEffectApplied;
    }

    public IslandManagerData getIslandManager() {
        return islandManager;
    }

    public void setIslandManager(IslandManagerData islandManager) {
        this.islandManager = islandManager;
    }

    public CloudManagerData getCloudManager() {
        return cloudManager;
    }

    public void setCloudManager(CloudManagerData cloudManager) {
        this.cloudManager = cloudManager;
    }

    public PlayerData[] getPlayerData() {
        return playerData;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public TurnState getTurnState() {
        return turnState;
    }

    public void setTurnState(TurnState turnState) {
        this.turnState = turnState;
    }

    public String getCurrPlayer() {
        return currPlayer;
    }

    public void setCurrPlayer(String currPlayer) {
        this.currPlayer = currPlayer;
    }

    public boolean isExpertModeEnabled() {
        return expertModeEnabled;
    }

    public CharacterCardData[] getCharacterCardData() {
        return characterCardData;
    }

    public boolean isLastRound() {
        return lastRound;
    }

    public void setLastRound(boolean lastRound) {
        this.lastRound = lastRound;
    }

    public CharacterID getActiveCharacterCard(){ return activeCharacterCard;}

    public void setActiveCharacterCard(CharacterID activeCharacterCard){this.activeCharacterCard = activeCharacterCard; }

    public boolean isActiveCharacterPunctualEffectApplied() {
        return activeCharacterPunctualEffectApplied;
    }

    public void setActiveCharacterPunctualEffectApplied(boolean activeCharacterPunctualEffectApplied) {
        this.activeCharacterPunctualEffectApplied = activeCharacterPunctualEffectApplied;
    }

    public List<String> getWinnersNicknames() {
        return winnersNicknames;
    }

    public void setWinnersNicknames(List<String> winnersNicknames) {
        this.winnersNicknames = winnersNicknames;
    }

    @Override
    public String toString() {
        return "GameData{" +
                "islandManager=" + islandManager +
                ", cloudManager=" + cloudManager +
                ", playerData=" + Arrays.toString(playerData) +
                ", gameState=" + gameState +
                ", turnState=" + turnState +
                ", currPlayer='" + currPlayer + '\'' +
                ", expertModeEnabled=" + expertModeEnabled +
                ", characterCardData=" + Arrays.toString(characterCardData) +
                ", lastRound=" + lastRound +
                ", winnersNicknames=" + winnersNicknames +
                ", activeCharacterCard=" + activeCharacterCard +
                ", activeCharacterPunctualEffectApplied=" + activeCharacterPunctualEffectApplied +
                '}';
    }

}
