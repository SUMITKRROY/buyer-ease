package com.sync;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.General.EnclosureModal;
import com.android.volley.VolleyError;
import com.buyereasefsl.ItemInspectionDetailHandler;
import com.constant.AppConfig;
import com.constant.FEnumerations;
import com.constant.JsonKey;
import com.database.DBHelper;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.login.LogInHandler;
import com.login.UserMaster;
import com.util.Device_Info;
import com.util.FslLog;
import com.util.GenUtils;
import com.util.HandleToConnectionEithServer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import me.drakeet.support.toast.ToastCompat;

/**
 * Created by ADMIN on 1/2/2018.
 */

public class GetDataHandler implements JsonKey {
    static String TAG = "GetDataHandler";

    //added by shekhar
    public static void getDefectMasterData(final Context mContext,
                               final GetCallBackResult getCallBackResult) {
        String userId = LogInHandler.getUserId(mContext);
        if (TextUtils.isEmpty(userId)) {
            Toast toast = ToastCompat.makeText(mContext, "Could not find valid user", Toast.LENGTH_SHORT);
            GenUtils.safeToastShow(TAG, mContext, toast);
        }

        String url = AppConfig.URL_DEFECT_MASTER;
        final Map<String, String> params = new HashMap<String, String>();
        params.put("UserID", userId);

        Log.e("DefectMaster","DefectMaster="+url);
        Log.e("DefectMaster","DefectMaster oaram="+new JSONObject(params));
        FslLog.d(TAG, " DefectMaster url " + url);
        FslLog.d(TAG, "DefectMaster param " + new JSONObject(params));
        HandleToConnectionEithServer handleToConnectionEithServer = new HandleToConnectionEithServer();
        handleToConnectionEithServer.setRequest(url,
                new JSONObject(params), new HandleToConnectionEithServer.CallBackResult() {
                    @Override
                    public void onSuccess(JSONObject result) {
                        JSONArray jsonObjectData = result.optJSONArray("Data");
                        if (jsonObjectData != null && jsonObjectData.length() > 0) {
                            JSONObject jsonObject = jsonObjectData.optJSONObject(0);
                            if (jsonObject != null) {
                                JSONArray dataArray = jsonObject.optJSONArray("DefectMaster");
                                if (dataArray != null && dataArray.length() > 0) {
                                    handleDefectMasterData(mContext, dataArray);
                                } else {
                                    Toast toast = ToastCompat.makeText(mContext, "Data not found", Toast.LENGTH_LONG);
                                    GenUtils.safeToastShow(TAG, mContext, toast);
                                }
                            } else {
                                Toast toast = ToastCompat.makeText(mContext, "Data not found", Toast.LENGTH_LONG);
                                GenUtils.safeToastShow(TAG, mContext, toast);
                            }
                        } else {
                            Toast toast = ToastCompat.makeText(mContext, "Data not found", Toast.LENGTH_LONG);
                            GenUtils.safeToastShow(TAG, mContext, toast);
                        }
                        getCallBackResult.onSuccess(null);
                    }

                    @Override
                    public void onError(VolleyError volleyError) {

                        getCallBackResult.onError(volleyError);
                    }
                });

    }

    public static void getData(final Context mContext,
                               final GetCallBackResult getCallBackResult) {

//        String url = "http://192.168.0.224/BuyereaseWebAPI/api/Quality/GetQualityInspection";
        String userId = LogInHandler.getUserId(mContext);
        if (TextUtils.isEmpty(userId)) {
            Toast toast = ToastCompat.makeText(mContext, "Could not find valid user", Toast.LENGTH_SHORT);
            GenUtils.safeToastShow(TAG, mContext, toast);
        }

        String url = AppConfig.URL_GET_DATA;
        String lastSync = getLastGetSyncData(mContext);
        if (TextUtils.isEmpty(lastSync))
            lastSync = "2000-01-01";
        final Map<String, String> params = new HashMap<String, String>();
        params.put("UserID", userId);
        params.put("LastSyncDate", lastSync);
        params.put(KEY_DEVICE_ID, new Device_Info(mContext).getUniqueID());
        params.put(KEY_DEVICE_IP, new Device_Info(mContext).getDeviceIp());
        params.put(KEY_DEVICE_LOCATION, "");
        params.put(KEY_DEVICE_TYPE, "A");

        Log.e("GetDataHandler response",""+url);
        Log.e("GetDataHandler response",""+new JSONObject(params));
        FslLog.d(TAG, " url " + url);
        FslLog.d(TAG, " param " + new JSONObject(params));

        HandleToConnectionEithServer handleToConnectionEithServer = new HandleToConnectionEithServer();
        handleToConnectionEithServer.setRequest(url,
                new JSONObject(params), new HandleToConnectionEithServer.CallBackResult() {
                    @Override
                    public void onSuccess(JSONObject result) {
//                if (isBackGround == FEnumerations.E_SYNC_IN_BACKGROUND) {
//
//                } else {
//                        getCallBackResult.onSuccess(result);
//                }
                        Log.e("GetDataHandler response",""+result);
                        JSONArray dataArray = result.optJSONArray("Data");
                        if (dataArray != null && dataArray.length() > 0) {
                            for (int i = 0; i < dataArray.length(); i++) {
                                JSONObject jsonObject = dataArray.optJSONObject(i);
                                if (jsonObject != null) {
                                    String feedbackTab = "Table" + (jsonObject.length() - 1);
                                    Iterator<String> keys = jsonObject.keys();

                                    while (keys.hasNext()) {
                                        String key = keys.next();
                                        if (key.equals(feedbackTab)) {
                                            JSONArray jsonArrayQRFeedBackHdr = jsonObject.optJSONArray(feedbackTab);
                                            handleQRFeedBackHdr(mContext, jsonArrayQRFeedBackHdr);

                                        } else {
//                                System.out.println("Key :" + key + "  Value :" + jsonObject.get(key));
                                            switch (key) {
                                                case FEnumerations.E_TABLE_QRPOItemHdr:
                                                    JSONArray jsonArrayQRPOItemHdr = jsonObject.optJSONArray(FEnumerations.E_TABLE_QRPOItemHdr);
                                                    handleQRPOItemHdr(mContext, jsonArrayQRPOItemHdr);
                                                    break;
                                                case FEnumerations.E_TABLE_QRPOItemDtl:
                                                    JSONArray jsonArrayQRPOItemDtl = jsonObject.optJSONArray(FEnumerations.E_TABLE_QRPOItemDtl);
                                                    handleQRPOItemDtl(mContext, jsonArrayQRPOItemDtl);
                                                    break;
                                                case FEnumerations.E_TABLE_QRPOItemDtl_Image:
                                                    JSONArray jsonArrayQRPOItemDtl_Image = jsonObject.optJSONArray(FEnumerations.E_TABLE_QRPOItemDtl_Image);
                                                    handleQRPOItemDtl_Image(mContext, jsonArrayQRPOItemDtl_Image);
                                                    break;
                                                case FEnumerations.E_TABLE_QRPOIntimationDetails:
                                                    JSONArray jsonArrayQRPOIntimationDetails = jsonObject.optJSONArray(FEnumerations.E_TABLE_QRPOIntimationDetails);
                                                    handleQRPOIntimationDetails(mContext, jsonArrayQRPOIntimationDetails);
                                                    break;
                                                case FEnumerations.E_TABLE_GenMst:
                                                    JSONArray jsonArrayGenMst = jsonObject.optJSONArray(FEnumerations.E_TABLE_GenMst);
                                                    handleGenMst(mContext, jsonArrayGenMst);
                                                    break;
                                                case FEnumerations.E_TABLE_SysData22:
                                                    JSONArray jsonArraySysData22 = jsonObject.optJSONArray(FEnumerations.E_TABLE_SysData22);
                                                    handleSysData22(mContext, jsonArraySysData22);
                                                    break;
                                                case FEnumerations.E_TABLE_QualityLevel:
                                                    JSONArray jsonArrayQualityLevel = jsonObject.optJSONArray(FEnumerations.E_TABLE_QualityLevel);
                                                    handleQualityLevel(mContext, jsonArrayQualityLevel);
                                                    break;
                                                case FEnumerations.E_TABLE_QualityLevelDtl:
                                                    JSONArray jsonArrayQualityLevelDtl = jsonObject.optJSONArray(FEnumerations.E_TABLE_QualityLevelDtl);
                                                    handleQualityLevelDtl(mContext, jsonArrayQualityLevelDtl);
                                                    break;
                                                case FEnumerations.E_TABLE_InsplvlHdr:
                                                    JSONArray jsonArrayInsplvlHdr = jsonObject.optJSONArray(FEnumerations.E_TABLE_InsplvlHdr);
                                                    handleInsplvlHdr(mContext, jsonArrayInsplvlHdr);
                                                    break;
                                                case FEnumerations.E_TABLE_InspLvlDtl:
                                                    JSONArray jsonArrayInspLvlDtl = jsonObject.optJSONArray(FEnumerations.E_TABLE_InspLvlDtl);
                                                    handleInspLvlDtl(mContext, jsonArrayInspLvlDtl);
                                                    break;
                                                case FEnumerations.E_TABLE_Enclosures:
                                                    JSONArray jsonArrayEnclosures = jsonObject.optJSONArray(FEnumerations.E_TABLE_Enclosures);
                                                    handleEnclosures(mContext, jsonArrayEnclosures);
                                                    break;

                                                case FEnumerations.E_TABLE_QRInspectionHistory:
                                                    JSONArray jsonArrayQRInspectionHistory = jsonObject.optJSONArray(FEnumerations.E_TABLE_QRInspectionHistory);
                                                    handleQRInspectionHistory(mContext, jsonArrayQRInspectionHistory);
                                                    break;

                                                case FEnumerations.E_TABLE_TestReport:
                                                    JSONArray jsonArrayTestReport = jsonObject.optJSONArray(FEnumerations.E_TABLE_TestReport);
                                                    handleTestReport(mContext, jsonArrayTestReport);
                                                    break;

                                                case FEnumerations.E_TABLE_UserMaster_UpdateCriticalAllowed:
                                                    JSONArray jsonArrayUpdateCriticalAllowed = jsonObject.optJSONArray(FEnumerations.E_TABLE_UserMaster_UpdateCriticalAllowed);
                                                    handleUpdateCriticalAllowed(mContext, jsonArrayUpdateCriticalAllowed);
                                                    break;

                                                case FEnumerations.E_TABLE_ItemMeasurement:
                                                    JSONArray jsonArrayItemMeasurement = jsonObject.optJSONArray(FEnumerations.E_TABLE_ItemMeasurement);
                                                    handleItemMeasurement(mContext, jsonArrayItemMeasurement);
                                                    break;

                                                case FEnumerations.E_TABLE_AuditBatchDetails:
                                                    JSONArray jsonArrayAuditBatchDetails = jsonObject.optJSONArray(FEnumerations.E_TABLE_AuditBatchDetails);
                                                    handleAuditBatchDetails(mContext, jsonArrayAuditBatchDetails);
                                                    break;

                                             /*   case feedbackTab://FEnumerations.E_TABLE_QRFeedBackHdr
                                                    JSONArray jsonArrayQRFeedBackHdr = jsonObject.optJSONArray(FEnumerations.E_TABLE_QRFeedBackHdr);
                                                    handleQRFeedBackHdr(mContext, jsonArrayQRFeedBackHdr);
                                                    break;*/

                                            }
                                        }

                                    }
                                    updateLastGetSyncDate(mContext);
                                } else {
                                    Toast toast = ToastCompat.makeText(mContext, "Could not find currect data", Toast.LENGTH_SHORT);
                                    GenUtils.safeToastShow(TAG, mContext, toast);
                                }
                            }
                        } else {
                            Toast toast = ToastCompat.makeText(mContext, "Data not found", Toast.LENGTH_LONG);
                            GenUtils.safeToastShow(TAG, mContext, toast);
                        }


                        getCallBackResult.onSuccess(null);
                    }

                    @Override
                    public void onError(VolleyError volleyError) {
//                        getLoginResult.onError(volleyError);

                        getCallBackResult.onError(volleyError);
                    }
                });


    }

    public static void getStyleData(final Context mContext,
                                    final GetCallBackResult getCallBackResult) {

        String userId = LogInHandler.getUserId(mContext);
        if (TextUtils.isEmpty(userId)) {
            Toast toast = ToastCompat.makeText(mContext, "Could not find valid user", Toast.LENGTH_SHORT);
            GenUtils.safeToastShow(TAG, mContext, toast);
        }

        String url = AppConfig.URL_GET_STYLE_DATA;
       /* String lastSync = getLastGetSyncData(mContext);
        if (TextUtils.isEmpty(lastSync))
            lastSync = "2000-01-01";*/
        final Map<String, String> params = new HashMap<String, String>();
        params.put("UserID", userId);
       /* params.put("LastSyncDate", lastSync);
        params.put(KEY_DEVICE_ID, new Device_Info(mContext).getUniqueID());
        params.put(KEY_DEVICE_IP, new Device_Info(mContext).getDeviceIp());
        params.put(KEY_DEVICE_LOCATION, "");
        params.put(KEY_DEVICE_TYPE, "A");*/

//        FslLog.d(TAG, " url " + url);
        FslLog.d(TAG, " param " + new JSONObject(params));

        HandleToConnectionEithServer handleToConnectionEithServer = new HandleToConnectionEithServer();
        handleToConnectionEithServer.setRequest(url,
                new JSONObject(params), new HandleToConnectionEithServer.CallBackResult() {
                    @Override
                    public void onSuccess(JSONObject result) {
                        JSONArray jsonObjectData = result.optJSONArray("Data");
                        if (jsonObjectData != null && jsonObjectData.length() > 0) {
                            JSONObject jsonObject = jsonObjectData.optJSONObject(0);
                            if (jsonObject != null) {
                                JSONArray dataArray = jsonObject.optJSONArray("StyleList");
                                if (dataArray != null && dataArray.length() > 0) {
                                    handleStyleData(mContext, dataArray);
                                } else {
                                    Toast toast = ToastCompat.makeText(mContext, "Data not found", Toast.LENGTH_LONG);
                                    GenUtils.safeToastShow(TAG, mContext, toast);
                                }
                            } else {
                                Toast toast = ToastCompat.makeText(mContext, "Data not found", Toast.LENGTH_LONG);
                                GenUtils.safeToastShow(TAG, mContext, toast);
                            }
                        } else {
                            Toast toast = ToastCompat.makeText(mContext, "Data not found", Toast.LENGTH_LONG);
                            GenUtils.safeToastShow(TAG, mContext, toast);
                        }
                        getCallBackResult.onSuccess(null);
                    }

                    @Override
                    public void onError(VolleyError volleyError) {

                        getCallBackResult.onError(volleyError);
                    }
                });
    }


    private static void handleQRFeedBackHdr(Context mContext, JSONArray jsonArrayQRFeedBackHdr) {

        if (jsonArrayQRFeedBackHdr != null && jsonArrayQRFeedBackHdr.length() > 0) {
            for (int i = 0; i < jsonArrayQRFeedBackHdr.length(); i++) {
                JSONObject jsonObject = jsonArrayQRFeedBackHdr.optJSONObject(i);
                if (jsonObject != null) {
                    updateOrInsertQRFeedbackhdr(mContext, jsonObject);
                } else {
                    FslLog.d(TAG, " QRFeedBackHdr jsonObject IS NULL");
                }

            }
        }

    }

    private static void handleDefectMasterData(Context mContext, JSONArray jsonArrayDefectMaster) {

        if (jsonArrayDefectMaster != null && jsonArrayDefectMaster.length() > 0) {
            for (int i = 0; i < jsonArrayDefectMaster.length(); i++) {
                JSONObject jsonObject = jsonArrayDefectMaster.optJSONObject(i);
                if (jsonObject != null) {
                    updateOrInsertDefectMasterData(mContext, jsonObject);
                } else {
                    FslLog.d(TAG, " jsonArrayStyle jsonObject IS NULL");
                }

            }
        }

    }

    public static int updateOrInsertDefectMasterData(Context mContext, JSONObject json) {


        ContentValues contentValues = new ContentValues();
        ContentValues updatecontentValues = new ContentValues();
        try {
            Cursor cursor = GenUtils.getColumnExistsInTable(mContext, DBHelper.TB_DEFECT_MASTER_TABLE);
            Iterator<String> keys = json.keys();
            while (keys.hasNext()) {
                String key = keys.next();
//                System.out.println("Key :" + key + "  Value :" + json.get(key));

//                if (GenUtils.columnExistsInTable(mContext, "QRPOItemHdr", key)) {
                if (cursor.getColumnIndex(key) != -1) {
                    contentValues.put(key, String.valueOf(json.get(key)));
                } else {
                    FslLog.e(TAG, " column " + key + " NOT EXIST in QRPOItemHdr");
                }
            }
            if (cursor != null) cursor.close();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        DBHelper dbHelper = new DBHelper(mContext);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        /*int rows = database.update("DefectMaster", updatecontentValues, "pRowID"
                + " = '" + contentValues.getAsString("pRowID") + "'", null);*/
        int mStatus = 0;
//        if (rows == 0) {
            long status = database.insert("DefectMaster", null, contentValues);
            if (status > 0) {
                mStatus = 2;
            }

            FslLog.d(TAG, "insert DefectMaster successfully  ");
            Log.e(TAG, "insert DefectMaster successfully  ");

       /* } else {
            FslLog.d(TAG, "update DefectMaster successfully  ");
            mStatus = 1;
        }*/
        database.close();
        return mStatus;
    }



    private static void handleStyleData(Context mContext, JSONArray jsonArrayStyle) {

        if (jsonArrayStyle != null && jsonArrayStyle.length() > 0) {
            Cursor cursor = GenUtils.getColumnExistsInTable(mContext, DBHelper.TB_STYLE_DETAIL_TABLE);
            for (int i = 0; i < jsonArrayStyle.length(); i++) {
                JSONObject jsonObject = jsonArrayStyle.optJSONObject(i);
                if (jsonObject != null) {
                    updateOrInsertStyleData(mContext, jsonObject, cursor);
                } else {
                    FslLog.d(TAG, " jsonArrayStyle jsonObject IS NULL");
                }

            }
            if (cursor != null) cursor.close();
        }

    }

    private static void handleAuditBatchDetails(Context mContext, JSONArray jsonArrayAuditBatchDetails) {

        if (jsonArrayAuditBatchDetails != null && jsonArrayAuditBatchDetails.length() > 0) {
            for (int i = 0; i < jsonArrayAuditBatchDetails.length(); i++) {
                JSONObject jsonObject = jsonArrayAuditBatchDetails.optJSONObject(i);
                if (jsonObject != null) {
                    updateOrInsertQRAuditBatchDetails(mContext, jsonObject);
                } else {
                    FslLog.d(TAG, " AuditBatchDetails jsonObject IS NULL");
                }

            }
        }
    }

    private static void handleItemMeasurement(Context mContext, JSONArray jsonArrayItemMeasurement) {
        if (jsonArrayItemMeasurement != null && jsonArrayItemMeasurement.length() > 0) {
            for (int i = 0; i < jsonArrayItemMeasurement.length(); i++) {
                JSONObject jsonObject = jsonArrayItemMeasurement.optJSONObject(i);
                if (jsonObject != null) {
                    updateOrInsertQRPOItemDtl_ItemMeasurement(mContext, jsonObject);
                } else {
                    FslLog.d(TAG, " ItemMeasurement jsonObject IS NULL");
                }

            }
        }

    }

    private static void handleUpdateCriticalAllowed(Context mContext, JSONArray jsonArrayUpdateCriticalAllowed) {
        if (jsonArrayUpdateCriticalAllowed != null && jsonArrayUpdateCriticalAllowed.length() > 0) {
            for (int i = 0; i < jsonArrayUpdateCriticalAllowed.length(); i++) {
                JSONObject jsonObject = jsonArrayUpdateCriticalAllowed.optJSONObject(i);
                if (jsonObject != null) {
                    updateOrInsertOnUserMasterData(mContext, jsonObject);
                } else {
                    FslLog.d(TAG, " CriticalAllowed jsonObject IS NULL");
                }

            }
        }

    }

    private static void updateOrInsertOnUserMasterData(Context mContext, JSONObject jsonObject) {

        UserMaster userMaster = new UserMaster();
        userMaster.CriticalDefectsAllowed = jsonObject.optInt("CriticalAllowed");
        userMaster.QualityReportConfig = jsonObject.optString("QualityReportConfig");
        userMaster.CompanyName = jsonObject.optString("Name");
        updateCriticalAllowAndConfig(mContext, userMaster);
    }

    private static void handleTestReport(Context mContext, JSONArray jsonArrayTestReport) {

        if (jsonArrayTestReport != null && jsonArrayTestReport.length() > 0) {
            for (int i = 0; i < jsonArrayTestReport.length(); i++) {
                JSONObject jsonObject = jsonArrayTestReport.optJSONObject(i);
                if (jsonObject != null) {
                    updateOrInsertTestReport(mContext, jsonObject);
                } else {
                    FslLog.d(TAG, " TestReport jsonObject IS NULL");
                }

            }
        }


    }

    private static void handleQRInspectionHistory(Context mContext, JSONArray jsonArrayQRInspectionHistory) {
        if (jsonArrayQRInspectionHistory != null && jsonArrayQRInspectionHistory.length() > 0) {
            for (int i = 0; i < jsonArrayQRInspectionHistory.length(); i++) {
                JSONObject jsonObject = jsonArrayQRInspectionHistory.optJSONObject(i);
                if (jsonObject != null) {
                    updateOrInsertQRInspectionHistory(mContext, jsonObject);
                } else {
                    FslLog.d(TAG, " QRInspectionHistory jsonObject IS NULL");
                }

            }
        }

    }

    private static void handleEnclosures(Context mContext, JSONArray jsonArrayEnclosures) {

        if (jsonArrayEnclosures != null && jsonArrayEnclosures.length() > 0) {
            for (int i = 0; i < jsonArrayEnclosures.length(); i++) {
                JSONObject jsonObject = jsonArrayEnclosures.optJSONObject(i);
                if (jsonObject != null) {
                    updateOrInsertEnclosures(mContext, jsonObject);
                } else {
                    FslLog.d(TAG, " Enclosures jsonObject IS NULL");
                }

            }
        }
    }

    private static void handleInspLvlDtl(Context mContext, JSONArray jsonArrayInspLvlDtl) {

        if (jsonArrayInspLvlDtl != null && jsonArrayInspLvlDtl.length() > 0) {
            for (int i = 0; i < jsonArrayInspLvlDtl.length(); i++) {
                JSONObject jsonObject = jsonArrayInspLvlDtl.optJSONObject(i);
                if (jsonObject != null) {
                    updateOrInsertInspLvlDtl(mContext, jsonObject);
                } else {
                    FslLog.d(TAG, " InspLvlDtl jsonObject IS NULL");
                }

            }
        }
    }

    private static void handleInsplvlHdr(Context mContext, JSONArray jsonArrayInsplvlHdr) {

        if (jsonArrayInsplvlHdr != null && jsonArrayInsplvlHdr.length() > 0) {
            for (int i = 0; i < jsonArrayInsplvlHdr.length(); i++) {
                JSONObject jsonObject = jsonArrayInsplvlHdr.optJSONObject(i);
                if (jsonObject != null) {
                    updateOrInsertInsplvlHdr(mContext, jsonObject);
                } else {
                    FslLog.d(TAG, " InsplvlHdr jsonObject IS NULL");
                }

            }
        }
    }

    private static void handleQualityLevelDtl(Context mContext, JSONArray jsonArrayQualityLevelDtl) {

        if (jsonArrayQualityLevelDtl != null && jsonArrayQualityLevelDtl.length() > 0) {
            for (int i = 0; i < jsonArrayQualityLevelDtl.length(); i++) {
                JSONObject jsonObject = jsonArrayQualityLevelDtl.optJSONObject(i);
                if (jsonObject != null) {
                    updateOrInsertQualityLevelDtl(mContext, jsonObject);
                } else {
                    FslLog.d(TAG, " QualityDtl jsonObject IS NULL");
                }

            }
        }
    }

    private static void handleQualityLevel(Context mContext, JSONArray jsonArrayQualityLevel) {
        if (jsonArrayQualityLevel != null && jsonArrayQualityLevel.length() > 0) {
            for (int i = 0; i < jsonArrayQualityLevel.length(); i++) {
                JSONObject jsonObject = jsonArrayQualityLevel.optJSONObject(i);
                if (jsonObject != null) {
                    updateOrInsertQualityLevel(mContext, jsonObject);
                } else {
                    FslLog.d(TAG, " QualityLevel jsonObject IS NULL");
                }

            }
        }

    }

    private static void handleSysData22(Context mContext, JSONArray jsonArraySysData22) {
        if (jsonArraySysData22 != null && jsonArraySysData22.length() > 0) {
            for (int i = 0; i < jsonArraySysData22.length(); i++) {
                JSONObject jsonObject = jsonArraySysData22.optJSONObject(i);
                if (jsonObject != null) {
                    updateOrInsertSysdata22(mContext, jsonObject);
                } else {
                    FslLog.d(TAG, " Sysdata22 jsonObject IS NULL");
                }

            }
        }

    }

    private static void handleGenMst(Context mContext, JSONArray jsonArrayGenMst) {
        if (jsonArrayGenMst != null && jsonArrayGenMst.length() > 0) {
            for (int i = 0; i < jsonArrayGenMst.length(); i++) {
                JSONObject jsonObject = jsonArrayGenMst.optJSONObject(i);
                if (jsonObject != null) {
                    updateOrInsertGenMst(mContext, jsonObject);
                } else {
                    FslLog.d(TAG, " GenMst jsonObject IS NULL");
                }

            }
        }

    }

    private static void handleQRPOIntimationDetails(Context mContext, JSONArray jsonArrayQRPOIntimationDetails) {

        if (jsonArrayQRPOIntimationDetails != null && jsonArrayQRPOIntimationDetails.length() > 0) {
            for (int i = 0; i < jsonArrayQRPOIntimationDetails.length(); i++) {
                JSONObject jsonObject = jsonArrayQRPOIntimationDetails.optJSONObject(i);
                if (jsonObject != null) {
                    updateOrInsertQRPOIntimationDetails(mContext, jsonObject);
                } else {
                    FslLog.d(TAG, " QRPOIntimationDetails jsonObject IS NULL");
                }

            }
        }
    }

    private static void handleQRPOItemDtl_Image(Context mContext, JSONArray jsonArrayQRPOItemDtl_image) {
        if (jsonArrayQRPOItemDtl_image != null && jsonArrayQRPOItemDtl_image.length() > 0) {
            for (int i = 0; i < jsonArrayQRPOItemDtl_image.length(); i++) {
                JSONObject jsonObject = jsonArrayQRPOItemDtl_image.optJSONObject(i);
                if (jsonObject != null) {
                    updateOrInsertQRPOItemDtl_Image(mContext, jsonObject);
                } else {
                    FslLog.d(TAG, " handleQRPOItemDtl_Image jsonObject IS NULL");
                }

            }
        }
    }

    private static void handleQRPOItemDtl(Context mContext, JSONArray jsonArrayQRPOItemDtl) {
        if (jsonArrayQRPOItemDtl != null && jsonArrayQRPOItemDtl.length() > 0) {
            for (int i = 0; i < jsonArrayQRPOItemDtl.length(); i++) {
                JSONObject jsonObject = jsonArrayQRPOItemDtl.optJSONObject(i);
                if (jsonObject != null) {
                    updateOrInsertPOItemDtl(mContext, jsonObject);
                } else {
                    FslLog.d(TAG, " handleQRPOItemDtl jsonObject IS NULL");
                }

            }
        }
    }

    private static void handleQRPOItemHdr(Context mContext, JSONArray jsonArrayQRPOItemHdr) {
        if (jsonArrayQRPOItemHdr != null && jsonArrayQRPOItemHdr.length() > 0) {
            for (int i = 0; i < jsonArrayQRPOItemHdr.length(); i++) {
                JSONObject jsonObject = jsonArrayQRPOItemHdr.optJSONObject(i);
                if (jsonObject != null) {
                    updateOrInsertPOItemHdr(mContext, jsonObject);
                } else {
                    FslLog.d(TAG, " handleQRPOItemHdr jsonObject IS NULL");
                }

            }
        }
    }


    public static int updateOrInsertUserMaster(Context mContext, JSONObject json) {


        ContentValues contentValues = new ContentValues();
        try {
            Iterator<String> keys = json.keys();
            while (keys.hasNext()) {
                String key = keys.next();
//                System.out.println("Key :" + key + "  Value :" + json.get(key));
                if (GenUtils.columnExistsInTable(mContext, "UserMaster", key)) {
                    contentValues.put(key, String.valueOf(json.get(key)));
                } else {
                    FslLog.e(TAG, " column " + key + " NOT EXIST in UserMaster");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        DBHelper dbHelper = new DBHelper(mContext);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        int rows = -1;
        if(contentValues.getAsString("pUserID")!=null
                && !contentValues.getAsString("pUserID").isEmpty()){
            Log.e("UserMaster","UserMaster updated");
              rows = database.update("UserMaster", contentValues, "pUserID"
                    + " = '" + contentValues.getAsString("pUserID") + "'", null);
        }else {
            Log.e("UserMaster","pUserID empty");
        }

        int mStatus = 0;
        if (rows == 0) {
            long status = database.insert("UserMaster", null, contentValues);
            if (status > 0) {
                mStatus = 2;
            }

            FslLog.d(TAG, "insert UserMaster successfully  ");

        } else {
            FslLog.d(TAG, "update UserMaster successfully  ");
            mStatus = 1;
        }
        database.close();
        return mStatus;
    }

    public static int updateOrInsertPOItemDtl(Context mContext, JSONObject json) {


        ContentValues contentValues = new ContentValues();
        ContentValues updatecontentValues = new ContentValues();
        Cursor cursor = GenUtils.getColumnExistsInTable(mContext, "QRPOItemdtl");
        try {
            Iterator<String> keys = json.keys();
            while (keys.hasNext()) {
                String key = keys.next();
//                System.out.println("Key :" + key + "  Value :" + json.get(key));

                if (cursor.getColumnIndex("RetailPrice") == -1) {
                    FslLog.e(TAG, " not column found " + "RetailPrice" + " so alter execute as REAL");
                    GetDataHandler.handleToAlterAsReal(mContext, "QRPOItemdtl", "RetailPrice");
                }
               /* if (!GenUtils.columnExistsInTable(mContext, "QRPOItemdtl", "RetailPrice")) {
                    FslLog.e(TAG, " not column found " + "RetailPrice" + " so alter execute as REAL");
                    GetDataHandler.handleToAlterAsReal(mContext, "QRPOItemdtl", "RetailPrice");
                }*/

//                if (GenUtils.columnExistsInTable(mContext, "QRPOItemdtl", key)) {
                if (cursor.getColumnIndex(key) != -1) {
                    if (key.equals("LatestDelDt") || key.equals("OrderQty") || key.equals("EarlierInspected")) {
                        updatecontentValues.put(key, String.valueOf(json.get(key)));
                    }
                    if (key.equals("recDt")) {
                        updatecontentValues.put(key, AppConfig.getCurrntDate());
                    }
                    if (key.equals("LatestDelDt")){
                        Log.e("LatestDelDt",String.valueOf(json.get(key)));
                    }

                    contentValues.put(key, String.valueOf(json.get(key)));
                } else {
                    FslLog.e(TAG, " column " + key + " NOT EXIST in QRPOItemdtl");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (cursor != null) cursor.close();
        DBHelper dbHelper = new DBHelper(mContext);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        int rows = database.update("QRPOItemdtl", updatecontentValues, "pRowID"
                + " = '" + contentValues.getAsString("pRowID") + "'", null);
        int mStatus = 0;
        if (rows == 0) {
            long status = database.insert("QRPOItemdtl", null, contentValues);
            if (status > 0) {
                mStatus = 2;
            }

            FslLog.d(TAG, "insert QRPOItemdtl successfully  ");

        } else {
            FslLog.d(TAG, "update QRPOItemdtl successfully  ");
            mStatus = 1;
        }
        database.close();
        return mStatus;
    }

    public static int updateOrInsertPOItemHdr(Context mContext, JSONObject json) {


        ContentValues contentValues = new ContentValues();
        ContentValues updatecontentValues = new ContentValues();
        try {
            Cursor cursor = GenUtils.getColumnExistsInTable(mContext, "QRPOItemHdr");
            Iterator<String> keys = json.keys();
            while (keys.hasNext()) {
                String key = keys.next();
//                System.out.println("Key :" + key + "  Value :" + json.get(key));

//                if (GenUtils.columnExistsInTable(mContext, "QRPOItemHdr", key)) {
                if (cursor.getColumnIndex(key) != -1) {
                    if (key.equals("TestReportStatus") || key.equals("BaseMatDescr")) {
                        updatecontentValues.put(key, String.valueOf(json.get(key)));
                    }
                    contentValues.put(key, String.valueOf(json.get(key)));
                } else {
                    FslLog.e(TAG, " column " + key + " NOT EXIST in QRPOItemHdr");
                }
            }
            if (cursor != null) cursor.close();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        DBHelper dbHelper = new DBHelper(mContext);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        int rows = database.update("QRPOItemHdr", updatecontentValues, "pRowID"
                + " = '" + contentValues.getAsString("pRowID") + "'", null);
        int mStatus = 0;
        if (rows == 0) {
            long status = database.insert("QRPOItemHdr", null, contentValues);
            if (status > 0) {
                mStatus = 2;
            }

            FslLog.d(TAG, "insert QRPOItemHdr successfully  ");

        } else {
            FslLog.d(TAG, "update QRPOItemHdr successfully  ");
            mStatus = 1;
        }
        database.close();
        return mStatus;
    }

    public static int updateOrInsertQRFeedbackhdr(Context mContext, JSONObject json) {

        Cursor cursor = GenUtils.getColumnExistsInTable(mContext, "QRFeedbackhdr");
        if (cursor.getColumnIndex("IsSynced") == -1) {
            FslLog.e(TAG, " not column found " + "IsSynced" + " so alter execute as INTEGER");
            GetDataHandler.handleToAlterAsIn(mContext, "QRFeedbackhdr", "IsSynced");
        }
        /*if (!GenUtils.columnExistsInTable(mContext, "QRFeedbackhdr", "IsSynced")) {
            FslLog.e(TAG, " not column found " + "IsSynced" + " so alter execute as INTEGER");
            GetDataHandler.handleToAlterAsIn(mContext, "QRFeedbackhdr", "IsSynced");
        }*/
        ContentValues updatecontentValues = new ContentValues();
        ContentValues contentValues = new ContentValues();
//        contentValues.put("Last_Sync_Dt", "1900-01-01 00:00:00");
        try {
            Iterator<String> keys = json.keys();
            while (keys.hasNext()) {
                String key = keys.next();
//                System.out.println("Key :" + key + "  Value :" + json.get(key));
//                if (GenUtils.columnExistsInTable(mContext, "QRFeedbackhdr", key)) {
                if (cursor.getColumnIndex(key) != -1) {
                    if (key.equals("InspectionDt") || key.equals("FactoryAddress") || key.equals("Factory")) {
                        updatecontentValues.put(key, String.valueOf(json.get(key)));
                    }
                    if (key.equals("recDt")) {
//                        if (!TextUtils.isEmpty(String.valueOf(json.get(key))) && !String.valueOf(json.get(key)).equals("null")) {
//                            contentValues.put(key, String.valueOf(json.get(key)));
//                        } else {
                        contentValues.put(key, AppConfig.getCurrntDate());
//                        }
                    } else {
                        contentValues.put(key, String.valueOf(json.get(key)));
                    }
                } else {
                    FslLog.e(TAG, " column " + key + " NOT EXIST in QRFeedbackhdr");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (cursor != null) cursor.close();
        DBHelper dbHelper = new DBHelper(mContext);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        int rows = database.update("QRFeedbackhdr", updatecontentValues, "pRowID"
                + " = '" + contentValues.getAsString("pRowID") + "'", null);

        int mStatus = 0;
        if (rows == 0) {
            long status = database.insert("QRFeedbackhdr", null, contentValues);
            if (status > 0) {
                mStatus = 2;
            }

            FslLog.d(TAG, "insert QRFeedbackhdr successfully  ");

        } else {
            FslLog.d(TAG, "update QRFeedbackhdr successfully  ");
            mStatus = 1;
        }
        database.close();
        return mStatus;
    }

    public static int updateOrInsertStyleData(Context mContext, JSONObject json, Cursor cursor) {

        ContentValues contentValues = new ContentValues();
//        contentValues.put("Last_Sync_Dt", "1900-01-01 00:00:00");
        try {
            Iterator<String> keys = json.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                if (cursor.getColumnIndex(key) != -1) {
                    if (key.equals("ItemID")) {
                        contentValues.put(key, String.valueOf(json.get(key)).replace("'", " "));
                        contentValues.put(DBHelper.CL_PROW_ID, String.valueOf(json.get(key)).replace("'", " "));// ItemInspectionDetailHandler.GeneratePK(mContext, DBHelper.TB_STYLE_DETAIL_TABLE)
                    } else {
                        contentValues.put(key, String.valueOf(json.get(key)).replace("'", " "));
                    }
                } else {
                    FslLog.e(TAG, " column " + key + " NOT EXIST in styleDeatil");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        int mStatus = 0;

        DBHelper dbHelper = new DBHelper(mContext);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        String strQuery = "Select * from " + DBHelper.TB_STYLE_DETAIL_TABLE + " where " + DBHelper.CL_ITEM_ID
                + "='" + contentValues.getAsString(DBHelper.CL_ITEM_ID) + "'";
        Cursor checkCursor = database.rawQuery(strQuery, null);
        boolean IsNotAvailableTOSync = false;
        try {

            if (checkCursor != null && checkCursor.getCount() > 0) {
                checkCursor.moveToNext();
                int syncStatusColumnIndex = checkCursor.getColumnIndexOrThrow(DBHelper.CL_SYNC);  // Safer to use getColumnIndexOrThrow
                int status = checkCursor.getInt(syncStatusColumnIndex);
                if (status == 1) {
                    IsNotAvailableTOSync = true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            checkCursor.close();  // Ensure cursor is always closed to avoid memory leaks
        }
        if (!IsNotAvailableTOSync) {
            int rows = database.update(DBHelper.TB_STYLE_DETAIL_TABLE, contentValues,
                    DBHelper.CL_ITEM_ID + " = '" + contentValues.getAsString(DBHelper.CL_ITEM_ID) + "'"
                    , null);
/* + "' and " + DBHelper.CL_CUSTOMER_NAME + " = '" + contentValues.getAsString(DBHelper.CL_CUSTOMER_NAME)
                            + "' and " + DBHelper.CL_DEPARTMENT_NAME + " = '" + contentValues.getAsString(DBHelper.CL_DEPARTMENT_NAME)
                            + "' and " + DBHelper.CL_VENDOR + " = '" + contentValues.getAsString(DBHelper.CL_VENDOR)*/

            if (rows == 0) {
                long status = database.insert(DBHelper.TB_STYLE_DETAIL_TABLE, null, contentValues);
                if (status > 0) {
                    mStatus = 2;
                }

                FslLog.d(TAG, "insert styleDeatil successfully  " + status);

            } else {
                FslLog.d(TAG, "update styleDeatil successfully  " + rows);
                mStatus = 1;
            }
        } else {
            FslLog.d(TAG, "No need to update style bcos already edited HOLOGRAM NO ..... ");
        }
        database.close();

        return mStatus;
    }

    public static int updateOrInsertQRAuditBatchDetails(Context mContext, JSONObject json) {

        DBHelper dbHelper = new DBHelper(mContext);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        try {
            Iterator<String> keys = json.keys();
            while (keys.hasNext()) {
                String key = keys.next();
//                System.out.println("Key :" + key + "  Value :" + json.get(key));
                if (key.equals("pRowID")) {
                    contentValues.put(key, ItemInspectionDetailHandler.GeneratePK(mContext, "QRAuditBatchDetails"));
                    contentValues.put("BE_pRowID", String.valueOf(json.get("pRowID")));
                } else {
                    if (key.equals("SampleRqstCriticalRowID") || key.equals("POItemHdrCriticalRowID")) {

                    } else {
                        contentValues.put(key, String.valueOf(json.get(key)));
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        int rows = database.update("QRAuditBatchDetails", contentValues, "pRowID"
                + " = '" + contentValues.getAsString("pRowID") + "'", null);
        int mStatus = 0;
        if (rows == 0) {
            long status = database.insert("QRAuditBatchDetails", null, contentValues);
            if (status > 0) {
                mStatus = 2;
            }

            FslLog.d(TAG, "insert QRAuditBatchDetails successfully  ");

        } else {
            FslLog.d(TAG, "update QRAuditBatchDetails successfully  ");
            mStatus = 1;
        }
        database.close();
        return mStatus;
    }

    public static int updateOrInsertQREnclosure(Context mContext, JSONObject json) {

        DBHelper dbHelper = new DBHelper(mContext);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        try {
            Iterator<String> keys = json.keys();
            while (keys.hasNext()) {
                String key = keys.next();
//                System.out.println("Key :" + key + "  Value :" + json.get(key));
                contentValues.put(key, String.valueOf(json.get(key)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        int rows = database.update("QREnclosure", contentValues, "pRowID"
                + " = '" + contentValues.getAsString("pRowID") + "'", null);
        int mStatus = 0;
        if (rows == 0) {
            long status = database.insert("QREnclosure", null, contentValues);
            if (status > 0) {
                mStatus = 2;
            }

            FslLog.d(TAG, "insert QREnclosure successfully  ");

        } else {
            FslLog.d(TAG, "update QREnclosure successfully  ");
            mStatus = 1;
        }
        database.close();
        return mStatus;
    }

    public static int updateOrInsertQRInspectionHistory(Context mContext, JSONObject json) {

        DBHelper dbHelper = new DBHelper(mContext);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        try {
            Iterator<String> keys = json.keys();
            while (keys.hasNext()) {
                String key = keys.next();
//                System.out.println("Key :" + key + "  Value :" + json.get(key));
                contentValues.put(key, String.valueOf(json.get(key)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        int rows = database.update("QRInspectionHistory", contentValues, "QRPOItemHdrID"
                + " = '" + contentValues.getAsString("QRPOItemHdrID") + "'"
                + "and  QRHdrID" + " = '" + contentValues.getAsString("QRHdrID")
                + "' and RefQRPOItemHdrID" + " = '" + contentValues.getAsString("RefQRPOItemHdrID") + "'", null);
        int mStatus = 0;
        if (rows == 0) {
            long status = database.insert("QRInspectionHistory", null, contentValues);
            if (status > 0) {
                mStatus = 2;
            }

            FslLog.d(TAG, "insert QRInspectionHistory successfully  ");

        } else {
            FslLog.d(TAG, "update QRInspectionHistory successfully  ");
            mStatus = 1;
        }
        database.close();
        return mStatus;
    }

    public static int updateOrInsertQRPOIntimationDetails(Context mContext, JSONObject json) {

        DBHelper dbHelper = new DBHelper(mContext);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        try {
            Iterator<String> keys = json.keys();
            while (keys.hasNext()) {
                String key = keys.next();
//                System.out.println("Key :" + key + "  Value :" + json.get(key));
                if (key.equals("pRowID")) {
                    contentValues.put(key, ItemInspectionDetailHandler.GeneratePK(mContext, "QRPOIntimationDetails"));
                } else {
                    contentValues.put(key, String.valueOf(json.get(key)));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        int mStatus = 0;
        try {
            int rows = database.update("QRPOIntimationDetails", contentValues, "QRHdrID"
                    + " = '" + contentValues.getAsString("QRHdrID")
                    + "' AND EmailID='" + contentValues.getAsString("EmailID") + "'", null);
            mStatus = 0;
            if (rows == 0) {
                long status = database.insert("QRPOIntimationDetails", null, contentValues);
                if (status > 0) {
                    mStatus = 2;
                }

                FslLog.d(TAG, "insert QRPOIntimationDetails successfully  ");

            } else {
                FslLog.d(TAG, "update QRPOIntimationDetails successfully  ");
                mStatus = 1;
            }
        } catch (Exception e) {
            FslLog.e(TAG, " UNIQUE constraint failed: QRPOIntimationDetails pRowID is duplicate ???????????????????????????????????");
            FirebaseCrashlytics.getInstance().recordException(new Exception("UNIQUE constraint failed: QRPOIntimationDetails pRowID is duplicate ???????????????????????????????????"));
            e.printStackTrace();
        }
        database.close();
        return mStatus;
    }

    public static int updateOrInsertQRPOItemDtl_Image(Context mContext, JSONObject json) {


        ContentValues contentValues = new ContentValues();
        try {
            Cursor cursor = GenUtils.getColumnExistsInTable(mContext, "QRPOItemDtl_Image");
            Iterator<String> keys = json.keys();
            while (keys.hasNext()) {
                String key = keys.next();
//                System.out.println("Key :" + key + "  Value :" + json.get(key));
//                if (GenUtils.columnExistsInTable(mContext, "QRPOItemDtl_Image", key)) {
                if (cursor.getColumnIndex(key) != -1) {
                    if (key.equals("pRowID")) {
                        contentValues.put(key, ItemInspectionDetailHandler.GeneratePK(mContext, "QRPOItemDtl_Image"));
                        contentValues.put("BE_pRowID", String.valueOf(json.get("pRowID")));
                    } else {
                        contentValues.put(key, String.valueOf(json.get(key)));
                    }
//                    contentValues.put(key, String.valueOf(json.get(key)));
                } else {
                    FslLog.e(TAG, " column " + key + " NOT EXIST in QRPOItemDtl_Image");
                }
            }
            if (cursor != null) cursor.close();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        DBHelper dbHelper = new DBHelper(mContext);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        String strQuery = "Select * from QRPOItemDtl_Image where BE_pRowID='" + contentValues.getAsString("BE_pRowID") + "'";
        Cursor cursor = database.rawQuery(strQuery, null);
        int mStatus = 0;
        if (cursor.getCount() < 1) {
            int rows = database.update("QRPOItemDtl_Image", contentValues, "BE_pRowID"
                    + " = '" + contentValues.getAsString("BE_pRowID") + "'", null);

            if (rows == 0) {
                long status = database.insert("QRPOItemDtl_Image", null, contentValues);
                if (status > 0) {
                    mStatus = 2;
                }

                FslLog.d(TAG, "insert QRPOItemDtl_Image successfully  ");

            } else {
                FslLog.d(TAG, "update QRPOItemDtl_Image successfully  ");
                mStatus = 1;
            }
        } else {
            FslLog.d(TAG, "default QRPOItemDtl_Image already available...  ");
        }
        database.close();
        return mStatus;
    }

    public static int updateOrInsertQRPOItemDtl_ItemMeasurement(Context mContext, JSONObject json) {


        ContentValues contentValues = new ContentValues();
        try {
            Cursor cursor = GenUtils.getColumnExistsInTable(mContext, "QRPOItemDtl_ItemMeasurement");
            Iterator<String> keys = json.keys();
            while (keys.hasNext()) {
                String key = keys.next();
//                System.out.println("Key :" + key + "  Value :" + json.get(key));
//                if (GenUtils.columnExistsInTable(mContext, "QRPOItemDtl_ItemMeasurement", key)) {
                if (cursor.getColumnIndex(key) != -1) {
                    if (key.equals("pRowID")) {
                        contentValues.put(key, ItemInspectionDetailHandler.GeneratePK(mContext, "QRPOItemDtl_ItemMeasurement"));
                        contentValues.put("BE_pRowID", String.valueOf(json.get("pRowID")));
                    } else {
                        contentValues.put(key, String.valueOf(json.get(key)));
                    }

                } else {
                    FslLog.e(TAG, " column " + key + " NOT EXIST in QRPOItemDtl_ItemMeasurement");
                }
            }
            if (cursor != null) cursor.close();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        DBHelper dbHelper = new DBHelper(mContext);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        int rows = database.update("QRPOItemDtl_ItemMeasurement", contentValues, "pRowID"
                + " = '" + contentValues.getAsString("pRowID") + "'", null);
        int mStatus = 0;
        if (rows == 0) {
            long status = database.insert("QRPOItemDtl_ItemMeasurement", null, contentValues);
            if (status > 0) {
                mStatus = 2;
            }

            FslLog.d(TAG, "insert QRPOItemDtl_ItemMeasurement successfully  ");

        } else {
            FslLog.d(TAG, "update QRPOItemDtl_ItemMeasurement successfully  ");
            mStatus = 1;
        }
        database.close();
        return mStatus;
    }

    public static int updateOrInsertQRFindings(Context mContext, JSONObject json) {

        DBHelper dbHelper = new DBHelper(mContext);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        try {
            Iterator<String> keys = json.keys();
            while (keys.hasNext()) {
                String key = keys.next();
//                System.out.println("Key :" + key + "  Value :" + json.get(key));
                contentValues.put(key, String.valueOf(json.get(key)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        int rows = database.update("QRFindings", contentValues, "pRowID"
                + " = '" + contentValues.getAsString("pRowID") + "'", null);
        int mStatus = 0;
        if (rows == 0) {
            long status = database.insert("QRFindings", null, contentValues);
            if (status > 0) {
                mStatus = 2;
            }

            FslLog.d(TAG, "insert QRFindings successfully  ");

        } else {
            FslLog.d(TAG, "update QRFindings successfully  ");
            mStatus = 1;
        }
        database.close();
        return mStatus;
    }

    public static int updateOrInsertTestReport(Context mContext, JSONObject json) {


        ContentValues contentValues = new ContentValues();
        try {
            Cursor cursor = GenUtils.getColumnExistsInTable(mContext, "TestReport");
            Iterator<String> keys = json.keys();
            while (keys.hasNext()) {
                String key = keys.next();

//                if (GenUtils.columnExistsInTable(mContext, "TestReport", key)) {
                if (cursor.getColumnIndex(key) != -1) {
                    contentValues.put(key, String.valueOf(json.get(key)));
                } else {
                    if (key.equals("ActivityID")) {
                        contentValues.put("AcitvityID", String.valueOf(json.get(key)));
                    } else {
                        FslLog.e(TAG, " column " + key + " NOT EXIST in TestReport");
                    }
                }
//                }
            }
            if (cursor != null) cursor.close();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        DBHelper dbHelper = new DBHelper(mContext);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        int rows = database.update("TestReport", contentValues, "pRowID"
                + " = '" + contentValues.getAsString("pRowID") + "'", null);
        int mStatus = 0;
        if (rows == 0) {
            long status = database.insert("TestReport", null, contentValues);
            if (status > 0) {
                mStatus = 2;
            }

            FslLog.d(TAG, "insert TestReport successfully  ");

        } else {
            FslLog.d(TAG, "update TestReport successfully  ");
            mStatus = 1;
        }
        database.close();
        return mStatus;
    }

    public static int updateOrInsertQRQualiltyParameterFields(Context mContext, JSONObject json) {

        DBHelper dbHelper = new DBHelper(mContext);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        try {
            Iterator<String> keys = json.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                System.out.println("Key :" + key + "  Value :" + json.get(key));
                contentValues.put(key, String.valueOf(json.get(key)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        int rows = database.update("QRQualiltyParameterFields", contentValues, "pRowID"
                + " = '" + contentValues.getAsString("pRowID") + "'", null);
        int mStatus = 0;
        if (rows == 0) {
            long status = database.insert("QRQualiltyParameterFields", null, contentValues);
            if (status > 0) {
                mStatus = 2;
            }

            FslLog.d(TAG, "insert QRQualiltyParameterFields successfully  ");
            database.close();
        } else {
            FslLog.d(TAG, "update QRQualiltyParameterFields successfully  ");
            mStatus = 1;
        }
        return mStatus;
    }

    public static int updateOrInsertQRProductionStatus(Context mContext, JSONObject json) {

        DBHelper dbHelper = new DBHelper(mContext);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        try {
            Iterator<String> keys = json.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                System.out.println("Key :" + key + "  Value :" + json.get(key));
                contentValues.put(key, String.valueOf(json.get(key)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        int rows = database.update("QRProductionStatus", contentValues, "pRowID"
                + " = '" + contentValues.getAsString("pRowID") + "'", null);
        int mStatus = 0;
        if (rows == 0) {
            long status = database.insert("QRProductionStatus", null, contentValues);
            if (status > 0) {
                mStatus = 2;
            }

            FslLog.d(TAG, "insert QRProductionStatus successfully  ");

        } else {
            FslLog.d(TAG, "update QRProductionStatus successfully  ");
            mStatus = 1;
        }
        database.close();
        return mStatus;
    }

    public static int updateOrInsertQRPOItemFitnessCheck(Context mContext, JSONObject json) {

        DBHelper dbHelper = new DBHelper(mContext);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        try {
            Iterator<String> keys = json.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                System.out.println("Key :" + key + "  Value :" + json.get(key));
                contentValues.put(key, String.valueOf(json.get(key)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        int rows = database.update("QRPOItemFitnessCheck", contentValues, "pRowID"
                + " = '" + contentValues.getAsString("pRowID") + "'", null);
        int mStatus = 0;
        if (rows == 0) {
            long status = database.insert("QRPOItemFitnessCheck", null, contentValues);
            if (status > 0) {
                mStatus = 2;
            }

            FslLog.d(TAG, "insert QRPOItemFitnessCheck successfully  ");

        } else {
            FslLog.d(TAG, "update QRPOItemFitnessCheck successfully  ");
            mStatus = 1;
        }
        database.close();
        return mStatus;
    }


    public static int updateOrInsertSysdata22(Context mContext, JSONObject json) {


        ContentValues contentValues = new ContentValues();
        try {
            Cursor cursor = GenUtils.getColumnExistsInTable(mContext, "Sysdata22");
            Iterator<String> keys = json.keys();
            while (keys.hasNext()) {
                String key = keys.next();
//                System.out.println("Key :" + key + "  Value :" + json.get(key));
//                if (GenUtils.columnExistsInTable(mContext, "Sysdata22", key)) {
                if (cursor.getColumnIndex(key) != -1) {
                    contentValues.put(key, String.valueOf(json.get(key)));
                } else {
                    FslLog.e(TAG, " column " + key + " NOT EXIST in Sysdata22");
                }
            }
            if (cursor != null) cursor.close();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        DBHelper dbHelper = new DBHelper(mContext);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        int rows = database.update("Sysdata22", contentValues, "GenID"
                        + " = '" + contentValues.getAsString("GenID")
                        + "' and MainID" + " = '" + contentValues.getAsString("MainID")
                        + "' and SubID" + " = '" + contentValues.getAsString("SubID") + "'"
                , null);
        int mStatus = 0;
        if (rows == 0) {
            long status = database.insert("Sysdata22", null, contentValues);
            if (status > 0) {
                mStatus = 2;
            }

            FslLog.d(TAG, "insert Sysdata22 successfully  ");

        } else {
            FslLog.d(TAG, "update Sysdata22 successfully  ");
            mStatus = 1;
        }
        database.close();
        return mStatus;
    }

    public static int updateOrInsertGenMst(Context mContext, JSONObject json) {

        DBHelper dbHelper = new DBHelper(mContext);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        try {
            Iterator<String> keys = json.keys();
            while (keys.hasNext()) {
                String key = keys.next();
//                System.out.println("Key :" + key + "  Value :" + json.get(key));
                contentValues.put(key, String.valueOf(json.get(key)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        int rows = database.update("GenMst", contentValues, "pGenRowID"
                + " = '" + contentValues.getAsString("pGenRowID") + "'", null);
        int mStatus = 0;
        if (rows == 0) {
            long status = database.insert("GenMst", null, contentValues);
            if (status > 0) {
                mStatus = 2;
            }

            FslLog.d(TAG, "insert GenMst successfully  ");

        } else {
            FslLog.d(TAG, "update GenMst successfully  ");
            mStatus = 1;
        }
        database.close();
        return mStatus;
    }

    public static int updateOrInsertDefectMaster(Context mContext, JSONObject json) {

        DBHelper dbHelper = new DBHelper(mContext);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        try {
            Iterator<String> keys = json.keys();
            while (keys.hasNext()) {
                String key = keys.next();
//                System.out.println("Key :" + key + "  Value :" + json.get(key));
                contentValues.put(key, String.valueOf(json.get(key)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        int rows = database.update("DefectMaster", contentValues, "pRowID"
                + " = '" + contentValues.getAsString("pRowID") + "'", null);
        int mStatus = 0;
        if (rows == 0) {
            long status = database.insert("DefectMaster", null, contentValues);
            if (status > 0) {
                mStatus = 2;
            }

            FslLog.d(TAG, "insert DefectMaster successfully  ");

        } else {
            FslLog.d(TAG, "update DefectMaster successfully  ");
            mStatus = 1;
        }
        database.close();
        return mStatus;
    }

    public static int updateOrInsertInsplvlHdr(Context mContext, JSONObject json) {


        ContentValues contentValues = new ContentValues();
        try {
            Cursor cursor = GenUtils.getColumnExistsInTable(mContext, "InsplvlHdr");
            Iterator<String> keys = json.keys();
            while (keys.hasNext()) {
                String key = keys.next();
//                System.out.println("Key :" + key + "  Value :" + json.get(key));

//                if (GenUtils.columnExistsInTable(mContext, "InsplvlHdr", key)) {
                if (cursor.getColumnIndex(key) != -1) {
                    contentValues.put(key, String.valueOf(json.get(key)));
                } else {
                    FslLog.e(TAG, " column " + key + " NOT EXIST in InsplvlHdr");
                }
            }
            if (cursor != null) cursor.close();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        DBHelper dbHelper = new DBHelper(mContext);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        int rows = database.update("InsplvlHdr", contentValues, "pRowID"
                + " = '" + contentValues.getAsString("pRowID") + "'", null);
        int mStatus = 0;
        if (rows == 0) {
            long status = database.insert("InsplvlHdr", null, contentValues);
            if (status > 0) {
                mStatus = 2;
            }

            FslLog.d(TAG, "insert InsplvlHdr successfully  ");

        } else {
            FslLog.d(TAG, "update InsplvlHdr successfully  ");
            mStatus = 1;
        }
        database.close();
        return mStatus;
    }

    public static int updateOrInsertInspLvlDtl(Context mContext, JSONObject json) {


        ContentValues contentValues = new ContentValues();
        try {
            Cursor cursor = GenUtils.getColumnExistsInTable(mContext, "InspLvlDtl");
            Iterator<String> keys = json.keys();
            while (keys.hasNext()) {
                String key = keys.next();
//                System.out.println("Key :" + key + "  Value :" + json.get(key));

//                if (GenUtils.columnExistsInTable(mContext, "InspLvlDtl", key)) {
                if (cursor.getColumnIndex(key) != -1) {
                    contentValues.put(key, String.valueOf(json.get(key)));
                } else {
                    FslLog.e(TAG, " column " + key + " NOT EXIST in InspLvlDtl");
                }


            }
            if (cursor != null) cursor.close();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        DBHelper dbHelper = new DBHelper(mContext);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        int rows = database.update("InspLvlDtl", contentValues, "pRowID"
                + " = '" + contentValues.getAsString("pRowID") + "'", null);
        int mStatus = 0;
        if (rows == 0) {
            long status = database.insert("InspLvlDtl", null, contentValues);
            if (status > 0) {
                mStatus = 2;
            }

            FslLog.d(TAG, "insert InspLvlDtl successfully  ");

        } else {
            FslLog.d(TAG, "update InspLvlDtl successfully  ");
            mStatus = 1;
        }
        database.close();
        return mStatus;
    }

    public static int updateOrInsertQualityLevel(Context mContext, JSONObject json) {


        ContentValues contentValues = new ContentValues();
        try {
            Cursor cursor = GenUtils.getColumnExistsInTable(mContext, "QualityLevel");
            Iterator<String> keys = json.keys();
            while (keys.hasNext()) {
                String key = keys.next();
//                System.out.println("Key :" + key + "  Value :" + json.get(key));

//                if (GenUtils.columnExistsInTable(mContext, "QualityLevel", key)) {
                if (cursor.getColumnIndex(key) != -1) {
                    contentValues.put(key, String.valueOf(json.get(key)));
                } else {
                    FslLog.e(TAG, " column " + key + " NOT EXIST in QualityLevel");
                }


            }
            if (cursor != null) cursor.close();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        DBHelper dbHelper = new DBHelper(mContext);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        int rows = database.update("QualityLevel", contentValues, "pRowID"
                + " = '" + contentValues.getAsString("pRowID") + "'", null);
        int mStatus = 0;
        if (rows == 0) {
            long status = database.insert("QualityLevel", null, contentValues);
            if (status > 0) {
                mStatus = 2;
            }

            FslLog.d(TAG, "insert QualityLevel successfully  ");

        } else {
            FslLog.d(TAG, "update QualityLevel successfully  ");
            mStatus = 1;
        }
        database.close();
        return mStatus;
    }

    public static int updateOrInsertQualityLevelDtl(Context mContext, JSONObject json) {


        ContentValues contentValues = new ContentValues();
        try {
            Cursor cursor = GenUtils.getColumnExistsInTable(mContext, "QualityLevelDtl");
            Iterator<String> keys = json.keys();
            while (keys.hasNext()) {
                String key = keys.next();
//                System.out.println("Key :" + key + "  Value :" + json.get(key));

//                if (GenUtils.columnExistsInTable(mContext, "QualityLevelDtl", key)) {
                if (cursor.getColumnIndex(key) != -1) {
                    contentValues.put(key, String.valueOf(json.get(key)));
                } else {
                    FslLog.e(TAG, " column " + key + " NOT EXIST in QualityLevelDtl");
                }


            }
            if (cursor != null) cursor.close();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        DBHelper dbHelper = new DBHelper(mContext);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        int rows = database.update("QualityLevelDtl", contentValues, "pRowID"
                + " = '" + contentValues.getAsString("pRowID") + "'", null);
        int mStatus = 0;
        if (rows == 0) {
            long status = database.insert("QualityLevelDtl", null, contentValues);
            if (status > 0) {
                mStatus = 2;
            }

            FslLog.d(TAG, "insert QualityLevelDtl successfully  ");
            database.close();
        } else {
            FslLog.d(TAG, "update QualityLevelDtl successfully  ");
            mStatus = 1;
        }
        database.close();
        return mStatus;
    }

    public static int updateOrInsertEnclosures(Context mContext, JSONObject json) {


        ContentValues contentValues = new ContentValues();
        Cursor cursor = GenUtils.getColumnExistsInTable(mContext, "Enclosures");
        try {

            Iterator<String> keys = json.keys();
            while (keys.hasNext()) {
                String key = keys.next();
//                System.out.println("Key :" + key + "  Value :" + json.get(key));
//                if (!GenUtils.columnExistsInTable(mContext, "Enclosures", key)) {
                if (cursor.getColumnIndex(key) == -1) {
                    if (key.equalsIgnoreCase("IsImportant")) {
                        FslLog.e(TAG, " not column found " + key + " so alter execute as INTEGER");
                        handleToAlterAsIn(mContext, "Enclosures", key);
                    } else {
                        FslLog.e(TAG, " not column found " + key + " so NEED TO alter  as STRING");
                    }
                }

//                if (GenUtils.columnExistsInTable(mContext, "Enclosures", key)) {
                if (cursor.getColumnIndex(key) != -1) {
                    if (key.equalsIgnoreCase("IsImportant")) {
                        if (String.valueOf(json.get(key)).equalsIgnoreCase("true")) {
                            contentValues.put(key, 1);
                        } else {
                            contentValues.put(key, 0);
                        }
                    } else {
                        contentValues.put(key, String.valueOf(json.get(key)));
                    }
                } else {
                    FslLog.e(TAG, " column " + key + " NOT EXIST in Enclosures");
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
       /* if (!GenUtils.columnExistsInTable(mContext, "Enclosures", "IsImportant")) {
            FslLog.e(TAG, " not column found " + "IsImportant" + " so alter execute as INTEGER");
            GetDataHandler.handleToAlterAsIn(mContext, "Enclosures", "IsImportant");

        }*/
        if (cursor.getColumnIndex("IsImportant") == -1) {
            FslLog.e(TAG, " not column found " + "IsImportant" + " so alter execute as INTEGER");
            GetDataHandler.handleToAlterAsIn(mContext, "Enclosures", "IsImportant");
        }
        if (cursor.getColumnIndex("IsRead") == -1) {
            FslLog.e(TAG, " not column found " + "IsRead" + " so alter execute as INTEGER");
            GetDataHandler.handleToAlterAsIn(mContext, "Enclosures", "IsRead");
        }
       /* if (!GenUtils.columnExistsInTable(mContext, "Enclosures", "IsRead")) {
            FslLog.e(TAG, " not column found " + "IsRead" + " so alter execute as INTEGER");
            GetDataHandler.handleToAlterAsIn(mContext, "Enclosures", "IsRead");

        }*/
        if (cursor != null) cursor.close();
        DBHelper dbHelper = new DBHelper(mContext);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        int rows = database.update("Enclosures", contentValues, "EnclRowID"
                + " = '" + contentValues.getAsString("EnclRowID")
                + "' and (IFNULL(QRPOItemHdrId,'')='" + contentValues.getAsString("QRPOItemHdrID") + "' OR IFNULL(QRPOItemHdrId,'')='')"
                + " and QRHdrID" + " = '" + contentValues.getAsString("QRHdrID") + "'", null);
        int mStatus = 0;
        if (rows == 0) {
            long status = database.insert("Enclosures", null, contentValues);
            if (status > 0) {
                mStatus = 2;
            }

            FslLog.d(TAG, "insert Enclosures successfully  ");

        } else {
            FslLog.d(TAG, "update Enclosures successfully  ");
            mStatus = 1;
        }
        database.close();
        return mStatus;
    }

    public static void handleToAlterAsIn(Context mContext, String tableName, String columnName) {
        DBHelper dbHelper = new DBHelper(mContext);
        SQLiteDatabase dbAlter = dbHelper.getWritableDatabase();
        String addLatColumn = "ALTER TABLE " + tableName + " ADD COLUMN " + columnName + "  INTEGER DEFAULT 0";
        try {
            dbAlter.execSQL(addLatColumn);
        } catch (SQLiteException ex) {
            FslLog.w(TAG, "Altering " + tableName + ": " + ex.getMessage());
        }
    }

    public static void handleToAlterAsReal(Context mContext, String tableName, String columnName) {
        DBHelper dbHelper = new DBHelper(mContext);
        SQLiteDatabase dbAlter = dbHelper.getWritableDatabase();
        String addLatColumn = "ALTER TABLE " + tableName + " ADD COLUMN " + columnName + " REAL";
        try {
            dbAlter.execSQL(addLatColumn);
        } catch (SQLiteException ex) {
            FslLog.w(TAG, "Altering " + tableName + ": " + ex.getMessage());
        }
        dbAlter.close();
    }

    public static long updateCriticalAllowAndConfig(Context mContext, UserMaster userMaster) {

        long status = 0;
        try {
            String userId = LogInHandler.getUserId(mContext);
            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getReadableDatabase();

            ContentValues contentValues = new ContentValues();

//            contentValues.put("pRowID", poItemDtl.QRPOItemHdrID);

            contentValues.put("CriticalDefectsAllowed", userMaster.CriticalDefectsAllowed);
            contentValues.put("CompanyName", userMaster.CompanyName);
            contentValues.put("QualityReportConfig", userMaster.QualityReportConfig);

            int rows = database.update("UserMaster", contentValues, "pUserID"
                    + " = '" + userId + "'", null);
            if (rows == 0) {
                FslLog.d(TAG, "COULD NOT update UserMaster ");
            } else {
                status = 1;
                FslLog.d(TAG, " update UserMaster ");
            }

            database.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    public static long updateLastGetSyncDate(Context mContext) {

        long status = 0;
        try {

            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getReadableDatabase();

            ContentValues contentValues = new ContentValues();
            contentValues.put("Last_Get_Dt", AppConfig.getCurrntDateOnly());
            int rows = database.update("Sync_Info", contentValues, null
                    , null);
            if (rows == 0) {
                FslLog.d(TAG, "COULD NOT update Sync_Info ");
            } else {
                status = 1;
                FslLog.d(TAG, " update Sync_Info ");
            }

            database.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    public static String getLastGetSyncData(Context mContext) {
        DBHelper dbHelper = new DBHelper(mContext);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String query = "SELECT Last_Get_Dt FROM Sync_Info desc LIMIT 1";
        FslLog.d(TAG, " query for get last Dt from SyncInfo " + query);
        Cursor cursor = db.rawQuery(query, null);
        String recDt = "";
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                recDt = cursor.getString(cursor.getColumnIndex("Last_Get_Dt"));
            }
        }
        cursor.close();
        db.close();
        FslLog.d(TAG, " return last of Get sync ....." + recDt);
        return recDt;
    }


    public static interface GetCallBackResult {
        public void onSuccess(JSONObject loginResponse);

        public void onError(VolleyError volleyError);


    }

    public static boolean isInteger(String s) {
        return isInteger(s, 10);
    }

    public static boolean isInteger(String s, int radix) {
        if (s.isEmpty()) return false;
        for (int i = 0; i < s.length(); i++) {
            if (i == 0 && s.charAt(i) == '-') {
                if (s.length() == 1) return false;
                else continue;
            }
            if (Character.digit(s.charAt(i), radix) < 0) return false;
        }
        return true;
    }
}
