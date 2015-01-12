package io.picarete.picarete.game_logics;

import android.util.Log;

/**
 * Created by root on 1/12/15.
 */
public class BuilderFactory {
    public static ABuilder getBuilder(EGameMode gameMode){
        ABuilder builder = new BuilderClassic();

        if(gameMode == EGameMode.CLASSIC){
            builder = new BuilderClassic();
        } else if(gameMode == EGameMode.TILE_GOOD){
            builder = new BuilderGoodTile();
        } else if(gameMode == EGameMode.TILE_BAD){
            builder = new BuilderBadTile();
        } else {
            Log.d("BuilderFactory", "Game mode ("+gameMode.toString()+") is not recognized, default mode : "+EGameMode.CLASSIC.toString());
        }

        return builder;
    }
}
