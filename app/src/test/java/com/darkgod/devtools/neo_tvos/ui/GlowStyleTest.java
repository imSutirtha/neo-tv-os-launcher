package com.darkgod.devtools.neo_tvos.ui;

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

import static org.junit.Assert.assertEquals;

/** glow_style from config.xml or a theme: horizon by name, radial for anything else. */
public class GlowStyleTest {

    @Test
    public void horizonByNameAnyCase() {
        assertEquals(Config.GLOW_HORIZON, Config.glowStyle("horizon"));
        assertEquals(Config.GLOW_HORIZON, Config.glowStyle(" Horizon "));
    }

    @Test
    public void anythingElseIsRadial() {
        assertEquals(Config.GLOW_RADIAL, Config.glowStyle("radial"));
        assertEquals(Config.GLOW_RADIAL, Config.glowStyle(""));
        assertEquals(Config.GLOW_RADIAL, Config.glowStyle(null));
        assertEquals(Config.GLOW_RADIAL, Config.glowStyle("aurora"));
    }
}
