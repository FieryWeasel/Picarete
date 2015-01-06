package io.picarete.picarete.game_logics;

import android.content.Context;
import android.graphics.Canvas;
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
    Map<ETileSide, Edge> edges;

    // Event Management
    TileEventListener eventListener = null;

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

        if(!edge.isChosen()){
            edge.setChosen(true);

            if(eventListener != null)
                eventListener.OnClick(this, edge);
        } else
            Log.d(this.getClass().getName(), "Edge already chosen by a player");


        return true;
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
