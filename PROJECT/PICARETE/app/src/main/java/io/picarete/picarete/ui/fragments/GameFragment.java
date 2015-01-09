package io.picarete.picarete.ui.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
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

public abstract class GameFragment extends Fragment implements Game.GameEventListener {

    protected String mode;
    protected int column;
    protected int row;
    private Game game;

    private GridLayout grid;
    private List<UITile> uiTiles;
    private int size;

    protected TextView turnPlayer1;
    protected TextView turnPlayer2;
    protected TextView scorePlayer1;
    protected TextView scorePlayer2;

    protected abstract View createViewFragment(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);
    protected abstract void createFragment(Bundle savedInstanceState);
    protected abstract void attachFragment(Activity activity);
    protected abstract void detachFragment();

    public GameFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            column = getArguments().getInt(Constants.COLUMN_KEY);
            row = getArguments().getInt(Constants.ROW_KEY);
            mode = getArguments().getString(Constants.MODE_KEY);
        }
        createFragment(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = createViewFragment(inflater, container, savedInstanceState);

        turnPlayer1 = (TextView) view.findViewById(R.id.turn_p1);
        turnPlayer2 = (TextView) view.findViewById(R.id.turn_p2);
        scorePlayer1 = (TextView) view.findViewById(R.id.score_1);
        scorePlayer2 = (TextView) view.findViewById(R.id.score_2);

        grid = (GridLayout) view.findViewById(R.id.grid_game);

        size = getTileMeasure(column);

        createGame();
        resetScore();

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        attachFragment(activity);
    }


    @Override
    public void onDetach() {
        super.onDetach();
        detachFragment();
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
            lp.width = size;
            lp.height = size;
            grid.addView(UITile, lp);
        }
    }

    private void resetScore(){
        game.getScores().put(0, 0);
        game.getScores().put(1, 0);
    }

    private int getTileMeasure(int column) {
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        width -= dpToPx(Constants.GRID_PADDING);
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

    }
}
