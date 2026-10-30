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


/** Small persisted weather value; network work stays outside Home's UI thread. */
public final class WeatherSnapshot {
    public final String city, summary, temperature;
    public final long updatedAt;
    public WeatherSnapshot(String city, String summary, String temperature, long updatedAt) {
        this.city = city; this.summary = summary; this.temperature = temperature; this.updatedAt = updatedAt;
    }
    public boolean stale(long now) { return now - updatedAt > 3 * 60 * 60 * 1000L; }
}
