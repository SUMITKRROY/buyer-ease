package com.util;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.constant.FClientConstants;

import me.drakeet.support.toast.ToastCompat;


public class VolleyErrorHandler {

    private static String TAG = "VolleyErrorHandler";

    public static boolean errorHandler(Context context, VolleyError error) {


        if (error instanceof AuthFailureError) {
            FslLog.e(TAG, "AuthFailureError " + error.toString());
            //NetworkUtil.networkErrorDialog(context, "AuthFailure");
            // get society result and do generic handling
            //if 411 - Invalid session id then show main activity with options of signin and new user
            ServerErrorHandleStatus.onAuthenticationErrorStatus(context, error);
            return true;
        } else if (error instanceof ParseError) {
            FslLog.e(TAG, "ParseError " + error);
            if (error != null)
                FslLog.e(TAG, "ParseError message " + error.getMessage());

            Toast toast = ToastCompat.makeText(context, FClientConstants.TEXT_facing_some_trouble_error_to_request, Toast.LENGTH_LONG);
            GenUtils.safeToastShow(TAG, context, toast);
            //Anand comment only for testing
            NetworkUtil.showServerErrorDialog(context, null);

            return true;
        } else if (error.getClass().equals(NoConnectionError.class)) {
            FslLog.e(TAG, "No connection ERROR with server error handling");
            Toast toast = ToastCompat.makeText(context, FClientConstants.TEXT_not_able_to_receive_response_from_server, Toast.LENGTH_LONG);
            GenUtils.safeToastShow(TAG, context, toast);
//            handleOnNoCOnnectionError(context);
            return true;
        } else if (error.getClass().equals(TimeoutError.class)) {
            FslLog.e(TAG, "TimeoutError No connection ERROR with server error handling");
            Toast toast = ToastCompat.makeText(context, FClientConstants.TEXT_not_able_to_receive_response_from_server, Toast.LENGTH_LONG);
            GenUtils.safeToastShow(TAG, context, toast);
//            handleTimeoutError(context);
            return true;
        } else {
            FslLog.e(TAG, "no generic http error handling done");
            return false;
        }
    }

    private static void handleTimeoutError(final Context context) {

        FslLog.e(TAG, "No response from server, may be slow network");
        Toast toast = ToastCompat.makeText(context, FClientConstants.TEXT_not_able_to_receive_response_from_server, Toast.LENGTH_LONG);
        GenUtils.safeToastShow(TAG, context, toast);
        //Anand comment only for testing
        if (!NetworkUtil.isNetworkAvailable(context)) {
            NetworkUtil.showNetworkErrorDialog(context);
        } else {
            NetworkUtil.showSessionTimeDialog(context,
                    FClientConstants.TEXT_request_time_out,
                    FClientConstants.TEXT_we_are_not_able_to_receive_response_from_server,
                    new NetworkUtil.SessionCloseListner() {
                        @Override
                        public void onSessionClosed() {
                            //((Activity) context).finish();
                            // do nothing
                        }
                    });
        }
    }

    private static void handleOnNoCOnnectionError(final Context context) {

        FslLog.e(TAG, "No connection with server error handling");
        Toast toast = ToastCompat.makeText(context, FClientConstants.TEXT_not_able_to_receive_response_from_server, Toast.LENGTH_LONG);
        GenUtils.safeToastShow(TAG, context, toast);
        //Anand comment only for testing
        if (!NetworkUtil.isNetworkAvailable(context)) {
            NetworkUtil.showNetworkErrorDialog(context);
        } else {
            //this can happen when phone is connected to Wifi but there is no forward connection
            //NetworkUtil.showServerErrorDialog(context, null);
            GenUtils.forConfirmationAlertDialog(context
                    , "Exit"
                    , "Cancel"
                    , "Server Connection Error"
                    , "There seems to be a momentary problem in connecting with our server. You can retry or restart app."
                    , new GenUtils.AlertDialogClickListener() {
                        @Override
                        public void onPositiveButton() {
                            ((Activity) context).finishAffinity();
                        }

                        @Override
                        public void onNegativeButton() {
                            //do nothing
                        }
                    });
        }

    }

    public static void handleInternalError(final Context context, VolleyError volleyError) {
        FslLog.e(TAG, " Status code - " + volleyError.networkResponse.statusCode + " Internal SERVER ERROR........");

        //Anand comment only for testing
        NetworkUtil.showServerErrorDialog(context, null);

    }
    public static void handleInternaBadError(final Context context, VolleyError volleyError) {
        FslLog.e(TAG, " Status code - " + volleyError.networkResponse.statusCode + " Internal SERVER ERROR........");

        //Anand comment only for testing
        NetworkUtil.showServerErrorDialog(context, "Bad Request");

    }

}
