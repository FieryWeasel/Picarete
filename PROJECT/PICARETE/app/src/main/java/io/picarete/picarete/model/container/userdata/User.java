package io.picarete.picarete.model.container.userdata;

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
import io.picarete.picarete.model.container.ColorCustom;
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

    public static final double MULTIPLIER_SOLO = 1;
    public static final double MULTIPLIER_MULTI = 0.5;

    public static final double MULTIPLIER_EASY = 1;
    public static final double MULTIPLIER_EASY_MAX_TILE = 2;
    public static final double MULTIPLIER_AGGRESSIVE = 2;
    public static final double MULTIPLIER_MINIMAX = 3;

    public static final double MULTIPLIER_CLASSIC = 1;
    public static final double MULTIPLIER_EDGE_BAD = 2;
    public static final double MULTIPLIER_EDGE_GOOD = 2;
    public static final double MULTIPLIER_TILE_BAD = 2;
    public static final double MULTIPLIER_TILE_GOOD = 2;
    public static final double MULTIPLIER_BEST_AREA = 3;
    public static final double MULTIPLIER_CONTINUE_TO_PLAY = 4;

    public static final double VALUE_TILES_WIN = 1;
    public static final double VALUE_GAME_WON = 10;
    public static final double MULTIPLIER_GAME_EQUALITY = 0.7;
    public static final double MULTIPLIER_GAME_LOST = 0.5;

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

    public void userFinishedAGame(EMode mode, EGameMode gameMode, EIA difficulty, int tilesP1, int tilesP2, int tilesNeutral, int scoreP1, int scoreP2, int result){
        computeXpNeededNextLevel();
        actualXp += computeXpEarned(mode, gameMode, difficulty, tilesP1, result);
        if(actualXp > nextXp){
            previousXp = nextXp;
            actualXp = actualXp-nextXp;
            level++;

        }
        //TODO save stats and user
    }

    private double computeXpEarned(EMode mode, EGameMode gameMode, EIA difficulty, int tilesWin, int result) {
        double xpBase = VALUE_GAME_WON + (VALUE_TILES_WIN * tilesWin) + (result == -1 ? VALUE_GAME_WON : 0);
        double xpDifficulty = 0;
        double xpGameMode = 0;

        if(difficulty == EIA.EASY){
            xpDifficulty = xpBase * MULTIPLIER_EASY;

        }else if(difficulty == EIA.EASY_MAX_TILE){
            xpDifficulty = xpBase * MULTIPLIER_EASY_MAX_TILE;

        }else if(difficulty == EIA.AGGRESSIVE){
            xpDifficulty = xpBase * MULTIPLIER_AGGRESSIVE;

        }else if(difficulty == EIA.MINIMAX){
            xpDifficulty = xpBase * MULTIPLIER_MINIMAX;

        }

        if(gameMode == EGameMode.CLASSIC){
            xpGameMode = xpDifficulty * MULTIPLIER_CLASSIC;

        }else if(gameMode == EGameMode.EDGE_BAD){
            xpGameMode = xpDifficulty * MULTIPLIER_EDGE_BAD;

        }else if(gameMode == EGameMode.EDGE_GOOD){
            xpGameMode = xpDifficulty * MULTIPLIER_EDGE_GOOD;

        }else if(gameMode == EGameMode.TILE_BAD){
            xpGameMode = xpDifficulty * MULTIPLIER_TILE_BAD;

        }else if(gameMode == EGameMode.TILE_GOOD){
            xpGameMode = xpDifficulty * MULTIPLIER_TILE_GOOD;

        }else if(gameMode == EGameMode.BEST_AREA){
            xpGameMode = xpDifficulty * MULTIPLIER_BEST_AREA;

        }else if(gameMode == EGameMode.CONTINUE_TO_PLAY){
            xpGameMode = xpDifficulty * MULTIPLIER_CONTINUE_TO_PLAY;

        }

        if(result == 0)
            xpGameMode = xpGameMode * MULTIPLIER_GAME_EQUALITY;
        else if(result == 1)
            xpGameMode = xpGameMode * MULTIPLIER_GAME_LOST;

        return xpGameMode * (mode == EMode.SOLO ? MULTIPLIER_SOLO : MULTIPLIER_MULTI);
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
