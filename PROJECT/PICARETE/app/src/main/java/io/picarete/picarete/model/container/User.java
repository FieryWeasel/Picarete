package io.picarete.picarete.model.container;

import android.content.Context;

import com.google.gson.Gson;

import java.util.List;

import io.picarete.picarete.game_logics.EGameMode;
import io.picarete.picarete.game_logics.ia.EIA;
import io.picarete.picarete.model.EMode;
import io.picarete.picarete.model.NoDuplicatesList;
import io.picarete.picarete.model.data_sets.GameModeSet;
import io.picarete.picarete.model.data_sets.IASet;

/**
 * Created by iem on 13/01/15.
 */
public class User {

    private List<Stat> stats;
    private String name;
    private String title;
    private ColorCustom player1;
    private ColorCustom player2;

    public User(Context context) {

        for( EGameMode gameMode : GameModeSet.getEGameMode(context)){
            for(EIA ia : IASet.getEIAs(context)){
                stats.add(new Stat(ia, gameMode, EMode.SOLO));
                stats.add(new Stat(ia, gameMode, EMode.MULTI));
            }
        }

    }

    public List<Stat> getStat(EGameMode gameMode, EIA ia, EMode mode){

        List<Stat> gameModeFound =  new NoDuplicatesList<>();
        if(gameMode != null){
            for(Stat stat : stats){
                if(stat.gameMode == gameMode){
                    gameModeFound.add(stat);
                }
            }
        } else {
            for(Stat stat : stats){
                gameModeFound.add(stat);
            }
        }

        List<Stat> iaFound =  new NoDuplicatesList<>();
        if(ia != null){
            for(Stat stat : gameModeFound){
                if(stat.ia == ia){
                    iaFound.add(stat);
                }
            }
        } else {
            for(Stat stat : gameModeFound){
                iaFound.add(stat);
            }
        }

        List<Stat> modeFound =  new NoDuplicatesList<>();
        if(mode != null){
            for(Stat stat : iaFound){
                if(stat.mode == mode){
                    modeFound.add(stat);
                }
            }
        } else {
            for(Stat stat : iaFound){
                modeFound.add(stat);
            }
        }

        return modeFound;

    }

    public void save(){
        //TODO
        Gson gson = new Gson();
        String json = gson.toJson(this);
    }

    public void load(){
        //TODO
    }
}
