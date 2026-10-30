package com.darkgod.devtools.neo_tvos.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * QuickSettingsActivity forwards to LauncherSettingsActivity for a unified TV settings experience.
 */
public final class QuickSettingsActivity extends Activity {
    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        Intent open = new Intent(this, LauncherSettingsActivity.class);
        if (getIntent() != null && getIntent().getExtras() != null) {
            open.putExtras(getIntent().getExtras());
        }
        startActivity(open);
        finish();
        overridePendingTransition(0, 0);
    }
}
