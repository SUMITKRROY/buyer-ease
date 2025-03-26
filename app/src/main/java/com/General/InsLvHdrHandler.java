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

public class InsLvHdrHandler implements JsonKey {

    static String TAG = "InsLvHdrHandler";

    public static boolean insertInsLvHdrMaster(Context mContext, InsLvHdrModal insLvHdrModal) {

        try {
            DBHelper mydb = new DBHelper(mContext);
            SQLiteDatabase db = mydb.getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            contentValues.put("pRowID", insLvHdrModal.pRowID);
            contentValues.put("LocID", insLvHdrModal.LocID);

            contentValues.put("InspDescr", insLvHdrModal.InspDescr);
            contentValues.put("InspAbbrv", insLvHdrModal.InspAbbrv);
            contentValues.put("recDirty", insLvHdrModal.recDirty);
            contentValues.put("recEnable", insLvHdrModal.recEnable);
            contentValues.put("recUser", insLvHdrModal.recUser);
            contentValues.put("recDt", insLvHdrModal.recDt);
            contentValues.put("IsDefault", insLvHdrModal.IsDefault);

            int rows = db.update("InsplvlHdr", contentValues, "pRowID"
                    + " = '" + insLvHdrModal.pRowID + "'", null);
            if (rows == 0) {
                long result = db.insert("InsplvlHdr", null, contentValues);
                FslLog.d(TAG, " InsplvlHdr table insert result................" + result);
            } else {
                FslLog.d(TAG, " InsplvlHdr type update result................" + rows);
            }
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
            FslLog.d(TAG, "Exception to inserting InsplvlHdr");
        }


        return true;
    }


    public static List<InsLvHdrModal> getInsLvHdrList(Context mContext) {


        ArrayList<InsLvHdrModal> lGeneral = new ArrayList<InsLvHdrModal>();
        try {
//            int status = VisitorNonGuardHandler.getTableExistORNot(mContext, DBHelper.GENERAL_MASTER_TABLE_NAME);
//            FslLog.d(TAG, " table exist or not " + status);
//            if (status > 0) {
            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getReadableDatabase();

            String query = "SELECT * FROM " + "InsplvlHdr";
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

    private static InsLvHdrModal getData(Cursor cursor) {

        InsLvHdrModal insLvHdrModal = new InsLvHdrModal();


        insLvHdrModal.pRowID = cursor.getString(cursor.getColumnIndex("pRowID"));
        insLvHdrModal.LocID = cursor.getString(cursor.getColumnIndex("LocID"));

        insLvHdrModal.InspDescr = cursor.getString(cursor.getColumnIndex("InspDescr"));
        insLvHdrModal.InspAbbrv = cursor.getString(cursor.getColumnIndex("InspAbbrv"));
        insLvHdrModal.recDirty = cursor.getInt(cursor.getColumnIndex("recDirty"));
        insLvHdrModal.recEnable = cursor.getInt(cursor.getColumnIndex("recEnable"));
        insLvHdrModal.recUser = cursor.getString(cursor.getColumnIndex("recUser"));
        insLvHdrModal.recDt = cursor.getString(cursor.getColumnIndex("recDt"));
        insLvHdrModal.IsDefault = cursor.getInt(cursor.getColumnIndex("IsDefault"));

        return insLvHdrModal;

    }

    public static List<InsLvHdrModal> getDataAccordingToParticularList(Context mContext, String pRowID) {



        DBHelper dbHelper = new DBHelper(mContext);
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        String query = "SELECT * FROM " + "InsplvlHdr"
                + " WHERE " /*+ "GenID" + "='" + GenId + "' AND "*/ + "pRowID" + "='" + pRowID + "'";
        FslLog.d(TAG, "query for  get closed general list  " + query);
        Cursor cursor = database.rawQuery(query, null);

        ArrayList<InsLvHdrModal> lGeneral = new ArrayList<InsLvHdrModal>();

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

        String query = "SELECT " + "MainDescr" + " FROM " + "InsplvlHdr"
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


    public static void handlerToInsLvHdrMaster(final Context mContext,
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
        List<InsLvHdrModal> lGeneralModelTypeList = parseInsLvHdr(generalMasterType, result);
        if (mContext != null && lGeneralModelTypeList != null && lGeneralModelTypeList.size() > 0) {
            updateDataBaseOfGeneralMaster(mContext, lGeneralModelTypeList);
        }
    }

    public static void updateDataBaseOfGeneralMaster(Context mContext, List<InsLvHdrModal> lGeneralModelTypeList) {
        if (mContext != null && lGeneralModelTypeList != null && lGeneralModelTypeList.size() > 0) {
            for (int i = 0; i < lGeneralModelTypeList.size(); i++) {
                insertInsLvHdrMaster(mContext, lGeneralModelTypeList.get(i));
            }
        }

    }

    public static List<InsLvHdrModal> parseInsLvHdr(String generalMasterType, JSONObject jsonObject) {

        List<InsLvHdrModal> generalModelList = null;
        if (jsonObject != null) {
            generalModelList = new ArrayList<>();
            JSONArray jsonArray = jsonObject.optJSONArray(generalMasterType);
            if (jsonArray != null && jsonArray.length() > 0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObjectOfComplaint = jsonArray.optJSONObject(i);
                    InsLvHdrModal complaintModel = new InsLvHdrModal();
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

