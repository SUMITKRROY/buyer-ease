package com.util;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.text.TextUtils;

import com.constant.FClientConfig;
import com.constant.FEnumerations;

import java.text.SimpleDateFormat;
import java.util.Locale;




/**
 * Created by Anand on 19-09-2016.
 */
public class IncomingSMSReader extends BroadcastReceiver {

    private final String SMS_BUNDLE = "pdus";
    private static final String TAG = "IncomingSMSReader";

    private boolean mIsRegistered = false;
    private IncomingSMSCallback mCallBackListner = null;

    public IncomingSMSReader() {
    }

    public boolean registerBroadcastReceiver(Activity currentActivity
            , IncomingSMSCallback callBackListner) {
        boolean lReadSMSPermissionFlag = true;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            lReadSMSPermissionFlag = PermissionSeekingActivity.checkPermission(currentActivity
                    , FEnumerations.READ_SMS_PERMISSION);
        }

        if (lReadSMSPermissionFlag) {
            FslLog.d(TAG, "registering for receiving smses");
            IntentFilter intentFilter = new IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION);//"android.provider.Telephony.SMS_RECEIVED");
            intentFilter.setPriority(999);
            currentActivity.registerReceiver(this, intentFilter);
            mCallBackListner = callBackListner;
            mIsRegistered = true;

        } else {
            FslLog.d(TAG, "Reading SMS persmission not granted so not listening to SMSes");
        }

        return lReadSMSPermissionFlag;
    }

    //Please call this method
    // onBackPressed & on home button
    // before finishing actviity
    // on submitting final submit button
    // on resend SMS
    public boolean unRegisterBroadcastReceiver(Activity currentActivity) {

        if (mIsRegistered) {
            FslLog.d(TAG, "SMS receiver unregistered");
            currentActivity.unregisterReceiver(this);
            mIsRegistered = false;
        }
        return true;
    }

    public void onReceive(Context context, Intent intent) {

        Bundle intentExtras = intent.getExtras();

        if (intentExtras != null) {
            Object[] messages = (Object[]) intentExtras.get(SMS_BUNDLE);

            String smsMessageStr = "";
            SmsMessage[] smsm = null;
            smsm = new SmsMessage[messages.length];
            FslLog.d(TAG, "No of SMSes received - " + messages.length);

            for (int i = 0; i < messages.length; ++i) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    String format = intentExtras.getString("format");
                    smsm[i] = SmsMessage.createFromPdu((byte[]) messages[i], format);
                } else {
                    smsm[i] = SmsMessage.createFromPdu((byte[]) messages[i]);
                }

                String smsBody = smsm[i].getMessageBody().toString();
                String address = smsm[i].getOriginatingAddress();

                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss aa", Locale.US);
                String dateTime = formatter.format(smsm[i].getTimestampMillis()).toString();

                smsMessageStr += "SMS From: " + address + "\n";
                smsMessageStr += smsBody + "\n";
                smsMessageStr += "datetime : " + dateTime + "\n";
                FslLog.d(TAG, smsMessageStr);

            }

            if (mCallBackListner != null
                    && smsm != null
                    && smsm.length > 0)
                mCallBackListner.onIncomingSMS(smsm);

        }
    }

    public static String parseOTPSms(SmsMessage[] receivedSMSes,
                                     String strToMatch) {

        String lOTP = "";

        if (TextUtils.isEmpty(strToMatch))
            strToMatch = FClientConfig.OTP_SMS_STRING_TO_MATCH;

        String lMatchingSMS = parseSms(receivedSMSes, strToMatch);

        if (!TextUtils.isEmpty(lMatchingSMS)) {
            lOTP = lMatchingSMS.substring(0, FClientConfig.MIN_OTP_LEN);
            FslLog.d(TAG, "Received OTP is " + lOTP);

        }

        return lOTP;
    }

  /**/


   /**/
    public static String parseSms(SmsMessage[] receivedSMSes,
                                  String strToMatch) {

        String lMatchingSMS = "";
        if (receivedSMSes != null
                && receivedSMSes.length > 0) {
            for (int i = 0; i < receivedSMSes.length; ++i) {

                SmsMessage lSms = receivedSMSes[i];
                if (lSms != null
                        && !TextUtils.isEmpty(lSms.getMessageBody())
                        && lSms.getMessageBody().contains(strToMatch)) {

                    lMatchingSMS = lSms.getMessageBody();
                    FslLog.d(TAG, "Matching SMS - " + lMatchingSMS);
                    break;

                }
            }
        }

        return lMatchingSMS;
    }

    public interface IncomingSMSCallback {
        void onIncomingSMS(SmsMessage[] receivedSMSes);
    }
}
