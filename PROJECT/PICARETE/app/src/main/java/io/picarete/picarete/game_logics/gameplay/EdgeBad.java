package io.picarete.picarete.game_logics.gameplay;

/**
 * Created by root on 1/12/15.
 */
public class EdgeBad extends Edge {
    @Override
    public int getScoreForPlayer() {
        return -1;
    }
}
