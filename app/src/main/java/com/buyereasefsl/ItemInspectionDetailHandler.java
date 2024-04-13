package com.buyereasefsl;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.Toast;

import com.General.EnclosureModal;
import com.android.volley.VolleyError;
import com.constant.AppConfig;
import com.constant.FClientConfig;
import com.constant.FEnumerations;
import com.database.DBHelper;
import com.inspection.InspectionModal;
import com.login.LogInHandler;
import com.podetail.POItemDtl;
import com.sync.GetDataHandler;
import com.util.FslLog;
import com.util.GenUtils;
import com.util.HandleToConnectionEithServer;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import me.drakeet.support.toast.ToastCompat;

/**
 * Created by ADMIN on 12/23/2017.
 */

public class ItemInspectionDetailHandler {

    static String TAG = "ItemInspectionDetailHandler";

    public static String updateImage(Context mContext, String QRHdrID, String QRPOItemHdrID, DigitalsUploadModal digitalsUploadModal) {

        String userId = LogInHandler.getUserId(mContext);

        DBHelper dbHelper = new DBHelper(mContext);
        SQLiteDatabase database = dbHelper.getWritableDatabase();


        ContentValues contentValues = new ContentValues();

        contentValues.put("Title", digitalsUploadModal.title);
        contentValues.put("ImageName", "ImName" + FClientConfig.LOC_ID);
        contentValues.put("ImageExtn", digitalsUploadModal.ImageExtn);
        contentValues.put("ImageSymbol", " ");
        contentValues.put("ImageSqn", 1);
        contentValues.put("fileContent", digitalsUploadModal.selectedPicPath);
        contentValues.put("recEnable", 1);
        contentValues.put("recUser", userId);
        contentValues.put("recAddDt", AppConfig.getCurrntDate());
        contentValues.put("recDt", AppConfig.getCurrntDate());

        /*String query = "INSERT INTO QRPOItemDtl_Image(pRowID,LocID,QRHdrID,QRPOItemHdrID,Title,ImageName,ImageExtn,ImageSymbol,ImageSqn,recEnable,recAddDt, recDt,recUser) " +
                "VALUES('" + GeneratePK(mContext, FEnumerations.E_TABLE_NAME_QRPOItemDtl_Image) + "', '" +
                "" + "','" + pInspectionID + "', '" + pItemHdrID + "','" + digitalsUploadModal.title + "','" + digitalsUploadModal.listImages + "','" + ".jpg" + "','" + " "
                + "',1, 1,'" + AppConfig.getCurrntDate() + "','" + AppConfig.getCurrntDate() + "'" + userId + "')";*/
        FslLog.d(TAG, "Set content images  " + contentValues);
       /* Cursor cursor = database.rawQuery(query, null);
        if (cursor.getCount() > 0) {
            return true;
        }*/

        int rows = database.update("QRPOItemDtl_Image", contentValues, "pRowID"
                + " = '" + digitalsUploadModal.pRowID + "'", null);
//        long status = 0;
        if (rows == 0) {
            contentValues.put("pRowID", digitalsUploadModal.pRowID);
            contentValues.put("LocID", FClientConfig.LOC_ID);
            contentValues.put("QRHdrID", QRHdrID);
            contentValues.put("QRPOItemHdrID", QRPOItemHdrID);
            long status = database.insert("QRPOItemDtl_Image", null, contentValues);
            FslLog.d(TAG, " insert QRPOItemDtl_Image ");
            database.close();
            return digitalsUploadModal.pRowID;
        } else {
            FslLog.d(TAG, " update QRPOItemDtl_Image comman method");
            database.close();
            return null;
        }

    }

    public static String updateImageFromQualityParameter(Context mContext, String QRHdrID,
                                                         String QRPOItemHdrID, String ItemID,
                                                         DigitalsUploadModal digitalsUploadModal) {

        String userId = LogInHandler.getUserId(mContext);

        DBHelper dbHelper = new DBHelper(mContext);
        SQLiteDatabase database = dbHelper.getWritableDatabase();


        ContentValues contentValues = new ContentValues();

        contentValues.put("Title", digitalsUploadModal.title);
        contentValues.put("ImageName", "ImName" + FClientConfig.LOC_ID);
        contentValues.put("ImageExtn", digitalsUploadModal.ImageExtn);
        contentValues.put("ImageSymbol", " ");
        contentValues.put("ImageSqn", 1);
        contentValues.put("fileContent", digitalsUploadModal.selectedPicPath);
        contentValues.put("recEnable", 1);
        contentValues.put("recUser", userId);
        contentValues.put("recAddDt", AppConfig.getCurrntDate());
        contentValues.put("recDt", AppConfig.getCurrntDate());

        /*String query = "INSERT INTO QRPOItemDtl_Image(pRowID,LocID,QRHdrID,QRPOItemHdrID,Title,ImageName,ImageExtn,ImageSymbol,ImageSqn,recEnable,recAddDt, recDt,recUser) " +
                "VALUES('" + GeneratePK(mContext, FEnumerations.E_TABLE_NAME_QRPOItemDtl_Image) + "', '" +
                "" + "','" + pInspectionID + "', '" + pItemHdrID + "','" + digitalsUploadModal.title + "','" + digitalsUploadModal.listImages + "','" + ".jpg" + "','" + " "
                + "',1, 1,'" + AppConfig.getCurrntDate() + "','" + AppConfig.getCurrntDate() + "'" + userId + "')";*/
        FslLog.d(TAG, "Set content images  " + contentValues);
       /* Cursor cursor = database.rawQuery(query, null);
        if (cursor.getCount() > 0) {
            return true;
        }*/

        int rows = database.update("QRPOItemDtl_Image", contentValues, "pRowID"
                + " = '" + digitalsUploadModal.pRowID + "'", null);
//        long status = 0;
        if (rows == 0) {
            contentValues.put("pRowID", digitalsUploadModal.pRowID);
            contentValues.put("LocID", FClientConfig.LOC_ID);
            contentValues.put("QRHdrID", QRHdrID);
            contentValues.put("ItemID", ItemID);
            contentValues.put("QRPOItemHdrID", QRPOItemHdrID);
            long status = database.insert("QRPOItemDtl_Image", null, contentValues);
            FslLog.d(TAG, " insert QRPOItemDtl_Image ");
            database.close();
            return digitalsUploadModal.pRowID;
        } else {
            FslLog.d(TAG, " update QRPOItemDtl_Image comman method");
            database.close();
            return null;
        }

    }


    public static void updateImageToSync(Context mContext, String pRowID) {

        DBHelper dbHelper = new DBHelper(mContext);
        SQLiteDatabase database = dbHelper.getWritableDatabase();


        ContentValues contentValues = new ContentValues();
        contentValues.put("FileSent", 1);
        FslLog.d(TAG, "Set content images  " + contentValues);


        int rows = database.update("QRPOItemDtl_Image", contentValues, "pRowID"
                + " = '" + pRowID + "'", null);
        database.close();
        if (rows > 0) {
            FslLog.d(TAG, " update QRPOItemDtl_Image  after sync");
        }

    }

    public static void updateImageTitle(Context mContext, String title, String pRowID) {

        DBHelper dbHelper = new DBHelper(mContext);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("Title", title);
        FslLog.d(TAG, "Set content images  " + contentValues);


        int rows = database.update("QRPOItemDtl_Image", contentValues, "pRowID"
                + " = '" + pRowID + "'", null);
        database.close();
        if (rows > 0) {
            FslLog.d(TAG, " update title QRPOItemDtl_Image ");
        }

    }

    public static void updateImageToMakeAgainNotSync(Context mContext, String QRHdrID) {

        String userId = LogInHandler.getUserId(mContext);

        DBHelper dbHelper = new DBHelper(mContext);
        SQLiteDatabase database = dbHelper.getWritableDatabase();


        ContentValues contentValues = new ContentValues();
        contentValues.put("FileSent", 0);
        FslLog.d(TAG, "Set content images  " + contentValues);


        int rows = database.update("QRPOItemDtl_Image", contentValues, "QRHdrID"
                + " = '" + QRHdrID + "'", null);
        database.close();
        if (rows > 0) {
            FslLog.d(TAG, " update QRPOItemDtl_Image to make again sync ");
        }

    }


    public static void updateQREnClosureToSync(Context mContext, String pRowID) {

        String userId = LogInHandler.getUserId(mContext);

        DBHelper dbHelper = new DBHelper(mContext);
        SQLiteDatabase database = dbHelper.getWritableDatabase();


        ContentValues contentValues = new ContentValues();
        contentValues.put("FileSent", 1);
        FslLog.d(TAG, "Set content QRClosure  " + contentValues);

        int rows = database.update("QREnclosure", contentValues, "pRowID"
                + " = '" + pRowID + "'", null);
        database.close();
        if (rows > 0) {
            FslLog.d(TAG, " update QREnclosure ");
        }

    }


    public static List<POItemDtl> getPackagingMeasurementList(Context mContext, String QRHDrID, String QRPOItemHdrID) {


        List<POItemDtl> itemArrayList = new ArrayList<POItemDtl>();
        try {
            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getReadableDatabase();

        String query = "Select dtl.pRowID, dtl.QRPOItemHdrID,hdr.PKG_Me_Pallet_Digitals, " +
                "  hdr.PKG_Me_Master_Digitals,    hdr.PKG_Me_Inner_Digitals,   hdr.PKG_Me_Unit_Digitals,  IFNull(OPDescr,'') As OPDescr, IFNUll(OPWt,0) As OPWt," +
                " IFNull(OPCBM,0) As OPCBM, IFNull(OPQty,0) As OPQty,  IFNull(IPDimn,'') As IPDimn, IFNUll(IPWt,0) As IPWt," +
                "  IFNull(IPCBM,0) As IPCBM,           IFNull(IPQty,0) As IPQty,   IFNull(PalletDimn,'') As PalletDimn, " +
                " IFNull(PalletWt,0) As PalletWt, IFNull(PalletCBM,0) As PalletCBM,   IFNull(PalletQty,0) As PalletQty," +
                "  IFNull(IDimn,'') As IDimn,  IFNUll(Weight,0) As Weight,  IFNull(CBM,0) As CBM  From QRPOItemDtl dtl" +
                "  Inner Join QRPOItemHdr hdr On hdr.pRowID = dtl.QRPOItemHdrID " +
                " Where dtl.QRHDrID='" + QRHDrID + "' And dtl.QRPOItemHdrID='" + QRPOItemHdrID + "' And dtl.recEnable = 1";
          /*  String query = "Select pRowID,ItemID,Ifnull(ItemMeasurement_InspectionResultID,'')ItemMeasurement_InspectionResultID,  ifnull(AllowedInspectionQty,'')AllowedInspectionQty,ifnull(InspectedQty,'') InspectedQty," +
                    " ifnull(CartonsPacked,'')  CartonsPacked,ifnull(AllowedCartonInspection,'')AllowedCartonInspection," +
                    "ifnull(CartonsInspected,'') CartonsInspected, ifnull(ItemMeasurement_Remark,'') ItemMeasurement_Remark," +
                    "ifnull(WorkmanShip_InspectionResultID,''),  ifnull(Overall_InspectionResultID,'') Overall_InspectionResultID, WorkmanShip_InspectionResultID, " +
                    "ifnull(CriticalDefectPieces,0) CriticalDefectPieces,ifnull(MajorDefectPieces,0) MajorDefectPieces," +
                    "ifnull(MinorDefectPieces,0) MinorDefectPieces, Ifnull(CriticalDefectsAllowed,0) CriticalDefectsAllowed, " +
                    "ifnull(MajorDefectsAllowed,0) MajorDefectsAllowed,ifnull(MinorDefectsAllowed,0) MinorDefectsAllowed," +
                    " ifnull(WorkmanShip_Remark,'')  WorkmanShip_Remark , (Select Sum(Ifnull(AvailableQty,0)) AvailableQty" +
                    " from QrpoItemDtl where QrPoItemHdrId=hdr.pRowID) AvailableQty,Ifnull(CriticalDefect,0) CriticalDefect," +
                    "  IFNULL(hdr.PKG_Me_InspectionResultID,' ') As PKG_Me_InspectionResultID, " +
                    "IFNULL(hdr.PKG_Me_InspectionLevelID,'') As PKG_Me_InspectionLevelID, IFNULL(hdr.PKG_Me_Remark,'') As PKG_Me_Remark, " +
                    " IFNULL(hdr.PKG_Me_Pallet_InspectionResultID,' ') As PKG_Me_Pallet_InspectionResultID, " +
                    " IFNULL(hdr.PKG_Me_Pallet_SampleSizeID,' ') As PKG_Me_Pallet_SampleSizeID, IFNULL(hdr.PKG_Me_Pallet_Digitals,'') As PKG_Me_Pallet_Digitals," +
                    "  IFNULL(hdr.PKG_Me_Master_InspectionResultID,' ') As PKG_Me_Master_InspectionResultID," +
                    "  IFNULL(hdr.PKG_Me_Master_SampleSizeID,'') As PKG_Me_Master_SampleSizeID, " +
                    "IFNULL(hdr.PKG_Me_Master_Digitals,'') As PKG_Me_Master_Digitals, " +
                    " IFNULL(hdr.PKG_Me_Inner_InspectionResultID,'') As PKG_Me_Inner_InspectionResultID,  " +
                    "  IFNULL(hdr.PKG_Me_Inner_SampleSizeID,'') As PKG_Me_Inner_SampleSizeID, " +
                    "IFNULL(hdr.PKG_Me_Inner_Digitals,'') As PKG_Me_Inner_Digitals,  " +
                    "IFNULL(hdr.PKG_Me_Unit_InspectionResultID,' ') As PKG_Me_Unit_InspectionResultID,  " +
                    "  IFNULL(hdr.PKG_Me_Unit_SampleSizeID,' ') As PKG_Me_Unit_SampleSizeID," +
                    " IFNULL(hdr.PKG_Me_Unit_Digitals,'') As PKG_Me_Unit_Digitals,  0 As PKG_Me_Pallet_SpecificationL,  0 " +
                    "As PKG_Me_Pallet_SpecificationB,  0 As PKG_Me_Pallet_SpecificationH,  0 " +
                    "As PKG_Me_Pallet_SpecificationWt, 0 As PKG_Me_Pallet_SpecificationCBM,    0 " +
                    "As PKG_Me_Pallet_SpecificationQty,  0 As PKG_Me_Master_SpecificationL,    0 " +
                    "As PKG_Me_Master_SpecificationB,  0 As PKG_Me_Master_SpecificationH,  0 " +
                    "As PKG_Me_Master_SpecificationWt, 0 As PKG_Me_Master_SpecificationCBM,    0" +
                    " As PKG_Me_Master_SpecificationQty,  0 As PKG_Me_Inner_SpecificationL,     0 " +
                    "As PKG_Me_Inner_SpecificationB,   0 As PKG_Me_Inner_SpecificationH,   0 " +
                    "As PKG_Me_Inner_SpecificationWt,  0 As PKG_Me_Inner_SpecificationCBM,     0 " +
                    "As PKG_Me_Inner_SpecificationQty,  0 As PKG_Me_Unit_SpecificationL,      0 " +
                    "As PKG_Me_Unit_SpecificationB,    0 As PKG_Me_Unit_SpecificationH,    0 As PKG_Me_Unit_SpecificationWt, 0 " +
                    "As PKG_Me_Unit_SpecificationCBM,  IFNULL(hdr.PKG_Me_Pallet_FindingL,0) As PKG_Me_Pallet_FindingL, " +
                    "  IFNULL(hdr.PKG_Me_Pallet_FindingB,0) As PKG_Me_Pallet_FindingB, IFNULL(hdr.PKG_Me_Pallet_FindingH,0)" +
                    " As PKG_Me_Pallet_FindingH, IFNULL(hdr.PKG_Me_Pallet_FindingWt,0) As PKG_Me_Pallet_FindingWt, " +
                    "  IFNULL(hdr.PKG_Me_Pallet_FindingCBM,0) As PKG_Me_Pallet_FindingCBM," +
                    " IFNULL(hdr.PKG_Me_Pallet_FindingQty,0) As PKG_Me_Pallet_FindingQty, " +
                    " IFNULL(hdr.PKG_Me_Master_FindingL,0) As PKG_Me_Master_FindingL, " +
                    "  IFNULL(hdr.PKG_Me_Master_FindingB,0) As PKG_Me_Master_FindingB," +
                    " IFNULL(hdr.PKG_Me_Master_FindingH,0) As PKG_Me_Master_FindingH," +
                    " IFNULL(hdr.PKG_Me_Master_FindingWt,0) As PKG_Me_Master_FindingWt, " +
                    "  IFNULL(hdr.PKG_Me_Master_FindingCBM,0) As PKG_Me_Master_FindingCBM," +
                    " IFNULL(hdr.PKG_Me_Master_FindingQty,0) As PKG_Me_Master_FindingQty, " +
                    " IFNULL(hdr.PKG_Me_Inner_FindingL,0) As PKG_Me_Inner_FindingL,  " +
                    "   IFNULL(hdr.PKG_Me_Inner_FindingB,0) As PKG_Me_Inner_FindingB, " +
                    "  IFNULL(hdr.PKG_Me_Inner_FindingH,0) As PKG_Me_Inner_FindingH, " +
                    "  IFNULL(hdr.PKG_Me_Inner_FindingWt,0) As PKG_Me_Inner_FindingWt,  " +
                    "   IFNULL(hdr.PKG_Me_Inner_FindingCBM,0) As PKG_Me_Inner_FindingCBM, " +
                    "  IFNULL(hdr.PKG_Me_Inner_FindingQty,0) As PKG_Me_Inner_FindingQty, " +
                    " IFNULL(hdr.PKG_Me_Unit_FindingL,0) As PKG_Me_Unit_FindingL,    " +
                    "   IFNULL(hdr.PKG_Me_Unit_FindingB,0) As PKG_Me_Unit_FindingB,  " +
                    "   IFNULL(hdr.PKG_Me_Unit_FindingH,0) As PKG_Me_Unit_FindingH,  " +
                    "   IFNULL(hdr.PKG_Me_Unit_FindingWt,0) As PKG_Me_Unit_FindingWt,  " +
                    "     IFNULL(hdr.PKG_Me_Unit_FindingCBM,0) As PKG_Me_Unit_FindingCBM," +
                    "  IFNULL(hdr.BaseMatDescr,'') As BaseMatDescr, " +
                    "IFNULL((Select HologramNo From QRPOItemDtl Where QRPOItemHdrID = hdr.pRowID limit 1),'')" +
                    " As HologramNo,  (Case When Date(IFNULL((Select Hologram_ExpiryDt" +
                    " From QRPOItemDtl Where QRPOItemHdrID = hdr.pRowID limit 1),'2010-01-01 00:00:00')) >= Date('now','localtime')" +
                    " Then 0 Else 1 End) As IsHologramExpired From QRPOItemHdr hdr" +
                    "  Where pRowID='" + QRPOItemHdrID + "' and ItemID='" + ItemID + "'";*/


        /* String query = "SELECT * FROM " + "QRPOItemdtl" + " where QRHdrID='" + pRowID + "'";*/
        FslLog.d(TAG, "get update query for  get packaging Measurement list  " + query);
        Cursor cursor = database.rawQuery(query, null);


        POItemDtl poItemDtl;
        if (cursor.getCount() > 0) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();
                poItemDtl = new POItemDtl();
                poItemDtl.pRowID = cursor.getString(cursor.getColumnIndex("pRowID"));
//                    poItemDtl.LocID = cursor.getString(cursor.getColumnIndex("LocID"));
//                    poItemDtl.QRHdrID = cursor.getString(cursor.getColumnIndex("QRHdrID"));
                poItemDtl.QRPOItemHdrID = cursor.getString(cursor.getColumnIndex("QRPOItemHdrID"));
                poItemDtl.OPDescr = cursor.getString(cursor.getColumnIndex("OPDescr"));
                if (!TextUtils.isEmpty(poItemDtl.OPDescr) && !poItemDtl.OPDescr.equals("null")) {

                    String[] spStr = poItemDtl.OPDescr.split(Pattern.quote("x"), 0);
                    if (spStr != null && spStr.length > 0) {
//                            StringTokenizer st = new StringTokenizer(poItemDtl.OPDescr, "x");
                        poItemDtl.mapCountMaster = Integer.parseInt(spStr[0]);
                        String l = spStr[1];//st.nextToken();
                        String w = spStr[2];//st.nextToken();
                        String h = spStr[3];//st.nextToken();
                        poItemDtl.OPL = Double.parseDouble(l);
                        poItemDtl.OPW = Double.parseDouble(w);
                        poItemDtl.OPh = Double.parseDouble(h);
                    }
                }
                poItemDtl.OPWt = cursor.getDouble(cursor.getColumnIndex("OPWt"));
                poItemDtl.OPCBM = cursor.getDouble(cursor.getColumnIndex("OPCBM"));
                poItemDtl.OPQty = cursor.getInt(cursor.getColumnIndex("OPQty"));
                poItemDtl.IPDimn = cursor.getString(cursor.getColumnIndex("IPDimn"));
                if (!TextUtils.isEmpty(poItemDtl.IPDimn) && !poItemDtl.IPDimn.equals("null")) {
//                        StringTokenizer st = new StringTokenizer(poItemDtl.IPDimn, "x");
                    String[] spStr = poItemDtl.IPDimn.split(Pattern.quote("x"), 0);
                    if (spStr != null && spStr.length > 0) {
                        poItemDtl.mapCountInner = Integer.parseInt(spStr[0]);
                        String l = spStr[1];//st.nextToken();
                        String w = spStr[2];//st.nextToken();
                        String h = spStr[3];//st.nextToken();
                        poItemDtl.IPL = Double.parseDouble(l);
                        poItemDtl.IPW = Double.parseDouble(w);
                        poItemDtl.IPh = Double.parseDouble(h);
                    }
                }
                poItemDtl.IPWt = cursor.getDouble(cursor.getColumnIndex("IPWt"));
                poItemDtl.IPCBM = cursor.getDouble(cursor.getColumnIndex("IPCBM"));
                poItemDtl.IPQty = cursor.getInt(cursor.getColumnIndex("IPQty"));
                poItemDtl.PalletDimn = cursor.getString(cursor.getColumnIndex("PalletDimn"));
                if (!TextUtils.isEmpty(poItemDtl.PalletDimn) && !poItemDtl.PalletDimn.equals("null")) {
//                        StringTokenizer st = new StringTokenizer(poItemDtl.PalletDimn, "x");
                    String[] spStr = poItemDtl.PalletDimn.split(Pattern.quote("x"), 0);
                    if (spStr != null && spStr.length > 0) {
                        poItemDtl.mapCountPallet = Integer.parseInt(spStr[0]);
                        String l = spStr[1];//st.nextToken();
                        String w = spStr[2];//st.nextToken();
                        String h = spStr[3];//st.nextToken();
                        poItemDtl.PalletL = Double.parseDouble(l);
                        poItemDtl.PalletW = Double.parseDouble(w);
                        poItemDtl.Palleth = Double.parseDouble(h);
                    }
                }

                poItemDtl.PalletWt = cursor.getInt(cursor.getColumnIndex("PalletWt"));
                poItemDtl.PalletCBM = cursor.getInt(cursor.getColumnIndex("PalletCBM"));
                poItemDtl.PalletQty = cursor.getInt(cursor.getColumnIndex("PalletQty"));
                poItemDtl.IDimn = cursor.getString(cursor.getColumnIndex("IDimn"));
                if (!TextUtils.isEmpty(poItemDtl.IDimn) && !poItemDtl.IDimn.equals("null")) {
//                        StringTokenizer st = new StringTokenizer(poItemDtl.IDimn, "x");
                    String[] spStr = poItemDtl.IDimn.split(Pattern.quote("x"), 0);
                    if (spStr != null && spStr.length > 0) {
                        String l = spStr[1];//st.nextToken();
                        String w = spStr[2];//st.nextToken();
                        String h = spStr[3];//st.nextToken();
                        poItemDtl.mapCountUnit = Integer.parseInt(spStr[0]);
                        poItemDtl.UnitL = Double.parseDouble(l);
                        poItemDtl.UnitW = Double.parseDouble(w);
                        poItemDtl.Unith = Double.parseDouble(h);
                    }
                }
                poItemDtl.Weight = cursor.getInt(cursor.getColumnIndex("Weight"));
                poItemDtl.CBM = cursor.getDouble(cursor.getColumnIndex("CBM"));

                poItemDtl.PKG_Me_Pallet_Digitals = cursor.getString(cursor.getColumnIndex("PKG_Me_Pallet_Digitals"));
                poItemDtl.PKG_Me_Master_Digitals = cursor.getString(cursor.getColumnIndex("PKG_Me_Master_Digitals"));
                poItemDtl.PKG_Me_Inner_Digitals = cursor.getString(cursor.getColumnIndex("PKG_Me_Inner_Digitals"));
                poItemDtl.PKG_Me_Unit_Digitals = cursor.getString(cursor.getColumnIndex("PKG_Me_Unit_Digitals"));


//                    poItemDtl.POItemDtlRowID = cursor.getString(cursor.getColumnIndex("POItemDtlRowID"));
//                    poItemDtl.SampleRqstHdrlRowID = cursor.getString(cursor.getColumnIndex("SampleRqstHdrlRowID"));
//                    poItemDtl.QualityDefectHdrID = cursor.getString(cursor.getColumnIndex("QualityDefectHdrID"));
//                    poItemDtl.BaseMaterialID = cursor.getString(cursor.getColumnIndex("BaseMaterialID"));
//                    poItemDtl.BaseMaterial_AddOnInfo = cursor.getString(cursor.getColumnIndex("BaseMaterial_AddOnInfo"));
//                    poItemDtl.POTnARowID = cursor.getString(cursor.getColumnIndex("POTnARowID"));


//                    poItemDtl.SampleCodeID = cursor.getString(cursor.getColumnIndex("SampleCodeID"));
//                    poItemDtl.AvailableQty = cursor.getInt(cursor.getColumnIndex("AvailableQty"));
//                    poItemDtl.AllowedinspectionQty = cursor.getString(cursor.getColumnIndex("AllowedInspectionQty"));
//                    poItemDtl.InspectedQty = cursor.getInt(cursor.getColumnIndex("InspectedQty"));
//
//                    poItemDtl.AcceptedQty = cursor.getInt(cursor.getColumnIndex("AcceptedQty"));
//                    poItemDtl.FurtherInspectionReqd = cursor.getInt(cursor.getColumnIndex("FurtherInspectionReqd"));
//                    poItemDtl.ShortStockQty = cursor.getInt(cursor.getColumnIndex("ShortStockQty"));


//                    poItemDtl.PalletPackedQty = cursor.getInt(cursor.getColumnIndex("PalletPackedQty"));
//                    poItemDtl.MasterPackedQty = cursor.getInt(cursor.getColumnIndex("MasterPackedQty"));
//                    poItemDtl.InnerPackedQty = cursor.getInt(cursor.getColumnIndex("InnerPackedQty"));
//                    poItemDtl.PackedQty = cursor.getInt(cursor.getColumnIndex("PackedQty"));
//                    poItemDtl.UnpackedQty = cursor.getInt(cursor.getColumnIndex("UnpackedQty"));
//                    poItemDtl.UnfinishedQty = cursor.getInt(cursor.getColumnIndex("UnfinishedQty"));

//                    poItemDtl.CartonsInspected = cursor.getInt(cursor.getColumnIndex("CartonsInspected"));
//                    poItemDtl.CartonsPacked = cursor.getInt(cursor.getColumnIndex("CartonsPacked"));
//                    poItemDtl.CartonsPacked2 = cursor.getInt(cursor.getColumnIndex("CartonsPacked2"));
//                    poItemDtl.AllowedCartonInspection = cursor.getInt(cursor.getColumnIndex("AllowedCartonInspection"));
//                    poItemDtl.CriticalDefectsAllowed = cursor.getInt(cursor.getColumnIndex("CriticalDefectsAllowed"));
//
//                    poItemDtl.MajorDefectsAllowed = cursor.getInt(cursor.getColumnIndex("MajorDefectsAllowed"));
//                    poItemDtl.MinorDefectsAllowed = cursor.getInt(cursor.getColumnIndex("MinorDefectsAllowed"));
//                    poItemDtl.CriticalDefect = cursor.getInt(cursor.getColumnIndex("CriticalDefect"));
//                    poItemDtl.MajorDefect = cursor.getInt(cursor.getColumnIndex("MajorDefect"));
//                    poItemDtl.MinorDefect = cursor.getInt(cursor.getColumnIndex("MinorDefect"));


//                    poItemDtl.recAddDt = cursor.getString(cursor.getColumnIndex("recAddDt"));
//                    poItemDtl.recEnable = cursor.getInt(cursor.getColumnIndex("recEnable"));
//                    poItemDtl.recDirty = cursor.getInt(cursor.getColumnIndex("recDirty"));

//                    poItemDtl.recAddUser = cursor.getString(cursor.getColumnIndex("recAddUser"));
//                    poItemDtl.recUser = cursor.getString(cursor.getColumnIndex("recUser"));

//                    poItemDtl.recDt = cursor.getString(cursor.getColumnIndex("recDt"));
//                    poItemDtl.ediDt = cursor.getString(cursor.getColumnIndex("ediDt"));
//                    poItemDtl.PONO = cursor.getString(cursor.getColumnIndex("PONO"));

//                    poItemDtl.BuyerPODt = cursor.getString(cursor.getColumnIndex("BuyerPODt"));
//                    poItemDtl.ItemDescr = cursor.getString(cursor.getColumnIndex("ItemDescr"));

//                    poItemDtl.OrderQty = cursor.getString(cursor.getColumnIndex("OrderQty"));
//                    poItemDtl.EarlierInspected = cursor.getInt(cursor.getColumnIndex("EarlierInspected"));
//                    poItemDtl.POMasterPackQty = cursor.getInt(cursor.getColumnIndex("POMasterPackQty"));
//                    poItemDtl.MR = cursor.getString(cursor.getColumnIndex("MR"));
//                    poItemDtl.LR = cursor.getString(cursor.getColumnIndex("LR"));
//                    poItemDtl.CustomerDepartment = cursor.getString(cursor.getColumnIndex("CustomerDepartment"));
//                    poItemDtl.CustomerItemRef = cursor.getString(cursor.getColumnIndex("CustomerItemRef"));
//                    poItemDtl.LatestDelDt = cursor.getString(cursor.getColumnIndex("LatestDelDt"));
//                    poItemDtl.HologramNo = cursor.getString(cursor.getColumnIndex("HologramNo"));
//                    poItemDtl.Hologram_ExpiryDt = cursor.getString(cursor.getColumnIndex("Hologram_ExpiryDt"));
//                    poItemDtl.OPDescr = cursor.getString(cursor.getColumnIndex("OPDescr"));
//                    poItemDtl.OPWt = cursor.getInt(cursor.getColumnIndex("OPWt"));
//                    poItemDtl.OPCBM = cursor.getInt(cursor.getColumnIndex("OPCBM"));
//                    poItemDtl.OPQty = cursor.getInt(cursor.getColumnIndex("OPQty"));
//                    poItemDtl.IPDimn = cursor.getString(cursor.getColumnIndex("IPDimn"));

//                    poItemDtl.IPWt = cursor.getInt(cursor.getColumnIndex("IPWt"));
//                    poItemDtl.IPCBM = cursor.getInt(cursor.getColumnIndex("IPCBM"));
//                    poItemDtl.IPQty = cursor.getInt(cursor.getColumnIndex("IPQty"));
//                    poItemDtl.PalletDimn = cursor.getString(cursor.getColumnIndex("PalletDimn"));
//                    poItemDtl.PalletWt = cursor.getInt(cursor.getColumnIndex("PalletWt"));
//
//                    poItemDtl.PalletCBM = cursor.getInt(cursor.getColumnIndex("PalletCBM"));
//                    poItemDtl.PalletQty = cursor.getInt(cursor.getColumnIndex("PalletQty"));
//
//                    poItemDtl.IDimn = cursor.getString(cursor.getColumnIndex("IDimn"));
//                    poItemDtl.Weight = cursor.getInt(cursor.getColumnIndex("Weight"));
//                    poItemDtl.CBM = cursor.getInt(cursor.getColumnIndex("CBM"));

//                    poItemDtl.QrItemID = cursor.getString(cursor.getColumnIndex("QrItemID"));
//                    poItemDtl.SampleSizeDescr = cursor.getString(cursor.getColumnIndex("SampleSizeDescr"));
//                    poItemDtl.ItemID = cursor.getString(cursor.getColumnIndex("ItemID"));
//                    poItemDtl.QRItemBaseMaterialID = cursor.getString(cursor.getColumnIndex("QRItemBaseMaterialID"));
//                    poItemDtl.QRItemBaseMaterial_AddOnInfo = cursor.getString(cursor.getColumnIndex("QRItemBaseMaterial_AddOnInfo"));
//                    poItemDtl.Barcode_InspectionResult = cursor.getString(cursor.getColumnIndex("Barcode_InspectionResult"));
//                    poItemDtl.OnSiteTest_InspectionResult = cursor.getString(cursor.getColumnIndex("OnSiteTest_InspectionResult"));
//                    poItemDtl.ItemMeasurement_InspectionResult = cursor.getString(cursor.getColumnIndex("ItemMeasurement_InspectionResult"));
//                    poItemDtl.WorkmanShip_InspectionResult = cursor.getString(cursor.getColumnIndex("WorkmanShip_InspectionResult"));
//                    poItemDtl.Overall_InspectionResult = cursor.getString(cursor.getColumnIndex("Overall_InspectionResult"));
//                    poItemDtl.Digitals = cursor.getInt(cursor.getColumnIndex("Digitals"));
//                    poItemDtl.EnclCount = cursor.getInt(cursor.getColumnIndex("EnclCount"));
//                    poItemDtl.IsSelected = cursor.getInt(cursor.getColumnIndex("IsSelected"));
//                    poItemDtl.SizeBreakUP = cursor.getInt(cursor.getColumnIndex("SizeBreakUP"));
//                    poItemDtl.ShipToBreakUP = cursor.getString(cursor.getColumnIndex("ShipToBreakUP"));
//                    poItemDtl.testReportStatus = cursor.getString(cursor.getColumnIndex("testReportStatus"));
//                    poItemDtl.DuplicateFlag = cursor.getInt(cursor.getColumnIndex("DuplicateFlag"));
//                    poItemDtl.IsHologramExpired = cursor.getInt(cursor.getColumnIndex("IsHologramExpired"));

                    itemArrayList.add(poItemDtl);
                }
            }
            cursor.close();
            database.close();
            FslLog.d(TAG, " cout of founded list of packagingMeasurement " + itemArrayList.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemArrayList;
    }

    public static List<POItemDtl> getPackagingFindingMeasurementList(Context mContext, String QRHDrID, String QRPOItemHdrID) {

        List<POItemDtl> itemArrayList = new ArrayList<POItemDtl>();
        try {
            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getReadableDatabase();

            /*String query = "Select pRowID,ItemID,ItemMeasurement_InspectionResultID, ItemMeasurement_Remark, " +
                    " Overall_InspectionResultID, WorkmanShip_InspectionResultID,  " +
                    "   WorkmanShip_Remark , PKG_Me_InspectionResultID," +
                    " PKG_Me_InspectionLevelID,PKG_Me_Remark, PKG_App_Remark,OnSiteTest_Remark, Qty_Remark," +
                    " PKG_Me_Pallet_InspectionResultID,  OnSiteTest_InspectionResultID," +
                    "  PKG_Me_Pallet_SampleSizeID, PKG_Me_Pallet_Digitals, " +
                    "  PKG_Me_Master_InspectionResultID,   " +
                    " PKG_Me_Master_SampleSizeID, " +
                    " PKG_Me_Master_Digitals,  PKG_Me_Inner_InspectionResultID, PKG_App_ShippingMark_InspectionResultID,  " +
                    "  PKG_App_shippingMark_SampleSizeId, PKG_Me_Inner_SampleSizeID, " +
                    " PKG_Me_Inner_Digitals, PKG_Me_Unit_InspectionResultID,  " +
                    "  PKG_Me_Unit_SampleSizeID, PKG_Me_Unit_Digitals, PKG_Me_Pallet_FindingL, " +
                    "  PKG_Me_Pallet_FindingB,  PKG_Me_Pallet_FindingH, PKG_Me_Pallet_FindingWt," +
                    " PKG_Me_Pallet_FindingCBM, PKG_Me_Pallet_FindingQty,   PKG_Me_Master_FindingL,  " +
                    "  PKG_Me_Master_FindingB, PKG_Me_Master_FindingH,  PKG_Me_Master_FindingWt, " +
                    "   PKG_Me_Master_FindingCBM,   PKG_Me_Master_FindingQty,  PKG_Me_Inner_FindingL, " +
                    " PKG_Me_Inner_FindingB,     PKG_Me_Inner_FindingH,   PKG_Me_Inner_FindingWt, " +
                    " PKG_Me_Inner_FindingCBM,   PKG_Me_Inner_FindingQty,   PKG_Me_Unit_FindingL,  " +
                    " PKG_Me_Unit_FindingB,   PKG_Me_Unit_FindingH,  PKG_Me_Unit_FindingWt,     " +
                    "   PKG_Me_Unit_FindingCBM, " +
                    "  BaseMatDescr From QRPOItemHdr hdr   Where pRowID='" + QRPOItemHdrID + "' and ItemID='" + QRHDrID + "'";*/

            /* String query = "SELECT * FROM " + "QRPOItemdtl" + " where QRHdrID='" + pRowID + "'";*/
            String query = "Select * From QRPOItemHdr hdr Where pRowID = '" + QRPOItemHdrID + "' and ItemID='" + QRHDrID + "'";
            FslLog.d(TAG, "get update query for  get packaging Measurement list  " + query);
            Cursor cursor = database.rawQuery(query, null);


            POItemDtl poItemDtl;
            if (cursor.getCount() > 0) {
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToNext();
                    poItemDtl = new POItemDtl();
                    poItemDtl.pRowID = cursor.getString(cursor.getColumnIndex("pRowID"));
//                    poItemDtl.LocID = cursor.getString(cursor.getColumnIndex("LocID"));
//                    poItemDtl.QRHdrID = cursor.getString(cursor.getColumnIndex("QRHdrID"));
//                    poItemDtl.QRPOItemHdrID = cursor.getString(cursor.getColumnIndex("QRPOItemHdrID"));

                    poItemDtl.PKG_Me_InspectionResultID = cursor.getString(cursor.getColumnIndex("PKG_Me_InspectionResultID"));
                    poItemDtl.PKG_Me_Master_InspectionResultID = cursor.getString(cursor.getColumnIndex("PKG_Me_Master_InspectionResultID"));
                    poItemDtl.PKG_Me_Pallet_InspectionResultID = cursor.getString(cursor.getColumnIndex("PKG_Me_Pallet_InspectionResultID"));
                    poItemDtl.PKG_Me_Unit_InspectionResultID = cursor.getString(cursor.getColumnIndex("PKG_Me_Unit_InspectionResultID"));
                    poItemDtl.PKG_Me_Inner_InspectionResultID = cursor.getString(cursor.getColumnIndex("PKG_Me_Inner_InspectionResultID"));

                    //added by shekhar
                    poItemDtl.PKG_App_ShippingMark_InspectionResultID = cursor.getString(cursor.getColumnIndex("PKG_App_ShippingMark_InspectionResultID"));
                    poItemDtl.PKG_App_Remark = cursor.getString(cursor.getColumnIndex("PKG_App_Remark"));
                    poItemDtl.PKG_App_InspectionResultID = cursor.getString(cursor.getColumnIndex("PKG_App_InspectionResultID"));
                    poItemDtl.PKG_App_InspectionLevelID = cursor.getString(cursor.getColumnIndex("PKG_App_InspectionLevelID"));
                    poItemDtl.PKG_App_Pallet_InspectionResultID = cursor.getString(cursor.getColumnIndex("PKG_App_Pallet_InspectionResultID"));
                    poItemDtl.PKG_App_Pallet_SampleSizeID = cursor.getString(cursor.getColumnIndex("PKG_App_Pallet_SampleSizeID"));
                    poItemDtl.PKG_App_Pallet_SampleSizeValue = cursor.getString(cursor.getColumnIndex("PKG_App_Pallet_SampleSizeValue"));
                    poItemDtl.PKG_App_Master_SampleSizeID = cursor.getString(cursor.getColumnIndex("PKG_App_Master_SampleSizeID"));
                    poItemDtl.PKG_App_Master_SampleSizeValue = cursor.getString(cursor.getColumnIndex("PKG_App_Master_SampleSizeValue"));
                    poItemDtl.PKG_App_Master_InspectionResultID = cursor.getString(cursor.getColumnIndex("PKG_App_Master_InspectionResultID"));
                    poItemDtl.PKG_App_Inner_SampleSizeID = cursor.getString(cursor.getColumnIndex("PKG_App_Inner_SampleSizeID"));
                    poItemDtl.PKG_App_Inner_SampleSizeValue = cursor.getString(cursor.getColumnIndex("PKG_App_Inner_SampleSizeValue"));
                    poItemDtl.PKG_App_Unit_SampleSizeID = cursor.getString(cursor.getColumnIndex("PKG_App_Unit_SampleSizeID"));
                    poItemDtl.PKG_App_Unit_InspectionResultID = cursor.getString(cursor.getColumnIndex("PKG_App_Unit_InspectionResultID"));
                    poItemDtl.OnSiteTest_Remark = cursor.getString(cursor.getColumnIndex("OnSiteTest_Remark"));
                    poItemDtl.Qty_Remark = cursor.getString(cursor.getColumnIndex("Qty_Remark"));
                    poItemDtl.OnSiteTest_InspectionResultID = cursor.getString(cursor.getColumnIndex("OnSiteTest_InspectionResultID"));


                    poItemDtl.Barcode_InspectionLevelID = cursor.getString(cursor.getColumnIndex("Barcode_InspectionLevelID"));
                    poItemDtl.Barcode_InspectionResultID = cursor.getString(cursor.getColumnIndex("Barcode_InspectionResultID"));
                    poItemDtl.Barcode_Remark = cursor.getString(cursor.getColumnIndex("Barcode_Remark"));
                    poItemDtl.Barcode_Pallet_SampleSizeID = cursor.getString(cursor.getColumnIndex("Barcode_Pallet_SampleSizeID"));
                    poItemDtl.Barcode_Pallet_SampleSizeValue = cursor.getInt(cursor.getColumnIndex("Barcode_Pallet_SampleSizeValue"));
                    poItemDtl.Barcode_Pallet_Visual = cursor.getString(cursor.getColumnIndex("Barcode_Pallet_Visual"));
                    poItemDtl.Barcode_Pallet_Scan = cursor.getString(cursor.getColumnIndex("Barcode_Pallet_Scan"));
                    poItemDtl.Barcode_Pallet_InspectionResultID = cursor.getString(cursor.getColumnIndex("Barcode_Pallet_InspectionResultID"));
                    poItemDtl.Barcode_Master_SampleSizeID = cursor.getString(cursor.getColumnIndex("Barcode_Master_SampleSizeID"));
                    poItemDtl.Barcode_Master_SampleSizeValue = cursor.getInt(cursor.getColumnIndex("Barcode_Master_SampleSizeValue"));
                    poItemDtl.Barcode_Master_Visual = cursor.getString(cursor.getColumnIndex("Barcode_Master_Visual"));
                    poItemDtl.Barcode_Master_Scan = cursor.getString(cursor.getColumnIndex("Barcode_Master_Scan"));
                    poItemDtl.Barcode_Inner_SampleSizeID = cursor.getString(cursor.getColumnIndex("Barcode_Inner_SampleSizeID"));
                    poItemDtl.Barcode_Master_InspectionResultID = cursor.getString(cursor.getColumnIndex("Barcode_Master_InspectionResultID"));
                    poItemDtl.Barcode_Inner_SampleSizeValue = cursor.getInt(cursor.getColumnIndex("Barcode_Inner_SampleSizeValue"));
                    poItemDtl.Barcode_Inner_Visual = cursor.getString(cursor.getColumnIndex("Barcode_Inner_Visual"));
                    poItemDtl.Barcode_Inner_Scan = cursor.getString(cursor.getColumnIndex("Barcode_Inner_Scan"));
                    poItemDtl.Barcode_Inner_InspectionResultID = cursor.getString(cursor.getColumnIndex("Barcode_Inner_InspectionResultID"));
                    poItemDtl.Barcode_Unit_SampleSizeID = cursor.getString(cursor.getColumnIndex("Barcode_Unit_SampleSizeID"));
                    poItemDtl.Barcode_Unit_SampleSizeValue = cursor.getInt(cursor.getColumnIndex("Barcode_Unit_SampleSizeValue"));
                    poItemDtl.Barcode_Unit_Visual = cursor.getString(cursor.getColumnIndex("Barcode_Unit_Visual"));
                    poItemDtl.Barcode_Unit_Scan = cursor.getString(cursor.getColumnIndex("Barcode_Unit_Scan"));
                    poItemDtl.Barcode_Unit_InspectionResultID = cursor.getString(cursor.getColumnIndex("Barcode_Unit_InspectionResultID"));
                    ////////////////////////////////////

                    poItemDtl.PKG_Me_Remark = cursor.getString(cursor.getColumnIndex("PKG_Me_Remark"));
                    poItemDtl.Overall_InspectionResultID = cursor.getString(cursor.getColumnIndex("Overall_InspectionResultID"));
                    poItemDtl.WorkmanShip_InspectionResultID = cursor.getString(cursor.getColumnIndex("WorkmanShip_InspectionResultID"));
                    poItemDtl.WorkmanShip_Remark = cursor.getString(cursor.getColumnIndex("WorkmanShip_Remark"));
                    poItemDtl.ItemMeasurement_InspectionResultID = cursor.getString(cursor.getColumnIndex("ItemMeasurement_InspectionResultID"));
                    poItemDtl.ItemMeasurement_Remark = cursor.getString(cursor.getColumnIndex("ItemMeasurement_Remark"));


                    poItemDtl.PKG_Me_Pallet_FindingL = cursor.getInt(cursor.getColumnIndex("PKG_Me_Pallet_FindingL"));

                    poItemDtl.PKG_Me_Pallet_FindingB = cursor.getInt(cursor.getColumnIndex("PKG_Me_Pallet_FindingB"));
                    poItemDtl.PKG_Me_Pallet_FindingH = cursor.getInt(cursor.getColumnIndex("PKG_Me_Pallet_FindingH"));
                    poItemDtl.PKG_Me_Pallet_FindingWt = cursor.getInt(cursor.getColumnIndex("PKG_Me_Pallet_FindingWt"));
                    //coment by shekhar
//                    poItemDtl.PKG_Me_Pallet_FindingCBM = cursor.getString(cursor.getColumnIndex("PKG_Me_Pallet_FindingCBM"));
                    poItemDtl.PKG_Me_Pallet_FindingCBM = cursor.getInt(cursor.getColumnIndex("PKG_Me_Pallet_FindingCBM"));

                    poItemDtl.PKG_Me_Pallet_FindingQty = cursor.getInt(cursor.getColumnIndex("PKG_Me_Pallet_FindingQty"));
                    poItemDtl.PKG_Me_Master_FindingL = cursor.getInt(cursor.getColumnIndex("PKG_Me_Master_FindingL"));
                    poItemDtl.PKG_Me_Master_FindingB = cursor.getInt(cursor.getColumnIndex("PKG_Me_Master_FindingB"));
                    poItemDtl.PKG_Me_Master_FindingH = cursor.getInt(cursor.getColumnIndex("PKG_Me_Master_FindingH"));

                    poItemDtl.PKG_Me_Master_FindingWt = cursor.getInt(cursor.getColumnIndex("PKG_Me_Master_FindingWt"));
                    //comment by shekhar
//                    poItemDtl.PKG_Me_Master_FindingCBM = cursor.getString(cursor.getColumnIndex("PKG_Me_Master_FindingCBM"));
                    poItemDtl.PKG_Me_Master_FindingCBM = cursor.getInt(cursor.getColumnIndex("PKG_Me_Master_FindingCBM"));

                    poItemDtl.PKG_Me_Master_FindingQty = cursor.getInt(cursor.getColumnIndex("PKG_Me_Master_FindingQty"));
                    poItemDtl.PKG_Me_Inner_FindingL = cursor.getInt(cursor.getColumnIndex("PKG_Me_Inner_FindingL"));

                    poItemDtl.PKG_Me_Inner_FindingB = cursor.getInt(cursor.getColumnIndex("PKG_Me_Inner_FindingB"));
                    poItemDtl.PKG_Me_Inner_FindingH = cursor.getInt(cursor.getColumnIndex("PKG_Me_Inner_FindingH"));
                    poItemDtl.PKG_Me_Inner_FindingWt = cursor.getInt(cursor.getColumnIndex("PKG_Me_Inner_FindingWt"));
                    //comment by shekhar
                    poItemDtl.PKG_Me_Inner_FindingCBM = cursor.getInt(cursor.getColumnIndex("PKG_Me_Inner_FindingCBM"));
//                    poItemDtl.PKG_Me_Inner_FindingCBM = cursor.getString(cursor.getColumnIndex("PKG_Me_Inner_FindingCBM"));

                    poItemDtl.PKG_Me_Inner_FindingQty = cursor.getInt(cursor.getColumnIndex("PKG_Me_Inner_FindingQty"));
                    poItemDtl.PKG_Me_Unit_FindingL = cursor.getInt(cursor.getColumnIndex("PKG_Me_Unit_FindingL"));
                    poItemDtl.PKG_Me_Unit_FindingB = cursor.getInt(cursor.getColumnIndex("PKG_Me_Unit_FindingB"));
                    poItemDtl.PKG_Me_Unit_FindingH = cursor.getInt(cursor.getColumnIndex("PKG_Me_Unit_FindingH"));
                    poItemDtl.PKG_Me_Unit_FindingWt = cursor.getInt(cursor.getColumnIndex("PKG_Me_Unit_FindingWt"));
                    //comment by shekhar
                    poItemDtl.PKG_Me_Unit_FindingCBM = cursor.getInt(cursor.getColumnIndex("PKG_Me_Unit_FindingCBM"));
//                    poItemDtl.PKG_Me_Unit_FindingCBM = cursor.getString(cursor.getColumnIndex("PKG_Me_Unit_FindingCBM"));


                    poItemDtl.PKG_Me_Inner_SampleSizeID = cursor.getString(cursor.getColumnIndex("PKG_Me_Inner_SampleSizeID"));
                    poItemDtl.PKG_Me_Unit_SampleSizeID = cursor.getString(cursor.getColumnIndex("PKG_Me_Unit_SampleSizeID"));
                    poItemDtl.PKG_Me_Pallet_SampleSizeID = cursor.getString(cursor.getColumnIndex("PKG_Me_Pallet_SampleSizeID"));
                    poItemDtl.PKG_Me_Master_SampleSizeID = cursor.getString(cursor.getColumnIndex("PKG_Me_Master_SampleSizeID"));

                    //added by shekhar
                    poItemDtl.PKG_App_shippingMark_SampleSizeId = cursor.getString(cursor.getColumnIndex("PKG_App_shippingMark_SampleSizeId"));


                    itemArrayList.add(poItemDtl);
                }
            }
            cursor.close();
            database.close();
            FslLog.d(TAG, " cout of founded list of packagingMeasurement " + itemArrayList.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemArrayList;
    }

    public static List<POItemDtl> getDigitalsPackagingMeasurement(Context mContext, String QRHDrID, String QRPOItemHdrID) {



        List<POItemDtl> itemArrayList = new ArrayList<POItemDtl>();
        try {
            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getReadableDatabase();

            String query = "Select pRowID, PKG_Me_Pallet_Digitals, " +
                    "  PKG_Me_Master_Digitals,    PKG_Me_Inner_Digitals,   PKG_Me_Unit_Digitals   From QRPOItemHdr hdr   Where pRowID='" + QRPOItemHdrID + "' and QRHDrID='" + QRHDrID + "'";

            /* String query = "SELECT * FROM " + "QRPOItemdtl" + " where QRHdrID='" + pRowID + "'";*/
            FslLog.d(TAG, "get update query for  get packaging Measurement list  " + query);
            Cursor cursor = database.rawQuery(query, null);


            POItemDtl poItemDtl;
            if (cursor.getCount() > 0) {
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToNext();
                    poItemDtl = new POItemDtl();
                    poItemDtl.pRowID = cursor.getString(cursor.getColumnIndex("pRowID"));

                    poItemDtl.PKG_Me_Pallet_Digitals = cursor.getString(cursor.getColumnIndex("PKG_Me_Pallet_Digitals"));
                    poItemDtl.PKG_Me_Master_Digitals = cursor.getString(cursor.getColumnIndex("PKG_Me_Master_Digitals"));
                    poItemDtl.PKG_Me_Inner_Digitals = cursor.getString(cursor.getColumnIndex("PKG_Me_Inner_Digitals"));
                    poItemDtl.PKG_Me_Unit_Digitals = cursor.getString(cursor.getColumnIndex("PKG_Me_Unit_Digitals"));

                    itemArrayList.add(poItemDtl);
                }
            }
            cursor.close();
            database.close();
            FslLog.d(TAG, " cout of founded list of packagingMeasurement " + itemArrayList.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemArrayList;
    }

    public static long updateDigitalsUnitPackagingMeasurement(Context mContext, POItemDtl poItemDtl) {

        long status = 0;
        try {

            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getReadableDatabase();

            ContentValues contentValues = new ContentValues();

            contentValues.put("PKG_Me_Unit_Digitals", poItemDtl.PKG_Me_Unit_Digitals);

            contentValues.put("recDt", AppConfig.getCurrntDate());

            int rows = database.update("QRPOItemHdr", contentValues, "pRowID"
                    + " = '" + poItemDtl.pRowID + "'", null);
            if (rows == 0) {
                FslLog.d(TAG, " NOT UPDATED packagingMeasurement ");
            } else {
                status = 1;
                FslLog.d(TAG, " update packagingMeasurement ");
            }

            database.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    public static long updateDigitalsInnerPackagingMeasurement(Context mContext, POItemDtl poItemDtl) {

        long status = 0;
        try {

            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getReadableDatabase();

            ContentValues contentValues = new ContentValues();

            contentValues.put("PKG_Me_Inner_Digitals", poItemDtl.PKG_Me_Inner_Digitals);

            contentValues.put("recDt", AppConfig.getCurrntDate());

            int rows = database.update("QRPOItemHdr", contentValues, "pRowID"
                    + " = '" + poItemDtl.pRowID + "'", null);
            if (rows == 0) {
                FslLog.d(TAG, " NOT UPDATED packagingMeasurement ");
            } else {
                status = 1;
                FslLog.d(TAG, " update packagingMeasurement ");
            }

            database.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    public static long updateDigitalsMasterPackagingMeasurement(Context mContext, POItemDtl poItemDtl) {

        long status = 0;
        try {

            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getReadableDatabase();

            ContentValues contentValues = new ContentValues();

            contentValues.put("PKG_Me_Master_Digitals", poItemDtl.PKG_Me_Master_Digitals);

            contentValues.put("recDt", AppConfig.getCurrntDate());

            int rows = database.update("QRPOItemHdr", contentValues, "pRowID"
                    + " = '" + poItemDtl.pRowID + "'", null);
            if (rows == 0) {
                FslLog.d(TAG, " NOT UPDATED packagingMeasurement ");
            } else {
                status = 1;
                FslLog.d(TAG, " update packagingMeasurement ");
            }

            database.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    public static long updateDigitalsPlannetPackagingMeasurement(Context mContext, POItemDtl poItemDtl) {

        long status = 0;
        try {

            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getReadableDatabase();

            ContentValues contentValues = new ContentValues();

            contentValues.put("PKG_Me_Pallet_Digitals", poItemDtl.PKG_Me_Pallet_Digitals);

            contentValues.put("recDt", AppConfig.getCurrntDate());

            int rows = database.update("QRPOItemHdr", contentValues, "pRowID"
                    + " = '" + poItemDtl.pRowID + "'", null);
            if (rows == 0) {
                FslLog.d(TAG, " NOT UPDATED packagingMeasurement ");
            } else {
                status = 1;
                FslLog.d(TAG, " update packagingMeasurement ");
            }

            database.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    public static long updateDigitalsQualityMeasurement(Context mContext, QualityParameter qualityParameter) {

        long status = 0;
        try {

            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getReadableDatabase();

            ContentValues contentValues = new ContentValues();

            contentValues.put("Digitals", qualityParameter.Digitals);

            contentValues.put("recDt", AppConfig.getCurrntDate());

            int rows = database.update("QRQualiltyParameterFields", contentValues, "pRowID"
                    + " = '" + qualityParameter.pRowID + "'", null);
            if (rows == 0) {
                FslLog.d(TAG, " NOT UPDATED QRQualiltyParameterFields ");
            } else {
                status = 1;
                FslLog.d(TAG, " update QRQualiltyParameterFields ");
            }

            database.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    public static long updateBarcode(Context mContext, POItemDtl poItemDtl) {
        long status = 0;
        try {

            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getReadableDatabase();

            ContentValues contentValues = new ContentValues();



            contentValues.put("Barcode_InspectionLevelID", poItemDtl.Barcode_InspectionLevelID);
            contentValues.put("Barcode_InspectionResultID", poItemDtl.Barcode_InspectionResultID);
            contentValues.put("Barcode_Remark", poItemDtl.Barcode_Remark);
            contentValues.put("Barcode_Pallet_SampleSizeID", poItemDtl.Barcode_Pallet_SampleSizeID);
            contentValues.put("Barcode_Pallet_SampleSizeValue", poItemDtl.Barcode_Pallet_SampleSizeValue);
            contentValues.put("Barcode_Pallet_Scan", poItemDtl.Barcode_Pallet_Scan);
            contentValues.put("Barcode_Pallet_Visual", poItemDtl.Barcode_Pallet_Visual);
            contentValues.put("Barcode_Pallet_InspectionResultID", poItemDtl.Barcode_Pallet_InspectionResultID);
            contentValues.put("Barcode_Master_SampleSizeID", poItemDtl.Barcode_Master_SampleSizeID);
            contentValues.put("Barcode_Master_SampleSizeValue", poItemDtl.Barcode_Master_SampleSizeValue);
            contentValues.put("Barcode_Master_Visual", poItemDtl.Barcode_Master_Visual);
            contentValues.put("Barcode_Master_Scan", poItemDtl.Barcode_Master_Scan);
            contentValues.put("Barcode_Inner_SampleSizeID", poItemDtl.Barcode_Inner_SampleSizeID);
            contentValues.put("Barcode_Master_InspectionResultID", poItemDtl.Barcode_Master_InspectionResultID);
            contentValues.put("Barcode_Inner_SampleSizeValue", poItemDtl.Barcode_Inner_SampleSizeValue);
            contentValues.put("Barcode_Inner_Visual", poItemDtl.Barcode_Inner_Visual);
            contentValues.put("Barcode_Inner_Scan", poItemDtl.Barcode_Inner_Scan);
            contentValues.put("Barcode_Inner_InspectionResultID", poItemDtl.Barcode_Inner_InspectionResultID);
            contentValues.put("Barcode_Unit_SampleSizeID", poItemDtl.Barcode_Unit_SampleSizeID);
            contentValues.put("Barcode_Unit_SampleSizeValue", poItemDtl.Barcode_Unit_SampleSizeValue);
            contentValues.put("Barcode_Unit_Visual", poItemDtl.Barcode_Unit_Visual);
            contentValues.put("Barcode_Unit_Scan", poItemDtl.Barcode_Unit_Scan);
            contentValues.put("Barcode_Unit_InspectionResultID", poItemDtl.Barcode_Unit_InspectionResultID);

            contentValues.put("recDt", AppConfig.getCurrntDate());

            int rows = database.update("QRPOItemHdr", contentValues, "pRowID"
                    + " = '" + poItemDtl.QRPOItemHdrID + "'", null);
            if (rows == 0) {
                status = database.insert("QRPOItemHdr", null, contentValues);
                FslLog.d(TAG, " insert packagingMeasurement ");
            } else {
                status = 1;
                FslLog.d(TAG, " update packagingMeasurement ");
            }

            database.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    public static long updatePackagingFindingMeasurementList(Context mContext, POItemDtl poItemDtl) {


        long status = 0;
        try {

            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getReadableDatabase();

            ContentValues contentValues = new ContentValues();

            contentValues.put("pRowID", poItemDtl.QRPOItemHdrID);
            contentValues.put("PKG_Me_InspectionResultID", poItemDtl.PKG_Me_InspectionResultID);
            contentValues.put("PKG_Me_Master_InspectionResultID", poItemDtl.PKG_Me_Master_InspectionResultID);
            contentValues.put("PKG_Me_Pallet_InspectionResultID", poItemDtl.PKG_Me_Pallet_InspectionResultID);
            contentValues.put("PKG_Me_Unit_InspectionResultID", poItemDtl.PKG_Me_Unit_InspectionResultID);
            contentValues.put("PKG_Me_Inner_InspectionResultID", poItemDtl.PKG_Me_Inner_InspectionResultID);
            //added by shekhar
            contentValues.put("PKG_App_ShippingMark_InspectionResultID", poItemDtl.PKG_App_ShippingMark_InspectionResultID);
            contentValues.put("PKG_App_Remark", poItemDtl.PKG_App_Remark);
            contentValues.put("PKG_App_InspectionResultID", poItemDtl.PKG_App_InspectionResultID);
            contentValues.put("PKG_App_InspectionLevelID", poItemDtl.PKG_App_InspectionLevelID);
            contentValues.put("PKG_App_Pallet_InspectionResultID", poItemDtl.PKG_App_Pallet_InspectionResultID);
            contentValues.put("PKG_App_Pallet_SampleSizeID", poItemDtl.PKG_App_Pallet_SampleSizeID);
            contentValues.put("PKG_App_Pallet_SampleSizeValue", poItemDtl.PKG_App_Pallet_SampleSizeValue);
            contentValues.put("PKG_App_Master_SampleSizeID", poItemDtl.PKG_App_Master_SampleSizeID);
            contentValues.put("PKG_App_Master_SampleSizeValue", poItemDtl.PKG_App_Master_SampleSizeValue);
            contentValues.put("PKG_App_Master_InspectionResultID", poItemDtl.PKG_App_Master_InspectionResultID);
            contentValues.put("PKG_App_Inner_SampleSizeID", poItemDtl.PKG_App_Inner_SampleSizeID);
            contentValues.put("PKG_App_Inner_SampleSizeValue", poItemDtl.PKG_App_Inner_SampleSizeValue);
            contentValues.put("PKG_App_Unit_SampleSizeID", poItemDtl.PKG_App_Unit_SampleSizeID);
            contentValues.put("PKG_App_Unit_InspectionResultID", poItemDtl.PKG_App_Unit_InspectionResultID);
            contentValues.put("OnSiteTest_Remark", poItemDtl.OnSiteTest_Remark);
            contentValues.put("Qty_Remark", poItemDtl.Qty_Remark);
            contentValues.put("OnSiteTest_InspectionResultID", poItemDtl.OnSiteTest_InspectionResultID);

            contentValues.put("Barcode_InspectionLevelID", poItemDtl.Barcode_InspectionLevelID);
            contentValues.put("Barcode_InspectionResultID", poItemDtl.Barcode_InspectionResultID);
            contentValues.put("Barcode_Remark", poItemDtl.Barcode_Remark);
            contentValues.put("Barcode_Pallet_SampleSizeID", poItemDtl.Barcode_Pallet_SampleSizeID);
            contentValues.put("Barcode_Pallet_SampleSizeValue", poItemDtl.Barcode_Pallet_SampleSizeValue);
            contentValues.put("Barcode_Pallet_Visual", poItemDtl.Barcode_Pallet_Visual);
            contentValues.put("Barcode_Pallet_Scan", poItemDtl.Barcode_Pallet_Scan);
            contentValues.put("Barcode_Pallet_InspectionResultID", poItemDtl.Barcode_Pallet_InspectionResultID);
            contentValues.put("Barcode_Master_SampleSizeID", poItemDtl.Barcode_Master_SampleSizeID);
            contentValues.put("Barcode_Master_SampleSizeValue", poItemDtl.Barcode_Master_SampleSizeValue);
            contentValues.put("Barcode_Master_Visual", poItemDtl.Barcode_Master_Visual);
            contentValues.put("Barcode_Master_Scan", poItemDtl.Barcode_Master_Scan);
            contentValues.put("Barcode_Inner_SampleSizeID", poItemDtl.Barcode_Inner_SampleSizeID);
            contentValues.put("Barcode_Master_InspectionResultID", poItemDtl.Barcode_Master_InspectionResultID);
            contentValues.put("Barcode_Inner_SampleSizeValue", poItemDtl.Barcode_Inner_SampleSizeValue);
            contentValues.put("Barcode_Inner_Visual", poItemDtl.Barcode_Inner_Visual);
            contentValues.put("Barcode_Inner_Scan", poItemDtl.Barcode_Inner_Scan);
            contentValues.put("Barcode_Inner_InspectionResultID", poItemDtl.Barcode_Inner_InspectionResultID);
            contentValues.put("Barcode_Unit_SampleSizeID", poItemDtl.Barcode_Unit_SampleSizeID);
            contentValues.put("Barcode_Unit_SampleSizeValue", poItemDtl.Barcode_Unit_SampleSizeValue);
            contentValues.put("Barcode_Unit_Visual", poItemDtl.Barcode_Unit_Visual);
            contentValues.put("Barcode_Unit_Scan", poItemDtl.Barcode_Unit_Scan);
            contentValues.put("Barcode_Unit_InspectionResultID", poItemDtl.Barcode_Unit_InspectionResultID);

            //////////////////

            contentValues.put("PKG_Me_Remark", poItemDtl.PKG_Me_Remark);

            contentValues.put("PKG_Me_Pallet_FindingL", poItemDtl.PKG_Me_Pallet_FindingL);

            contentValues.put("PKG_Me_Pallet_FindingB", poItemDtl.PKG_Me_Pallet_FindingB);
            contentValues.put("PKG_Me_Pallet_FindingH", poItemDtl.PKG_Me_Pallet_FindingH);
            contentValues.put("PKG_Me_Pallet_FindingWt", poItemDtl.PKG_Me_Pallet_FindingWt);
            contentValues.put("PKG_Me_Pallet_FindingCBM", poItemDtl.PKG_Me_Pallet_FindingCBM);

            contentValues.put("PKG_Me_Pallet_FindingQty", poItemDtl.PKG_Me_Pallet_FindingQty);
            contentValues.put("PKG_Me_Master_FindingL", poItemDtl.PKG_Me_Master_FindingL);
            contentValues.put("PKG_Me_Master_FindingB", poItemDtl.PKG_Me_Master_FindingB);
            contentValues.put("PKG_Me_Master_FindingH", poItemDtl.PKG_Me_Master_FindingH);

            contentValues.put("PKG_Me_Master_FindingWt", poItemDtl.PKG_Me_Master_FindingWt);
            contentValues.put("PKG_Me_Master_FindingCBM", poItemDtl.PKG_Me_Master_FindingCBM);
            contentValues.put("PKG_Me_Master_FindingQty", poItemDtl.PKG_Me_Master_FindingQty);
            contentValues.put("PKG_Me_Inner_FindingL", poItemDtl.PKG_Me_Inner_FindingL);

            contentValues.put("PKG_Me_Inner_FindingB", poItemDtl.PKG_Me_Inner_FindingB);
            contentValues.put("PKG_Me_Inner_FindingH", poItemDtl.PKG_Me_Inner_FindingH);
            contentValues.put("PKG_Me_Inner_FindingWt", poItemDtl.PKG_Me_Inner_FindingWt);
            contentValues.put("PKG_Me_Inner_FindingCBM", poItemDtl.PKG_Me_Inner_FindingCBM);
            contentValues.put("PKG_Me_Inner_FindingQty", poItemDtl.PKG_Me_Inner_FindingQty);
            contentValues.put("PKG_Me_Unit_FindingL", poItemDtl.PKG_Me_Unit_FindingL);
            contentValues.put("PKG_Me_Unit_FindingB", poItemDtl.PKG_Me_Unit_FindingB);
            contentValues.put("PKG_Me_Unit_FindingH", poItemDtl.PKG_Me_Unit_FindingH);
            contentValues.put("PKG_Me_Unit_FindingWt", poItemDtl.PKG_Me_Unit_FindingWt);
            contentValues.put("PKG_Me_Unit_FindingCBM", poItemDtl.PKG_Me_Unit_FindingCBM);

            contentValues.put("PKG_Me_Inner_SampleSizeID", poItemDtl.PKG_Me_Inner_SampleSizeID);
            contentValues.put("PKG_Me_Unit_SampleSizeID", poItemDtl.PKG_Me_Unit_SampleSizeID);
            contentValues.put("PKG_Me_Pallet_SampleSizeID", poItemDtl.PKG_Me_Pallet_SampleSizeID);
            contentValues.put("PKG_Me_Master_SampleSizeID", poItemDtl.PKG_Me_Master_SampleSizeID);
            //added by shekhar
            contentValues.put("PKG_App_shippingMark_SampleSizeId", poItemDtl.PKG_App_shippingMark_SampleSizeId);

            contentValues.put("WorkmanShip_InspectionResultID", poItemDtl.WorkmanShip_InspectionResultID);

            contentValues.put("recDt", AppConfig.getCurrntDate());

            int rows = database.update("QRPOItemHdr", contentValues, "pRowID"
                    + " = '" + poItemDtl.QRPOItemHdrID + "'", null);
            if (rows == 0) {
                status = database.insert("QRPOItemHdr", null, contentValues);
                FslLog.d(TAG, " insert packagingMeasurement ");
            } else {
                status = 1;
                FslLog.d(TAG, " update packagingMeasurement ");
            }

            database.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    public static boolean updateEnclosure(Context mContext, InspectionModal inspectionModal, EnclosureModal enclosureModal) {

        try {
            String userId = LogInHandler.getUserId(mContext);

            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getWritableDatabase();

        /*String query = "Insert Into QREnclosure(pRowID,LocID,ContextID,EnclType,ImageName,ImageExtn," +
                "  Title,FileName,recDirty,recEnable,recUser,recAddDt,recDt,numVal1)" +
                "  VALUES('" + enclosureModal.pRowID + "','DEL','" + pInspectionID +
                "','" + enclosureModal.EnclType + "', '" + enclosureModal.ImageName + "', '" + enclosureModal.fileExt +
                "',  '" + enclosureModal.Title + "', '"
                + enclosureModal.fileBase64Str + "',  1,1,'"
                + userId + "','" + AppConfig.getCurrntDate() + "','" + AppConfig.getCurrntDate() + "'," + "" + ")";
*/
            ContentValues contentValues = new ContentValues();

            contentValues.put("ContextID", inspectionModal.pRowID);
            contentValues.put("EnclType", enclosureModal.EnclType);
            contentValues.put("ImageName", enclosureModal.ImageName);
            contentValues.put("FileName", enclosureModal.fileName);
            contentValues.put("ImageExtn", enclosureModal.fileExt);
            contentValues.put("Title", enclosureModal.Title);
            contentValues.put("fileContent", enclosureModal.fileContent);
            contentValues.put("numVal1", enclosureModal.numVal1);
            contentValues.put("recDirty", 1);
            contentValues.put("recEnable", 1);
            contentValues.put("recUser", userId);
            contentValues.put("recAddDt", AppConfig.getCurrntDate());
            contentValues.put("recDt", AppConfig.getCurrntDate());

            int rows = database.update("QREnclosure", contentValues, "pRowID"
                    + " = '" + enclosureModal.pRowID + "'", null);
            long status = 0;
            if (rows == 0) {
                contentValues.put("LocID", FClientConfig.LOC_ID);
                contentValues.put("pRowID", enclosureModal.pRowID);

                status = database.insert("QREnclosure", null, contentValues);
                if (status > 0)
                    FslLog.d(TAG, " insert QREnclosure " + status);
                else FslLog.d(TAG, "NOT insert QREnclosure ");
            } else {
                status = 1;
                FslLog.d(TAG, " update QREnclosure ");
            }
            database.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public static List<EnclosureModal> getQREnclosureList(Context mContext, String pRowId) {


        List<EnclosureModal> itemArrayList = new ArrayList<EnclosureModal>();
        try {
            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getReadableDatabase();

            String query = "Select pRowID, ContextID, EnclType, Title, ImageName," +
                    " ImageExtn, FileName, fileContent From QREnclosure" +
                    "  Where  ContextID='" + pRowId + "'";


            /* String query = "SELECT * FROM " + "QRPOItemdtl" + " where QRHdrID='" + pRowID + "'";*/
            FslLog.d(TAG, "get update query for  get Enclosure list  " + query);
            Cursor cursor = database.rawQuery(query, null);
            EnclosureModal enclosureModal;
            if (cursor.getCount() > 0) {
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToNext();
                    enclosureModal = new EnclosureModal();
                    enclosureModal.pRowID = cursor.getString(cursor.getColumnIndex("pRowID"));
//                    poItemDtl.QRHdrID = cursor.getString(cursor.getColumnIndex("QRHdrID"));
//                    enclosureModal.QRPOItemHdrID = cursor.getString(cursor.getColumnIndex("QRPOItemHdrID"));
                    enclosureModal.ContextID = cursor.getString(cursor.getColumnIndex("ContextID"));

                    enclosureModal.EnclType = cursor.getString(cursor.getColumnIndex("EnclType"));
                    enclosureModal.Title = cursor.getString(cursor.getColumnIndex("Title"));
                    enclosureModal.ImageName = cursor.getString(cursor.getColumnIndex("ImageName"));
                    enclosureModal.fileName = cursor.getString(cursor.getColumnIndex("FileName"));
//                    String[] strA = enclosureModal.ImageName.split(Pattern.quote("."));
//                    enclosureModal.fileExt = "." + strA[1];
                    enclosureModal.fileExt = cursor.getString(cursor.getColumnIndex("ImageExtn"));
//                    enclosureModal.fileName = cursor.getString(cursor.getColumnIndex("FileName"));
//                    enclosureModal.EnclFileType = cursor.getString(cursor.getColumnIndex("EnclFileType"));
//                    enclosureModal.ImagePathID = cursor.getString(cursor.getColumnIndex("ImagePathID"));
                    enclosureModal.fileContent = cursor.getString(cursor.getColumnIndex("fileContent"));
//                    enclosureModal.ContextDs3 = cursor.getString(cursor.getColumnIndex("ContextDs3"));
//                    enclosureModal.ContextDs2 = cursor.getString(cursor.getColumnIndex("ContextDs2"));

                    itemArrayList.add(enclosureModal);
                }
            }
            cursor.close();
            database.close();
            FslLog.d(TAG, " cout of founded list of enclosure " + itemArrayList.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemArrayList;
    }

    public static boolean deleteEnclosure(Context mContext, EnclosureModal enclosureModal) {

        // String userId = LogInHandler.getUserId(mContext);

        DBHelper dbHelper = new DBHelper(mContext);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        String query = "DELETE FROM  QREnclosure WHERE pRowID='" + enclosureModal.pRowID + "'";

        FslLog.d(TAG, " query for DELETED QREnclosure  " + query);
        Cursor cursor = database.rawQuery(query, null);
        if (cursor.getCount() > 0) {
            FslLog.d(TAG, "DELETED QREnclosure successfully..........  ");
            return true;
        }
        return false;
    }

    public static List<EnclosureModal> getEnclosureList(Context mContext, String QRHDrID, String QRPOItemHdrID) {


        List<EnclosureModal> itemArrayList = new ArrayList<EnclosureModal>();
        try {
            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getReadableDatabase();

            String query = "Select distinct  EnclRowID pRowID, ifnull(contextDs,'') contextDs,ifnull(Encltype,'') Encltype,Ifnull(Title,'')Title , Ifnull(ImageName,'') " +
                    "As ImageName, Ifnull(EnclFileType,'') As EnclFileType, IFNULL(ImagePathID,'') As ImagePathID," +
                    " IFNULL(ContextDs2,'') As ContextDs2 , IFNULL(ContextDs3,'') As ContextDs3 , IsImportant , IsRead  From Enclosures" +
                    "  Where  (IFNULL(QRPOItemHdrId,'')='" + QRPOItemHdrID + "' OR IFNULL(QRPOItemHdrId,'')='')  and QRHdrID='" + QRHDrID + "'";


            /* String query = "SELECT * FROM " + "QRPOItemdtl" + " where QRHdrID='" + pRowID + "'";*/
            FslLog.d(TAG, "get update query for  get Enclosure list  " + query);
            Cursor cursor = database.rawQuery(query, null);


            EnclosureModal enclosureModal;
            if (cursor.getCount() > 0) {
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToNext();
                    enclosureModal = new EnclosureModal();
                    enclosureModal.EnclRowID = cursor.getString(cursor.getColumnIndex("pRowID"));
//                    poItemDtl.QRHdrID = cursor.getString(cursor.getColumnIndex("QRHdrID"));
//                    enclosureModal.QRPOItemHdrID = cursor.getString(cursor.getColumnIndex("QRPOItemHdrID"));
                    enclosureModal.ContextDs = cursor.getString(cursor.getColumnIndex("contextDs"));

                    enclosureModal.EnclType = cursor.getString(cursor.getColumnIndex("Encltype"));
                    enclosureModal.Title = cursor.getString(cursor.getColumnIndex("Title"));
                    enclosureModal.ImageName = cursor.getString(cursor.getColumnIndex("ImageName"));
                    String[] strA = enclosureModal.ImageName.split(Pattern.quote("."));
                    enclosureModal.fileExt = "." + strA[1];
                    enclosureModal.EnclFileType = cursor.getString(cursor.getColumnIndex("EnclFileType"));

                    enclosureModal.ImagePathID = cursor.getString(cursor.getColumnIndex("ImagePathID"));
                    enclosureModal.ContextDs3 = cursor.getString(cursor.getColumnIndex("ContextDs3"));
                    enclosureModal.ContextDs2 = cursor.getString(cursor.getColumnIndex("ContextDs2"));
                    enclosureModal.IsImportant = cursor.getInt(cursor.getColumnIndex("IsImportant"));
                    enclosureModal.IsRead = cursor.getInt(cursor.getColumnIndex("IsRead"));

                    itemArrayList.add(enclosureModal);
                }
            }
            cursor.close();
            database.close();
            FslLog.d(TAG, " cout of founded list of enclosure " + itemArrayList.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemArrayList;
    }

    public static List<InspectionModal> getHistoryList(Context mContext, String QrItemID, String QRPOItemHdrID) {


        List<InspectionModal> itemArrayList = new ArrayList<InspectionModal>();
        try {
            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getReadableDatabase();

            String query = "Select QRHdrID pRowID,IFnull(InspectionDt,'') InspectionDt,Ifnull(StatusDs,'') StatusDs," +
                    " Ifnull(ActivityDs,'') ActivityDs,ifnull(QR,'') QR,ifnull(Inspector,'') Inspector" +
                    " from   QRInspectionHistory where RefQRPOItemHdrID='" + QRPOItemHdrID + "' And RefQRHdrID = '" + QrItemID + "'";


            /* String query = "SELECT * FROM " + "QRPOItemdtl" + " where QRHdrID='" + pRowID + "'";*/
            FslLog.d(TAG, "get update query for  get history list  " + query);
            Cursor cursor = database.rawQuery(query, null);


            InspectionModal inspectionModal;
            if (cursor.getCount() > 0) {
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToNext();
                    inspectionModal = new InspectionModal();
//                    enclosureModal.EnclRowID = cursor.getString(cursor.getColumnIndex("pRowID"));
                    inspectionModal.QRHdrID = cursor.getString(cursor.getColumnIndex("pRowID"));
                    inspectionModal.InspectionDt = cursor.getString(cursor.getColumnIndex("InspectionDt"));
                    inspectionModal.Status = cursor.getString(cursor.getColumnIndex("StatusDs"));
//
                    inspectionModal.Activity = cursor.getString(cursor.getColumnIndex("ActivityDs"));
                    inspectionModal.QR = cursor.getString(cursor.getColumnIndex("QR"));
                    inspectionModal.Inspector = cursor.getString(cursor.getColumnIndex("Inspector"));
//                    enclosureModal.EnclFileType = cursor.getString(cursor.getColumnIndex("EnclFileType"));
//
//                    enclosureModal.ImagePathID = cursor.getString(cursor.getColumnIndex("ImagePathID"));
//                    enclosureModal.ContextDs3 = cursor.getString(cursor.getColumnIndex("ContextDs3"));
//                    enclosureModal.ContextDs2 = cursor.getString(cursor.getColumnIndex("ContextDs2"));
//
                    itemArrayList.add(inspectionModal);
                }
            }
            cursor.close();
            database.close();
            FslLog.d(TAG, " cout of founded list of history " + itemArrayList.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemArrayList;
    }

    public static List<TestReportModal> getTestReportList(Context mContext, String QrItemID, String QRPOItemHdrID) {


        List<TestReportModal> itemArrayList = new ArrayList<TestReportModal>();
        try {
            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getReadableDatabase();

            String query = "Select pRowId,Ifnull(ActivityDescr,'') ActivityDescr,Ifnull(testdt,'') testdt,ifnull(ReportName,'')" +
                    "  ReportName,ifnull(Remarks,'') Remarks,ifnull(fileExtn,'')fileExtn, ifnull(Validuptodt,'') Validuptodt," +
                    "  IFNULL(pRowID,'') || '.' || IFNULL(FileExtn,'') As ImageName, IFNULL(ImagePathID,'') " +
                    "As ImagePathID  From TestReport  Where QrPoItemHdrID='" + QRPOItemHdrID + "'  and QrhdrId='" + QrItemID + "'";

            /* String query = "SELECT * FROM " + "QRPOItemdtl" + " where QRHdrID='" + pRowID + "'";*/
            FslLog.d(TAG, "get update query for  get test report list  " + query);
            Cursor cursor = database.rawQuery(query, null);


            TestReportModal inspectionModal;
            if (cursor.getCount() > 0) {
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToNext();
                    inspectionModal = new TestReportModal();
//                    enclosureModal.EnclRowID = cursor.getString(cursor.getColumnIndex("pRowID"));
                    inspectionModal.pRowID = cursor.getString(cursor.getColumnIndex("pRowID"));
                    inspectionModal.ActivityDescr = cursor.getString(cursor.getColumnIndex("ActivityDescr"));
                    inspectionModal.testdt = cursor.getString(cursor.getColumnIndex("testdt"));
//
                    inspectionModal.ReportName = cursor.getString(cursor.getColumnIndex("ReportName"));
                    inspectionModal.Remarks = cursor.getString(cursor.getColumnIndex("Remarks"));
                    inspectionModal.fileExtn = cursor.getString(cursor.getColumnIndex("fileExtn"));
                    inspectionModal.Validuptodt = cursor.getString(cursor.getColumnIndex("Validuptodt"));
                    inspectionModal.ImageName = cursor.getString(cursor.getColumnIndex("ImageName"));
                    inspectionModal.ImagePathID = cursor.getString(cursor.getColumnIndex("ImagePathID"));

//
                    itemArrayList.add(inspectionModal);
                }
            }
            cursor.close();
            database.close();
            FslLog.d(TAG, " cout of founded list of test report " + itemArrayList.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemArrayList;
    }


    public static List<DigitalsUploadModal> getDigitalsUploadList(Context mContext, String pItemHdrID, String pInspectionID) {


        List<DigitalsUploadModal> itemArrayList = new ArrayList<DigitalsUploadModal>();
        try {
            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getReadableDatabase();

            String query = "Select  Img.pRowID As Row_ID,Img.ImageName,Img.ImageExtn, Img.fileContent," +
                    "IFNULL(Img.Title,'') As Title,Img.ImageSymbol,1 As IsSelected," +
                    "IfNULL(Hdr.DefaultImageRowID,'') As SetAsDefault  From QRPOItemDtl_Image Img" +
                    "  Left Join QRPOItemHdr Hdr on Hdr.pRowID=Img.QRPOItemHdrID And Hdr.DefaultImageRowID=Img.pRowID " +
                    "  Where Img.RecEnable=1 And   Img.QRHdrID = '" + pItemHdrID + "'  AND IfNULL(Img.QRPOItemHdrID,'') = '" + pInspectionID + "'Order By trim(Img.Title) ASC";//


            /* String query = "SELECT * FROM " + "QRPOItemdtl" + " where QRHdrID='" + pRowID + "'";*/
            FslLog.d(TAG, "get  query for  get digital list  " + query);
            Cursor cursor = database.rawQuery(query, null);


            DigitalsUploadModal digitalsUploadModal;
            if (cursor.getCount() > 0) {
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToNext();
                    digitalsUploadModal = new DigitalsUploadModal();
                    digitalsUploadModal.pRowID = cursor.getString(cursor.getColumnIndex("Row_ID"));
                    digitalsUploadModal.title = cursor.getString(cursor.getColumnIndex("Title"));
                    digitalsUploadModal.ImageExtn = cursor.getString(cursor.getColumnIndex("ImageExtn"));

                    digitalsUploadModal.ImageName = cursor.getString(cursor.getColumnIndex("ImageName"));

                    digitalsUploadModal.selectedPicPath = cursor.getString(cursor.getColumnIndex("fileContent"));

                    itemArrayList.add(digitalsUploadModal);
                }
            }
            cursor.close();
            database.close();
            FslLog.d(TAG, " cout of founded list of digital " + itemArrayList.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemArrayList;
    }

    public static long updateItemMeasurementRemark(Context mContext, POItemDtl poItemDtl) {

        long status = 0;
        try {

            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getReadableDatabase();

            ContentValues contentValues = new ContentValues();

//            contentValues.put("pRowID", poItemDtl.QRPOItemHdrID);

            contentValues.put("ItemMeasurement_Remark", poItemDtl.ItemMeasurement_Remark);

            int rows = database.update("QRPOItemHdr", contentValues, "pRowID"
                    + " = '" + poItemDtl.QRPOItemHdrID + "'", null);
            if (rows == 0) {
                FslLog.d(TAG, "COULD NOT update item Measurement ");
            } else {
                status = 1;
                FslLog.d(TAG, " update item Measurement ");
            }

            database.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    public static long updateWorkmanshipRemark(Context mContext, POItemDtl poItemDtl) {

        long status = 0;
        try {

            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getReadableDatabase();

            ContentValues contentValues = new ContentValues();
//            contentValues.put("pRowID", poItemDtl.QRPOItemHdrID);
            contentValues.put("WorkmanShip_Remark", poItemDtl.WorkmanShip_Remark);

            int rows = database.update("QRPOItemHdr", contentValues, "pRowID"
                    + " = '" + poItemDtl.QRPOItemHdrID + "'", null);
            if (rows == 0) {
                FslLog.d(TAG, "COULD NOT update workmanship ");
            } else {
                status = 1;
                FslLog.d(TAG, " update workmanship ");
            }

            database.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    public static long updateWorkmanshipOverAllResult(Context mContext, POItemDtl poItemDtl) {

        long status = 0;
        try {

            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getReadableDatabase();

            ContentValues contentValues = new ContentValues();
//            contentValues.put("pRowID", poItemDtl.QRPOItemHdrID);
            contentValues.put("WorkmanShip_InspectionResultID", poItemDtl.WorkmanShip_InspectionResultID);
            contentValues.put("ItemMeasurement_InspectionResultID", poItemDtl.ItemMeasurement_InspectionResultID);
            contentValues.put("Overall_InspectionResultID", poItemDtl.Overall_InspectionResultID);


            int rows = database.update("QRPOItemHdr", contentValues, "pRowID"
                    + " = '" + poItemDtl.QRPOItemHdrID + "'", null);
            if (rows == 0) {
                FslLog.d(TAG, "COULD NOT update WorkmanShip_InspectionResultID ");
            } else {
                status = 1;
                FslLog.d(TAG, " update InspectionResultID ");
            }

            database.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    public static String updateItemMeasurement(Context mContext, ItemMeasurementModal itemMeasurementModal, POItemDtl poItemDtl) {

        long status = 0;
        try {
            String userId = LogInHandler.getUserId(mContext);
            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getReadableDatabase();

            ContentValues contentValues = new ContentValues();


            contentValues.put("ItemMeasurementDescr", itemMeasurementModal.ItemMeasurementDescr);

            contentValues.put("Dim_Height", itemMeasurementModal.Dim_Height);
            contentValues.put("Dim_Width", itemMeasurementModal.Dim_Width);
            contentValues.put("Dim_length", itemMeasurementModal.Dim_length);
            contentValues.put("SampleSizeValue", itemMeasurementModal.SampleSizeValue);
            contentValues.put("SampleSizeID", itemMeasurementModal.SampleSizeID);
            contentValues.put("InspectionResultID", itemMeasurementModal.InspectionResultID);
            contentValues.put("Tolerance_Range", itemMeasurementModal.Tolerance_Range);
            contentValues.put("Digitals", itemMeasurementModal.Digitals);
            contentValues.put("recDt", AppConfig.getCurrntDate());
            contentValues.put("LocID", FClientConfig.LOC_ID);
            contentValues.put("recUser", userId);

            if (TextUtils.isEmpty(itemMeasurementModal.pRowID) || itemMeasurementModal.pRowID.equalsIgnoreCase("null")) {
                itemMeasurementModal.pRowID = GeneratePK(mContext, FEnumerations.E_TABLE_NAME_ItemMeasurement);
            }
            if (!TextUtils.isEmpty(itemMeasurementModal.pRowID) && itemMeasurementModal.pRowID.length() == 10) {
                int rows = database.update("QRPOItemDtl_ItemMeasurement", contentValues, "pRowID"
                        + " = '" + itemMeasurementModal.pRowID + "'", null);
                if (rows == 0) {
                    contentValues.put("pRowID", itemMeasurementModal.pRowID);
                    contentValues.put("QRHdrID", poItemDtl.QRHdrID);
                    contentValues.put("QRPOItemHdrID", poItemDtl.QRPOItemHdrID);
                    status = database.insert("QRPOItemDtl_ItemMeasurement", null, contentValues);
                    FslLog.d(TAG, " insert QRPOItemDtl_ItemMeasurement ");
                } else {
                    status = 1;
                    FslLog.d(TAG, " update QRPOItemDtl_ItemMeasurement ");
                }
            } else {
                FslLog.d(TAG, " could not update QRPOItemDtl_ItemMeasurement bcos pRowID is NULL ");
            }

            database.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        FslLog.d(TAG, "return ID from updating QRPOItemDtl_ItemMeasurement  " + itemMeasurementModal.pRowID);
        return itemMeasurementModal.pRowID;
    }

    public static void deleteFindingOld(Context mContext, String pRowIDForFinding) {
        DBHelper dbHelper = new DBHelper(mContext);
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        String query2 = " Delete From QRFindings Where MeasurementID = '" + pRowIDForFinding + "';";
        database.execSQL(query2);
        FslLog.d(TAG, " delete finding QRFinding..");
        database.close();
    }

    public static long updateFindingItemMeasurement(Context mContext, ItemMeasurementModal itemMeasurementModal, POItemDtl poItemDtl) {

        long status = 0;
        try {
            String userId = LogInHandler.getUserId(mContext);
            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getReadableDatabase();

            ContentValues contentValues = new ContentValues();


            contentValues.put("Descr", itemMeasurementModal.ItemMeasurementDescr);

            contentValues.put("New_Height", itemMeasurementModal.Dim_Height);
            contentValues.put("New_Width", itemMeasurementModal.Dim_Width);
            contentValues.put("New_Length", itemMeasurementModal.Dim_length);

            contentValues.put("OLD_Height", itemMeasurementModal.OLD_Height);
            contentValues.put("OLD_Width", itemMeasurementModal.OLD_Width);
            contentValues.put("OLD_Length", itemMeasurementModal.OLD_Length);
//            contentValues.put("SampleSizeValue", itemMeasurementModal.SampleSizeValue);
            contentValues.put("SampleSizeID", itemMeasurementModal.SampleSizeID);
//            contentValues.put("InspectionResultID", itemMeasurementModal.InspectionResultID);
            contentValues.put("LocID", FClientConfig.LOC_ID);
            contentValues.put("recUser", userId);
            contentValues.put("recDt", AppConfig.getCurrntDate());

            if (TextUtils.isEmpty(itemMeasurementModal.pRowIDForFinding) || itemMeasurementModal.pRowIDForFinding.equalsIgnoreCase("null")) {
                itemMeasurementModal.pRowIDForFinding = ItemInspectionDetailHandler.GeneratePK(mContext, "QRFindings");
            }

            int rows = database.update("QRFindings", contentValues, "pRowID"
                    + " = '" + itemMeasurementModal.pRowIDForFinding + "'", null);
            if (rows == 0) {
                if (!TextUtils.isEmpty(itemMeasurementModal.pRowIDForFinding)
                        && !itemMeasurementModal.pRowIDForFinding.equalsIgnoreCase("null")) {
                    contentValues.put("pRowID", itemMeasurementModal.pRowIDForFinding);
                    contentValues.put("MeasurementID", itemMeasurementModal.pRowID);
                    contentValues.put("QrHdrID", poItemDtl.QRHdrID);
                    contentValues.put("QRPOItemHdrID", poItemDtl.QRPOItemHdrID);
                    status = database.insert("QRFindings", null, contentValues);
                    FslLog.d(TAG, " insert QRFindings ");
                } else {
                    FslLog.d(TAG, " pRowID NULL in insert QRFindings ");
                }
            } else {
                status = 1;
                FslLog.d(TAG, " update QRFindings ");
            }

            database.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    public static List<ItemMeasurementModal> getFindingItemMeasurementList(Context mContext, ItemMeasurementModal itemMeasurementModal) {


        List<ItemMeasurementModal> itemArrayList = new ArrayList<ItemMeasurementModal>();
        try {
            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getReadableDatabase();

            String query = "Select *  From QRFindings  Where MeasurementID='" + itemMeasurementModal.pRowID + "'";

            FslLog.d(TAG, "get  query for  finding ItemMeasurement list  " + query);
            Cursor cursor = database.rawQuery(query, null);


            ItemMeasurementModal mItemMeasurementModal;
            if (cursor.getCount() > 0) {
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToNext();
                    mItemMeasurementModal = new ItemMeasurementModal();

                    mItemMeasurementModal.pRowIDForFinding = cursor.getString(cursor.getColumnIndex("pRowID"));
                    mItemMeasurementModal.QRHdrID = cursor.getString(cursor.getColumnIndex("QrHdrID"));
                    mItemMeasurementModal.QRPOItemHdrID = cursor.getString(cursor.getColumnIndex("QRPOItemHdrID"));
                    mItemMeasurementModal.ItemMeasurementDescr = cursor.getString(cursor.getColumnIndex("Descr"));

                    mItemMeasurementModal.Dim_Height = cursor.getDouble(cursor.getColumnIndex("New_Height"));
                    mItemMeasurementModal.Dim_Width = cursor.getDouble(cursor.getColumnIndex("New_Width"));
                    mItemMeasurementModal.Dim_length = cursor.getDouble(cursor.getColumnIndex("New_Length"));

                    mItemMeasurementModal.OLD_Height = cursor.getDouble(cursor.getColumnIndex("OLD_Height"));
                    mItemMeasurementModal.OLD_Width = cursor.getDouble(cursor.getColumnIndex("OLD_Width"));
                    mItemMeasurementModal.OLD_Length = cursor.getDouble(cursor.getColumnIndex("OLD_Length"));


//                    mItemMeasurementModal.SampleSizeValue = cursor.getString(cursor.getColumnIndex("SampleSizeValue"));
                    mItemMeasurementModal.SampleSizeID = cursor.getString(cursor.getColumnIndex("SampleSizeID"));
                    mItemMeasurementModal.pRowID = cursor.getString(cursor.getColumnIndex("MeasurementID"));


                    itemArrayList.add(mItemMeasurementModal);
                }
            }
            cursor.close();
            database.close();
            FslLog.d(TAG, " cout of founded list of item Measurement " + itemArrayList.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemArrayList;


    }

    public static List<ItemMeasurementModal> getItemMeasurementList(Context mContext, String pItemHdrID, String pInspectionID) {


        List<ItemMeasurementModal> itemArrayList = new ArrayList<ItemMeasurementModal>();
        try {
            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getReadableDatabase();

            String query = "Select   pRowID, LocID,ItemID, QRHdrID, QRPOItemHdrID," +
                    " ifNull(ItemMeasurementDescr,'') ItemMeasurementDescr," +
                    " IfNUll(Dim_Height,0) Dim_Height,Ifnull(Dim_Width,0) Dim_Width,Ifnull(Dim_length,0) Dim_length," +
                    " Ifnull(SampleSizeID,'') SampleSizeID, Ifnull(SampleSizeValue,'') SampleSizeValue, Ifnull(Finding,'0') Finding," +
                    " Ifnull(InspectionResultID,'') InspectionResultID, Ifnull(Tolerance_Range,'') Tolerance_Range, recEnable, recAddDt, recDt, recUser," +
                    " IFNULL(Digitals,'') As Digitals  From QRPOItemDtl_ItemMeasurement  Where QRHDrID='" + pItemHdrID + "'and QRPOItemHdrID='" + pInspectionID + "' and recEnable=1";


            /* String query = "SELECT * FROM " + "QRPOItemdtl" + " where QRHdrID='" + pRowID + "'";*/
            FslLog.d(TAG, "get  query for  get ItemMeasurement list  " + query);
            Cursor cursor = database.rawQuery(query, null);


            ItemMeasurementModal itemMeasurementModal;
            if (cursor.getCount() > 0) {
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToNext();
                    itemMeasurementModal = new ItemMeasurementModal();
                    itemMeasurementModal.pRowID = cursor.getString(cursor.getColumnIndex("pRowID"));
                    itemMeasurementModal.QRHdrID = cursor.getString(cursor.getColumnIndex("QRHdrID"));
                    itemMeasurementModal.QRPOItemHdrID = cursor.getString(cursor.getColumnIndex("QRPOItemHdrID"));
                    itemMeasurementModal.ItemMeasurementDescr = cursor.getString(cursor.getColumnIndex("ItemMeasurementDescr"));

                    itemMeasurementModal.Dim_Height = cursor.getDouble(cursor.getColumnIndex("Dim_Height"));
                    itemMeasurementModal.Dim_Width = cursor.getDouble(cursor.getColumnIndex("Dim_Width"));
                    itemMeasurementModal.Dim_length = cursor.getDouble(cursor.getColumnIndex("Dim_length"));

                    itemMeasurementModal.OLD_Height = cursor.getDouble(cursor.getColumnIndex("Dim_Height"));
                    itemMeasurementModal.OLD_Width = cursor.getDouble(cursor.getColumnIndex("Dim_Width"));
                    itemMeasurementModal.OLD_Length = cursor.getDouble(cursor.getColumnIndex("Dim_length"));


                    itemMeasurementModal.SampleSizeValue = cursor.getString(cursor.getColumnIndex("SampleSizeValue"));
                    itemMeasurementModal.SampleSizeID = cursor.getString(cursor.getColumnIndex("SampleSizeID"));
                    itemMeasurementModal.InspectionResultID = cursor.getString(cursor.getColumnIndex("InspectionResultID"));
                    itemMeasurementModal.Tolerance_Range = cursor.getString(cursor.getColumnIndex("Tolerance_Range"));
                    itemMeasurementModal.Digitals = cursor.getString(cursor.getColumnIndex("Digitals"));
                    if (!TextUtils.isEmpty(itemMeasurementModal.Digitals) && !itemMeasurementModal.Digitals.equals("null")) {
                        String[] spStr = itemMeasurementModal.Digitals.split(",");
                        if (spStr != null && spStr.length > 0) {
                            for (int j = 0; j < spStr.length; j++) {
                                String rowId = spStr[j];
                                if (!TextUtils.isEmpty(rowId)) {
                                    DigitalsUploadModal digitalsUploadModal = ItemInspectionDetailHandler.getImageAccordingToItem(mContext, rowId, itemMeasurementModal.pRowID);
                                    if (digitalsUploadModal != null) {
                                        itemMeasurementModal.listImages.add(digitalsUploadModal);
                                    }
                                }
                            }

                        }

                    }

                    itemArrayList.add(itemMeasurementModal);
                }
            }
            cursor.close();
            database.close();
            FslLog.d(TAG, " cout of founded list of item Measurement " + itemArrayList.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemArrayList;
    }


    public static List<ItemMeasurementModal> getItemMeasurementHistoryList(Context mContext, String pItemHdrID) {


        List<ItemMeasurementModal> itemArrayList = new ArrayList<ItemMeasurementModal>();
        try {
            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getReadableDatabase();

            String query = "SELECT Ifnull(QrItem.QrhdrID,'') as QrhdrID,Ifnull(QrHistory.ActivityDs,'') as ActivityDs" +
                    ",  QrHistory.RefQRHdrID, ifnull(QrHistory.InspectionDt,'') as InspectionDt, " +
                    " QrItem.pRowID, QrItem.LocID,QrItem.ItemID, QrItem.QRHdrID, QrItem.QRPOItemHdrID, ifNull(QrItem.ItemMeasurementDescr,'') ItemMeasurementDescr, " +
                    " IfNUll(QrItem.Dim_Height,0) Dim_Height,Ifnull(QrItem.Dim_Width,0) Dim_Width,Ifnull(QrItem.Dim_length,0) Dim_length, Ifnull(QrItem.SampleSizeID,'') SampleSizeID," +
                    "  Ifnull(QrItem.SampleSizeValue,'') SampleSizeValue, Ifnull(QrItem.Finding,'0') Finding, Ifnull(QrItem.InspectionResultID,'') InspectionResultID,  Ifnull(Tolerance_Range,'')" +
                    " Tolerance_Range, recEnable, recAddDt, recDt, recUser from    QRPOItemDtl_ItemMeasurement QrItem" +
                    " Inner JOin qrinspectionHistory QrHistory on QrHistory.QRPOItemHdrId=QrItem.QRPOItemHdrID where  QrItem.recEnable=1 and ifnull(QrItem.BE_pRowID,'')<>'' " +
                    "  And QrItem.QRPOItemHdrID = (Select QRPOItemHdrID From" +
                    " QRInspectionHistory Where RefQRPOItemHdrID = '" + pItemHdrID + "' Order by QRPOItemHdrID Desc Limit 1)";

            /* String query = "SELECT * FROM " + "QRPOItemdtl" + " where QRHdrID='" + pRowID + "'";*/
            FslLog.d(TAG, "get  query for  get ItemMeasurement list  " + query);
            Cursor cursor = database.rawQuery(query, null);


            ItemMeasurementModal itemMeasurementModal;
            if (cursor.getCount() > 0) {
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToNext();
                    itemMeasurementModal = new ItemMeasurementModal();
                    itemMeasurementModal.pRowID = cursor.getString(cursor.getColumnIndex("QrhdrID"));
                    itemMeasurementModal.QRHdrID = cursor.getString(cursor.getColumnIndex("QRHdrID"));
                    itemMeasurementModal.QRPOItemHdrID = cursor.getString(cursor.getColumnIndex("QRPOItemHdrID"));
                    itemMeasurementModal.ItemMeasurementDescr = cursor.getString(cursor.getColumnIndex("ItemMeasurementDescr"));

                    itemMeasurementModal.Dim_Height = cursor.getDouble(cursor.getColumnIndex("Dim_Height"));
                    itemMeasurementModal.Dim_Width = cursor.getDouble(cursor.getColumnIndex("Dim_Width"));
                    itemMeasurementModal.Dim_length = cursor.getDouble(cursor.getColumnIndex("Dim_length"));

                    itemMeasurementModal.OLD_Height = cursor.getDouble(cursor.getColumnIndex("Dim_Height"));
                    itemMeasurementModal.OLD_Width = cursor.getDouble(cursor.getColumnIndex("Dim_Width"));
                    itemMeasurementModal.OLD_Length = cursor.getDouble(cursor.getColumnIndex("Dim_length"));


                    itemMeasurementModal.SampleSizeValue = cursor.getString(cursor.getColumnIndex("SampleSizeValue"));
                    itemMeasurementModal.SampleSizeID = cursor.getString(cursor.getColumnIndex("SampleSizeID"));
                    itemMeasurementModal.InspectionResultID = cursor.getString(cursor.getColumnIndex("InspectionResultID"));
                    itemMeasurementModal.Tolerance_Range = cursor.getString(cursor.getColumnIndex("Tolerance_Range"));
                    itemMeasurementModal.Activity = cursor.getString(cursor.getColumnIndex("ActivityDs"));
                    itemMeasurementModal.InspectionDate = cursor.getString(cursor.getColumnIndex("InspectionDt"));


                    itemArrayList.add(itemMeasurementModal);
                }
            }
            cursor.close();
            database.close();
            FslLog.d(TAG, " cout of founded list of item Measurement " + itemArrayList.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemArrayList;
    }


    public static String updateWorkmanShip(Context mContext, String QRHdrID, String QRPOItemHdrID, String pItemID,
                                           WorkManShipModel workManShipModel) {

        String userId = LogInHandler.getUserId(mContext);

        DBHelper dbHelper = new DBHelper(mContext);
        SQLiteDatabase database = dbHelper.getWritableDatabase();


        ContentValues contentValues = new ContentValues();


        contentValues.put("DefectID", "");
        contentValues.put("DefectCode", workManShipModel.Code);
        contentValues.put("DefectName", workManShipModel.Description);

        contentValues.put("DCLTypeID", 0);
        contentValues.put("DefectComments", workManShipModel.Comments);
        contentValues.put("CriticalDefect", workManShipModel.Critical);
        contentValues.put("MajorDefect", workManShipModel.Major);
        contentValues.put("MinorDefect", workManShipModel.Minor);
        contentValues.put("CriticalType", 1);

        contentValues.put("MajorType", 1);
        contentValues.put("MinorType", 1);
        contentValues.put("SampleRqstCriticalRowID", "");
        contentValues.put("POItemHdrCriticalRowID", "");
        contentValues.put("Digitals", workManShipModel.Digitals);

        contentValues.put("recAddDt", AppConfig.getCurrntDate());
        contentValues.put("recDt", AppConfig.getCurrntDate());
        contentValues.put("recAddUser", userId);
        contentValues.put("recUser", userId);
        contentValues.put("LocID", "DEL");

        int rows = database.update("QRAuditBatchDetails", contentValues, "pRowID"
                + " = '" + workManShipModel.pRowID + "'", null);

        int mStatus = 0;
        if (rows == 0) {
            if (TextUtils.isEmpty(workManShipModel.pRowID)
                    || workManShipModel.pRowID.equalsIgnoreCase("null")) {
                workManShipModel.pRowID = ItemInspectionDetailHandler.GeneratePK(mContext, FEnumerations.E_TABLE_NAME_AuditBatchDetails);
            }
            if (!TextUtils.isEmpty(workManShipModel.pRowID)
                    && !workManShipModel.pRowID.equalsIgnoreCase("null")) {
                contentValues.put("pRowID", workManShipModel.pRowID);
                contentValues.put("LocID", "DEL");
                contentValues.put("recAddUser", userId);
                contentValues.put("recUser", userId);
                contentValues.put("QRHdrID", QRHdrID);
                contentValues.put("QRPOItemHdrID", QRPOItemHdrID);
                contentValues.put("ItemID", pItemID);

                long status = database.insert("QRAuditBatchDetails", null, contentValues);
                if (status > 0) {
                    mStatus = 2;
                }

                FslLog.d(TAG, "insert QRAuditBatchDetails successfully");

            } else {
                FslLog.d(TAG, "pRowID is NULL inserting QRAuditBatchDetails  ");
            }
            database.close();


        } else {
            FslLog.d(TAG, "update QRAuditBatchDetails successfully  ");
            mStatus = 1;
        }
        FslLog.d(TAG, "return ID from updating QRAuditBatchDetails  " + workManShipModel.pRowID);
        return workManShipModel.pRowID;
    }

    public static int updateDigitalFromWorkmanShip(Context mContext, WorkManShipModel workManShipModel) {


        DBHelper dbHelper = new DBHelper(mContext);
        SQLiteDatabase database = dbHelper.getWritableDatabase();


        ContentValues contentValues = new ContentValues();


        contentValues.put("Digitals", workManShipModel.Digitals);

        contentValues.put("recAddDt", AppConfig.getCurrntDate());
        contentValues.put("recDt", AppConfig.getCurrntDate());


        int rows = database.update("QRAuditBatchDetails", contentValues, "pRowID"
                + " = '" + workManShipModel.pRowID + "'", null);

        int mStatus = 0;
        if (rows == 0) {
            FslLog.d(TAG, "NOT updated  ");
        } else {
            FslLog.d(TAG, "update digital QRAuditBatchDetails successfully  ");
            mStatus = 1;
        }
        database.close();
        return mStatus;
    }


    public static List<WorkManShipModel> getWorkmanShip(Context mContext, String QRHdrID, String QRPOItemHdrID, String pItemID) {
        List<WorkManShipModel> itemArrayList = new ArrayList<WorkManShipModel>();


        try {
            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getReadableDatabase();

            String query = "Select  pRowID, LocID,  QRHdrID, QRPOItemHdrID, ItemID,   DefectID," +
                    "DefectCode, DefectName, DCLTypeID, DefectComments, " +
                    "   CriticalDefect, MajorDefect, MinorDefect, CriticalType, MajorType, MinorType," +
                    "   SampleRqstCriticalRowID, POItemHdrCriticalRowID, Digitals, recAddDt, recDt, recAddUser, recUser from  QRAuditBatchDetails where " +
                    "  QRHdrID=  '" + QRHdrID + "' and QRPOItemHdrID= '" + QRPOItemHdrID + "'";// and ItemID= '" + pItemID + "'";

            FslLog.d(TAG, "get  query for  workmanship list  " + query);
            Cursor cursor = database.rawQuery(query, null);
            WorkManShipModel workManShipModel;
            if (cursor.getCount() > 0) {
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToNext();
                    workManShipModel = new WorkManShipModel();

                    workManShipModel.pRowID = cursor.getString(cursor.getColumnIndex("pRowID"));
                    workManShipModel.Code = cursor.getString(cursor.getColumnIndex("DefectCode"));
                    workManShipModel.Description = cursor.getString(cursor.getColumnIndex("DefectName"));
                    workManShipModel.Digitals = cursor.getString(cursor.getColumnIndex("Digitals"));
                    workManShipModel.Critical = cursor.getInt(cursor.getColumnIndex("CriticalDefect"));
                    workManShipModel.Major = cursor.getInt(cursor.getColumnIndex("MajorDefect"));
                    workManShipModel.Minor = cursor.getInt(cursor.getColumnIndex("MinorDefect"));
                    workManShipModel.Comments = cursor.getString(cursor.getColumnIndex("DefectComments"));
                    if (!TextUtils.isEmpty(workManShipModel.Digitals) && !workManShipModel.Digitals.equals("null")) {
                        String[] spStr = workManShipModel.Digitals.split(",");
                        if (spStr != null && spStr.length > 0) {
                            for (int j = 0; j < spStr.length; j++) {
                                String rowId = spStr[j];
                                if (!TextUtils.isEmpty(rowId)) {
                                    DigitalsUploadModal digitalsUploadModal = ItemInspectionDetailHandler.getImageAccordingToItem(mContext, rowId, workManShipModel.pRowID);
                                    if (digitalsUploadModal != null) {
                                        workManShipModel.listImages.add(digitalsUploadModal);
                                    }
                                }
                            }

                        }

                    }
                    itemArrayList.add(workManShipModel);
                }
            }

            cursor.close();
            database.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        FslLog.d(TAG, " cout of founded list of workmanship " + itemArrayList.size());
        return itemArrayList;
    }

    public static List<String> getWorkmanShipDescriptionList(Context mContext) {
        List<String> itemArrayList = new ArrayList<String>();


        try {
            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getReadableDatabase();

//            String query = "Select  Distinct DefectName    from  QRAuditBatchDetails";// and ItemID= '" + pItemID + "'";
//            String query = "Select  Distinct DefectName    from  DefectMaster";// and ItemID= '" + pItemID + "'";
            String query = "Select * from QRPOItemDtl_ItemMeasurement";// and ItemID= '" + pItemID + "'";

            FslLog.d(TAG, "get  query for  workmanship list  " + query);
            Cursor cursor = database.rawQuery(query, null);

            if (cursor.getCount() > 0) {
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToNext();
                    String str = cursor.getString(cursor.getColumnIndex("DefectName"));
                    itemArrayList.add(str);
                }
            }

            cursor.close();
            database.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        FslLog.d(TAG, " cout of defect list of workmanship " + itemArrayList.size());
        return itemArrayList;
    }


    public static List<WorkManShipModel> getWorkmanShipHistory(Context mContext, String QRPOItemHdrID) {
        List<WorkManShipModel> itemArrayList = new ArrayList<WorkManShipModel>();


        try {
            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getReadableDatabase();

            String query = "SELECT distinct Ifnull(QRDtl.QrhdrID,'')" +
                    " as QrhdrID,Ifnull(QrHostory.ActivityDs,'') as ActivityDs , " +
                    "QrHostory.RefQRHdrID, " +
                    " ifnull(QrHostory.InspectionDt,'') as InspectionDt, " +
                    "  IfNULL(QRDtl.pRowID,'') As pRowID, IfNULL(DM.pRowID,'') As DefectID, " +
                    "IfNULL(DM.Code,ifnull(QRDtl.DefectCode,'New')) As DefectCode,  " +
                    " IfNULL(DM.DefectName,IfNULL(QRDtl.DefectName,'')) As DefectName, " +
                    "IfNULL(DM.DCLTypeID,IfNULL(QRDtl.DCLTypeID,'')) As DCLTypeID,  " +
                    " IfNULL(gdDCLType.MainDescr,'[General]') As DCLTypeDs," +
                    " IfNULL(gdDCLType.numVal1,0) As DCLTypeSeq,IfNULL(QRDtl.DefectComments,'')" +
                    " As DefectComments,   IfNULL(QRDtl.CriticalDefect,0) As CriticalDefect," +
                    " IfNULL(QRDtl.MajorDefect,0) As MajorDefect,   IfNULL(QRDtl.MinorDefect,0)" +
                    " As MinorDefect, (IfNULL(QRDtl.CriticalDefect,0)+IfNULL(QRDtl.MajorDefect,0)+IfNULL(QRDtl.MinorDefect,0)) As TotalDefects, " +
                    "  IfNULL(QRDtl.CriticalType, DM.ChkCritical) As CriticalType, IfNULL(QRDtl.MajorType, DM.ChkMajor) As MajorType," +
                    "   IfNULL(QRDtl.MinorType, DM.ChkMinor) As MinorType, IfNULL(QRDtl.SampleRqstCriticalRowID,'') As SampleRqstCriticalRowID,   ''" +
                    " As POItemHdrCriticalRowID, '' As DefectMessage,IfNULL(QRDtl.Digitals,'') As Digitals, 0 As recDirty, 0 As RemoveFlag" +
                    "   FROM QRAuditBatchDetails QRDtl Inner Join QrinspectionHistory QrHostory on  QrHostory.QrPOItemHdrId=QRDtl.QrPOItemHdrID" +
                    " LEFT JOIN DefectMaster DM on DM.pRowID = QRDtl.DefectID   LEFT JOIN GenMst gdDCLType on gdDCLType.pGenRowID = IfNULL(DM.DCLTypeID,QRDtl.DCLTypeID) " +
                    "   WHERE ifNull(QRDtl.SampleRqstCriticalRowID,'')='' AND Ifnull(QRDtl.POItemHdrCriticalRowID,'')=''   and ifnull(BE_pRowID,'')<>'' " +
                    "  And QRDtl.QRPOItemHdrID = (Select QRPOItemHdrID From QRInspectionHistory" +
                    " Where RefQRPOItemHdrID = '" + QRPOItemHdrID + "' Order by QRPOItemHdrID Desc Limit 1)";

            FslLog.d(TAG, "get  query for  workmanship history list  " + query);
            Cursor cursor = database.rawQuery(query, null);
            WorkManShipModel workManShipModel;
            if (cursor.getCount() > 0) {
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToNext();
                    workManShipModel = new WorkManShipModel();

                    workManShipModel.pRowID = cursor.getString(cursor.getColumnIndex("QrhdrID"));
                    workManShipModel.Code = cursor.getString(cursor.getColumnIndex("DefectCode"));
                    workManShipModel.Description = cursor.getString(cursor.getColumnIndex("DefectName"));
                    workManShipModel.Digitals = cursor.getString(cursor.getColumnIndex("Digitals"));
                    workManShipModel.Critical = cursor.getInt(cursor.getColumnIndex("CriticalDefect"));
                    workManShipModel.Major = cursor.getInt(cursor.getColumnIndex("MajorDefect"));
                    workManShipModel.Minor = cursor.getInt(cursor.getColumnIndex("MinorDefect"));
                    workManShipModel.Comments = cursor.getString(cursor.getColumnIndex("DefectComments"));
                    workManShipModel.Activity = cursor.getString(cursor.getColumnIndex("ActivityDs"));
                    workManShipModel.InspectionDate = cursor.getString(cursor.getColumnIndex("InspectionDt"));
                    if (!TextUtils.isEmpty(workManShipModel.Digitals) && !workManShipModel.Digitals.equals("null")) {
                        String[] spStr = workManShipModel.Digitals.split(",");
                        if (spStr != null && spStr.length > 0) {
                            for (int j = 0; j < spStr.length; j++) {
                                String rowId = spStr[j];
                                if (!TextUtils.isEmpty(rowId)) {
                                    DigitalsUploadModal digitalsUploadModal = ItemInspectionDetailHandler.getImage(mContext, rowId);
                                    if (digitalsUploadModal != null) {
                                        workManShipModel.listImages.add(digitalsUploadModal);
                                    }
                                }
                            }

                        }

                    }
                    itemArrayList.add(workManShipModel);
                }
            }

            cursor.close();
            database.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        FslLog.d(TAG, " cout of founded list of workmanship " + itemArrayList.size());
        return itemArrayList;
    }

    public static List<WorkManShipModel> getDigitalsFromWorkmanShip(Context mContext, String wRowID) {
        List<WorkManShipModel> itemArrayList = new ArrayList<WorkManShipModel>();


        try {
            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getReadableDatabase();

            String query = "Select  pRowID, Digitals from  QRAuditBatchDetails where " +
                    "  pRowID=  '" + wRowID + "'";// and ItemID= '" + pItemID + "'";

            FslLog.d(TAG, "get  query for  workmanship list  " + query);
            Cursor cursor = database.rawQuery(query, null);
            WorkManShipModel workManShipModel;
            if (cursor.getCount() > 0) {
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToNext();
                    workManShipModel = new WorkManShipModel();

                    workManShipModel.pRowID = cursor.getString(cursor.getColumnIndex("pRowID"));
                    workManShipModel.Digitals = cursor.getString(cursor.getColumnIndex("Digitals"));


                    itemArrayList.add(workManShipModel);

                }
            }

            cursor.close();
            database.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        FslLog.d(TAG, " cout of founded digital list from QRAuditBatchDetails " + itemArrayList.size());
        return itemArrayList;
    }

    public static List<ItemMeasurementModal> getDigitalsFromItemMeasurementShip(Context mContext, String wRowID) {
        List<ItemMeasurementModal> itemArrayList = new ArrayList<ItemMeasurementModal>();


        try {
            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getReadableDatabase();

            String query = "Select  pRowID, Digitals from  QRPOItemDtl_ItemMeasurement where " +
                    "  pRowID=  '" + wRowID + "'";// and ItemID= '" + pItemID + "'";

            FslLog.d(TAG, "get  query for  workmanship list  " + query);
            Cursor cursor = database.rawQuery(query, null);
            ItemMeasurementModal workManShipModel;
            if (cursor.getCount() > 0) {
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToNext();
                    workManShipModel = new ItemMeasurementModal();

                    workManShipModel.pRowID = cursor.getString(cursor.getColumnIndex("pRowID"));
                    workManShipModel.Digitals = cursor.getString(cursor.getColumnIndex("Digitals"));


                    itemArrayList.add(workManShipModel);

                }
            }

            cursor.close();
            database.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        FslLog.d(TAG, " cout of founded digital list from QRPOItemDtl_ItemMeasurement " + itemArrayList.size());
        return itemArrayList;
    }

    public static int updateDigitalFromItemmeasurement(Context mContext, ItemMeasurementModal itemMeasurementModal) {


        DBHelper dbHelper = new DBHelper(mContext);
        SQLiteDatabase database = dbHelper.getWritableDatabase();


        ContentValues contentValues = new ContentValues();


        contentValues.put("Digitals", itemMeasurementModal.Digitals);

        contentValues.put("recAddDt", AppConfig.getCurrntDate());
        contentValues.put("recDt", AppConfig.getCurrntDate());


        int rows = database.update("QRPOItemDtl_ItemMeasurement", contentValues, "pRowID"
                + " = '" + itemMeasurementModal.pRowID + "'", null);

        int mStatus = 0;
        if (rows == 0) {
            FslLog.d(TAG, "NOT updated  ");
        } else {
            FslLog.d(TAG, "update digital QRPOItemDtl_ItemMeasurement successfully  ");
            mStatus = 1;
        }
        database.close();
        return mStatus;
    }

    public static List<String> getImagesList(Context mContext, String pItemHdrID, String pInspectionID, String pItemID) {
        List<String> itemArrayList = new ArrayList<String>();


        try {
            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getReadableDatabase();
            String query = "Select * from QRPOItemDtl_Image where QRHdrID='" + pInspectionID + "' and QRPOItemHdrID='" + pItemHdrID + "'";
            FslLog.d(TAG, "get  query for  image list  " + query);
            Cursor cursor = database.rawQuery(query, null);

            if (cursor.getCount() > 0) {
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToNext();
                    String img = null;
                    if (cursor.getString(cursor.getColumnIndex("pRowID")).contains("DEL")) {
//                                digitalsUploadModal.ImageName = cursor.getString(cursor.getColumnIndex("ImageName"));
                    } else {
                        img = cursor.getString(cursor.getColumnIndex("ImageName"));
                    }


                    itemArrayList.add(img);
//                    cursorImage.close();
                }
            }

            cursor.close();
            database.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        FslLog.d(TAG, " cout of founded list of workmanship " + itemArrayList.size());
        return itemArrayList;
    }

    public static DigitalsUploadModal getImage(Context mContext, String pRowID) {
        DigitalsUploadModal digitalsUploadModal = null;


        try {
            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getReadableDatabase();
            String query = "Select * from QRPOItemDtl_Image where pRowID='" + pRowID.trim() + "'";
            FslLog.d(TAG, "get  query for  image   " + query);
            Cursor cursor = database.rawQuery(query, null);

            if (cursor.getCount() > 0) {
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToNext();
                    digitalsUploadModal = new DigitalsUploadModal();
                    digitalsUploadModal.pRowID = cursor.getString(cursor.getColumnIndex("pRowID"));
                    digitalsUploadModal.selectedPicPath = cursor.getString(cursor.getColumnIndex("fileContent"));
                    digitalsUploadModal.ImageName = cursor.getString(cursor.getColumnIndex("ImageName"));
                    digitalsUploadModal.title = cursor.getString(cursor.getColumnIndex("Title"));
                    digitalsUploadModal.ImageExtn = cursor.getString(cursor.getColumnIndex("ImageExtn"));
                    digitalsUploadModal.ImageName = cursor.getString(cursor.getColumnIndex("ImageName"));

                }
            }

            cursor.close();
            database.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return digitalsUploadModal;
    }

    public static DigitalsUploadModal getImageAccordingToItem(Context mContext, String pRowID, String ItemID) {
        DigitalsUploadModal digitalsUploadModal = null;


        try {
            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getReadableDatabase();
            String query = "Select * from QRPOItemDtl_Image where pRowID='" + pRowID.trim() + "'";
            if (!TextUtils.isEmpty(ItemID) && !"null".equalsIgnoreCase(ItemID)) {
                query = query + " and ItemID='" + ItemID + "'";
            }
            FslLog.d(TAG, "get  query for  image   " + query);
            Cursor cursor = database.rawQuery(query, null);

            if (cursor.getCount() > 0) {
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToNext();
                    digitalsUploadModal = new DigitalsUploadModal();
                    digitalsUploadModal.pRowID = cursor.getString(cursor.getColumnIndex("pRowID"));
                    digitalsUploadModal.selectedPicPath = cursor.getString(cursor.getColumnIndex("fileContent"));
                    digitalsUploadModal.ImageName = cursor.getString(cursor.getColumnIndex("ImageName"));
                    digitalsUploadModal.title = cursor.getString(cursor.getColumnIndex("Title"));
                    digitalsUploadModal.ImageExtn = cursor.getString(cursor.getColumnIndex("ImageExtn"));
                    digitalsUploadModal.ImageName = cursor.getString(cursor.getColumnIndex("ImageName"));

                }
            }

            cursor.close();
            database.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return digitalsUploadModal;
    }

    public static DigitalsUploadModal getImageQuality(Context mContext, String QRHdrID, String QRPOItemHdrID, String pRowID, String ItemID) {
        DigitalsUploadModal digitalsUploadModal = null;

        try {
            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getReadableDatabase();
            String query = "Select * from QRPOItemDtl_Image where pRowID='" + pRowID.trim() + "' and QRHdrID='" + QRHdrID + "' and QRPOItemHdrID ='" + QRPOItemHdrID + "'";
            if (!TextUtils.isEmpty(ItemID) && !"null".equalsIgnoreCase(ItemID)) {
                query = query + " and ItemID='" + ItemID + "'";
            }
            FslLog.d(TAG, "get  query for  image   " + query);
            Cursor cursor = database.rawQuery(query, null);

            if (cursor.getCount() > 0) {
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToNext();
                    digitalsUploadModal = new DigitalsUploadModal();
                    digitalsUploadModal.pRowID = cursor.getString(cursor.getColumnIndex("pRowID"));
                    digitalsUploadModal.selectedPicPath = cursor.getString(cursor.getColumnIndex("fileContent"));
                    digitalsUploadModal.ImageName = cursor.getString(cursor.getColumnIndex("ImageName"));
                    digitalsUploadModal.title = cursor.getString(cursor.getColumnIndex("Title"));
                    digitalsUploadModal.ImageExtn = cursor.getString(cursor.getColumnIndex("ImageExtn"));
                    digitalsUploadModal.ImageName = cursor.getString(cursor.getColumnIndex("ImageName"));

                }
            }

            cursor.close();
            database.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return digitalsUploadModal;
    }

    public static DigitalsUploadModal getImageQualityInspectionLevel(Context mContext, String QRHdrID, String pRowID, String ItemID) {
        DigitalsUploadModal digitalsUploadModal = null;

        try {
            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getReadableDatabase();
            String query = "Select * from QRPOItemDtl_Image where pRowID='" + pRowID.trim() + "' and QRHdrID='" + QRHdrID + "'";
            if (!TextUtils.isEmpty(ItemID) && !"null".equalsIgnoreCase(ItemID)) {
                query = query + " and ItemID='" + ItemID + "'";
            }
            FslLog.d(TAG, "get  query for  image   " + query);
            Cursor cursor = database.rawQuery(query, null);

            if (cursor.getCount() > 0) {
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToNext();
                    digitalsUploadModal = new DigitalsUploadModal();
                    digitalsUploadModal.pRowID = cursor.getString(cursor.getColumnIndex("pRowID"));
                    digitalsUploadModal.selectedPicPath = cursor.getString(cursor.getColumnIndex("fileContent"));
                    digitalsUploadModal.ImageName = cursor.getString(cursor.getColumnIndex("ImageName"));
                    digitalsUploadModal.title = cursor.getString(cursor.getColumnIndex("Title"));
                    digitalsUploadModal.ImageExtn = cursor.getString(cursor.getColumnIndex("ImageExtn"));
                    digitalsUploadModal.ImageName = cursor.getString(cursor.getColumnIndex("ImageName"));

                }
            }

            cursor.close();
            database.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return digitalsUploadModal;
    }


    public static boolean deleteDigitalUploaded(Context mContext, String pRowID) {

        String userId = LogInHandler.getUserId(mContext);

        DBHelper dbHelper = new DBHelper(mContext);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        String query = "DELETE FROM  QRPOItemDtl_Image WHERE pRowID='" + pRowID + "'";

        FslLog.d(TAG, "get  query for delete QRPOItemDtl_Image  " + query);
        Cursor cursor = database.rawQuery(query, null);
        if (cursor.getCount() > 0) {
            FslLog.d(TAG, "DELETED QRPOItemDtl_Image  ");

        }
        cursor.close();
        database.close();
        return true;
    }

    public static boolean deleteWorkmanship(Context mContext, String pRowID) {

        String userId = LogInHandler.getUserId(mContext);

        DBHelper dbHelper = new DBHelper(mContext);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        String query = "DELETE FROM  QRAuditBatchDetails WHERE pRowID='" + pRowID + "'";

        FslLog.d(TAG, "get  query for delete QRAuditBatchDetails  " + query);
        Cursor cursor = database.rawQuery(query, null);
        if (cursor.getCount() > 0) {
            FslLog.d(TAG, "DELETED QRAuditBatchDetails  ");

        }
        cursor.close();
        database.close();
        return true;
    }

    public static boolean deletItemmeasurement(Context mContext, String pRowID) {

        DBHelper dbHelper = new DBHelper(mContext);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        String query = "DELETE FROM  QRPOItemDtl_ItemMeasurement WHERE pRowID='" + pRowID + "'";

        FslLog.d(TAG, "get  query for delete QRPOItemDtl_ItemMeasurement  " + query);
        database.execSQL(query);
//        Cursor cursor = database.rawQuery(query, null);
//        if (cursor.getCount() > 0) {
//            FslLog.d(TAG, "DELETED QRPOItemDtl_ItemMeasurement  ");
//
//        }
//        cursor.close();
        database.close();
        return true;
    }

    public static boolean deletItemMeasurementOnNullPRowID(Context mContext) {

        try {
            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getWritableDatabase();

            String query = "DELETE FROM  QRPOItemDtl_ItemMeasurement WHERE pRowID is null or pRowID ='null'";

            FslLog.d(TAG, "get  query for pRowID is null delete QRPOItemDtl_ItemMeasurement  " + query);
            database.execSQL(query);

            database.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }


    public static boolean deletFindingItemMeasurement(Context mContext, String pRowID) {

        DBHelper dbHelper = new DBHelper(mContext);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        String query = "DELETE FROM  QRFindings WHERE pRowID='" + pRowID + "'";

        FslLog.d(TAG, "get  query for delete QRFindings  " + query);
        Cursor cursor = database.rawQuery(query, null);
        if (cursor.getCount() > 0) {
            FslLog.d(TAG, "DELETED QRFindings  ");
        }
        cursor.close();
        database.close();
        return true;
    }

    public static List<QualityParameter> getListQualityParameter(Context mContext, InspectionModal inspectionModal, String QRHdrID, String QRPOItemHdrID) {
        List<QualityParameter> qualityParameters = new ArrayList<>();
        try {
            String strWhere = " AND gm.numVal2 < 5  ";
            if (!TextUtils.isEmpty(inspectionModal.ActivityID)) {
                String[] spStr = inspectionModal.ActivityID.split("SYS");
                if (spStr != null && spStr.length > 0) {
                    String afterSplite = spStr[1];
                    if (!TextUtils.isEmpty(afterSplite)) {
                        int iVal = Integer.parseInt(afterSplite.trim());
                        if (iVal > 0) {
                            String makedNum = " AND gm.numVal" + (iVal + 2) + " > 0  ";
                            strWhere = makedNum;
                        }
                    }
                }
            }

            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getReadableDatabase();
            String query = "SELECT IfNULL(QRParam.pRowID,'') As pRowID,   QRParam.QualityParameterID As QualityParameterID, " +
                    "  IfNULL(gm.MainDescr,'') As MainDescr,  IfNULL(gm.Abbrv,'') As Abbrv,   " +
                    " IfNULL(gm.chrVal2,'') As OptionValue,  IfNULL(gm.numVal1,0) As PromptType, " +
                    "  IfNULL(gm.numVal2,0) As Position,   IfNULL(QRParam.IsApplicable,0) As IsApplicable,  " +
                    " IfNULL(QRParam.OptionSelected,0) As OptionSelected, " +
                    " IfNULL(QRParam.Remarks,'') As Remarks, 0 As recDirty, " +
                    "  IFNULL(Cast(gm.chrVal3 As Integer),0) As ImageRequired, " +
                    "IFNULL(Digitals,'') As Digitals  " +
                    " FROM GenMst gm " +
                    " INNER JOIN QRQualiltyParameterFields QRParam on QRParam.QualityParameterID = gm.pGenRowID  " +
                    "  WHERE gm.GenId = '555' And QRParam.QRHdrID = '" + QRHdrID + "'   " +
                    "    AND IfNULL(QRParam.QRPOItemHdrID,'') = '" + QRPOItemHdrID + "' AND gm.numVal2 > 5" + strWhere +
                    "      UNION   SELECT '' As pRowID,  gm.pGenRowID As QRParamID, IfNULL(gm.MainDescr,'') As Name, " +
                    "   IfNULL(gm.Abbrv,'') As Caption, IfNULL(gm.chrVal2,'') As OptionValue, " +
                    "  IfNULL(gm.numVal1,0) As PromptType, " +
                    "IfNULL(gm.numVal2,0) As Position,    0 As IsApplicable, 0 As OptionSelected, '' As Remarks, 0 As recDirty, " +
                    "  IFNULL(Cast(gm.chrVal3 As Integer),0) As ImageRequired, '' As Digitals   FROM GenMst gm " +
                    " WHERE gm.GenId = '555' AND gm.recEnable = 1   AND gm.numVal2 > 5   " + strWhere +
                    "     AND gm.pGenRowID NOT IN ( SELECT QRParam.QualityParameterID FROM QRQualiltyParameterFields QRParam " +
                    "  WHERE QRParam.QualityParameterID = gm.pGenRowID AND QRParam.QRHdrID = '" + QRHdrID + "' AND " +
                    "  IfNULL(QRParam.QRPOItemHdrID,'') = '" + QRPOItemHdrID + "'   AND gm.numVal2 > 5 " + strWhere + " ) " +
                    "  ORDER BY Position Desc, Caption, QRParamID";
            FslLog.d(TAG, "get  query for  getListQualityParameter  " + query);
            Cursor cursor = database.rawQuery(query, null);

            if (cursor.getCount() > 0) {
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToNext();
                    QualityParameter qualityParameter = new QualityParameter();

                    qualityParameter.pRowID = cursor.getString(cursor.getColumnIndex("pRowID"));
                    qualityParameter.QualityParameterID = cursor.getString(cursor.getColumnIndex("QualityParameterID"));
                    qualityParameter.MainDescr = cursor.getString(cursor.getColumnIndex("MainDescr"));
                    qualityParameter.Abbrv = cursor.getString(cursor.getColumnIndex("Abbrv"));
                    qualityParameter.OptionValue = cursor.getString(cursor.getColumnIndex("OptionValue"));
                    qualityParameter.Remarks = cursor.getString(cursor.getColumnIndex("Remarks"));
                    qualityParameter.Digitals = cursor.getString(cursor.getColumnIndex("Digitals"));
                    qualityParameter.PromptType = cursor.getInt(cursor.getColumnIndex("PromptType"));
                    qualityParameter.Position = cursor.getInt(cursor.getColumnIndex("Position"));
                    qualityParameter.IsApplicable = cursor.getInt(cursor.getColumnIndex("IsApplicable"));
                    qualityParameter.OptionSelected = cursor.getInt(cursor.getColumnIndex("OptionSelected"));
                    qualityParameter.recDirty = cursor.getInt(cursor.getColumnIndex("recDirty"));
                    qualityParameter.ImageRequired = cursor.getInt(cursor.getColumnIndex("ImageRequired"));


                    if (!TextUtils.isEmpty(qualityParameter.Digitals) && !qualityParameter.Digitals.equals("null")) {
                        String[] spStr = qualityParameter.Digitals.split(",");
                        if (spStr != null && spStr.length > 0) {
                            for (int j = 0; j < spStr.length; j++) {
                                String rowId = spStr[j];
                                if (!TextUtils.isEmpty(rowId)) {
                                    DigitalsUploadModal digitalsUploadModal = ItemInspectionDetailHandler.getImageQuality(mContext, QRHdrID, QRPOItemHdrID, rowId, qualityParameter.pRowID);
                                    if (digitalsUploadModal != null) {
                                        qualityParameter.imageAttachmentList.add(digitalsUploadModal);
                                    }
                                }
                            }

                        }

                    }

                    qualityParameters.add(qualityParameter);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        FslLog.d(TAG, "size of QualityParameter  is " + qualityParameters.size());
        return qualityParameters;
    }

    public static List<QualityParameter> getListQualityParameterAccordingRowId(Context mContext, String pRowID) {
        List<QualityParameter> qualityParameters = new ArrayList<>();
        try {
            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getReadableDatabase();
            String query = "SELECT * FROM QRQualiltyParameterFields" +
                    "  WHERE pRowID = '" + pRowID + "'";
            FslLog.d(TAG, "get  query for  getListQualityParameter  " + query);
            Cursor cursor = database.rawQuery(query, null);
            QualityParameter qualityParameter;
            if (cursor.getCount() > 0) {
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToNext();
                    qualityParameter = new QualityParameter();
                    qualityParameter.pRowID = cursor.getString(cursor.getColumnIndex("pRowID"));
                    qualityParameter.Digitals = cursor.getString(cursor.getColumnIndex("Digitals"));
                    qualityParameters.add(qualityParameter);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        FslLog.d(TAG, "size of QualityParameter  is " + qualityParameters.size());
        return qualityParameters;
    }


    public static List<QualityParameter> getListQualityParameterForItemLevel(Context mContext, InspectionModal inspectionModal) {
        List<QualityParameter> qualityParameters = new ArrayList<>();
        try {
            String strWhere = " AND gm.numVal2 < 5  ";
            if (!TextUtils.isEmpty(inspectionModal.ActivityID)) {
                String[] spStr = inspectionModal.ActivityID.split("SYS");
                if (spStr != null && spStr.length > 0) {
                    String afterSplite = spStr[1];
                    if (!TextUtils.isEmpty(afterSplite)) {
                        int iVal = Integer.parseInt(afterSplite.trim());
                        if (iVal > 0) {
                            String makedNum = " AND gm.numVal" + (iVal + 2) + " > 0  ";
                            strWhere = strWhere + makedNum;
                        }
                    }
                }
            }
            FslLog.d(TAG, " made strWhere = " + strWhere);
            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getReadableDatabase();
            String query = "SELECT IfNULL(QRParam.pRowID,'') As pRowID,   QRParam.QualityParameterID As QualityParameterID, IfNULL(gm.MainDescr,'')   As MainDescr, " +
                    " IfNULL(gm.Abbrv,'') As Abbrv, IfNULL(gm.chrVal2,'') As OptionValue,  IfNULL(gm.numVal1,0) As PromptType,   IfNULL(gm.numVal2,0) As Position, " +
                    "  IfNULL(QRParam.IsApplicable,0) As IsApplicable, IfNULL(QRParam.OptionSelected,0)   As OptionSelected, " +
                    " IfNULL(QRParam.Remarks,'') As Remarks, 0 As recDirty, IFNULL(Cast(gm.chrVal3 As Integer),0) As ImageRequired,  " +
                    " IFNULL(QRParam.Digitals,'') As Digitals   FROM GenMst gm  INNER JOIN QRQualiltyParameterFields QRParam on QRParam.QualityParameterID = gm.pGenRowID  " +
                    "   WHERE gm.GenId = '555' And QRParam.QRHdrID = '" + inspectionModal.pRowID + "' AND IfNULL(QRParam.QRPOItemHdrID,'') = '' " +
                    "   AND gm.numVal2 < 5   AND gm.numVal5 > 0 " + strWhere + " UNION   SELECT '' As pRowID,  gm.pGenRowID As QRParamID," +
                    " IfNULL(gm.MainDescr,'') As Name,    IfNULL(gm.Abbrv,'') As Caption, IfNULL(gm.chrVal2,'') As OptionValue, " +
                    " IfNULL(gm.numVal1,0) As PromptType,   IfNULL(gm.numVal2,0) As Position,  0 As IsApplicable, 0 As OptionSelected, '' As Remarks, 0 As recDirty, " +
                    "  IFNULL(Cast(gm.chrVal3 As Integer),0) As ImageRequired, '' As Digitals   FROM GenMst gm " +
                    " WHERE gm.GenId = '555' AND gm.recEnable = 1  AND gm.numVal2 < 5   AND gm.numVal5 > 0  " + strWhere +
                    " AND gm.pGenRowID NOT IN ( SELECT QRParam.QualityParameterID FROM QRQualiltyParameterFields QRParam  " +
                    "  WHERE QRParam.QualityParameterID = gm.pGenRowID AND QRParam.QRHdrID = '" + inspectionModal.pRowID + "' " + strWhere +
                    "  AND IfNULL(QRParam.QRPOItemHdrID,'') = '' AND gm.numVal2 < 5   AND gm.numVal5 > 0) " +
                    "  ORDER BY Position Desc, Caption, QRParamID";
            FslLog.d(TAG, "get  query for  getListQualityParameter  " + query);
            Cursor cursor = database.rawQuery(query, null);
            QualityParameter qualityParameter;
            if (cursor.getCount() > 0) {
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToNext();
                    qualityParameter = new QualityParameter();

                    qualityParameter.pRowID = cursor.getString(cursor.getColumnIndex("pRowID"));
                    qualityParameter.QualityParameterID = cursor.getString(cursor.getColumnIndex("QualityParameterID"));
                    qualityParameter.MainDescr = cursor.getString(cursor.getColumnIndex("MainDescr"));
                    qualityParameter.Abbrv = cursor.getString(cursor.getColumnIndex("Abbrv"));
                    qualityParameter.OptionValue = cursor.getString(cursor.getColumnIndex("OptionValue"));
                    qualityParameter.Remarks = cursor.getString(cursor.getColumnIndex("Remarks"));
                    qualityParameter.Digitals = cursor.getString(cursor.getColumnIndex("Digitals"));
                    qualityParameter.PromptType = cursor.getInt(cursor.getColumnIndex("PromptType"));
                    qualityParameter.Position = cursor.getInt(cursor.getColumnIndex("Position"));
                    qualityParameter.IsApplicable = cursor.getInt(cursor.getColumnIndex("IsApplicable"));
                    qualityParameter.OptionSelected = cursor.getInt(cursor.getColumnIndex("OptionSelected"));
                    qualityParameter.recDirty = cursor.getInt(cursor.getColumnIndex("recDirty"));
                    qualityParameter.ImageRequired = cursor.getInt(cursor.getColumnIndex("ImageRequired"));

                    if (!TextUtils.isEmpty(qualityParameter.Digitals) && !qualityParameter.Digitals.equals("null")) {
                        String[] spStr = qualityParameter.Digitals.split(",");
                        if (spStr != null && spStr.length > 0) {
                            for (int j = 0; j < spStr.length; j++) {
                                String rowId = spStr[j];
                                if (!TextUtils.isEmpty(rowId)) {
                                    DigitalsUploadModal digitalsUploadModal = ItemInspectionDetailHandler.getImageQualityInspectionLevel(mContext, inspectionModal.pRowID, rowId, qualityParameter.pRowID);
                                    if (digitalsUploadModal != null) {
                                        qualityParameter.imageAttachmentList.add(digitalsUploadModal);
                                    }
                                }
                            }

                        }

                    }
                    qualityParameters.add(qualityParameter);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        FslLog.d(TAG, "size of QualityParameter for item level is " + qualityParameters.size());
        return qualityParameters;
    }

    public static List<QualityParameter> getSavedQualityParameter(Context mContext, String QRHdrID, String QRPOItemHdrID) {
        List<QualityParameter> qualityParameters = new ArrayList<>();
        try {
            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getReadableDatabase();
            String query = "SELECT * FROM QRQualiltyParameterFields where  QRHdrID='" + QRHdrID + "'";
            if (!TextUtils.isEmpty(QRPOItemHdrID)) {
                query = query + " and QRPOItemHdrID='" + QRPOItemHdrID + "'";
            }

            FslLog.d(TAG, "get  query for  getListQualityParameter  " + query);
            Cursor cursor = database.rawQuery(query, null);
            QualityParameter qualityParameter;
            if (cursor.getCount() > 0) {
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToNext();
                    qualityParameter = new QualityParameter();

                    qualityParameter.pRowID = cursor.getString(cursor.getColumnIndex("pRowID"));
                    qualityParameter.QualityParameterID = cursor.getString(cursor.getColumnIndex("QualityParameterID"));
                    qualityParameter.Remarks = cursor.getString(cursor.getColumnIndex("Remarks"));
                    qualityParameter.IsApplicable = cursor.getInt(cursor.getColumnIndex("IsApplicable"));
                    qualityParameter.OptionSelected = cursor.getInt(cursor.getColumnIndex("OptionSelected"));

                    qualityParameters.add(qualityParameter);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        FslLog.d(TAG, "size of QualityParameter  is " + qualityParameters.size());
        return qualityParameters;
    }


    public static String updateQualityParameter(Context mContext, QualityParameter qualityParameter, POItemDtl poItemDtl) {
        String userId = LogInHandler.getUserId(mContext);
        long status = 0;
        String returnId = null;
        try {

            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getReadableDatabase();

            ContentValues contentValues = new ContentValues();

            contentValues.put("IsApplicable", qualityParameter.IsApplicable);
            contentValues.put("OptionSelected", qualityParameter.OptionSelected);
            contentValues.put("Remarks", qualityParameter.Remarks);
            contentValues.put("recEnable", 1);
            contentValues.put("recDirty", 1);
            contentValues.put("recAddDt", AppConfig.getCurrntDate());
            contentValues.put("recDt", AppConfig.getCurrntDate());
            contentValues.put("recAddUser", userId);
            contentValues.put("recUser", userId);
//            returnId = qualityParameter.pRowID;
            int rows = database.update("QRQualiltyParameterFields", contentValues, "QRHdrID"
                    + " = '" + poItemDtl.QRHdrID + "' AND QRPOItemHdrID='" + poItemDtl.QRPOItemHdrID + "' AND QualityParameterID='" + qualityParameter.QualityParameterID + "'", null);
            if (rows == 0) {
                returnId = GeneratePK(mContext, "QRQualiltyParameterFields");
                contentValues.put("LocID", FClientConfig.LOC_ID);
                contentValues.put("pRowID", GeneratePK(mContext, "QRQualiltyParameterFields"));
                contentValues.put("QRHdrID", poItemDtl.QRHdrID);
                contentValues.put("QRPOItemHdrID", poItemDtl.QRPOItemHdrID);
                contentValues.put("ItemID", poItemDtl.ItemID);
                contentValues.put("QualityParameterID", qualityParameter.QualityParameterID);
                status = database.insert("QRQualiltyParameterFields", null, contentValues);
                FslLog.d(TAG, " insert QRQualilty Parameter Fields .......");
            } else {
                String query = "Select pRowID from QRQualiltyParameterFields where  QRHdrID"
                        + " = '" + poItemDtl.QRHdrID + "' AND QRPOItemHdrID='" + poItemDtl.QRPOItemHdrID + "' AND QualityParameterID='" + qualityParameter.QualityParameterID + "'";
                Cursor cursor = database.rawQuery(query, null);
                if (cursor.getCount() > 0) {
                    for (int i = 0; i < cursor.getCount(); i++) {
                        cursor.moveToNext();
                        returnId = cursor.getString(cursor.getColumnIndex("pRowID"));
                    }
                }

                FslLog.d(TAG, " update QRQualilty Parameter Fields ............");
            }

            database.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        FslLog.d(TAG, " return quality parameter  id.." + returnId);
        return returnId;
    }

    public static String updateQualityParameterForItemLevel(Context mContext, QualityParameter qualityParameter, InspectionModal inspectionModal) {
        String userId = LogInHandler.getUserId(mContext);
        String returnId = null;
        try {

            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getReadableDatabase();

            ContentValues contentValues = new ContentValues();

            contentValues.put("IsApplicable", qualityParameter.IsApplicable);
            contentValues.put("OptionSelected", qualityParameter.OptionSelected);
            contentValues.put("Remarks", qualityParameter.Remarks);
            contentValues.put("recEnable", 1);
            contentValues.put("recDirty", 1);
            contentValues.put("recAddDt", AppConfig.getCurrntDate());
            contentValues.put("recDt", AppConfig.getCurrntDate());
            contentValues.put("recAddUser", userId);
            contentValues.put("recUser", userId);
//            returnId = qualityParameter.pRowID;

            int rows = database.update("QRQualiltyParameterFields", contentValues, "QRHdrID"
                    + " = '" + inspectionModal.pRowID + "' AND QualityParameterID='" + qualityParameter.QualityParameterID + "'", null);
            if (rows == 0) {
                returnId = GeneratePK(mContext, "QRQualiltyParameterFields");
                contentValues.put("LocID", FClientConfig.LOC_ID);
                contentValues.put("pRowID", GeneratePK(mContext, "QRQualiltyParameterFields"));
                contentValues.put("QRHdrID", inspectionModal.pRowID);
//                contentValues.put("QRPOItemHdrID", poItemDtl.QRPOItemHdrID);
//                contentValues.put("ItemID", poItemDtl.ItemID);
                contentValues.put("QualityParameterID", qualityParameter.QualityParameterID);
                database.insert("QRQualiltyParameterFields", null, contentValues);
                FslLog.d(TAG, " insert QRQualilty Parameter Fields .......");
            } else {
                String query = "Select pRowID from QRQualiltyParameterFields where  QRHdrID"
                        + " = '" + inspectionModal.pRowID + "'  AND QualityParameterID='" + qualityParameter.QualityParameterID + "'";
                Cursor cursor = database.rawQuery(query, null);
                if (cursor.getCount() > 0) {
                    for (int i = 0; i < cursor.getCount(); i++) {
                        cursor.moveToNext();
                        returnId = cursor.getString(cursor.getColumnIndex("pRowID"));
                    }
                }
                FslLog.d(TAG, " update QRQualilty Parameter Fields ............");
            }

            database.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        FslLog.d(TAG, " return quality parameter  id.." + returnId);
        return returnId;
    }

    public static List<QualityParameter> getCompletionProductionStatus(Context mContext, InspectionModal inspectionModal) {
        List<QualityParameter> qualityParameters = new ArrayList<>();
        try {
            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getReadableDatabase();
            String query =
                    "SELECT IFNULL(QRPStatus.pRowID,'') As pRowID,   QRPStatus.QRProdStatusID As QRPStatusID, IFNULL(gm.MainDescr,'') As Name, " +
                            "  QRPStatus.Percentage As Percentage, 0 As recDirty   FROM GenMst gm  " +
                            "  INNER JOIN QRProductionStatus QRPStatus on QRPStatus.QRProdStatusID = gm.pGenRowID   WHERE gm.GenId = '594' And QRPStatus.QRHdrID = '" + inspectionModal.pRowID + "'" +
                            "  UNION    SELECT '' As pRowID,   gm.pGenRowID As QRPStatusID, IFNULL(gm.MainDescr,'') As Name,   NULL As Percentage, 0 As recDirty   FROM GenMst gm " +
                            "  WHERE gm.GenId = '594' AND gm.recEnable = 1    AND gm.pGenRowID NOT IN ( SELECT QRPStatus.QRProdStatusID FROM QRProductionStatus QRPStatus      " +
                            "  WHERE QRPStatus.QRProdStatusID = gm.pGenRowID AND QRPStatus.QRHdrID = '" + inspectionModal.pRowID + "')   ORDER BY QRPStatusID  ";
            FslLog.d(TAG, "get  query for  Completion Production Status  " + query);
            Cursor cursor = database.rawQuery(query, null);
            QualityParameter qualityParameter;
            if (cursor.getCount() > 0) {
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToNext();
                    qualityParameter = new QualityParameter();

                    qualityParameter.pRowID = cursor.getString(cursor.getColumnIndex("pRowID"));
                    qualityParameter.QualityParameterID = cursor.getString(cursor.getColumnIndex("QRFitnessID"));
                    qualityParameter.MainDescr = cursor.getString(cursor.getColumnIndex("MainDescr"));
                    qualityParameter.Abbrv = cursor.getString(cursor.getColumnIndex("Abbrv"));
                    qualityParameter.OptionValue = cursor.getString(cursor.getColumnIndex("OptionValue"));
                    qualityParameter.Remarks = cursor.getString(cursor.getColumnIndex("Remarks"));
                    qualityParameter.Digitals = cursor.getString(cursor.getColumnIndex("Digitals"));
                    qualityParameter.PromptType = cursor.getInt(cursor.getColumnIndex("PromptType"));
                    qualityParameter.Position = cursor.getInt(cursor.getColumnIndex("Position"));
                    qualityParameter.IsApplicable = cursor.getInt(cursor.getColumnIndex("IsApplicable"));
                    qualityParameter.OptionSelected = cursor.getInt(cursor.getColumnIndex("OptionSelected"));
                    qualityParameter.recDirty = cursor.getInt(cursor.getColumnIndex("recDirty"));
                    qualityParameter.ImageRequired = cursor.getInt(cursor.getColumnIndex("ImageRequired"));
                    qualityParameters.add(qualityParameter);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        FslLog.d(TAG, "size of Completion Production Status  is " + qualityParameters.size());
        return qualityParameters;
    }

    public static long updateCompletionProductionStatus(Context mContext,
                                                        String remark, String dt,
                                                        InspectionModal inspectionModal) {
        String userId = LogInHandler.getUserId(mContext);
        long status = 0;
        try {

            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getReadableDatabase();

            ContentValues contentValues = new ContentValues();


            contentValues.put("Remarks", remark);
            contentValues.put("recEnable", 1);

            contentValues.put("recAddDt", dt);
            contentValues.put("recDt", AppConfig.getCurrntDate());
//            contentValues.put("recAddUser", userId);
            contentValues.put("recUser", userId);

            int rows = database.update("QRProductionStatus", contentValues, "pRowID"
                    + " = '" + inspectionModal.pRowID + "'", null);
            if (rows == 0) {
                contentValues.put("LocID", FClientConfig.LOC_ID);
                contentValues.put("pRowID", ItemInspectionDetailHandler.GeneratePK(mContext, "QRProductionStatus"));
                contentValues.put("QRHdrID", inspectionModal.pRowID);
//                contentValues.put("QRPOItemHdrID", poItemDtl.QRPOItemHdrID);
//                contentValues.put("ItemID", poItemDtl.ItemID);
                contentValues.put("QRProdStatusID", " ");
                status = database.insert("QRProductionStatus", null, contentValues);
                FslLog.d(TAG, " insert QRProduction Status Fields .......");
            } else {
                status = 1;
                FslLog.d(TAG, " update QRProduction Status  Fields ............");
            }

            database.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

//    public static long im

    public static String updateFitness(Context mContext, QualityParameter qualityParameter, POItemDtl poItemDtl) {
        String userId = LogInHandler.getUserId(mContext);
        long status = 0;
        String returnId = null;
        try {

            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getReadableDatabase();

            ContentValues contentValues = new ContentValues();

            contentValues.put("IsApplicable", qualityParameter.IsApplicable);
            contentValues.put("OptionSelected", qualityParameter.OptionSelected);
            contentValues.put("Remarks", qualityParameter.Remarks);
            contentValues.put("recEnable", 1);
            contentValues.put("recDirty", 1);
            contentValues.put("recAddDt", AppConfig.getCurrntDate());
            contentValues.put("recDt", AppConfig.getCurrntDate());
            contentValues.put("recAddUser", userId);
            contentValues.put("recUser", userId);


            int rows = database.update("QRPOItemFitnessCheck", contentValues, "QRHdrID"
                    + " = '" + poItemDtl.QRHdrID + "' AND QRPOItemHdrID='" + poItemDtl.QRPOItemHdrID + "' AND QRFitnessCheckID='" + qualityParameter.QualityParameterID + "'", null);
            returnId = qualityParameter.pRowID;
            if (rows == 0) {
                returnId = GeneratePK(mContext, "QRPOItemFitnessCheck");
                contentValues.put("LocID", FClientConfig.LOC_ID);
                contentValues.put("pRowID", GeneratePK(mContext, "QRPOItemFitnessCheck"));
                contentValues.put("QRHdrID", poItemDtl.QRHdrID);
                contentValues.put("QRPOItemHdrID", poItemDtl.QRPOItemHdrID);
                contentValues.put("ItemID", poItemDtl.ItemID);
                contentValues.put("QRFitnessCheckID", qualityParameter.QualityParameterID);
                status = database.insert("QRPOItemFitnessCheck", null, contentValues);
                FslLog.d(TAG, " insert QRPOItem Fitness Check... ");
            } else {
                status = 1;
                String query = "Select pRowID from QRPOItemFitnessCheck where  QRHdrID"
                        + " = '" + poItemDtl.QRHdrID + "' AND QRPOItemHdrID='" + poItemDtl.QRPOItemHdrID + "' AND QRFitnessCheckID='" + qualityParameter.QualityParameterID + "'";
                Cursor cursor = database.rawQuery(query, null);
                if (cursor.getCount() > 0) {
                    for (int i = 0; i < cursor.getCount(); i++) {
                        cursor.moveToNext();
                        returnId = cursor.getString(cursor.getColumnIndex("pRowID"));
                    }
                }

                FslLog.d(TAG, " update QRPOItem Fitness Check ...");
            }

            database.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnId;
    }

    public static List<QualityParameter> getListFitnessCheck(Context mContext, String QRHdrID, String QRPOItemHdrID) {
        List<QualityParameter> qualityParameters = new ArrayList<>();
        try {
            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getReadableDatabase();
            String query = "SELECT IFNULL(QRFitness.pRowID,'') As pRowID, QRFitness.QRFitnessCheckID As QRFitnessID, " +
                    " IFNULL(gm.MainDescr,'') As MainDescr,  IFNULL(gm.Abbrv,'') As Abbrv,  IFNULL(gm.chrVal2,'') As OptionValue," +
                    "  IFNULL(gm.numVal1,0) As PromptType,  IFNULL(gm.numVal2,0) As Position, " +
                    "  IFNULL(QRFitness.IsApplicable,0) As IsApplicable, IFNULL(QRFitness.OptionSelected,0) As OptionSelected, " +
                    " IFNULL(QRFitness.Remarks,'') As Remarks, 0 As recDirty,  IFNULL(Cast(gm.chrVal3 As Integer),0) As ImageRequired, " +
                    "IFNULL(Digitals,'') As Digitals  FROM GenMst gm  " +
                    "  INNER JOIN QRPOItemFitnessCheck QRFitness on QRFitness.QRFitnessCheckID = gm.pGenRowID " +
                    "  WHERE gm.GenId = '595' And QRFitness.QRHdrID = '" + QRHdrID + "'   " +
                    "    AND IFNULL(QRFitness.QRPOItemHdrID,'') = '" + QRPOItemHdrID + "'     UNION         SELECT '' As pRowID, gm.pGenRowID As QRFitnessID, IFNULL(gm.MainDescr,'') As Name, " +
                    "  IFNULL(gm.Abbrv,'') As Caption, IFNULL(gm.chrVal2,'') As OptionValue, " +
                    "  IFNULL(gm.numVal1,0) As PromptType, IFNULL(gm.numVal2,0) As Position, " +
                    "  0 As IsApplicable, 0 As OptionSelected, '' As Remarks, 0 As recDirty, " +
                    "  IFNULL(Cast(gm.chrVal3 As Integer),0) As ImageRequired, '' As Digitals  " +
                    " FROM GenMst gm  WHERE gm.GenId = '595' AND gm.recEnable = 1  AND gm.pGenRowID NOT IN ( SELECT QRFitness.QRFitnessCheckID FROM QRPOItemFitnessCheck QRFitness  " +
                    "    WHERE QRFitness.QRFitnessCheckID = gm.pGenRowID AND QRFitness.QRHdrID = '" + QRHdrID + "'  " +
                    "     AND IFNULL(QRFitness.QRPOItemHdrID,'') = '" + QRPOItemHdrID + "'  )   ORDER BY QRFitnessID ";
            FslLog.d(TAG, "get  query for  Fitness check  " + query);
            Cursor cursor = database.rawQuery(query, null);
            QualityParameter qualityParameter;
            if (cursor.getCount() > 0) {
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToNext();
                    qualityParameter = new QualityParameter();

                    qualityParameter.pRowID = cursor.getString(cursor.getColumnIndex("pRowID"));
                    qualityParameter.QualityParameterID = cursor.getString(cursor.getColumnIndex("QRFitnessID"));
                    qualityParameter.MainDescr = cursor.getString(cursor.getColumnIndex("MainDescr"));
                    qualityParameter.Abbrv = cursor.getString(cursor.getColumnIndex("Abbrv"));
                    qualityParameter.OptionValue = cursor.getString(cursor.getColumnIndex("OptionValue"));
                    qualityParameter.Remarks = cursor.getString(cursor.getColumnIndex("Remarks"));
                    qualityParameter.Digitals = cursor.getString(cursor.getColumnIndex("Digitals"));
                    qualityParameter.PromptType = cursor.getInt(cursor.getColumnIndex("PromptType"));
                    qualityParameter.Position = cursor.getInt(cursor.getColumnIndex("Position"));
                    qualityParameter.IsApplicable = cursor.getInt(cursor.getColumnIndex("IsApplicable"));
                    qualityParameter.OptionSelected = cursor.getInt(cursor.getColumnIndex("OptionSelected"));
                    qualityParameter.recDirty = cursor.getInt(cursor.getColumnIndex("recDirty"));
                    qualityParameter.ImageRequired = cursor.getInt(cursor.getColumnIndex("ImageRequired"));
                    if (!TextUtils.isEmpty(qualityParameter.Digitals) && !qualityParameter.Digitals.equals("null")) {
                        String[] spStr = qualityParameter.Digitals.split(",");
                        if (spStr != null && spStr.length > 0) {
                            for (int j = 0; j < spStr.length; j++) {
                                String rowId = spStr[j];
                                if (!TextUtils.isEmpty(rowId)) {
                                    DigitalsUploadModal digitalsUploadModal = ItemInspectionDetailHandler.getImageQuality(mContext, QRHdrID, QRPOItemHdrID, rowId, qualityParameter.pRowID);
                                    if (digitalsUploadModal != null) {
                                        qualityParameter.imageAttachmentList.add(digitalsUploadModal);
                                    }
                                }
                            }

                        }

                    }
                    qualityParameters.add(qualityParameter);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        FslLog.d(TAG, "size of fitness check  is " + qualityParameters.size());
        return qualityParameters;
    }

    public static List<QualityParameter> getListFitness(Context mContext, String QRHdrID, String QRPOItemHdrID) {
        List<QualityParameter> qualityParameters = new ArrayList<>();
        try {
            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getReadableDatabase();
            String query = "SELECT * from QRPOItemFitnessCheck where QRHdrID = '" + QRHdrID + "'   " +
                    "    AND QRPOItemHdrID = '" + QRPOItemHdrID + "'";
            FslLog.d(TAG, "get  query for  Fitness check  " + query);
            Cursor cursor = database.rawQuery(query, null);
            QualityParameter qualityParameter;
            if (cursor.getCount() > 0) {
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToNext();
                    qualityParameter = new QualityParameter();

                    qualityParameter.pRowID = cursor.getString(cursor.getColumnIndex("pRowID"));
                    qualityParameter.QualityParameterID = cursor.getString(cursor.getColumnIndex("QRFitnessCheckID"));

                    qualityParameters.add(qualityParameter);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        FslLog.d(TAG, "size of fitness check  is " + qualityParameters.size());
        return qualityParameters;
    }

    public static List<QualityParameter> getListFitnessCheckAccordingRowId(Context mContext, String pRowID) {
        List<QualityParameter> qualityParameters = new ArrayList<>();
        try {
            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getReadableDatabase();
            String query = "SELECT * FROM QRPOItemFitnessCheck" +
                    "  WHERE pRowID = '" + pRowID + "'";
            FslLog.d(TAG, "get  query for  getListQRPOItemFitnessCheck  " + query);
            Cursor cursor = database.rawQuery(query, null);
            QualityParameter qualityParameter;
            if (cursor.getCount() > 0) {
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToNext();
                    qualityParameter = new QualityParameter();
                    qualityParameter.pRowID = cursor.getString(cursor.getColumnIndex("pRowID"));
                    qualityParameter.Digitals = cursor.getString(cursor.getColumnIndex("Digitals"));
                    qualityParameters.add(qualityParameter);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        FslLog.d(TAG, "size of QRPOItemFitnessCheck  is " + qualityParameters.size());
        return qualityParameters;
    }

    public static long updateDigitalsFitnessCheck(Context mContext, QualityParameter qualityParameter) {

        long status = 0;
        try {

            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getReadableDatabase();

            ContentValues contentValues = new ContentValues();

            contentValues.put("Digitals", qualityParameter.Digitals);

            contentValues.put("recDt", AppConfig.getCurrntDate());

            int rows = database.update("QRPOItemFitnessCheck", contentValues, "pRowID"
                    + " = '" + qualityParameter.pRowID + "'", null);
            if (rows == 0) {
                FslLog.d(TAG, " NOT UPDATED QRPOItemFitnessCheck ");
            } else {
                status = 1;
                FslLog.d(TAG, " update QRPOItemFitnessCheck ");
            }

            database.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    public static List<IntimationModal> getIntimationList(Context mContext, String QRHdrID) {


        List<IntimationModal> itemArrayList = new ArrayList<IntimationModal>();
        try {
            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getReadableDatabase();

            String query = "Select * from QRPOIntimationDetails  Where  QRHdrID ='" + QRHdrID + "'";
            FslLog.d(TAG, "get  query for  intimation  " + query);
            Cursor cursor = database.rawQuery(query, null);


            IntimationModal inspectionModal;
            if (cursor.getCount() > 0) {
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToNext();
                    inspectionModal = new IntimationModal();
//                    enclosureModal.EnclRowID = cursor.getString(cursor.getColumnIndex("pRowID"));
                    inspectionModal.pRowID = cursor.getString(cursor.getColumnIndex("pRowID"));
                    inspectionModal.LocID = cursor.getString(cursor.getColumnIndex("LocID"));
                    inspectionModal.QRHdrID = cursor.getString(cursor.getColumnIndex("QRHdrID"));
//
                    inspectionModal.Name = cursor.getString(cursor.getColumnIndex("Name"));
                    inspectionModal.EmailID = cursor.getString(cursor.getColumnIndex("EmailID"));
                    inspectionModal.ID = cursor.getString(cursor.getColumnIndex("ID"));
                    inspectionModal.recAddUser = cursor.getString(cursor.getColumnIndex("recAddUser"));
                    inspectionModal.recAddDt = cursor.getString(cursor.getColumnIndex("recAddDt"));
                    inspectionModal.recUser = cursor.getString(cursor.getColumnIndex("recUser"));

                    inspectionModal.recDt = cursor.getString(cursor.getColumnIndex("recDt"));
                    inspectionModal.EDIDt = cursor.getString(cursor.getColumnIndex("EDIDt"));
                    inspectionModal.BE_pRowID = cursor.getString(cursor.getColumnIndex("BE_pRowID"));

                    inspectionModal.IsLink = cursor.getInt(cursor.getColumnIndex("IsLink"));
                    inspectionModal.IsReport = cursor.getInt(cursor.getColumnIndex("IsReport"));
                    inspectionModal.recType = cursor.getInt(cursor.getColumnIndex("recType"));
                    inspectionModal.recEnable = cursor.getInt(cursor.getColumnIndex("recEnable"));
                    inspectionModal.IsHtmlLink = cursor.getInt(cursor.getColumnIndex("IsHtmlLink"));
                    inspectionModal.IsRcvApplicable = cursor.getInt(cursor.getColumnIndex("IsRcvApplicable"));
                    inspectionModal.IsSelected = cursor.getInt(cursor.getColumnIndex("IsSelected"));


//
                    itemArrayList.add(inspectionModal);
                }
            }
            cursor.close();
            database.close();
            FslLog.d(TAG, " cout of founded list of test report " + itemArrayList.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemArrayList;
    }

    public static int updateOrInsertQRPOIntimationDetails(Context mContext, IntimationModal intimationModal) {

        int mStatus = 0;
        String userId = LogInHandler.getUserId(mContext);
        try {
            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getWritableDatabase();

            ContentValues contentValues = new ContentValues();

            contentValues.put("LocID", FClientConfig.LOC_ID);
            contentValues.put("QRHdrID", intimationModal.QRHdrID);
            contentValues.put("Name", intimationModal.Name);
            contentValues.put("EmailID", intimationModal.EmailID);
            contentValues.put("ID", intimationModal.ID);
            contentValues.put("recAddUser", userId);
            contentValues.put("recAddDt", AppConfig.getCurrntDate());
            contentValues.put("recUser", userId);
            contentValues.put("recDt", AppConfig.getCurrntDate());
            contentValues.put("BE_pRowID", intimationModal.BE_pRowID);
            contentValues.put("IsLink", intimationModal.IsLink);
            contentValues.put("IsReport", intimationModal.IsReport);
            contentValues.put("recType", intimationModal.recType);
            contentValues.put("recEnable", intimationModal.recEnable);
            contentValues.put("IsHtmlLink", intimationModal.IsHtmlLink);
            contentValues.put("IsRcvApplicable", intimationModal.IsRcvApplicable);
            contentValues.put("IsSelected", intimationModal.IsSelected);

            if (!TextUtils.isEmpty(intimationModal.pRowID) && !intimationModal.pRowID.equals("null")) {
                int rows = database.update("QRPOIntimationDetails", contentValues, "pRowID"
                        + " = '" + intimationModal.pRowID + "'", null);

                if (rows == 0) {
                    contentValues.put("pRowID", intimationModal.pRowID);
                    long status = database.insert("QRPOIntimationDetails", null, contentValues);
                    if (status > 0) {
                        mStatus = 2;
                    }

                    FslLog.d(TAG, "insert QRPOIntimationDetails successfully  ");

                } else {
                    FslLog.d(TAG, "update QRPOIntimationDetails successfully  ");
                    mStatus = 1;
                }
            } else {
                FslLog.e(TAG, "NOT UPDATE OR INSERT Because pROWID is NULL ");
            }
            database.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mStatus;
    }


    public static List<DigitalsUploadModal> getImageTitle(Context mContext, String QRHdrID, String QRPOItemHdrID) {

        List<DigitalsUploadModal> lDigitalsUploadModals = new ArrayList<>();
        try {
            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getReadableDatabase();

            String query = "Select Distinct  Min(tbl.pRowID) As pRowID,  tbl.Title " +
                    "From (   SELECT 0 As Section, 'None' As SectionDescr, '0_' As pRowID, '[None]' As Title    " +
                    "     UNION      SELECT Distinct 1 As Section, 'WorkManship' As SectionDescr, '1_' || IFNULL(QRDtl.pRowID,'') As pRowID,  IFNULL(DM.DefectName,IFNULL(QRDtl.DefectName,'')) As Title" +
                    "   FROM QRAuditBatchDetails QRDtl   LEFT JOIN DefectMaster DM on DM.pRowID = QRDtl.DefectID  " +
                    " LEFT JOIN GenMst gdDCLType on gdDCLType.pGenRowID = IFNULL(DM.DCLTypeID,QRDtl.DCLTypeID) " +
                    "  WHERE QRDtl.QRPOItemHdrID = '" + QRPOItemHdrID.trim() + "' " +
                    "  AND IFNULL(QRDtl.SampleRqstCriticalRowID,'') = '' AND" +
                    " IFNULL(QRDtl.POItemHdrCriticalRowID,'') = '' AND IFNULL(QRDtl.BE_pRowID,'') = ''  " +
                    "  UNION         Select Distinct 2 As Section, 'ItemMeasurement' As SectionDescr, '2_' || pRowID, " +
                    "IFNULL(ItemMeasurementDescr,'') Title   FROM QRPOItemDtl_ItemMeasurement " +
                    "  Where QRHDrID='" + QRHdrID.trim() + "' AND QRPOItemHdrID = '" + QRPOItemHdrID.trim() + "' AND recEnable = 1  " +
                    "   UNION       SELECT Distinct 3 As Section, 'Quality Parameter' As SectionDescr, '3_' || IFNULL(QRParam.pRowID,'') As pRowID, " +
                    " IFNULL(gm.MainDescr,'') As Title   FROM GenMst gm   INNER JOIN QRQualiltyParameterFields QRParam on QRParam.QualityParameterID = gm.pGenRowID " +
                    "  WHERE gm.GenId = '555' And QRParam.QRHdrID = '" + QRHdrID.trim() + "'      AND IFNULL(QRParam.QRPOItemHdrID,'') = '" + QRPOItemHdrID.trim() + "' AND (gm.numVal2 > 5 Or gm.numVal2 < 5) AND" +
                    " gm.numVal5 > 0      AND IFNULL(Cast(gm.chrVal3 As Integer),0) = 1  " +
                    " /*UNION   SELECT Distinct 3 As Section, 'Quality Parameter' As SectionDescr, '3_' || '' As pRowID, IFNULL(gm.MainDescr,'') As Title  FROM GenMst gm  " +
                    "  WHERE gm.GenId = '555' AND gm.recEnable = 1 AND (gm.numVal2 > 5 Or gm.numVal2 < 5) AND gm.numVal5 > 0 AND IFNULL(Cast(gm.chrVal3 As Integer),0) = 1   AND gm.pGenRowID NOT IN ( SELECT QRParam.QualityParameterID FROM QRQualiltyParameterFields QRParam   " +
                    "     WHERE QRParam.QualityParameterID = gm.pGenRowID AND QRParam.QRHdrID = '" + QRHdrID.trim() + "'    AND IFNULL(QRParam.QRPOItemHdrID,'') = '" + QRPOItemHdrID.trim() + "'" +
                    " AND (gm.numVal2 > 5 Or gm.numVal2 < 5) AND gm.numVal5 > 0 ) */   " +
                    "    UNION        Select Distinct 4 As Section, 'Image' As SectionDescr, '4_' || IFNULL(img.pRowID,'') As pRowID,  " +
                    " IFNULL(img.Title,'') As Title   From QRPOItemDtl_Image img   Where img.QRPOItemHdrID = '" + QRPOItemHdrID.trim() + "' And IFNULL(img.Title,'') <> '' And img.recEnable = 1  " +
                    "    /*UNION         Select Distinct 5 As Section, '[Type...]' As SectionDescr, '5' As pRowID,   '[Type...]' As Title*/  " +
                    "     UNION        SELECT Distinct 6 As Section, 'Fitness Check' As SectionDescr, '6_' || IFNULL(QRFitness.pRowID,'') As pRowID,   IFNULL(gm.MainDescr,'') As Title " +
                    " FROM GenMst gm   INNER JOIN QRPOItemFitnessCheck QRFitness on QRFitness.QRFitnessCheckID = gm.pGenRowID   WHERE gm.GenId = '595' And QRFitness.QRHdrID = '" + QRHdrID.trim() + "'  " +
                    "    AND IFNULL(QRFitness.QRPOItemHdrID,'') = '" + QRPOItemHdrID.trim() + "'      AND IFNULL(Cast(gm.chrVal3 As Integer),0) = 1   ) tbl Group by tbl.Title Order by tbl.Section";

            FslLog.d(TAG, "get update query for  get getToInspect dtl  " + query);
            Cursor cursor = database.rawQuery(query, null);

            if (cursor.getCount() > 0) {

                for (int i = 0; i < cursor.getCount(); i++) {
                    DigitalsUploadModal digitalsUploadModal = new DigitalsUploadModal();
                    cursor.moveToNext();
                    digitalsUploadModal.pRowID = cursor.getString(cursor.getColumnIndex("pRowID"));
                    digitalsUploadModal.title = cursor.getString(cursor.getColumnIndex("Title"));
                    lDigitalsUploadModals.add(digitalsUploadModal);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        FslLog.d(TAG, "count title size   " + lDigitalsUploadModals.size());
        return lDigitalsUploadModals;

    }

    public static long hash() {
//        int h = 0;
//        for (int i = 0; i < s.length(); i++) {
//            h = 31 * h + s.charAt(i);
//        }

        long t = System.currentTimeMillis();
        return t;
    }

    public static String GeneratePK(Context mContext, String TableName) {
        String LocID = FClientConfig.LOC_ID;
        String pRowID = "";
        //sql for GetMax pRowID from Table
        String OLDpRowID = maxId(mContext, TableName);
        int genNo = (Integer.parseInt(OLDpRowID.substring(3, 10)) + 1);
        String addNo = "0000000" + genNo;
        String s = String.valueOf(genNo);
        String numbers = addNo.substring(s.length(), addNo.length());
        pRowID = LocID + numbers;
        FslLog.d(TAG, " generated pRowID ............ " + pRowID);
        return pRowID;
    }

    public static List<POItemDtl> capyFindingDataToSpecification(List<POItemDtl> packDetailList, List<POItemDtl> packFindingList) {

        if (packDetailList != null && packDetailList.size() > 0) {
            if (packFindingList != null && packFindingList.size() > 0) {
                for (int i = 0; i < packFindingList.size(); i++) {
                    if (i < packDetailList.size()) {
                        if (packDetailList.get(i).QRPOItemHdrID.equals(packFindingList.get(i).pRowID)) {
                            packDetailList.get(i).PKG_Me_InspectionResultID = packFindingList.get(i).PKG_Me_InspectionResultID;
                            packDetailList.get(i).PKG_Me_Master_InspectionResultID = packFindingList.get(i).PKG_Me_Master_InspectionResultID;
                            packDetailList.get(i).PKG_Me_Pallet_InspectionResultID = packFindingList.get(i).PKG_Me_Pallet_InspectionResultID;
                            packDetailList.get(i).PKG_Me_Unit_InspectionResultID = packFindingList.get(i).PKG_Me_Unit_InspectionResultID;
                            packDetailList.get(i).PKG_Me_Inner_InspectionResultID = packFindingList.get(i).PKG_Me_Inner_InspectionResultID;
                            //added by shekhar
                            packDetailList.get(i).PKG_App_ShippingMark_InspectionResultID = packFindingList.get(i).PKG_App_ShippingMark_InspectionResultID;
                            packDetailList.get(i).PKG_App_Remark = packFindingList.get(i).PKG_App_Remark;
                            packDetailList.get(i).PKG_App_InspectionResultID = packFindingList.get(i).PKG_App_InspectionResultID;
                            packDetailList.get(i).PKG_App_InspectionLevelID = packFindingList.get(i).PKG_App_InspectionLevelID;
                            packDetailList.get(i).PKG_App_Pallet_InspectionResultID = packFindingList.get(i).PKG_App_Pallet_InspectionResultID;
                            packDetailList.get(i).PKG_App_Pallet_SampleSizeID = packFindingList.get(i).PKG_App_Pallet_SampleSizeID;
                            packDetailList.get(i).PKG_App_Pallet_SampleSizeValue = packFindingList.get(i).PKG_App_Pallet_SampleSizeValue;
                            packDetailList.get(i).PKG_App_Master_SampleSizeID = packFindingList.get(i).PKG_App_Master_SampleSizeID;
                            packDetailList.get(i).PKG_App_Master_SampleSizeValue = packFindingList.get(i).PKG_App_Master_SampleSizeValue;
                            packDetailList.get(i).PKG_App_Master_InspectionResultID = packFindingList.get(i).PKG_App_Master_InspectionResultID;
                            packDetailList.get(i).PKG_App_Inner_SampleSizeID = packFindingList.get(i).PKG_App_Inner_SampleSizeID;
                            packDetailList.get(i).PKG_App_Inner_SampleSizeValue = packFindingList.get(i).PKG_App_Inner_SampleSizeValue;
                            packDetailList.get(i).PKG_App_Unit_SampleSizeID = packFindingList.get(i).PKG_App_Unit_SampleSizeID;
                            packDetailList.get(i).PKG_App_Unit_InspectionResultID = packFindingList.get(i).PKG_App_Unit_InspectionResultID;
                            packDetailList.get(i).OnSiteTest_Remark = packFindingList.get(i).OnSiteTest_Remark;
                            packDetailList.get(i).Qty_Remark = packFindingList.get(i).Qty_Remark;
                            packDetailList.get(i).OnSiteTest_InspectionResultID = packFindingList.get(i).OnSiteTest_InspectionResultID;

                            packDetailList.get(i).Barcode_InspectionLevelID = packFindingList.get(i).Barcode_InspectionLevelID;
                            packDetailList.get(i).Barcode_InspectionResultID = packFindingList.get(i).Barcode_InspectionResultID;
                            packDetailList.get(i).Barcode_Remark = packFindingList.get(i).Barcode_Remark;
                            packDetailList.get(i).Barcode_Pallet_SampleSizeID = packFindingList.get(i).Barcode_Pallet_SampleSizeID;
                            packDetailList.get(i).Barcode_Pallet_SampleSizeValue = packFindingList.get(i).Barcode_Pallet_SampleSizeValue;
                            packDetailList.get(i).Barcode_Pallet_Visual = packFindingList.get(i).Barcode_Pallet_Visual;
                            packDetailList.get(i).Barcode_Pallet_Scan = packFindingList.get(i).Barcode_Pallet_Scan;
                            packDetailList.get(i).Barcode_Pallet_InspectionResultID = packFindingList.get(i).Barcode_Pallet_InspectionResultID;
                            packDetailList.get(i).Barcode_Master_SampleSizeID = packFindingList.get(i).Barcode_Master_SampleSizeID;
                            packDetailList.get(i).Barcode_Master_SampleSizeValue = packFindingList.get(i).Barcode_Master_SampleSizeValue;
                            packDetailList.get(i).Barcode_Master_Visual = packFindingList.get(i).Barcode_Master_Visual;
                            packDetailList.get(i).Barcode_Master_Scan = packFindingList.get(i).Barcode_Master_Scan;
                            packDetailList.get(i).Barcode_Inner_SampleSizeID = packFindingList.get(i).Barcode_Inner_SampleSizeID;
                            packDetailList.get(i).Barcode_Master_InspectionResultID = packFindingList.get(i).Barcode_Master_InspectionResultID;
                            packDetailList.get(i).Barcode_Inner_SampleSizeValue = packFindingList.get(i).Barcode_Inner_SampleSizeValue;
                            packDetailList.get(i).Barcode_Inner_Visual = packFindingList.get(i).Barcode_Inner_Visual;
                            packDetailList.get(i).Barcode_Inner_Scan = packFindingList.get(i).Barcode_Inner_Scan;
                            packDetailList.get(i).Barcode_Inner_InspectionResultID = packFindingList.get(i).Barcode_Inner_InspectionResultID;
                            packDetailList.get(i).Barcode_Unit_SampleSizeID = packFindingList.get(i).Barcode_Unit_SampleSizeID;
                            packDetailList.get(i).Barcode_Unit_SampleSizeValue = packFindingList.get(i).Barcode_Unit_SampleSizeValue;
                            packDetailList.get(i).Barcode_Unit_Visual = packFindingList.get(i).Barcode_Unit_Visual;
                            packDetailList.get(i).Barcode_Unit_Scan = packFindingList.get(i).Barcode_Unit_Scan;
                            packDetailList.get(i).Barcode_Unit_InspectionResultID = packFindingList.get(i).Barcode_Unit_InspectionResultID;


                            ///////////
                            packDetailList.get(i).PKG_Me_Remark = packFindingList.get(i).PKG_Me_Remark;
                            packDetailList.get(i).WorkmanShip_InspectionResultID = packFindingList.get(i).WorkmanShip_InspectionResultID;
                            packDetailList.get(i).WorkmanShip_Remark = packFindingList.get(i).WorkmanShip_Remark;
                            packDetailList.get(i).ItemMeasurement_InspectionResultID = packFindingList.get(i).ItemMeasurement_InspectionResultID;
                            packDetailList.get(i).Overall_InspectionResultID = packFindingList.get(i).Overall_InspectionResultID;
                            packDetailList.get(i).ItemMeasurement_Remark = packFindingList.get(i).ItemMeasurement_Remark;
                            packDetailList.get(i).PKG_Me_Pallet_FindingL = packFindingList.get(i).PKG_Me_Pallet_FindingL;
                            packDetailList.get(i).PKG_Me_Pallet_FindingB = packFindingList.get(i).PKG_Me_Pallet_FindingB;
                            packDetailList.get(i).PKG_Me_Pallet_FindingH = packFindingList.get(i).PKG_Me_Pallet_FindingH;
                            packDetailList.get(i).PKG_Me_Pallet_FindingWt = packFindingList.get(i).PKG_Me_Pallet_FindingWt;
                            packDetailList.get(i).PKG_Me_Pallet_FindingCBM = packFindingList.get(i).PKG_Me_Pallet_FindingCBM;
                            packDetailList.get(i).PKG_Me_Pallet_FindingQty = packFindingList.get(i).PKG_Me_Pallet_FindingQty;
                            packDetailList.get(i).PKG_Me_Master_FindingL = packFindingList.get(i).PKG_Me_Master_FindingL;
                            packDetailList.get(i).PKG_Me_Master_FindingB = packFindingList.get(i).PKG_Me_Master_FindingB;
                            packDetailList.get(i).PKG_Me_Master_FindingH = packFindingList.get(i).PKG_Me_Master_FindingH;
                            packDetailList.get(i).PKG_Me_Master_FindingWt = packFindingList.get(i).PKG_Me_Master_FindingWt;
                            packDetailList.get(i).PKG_Me_Master_FindingCBM = packFindingList.get(i).PKG_Me_Master_FindingCBM;
                            packDetailList.get(i).PKG_Me_Master_FindingQty = packFindingList.get(i).PKG_Me_Master_FindingQty;
                            packDetailList.get(i).PKG_Me_Inner_FindingL = packFindingList.get(i).PKG_Me_Inner_FindingL;
                            packDetailList.get(i).PKG_Me_Inner_FindingB = packFindingList.get(i).PKG_Me_Inner_FindingB;
                            packDetailList.get(i).PKG_Me_Inner_FindingH = packFindingList.get(i).PKG_Me_Inner_FindingH;
                            packDetailList.get(i).PKG_Me_Inner_FindingWt = packFindingList.get(i).PKG_Me_Inner_FindingWt;
                            packDetailList.get(i).PKG_Me_Inner_FindingCBM = packFindingList.get(i).PKG_Me_Inner_FindingCBM;
                            packDetailList.get(i).PKG_Me_Inner_FindingQty = packFindingList.get(i).PKG_Me_Inner_FindingQty;
                            packDetailList.get(i).PKG_Me_Unit_FindingL = packFindingList.get(i).PKG_Me_Unit_FindingL;
                            packDetailList.get(i).PKG_Me_Unit_FindingB = packFindingList.get(i).PKG_Me_Unit_FindingB;
                            packDetailList.get(i).PKG_Me_Unit_FindingH = packFindingList.get(i).PKG_Me_Unit_FindingH;
                            packDetailList.get(i).PKG_Me_Unit_FindingWt = packFindingList.get(i).PKG_Me_Unit_FindingWt;
                            packDetailList.get(i).PKG_Me_Unit_FindingCBM = packFindingList.get(i).PKG_Me_Unit_FindingCBM;

                            packDetailList.get(i).PKG_Me_Inner_SampleSizeID = packFindingList.get(i).PKG_Me_Inner_SampleSizeID;
                            packDetailList.get(i).PKG_Me_Unit_SampleSizeID = packFindingList.get(i).PKG_Me_Unit_SampleSizeID;
                            packDetailList.get(i).PKG_Me_Pallet_SampleSizeID = packFindingList.get(i).PKG_Me_Pallet_SampleSizeID;
                            packDetailList.get(i).PKG_Me_Master_SampleSizeID = packFindingList.get(i).PKG_Me_Master_SampleSizeID;
                            //added by shekhar
                            packDetailList.get(i).PKG_App_shippingMark_SampleSizeId = packFindingList.get(i).PKG_App_shippingMark_SampleSizeId;

                        }
                    }
                }
            }
        }
        return packDetailList;

    }

    public static String maxId(Context mContext, String tableName) {


        String pRowID = null;
        String pR = null;
        try {
            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getReadableDatabase();
            String query = "Select ifNull(max (pRowID),'0000000000') As pRowID from " + tableName;
            FslLog.d(TAG, "get query for  max pRowID  " + query);
            Cursor cursor = database.rawQuery(query, null);
            if (cursor.getCount() > 0) {
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToNext();
                    pR = cursor.getString(cursor.getColumnIndex("pRowID"));
                }
            }
            cursor.close();
            database.close();
            FslLog.d(TAG, " get id from db " + pRowID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (TextUtils.isEmpty(pR) || pR.equals("null")) {
            pRowID = "0000000000";
        } else {
            pRowID = pR;
        }
        return pRowID;
    }


    public static void toRedirectWebView(final Context mContext, InspectionModal inspectionModal) {


        final ProgressDialog loadingDialog = new ProgressDialog(mContext);
//        loadingDialog.setTitle("Please wait");
        loadingDialog.setMessage("Loading...");
        if (loadingDialog != null && !loadingDialog.isShowing()) {
//            loadingDialog.show();
            GenUtils.safeProgressDialogShow(TAG, mContext, loadingDialog);
        }

        final String url = AppConfig.URL_HISTORY;

        final Map<String, String> params = new HashMap<String, String>();
        params.put("InspectionpRowID", inspectionModal.QRHdrID);


        FslLog.d(TAG, "url " + url);
        FslLog.d(TAG, "param " + new JSONObject(params));

        HandleToConnectionEithServer handleToConnectionEithServer = new HandleToConnectionEithServer();
        handleToConnectionEithServer.setRequest(url,
                new JSONObject(params), new HandleToConnectionEithServer.CallBackResult() {
                    @Override
                    public void onSuccess(JSONObject result) {

                        String token = result.optString("EncryptValue");
                        if (!TextUtils.isEmpty(token)) {
                            String rdUrl = AppConfig.URL_HISTORY_DETAIL + token;
                            FslLog.d(TAG, "redirect url " + rdUrl);
//                            Intent intent = new Intent(mContext, SupportFeature.class);
//                            intent.putExtra("title", "History");
//                            intent.putExtra("url", rdUrl);
                            if (loadingDialog != null && loadingDialog.isIndeterminate() && loadingDialog.isShowing()) {
                                loadingDialog.dismiss();

                            }
//                            mContext.startActivity(intent);
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(rdUrl));
                            mContext.startActivity(browserIntent);
                        }

                    }

                    @Override
                    public void onError(VolleyError volleyError) {
                        if (loadingDialog != null && loadingDialog.isIndeterminate() && loadingDialog.isShowing()) {
                            loadingDialog.dismiss();
                            Toast toast = ToastCompat.makeText(mContext, "Couldn't load", Toast.LENGTH_SHORT);
                            GenUtils.safeToastShow(TAG, mContext, toast);
                        }
                    }
                });


    }


    public static long updateDefect(Context mContext, POItemDtl poItemDtl) {

        long status = 0;
        try {

            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getReadableDatabase();

            ContentValues contentValues = new ContentValues();

            contentValues.put("CriticalDefect", poItemDtl.CriticalDefect);
            contentValues.put("MajorDefect", poItemDtl.MajorDefect);
            contentValues.put("MinorDefect", poItemDtl.MinorDefect);

            contentValues.put("recDt", AppConfig.getCurrntDate());

            int rows = database.update("QRPOItemHdr", contentValues, "pRowID"
                    + " = '" + poItemDtl.pRowID + "'", null);
            if (rows == 0) {
                FslLog.d(TAG, " NOT UPDATED packagingMeasurement ");
            } else {
                status = 1;
                FslLog.d(TAG, " update packagingMeasurement ");
            }

            database.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    public static void updateFinalSync(Context mContext, String pRowID) {

        DBHelper dbHelper = new DBHelper(mContext);
        SQLiteDatabase database = dbHelper.getWritableDatabase();


        String query = "Update QRFeedBackHdr Set Last_Sync_Dt ='" + AppConfig.getCurrntDate() + "', IsSynced=1 Where pRowID = '" + pRowID + "'";

        database.execSQL(query);
        database.close();

        FslLog.d(TAG, " update final sync ");


    }

    public static List<EnclosureModal> getSyncEnclosureList(Context mContext, String QRHDrID) {


        List<EnclosureModal> itemArrayList = new ArrayList<EnclosureModal>();
        try {

            if (!GenUtils.columnExistsInTable(mContext, "Enclosures", "IsImportant")) {
                FslLog.e(TAG, " not column found " + "IsImportant" + " so alter execute as INTEGER");
                GetDataHandler.handleToAlterAsIn(mContext, "Enclosures", "IsImportant");

            }
            if (!GenUtils.columnExistsInTable(mContext, "Enclosures", "IsRead")) {
                FslLog.e(TAG, " not column found " + "IsRead" + " so alter execute as INTEGER");
                GetDataHandler.handleToAlterAsIn(mContext, "Enclosures", "IsRead");

            }
            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getReadableDatabase();

            String query = "Select distinct  EnclRowID pRowID, ifnull(contextDs,'') contextDs,ifnull(Encltype,'') Encltype,Ifnull(Title,'')Title , Ifnull(ImageName,'') " +
                    "As ImageName, Ifnull(EnclFileType,'') As EnclFileType, IFNULL(ImagePathID,'') As ImagePathID," +
                    " IFNULL(ContextDs2,'') As ContextDs2 , IFNULL(ContextDs3,'') As ContextDs3 , IsImportant , IsRead  From Enclosures" +
                    "  Where   QRHdrID='" + QRHDrID + "' and IsRead=1";


           /* String query = "SELECT * FROM " + "QRPOItemdtl" + " where QRHdrID='" + pRowID + "'";*/
            FslLog.d(TAG, "get update query for  get Enclosure list  " + query);
            Cursor cursor = database.rawQuery(query, null);


            EnclosureModal enclosureModal;
            if (cursor.getCount() > 0) {
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToNext();
                    enclosureModal = new EnclosureModal();
                    enclosureModal.EnclRowID = cursor.getString(cursor.getColumnIndex("pRowID"));
//                    poItemDtl.QRHdrID = cursor.getString(cursor.getColumnIndex("QRHdrID"));
//                    enclosureModal.QRPOItemHdrID = cursor.getString(cursor.getColumnIndex("QRPOItemHdrID"));
                    enclosureModal.ContextDs = cursor.getString(cursor.getColumnIndex("contextDs"));

                    enclosureModal.EnclType = cursor.getString(cursor.getColumnIndex("Encltype"));
                    enclosureModal.Title = cursor.getString(cursor.getColumnIndex("Title"));
                    enclosureModal.ImageName = cursor.getString(cursor.getColumnIndex("ImageName"));
                    String[] strA = enclosureModal.ImageName.split(Pattern.quote("."));
                    enclosureModal.fileExt = "." + strA[1];
                    enclosureModal.EnclFileType = cursor.getString(cursor.getColumnIndex("EnclFileType"));

                    enclosureModal.ImagePathID = cursor.getString(cursor.getColumnIndex("ImagePathID"));
                    enclosureModal.ContextDs3 = cursor.getString(cursor.getColumnIndex("ContextDs3"));
                    enclosureModal.ContextDs2 = cursor.getString(cursor.getColumnIndex("ContextDs2"));
                    enclosureModal.IsImportant = cursor.getInt(cursor.getColumnIndex("IsImportant"));
                    enclosureModal.IsRead = cursor.getInt(cursor.getColumnIndex("IsRead"));

                    itemArrayList.add(enclosureModal);
                }
            }
            cursor.close();
            database.close();
            FslLog.d(TAG, " cout of founded list of enclosure " + itemArrayList.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemArrayList;
    }

    public static List<Integer> IsImportant(Context mContext, String QRHdrID, String QRPOItemHdrID) {


        List<Integer> importantList = new ArrayList<>();

        try {

            if (!GenUtils.columnExistsInTable(mContext, "Enclosures", "IsImportant")) {
                FslLog.e(TAG, " not column found " + "IsImportant" + " so alter execute as INTEGER");
                GetDataHandler.handleToAlterAsIn(mContext, "Enclosures", "IsImportant");

            }
            if (!GenUtils.columnExistsInTable(mContext, "Enclosures", "IsRead")) {
                FslLog.e(TAG, " not column found " + "IsRead" + " so alter execute as INTEGER");
                GetDataHandler.handleToAlterAsIn(mContext, "Enclosures", "IsRead");

            }
            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getReadableDatabase();

            String query;
            if (!TextUtils.isEmpty(QRPOItemHdrID) && !"null".equalsIgnoreCase(QRPOItemHdrID)) {
                query = "Select distinct  IsImportant  From Enclosures" +
                        "  Where  (IFNULL(QRPOItemHdrId,'')='" + QRPOItemHdrID + "' OR IFNULL(QRPOItemHdrId,'')='')  and QRHdrID='" + QRHdrID + "' and IsRead=0";
            } else {
                query = "Select distinct  IsImportant  From Enclosures Where  QRHdrID='" + QRHdrID + "' and IsRead=0";
            }
            FslLog.d(TAG, "get Flag to Important " + query);

            Cursor cursor = database.rawQuery(query, null);
            if (cursor.getCount() > 0) {
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToNext();
                    importantList.add(cursor.getInt(cursor.getColumnIndex("IsImportant")));
                }
            }
            cursor.close();
            database.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return importantList;
    }

    public static void updateEnClosureToRead(Context mContext, EnclosureModal enclosureModal) {

        DBHelper dbHelper = new DBHelper(mContext);
        SQLiteDatabase database = dbHelper.getWritableDatabase();


        ContentValues contentValues = new ContentValues();
        contentValues.put("IsRead", enclosureModal.IsRead);
        FslLog.d(TAG, "Set content Enclosures  " + contentValues);

        int rows = database.update("Enclosures", contentValues, "EnclRowID"
                + " = '" + enclosureModal.EnclRowID + "'", null);
        database.close();
        if (rows > 0) {
            FslLog.d(TAG, " update Enclosures ");
        }

    }


    public static void getUpdateDownloadStaus(final Context mContext, final EnclosureModal enclosureModal) {

        String url = AppConfig.URL_UPDATE_READ_STATUS;

        final Map<String, String> params = new HashMap<String, String>();
        params.put("EnclosureID", enclosureModal.EnclRowID);

        FslLog.d(TAG, "download status of enclosure url " + url);
        FslLog.d(TAG, "download staus param " + new JSONObject(params));

        HandleToConnectionEithServer handleToConnectionEithServer = new HandleToConnectionEithServer();
        handleToConnectionEithServer.setRequest(url,
                new JSONObject(params), new HandleToConnectionEithServer.CallBackResult() {
                    @Override
                    public void onSuccess(JSONObject result) {
                        if (result.optBoolean("Result")) {
                            enclosureModal.IsRead = 2;
                            ItemInspectionDetailHandler.updateEnClosureToRead(mContext, enclosureModal);
                        } else {
                            FslLog.e(TAG, "download staus param NOR UPDATE ON SERVER");
                        }
                    }

                    @Override
                    public void onError(VolleyError volleyError) {

                    }
                });
    }

}
