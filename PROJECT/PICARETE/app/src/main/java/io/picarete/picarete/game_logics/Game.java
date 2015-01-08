package io.picarete.picarete.game_logics;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Game implements Tile.TileEventListener{
    private int idPlayer = 0;
    private Map<Integer, Integer> scores = null;

    private List<Tile> tiles = null;
    private int width = 0;
    private int height = 0;

    private List<Edge> edgesPreviousPlayed = null;

    private Context context = null;

    // Event Management
    GameEventListener eventListener = null;

    public interface GameEventListener {
        public abstract void OnFinished();

        public abstract void OnMajGUI(int idPlayer);

        public abstract void OnMajTile();
    }

    public void setEventListener(GameEventListener e){
        this.eventListener = e;
    }

    // Constructor
    public Game(Context context){
        this.tiles = new ArrayList<Tile>();
        this.edgesPreviousPlayed = new ArrayList<>();
        this.scores = new HashMap<>();
        this.scores.put(0, 0);
        this.scores.put(1, 0);

        this.context = context;
    }

    @Override
    public void OnClick(Tile tile, Edge edge) {
        if(tile.isComplete()){
            addScoreForPlayer(idPlayer, 10);
        }

        edgesPreviousPlayed.add(edge);
        edge.setIdPlayer(idPlayer);

        List<Tile> tiles = findNeighbor(edge);
        tiles.add(tile);
        if(eventListener != null)
            eventListener.OnMajTile();

        idPlayer = (idPlayer + 1) % 2;

        if(eventListener != null)
            eventListener.OnMajGUI(idPlayer);

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
            for(Edge e : tiles.get(i).getEdges().values()){
                if(e == edge)
                    tilesNeighbor.add(tiles.get(i));
            }
        }

        return tilesNeighbor;
    }

    private void majTile(){
        if(eventListener != null)
            eventListener.OnMajTile();
    }

    public boolean isGameEnd(){
        boolean isGameEnd = true;

        for(int i = 0; i < tiles.size(); i++){
            if(!tiles.get(i).isComplete())
                isGameEnd = false;
        }

        return isGameEnd;
    }

    public List<Tile> createGame(int height, int width){
        this.height = height;
        this.width = width;

        for(int i = 0; i < height; i++){
            for (int j = 0; j < width; j++){
                Tile t = new Tile();
                t.setEventListener(this);
                tiles.add(i + j, t);
            }
        }

        for(int i = 0; i < tiles.size(); i++){
            int xPosition = i % width;
            int yPosition = i / width;
            if(xPosition == 0)
                tiles.get(i).getEdges().get(ETileSide.LEFT).setChosen(true);
            else if(yPosition == 0)
                tiles.get(i).getEdges().get(ETileSide.TOP).setChosen(true);
            else if(xPosition == width-1)
                tiles.get(i).getEdges().get(ETileSide.RIGHT).setChosen(true);
            else if(yPosition == height-1)
                tiles.get(i).getEdges().get(ETileSide.BOTTOM).setChosen(true);
        }

        return tiles;
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public List<Edge> getEdgesPreviousPlayed() {
        return edgesPreviousPlayed;
    }
}
