package io.picarete.picarete.model.container.userdata;

import android.content.Context;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.picarete.picarete.game_logics.EGameMode;
import io.picarete.picarete.game_logics.ia.EIA;
import io.picarete.picarete.model.Constants;
import io.picarete.picarete.model.EMode;
import io.picarete.picarete.model.SGM.NoDuplicatesList;
import io.picarete.picarete.model.container.ColorCustom;
import io.picarete.picarete.model.data_sets.ColorSet;
import io.picarete.picarete.model.data_sets.GameModeSet;
import io.picarete.picarete.model.data_sets.IASet;
import io.picarete.picarete.model.data_sets.TitleSet;

/**
 * Created by iem on 13/01/15.
 */
public class User implements Serializable{

    public interface UserEventListener{
        public void gainALevel(int level);
        public void gainAGameMode();
        public void gainARow(int row);
        public void gainAColumn(int column);
        public void gainAColor(ColorCustom color);
        public void gainAnIA();
        public void gainATitle(String title);
    }

    public UserEventListener listener = null;

    private List<Stat> stats;
    public String name;
    public String title;
    private ColorCustom colorPlayer1;
    private ColorCustom colorPlayer2;
    private int previousXp;
    public int actualXp;
    public int nextXp;
    public int level;

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
        computeXpNeededNextLevel();
        setColorPlayer1(new ColorCustom("#09C2BF"));
        setColorPlayer2(new ColorCustom("#FF6C6B"));
        title = "";
        stats = new ArrayList<>();
        name = "";
        Log.d("NEW USER", "new");
        for( EGameMode gameMode : GameModeSet.getEGameMode(context)){
            stats.add(new Stat(null, gameMode, EMode.MULTI));
            for(EIA ia : IASet.getEIAs(context)){
                stats.add(new Stat(ia, gameMode, EMode.SOLO));
            }
        }
    }

    public void userFinishedAGame(EMode mode, EGameMode gameMode, EIA difficulty, int tilesP1, int tilesP2, int tilesNeutral, int scoreP1, int scoreP2, int result){
        computeXpNeededNextLevel();
        actualXp += Math.ceil(computeXpEarned(mode, gameMode, difficulty, tilesP1, result));
        while(actualXp > nextXp){
            previousXp = nextXp;
            actualXp = actualXp-nextXp;
            level++;
            launchEventsForLevel(level);
            computeXpNeededNextLevel();
        }
    }

    private void launchEventsForLevel(int level) {
        if(listener != null){
            listener.gainALevel(level);
            for(AUnlock unlock : Config.getUnlockedElementsForLevel(level)){
                if(unlock instanceof UnlockColor)
                    listener.gainAColor(((UnlockColor) unlock).color);
                else if (unlock instanceof UnlockIA)
                    listener.gainAnIA();
                else if (unlock instanceof UnlockMode)
                    listener.gainAGameMode();
                else if (unlock instanceof UnlockRow)
                    listener.gainARow(((UnlockRow) unlock).row);
                else if (unlock instanceof UnlockColumn)
                    listener.gainAColumn(((UnlockColumn) unlock).column);
            }
        }
    }

    private double computeXpEarned(EMode mode, EGameMode gameMode, EIA difficulty, int tilesWin, int result) {
        double xpBase = (VALUE_TILES_WIN * tilesWin) + (result == -1 ? VALUE_GAME_WON : 0);

        if(difficulty == EIA.EASY){
            xpBase *= MULTIPLIER_EASY;

        }else if(difficulty == EIA.EASY_MAX_TILE){
            xpBase *= MULTIPLIER_EASY_MAX_TILE;

        }else if(difficulty == EIA.AGGRESSIVE){
            xpBase *= MULTIPLIER_AGGRESSIVE;

        }else if(difficulty == EIA.MINIMAX){
            xpBase *= MULTIPLIER_MINIMAX;
        }

        if(gameMode == EGameMode.CLASSIC){
            xpBase *= MULTIPLIER_CLASSIC;

        }else if(gameMode == EGameMode.EDGE_BAD){
            xpBase *= MULTIPLIER_EDGE_BAD;

        }else if(gameMode == EGameMode.EDGE_GOOD){
            xpBase *= MULTIPLIER_EDGE_GOOD;

        }else if(gameMode == EGameMode.TILE_BAD){
            xpBase *= MULTIPLIER_TILE_BAD;

        }else if(gameMode == EGameMode.TILE_GOOD){
            xpBase *= MULTIPLIER_TILE_GOOD;

        }else if(gameMode == EGameMode.BEST_AREA){
            xpBase *= MULTIPLIER_BEST_AREA;

        }else if(gameMode == EGameMode.CONTINUE_TO_PLAY){
            xpBase *= MULTIPLIER_CONTINUE_TO_PLAY;

        }

        if(result == 0)
            xpBase *= MULTIPLIER_GAME_EQUALITY;
        else if(result == 1)
            xpBase *= MULTIPLIER_GAME_LOST;

        return xpBase * (mode == EMode.SOLO ? MULTIPLIER_SOLO : MULTIPLIER_MULTI);
    }

    //(i-1)+(i*const)
    public void computeXpNeededNextLevel(){
        nextXp = previousXp + ((level+1) * Constants.CONSTANT_LEVEL);
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

    public int getPlayedGames(){
        int played = 0;

        for(Stat stat : stats){
            played += stat.getPlayed();
        }

        return played;
    }

    public int getWonGames(){
        int won = 0;

        for(Stat stat : stats){
            won += stat.getWin();
        }

        return won;
    }

    public float getRatio(){
        float ratio = 0;
        if(getPlayedGames() != 0)
           ratio = (float) getWonGames() / (float) getPlayedGames();
        return ratio;
    }

    public void MAJStats(Context context, EMode mode, EGameMode gameMode, EIA difficulty, int tilesP1, int tilesP2, int tilesNeutral, int scoreP1, int scoreP2, int result) {
        List<String> titlesBefore  = TitleSet.getUnlockedTitles(context);
        for(Stat stat : stats) {
            if (stat.gameMode == gameMode &&(stat.ia == null || stat.ia == difficulty) && stat.mode == mode) {
                StatGame statGame = new StatGame();
                statGame.tileP1 = tilesP1;
                statGame.tileP2 = tilesP2;
                statGame.tileNeutral = tilesNeutral;
                statGame.scoreP1 = scoreP1;
                statGame.scoreP2 = scoreP2;
                statGame.setIdWinner(result);
                stat.statGames.add(statGame);
            }
        }
        List<String> titlesAfter  = TitleSet.getUnlockedTitles(context);

        if(listener != null){
            for (String t : titlesAfter){
                if(!titlesBefore.contains(t))
                    listener.gainATitle(t);
            }
        }
    }

    public ColorCustom getColorPlayer1() {
        return colorPlayer1;
    }

    public void setColorPlayer1(ColorCustom colorPlayer1) {
        this.colorPlayer1 = colorPlayer1;
        ColorSet.colorEdgePlayer1 = colorPlayer1;
        ColorSet.colorTileBgPlayer1 = colorPlayer1;
    }

    public ColorCustom getColorPlayer2() {
        return colorPlayer2;
    }

    public void setColorPlayer2(ColorCustom colorPlayer2) {
        this.colorPlayer2 = colorPlayer2;
        ColorSet.colorEdgePlayer2 = colorPlayer2;
        ColorSet.colorTileBgPlayer2 = colorPlayer2;
    }
}
