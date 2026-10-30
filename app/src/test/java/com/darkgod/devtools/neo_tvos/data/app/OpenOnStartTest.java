package com.darkgod.devtools.neo_tvos.data.app;

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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class OpenOnStartTest {
    private static final String SELF = "com.darkgod.devtools.neo_tvos";
    private static final String YOUTUBE = "com.google.android.youtube.tv";

    @Test public void firstHomeOfANewBootOpensTheApp() {
        assertTrue(OpenOnStart.due(12, 11, YOUTUBE, SELF));
        assertTrue(OpenOnStart.due(0, -1, YOUTUBE, SELF));
    }

    @Test public void laterHomeOfTheSameBootStaysHome() {
        assertFalse(OpenOnStart.due(12, 12, YOUTUBE, SELF));
    }

    @Test public void unknownBootCountStaysHome() {
        assertFalse(OpenOnStart.due(-1, -1, YOUTUBE, SELF));
    }

    @Test public void offStaysHome() {
        assertFalse(OpenOnStart.due(12, 11, "", SELF));
        assertFalse(OpenOnStart.due(12, 11, null, SELF));
    }

    @Test public void theLauncherItselfIsNeverOpened() {
        assertFalse(OpenOnStart.due(12, 11, SELF, SELF));
    }

    @Test public void onlineTheAppOpensAtOnce() {
        assertTrue(OpenOnStart.opensNow(true, 1_000, 60_000));
    }

    @Test public void offlineTheAppWaitsUntilTheEndOfTheWait() {
        assertFalse(OpenOnStart.opensNow(false, 59_999, 60_000));
        assertTrue(OpenOnStart.opensNow(false, 60_000, 60_000));
    }
}
