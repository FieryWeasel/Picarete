package io.picarete.picarete.game_logics;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import io.picarete.picarete.game_logics.gameplay.Edge;
import io.picarete.picarete.game_logics.gameplay.Tile;


public class Game implements Tile.TileEventListener{
    private int idPlayer = 0;
    private Map<Integer, Integer> scores = null;

    private List<Tile> tiles = null;
    private int width = 0;
    private int height = 0;
    private EGameMode gameMode = EGameMode.CLASSIC;

    private List<Edge> edgesPreviousPlayed = null;

    private Context context = null;

    // Event Management
    GameEventListener eventListener = null;

    public Map<Integer, Integer> getScores() {
        return scores;
    }

    public interface GameEventListener {
        public abstract void OnFinished();

        public abstract void OnMajGUI(int idPlayerActual);

        public abstract void OnMajTile(List<Tile> tilesToRedraw);

        public abstract void OnNextPlayer(int idPlayerActual);
    }

    public void setEventListener(GameEventListener e){
        this.eventListener = e;
    }

    // Constructor
    public Game(Context context, EGameMode gameMode, int height, int width){
        this.tiles = new LinkedList<>();
        this.edgesPreviousPlayed = new LinkedList<>();
        this.scores = new HashMap<>();
        this.scores.put(0, 0);
        this.scores.put(1, 0);

        this.gameMode = gameMode;
        this.height = height;
        this.width = width;

        this.context = context;
    }

    @Override
    public void OnClick(Tile tile, Edge edge) {
        boolean hasCompletedATile = false;

        edgesPreviousPlayed.add(edge);
        edge.setIdPlayer(idPlayer);

        List<Tile> neighbor = findNeighbor(edge);
        for(Tile t : neighbor){
            if(t.isComplete()){
                t.setIdPlayer(idPlayer);
                addScoreForPlayer(idPlayer, t.getScoreForPlayer() + edge.getScoreForPlayer());
                hasCompletedATile = true;
            }
        }
        if(eventListener != null)
            eventListener.OnMajTile(neighbor);

        idPlayer = (idPlayer + 1) % 2;

        if(eventListener != null)
            eventListener.OnMajGUI(idPlayer);

        if(isGameEnd()){
            if(eventListener != null)
                eventListener.OnFinished();
            return;
        }

        if(gameMode != EGameMode.CONTINUE_TO_PLAY || (gameMode == EGameMode.CONTINUE_TO_PLAY && !hasCompletedATile)){
            if(eventListener != null)
                eventListener.OnNextPlayer(idPlayer);
        }
    }

    private void addScoreForPlayer(int idPlayer, int score){
        scores.put(idPlayer, scores.get(idPlayer)+score);
    }

    public List<Tile> findNeighbor(Edge edge){
        List<Tile> tilesNeighbor = new ArrayList<Tile>();

        for(int i = 0; i < tiles.size(); i++){
            for(Edge e : tiles.get(i).getEdges().values()){
                if(e == edge)
                    tilesNeighbor.add(tiles.get(i));
            }
        }

        return tilesNeighbor;
    }

    public boolean isGameEnd(){
        boolean isGameEnd = true;

        for(int i = 0; i < tiles.size(); i++){
            if(!tiles.get(i).isComplete())
                isGameEnd = false;
        }

        return isGameEnd;
    }

    public List<Tile> createGame(){
        tiles = BuilderFactory.getBuilder(gameMode).createGame(height, width, this);

        return tiles;
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public List<Edge> getEdgesPreviousPlayed() {
        return edgesPreviousPlayed;
    }
}
