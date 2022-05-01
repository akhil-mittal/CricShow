package services;

import models.TeamInfo;

public interface BattingTeamScoreCard {
    void updateTeamScore(TeamInfo team, int score);
    void updateBatsmanScore(TeamInfo team, int score, String playerName);
    void updateBatsmanStatus(TeamInfo team, String playerName, String Status);
    void updateTeamWickets(TeamInfo team);
    String getNewBatsman(TeamInfo team);
}
