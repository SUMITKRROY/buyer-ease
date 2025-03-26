package com.sizeQty;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.database.DBHelper;
import com.util.FslLog;
import java.util.ArrayList;
import java.util.List;

public class SizeQtyModelHandler {

    static String TAG = "SizeQtyModelHandler";
    public static List<SizeQtyModel> getSizeQtyList(Context mContext, String pRowID) {

        List<SizeQtyModel> sizeQtyModelList = new ArrayList<SizeQtyModel>();
        try {
            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getReadableDatabase();


            String query = "SELECT * FROM " + "SizeQuantity"
                    + " WHERE " + "QRPOItemDtlID" + "='" + pRowID + "' order by SizeID ASC"; //+ order by SizeGroupDescr ASC

            /* String query = "SELECT * FROM " + "SizeQuantity"
                    + " WHERE " + "QRPOItemHdrID" + "='" + pRowID + "' order by SizeID ASC"; //+ order by SizeGroupDescr ASC*/
            FslLog.d(TAG, "query for  get closed SizeQty list  " + query);
            Cursor cursor = database.rawQuery(query, null);
            if (cursor.getCount() > 0) {
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToNext();
                    sizeQtyModelList.add(getData(cursor));
                }
            }
            cursor.close();
            database.close();
//            }
            FslLog.d(TAG, " count of founded list of SizeQty " + sizeQtyModelList.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sizeQtyModelList;
    }

    private static SizeQtyModel getData(Cursor cursor) {
        SizeQtyModel sizeQtyModel = new SizeQtyModel();

        sizeQtyModel.AcceptedQty=cursor.getInt(cursor.getColumnIndex("AcceptedQty"));
        sizeQtyModel.AvailableQty=cursor.getInt(cursor.getColumnIndex("AvailableQty"));
        sizeQtyModel.EarlierInspected=cursor.getInt(cursor.getColumnIndex("EarlierInspected"));
        sizeQtyModel.OrderQty=cursor.getInt(cursor.getColumnIndex("OrderQty"));
        sizeQtyModel.POID=cursor.getString(cursor.getColumnIndex("POID"));
        sizeQtyModel.QRPOItemDtlID=cursor.getString(cursor.getColumnIndex("QRPOItemDtlID"));
        sizeQtyModel.QRPOItemHdrID=cursor.getString(cursor.getColumnIndex("QRPOItemHdrID"));
        sizeQtyModel.SizeGroupDescr=cursor.getString(cursor.getColumnIndex("SizeGroupDescr"));
        sizeQtyModel.SizeID=cursor.getString(cursor.getColumnIndex("SizeID"));

        return sizeQtyModel;
    }

    public static void updateSizeQty(Context mContext, List<SizeQtyModel> sizeQtylList) {
        if (mContext != null && sizeQtylList != null && sizeQtylList.size() > 0) {
            for (int i = 0; i < sizeQtylList.size(); i++) {
                insertSizeQty(mContext, sizeQtylList.get(i));
            }
        }

    }
    public static void insertSizeQty(Context mContext, SizeQtyModel sizeQtyModel) {

        try {
            DBHelper mydb = new DBHelper(mContext);
            SQLiteDatabase db = mydb.getWritableDatabase();
            ContentValues contentValues = new ContentValues();

            contentValues.put("AcceptedQty", sizeQtyModel.AcceptedQty);
            contentValues.put("AvailableQty", sizeQtyModel.AvailableQty);
            contentValues.put("EarlierInspected", sizeQtyModel.EarlierInspected);
            contentValues.put("OrderQty", sizeQtyModel.OrderQty);
            contentValues.put("POID", sizeQtyModel.POID);
            contentValues.put("QRPOItemDtlID", sizeQtyModel.QRPOItemDtlID);
            contentValues.put("QRPOItemHdrID", sizeQtyModel.QRPOItemHdrID);
            contentValues.put("SizeGroupDescr", sizeQtyModel.SizeGroupDescr);
            contentValues.put("SizeID", sizeQtyModel.SizeID);

            int rows = db.update("SizeQuantity", contentValues, "QRPOItemHdrID"
                    + " = '" + sizeQtyModel.QRPOItemHdrID + "'"
                    + "and  SizeID" + " = '" + sizeQtyModel.SizeID + "'", null);

            if(rows ==0){
                Log.e("SizeQuantity","SizeQuantity Not updated"+rows);
            }else if(rows ==1){
                Log.e("SizeQuantity","SizeQuantity updated"+rows);
            }


            db.close();
        } catch (Exception e) {
            e.printStackTrace();
            FslLog.d(TAG, "Exception to inserting general");
        }
    }
}
