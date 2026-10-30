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
import static org.junit.Assert.*;

public class LibraryLoadsTest {
    @Test public void aRefreshDuringTheOpeningStillRevealsOnce() {
        LibraryLoads loads = new LibraryLoads();
        int opening = loads.start(true);
        int refresh = loads.start(false);   // a package changed before the opening's result came back
        assertFalse(loads.isLatest(opening));
        assertTrue(loads.isLatest(refresh));
        assertTrue(loads.takeReveal());
        assertFalse(loads.takeReveal());
    }

    @Test public void aRefreshOnItsOwnNeverReveals() {
        LibraryLoads loads = new LibraryLoads();
        assertTrue(loads.isLatest(loads.start(false)));
        assertFalse(loads.takeReveal());
    }

    @Test public void anOpeningAfterARefreshRevealsWithItsOwnLoad() {
        LibraryLoads loads = new LibraryLoads();
        int refresh = loads.start(false);
        int opening = loads.start(true);
        assertFalse(loads.isLatest(refresh));
        assertTrue(loads.isLatest(opening));
        assertTrue(loads.takeReveal());
    }

    @Test public void refreshesAfterTheRevealDoNotRevealAgain() {
        LibraryLoads loads = new LibraryLoads();
        loads.start(true);
        assertTrue(loads.takeReveal());
        loads.start(false);
        assertFalse(loads.takeReveal());
    }
}
