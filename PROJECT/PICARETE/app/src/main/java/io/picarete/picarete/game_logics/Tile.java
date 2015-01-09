package io.picarete.picarete.game_logics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import java.util.HashMap;
import java.util.Map;

import io.picarete.picarete.R;

public class Tile{
    private Map<ETileSide, Edge> edges;
    public int id = -1;
    public int row = -1;
    public int col = -1;

    // Event Management
    TileEventListener eventListener = null;

    public Map<ETileSide, Edge> getEdges() {
        return edges;
    }

    public interface TileEventListener {
        public abstract void OnClick(Tile l, Edge edge);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tile)) return false;

        Tile tile = (Tile) o;

        if (col != tile.col) return false;
        if (id != tile.id) return false;
        if (row != tile.row) return false;
        if (!edges.equals(tile.edges)) return false;
        if (eventListener != null ? !eventListener.equals(tile.eventListener) : tile.eventListener != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = edges.hashCode();
        result = 31 * result + id;
        result = 31 * result + row;
        result = 31 * result + col;
        result = 31 * result + (eventListener != null ? eventListener.hashCode() : 0);
        return result;
    }

    public void setEventListener(TileEventListener e){
        this.eventListener = e;
    }

    public Tile(int id, Edge left, Edge top, Edge right, Edge bottom){
        edges = new HashMap<>();
        edges.put(ETileSide.LEFT, left);
        edges.put(ETileSide.TOP, top);
        edges.put(ETileSide.RIGHT, right);
        edges.put(ETileSide.BOTTOM, bottom);

        this.id = id;
    }

    public void onClick(Edge edge){

        if(!edge.isChosen()){
            edge.setChosen(true);
            eventListener.OnClick(this, edge);
        } else
            Log.d(this.getClass().getName(), "Edge already chosen by a player");
    }

    public boolean isComplete(){
        boolean isComplete = true;

        for(Edge e : edges.values()){
            if(!e.isChosen())
                isComplete = false;
        }

        return isComplete;
    }

}
