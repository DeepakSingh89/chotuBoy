package com.chotupartner.commonBase.fire_base_service;

/**
 * Created by santhosh@appoets.com on 21-05-2018.
 */

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.chotupartner.BuildConfig;
import com.chotupartner.R;
import com.chotupartner.activity.DashBoardActivity;
import com.chotupartner.utils.ChotuBoyPrefs;
import com.chotupartner.utils.Constants;
import com.chotupartner.utils.Utilities;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;

import static com.chotupartner.utils.Constants.NEW_ORDER;
import static com.chotupartner.utils.Constants.ORDER_CANCLE_BY_DELIVERY_BOY;
import static com.chotupartner.utils.Constants.ORDER_CANCLE_BY_OutLet;
import static com.chotupartner.utils.Constants.ORDER_DELIVER;

public class MyFirebaseServiceMessaging extends FirebaseMessagingService {
    private LocalBroadcastManager broadcaster;
    int notificationId = 0;
    OnMessageRecievedFromPush onMessageRecievedFromPush;
    private final String TAG = getClass().getSimpleName();
    public static final String INTENT_FILTER = "INTENT_FILTER" + BuildConfig.APPLICATION_ID;

    @Override
    public void onNewToken(String s) {
        @SuppressLint("HardwareIds")
        String deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        Log.d("DEVICE_ID: ", deviceId);
        Log.d("FCM_TOKEN", s);

        ChotuBoyPrefs.putString(this, "device_token", s);
        ChotuBoyPrefs.putString(this, "device_id", deviceId);
    }


    @Override
    public void onCreate() {
        broadcaster = LocalBroadcastManager.getInstance(this);
    }

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (!ChotuBoyPrefs.getBoolean(this, Constants.LoginCheck)) {
            return;
        }

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "Fromdata: " + remoteMessage.getData());
        JSONObject jsonObject = new JSONObject();

        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
//            sendNotification(remoteMessage.getData().get("message"));
            try {
                String orderType = remoteMessage.getData().get("OrderType");

                switch (orderType) {
                    case NEW_ORDER:

                        jsonObject.put(Constants.PUSH_TYPE, orderType);
                        jsonObject.put(Constants.CUSTOMER_NAME, remoteMessage.getData().get("customer"));
                        jsonObject.put(Constants.PUSH_MOBILE, remoteMessage.getData().get("mobile"));
                        jsonObject.put(Constants.ORDER_ID, remoteMessage.getData().get("order_Id"));
                        jsonObject.put(Constants.OUTLET_ID, remoteMessage.getData().get("outlet_id"));
                        jsonObject.put(Constants.USERTYPE, remoteMessage.getData().get("userType"));
                        jsonObject.put(Constants.CUSTOMER_ID, remoteMessage.getData().get("customerId"));
                        jsonObject.put(Constants.CUSTOMER, remoteMessage.getData().get("customer"));

                        sendNotification(jsonObject, orderType,"New Order Conformed" );

                        break;

                    case ORDER_DELIVER:

                        jsonObject.put(Constants.PUSH_TYPE, orderType);
                        jsonObject.put(Constants.CUSTOMER_NAME, remoteMessage.getData().get("customer"));
                        jsonObject.put(Constants.PUSH_MOBILE, remoteMessage.getData().get("mobile"));
                        jsonObject.put(Constants.ORDER_ID, remoteMessage.getData().get("order_Id"));
                        jsonObject.put(Constants.OUTLET_ID, remoteMessage.getData().get("outlet_id"));
                        jsonObject.put(Constants.USERTYPE, remoteMessage.getData().get("userType"));
                        jsonObject.put(Constants.CUSTOMER_ID, remoteMessage.getData().get("customerId"));
                        jsonObject.put(Constants.CUSTOMER, remoteMessage.getData().get("customer"));

                        sendNotification(jsonObject, orderType,"Your Order Delivered" );

                        break;

                        case ORDER_CANCLE_BY_DELIVERY_BOY:
                        jsonObject.put(Constants.PUSH_TYPE, orderType);
                        sendNotification(jsonObject, orderType,"Your order cancel by delivery boy");

                        break;

                        case ORDER_CANCLE_BY_OutLet:
                        jsonObject.put(Constants.PUSH_TYPE, orderType);
                        sendNotification(jsonObject, orderType,"Your order is cancel by outlet");
                        break;

                        // do same next

                }

            } catch (Exception e) {
                Log.e("Exception: ", e.getMessage());
            }
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
    // [END receive_message]


    /**
     * Handle time allotted to BroadcastReceivers.
     */
    private void handleNow() {
        Log.d(TAG, "Short lived task is done.");
    }

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     */


    @RequiresApi(api = Build.VERSION_CODES.Q)
    private void sendNotification(JSONObject jsonObject, String type,String tittle) {
        String messageBody = jsonObject.toString();
        if (!Utilities.isAppIsInBackground(getApplicationContext())) {

            if (type.equalsIgnoreCase(NEW_ORDER) && ChotuBoyPrefs.getString(getApplicationContext(), Constants.USERTYPE).equalsIgnoreCase("4")) {
                return;
            }
            Intent intent = new Intent("MyData");
            intent.putExtra(Constants.PUSH_NEW_ORDER_DATA_KEY, jsonObject.toString());

            broadcaster.sendBroadcast(intent);


        } else {

            if (type.equalsIgnoreCase(NEW_ORDER) && ChotuBoyPrefs.getString(getApplicationContext(), Constants.USERTYPE).equalsIgnoreCase("4")) {
                return;
            }
            Utilities.printV(TAG, "background");
            Intent mainIntent = new Intent(this, DashBoardActivity.class);
            mainIntent.putExtra(Constants.PUSH_NEW_ORDER_DATA_KEY, jsonObject.toString());
            mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(mainIntent);


        }

        // NotificationPushData data = BaseUtil.objectFromString(messageBody, NotificationPushData.class);
        Intent intent = new Intent(getApplicationContext(), DashBoardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        //intent.putExtra(Constants.PUSH_NEW_BOOKING_TRIP_DATA_KEY, data);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

//        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getApplicationContext());
//        Intent localIntent = new Intent("CUSTOM_ACTION");
//        // Send local broadcast
//        localBroadcastManager.sendBroadcast(localIntent);
//        localBroadcastManager.sendBroadcast(intent);
//        PushNavigateReceiver receiver = new PushNavigateReceiver();
//        IntentFilter intentFilter = new IntentFilter("custom.notification.navigation");
//        registerReceiver(receiver, intentFilter);
        Intent filter = new Intent("custom.notification.navigation");
        sendBroadcast(filter);


        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "PUSH");
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        inboxStyle.addLine(messageBody);

        long when = System.currentTimeMillis();         // notification time


        // Sets an ID for the notification, so it can be updated.
        int notifyID = 1;
        String CHANNEL_ID = "my_channel_01";// The id of the channel.
        CharSequence name = "Channel human readable title";// The user-visible name of the channel.
        int importance = NotificationManager.IMPORTANCE_HIGH;


        Notification notification;
        notification = mBuilder.setSmallIcon(R.mipmap.ic_launcher).setTicker(getString(R.string.app_name)).setWhen(when)
//                .setAutoCancel(true)
                .setContentTitle(getString(R.string.app_name))
                .setContentIntent(pendingIntent)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(tittle))
                .setWhen(when)
                .setSmallIcon(getNotificationIcon(mBuilder))
                .setContentText(messageBody)
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


    public void setPushListener(OnMessageRecievedFromPush onMessageRecievedFromPush) {
        this.onMessageRecievedFromPush = onMessageRecievedFromPush;
    }


    public interface OnMessageRecievedFromPush {
        void onPushData(JSONObject jsonObject);
    }

}
