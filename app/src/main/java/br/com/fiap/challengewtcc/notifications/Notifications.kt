package br.com.fiap.challengewtcc.notifications

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.content.Context
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat

fun canPostNotifications(ctx: Context): Boolean {
    return if (Build.VERSION.SDK_INT >= 33) {
        ContextCompat.checkSelfPermission(
            ctx,
            Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED
    } else {
        NotificationManagerCompat.from(ctx).areNotificationsEnabled()
    }
}
