package models;

import lombok.Data;

@Data
public class PlayerScoreCard {
    int score;
    int ballFaced;
    int fours;
    int six;
    PlayerBattingStatus batted;

    public PlayerScoreCard() {
        this.score = 0;
        this.ballFaced = 0;
        this.fours = 0;
        this.six = 0;
        this.batted = PlayerBattingStatus.BATTED_NOT_YET;
    }
}
