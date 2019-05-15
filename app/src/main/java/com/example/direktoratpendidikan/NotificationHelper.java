package com.example.direktoratpendidikan;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.example.direktoratpendidikan.dosen.MainActivity;
import com.google.firebase.messaging.RemoteMessage;

import static android.app.Notification.DEFAULT_LIGHTS;
import static android.app.Notification.DEFAULT_SOUND;
import static android.app.Notification.DEFAULT_VIBRATE;

public class NotificationHelper {

//    public static void displayNotification(Context context, String title, String body) {
//
//        Intent intent = new Intent(context, AgendaFragment.class);
//
//        PendingIntent pendingIntent = PendingIntent.getActivity(
//                context,
//                100,
//                intent,
//                PendingIntent.FLAG_CANCEL_CURRENT
//        );
//
//        NotificationCompat.Builder mBuilder =
//                new NotificationCompat.Builder(context, MainActivity.CHANNEL_ID)
//                        .setSmallIcon(R.mipmap.ic_launcher_round)
//                        .setContentTitle(title)
//                        .setContentText(body)
//                        .setContentIntent(pendingIntent)
//                        .setDefaults(NotificationCompat.DEFAULT_ALL)
//                        .setPriority(NotificationCompat.PRIORITY_HIGH)
//                        .setAutoCancel(true);
//
//        NotificationManagerCompat mNotificationMgr = NotificationManagerCompat.from(context);
//        mNotificationMgr.notify(1, mBuilder.build());
//
//    }

}
