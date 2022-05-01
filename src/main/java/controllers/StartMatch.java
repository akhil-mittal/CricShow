package controllers;

import models.GameDetails;
import models.TeamInfo;
import java.util.Scanner;

public class StartMatch {

    static GameDetails gameDetails = new GameDetails();
    static TeamInfo team1 = new TeamInfo();
    static TeamInfo team2 = new TeamInfo();
    static String playingTeam = "1";

    public static void main(String args[]) {

        // set GameDetails like No. of overs, players in each team.
        setGameDetails();

        if(gameDetails.getTotalPlayers() >= 2 && gameDetails.getTotalOvers()>0) {
            // Enter batting order details for team 1
            StartInning.createPlayersMapState(team1, gameDetails, playingTeam);
            // Let's start batting with team1
            StartInning.playMatch(team1, gameDetails, playingTeam);

            // Enter batting order details for team 2
            playingTeam = "2";
            StartInning.createPlayersMapState(team2, gameDetails, playingTeam);
            // Let's start batting with team2
            StartInning.playMatch(team2, gameDetails, playingTeam);

            // Show Result
            getResult(team1, team2);
        }
    }

    public static void getResult(TeamInfo team1, TeamInfo team2) {
        if(team1.getTotalScore() > team2.getTotalScore())
            System.out.println("Result : Team1 won the match by " + (team1.getTotalScore() - team2.getTotalScore()) + " runs");
        else if(team1.getTotalScore() < team2.getTotalScore())
            System.out.println("Result : Team 2 won the match by " + (gameDetails.getTotalPlayers() - team2.getTotalWicketFall() -1) + " wickets");
        else
            System.out.println("Result : Match Tied");
    }

    public static void setGameDetails(){
        Scanner sc = new Scanner(System.in);

        System.out.print("No. of players for each team : ");
        gameDetails.setTotalPlayers(sc.nextInt());

        System.out.print("No. of overs : ");
        gameDetails.setTotalOvers(sc.nextInt());
    }
}
