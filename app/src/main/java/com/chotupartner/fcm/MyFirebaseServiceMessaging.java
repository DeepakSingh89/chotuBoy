package com.chotupartner.fcm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.chotupartner.R;
import com.chotupartner.activity.SplashActivity;
import com.chotupartner.utils.ChotuBoyPrefs;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.IOException;

public class MyFirebaseServiceMessaging extends FirebaseMessagingService {

    private static final String TAG = MyFirebaseServiceMessaging.class.getName();

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        MediaPlayer media = MediaPlayer.create(this, R.raw.ordermp3);
        try {
            media.prepare();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        media.start();

        Intent filter = new Intent("custom.notification.navigation");
        sendBroadcast(filter);

        if (remoteMessage.getData().size() > 0) {
            sendNotification(remoteMessage.getData().get("message"));


        }
    }


    @RequiresApi(api = Build.VERSION_CODES.Q)
    private void sendNotification(String message) {

        // NotificationPushData data = BaseUtil.objectFromString(messageBody, NotificationPushData.class);


        Intent intent = new Intent(getApplicationContext(), SplashActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        //intent.putExtra(Constants.PUSH_NEW_BOOKING_TRIP_DATA_KEY, data);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);


        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "PUSH");
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        inboxStyle.addLine(message);

        long when = System.currentTimeMillis();         // notification time


        // Sets an ID for the notification, so it can be updated.
        int notifyID = 1;
        String CHANNEL_ID = "my_channel_01";// The id of the channel.
        CharSequence name = "Channel human readable title";// The user-visible name of the channel.
        int importance = NotificationManager.IMPORTANCE_HIGH;


        Notification notification;
        notification = mBuilder.setSmallIcon(R.mipmap.ic_launcher).setTicker(getString(R.string.app_name)).setWhen(when)
//                .setAutoCancel(true)
                .setContentTitle("Order Update")
                .setContentIntent(pendingIntent)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setWhen(when)
                .setSmallIcon(getNotificationIcon(mBuilder))
                .setContentText(message)
                .setChannelId(CHANNEL_ID)
                .setDefaults(Notification.DEFAULT_VIBRATE
                        | Notification.DEFAULT_LIGHTS
                )
                .build();

        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {


            android.app.NotificationChannel mChannel = new android.app.NotificationChannel(CHANNEL_ID, name, importance);
            // Create a notification and set the notification channel.
            notificationManager.createNotificationChannel(mChannel);
        }

        notificationManager.notify(notifyID, notification);
    }

    private int getNotificationIcon(NotificationCompat.Builder notificationBuilder) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            notificationBuilder.setColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
            return R.drawable.app_logo;
        } else {
            return R.mipmap.ic_launcher;
        }
    }


    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        ChotuBoyPrefs.putString(this, "fcm", s);
    }
}

