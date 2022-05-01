package controllers;

import models.PlayerScoreCard;
import models.TeamInfo;
import services.BattingPlayersInningDetailsService;
import services.BattingTeamScoreCardService;
import models.GameDetails;

import java.util.Map;
import java.util.Scanner;

public class StartInning {

    static String onStrike = "";
    static String onNonStrike = "";
    static int team1Score = 0;

    public static void createPlayersMapState(TeamInfo team, GameDetails gameDetails, String playingTeam) {
        int totalPlayers = gameDetails.getTotalPlayers();
        System.out.println("Batting Order for team" + playingTeam + " : ");
        Scanner sc = new Scanner(System.in);
        for(int i=1;i<=totalPlayers;i++) {
            String playerName = sc.nextLine();
            BattingPlayersInningDetailsService battingPlayersInningDetailsService = new BattingPlayersInningDetailsService();
            if (battingPlayersInningDetailsService.addNewPlayerMapState(team, playerName) == 0){
                System.out.println("This player already added by you. Please add different player");
                --i;
                continue;
            }
            if(i==1)
                onStrike = playerName;
            if(i==2)
                onNonStrike = playerName;

        }
    }

    public static void playMatch(TeamInfo team, GameDetails gameDetails, String playingTeam) {

        int totalOvers = 1;
        System.out.println("\nPress 1 for 1 run");
        System.out.println("Press 2 for 2 run");
        System.out.println("Press 3 for 3 run");
        System.out.println("Press 4 for 4 run");
        System.out.println("Press 6 for 6 run");
        System.out.println("Press wd for Wide");
        System.out.println("Press Nb for No Ball");
        System.out.println("Press W for wicket");
        Scanner sc = new Scanner(System.in);
        BattingTeamScoreCardService battingTeamScoreCardService = new BattingTeamScoreCardService();
        battingTeamScoreCardService.updateBatsmanStatus(team,onStrike,"batting");
        battingTeamScoreCardService.updateBatsmanStatus(team,onNonStrike,"batting");
        boolean isAllOut = false;
        boolean isWin = false;
        while(totalOvers <= gameDetails.getTotalOvers()) {
            System.out.println("\nOver : " + Integer.toString(totalOvers) + " --> (" + onStrike + " is on Strike and " + onNonStrike + " is on Non-Strike)");
            int totalBowls = 1;
            while(totalBowls <= 6){
                String bowl = sc.nextLine();
                if(bowl.equalsIgnoreCase("wd") || bowl.equalsIgnoreCase("nb")){
                    battingTeamScoreCardService.updateTeamScore(team,1);
                    continue;
                }
                else if(bowl.equalsIgnoreCase("1") || bowl.equalsIgnoreCase("3")){
                    battingTeamScoreCardService.updateTeamScore(team, Integer.parseInt(bowl));
                    battingTeamScoreCardService.updateBatsmanScore(team, Integer.parseInt(bowl), onStrike);

                    if(totalBowls < 6){
                        String temp = onNonStrike;
                        onNonStrike = onStrike;
                        onStrike = temp;
                    }
                    if(playingTeam.equalsIgnoreCase("2")) {
                        if(team.getTotalScore() > team1Score){
                            isWin = true;
                            break;
                        }
                    }

                }
                else if(bowl.equalsIgnoreCase("0") || bowl.equalsIgnoreCase("2") || bowl.equalsIgnoreCase("4") || bowl.equalsIgnoreCase("6")) {
                    if(!bowl.equalsIgnoreCase("0")) {
                        battingTeamScoreCardService.updateTeamScore(team, Integer.parseInt(bowl));
                    }
                    battingTeamScoreCardService.updateBatsmanScore(team, Integer.parseInt(bowl), onStrike);

                    if(totalBowls == 6) {
                        String temp = onNonStrike;
                        onNonStrike = onStrike;
                        onStrike = temp;
                    }

                    if(playingTeam.equalsIgnoreCase("2")) {
                        if(team.getTotalScore() > team1Score){
                            isWin = true;
                            break;
                        }
                    }
                }
                else if(bowl.equalsIgnoreCase("w")) {
                    battingTeamScoreCardService.updateTeamWickets(team);
                    battingTeamScoreCardService.updateBatsmanStatus(team, onStrike, "out");

                    if(team.getTotalWicketFall() == (gameDetails.getTotalPlayers()-1)){
                        onStrike = "";
                        isAllOut = true;
                        break;
                    }

                    if(totalBowls == 1) {
                        onStrike = onNonStrike;
                        onNonStrike = battingTeamScoreCardService.getNewBatsman(team);
                    } else {
                        onStrike = battingTeamScoreCardService.getNewBatsman(team);
                    }

                }
                else{
                    System.out.println("Invalid score. please fill this ball entry again");
                    continue;
                }

                ++totalBowls;
            }
            printScoreCard(team, totalOvers, playingTeam, totalBowls);
            if(isAllOut || isWin)
                break;
            ++totalOvers;
        }
    }

    public static void printScoreCard(TeamInfo team, int totalOvers, String playingTeam, int totalBowls){
        System.out.println("ScoreCard for team" + playingTeam);
        Map<String, PlayerScoreCard> mp = team.getBattingPlayersInningDetails().getPlayerScoreCard();
        System.out.println("PlayerName\t\tScore\tBalls\t4s\t\t6s\t\tBatted");
        for (String playerName : mp.keySet()) {
            PlayerScoreCard playerScoreCard = mp.get(playerName);
            if(playerName.equalsIgnoreCase(onNonStrike) || playerName.equalsIgnoreCase(onStrike))
                playerName = playerName + "*";
            System.out.println(playerName+"\t\t\t\t"+ playerScoreCard.getScore() + "\t\t" + playerScoreCard.getBallFaced() + "\t\t"  + playerScoreCard.getFours() + "\t\t" + playerScoreCard.getSix() + "\t\t" + playerScoreCard.getBatted());
        }
        System.out.println("Total: " + team.getTotalScore() + "/" + team.getTotalWicketFall());
        if(totalBowls < 6)
            totalOvers -= 1;
        if(totalBowls >= 6)
            totalBowls = 0;
        System.out.println("Overs : " + Integer.toString(totalOvers) + "." + Integer.toString(totalBowls));

        if(playingTeam.equalsIgnoreCase("1"))
            team1Score = team.getTotalScore();
    }
}
