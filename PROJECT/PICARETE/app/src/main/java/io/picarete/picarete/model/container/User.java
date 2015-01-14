package io.picarete.picarete.model.container;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.picarete.picarete.game_logics.EGameMode;
import io.picarete.picarete.game_logics.ia.EIA;
import io.picarete.picarete.model.Constants;
import io.picarete.picarete.model.EMode;
import io.picarete.picarete.model.NoDuplicatesList;
import io.picarete.picarete.model.data_sets.GameModeSet;
import io.picarete.picarete.model.data_sets.IASet;

/**
 * Created by iem on 13/01/15.
 */
public class User implements Serializable{

    private List<Stat> stats;
    private String name;
    private String title;
    private ColorCustom player1;
    private ColorCustom player2;
    private int previousXp;
    private int actualXp;
    private int nextXp;
    private int level;

    public User(Context context) {
        previousXp = 0;
        actualXp = 0;
        nextXp = 0;
        level = 0;
        player1 = new ColorCustom();
        player2 = new ColorCustom();
        stats = new ArrayList<>();
        Log.d("NEW USER", "new");
        for( EGameMode gameMode : GameModeSet.getEGameMode(context)){
            for(EIA ia : IASet.getEIAs(context)){
                stats.add(new Stat(ia, gameMode, EMode.SOLO));
                stats.add(new Stat(ia, gameMode, EMode.MULTI));
            }
        }
    }

    public void computeXpEarned(EMode mode, EGameMode gameMode, Object difficulty) {
        
    }

    private void userEarnedXp(int xp){
        computeXpNeededNextLevel();
        actualXp += xp;
        if(actualXp > nextXp){
            previousXp = nextXp;
            actualXp = actualXp-nextXp;
            level++;

        }
    }
    //(i-1)+(i*const)
    private void computeXpNeededNextLevel(){
        nextXp = previousXp + (level * Constants.CONSTANT_LEVEL);
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

    public void save(Context context){
        Gson gson = new Gson();
        String userJson = gson.toJson(this);
        SharedPreferences sharedPref = ((Activity)context).getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(Constants.PREFERENCES_USER, userJson);
        editor.apply();
    }

    public User load(Context context){
        Log.d("LOAD USER", "load");
        SharedPreferences sharedPref = ((Activity)context).getPreferences(Context.MODE_PRIVATE);
        String userJson = sharedPref.getString(Constants.PREFERENCES_USER, "");
        Gson gson = new Gson();
        if(!userJson.equalsIgnoreCase(""))
            return gson.fromJson(userJson, User.class);
        else
            return new User(context);
    }

}
