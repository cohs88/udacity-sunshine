package com.example.sunshine2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.sunshine2.BuildConfig;// muy importante esto!


public class MainActivity extends ActionBarActivity {
    private static String LOG_TAG = "Chori Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Log.v(LOG_TAG, "ay wey! " + BuildConfig.OPEN_WEATHER_MAP_API_KEY);
        /*
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new ForecastFragment())
                    .commit();
        }
        */
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.v(LOG_TAG, "onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.v(LOG_TAG, "onStop");
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.v(LOG_TAG, "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.v(LOG_TAG, "onResume");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.v(LOG_TAG, "onDestroy");
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
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        if(id == R.id.action_map_location)
        {
            openPreferredLocationMap();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void openPreferredLocationMap()
    {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        /*
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String settingLocation = sharedPreferences.getString(
                getString(R.string.pref_location_key),
                getString(R.string.pref_location_default)
        );
        */
        String settingLocation = Utility.getPreferredLocation(this);

        //Uri geoLocation = Uri.parse("geo:0,0?q=" + settingLocation);
        Uri geoLocation = Uri.parse("geo:0,0?").buildUpon().appendQueryParameter("q", settingLocation).build();

        intent.setData(geoLocation);

        if (intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }
    }
}
