package com.util;

import android.content.Context;

import com.constant.FClientConfig;
import com.data.DeviceSession;
import com.data.InitiateStaticVariable;


/**
 * Created by ADMIN on 10/4/2017.
 */

public class SetInitiateStaticVariable {
    public static void setInitiateStaticVariable(Context context) {

        DeviceSession deviceSession = new DeviceSession(context);
        InitiateStaticVariable mInitiateStaticVariable = deviceSession.getInitiateStaticVariable();
        FClientConfig.mExternalStorageDir = mInitiateStaticVariable.sExternalStorageDir;
        //CONSTANT.HOST = mInitiateStaticVariable.sHost;
        //CONSTANT.setURLs();
    }
}
