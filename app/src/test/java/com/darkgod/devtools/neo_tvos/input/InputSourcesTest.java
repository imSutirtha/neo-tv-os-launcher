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


import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class InputSourcesTest {
    @Test public void mapsTheLastInputComponent() {
        assertEquals(Integer.valueOf(25), InputSources.mapped(
                Collections.singletonList("HW7=25"), "service/.Hdmi/HW7"));
    }

    @Test public void trimsMappingWhitespace() {
        assertEquals(Integer.valueOf(25), InputSources.mapped(
                Collections.singletonList(" HW7 = 25 "), "service/.Hdmi/HW7"));
    }

    @Test public void aDashHidesAnInput() {
        assertEquals(Integer.valueOf(-1), InputSources.mapped(
                Collections.singletonList("HW8=-"), "service/.Hdmi/HW8"));
    }

    @Test public void anUnknownOrMalformedInputIsNotMapped() {
        assertNull(InputSources.mapped(Arrays.asList("HW5=23", "HW7=nope"), "service/.Hdmi/HW6"));
        assertNull(InputSources.mapped(Collections.singletonList("HW7=nope"), "service/.Hdmi/HW7"));
        assertNull(InputSources.mapped(null, "service/.Hdmi/HW7"));
    }
}
