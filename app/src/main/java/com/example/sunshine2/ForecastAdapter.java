package com.example.sunshine2;

/**
 * Created by servando on 12/7/2015.
 */

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sunshine2.data.WeatherContract;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ForecastAdapter extends CursorAdapter {
    private final int VIEW_TYPE_TODAY = 0;
    private final int VIEW_TYPE_FUTURE_DAY = 1;

    // flag to determine if we want to use a separate view for "today"
    private boolean mUseTodayLayout = true;

    // These indices are tied to FORECAST_COLUMNS.  If FORECAST_COLUMNS changes, these
    // must change.
    static final int COL_WEATHER_ID = 0;
    static final int COL_WEATHER_DATE = 1;
    static final int COL_WEATHER_DESC = 2;
    static final int COL_WEATHER_MAX_TEMP = 3;
    static final int COL_WEATHER_MIN_TEMP = 4;
    static final int COL_LOCATION_SETTING = 5;
    static final int COL_WEATHER_CONDITION_ID = 6;
    static final int COL_COORD_LAT = 7;
    static final int COL_COORD_LONG = 8;


    /**
     * Cache of the children views for a forecast list item.
     */
    public static class ViewHolder {
        public final ImageView iconView;
        public final TextView dateView;
        public final TextView descriptionView;
        public final TextView highTempView;
        public final TextView lowTempView;

        public ViewHolder(View view) {
            iconView = (ImageView) view.findViewById(R.id.list_item_icon);
            dateView = (TextView) view.findViewById(R.id.list_item_date_textview);
            descriptionView = (TextView) view.findViewById(R.id.list_item_forecast_textview);
            highTempView = (TextView) view.findViewById(R.id.list_item_high_textview);
            lowTempView = (TextView) view.findViewById(R.id.list_item_low_textview);
        }
    }


    public ForecastAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public int getItemViewType(int position) {
        return (position == 0 && mUseTodayLayout) ? VIEW_TYPE_TODAY : VIEW_TYPE_FUTURE_DAY;
    }

    public int getViewTypeCount()
    {
        return 2;
    }


    /*
        Remember that these views are reused as needed.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Choose the layout type
        int viewType = getItemViewType(cursor.getPosition());
        int layoutId = -1;
        // TODO: Determine layoutId from viewType
        if (viewType == VIEW_TYPE_TODAY){
            layoutId = R.layout.list_item_forecast_today;
        }
        else if(viewType == VIEW_TYPE_FUTURE_DAY)
        {
            layoutId = R.layout.list_item_forecast;
        }


        View view = LayoutInflater.from(context).inflate(layoutId, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);
        return view;
    }

    public void setUseTodayLayout(boolean useTodayLayout)
    {
        mUseTodayLayout = useTodayLayout;
    }

    /*
        This is where we fill-in the views with the contents of the cursor.
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor)
    {
        // Read weather icon ID from cursor
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        // Use placeholder image for now
        //viewHolder.iconView.setImageResource(R.mipmap.ic_launcher_2);
        int viewType = getItemViewType(cursor.getPosition());
        switch (viewType) {
            case VIEW_TYPE_TODAY: {
            // Get weather icon
            viewHolder.iconView.setImageResource(Utility.getArtResourceForWeatherCondition(
            cursor.getInt(ForecastFragment.COL_WEATHER_CONDITION_ID)));
            break;
            }
            case VIEW_TYPE_FUTURE_DAY: {
            // Get weather icon
            viewHolder.iconView.setImageResource(Utility.getIconResourceForWeatherCondition(
            cursor.getInt(ForecastFragment.COL_WEATHER_CONDITION_ID)));
            break;
            }
        }



        // TODO Read date from cursor
        long dateInMillis = cursor.getLong(ForecastFragment.COL_WEATHER_DATE);
        viewHolder.dateView.setText(Utility.getFriendlyDayString(context, dateInMillis));

        // TODO Read weather forecast from cursor
        String description = cursor.getString(ForecastFragment.COL_WEATHER_DESC);
        viewHolder.descriptionView.setText(description);

        viewHolder.iconView.setContentDescription(description);

        // Read user preference for metric or imperial temperature units
        boolean isMetric = Utility.isMetric(context);


        // Read high temperature from cursor
        double high = cursor.getDouble(ForecastFragment.COL_WEATHER_MAX_TEMP);
        TextView highView = (TextView) view.findViewById(R.id.list_item_high_textview);
        highView.setText(Utility.formatTemperature(context, high, isMetric));

        // TODO Read low temperature from cursor
        float low = cursor.getFloat(ForecastFragment.COL_WEATHER_MIN_TEMP);
        viewHolder.highTempView.setText(Utility.formatTemperature(context,high, isMetric));
    }
}
