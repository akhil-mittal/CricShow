package models;

import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;


@Data
public class BattingPlayersInningDetails {

    private Map<String, PlayerScoreCard> playerScoreCard;

    public BattingPlayersInningDetails(){
        this.playerScoreCard = new LinkedHashMap<String, PlayerScoreCard>();
    }
}
