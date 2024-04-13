package com.General;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.constant.FEnumerations;
import com.constant.JsonKey;
import com.data.UserSession;
import com.database.DBHelper;
import com.login.UserData;
import com.util.FslLog;
import com.util.GenUtils;
import com.util.HandleToConnectionEithServer;
import com.util.NetworkUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.drakeet.support.toast.ToastCompat;

/**
 * Created by ADMIN on 12/18/2017.
 */

public class QualityLevelDtlHandler implements JsonKey {

    static String TAG = "QualityLevelDtlHandler";

    public static boolean insertQualityLevelMaster(Context mContext, QualityLevelDtl qualityLevelDtl) {

        try {
            DBHelper mydb = new DBHelper(mContext);
            SQLiteDatabase db = mydb.getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            contentValues.put("pRowID", qualityLevelDtl.pRowID);
            contentValues.put("LocID", qualityLevelDtl.LocID);

            contentValues.put("QlHdrID", qualityLevelDtl.QlHdrID);
            contentValues.put("SampleCode", qualityLevelDtl.SampleCode);
            contentValues.put("Accepted", qualityLevelDtl.Accepted);

            contentValues.put("recAddDt", qualityLevelDtl.recAddDt);
            contentValues.put("recDirty", qualityLevelDtl.recDirty);
            contentValues.put("recEnable", qualityLevelDtl.recEnable);
            contentValues.put("recUser", qualityLevelDtl.recUser);
            contentValues.put("recDt", qualityLevelDtl.recDt);

            int rows = db.update("QualityLevelDtl", contentValues, "pRowID"
                    + " = '" + qualityLevelDtl.pRowID + "'", null);
            if (rows == 0) {
                long result = db.insert("QualityLevelDtl", null, contentValues);
                FslLog.d(TAG, " QualityLevelDtl table insert result................" + result);
            } else {
                FslLog.d(TAG, " QualityLevelDtl type update result................" + rows);
            }
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
            FslLog.d(TAG, "Exception to inserting QualityLevelDtl");
        }


        return true;
    }


    public static List<QualityLevelDtl> getQualityLevelList(Context mContext) {


        ArrayList<QualityLevelDtl> lGeneral = new ArrayList<QualityLevelDtl>();
        try {
//            int status = VisitorNonGuardHandler.getTableExistORNot(mContext, DBHelper.GENERAL_MASTER_TABLE_NAME);
//            FslLog.d(TAG, " table exist or not " + status);
//            if (status > 0) {
            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getReadableDatabase();

            String query = "SELECT * FROM " + "QualityLevelDtl";
//                    + " WHERE " + "GenID" + "='" + GenId + "'";
            FslLog.d(TAG, "query for  get closed general list  " + query);
            Cursor cursor = database.rawQuery(query, null);


            if (cursor.getCount() > 0) {
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToNext();
                    lGeneral.add(getData(cursor));
                }
            }
            cursor.close();
            database.close();
//            }
            FslLog.d(TAG, " count of founded list of general " + lGeneral.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lGeneral;
    }

    private static QualityLevelDtl getData(Cursor cursor) {

        QualityLevelDtl qualityLevelDtl = new QualityLevelDtl();


        qualityLevelDtl.pRowID = cursor.getString(cursor.getColumnIndex("pRowID"));
        qualityLevelDtl.LocID = cursor.getString(cursor.getColumnIndex("LocID"));

        qualityLevelDtl.QlHdrID = cursor.getString(cursor.getColumnIndex("QlHdrID"));
        qualityLevelDtl.SampleCode = cursor.getString(cursor.getColumnIndex("SampleCode"));
        qualityLevelDtl.Accepted = cursor.getInt(cursor.getColumnIndex("Accepted"));

        qualityLevelDtl.recAddDt = cursor.getString(cursor.getColumnIndex("recAddDt"));
        qualityLevelDtl.recDirty = cursor.getInt(cursor.getColumnIndex("recDirty"));
        qualityLevelDtl.recEnable = cursor.getInt(cursor.getColumnIndex("recEnable"));
        qualityLevelDtl.recUser = cursor.getString(cursor.getColumnIndex("recUser"));
        qualityLevelDtl.recDt = cursor.getString(cursor.getColumnIndex("recDt"));


        return qualityLevelDtl;

    }

    public static List<QualityLevelDtl> getDataAccordingToParticularList(Context mContext, String GenId, String pRowID) {


        DBHelper dbHelper = new DBHelper(mContext);
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        String query = "SELECT * FROM " + "QualityLevelDtl"
                + " WHERE " /*+ "GenID" + "='" + GenId + "' AND "*/ + "pRowID" + "='" + pRowID + "'";
        FslLog.d(TAG, "query for  get closed general list  " + query);
        Cursor cursor = database.rawQuery(query, null);

        ArrayList<QualityLevelDtl> lGeneral = new ArrayList<QualityLevelDtl>();

        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                lGeneral.add(getData(cursor));
            }
        }
        cursor.close();
        database.close();
        FslLog.d(TAG, " count of founded list of general " + lGeneral.size());
        return lGeneral;
    }

    /*public static String getDataAccordingId(Context mContext, String pRowID) {


        DBHelper dbHelper = new DBHelper(mContext);

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT " + "fggg" + " FROM " + "QualityLevelDtl"
                + " where " + "pRowID" + " = '" + pRowID + "'";

        FslLog.d(TAG, " query for complaint name " + query);
        Cursor cursor = db.rawQuery(query, null);
        String MainDescr = null;
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                MainDescr = cursor.getString(cursor.getColumnIndex("ff"));
            }
        }
        cursor.close();
        db.close();

        return MainDescr;
    }*/


    public static void handlerToQualityLevelMaster(final Context mContext,
                                                   final int isBackGround,
                                                   final String dataFor,
                                                   String filter,
                                                   final DefectHandler.CallBackResult callBackResult) {

        String url = null;
        UserSession userSession = new UserSession(mContext);
        UserData userData = userSession.getLoginData();
        final Map<String, String> params = new HashMap<String, String>();
//        params.put(KEY_COMMUNITY, userData.community);
        params.put(KEY_USER_ID, userData.userId);
//        params.put(KEY_DATA_FOR, dataFor);
        params.put(KEY_DATA_FILTER, filter);


        FslLog.d(TAG, " master url " + url);
        FslLog.d(TAG, " master  param " + new JSONObject(params));

        if (!NetworkUtil.isNetworkAvailable(mContext)) {
            Toast toast = ToastCompat.makeText(mContext, "No Network Connectivity ", Toast.LENGTH_SHORT);
            GenUtils.safeToastShow(TAG, mContext, toast);
            return;
        }

        HandleToConnectionEithServer handleToConnectionEithServer = new HandleToConnectionEithServer();
        handleToConnectionEithServer.setRequest(url,
                new JSONObject(params), new HandleToConnectionEithServer.CallBackResult() {
                    @Override
                    public void onSuccess(JSONObject result) {
                        if (isBackGround == FEnumerations.E_SYNC_IN_BACKGROUND) {
                            handleOfGenMaster(mContext, dataFor, result);
                        } else {
                            callBackResult.onSuccess(result);
                        }
                    }

                    @Override
                    public void onError(VolleyError volleyError) {
                        callBackResult.onError(volleyError);
                    }
                });


    }

    private static void handleOfGenMaster(Context mContext, String generalMasterType, JSONObject result) {
        List<QualityLevelDtl> lGeneralModelTypeList = parseQualityLevel(generalMasterType, result);
        if (mContext != null && lGeneralModelTypeList != null && lGeneralModelTypeList.size() > 0) {
            updateDataBaseOfGeneralMaster(mContext, lGeneralModelTypeList);
        }
    }

    public static void updateDataBaseOfGeneralMaster(Context mContext, List<QualityLevelDtl> lGeneralModelTypeList) {
        if (mContext != null && lGeneralModelTypeList != null && lGeneralModelTypeList.size() > 0) {
            for (int i = 0; i < lGeneralModelTypeList.size(); i++) {
                insertQualityLevelMaster(mContext, lGeneralModelTypeList.get(i));
            }
        }

    }

    public static List<QualityLevelDtl> parseQualityLevel(String generalMasterType, JSONObject jsonObject) {

        List<QualityLevelDtl> generalModelList = null;
        if (jsonObject != null) {
            generalModelList = new ArrayList<>();
            JSONArray jsonArray = jsonObject.optJSONArray(generalMasterType);
            if (jsonArray != null && jsonArray.length() > 0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObjectOfComplaint = jsonArray.optJSONObject(i);
                    QualityLevelDtl complaintModel = new QualityLevelDtl();
//                    complaintModel.GEN_NAME = jsonObjectOfComplaint.optString("Name");
//                    complaintModel.SERVER_GENERAL_MASTER_ID = jsonObjectOfComplaint.optString("ID");
//                    complaintModel.GENID = jsonObjectOfComplaint.optString("GenID");
//                    complaintModel.BBRV = jsonObjectOfComplaint.optString("Abbrv");
//                    complaintModel.RECOVER_DATE = jsonObjectOfComplaint.optString(RECOVER_DATE);

                    generalModelList.add(complaintModel);
                }
            }

        }

        return generalModelList;
    }

    public interface CallBackResult {
        public void onSuccess(JSONObject resultResponse);

        public void onError(VolleyError volleyError);
    }
}


