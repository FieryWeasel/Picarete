package io.picarete.picarete.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.picarete.picarete.R;
import io.picarete.picarete.game_logics.EGameMode;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MultiChooserFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MultiChooserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MultiChooserFragment extends ChooserFragment {


    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MultiChooserFragment.
     */
    public static MultiChooserFragment newInstance() {
        MultiChooserFragment fragment = new MultiChooserFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void createFragment() {

    }

    @Override
    protected View createViewFragment(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_multi_chooser, container, false);

        (view.findViewById(R.id.spinner_ia)).setVisibility(View.GONE);
        (view.findViewById(R.id.label_ia)).setVisibility(View.GONE);

        return view;
    }

    @Override
    protected void onValidate(EGameMode gameMode, int column, int row) {
        mListener.onPlayersReady(gameMode,
                column,
                row);
    }

    public MultiChooserFragment() {
        // Required empty public constructor
    }

    @Override
    public void attachFragment(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void detachFragment() {
        super.onDetach();
        mListener = null;
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
        // TODO: Update argument type and name
        public void onPlayersReady(EGameMode gameMode, int column, int row);
    }

}
