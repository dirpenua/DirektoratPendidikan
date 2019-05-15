package com.example.direktoratpendidikan;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.example.direktoratpendidikan.data.Notif;
import com.example.direktoratpendidikan.dosen.MainActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import static com.android.volley.VolleyLog.TAG;

public class MyFirebaseMessagingService extends FirebaseMessagingService {


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            Map<String, String> data = remoteMessage.getData();
            String title= data.get("title");
            String body= data.get("body");

            displayNotification(getApplicationContext(), title, body);

//            if (/* Check if data needs to be processed by long running job */ true) {
//                // For long-running tasks (10 seconds or more) use WorkManager.
//                scheduleJob();
//            } else {
//                // Handle message within 10 seconds
//                handleNow();
//            }

        }

        // Check if message contains a notification payload.

        if (remoteMessage.getNotification() != null) {
            String title = remoteMessage.getNotification().getTitle();
            String body = remoteMessage.getNotification().getBody();

            displayNotification(getApplicationContext(), title, body);
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

    public static void displayNotification(Context context, String title, String body) {

        Intent intent = new Intent(context, NotifikasiActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(
                context,
                100,
                intent,
                PendingIntent.FLAG_CANCEL_CURRENT
        );

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context, MainActivity.CHANNEL_ID)
                        .setSmallIcon(R.mipmap.ic_launcher_round)
                        .setContentTitle(title)
                        .setContentText(body)
                        .setContentIntent(pendingIntent)
                        .setDefaults(NotificationCompat.DEFAULT_ALL)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setAutoCancel(true);

        NotificationManagerCompat mNotificationMgr = NotificationManagerCompat.from(context);
        mNotificationMgr.notify(1, mBuilder.build());

    }
}