package io.picarete.picarete.game_logics.gameplay;

/**
 * Created by root on 1/12/15.
 */
public class TileBad extends Tile{
    public int scoreForPlayer = -1;

    public TileBad(int id, Edge left, Edge top, Edge right, Edge bottom) {
        super(id, left, top, right, bottom);
    }
}
