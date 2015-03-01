package io.picarete.picarete.ui.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.picarete.picarete.R;
import io.picarete.picarete.game_logics.gameplay.Tile;
import io.picarete.picarete.model.Constants;
import io.picarete.picarete.model.SGM.SGMTutorial.SGMStep;
import io.picarete.picarete.model.SGM.SGMTutorial.SGMTutorial;
import io.picarete.picarete.model.container.tutorial.ConditionTileClicked;
import io.picarete.picarete.model.container.tutorial.ResultDisplayMsg;
import io.picarete.picarete.model.container.userdata.UserAccessor;

/**
 * A simple {@link android.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link io.picarete.picarete.ui.fragments.TutorialFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link io.picarete.picarete.ui.fragments.TutorialFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TutorialFragment extends Fragment implements SGMTutorial.TutorialListener {

    private SGMTutorial<Tile> tutorial = null;
    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment HomeFragment.
     */
    public static TutorialFragment newInstance() {
        TutorialFragment fragment = new TutorialFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public TutorialFragment() {
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Todo : Create the view
        tutorial.addStep(new SGMStep(0, new ConditionTileClicked(0, 0), new ResultDisplayMsg(null, "")));

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

    @Override
    public void firstStepLaunch() {
        // Todo : Display the first message
    }

    @Override
    public void stepFinished() {
    }

    @Override
    public void sequenceFinished() {
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

    }

}
