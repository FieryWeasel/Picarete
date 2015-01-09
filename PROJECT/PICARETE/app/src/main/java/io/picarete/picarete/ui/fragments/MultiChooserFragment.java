package io.picarete.picarete.ui.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.NumberPicker;
import android.widget.Spinner;

import io.picarete.picarete.R;
import io.picarete.picarete.model.Constants;
import io.picarete.picarete.ui.adapters.SpinnerModeAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MultiChooserFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MultiChooserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MultiChooserFragment extends Fragment {
    private String[] gameModes;
    private String gameMode;

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

    public MultiChooserFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameModes = getResources().getStringArray(R.array.game_modes);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_multi_chooser, container, false);


        Spinner spinnerMode = (Spinner) view.findViewById(R.id.spinner_mode);
        spinnerMode.setAdapter(new SpinnerModeAdapter(getActivity(),
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.game_modes),
                getResources().getStringArray(R.array.game_modes_descriptions)));
        spinnerMode.setSelection(0);
        spinnerMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gameMode = gameModes[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        (view.findViewById(R.id.spinner_ia)).setVisibility(View.GONE);
        (view.findViewById(R.id.label_ia)).setVisibility(View.GONE);

        final NumberPicker columnPicker = (NumberPicker)view.findViewById(R.id.picker_column);
        columnPicker.setMaxValue(Constants.COLUMN_ROW_MAX);
        columnPicker.setMinValue(Constants.COLUMN_ROW_MIN);
        final NumberPicker rowPicker = (NumberPicker)view.findViewById(R.id.picker_row);
        rowPicker.setMaxValue(10);
        rowPicker.setMinValue(3);

        (view.findViewById(R.id.validate_modeChooser)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onPlayersReady(gameMode,
                        columnPicker.getValue(),
                        rowPicker.getValue());
            }
        });

        return view;
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
        // TODO: Update argument type and name
        public void onPlayersReady(String gameMode, int column, int row);
    }

}
