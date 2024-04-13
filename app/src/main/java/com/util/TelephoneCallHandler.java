package com.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import com.constant.FEnumerations;


public class TelephoneCallHandler {

    public static final String TAG = "TelephoneCallHandler";

    private static String mTelNum;

    public static boolean initiateCall(final Context currentActivity, final String telephoneNum) {
        boolean isPermissionAvailable = false;

        if (!TextUtils.isEmpty(telephoneNum)) {
            FslLog.d(TAG, "Initiating call to number " + telephoneNum);
            mTelNum = "tel:+" + telephoneNum;

            //Check for permission - not seeking customer permission

            //Just initiating dialer without any permission to directly call
            initiateDialer(currentActivity, mTelNum);

            return true;
        }

        FslLog.e(TAG, "Telephone number invalid");
        return false;
    }

    private static void performOutgoingCall(Activity currentActivity, String number) {
        try {

            FslLog.d(TAG, "Permission available to initiate call - initiating call");
            Intent callIntent;
            callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse(number));
            callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            currentActivity.startActivity(callIntent);

        } catch (SecurityException e) {
            //should not happen
            FslLog.e(TAG, "Permission not available to initiate call]");
            e.printStackTrace();
        }
    }

    private static void initiateDialer(Context currentActivity, String number) {
        FslLog.d(TAG, "Permission not available to initiate call - only opening dialer");
        Intent callIntent;
        callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse(number));
        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        currentActivity.startActivity(callIntent);
    }

    public static void onResultFromActivity(Activity currentActivity, int requestCode, int resultCode, Intent result) {

        if (requestCode == FEnumerations.PERMISSION_REQUEST) {
            if (resultCode == currentActivity.RESULT_OK) {
                performOutgoingCall(currentActivity, mTelNum);
            } else {
                initiateDialer(currentActivity, mTelNum);
            }
        }
    }

}
