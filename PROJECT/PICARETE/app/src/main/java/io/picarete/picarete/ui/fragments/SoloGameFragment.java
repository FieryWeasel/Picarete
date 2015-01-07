package io.picarete.picarete.ui.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.picarete.picarete.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SoloGameFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SoloGameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SoloGameFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "column";
    private static final String ARG_PARAM2 = "row";
    private static final String ARG_PARAM3 = "name";
    private static final String ARG_PARAM4 = "mode";

    private String mode;
    private int column;
    private int row;
    private String iaName;

    private OnFragmentInteractionListener mListener;

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
        return inflater.inflate(R.layout.fragment_solo_game, container, false);
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
