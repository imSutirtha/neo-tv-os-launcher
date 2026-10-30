package com.darkgod.devtools.neo_tvos.data.weather;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class WeatherTest {

    @Test
    public void testWeatherCodeToDescription() {
        assertEquals("Clear", WeatherClient.weatherCodeToDescription(0));
        assertEquals("Cloudy", WeatherClient.weatherCodeToDescription(1));
        assertEquals("Cloudy", WeatherClient.weatherCodeToDescription(2));
        assertEquals("Cloudy", WeatherClient.weatherCodeToDescription(3));
        assertEquals("Rain", WeatherClient.weatherCodeToDescription(51));
        assertEquals("Rain", WeatherClient.weatherCodeToDescription(65));
        assertEquals("Snow", WeatherClient.weatherCodeToDescription(71));
        assertEquals("Snow", WeatherClient.weatherCodeToDescription(75));
        assertEquals("Storm", WeatherClient.weatherCodeToDescription(95));
        assertEquals("Storm", WeatherClient.weatherCodeToDescription(99));
    }

    @Test
    public void testWeatherSnapshotCreationAndStale() {
        long now = System.currentTimeMillis();
        WeatherSnapshot snapshot = new WeatherSnapshot("Tokyo", "Clear", "22°C", now);

        assertEquals("Tokyo", snapshot.city);
        assertEquals("Clear", snapshot.summary);
        assertEquals("22°C", snapshot.temperature);
        assertEquals(now, snapshot.updatedAt);

        assertFalse(snapshot.stale(now + 1000 * 60 * 30));
        assertTrue(snapshot.stale(now + 1000 * 60 * 60 * 4));
    }
}
