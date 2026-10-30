package com.darkgod.devtools.neo_tvos.ui;

import com.darkgod.devtools.neo_tvos.R;
import com.darkgod.devtools.neo_tvos.config.*;
import com.darkgod.devtools.neo_tvos.data.app.OpenOnStart;
import com.darkgod.devtools.neo_tvos.data.weather.*;
import com.darkgod.devtools.neo_tvos.receiver.NeoAdminReceiver;
import com.darkgod.devtools.neo_tvos.theme.WallpaperRotation;
import com.darkgod.devtools.neo_tvos.util.*;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.admin.DevicePolicyManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Modern, remote-friendly settings owned entirely by Neo tvOS.
 * Contains zero TV system settings and zero reorder clutter.
 */
public final class LauncherSettingsActivity extends Activity {
    @Override
    protected void attachBaseContext(android.content.Context base) {
        super.attachBaseContext(LauncherLocale.wrap(base));   // locale
    }

    static final String EXTRA_REORDER = "com.darkgod.devtools.neo_tvos.extra.REORDER_FROM_SETTINGS";
    private SharedPreferences choices;
    private final TextView[] columnChoices = new TextView[4];
    private final TextView[] rowChoices = new TextView[4];
    private static final int[] DOCK_OPACITIES = {100, 70, 50, 30};
    private final TextView[] dockChoices = new TextView[DOCK_OPACITIES.length];
    private final TextView[] clockChoices = new TextView[3];
    private static final String[] CLOCK_FORMATS = {"auto", "12", "24"};

    private TextView valGreeting;
    private TextView valWeatherCity;
    private TextView valWeatherUnit;
    private TextView valDockGlass;
    private TextView valWallpaper;
    private TextView valAnimations;
    private TextView valAdvanced;
    private TextView valOpenOnStart;
    private TextView valFollowTheme;
    private TextView valDeviceAdmin;

    private View cardGreeting;
    private View cardWeatherCity;
    private View cardWeatherUnit;
    private View cardDockGlass;
    private View wallpaperChoice;
    private View animationsChoice;
    private View advancedChoice;
    private View openOnStartChoice;
    private View followThemeChoice;
    private View defaultHomeChoice;
    private View cardDeviceAdmin;

    private int columns;
    private int rowsBelowDock;
    private int dockOpacity;
    private String clockFormat = "auto";
    private boolean animations;
    private boolean advancedEffects;

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_launcher_settings);
        choices = getSharedPreferences(Config.SETTINGS_NAME, MODE_PRIVATE);
        Config config = Config.load(this);
        columns = config.columns;
        rowsBelowDock = config.rowsBelowDock;
        dockOpacity = config.dockOpacity;
        clockFormat = choices.getString("clock_format", "auto");
        animations = config.animations;
        advancedEffects = config.animations && config.advancedEffects;

        bindViews();
        setupChoices();
        setupClickListeners(config);

        findViewById(R.id.scrim).setOnClickListener(v -> finish());
        View btnClose = findViewById(R.id.btnClose);
        if (btnClose != null) btnClose.setOnClickListener(v -> finish());

        if (getResources().getBoolean(R.bool.rtl_menu_bar)) {
            View panel = findViewById(R.id.panel);
            if (panel != null) {
                android.widget.FrameLayout.LayoutParams position =
                        (android.widget.FrameLayout.LayoutParams) panel.getLayoutParams();
                position.gravity = android.view.Gravity.TOP | android.view.Gravity.START;
                panel.setLayoutParams(position);
            }
        }
        if (getResources().getBoolean(R.bool.rtl_app_grid)) {
            reverseRow(columnChoices);
            reverseRow(rowChoices);
            reverseRow(dockChoices);
            reverseRow(clockChoices);
        }

        refresh();
        clockChoices[0].requestFocus();
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    private void bindViews() {
        valGreeting = findViewById(R.id.valGreeting);
        valWeatherCity = findViewById(R.id.valWeatherCity);
        valWeatherUnit = findViewById(R.id.valWeatherUnit);
        valDockGlass = findViewById(R.id.valDockGlass);
        valWallpaper = findViewById(R.id.valWallpaper);
        valAnimations = findViewById(R.id.valAnimations);
        valAdvanced = findViewById(R.id.valAdvanced);
        valOpenOnStart = findViewById(R.id.valOpenOnStart);
        valFollowTheme = findViewById(R.id.valFollowTheme);
        valDeviceAdmin = findViewById(R.id.valDeviceAdmin);

        cardGreeting = findViewById(R.id.cardGreeting);
        cardWeatherCity = findViewById(R.id.cardWeatherCity);
        cardWeatherUnit = findViewById(R.id.cardWeatherUnit);
        cardDockGlass = findViewById(R.id.cardDockGlass);
        wallpaperChoice = findViewById(R.id.wallpaperChoice);
        animationsChoice = findViewById(R.id.animationsChoice);
        advancedChoice = findViewById(R.id.advancedChoice);
        openOnStartChoice = findViewById(R.id.openOnStartChoice);
        followThemeChoice = findViewById(R.id.followThemeChoice);
        defaultHomeChoice = findViewById(R.id.defaultHomeChoice);
        cardDeviceAdmin = findViewById(R.id.cardDeviceAdmin);
    }

    private void setupChoices() {
        int[] ids = {R.id.columns4, R.id.columns5, R.id.columns6, R.id.columns7};
        for (int i = 0; i < ids.length; i++) {
            final int value = i + 4;
            columnChoices[i] = findViewById(ids[i]);
            columnChoices[i].setOnClickListener(v -> {
                if (columns == value) return;
                columns = value;
                choices.edit().putInt("columns", value).apply();
                changed();
                refresh();
            });
        }
        int[] rowIds = {R.id.rows0, R.id.rows1, R.id.rows2, R.id.rows3};
        for (int i = 0; i < rowIds.length; i++) {
            final int value = i;
            rowChoices[i] = findViewById(rowIds[i]);
            rowChoices[i].setOnClickListener(v -> {
                if (rowsBelowDock == value) return;
                rowsBelowDock = value;
                choices.edit().putInt(Config.ROWS_BELOW_DOCK_KEY, value).apply();
                changed();
                refresh();
            });
        }
        int[] dockIds = {R.id.dock100, R.id.dock70, R.id.dock50, R.id.dock30};
        for (int i = 0; i < dockIds.length; i++) {
            final int value = DOCK_OPACITIES[i];
            dockChoices[i] = findViewById(dockIds[i]);
            dockChoices[i].setOnClickListener(v -> {
                if (dockOpacity == value) return;
                dockOpacity = value;
                choices.edit().putInt(Config.DOCK_OPACITY_KEY, value).apply();
                changed();
                refresh();
            });
        }
        int[] clockIds = {R.id.clockAuto, R.id.clock12, R.id.clock24};
        for (int i = 0; i < clockIds.length; i++) {
            final String format = CLOCK_FORMATS[i];
            clockChoices[i] = findViewById(clockIds[i]);
            clockChoices[i].setOnClickListener(v -> {
                if (format.equals(clockFormat)) return;
                clockFormat = format;
                choices.edit().putString("clock_format", format).apply();
                changed();
                refresh();
            });
        }
    }

    private void setupClickListeners(Config config) {
        cardGreeting.setOnClickListener(v -> showGreetingDialog());
        cardWeatherCity.setOnClickListener(v -> showWeatherCityDialog());
        cardWeatherUnit.setOnClickListener(v -> toggleWeatherUnit());
        cardDockGlass.setOnClickListener(v -> toggleDockGlass());

        wallpaperChoice.setOnClickListener(v -> showWallpaperPicker());
        animationsChoice.setOnClickListener(v -> {
            animations = !animations;
            if (!animations) advancedEffects = false;
            choices.edit().putBoolean("animations", animations)
                    .putBoolean("advanced_effects", advancedEffects).apply();
            changed();
            refresh();
        });
        advancedChoice.setOnClickListener(v -> {
            if (advancedEffects) {
                setAdvanced(false);
            } else {
                new AlertDialog.Builder(this)
                        .setTitle(R.string.advanced_effects)
                        .setMessage(R.string.advanced_effects_warning)
                        .setPositiveButton(R.string.enable, (dialog, which) -> setAdvanced(true))
                        .setNegativeButton(android.R.string.cancel, null)
                        .show();
            }
        });

        if (config.openOnStartChoice) {
            openOnStartChoice.setVisibility(View.VISIBLE);
            openOnStartChoice.setOnClickListener(v -> showOpenOnStartPicker());
        } else {
            openOnStartChoice.setVisibility(View.GONE);
        }

        followThemeChoice.setOnClickListener(v -> {
            if (!hasOnTvChoices()) return;
            choices.edit().remove("columns").remove(Config.ROWS_BELOW_DOCK_KEY).remove("animations")
                    .remove("advanced_effects").remove(Config.WALLPAPER_CHOICE_KEY)
                    .remove(Config.DOCK_OPACITY_KEY).remove(OpenOnStart.KEY)
                    .remove("clock_format").remove("dock_glass")
                    .remove("greeting_name").remove("weather_city").remove("weather_unit").commit();
            Config current = Config.load(this);
            columns = current.columns;
            rowsBelowDock = current.rowsBelowDock;
            dockOpacity = current.dockOpacity;
            clockFormat = choices.getString("clock_format", "auto");
            animations = current.animations;
            advancedEffects = current.animations && current.advancedEffects;
            changed();
            refresh();
            Toast.makeText(this, "Reverted to theme defaults", Toast.LENGTH_SHORT).show();
        });

        defaultHomeChoice.setOnClickListener(v -> {
            Toast.makeText(this, R.string.home_settings_help, Toast.LENGTH_LONG).show();
            open(new Intent(Settings.ACTION_HOME_SETTINGS));
        });

        cardDeviceAdmin.setOnClickListener(v -> showDeviceAdminInfo());
    }

    private void showGreetingDialog() {
        LinearLayout form = new LinearLayout(this);
        form.setOrientation(LinearLayout.VERTICAL);
        form.setPadding(48, 24, 48, 12);
        EditText name = new EditText(this);
        name.setHint("Enter greeting name (e.g. Lux)");
        name.setText(choices.getString("greeting_name", ""));
        form.addView(name);
        new AlertDialog.Builder(this)
                .setTitle("Owner Greeting")
                .setView(form)
                .setPositiveButton("Save", (d, w) -> {
                    choices.edit().putString("greeting_name", name.getText().toString().trim()).apply();
                    changed();
                    refresh();
                })
                .setNegativeButton(android.R.string.cancel, null)
                .show();
    }

    private void showWeatherCityDialog() {
        LinearLayout form = new LinearLayout(this);
        form.setOrientation(LinearLayout.VERTICAL);
        form.setPadding(48, 24, 48, 12);
        EditText city = new EditText(this);
        city.setHint("City name (leave empty for automatic)");
        city.setText(choices.getString("weather_city", ""));
        form.addView(city);
        new AlertDialog.Builder(this)
                .setTitle("Weather Location")
                .setView(form)
                .setPositiveButton("Save", (d, w) -> {
                    String chosen = city.getText().toString().trim();
                    choices.edit().putString("weather_city", chosen).apply();
                    changed();
                    refresh();
                    if (!chosen.isEmpty()) {
                        WeatherClient.refresh(chosen, WeatherStore.unit(this), (val, err) -> {
                            if (val != null) WeatherStore.save(this, val);
                        });
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .show();
    }

    private void toggleWeatherUnit() {
        String current = WeatherStore.unit(this);
        String next = "fahrenheit".equals(current) ? "celsius" : "fahrenheit";
        choices.edit().putString("weather_unit", next).apply();
        changed();
        refresh();
        WeatherSnapshot snap = WeatherStore.read(this);
        if (snap != null) {
            WeatherClient.refresh(snap.city, next, (val, err) -> {
                if (val != null) WeatherStore.save(this, val);
            });
        }
    }

    private void toggleDockGlass() {
        boolean glass = choices.getBoolean("dock_glass", true);
        choices.edit().putBoolean("dock_glass", !glass).apply();
        changed();
        refresh();
    }

    private void showDeviceAdminInfo() {
        DevicePolicyManager dpm = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        ComponentName admin = new ComponentName(this, NeoAdminReceiver.class);
        boolean active = dpm != null && dpm.isAdminActive(admin);

        new AlertDialog.Builder(this)
                .setTitle("Anti-Uninstall Protection")
                .setMessage(active
                        ? "Neo tvOS is actively locked as a Device Administrator. The system 'Uninstall' button is disabled in TV Settings to protect against accidental deletion."
                        : "Device Administrator is currently inactive. Run the ADB command provided in the documentation to lock the launcher against uninstallation.")
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }

    private void reverseRow(TextView[] views) {
        LinearLayout row = (LinearLayout) views[0].getParent();
        row.removeAllViews();
        for (int i = views.length - 1; i >= 0; i--) row.addView(views[i]);
    }

    private void showWallpaperPicker() {
        File dir = Config.dir(this);
        List<File> images = new java.util.ArrayList<>(WallpaperRotation.images(
                dir == null ? null : new File(dir, "wallpapers")));
        File single = dir == null ? null : new File(dir, Config.WALLPAPER_NAME);
        if (single != null && single.isFile()) images.add(0, single);
        String[] labels = new String[images.size() + 1];
        for (int i = 0; i < images.size(); i++) labels[i] = images.get(i).getName();
        labels[images.size()] = getString(R.string.wallpaper_none);
        String current = choices.getString(Config.WALLPAPER_CHOICE_KEY, null);
        int checked = "none".equals(current) ? images.size() : indexOf(images, current);
        new AlertDialog.Builder(this)
                .setTitle(R.string.wallpaper_label)
                .setSingleChoiceItems(labels, checked, (dialog, which) -> {
                    dialog.dismiss();
                    if (which == images.size()) {
                        choices.edit().putString(Config.WALLPAPER_CHOICE_KEY, "none").apply();
                    } else {
                        choices.edit().putString(Config.WALLPAPER_CHOICE_KEY,
                                images.get(which).getName()).apply();
                    }
                    changed();
                    Toast.makeText(this, R.string.wallpaper_applied, Toast.LENGTH_SHORT).show();
                    refresh();
                })
                .setNeutralButton(R.string.wallpaper_theme_default, (dialog, which) -> {
                    choices.edit().remove(Config.WALLPAPER_CHOICE_KEY).apply();
                    changed();
                    refresh();
                })
                .setNegativeButton(android.R.string.cancel, null)
                .show();
    }

    private void showOpenOnStartPicker() {
        PackageManager pm = getPackageManager();
        List<String[]> apps = new ArrayList<>();
        Intent tv = new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_LEANBACK_LAUNCHER);
        for (ResolveInfo r : pm.queryIntentActivities(tv, 0)) {
            String pkg = r.activityInfo.packageName;
            if (pkg.equals(getPackageName())) continue;
            boolean seen = false;
            for (String[] a : apps) seen |= a[1].equals(pkg);
            if (!seen) apps.add(new String[]{r.loadLabel(pm).toString(), pkg});
        }
        Collections.sort(apps, (a, b) -> a[0].compareToIgnoreCase(b[0]));
        String[] labels = new String[apps.size() + 1];
        labels[0] = getString(R.string.off);
        String current = choices.getString(OpenOnStart.KEY, "");
        int checked = 0;
        for (int i = 0; i < apps.size(); i++) {
            labels[i + 1] = apps.get(i)[0];
            if (apps.get(i)[1].equals(current)) checked = i + 1;
        }
        new AlertDialog.Builder(this)
                .setTitle(R.string.open_on_start_label)
                .setSingleChoiceItems(labels, checked, (dialog, which) -> {
                    dialog.dismiss();
                    if (which == 0) choices.edit().remove(OpenOnStart.KEY).apply();
                    else {
                        OpenOnStart.skipThisBoot(this);
                        choices.edit().putString(OpenOnStart.KEY, apps.get(which - 1)[1]).apply();
                    }
                    refresh();
                })
                .setNegativeButton(android.R.string.cancel, null)
                .show();
    }

    private String openOnStartLabel() {
        String pkg = choices.getString(OpenOnStart.KEY, "");
        if (pkg.isEmpty()) return getString(R.string.off);
        try {
            PackageManager pm = getPackageManager();
            return pm.getApplicationLabel(pm.getApplicationInfo(pkg, 0)).toString();
        } catch (PackageManager.NameNotFoundException e) {
            return pkg;
        }
    }

    private static int indexOf(List<File> images, String name) {
        if (name == null) return -1;
        for (int i = 0; i < images.size(); i++) {
            if (images.get(i).getName().equals(name)) return i;
        }
        return -1;
    }

    private void changed() {
        setResult(RESULT_OK);
        choices.edit().putLong(Config.SETTINGS_REVISION_KEY,
                choices.getLong(Config.SETTINGS_REVISION_KEY, 0) + 1).apply();
    }

    private void setAdvanced(boolean enabled) {
        advancedEffects = enabled;
        choices.edit().putBoolean("advanced_effects", enabled).apply();
        changed();
        refresh();
    }

    private void refresh() {
        String greeting = choices.getString("greeting_name", "");
        valGreeting.setText(greeting.isEmpty() ? "Default (Lux)" : greeting);

        String city = choices.getString("weather_city", "");
        valWeatherCity.setText(city.isEmpty() ? "Auto" : city);

        boolean fahr = "fahrenheit".equals(WeatherStore.unit(this));
        valWeatherUnit.setText(fahr ? "Fahrenheit (°F)" : "Celsius (°C)");

        for (int i = 0; i < columnChoices.length; i++) {
            columnChoices[i].setSelected(columns == i + 4);
        }
        for (int i = 0; i < rowChoices.length; i++) {
            rowChoices[i].setSelected(rowsBelowDock == i);
        }
        for (int i = 0; i < dockChoices.length; i++) {
            dockChoices[i].setSelected(dockOpacity == DOCK_OPACITIES[i]);
        }
        for (int i = 0; i < clockChoices.length; i++) {
            clockChoices[i].setSelected(CLOCK_FORMATS[i].equals(clockFormat));
        }

        boolean glass = choices.getBoolean("dock_glass", true);
        valDockGlass.setText(glass ? "Enabled" : "Disabled");

        String wallpaperCurrent = choices.getString(Config.WALLPAPER_CHOICE_KEY, null);
        valWallpaper.setText("none".equals(wallpaperCurrent) ? getString(R.string.wallpaper_none)
                : wallpaperCurrent != null ? wallpaperCurrent
                : getString(R.string.wallpaper_theme_default));

        valAnimations.setText(animations ? "Enabled" : "Disabled");
        valAdvanced.setText(advancedEffects ? "Enabled" : "Disabled");
        advancedChoice.setEnabled(animations);
        advancedChoice.setAlpha(animations ? 1f : 0.5f);

        valOpenOnStart.setText(openOnStartLabel());
        valFollowTheme.setText(!hasOnTvChoices() ? "Synced" : "Customized");

        DevicePolicyManager dpm = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        boolean adminActive = dpm != null && dpm.isAdminActive(new ComponentName(this, NeoAdminReceiver.class));
        valDeviceAdmin.setText(adminActive ? "Active" : "Inactive");
    }

    private boolean hasOnTvChoices() {
        return choices.contains("columns") || choices.contains(Config.ROWS_BELOW_DOCK_KEY)
                || choices.contains("animations") || choices.contains("advanced_effects")
                || choices.contains(Config.WALLPAPER_CHOICE_KEY) || choices.contains(Config.DOCK_OPACITY_KEY)
                || choices.contains(OpenOnStart.KEY) || choices.contains("clock_format")
                || choices.contains("dock_glass") || choices.contains("greeting_name")
                || choices.contains("weather_city") || choices.contains("weather_unit");
    }

    private void open(Intent intent) {
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException | SecurityException e) {
            Toast.makeText(this, R.string.not_available, Toast.LENGTH_LONG).show();
        }
    }
}
