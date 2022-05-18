package it.polimi.ingsw.client.modeldata;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.TurnState;
import it.polimi.ingsw.model.charactercards.CharacterID;

import java.io.Serializable;
import java.util.Arrays;
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

    private CharacterID activeCharacterCard;
    private boolean activeCharacterEffectApplied;

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

    public static GameData createGameData(Game game){
        IslandManagerData islandManagerData = IslandManagerData.createIslandManagerData(game.getIslandManager());
        CloudManagerData cloudManagerData = CloudManagerData.createCloudManagerData(game.getCloudManager());
        PlayerData[] playerData = new PlayerData[game.getPlayers().length];
        for(int i=0; i<game.getPlayers().length; i++){
            playerData[i] = PlayerData.createPlayerData(game.getPlayers()[i]);
        }
        CharacterCardData[] characterData = new CharacterCardData[3];
        if(game.isExpertModeEnabled()){
            for(int i=0; i< game.getAvailableCharacterCards().length; i++){
                characterData[i] = CharacterCardData.createCharacterCardData(game.getAvailableCharacterCards()[i]);
            }
        }

        return new GameData(islandManagerData, cloudManagerData, playerData, game.getGameState(), game.getTurnState(), game.getCurrPlayer().getNickname(), game.isExpertModeEnabled(), characterData, game.isLastRound());
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

    public boolean isActiveCharacterEffectApplied() {
        return activeCharacterEffectApplied;
    }

    public void setActiveCharacterEffectApplied(boolean activeCharacterEffectApplied) {
        this.activeCharacterEffectApplied = activeCharacterEffectApplied;
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
                ", activeCharacterEffectApplied=" + activeCharacterEffectApplied +
                '}';
    }

}
