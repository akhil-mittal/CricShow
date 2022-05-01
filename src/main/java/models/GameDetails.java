package models;

import lombok.Data;

@Data
public class GameDetails {
    private int totalPlayers;
    private int totalOvers;

    public GameDetails(){
        this.totalPlayers = 0;
        this.totalOvers = 0;
    }
}
