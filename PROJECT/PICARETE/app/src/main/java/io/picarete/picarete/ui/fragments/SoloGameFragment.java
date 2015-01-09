package io.picarete.picarete.ui.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Point;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.picarete.picarete.R;
import io.picarete.picarete.game_logics.Game;
import io.picarete.picarete.game_logics.Tile;
import io.picarete.picarete.game_logics.UITile;
import io.picarete.picarete.model.Constants;
import io.picarete.picarete.ui.adapters.GridAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SoloGameFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SoloGameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SoloGameFragment extends GameFragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private String iaName;


    private OnFragmentInteractionListener mListener;


    public static SoloGameFragment newInstance(int col, int row, String iaName, String mode) {
        SoloGameFragment fragment = new SoloGameFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.COLUMN_KEY, col);//col
        args.putInt(Constants.ROW_KEY, row);//row
        args.putString(Constants.IA_KEY, iaName);//IA Name
        args.putString(Constants.MODE_KEY, mode);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected View createViewFragment(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_solo_game, container, false);
    }

    @Override
    protected void createFragment(Bundle savedInstanceState) {
        if (getArguments() != null) {
            iaName = getArguments().getString(Constants.IA_KEY);
        }
    }

    @Override
    protected void attachFragment(Activity activity) {
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    protected void detachFragment() {
        mListener = null;
    }

    public SoloGameFragment() {
        // Required empty public constructor
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {

        public void onFragmentInteraction();
    }

}
