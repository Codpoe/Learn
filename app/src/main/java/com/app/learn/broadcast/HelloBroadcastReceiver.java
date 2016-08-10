package com.app.learn.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Codpoe on 2016/7/28.
 */
public class HelloBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "LocalBroadcast", Toast.LENGTH_SHORT).show();
    }
}
