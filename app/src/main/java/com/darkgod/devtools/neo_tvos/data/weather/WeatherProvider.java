package com.darkgod.devtools.neo_tvos.data.weather;

/**
 * Interface contract for fetching current weather forecasts.
 */
public interface WeatherProvider {

    interface Callback {
        void done(WeatherSnapshot value, Exception error);
    }

    void fetchWeather(String city, String unit, Callback callback);
}
