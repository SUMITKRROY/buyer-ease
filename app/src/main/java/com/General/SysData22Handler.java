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
 * Created by ADMIN on 10/6/2017.
 */

public class SysData22Handler implements JsonKey {

    static String TAG = "GeneralMasterHandler";

    public static boolean insertSysData22Master(Context mContext, SysData22Modal sysData22Modal) {

        try {
            DBHelper mydb = new DBHelper(mContext);
            SQLiteDatabase db = mydb.getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            contentValues.put("GenID", sysData22Modal.GenID);
            contentValues.put("MainID", sysData22Modal.MainID);

            contentValues.put("SubID", sysData22Modal.SubID);
            contentValues.put("MasterName", sysData22Modal.MasterName);
            contentValues.put("MainDescr", sysData22Modal.MainDescr);
            contentValues.put("SubDescr", sysData22Modal.SubDescr);
            contentValues.put("numVal1", sysData22Modal.numVal1);
            contentValues.put("numVal2", sysData22Modal.numVal2);
            contentValues.put("AddonInfo", sysData22Modal.AddonInfo);
            contentValues.put("MoreInfo", sysData22Modal.MoreInfo);
            contentValues.put("Priviledge", sysData22Modal.Priviledge);

            contentValues.put("a", sysData22Modal.a);
            contentValues.put("ModuleAccess", sysData22Modal.ModuleAccess);
            contentValues.put("ModuleID", sysData22Modal.ModuleID);


            int rows = db.update("Sysdata22", contentValues, "MainID"
                    + " = '" + sysData22Modal.MainID + "'", null);
            if (rows == 0) {
                long result = db.insert("Sysdata22", null, contentValues);
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

    public static List<SysData22Modal> getSysData22ListAccToID(Context mContext, String GenId, String mainID) {


        ArrayList<SysData22Modal> lGeneral = new ArrayList<SysData22Modal>();
        try {
//            int status = VisitorNonGuardHandler.getTableExistORNot(mContext, DBHelper.GENERAL_MASTER_TABLE_NAME);
//            FslLog.d(TAG, " table exist or not " + status);
//            if (status > 0) {
            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getReadableDatabase();

            String query = "SELECT * FROM " + "Sysdata22"
                    + " WHERE " + "GenID" + "='" + GenId + "' and MainID='" + mainID + "' order by mainID";
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

    public static List<SysData22Modal> getSysData22List(Context mContext, String GenId) {


        ArrayList<SysData22Modal> lGeneral = new ArrayList<SysData22Modal>();
        try {
//            int status = VisitorNonGuardHandler.getTableExistORNot(mContext, DBHelper.GENERAL_MASTER_TABLE_NAME);
//            FslLog.d(TAG, " table exist or not " + status);
//            if (status > 0) {
            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getReadableDatabase();

            String query = "SELECT * FROM " + "Sysdata22"
                    + " WHERE " + "GenID" + "='" + GenId + "' order by mainID";
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

    private static SysData22Modal getData(Cursor cursor) {

        SysData22Modal sysData22Modal = new SysData22Modal();


        sysData22Modal.GenID = cursor.getString(cursor.getColumnIndex("GenID"));
        sysData22Modal.MainID = cursor.getString(cursor.getColumnIndex("MainID"));

        sysData22Modal.SubID = cursor.getString(cursor.getColumnIndex("SubID"));
        sysData22Modal.MasterName = cursor.getString(cursor.getColumnIndex("MasterName"));
        sysData22Modal.MainDescr = cursor.getString(cursor.getColumnIndex("MainDescr"));
        sysData22Modal.SubDescr = cursor.getString(cursor.getColumnIndex("SubDescr"));
        sysData22Modal.numVal1 = cursor.getString(cursor.getColumnIndex("numVal1"));
        sysData22Modal.numVal2 = cursor.getString(cursor.getColumnIndex("numVal2"));
        sysData22Modal.AddonInfo = cursor.getString(cursor.getColumnIndex("AddonInfo"));
        sysData22Modal.MoreInfo = cursor.getString(cursor.getColumnIndex("MoreInfo"));
        sysData22Modal.Priviledge = cursor.getString(cursor.getColumnIndex("Priviledge"));

        sysData22Modal.a = cursor.getString(cursor.getColumnIndex("a"));
        sysData22Modal.ModuleAccess = cursor.getString(cursor.getColumnIndex("ModuleAccess"));
        sysData22Modal.ModuleID = cursor.getString(cursor.getColumnIndex("ModuleID"));

        return sysData22Modal;

    }

    public static List<SysData22Modal> getDataAccordingToParticularList(Context mContext, String GenId, String departmentId) {


        DBHelper dbHelper = new DBHelper(mContext);
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        String query = "SELECT * FROM " + "GenMst"
                + " WHERE " + "GenID" + "='" + GenId + "' AND " + "pGenRowID" + "='" + departmentId + "'";
        FslLog.d(TAG, "query for  get closed general list  " + query);
        Cursor cursor = database.rawQuery(query, null);

        ArrayList<SysData22Modal> lGeneral = new ArrayList<SysData22Modal>();

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

    public static String getDataAccordingId(Context mContext, String pGenRowID) {


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


    public static void handlerToSysData22Master(final Context mContext,
                                                final int isBackGround,
                                                final String dataFor,
                                                String filter,
                                                final CallBackResult callBackResult) {

        String url = null;
        UserSession userSession = new UserSession(mContext);
        UserData userData = userSession.getLoginData();
        final Map<String, String> params = new HashMap<String, String>();

        params.put(KEY_USER_ID, userData.userId);

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
        List<SysData22Modal> lGeneralModelTypeList = parseComplaintType(generalMasterType, result);
        if (mContext != null && lGeneralModelTypeList != null && lGeneralModelTypeList.size() > 0) {
            updateDataBaseOfGeneralMaster(mContext, lGeneralModelTypeList);
        }
    }

    public static void updateDataBaseOfGeneralMaster(Context mContext, List<SysData22Modal> lGeneralModelTypeList) {
        if (mContext != null && lGeneralModelTypeList != null && lGeneralModelTypeList.size() > 0) {
            for (int i = 0; i < lGeneralModelTypeList.size(); i++) {
                insertSysData22Master(mContext, lGeneralModelTypeList.get(i));
            }
        }

    }

    public static List<SysData22Modal> parseComplaintType(String generalMasterType, JSONObject jsonObject) {

        List<SysData22Modal> generalModelList = null;
        if (jsonObject != null) {
            generalModelList = new ArrayList<>();
            JSONArray jsonArray = jsonObject.optJSONArray(generalMasterType);
            if (jsonArray != null && jsonArray.length() > 0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObjectOfComplaint = jsonArray.optJSONObject(i);
                    SysData22Modal complaintModel = new SysData22Modal();
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
