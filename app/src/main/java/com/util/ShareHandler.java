package com.util;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.constant.FClientConfig;
import com.data.UserSession;

import java.io.File;

public class ShareHandler {
    static String TAG = "ShareHandler";

    public static void HandleLogShare(Activity activity) {
        /*FslLog.d(TAG, "checking for camera & read storage permissions");
        boolean lPermissionFlag = PermissionSeekingActivity.checkPermission(activity
                , FEnumerations.PHOTO_PERMISSION);

        if (lPermissionFlag) {
            FslLog.d(TAG, "camera or read storage permission not available");

        } else {
            FslLog.d(TAG, "seeking camera or read storage permission");
            Intent intent = new Intent(activity, PermissionSeekingActivity.class);
            intent.putExtra(FClientConstants.PERMISSION_INTENT, FEnumerations.PHOTO_PERMISSION);
            activity.startActivityForResult(intent, FEnumerations.PERMISSION_REQUEST);
        }*/
        handleShareFile(activity);
    }

    private static void handleShareFile(Activity activity) {
        if (FClientConfig.mExternalStorageDir == null) {
            SetInitiateStaticVariable.setInitiateStaticVariable(AppController.getInstance());
        }
        File logFile = null;
        if (FClientConfig.mExternalStorageDir != null) {

//            logFile = new File(FClientConfig.mExternalStorageDir + "/" + FClientConfig.critical_Log_file_folder);
            logFile = new File(FClientConfig.mExternalStorageDir );


        }
        if (logFile.exists()) {

            Log.e("log file path=","log file path="+logFile.getAbsolutePath());
            UserSession userSession = new UserSession(activity);
            String userName = null, msgBody = null;
            if (userSession != null) {
                userName = userSession.getProfileName();
                msgBody = "This is email from user : " + userName;
            }
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "App log file " + userName);
            if (!TextUtils.isEmpty(msgBody)) {
                sharingIntent.putExtra(Intent.EXTRA_TEXT, msgBody);
            }
            sharingIntent.setType("text/*");
            String to[] = {"support@salesmantra.com"};
            sharingIntent.putExtra(Intent.EXTRA_EMAIL, to);
            sharingIntent.putExtra(Intent.EXTRA_STREAM, GenUtils.getUri(activity, logFile));//Uri.parse("content://" + logFile.getAbsolutePath())
            //sharingIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(logFile.getAbsolutePath())));

            sharingIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            try {
                activity.startActivity(Intent.createChooser(sharingIntent, "share file with"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(activity, "File not exist ", Toast.LENGTH_SHORT).show();
        }
    }
}
