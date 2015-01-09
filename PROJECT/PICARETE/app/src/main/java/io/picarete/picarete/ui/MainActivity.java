package io.picarete.picarete.ui;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import io.picarete.picarete.R;
import io.picarete.picarete.model.Constants;
import io.picarete.picarete.ui.fragments.CustomFragment;
import io.picarete.picarete.ui.fragments.HomeFragment;
import io.picarete.picarete.ui.fragments.MultiChooserFragment;
import io.picarete.picarete.ui.fragments.MultiFragment;
import io.picarete.picarete.ui.fragments.ProfileFragment;
import io.picarete.picarete.ui.fragments.SoloChooserFragment;
import io.picarete.picarete.ui.fragments.SoloGameFragment;


public class MainActivity extends ActionBarActivity implements HomeFragment.OnFragmentInteractionListener, SoloChooserFragment.OnFragmentInteractionListener,
        SoloGameFragment.OnFragmentInteractionListener, MultiFragment.OnFragmentInteractionListener, CustomFragment.OnFragmentInteractionListener, ProfileFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Instantiate a new fragment.
        Fragment newFragment = HomeFragment.newInstance();
        addFragmentToStack(newFragment, Constants.HOME);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    void addFragmentToStack(Fragment fragment, String tag) {
        // Add the fragment to the activity, pushing this transaction
        // on to the back stack.
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.frame, fragment, tag);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.addToBackStack(fragment.getTag());
        ft.commit();
    }

    @Override
    public void onModeChosen(String mode) {
        Fragment fragment;
        switch(mode){
            case Constants.SOLO_GAME:
                fragment = SoloChooserFragment.newInstance();
                addFragmentToStack(fragment, Constants.SOLO_GAME);
                break;
            case Constants.MULTI_GAME:
                fragment = MultiChooserFragment.newInstance();
                addFragmentToStack(fragment, Constants.MULTI_GAME);
                break;
            case Constants.PROFILE :
                fragment = ProfileFragment.newInstance();
                addFragmentToStack(fragment, Constants.PROFILE);
                break;
            case Constants.CUSTOM :
                fragment = CustomFragment.newInstance();
                addFragmentToStack(fragment, Constants.CUSTOM);
                break;
            default :
                break;
        }
    }

    @Override
    public void onPlayerReady(String gameMode, int columnCount, int rowCount, String nameIa) {
        Fragment fragment = SoloGameFragment.newInstance(gameMode, columnCount, rowCount, nameIa);
        addFragmentToStack(fragment, Constants.SOLO_GAME);
    }

    @Override
    public void onBackPressed() {

        if(getFragmentManager().getBackStackEntryCount()>1)
            getFragmentManager().popBackStack();
        else
            super.onBackPressed();
    }

    @Override
    public void onFragmentInteraction() {

    }
}
