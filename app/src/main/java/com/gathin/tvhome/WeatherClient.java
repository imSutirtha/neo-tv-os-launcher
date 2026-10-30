package com.gathin.tvhome;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/** Open-Meteo client: invoked only by a user-triggered/visible-Home refresh, never a service. */
final class WeatherClient {
    interface Callback { void done(WeatherSnapshot value, Exception error); }
    static void refresh(String city, String unit, Callback callback) {
        new Thread(() -> { try { callback.done(load(city, unit), null); } catch (Exception e) { callback.done(null, e); } }, "weather").start();
    }
    private static WeatherSnapshot load(String city, String unit) throws Exception {
        String q = URLEncoder.encode(city, "UTF-8");
        JSONObject place = new JSONObject(get("https://geocoding-api.open-meteo.com/v1/search?count=1&name=" + q));
        JSONArray results = place.getJSONArray("results");
        JSONObject first = results.getJSONObject(0);
        double lat = first.getDouble("latitude"), lon = first.getDouble("longitude");
        boolean fahrenheit = "fahrenheit".equals(unit);
        JSONObject now = new JSONObject(get("https://api.open-meteo.com/v1/forecast?latitude=" + lat + "&longitude=" + lon + "&current=temperature_2m,weather_code&temperature_unit=" + (fahrenheit ? "fahrenheit" : "celsius"))).getJSONObject("current");
        String temp = Math.round(now.getDouble("temperature_2m")) + (fahrenheit ? "°F" : "°C");
        return new WeatherSnapshot(first.getString("name"), code(now.getInt("weather_code")), temp, System.currentTimeMillis());
    }
    private static String get(String address) throws Exception {
        HttpURLConnection c = (HttpURLConnection) new URL(address).openConnection();
        c.setConnectTimeout(5000); c.setReadTimeout(5000); c.setRequestProperty("Accept", "application/json");
        try (BufferedReader r = new BufferedReader(new InputStreamReader(c.getInputStream()))) { return r.readLine(); }
        finally { c.disconnect(); }
    }
    private static String code(int c) { return c == 0 ? "Clear" : c <= 3 ? "Cloudy" : c <= 67 ? "Rain" : c <= 77 ? "Snow" : "Storm"; }
}
