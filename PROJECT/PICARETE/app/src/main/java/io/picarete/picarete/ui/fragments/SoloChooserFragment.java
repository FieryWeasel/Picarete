package io.picarete.picarete.ui.fragments;

import android.app.Activity;
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
import io.picarete.picarete.ui.adapters.SpinnerModeAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SoloChooserFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SoloChooserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SoloChooserFragment extends Fragment {


    private OnFragmentInteractionListener mListener;
    private String[] gameModes;
    private String[] iA;
    private String nameIa;
    private String gameMode;

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

    public SoloChooserFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameModes = getResources().getStringArray(R.array.game_modes);
        iA = getResources().getStringArray(R.array.ia_difficulty);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_solo_chooser, container, false);


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

        Spinner spinnerIADifficulty = (Spinner) view.findViewById(R.id.spinner_ia);
        spinnerIADifficulty.setAdapter(new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.ia_difficulty)));
        spinnerIADifficulty.setSelection(0);
        spinnerIADifficulty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                nameIa = iA[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final NumberPicker columnPicker = (NumberPicker)view.findViewById(R.id.picker_column);
        columnPicker.setMaxValue(10);
        columnPicker.setMinValue(3);
        final NumberPicker rowPicker = (NumberPicker)view.findViewById(R.id.picker_row);
        rowPicker.setMaxValue(10);
        rowPicker.setMinValue(3);

        (view.findViewById(R.id.validate_modeChooser)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onPlayerReady(gameMode,
                        columnPicker.getValue(),
                        rowPicker.getValue(),
                        nameIa);
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
        public void onPlayerReady(String gameMode, int columnCount, int rowCount, String nameIa);
    }

}
