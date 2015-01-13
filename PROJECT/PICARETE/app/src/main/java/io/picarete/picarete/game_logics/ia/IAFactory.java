package io.picarete.picarete.game_logics.ia;

import android.util.Log;

import io.picarete.picarete.game_logics.ABuilder;
import io.picarete.picarete.game_logics.BuilderBadTile;
import io.picarete.picarete.game_logics.BuilderClassic;
import io.picarete.picarete.game_logics.BuilderGoodTile;
import io.picarete.picarete.game_logics.EGameMode;

/**
 * Created by root on 1/12/15.
 */
public class IAFactory {
    public static AIA getIA(EIA eIA){
        AIA IA = new SimpleIA();

        if(eIA == EIA.EASY){
            IA = new SimpleIA();
        } else {
            Log.d("BuilderFactory", "IA (" + eIA.toString() + ") is not recognized, default mode : " + EIA.EASY.toString());
        }

        return IA;
    }
}
