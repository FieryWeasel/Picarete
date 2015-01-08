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

    // Event Management
    TileEventListener eventListener = null;

    public Map<ETileSide, Edge> getEdges() {
        return edges;
    }

    public interface TileEventListener {
        public abstract void OnClick(Tile l, Edge edge);
    }

    public void setEventListener(TileEventListener e){
        this.eventListener = e;
    }

    public Tile(){
        edges = new HashMap<>();
        edges.put(ETileSide.LEFT, new Edge());
        edges.put(ETileSide.TOP, new Edge());
        edges.put(ETileSide.RIGHT, new Edge());
        edges.put(ETileSide.BOTTOM, new Edge());
    }

    public void onClick(int posX, int posY, int size){
        int i = posX;
        int y = posY;
        int a = size;
        Edge edge = choseEdge(posX, posY, size);

        if(!edge.isChosen()){
            edge.setChosen(true);



            eventListener.OnClick(this, edge);
        } else
            Log.d(this.getClass().getName(), "Edge already chosen by a player");
    }

    private Edge choseEdge(float posX, float posY, int size) {

        int width = size, height = size;

        // Create points
        Point pA;
        Point pB;
        Point pC = new Point(width/2, height/2);
        Point pP = new Point((int) posX, (int) posY);

        // LEFT
        pA = new Point(0, height);
        pB = new Point(0, 0);
        if(isPointOnTriangle(pA, pB, pC, pP))
            return edges.get(ETileSide.LEFT);

        // TOP
        pA = new Point(0, 0);
        pB = new Point(width, 0);
        if(isPointOnTriangle(pA, pB, pC, pP))
            return edges.get(ETileSide.TOP);

        // RIGHT
        pA = new Point(width, 0);
        pB = new Point(width, height);
        if(isPointOnTriangle(pA, pB, pC, pP))
            return edges.get(ETileSide.RIGHT);

        // BOTTOM
        pA = new Point(width, height);
        pB = new Point(0, height);
        if(isPointOnTriangle(pA, pB, pC, pP))
            return edges.get(ETileSide.BOTTOM);

        throw new ArithmeticException(this.getClass().getName()+"No edge can be found for the point : ("+posX+"/"+posY+")");
    }

    private boolean isPointOnTriangle(Point pA, Point pB, Point pC, Point pP){
        // http://math.stackexchange.com/questions/51326/determining-if-an-arbitrary-point-lies-inside-a-triangle-defined-by-three-points

        // Move the origin at one vertex
        pB = moveToPoint(pB, pA);
        pC = moveToPoint(pC, pA);
        pP = moveToPoint(pP, pA);

        // Calculate the scalar d
        float d = pB.x*pC.y - pC.x*pB.y;
        // Calculate Barycentric weights
        float wA = (pP.x*(pB.y-pC.y) + pP.y*(pC.x-pB.x) + pB.x*pC.y - pC.x*pB.y) / d;
        float wB = (pP.x*pC.y - pP.y*pC.x) / d;
        float wC = (pP.y*pB.x - pP.x*pB.y) / d;

        boolean isOnTirangle = true;

        if(wA < 0 || wA > 1)
            isOnTirangle = false;
        if(wB < 0 || wB > 1)
            isOnTirangle = false;
        if(wC < 0 || wC > 1)
            isOnTirangle = false;

        return isOnTirangle;
    }

    private Point moveToPoint(Point pointToMove, Point origin){
        Point p = new Point();

        p.x = pointToMove.x - origin.x;
        p.y = pointToMove.y - origin.y;

        return p;
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
