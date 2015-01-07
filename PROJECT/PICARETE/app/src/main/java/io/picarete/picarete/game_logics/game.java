package io.picarete.picarete.game_logics;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by root on 1/6/15.
 */
public class Game implements Tile.TileEventListener{
    private int idPlayer = 0;
    private Map<Integer, Integer> scores = null;
    private List<Tile> tiles = null;
    private Context context = null;

    // Event Management
    GameEventListener eventListener = null;
    
    public interface GameEventListener {
        public abstract void OnFinished();

        public abstract void OnMajGUI();
    }

    public void setEventListener(GameEventListener e){
        this.eventListener = e;
    }

    // Constructor
    public Game(Context context){
        this.tiles = new ArrayList<Tile>();
        this.scores = new HashMap<Integer, Integer>();
        this.scores.put(0, 0);
        this.scores.put(1, 0);

        this.context = context;
    }

    @Override
    public void OnClick(Tile tile, Edge edge) {
        if(tile.isComplete()){
            addScoreForPlayer(idPlayer, 10);
        }

        edge.setIdPlayer(idPlayer);

        List<Tile> tiles = findNeighbor(edge);
        tiles.add(tile);
        majTile(tiles);

        idPlayer = (idPlayer + 1) % 2;

        if(eventListener != null)
            eventListener.OnMajGUI();

        if(isGameEnd()){
            if(eventListener != null)
                eventListener.OnFinished();
        }
    }

    private void addScoreForPlayer(int idPlayer, int score){
        scores.put(idPlayer, scores.get(idPlayer)+score);
    }

    private List<Tile> findNeighbor(Edge edge){
        List<Tile> tilesNeighbor = new ArrayList<Tile>();

        for(int i = 0; i < tiles.size(); i++){
            for(Edge e : tiles.get(i).edges.values()){
                if(e == edge)
                    tilesNeighbor.add(tiles.get(i));
            }
        }

        return tilesNeighbor;
    }

    private void majTile(List<Tile> tiles){
        for(int i = 0; i < tiles.size(); i++){
            tiles.get(i).invalidate();
        }
    }

    public boolean isGameEnd(){
        boolean isGameEnd = true;

        for(int i = 0; i < tiles.size(); i++){
            if(!tiles.get(i).isComplete())
                isGameEnd = false;
        }

        return isGameEnd;
    }

    public void createGame(int height, int widht){
        for(int i = 0; i < height; i++){
            for (int j = 0; j < widht; j++){
                Tile t = new Tile(this.context);
                t.setEventListener(this);
                tiles.add(i+j, t);
            }
        }

        majTile(tiles);
    }
}
