package com.General;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.constant.AppConfig;
import com.constant.FEnumerations;
import com.constant.JsonKey;
import com.data.UserSession;
import com.database.DBHelper;
import com.login.UserData;
import com.util.FslLog;
import com.util.HandleToConnectionEithServer;
import com.util.NetworkUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by ADMIN on 10/6/2017.
 */

public class GeneralMasterHandler implements JsonKey {

    static String TAG = "GeneralMasterHandler";

  /*  public static boolean insertGeneralMaster(Context mContext, GeneralModel generalModel) {

        try {
            DBHelper mydb = new DBHelper(mContext);
            SQLiteDatabase db = mydb.getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            contentValues.put("pGenRowID", generalModel.pGenRowID);
            contentValues.put("LocID", generalModel.LocID);
            contentValues.put("GenID", generalModel.GenID);
            contentValues.put("MainID", generalModel.MainID);

            contentValues.put("SubID", generalModel.SubID);
            contentValues.put("Abbrv", generalModel.Abbrv);
            contentValues.put("MainDescr", generalModel.MainDescr);
            contentValues.put("SubDescr", generalModel.SubDescr);
            contentValues.put("numVal1", generalModel.numVal1);
            contentValues.put("numVal2", generalModel.numVal2);
            contentValues.put("numVal3", generalModel.numVal3);
            contentValues.put("numVal4", generalModel.numVal4);
            contentValues.put("numVal5", generalModel.numVal5);

            contentValues.put("numVal6", generalModel.numVal6);
            contentValues.put("numval7", generalModel.numval7);
            contentValues.put("chrVal1", generalModel.chrVal1);
            contentValues.put("chrVal2", generalModel.chrVal2);
            contentValues.put("chrVal3", generalModel.chrVal3);
            contentValues.put("dtVal1", generalModel.dtVal1);
            contentValues.put("ImageName", generalModel.ImageName);
            contentValues.put("ImageExtn", generalModel.ImageExtn);
            contentValues.put("recDirty", generalModel.recDirty);
            contentValues.put("recEnable", generalModel.recEnable);
            contentValues.put("recUser", generalModel.recUser);
            contentValues.put("recAddDt", generalModel.recAddDt);

            contentValues.put("recDt", generalModel.recDt);
            contentValues.put("EDIDt", generalModel.EDIDt);
            contentValues.put("IsPrivilege", generalModel.IsPrivilege);
            contentValues.put("Last_Sync_Dt", generalModel.Last_Sync_Dt);


            int rows = db.update("GenMst", contentValues, "pGenRowID"
                    + " = '" + generalModel.pGenRowID + "'", null);
            if (rows == 0) {
                long result = db.insert("GenMst", null, contentValues);
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
*/

    public static List<GeneralModel> getGeneralList(Context mContext, String GenId) {

        ArrayList<GeneralModel> lGeneral = new ArrayList<GeneralModel>();
        try {
//            int status = VisitorNonGuardHandler.getTableExistORNot(mContext, DBHelper.GENERAL_MASTER_TABLE_NAME);
//            FslLog.d(TAG, " table exist or not " + status);
//            if (status > 0) {
            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getReadableDatabase();

            String query = "SELECT * FROM " + "GenMst"
                    + " WHERE " + "GenID" + "='" + GenId + "'";
            FslLog.d(TAG, "query for  get closed general list  " + query);
            Cursor cursor = database.rawQuery(query, null);


            GeneralModel mGeneralModel;
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

    public static List<GeneralModel> getGeneralAccordingToParticularList(Context mContext, String GenId, String departmentId) {


        DBHelper dbHelper = new DBHelper(mContext);
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        String query = "SELECT * FROM " + "GenMst"
                + " WHERE " + "GenID" + "='" + GenId + "' AND " + "pGenRowID" + "='" + departmentId + "'";
        FslLog.d(TAG, "query for  get closed general list  " + query);
        Cursor cursor = database.rawQuery(query, null);

        ArrayList<GeneralModel> lGeneral = new ArrayList<GeneralModel>();
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

    private static GeneralModel getData(Cursor cursor) {

        GeneralModel generalModel = new GeneralModel();

        generalModel.pGenRowID = cursor.getString(cursor.getColumnIndex("pGenRowID"));
        generalModel.LocID = cursor.getString(cursor.getColumnIndex("LocID"));
        generalModel.GenID = cursor.getString(cursor.getColumnIndex("GenID"));
        generalModel.MainID = cursor.getString(cursor.getColumnIndex("MainID"));

        generalModel.SubID = cursor.getString(cursor.getColumnIndex("SubID"));
        generalModel.Abbrv = cursor.getString(cursor.getColumnIndex("Abbrv"));
        generalModel.MainDescr = cursor.getString(cursor.getColumnIndex("MainDescr"));
        generalModel.SubDescr = cursor.getString(cursor.getColumnIndex("SubDescr"));
        generalModel.numVal1 = cursor.getString(cursor.getColumnIndex("numVal1"));
        generalModel.numVal2 = cursor.getString(cursor.getColumnIndex("numVal2"));
        generalModel.numVal3 = cursor.getString(cursor.getColumnIndex("numVal3"));
        generalModel.numVal4 = cursor.getString(cursor.getColumnIndex("numVal4"));
        generalModel.numVal5 = cursor.getString(cursor.getColumnIndex("numVal5"));

        generalModel.numVal6 = cursor.getString(cursor.getColumnIndex("numVal6"));
        generalModel.numval7 = cursor.getString(cursor.getColumnIndex("numval7"));
        generalModel.chrVal1 = cursor.getString(cursor.getColumnIndex("chrVal1"));
        generalModel.chrVal2 = cursor.getString(cursor.getColumnIndex("chrVal2"));
        generalModel.chrVal3 = cursor.getString(cursor.getColumnIndex("chrVal3"));
        generalModel.dtVal1 = cursor.getString(cursor.getColumnIndex("dtVal1"));
        generalModel.ImageName = cursor.getString(cursor.getColumnIndex("ImageName"));
        generalModel.ImageExtn = cursor.getString(cursor.getColumnIndex("ImageExtn"));
        generalModel.recDirty = cursor.getString(cursor.getColumnIndex("recDirty"));
        generalModel.recEnable = cursor.getString(cursor.getColumnIndex("recEnable"));
        generalModel.recUser = cursor.getString(cursor.getColumnIndex("recUser"));
        generalModel.recAddDt = cursor.getString(cursor.getColumnIndex("recAddDt"));

        generalModel.recDt = cursor.getString(cursor.getColumnIndex("recDt"));
        generalModel.EDIDt = cursor.getString(cursor.getColumnIndex("EDIDt"));
        generalModel.IsPrivilege = cursor.getString(cursor.getColumnIndex("IsPrivilege"));
        generalModel.Last_Sync_Dt = cursor.getString(cursor.getColumnIndex("Last_Sync_Dt"));

        return generalModel;

    }

    public static String getComplaintNatureAccordingId(Context mContext, String pGenRowID) {


        DBHelper dbHelper = new DBHelper(mContext);

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT " + "MainDescr" + " FROM " + "GenMst"
                + " where " + "pGenRowID" + " = '" + pGenRowID + "'";

        FslLog.d(TAG, " query for complaint name " + query);
        Cursor cursor = db.rawQuery(query, null);
        String MainDescr = null;
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                MainDescr = cursor.getString(cursor.getColumnIndex("MainDescr"));
            }
        }
        cursor.close();
        db.close();

        return MainDescr;
    }


   /* public static void handlerToGeneralMaster(final Context mContext,
                                              final int isBackGround,
                                              final String dataFor,
                                              String filter,
                                              final CallBackResult callBackResult) {

        String url = null;
        UserSession userSession = new UserSession(mContext);
        UserData userData = userSession.getLoginData();
        final Map<String, String> params = new HashMap<String, String>();
        params.put(KEY_COMMUNITY, userData.community);
        params.put(KEY_USER_ID, userData.userId);
        params.put(KEY_DATA_FOR, dataFor);
        params.put(KEY_DATA_FILTER, filter);


        FslLog.d(TAG, " master url " + url);
        FslLog.d(TAG, " master  param " + new JSONObject(params));

        if (!NetworkUtil.isNetworkAvailable(mContext)) {
            ToastCompat.makeText(mContext, "No Network Connectivity ", Toast.LENGTH_SHORT).show();
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
        List<GeneralModel> lGeneralModelTypeList = parseComplaintType(generalMasterType, result);
        if (mContext != null && lGeneralModelTypeList != null && lGeneralModelTypeList.size() > 0) {
            updateDataBaseOfGeneralMaster(mContext, lGeneralModelTypeList);
        }
    }

    public static void updateDataBaseOfGeneralMaster(Context mContext, List<GeneralModel> lGeneralModelTypeList) {
        if (mContext != null && lGeneralModelTypeList != null && lGeneralModelTypeList.size() > 0) {
            for (int i = 0; i < lGeneralModelTypeList.size(); i++) {
                insertGeneralMaster(mContext, lGeneralModelTypeList.get(i));
            }
        }

    }*/

    public static List<GeneralModel> parseComplaintType(String generalMasterType, JSONObject jsonObject) {

        List<GeneralModel> generalModelList = null;
        if (jsonObject != null) {
            generalModelList = new ArrayList<>();
            JSONArray jsonArray = jsonObject.optJSONArray(generalMasterType);
            if (jsonArray != null && jsonArray.length() > 0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObjectOfComplaint = jsonArray.optJSONObject(i);
                    GeneralModel complaintModel = new GeneralModel();
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
