package io.picarete.picarete.ui.adapters;

import android.app.Activity;
import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.List;

import io.picarete.picarete.R;

/**
 * Created by iem on 07/01/15.
 */
public class SpinnerModeAdapter extends ArrayAdapter<String>{

    private String mNames[];
    private String mDescriptions[];
    private LayoutInflater inflater;

    public SpinnerModeAdapter(Context context, int resource, String mNames[], String mDescriptions[]) {
        super(context, resource);
        this.mNames = mNames;
        this.mDescriptions = mDescriptions;
        inflater = ((Activity)context).getLayoutInflater();
    }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        ViewHolder holder; // to reference the child views for later actions
        if (view == null) {
            view = inflater.inflate(R.layout.item_spinner_mode, parent, false);
            holder = new ViewHolder();
            holder.title = (TextView)view.findViewById(R.id.title);
            holder.description = (TextView)view.findViewById(R.id.mode_desc);
            view.setTag(holder);
        }else{
            holder = (ViewHolder)view.getTag();
        }

        holder.title.setText(mNames[position]);
        holder.description.setText(mDescriptions[position]);

        return view;
    }

    // It gets a View that displays the data at the specified position
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        ViewHolder holder; // to reference the child views for later actions
        if (view == null) {
            view = inflater.inflate(R.layout.item_spinner_mode, parent, false);
            holder = new ViewHolder();
            holder.title = (TextView)view.findViewById(R.id.title);
            view.setTag(holder);
        }else{
            holder = (ViewHolder)view.getTag();
        }

        holder.title.setText(mNames[position]);

        return view;
    }

    private class ViewHolder {
        TextView title;
        TextView description;
    }



}
