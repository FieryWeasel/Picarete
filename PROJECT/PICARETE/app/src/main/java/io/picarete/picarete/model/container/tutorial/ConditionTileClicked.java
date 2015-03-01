package io.picarete.picarete.model.container.tutorial;

import io.picarete.picarete.game_logics.gameplay.Tile;
import io.picarete.picarete.model.SGM.SGMTutorial.SGMACondition;

/**
 * Created by Simon on 19/02/2015.
 */
public class ConditionTileClicked extends SGMACondition<Tile> {
    private int x = -1;
    private int y = -1;

    public ConditionTileClicked(int x, int y){
        this.x = x;
        this.y = y;
    }

    @Override
    public void sendAction(Tile... params) {
        if(params.length == 1 && params[0] instanceof Tile){
            if(params[0].col == x && params[0].row == y && params[0].isComplete()){
                this.setValidate(true);
            }
        }
    }
}
