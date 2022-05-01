package models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
public class TeamInfo {
    private int totalScore;
    private int totalWicketFall;
    private int totalWides;
    private int totalNoBalls;
    private BattingPlayersInningDetails battingPlayersInningDetails;

    public TeamInfo() {
        this.totalScore = 0;
        this.totalWicketFall = 0;
        this.totalWides = 0;
        this.totalNoBalls = 0;
        this.battingPlayersInningDetails = new BattingPlayersInningDetails();
    }
}


