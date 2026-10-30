package com.darkgod.devtools.neo_tvos.ui.widget;

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

import org.junit.Test;

public class VpnPillTest {

    @Test
    public void greenWhileAnyVpnIsUp() {
        assertEquals(VpnBadge.PILL_UP, VpnBadge.pillState(true, true));
        assertEquals(VpnBadge.PILL_UP, VpnBadge.pillState(true, false));
    }

    @Test
    public void redWhileTheInstalledVpnAppIsDown() {
        assertEquals(VpnBadge.PILL_OFF, VpnBadge.pillState(false, true));
    }

    @Test
    public void iconOnlyShowsTheClosedLockOnlyWhileUp() {
        assertEquals(R.drawable.ic_lock, VpnBadge.iconOnlyLock(true));
        assertEquals(R.drawable.ic_lock_open, VpnBadge.iconOnlyLock(false));
    }

    @Test
    public void hiddenWithNoVpnAndNoInstalledVpnApp() {
        assertEquals(VpnBadge.PILL_HIDDEN, VpnBadge.pillState(false, false));
    }

    /**
     * At rest the pill is an outline, so its ink is the state colour; on the solid focused fill it is the
     * contrasting ink. A solid pill at rest read as selected, which is the bug this pins.
     */
    @Test
    public void inkContrastsTheSolidFillOnlyWhenFocused() {
        assertEquals(VpnBadge.INK, VpnBadge.pillInkColor(true, true));
        assertEquals(VpnBadge.GREEN, VpnBadge.pillInkColor(true, false));
        assertEquals(VpnBadge.INK_OFF, VpnBadge.pillInkColor(false, true));
        assertEquals(VpnBadge.RED, VpnBadge.pillInkColor(false, false));
    }
}
