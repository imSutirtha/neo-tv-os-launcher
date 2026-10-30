package com.darkgod.devtools.neo_tvos.data.weather;

import com.darkgod.devtools.neo_tvos.R;
import com.darkgod.devtools.neo_tvos.card.*;
import com.darkgod.devtools.neo_tvos.config.*;
import com.darkgod.devtools.neo_tvos.data.app.*;
import com.darkgod.devtools.neo_tvos.data.weather.*;
import com.darkgod.devtools.neo_tvos.input.*;
import com.darkgod.devtools.neo_tvos.service.*;
import com.darkgod.devtools.neo_tvos.theme.*;
import com.darkgod.devtools.neo_tvos.ui.*;
import com.darkgod.devtools.neo_tvos.ui.decor.*;
import com.darkgod.devtools.neo_tvos.ui.widget.*;
import com.darkgod.devtools.neo_tvos.util.*;


import android.content.Context;
import android.content.SharedPreferences;

/** Preferences boundary for weather: no account, GPS, or background service. */
public final class WeatherStore {
    private static final String CITY = "weather_city", UNIT = "weather_unit", SUMMARY = "weather_summary", TEMP = "weather_temp";
    private static final String UPDATED = "weather_updated";
    public static WeatherSnapshot read(Context context) {
        SharedPreferences p = context.getSharedPreferences(Config.SETTINGS_NAME, Context.MODE_PRIVATE);
        String city = p.getString(CITY, "");
        if (city.isEmpty()) return null;
        return new WeatherSnapshot(city, p.getString(SUMMARY, "Unavailable"), p.getString(TEMP, "—"), p.getLong(UPDATED, 0));
    }
    public static void save(Context context, WeatherSnapshot value) {
        context.getSharedPreferences(Config.SETTINGS_NAME, Context.MODE_PRIVATE).edit()
                .putString(CITY, value.city).putString(SUMMARY, value.summary)
                .putString(TEMP, value.temperature).putLong(UPDATED, value.updatedAt).apply();
    }
    public static String unit(Context context) { return context.getSharedPreferences(Config.SETTINGS_NAME, Context.MODE_PRIVATE).getString(UNIT, "celsius"); }
}
