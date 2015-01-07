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

public class Tile extends ImageView implements View.OnTouchListener{
    private Map<ETileSide, Edge> edges;
    private int size = 0;

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
        edges = new HashMap<>();
        edges.put(ETileSide.LEFT, new Edge());
        edges.put(ETileSide.TOP, new Edge());
        edges.put(ETileSide.RIGHT, new Edge());
        edges.put(ETileSide.BOTTOM, new Edge());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // Todo Draw triangles from Edges status

        Paint paint = new Paint();

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);
        canvas.drawPaint(paint);

        paint.setColor(Color.parseColor("#CD5C5C"));
        Bitmap tileBG = BitmapFactory.decodeResource(getResources(), R.drawable.tile_bg);
        canvas.drawBitmap(tileBG, null, new Rect(0, 0, getWidth(), getHeight()), paint);

        /*
        for(Map.Entry<ETileSide, Edge> cursor : edges.entrySet()) {
            if(cursor.getValue().isChosen()){
                Bitmap tileEdge = BitmapFactory.decodeResource(getResources(), R.drawable.tile_bg);
                Matrix matrix = new Matrix();

                if(cursor.getKey() == ETileSide.LEFT){
                    matrix.postRotate(0);
                } else if(cursor.getKey() == ETileSide.TOP){
                    matrix.postRotate(0);
                } else if(cursor.getKey() == ETileSide.RIGHT){
                    matrix.postRotate(0);
                } else if(cursor.getKey() == ETileSide.BOTTOM){
                    matrix.postRotate(0);
                }

                Bitmap tileEdgeRotated = Bitmap.createBitmap(tileEdge , 0, 0, tileEdge.getWidth(), tileEdge.getHeight(), matrix, true);
                canvas.drawBitmap(tileEdgeRotated, null, new Rect(0, 0, getWidth(), getHeight()), paint);
            }
        }
        */

        super.onDraw(canvas);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //size = (widthMeasureSpec > heightMeasureSpec ? heightMeasureSpec : widthMeasureSpec);
        size = 100;
        super.onMeasure(size, size);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        MotionEvent.PointerCoords coor = new MotionEvent.PointerCoords();
        event.getPointerCoords(0, coor);
        Edge edge = choseEdge(coor.x, coor.y);

        if(!edge.isChosen()){
            edge.setChosen(true);

            if(eventListener != null)
                eventListener.OnClick(this, edge);
        } else
            Log.d(this.getClass().getName(), "Edge already chosen by a player");


        return true;
    }

    private Edge choseEdge(float posX, float posY) {

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
