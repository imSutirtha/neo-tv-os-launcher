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


import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

/** One-time, local-only owner name setup. Weather consent is handled separately in launcher settings. */
public final class SetupActivity extends Activity {
    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        LinearLayout page = new LinearLayout(this);
        page.setOrientation(LinearLayout.VERTICAL);
        page.setGravity(Gravity.CENTER_VERTICAL);
        page.setPadding(120, 64, 120, 64);
        page.setBackgroundColor(0xff101419);
        TextView title = new TextView(this);
        title.setText(getString(R.string.welcome_title, getString(R.string.app_name)));
        title.setTextColor(0xffffffff);
        title.setTextSize(34);
        page.addView(title);
        TextView hint = new TextView(this);
        hint.setText("What should your greeting call you? You can change this later in Launcher settings.");
        hint.setTextColor(0xffc4cbd1);
        hint.setTextSize(18);
        LinearLayout.LayoutParams gap = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        gap.topMargin = 22;
        page.addView(hint, gap);
        EditText name = new EditText(this);
        name.setSingleLine(true);
        name.setHint("Your name");
        name.setTextColor(0xffffffff);
        name.setHintTextColor(0xff9aa6b0);
        page.addView(name, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        Button done = new Button(this);
        done.setText("Continue");
        LinearLayout.LayoutParams button = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        button.topMargin = 28;
        page.addView(done, button);
        done.setOnClickListener(v -> {
            getSharedPreferences(Config.SETTINGS_NAME, MODE_PRIVATE).edit()
                    .putString("greeting_name", name.getText().toString().trim()).apply();
            finish();
        });
        setContentView(page);
        name.requestFocus();
    }
}
