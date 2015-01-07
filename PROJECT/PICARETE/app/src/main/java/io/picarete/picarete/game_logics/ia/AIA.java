package io.picarete.picarete.game_logics.ia;

import java.util.List;

import io.picarete.picarete.game_logics.Edge;
import io.picarete.picarete.game_logics.Tile;

/**
 * Created by root on 1/7/15.
 */
public abstract class AIA {
    public abstract Edge findEdge(int height, int width, List<Tile> game, List<Edge> previousEdgesPlayed);
}
