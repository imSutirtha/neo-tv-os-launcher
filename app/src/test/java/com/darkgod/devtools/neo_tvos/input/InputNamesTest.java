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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class InputNamesTest {
    @Test public void philipsTypeBecomesAnEmoji() {
        assertEquals("🎮 Xbox", InputNames.tidy("Xbox&GAME_CONSOLE"));
        assertEquals("🎮 Switch", InputNames.tidy("Switch &GAME_CONSOLE"));
        assertEquals("💻 Ordinateur", InputNames.tidy("Ordinateur&COMPUTER"));
    }

    @Test public void anUnknownTypeLeavesTheNameAlone() {
        assertEquals("Box", InputNames.tidy("Box&SOMETHING_NEW"));
    }

    @Test public void aTypeWithNoNameIsJustItsEmoji() {
        assertEquals("🎮", InputNames.tidy("&GAME_CONSOLE"));
    }

    @Test public void theTextNullAndBlanksAreNoName() {
        assertNull(InputNames.tidy("null"));
        assertNull(InputNames.tidy("  "));
        assertNull(InputNames.tidy(null));
    }

    @Test public void otherNamesAreKept() {
        assertEquals("MacBook Pro", InputNames.tidy("MacBook Pro"));
        assertEquals("Tom & Jerry", InputNames.tidy("Tom & Jerry"));
        assertEquals("AT&T", InputNames.tidy("AT&T"));
        assertEquals("R&D", InputNames.tidy("R&D"));
    }
}
