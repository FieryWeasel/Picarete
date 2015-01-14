package io.picarete.picarete.model.container;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.picarete.picarete.game_logics.EGameMode;
import io.picarete.picarete.game_logics.ia.EIA;
import io.picarete.picarete.model.EMode;

/**
 * Created by iem on 13/01/15.
 */
public class Stat implements Serializable{

    public EIA ia;
    public EGameMode gameMode;
    public EMode mode;
    public List<StatGame> statGame;

    public Stat(EIA ia, EGameMode gameMode, EMode mode) {
        this.ia = ia;
        this.gameMode = gameMode;
        this.mode = mode;
        this.statGame = new ArrayList<>();
    }
    //TODO
    public int getWin(){
        return 0;
    }
     public int getLost(){
         return 0;
     }

    public int getPlayed(){
        return 0;
    }
}
