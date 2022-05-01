package services;

import models.PlayerScoreCard;
import models.TeamInfo;

public class BattingPlayersInningDetailsService implements BattingPlayersInningDetails{

    public int addNewPlayerMapState(TeamInfo team, String playerName){
        if (!team.getBattingPlayersInningDetails().getPlayerScoreCard().containsKey(playerName)) {
            team.getBattingPlayersInningDetails().getPlayerScoreCard().put(playerName, new PlayerScoreCard());
            return 1;
        }
        return 0;
    }
}
