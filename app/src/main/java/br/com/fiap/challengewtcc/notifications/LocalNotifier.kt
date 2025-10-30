package br.com.fiap.challengewtcc.notifications

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import br.com.fiap.challengewtcc.R

object LocalNotifier {
    private const val CH_ID = "wtc_push"
    private const val CH_NAME = "Notificações"

    private fun ensureChannel(ctx: Context) {
        if (Build.VERSION.SDK_INT >= 26) {
            val chan = NotificationChannel(CH_ID, CH_NAME, NotificationManager.IMPORTANCE_DEFAULT).apply {
                description = "Eventos e campanhas"
                enableLights(true)
                lightColor = Color.MAGENTA
            }
            val nm = ctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            nm.createNotificationChannel(chan)
        }
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    fun notify(ctx: Context, title: String, body: String, id: Int = (System.currentTimeMillis() % Int.MAX_VALUE).toInt()) {
        ensureChannel(ctx)
        val n = NotificationCompat.Builder(ctx, CH_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(body)
            .setStyle(NotificationCompat.BigTextStyle().bigText(body))
            .setAutoCancel(true)
            .build()
        NotificationManagerCompat.from(ctx).notify(id, n)
    }
}
