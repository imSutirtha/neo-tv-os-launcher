package com.darkgod.devtools.neo_tvos.receiver;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import com.darkgod.devtools.neo_tvos.R;

/**
 * Protects Neo tvOS against accidental uninstallation by the user from Settings.
 */
public class NeoAdminReceiver extends DeviceAdminReceiver {
    @Override
    public CharSequence onDisableRequested(@NonNull Context context, @NonNull Intent intent) {
        return context.getString(R.string.admin_warning);
    }
}
