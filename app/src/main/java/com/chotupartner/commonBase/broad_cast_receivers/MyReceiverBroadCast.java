package com.chotupartner.commonBase.broad_cast_receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyReceiverBroadCast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String extraData = intent.getStringExtra("com.chotupartner.EXTRA_DATA");
        Toast.makeText(context, "From Receiver : "+extraData, Toast.LENGTH_SHORT).show();
    }
}
