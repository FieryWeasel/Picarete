package io.picarete.picarete.model.data_sets;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

import io.picarete.picarete.R;
import io.picarete.picarete.game_logics.EGameMode;
import io.picarete.picarete.model.container.GameModeCustom;

/**
 * Created by root on 1/12/15.
 */
public class GameModeSet {
    public static Map<EGameMode, GameModeCustom> gameModes = new HashMap<>();

    public static void constructListGameMode(Context context){
        String[] gameModesTitle = context.getResources().getStringArray(R.array.game_modes);
        String[] gameModesDesc = context.getResources().getStringArray(R.array.game_modes_descriptions);

        for (int i = 0; i < gameModesTitle.length; i++)
            gameModes.put(EGameMode.CLASSIC, new GameModeCustom(gameModesTitle[i], gameModesDesc[i]));

    }

    public static Map<EGameMode, GameModeCustom> getGameModes(Context context){
        if(gameModes == null)
            constructListGameMode(context);

        return gameModes;
    }
}
