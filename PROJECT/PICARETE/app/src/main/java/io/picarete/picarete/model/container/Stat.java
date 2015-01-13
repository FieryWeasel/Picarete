package io.picarete.picarete.model.container;

import io.picarete.picarete.game_logics.EGameMode;
import io.picarete.picarete.game_logics.ia.EIA;
import io.picarete.picarete.model.EMode;

/**
 * Created by iem on 13/01/15.
 */
public class Stat {

    EIA ia;
    EGameMode gameMode;
    EMode mode;
    int win;
    int lost;
    int played;

    public Stat(EIA ia, EGameMode gameMode, EMode mode) {
        this.ia = ia;
        this.gameMode = gameMode;
        this.mode = mode;
        win = 0;
        lost = 0;
        played = 0;
    }
}
