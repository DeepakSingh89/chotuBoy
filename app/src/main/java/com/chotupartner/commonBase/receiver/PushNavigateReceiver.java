package com.chotupartner.commonBase.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class PushNavigateReceiver extends BroadcastReceiver {

    private String TAG=getClass().getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e(TAG,"I m in Push Navigate");
        System.out.println(TAG+" I m in Push Navigate");
    }


}
