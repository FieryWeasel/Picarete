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

    private static final RecyclerView.OnItemTouchListener INTERCEPTATOR = new RecyclerView.OnItemTouchListener() {
        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            return true;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }
    };
    private RecyclerView recyclerView;
    private GridLayout grid;
    private List<UITile> uiTiles;

    private TextView turnPlayer1;
    private TextView turnIA;
    private TextView scorePlayer1;
    private TextView scoreIA;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
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
        grid = (GridLayout) inflate.findViewById(R.id.grid_game);

        createGame();
        resetScore();
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

    private void createGame(){
        game = new Game(getActivity());
        game.setEventListener(this);
        List<Tile> tiles = game.createGame(row, column);

        grid.setRowCount(row);
        grid.setColumnCount(column);
        uiTiles = new ArrayList<>();
        for(Tile t : tiles){
            UITile UITile = new UITile(getActivity());
            UITile.setTile(t);
            uiTiles.add(UITile);
            GridLayout.Spec row = GridLayout.spec(t.row);
            GridLayout.Spec col = GridLayout.spec(t.col);

            GridLayout.LayoutParams lp = new GridLayout.LayoutParams(row, col);
            lp.width = 300;
            lp.height = 300;
            grid.addView(UITile, lp);
        }
    }

    private void resetScore(){
        game.getScores().put(0, 0);
        game.getScores().put(1, 0);
    }

    @Override
    public void OnFinished() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // 2. Chain together various setter methods to set the dialog
        // characteristics
        builder.setTitle(R.string.dlg_solo_win_title);
        builder.setMessage(R.string.dlg_solo_win_msg);
        // 3. Add the buttons
        builder.setNegativeButton(R.string.dlg_solo_win_retry,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked Continue button
                        createGame();
                        resetScore();
                    }
                });
        builder.setPositiveButton(R.string.dlg_solo_win_continue,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked Continue button
                        // Todo Call the back method to go the selection
                    }
                });

        // 4. Get the AlertDialog from create()
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void OnMajGUI(int idPlayer) {
        /*if(idPlayer == 1)
            recyclerView.addOnItemTouchListener(INTERCEPTATOR);
        else
            recyclerView.removeOnItemTouchListener(INTERCEPTATOR);*/
    }

    @Override
    public void OnMajTile(List<Tile> tilesToRedraw) {
        for(UITile t : uiTiles){
            if(tilesToRedraw.contains(t.getTile()))
                t.invalidate();
        }
        Log.d(this.getClass().getName(), "Need to redraw elements");
    }

    @Override
    public void OnNextPlayer(int idPlayer) {

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
