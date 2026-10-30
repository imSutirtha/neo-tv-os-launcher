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


/**
 * Which catalogue load of the app library fills it, and when the popup appears. Opening the library starts a load
 * and so does a package change (install, update, removal) while it is open or still loading; only the latest load
 * fills the catalogue, so the reveal an opening asked for belongs to whichever load ends up the latest, not to the
 * one the opening started. Without that a package change in the middle of an opening left the popup unshown.
 */
public final class LibraryLoads {
    private int latest;
    private boolean revealPending;

    /** Starts a load, from opening the library when reveal is true. Returns its number. */
    public int start(boolean reveal) {
        if (reveal) revealPending = true;
        return ++latest;
    }

    /** Whether a finished load is the latest one, the only one that fills the catalogue. */
    public boolean isLatest(int load) {
        return load == latest;
    }

    /** Once the latest load has filled the catalogue: whether the popup still has to appear. True once per opening. */
    public boolean takeReveal() {
        boolean reveal = revealPending;
        revealPending = false;
        return reveal;
    }
}
