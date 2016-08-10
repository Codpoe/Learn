package com.app.learn.Service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;

import com.app.learn.MainActivity;
import com.app.learn.R;

/**
 * Created by Codpoe on 2016/7/27.
 */
public class LearnService extends Service {

    private static final int NOTIFY_ID = 123;

    @Override
    public void onCreate() {
        super.onCreate();
        showNotification();
    }

    public void showNotification() {
        NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_favorite_black_24dp)
                .setContentTitle("Test")
                .setContentText("123");
        // 点击通知触发的 Intent
        Intent resulteIntent = new Intent(this, MainActivity.class);
        // 任务栈 Builder
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resulteIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(resultPendingIntent);
        // 构建通知
        Notification notification = builder.build();
        // 通知管理器
        NotificationManager notificationMgr = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationMgr.notify(NOTIFY_ID, notification);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
