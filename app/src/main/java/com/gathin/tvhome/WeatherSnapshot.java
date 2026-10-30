package com.gathin.tvhome;

/** Small persisted weather value; network work stays outside Home's UI thread. */
final class WeatherSnapshot {
    final String city, summary, temperature;
    final long updatedAt;
    WeatherSnapshot(String city, String summary, String temperature, long updatedAt) {
        this.city = city; this.summary = summary; this.temperature = temperature; this.updatedAt = updatedAt;
    }
    boolean stale(long now) { return now - updatedAt > 3 * 60 * 60 * 1000L; }
}
