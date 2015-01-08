package io.picarete.picarete.game_logics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
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

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);
        canvas.drawPaint(paint);

        paint.setColor(Color.parseColor("#CD5C5C"));
        Bitmap tileBG = BitmapFactory.decodeResource(getResources(), R.drawable.tile_bg);
        canvas.drawBitmap(tileBG, null, new Rect(0, 0, getWidth(), getHeight()), paint);


        for(Map.Entry<ETileSide, Edge> cursor : edges.entrySet()) {
            if(cursor.getValue().isChosen()){
                Bitmap tileEdge = BitmapFactory.decodeResource(getResources(), R.drawable.tile_edge);
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
                tile.onClick((int) event.getX(), (int) event.getY(), 100);
                break;
        }


        return true;
    }

    public void setEdges(Map<ETileSide, Edge> edges) {
        this.edges = edges;
    }
}
