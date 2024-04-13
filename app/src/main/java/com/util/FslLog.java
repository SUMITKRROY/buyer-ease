package com.util;

import android.text.TextUtils;
import android.util.Log;

import com.constant.FClientConfig;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class FslLog {

    //private static File mExternalStorageDir = null;
    public static File logFile = null;

    //for additional info
    public static void i(String TAG, String message) {
        if (TextUtils.isEmpty(message))
            message = " ";
        // Printing the message to LogCat console
        Log.i(TAG, message);

        // Write the log message to the file
        appendLog("I", TAG, message);
    }

    //for general purpose of checking the flow
    public static void d(String TAG, String message) {
        if (TextUtils.isEmpty(message))
            message = " ";
        Log.d(TAG, message);
        appendLog("D", TAG, message);
    }

    public static void w(String TAG, String message) {
        if (TextUtils.isEmpty(message))
            message = " ";
        Log.w(TAG, message);
        appendLog("W", TAG, message);
    }

    //for only for unexpected scenarios
    public static void e(String TAG, String message) {
        if (TextUtils.isEmpty(message))
            message = " ";
        Log.e(TAG, message);
        appendLog("E", TAG, message);
    }

    //for detailed logs including printing JSON messages for  API related JSON messages
    public static void vc(String TAG, String message) {
        if (TextUtils.isEmpty(message))
            message = " ";
        Log.v(TAG, message);
        appendLog("V", TAG, message);
    }

    //for detailed logs including printing JSON messages for Google API related JSON messages
    public static void vg(String TAG, String message) {
        if (TextUtils.isEmpty(message))
            message = " ";
        Log.v(TAG, message);
        appendLog("V", TAG, message);
    }

    //public static void appendLog(String TAG, String whichType, String text) {
    public static void appendLog(String whichType, String TAG, String text) {

        boolean lInitFlag = true;

        if (logFile != null
                && logFile.exists()) {
            if (fileSize(logFile) >= FClientConfig.LogFileMaxSizeInKB) {
                Log.d("FslLog: ", "logger file more than " + FClientConfig.LogFileMaxSizeInKB + " deleting file");
                logFile.delete();
                logFile = null;
            }
        }

        if (logFile == null
                || !logFile.exists()) {
            Log.d("FslLog: ", "Logger file object doesn't exist");
            lInitFlag = initializeLog();
        }

        if (lInitFlag) {
            try {
                //BufferedWriter for performance, true to set append to file flag
                BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));
                String t = getTime();
                buf.append(t + ":: " + whichType + "/" + TAG + ": " + text);
                buf.newLine();
                buf.close();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            Log.d("FslLog: ", "Logger initialization failed");
        }

    }

    public static boolean initializeLog() {

        if (FClientConfig.mExternalStorageDir != null) {
            logFile = new File(FClientConfig.mExternalStorageDir, "FslLog.txt");
        }
        if (logFile == null) {
            Log.d("FslLog: ", "External storage path is null");
            return false;
        }

        Log.d("FslLog: ", "Logger file path - " + logFile.getAbsolutePath());

        //        File logFile = new File("sdcard/log.file");
        if (logFile.exists()) {
            Log.d("FslLog: ", "logger file exists");
            if (fileSize(logFile) >= FClientConfig.LogFileMaxSizeInKB) {
                Log.d("FslLog: ", "logger file more than " + FClientConfig.LogFileMaxSizeInKB + " deleting file");
                logFile.delete();
            }
        }

        if (!logFile.exists()) {
            try {
                logFile.createNewFile();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return false;
            }
        }

        try {

            BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));
            buf.append("\n\n\n---------------------------Logging Initialized---------------------------------\n\n\n");
            buf.newLine();
            buf.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return true;
    }


    static String getTime() {
        Calendar c = Calendar.getInstance();
//        System.out.println("Current time => "+c.getTime());

        //SimpleDateFormat df = new SimpleDateFormat("ddMMMyyyy hh:mm:ssaa", Locale.US);
        SimpleDateFormat df = new SimpleDateFormat("ddMMM hh:mm:ss.SSS aa", Locale.US);
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }

    public static long fileSize(File file) {
        long filesize = file.length();
        return filesize / 1024;
    }
}
