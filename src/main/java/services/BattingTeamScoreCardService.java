package services;

import models.PlayerBattingStatus;
import models.PlayerScoreCard;
import models.TeamInfo;

import java.util.Map;


public class BattingTeamScoreCardService implements BattingTeamScoreCard{

    public void updateTeamScore(TeamInfo team, int score) {
        team.setTotalScore(team.getTotalScore()+score);
    }

    public void updateBatsmanScore(TeamInfo team, int score, String playerName){
        int s = team.getBattingPlayersInningDetails().getPlayerScoreCard().get(playerName).getScore();
        int ballFaced = team.getBattingPlayersInningDetails().getPlayerScoreCard().get(playerName).getBallFaced();
        int four = team.getBattingPlayersInningDetails().getPlayerScoreCard().get(playerName).getFours();
        int six = team.getBattingPlayersInningDetails().getPlayerScoreCard().get(playerName).getSix();
        team.getBattingPlayersInningDetails().getPlayerScoreCard().get(playerName).setScore(s+score);
        team.getBattingPlayersInningDetails().getPlayerScoreCard().get(playerName).setBallFaced(ballFaced+1);
        if(score==4)
            team.getBattingPlayersInningDetails().getPlayerScoreCard().get(playerName).setFours(four+1);
        else if(score == 6)
            team.getBattingPlayersInningDetails().getPlayerScoreCard().get(playerName).setSix(six+1);
    }

    public void updateBatsmanStatus(TeamInfo team, String playerName, String status){
        if(status.equalsIgnoreCase("batting")){
            team.getBattingPlayersInningDetails().getPlayerScoreCard().get(playerName).setBatted(PlayerBattingStatus.BATTED_BATTING);
        } else if(status.equalsIgnoreCase("out")) {
            int ballFaced = team.getBattingPlayersInningDetails().getPlayerScoreCard().get(playerName).getBallFaced();
            team.getBattingPlayersInningDetails().getPlayerScoreCard().get(playerName).setBallFaced(ballFaced+1);
            team.getBattingPlayersInningDetails().getPlayerScoreCard().get(playerName).setBatted(PlayerBattingStatus.BATTED_OUT);
        }
    }

    public void updateTeamWickets(TeamInfo team) {
        team.setTotalWicketFall(team.getTotalWicketFall()+1);
    }

    public String getNewBatsman(TeamInfo team) {
        Map<String, PlayerScoreCard> mp = team.getBattingPlayersInningDetails().getPlayerScoreCard();
        for (String playerName : mp.keySet()) {
            PlayerScoreCard playerScoreCard = mp.get(playerName);
            if(playerScoreCard.getBatted().equals(PlayerBattingStatus.BATTED_NOT_YET)) {
                team.getBattingPlayersInningDetails().getPlayerScoreCard().get(playerName).setBatted(PlayerBattingStatus.BATTED_BATTING);
                return playerName;
            }
        }
        return "";
    }

}
