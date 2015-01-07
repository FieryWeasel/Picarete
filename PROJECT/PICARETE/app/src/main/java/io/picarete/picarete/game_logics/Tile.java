package io.picarete.picarete.game_logics;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Debug;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by root on 1/6/15.
 */

public class Tile extends ImageView implements View.OnTouchListener{
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

    // Constructor
    public Tile(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Todo Draw triangles from Edges status
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // Todo Detect the Edge clicked
        Edge edge = null;
        MotionEvent.PointerCoords coor = new MotionEvent.PointerCoords();
        event.getPointerCoords(0, coor);
        float posX = coor.x;
        float posY = coor.y;

        edge = choseEdge(posX, posY);

        if(!edge.isChosen()){
            edge.setChosen(true);

            if(eventListener != null)
                eventListener.OnClick(this, edge);
        } else
            Log.d(this.getClass().getName(), "Edge already chosen by a player");


        return true;
    }

    private Edge choseEdge(float posX, float posY) {
        // http://math.stackexchange.com/questions/51326/determining-if-an-arbitrary-point-lies-inside-a-triangle-defined-by-three-points
        Edge edge = null;
        int width = 100, height = 100;

        Point p1 = new Point();
        Point p2 = new Point();
        Point p3 = new Point();
        Point p4 = new Point((int) posX, (int) posY);

        // Create points
        p1 = new Point(0, 0);
        p2 = new Point(width, 0);
        p3 = new Point(width/2, height/2);

        // Move the origin at one vertex
        p2 = moveToPoint(p2, p1);
        p3 = moveToPoint(p3, p1);
        p4 = moveToPoint(p4, p1);

        // Calculate the scalar d
        float d = p2.x*p3.y - p3.x*p2.y;
        // Calculate Barycentric weights
        //float w1 = p4.x(p2.y-p3.y) + p4.y

        if(posX > 0 && posX < width/2 && posY > 0 && posY < height)
            edge = edges.get(ETileSide.LEFT);
        else if(posX > 0 && posX < width && posY > 0 && posY < height/2)
            edge = edges.get(ETileSide.TOP);
        else  if(posX > width/2 && posX < width && posY > 0 && posY < height)
            edge = edges.get(ETileSide.RIGHT);
        else if(posX > 0 && posX < width && posY > height/2 && posY < height)
            edge = edges.get(ETileSide.BOTTOM);

        return edge;
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
