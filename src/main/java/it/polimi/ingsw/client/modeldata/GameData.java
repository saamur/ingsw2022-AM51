package it.polimi.ingsw.client.modeldata;

import it.polimi.ingsw.constants.GameConstants;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.GameState;
import it.polimi.ingsw.model.TurnState;
import org.w3c.dom.CharacterData;

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

        return new GameData(islandManagerData, cloudManagerData, playerData, game.getGameState(), game.getTurn().getTurnState(), game.getCurrPlayer().getNickname(), game.isExpertModeEnabled(), characterData, game.isLastRound());
    }
}
