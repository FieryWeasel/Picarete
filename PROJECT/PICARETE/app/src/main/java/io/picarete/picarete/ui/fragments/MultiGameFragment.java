package io.picarete.picarete.ui.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import io.picarete.picarete.model.Constants;
import io.picarete.picarete.ui.adapters.GridAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MultiGameFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MultiGameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MultiGameFragment extends GameFragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private OnFragmentInteractionListener mListener;

    public static MultiGameFragment newInstance(int col, int row, String mode) {
        MultiGameFragment fragment = new MultiGameFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.COLUMN_KEY, col);
        args.putInt(Constants.ROW_KEY, row);
        args.putString(Constants.MODE_KEY, mode);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected View createViewFragment(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_multi_game, container, false);
    }

    @Override
    protected void createFragment(Bundle savedInstanceState) {

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

    public MultiGameFragment() {
        // Required empty public constructor
    }

    @Override
    public void OnFinished() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        if(game.getScores().get(0) > game.getScores().get(1)){
            // 2. Chain together various setter methods to set the dialog
            // characteristics
            builder.setTitle(R.string.dlg_multi_p1_win_title);
            builder.setMessage(R.string.dlg_multi_p1_win_msg);
            // 3. Add the buttons
            builder.setNegativeButton(R.string.dlg_multi_retry,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User clicked Continue button
                            createGame();
                            resetScore();
                        }
                    });
            builder.setPositiveButton(R.string.dlg_multi_continue,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User clicked Continue button
                            // Todo Call the back method to go the selection
                        }
                    });
        } else if(game.getScores().get(0) < game.getScores().get(1)){
            // 2. Chain together various setter methods to set the dialog
            // characteristics
            builder.setTitle(R.string.dlg_multi_p2_win_title);
            builder.setMessage(R.string.dlg_multi_p2_win_msg);
            // 3. Add the buttons
            builder.setNegativeButton(R.string.dlg_multi_retry,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User clicked Continue button
                            createGame();
                            resetScore();
                        }
                    });
            builder.setPositiveButton(R.string.dlg_multi_continue,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User clicked Continue button
                            // Todo Call the back method to go the selection
                        }
                    });
        } else {
            // 2. Chain together various setter methods to set the dialog
            // characteristics
            builder.setTitle(R.string.dlg_multi_equality_title);
            builder.setMessage(R.string.dlg_multi_equality_msg);
            // 3. Add the buttons
            builder.setNegativeButton(R.string.dlg_multi_retry,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User clicked Continue button
                            createGame();
                            resetScore();
                        }
                    });
            builder.setPositiveButton(R.string.dlg_multi_continue,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User clicked Continue button
                            // Todo Call the back method to go the selection
                        }
                    });
        }
        // 4. Get the AlertDialog from create()
        AlertDialog dialog = builder.create();
        dialog.show();
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
