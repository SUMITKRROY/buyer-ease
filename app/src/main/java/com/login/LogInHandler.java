package com.login;


import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.android.volley.VolleyError;
import com.constant.AppConfig;
import com.constant.FEnumerations;
import com.constant.JsonKey;
import com.data.UserSession;
import com.database.DBHelper;
import com.util.Device_Info;
import com.util.FslLog;
import com.util.HandleToConnectionEithServer;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by ADMIN on 8/30/2017.
 */

public class LogInHandler implements JsonKey {
    static String TAG = "LogInHandler";

    public static void getLogInMaster(final Context mContext, final String userNameNo,
                                      final String pass,
                                      final GetLoginResult getLoginResult) {

        String url = AppConfig.URL_LOGIN;

        final Map<String, String> params = new HashMap<String, String>();
        params.put(KEY_USER_ID, userNameNo);
        params.put(KEY_PASSWORD, pass);
        params.put(KEY_DEVICE_ID, new Device_Info(mContext).getUniqueID());
        params.put(KEY_DEVICE_IP, new Device_Info(mContext).getDeviceIp());
        params.put(KEY_DEVICE_LOCATION, "");
        params.put(KEY_DEVICE_TYPE, "A");

        params.put("HDDSerialNo", "");

        FslLog.d(TAG, "login url " + url);
        FslLog.d(TAG, "login param " + new JSONObject(params));

        HandleToConnectionEithServer handleToConnectionEithServer = new HandleToConnectionEithServer();
        handleToConnectionEithServer.setRequest(url,
                new JSONObject(params), new HandleToConnectionEithServer.CallBackResult() {
                    @Override
                    public void onSuccess(JSONObject result) {
//                if (isBackGround == FEnumerations.E_SYNC_IN_BACKGROUND) {
//
//                } else {
                        getLoginResult.onSuccess(result);
//                }
                    }

                    @Override
                    public void onError(VolleyError volleyError) {
//                        getLoginResult.onError(volleyError);
                        getLoginResult.onError(volleyError);
                    }
                });


    }


    public static UserMaster getUserMaster(Context mContext) {


//        List<UserMaster> itemArrayList = new ArrayList<UserMaster>();
        UserMaster userMaster = null;
        try {
            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getReadableDatabase();

            String query = "Select * from UserMaster";


           /* String query = "SELECT * FROM " + "QRPOItemdtl" + " where QRHdrID='" + pRowID + "'";*/
            FslLog.d(TAG, "get query for  user master  " + query);
            Cursor cursor = database.rawQuery(query, null);


            if (cursor.getCount() > 0) {
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToNext();
                    userMaster = new UserMaster();

                    userMaster.pUserID = cursor.getString(cursor.getColumnIndex("pUserID"));
                    userMaster.ParentID = cursor.getString(cursor.getColumnIndex("ParentID"));
                    userMaster.LoginName = cursor.getString(cursor.getColumnIndex("LoginName"));
                    userMaster.Password = cursor.getString(cursor.getColumnIndex("Password"));
                    userMaster.UserName = cursor.getString(cursor.getColumnIndex("UserName"));
                    userMaster.Email = cursor.getString(cursor.getColumnIndex("Email"));
                    userMaster.fCommunityID = cursor.getString(cursor.getColumnIndex("fCommunityID"));
                    userMaster.fContactID = cursor.getString(cursor.getColumnIndex("fContactID"));

                    userMaster.IsAdmin = cursor.getInt(cursor.getColumnIndex("IsAdmin"));
                    userMaster.DateFormat = cursor.getInt(cursor.getColumnIndex("DateFormat"));
                    userMaster.HomePOGraph = cursor.getInt(cursor.getColumnIndex("HomePOGraph"));

                    userMaster.LocId = cursor.getString(cursor.getColumnIndex("LocId"));
                    userMaster.Desg = cursor.getString(cursor.getColumnIndex("Desg"));
                    userMaster.Address = cursor.getString(cursor.getColumnIndex("Address"));
                    userMaster.City = cursor.getString(cursor.getColumnIndex("City"));
                    userMaster.Zip = cursor.getString(cursor.getColumnIndex("Zip"));
                    userMaster.State = cursor.getString(cursor.getColumnIndex("State"));
                    userMaster.CountryID = cursor.getString(cursor.getColumnIndex("CountryID"));
                    userMaster.PhoneNo1 = cursor.getString(cursor.getColumnIndex("PhoneNo1"));

                    try {
                        FslLog.d(TAG, " userType  " + cursor.getString(cursor.getColumnIndex("UserType")));
                        if (!TextUtils.isEmpty(cursor.getString(cursor.getColumnIndex("UserType")))
                                && cursor.getString(cursor.getColumnIndex("UserType")).equalsIgnoreCase("01")) {
                            userMaster.userType = FEnumerations.E_USER_TYPE_MR;
                        } else {
                            userMaster.userType = FEnumerations.E_USER_TYPE_ADMIN;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

//                    itemArrayList.add(poItemDtl);
                }
            }
            cursor.close();
            database.close();
//            FslLog.d(TAG, " cout of founded list of packagingMeasurement " + itemArrayList.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userMaster;
    }

    public static String getUserId(Context mContext) {


        String userId = null;
        try {
            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getReadableDatabase();

            String query = "Select pUserID from UserMaster";


           /* String query = "SELECT * FROM " + "QRPOItemdtl" + " where QRHdrID='" + pRowID + "'";*/
            FslLog.d(TAG, "get query for  user master  " + query);
            Cursor cursor = database.rawQuery(query, null);


            if (cursor.getCount() > 0) {
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToNext();

                    userId = cursor.getString(cursor.getColumnIndex("pUserID"));

                }
            }
            cursor.close();
            database.close();
            FslLog.d(TAG, " get user id from db " + userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userId;
    }



    public static void handlerToChangePassword(Context mContext, String url
            , JSONObject jsonObject, final GetLoginResult getLoginResult) {


        FslLog.d(TAG, "handlerToChangePassword url " + url);
        FslLog.d(TAG, "handlerToChangePassword param " + jsonObject);


        HandleToConnectionEithServer handleToConnectionEithServer = new HandleToConnectionEithServer();
        handleToConnectionEithServer.setRequest(url,
                jsonObject, new HandleToConnectionEithServer.CallBackResult() {
                    @Override
                    public void onSuccess(JSONObject result) {
//                if (isBackGround == FEnumerations.E_SYNC_IN_BACKGROUND) {
//
//                } else {
                        getLoginResult.onSuccess(result);
//                }
                    }

                    @Override
                    public void onError(VolleyError volleyError) {
//                        getLoginResult.onError(volleyError);
                        getLoginResult.onError(volleyError);
                    }
                });


    }




 /*   public void encript(String SrcKey,String SrcStr)
    {

        int Start_No , i , y , z , l ,N;
        Start_No = 0;
        for (i = 1 ;i<SrcKey.length();i++)
            Start_No = Start_No + Asc(Mid(SrcKey, i, 1)) * i;

                Start_No = Start_No % 255;
        SrcStr = Left(SrcStr.trim() + Space(N), N)
        EncrypStr = "";
        Randomize(Start_No);
        For i = 1 To N
        y = Asc(Mid(SrcStr, i, 1))
        l = CInt(Int(Rnd(-1) * 123))
        z = (l + y) Mod 255
        EncrypStr = EncrypStr + Chr(z)

    }*/

    public static interface GetLoginResult {
        public void onSuccess(JSONObject loginResponse);

        public void onError(VolleyError volleyError);


    }

    public static interface GetLogin {
        public void onSuccess(JSONArray loginResponse);

        public void onError(VolleyError volleyError);
    }
}
