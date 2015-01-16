package io.picarete.picarete.ui.fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Spinner;

import io.picarete.picarete.R;
import io.picarete.picarete.model.container.ColorCustom;
import io.picarete.picarete.model.container.userdata.Config;
import io.picarete.picarete.model.container.userdata.UserAccessor;
import io.picarete.picarete.model.data_sets.ColorSet;
import io.picarete.picarete.model.data_sets.TitleSet;
import io.picarete.picarete.ui.color_picker.ColorPickerDialog;
import io.picarete.picarete.ui.color_picker.ColorPickerSwatch;
import io.picarete.picarete.ui.color_picker.ColorStateDrawable;
import io.picarete.picarete.ui.custom.CustomFontTextView;

public class ProfileFragment extends Fragment {

    int colorPlayer1;
    int colorPlayer2;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.

     * @return A new instance of fragment ProfileFragment.
     */
    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        ProgressBar progressXp = (ProgressBar)view.findViewById(R.id.seekBar_xp);
        progressXp.setProgress((int) (((double) UserAccessor.getUser(getActivity()).actualXp/ (double) UserAccessor.getUser(getActivity()).nextXp)*100.0));
        CustomFontTextView level = (CustomFontTextView) view.findViewById(R.id.lvl);
        level.setText(Integer.toString(UserAccessor.getUser(getActivity()).level));
        CustomFontTextView value_playedGames = (CustomFontTextView) view.findViewById(R.id.value_playedGames);
        value_playedGames.setText(Integer.toString(UserAccessor.getUser(getActivity()).getPlayedGames()));
        CustomFontTextView value_wonGames = (CustomFontTextView) view.findViewById(R.id.value_wonGames);
        value_wonGames.setText(Integer.toString(UserAccessor.getUser(getActivity()).getWonGames()));

        RatingBar ratio = (RatingBar) view.findViewById(R.id.value_ratio);
        ratio.setRating(UserAccessor.getUser(getActivity()).getRatio()*5);

        ImageView colorPlayer1 = (ImageView) view.findViewById(R.id.value_colorP1);
        initColorImage(colorPlayer1, 0);
        colorPlayer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] colors = new int[Config.getColors(UserAccessor.getUser(getActivity()).level).size()];
                int i = 0;
                for(ColorCustom color : Config.getColors(UserAccessor.getUser(getActivity()).level)){
                    colors[i] = color.getColor();
                    i++;
                }
                showColorPickerDialog(colors, 0);
            }
        });
        ImageView colorPlayer2 = (ImageView) view.findViewById(R.id.value_colorP2);
        initColorImage(colorPlayer2, 1);
        colorPlayer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] colors = new int[Config.getColors(UserAccessor.getUser(getActivity()).level).size()];
                int i = 0;
                for(ColorCustom color : Config.getColors(UserAccessor.getUser(getActivity()).level)){
                    colors[i] = color.getColor();
                    i++;
                }
                showColorPickerDialog(colors, 1);
            }
        });

        Spinner titles = (Spinner) view.findViewById(R.id.spinner_title);
        //titles.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, TitleSet.getUnlockedTitles(getActivity())));
        


        return view;
    }

    private void initColorImage(ImageView colorImage, int player) {
        Drawable[] colorDrawable = new Drawable[] {
                getActivity().getResources().getDrawable(R.drawable.calendar_color_picker_swatch)
        };
        switch (player){
            case 0 :
                if(colorPlayer1 == 0){
                    colorPlayer1 = ColorSet.colorTileBgPlayer1.getColor();
                }else{
                    colorPlayer1 = UserAccessor.getUser(getActivity()).colorPlayer1.getColor();
                }
                colorImage.setImageDrawable(new ColorStateDrawable(colorDrawable, colorPlayer1));
                break;
            case 1:
                if(colorPlayer2 == 0){
                    colorPlayer2 = ColorSet.colorTileBgPlayer2.getColor();
                }else{
                    colorPlayer2 = UserAccessor.getUser(getActivity()).colorPlayer2.getColor();
                }
                colorImage.setImageDrawable(new ColorStateDrawable(colorDrawable, colorPlayer2));
                break;
        }



    }

    private void showColorPickerDialog(int[] colors, final int player){
        ColorPickerDialog colorcalendar = ColorPickerDialog.newInstance(
                R.string.color_picker_default_title,
                colors,
                (player == 0 ? colorPlayer1 : colorPlayer2),
                5,
                ColorPickerDialog.SIZE_SMALL);

        //Implement listener to get selected color value
        colorcalendar.setOnColorSelectedListener(new ColorPickerSwatch.OnColorSelectedListener(){

            @Override
            public void onColorSelected(int color) {
                resetColor(player, color);
            }

        });

        colorcalendar.show(getFragmentManager(),"cal");
    }

    private void resetColor(int player, int color){
        //TODO switch displayed color
        if(player == 0) {
            colorPlayer1 = color;
        }else {
            colorPlayer2 = color;
        }
    }

}
