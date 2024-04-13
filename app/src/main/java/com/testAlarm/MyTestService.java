package com.testAlarm;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * Created by ADMIN on 11/22/2017.
 */

public class MyTestService  extends IntentService {
    public MyTestService() {
        super("MyTestService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // Do the task here
        Log.i("MyTestService", "Service running");
    }
}