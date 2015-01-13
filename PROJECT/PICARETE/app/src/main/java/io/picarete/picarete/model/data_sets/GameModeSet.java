package io.picarete.picarete.model.data_sets;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import io.picarete.picarete.R;
import io.picarete.picarete.game_logics.EGameMode;
import io.picarete.picarete.model.container.GameModeCustom;

/**
 * Created by root on 1/12/15.
 */
public class GameModeSet {
    public static Map<EGameMode, GameModeCustom> gameModes = null;

    public static void constructListGameMode(Context context){
        gameModes = new LinkedHashMap<>();

        String[] gameModesTitle = context.getResources().getStringArray(R.array.game_modes);
        String[] gameModesDesc = context.getResources().getStringArray(R.array.game_modes_descriptions);

        gameModes.put(EGameMode.CLASSIC, new GameModeCustom(gameModesTitle[0], gameModesDesc[0]));
        gameModes.put(EGameMode.TILE_BAD, new GameModeCustom(gameModesTitle[2], gameModesDesc[2]));
        gameModes.put(EGameMode.TILE_GOOD, new GameModeCustom(gameModesTitle[4], gameModesDesc[4]));

    }

    public static Map<EGameMode, GameModeCustom> getGameModes(Context context){
        if(gameModes == null)
            constructListGameMode(context);

        return gameModes;
    }

    public static String[] getTitles(Context context){
        if(gameModes == null)
            constructListGameMode(context);

        String[] titles = new String[gameModes.size()];
        List<GameModeCustom> gameModesArr = new LinkedList<>(gameModes.values());

        for (int i = 0; i< gameModes.size(); i++){
            titles[i] = gameModesArr.get(i).title;
        }

        return titles;
    }

    public static String[] getDesc(Context context){
        if(gameModes == null)
            constructListGameMode(context);

        String[] desc = new String[gameModes.size()];
        List<GameModeCustom> gameModesArr = new ArrayList<>(gameModes.values());

        for (int i = 0; i< gameModes.size(); i++){
            desc[i] = gameModesArr.get(i).desc;
        }

        return desc;
    }

    public static EGameMode[] getEGameMode(Context context){
        if(gameModes == null)
            constructListGameMode(context);

        EGameMode[] gameModeArr = new EGameMode[gameModes.keySet().size()];
        int i = 0;
        for(EGameMode e : gameModes.keySet()){
            gameModeArr[i] = e;
            i++;
        }

        return gameModeArr;
    }
}
