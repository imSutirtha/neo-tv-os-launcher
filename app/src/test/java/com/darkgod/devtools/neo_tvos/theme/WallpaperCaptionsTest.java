package com.darkgod.devtools.neo_tvos.theme;

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


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Map;

import org.junit.Test;

public class WallpaperCaptionsTest {

    @Test
    public void readsFilenameTabName() {
        Map<String, String> names = WallpaperCaptions.parse(Arrays.asList(
                "001.jpg\tRed Spider Nebula · Hubble",
                "002.jpg\t  Falcon 9 at sunset  "));
        assertEquals("Red Spider Nebula · Hubble", names.get("001.jpg"));
        assertEquals("Falcon 9 at sunset", names.get("002.jpg"));
    }

    @Test
    public void skipsCommentsBlankAndMalformedLines() {
        Map<String, String> names = WallpaperCaptions.parse(Arrays.asList(
                "# captions for the collection", "", "   ", "no-tab-here.jpg Dusk", "\tno file", "003.jpg\t", null));
        assertTrue(names.isEmpty());
    }

    @Test
    public void cutsVeryLongNames() {
        StringBuilder longName = new StringBuilder();
        for (int i = 0; i < 30; i++) longName.append("word ");
        Map<String, String> names = WallpaperCaptions.parse(Arrays.asList("004.jpg\t" + longName));
        assertTrue(names.get("004.jpg").length() <= WallpaperCaptions.MAX_LENGTH);
    }

    @Test
    public void laterLineWinsForTheSameFile() {
        Map<String, String> names = WallpaperCaptions.parse(Arrays.asList("005.jpg\tOld", "005.jpg\tNew"));
        assertEquals("New", names.get("005.jpg"));
        assertFalse(names.containsKey("006.jpg"));
    }

    @Test
    public void missingFolderHasNoNames() {
        assertTrue(WallpaperCaptions.read(null).isEmpty());
    }
}
