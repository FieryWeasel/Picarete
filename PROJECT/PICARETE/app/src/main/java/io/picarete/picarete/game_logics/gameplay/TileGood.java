package io.picarete.picarete.game_logics.gameplay;

/**
 * Created by root on 1/12/15.
 */
public class TileGood extends Tile{
    public TileGood(int id, Edge left, Edge top, Edge right, Edge bottom) {
        super(id, left, top, right, bottom);
    }

    @Override
    public int getScoreForPlayer() {
        return 2;
    }
}
