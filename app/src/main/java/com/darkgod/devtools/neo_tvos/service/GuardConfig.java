package com.darkgod.devtools.neo_tvos.service;

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


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Opt in to leaving the home helper while the screen is off.
 * {@code adb shell am broadcast -a com.darkgod.devtools.neo_tvos.GUARD -n com.darkgod.devtools.neo_tvos/.GuardConfig --ez guard_off_in_standby true}
 */
public class GuardConfig extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.hasExtra("guard_off_in_standby")) {
            GuardKeep.setOffInStandby(context, intent.getBooleanExtra("guard_off_in_standby", false));
            GuardKeep.ensureHomeKey(context);
        }
        setResultData("guard_off_in_standby=" + GuardKeep.offInStandby(context));
    }
}
