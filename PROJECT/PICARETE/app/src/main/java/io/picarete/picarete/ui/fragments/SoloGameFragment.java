package io.picarete.picarete.ui.fragments;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import io.picarete.picarete.R;
import io.picarete.picarete.game_logics.Game;
import io.picarete.picarete.game_logics.Tile;
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
public class SoloGameFragment extends Fragment implements Game.GameEventListener {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "column";
    private static final String ARG_PARAM2 = "row";
    private static final String ARG_PARAM3 = "name";
    private static final String ARG_PARAM4 = "mode";

    private String mode;
    private int column;
    private int row;
    private String iaName;

    private Game game;
    private GridAdapter adapter;

    private OnFragmentInteractionListener mListener;

    private TextView turnPlayer1;
    private TextView turnIA;
    private TextView scorePlayer1;
    private TextView scoreIA;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param gameMode name.
     * @param column number.
     * @param row number.
     * @param name IA.
     * @return A new instance of fragment SoloGameFragment.
     */
    public static SoloGameFragment newInstance(String gameMode, int column, int row, String name) {
        SoloGameFragment fragment = new SoloGameFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM4, gameMode);
        args.putInt(ARG_PARAM1, column);
        args.putInt(ARG_PARAM2, row);
        args.putString(ARG_PARAM3, name);
        fragment.setArguments(args);
        return fragment;
    }

    public SoloGameFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            column = getArguments().getInt(ARG_PARAM1);
            row = getArguments().getInt(ARG_PARAM2);
            iaName = getArguments().getString(ARG_PARAM3);
            mode = getArguments().getString(ARG_PARAM4);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_solo_game, container, false);

        getTileMeasure(column);

        turnPlayer1 = (TextView) inflate.findViewById(R.id.turn_p1);
        turnIA = (TextView) inflate.findViewById(R.id.turn_p2);
        scorePlayer1 = (TextView) inflate.findViewById(R.id.score_1);
        scoreIA = (TextView) inflate.findViewById(R.id.score_2);

        game = new Game(getActivity());
        game.setEventListener(this);
        List<Tile> tiles = game.createGame(row, column);

        RecyclerView recyclerView = (RecyclerView) inflate.findViewById(R.id.gridGame);
        GridLayoutManager manager = new GridLayoutManager(getActivity(), column);
        recyclerView.setLayoutManager(manager);

        adapter = new GridAdapter(tiles, getActivity());
        recyclerView.setAdapter(adapter);

        return inflate;
    }

    private int getTileMeasure(int column) {
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        width-= dpToPx(Constants.GRID_PADDING);
        return width/column;
    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getActivity().getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    private void updatePlayerScore(int score, int which){
        switch (which){
            case 1 :
                scorePlayer1.setText(score);
                break;
            case 2 :
                scoreIA.setText(score);
                break;
        }
    }

    private void updateTurn(int which){
        switch (which){
            case 1 :
                //its the player 1 turn
                break;
            case 2 :
                //its the ia turn
                break;
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void OnFinished() {

    }

    @Override
    public void OnMajGUI(int idPlayer) {

    }

    @Override
    public void OnMajTile() {
        adapter.notifyDataSetChanged();
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
