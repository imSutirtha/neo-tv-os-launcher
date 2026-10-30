package com.gathin.tvhome;

import android.content.Context;
import android.content.SharedPreferences;

/** Preferences boundary for weather: no account, GPS, or background service. */
final class WeatherStore {
    private static final String CITY = "weather_city", UNIT = "weather_unit", SUMMARY = "weather_summary", TEMP = "weather_temp";
    private static final String UPDATED = "weather_updated";
    static WeatherSnapshot read(Context context) {
        SharedPreferences p = context.getSharedPreferences(Config.SETTINGS_NAME, Context.MODE_PRIVATE);
        String city = p.getString(CITY, "");
        if (city.isEmpty()) return null;
        return new WeatherSnapshot(city, p.getString(SUMMARY, "Unavailable"), p.getString(TEMP, "—"), p.getLong(UPDATED, 0));
    }
    static void save(Context context, WeatherSnapshot value) {
        context.getSharedPreferences(Config.SETTINGS_NAME, Context.MODE_PRIVATE).edit()
                .putString(CITY, value.city).putString(SUMMARY, value.summary)
                .putString(TEMP, value.temperature).putLong(UPDATED, value.updatedAt).apply();
    }
    static String unit(Context context) { return context.getSharedPreferences(Config.SETTINGS_NAME, Context.MODE_PRIVATE).getString(UNIT, "celsius"); }
}
