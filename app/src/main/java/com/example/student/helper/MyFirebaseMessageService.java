package com.example.student.helper;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.student.R;
import com.example.student.activities.SppDetailActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessageService extends FirebaseMessagingService {

    private static final String CHANNEL_ID = "SiswaNotifChannel";

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        // Mendapatkan data dari pesan yang diterima
        String title = message.getData().get("title");
        String body = message.getData().get("body");

        // Menampilkan notifikasi
        showNotification(title, body);
    }

    private void showNotification(String title, String body) {
        // Membuat request ID unik untuk setiap notifikasi
        int requestID = (int) System.currentTimeMillis();

        // Intent untuk membuka SppDetailActivity saat notifikasi diklik
        Intent intent = new Intent(this, SppDetailActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("title", title);
        intent.putExtra("body", body);

        // Membuat PendingIntent untuk membuka Activity
        PendingIntent pendingIntent = PendingIntent.getActivity(
                this,
                requestID,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        // Menentukan URI suara notifikasi
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        // Membuat Notification Channel untuk API 26+ (Oreo dan yang lebih baru)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notifManager = getSystemService(NotificationManager.class);
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Notifikasi Siswa",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("Channel untuk notifikasi siswa");
            notifManager.createNotificationChannel(channel);
        }

        // Membuat notifikasi menggunakan NotificationCompat.Builder
        NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(body)
                .setSound(soundUri)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        // Menampilkan notifikasi
        NotificationManager notifManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notifManager.notify(10, notifBuilder.build());
    }
}