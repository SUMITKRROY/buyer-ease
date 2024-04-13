package com.dashboard;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.constant.FEnumerations;
import com.database.DBHelper;
import com.util.FslLog;

import java.util.ArrayList;

/**
 * Created by ADMIN on 12/27/2017.
 */

public class SyncDataHandler {

    static String TAG = "SyncDataHandler";

    public static ArrayList<String> getTablesToSyncList(Context mContext) {


        ArrayList<String> tableArrayList = new ArrayList<String>();
        tableArrayList.add(FEnumerations.E_SYNC_HEADER_TABLE);
        tableArrayList.add(FEnumerations.E_SYNC_IMAGES_TABLE);
        tableArrayList.add(FEnumerations.E_SYNC_WORKMANSHIP_TABLE);
        tableArrayList.add(FEnumerations.E_SYNC_ITEM_MEASUREMENT_TABLE);
        tableArrayList.add(FEnumerations.E_SYNC_FINDING_TABLE);
        tableArrayList.add(FEnumerations.E_SYNC_QUALITY_PARAMETER_TABLE);
        tableArrayList.add(FEnumerations.E_SYNC_FITNESS_CHECK_TABLE);
        tableArrayList.add(FEnumerations.E_SYNC_PRODUCTION_STATUS_TABLE);
        tableArrayList.add(FEnumerations.E_SYNC_INTIMATION_TABLE);
        tableArrayList.add(FEnumerations.E_SYNC_ENCLOSURE_TABLE);
        tableArrayList.add(FEnumerations.E_SYNC_DIGITAL_UPLOAD_TABLE);
        tableArrayList.add(FEnumerations.E_SYNC_PKG_APPEARANCE);
        tableArrayList.add(FEnumerations.E_SYNC_ON_SITE);
        tableArrayList.add(FEnumerations.E_SYNC_SAMPLE_COLLECTED);


       /* try {
            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getReadableDatabase();

            String query = "SELECT name FROM sqlite_master WHERE type='table' AND name!='android_metadata' order by name";
            FslLog.d(TAG, " query for  table list  " + query);

            Cursor cursor = database.rawQuery(query, null);


            if (cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    while (!cursor.isAfterLast()) {
                        tableArrayList.add(cursor.getString(0));
                        cursor.moveToNext();
                    }
                }
            }
            cursor.close();
            database.close();
            FslLog.d(TAG, " cout of founded table " + tableArrayList.size());
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        return tableArrayList;
    }

    public static ArrayList<String> getTablesToSyncListWithoutPkgApp(Context mContext) {

        ArrayList<String> tableArrayList = new ArrayList<String>();
        tableArrayList.add(FEnumerations.E_SYNC_HEADER_TABLE);
        tableArrayList.add(FEnumerations.E_SYNC_IMAGES_TABLE);
        tableArrayList.add(FEnumerations.E_SYNC_WORKMANSHIP_TABLE);
        tableArrayList.add(FEnumerations.E_SYNC_ITEM_MEASUREMENT_TABLE);
        tableArrayList.add(FEnumerations.E_SYNC_FINDING_TABLE);
        tableArrayList.add(FEnumerations.E_SYNC_QUALITY_PARAMETER_TABLE);
        tableArrayList.add(FEnumerations.E_SYNC_FITNESS_CHECK_TABLE);
        tableArrayList.add(FEnumerations.E_SYNC_PRODUCTION_STATUS_TABLE);
        tableArrayList.add(FEnumerations.E_SYNC_INTIMATION_TABLE);
        tableArrayList.add(FEnumerations.E_SYNC_ENCLOSURE_TABLE);
        tableArrayList.add(FEnumerations.E_SYNC_DIGITAL_UPLOAD_TABLE);
//        tableArrayList.add(FEnumerations.E_SYNC_PKG_APPEARANCE);
        tableArrayList.add(FEnumerations.E_SYNC_ON_SITE);
        tableArrayList.add(FEnumerations.E_SYNC_SAMPLE_COLLECTED);
        return tableArrayList;
    }

    public static ArrayList<String> getTablesToSyncStyleList(Context mContext) {


        ArrayList<String> tableArrayList = new ArrayList<String>();
        tableArrayList.add(FEnumerations.E_SYNC_STYLE);
        tableArrayList.add(FEnumerations.E_SYNC_IMAGES_TABLE);
        tableArrayList.add(FEnumerations.E_SYNC_FINALIZE_TABLE);

        return tableArrayList;
    }

    public static ArrayList<String> getTablesToDelete(Context mContext) {


        ArrayList<String> tableArrayList = new ArrayList<String>();



        try {
            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getReadableDatabase();

            String query = "SELECT name FROM sqlite_master WHERE type='table' AND name!='android_metadata' order by name";
            FslLog.d(TAG, " query for  table list  " + query);

            Cursor cursor = database.rawQuery(query, null);


            if (cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    while (!cursor.isAfterLast()) {
                        tableArrayList.add(cursor.getString(0));
                        cursor.moveToNext();
                    }
                }
            }
            cursor.close();
            database.close();
            FslLog.d(TAG, " cout of founded table " + tableArrayList.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tableArrayList;
    }

    public static void deleteRecordFromAllTAble(Context mContext, String tableName) {
        try {
            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getWritableDatabase();
//            if (!tableName.equals("UserMaster")) {
                database.execSQL("delete from " + tableName);
                FslLog.d(TAG, " delete all record from " + tableName);
//            } else {
//                FslLog.d(TAG, " Not deleted  " + tableName);
//            }

            database.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
