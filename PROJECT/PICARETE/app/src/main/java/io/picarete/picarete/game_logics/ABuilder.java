package io.picarete.picarete.game_logics;

import java.util.ArrayList;
import java.util.List;

import io.picarete.picarete.game_logics.gameplay.Edge;
import io.picarete.picarete.game_logics.gameplay.Tile;

/**
 * Created by root on 1/12/15.
 */
public abstract class ABuilder {
    protected List<Tile> tiles = new ArrayList<>();

    public abstract List<Tile> createGame(int height, int width, Game game);

    protected List<Edge> getAllEdges(){
        List<Edge> edges = new ArrayList<>();

        if(tiles != null){
            for (Tile t : tiles){
                for(Edge e : t.getEdges().values()){
                    if(!edges.contains(e))
                        edges.add(e);
                }

            }
        }

        return edges;

    }

}
