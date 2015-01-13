package io.picarete.picarete.game_logics;

import android.util.Log;

import java.util.List;
import java.util.Random;

import io.picarete.picarete.game_logics.gameplay.ETileSide;
import io.picarete.picarete.game_logics.gameplay.Edge;
import io.picarete.picarete.game_logics.gameplay.Tile;
import io.picarete.picarete.game_logics.gameplay.TileBad;
import io.picarete.picarete.game_logics.gameplay.TileGood;

/**
 * Created by root on 1/12/15.
 */
public class BuilderGoodTile extends ABuilder {
    @Override
    public List<Tile> createGame(int height, int width, Game game) {
        for(int i = 0; i < height; i++){
            for (int j = 0; j < width; j++){
                Edge left;
                Edge top;
                Edge right = new Edge();
                Edge bottom = new Edge();
                if(i == 0){
                    top = new Edge();
                } else {
                    top = tiles.get((i-1)*width+j).getEdges().get(ETileSide.BOTTOM);
                    Log.d(this.getClass().getName(), "For UITile " + (i * width + j) + " / Top : " + Integer.toString((i - 1) * width + j));
                }

                if(j == 0){
                    left = new Edge();
                } else {
                    left = tiles.get(i*width+j-1).getEdges().get(ETileSide.RIGHT);
                    Log.d(this.getClass().getName(), "For UITile "+(i*width+j)+" / Left : "+Integer.toString((i)*width+j-1));
                }

                Tile t;
                if(generateTile())
                    t = new TileGood(i*width+j, left, top, right, bottom);
                else
                    t = new Tile(i*width+j, left, top, right, bottom);
                t.row = i;
                t.col = j;
                t.setEventListener(game);
                tiles.add(t);
            }
        }

        for(int i = 0; i < tiles.size(); i++){
            int xPosition = i % width;
            int yPosition = i / width;
            if(xPosition == 0)
                tiles.get(i).getEdges().get(ETileSide.LEFT).setChosen(true);
            if(yPosition == 0)
                tiles.get(i).getEdges().get(ETileSide.TOP).setChosen(true);
            if(xPosition == width-1)
                tiles.get(i).getEdges().get(ETileSide.RIGHT).setChosen(true);
            if(yPosition == height-1)
                tiles.get(i).getEdges().get(ETileSide.BOTTOM).setChosen(true);
        }

        int minEdges = 20;
        int maxEdges = 30;

        Random r = new Random();
        int percentOfEdges = r.nextInt(maxEdges - minEdges + 1) + minEdges;
        int numberOfEdge = percentOfEdges * getAllEdges().size() / 100;

        for (int i = 0; i < numberOfEdge; i++){
            int edgeID = new Random().nextInt(getAllEdges().size());
            Edge edge = getAllEdges().get(edgeID);
            if(!edge.isChosen()){
                edge.setChosen(true);
            }
        }

        return tiles;
    }

    private boolean generateTile(){
        int maxEdges = 3;

        Random r = new Random();
        int percentOfEdges = r.nextInt(maxEdges - 1 + 1) + 1;
        return (percentOfEdges == 1 ? true : false);
    }
}
