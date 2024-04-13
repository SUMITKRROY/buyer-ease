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
 * Created by ADMIN on 12/18/2017.
 */

public class InsLvDtlHandler implements JsonKey {

    static String TAG = "InsLvDtlHandler";

    public static boolean insertInsLvDtlMaster(Context mContext, InsLvDtlModal insLvDtlModal) {

        try {
            DBHelper mydb = new DBHelper(mContext);
            SQLiteDatabase db = mydb.getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            contentValues.put("pRowID", insLvDtlModal.pRowID);
            contentValues.put("LocID", insLvDtlModal.LocID);

            contentValues.put("InspHdrID", insLvDtlModal.InspHdrID);
            contentValues.put("SignDescr", insLvDtlModal.SignDescr);
            contentValues.put("BatchSize", insLvDtlModal.BatchSize);
            contentValues.put("SampleCode", insLvDtlModal.SampleCode);
            contentValues.put("recDirty", insLvDtlModal.recDirty);
            contentValues.put("recEnable", insLvDtlModal.recEnable);
            contentValues.put("recUser", insLvDtlModal.recUser);
            contentValues.put("recDt", insLvDtlModal.recDt);
            contentValues.put("recAddUser", insLvDtlModal.recAddUser);
            contentValues.put("recAddDt", insLvDtlModal.recAddDt);

            int rows = db.update("InspLvlDtl", contentValues, "pRowID"
                    + " = '" + insLvDtlModal.pRowID + "'", null);
            if (rows == 0) {
                long result = db.insert("InspLvlDtl", null, contentValues);
                FslLog.d(TAG, " InspLvlDtl table insert result................" + result);
            } else {
                FslLog.d(TAG, " InspLvlDtl type update result................" + rows);
            }
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
            FslLog.d(TAG, "Exception to inserting InspLvlDtl");
        }


        return true;
    }


    public static List<InsLvDtlModal> getInsLvDtlList(Context mContext) {


        ArrayList<InsLvDtlModal> lGeneral = new ArrayList<InsLvDtlModal>();
        try {
//            int status = VisitorNonGuardHandler.getTableExistORNot(mContext, DBHelper.GENERAL_MASTER_TABLE_NAME);
//            FslLog.d(TAG, " table exist or not " + status);
//            if (status > 0) {
            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getReadableDatabase();

            String query = "SELECT * FROM " + "InspLvlDtl";
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

    private static InsLvDtlModal getData(Cursor cursor) {

        InsLvDtlModal insLvDtlModal = new InsLvDtlModal();


        insLvDtlModal.pRowID = cursor.getString(cursor.getColumnIndex("pRowID"));
        insLvDtlModal.LocID = cursor.getString(cursor.getColumnIndex("LocID"));

        insLvDtlModal.InspHdrID = cursor.getString(cursor.getColumnIndex("InspHdrID"));
        insLvDtlModal.SignDescr = cursor.getString(cursor.getColumnIndex("SignDescr"));
        insLvDtlModal.BatchSize = cursor.getInt(cursor.getColumnIndex("BatchSize"));
        insLvDtlModal.SampleCode = cursor.getString(cursor.getColumnIndex("SampleCode"));

        insLvDtlModal.recDirty = cursor.getInt(cursor.getColumnIndex("recDirty"));
        insLvDtlModal.recEnable = cursor.getInt(cursor.getColumnIndex("recEnable"));
        insLvDtlModal.recUser = cursor.getString(cursor.getColumnIndex("recUser"));

        insLvDtlModal.recAddUser = cursor.getString(cursor.getColumnIndex("recAddUser"));
        insLvDtlModal.recAddDt = cursor.getString(cursor.getColumnIndex("recAddDt"));


        insLvDtlModal.recDt = cursor.getString(cursor.getColumnIndex("recDt"));


        return insLvDtlModal;

    }

    public static List<InsLvDtlModal> getDataAccordingToParticularList(Context mContext, String GenId, String pRowID) {


        DBHelper dbHelper = new DBHelper(mContext);
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        String query = "SELECT * FROM " + "InspLvlDtl"
                + " WHERE " /*+ "GenID" + "='" + GenId + "' AND "*/ + "pRowID" + "='" + pRowID + "'";
        FslLog.d(TAG, "query for  get closed general list  " + query);
        Cursor cursor = database.rawQuery(query, null);

        ArrayList<InsLvDtlModal> lGeneral = new ArrayList<InsLvDtlModal>();

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

        String query = "SELECT " + "InspDescr" + " FROM " + "InspLvlDtl"
                + " where " + "pRowID" + " = '" + pRowID + "'";

        FslLog.d(TAG, " query for complaint name " + query);
        Cursor cursor = db.rawQuery(query, null);
        String MainDescr = null;
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                MainDescr = cursor.getString(cursor.getColumnIndex("InspDescr"));
            }
        }
        cursor.close();
        db.close();

        return MainDescr;
    }


    public static void handlerToInsLvDtlMaster(final Context mContext,
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
           Toast toast= ToastCompat.makeText(mContext, "No Network Connectivity ", Toast.LENGTH_SHORT);
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
        List<InsLvDtlModal> lGeneralModelTypeList = parseInsLvDtl(generalMasterType, result);
        if (mContext != null && lGeneralModelTypeList != null && lGeneralModelTypeList.size() > 0) {
            updateDataBaseOfGeneralMaster(mContext, lGeneralModelTypeList);
        }
    }

    public static void updateDataBaseOfGeneralMaster(Context mContext, List<InsLvDtlModal> lGeneralModelTypeList) {
        if (mContext != null && lGeneralModelTypeList != null && lGeneralModelTypeList.size() > 0) {
            for (int i = 0; i < lGeneralModelTypeList.size(); i++) {
                insertInsLvDtlMaster(mContext, lGeneralModelTypeList.get(i));
            }
        }

    }

    public static List<InsLvDtlModal> parseInsLvDtl(String generalMasterType, JSONObject jsonObject) {

        List<InsLvDtlModal> generalModelList = null;
        if (jsonObject != null) {
            generalModelList = new ArrayList<>();
            JSONArray jsonArray = jsonObject.optJSONArray(generalMasterType);
            if (jsonArray != null && jsonArray.length() > 0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObjectOfComplaint = jsonArray.optJSONObject(i);
                    InsLvDtlModal complaintModel = new InsLvDtlModal();
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

