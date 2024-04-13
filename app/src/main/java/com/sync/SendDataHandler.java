package com.sync;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.VolleyError;
import com.constant.AppConfig;
import com.constant.FClientConfig;
import com.constant.FEnumerations;
import com.constant.JsonKey;
import com.database.DBHelper;
import com.login.LogInHandler;
import com.util.Device_Info;
import com.util.FslLog;
import com.util.HandleToConnectionEithServer;
import com.util.ImgToStringConverter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ADMIN on 1/2/2018.
 */

public class SendDataHandler implements JsonKey {

    static String TAG = "SendDataHandler";

    public static Map<String, Object> getUserMasterJson(Context mContext) {
        Map<String, Object> params = null;
        try {
            String query = "Select * from UserMaster";
            JSONArray jsonArrayList = getJsonFromTableColumn(mContext, query);
            if (jsonArrayList != null) {
                params = new HashMap<>();
                params.put("Data", jsonArrayList);
            }
            FslLog.d(TAG, " json from user master table " + params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return params;
    }

    public static Map<String, Object> getHdrTableData(Context mContext, List<String> hdrIdList) {
        Map<String, Object> params = null;
        if (hdrIdList != null && hdrIdList.size() > 0) {
            params = new HashMap<>();
            for (int i = 0; i < hdrIdList.size(); i++) {
                params.put("QRFeedbackhdr", getQRFeedbackhdrJson(mContext, hdrIdList.get(i)));
                params.put("QRPOItemHdr", getQRPOItemHdrJson(mContext, hdrIdList.get(i)));
                params.put("QRPOItemDtl", getQRPOItemdtlJson(mContext, hdrIdList.get(i)));
            }


        }


        return params;
    }

    public static Map<String, Object> getImagesTableData(Context mContext, List<String> hdrIdList) {
        Map<String, Object> params = null;
        if (hdrIdList != null && hdrIdList.size() > 0) {
            params = new HashMap<>();
            for (int i = 0; i < hdrIdList.size(); i++) {
                params.put("QRPOItemDtl_Image", getQRPOItemDtl_ImageJson(mContext, hdrIdList.get(i)));
            }
        }
        return params;
    }

    /*public static Map<String, Object> getImagesFileTableData(Context mContext, List<String> hdrIdList) {
        Map<String, Object> params = null;
        if (hdrIdList != null && hdrIdList.size() > 0) {
            params = new HashMap<>();
            for (int i = 0; i < hdrIdList.size(); i++) {
                params.put("list", getImageFilesJson(mContext, hdrIdList.get(i)));
            }
        }
        return params;
    }*/

    public static List<ImageModal> getImagesFileArrayData(Context mContext, List<String> hdrIdList) {
        List<ImageModal> jsonArrays = null;
        if (hdrIdList != null && hdrIdList.size() > 0) {
            for (int i = 0; i < hdrIdList.size(); i++) {
                jsonArrays = SendDataHandler.getImageFilesJson(mContext, hdrIdList.get(i));
            }
        }
        return jsonArrays;
    }

   /* public static Map<String, Object> getEnclosureFileTableData(Context mContext, List<String> hdrIdList) {
        Map<String, Object> params = null;
        if (hdrIdList != null && hdrIdList.size() > 0) {
            params = new HashMap<>();
            for (int i = 0; i < hdrIdList.size(); i++) {
                params.put("list", getFilesQREnclosureJson(mContext, hdrIdList.get(i)));
            }
        }
        return params;
    }*/


    public static JSONArray getEnclosureFileArrayData(Context mContext, List<String> hdrIdList) {
        JSONArray jsonArrays = null;
        if (hdrIdList != null && hdrIdList.size() > 0) {
            for (int i = 0; i < hdrIdList.size(); i++) {
                jsonArrays = getFilesQREnclosureJson(mContext, hdrIdList.get(i));
            }
        }
        return jsonArrays;
    }

    public static Map<String, Object> getWorkmanShipData(Context mContext, List<String> hdrIdList) {

        Map<String, Object> params = null;
        if (hdrIdList != null && hdrIdList.size() > 0) {
            params = new HashMap<>();
            for (int i = 0; i < hdrIdList.size(); i++) {

                params.put("QRAuditBatchDetails", getQRAuditBatchDetailOrWorkManShipsJson(mContext, hdrIdList.get(i)));
            }
        }
        return params;
    }

    public static Map<String, Object> getItemMeasurementData(Context mContext, List<String> hdrIdList) {

        Map<String, Object> params = null;
        if (hdrIdList != null && hdrIdList.size() > 0) {
            params = new HashMap<>();
            for (int i = 0; i < hdrIdList.size(); i++) {
                params.put("QRPOItemDtl_ItemMeasurement", getQRPOItemDtl_ItemMeasurementJson(mContext, hdrIdList.get(i)));
            }
        }
        return params;
    }

    public static Map<String, Object> getFindingData(Context mContext, List<String> hdrIdList) {
        Map<String, Object> params = null;
        if (hdrIdList != null && hdrIdList.size() > 0) {
            params = new HashMap<>();
            for (int i = 0; i < hdrIdList.size(); i++) {
                params.put("QRFindings", getQRFindingsJson(mContext, hdrIdList.get(i)));
            }
        }
        return params;
    }

    public static Map<String, Object> getQualityParameterData(Context mContext, List<String> hdrIdList) {
        Map<String, Object> params = null;
        if (hdrIdList != null && hdrIdList.size() > 0) {
            params = new HashMap<>();
            for (int i = 0; i < hdrIdList.size(); i++) {
                params.put("QRQualiltyParameterFields", getQRQualiltyParameterFields(mContext, hdrIdList.get(i)));
            }
        }
        return params;
    }

    public static Map<String, Object> getFitnessCheckData(Context mContext, List<String> hdrIdList) {
        Map<String, Object> params = null;
        if (hdrIdList != null && hdrIdList.size() > 0) {
            params = new HashMap<>();
            for (int i = 0; i < hdrIdList.size(); i++) {
                params.put("QRPOItemFitnessCheck", getQRPOItemFitnessCheckJson(mContext, hdrIdList.get(i)));
            }
        }
        return params;
    }

    public static Map<String, Object> getProductionStatusData(Context mContext, List<String> hdrIdList) {
        Map<String, Object> params = null;
        if (hdrIdList != null && hdrIdList.size() > 0) {
            params = new HashMap<>();
            for (int i = 0; i < hdrIdList.size(); i++) {
                params.put("QRPOItemFitnessCheck", getQRProductionStatusJson(mContext, hdrIdList.get(i)));
            }
        }
        return params;
    }

    public static Map<String, Object> getIntimationData(Context mContext, List<String> hdrIdList) {
        Map<String, Object> params = null;
        if (hdrIdList != null && hdrIdList.size() > 0) {
            params = new HashMap<>();
            for (int i = 0; i < hdrIdList.size(); i++) {
                params.put("QRPOIntimationDetails", getQRPOIntimationDetailsJson(mContext, hdrIdList.get(i)));
            }
        }
        return params;
    }

    public static Map<String, Object> getQREnclosureData(Context mContext, List<String> hdrIdList) {
        Map<String, Object> params = null;
        if (hdrIdList != null && hdrIdList.size() > 0) {
            params = new HashMap<>();
            for (int i = 0; i < hdrIdList.size(); i++) {
                params.put("QREnclosure", getQREnclosureJson(mContext, hdrIdList.get(i)));
            }
        }
        return params;
    }

    public static Map<String, Object> getDigitalsColumnFromMultipleData(Context mContext, List<String> hdrIdList) {
        Map<String, Object> params = null;
        if (hdrIdList != null && hdrIdList.size() > 0) {
            params = new HashMap<>();
            for (int i = 0; i < hdrIdList.size(); i++) {
                params.put("QRDigitals", getDigitalsColumnFromMultipleJson(mContext, hdrIdList.get(i)));
            }
        }
        return params;
    }
    //need to get barcode data and package appearance master data

    public static Map<String, Object> getOnSiteDataData(Context mContext, List<String> hdrIdList) {
        Map<String, Object> params = null;
        if (hdrIdList != null && hdrIdList.size() > 0) {
            params = new HashMap<>();
            for (int i = 0; i < hdrIdList.size(); i++) {
                params.put("QRPOItemDtl_OnSite_Test", getOnSiteJsonData(mContext, hdrIdList.get(i)));//need to change query
            }
        }
        return params;
    }

    public static Map<String, Object> getSampleCollectedData(Context mContext, List<String> hdrIdList) {
        Map<String, Object> params = null;
        if (hdrIdList != null && hdrIdList.size() > 0) {
            params = new HashMap<>();
            for (int i = 0; i < hdrIdList.size(); i++) {
                params.put("QRPOItemDtl_Sample_Purpose", getSampleCollectedJsonData(mContext, hdrIdList.get(i)));//need to change query
            }
        }
        return params;
    }

    public static Map<String, Object> getPkgAppearanceData(Context mContext, List<String> hdrIdList) {
        Map<String, Object> params = null;
        if (hdrIdList != null && hdrIdList.size() > 0) {
            params = new HashMap<>();
            for (int i = 0; i < hdrIdList.size(); i++) {
                params.put("QRPOItemDtl_PKG_App_Details", getPkgAppJsonData(mContext, hdrIdList.get(i)));//need to change query
            }
        }
        return params;
    }

    public static JSONArray getSampleCollectedJsonData(Context mContext, String hdrID) {

        JSONArray jsonArrayList = null;
        try {
            String query = "SELECT * FROM " + "QRPOItemDtl_Sample_Purpose";
            jsonArrayList = getJsonFromTableColumn(mContext, query);
            FslLog.d(TAG, " json from QRPOItemDtl_Sample_Purpose table " + jsonArrayList);
            Log.e(TAG, " json from QRPOItemDtl_Sample_Purpose table " + jsonArrayList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonArrayList;
    }
    public static JSONArray getOnSiteJsonData(Context mContext, String hdrID) {

        JSONArray jsonArrayList = null;
        try {
            String query = "SELECT * FROM " + "QRPOItemDtl_OnSite_Test";
            jsonArrayList = getJsonFromTableColumn(mContext, query);
            FslLog.d(TAG, " json from QRPOItemDtl_Sample_Purpose table " + jsonArrayList);
            Log.e(TAG, " json from QRPOItemDtl_OnSite_Test table " + jsonArrayList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonArrayList;
    }
    public static JSONArray getPkgAppJsonData(Context mContext, String hdrID) {

        JSONArray jsonArrayList = null;
        try {
            String query = "SELECT * FROM " + "QRPOItemDtl_PKG_App_Details";
            jsonArrayList = getJsonFromTableColumn(mContext, query);
            FslLog.d(TAG, " json from QRPOItemDtl_Sample_Purpose table " + jsonArrayList);
            Log.e(TAG, " json from QRPOItemDtl_PKG_App_Details table " + jsonArrayList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonArrayList;
    }

    public static Map<String, Object> getSelectedInspectionIdsData(Context mContext, List<String> hdrIdList) {
        Map<String, Object> params = null;
        if (hdrIdList != null && hdrIdList.size() > 0) {
            Map<String, Object> paramsIDs = new HashMap<>();
            params = new HashMap<>();
            String ids = "";
            for (int i = 0; i < hdrIdList.size(); i++) {
                if (i == 0) {
                    ids = "'" + hdrIdList.get(i) + "'";
                } else {
                    ids = ids + ", '" + hdrIdList.get(i) + "'";
                }
            }
            paramsIDs.put("pRowIDs", ids);
            paramsIDs.put(KEY_DEVICE_ID, new Device_Info(mContext).getUniqueID());
            paramsIDs.put(KEY_DEVICE_IP, new Device_Info(mContext).getDeviceIp());
            paramsIDs.put(KEY_DEVICE_LOCATION, "");
            paramsIDs.put(KEY_DEVICE_TYPE, "A");

            params.put("Finalize", new JSONArray().put(new JSONObject(paramsIDs)));
        }
        return params;
    }

    public static Map<String, Object> getSelectedStyleIdsData(Context mContext, List<String> hdrIdList) {
        Map<String, Object> params = null;
        if (hdrIdList != null && hdrIdList.size() > 0) {
            Map<String, Object> paramsIDs = new HashMap<>();
            params = new HashMap<>();
            String ids = "";
            for (int i = 0; i < hdrIdList.size(); i++) {
                if (i == 0) {
                    ids = "'" + hdrIdList.get(i) + "'";
                } else {
                    ids = ids + ", '" + hdrIdList.get(i) + "'";
                }
            }
            paramsIDs.put("pRowIDs", ids);
            paramsIDs.put(KEY_DEVICE_ID, new Device_Info(mContext).getUniqueID());
            paramsIDs.put(KEY_DEVICE_IP, new Device_Info(mContext).getDeviceIp());
            paramsIDs.put(KEY_DEVICE_LOCATION, "");
            paramsIDs.put(KEY_DEVICE_TYPE, "A");

            params.put("FinalizeHologram", new JSONArray().put(new JSONObject(paramsIDs)));
        }
        return params;
    }

    public static JSONArray getQRFeedbackhdrJson(Context mContext, String hdrID) {
//        Map<String, Object> params = null;
        JSONArray jsonArrayList = null;
        try {
//            String query = "Select * from QRFeedbackhdr";
            String query = "Select pRowID, QRID, IFNULL(InspectorID,'') AS InspectorID, IFNULL(CriticalDefect,0) AS CriticalDefect," +
                    " IFNULL(MajorDefect,0) AS MajorDefect,  IFNULL(MinorDefect,0) AS MinorDefect, recUser, recDt, Status," +
                    " IFNULL(VendorContact,'NULL') AS VendorContact, IFNULL(Arrivaltime,'NULL') AS Arrivaltime, " +
                    "  IFNULL(Inspstarttime,'NULL') AS Inspstarttime, IFNULL(CompleteTime,'NULL') AS CompleteTime," +
                    " IFNULL(DateTime(AcceptedDt),'NULL') As AcceptedDt,   IFNULL(QLMajor,'null') As QLMajor," +
                    " IFNULL(QLMinor,'null') As QLMinor, IFNULL(Comments,'null') As Comments, IFNULL(InspectionLevel,'null') As InspectionLevel, " +
                    "  IFNULL(InspectionDt,'null') As InspectionDt, IFNULL(ProductionCompletionDt,'NULL') As ProductionCompletionDt," +
                    " IFNULL(ProductionStatusRemark,'NULL') As ProductionStatusRemark " +
                    "  From QRFeedbackhdr " +
                    "Where /*DateTime(recDt) > DateTime(IFNULL(Last_Sync_Dt,'2010-01-01 00:00:00'))  And*/ pRowID in ('" + hdrID + "')";

            jsonArrayList = getJsonFromTableColumn(mContext, query);
//            if (jsonArrayList != null) {
//                params = new HashMap<>();
//                params.put("QRFeedbackhdr", jsonArrayList);
//            }
            FslLog.d(TAG, " json from QRFeedbackhdr table " + jsonArrayList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonArrayList;
    }

    public static JSONArray getQRPOItemHdrJson(Context mContext, String hdrID) {
        JSONArray jsonArrayList = null;
        try {
            String query = "Select * from QRPOItemHdr";//change by shekhar

//            String query = "Select i.pRowID, i.QRHdrID, IFNULL(i.WorkmanShip_QRInspectionLevel,'') AS WorkmanShip_QRInspectionLevel," +
//                    " IFNULL(i.WorkmanShip_QLMajor,'') AS WorkmanShip_QLMajor,  IFNULL(i.WorkmanShip_QLMinor,'') AS WorkmanShip_QLMinor," +
//                    " IFNULL(i.WorkmanShip_SampleSizeID,'') AS WorkmanShip_SampleSizeID,   IFNULL(i.WorkmanShip_InspectionResultID,'')" +
//                    " AS WorkmanShip_InspectionResultID, IFNULL(i.WorkmanShip_Remark,'') AS WorkmanShip_Remark,   i.CriticalDefectPieces , i.MajorDefectPieces, i.MinorDefectPieces, i.CriticalDefect , i.MajorDefect, i.MinorDefect," +
//                    " IFNULL(i.SampleCodeID,'') AS SampleCodeID,   i.CriticalDefectsAllowed , i.MajorDefectsAllowed , i.MinorDefectsAllowed , IFNULL(i.ItemMeASurement_InspectionResultID,'') AS ItemMeASurement_InspectionResultID,  " +
//                    " IFNULL(i.ItemMeASurement_Remark,'') AS ItemMeASurement_Remark, IFNULL(i.DefaultImageRowID,'') AS DefaultImageRowID , i.recUser, " +
//                    "  IFNULL(i.AllowedInspectionQty,0) AS AllowedInspectionQty, IFNULL(i.InspectedQty,0) AS InspectedQty, " +
//                    "IFNULL(i.CartonsPacked,0) AS CartonsPacked,   IFNULL(i.AllowedCartonInspection,0) AS AllowedCartonInspection," +
//                    " IFNULL(i.CartonsInspected,0) AS CartonsInspected,   IFNULL(i.Overall_InspectionResultID,'') As Overall_InspectionResultID,  " +
//                    " i.PKG_Me_InspectionLevelID, i.PKG_Me_InspectionResultID, i.PKG_Me_Remark,   i.PKG_Me_Pallet_SampleSizeID, i.PKG_Me_Pallet_InspectionResultID," +
//                    " i.PKG_Me_Pallet_FindingL, i.PKG_Me_Pallet_FindingB, i.PKG_Me_Pallet_FindingH,   i.PKG_Me_Pallet_FindingWt, i.PKG_Me_Pallet_FindingCBM, i.PKG_Me_Pallet_FindingQty, " +
//                    "i.PKG_Me_Pallet_Digitals,   i.PKG_Me_Master_SampleSizeID, i.PKG_Me_Master_InspectionResultID, i.PKG_Me_Master_FindingL, i.PKG_Me_Master_FindingB, i.PKG_Me_Master_FindingH, " +
//                    "  i.PKG_Me_Master_FindingWt, i.PKG_Me_Master_FindingCBM, i.PKG_Me_Master_FindingQty, i.PKG_Me_Master_Digitals,  i.PKG_Me_Inner_SampleSizeID, i.PKG_Me_Inner_InspectionResultID," +
//                    " i.PKG_Me_Inner_FindingL, i.PKG_Me_Inner_FindingB, i.PKG_Me_Inner_FindingH,   i.PKG_Me_Inner_FindingWt, i.PKG_Me_Inner_FindingCBM, i.PKG_Me_Inner_FindingQty, i.PKG_Me_Inner_Digitals, " +
//                    "  i.PKG_Me_Unit_SampleSizeID, i.PKG_Me_Unit_InspectionResultID, i.PKG_Me_Unit_FindingL, i.PKG_Me_Unit_FindingB, i.PKG_Me_Unit_FindingH,  i.PKG_Me_Unit_FindingWt, i.PKG_Me_Unit_FindingCBM," +
//                    " i.PKG_Me_Unit_Digitals   From QRPOItemHdr i   Inner Join QRFeedbackhdr hdr On hdr.pRowID = i.QRHdrID  Where /*DateTime(i.recDt) > DateTime(IFNULL(hdr.Last_Sync_Dt,'2010-01-01 00:00:00'))   And*/ i.QRHdrID in ('" + hdrID + "')";

            jsonArrayList = getJsonFromTableColumn(mContext, query);
//            if (jsonArrayList != null) {
//                params = new HashMap<>();
//                params.put("QRPOItemHdr", jsonArrayList);
//            }
            FslLog.d(TAG, " json from QRPOItemHdr table " + jsonArrayList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonArrayList;
    }

    public static JSONArray getQRPOItemdtlJson(Context mContext, String hdrID) {
        JSONArray jsonArrayList = null;
        try {
            /*String query = "Select * from QRPOItemdtl";*/

            String query = "Select i.pRowID, i.QRHdrID, IFNULL(i.QRPOItemHdrID,'') AS QRPOItemHdrID, i.FurtherInspectionReqd," +
                    " IFNULL(i.PalletPackedQty,0) AS PalletPackedQty,   IFNULL(i.MASterPackedQty,0) AS MASterPackedQty," +
                    " IFNULL(i.InnerPackedQty,0) AS InnerPackedQty, IFNULL(i.PackedQty,0) AS PackedQty, " +
                    "  IFNULL(i.UnpackedQty,0) AS UnpackedQty, IFNULL(i.UnfinishedQty,0) AS UnfinishedQty, i.CriticalDefectsAllowed," +
                    " i.MajorDefectsAllowed, i.MinorDefectsAllowed,   IFNULL(i.SampleCodeID,'') AS SampleCodeID, i.recUser," +
                    " IFNULL(i.InspectedQty,0) AS InspectedQty, i.recEnable,   IFNULL(i.CartonsPacked,0) AS CartonsPacked," +
                    " IFNULL(i.CartonsInspected,0) AS CartonsInspected, IFNULL(i.AvailableQty,0) AS AvailableQty, " +
                    "  IFNULL(i.AcceptedQty,0) AS AcceptedQty, IFNULL(i.ShortStockQty,0) AS ShortStockQty," +
                    " IFNULL(i.CartonsPacked2,0) AS CartonsPacked2,   IFNULL(i.CriticalDefect,0) As CriticalDefect," +
                    " IFNULL(i.MajorDefect,0) As MajorDefect, IFNULL(i.MinorDefect,0) As MinorDefect,  " +
                    " IFNULL(i.AllowedInspectionQty,0) As AllowedInspectionQty " +
                    "  From QRPOItemDtl i " +
                    " Inner Join QRFeedbackhdr hdr On hdr.pRowID = i.QRHdrID " +
                    " Where /*DateTime(i.recDt) > DateTime(IFNULL(hdr.Last_Sync_Dt,'2010-01-01 00:00:00'))  And*/ i.QRHdrID in ('" + hdrID + "')";

            jsonArrayList = getJsonFromTableColumn(mContext, query);
//            if (jsonArrayList != null) {
//                params = new HashMap<>();
//                params.put("QRPOItemdtl", jsonArrayList);
//            }
            FslLog.d(TAG, " json from QRPOItemdtl table " + jsonArrayList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonArrayList;
    }


    public static JSONArray getQRAuditBatchDetailOrWorkManShipsJson(Context mContext, String hdrID) {
        JSONArray jsonArrayList = null;
        try {


            /*String query = "Select * from QRAuditBatchDetails";*/

            String query = "Select i.pRowID, i.LocID, IFNULL(i.QRHdrID,'') AS QRHdrID, IFNULL(i.QRPOItemHdrID,'') AS QRPOItemHdrID," +
                    " IFNULL(i.QRPOItemDtlRowID,'') AS QRPOItemDtlRowID,   IFNULL(i.ItemID,'') AS ItemID, " +
                    "IFNULL(i.ColorID,'') AS ColorID, IFNULL(i.DefectID,'') AS DefectID, IFNULL(i.DefectCode,'') AS DefectCode, " +
                    " IFNULL(i.DefectName,'') AS DefectName, IFNULL(i.DCLTypeID,'') AS DCLTypeID, IFNULL(i.DefectComments,'')" +
                    " AS DefectComments,   IFNULL(i.DefectDescription,'') AS DefectDescription, i.CriticalDefect, i.MajorDefect," +
                    " i.MinorDefect, IFNULL(i.CriticalType,1) AS CriticalType,   IFNULL(i.MajorType,1) AS MajorType, " +
                    "IFNULL(i.MinorType,1) AS MinorType, IFNULL(i.SampleRqstCriticalRowID,'') AS SampleRqstCriticalRowID, " +
                    "  IFNULL(i.SampleRqstCriticalRowID,'') AS SampleRqstCriticalRowID, IFNULL(i.POItemHdrCriticalRowID,'') " +
                    "AS POItemHdrCriticalRowID,   IFNULL(i.Digitals,'') AS Digitals, i.recAddUser, i.recUser, IFNULL(i.recEnable,1) " +
                    "As recEnable   From QRAuditBatchDetails i  Inner Join QRFeedbackhdr hdr On hdr.pRowID = i.QRHdrID " +
                    " Where /*DateTime(i.recDt) > DateTime(IFNULL(hdr.Last_Sync_Dt,'2010-01-01 00:00:00'))   And*/ i.QRHdrID in ('" + hdrID + "') ";//  And IFNULL(i.BE_pRowID,'') = ''";

            jsonArrayList = getJsonFromTableQRAuditBatchDetailsColumn(mContext, query);
//            if (jsonArrayList != null) {
//                params = new HashMap<>();
//                params.put("Data", jsonArrayList);
//            }
            FslLog.d(TAG, " json from QRAuditBatchDetails table " + jsonArrayList);
            Log.e(TAG, " json from QRAuditBatchDetails table " + jsonArrayList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonArrayList;
    }

    public static JSONArray getQRPOItemDtl_ImageJson(Context mContext, String hdrID) {
        JSONArray jsonArrayList = null;
        try {
            /*String query = "Select * from QRPOItemDtl_Image";*/

            String query = "Select i.pRowID, i.LocID, i.QRHdrID, IFNULL(i.QRPOItemHdrID,'') as QRPOItemHdrID, IFNULL(i.QRPOItemDtlID,'')" +
                    " as QRPOItemDtlID, IFNULL(i.ItemID,'') as ItemID,   IFNULL(i.ColorID,'') as ColorID, IFNULL(i.Title,'')" +
                    " as Title, i.ImageName, i.fileContent, i.ImageExtn, IFNULL(i.ImageSymbol,'') as ImageSymbol, i.ImageSqn, i.recUser, " +
                    "  IFNULL(i.BE_pRowID,'NULL') As BE_pRowID, IFNULL(i.FileSent,0) As FileSent  " +
                    " From QRPOItemDtl_Image i  Inner Join QRFeedbackhdr hdr On hdr.pRowID = i.QRHdrID " +
                    " Where /*DateTime(i.recDt) > DateTime(IFNULL(hdr.Last_Sync_Dt,'2010-01-01 00:00:00'))   And*/ i.QRHdrID in ('" + hdrID + "')   And i.recEnable = 1";

            jsonArrayList = getJsonFromTableColumn(mContext, query);
//            if (jsonArrayList != null) {
//                params = new HashMap<>();
//                params.put("Data", jsonArrayList);
//            }
            FslLog.d(TAG, " json from QRPOItemDtl_Image table " + jsonArrayList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonArrayList;
    }

    public static List<ImageModal> getImageFilesJson(Context mContext, String hdrID) {
        List<ImageModal> jsonArrayList = null;
        try {
            /*String query = "Select * from QRPOItemDtl_Image";*/

            String query = "Select i.pRowID, i.LocID, i.QRHdrID, IFNULL(i.QRPOItemHdrID,'') as QRPOItemHdrID, IFNULL(i.QRPOItemDtlID,'')" +
                    " as QRPOItemDtlID, IFNULL(i.ItemID,'') as ItemID,   IFNULL(i.ColorID,'') as ColorID, IFNULL(i.Title,'')" +
                    " as Title, i.ImageName, i.fileContent, i.ImageExtn, IFNULL(i.ImageSymbol,'') as ImageSymbol, i.ImageSqn, i.recUser, " +
                    "  IFNULL(i.BE_pRowID,'NULL') As BE_pRowID, IFNULL(i.FileSent,0) As FileSent  " +
                    " From QRPOItemDtl_Image i  Inner Join QRFeedbackhdr hdr On hdr.pRowID = i.QRHdrID " +
                    " Where /*DateTime(i.recDt) > DateTime(IFNULL(hdr.Last_Sync_Dt,'2010-01-01 00:00:00'))   And*/ i.QRHdrID in ('" + hdrID + "')   And i.recEnable = 1";

            jsonArrayList = getFileContentFromTableToSendSeparateImageColumn(mContext, query);
//            if (jsonArrayList != null) {
//                params = new HashMap<>();
//                params.put("Data", jsonArrayList);
//            }
            FslLog.d(TAG, " json from QRPOItemDtl_Image table " + jsonArrayList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonArrayList;
    }


    public static JSONArray getQRPOItemDtl_ItemMeasurementJson(Context mContext, String hdrID) {

        JSONArray jsonArrayList = null;
        try {
            /* String query = "Select * from QRPOItemDtl_ItemMeasurement";*/

            String query = "Select i.pRowID, i.LocID, i.QRHdrID, IFNULL(i.QRPOItemHdrID,'') as QRPOItemHdrID," +
                    " IFNULL(i.QRPOItemDtlID,'') as QRPOItemDtlID, IFNULL(i.ItemID,'') as ItemID, " +
                    "  IFNULL(i.ColorID,'') as ColorID, IFNULL(i.ItemMeasurementDescr,'') As ItemMeasurementDescr," +
                    " IFNULL(i.SampleSizeID,'') as SampleSizeID,   IFNULL(i.SampleSizeValue,'') as SampleSizeValue, " +
                    "  IFNULL(i.Finding,'') as Finding, IFNULL(i.InspectionResultID,'') as InspectionResultID, i.recUser, " +
                    "IFNULL(i.Dim_Height,'') as Dim_Height,   IFNULL(i.Dim_Width,'') as Dim_Width, IFNULL(i.Dim_Length,'') as Dim_Length, " +
                    "IFNULL(i.Tolerance_Range,'') as Tolerance_Range, i.Digitals " +
                    "  From QRPOItemDtl_ItemMeasurement i " +
                    " Inner Join QRFeedbackhdr hdr On hdr.pRowID = i.QRHdrID " +
                    " Where /*DateTime(i.recDt) > DateTime(IFNULL(hdr.Last_Sync_Dt,'2010-01-01 00:00:00'))  And*/ i.QRHdrID in ('" + hdrID + "')   And i.recEnable = 1 ";// And IFNULL(i.BE_pRowID,'') = ''";


            jsonArrayList = getJsonFromTableColumn(mContext, query);
//            if (jsonArrayList != null) {
//                params = new HashMap<>();
//                params.put("Data", jsonArrayList);
//            }
            FslLog.d(TAG, " json from QRPOItemDtl_ItemMeasurement table " + jsonArrayList);
            Log.e(TAG, " json from QRPOItemDtl_ItemMeasurement table " + jsonArrayList);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonArrayList;
    }


    public static JSONArray getQRFindingsJson(Context mContext, String hdrID) {
        JSONArray jsonArrayList = null;
        try {
            /*String query = "Select * from QRFindings";*/
            String query = "Select i.pRowID, i.LocID, IFNULL(i.QRHdrID,'') As QRHdrID, IFNULL(i.QRPOItemHdrID,'') as QRPOItemHdrID, IFNULL(i.ItemID,'') as ItemID, " +
                    "IFNULL(i.Descr,'') as Descr,   IFNULL(i.recUser,'') as recUser, IFNULL(i.SampleSizeID,'') as SampleSizeID," +
                    " IFNULL(i.ChangeCount,'') as ChangeCount, IFNULL(i.Old_Height,'') as Old_Height,   IFNULL(i.Old_Width,'') as Old_Width," +
                    " IFNULL(i.Old_Length,'') as Old_Length, IFNULL(i.New_Height,'') as New_Height, IFNULL(i.New_Width,'') as New_Width,  " +
                    " IFNULL(i.New_Length,'') as New_Length, IFNULL(i.MeasurementID,'') As MeasurementID  " +
                    " From QRFindings i  Inner Join QRFeedbackhdr hdr On hdr.pRowID = i.QRHdrID " +
                    " Where i.MeasurementID is not null And /*DateTime(i.recDt) > DateTime(IFNULL(hdr.Last_Sync_Dt,'2010-01-01 00:00:00'))   And*/ i.QRHdrID in ('" + hdrID + "')";

            jsonArrayList = getJsonFromTableColumn(mContext, query);
//            if (jsonArrayList != null) {
//                params = new HashMap<>();
//                params.put("Data", jsonArrayList);
//            }
            FslLog.d(TAG, " json from QRFindings table " + jsonArrayList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonArrayList;
    }


    public static JSONArray getQRQualiltyParameterFields(Context mContext, String hdrID) {
        JSONArray jsonArrayList = null;
        try {
            /*String query = "Select * from QRQualiltyParameterFields";*/

            String query = "Select i.pRowID, i.LocID, i.QRHdrID, IFNULL(i.QRPOItemDtlID,'') as QRPOItemDtlID, IFNULL(i.ItemID,'') as ItemID," +
                    " IFNULL(i.ColorID,'') as ColorID,   i.QualityParameterID, IFNULL(i.IsApplicable,'') as IsApplicable," +
                    " IFNULL(i.OptionSelected,'') as OptionSelected, IFNULL(i.Remarks,'') as Remarks, " +
                    "  i.recAddUser, i.recEnable, i.recDirty, i.recUser, IFNULL(i.QRPOItemHdrID,'') " +
                    "as QRPOItemHdrID, i.Digitals " +
                    "  From QRQualiltyParameterFields i " +
                    " Inner Join QRFeedbackhdr hdr On hdr.pRowID = i.QRHdrID " +
                    " Where /*DateTime(i.recDt) > DateTime(IFNULL(hdr.Last_Sync_Dt,'2010-01-01 00:00:00'))   And*/ i.QRHdrID in ('" + hdrID + "')";

            jsonArrayList = getJsonFromTableColumn(mContext, query);
//            if (jsonArrayList != null) {
//                params = new HashMap<>();
//                params.put("Data", jsonArrayList);
//            }
            FslLog.d(TAG, " json from QRQualiltyParameterFields table " + jsonArrayList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonArrayList;
    }

    public static JSONArray getQRProductionStatusJson(Context mContext, String hdrID) {
        JSONArray jsonArrayList = null;
        try {
            /* String query = "Select * from QRProductionStatus";*/

            String query = "Select i.pRowID, i.LocID, i.QRHdrID, i.QRProdStatusID, i.recEnable, i.recUser, i.Percentage  " +
                    " From QRProductionStatus i   Inner Join QRFeedbackhdr hdr On hdr.pRowID = i.QRHdrID " +
                    "  Where /*DateTime(i.recDt) > DateTime(IFNULL(hdr.Last_Sync_Dt,'2010-01-01 00:00:00'))   And*/ i.QRHdrID in ('" + hdrID + "')";

            jsonArrayList = getJsonFromTableColumn(mContext, query);
//            if (jsonArrayList != null) {
//                params = new HashMap<>();
//                params.put("Data", jsonArrayList);
//            }
            FslLog.d(TAG, " json from QRProductionStatus table " + jsonArrayList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonArrayList;
    }

    public static JSONArray getQRPOItemFitnessCheckJson(Context mContext, String hdrID) {
        JSONArray jsonArrayList = null;
        try {
            /* String query = "Select * from QRPOItemFitnessCheck";*/
            String query = "Select i.pRowID, i.LocID, i.QRHdrID, IFNULL(i.QRPOItemDtlID,'') as QRPOItemDtlID, IFNULL(i.ItemID,'') as ItemID," +
                    " IFNULL(i.ColorID,'') as ColorID,   i.QRFitnessCheckID, IFNULL(i.IsApplicable,'') as IsApplicable, " +
                    "IFNULL(i.OptionSelected,'') as OptionSelected, IFNULL(i.Remarks,'') as Remarks,   i.recAddUser, i.recEnable, i.recDirty, i.recUser," +
                    " IFNULL(i.QRPOItemHdrID,'') as QRPOItemHdrID, i.Digitals " +
                    "  From QRPOItemFitnessCheck i " +
                    " Inner Join QRFeedbackhdr hdr On hdr.pRowID = i.QRHdrID " +
                    " Where /*DateTime(i.recDt) > DateTime(IFNULL(hdr.Last_Sync_Dt,'2010-01-01 00:00:00'))   And*/ i.QRHdrID in ('" + hdrID + "')";
            jsonArrayList = getJsonFromTableColumn(mContext, query);
//            if (jsonArrayList != null) {
//                params = new HashMap<>();
//                params.put("Data", jsonArrayList);
//            }
            FslLog.d(TAG, " json from QRPOItemFitnessCheck table " + jsonArrayList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonArrayList;
    }


    public static JSONArray getQRPOIntimationDetailsJson(Context mContext, String hdrID) {
        JSONArray jsonArrayList = null;
        try {
            /* String query = "Select * from QRPOItemFitnessCheck";*/
            String query = "Select i.pRowID, i.LocID, i.QRHdrID, IFNULL(i.Name,'') as Name," +
                    " IFNULL(i.EmailID,'') as EmailID, IFNULL(i.ID,'') as ID, " +
                    "  i.IsLink, i.IsReport, i.recType, i.recEnable, i.recAddUser, i.recAddDt, i.recUser, i.recDt," +
                    " i.IsHtmlLink, i.IsRcvApplicable, i.BE_pRowID  " +
                    " From QRPOIntimationDetails i " +
                    " Inner Join QRFeedbackhdr hdr On hdr.pRowID = i.QRHdrID " +
                    " Where /*DateTime(i.recDt) > DateTime(IFNULL(hdr.Last_Sync_Dt,'2010-01-01 00:00:00'))  And*/ i.IsSelected = 1  And i.QRHdrID in ('" + hdrID + "')";
            jsonArrayList = getJsonFromTableColumn(mContext, query);
//            if (jsonArrayList != null) {
//                params = new HashMap<>();
//                params.put("Data", jsonArrayList);
//            }
            FslLog.d(TAG, " json from QRPOItemFitnessCheck table " + jsonArrayList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonArrayList;
    }

    public static JSONArray getQREnclosureJson(Context mContext, String hdrID) {
        JSONArray jsonArrayList = null;
        try {
            /* String query = "Select * from QRPOItemFitnessCheck";*/
            String query = "Select i.pRowID, i.LocID, i.IsMandatory, i.ContextID, i.ContextType, i.EnclType, i.ImageName, i.ImageExtn, i.Title," +
                    " i.FileName,  i.recUser, i.recAddDt, i.recDt, i.numVal1, i.ContextID As QRHdrID, " +
                    "IFNULL(i.FileSent,0) As FileSent   From QREnclosure i " +
                    "  Inner Join QRFeedbackhdr hdr On hdr.pRowID = i.ContextID " +
                    " Where /*DateTime(i.recDt) > DateTime(IFNULL(hdr.Last_Sync_Dt,'2010-01-01 00:00:00'))   And*/ i.ContextID in ('" + hdrID + "')";
            jsonArrayList = getJsonFromTableColumn(mContext, query);
//            if (jsonArrayList != null) {
//                params = new HashMap<>();
//                params.put("Data", jsonArrayList);
//            }
            FslLog.d(TAG, " json from QRPOItemFitnessCheck table " + jsonArrayList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonArrayList;
    }


    public static JSONArray getFilesQREnclosureJson(Context mContext, String hdrID) {
        JSONArray jsonArrayList = null;
        try {
            /* String query = "Select * from QRPOItemFitnessCheck";*/
            String query = "Select i.pRowID, i.LocID, i.IsMandatory, i.ContextID, i.ContextType, i.EnclType, i.ImageName, i.ImageExtn, i.Title," +
                    " i.FileName,  i.fileContent, i.recUser, i.recAddDt, i.recDt, i.numVal1, i.ContextID As QRHdrID, " +
                    "IFNULL(i.FileSent,0) As FileSent   From QREnclosure i " +
                    "  Inner Join QRFeedbackhdr hdr On hdr.pRowID = i.ContextID " +
                    " Where /*DateTime(i.recDt) > DateTime(IFNULL(hdr.Last_Sync_Dt,'2010-01-01 00:00:00'))   And*/ i.ContextID in ('" + hdrID + "')";
            jsonArrayList = getJsonEnclosureFileContentFromTableColumn(mContext, query);
//            if (jsonArrayList != null) {
//                params = new HashMap<>();
//                params.put("Data", jsonArrayList);
//            }
            FslLog.d(TAG, " json from QRPOItemFitnessCheck table " + jsonArrayList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonArrayList;
    }

    public static JSONArray getDigitalsColumnFromMultipleJson(Context mContext, String hdrID) {
        JSONArray jsonArrayList = null;
        try {
            /* String query = "Select * from QRPOItemFitnessCheck";*/
            String query = "Select i.pRowID As pRowID, i.Digitals As Digitals, 'OITemp_QRAuditBatchDetails' As [TableName], " +
                    "i.QRHdrID As QRHdrID, i.QRPOItemHdrID As QRPOItemHdrID, 'Digitals' As Col " +
                    "  From QRAuditBatchDetails i  Inner Join QRFeedbackhdr hdr On hdr.pRowID = i.QRHdrID " +
                    " Where /*DateTime(i.recDt) > DateTime(IFNULL(hdr.Last_Sync_Dt,'2010-01-01 00:00:00'))   And*/ i.QRHdrID in ('" + hdrID + "')   And IFNULL(i.BE_pRowID,'') = '' " +
                    "  UNION   Select i.pRowID As pRowID, i.Digitals As Digitals," +
                    " 'OITemp_QRPOItemDtl_ItemMeasurement' As [TableName]," +
                    " i.QRHdrID As QRHdrID, i.QRPOItemHdrID As QRPOItemHdrID, 'Digitals' As Col  " +
                    " From QRPOItemDtl_ItemMeasurement i " +
                    " Inner Join QRFeedbackhdr hdr On hdr.pRowID = i.QRHdrID " +
                    " Where /*DateTime(i.recDt) > DateTime(IFNULL(hdr.Last_Sync_Dt,'2010-01-01 00:00:00'))     And*/ i.QRHdrID in ('" + hdrID + "') And IFNULL(i.BE_pRowID,'') = '' And i.recEnable = 1 " +
                    " UNION   Select i.pRowID As pRowID, i.Digitals As Digitals, 'OITemp_QRQualiltyParameterFields'" +
                    " As [TableName], i.QRHdrID As QRHdrID, i.QRPOItemHdrID As QRPOItemHdrID, 'Digitals' As Col " +
                    "  From QRQualiltyParameterFields i  Inner Join QRFeedbackhdr hdr On hdr.pRowID = i.QRHdrID " +
                    " Where /*DateTime(i.recDt) > DateTime(IFNULL(hdr.Last_Sync_Dt,'2010-01-01 00:00:00'))   And*/ i.QRHdrID in ('" + hdrID + "')    UNION   Select i.pRowID, i.Digitals As Digitals, " +
                    "'OITemp_QRPOItemFitnessCheck' As [TableName], i.QRHdrID As QRHdrID, i.QRPOItemHdrID As QRPOItemHdrID, " +
                    "'Digitals' As Col   From QRPOItemFitnessCheck i  Inner Join QRFeedbackhdr hdr On hdr.pRowID = i.QRHdrID " +
                    " Where /*DateTime(i.recDt) > DateTime(IFNULL(hdr.Last_Sync_Dt,'2010-01-01 00:00:00'))  And*/ i.QRHdrID in ('" + hdrID + "')    UNION   Select '' As pRowID, i.PKG_Me_Pallet_Digitals As Digitals," +
                    " 'OITemp_QRPOItemHdr' As [TableName], i.QRHdrID As QRHdrID, i.pRowID As QRPOItemHdrID," +
                    " 'PKG_Me_Pallet_Digitals' As Col  From QRPOItemHdr i  " +
                    " Inner Join QRFeedbackhdr hdr On hdr.pRowID = i.QRHdrID " +
                    " Where /*DateTime(i.recDt) > DateTime(IFNULL(hdr.Last_Sync_Dt,'2010-01-01 00:00:00'))   And*/ i.QRHdrID in ('" + hdrID + "')    UNION   Select '' As pRowID, i.PKG_Me_Master_Digitals As Digitals," +
                    " 'OITemp_QRPOItemHdr' As [TableName], i.QRHdrID As QRHdrID, i.pRowID As QRPOItemHdrID," +
                    " 'PKG_Me_Master_Digitals' As Col   From QRPOItemHdr i  " +
                    " Inner Join QRFeedbackhdr hdr On hdr.pRowID = i.QRHdrID " +
                    " Where /*DateTime(i.recDt) > DateTime(IFNULL(hdr.Last_Sync_Dt,'2010-01-01 00:00:00'))   And*/ i.QRHdrID in ('" + hdrID + "')   UNION   Select '' As pRowID, i.PKG_Me_Inner_Digitals As Digitals," +
                    " 'OITemp_QRPOItemHdr' As [TableName], i.QRHdrID As QRHdrID, i.pRowID As QRPOItemHdrID, 'PKG_Me_Inner_Digitals' As Col" +
                    "   From QRPOItemHdr i   Inner Join QRFeedbackhdr hdr On hdr.pRowID = i.QRHdrID  Where /*DateTime(i.recDt) > DateTime(IFNULL(hdr.Last_Sync_Dt,'2010-01-01 00:00:00'))   And*/ i.QRHdrID in ('" + hdrID + "')    UNION   Select '' As pRowID, i.PKG_Me_Unit_Digitals As Digitals," +
                    " 'OITemp_QRPOItemHdr' As [TableName], i.QRHdrID As QRHdrID, i.pRowID As QRPOItemHdrID, 'PKG_Me_Unit_Digitals' As Col " +
                    "  From QRPOItemHdr i   Inner Join QRFeedbackhdr hdr On hdr.pRowID = i.QRHdrID " +
                    " Where /*DateTime(i.recDt) > DateTime(IFNULL(hdr.Last_Sync_Dt,'2010-01-01 00:00:00'))   And */ i.QRHdrID in ('" + hdrID + "') ";
            jsonArrayList = getJsonFromTableColumn(mContext, query);
//            if (jsonArrayList != null) {
//                params = new HashMap<>();
//                params.put("Data", jsonArrayList);
//            }
            FslLog.d(TAG, " json from QRPOItemFitnessCheck table " + jsonArrayList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonArrayList;
    }

    /*contentValues.put("recAddUser", userId);
        contentValues.put("recUser", userId);
        contentValues.put("LocID", "DEL");*/


    public static JSONArray getJsonFromTableQRAuditBatchDetailsColumn(Context mContext, String query) {
        JSONArray jsonArrayList = null;
        try {
            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getReadableDatabase();

            FslLog.d(TAG, " query to pass  " + query);
            Cursor cursor = database.rawQuery(query, null);

            if (cursor.getCount() > 0) {
                jsonArrayList = new JSONArray();

                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToNext();

                    Map<String, Object> jsonObj = new HashMap<String, Object>();
                    for (int j = 0; j < cursor.getColumnCount(); j++) {
                        if(cursor.getColumnName(j).equals("recUser")){
                            if(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(j)))!=null ){
                                jsonObj.put(cursor.getColumnName(j), LogInHandler.getUserId(mContext));
                            }else {
                                jsonObj.put(cursor.getColumnName(j), cursor.getString(cursor.getColumnIndex(cursor.getColumnName(j))));
                            }
                        }else if(cursor.getColumnName(j).equals("recAddUser")){
                            if(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(j)))!=null ){
                                jsonObj.put(cursor.getColumnName(j), LogInHandler.getUserId(mContext));
                            }else {
                                jsonObj.put(cursor.getColumnName(j), cursor.getString(cursor.getColumnIndex(cursor.getColumnName(j))));
                            }
                        }else if(cursor.getColumnName(j).equals("LocID")){
                            if(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(j)))!=null ){
                                jsonObj.put(cursor.getColumnName(j), FClientConfig.LOC_ID);
                            }else {
                                jsonObj.put(cursor.getColumnName(j), cursor.getString(cursor.getColumnIndex(cursor.getColumnName(j))));
                            }
                        } else {
                            if(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(j)))!=null ){
                                jsonObj.put(cursor.getColumnName(j), cursor.getString(cursor.getColumnIndex(cursor.getColumnName(j))));
                            }else {
                                jsonObj.put(cursor.getColumnName(j), "");
                            }
                        }
                    }
                    jsonArrayList.put(new JSONObject(jsonObj));
                }
            }
            cursor.close();
            database.close();
            FslLog.d(TAG, " json array from table " + jsonArrayList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonArrayList;
    }
    public static JSONArray getJsonFromTableColumn(Context mContext, String query) {
        JSONArray jsonArrayList = null;
        try {
            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getReadableDatabase();

            FslLog.d(TAG, " query to pass  " + query);
            Cursor cursor = database.rawQuery(query, null);

            if (cursor.getCount() > 0) {
                jsonArrayList = new JSONArray();

                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToNext();

                    Map<String, Object> jsonObj = new HashMap<String, Object>();
                    for (int j = 0; j < cursor.getColumnCount(); j++) {

                        if (cursor.getColumnName(j).equals("fileContent")) {
                            //No need to file content

                        }
                        if (cursor.getColumnName(j).equals("ItemID")) {
                            //No need to file content
                            jsonObj.put(cursor.getColumnName(j), "");
                        } else if (cursor.getColumnName(j).equals("ImageSqn")) {
                            //No need to file content
                            jsonObj.put(cursor.getColumnName(j), (i + 1));
                        } else if (cursor.getColumnName(j).equals("ImageExtn")) {
                            //No need to file content
                            String fileExt = cursor.getString(cursor.getColumnIndex(cursor.getColumnName(j)));
                            if (TextUtils.isEmpty(fileExt) || fileExt.equals("null")) {
                                jsonObj.put(cursor.getColumnName(j), FEnumerations.E_IMAGE_EXTN);
                            } else {
                                if(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(j)))!=null /*||
                                        !cursor.getString(cursor.getColumnIndex(cursor.getColumnName(j))).equals("null")*/){
                                    jsonObj.put(cursor.getColumnName(j), cursor.getString(cursor.getColumnIndex(cursor.getColumnName(j))));
                                }else {
                                    jsonObj.put(cursor.getColumnName(j), "");
                                }
                            }
                        } else {
                            if(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(j)))!=null /*||
                                    !cursor.getString(cursor.getColumnIndex(cursor.getColumnName(j))).equals("null")*/){
                                jsonObj.put(cursor.getColumnName(j), cursor.getString(cursor.getColumnIndex(cursor.getColumnName(j))));
                            }else {
                                jsonObj.put(cursor.getColumnName(j), "");
                            }
                        }
                    }
                    jsonArrayList.put(new JSONObject(jsonObj));
                }
            }
            cursor.close();
            database.close();
            FslLog.d(TAG, " json array from table " + jsonArrayList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonArrayList;
    }

    /*public static JSONArray getJsonFileContentFromTableToSendSeparateImageColumn(Context mContext, String query) {
        JSONArray jsonArrayList = null;
        try {
            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getReadableDatabase();

            FslLog.d(TAG, " query to pass  " + query);
            Cursor cursor = database.rawQuery(query, null);

            if (cursor.getCount() > 0) {
                jsonArrayList = new JSONArray();

                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToNext();


//                    for (int j = 0; j < cursor.getColumnCount(); j++) {
//                    jsonObj.put("FileName", cursor.getString(cursor.getColumnIndex("ImageName")));

                    int sentFile = cursor.getInt(cursor.getColumnIndex("FileSent"));
                    if (sentFile == 0) {
                        String _file = cursor.getString(cursor.getColumnIndex("fileContent"));
                        if (!TextUtils.isEmpty(_file)
                                && !_file.equals("null")) {
                            Map<String, Object> jsonObj = new HashMap<String, Object>();
                            jsonObj.put("pRowID", cursor.getString(cursor.getColumnIndex("pRowID")));
                            jsonObj.put("QRHdrID", cursor.getString(cursor.getColumnIndex("QRHdrID")));
                            jsonObj.put("QRPOItemHdrID", cursor.getString(cursor.getColumnIndex("QRPOItemHdrID")));
                            jsonObj.put("Length", "");

                            String fileExtn = FEnumerations.E_IMAGE_EXTN;
                            String _fileEx = cursor.getString(cursor.getColumnIndex("ImageExtn"));
                            if (!TextUtils.isEmpty(_fileEx) && !_fileEx.equals("null")) {
                                fileExtn = _fileEx;
                            }

                            String file = cursor.getString(cursor.getColumnIndex("QRHdrID")) + "_"
                                    + cursor.getString(cursor.getColumnIndex("pRowID")) + "." + fileExtn;
                            jsonObj.put("FileName", file);
//                    String file = cursor.getString(cursor.getColumnIndex("fileContent"));
//                    if (!TextUtils.isEmpty(file) && !file.equals("null")) {
//                        byte[] decodedBytes = Base64.decode(file, 1);
//                        jsonObj.put("fileContent", decodedBytes);
//                    } else {


//                            String base64 = ImgToStringConverter.encodeFileToBase64Binary(_file);
//                        byte[] compressed = GzipUtil.compress(base64);
//                        String result = Base64.encodeToString(compressed, Base64.DEFAULT);

                            jsonObj.put("fileContent", _file);

                            jsonArrayList.put(new JSONObject(jsonObj));
                        } else {
                            FslLog.d(TAG, " FILE CONTENT IS NULL......................???");
                        }

                    } else {
                        FslLog.d(TAG, " FILE ALREADY SYNC ..................************");
                    }


                }
            }
            cursor.close();
            database.close();
            FslLog.d(TAG, " json array from table " + jsonArrayList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonArrayList;
    }*/

    public static List<ImageModal> getFileContentFromTableToSendSeparateImageColumn(Context mContext, String query) {

        List<ImageModal> imageModals = null;
        try {
            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getReadableDatabase();

            FslLog.d(TAG, " query to pass  " + query);
            Cursor cursor = database.rawQuery(query, null);

            if (cursor.getCount() > 0) {
                imageModals = new ArrayList<>();

                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToNext();


//                    for (int j = 0; j < cursor.getColumnCount(); j++) {
//                    jsonObj.put("FileName", cursor.getString(cursor.getColumnIndex("ImageName")));

                    int sentFile = cursor.getInt(cursor.getColumnIndex("FileSent"));
                    if (sentFile == 0) {
                        String _file = cursor.getString(cursor.getColumnIndex("fileContent"));


                        if (!TextUtils.isEmpty(_file)
                                && !_file.equals("null")) {
                            Uri uri = Uri.parse(_file);
                            File testFile = new File(uri.getPath());
                            if (testFile.exists()) {
//                            Map<String, Object> jsonObj = new HashMap<String, Object>();
                                ImageModal
                                        imageModal = new ImageModal();
                                imageModal.pRowID = cursor.getString(cursor.getColumnIndex("pRowID"));
                                imageModal.QRHdrID = cursor.getString(cursor.getColumnIndex("QRHdrID"));
                                imageModal.QRPOItemHdrID = cursor.getString(cursor.getColumnIndex("QRPOItemHdrID"));
                                imageModal.Length = "";

                                String fileExtn = FEnumerations.E_IMAGE_EXTN;
                                String _fileEx = cursor.getString(cursor.getColumnIndex("ImageExtn"));
                                if (!TextUtils.isEmpty(_fileEx) && !_fileEx.equals("null")) {
                                    fileExtn = _fileEx;
                                }

                                String file = cursor.getString(cursor.getColumnIndex("QRHdrID")) + "_"
                                        + cursor.getString(cursor.getColumnIndex("pRowID")) + "." + fileExtn;
                                imageModal.FileName = file;
//                    String file = cursor.getString(cursor.getColumnIndex("fileContent"));
//                    if (!TextUtils.isEmpty(file) && !file.equals("null")) {
//                        byte[] decodedBytes = Base64.decode(file, 1);
//                        jsonObj.put("fileContent", decodedBytes);
//                    } else {


//                            String base64 = ImgToStringConverter.encodeFileToBase64Binary(_file);
//                        byte[] compressed = GzipUtil.compress(base64);
//                        String result = Base64.encodeToString(compressed, Base64.DEFAULT);

                                imageModal.fileContent = _file;

//                            jsonArrayList.put(new JSONObject(jsonObj));
                                imageModals.add(imageModal);
                            } else {
                                FslLog.e(TAG, " FILE NOT FOUND......................???");
                            }
                        } else {
                            FslLog.e(TAG, " FILE CONTENT IS NULL......................???");
                        }

                    } else {
                        FslLog.d(TAG, " FILE ALREADY SYNC ..................************");
                    }


                }
            }
            cursor.close();
            database.close();
            if (imageModals != null)
                FslLog.d(TAG, " json list images from table " + imageModals.size());
            else FslLog.d(TAG, " json list images  table IS NULL");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imageModals;
    }


    public static JSONArray getJsonEnclosureFileContentFromTableColumn(Context mContext, String query) {
        JSONArray jsonArrayList = null;
        try {
            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getReadableDatabase();

            FslLog.d(TAG, " query to pass  " + query);
            Cursor cursor = database.rawQuery(query, null);

            if (cursor.getCount() > 0) {
                jsonArrayList = new JSONArray();

                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToNext();
                    int sentFile = cursor.getInt(cursor.getColumnIndex("FileSent"));
                    if (sentFile == 0) {
                        Map<String, Object> jsonObj = new HashMap<String, Object>();
//                    for (int j = 0; j < cursor.getColumnCount(); j++) {
//                    jsonObj.put("FileName", cursor.getString(cursor.getColumnIndex("ImageName")));
                        jsonObj.put("pRowID", cursor.getString(cursor.getColumnIndex("pRowID")));
                        jsonObj.put("QRHdrID", cursor.getString(cursor.getColumnIndex("ContextID")));
                        jsonObj.put("QRPOItemHdrID", null);
                        jsonObj.put("Length", "");

                        String fileExtn = FEnumerations.E_IMAGE_EXTN;
                        String _fileEx = cursor.getString(cursor.getColumnIndex("ImageExtn"));
                        if (!TextUtils.isEmpty(_fileEx) && !_fileEx.equals("null")) {
                            fileExtn = _fileEx;
                        }

                        String file = cursor.getString(cursor.getColumnIndex("ContextID")) + "_"
                                + cursor.getString(cursor.getColumnIndex("pRowID")) + "." + fileExtn;
                        jsonObj.put("FileName", file);

//                    String file = cursor.getString(cursor.getColumnIndex("fileContent"));
//                    if (!TextUtils.isEmpty(file) && !file.equals("null")) {
//                        byte[] decodedBytes = Base64.decode(file, 1);
//                        jsonObj.put("fileContent", decodedBytes);
//                    } else {
                        String _file = cursor.getString(cursor.getColumnIndex("fileContent"));
                        if (!TextUtils.isEmpty(_file)
                                && !_file.equals("null")) {

                            String base64 = ImgToStringConverter.encodeFileToBase64Binary(_file);
//                        byte[] compressed = GzipUtil.compress(base64);
//                        String result = Base64.encodeToString(compressed, Base64.DEFAULT);

                            jsonObj.put("fileContent", base64);
                        } else {
                            jsonObj.put("fileContent", _file);
                        }
//                    jsonObj.put("fileContent", cursor.getString(cursor.getColumnIndex("fileContent")));
//                    }


                        jsonArrayList.put(new JSONObject(jsonObj));
                    } else {
                        FslLog.d(TAG, " QR ENCLOSURE FILE ALREADY SYNC ..................************");
                    }
                }
            }
            cursor.close();
            database.close();
            FslLog.d(TAG, " json array from table " + jsonArrayList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonArrayList;
    }

    public void handleRequest(final Map<String, Object> jsonMap, int pos
            , final GetCallBackSendResult getCallBackResult) {

        FslLog.d(TAG, "SINGLE IMAGES sync .........................................\n\n");
        SendDataHandler sendDataHandler = new SendDataHandler();
        sendDataHandler.sendData(jsonMap, "", new SendDataHandler.GetCallBackResult() {
            @Override
            public void onSuccess(JSONObject result) {

                getCallBackResult.onSuccess(result, pos);
            }

            @Override
            public void onError(VolleyError volleyError) {
                getCallBackResult.onError(volleyError, pos);
//                updateSyncList(FEnumerations.E_SYNC_IMAGES_TABLE, FEnumerations.E_SYNC_FAILED_STATUS);
//                requestVolleyError(volleyError);
            }
        });


    }

    public void handleStyleRequest(final Map<String, Object> jsonMap, int pos
            , final GetCallBackSendResult getCallBackResult) {

        FslLog.d(TAG, "SINGLE STYLE IMAGES sync .........................................\n\n");
        SendDataHandler sendDataHandler = new SendDataHandler();
        sendDataHandler.sendStyleData(jsonMap, "", new SendDataHandler.GetCallBackResult() {
            @Override
            public void onSuccess(JSONObject result) {

                getCallBackResult.onSuccess(result, pos);
            }

            @Override
            public void onError(VolleyError volleyError) {
                getCallBackResult.onError(volleyError, pos);
//                updateSyncList(FEnumerations.E_SYNC_IMAGES_TABLE, FEnumerations.E_SYNC_FAILED_STATUS);
//                requestVolleyError(volleyError);
            }
        });


    }


    public static void sendData(final Context mContext, Map<String, Object> params,
                                String data, final GetCallBackResult getCallBackResult) {

//        String url = "http://192.168.0.224/BuyereaseWebAPI/api/Quality/GetQualityInspection";
        String url = AppConfig.URL_SEND_DATA;
        String userId = LogInHandler.getUserId(mContext);
//        String lastSync = getLastGetSyncData(mContext);
//        if (TextUtils.isEmpty(lastSync))
//            lastSync = "2000-01-01";
//        final Map<String, String> params = new HashMap<String, String>();
        params.put("UserID", userId);
//        params.put("LastSyncDate", lastSync);


        FslLog.d(TAG, " url " + url);
        FslLog.d(TAG, " param " + new JSONObject(params));
        Log.e("sync url","url="+url);
        Log.e("sync param","param="+ new JSONObject(params));

        HandleToConnectionEithServer handleToConnectionEithServer = new HandleToConnectionEithServer();
        handleToConnectionEithServer.setRequest(url,
                new JSONObject(params), new HandleToConnectionEithServer.CallBackResult() {
                    @Override
                    public void onSuccess(JSONObject result) {

                        getCallBackResult.onSuccess(result);
                    }

                    @Override
                    public void onError(VolleyError volleyError) {
//                        getLoginResult.onError(volleyError);

                        getCallBackResult.onError(volleyError);
                    }
                });


    }

    public void sendData(Map<String, Object> params,
                         String data, final GetCallBackResult getCallBackResult) {

//        String url = "http://192.168.0.224/BuyereaseWebAPI/api/Quality/GetQualityInspection";
        String url = AppConfig.URL_SEND_FILE;
//        String userId = LogInHandler.getUserId(mContext);
//        String lastSync = getLastGetSyncData(mContext);
//        if (TextUtils.isEmpty(lastSync))
//            lastSync = "2000-01-01";
//        final Map<String, String> params = new HashMap<String, String>();
//        params.put("UserID", userId);
//        params.put("LastSyncDate", lastSync);


        FslLog.d(TAG, " url " + url);
        FslLog.d(TAG, " param " + new JSONObject(params));

        HandleToConnectionEithServer handleToConnectionEithServer = new HandleToConnectionEithServer();
        handleToConnectionEithServer.setRequest(url,
                new JSONObject(params), new HandleToConnectionEithServer.CallBackResult() {
                    @Override
                    public void onSuccess(JSONObject result) {

                        getCallBackResult.onSuccess(result);
                    }

                    @Override
                    public void onError(VolleyError volleyError) {
//                        getLoginResult.onError(volleyError);

                        getCallBackResult.onError(volleyError);
                    }
                });


    }

    public static void sendStyleData(final Context mContext, Map<String, Object> params,
                                     String data, final GetCallBackResult getCallBackResult) {

//        String url = "http://192.168.0.224/BuyereaseWebAPI/api/Quality/GetQualityInspection";
        String url = AppConfig.URL_SEND_STYLE_DATA;
        String userId = LogInHandler.getUserId(mContext);
//        String lastSync = getLastGetSyncData(mContext);
//        if (TextUtils.isEmpty(lastSync))
//            lastSync = "2000-01-01";
//        final Map<String, String> params = new HashMap<String, String>();
        params.put("UserID", userId);
//        params.put("LastSyncDate", lastSync);


        FslLog.d(TAG, " url " + url);
        FslLog.d(TAG, " param " + new JSONObject(params));

        HandleToConnectionEithServer handleToConnectionEithServer = new HandleToConnectionEithServer();
        handleToConnectionEithServer.setRequest(url,
                new JSONObject(params), new HandleToConnectionEithServer.CallBackResult() {
                    @Override
                    public void onSuccess(JSONObject result) {

                        getCallBackResult.onSuccess(result);
                    }

                    @Override
                    public void onError(VolleyError volleyError) {
//                        getLoginResult.onError(volleyError);

                        getCallBackResult.onError(volleyError);
                    }
                });


    }

    public void sendStyleData(Map<String, Object> params,
                              String data, final GetCallBackResult getCallBackResult) {

//        String url = "http://192.168.0.224/BuyereaseWebAPI/api/Quality/GetQualityInspection";
        String url = AppConfig.URL_SEND_STYLE_FILE;
//        String userId = LogInHandler.getUserId(mContext);
//        String lastSync = getLastGetSyncData(mContext);
//        if (TextUtils.isEmpty(lastSync))
//            lastSync = "2000-01-01";
//        final Map<String, String> params = new HashMap<String, String>();
//        params.put("UserID", userId);
//        params.put("LastSyncDate", lastSync);


        FslLog.d(TAG, " url " + url);
        FslLog.d(TAG, " param " + new JSONObject(params));

        HandleToConnectionEithServer handleToConnectionEithServer = new HandleToConnectionEithServer();
        handleToConnectionEithServer.setRequest(url,
                new JSONObject(params), new HandleToConnectionEithServer.CallBackResult() {
                    @Override
                    public void onSuccess(JSONObject result) {

                        getCallBackResult.onSuccess(result);
                    }

                    @Override
                    public void onError(VolleyError volleyError) {
//                        getLoginResult.onError(volleyError);

                        getCallBackResult.onError(volleyError);
                    }
                });


    }


    public static interface GetCallBackResult {
        public void onSuccess(JSONObject loginResponse);

        public void onError(VolleyError volleyError);


    }

    public static interface GetCallBackSendResult {
        public void onSuccess(JSONObject loginResponse, int pos);

        public void onError(VolleyError volleyError, int pos);


    }
}
