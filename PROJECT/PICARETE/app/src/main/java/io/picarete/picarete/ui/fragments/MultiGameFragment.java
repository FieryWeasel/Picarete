package io.picarete.picarete.ui.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import io.picarete.picarete.R;
import io.picarete.picarete.game_logics.Game;
import io.picarete.picarete.game_logics.Tile;
import io.picarete.picarete.game_logics.UITile;
import io.picarete.picarete.ui.adapters.GridAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MultiGameFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MultiGameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MultiGameFragment extends Fragment implements Game.GameEventListener {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "column";
    private static final String ARG_PARAM2 = "row";
    private static final String ARG_PARAM3 = "mode";

    private String mode;
    private int column;
    private int row;

    private Game game;
    private GridAdapter adapter;

    private OnFragmentInteractionListener mListener;

    private TextView turnPlayer1;
    private TextView turnPlayer2;
    private TextView scorePlayer1;
    private TextView scorePlayer2;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param gameMode name.
     * @param column number.
     * @param row number.
     * @return A new instance of fragment MultiGameFragment.
     */
    public static MultiGameFragment newInstance(String gameMode, int column, int row) {
        MultiGameFragment fragment = new MultiGameFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM3, gameMode);
        args.putInt(ARG_PARAM1, column);
        args.putInt(ARG_PARAM2, row);
        fragment.setArguments(args);
        return fragment;
    }

    public MultiGameFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            column = getArguments().getInt(ARG_PARAM1);
            row = getArguments().getInt(ARG_PARAM2);
            mode = getArguments().getString(ARG_PARAM3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_multi_game, container, false);

        turnPlayer1 = (TextView) inflate.findViewById(R.id.turn_p1);
        turnPlayer2 = (TextView) inflate.findViewById(R.id.turn_p2);
        scorePlayer1 = (TextView) inflate.findViewById(R.id.score_1);
        scorePlayer2 = (TextView) inflate.findViewById(R.id.score_2);

        return inflate;
    }

    private void updatePlayerScore(int score, int which){
        switch (which){
            case 1 :
                scorePlayer1.setText(score);
                break;
            case 2 :
                scorePlayer2.setText(score);
                break;
        }
    }

    private void updateTurn(int which){
        switch (which){
            case 1 :
                //its the player 1 turn
                break;
            case 2 :
                //its the player 2 turn
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
    public void OnMajTile(List<Tile> tilesToRedraw) {
        Log.d(this.getClass().getName(), "Need to redraw elements");
    }

    @Override
    public void OnNextPlayer(int idPlayer) {

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
