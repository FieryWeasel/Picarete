package io.picarete.picarete.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;
import java.util.List;

import io.picarete.picarete.R;
import io.picarete.picarete.game_logics.Tile;
import io.picarete.picarete.game_logics.UITile;

/**
 * Created by root on 1/8/15.
 */
public class GridAdapter extends RecyclerView.Adapter<GridAdapter.ViewHolder> {
    private LinkedList<Tile> tiles;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public UITile UITile;

        public ViewHolder(View itemView) {
            super(itemView);

            UITile = (UITile) itemView.findViewById(R.id.grid_item_tile);
        }
    }

    public GridAdapter(List<Tile> tiles, Context context){
        this.tiles = new LinkedList<>();
        this.tiles.addAll(tiles);
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.d(this.getClass().getName(), "Position : "+position);
        Log.d(this.getClass().getName(), "UI : "+holder.UITile.toString());
        holder.UITile.setTile(tiles.get(position));
    }

    @Override
    public int getItemCount() {
        return tiles.size();
    }


}
