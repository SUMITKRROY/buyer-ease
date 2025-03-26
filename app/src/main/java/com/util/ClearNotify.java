package com.util;

import android.app.NotificationManager;
import android.content.Context;


public class ClearNotify {
    public static void clearNotify(Context context) {
        FslLog.d("ClearNotify", " notification is clear");
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }
}
