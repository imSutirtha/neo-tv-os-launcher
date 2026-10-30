package com.darkgod.devtools.neo_tvos.receiver;

import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import com.darkgod.devtools.neo_tvos.R;

/**
 * Protects Neo tvOS against uninstallation by locking package deletion.
 */
public class NeoAdminReceiver extends DeviceAdminReceiver {
    @Override
    public void onEnabled(@NonNull Context context, @NonNull Intent intent) {
        super.onEnabled(context, intent);
        lockUninstall(context);
    }

    @Override
    public CharSequence onDisableRequested(@NonNull Context context, @NonNull Intent intent) {
        return context.getString(R.string.admin_warning);
    }

    public static void lockUninstall(Context context) {
        DevicePolicyManager dpm = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        ComponentName admin = new ComponentName(context, NeoAdminReceiver.class);
        if (dpm != null && dpm.isDeviceOwnerApp(context.getPackageName())) {
            try {
                dpm.setUninstallBlocked(admin, context.getPackageName(), true);
            } catch (Exception ignored) {
            }
        }
    }
}
