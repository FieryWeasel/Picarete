package io.picarete.picarete.model.data_sets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import java.util.HashMap;
import java.util.Map;

import io.picarete.picarete.R;
import io.picarete.picarete.game_logics.gameplay.ETileSide;
import io.picarete.picarete.game_logics.gameplay.Tile;
import io.picarete.picarete.game_logics.gameplay.TileBad;
import io.picarete.picarete.game_logics.gameplay.TileGood;

/**
 * Created by root on 1/12/15.
 */
public class AssetsSet {
    private static Bitmap tileBackground = null;
    private static Bitmap tileBackgroundOverlayGood = null;
    private static Bitmap tileBackgroundOverlayBad = null;
    private static Bitmap edgeBackground = null;
    private static Map<ETileSide, Bitmap> edgesBackgroundRotated = new HashMap<>();

    public static Bitmap getEdgeBackground(Context context) {

        edgeBackground = BitmapFactory.decodeResource(context.getResources(), R.drawable.edge_bg);
        return edgeBackground;
    }

    public static Bitmap getTileBackground(Context context) {
        if(tileBackground == null)
            tileBackground = BitmapFactory.decodeResource(context.getResources(), R.drawable.tile_bg);
        return tileBackground;
    }

    public static Bitmap getEdgeBackgroundRotated(Context context, ETileSide side, Matrix matrix){
        if(!edgesBackgroundRotated.containsKey(side))
            edgesBackgroundRotated.put(side, Bitmap.createBitmap(getEdgeBackground(context), 0, 0, getEdgeBackground(context).getWidth(), getEdgeBackground(context).getHeight(), matrix, true));

        return edgesBackgroundRotated.get(side);
    }

    public static Bitmap getTileBackgroundOverlay(Context context, Tile tile) {
        if (tile instanceof TileBad) {
            if (tileBackgroundOverlayBad == null)
                tileBackgroundOverlayBad = BitmapFactory.decodeResource(context.getResources(), R.drawable.tile_bad);

            return tileBackgroundOverlayBad;
        } else if (tile instanceof TileGood) {
            if (tileBackgroundOverlayGood == null)
                tileBackgroundOverlayGood = BitmapFactory.decodeResource(context.getResources(), R.drawable.tile_good);

            return tileBackgroundOverlayGood;

        }
        return null;
    }
}
