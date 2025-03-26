package com.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.constant.JsonKey;


/**
 * Created by ADMIN on 8/31/2017.
 */

public class DeviceSession implements JsonKey {
    SharedPreferences pref;
    //Editor for Shared preferences
    SharedPreferences.Editor editor;
    //Context
    Context context;
    //Shared pref mode
    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = DeviceSession.class.getName();

    public DeviceSession(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void saveStaticVariable(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }
    public InitiateStaticVariable getInitiateStaticVariable() {
        InitiateStaticVariable mInitiateStaticVariable = new InitiateStaticVariable();
        mInitiateStaticVariable.sExternalStorageDir = pref.getString(KEY_mExternalStorageDir, null);
//        mInitiateStaticVariable.sHost = pref.getString(KEY_Host, null);
        return mInitiateStaticVariable;
    }
    public void updateRegistrationId(String token) {
        editor.putString(KEY_GCM_ID, token);
        editor.commit();
    }

    public String getDeviceGcmID() {

        return pref.getString(KEY_GCM_ID, null);
    }
}
