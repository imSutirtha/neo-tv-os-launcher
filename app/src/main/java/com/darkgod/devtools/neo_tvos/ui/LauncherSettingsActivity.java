package com.darkgod.devtools.neo_tvos.ui;

import com.darkgod.devtools.neo_tvos.R;
import com.darkgod.devtools.neo_tvos.config.Config;
import com.darkgod.devtools.neo_tvos.data.weather.WeatherClient;
import com.darkgod.devtools.neo_tvos.data.weather.WeatherSnapshot;
import com.darkgod.devtools.neo_tvos.data.weather.WeatherStore;
import com.darkgod.devtools.neo_tvos.util.LauncherLocale;
import com.darkgod.devtools.neo_tvos.util.SystemSettings;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Floating glass Settings dropdown matching the reference Neo tvOS TV UI.
 * Root Menu contains:
 *   1. Reorder apps
 *   2. Launcher settings (No icon)
 *   3. TV system settings
 *
 * Launcher Settings submenu contains (no Back button):
 *   1. Greeting name update
 *   2. Location update
 *   3. Weather Unit (just after Location update)
 *   4. Time Format
 *   5. Dock Glass
 *   6. Animation
 */
public final class LauncherSettingsActivity extends Activity {

    public static final String EXTRA_REORDER = "com.darkgod.devtools.neo_tvos.extra.REORDER_FROM_SETTINGS";

    private SharedPreferences choices;
    private LinearLayout rows;
    private boolean inSubmenu = false;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LauncherLocale.wrap(base));
    }

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_launcher_settings);

        choices = getSharedPreferences(Config.SETTINGS_NAME, MODE_PRIVATE);

        // Position panel at top-left for RTL layouts if configured
        if (getResources().getBoolean(R.bool.rtl_menu_bar)) {
            View panel = findViewById(R.id.panel);
            FrameLayout.LayoutParams position = (FrameLayout.LayoutParams) panel.getLayoutParams();
            position.gravity = Gravity.TOP | Gravity.START;
            int inset = position.getMarginEnd();
            position.setMarginEnd(0);
            position.setMargins(inset, position.topMargin, 0, position.bottomMargin);
            panel.setLayoutParams(position);
        }

        findViewById(R.id.scrim).setOnClickListener(v -> finishAndDismiss());
        rows = findViewById(R.id.rows);

        showRootMenu();
    }

    /**
     * Root Settings:
     *   1. Reorder apps
     *   2. Launcher settings (No icon)
     *   3. TV system settings
     */
    private void showRootMenu() {
        inSubmenu = false;
        rows.removeAllViews();

        // 1. Reorder apps
        addRow(R.drawable.ic_swap_vert, getString(R.string.reorder), null, v -> {
            setResult(RESULT_OK, new Intent().putExtra(EXTRA_REORDER, true));
            finishAndDismiss();
        });

        // 2. Launcher settings (with left gear icon, and NO right side icon)
        addRow(R.drawable.ic_settings, getString(R.string.launcher_settings), null, v -> showLauncherSettingsMenu());

        // 3. TV system settings
        addRow(R.drawable.ic_picture, getString(R.string.tv_settings), null, v -> {
            try {
                Intent intent = SystemSettings.intent(this, Settings.ACTION_SETTINGS);
                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            } catch (ActivityNotFoundException | SecurityException e) {
                Toast.makeText(this, R.string.not_available, Toast.LENGTH_SHORT).show();
            }
            finishAndDismiss();
        });

        if (rows.getChildCount() > 0) {
            rows.getChildAt(0).requestFocus();
        }
    }

    /**
     * Launcher Settings Submenu (No Back button):
     *   1. Greeting name update
     *   2. Location update
     *   3. Weather Unit (just after Location update)
     *   4. Time Format
     *   5. Dock Glass
     *   6. Animation
     */
    private void showLauncherSettingsMenu() {
        inSubmenu = true;
        rows.removeAllViews();

        // 1. Greeting name update
        String greetingName = choices.getString("greeting_name", "");
        String greetingTitle = greetingName.isEmpty() ? "Greeting name" : "Greeting: " + greetingName;
        View greetingRow = addRow(R.drawable.ic_person, greetingTitle, null, null);
        greetingRow.setOnClickListener(v -> showGreetingDialog(greetingRow));

        // 2. Location update
        String locationCity = choices.getString("weather_city", "");
        String locationTitle = locationCity.isEmpty() ? "Location (City)" : "Location: " + locationCity;
        View locationRow = addRow(R.drawable.ic_weather, locationTitle, null, null);
        locationRow.setOnClickListener(v -> showLocationDialog(locationRow));

        // 3. Weather Unit (just after Location update)
        String weatherUnit = WeatherStore.unit(this);
        View weatherRow = addRow(R.drawable.ic_sparkle, "Weather unit: " + ("fahrenheit".equals(weatherUnit) ? "°F" : "°C"), null, null);
        weatherRow.setOnClickListener(v -> toggleWeatherUnits(weatherRow));

        // 4. Time Format
        String clockFmt = choices.getString("clock_format", "auto");
        View clockRow = addRow(R.drawable.ic_clock, "Time format: " + getClockFormatLabel(clockFmt), null, null);
        clockRow.setOnClickListener(v -> toggleClockFormat(clockRow));

        // 5. Dock Glass
        boolean glass = choices.getBoolean("dock_glass", true);
        View glassRow = addRow(R.drawable.ic_wallpaper, "Dock glass: " + (glass ? "On" : "Off"), null, null);
        glassRow.setOnClickListener(v -> toggleDockGlass(glassRow));

        // 6. Animation
        boolean animations = choices.getBoolean("animations", true);
        View animRow = addRow(R.drawable.ic_animation, "Animations: " + (animations ? "On" : "Off"), null, null);
        animRow.setOnClickListener(v -> toggleAnimations(animRow));

        if (rows.getChildCount() > 0) {
            rows.getChildAt(0).requestFocus();
        }
    }

    private View addRow(int iconRes, String titleText, String statusText, View.OnClickListener listener) {
        View row = getLayoutInflater().inflate(R.layout.item_input, rows, false);
        ImageView icon = row.findViewById(R.id.icon);
        TextView title = row.findViewById(R.id.title);
        TextView status = row.findViewById(R.id.status);
        View dot = row.findViewById(R.id.dot);

        if (iconRes != 0) {
            icon.setImageResource(iconRes);
            icon.setVisibility(View.VISIBLE);
        } else {
            icon.setVisibility(View.GONE);
            ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) title.getLayoutParams();
            mlp.setMarginStart(0);
            title.setLayoutParams(mlp);
        }

        title.setText(titleText);
        if (statusText != null && !statusText.isEmpty()) {
            status.setText(statusText);
            status.setVisibility(View.VISIBLE);
        } else {
            status.setVisibility(View.GONE);
        }
        dot.setVisibility(View.GONE);

        if (listener != null) {
            row.setOnClickListener(listener);
        }
        rows.addView(row);
        return row;
    }

    private void showGreetingDialog(View row) {
        LinearLayout container = new LinearLayout(this);
        container.setOrientation(LinearLayout.VERTICAL);
        container.setPadding(48, 24, 48, 12);

        EditText input = new EditText(this);
        input.setHint("Enter greeting name");
        String current = choices.getString("greeting_name", "");
        input.setText(current);
        if (!current.isEmpty()) {
            input.setSelection(current.length());
        }
        container.addView(input);

        new AlertDialog.Builder(this)
                .setTitle("Greeting Name")
                .setView(container)
                .setPositiveButton("Save", (dialog, which) -> {
                    String chosen = input.getText().toString().trim();
                    choices.edit()
                            .putString("greeting_name", chosen)
                            .putLong(Config.SETTINGS_REVISION_KEY, choices.getLong(Config.SETTINGS_REVISION_KEY, 0) + 1)
                            .apply();
                    TextView title = row.findViewById(R.id.title);
                    title.setText(chosen.isEmpty() ? "Greeting name" : "Greeting: " + chosen);
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void showLocationDialog(View row) {
        LinearLayout container = new LinearLayout(this);
        container.setOrientation(LinearLayout.VERTICAL);
        container.setPadding(48, 24, 48, 12);

        EditText input = new EditText(this);
        input.setHint("Enter city name");
        String current = choices.getString("weather_city", "");
        input.setText(current);
        if (!current.isEmpty()) {
            input.setSelection(current.length());
        }
        container.addView(input);

        new AlertDialog.Builder(this)
                .setTitle("Weather Location")
                .setView(container)
                .setPositiveButton("Save", (dialog, which) -> {
                    String chosen = input.getText().toString().trim();
                    choices.edit()
                            .putString("weather_city", chosen)
                            .putLong(Config.SETTINGS_REVISION_KEY, choices.getLong(Config.SETTINGS_REVISION_KEY, 0) + 1)
                            .apply();
                    TextView title = row.findViewById(R.id.title);
                    title.setText(chosen.isEmpty() ? "Location (City)" : "Location: " + chosen);

                    if (!chosen.isEmpty()) {
                        WeatherClient.refresh(chosen, WeatherStore.unit(this), (fresh, err) -> {
                            if (fresh != null) WeatherStore.save(this, fresh);
                        });
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private String getClockFormatLabel(String fmt) {
        if ("12".equals(fmt)) return "12-hour";
        if ("24".equals(fmt)) return "24-hour";
        return "Auto";
    }

    private void toggleClockFormat(View row) {
        String current = choices.getString("clock_format", "auto");
        String next;
        if ("auto".equals(current)) {
            next = "12";
        } else if ("12".equals(current)) {
            next = "24";
        } else {
            next = "auto";
        }

        choices.edit()
                .putString("clock_format", next)
                .putLong(Config.SETTINGS_REVISION_KEY, choices.getLong(Config.SETTINGS_REVISION_KEY, 0) + 1)
                .apply();

        TextView title = row.findViewById(R.id.title);
        title.setText("Time format: " + getClockFormatLabel(next));
        Toast.makeText(this, "Time format: " + getClockFormatLabel(next), Toast.LENGTH_SHORT).show();
    }

    private void toggleWeatherUnits(View row) {
        String current = WeatherStore.unit(this);
        String next = "fahrenheit".equals(current) ? "celsius" : "fahrenheit";

        choices.edit()
                .putString("weather_unit", next)
                .putLong(Config.SETTINGS_REVISION_KEY, choices.getLong(Config.SETTINGS_REVISION_KEY, 0) + 1)
                .apply();

        WeatherSnapshot snapshot = WeatherStore.read(this);
        if (snapshot != null) {
            WeatherClient.refresh(snapshot.city, next, (fresh, error) -> {
                if (fresh != null) WeatherStore.save(this, fresh);
            });
        }

        TextView title = row.findViewById(R.id.title);
        title.setText("Weather unit: " + ("fahrenheit".equals(next) ? "°F" : "°C"));
    }

    private void toggleDockGlass(View row) {
        boolean current = choices.getBoolean("dock_glass", true);
        boolean next = !current;

        choices.edit()
                .putBoolean("dock_glass", next)
                .putLong(Config.SETTINGS_REVISION_KEY, choices.getLong(Config.SETTINGS_REVISION_KEY, 0) + 1)
                .apply();

        TextView title = row.findViewById(R.id.title);
        title.setText("Dock glass: " + (next ? "On" : "Off"));
    }

    private void toggleAnimations(View row) {
        boolean current = choices.getBoolean("animations", true);
        boolean next = !current;

        choices.edit()
                .putBoolean("animations", next)
                .putLong(Config.SETTINGS_REVISION_KEY, choices.getLong(Config.SETTINGS_REVISION_KEY, 0) + 1)
                .apply();

        TextView title = row.findViewById(R.id.title);
        title.setText("Animations: " + (next ? "On" : "Off"));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (inSubmenu) {
                showRootMenu();
                return true;
            }
            finishAndDismiss();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        finishAndDismiss();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
        overridePendingTransition(0, 0);
    }

    private void finishAndDismiss() {
        finish();
        overridePendingTransition(0, 0);
    }
}
