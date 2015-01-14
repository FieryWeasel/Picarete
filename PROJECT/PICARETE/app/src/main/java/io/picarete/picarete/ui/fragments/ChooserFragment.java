package io.picarete.picarete.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.NumberPicker;
import android.widget.Spinner;

import io.picarete.picarete.R;
import io.picarete.picarete.game_logics.EGameMode;
import io.picarete.picarete.model.Constants;
import io.picarete.picarete.model.data_sets.GameModeSet;
import io.picarete.picarete.ui.adapters.SpinnerModeAdapter;
import io.picarete.picarete.ui.custom.CustomFontSwitch;

public abstract class ChooserFragment extends Fragment {


    private EGameMode mGameMode;
    protected NumberPicker mColumnPicker;
    private NumberPicker mRowPicker;
    private CustomFontSwitch mSwitchChosenBorderTile;
    private CustomFontSwitch mSwitchChosenTile;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */
    protected abstract void createFragment();
    protected abstract View createViewFragment(LayoutInflater inflater, ViewGroup container);
    protected abstract void onValidate(EGameMode gameMode, int column, int row, boolean needChosenBorderTile, boolean needChosenTile);
    protected abstract void attachFragment(Activity activity);
    protected abstract void detachFragment();

    public ChooserFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = createViewFragment(inflater, container);

        Spinner spinnerMode = (Spinner) view.findViewById(R.id.mode_chooser_spinner_game_mode);
        spinnerMode.setAdapter(new SpinnerModeAdapter(getActivity(),
                android.R.layout.simple_spinner_item,
                GameModeSet.getTitles(getActivity()),
                GameModeSet.getDesc(getActivity())));
        spinnerMode.setSelection(0);
        spinnerMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mGameMode = GameModeSet.getEGameMode(getActivity())[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mColumnPicker = (NumberPicker) view.findViewById(R.id.mode_chooser_picker_column);
        mColumnPicker.setMaxValue(Constants.COLUMN_ROW_MAX);
        mColumnPicker.setMinValue(Constants.COLUMN_ROW_MIN);

        mRowPicker = (NumberPicker) view.findViewById(R.id.mode_chooser_picker_row);
        mRowPicker.setMaxValue(Constants.COLUMN_ROW_MAX);
        mRowPicker.setMinValue(Constants.COLUMN_ROW_MIN);

        mSwitchChosenBorderTile = (CustomFontSwitch) view.findViewById(R.id.mode_chooser_switch_chosen_border_tile);
        mSwitchChosenTile = (CustomFontSwitch) view.findViewById(R.id.mode_chooser_switch_chosen_tile);

        (view.findViewById(R.id.mode_chooser_validate)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onValidate(mGameMode, mColumnPicker.getValue(), mRowPicker.getValue(), mSwitchChosenBorderTile.isChecked(), mSwitchChosenTile.isChecked());
            }
        });

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


}
