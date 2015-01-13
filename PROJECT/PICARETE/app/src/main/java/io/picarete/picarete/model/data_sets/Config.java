package io.picarete.picarete.model.data_sets;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import io.picarete.picarete.game_logics.EGameMode;
import io.picarete.picarete.game_logics.ia.EIA;
import io.picarete.picarete.model.container.AUnlock;
import io.picarete.picarete.model.container.ColorCustom;
import io.picarete.picarete.model.container.Level;
import io.picarete.picarete.model.container.UnlockColor;
import io.picarete.picarete.model.container.UnlockIA;
import io.picarete.picarete.model.container.UnlockMode;

/**
 * Created by iem on 13/01/15.
 */
public class Config {

    private static List<Level> levels;

    private static void loadConfig(){
        levels = new ArrayList<>();
        //TODO load from parser
    }

    public static List<Level> getLevels(){
        if(levels == null)
            loadConfig();

        return levels;
    }

    public static List<ColorCustom> getColors(int level){
        List<ColorCustom> colors = new LinkedList<>();

        for(Level lvl : levels){

            if(lvl.id<=level && lvl.unlocks!=null) {

                for (AUnlock unlock : lvl.unlocks) {
                    if (unlock instanceof UnlockColor)
                        colors.add(((UnlockColor) unlock).color);
                }

            }

        }
        return colors;
    }

    public static List<EIA> getIAs(int level){
        List<EIA> colors = new LinkedList<>();

        for(Level lvl : levels){

            if(lvl.id<=level && lvl.unlocks!=null) {

                for (AUnlock unlock : lvl.unlocks) {
                    if (unlock instanceof UnlockIA)
                        colors.add(((UnlockIA) unlock).ia);
                }

            }

        }
        return colors;
    }

    public static List<EGameMode> getGameModes(int level){
        List<EGameMode> colors = new LinkedList<>();
        for(Level lvl : levels){
            if(lvl.id<=level && lvl.unlocks!=null) {
                for (AUnlock unlock : lvl.unlocks) {
                    if (unlock instanceof UnlockMode)
                        colors.add(((UnlockMode) unlock).gameMode);
                }
            }
        }
        return colors;
    }
}
