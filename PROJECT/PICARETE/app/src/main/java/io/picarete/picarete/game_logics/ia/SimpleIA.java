package io.picarete.picarete.game_logics.ia;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.picarete.picarete.game_logics.Edge;
import io.picarete.picarete.game_logics.Tile;
import io.picarete.picarete.model.NoDuplicatesList;

/**
 * Created by root on 1/7/15.
 */
public class SimpleIA extends AIA {
    @Override
    public Edge findEdge(int height, int width, List<Tile> game, List<Edge> previousEdgesPlayed) {
        List<Edge> allEdgesPossible = new NoDuplicatesList<>();

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
                allEdgesPossible.add(edgeFree);
            }
        }
        if(allEdgesPossible.size() > 0){
            Random r = new Random();
            int Low = 0;
            int High = allEdgesPossible.size();
            int R = r.nextInt(High-Low) + Low;
            return allEdgesPossible.get(R);
        }

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
        if(allEdgesPossible.size() > 0){
            Random r = new Random();
            int Low = 0;
            int High = allEdgesPossible.size();
            int R = r.nextInt(High-Low) + Low;
            return allEdgesPossible.get(R);
        }

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
        if(allEdgesPossible.size() > 0) {
            Random r = new Random();
            int Low = 0;
            int High = allEdgesPossible.size();
            int R = r.nextInt(High - Low) + Low;
            return allEdgesPossible.get(R);
        } else {
            throw new ArithmeticException(this.getClass().getName()+ " - No such edge can be found with this algorithm");
        }

    }
}
