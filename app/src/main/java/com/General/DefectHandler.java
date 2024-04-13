package com.General;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.buyereasefsl.AddWorkManShip;
import com.constant.AppConfig;
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
 * Created by ADMIN on 10/6/2017.
 */

public class DefectHandler implements JsonKey {

    static String TAG = "GeneralMasterHandler";

    public static boolean insertDefectMaster(Context mContext, DefectModal defectModal) {

        try {
            DBHelper mydb = new DBHelper(mContext);
            SQLiteDatabase db = mydb.getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            contentValues.put("pRowID", defectModal.pRowID);
            contentValues.put("LocID", defectModal.LocID);

            contentValues.put("DefectName", defectModal.DefectName);
            contentValues.put("Code", defectModal.Code);
            contentValues.put("DCLTypeID", defectModal.DCLTypeID);
            contentValues.put("InspectionStage", defectModal.InspectionStage);
            contentValues.put("chkCritical", defectModal.chkCritical);
            contentValues.put("chkMajor", defectModal.chkMajor);
            contentValues.put("chkMinor", defectModal.chkMinor);
            contentValues.put("recEnable", defectModal.recEnable);
            contentValues.put("recAdddt", defectModal.recAdddt);

            contentValues.put("recDt", AppConfig.getCurrntDate());
            contentValues.put("recAddUser", defectModal.recAddUser);
            contentValues.put("recUser", defectModal.recUser);


            int rows = db.update("DefectMaster", contentValues, "pRowID"
                    + " = '" + defectModal.pRowID + "'", null);
            if (rows == 0) {
                long result = db.insert("DefectMaster", null, contentValues);
                FslLog.d(TAG, " general table insert result................" + result);
            } else {
                FslLog.d(TAG, " general type update result................" + rows);
            }
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
            FslLog.d(TAG, "Exception to inserting general");
        }


        return true;
    }


    public static List<DefectModal> getDefectList(Context mContext) {


        ArrayList<DefectModal> lGeneral = new ArrayList<DefectModal>();
        try {
//            int status = VisitorNonGuardHandler.getTableExistORNot(mContext, DBHelper.GENERAL_MASTER_TABLE_NAME);
//            FslLog.d(TAG, " table exist or not " + status);
//            if (status > 0) {
            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getReadableDatabase();

            String query = "SELECT * FROM " + "DefectMaster";
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

    private static DefectModal getData(Cursor cursor) {

        DefectModal defectModal = new DefectModal();


        defectModal.pRowID = cursor.getString(cursor.getColumnIndex("pRowID"));
        defectModal.LocID = cursor.getString(cursor.getColumnIndex("LocID"));

        defectModal.DefectName = cursor.getString(cursor.getColumnIndex("DefectName"));
        defectModal.Code = cursor.getString(cursor.getColumnIndex("Code"));
        defectModal.DCLTypeID = cursor.getString(cursor.getColumnIndex("DCLTypeID"));
        defectModal.InspectionStage = cursor.getString(cursor.getColumnIndex("InspectionStage"));
        defectModal.chkCritical = cursor.getString(cursor.getColumnIndex("chkCritical"));
        defectModal.chkMajor = cursor.getString(cursor.getColumnIndex("chkMajor"));
        defectModal.chkMinor = cursor.getString(cursor.getColumnIndex("chkMinor"));
        defectModal.recEnable = cursor.getString(cursor.getColumnIndex("recEnable"));
        defectModal.recAdddt = cursor.getString(cursor.getColumnIndex("recAdddt"));
        defectModal.recDt = cursor.getString(cursor.getColumnIndex("recDt"));
        defectModal.recAddUser = cursor.getString(cursor.getColumnIndex("recAddUser"));
        defectModal.recUser = cursor.getString(cursor.getColumnIndex("recUser"));

        return defectModal;

    }

    public static List<DefectModal> getDataAccordingToParticularList(Context mContext, String GenId, String pRowID) {


        DBHelper dbHelper = new DBHelper(mContext);
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        String query = "SELECT * FROM " + "DefectMaster"
                + " WHERE " /*+ "GenID" + "='" + GenId + "' AND "*/ + "pRowID" + "='" + pRowID + "'";
        FslLog.d(TAG, "query for  get closed general list  " + query);
        Cursor cursor = database.rawQuery(query, null);

        ArrayList<DefectModal> lGeneral = new ArrayList<DefectModal>();

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

        String query = "SELECT " + "MainDescr" + " FROM " + "DefectMaster"
                + " where " + "pRowID" + " = '" + pRowID + "'";

        FslLog.d(TAG, " query for complaint name " + query);
        Cursor cursor = db.rawQuery(query, null);
        String MainDescr = null;
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                MainDescr = cursor.getString(cursor.getColumnIndex("DefectName"));
            }
        }
        cursor.close();
        db.close();

        return MainDescr;
    }


    public static void handlerToDefectMaster(final Context mContext,
                                             final int isBackGround,
                                             final String dataFor,
                                             String filter,
                                             final CallBackResult callBackResult) {

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
        List<DefectModal> lGeneralModelTypeList = parseComplaintType(generalMasterType, result);
        if (mContext != null && lGeneralModelTypeList != null && lGeneralModelTypeList.size() > 0) {
            updateDataBaseOfGeneralMaster(mContext, lGeneralModelTypeList);
        }
    }

    public static void updateDataBaseOfGeneralMaster(Context mContext, List<DefectModal> lGeneralModelTypeList) {
        if (mContext != null && lGeneralModelTypeList != null && lGeneralModelTypeList.size() > 0) {
            for (int i = 0; i < lGeneralModelTypeList.size(); i++) {
                insertDefectMaster(mContext, lGeneralModelTypeList.get(i));
            }
        }

    }

    public static List<DefectModal> parseComplaintType(String generalMasterType, JSONObject jsonObject) {

        List<DefectModal> generalModelList = null;
        if (jsonObject != null) {
            generalModelList = new ArrayList<>();
            JSONArray jsonArray = jsonObject.optJSONArray(generalMasterType);
            if (jsonArray != null && jsonArray.length() > 0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObjectOfComplaint = jsonArray.optJSONObject(i);
                    DefectModal complaintModel = new DefectModal();
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
