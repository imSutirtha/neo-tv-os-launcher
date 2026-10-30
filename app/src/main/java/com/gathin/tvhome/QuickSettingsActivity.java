package com.gathin.tvhome;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * The panel the remote's Settings button opens when {@code settings_key_panel} is on (HomeKeyService):
 * a few glass rows under the Settings pill, over Home or whatever app is playing. Translucent and in a
 * task of its own, so the app behind keeps running and Back returns to it. Pressing Settings again
 * closes the panel.
 */
public final class QuickSettingsActivity extends Activity {
    @Override
    protected void attachBaseContext(android.content.Context base) {
        super.attachBaseContext(LauncherLocale.wrap(base));   // locale
    }


    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_quick_settings);
        if (getResources().getBoolean(R.bool.rtl_menu_bar)) {
            View panel = findViewById(R.id.panel);
            android.widget.FrameLayout.LayoutParams position =
                    (android.widget.FrameLayout.LayoutParams) panel.getLayoutParams();
            position.gravity = android.view.Gravity.TOP | android.view.Gravity.LEFT;
            int inset = position.getMarginEnd();
            position.setMarginEnd(0);
            position.setMargins(inset, position.topMargin, 0, position.bottomMargin);
            panel.setLayoutParams(position);
        }
        findViewById(R.id.scrim).setOnClickListener(v -> finish());
        LinearLayout rows = findViewById(R.id.rows);
        addRow(rows, R.drawable.ic_settings, R.string.tv_settings, 0, SystemSettings.intent(this, Settings.ACTION_SETTINGS));
        addRow(rows, R.drawable.ic_picture, R.string.quick_picture, 0, SystemSettings.intent(this, Settings.ACTION_DISPLAY_SETTINGS));
        addRow(rows, R.drawable.ic_wifi, R.string.quick_network, 0, SystemSettings.intent(this, Settings.ACTION_WIFI_SETTINGS));
        addRow(rows, R.drawable.ic_apps, R.string.quick_apps, R.string.quick_apps_hint,
                SystemSettings.intent(this, Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS));
        addAction(rows, "Personalize", this::showPersonalize);
        addAction(rows, "Weather units: " + ("fahrenheit".equals(WeatherStore.unit(this)) ? "°F" : "°C"), this::toggleWeatherUnits);
        boolean glass = getSharedPreferences(Config.SETTINGS_NAME, MODE_PRIVATE).getBoolean("dock_glass", true);
        addAction(rows, "Dock glass: " + (glass ? "On" : "Off"), this::toggleDockGlass);
        addRow(rows, R.drawable.ic_swap_vert, R.string.reorder, 0, new Intent(this, MainActivity.class).setAction(MainActivity.ACTION_REORDER_DOCK));
        rows.getChildAt(0).requestFocus();
    }

    private void showPersonalize() {
        LinearLayout form = new LinearLayout(this); form.setOrientation(LinearLayout.VERTICAL); form.setPadding(48, 24, 48, 12);
        EditText name = new EditText(this); name.setHint("Greeting name");
        EditText city = new EditText(this); city.setHint("Weather city (optional)");
        android.content.SharedPreferences p = getSharedPreferences(Config.SETTINGS_NAME, MODE_PRIVATE);
        name.setText(p.getString("greeting_name", "")); city.setText(p.getString("weather_city", ""));
        form.addView(name); form.addView(city);
        new AlertDialog.Builder(this).setTitle("Personalize Home").setView(form)
                .setPositiveButton("Save", (d, w) -> {
                    String chosenCity = city.getText().toString().trim();
                    p.edit().putString("greeting_name", name.getText().toString().trim()).putString("weather_city", chosenCity).putLong(Config.SETTINGS_REVISION_KEY, p.getLong(Config.SETTINGS_REVISION_KEY, 0) + 1).apply();
                    if (!chosenCity.isEmpty()) WeatherClient.refresh(chosenCity, WeatherStore.unit(this), (value, error) -> {
                        if (value != null) WeatherStore.save(this, value);
                    });
                    finish();
                })
                .setNegativeButton("Cancel", null).show();
    }
    private void toggleWeatherUnits() {
        android.content.SharedPreferences p = getSharedPreferences(Config.SETTINGS_NAME, MODE_PRIVATE);
        String next = "fahrenheit".equals(WeatherStore.unit(this)) ? "celsius" : "fahrenheit";
        p.edit().putString("weather_unit", next).putLong(Config.SETTINGS_REVISION_KEY, p.getLong(Config.SETTINGS_REVISION_KEY, 0) + 1).apply();
        WeatherSnapshot current = WeatherStore.read(this);
        if (current != null) WeatherClient.refresh(current.city, next, (fresh, error) -> { if (fresh != null) WeatherStore.save(this, fresh); });
        finish();
    }
    private void toggleDockGlass() {
        android.content.SharedPreferences p = getSharedPreferences(Config.SETTINGS_NAME, MODE_PRIVATE);
        p.edit().putBoolean("dock_glass", !p.getBoolean("dock_glass", true))
                .putLong(Config.SETTINGS_REVISION_KEY, p.getLong(Config.SETTINGS_REVISION_KEY, 0) + 1).apply();
        finish();
    }
    private void addAction(LinearLayout rows, String title, Runnable action) {
        View row = getLayoutInflater().inflate(R.layout.item_input, rows, false);
        ((ImageView) row.findViewById(R.id.icon)).setImageResource(R.drawable.ic_settings);
        ((TextView) row.findViewById(R.id.title)).setText(title); row.findViewById(R.id.dot).setVisibility(View.GONE); row.findViewById(R.id.status).setVisibility(View.GONE);
        row.setOnClickListener(v -> action.run()); rows.addView(row);
    }

    /** Settings pressed again while the panel is up. */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        finish();
    }

    /** Anything else taking the screen (Home, an app, a screensaver) closes the panel. */
    @Override
    protected void onPause() {
        super.onPause();
        finish();
        overridePendingTransition(0, 0);
    }

    private void addRow(LinearLayout rows, int icon, int title, int status, Intent target) {
        View row = getLayoutInflater().inflate(R.layout.item_input, rows, false);
        ((ImageView) row.findViewById(R.id.icon)).setImageResource(icon);
        ((TextView) row.findViewById(R.id.title)).setText(title);
        row.findViewById(R.id.dot).setVisibility(View.GONE);
        TextView hint = row.findViewById(R.id.status);
        if (status != 0) hint.setText(status);
        else hint.setVisibility(View.GONE);
        row.setOnClickListener(v -> {
            try {
                startActivity(target.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            } catch (ActivityNotFoundException | SecurityException e) {
                Toast.makeText(this, R.string.not_available, Toast.LENGTH_SHORT).show();
            }
            finish();
        });
        rows.addView(row);
    }
}
