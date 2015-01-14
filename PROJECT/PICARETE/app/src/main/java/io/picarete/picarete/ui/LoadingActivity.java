package io.picarete.picarete.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.gson.Gson;

import io.picarete.picarete.R;
import io.picarete.picarete.model.Constants;
import io.picarete.picarete.model.container.userdata.User;
import io.picarete.picarete.model.data_sets.Config;
import io.picarete.picarete.model.data_sets.TitleSet;

public class LoadingActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        new AsyncTask<Void, String, String>(){

            @Override
            protected String doInBackground(Void... params) {
                TitleSet.getTitles(LoadingActivity.this);
                Config.getLevels(LoadingActivity.this);
                User user = new User(LoadingActivity.this).load(LoadingActivity.this);
                Gson gson = new Gson();
                String userJson = gson.toJson(user);

                return userJson;
            }

            @Override
            protected void onPostExecute(String userJson) {
                super.onPostExecute(userJson);
                Intent intent = new Intent(LoadingActivity.this, MainActivity.class);
                intent.putExtra(Constants.EXTRA_USER, userJson);
                startActivity(intent);
                finish();
            }
        }.execute();



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_loading, menu);
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
}
