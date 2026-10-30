package com.darkgod.devtools.neo_tvos.input;

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

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/** Owner-selected TV inputs kept as launchable dock tiles. */
public final class InputPins {
    private static final String KEY = "pinned_inputs";
    private static final char SEPARATOR = '\u0001';

    public static final class Pin {
        public final String id;
        public final String label;

        public Pin(String id, String label) {
            this.id = id;
            this.label = label;
        }
    }

    private InputPins() { }

    public static List<Pin> load(Context context) {
        Set<String> raw = context.getSharedPreferences(Config.SETTINGS_NAME, Context.MODE_PRIVATE)
                .getStringSet(KEY, new LinkedHashSet<>());
        List<Pin> pins = new ArrayList<>();
        for (String value : raw) {
            int split = value.indexOf(SEPARATOR);
            if (split > 0 && split < value.length() - 1) {
                pins.add(new Pin(value.substring(0, split), value.substring(split + 1)));
            }
        }
        return pins;
    }

    public static void pin(Context context, String id, String label) {
        SharedPreferences preferences = context.getSharedPreferences(Config.SETTINGS_NAME, Context.MODE_PRIVATE);
        LinkedHashSet<String> values = new LinkedHashSet<>(preferences.getStringSet(KEY, new LinkedHashSet<>()));
        values.removeIf(value -> value.startsWith(id + SEPARATOR));
        values.add(id + SEPARATOR + label);
        preferences.edit().putStringSet(KEY, values).apply();
    }

    public static void remove(Context context, String id) {
        SharedPreferences preferences = context.getSharedPreferences(Config.SETTINGS_NAME, Context.MODE_PRIVATE);
        LinkedHashSet<String> values = new LinkedHashSet<>(preferences.getStringSet(KEY, new LinkedHashSet<>()));
        values.removeIf(value -> value.startsWith(id + SEPARATOR));
        preferences.edit().putStringSet(KEY, values).apply();
    }
}
