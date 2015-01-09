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
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import java.util.HashMap;
import java.util.Map;

import io.picarete.picarete.R;

/**
 * Created by root on 1/8/15.
 */
public class UITile extends ImageView implements View.OnTouchListener{
    private int size = 0;
    private Tile tile;
    private Map<ETileSide, Edge> edges;

    // Constructor
    public UITile(Context context) {
        super(context);
        setOnTouchListener(this);
    }

    public UITile(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnTouchListener(this);
    }

    public UITile(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOnTouchListener(this);
    }

    public void setTile(Tile tile){
        this.tile = tile;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // Todo Draw triangles from Edges status

        Paint paint = new Paint();

        if(tile != null)
            edges = tile.getEdges();

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);
        canvas.drawPaint(paint);

        paint.setColor(Color.parseColor("#CD5C5C"));
        Bitmap tileBG = BitmapFactory.decodeResource(getResources(), R.drawable.tile_bg);
        canvas.drawBitmap(tileBG, null, new Rect(0, 0, getWidth(), getHeight()), paint);

        if(edges != null){
            Log.d(this.getClass().getName(), "Draw Tile : "+edges.size());
            for(Map.Entry<ETileSide, Edge> cursor : edges.entrySet()) {
                if(cursor.getValue().isChosen()){
                    Log.d(this.getClass().getName(), "Draw Edges : "+cursor.getValue().toString());
                    Bitmap tileEdge = BitmapFactory.decodeResource(getResources(), R.drawable.tile_edge);
                    Matrix matrix = new Matrix();

                    if(cursor.getKey() == ETileSide.LEFT){
                        matrix.postRotate(90);
                    } else if(cursor.getKey() == ETileSide.TOP){
                        matrix.postRotate(180);
                    } else if(cursor.getKey() == ETileSide.RIGHT){
                        matrix.postRotate(-90);
                    } else if(cursor.getKey() == ETileSide.BOTTOM){
                        matrix.postRotate(0);
                    }

                    Bitmap tileEdgeRotated = Bitmap.createBitmap(tileEdge , 0, 0, tileEdge.getWidth(), tileEdge.getHeight(), matrix, true);
                    canvas.drawBitmap(tileEdgeRotated, null, new Rect(0, 0, getWidth(), getHeight()), paint);
                }
            }
        }

        paint.setColor(Color.GREEN);
        paint.setTextSize(20);
        if(tile != null)
            canvas.drawText(Integer.toString(tile.id), 50, 50, paint);

        super.onDraw(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        size = (widthMeasureSpec > heightMeasureSpec ? heightMeasureSpec : widthMeasureSpec);
        super.onMeasure(size, size);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();

        switch (action){
            case MotionEvent.ACTION_UP:
                // Todo Is edges point on the Tile's edges or new objects ?
                edges = tile.getEdges();
                try {
                    Edge edge = choseEdge((int) event.getX(), (int) event.getY(), 300);
                    Log.d(this.getClass().getName(), "Chose edge : "+edge.toString());
                    tile.onClick(edge);
                } catch (ArithmeticException e){
                    Log.d(this.getClass().getName(), e.getMessage());
                }
                break;
        }

        return true;
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

    public Tile getTile() {
        return tile;
    }
}
