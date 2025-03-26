package com.podetail;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.General.DefectMasterModal;
import com.General.InsLvHdrModal;
import com.General.OnSIteModal;
import com.General.SampleCollectedModal;
import com.General.SampleModal;
import com.buyereasefsl.ItemInspectionDetailHandler;
import com.constant.AppConfig;
import com.constant.FClientConfig;
import com.database.DBHelper;
import com.login.LogInHandler;
import com.sync.GetDataHandler;
import com.util.FslLog;
import com.util.GenUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ADMIN on 12/19/2017.
 */

public class POItemDtlHandler {

    static String TAG = "POItemDtlHandler";

    public static boolean updatePOItemDtl(Context mContext, POItemDtl poItemDtl) {

        try {
            DBHelper mydb = new DBHelper(mContext);
            SQLiteDatabase db = mydb.getWritableDatabase();
            ContentValues contentValues = new ContentValues();


            //allow
            contentValues.put("SampleCodeID", poItemDtl.SampleCodeID);
            contentValues.put("AvailableQty", poItemDtl.AvailableQty);
            contentValues.put("AllowedinspectionQty", poItemDtl.AllowedinspectionQty);
            contentValues.put("InspectedQty", poItemDtl.InspectedQty);
            contentValues.put("AcceptedQty", poItemDtl.AcceptedQty);
            contentValues.put("FurtherInspectionReqd", poItemDtl.FurtherInspectionReqd);
            contentValues.put("ShortStockQty", poItemDtl.ShortStockQty);


            //allow//Earliear it was commented// Uncomment for carton update //Shekhar
//            contentValues.put("CartonsInspected", poItemDtl.CartonsInspected);
//            contentValues.put("CartonsPacked", poItemDtl.CartonsPacked);
//            contentValues.put("CartonsPacked2", poItemDtl.CartonsPacked2);
//
//            contentValues.put("AllowedCartonInspection", poItemDtl.AllowedCartonInspection);

            contentValues.put("recDirty", poItemDtl.recDirty);
            contentValues.put("recDt", AppConfig.getCurrntDate());
//            contentValues.put("recDt", poItemDtl.recDt);//added by shekhar
            contentValues.put("recEnable", 1);

            int rows = db.update("QRPOItemdtl", contentValues, "pRowID"
                    + " = '" + poItemDtl.QrItemID + "'", null);
            if (rows == 0) {
//                long result = db.insert("QRPOItemdtl", null, contentValues);
                FslLog.d(TAG, " QRPOItemdtl table NOT UPDATED.........??????????????.......");
            } else {
                FslLog.d(TAG, " QRPOItemdtl type update result................" + rows);
            }
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
            FslLog.d(TAG, "Exception to inserting QRPOItemdtl");
        }


        return true;
    }

    public static boolean updatePOItemDtlPKGAppDetails(Context mContext, POItemPkgAppDetail poItemDtl) {

        try {
            DBHelper mydb = new DBHelper(mContext);
            SQLiteDatabase db = mydb.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("pRowID", poItemDtl.pRowID);
            contentValues.put("LocID", poItemDtl.LocID);
            contentValues.put("DescrID", poItemDtl.DescrID);
            contentValues.put("SampleSizeID", poItemDtl.SampleSizeID);
            contentValues.put("SampleSizeValue", poItemDtl.SampleSizeValue);
            contentValues.put("InspectionResultID", poItemDtl.InspectionResultID);
            contentValues.put("recDt", AppConfig.getCurrntDate());
            contentValues.put("recEnable", 1);
            contentValues.put("LocID", FClientConfig.LOC_ID);
            contentValues.put("recUser", LogInHandler.getUserId(mContext));

            int rows = db.update("QRPOItemDtl_PKG_App_Details", contentValues, "pRowID"
                    + " = '" + poItemDtl.pRowID + "'", null);
            if (rows == 0) {
//                long result = db.insert("QRPOItemdtl", null, contentValues);
                FslLog.d(TAG, " QRPOItemDtl_PKG_App_Details table NOT UPDATED.........??????????????.......");
            } else {
                FslLog.d(TAG, " QRPOItemDtl_PKG_App_Details type update result................" + rows);
            }
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
            FslLog.d(TAG, "Exception to inserting QRPOItemdtl");
        }
        return true;
    }

    public static boolean insertPOItemDtlPKGAppDetails(Context mContext, POItemPkgAppDetail poItemDtl,POItemDtl poItemDtlItem) {

        try {
            DBHelper mydb = new DBHelper(mContext);
            SQLiteDatabase db = mydb.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("pRowID", poItemDtl.pRowID);
            contentValues.put("LocID", poItemDtl.LocID);
            contentValues.put("DescrID", poItemDtl.DescrID);
            contentValues.put("SampleSizeID", poItemDtl.SampleSizeID);
            contentValues.put("SampleSizeValue", poItemDtl.SampleSizeValue);
            contentValues.put("InspectionResultID", poItemDtl.InspectionResultID);
            contentValues.put("recDt", AppConfig.getCurrntDate());
            contentValues.put("recEnable", 1);
            contentValues.put("recUser", LogInHandler.getUserId(mContext));
            contentValues.put("LocID", FClientConfig.LOC_ID);
            contentValues.put("QRHdrID", poItemDtlItem.QRHdrID);
            contentValues.put("QRPOItemHdrID", poItemDtlItem.QRPOItemHdrID);
            contentValues.put("QRPOItemDtlID", poItemDtlItem.pRowID);
            contentValues.put("recUser", LogInHandler.getUserId(mContext));
            long result = db.insert("QRPOItemDtl_PKG_App_Details", null, contentValues);
            FslLog.d(TAG, " QRPOItemDtl_PKG_App_Details table inserted.........??????????????......."+result);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
            FslLog.d(TAG, "Exception to inserting QRPOItemdtl");
        }
        return true;
    }

    public static boolean updateOnSIte(Context mContext, OnSIteModal onSIteModal) {

        try {
            DBHelper mydb = new DBHelper(mContext);
            SQLiteDatabase db = mydb.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("pRowID", onSIteModal.pRowID);
            contentValues.put("LocID", onSIteModal.LocID);
            contentValues.put("SampleSizeID", onSIteModal.SampleSizeID);
            contentValues.put("SampleSizeValue", onSIteModal.SampleSizeValue);
            contentValues.put("InspectionResultID", onSIteModal.InspectionResultID);
            contentValues.put("InspectionLevelID", onSIteModal.InspectionLevelID);
            contentValues.put("recDt", AppConfig.getCurrntDate());
            contentValues.put("LocID", FClientConfig.LOC_ID);
            contentValues.put("recUser", LogInHandler.getUserId(mContext));
            int rows = db.update("QRPOItemDtl_OnSite_Test", contentValues, "pRowID" + " = '" + onSIteModal.pRowID + "'", null);
            if (rows == 0) {
                FslLog.d(TAG, " QRPOItemDtl_OnSite_Test table NOT UPDATED................");
            } else {
                FslLog.d(TAG, " QRPOItemDtl_OnSite_Test type update result................" + rows);
            }
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
            FslLog.d(TAG, "Exception to inserting QRPOItemdtl");
        }


        return true;
    }

    public static boolean insertOnSIte(Context mContext, OnSIteModal onSIteModal,POItemDtl poItemDtl) {

        try {
            DBHelper mydb = new DBHelper(mContext);
            SQLiteDatabase db = mydb.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("pRowID", onSIteModal.pRowID);
            contentValues.put("LocID", FClientConfig.LOC_ID);
            contentValues.put("SampleSizeID", onSIteModal.SampleSizeID);
            contentValues.put("OnSiteTestID", onSIteModal.OnSiteTestID);
            contentValues.put("SampleSizeValue", onSIteModal.SampleSizeValue);
            contentValues.put("InspectionResultID", onSIteModal.InspectionResultID);
            contentValues.put("InspectionLevelID", onSIteModal.InspectionLevelID);
            contentValues.put("QRHdrID", poItemDtl.QRHdrID);
            contentValues.put("QRPOItemHdrID", poItemDtl.QRPOItemHdrID);
            contentValues.put("QRPOItemDtlID", poItemDtl.pRowID);
            contentValues.put("recDt", AppConfig.getCurrntDate());
            contentValues.put("recEnable", "1");
            contentValues.put("LocID", FClientConfig.LOC_ID);
            contentValues.put("recUser", LogInHandler.getUserId(mContext));
            long result = db.insert("QRPOItemDtl_OnSite_Test", null, contentValues);
            FslLog.d(TAG, " QRPOItemDtl_OnSite_Test table insert result................" + result);

            db.close();
        } catch (Exception e) {
            e.printStackTrace();
            FslLog.d(TAG, "Exception to inserting QRPOItemdtl");
        }


        return true;
    }

    public static boolean deleteSampleCollected(Context mContext, String prowId) {

        try {
            DBHelper mydb = new DBHelper(mContext);
            SQLiteDatabase db = mydb.getWritableDatabase();
            int rows = db.delete("QRPOItemDtl_Sample_Purpose", "pRowID" + " = '" + prowId + "'", null);
            if (rows == 0) {
                FslLog.d(TAG, " QRPOItemDtl_Sample_Purpose table NOT DELETED................");
            } else {
                FslLog.d(TAG, " QRPOItemDtl_Sample_Purpose type deleted result................" + rows);
            }
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
            FslLog.d(TAG, "Exception to inserting QRPOItemdtl");
        }
        return true;
    }

    public static boolean updateSampleCollected(Context mContext, SampleCollectedModal sampleCollectedModal) {

        try {
            DBHelper mydb = new DBHelper(mContext);
            SQLiteDatabase db = mydb.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("pRowID", sampleCollectedModal.pRowID);
//            contentValues.put("LocID", sampleCollectedModal.LocID);
            contentValues.put("SamplePurposeID", sampleCollectedModal.SamplePurposeID);
            contentValues.put("SampleNumber", sampleCollectedModal.SampleNumber);
            contentValues.put("recDt", AppConfig.getCurrntDate());
            int rows = db.update("QRPOItemDtl_Sample_Purpose", contentValues, "pRowID" + " = '" + sampleCollectedModal.pRowID + "'", null);
            if (rows == 0) {
                FslLog.d(TAG, " QRPOItemDtl_Sample_Purpose table NOT UPDATED................");
            } else {
                FslLog.d(TAG, " QRPOItemDtl_Sample_Purpose type update result................" + rows);
            }
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
            FslLog.d(TAG, "Exception to inserting QRPOItemdtl");
        }


        return true;
    }

    public static boolean insertSampleCollected(Context mContext, SampleCollectedModal sampleCollectedModal,POItemDtl poItemDtl) {

        try {
            DBHelper mydb = new DBHelper(mContext);
            SQLiteDatabase db = mydb.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("pRowID", sampleCollectedModal.pRowID);
            contentValues.put("LocID", FClientConfig.LOC_ID);
            contentValues.put("SamplePurposeID", sampleCollectedModal.SamplePurposeID);
            contentValues.put("SampleNumber", sampleCollectedModal.SampleNumber);
            contentValues.put("recDt", AppConfig.getCurrntDate());
            contentValues.put("QRHdrID", poItemDtl.QRHdrID);
            contentValues.put("QRPOItemHdrID", poItemDtl.QRPOItemHdrID);
            contentValues.put("QRPOItemDtlID", poItemDtl.pRowID);
            contentValues.put("recEnable", "1");
            contentValues.put("recUser", LogInHandler.getUserId(mContext));
            long result = db.insert("QRPOItemDtl_Sample_Purpose", null, contentValues);
            FslLog.d(TAG, " QRPOItemDtl_Sample_Purpose table insert result................" + result);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
            FslLog.d(TAG, "Exception to inserting QRPOItemdtl");
        }


        return true;
    }

    public static List<SampleCollectedModal> getSampleCollectedList(Context mContext) {


        ArrayList<SampleCollectedModal> lGeneral = new ArrayList<SampleCollectedModal>();
        try {
//            int status = VisitorNonGuardHandler.getTableExistORNot(mContext, DBHelper.GENERAL_MASTER_TABLE_NAME);
//            FslLog.d(TAG, " table exist or not " + status);
//            if (status > 0) {
            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getReadableDatabase();

            String query = "SELECT * FROM " + "QRPOItemDtl_Sample_Purpose";
//                    + " WHERE " + "GenID" + "='" + GenId + "'";
            FslLog.d(TAG, "query for  get closed general list  " + query);
            Cursor cursor = database.rawQuery(query, null);


            if (cursor.getCount() > 0) {
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToNext();
                    lGeneral.add(getSampleData(cursor));
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

    private static SampleCollectedModal getSampleData(Cursor cursor) {

        SampleCollectedModal insLvHdrModal = new SampleCollectedModal();

        insLvHdrModal.pRowID = cursor.getString(cursor.getColumnIndex("pRowID"));
        insLvHdrModal.SamplePurposeID = cursor.getString(cursor.getColumnIndex("SamplePurposeID"));
        insLvHdrModal.SampleNumber = cursor.getInt(cursor.getColumnIndex("SampleNumber"));
        insLvHdrModal.recDt = cursor.getString(cursor.getColumnIndex("recDt"));

        return insLvHdrModal;

    }

    public static List<OnSIteModal> getOnSiteList(Context mContext) {


        ArrayList<OnSIteModal> lGeneral = new ArrayList<OnSIteModal>();
        try {
//            int status = VisitorNonGuardHandler.getTableExistORNot(mContext, DBHelper.GENERAL_MASTER_TABLE_NAME);
//            FslLog.d(TAG, " table exist or not " + status);
//            if (status > 0) {
            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getReadableDatabase();

            String query = "SELECT * FROM " + "QRPOItemDtl_OnSite_Test";
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

    public static ArrayList<DefectMasterModal> getDefectMasterList(Context mContext) {

        ArrayList<DefectMasterModal> lGeneral = new ArrayList<DefectMasterModal>();
        try {
//            int status = VisitorNonGuardHandler.getTableExistORNot(mContext, DBHelper.GENERAL_MASTER_TABLE_NAME);
//            FslLog.d(TAG, " table exist or not " + status);
//            if (status > 0) {
            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getReadableDatabase();

            String query = "SELECT * FROM DefectMaster";
//                    + " WHERE " + "GenID" + "='" + GenId + "'";
            FslLog.d(TAG, "query for  get closed general list  " + query);
            Cursor cursor = database.rawQuery(query, null);


            if (cursor.getCount() > 0) {
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToNext();
                    lGeneral.add(getDefectMasterData(cursor));
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

    private static DefectMasterModal getDefectMasterData(Cursor cursor) {

        DefectMasterModal insLvHdrModal = new DefectMasterModal();


        insLvHdrModal.pRowID = cursor.getString(cursor.getColumnIndex("pRowID"));
//        insLvHdrModal.LocID = cursor.getString(cursor.getColumnIndex("LocID"));
        insLvHdrModal.DefectName = cursor.getString(cursor.getColumnIndex("DefectName"));
        insLvHdrModal.Code = cursor.getString(cursor.getColumnIndex("Code"));
        insLvHdrModal.DCLTypeID = cursor.getString(cursor.getColumnIndex("DCLTypeID"));
        insLvHdrModal.recAddUser = cursor.getString(cursor.getColumnIndex("recAddUser"));
        insLvHdrModal.InspectionStage = cursor.getString(cursor.getColumnIndex("InspectionStage"));
        insLvHdrModal.chkCritical = cursor.getInt(cursor.getColumnIndex("chkCritical"));
        insLvHdrModal.chkMajor = cursor.getInt(cursor.getColumnIndex("chkMajor"));
        insLvHdrModal.chkMinor = cursor.getInt(cursor.getColumnIndex("chkMinor"));

        return insLvHdrModal;

    }
    private static OnSIteModal getData(Cursor cursor) {

        OnSIteModal insLvHdrModal = new OnSIteModal();


        insLvHdrModal.pRowID = cursor.getString(cursor.getColumnIndex("pRowID"));
        insLvHdrModal.LocID = cursor.getString(cursor.getColumnIndex("LocID"));
        insLvHdrModal.InspectionLevelID = cursor.getString(cursor.getColumnIndex("InspectionLevelID"));
        insLvHdrModal.InspectionResultID = cursor.getString(cursor.getColumnIndex("InspectionResultID"));
        insLvHdrModal.OnSiteTestID = cursor.getString(cursor.getColumnIndex("OnSiteTestID"));
        insLvHdrModal.SampleSizeID = cursor.getString(cursor.getColumnIndex("SampleSizeID"));
        insLvHdrModal.SampleSizeValue = cursor.getString(cursor.getColumnIndex("SampleSizeValue"));
        insLvHdrModal.recEnable = cursor.getInt(cursor.getColumnIndex("recEnable"));
        insLvHdrModal.recUser = cursor.getString(cursor.getColumnIndex("recUser"));
        insLvHdrModal.recDt = cursor.getString(cursor.getColumnIndex("recDt"));

        return insLvHdrModal;

    }


    /*public static boolean updatePOItemHdr(Context mContext, POItemDtl poItemDtl) {

        try {
            DBHelper mydb = new DBHelper(mContext);
            SQLiteDatabase db = mydb.getWritableDatabase();
            ContentValues contentValues = new ContentValues();

//            contentValues.put("pRowID", poItemDtl.pRowID);


            contentValues.put("SampleCodeID", poItemDtl.SampleCodeID);

            contentValues.put("AllowedinspectionQty", poItemDtl.AllowedinspectionQty);
            contentValues.put("InspectedQty", poItemDtl.InspectedQty);


//            contentValues.put("CartonsInspected", poItemDtl.CartonsInspected);
//            contentValues.put("CartonsPacked", poItemDtl.CartonsPacked);

//            contentValues.put("AllowedCartonInspection", poItemDtl.AllowedCartonInspection);
//            contentValues.put("CriticalDefectsAllowed", poItemDtl.CriticalDefectsAllowed);

//            contentValues.put("MajorDefectsAllowed", poItemDtl.MajorDefectsAllowed);
//            contentValues.put("MinorDefectsAllowed", poItemDtl.MinorDefectsAllowed);
            contentValues.put("recDirty", poItemDtl.recDirty);
            contentValues.put("recDt", AppConfig.getCurrntDate());
            contentValues.put("recEnable", 1);

            int rows = db.update("QRPOItemHdr", contentValues, "pRowID"
                    + " = '" + poItemDtl.pRowID + "'", null);
            if (rows == 0) {
//                long result = db.insert("QRPOItemHdr", null, contentValues);
                FslLog.d(TAG, " QRPOItemHdr table NOT UPDATED................");
            } else {
                FslLog.d(TAG, " QRPOItemHdr type update result................" + rows);
            }
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
            FslLog.d(TAG, "Exception to inserting QRPOItemHdr");
        }


        return true;
    }*/


    public static boolean updatePOItemDtlOfWorkmanshipAndCarton(Context mContext, POItemDtl1 poItemDtl) {

        try {
            DBHelper mydb = new DBHelper(mContext);
            SQLiteDatabase db = mydb.getWritableDatabase();
            ContentValues contentValues = new ContentValues();


            //allow
//            contentValues.put("SampleCodeID", poItemDtl.SampleCodeID);
//            contentValues.put("AvailableQty", poItemDtl.AvailableQty);
//            contentValues.put("AllowedinspectionQty", poItemDtl.AllowedinspectionQty);
//            contentValues.put("InspectedQty", poItemDtl.InspectedQty);
//            contentValues.put("AcceptedQty", poItemDtl.AcceptedQty);
//            contentValues.put("FurtherInspectionReqd", poItemDtl.FurtherInspectionReqd);
//            contentValues.put("ShortStockQty", poItemDtl.ShortStockQty);


            //allow
            FslLog.d(TAG,"CartonsInspected db="+poItemDtl.CartonsInspected);
            contentValues.put("CartonsInspected", poItemDtl.CartonsInspected);
            contentValues.put("CartonsPacked", poItemDtl.CartonsPacked);
            contentValues.put("CartonsPacked2", poItemDtl.CartonsPacked2);
            contentValues.put("AllowedCartonInspection", poItemDtl.AllowedCartonInspection);


            contentValues.put("recDirty", poItemDtl.recDirty);
            contentValues.put("recDt", AppConfig.getCurrntDate());
            contentValues.put("recEnable", 1);

            int rows = db.update("QRPOItemdtl", contentValues, "pRowID"
                    + " = '" + poItemDtl.QrItemID + "'", null);
            if (rows == 0) {
//                long result = db.insert("QRPOItemdtl", null, contentValues);
                FslLog.d(TAG, " QRPOItemdtl table NOT UPDATED.........??????????????.......");
            } else {
                FslLog.d(TAG, " QRPOItemdtl type update result................" + rows);
            }
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
            FslLog.d(TAG, "Exception to inserting QRPOItemdtl");
        }


        return true;
    }


    public static boolean updatePOItemHdrOfWorkmanshipAndCarton(Context mContext, POItemDtl1 poItemDtl) {

        try {
            DBHelper mydb = new DBHelper(mContext);
            SQLiteDatabase db = mydb.getWritableDatabase();
            ContentValues contentValues = new ContentValues();

//            contentValues.put("pRowID", poItemDtl.pRowID);


            contentValues.put("SampleCodeID", poItemDtl.SampleCodeID);

            contentValues.put("AllowedinspectionQty", poItemDtl.AllowedinspectionQty);
            contentValues.put("InspectedQty", poItemDtl.InspectedQty);


            contentValues.put("CartonsInspected", poItemDtl.CartonsInspected);
//            contentValues.put("CartonsPacked2", poItemDtl.CartonsPacked2);


            contentValues.put("CartonsPacked", poItemDtl.CartonsPacked);
            contentValues.put("AllowedCartonInspection", poItemDtl.AllowedCartonInspection);
            contentValues.put("CriticalDefectsAllowed", poItemDtl.CriticalDefectsAllowed);
            contentValues.put("MajorDefectsAllowed", poItemDtl.MajorDefectsAllowed);
            contentValues.put("MinorDefectsAllowed", poItemDtl.MinorDefectsAllowed);
            contentValues.put("recDirty", poItemDtl.recDirty);
            contentValues.put("recDt", AppConfig.getCurrntDate());
            contentValues.put("recEnable", 1);

            //pRowID=DEL0366803
            Log.d(TAG,"updatePOItemHdrOfWorkmanshipAndCarton poItemDtl.pRowID"+poItemDtl.pRowID);
            Log.d(TAG,"updatePOItemHdrOfWorkmanshipAndCarton poItemDtl.QRPOItemHdrID"+poItemDtl.QRPOItemHdrID);
            int rows = db.update("QRPOItemHdr", contentValues, "pRowID"
                    + " = '" + poItemDtl.QRPOItemHdrID + "'", null);
            if (rows == 0) {
//                long result = db.insert("QRPOItemHdr", null, contentValues);
                FslLog.d(TAG, " QRPOItemHdr table NOT UPDATED................");
                db.close();
                return false;
            } else {
                FslLog.d(TAG, " QRPOItemHdr type update result................" + rows);
            }
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
            FslLog.d(TAG, "Exception to inserting QRPOItemHdr");
        }


        return true;
    }


    public static boolean updatePOItemDtlOnInspection(Context mContext, POItemDtl poItemDtl) {

        try {
            DBHelper mydb = new DBHelper(mContext);
            SQLiteDatabase db = mydb.getWritableDatabase();
            ContentValues contentValues = new ContentValues();


            //allow
            contentValues.put("SampleCodeID", poItemDtl.SampleCodeID);
            contentValues.put("AvailableQty", poItemDtl.AvailableQty);
            contentValues.put("AllowedinspectionQty", poItemDtl.AllowedinspectionQty);
            contentValues.put("InspectedQty", poItemDtl.InspectedQty);
            contentValues.put("AcceptedQty", poItemDtl.AcceptedQty);
            contentValues.put("FurtherInspectionReqd", poItemDtl.FurtherInspectionReqd);
            contentValues.put("ShortStockQty", poItemDtl.ShortStockQty);


            //allow no need already save
//            contentValues.put("CartonsInspected", poItemDtl.CartonsInspected);
//            contentValues.put("CartonsPacked", poItemDtl.CartonsPacked);
//            contentValues.put("CartonsPacked2", poItemDtl.CartonsPacked2);
//            contentValues.put("AllowedCartonInspection", poItemDtl.AllowedCartonInspection);


            contentValues.put("recDirty", poItemDtl.recDirty);
            contentValues.put("recDt", AppConfig.getCurrntDate());
            contentValues.put("recEnable", 1);

            int rows = db.update("QRPOItemdtl", contentValues, "pRowID"
                    + " = '" + poItemDtl.QrItemID + "'", null);
            if (rows == 0) {
//                long result = db.insert("QRPOItemdtl", null, contentValues);
                FslLog.d(TAG, " QRPOItemdtl table NOT UPDATED.........??????????????.......");
            } else {
                FslLog.d(TAG, " QRPOItemdtl type update result................" + rows);
            }
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
            FslLog.d(TAG, "Exception to inserting QRPOItemdtl");
        }


        return true;
    }


    public static boolean updatePOItemHdrOnInspection(Context mContext, POItemDtl poItemDtl) {

        try {
            DBHelper mydb = new DBHelper(mContext);
            SQLiteDatabase db = mydb.getWritableDatabase();
            ContentValues contentValues = new ContentValues();

//            contentValues.put("pRowID", poItemDtl.pRowID);


            contentValues.put("SampleCodeID", poItemDtl.SampleCodeID);

            contentValues.put("AllowedinspectionQty", poItemDtl.AllowedinspectionQty);
            contentValues.put("InspectedQty", poItemDtl.InspectedQty);


            /*contentValues.put("CartonsInspected", poItemDtl.CartonsInspected);
            contentValues.put("CartonsPacked", poItemDtl.CartonsPacked);
            contentValues.put("CartonsPacked2", poItemDtl.CartonsPacked2);
            contentValues.put("AllowedCartonInspection", poItemDtl.AllowedCartonInspection);*/
            contentValues.put("CriticalDefectsAllowed", poItemDtl.CriticalDefectsAllowed);
            contentValues.put("MajorDefectsAllowed", poItemDtl.MajorDefectsAllowed);
            contentValues.put("MinorDefectsAllowed", poItemDtl.MinorDefectsAllowed);
            contentValues.put("recDirty", poItemDtl.recDirty);
            contentValues.put("recDt", AppConfig.getCurrntDate());
            contentValues.put("recEnable", 1);

            /*int rows = db.update("QRPOItemHdr", contentValues, "pRowID"
                    + " = '" + poItemDtl.pRowID + "'", null);*/
            Log.d(TAG,"updatePOItemHdrOfWorkmanshipAndCarton poItemDtl.pRowID"+poItemDtl.pRowID);
            Log.d(TAG,"updatePOItemHdrOfWorkmanshipAndCarton poItemDtl.QRPOItemHdrID"+poItemDtl.QRPOItemHdrID);
            //change the prow id value for QRPOItemHdr table
            int rows = db.update("QRPOItemHdr", contentValues, "pRowID"
                    + " = '" + poItemDtl.QRPOItemHdrID + "'", null);
            if (rows == 0) {
//                long result = db.insert("QRPOItemHdr", null, contentValues);
                FslLog.d(TAG, " QRPOItemHdr table NOT UPDATED................");
            } else {
                FslLog.d(TAG, " QRPOItemHdr type update result................" + rows);
            }
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
            FslLog.d(TAG, "Exception to inserting QRPOItemHdr");
        }


        return true;
    }

    public static List<POItemDtl> getItemList(Context mContext, String pInspectionID) {


        List<POItemDtl> itemArrayList = new ArrayList<POItemDtl>();
        try {

            if (!GenUtils.columnExistsInTable(mContext, "QRPOItemdtl", "RetailPrice")) {
                FslLog.e(TAG, " not column found " + "RetailPrice" + " so alter execute as REAL");
                GetDataHandler.handleToAlterAsReal(mContext, "QRPOItemdtl", "RetailPrice");
            }
            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getReadableDatabase();

            String query = "Select QRItemHdr.pRowID,QRItem.RetailPrice, QRItem.pRowID QrItemID, QRItem.POItemDtlRowID, QRItem.QRHdrID, QRItem.QRPOItemHdrID,QRItem.CustomerItemRef,ifNull(QRItem.ItemDescr,'') ItemDescr,  ifNull(QRItem.PONO,'') PONO,QRItem.AvailableQty, Ifnull(QRItem.AcceptedQty,0) AcceptedQty, QRItem.FurtherInspectionReqd, QRItem.ShortStockQty," +
                    " IfNULL(QRItemHdr.SampleCodeID, '') as SampleCodeID, IfNULL(SampleSize.MainDescr,'') as SampleSizeDescr,QRItemHdr.ItemID,   QRItemHdr.AllowedInspectionQty, ifnull(QRItemHdr.InspectedQty,0) InspectedQty,  Ifnull(QRItem.CartonsPacked2,0) CartonsPacked2, Ifnull(QRItemHdr.CartonsPacked,0) CartonsPacked," +
                    " QRItemHdr.AllowedCartonInspection, Ifnull(QRItemHdr.CartonsInspected,0) CartonsInspected,  Ifnull(QRItemHdr.CriticalDefectsAllowed,0) CriticalDefectsAllowed,  ifnull(QRItemHdr.MajorDefectsAllowed,0) MajorDefectsAllowed,Ifnull(QRItem.OrderQty,0) OrderQty, ifnull(QRItemHdr.MinorDefectsAllowed,0) MinorDefectsAllowed," +
                    " IfNull(QRItemHdr.CriticalDefect,0) CriticalDefect,  IfNull(QRItemHdr.MajorDefect,0) MajorDefect,IfNull(QRItemHdr.MinorDefect,0) MinorDefect,   IfNULL(QRItem.BaseMaterialID, '') As QRItemBaseMaterialID,  IfNULL(QRItem.BaseMaterial_AddOnInfo, '') As QRItemBaseMaterial_AddOnInfo,ifnull(QRItem.EarlierInspected,0) EarlierInspected, " +
                    " IfNULL(gdResult_Barcode.MainDescr,'')  As Barcode_InspectionResult,  IfNULL(gdResult_OnSiteTest.MainDescr,'') As OnSiteTest_InspectionResult,IfNULL(gdResult_Packaging.MainDescr,'') As PKG_Me_InspectionResult,   IfNULL(gdResult_ItemMeasurement.MainDescr,'') As ItemMeasurement_InspectionResult,  IfNULL(gdResult_WorkmanShip.MainDescr,'')  As WorkmanShip_InspectionResult," +
                    " IfNULL(gdResult_Overall.MainDescr,'') As Overall_InspectionResult , 0 As recDirty,  (Select count(digitals.pRoWID) from QRPOItemDtl_Image digitals where digitals.recEnable=1 and digitals.QrHdrID=QRItemHdr.QRHdrID and digitals.QrPOItemHdrID=QRItemHdr.pRowID) Digitals ," +
                    " IFNULL((Select Count(*) From Enclosures e Where e.QrpoItemHdrId = QRItemHdr.pRowID and e.QrhdrID = QRHdr.pRowID And 1 = (case when (e.ContextType in ('51','52') And e.EnclType = '05') Then 0 Else 1 End)),0) As EnclCount,  1 As IsSelected, 0 As SizeBreakUP, 0 As ShipToBreakUP,ifnull(QRItemHdr.testReportStatus,0) testReportStatus,0 As DuplicateFlag, " +
                    " IFNULL(QRItem.HologramNo,'') As HologramNo,QRItem.POMasterPackQty,  (Case When Date(IFNULL(QRItem.Hologram_ExpiryDt,'2010-01-01 00:00:00')) >= Date('now','localtime') Then 0 Else 1 End) As IsHologramExpired  " + " , QRItem.ItemRepeat " +
                    " FROM QRPOItemDtl QRItem " +
                    " INNER JOIN QRPOItemHdr QRItemHdr On QRItemHdr.pRowID = QRItem.QRPOItemHdrID  INNER JOIN QRFeedbackHdr QRHdr on QRHdr.pRowID = QRItemHdr.QRHdrID  LEFT JOIN GenMst gdResult_Overall On gdResult_Overall.GenID = '530' AND gdResult_Overall.pGenRowID = QRItemHdr.Overall_InspectionResultID" +
                    " LEFT JOIN GenMst gdResult_Barcode On gdResult_Barcode.GenID = '530' AND gdResult_Barcode.pGenRowID = QRItemHdr.Barcode_InspectionResultID  LEFT JOIN GenMst gdResult_OnSiteTest On gdResult_OnSiteTest.GenID = '530' AND gdResult_OnSiteTest.pGenRowID = QRItemHdr.OnSiteTest_InspectionResultID " +
                    " LEFT JOIN GenMst gdResult_ItemMeasurement On gdResult_ItemMeasurement.GenID = '530' AND gdResult_ItemMeasurement.pGenRowID = QRItemHdr.ItemMeasurement_InspectionResultID   LEFT JOIN GenMst gdResult_WorkmanShip On gdResult_WorkmanShip.GenID = '530' AND gdResult_WorkmanShip.pGenRowID = QRItemHdr.WorkmanShip_InspectionResultID " +
                    " LEFT JOIN GenMst gdResult_Packaging On gdResult_Packaging.GenID = '530' AND gdResult_Packaging.pGenRowID = QRItemHdr.PKG_Me_InspectionResultID " +
                    " LEFT JOIN GenMst SampleSize On SampleSize.pGenRowID = QRItemHdr.SampleCodeID  WHERE QRItem.QRHdrID = '" + pInspectionID + "' and QRItem.recEnable=1"
                    + " Order by QRItem.QRPOItemHdrID,QRItem.pRowID";

            /* String query = "SELECT * FROM " + "QRPOItemdtl" + " where QRHdrID='" + pRowID + "'";*/
            FslLog.d(TAG, "get update query for  get QRPOItemdtl list  " + query);
            Cursor cursor = database.rawQuery(query, null);


            POItemDtl poItemDtl;
            if (cursor.getCount() > 0) {
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToNext();
                    poItemDtl = new POItemDtl();
                    Log.d("SizeQtyActivity","poItemDtl.pRowID==="+cursor.getString(cursor.getColumnIndex("QrItemID")));
//                    poItemDtl.pRowID = cursor.getString(cursor.getColumnIndex("pRowID"));
                    poItemDtl.pRowID = cursor.getString(cursor.getColumnIndex("QrItemID"));
//                    poItemDtl.LocID = cursor.getString(cursor.getColumnIndex("LocID"));
                    poItemDtl.QRHdrID = cursor.getString(cursor.getColumnIndex("QRHdrID"));
                    poItemDtl.QRPOItemHdrID = cursor.getString(cursor.getColumnIndex("QRPOItemHdrID"));
                    poItemDtl.POItemDtlRowID = cursor.getString(cursor.getColumnIndex("POItemDtlRowID"));
                    Log.d("SizeQtyActivity","poItemDtl.pRowID="+poItemDtl.pRowID);
                    Log.d("SizeQtyActivity","poItemDtl.QRHdrID="+poItemDtl.QRHdrID);
                    Log.d("SizeQtyActivity","poItemDtl.QRPOItemHdrID="+poItemDtl.QRPOItemHdrID);
                    Log.d("SizeQtyActivity","poItemDtl.POItemDtlRowID="+poItemDtl.POItemDtlRowID);
//                    poItemDtl.SampleRqstHdrlRowID = cursor.getString(cursor.getColumnIndex("SampleRqstHdrlRowID"));
//                    poItemDtl.QualityDefectHdrID = cursor.getString(cursor.getColumnIndex("QualityDefectHdrID"));
//                    poItemDtl.BaseMaterialID = cursor.getString(cursor.getColumnIndex("BaseMaterialID"));
//                    poItemDtl.BaseMaterial_AddOnInfo = cursor.getString(cursor.getColumnIndex("BaseMaterial_AddOnInfo"));
//                    poItemDtl.POTnARowID = cursor.getString(cursor.getColumnIndex("POTnARowID"));


                    poItemDtl.SampleCodeID = cursor.getString(cursor.getColumnIndex("SampleCodeID"));
                    poItemDtl.AvailableQty = cursor.getInt(cursor.getColumnIndex("AvailableQty"));
                    poItemDtl.AllowedinspectionQty = cursor.getInt(cursor.getColumnIndex("AllowedInspectionQty"));
                    poItemDtl.InspectedQty = cursor.getInt(cursor.getColumnIndex("InspectedQty"));
//                    poItemDtl.LatestDelDt = cursor.getString(cursor.getColumnIndex("LatestDelDt"));

                    poItemDtl.AcceptedQty = cursor.getInt(cursor.getColumnIndex("AcceptedQty"));
                    Log.d("SizeQtyActivity","poItemDtl.AcceptedQty"+poItemDtl.AcceptedQty);
                    poItemDtl.FurtherInspectionReqd = cursor.getInt(cursor.getColumnIndex("FurtherInspectionReqd"));
                    poItemDtl.ShortStockQty = cursor.getInt(cursor.getColumnIndex("ShortStockQty"));
//                    poItemDtl.LatestDelDt = cursor.getString(cursor.getColumnIndex("LatestDelDt"));//added by shekhar


//                    poItemDtl.PalletPackedQty = cursor.getInt(cursor.getColumnIndex("PalletPackedQty"));
//                    poItemDtl.MasterPackedQty = cursor.getInt(cursor.getColumnIndex("MasterPackedQty"));
//                    poItemDtl.InnerPackedQty = cursor.getInt(cursor.getColumnIndex("InnerPackedQty"));
//                    poItemDtl.PackedQty = cursor.getInt(cursor.getColumnIndex("PackedQty"));
//                    poItemDtl.UnpackedQty = cursor.getInt(cursor.getColumnIndex("UnpackedQty"));
//                    poItemDtl.UnfinishedQty = cursor.getInt(cursor.getColumnIndex("UnfinishedQty"));

                    poItemDtl.CartonsInspected = cursor.getInt(cursor.getColumnIndex("CartonsInspected"));
                    Log.d("SizeQtyActivity","poItemDtl.CartonsInspected="+poItemDtl.CartonsInspected);
                    poItemDtl.CartonsPacked = cursor.getInt(cursor.getColumnIndex("CartonsPacked"));
                    poItemDtl.CartonsPacked2 = cursor.getInt(cursor.getColumnIndex("CartonsPacked2"));
                    poItemDtl.AllowedCartonInspection = cursor.getInt(cursor.getColumnIndex("AllowedCartonInspection"));

                    poItemDtl.CriticalDefectsAllowed = cursor.getInt(cursor.getColumnIndex("CriticalDefectsAllowed"));

                    poItemDtl.MajorDefectsAllowed = cursor.getInt(cursor.getColumnIndex("MajorDefectsAllowed"));
                    poItemDtl.MinorDefectsAllowed = cursor.getInt(cursor.getColumnIndex("MinorDefectsAllowed"));
                    poItemDtl.CriticalDefect = cursor.getInt(cursor.getColumnIndex("CriticalDefect"));
                    poItemDtl.MajorDefect = cursor.getInt(cursor.getColumnIndex("MajorDefect"));
                    poItemDtl.MinorDefect = cursor.getInt(cursor.getColumnIndex("MinorDefect"));
                    poItemDtl.RetailPrice = cursor.getDouble(cursor.getColumnIndex("RetailPrice"));
                    poItemDtl.POMasterPackQty = cursor.getInt(cursor.getColumnIndex("POMasterPackQty"));


//                    poItemDtl.recAddDt = cursor.getString(cursor.getColumnIndex("recAddDt"));
//                    poItemDtl.recEnable = cursor.getInt(cursor.getColumnIndex("recEnable"));
                    poItemDtl.recDirty = cursor.getInt(cursor.getColumnIndex("recDirty"));

//                    poItemDtl.recAddUser = cursor.getString(cursor.getColumnIndex("recAddUser"));
//                    poItemDtl.recUser = cursor.getString(cursor.getColumnIndex("recUser"));

//                    poItemDtl.recDt = cursor.getString(cursor.getColumnIndex("recDt"));
//                    poItemDtl.ediDt = cursor.getString(cursor.getColumnIndex("ediDt"));
                    poItemDtl.PONO = cursor.getString(cursor.getColumnIndex("PONO"));

//                    poItemDtl.BuyerPODt = cursor.getString(cursor.getColumnIndex("BuyerPODt"));
                    poItemDtl.ItemDescr = cursor.getString(cursor.getColumnIndex("ItemDescr"));

                    poItemDtl.OrderQty = cursor.getString(cursor.getColumnIndex("OrderQty"));
                    poItemDtl.EarlierInspected = cursor.getInt(cursor.getColumnIndex("EarlierInspected"));
//                    poItemDtl.POMasterPackQty = cursor.getInt(cursor.getColumnIndex("POMasterPackQty"));
//                    poItemDtl.MR = cursor.getString(cursor.getColumnIndex("MR"));
//                    poItemDtl.LR = cursor.getString(cursor.getColumnIndex("LR"));
//                    poItemDtl.CustomerDepartment = cursor.getString(cursor.getColumnIndex("CustomerDepartment"));
                    poItemDtl.CustomerItemRef = cursor.getString(cursor.getColumnIndex("CustomerItemRef"));
//                    poItemDtl.LatestDelDt = cursor.getString(cursor.getColumnIndex("LatestDelDt"));
                    poItemDtl.HologramNo = cursor.getString(cursor.getColumnIndex("HologramNo"));
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

                    poItemDtl.QrItemID = cursor.getString(cursor.getColumnIndex("QrItemID"));
                    poItemDtl.SampleSizeDescr = cursor.getString(cursor.getColumnIndex("SampleSizeDescr"));
                    poItemDtl.ItemID = cursor.getString(cursor.getColumnIndex("ItemID"));
                    poItemDtl.QRItemBaseMaterialID = cursor.getString(cursor.getColumnIndex("QRItemBaseMaterialID"));
                    poItemDtl.QRItemBaseMaterial_AddOnInfo = cursor.getString(cursor.getColumnIndex("QRItemBaseMaterial_AddOnInfo"));
                    poItemDtl.Barcode_InspectionResult = cursor.getString(cursor.getColumnIndex("Barcode_InspectionResult"));
                    poItemDtl.OnSiteTest_InspectionResult = cursor.getString(cursor.getColumnIndex("OnSiteTest_InspectionResult"));
                    poItemDtl.ItemMeasurement_InspectionResult = cursor.getString(cursor.getColumnIndex("ItemMeasurement_InspectionResult"));
                    poItemDtl.WorkmanShip_InspectionResult = cursor.getString(cursor.getColumnIndex("WorkmanShip_InspectionResult"));
                    poItemDtl.Overall_InspectionResult = cursor.getString(cursor.getColumnIndex("Overall_InspectionResult"));
                    poItemDtl.PKG_Me_InspectionResult = cursor.getString(cursor.getColumnIndex("PKG_Me_InspectionResult"));
                    poItemDtl.Digitals = cursor.getInt(cursor.getColumnIndex("Digitals"));
                    poItemDtl.EnclCount = cursor.getInt(cursor.getColumnIndex("EnclCount"));
                    poItemDtl.IsSelected = cursor.getInt(cursor.getColumnIndex("IsSelected"));
                    poItemDtl.SizeBreakUP = cursor.getInt(cursor.getColumnIndex("SizeBreakUP"));
                    poItemDtl.ShipToBreakUP = cursor.getString(cursor.getColumnIndex("ShipToBreakUP"));
                    poItemDtl.testReportStatus = cursor.getInt(cursor.getColumnIndex("testReportStatus"));
                    poItemDtl.DuplicateFlag = cursor.getInt(cursor.getColumnIndex("DuplicateFlag"));
                    poItemDtl.IsHologramExpired = cursor.getInt(cursor.getColumnIndex("IsHologramExpired"));
                    poItemDtl.ItemRepeat = cursor.getInt(cursor.getColumnIndex("ItemRepeat"));

                    List<Integer> listImportant = ItemInspectionDetailHandler.IsImportant(mContext, poItemDtl.QRHdrID, poItemDtl.QRPOItemHdrID);
                    if (listImportant != null && listImportant.size() > 0) {
                        boolean isFounded = false;
                        for (int p = 0; p < listImportant.size(); p++) {
                            if (listImportant.get(p) == 1) {
                                isFounded = true;
                                break;
                            }
                        }
                        if (isFounded) {
                            poItemDtl.IsImportant = 1;
                        }
                    }
                    itemArrayList.add(poItemDtl);
                }
            }
            cursor.close();
            database.close();
            FslLog.d(TAG, " cout of founded poItemDtl " + itemArrayList.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemArrayList;
    }

    /*
     * creted by shekhar
     */
    public static String getPOListItemLatestDelDate(Context mContext, String pInspectionID, POItemDtl poItemDtl) {
        String latestDelDate = "";
        try {
            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getReadableDatabase();
            String query = "Select * from QRPOItemdtl WHERE QRHdrID = '" + pInspectionID + "'";
            FslLog.d(TAG, "getPOListItem update query for  get QRPOItemdtl list  " + query);
            Cursor cursor = database.rawQuery(query, null);

            Log.e("LatestDelDtprowid","poItemDtl.pRowID="+poItemDtl.pRowID);
            Log.e("LatestDelDtprowid","poItemDtl.QRPOItemHdrID="+poItemDtl.QRPOItemHdrID);

            if (cursor.getCount() > 0) {
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToNext();
                    Log.e("LatestDelDtprowid","cursor prowid"+cursor.getString(cursor.getColumnIndex("pRowID")));
                    /*if (poItemDtl.pRowID.equals(cursor.getString(cursor.getColumnIndex("pRowID")))) {
                        latestDelDate = cursor.getString(cursor.getColumnIndex("LatestDelDt"));
                    }*/
                    if (poItemDtl.QrItemID.equals(cursor.getString(cursor.getColumnIndex("pRowID")))) {
                        latestDelDate = cursor.getString(cursor.getColumnIndex("LatestDelDt"));
                    }
                }
            }else {
                Log.e("LatestDelDtcursor","cursor count =0");
            }
            cursor.close();
            database.close();
//            FslLog.d(TAG, " count of founded poItemDtl item " + itemArrayList.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return latestDelDate;
    }

    public static List<String> getPOList(Context mContext, String pInspectionID) {


        List<String> itemArrayList = new ArrayList<String>();
        try {
            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getReadableDatabase();

            String query = "Select  distinct PONO FROM QRPOItemDtl WHERE QRHdrID = '" + pInspectionID + "' and  recEnable=1";

            /* String query = "SELECT * FROM " + "QRPOItemdtl" + " where QRHdrID='" + pRowID + "'";*/
            FslLog.d(TAG, "get PO  from QRPOItemdtl list  " + query);
            Cursor cursor = database.rawQuery(query, null);


            if (cursor.getCount() > 0) {
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToNext();
                    String PONO = cursor.getString(cursor.getColumnIndex("PONO"));
                    itemArrayList.add(PONO);
                }
            }
            cursor.close();
            database.close();
            FslLog.d(TAG, " cout of founded PO lsit " + itemArrayList.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemArrayList;
    }
    public static List<String> getItemIdList(Context mContext, String pInspectionID) {


        List<String> itemArrayList = new ArrayList<String>();
        try {
            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getReadableDatabase();

            String query = "Select  distinct CustomerItemRef FROM QRPOItemDtl WHERE QRHdrID = '" + pInspectionID + "' and  recEnable=1";

            /* String query = "SELECT * FROM " + "QRPOItemdtl" + " where QRHdrID='" + pRowID + "'";*/
            FslLog.d(TAG, "get Item id  from QRPOItemdtl list  " + query);
            Cursor cursor = database.rawQuery(query, null);


            if (cursor.getCount() > 0) {
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToNext();
                    String ItemIds = cursor.getString(cursor.getColumnIndex("CustomerItemRef"));
                    itemArrayList.add(ItemIds);
                }
            }
            cursor.close();
            database.close();
            FslLog.d(TAG, " cout of founded item id lsit " + itemArrayList.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return itemArrayList;
    }

    public static List<POItemPkgAppDetail> getPKGAPPList(Context mContext) {


        List<POItemPkgAppDetail> pKGAppListItem = new ArrayList<POItemPkgAppDetail>();
        try {
            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getReadableDatabase();

            String query = "Select * from QRPOItemDtl_PKG_App_Details";

            /* String query = "SELECT * FROM " + "QRPOItemdtl" + " where QRHdrID='" + pRowID + "'";*/
            FslLog.d(TAG, "get PO  from QRPOItemdtl list  " + query);
            Cursor cursor = database.rawQuery(query, null);

            POItemPkgAppDetail pOItemPkgAppDetail;
            if (cursor.getCount() > 0) {
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToNext();
                    pOItemPkgAppDetail = new POItemPkgAppDetail();
                    pOItemPkgAppDetail.pRowID = cursor.getString(cursor.getColumnIndex("pRowID"));
                    pOItemPkgAppDetail.LocID = cursor.getString(cursor.getColumnIndex("LocID"));
                    pOItemPkgAppDetail.DescrID = cursor.getString(cursor.getColumnIndex("DescrID"));
                    pOItemPkgAppDetail.SampleSizeID = cursor.getString(cursor.getColumnIndex("SampleSizeID"));
                    pOItemPkgAppDetail.SampleSizeValue = cursor.getString(cursor.getColumnIndex("SampleSizeValue"));
                    pOItemPkgAppDetail.InspectionResultID = cursor.getString(cursor.getColumnIndex("InspectionResultID"));
                    pOItemPkgAppDetail.recUser = cursor.getString(cursor.getColumnIndex("recUser"));

                    pKGAppListItem.add(pOItemPkgAppDetail);
                }
            }
            cursor.close();
            database.close();
            FslLog.d(TAG, " cout of founded pKGAppListItem " + pKGAppListItem.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pKGAppListItem;
    }

    public static String[] getToInspect(Context mContext, String pRowInspection, int AvailableQty) {
        String[] toInspArray = null;
        try {
            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getReadableDatabase();

            /*String query = "select  Ins.SampleCode,GenSample.MainDescr,GenSample.numVal1  from InspLvlDtl Ins left join GenMst GenSample on Ins.SampleCode=GenSample.pGenRowID " +
                    "where InspHdrID='" + pRowInspection + "'  and  BatchSize <=" + AvailableQty + " order by BatchSize desc Limit 1";
*/
            String query = "Select tbl.SampleCode as SampleSizeID, tbl.BatchSize, gd.MainDescr as SampleSizeDescr, gd.numVal1 As SampleSizeQty" +
                    "   From (   Select  SampleCode, BatchSize  " +
                    "  From (Select  SampleCode, BatchSize From InspLvlDtl  " +
                    "  Where InspHdrID = '" + pRowInspection + "' And trim(SignDescr) = '<='    And BatchSize >= " + AvailableQty + "  " +
                    "  Order by BatchSize Asc limit 1) v   Union      " +
                    "  Select  SampleCode, BatchSize From InspLvlDtl " +
                    "  Where InspHdrID = '" + pRowInspection + "' And trim(SignDescr) = '>'    Order" +
                    " by BatchSize Asc limit 1) tbl   Inner Join GenMst gd on gd.GenID = '320'" +
                    " and gd.pGenRowID = tbl.SampleCode Order by BatchSize Asc limit 1";
            FslLog.d(TAG, "get update query for  get getToInspect dtl  " + query);
            Cursor cursor = database.rawQuery(query, null);

            if (cursor.getCount() > 0) {
                toInspArray = new String[3];
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToNext();
                    toInspArray[0] = cursor.getString(cursor.getColumnIndex("SampleSizeID"));
                    toInspArray[1] = cursor.getString(cursor.getColumnIndex("SampleSizeDescr"));
                    toInspArray[2] = cursor.getString(cursor.getColumnIndex("SampleSizeQty"));

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (toInspArray != null && toInspArray.length > 0) {
            FslLog.d(TAG, " Samplecode " + toInspArray[0] + " MainDescr " + toInspArray[1] + " numVal " + toInspArray[2]);

        } else {
            FslLog.d(TAG, " founded sample code and size  is NULL");

        }
        return toInspArray;
    }

    public static List<SampleModal> getSampleSizeList(Context mContext) {
        List<SampleModal> toInspArray = new ArrayList<>();
        try {
            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getReadableDatabase();

            String query = "select DISTINCT Ins.SampleCode, GenSample.MainDescr, GenSample.numVal1  from InspLvlDtl Ins left join GenMst GenSample on Ins.SampleCode=GenSample.pGenRowID";
            /*"where InspHdrID='" + pRowInspection + "'  and  BatchSize <=" + AvailableQty + " order by BatchSize desc Limit 1";*/

            FslLog.d(TAG, "get query for  get sample size   " + query);
            Cursor cursor = database.rawQuery(query, null);

            if (cursor.getCount() > 0) {

                for (int i = 0; i < cursor.getCount(); i++) {
                    SampleModal sampleModal = new SampleModal();
                    cursor.moveToNext();
                    sampleModal.SampleCode = cursor.getString(cursor.getColumnIndex("SampleCode"));
                    sampleModal.MainDescr = cursor.getString(cursor.getColumnIndex("MainDescr"));
                    sampleModal.SampleVal = cursor.getString(cursor.getColumnIndex("numVal1"));
                    toInspArray.add(sampleModal);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        FslLog.d(TAG, " founded data count is  " + toInspArray.size());


        return toInspArray;
    }


    public static String[] getDefectAccepted(Context mContext, String pRowIdOfQualityLevel, String SampleCode) {

        try {
            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getReadableDatabase();

            String query = "select *  from qualityLevelDtl where QlHdrID='" + pRowIdOfQualityLevel + "'  and SampleCode='" + SampleCode + "'";

            FslLog.d(TAG, "get update query for  get getToInspect dtl  " + query);
            Cursor cursor = database.rawQuery(query, null);
            String[] toInspArray = null;
            if (cursor.getCount() > 0) {
                toInspArray = new String[2];
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToNext();
                    toInspArray[0] = cursor.getString(cursor.getColumnIndex("SampleCode"));
                    toInspArray[1] = cursor.getString(cursor.getColumnIndex("Accepted"));
                    //                toInspArray[2] = cursor.getString(cursor.getColumnIndex("numVal1"));

                }
                FslLog.d(TAG, " Samplecode " + toInspArray[0] + " Accepted " + toInspArray[1]);
            } else {
                FslLog.d(TAG, " No result found for Sample code and accepted defect  ");
            }

            return toInspArray;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void updateItemForDisable(Context mContext, POItemDtl poItemDtl) {
        try {
            DBHelper mydb = new DBHelper(mContext);
            SQLiteDatabase db = mydb.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("recEnable", 0);

            int rows = db.update("QRPOItemDtl", contentValues, "pRowID"
                    + " = '" + poItemDtl.QrItemID + "'", null);
            if (rows == 0) {
                FslLog.d(TAG, " QRPOItemDtl table NOT UPDATED................");
            } else {
                FslLog.d(TAG, " QRPOItemDtl type update result................" + rows);
            }
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
            FslLog.d(TAG, "Exception to inserting QRPOItemDtl");
        }
    }

    public static boolean updateItemForQty(Context mContext, POItemDtl poItemDtl) {
        try {
            DBHelper mydb = new DBHelper(mContext);
            SQLiteDatabase db = mydb.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("AvailableQty", poItemDtl.AvailableQty);
            contentValues.put("AcceptedQty", poItemDtl.AcceptedQty);
            contentValues.put("ShortStockQty", poItemDtl.ShortStockQty);

            FslLog.d(TAG, " updateItemForQty pRowID="+poItemDtl.pRowID);
            FslLog.d(TAG, " updateItemForQty AvailableQty="+poItemDtl.AvailableQty);
            FslLog.d(TAG, " updateItemForQty AcceptedQty="+poItemDtl.AcceptedQty);
            FslLog.d(TAG, " updateItemForQty ShortStockQty="+poItemDtl.ShortStockQty);

            int rows = db.update("QRPOItemdtl", contentValues, "pRowID"
                    + " = '" + poItemDtl.pRowID + "'" +
                    "and  QRHdrID" + " = '" + poItemDtl.QRHdrID + "'", null);
            if (rows == 0) {
                FslLog.d(TAG, " QRPOItemDtl table NOT UPDATED................");
                return false;
            } else {
                FslLog.d(TAG, " QRPOItemDtl type update result................" + rows);
            }
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
            FslLog.d(TAG, "Exception to inserting QRPOItemDtl");
            return false;
        }
        return true;
    }

    public static boolean updateItemForQty1(Context mContext, POItemDtl poItemDtl) {
        try {
            DBHelper mydb = new DBHelper(mContext);
            SQLiteDatabase db = mydb.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("AvailableQty", poItemDtl.AvailableQty);
            contentValues.put("AcceptedQty", poItemDtl.AcceptedQty);
            contentValues.put("ShortStockQty", poItemDtl.ShortStockQty);

            FslLog.d(TAG, " updateItemForQty1 pRowID="+poItemDtl.pRowID);
            FslLog.d(TAG, " updateItemForQty1 AvailableQty="+poItemDtl.AvailableQty);
            FslLog.d(TAG, " updateItemForQty1 AcceptedQty="+poItemDtl.AcceptedQty);
            FslLog.d(TAG, " updateItemForQty1 ShortStockQty="+poItemDtl.ShortStockQty);

            int rows = db.update("QRPOItemdtl", contentValues, "pRowID"
                    + " = '" + poItemDtl.pRowID + "'", null);
            if (rows == 0) {
                FslLog.d(TAG, " QRPOItemDtl table NOT UPDATED................");
                return false;
            } else {
                FslLog.d(TAG, " QRPOItemDtl type update result................" + rows);
            }
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
            FslLog.d(TAG, "Exception to inserting QRPOItemDtl");
            return false;
        }
        return true;
    }

}
