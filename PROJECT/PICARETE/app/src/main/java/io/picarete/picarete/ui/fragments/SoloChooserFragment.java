package io.picarete.picarete.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


import io.picarete.picarete.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SoloChooserFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SoloChooserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SoloChooserFragment extends ChooserFragment {


    private OnFragmentInteractionListener mListener;

    private String[] mIA;
    private String mNameIa;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.

     * @return A new instance of fragment SoloFragment.
     */
    public static SoloChooserFragment newInstance() {
        SoloChooserFragment fragment = new SoloChooserFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void createFragment() {
        mIA = getResources().getStringArray(R.array.ia_difficulty);
    }

    @Override
    protected View createViewFragment(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_solo_chooser, container, false);

        Spinner spinnerIADifficulty = (Spinner) view.findViewById(R.id.spinner_ia);
        spinnerIADifficulty.setAdapter(new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.ia_difficulty)));
        spinnerIADifficulty.setSelection(0);
        spinnerIADifficulty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mNameIa = mIA[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }

    @Override
    protected void onValidate(String gameMode, int column, int row) {
        mListener.onPlayerReady(gameMode,
                column,
                row,
                mNameIa);
    }

    @Override
    protected void attachFragment(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    protected void detachFragment() {
        super.onDetach();
        mListener = null;
    }

    public SoloChooserFragment() {
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

        public void onPlayerReady(String gameMode, int columnCount, int rowCount, String nameIa);
    }

}
