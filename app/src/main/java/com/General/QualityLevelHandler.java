package com.General;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.buyereasefsl.AddWorkManShip;
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

public class QualityLevelHandler implements JsonKey {

    static String TAG = "QualityLevelHandler";

    public static boolean insertQualityLevelMaster(Context mContext, QualityLevelModal qualityLevelModal) {

        try {
            DBHelper mydb = new DBHelper(mContext);
            SQLiteDatabase db = mydb.getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            contentValues.put("pRowID", qualityLevelModal.pRowID);
            contentValues.put("LocID", qualityLevelModal.LocID);

            contentValues.put("QualityLevel", qualityLevelModal.QualityLevel);
            contentValues.put("recAddDt", qualityLevelModal.recAddDt);
            contentValues.put("recDirty", qualityLevelModal.recDirty);
            contentValues.put("recEnable", qualityLevelModal.recEnable);
            contentValues.put("recUser", qualityLevelModal.recUser);
            contentValues.put("recDt", qualityLevelModal.recDt);

            int rows = db.update("QualityLevel", contentValues, "pRowID"
                    + " = '" + qualityLevelModal.pRowID + "'", null);
            if (rows == 0) {
                long result = db.insert("QualityLevel", null, contentValues);
                FslLog.d(TAG, " QualityLevel table insert result................" + result);
            } else {
                FslLog.d(TAG, " QualityLevel type update result................" + rows);
            }
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
            FslLog.d(TAG, "Exception to inserting QualityLevel");
        }


        return true;
    }


    public static List<QualityLevelModal> getQualityLevelList(Context mContext) {


        ArrayList<QualityLevelModal> lGeneral = new ArrayList<QualityLevelModal>();
        try {
//            int status = VisitorNonGuardHandler.getTableExistORNot(mContext, DBHelper.GENERAL_MASTER_TABLE_NAME);
//            FslLog.d(TAG, " table exist or not " + status);
//            if (status > 0) {
            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getReadableDatabase();

            String query = "SELECT * FROM " + "QualityLevel";
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

    private static QualityLevelModal getData(Cursor cursor) {

        QualityLevelModal qualityLevelModal = new QualityLevelModal();


        qualityLevelModal.pRowID = cursor.getString(cursor.getColumnIndex("pRowID"));
        qualityLevelModal.LocID = cursor.getString(cursor.getColumnIndex("LocID"));

        qualityLevelModal.QualityLevel = cursor.getString(cursor.getColumnIndex("QualityLevel"));
        qualityLevelModal.recAddDt = cursor.getString(cursor.getColumnIndex("recAddDt"));
        qualityLevelModal.recDirty = cursor.getInt(cursor.getColumnIndex("recDirty"));
        qualityLevelModal.recEnable = cursor.getInt(cursor.getColumnIndex("recEnable"));
        qualityLevelModal.recUser = cursor.getString(cursor.getColumnIndex("recUser"));
        qualityLevelModal.recDt = cursor.getString(cursor.getColumnIndex("recDt"));


        return qualityLevelModal;

    }

    public static List<QualityLevelModal> getDataAccordingToParticularList(Context mContext, String pRowID) {


        DBHelper dbHelper = new DBHelper(mContext);
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        String query = "SELECT * FROM " + "QualityLevel"
                + " WHERE " /*+ "GenID" + "='" + GenId + "' AND "*/ + "pRowID" + "='" + pRowID + "'";
        FslLog.d(TAG, "query for  get closed general list  " + query);
        Cursor cursor = database.rawQuery(query, null);

        ArrayList<QualityLevelModal> lGeneral = new ArrayList<QualityLevelModal>();

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

    public static String getDataAccordingId(Context mContext, String pRowID) {


        DBHelper dbHelper = new DBHelper(mContext);

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT " + "QualityLevel" + " FROM " + "QualityLevel"
                + " where " + "pRowID" + " = '" + pRowID + "'";

        FslLog.d(TAG, " query for complaint name " + query);
        Cursor cursor = db.rawQuery(query, null);
        String MainDescr = null;
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                MainDescr = cursor.getString(cursor.getColumnIndex("QualityLevel"));
            }
        }
        cursor.close();
        db.close();

        return MainDescr;
    }


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
        List<QualityLevelModal> lGeneralModelTypeList = parseQualityLevel(generalMasterType, result);
        if (mContext != null && lGeneralModelTypeList != null && lGeneralModelTypeList.size() > 0) {
            updateDataBaseOfGeneralMaster(mContext, lGeneralModelTypeList);
        }
    }

    public static void updateDataBaseOfGeneralMaster(Context mContext, List<QualityLevelModal> lGeneralModelTypeList) {
        if (mContext != null && lGeneralModelTypeList != null && lGeneralModelTypeList.size() > 0) {
            for (int i = 0; i < lGeneralModelTypeList.size(); i++) {
                insertQualityLevelMaster(mContext, lGeneralModelTypeList.get(i));
            }
        }

    }

    public static List<QualityLevelModal> parseQualityLevel(String generalMasterType, JSONObject jsonObject) {

        List<QualityLevelModal> generalModelList = null;
        if (jsonObject != null) {
            generalModelList = new ArrayList<>();
            JSONArray jsonArray = jsonObject.optJSONArray(generalMasterType);
            if (jsonArray != null && jsonArray.length() > 0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObjectOfComplaint = jsonArray.optJSONObject(i);
                    QualityLevelModal complaintModel = new QualityLevelModal();
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

