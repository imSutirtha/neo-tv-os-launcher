package com.darkgod.devtools.neo_tvos.data.weather;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Open-Meteo client: invoked only by a user-triggered or visible-Home refresh, never a background service.
 * Performs lightweight geocoding and current weather queries off the main UI thread.
 */
public final class WeatherClient implements WeatherProvider {

    private static final int TIMEOUT_MS = 5000;
    private static final ExecutorService EXECUTOR = Executors.newSingleThreadExecutor(r -> {
        Thread thread = new Thread(r, "weather-worker");
        thread.setDaemon(true);
        return thread;
    });

    public interface Callback {
        void done(WeatherSnapshot value, Exception error);
    }

    public WeatherClient() {
    }

    @Override
    public void fetchWeather(String city, String unit, WeatherProvider.Callback callback) {
        refresh(city, unit, (value, error) -> {
            if (callback != null) {
                callback.done(value, error);
            }
        });
    }

    public static void refresh(String city, String unit, Callback callback) {
        if (city == null || city.trim().isEmpty()) {
            if (callback != null) {
                callback.done(null, new IllegalArgumentException("City name is empty"));
            }
            return;
        }
        EXECUTOR.execute(() -> {
            try {
                WeatherSnapshot snapshot = load(city.trim(), unit);
                if (callback != null) {
                    callback.done(snapshot, null);
                }
            } catch (Exception e) {
                if (callback != null) {
                    callback.done(null, e);
                }
            }
        });
    }

    private static WeatherSnapshot load(String city, String unit) throws Exception {
        String encodedCity = URLEncoder.encode(city, StandardCharsets.UTF_8.name());
        String geocodingUrl = "https://geocoding-api.open-meteo.com/v1/search?count=1&name=" + encodedCity;
        JSONObject place = new JSONObject(get(geocodingUrl));
        JSONArray results = place.optJSONArray("results");
        if (results == null || results.length() == 0) {
            throw new IOException("Location not found for: " + city);
        }
        JSONObject first = results.getJSONObject(0);
        double lat = first.getDouble("latitude");
        double lon = first.getDouble("longitude");

        boolean fahrenheit = "fahrenheit".equals(unit);
        String weatherUrl = "https://api.open-meteo.com/v1/forecast?latitude=" + lat + "&longitude=" + lon
                + "&current=temperature_2m,weather_code&temperature_unit=" + (fahrenheit ? "fahrenheit" : "celsius");

        JSONObject forecast = new JSONObject(get(weatherUrl));
        JSONObject current = forecast.getJSONObject("current");
        String temp = Math.round(current.getDouble("temperature_2m")) + (fahrenheit ? "°F" : "°C");
        return new WeatherSnapshot(first.getString("name"), weatherCodeToDescription(current.getInt("weather_code")),
                temp, System.currentTimeMillis());
    }

    private static String get(String address) throws Exception {
        HttpURLConnection connection = (HttpURLConnection) new URL(address).openConnection();
        connection.setConnectTimeout(TIMEOUT_MS);
        connection.setReadTimeout(TIMEOUT_MS);
        connection.setRequestProperty("Accept", "application/json");
        try {
            int statusCode = connection.getResponseCode();
            if (statusCode < 200 || statusCode >= 300) {
                throw new IOException("HTTP " + statusCode + " fetching " + address);
            }
            StringBuilder response = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
            }
            return response.toString();
        } finally {
            connection.disconnect();
        }
    }

    public static String weatherCodeToDescription(int code) {
        if (code == 0) return "Clear";
        if (code <= 3) return "Cloudy";
        if (code <= 67) return "Rain";
        if (code <= 77) return "Snow";
        return "Storm";
    }
}
