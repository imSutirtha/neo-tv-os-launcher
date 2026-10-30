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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GamepadPassthroughTest {
    // Sources as dumpsys input reports them on a TCL G07.
    @Test public void xboxControllerIsAGamepad() {
        assertTrue(GamepadPassthrough.isGamepad(0x01000511));
    }

    @Test public void voiceRemoteWithJoystickIsNot() {
        assertFalse(GamepadPassthrough.isGamepad(0x01000311));
    }

    @Test public void virtualSearchKeyboardIsNot() {
        assertFalse(GamepadPassthrough.isGamepad(0x00000701));
    }

    @Test public void keypadIsNot() {
        assertFalse(GamepadPassthrough.isGamepad(0x00000101));
    }
}
