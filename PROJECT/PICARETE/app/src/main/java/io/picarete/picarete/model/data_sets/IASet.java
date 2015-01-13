package io.picarete.picarete.model.data_sets;

import android.content.Context;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import io.picarete.picarete.R;
import io.picarete.picarete.game_logics.EGameMode;
import io.picarete.picarete.game_logics.ia.EIA;
import io.picarete.picarete.model.container.GameModeCustom;
import io.picarete.picarete.model.container.IACustom;

/**
 * Created by root on 1/12/15.
 */
public class IASet {
    public static Map<EIA, IACustom> IAs = null;

    public static void constructListGameMode(Context context){
        IAs = new LinkedHashMap<>();

        String[] iaName = context.getResources().getStringArray(R.array.ia_difficulty);

        IAs.put(EIA.EASY, new IACustom(iaName[0], 0));
    }

    public static Map<EIA, IACustom> getIAs(Context context){
        if(IAs == null)
            constructListGameMode(context);

        return IAs;
    }

    public static String[] getNames(Context context){
        if(IAs == null)
            constructListGameMode(context);

        String[] name = new String[IAs.size()];
        List<IACustom> IAsArr = new LinkedList<>(IAs.values());

        for (int i = 0; i< IAs.size(); i++){
            name[i] = IAsArr.get(i).name;
        }

        return name;
    }

    public static int[] getRating(Context context){
        if(IAs == null)
            constructListGameMode(context);

        int[] rating = new int[IAs.size()];
        List<IACustom> IAsArr = new ArrayList<>(IAs.values());

        for (int i = 0; i< IAs.size(); i++){
            rating[i] = IAsArr.get(i).rating;
        }

        return rating;
    }

    public static EIA[] getEIAs(Context context){
        if(IAs == null)
            constructListGameMode(context);

        EIA[] IAsArr = new EIA[IAs.keySet().size()];
        int i = 0;
        for(EIA e : IAs.keySet())
            IAsArr[i] = e;

        return IAsArr;
    }

    public static EIA searchIA(String ia){
        EIA mode;
        switch (ia){
            case "EASY":
                mode = EIA.EASY;
                break;
            case "EASY_MAX_TILE":
                mode = EIA.EASY_MAX_TILE;
                break;
            case "AGGRESSIVE":
                mode = EIA.AGGRESSIVE;
                break;
            case "MINIMAX":
                mode = EIA.MINIMAX;
                break;
            default:
                mode = EIA.EASY;
                break;
        }
        return mode;
    }
}
