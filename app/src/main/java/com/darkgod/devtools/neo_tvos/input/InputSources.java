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


import java.util.List;

/** Vendor source numbers for passthrough inputs, such as Xiaomi's EXTSRC_PLAY route. */
public final class InputSources {
    private InputSources() {
    }

    /** Returns the mapped source, -1 for a hidden input, or null when the input is not mapped. */
    public static Integer mapped(List<String> mappings, String inputId) {
        if (inputId == null || mappings == null) return null;
        String input = inputId.substring(inputId.lastIndexOf('/') + 1);
        for (String mapping : mappings) {
            if (mapping == null) continue;
            int equals = mapping.indexOf('=');
            if (equals <= 0 || !mapping.substring(0, equals).trim().equals(input)) continue;
            String value = mapping.substring(equals + 1).trim();
            if (value.equals("-")) return -1;
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException ignored) {
                return null;
            }
        }
        return null;
    }
}
