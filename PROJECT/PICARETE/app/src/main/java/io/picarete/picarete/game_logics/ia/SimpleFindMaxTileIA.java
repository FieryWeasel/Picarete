package io.picarete.picarete.game_logics.ia;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import io.picarete.picarete.game_logics.Edge;
import io.picarete.picarete.game_logics.Tile;
import io.picarete.picarete.model.NoDuplicatesList;

/**
 * Created by root on 1/7/15.
 */
public class SimpleFindMaxTileIA extends AIA {
    @Override
    public Edge findEdge(int height, int width, List<Tile> game, List<Edge> previousEdgesPlayed) {
        List<Edge> allEdgesPossible = new NoDuplicatesList<>();
        Map<Edge, Integer> allEdgesPossibleWithMaxTile = new HashMap<>();
        int bestClose  = 0;
        Edge bestEdge = null;

        // Search to complete an existing tile
        for(Tile t : game){
            int nbEdgeFree = 4;
            Edge edgeFree = null;
            for(Edge e : t.getEdges().values()){
                if(!e.isChosen()){
                    nbEdgeFree--;
                    edgeFree = e;
                }
            }
            if(nbEdgeFree == 1){
                if(!allEdgesPossibleWithMaxTile.containsKey(edgeFree))
                    allEdgesPossibleWithMaxTile.put(edgeFree, 1);
                else
                    allEdgesPossibleWithMaxTile.put(edgeFree, allEdgesPossibleWithMaxTile.get(edgeFree)+1);

                bestClose = (allEdgesPossibleWithMaxTile.get(edgeFree) > bestClose ? allEdgesPossibleWithMaxTile.get(edgeFree)  : bestClose);
            }
        }
        for(Map.Entry<Edge, Integer> cursor : allEdgesPossibleWithMaxTile.entrySet()) {
            if(cursor.getValue() == bestClose)
                allEdgesPossible.add(cursor.getKey());
        }

        bestEdge = choseEdge(allEdgesPossible);
        if(bestEdge != null)
            return bestEdge;


        // Search to chose an edge free and with more 2 edge free
        allEdgesPossible.clear();
        for(Tile t : game){
            int nbEdgeFree = 4;
            List<Edge> edgesFree = null;
            for(Edge e : t.getEdges().values()){
                if(!e.isChosen()){
                    nbEdgeFree--;
                    edgesFree.add(e);
                }
            }
            if(nbEdgeFree > 2){
                for(Edge e : edgesFree)
                    allEdgesPossible.add(e);
            }
        }
        bestEdge = choseEdge(allEdgesPossible);
        if(bestEdge != null)
            return bestEdge;

        // Search to chose a free edge
        allEdgesPossible.clear();
        for(Tile t : game){
            List<Edge> edgesFree = null;
            for(Edge e : t.getEdges().values()){
                if(!e.isChosen()){
                    edgesFree.add(e);
                }
            }

            for(Edge e : edgesFree)
                allEdgesPossible.add(e);
        }

        bestEdge = choseEdge(allEdgesPossible);
        if(bestEdge != null)
            return bestEdge;
        else
            throw new ArithmeticException(this.getClass().getName()+ " - No such edge can be found with this algorithm");
    }

    public Edge choseEdge(List<Edge> edgesPossibles){
        if(edgesPossibles.size() > 0){
            Random r = new Random();
            int Low = 0;
            int High = edgesPossibles.size();
            int R = r.nextInt(High-Low) + Low;
            return edgesPossibles.get(R);
        }

        return null;
    }
}
