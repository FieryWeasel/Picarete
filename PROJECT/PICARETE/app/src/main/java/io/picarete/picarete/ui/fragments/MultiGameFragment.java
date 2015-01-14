package io.picarete.picarete.ui.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.picarete.picarete.R;
import io.picarete.picarete.game_logics.EGameMode;
import io.picarete.picarete.model.Constants;
import io.picarete.picarete.model.EMode;
import io.picarete.picarete.model.container.userdata.User;

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

    public static MultiGameFragment newInstance(int col, int row, EGameMode mode, boolean needChosenBorderTile, boolean needChosenTile, User user) {
        MultiGameFragment fragment = new MultiGameFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.COLUMN_KEY, col);
        args.putInt(Constants.ROW_KEY, row);
        args.putSerializable(Constants.MODE_KEY, mode);
        args.putBoolean(Constants.NEED_BORDER_TILE_CHOSEN_KEY, needChosenBorderTile);
        args.putBoolean(Constants.NEED_TILE_CHOSEN_KEY, needChosenTile);
        args.putSerializable(Constants.USER_KEY, user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected View createViewFragment(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_game, container, false);
        return inflate;
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
    protected void initializeComponent() {
        UITitle.setText(R.string.multi);
    }

    @Override
    public void OnFinished() {
        int res = 0;
        if(game.getScores().get(0) > game.getScores().get(1))
            res = 1;
        else if(game.getScores().get(0) < game.getScores().get(1))
            res = -1;

        user.userFinishedAGame(EMode.MULTI, mode, null, 0, 0, 0, game.getScores().get(0), game.getScores().get(1), res);

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
                            getActivity().onBackPressed();
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
                            getActivity().onBackPressed();
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
                            getActivity().onBackPressed();
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
