package com.hologram;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;

import com.buyereasefsl.ItemInspectionDetailHandler;
import com.constant.AppConfig;
import com.constant.FClientConfig;
import com.constant.FEnumerations;
import com.database.DBHelper;
import com.login.LogInHandler;
import com.login.UserMaster;
import com.sync.ImageModal;
import com.util.FslLog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StyleHandler {
    static String TAG = "StyleHandler";

    public static void checkAndCreateTable(Context mContext) {
        DBHelper mydb = new DBHelper(mContext);
        SQLiteDatabase mDb = mydb.getWritableDatabase();
        DBHelper.reCreateTable(mDb);
    }

    public static List<HologramModal> getStyleList(Context mContext, String searchStr) {
        ArrayList<HologramModal> hologramModalArrayList = new ArrayList<HologramModal>();
        try {
            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getReadableDatabase();
            String query = "SELECT * FROM " + DBHelper.TB_STYLE_DETAIL_TABLE + " ORDER BY " + DBHelper.CL_REC_DATE + " DESC";
                   /* + " where " + DBHelper.CL_CUSTOMER_NAME + " LIKE '%" + searchStr + "%' OR "
                    + DBHelper.CL_DEPARTMENT_NAME + " LIKE '%" + searchStr + "%' OR "
                    + DBHelper.CL_VENDOR + " LIKE '%" + searchStr + "%'"*/
            ;
            FslLog.d(TAG, "get   query for  get style list  " + query);
            Cursor cursor = database.rawQuery(query, null);
            HologramModal hologramModal;
            if (cursor.getCount() > 0) {
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToNext();
                    hologramModal = new HologramModal();
                    hologramModal.ItemID = cursor.getString(cursor.getColumnIndex(DBHelper.CL_ITEM_ID));
                    hologramModal.style_ref_no = cursor.getString(cursor.getColumnIndex(DBHelper.CL_STYLE_REFERENCE_NO));
                    hologramModal.code = cursor.getString(cursor.getColumnIndex(DBHelper.CL_CODE));
                    hologramModal.description = cursor.getString(cursor.getColumnIndex(DBHelper.CL_DESCRIPTION));
                    hologramModal.customerName = cursor.getString(cursor.getColumnIndex(DBHelper.CL_CUSTOMER_NAME));
                    hologramModal.department = cursor.getString(cursor.getColumnIndex(DBHelper.CL_DEPARTMENT_NAME));
                    hologramModal.vendor = cursor.getString(cursor.getColumnIndex(DBHelper.CL_VENDOR));
                    hologramModal.hologramStatus = cursor.getString(cursor.getColumnIndex(DBHelper.CL_HOLOGRAM_STATUS));
                    hologramModal.editable_date = cursor.getString(cursor.getColumnIndex(DBHelper.CL_EDITABLE_DATE));
                    hologramModal.hologram_no = cursor.getString(cursor.getColumnIndex(DBHelper.CL_HOLOGRAM_NO));
                    hologramModal.hologram_establish_date = cursor.getString(cursor.getColumnIndex(DBHelper.CL_HOLOGRAM_ESTABLISH_DATE));
                    hologramModal.hologram_expiry_date = cursor.getString(cursor.getColumnIndex(DBHelper.CL_HOLOGRAM_EXPIRY_DATE));
                    hologramModal.synced = cursor.getInt(cursor.getColumnIndex(DBHelper.CL_SYNC));

                    hologramModal.imagesList.addAll(getListImages(mContext, hologramModal.ItemID));

                    hologramModalArrayList.add(hologramModal);
                }
            }
            cursor.close();
            database.close();
            FslLog.d(TAG, " cout of founded style list " + hologramModalArrayList.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hologramModalArrayList;
    }


    public static String getQueryForSyncStyle(String pRowID) {

        String query = "SELECT Distinct " + DBHelper.CL_ITEM_ID
                + ", " + DBHelper.CL_HOLOGRAM_ESTABLISH_DATE
                + ", " + DBHelper.CL_HOLOGRAM_EXPIRY_DATE
                + ", " + DBHelper.CL_HOLOGRAM_NO
                + ", " + DBHelper.CL_LOC_ID
                + ", " + DBHelper.CL_PROW_ID
                + ", " + DBHelper.CL_Holoram_User
                + "  FROM " + DBHelper.TB_STYLE_DETAIL_TABLE
                + " where " + DBHelper.CL_ITEM_ID + "='" + pRowID + "'  GROUP BY " + DBHelper.CL_ITEM_ID;
        return query;
    }

    public static String getQueryForSyncStyleImage(String pRowID) {

        String query = "SELECT *  FROM " + DBHelper.TB_STYLE_IMAGE_TABLE + " where " + DBHelper.CL_STYLE_PROW_ID + "='" + pRowID + "'";
        return query;
    }

    public static Map<String, Object> getStyleImagesTableData(Context mContext, List<String> hdrIdList) {
        Map<String, Object> params = null;
        if (hdrIdList != null && hdrIdList.size() > 0) {
            params = new HashMap<>();
//            for (int i = 0; i < hdrIdList.size(); i++) {
            params.put("HologramImages", getHologram_ImageJson(mContext, hdrIdList));
//            }
        }
        return params;
    }


    private static JSONArray getHologram_ImageJson(Context mContext, List<String> idsListForSync) {

        JSONArray jsonArrayList = new JSONArray();
        try {
            String userId = LogInHandler.getUserId(mContext);
            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getReadableDatabase();
            for (int r = 0; r < idsListForSync.size(); r++) {
                String query = getQueryForSyncStyleImage(idsListForSync.get(r));
                FslLog.d(TAG, " query to pass  " + query);
                Cursor cursor = database.rawQuery(query, null);
                if (cursor.getCount() > 0) {
                    for (int i = 0; i < cursor.getCount(); i++) {
                        cursor.moveToNext();
                        Map<String, Object> jsonObj = new HashMap<String, Object>();
                        for (int j = 0; j < cursor.getColumnCount(); j++) {
                            if (cursor.getColumnName(j).equals(DBHelper.CL_STYLE_IMAGE)) {
                                //No need to file content
                            }
                            /*if (cursor.getColumnName(j).equals("ItemID")) {
                                //No need to file content
                                jsonObj.put(cursor.getColumnName(j), "");
                            } else*/
                            if (cursor.getColumnName(j).equals("ImageSqn")) {
                                //No need to file content
                                jsonObj.put(cursor.getColumnName(j), (i + 1));
                            } else if (cursor.getColumnName(j).equals(DBHelper.CL_STYLE_IMAGE_EXT)) {


                                //No need to file content
                                String fileExt = cursor.getString(cursor.getColumnIndex(cursor.getColumnName(j)));

                                String file = cursor.getString(cursor.getColumnIndex(DBHelper.CL_STYLE_PROW_ID)) + "_"
                                        + userId + "_"
                                        + cursor.getString(cursor.getColumnIndex(DBHelper.CL_IMAGE_PROW_ID))
                                        + "." + fileExt;
                                jsonObj.put("ImageName", file);


                                if (TextUtils.isEmpty(fileExt) || fileExt.equals("null")) {
                                    jsonObj.put(cursor.getColumnName(j), FEnumerations.E_IMAGE_EXTN);
                                } else {
                                    jsonObj.put(cursor.getColumnName(j), cursor.getString(cursor.getColumnIndex(cursor.getColumnName(j))));
                                }
                            } else {
                                jsonObj.put(cursor.getColumnName(j), cursor.getString(cursor.getColumnIndex(cursor.getColumnName(j))));
                            }

                        }
                        jsonArrayList.put(new JSONObject(jsonObj));
                    }
                }
                cursor.close();
            }

            database.close();
            FslLog.d(TAG, " json array from table " + jsonArrayList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonArrayList;


    }

    public static List<ImageModal> getStyleImagesForSync(Context mContext, List<String> idsListForSync) {
        List<ImageModal> imageModals = new ArrayList<>();
        try {
            String userId = LogInHandler.getUserId(mContext);
            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getReadableDatabase();
            for (int r = 0; r < idsListForSync.size(); r++) {
                String query = getQueryForSyncStyleImage(idsListForSync.get(r));
                FslLog.d(TAG, "get   query for  syncing style list image " + query);
                Cursor cursor = database.rawQuery(query, null);
                if (cursor.getCount() > 0) {

                    for (int i = 0; i < cursor.getCount(); i++) {
                        cursor.moveToNext();
                        int sentFile = cursor.getInt(cursor.getColumnIndex(DBHelper.CL_SYNC));
                        if (sentFile == 0) {
                            String _file = cursor.getString(cursor.getColumnIndex(DBHelper.CL_STYLE_IMAGE));
                            if (!TextUtils.isEmpty(_file)
                                    && !_file.equals("null")) {
                                Uri uri = Uri.parse(_file);
                                File testFile = new File(uri.getPath());
                                if (testFile.exists()) {
                                    ImageModal
                                            imageModal = new ImageModal();
                                    imageModal.pRowID = cursor.getString(cursor.getColumnIndex(DBHelper.CL_IMAGE_PROW_ID));
                                    imageModal.QRPOItemHdrID = cursor.getString(cursor.getColumnIndex(DBHelper.CL_STYLE_PROW_ID));

                                    imageModal.Length = "";

                                    String fileExtn = FEnumerations.E_IMAGE_EXTN;
                                    String _fileEx = cursor.getString(cursor.getColumnIndex(DBHelper.CL_STYLE_IMAGE_EXT));
                                    if (!TextUtils.isEmpty(_fileEx) && !_fileEx.equals("null")) {
                                        fileExtn = _fileEx;
                                    }

                                    String file = cursor.getString(cursor.getColumnIndex(DBHelper.CL_STYLE_PROW_ID)) + "_"
                                            + userId + "_"
                                            + cursor.getString(cursor.getColumnIndex(DBHelper.CL_IMAGE_PROW_ID))
                                            + "." + fileExtn;
                                    imageModal.FileName = file;


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
                } else {
                    FslLog.e(TAG, " NO IMAGES TO STYLE SYNC........... ");
                }
                cursor.close();
            }
            database.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imageModals;
    }

    public static void updateImageToSync(Context mContext, String plocalID) {

        DBHelper dbHelper = new DBHelper(mContext);
        SQLiteDatabase database = dbHelper.getWritableDatabase();


        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.CL_SYNC, 1);
        FslLog.d(TAG, "Set content images  " + contentValues);


        int rows = database.update(DBHelper.TB_STYLE_IMAGE_TABLE, contentValues, DBHelper.CL_IMAGE_PROW_ID
                + " = '" + plocalID + "'", null);
        database.close();
        if (rows > 0) {
            FslLog.d(TAG, " update  STYLE_IMAGE_TABLE  after sync");
        }

    }

    public static void updateHologramSyncToServer(Context mContext, List<String> idsListForSync) {

        DBHelper dbHelper = new DBHelper(mContext);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        for (int i = 0; i < idsListForSync.size(); i++) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DBHelper.CL_SYNC, 2);
            FslLog.d(TAG, "Set content images  " + contentValues);
            int rows = database.update(DBHelper.TB_STYLE_DETAIL_TABLE, contentValues, DBHelper.CL_ITEM_ID
                    + " = '" + idsListForSync.get(i) + "'", null);
            if (rows > 0) {
                FslLog.d(TAG, " update  STYLE TABLE  after sync");
            }
        }
        database.close();
    }

    public static Map<String, Object> getStyleData(Context mContext, List<String> idsListForSync) {
        Map<String, Object> params = new HashMap<>();

        params.put("HologramList", getJsonFromStyle(mContext, idsListForSync));


        return params;
    }

    public static JSONArray getJsonFromStyle(Context mContext, List<String> idsListForSync) {
        JSONArray jsonArrayList = new JSONArray();
        try {
            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getReadableDatabase();
            for (int r = 0; r < idsListForSync.size(); r++) {
                String query = getQueryForSyncStyle(idsListForSync.get(r));
                FslLog.d(TAG, " query to pass  " + query);
                Cursor cursor = database.rawQuery(query, null);

                if (cursor.getCount() > 0) {


                    for (int i = 0; i < cursor.getCount(); i++) {
                        cursor.moveToNext();

                        Map<String, Object> jsonObj = new HashMap<String, Object>();
                        for (int j = 0; j < cursor.getColumnCount(); j++) {
                            jsonObj.put(cursor.getColumnName(j), cursor.getString(cursor.getColumnIndex(cursor.getColumnName(j))));
                        }
                        jsonArrayList.put(new JSONObject(jsonObj));
                    }
                }
                cursor.close();
            }
            database.close();
            FslLog.d(TAG, " json array from table " + jsonArrayList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonArrayList;
    }

    public static ArrayList<String> getListImages(Context mContext, String pRowID) {
        ArrayList<String> images = new ArrayList<>();
        try {
            DBHelper dbHelper = new DBHelper(mContext);
            SQLiteDatabase database = dbHelper.getReadableDatabase();
            String query = "SELECT * FROM  " + DBHelper.TB_STYLE_IMAGE_TABLE +
                    "  WHERE " + DBHelper.CL_STYLE_PROW_ID + " = '" + pRowID + "'";
            FslLog.d(TAG, "get  query for  style image  " + query);
            Cursor cursor = database.rawQuery(query, null);

            if (cursor.getCount() > 0) {
                for (int i = 0; i < cursor.getCount(); i++) {
                    cursor.moveToNext();
                    images.add(cursor.getString(cursor.getColumnIndex(DBHelper.CL_STYLE_IMAGE)));
                }
            }
            cursor.close();
            database.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        FslLog.d(TAG, "size of style images  is " + images.size());
        return images;
    }

    public static void updateHologram(Context mContext, HologramModal mHologramModal, String itemID) {
        UserMaster userMaster = LogInHandler.getUserMaster(mContext);
        DBHelper dbHelper = new DBHelper(mContext);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        handleCommanUpdateHologram(mHologramModal, userMaster, itemID, database);
        database.close();


    }

    private static void handleCommanUpdateHologram(HologramModal mHologramModal, UserMaster userMaster, String itemID, SQLiteDatabase database) {

        ContentValues contentValues = new ContentValues();

        contentValues.put(DBHelper.CL_HOLOGRAM_NO, mHologramModal.hologram_no);
        contentValues.put(DBHelper.CL_HOLOGRAM_ESTABLISH_DATE, mHologramModal.hologram_establish_date);
        contentValues.put(DBHelper.CL_HOLOGRAM_EXPIRY_DATE, mHologramModal.hologram_expiry_date);
        contentValues.put(DBHelper.CL_SYNC, 1);
        contentValues.put(DBHelper.CL_Holoram_User, userMaster.LoginName);
        contentValues.put(DBHelper.CL_LOC_ID, FClientConfig.LOC_ID);
        contentValues.put(DBHelper.CL_REC_DATE, AppConfig.getCurrntDate());

        FslLog.d(TAG, "Set content hologram updating  " + contentValues);
        int rows = database.update(DBHelper.TB_STYLE_DETAIL_TABLE, contentValues, DBHelper.CL_ITEM_ID
                + " = '" + itemID + "'", null);

        if (rows == 0) {
            FslLog.d(TAG, " why NOT UPDATE ??? ");
        } else {
            FslLog.d(TAG, " update hologram  ..........");
        }
    }

    public static void updateMultipleHologram(Context mContext, HologramModal mHologramModal, List<HologramModal> hologramModalList) {
        UserMaster userMaster = LogInHandler.getUserMaster(mContext);
        DBHelper dbHelper = new DBHelper(mContext);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        for (int i = 0; i < hologramModalList.size(); i++) {
            handleCommanUpdateHologram(mHologramModal, userMaster, hologramModalList.get(i).ItemID, database);
        }
        database.close();


    }


    public static void insertImage(Context mContext, ArrayList<String> imagePath, String ext, String stylePRowId) {
        String userId = LogInHandler.getUserId(mContext);
        DBHelper dbHelper = new DBHelper(mContext);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        for (int i = 0; i < imagePath.size(); i++) {
            handleCommanInsertImage(mContext, imagePath.get(i), ext, stylePRowId, userId, database);
        }
        database.close();

    }

    private static void handleCommanInsertImage(Context mContext, String imagePath, String ext, String stylePRowId, String userId, SQLiteDatabase database) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.CL_STYLE_IMAGE, imagePath);
        contentValues.put(DBHelper.CL_STYLE_IMAGE_EXT, ext);
        contentValues.put(DBHelper.CL_STYLE_PROW_ID, stylePRowId);
        contentValues.put(DBHelper.CL_REC_USER_ID, userId);
        contentValues.put(DBHelper.CL_LOC_ID, FClientConfig.LOC_ID);
        contentValues.put(DBHelper.CL_IMAGE_PROW_ID, ItemInspectionDetailHandler.GeneratePK(mContext, DBHelper.TB_STYLE_IMAGE_TABLE));
        FslLog.d(TAG, "Set content images  " + contentValues);

        long status = database.insert(DBHelper.TB_STYLE_IMAGE_TABLE, null, contentValues);
        FslLog.d(TAG, " insert image in " + DBHelper.TB_STYLE_IMAGE_TABLE);
    }

    public static void insertMultipleStyleImage(Context mContext, ArrayList<String> imagePath, String ext, List<HologramModal> lStyle) {
        String userId = LogInHandler.getUserId(mContext);
        DBHelper dbHelper = new DBHelper(mContext);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        for (int s = 0; s < lStyle.size(); s++) {
            for (int i = 0; i < imagePath.size(); i++) {
                handleCommanInsertImage(mContext, imagePath.get(i), ext, lStyle.get(s).ItemID, userId, database);
            }
        }
        database.close();

    }

    public static boolean deleteImage(Context mContext, String path, String ItemID) {

        DBHelper dbHelper = new DBHelper(mContext);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        removeCommanImage(path, ItemID, database);

        database.close();
        return true;
    }

    public static void deleteMultipleImage(Context mContext, String path, List<HologramModal> lStyle) {
        DBHelper dbHelper = new DBHelper(mContext);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        for (int s = 0; s < lStyle.size(); s++) {
            removeCommanImage(path, lStyle.get(s).ItemID, database);
        }
        database.close();
    }

    private static void removeCommanImage(String path, String ItemID, SQLiteDatabase database) {
        String query = "DELETE FROM  " + DBHelper.TB_STYLE_IMAGE_TABLE + " WHERE " + DBHelper.CL_STYLE_IMAGE + "='" + path
                + "' and " + DBHelper.CL_STYLE_PROW_ID + "='" + ItemID + "'";

        FslLog.d(TAG, "get  query for delete hologram image  " + query);
        FslLog.d(TAG, "delete image of item id : " + ItemID);
        database.execSQL(query);
    }

}
