package io.picarete.picarete.model.container;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.picarete.picarete.game_logics.EGameMode;
import io.picarete.picarete.model.EMode;
import io.picarete.picarete.model.data_sets.GameModeSet;
import io.picarete.picarete.model.data_sets.IASet;

/**
 * Created by iem on 13/01/15.
 */
public class Condition {

    Map<EConditionType, String> map;

    public enum EConditionType{
        GAME_MODE,
        MODE,
        WIN,
        LOST,
        PLAY,
        LEVEL,
        DIFFICULTY
    }


    public Condition(Map<EConditionType, String> map) {
        this.map = map;
    }

    public boolean isConditionValidated(User user){
        List<Stat> statsSearched = user.getStat(GameModeSet.searchGameMode(map.get(EConditionType.GAME_MODE)),
                IASet.searchIA(map.get(EConditionType.DIFFICULTY)),
                (map.get(EConditionType.MODE).compareTo("SOLO") == 0 ? EMode.SOLO : EMode.MULTI));
        int valueStatsFound = 0;

        if(map.containsKey(EConditionType.WIN)){
            for (Stat s : statsSearched){
                valueStatsFound += s.getWin();
            }
            int valueWin = Integer.parseInt(map.get(EConditionType.WIN));
            if(valueWin < valueStatsFound)
                return false;
        }

        if(map.containsKey(EConditionType.LOST)){
            for (Stat s : statsSearched){
                valueStatsFound += s.getLost();
            }
            int valueLost = Integer.parseInt(map.get(EConditionType.LOST));
            if(valueLost < valueStatsFound)
                return false;
        }

        if(map.containsKey(EConditionType.PLAY)){
            for (Stat s : statsSearched){
                valueStatsFound += s.getPlayed();
            }
            int valuePlayed = Integer.parseInt(map.get(EConditionType.PLAY));
            if(valuePlayed < valueStatsFound)
                return false;
        }

        return true;
    }
}
