package com.example.sunshine2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


import com.example.sunshine2.BuildConfig;// muy importante esto!

/**
 * A placeholder fragment containing a simple view.
 */
public class ForecastFragment extends Fragment {

    ArrayAdapter<String> mForecastAdapter;
    public ForecastFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ArrayList<String> weekForecast = new ArrayList<>();
        weekForecast.add("Today - Sunny - 88/63");
        weekForecast.add("Tomorrow - Sunny - 88/63");
        weekForecast.add("Weds - Sunny - 88/63");
        weekForecast.add("Thurs - Sunny - 88/63");

        // contexto, layout, listview, datos
        mForecastAdapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item_forecast, R.id.list_item_forecast_textview, new ArrayList<String>());

        View view = inflater.inflate(R.layout.fragment_main, container, false);

        ListView listView = (ListView)view.findViewById(R.id.listview_forecast);

        listView.setAdapter(mForecastAdapter);


        /*url*/
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Context context = getActivity().getApplicationContext();

                TextView textView = (TextView) view.findViewById(R.id.list_item_forecast_textview);
                String forecast = mForecastAdapter.getItem(position);

                Toast.makeText(context, forecast, Toast.LENGTH_SHORT).show();

                Intent detailIntent = new Intent(context, DetailActivity.class).putExtra(Intent.EXTRA_TEXT, forecast);
                startActivity(detailIntent);
            }
        });
        /*url*/

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        //menu.clear();
        inflater.inflate(R.menu.forecastfragment, menu);
    }

    @Override
    public void onStart() {
        super.onStart();

        updateWeather();
    }

    private void updateWeather()
    {
        String settingLocation = "";
        String settingUnits = "";
        //settings
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getActivity());//getActivity().getPreferences(0); //getActivity().getSharedPreferences("SettingsActivity", 0);

        settingLocation = settings.getString(
                getString(R.string.pref_location_key),
                getString(R.string.pref_location_default)
        );
        settingUnits = settings.getString(
                getString(R.string.pref_units_key),
                getString(R.string.pref_units_default)
        );
        Log.d("ActionRefreshTag", "settings units " + settingUnits);
        //settings

        //new FetchWeatherTask().doInBackground();
        FetchWeatherTask weatherTask = new FetchWeatherTask(getActivity(), mForecastAdapter);
        weatherTask.execute(settingLocation, settingUnits);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_refresh){

            updateWeather();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*gist*/
    /*gist*/

}
