package com.inspection;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.buyereasefsl.ItemInspectionDetailHandler;
import com.constant.AppConfig;
import com.database.DBHelper;
import com.podetail.POItemDtlHandler;
import com.sync.GetDataHandler;
import com.util.FslLog;
import com.util.GenUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ADMIN on 12/18/2017.
 */

public class InspectionListHandler {

    static String TAG = "InspectionListHandler";

    public static boolean updatePOItemhdr(Context mContext, InspectionModal inspectionModal) {

        try {
            DBHelper mydb = new DBHelper(mContext);
            SQLiteDatabase db = mydb.getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            contentValues.put("VendorContact", inspectionModal.VendorContact);
            contentValues.put("ArrivalTime", inspectionModal.ArrivalTime);
            contentValues.put("InspStartTime", inspectionModal.InspStartTime);
            contentValues.put("CompleteTime", inspectionModal.CompleteTime);

            contentValues.put("InspectionLevel", inspectionModal.InspectionLevel);
            contentValues.put("QLMajor", inspectionModal.QLMajor);
            contentValues.put("QLMinor", inspectionModal.QLMinor);
            contentValues.put("QLMajorDescr", inspectionModal.QLMajorDescr);

            contentValues.put("QLMinorDescr", inspectionModal.QLMinorDescr);
            contentValues.put("Status", inspectionModal.Status);
            contentValues.put("recDt", AppConfig.getCurrntDate());

            contentValues.put("AcceptedDt", AppConfig.getCurrntDate());//inspectionModal.AcceptedDt
            contentValues.put("Comments", inspectionModal.Comments);
            contentValues.put("InspectionDt", inspectionModal.InspectionDt);
            contentValues.put("ProductionStatusRemark", inspectionModal.ProductionStatusRemark);

//            contentValues.put("PONO", inspectionModal.PONO);

            int rows = db.update("QRFeedbackhdr", contentValues, "pRowID"
                    + " = '" + inspectionModal.pRowID + "'", null);
            if (rows == 0) {
                FslLog.d(TAG, " QRFeedbackhdr table NOT UPDATE................");
            } else {
                FslLog.d(TAG, " QRFeedbackhdr type update result................" + rows);
            }
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
            FslLog.d(TAG, "Exception to inserting QRFeedbackhdr");
        }


        return true;
    }

    public static List<InspectionModal> getInspectionList(Context mContext,String searchStr) {


        ArrayList<InspectionModal> inspectionArrayList = new ArrayList<InspectionModal>();
        try {

            if (!GenUtils.columnExistsInTable(mContext, "QRFeedbackhdr", "IsSynced")) {
                FslLog.e(TAG, " not column found " + "IsSynced" + " so alter execute as INTEGER");
                GetDataHandler.handleToAlterAsIn(mContext, "QRFeedbackhdr", "IsSynced");

            }
            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getReadableDatabase();
            String query = "SELECT * FROM " + " QRFeedbackhdr" +
                    " Where pRowID not in (select prowid from QRFeedbackhdr where status in (20,40,45,50,60,65)" +
                    "  and Datetime(recDt) <= DateTime(IFNULL(Last_Sync_Dt,'1900-01-01 00:00:00'))) and IsSynced=0 ";

            if(searchStr!=null && !searchStr.isEmpty())
            {
                query +=" and ( pRowID like '%"+searchStr+"%' ";
                query +=" or  pRowID in ( select QRHdrID from QRPOItemDtl where PONO like '%"+searchStr+"%') ";
                query +=" or  pRowID in ( select QRHdrID from QRPOItemDtl where CustomerItemRef like '%"+searchStr+"%')) ";
            }
            query +=" order by InspectionDt desc";
            FslLog.d(TAG, "get update query for  get inspection list  " + query);
            Cursor cursor = database.rawQuery(query, null);


            InspectionModal inspectionModal;
            if (cursor.getCount() > 0) {
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToNext();
                    inspectionModal = new InspectionModal();
                    inspectionModal.pRowID = cursor.getString(cursor.getColumnIndex("pRowID"));
                    inspectionModal.Customer = cursor.getString(cursor.getColumnIndex("Customer"));
                    inspectionModal.Vendor = cursor.getString(cursor.getColumnIndex("Vendor"));
                    inspectionModal.VendorContact = cursor.getString(cursor.getColumnIndex("VendorContact"));
                    inspectionModal.VendorAddress = cursor.getString(cursor.getColumnIndex("FactoryAddress"));
                    inspectionModal.InspectionDt = cursor.getString(cursor.getColumnIndex("InspectionDt"));
                    inspectionModal.Activity = cursor.getString(cursor.getColumnIndex("Activity"));
                    inspectionModal.ActivityID = cursor.getString(cursor.getColumnIndex("ActivityID"));

                    inspectionModal.QR = cursor.getString(cursor.getColumnIndex("QR"));
                    inspectionModal.Inspector = cursor.getString(cursor.getColumnIndex("Inspector"));

                    inspectionModal.ArrivalTime = cursor.getString(cursor.getColumnIndex("ArrivalTime"));
                    inspectionModal.InspStartTime = cursor.getString(cursor.getColumnIndex("InspStartTime"));

                    inspectionModal.CompleteTime = cursor.getString(cursor.getColumnIndex("CompleteTime"));
                    inspectionModal.InspectionLevel = cursor.getString(cursor.getColumnIndex("InspectionLevel"));
                    inspectionModal.QLMajor = cursor.getString(cursor.getColumnIndex("QLMajor"));
                    inspectionModal.QLMinor = cursor.getString(cursor.getColumnIndex("QLMinor"));
                    inspectionModal.QLMajorDescr = cursor.getString(cursor.getColumnIndex("QLMajorDescr"));

                    inspectionModal.QLMinorDescr = cursor.getString(cursor.getColumnIndex("QLMinorDescr"));
                    inspectionModal.Status = cursor.getString(cursor.getColumnIndex("Status"));
                    inspectionModal.recDt = cursor.getString(cursor.getColumnIndex("recDt"));
                    inspectionModal.AcceptedDt = cursor.getString(cursor.getColumnIndex("AcceptedDt"));
                    inspectionModal.Comments = cursor.getString(cursor.getColumnIndex("Comments"));
                    inspectionModal.InspectionDt = cursor.getString(cursor.getColumnIndex("InspectionDt"));
                    inspectionModal.Factory = cursor.getString(cursor.getColumnIndex("Factory"));
                    inspectionModal.ProductionStatusRemark = cursor.getString(cursor.getColumnIndex("ProductionStatusRemark"));
                    inspectionModal.AQLFormula = cursor.getInt(cursor.getColumnIndex("AQLFormula"));
                    List<String> ItemIdlists = POItemDtlHandler.getItemIdList(mContext,inspectionModal.pRowID);
                    Log.e("InspectionListHandler","itemlist"+ItemIdlists);
                    if (ItemIdlists != null && ItemIdlists.size() > 0) {
                        String str = "";
                        for (int j = 0; j < ItemIdlists.size(); j++) {
                            if (ItemIdlists.size() == 1) {
                                str = ItemIdlists.get(j);
                            } else {
                                if (j == 0) {
                                    str = ItemIdlists.get(j);
                                } else {
                                    str = str + ", " + ItemIdlists.get(j);
                                }
                            }
                        }
                        inspectionModal.ItemListId = str;
                    }

                    List<String> list = POItemDtlHandler.getPOList(mContext, inspectionModal.pRowID);
                    if (list != null && list.size() > 0) {
                        String str = "";
                        for (int j = 0; j < list.size(); j++) {
                            if (list.size() == 1) {
                                str = list.get(j);
                            } else {
                                if (j == 0) {
                                    str = list.get(j);
                                } else {
                                    str = str + ", " + list.get(j);
                                }
                            }
                        }
                        inspectionModal.POListed = str;
                    }

                    List<Integer> listImportant = ItemInspectionDetailHandler.IsImportant(mContext, inspectionModal.pRowID, null);
                    if (listImportant != null && listImportant.size() > 0) {
                        boolean isFounded = false;
                        for (int p = 0; p < listImportant.size(); p++) {
                            if (listImportant.get(p) == 1) {
                                isFounded = true;
                                break;
                            }
                        }
                        if (isFounded) {
                            inspectionModal.IsImportant = 1;
                        }
                    }

                    inspectionArrayList.add(inspectionModal);
                }
            }
            cursor.close();
            database.close();
            FslLog.d(TAG, " cout of founded QRFeedbackhdr " + inspectionArrayList.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inspectionArrayList;
    }

    public static List<String> getItemList(Context mContext, String pRowID){
        return POItemDtlHandler.getItemIdList(mContext,pRowID);
    }
    public static List<InspectionModal> getSyncedInspectionList(Context mContext,String searchStr) {

        ArrayList<InspectionModal> inspectionArrayList = new ArrayList<InspectionModal>();
        try {

            if (!GenUtils.columnExistsInTable(mContext, "QRFeedbackhdr", "IsSynced")) {
                FslLog.e(TAG, " not column found " + "IsSynced" + " so alter execute as INTEGER");
                GetDataHandler.handleToAlterAsIn(mContext, "QRFeedbackhdr", "IsSynced");

            }
            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getReadableDatabase();
            String query = "SELECT * FROM " + " QRFeedbackhdr" +
                    " Where  /*Datetime(recDt) <= DateTime(IFNULL(Last_Sync_Dt,'1900-01-01 00:00:00')) and*/ IsSynced=1 ";
           if(searchStr!=null && !searchStr.isEmpty())
           {
//               query +=" and pRowID like '%"+searchStr+"%' ";
               query +=" and ( pRowID like '%"+searchStr+"%' ";
               query +=" or  pRowID in ( select QRHdrID from QRPOItemDtl where PONO like '%"+searchStr+"%') ";
               query +=" or  pRowID in ( select QRHdrID from QRPOItemDtl where CustomerItemRef like '%"+searchStr+"%')) ";
           }
           query +=" order by InspectionDt desc";
            FslLog.d(TAG, "get update query for  get inspection list  " + query);//pRowID not in (select prowid from QRFeedbackhdr where status in (20,40,45,50,60,65)
            Cursor cursor = database.rawQuery(query, null);


            InspectionModal inspectionModal;
            if (cursor.getCount() > 0) {
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToNext();
                    inspectionModal = new InspectionModal();
                    inspectionModal.pRowID = cursor.getString(cursor.getColumnIndex("pRowID"));
                    inspectionModal.Customer = cursor.getString(cursor.getColumnIndex("Customer"));
                    inspectionModal.Vendor = cursor.getString(cursor.getColumnIndex("Vendor"));
                    inspectionModal.VendorContact = cursor.getString(cursor.getColumnIndex("VendorContact"));
                    inspectionModal.VendorAddress = cursor.getString(cursor.getColumnIndex("FactoryAddress"));
                    inspectionModal.InspectionDt = cursor.getString(cursor.getColumnIndex("InspectionDt"));
                    inspectionModal.Activity = cursor.getString(cursor.getColumnIndex("Activity"));
                    inspectionModal.ActivityID = cursor.getString(cursor.getColumnIndex("ActivityID"));

                    inspectionModal.QR = cursor.getString(cursor.getColumnIndex("QR"));
                    inspectionModal.Inspector = cursor.getString(cursor.getColumnIndex("Inspector"));

                    inspectionModal.ArrivalTime = cursor.getString(cursor.getColumnIndex("ArrivalTime"));
                    inspectionModal.InspStartTime = cursor.getString(cursor.getColumnIndex("InspStartTime"));

                    inspectionModal.CompleteTime = cursor.getString(cursor.getColumnIndex("CompleteTime"));
                    inspectionModal.InspectionLevel = cursor.getString(cursor.getColumnIndex("InspectionLevel"));
                    inspectionModal.QLMajor = cursor.getString(cursor.getColumnIndex("QLMajor"));
                    inspectionModal.QLMinor = cursor.getString(cursor.getColumnIndex("QLMinor"));
                    inspectionModal.QLMajorDescr = cursor.getString(cursor.getColumnIndex("QLMajorDescr"));

                    inspectionModal.QLMinorDescr = cursor.getString(cursor.getColumnIndex("QLMinorDescr"));
                    inspectionModal.Status = cursor.getString(cursor.getColumnIndex("Status"));
                    inspectionModal.recDt = cursor.getString(cursor.getColumnIndex("recDt"));
                    inspectionModal.AcceptedDt = cursor.getString(cursor.getColumnIndex("AcceptedDt"));
                    inspectionModal.Comments = cursor.getString(cursor.getColumnIndex("Comments"));
                    inspectionModal.InspectionDt = cursor.getString(cursor.getColumnIndex("InspectionDt"));
                    inspectionModal.Factory = cursor.getString(cursor.getColumnIndex("Factory"));
                    inspectionModal.ProductionStatusRemark = cursor.getString(cursor.getColumnIndex("ProductionStatusRemark"));
                    inspectionModal.AQLFormula = cursor.getInt(cursor.getColumnIndex("AQLFormula"));
                    List<String> ItemIdlist = POItemDtlHandler.getItemIdList(mContext,inspectionModal.pRowID);
                    Log.e("InspectionListHandler","itemlistid close"+ItemIdlist);
//                    List<String> list = POItemDtlHandler.getPOList(mContext, inspectionModal.pRowID);
                    List<String> ItemIdClosedlists = POItemDtlHandler.getPOList(mContext, inspectionModal.pRowID);
                    Log.e("InspectionListHandler","ItemIdClosedlists"+ItemIdClosedlists);

                    if (ItemIdlist != null && ItemIdlist.size() > 0) {
                        String str = "";
                        for (int j = 0; j < ItemIdlist.size(); j++) {
                            if (ItemIdlist.size() == 1) {
                                str = ItemIdlist.get(j);
                            } else {
                                if (j == 0) {
                                    str = ItemIdlist.get(j);
                                } else {
                                    str = str + ", " + ItemIdlist.get(j);
                                }
                            }
                        }
                        inspectionModal.ItemListId = str;
                    }

                    if (ItemIdClosedlists != null && ItemIdClosedlists.size() > 0) {
                        String str = "";
                        for (int j = 0; j < ItemIdClosedlists.size(); j++) {
                            if (ItemIdClosedlists.size() == 1) {
                                str = ItemIdClosedlists.get(j);
                            } else {
                                if (j == 0) {
                                    str = ItemIdClosedlists.get(j);
                                } else {
                                    str = str + ", " + ItemIdClosedlists.get(j);
                                }
                            }
                        }
                        inspectionModal.POListed = str;
                    }

                    inspectionArrayList.add(inspectionModal);
                }
            }
            cursor.close();
            database.close();
            FslLog.d(TAG, " cout of synced founded QRFeedbackhdr " + inspectionArrayList.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inspectionArrayList;
    }


    public static void deleteInspectionRecordFromAllTAble(Context mContext, String pRowID) {
        try {
            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getWritableDatabase();

            String query1 = "Update QRPOItemHdr Set DefaultImageRowID = NULL Where QRHdrID = '" + pRowID + "';";
            String query2 = " Delete From QRFindings Where QRHdrID = '" + pRowID + "';";
            String query3 = " Delete From QRQualiltyParameterFields Where QRHdrID = '" + pRowID + "';";

            String query4 = " Delete From QRPOItemFitnessCheck Where QRHdrID = '" + pRowID + "';";
            String query5 = " Delete From QRProductionStatus Where QRHdrID = '" + pRowID + "';";
            String query6 = " Delete From QREnclosure Where ContextID = '" + pRowID + "';";

            String query7 = " Delete From QRPOIntimationDetails Where QRHdrID = '" + pRowID + "';";
            String query8 = " Delete From QRPOItemDtl_ItemMeasurement Where QRHdrID = '" + pRowID + "';";
            String query9 = " Delete From QRInspectionHistory Where RefQRHdrID = '" + pRowID + "';";
            String query10 = " Delete From QRAuditBatchDetails Where QRHdrID = '" + pRowID + "';";
            String query11 = " Delete From Enclosures Where QRHdrID = '" + pRowID + "';";
            String query12 = " Delete From QRPOItemDtl_Image Where QRHdrID = '" + pRowID + "';";
            String query13 = " Delete From QRPOItemdtl Where QRHdrID = '" + pRowID + "';";
            String query14 = " Delete From QRPOItemHdr Where QRHdrID = '" + pRowID + "';";
            String query15 = " Delete From QRFeedbackhdr Where pRowID='" + pRowID + "';";

            FslLog.d(TAG, " update record from query1 " + query1);
            FslLog.d(TAG, " delete all record from " + query2);
            FslLog.d(TAG, " delete all record from " + query3);
            FslLog.d(TAG, " delete all record from " + query4);
            FslLog.d(TAG, " delete all record from " + query5);
            FslLog.d(TAG, " delete all record from " + query6);
            FslLog.d(TAG, " delete all record from " + query7);
            FslLog.d(TAG, " delete all record from " + query8);
            FslLog.d(TAG, " delete all record from " + query9);
            FslLog.d(TAG, " delete all record from " + query10);
            FslLog.d(TAG, " delete all record from " + query11);
            FslLog.d(TAG, " delete all record from " + query12);
            FslLog.d(TAG, " delete all record from " + query13);
            FslLog.d(TAG, " delete all record from " + query14);
            FslLog.d(TAG, " delete all record from " + query15);


            database.execSQL(query1);
            database.execSQL(query2);
            database.execSQL(query3);
            database.execSQL(query4);
            database.execSQL(query5);
            database.execSQL(query6);
            database.execSQL(query7);
            database.execSQL(query8);
            database.execSQL(query9);
            database.execSQL(query10);
            database.execSQL(query11);
            database.execSQL(query11);
            database.execSQL(query12);
            database.execSQL(query13);
            database.execSQL(query14);
            database.execSQL(query15);


            database.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int getPackagingAppearanceImageCount(Context mContext, String pRowID) {
        int count = 0;
        try {
            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getReadableDatabase();
            
            // Query to get image count for packaging appearance
            String query = "SELECT COUNT(*) FROM QREnclosure WHERE ContextID = ? " +
                            "AND ContextType = 'PackagingAppearance'";
            
            FslLog.d(TAG, "Image count query: " + query + " with pRowID: " + pRowID);
            
            Cursor cursor = database.rawQuery(query, new String[]{pRowID});
            
            if (cursor != null && cursor.moveToFirst()) {
                count = cursor.getInt(0);
                FslLog.d(TAG, "Retrieved image count: " + count);
            } else {
                FslLog.d(TAG, "No results found for image count query");
            }
            
            if (cursor != null) {
                cursor.close();
            }
            database.close();
        } catch (Exception e) {
            e.printStackTrace();
            FslLog.e(TAG, "Error getting packaging appearance image count: " + e.getMessage());
        }
        
        return count;
    }

    // For retrieving the image paths
    public static List<String> getPackagingAppearanceImages(Context mContext, String pRowID) {
        List<String> imagePaths = new ArrayList<>();
        try {
            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getReadableDatabase();
            
            // Query to get all image paths for packaging appearance
            String query = "SELECT FilePath FROM QREnclosure WHERE ContextID = ? " +
                           "AND ContextType = 'PackagingAppearance'";
            
            Cursor cursor = database.rawQuery(query, new String[]{pRowID});
            
            if (cursor != null && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    String path = cursor.getString(cursor.getColumnIndex("FilePath"));
                    if (path != null && !path.isEmpty()) {
                        imagePaths.add(path);
                    }
                }
            }
            
            if (cursor != null) {
                cursor.close();
            }
            database.close();
        } catch (Exception e) {
            e.printStackTrace();
            FslLog.e(TAG, "Error getting packaging appearance images: " + e.getMessage());
        }
        
        return imagePaths;
    }
}
