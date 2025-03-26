package com.dashboard;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.General.SysData22Handler;
import com.General.SysData22Modal;
import com.android.volley.VolleyError;
import com.buyereasefsl.ItemInspectionDetailHandler;
import com.buyereasefsl.R;
import com.constant.FClientConfig;
import com.constant.FEnumerations;
import com.constant.FStatusCode;
import com.constant.JsonKey;
import com.data.UserSession;
import com.hologram.StyleHandler;
import com.hologram.StyleList;
import com.inspection.InspectionListActivity;
import com.login.ChangePassword;
import com.login.LogInActivity;
import com.sync.GetDataHandler;
import com.sync.ImageModal;
import com.sync.MyWorkerThread;
import com.sync.SendDataHandler;
import com.util.BitmapUtil;
import com.util.FslLog;
import com.util.GenUtils;
import com.util.ImgToStringConverter;
import com.util.NetworkUtil;
import com.util.SetInitiateStaticVariable;
import com.util.VolleyErrorHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import me.drakeet.support.toast.ToastCompat;

/**
 * Created by ADMIN on 12/26/2017.
 */

public class ChooseDataActivity extends AppCompatActivity implements JsonKey, View.OnClickListener {


    String TAG = "ChooseDataActivity";
    UserSession userSession;
    static boolean active = false;
    TextView txtSyncCountSection;

    ProgressDialog loadingDialog;

    ProgressBar progress;

    CheckBox chkGetInspectionData, chkGetMasterData;
    Button getDataSubmit, sendSubmit, getStyleDataSubmit, sendStyleSubmit;
    //    ProgressBar getDataProgressBar;
    List<StatusModal> statusModalList;

    RecyclerView recyclerView;
    SyncStatusAdaptor syncStatusAdaptor;
    int mViewType, mSyncViewType;
    List<String> listedSyncIds = null;
    List<String> idsListForSync = new ArrayList<>();
    List<String> syncDoneProcess = new ArrayList<>();
    int totalIdsToSync;
    LinearLayout styleSyncContainer, poSyncContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        userSession = new UserSession(ChooseDataActivity.this);
        getSupportActionBar().setTitle("Buyerease");
        TextView companyName = (TextView) findViewById(R.id.companyName);
        String st = GenUtils.truncate(new UserSession(ChooseDataActivity.this).getCompanyName(), FClientConfig.COMPANY_TRUNC_LENGTH);
        companyName.setText(st);
        SetInitiateStaticVariable.setInitiateStaticVariable(ChooseDataActivity.this);
        if (savedInstanceState != null) {
            mViewType = savedInstanceState.getInt("type", 0);
            mSyncViewType = savedInstanceState.getInt("mSyncViewType", 0);
        } else {
            mViewType = getIntent().getIntExtra("type", 0);
            mSyncViewType = getIntent().getIntExtra("mSyncViewType", 0);
        }
        if (getIntent().hasExtra("list")) {
            listedSyncIds = getIntent().getStringArrayListExtra("list");
        }

        styleSyncContainer = (LinearLayout) findViewById(R.id.styleSyncContainer);
        poSyncContainer = (LinearLayout) findViewById(R.id.poSyncContainer);

        chkGetInspectionData = (CheckBox) findViewById(R.id.chkGetInspectionData);
        chkGetMasterData = (CheckBox) findViewById(R.id.chkGetMasterData);
        getDataSubmit = (Button) findViewById(R.id.getDataSubmit);
        sendSubmit = (Button) findViewById(R.id.sendSubmit);
        getStyleDataSubmit = (Button) findViewById(R.id.getStyleDataSubmit);
        sendStyleSubmit = (Button) findViewById(R.id.sendStyleSubmit);

//        getDataProgressBar = (ProgressBar) findViewById(R.id.getDataProgressBar);

        txtSyncCountSection = (TextView) findViewById(R.id.txtSyncCountSection);
//        if (!chkGetInspectionData.isChecked() && !chkGetMasterData.isChecked()) {
//            disableGetDataButton();
//        }

        if (mSyncViewType == FEnumerations.E_VIEW_TYPE_HOLOGRAM_STYLE) {
            poSyncContainer.setVisibility(View.GONE);
            styleSyncContainer.setVisibility(View.VISIBLE);
            getStyleDataSubmit.setOnClickListener(this);
            sendStyleSubmit.setOnClickListener(this);
            handleHologramView();
            handleToCreateHologramDb();
        } else {
            poSyncContainer.setVisibility(View.VISIBLE);
            styleSyncContainer.setVisibility(View.GONE);
            getDataSubmit.setOnClickListener(this);
            sendSubmit.setOnClickListener(this);
            handleView();
        }


    }

    private void handleToCreateHologramDb() {
        StyleHandler.checkAndCreateTable(ChooseDataActivity.this);
    }

    private void handleHologramView() {
        if (mViewType == FEnumerations.E_VIEW_ONLY_SEND) {
            findViewById(R.id.getStyleDataCardContainer).setVisibility(View.GONE);
            findViewById(R.id.sendStyleDataCardContainer).setVisibility(View.VISIBLE);
            findViewById(R.id.syncContainer).setVisibility(View.VISIBLE);
            InitilizaeStatusHologramView();
        } else {
            findViewById(R.id.getStyleDataCardContainer).setVisibility(View.VISIBLE);
            findViewById(R.id.sendStyleDataCardContainer).setVisibility(View.VISIBLE);
            findViewById(R.id.syncContainer).setVisibility(View.GONE);
        }
    }


    private void handleView() {

        if (mViewType == FEnumerations.E_VIEW_ONLY_SYNC) {
            findViewById(R.id.getDataCardContainer).setVisibility(View.GONE);
            findViewById(R.id.sendDataCardContainer).setVisibility(View.GONE);
            findViewById(R.id.syncContainer).setVisibility(View.VISIBLE);
            InitilizaeStatusView();


        } else if (mViewType == FEnumerations.E_VIEW_SEND_AND_SYNC) {
            findViewById(R.id.getDataCardContainer).setVisibility(View.GONE);
            findViewById(R.id.sendDataCardContainer).setVisibility(View.GONE);
            findViewById(R.id.syncContainer).setVisibility(View.VISIBLE);
            InitilizaeStatusView();


        } else {
            findViewById(R.id.getDataCardContainer).setVisibility(View.VISIBLE);
            findViewById(R.id.sendDataCardContainer).setVisibility(View.GONE);
            findViewById(R.id.syncContainer).setVisibility(View.GONE);


        }


    }

    private void InitilizaeStatusHologramView() {
        if (listedSyncIds != null && listedSyncIds.size() > 0) {
            FslLog.d(TAG, "Select list ids : %s%n " + listedSyncIds.toString());
            Set<String> hashsetList = new HashSet<String>(listedSyncIds);
            FslLog.d(TAG, "\nUnique list ids  : %s%n " + hashsetList.toString());
            idsListForSync.addAll(hashsetList);
            handleListToSync();
            viewStatusListOfSyncOfStyleHologram();
            handleToSynsStyle();
        }

    }

    private void InitilizaeStatusView() {
        if (listedSyncIds != null && listedSyncIds.size() > 0) {
            totalIdsToSync = listedSyncIds.size();
        }
        handleListToSync();
        handleToSendData();
    }

    private void handleToSendData() {
        updateStatusUI();
        if (listedSyncIds != null && listedSyncIds.size() > 0) {
            idsListForSync.add(listedSyncIds.get(0));
            viewStatusListOfSync();
            getDELToSunc();
        }

    }

    private void getDELToSunc() {
        handleToHeaderSync();
    }

    private void handleToHeaderSync() {
        if (idsListForSync != null && idsListForSync.size() > 0) {
            //for testing purpose
//            SendDataHandler.getOnSiteDataData(ChooseDataActivity.this, idsListForSync);
//            SendDataHandler.getPkgAppearanceData(ChooseDataActivity.this, idsListForSync);
//            SendDataHandler.getSampleCollectedData(ChooseDataActivity.this, idsListForSync);

            Map<String, Object> hdrTables = SendDataHandler.getHdrTableData(ChooseDataActivity.this, idsListForSync);
            if (hdrTables != null && hdrTables.size() > 0) {
                updateSyncList(FEnumerations.E_SYNC_HEADER_TABLE, FEnumerations.E_SYNC_IN_PROCESS_STATUS);
                final Map<String, Object> inspectionData = new HashMap<String, Object>();
                final Map<String, Object> data = new HashMap<String, Object>();
                data.put("Data", new JSONObject(hdrTables));
                data.put("ImageFiles", "");
                data.put("EnclosureFiles", "");
                data.put("TestReportFiles", "");
                data.put("Result", true);
                data.put("Message", "");
                data.put("MsgDetail", "");
                inspectionData.put("InspectionData", data);

                FslLog.d(TAG, " Header sync .........................................\n\n");

                SendDataHandler.sendData(ChooseDataActivity.this, inspectionData, "", new SendDataHandler.GetCallBackResult() {
                    @Override
                    public void onSuccess(JSONObject loginResponse) {
                        if (loginResponse.optBoolean("Result")) {
                            updateSyncList(FEnumerations.E_SYNC_HEADER_TABLE, FEnumerations.E_SYNC_SUCCESS_STATUS);
                            handleToSizeQtySync();
                        } else {
                            updateSyncList(FEnumerations.E_SYNC_HEADER_TABLE, FEnumerations.E_SYNC_FAILED_STATUS);
                            String msg = loginResponse.optString("Message");
                            FslLog.e(TAG, FEnumerations.E_SYNC_HEADER_TABLE + " \n" + msg);
                            handleToSizeQtySync();//added by shekhar
                        }
                    }

                    @Override
                    public void onError(VolleyError volleyError) {
                        updateSyncList(FEnumerations.E_SYNC_HEADER_TABLE, FEnumerations.E_SYNC_FAILED_STATUS);
                        requestVolleyError(volleyError);
                    }
                });
            } else {
                updateSyncList(FEnumerations.E_SYNC_HEADER_TABLE, FEnumerations.E_SYNC_SUCCESS_STATUS);
                handleToImageSync();
                FslLog.d(TAG, " NO DATA FOUNDED FROM HDR TO SYNC..........");
            }
        }
    }
    private void handleToSizeQtySync() {
        if (idsListForSync != null && idsListForSync.size() > 0) {
            Map<String, Object> hdrTables = SendDataHandler.getSizeQtyData(ChooseDataActivity.this, idsListForSync);
            if (hdrTables != null && hdrTables.size() > 0) {
                updateSyncList(FEnumerations.E_SYNC_SIZE_QUANTITY_TABLE, FEnumerations.E_SYNC_IN_PROCESS_STATUS);
                final Map<String, Object> inspectionData = new HashMap<String, Object>();
                final Map<String, Object> data = new HashMap<String, Object>();
                data.put("Data", new JSONObject(hdrTables));
                data.put("ImageFiles", "");
                data.put("EnclosureFiles", "");
                data.put("TestReportFiles", "");
                data.put("Result", true);
                data.put("Message", "");
                data.put("MsgDetail", "");
                inspectionData.put("InspectionData", data);

                FslLog.d(TAG, " Header sync .........................................\n\n");

                SendDataHandler.sendData(ChooseDataActivity.this, inspectionData, "", new SendDataHandler.GetCallBackResult() {
                    @Override
                    public void onSuccess(JSONObject loginResponse) {
                        if (loginResponse.optBoolean("Result")) {
                            updateSyncList(FEnumerations.E_SYNC_SIZE_QUANTITY_TABLE, FEnumerations.E_SYNC_SUCCESS_STATUS);
                            handleToImageSync();
                        } else {
                            updateSyncList(FEnumerations.E_SYNC_SIZE_QUANTITY_TABLE, FEnumerations.E_SYNC_FAILED_STATUS);
                            String msg = loginResponse.optString("Message");
                            FslLog.e(TAG, FEnumerations.E_SYNC_SIZE_QUANTITY_TABLE + " \n" + msg);
                            handleToImageSync();//added by shekhar
                        }
                    }

                    @Override
                    public void onError(VolleyError volleyError) {
                        updateSyncList(FEnumerations.E_SYNC_HEADER_TABLE, FEnumerations.E_SYNC_FAILED_STATUS);
                        requestVolleyError(volleyError);
                    }
                });
            } else {
                updateSyncList(FEnumerations.E_SYNC_HEADER_TABLE, FEnumerations.E_SYNC_SUCCESS_STATUS);
                handleToImageSync();
                FslLog.d(TAG, " NO DATA FOUNDED FROM HDR TO SYNC..........");
            }
        }
    }

    private void handleToImageSync() {
        if (idsListForSync != null && idsListForSync.size() > 0) {
            Map<String, Object> imgTables = SendDataHandler.getImagesTableData(ChooseDataActivity.this, idsListForSync);
            if (imgTables != null && imgTables.size() > 0) {
                updateSyncList(FEnumerations.E_SYNC_IMAGES_TABLE, FEnumerations.E_SYNC_IN_PROCESS_STATUS);
                final Map<String, Object> inspectionData = new HashMap<String, Object>();
                final Map<String, Object> data = new HashMap<String, Object>();
                data.put("Data", new JSONObject(imgTables));
//                Map<String, Object> imagesList = SendDataHandler.getImagesFileTableData(ChooseDataActivity.this, idsListForSync);
//                if (imagesList != null) {
//                    data.put("ImageFiles", imagesList.get("list"));
//                } else {
                data.put("ImageFiles", "");
//                }

                data.put("EnclosureFiles", "");
                data.put("TestReportFiles", "");
                data.put("Result", true);
                data.put("Message", "");
                data.put("MsgDetail", "");
                inspectionData.put("InspectionData", data);

                FslLog.d(TAG, " IMAGES sync .........................................\n\n");

                SendDataHandler.sendData(ChooseDataActivity.this, inspectionData, "", new SendDataHandler.GetCallBackResult() {
                    @Override
                    public void onSuccess(JSONObject loginResponse) {
                        if (loginResponse.optBoolean("Result")) {
//                            updateSyncList(FEnumerations.E_SYNC_IMAGES_TABLE, FEnumerations.E_SYNC_SUCCESS_STATUS);
                            handleToSingleImageSync();

                        } else {
                            updateSyncList(FEnumerations.E_SYNC_IMAGES_TABLE, FEnumerations.E_SYNC_FAILED_STATUS);
                            String msg = loginResponse.optString("Message");
                            FslLog.e(TAG, FEnumerations.E_SYNC_IMAGES_TABLE + " \n" + msg);
                            handleToSingleImageSync();//added by shekhar
//                            handleToWorkmanShipSync();
                        }
                    }

                    @Override
                    public void onError(VolleyError volleyError) {
                        updateSyncList(FEnumerations.E_SYNC_IMAGES_TABLE, FEnumerations.E_SYNC_FAILED_STATUS);
                        requestVolleyError(volleyError);
                    }
                });
            } else {
                handleToSingleImageSync();
                FslLog.d(TAG, " NO DATA FOUNDED FROM IMAGES TO SYNC..........");
            }
        }
    }


    private void handleToSingleImageSync() {
        MyWorkerThread mWorkerThread;
        if (idsListForSync != null && idsListForSync.size() > 0) {
           /* mWorkerThread = new MyWorkerThread(new Handler(), new MyWorkerThread.Callback() {
                @Override
                public void onImageUploaded(JSONObject result, int pos) {
                    FslLog.d(TAG, " response of sending image  " + result + " pos " + pos);
                    if ((idsListForSync.size() - 1) == pos) {
                        updateSyncList(FEnumerations.E_SYNC_IMAGES_TABLE, FEnumerations.E_SYNC_SUCCESS_STATUS);
                        handleToWorkmanShipSync();
                    }
                }

                @Override
                public void onErrorUploaded(VolleyError volleyError, int pos) {
                    FslLog.d(TAG, " error of sending image  " + volleyError + " pos " + pos);
//                    if ((idsListForSync.size() - 1) == pos) {
//                        handleToWorkmanShipSync();
//                    }
                    updateSyncList(FEnumerations.E_SYNC_IMAGES_TABLE, FEnumerations.E_SYNC_FAILED_STATUS);
                    requestVolleyError(volleyError);
                }
            });
            mWorkerThread.start();
            mWorkerThread.prepareHandler();
            Random random = new Random();*/


            updateSyncList(FEnumerations.E_SYNC_IMAGES_TABLE, FEnumerations.E_SYNC_IN_PROCESS_STATUS);
//                final Map<String, Object> inspectionData = new HashMap<String, Object>();
            final Map<String, Object> data = new HashMap<String, Object>();
//                data.put("Data", new JSONObject(imgTables));
            List<ImageModal> imagesList = SendDataHandler.getImagesFileArrayData(ChooseDataActivity.this, idsListForSync);
            List<String> syncedList = new ArrayList<>();
            if (imagesList != null && imagesList.size() > 0) {
                data.put("FileType", FEnumerations.E_SEND_FILE_IMAGE_INSPECTION);
                for (int i = 0; i < imagesList.size(); i++) {
                    ImageModal imageModal = imagesList.get(i);
                    Map<String, Object> jsonObj = new HashMap<String, Object>();
//                    for (int j = 0; j < cursor.getColumnCount(); j++) {
//                    jsonObj.put("FileName", cursor.getString(cursor.getColumnIndex("ImageName")));
                    jsonObj.put("pRowID", imageModal.pRowID);
                    jsonObj.put("QRHdrID", imageModal.QRHdrID);
                    jsonObj.put("QRPOItemHdrID", imageModal.QRPOItemHdrID);
                    jsonObj.put("Length", imageModal.Length);


                    jsonObj.put("FileName", imageModal.FileName);

//                    String file = cursor.getString(cursor.getColumnIndex("fileContent"));
//                    if (!TextUtils.isEmpty(file) && !file.equals("null")) {
//                        byte[] decodedBytes = Base64.decode(file, 1);
//                        jsonObj.put("fileContent", decodedBytes);
//                    } else {


                    String base64 = null;
                    BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                    Bitmap bitmap = BitmapFactory.decodeFile(imageModal.fileContent, bmOptions);
                    if (bitmap != null)
                        base64 = ImgToStringConverter.BitMapToStringT(BitmapUtil.CustomScaleBitmap(bitmap)); //ImgToStringConverter.encodeFileToBase64Binary(imageModal.fileContent);
                    //                        byte[] compressed = GzipUtil.compress(base64);
//                        String result = Base64.encodeToString(compressed, Base64.DEFAULT);
                    if (bitmap != null) {
                        FslLog.d(TAG, " recycle bitmap.. ");
                        bitmap.recycle();
                        bitmap = null;
                        System.gc();
                    }

                    jsonObj.put("fileContent", base64);

                    data.put("FileData", new JSONObject(jsonObj));
//                    mWorkerThread.queueTask(data, i);
                    SendDataHandler sendDataHandler = new SendDataHandler();
                    sendDataHandler.handleRequest(data, i, new SendDataHandler.GetCallBackSendResult() {
                        @Override
                        public void onSuccess(JSONObject result, int postion) {
                            if (result.has("Result")) {
                                String pRowID = result.optString("Result");
                                syncedList.add(pRowID);
                                ItemInspectionDetailHandler.updateImageToSync(ChooseDataActivity.this, pRowID);
                                String str = syncedList.size() + "/" + imagesList.size();
                                updateSyncTitleList(FEnumerations.E_SYNC_IMAGES_TABLE, str);
                            }
                            FslLog.d(TAG, " response of sending image  " + result + " pos " + postion);
                            if (imagesList.size() == syncedList.size()) {
                                updateSyncList(FEnumerations.E_SYNC_IMAGES_TABLE, FEnumerations.E_SYNC_SUCCESS_STATUS);
                                handleToWorkmanShipSync();
                            }
                        }

                        @Override
                        public void onError(VolleyError volleyError, int postion) {
                            FslLog.d(TAG, " error of sending image  " + volleyError + " pos " + postion);
                            updateSyncList(FEnumerations.E_SYNC_IMAGES_TABLE, FEnumerations.E_SYNC_FAILED_STATUS);
                            String str = syncedList.size() + "/" + imagesList.size() + " Internet disconnect please try again.";
                            updateSyncTitleList(FEnumerations.E_SYNC_IMAGES_TABLE, str);
                            requestVolleyError(volleyError);
//                            handleToWorkmanShipSync();
                        }
                    });
                }
            } else {
                updateSyncList(FEnumerations.E_SYNC_IMAGES_TABLE, FEnumerations.E_SYNC_SUCCESS_STATUS);
                handleToWorkmanShipSync();
                FslLog.d(TAG, " NO DATA FOUNDED FROM IMAGES TO SYNC..........");
            }
        }
    }


    private void handleToWorkmanShipSync() {

        if (idsListForSync != null && idsListForSync.size() > 0) {
            /*SendDataHandler.getOnSiteDataData(ChooseDataActivity.this, idsListForSync);
            SendDataHandler.getPkgAppearanceData(ChooseDataActivity.this, idsListForSync);
            SendDataHandler.getSampleCollectedData(ChooseDataActivity.this, idsListForSync);*/

            Map<String, Object> workTables = SendDataHandler.getWorkmanShipData(ChooseDataActivity.this, idsListForSync);
            if (workTables != null && workTables.size() > 0) {
                updateSyncList(FEnumerations.E_SYNC_WORKMANSHIP_TABLE, FEnumerations.E_SYNC_IN_PROCESS_STATUS);
                final Map<String, Object> inspectionData = new HashMap<String, Object>();
                final Map<String, Object> data = new HashMap<String, Object>();
                data.put("Data", new JSONObject(workTables));
                data.put("ImageFiles", "");
                data.put("EnclosureFiles", "");
                data.put("TestReportFiles", "");
                data.put("Result", true);
                data.put("Message", "");
                data.put("MsgDetail", "");
                inspectionData.put("InspectionData", data);
                FslLog.d(TAG, " WORKMANSHIP sync .........................................\n\n");
                SendDataHandler.sendData(ChooseDataActivity.this, inspectionData, "", new SendDataHandler.GetCallBackResult() {
                    @Override
                    public void onSuccess(JSONObject loginResponse) {
                        if (loginResponse.optBoolean("Result")) {
                            updateSyncList(FEnumerations.E_SYNC_WORKMANSHIP_TABLE, FEnumerations.E_SYNC_SUCCESS_STATUS);
                            handleToItemMeasurementSync();
//                            handleToPkgAppearanceSync();
                        } else {
                            updateSyncList(FEnumerations.E_SYNC_WORKMANSHIP_TABLE, FEnumerations.E_SYNC_FAILED_STATUS);
                            String msg = loginResponse.optString("Message");
                            FslLog.e(TAG, FEnumerations.E_SYNC_WORKMANSHIP_TABLE + " \n" + msg);
                            handleToItemMeasurementSync();//added by shekhar
//                            handleToPkgAppearanceSync();
                        }
                    }

                    @Override
                    public void onError(VolleyError volleyError) {
                        updateSyncList(FEnumerations.E_SYNC_WORKMANSHIP_TABLE, FEnumerations.E_SYNC_FAILED_STATUS);
                        requestVolleyError(volleyError);
                        handleToItemMeasurementSync();//added by shekhar
//                        handleToPkgAppearanceSync();
                    }
                });
            } else {
                updateSyncList(FEnumerations.E_SYNC_WORKMANSHIP_TABLE, FEnumerations.E_SYNC_SUCCESS_STATUS);
                handleToItemMeasurementSync();
//                handleToPkgAppearanceSync();
                FslLog.d(TAG, " NO DATA FOUNDED FROM WORK MAN SHIP TO SYNC..........");
            }
        }
    }

    private void handleToItemMeasurementSync() {
        if (idsListForSync != null && idsListForSync.size() > 0) {
            Map<String, Object> itemTables = SendDataHandler.getItemMeasurementData(ChooseDataActivity.this, idsListForSync);
            if (itemTables != null && itemTables.size() > 0) {
                updateSyncList(FEnumerations.E_SYNC_ITEM_MEASUREMENT_TABLE, FEnumerations.E_SYNC_IN_PROCESS_STATUS);
                final Map<String, Object> inspectionData = new HashMap<String, Object>();
                final Map<String, Object> data = new HashMap<String, Object>();
                data.put("Data", new JSONObject(itemTables));
                data.put("ImageFiles", "");
                data.put("EnclosureFiles", "");
                data.put("TestReportFiles", "");
                data.put("Result", true);
                data.put("Message", "");
                data.put("MsgDetail", "");
                inspectionData.put("InspectionData", data);
                FslLog.d(TAG, " ITEM MEASUREMENT sync .........................................\n\n");
                SendDataHandler.sendData(ChooseDataActivity.this, inspectionData, "", new SendDataHandler.GetCallBackResult() {
                    @Override
                    public void onSuccess(JSONObject loginResponse) {
                        if (loginResponse.optBoolean("Result")) {
                            updateSyncList(FEnumerations.E_SYNC_ITEM_MEASUREMENT_TABLE, FEnumerations.E_SYNC_SUCCESS_STATUS);
                            handleToFindingSync();
                        } else {
                            updateSyncList(FEnumerations.E_SYNC_ITEM_MEASUREMENT_TABLE, FEnumerations.E_SYNC_FAILED_STATUS);
                            String msg = loginResponse.optString("Message");
                            FslLog.e(TAG, FEnumerations.E_SYNC_ITEM_MEASUREMENT_TABLE + " \n" + msg);
//                            handleToFindingSync();
                        }
                    }

                    @Override
                    public void onError(VolleyError volleyError) {
                        updateSyncList(FEnumerations.E_SYNC_ITEM_MEASUREMENT_TABLE, FEnumerations.E_SYNC_FAILED_STATUS);
                        requestVolleyError(volleyError);
                        handleToFindingSync();//added by shekhar
                    }
                });
            } else {
                updateSyncList(FEnumerations.E_SYNC_ITEM_MEASUREMENT_TABLE, FEnumerations.E_SYNC_SUCCESS_STATUS);
                handleToFindingSync();
                FslLog.d(TAG, " NO DATA FOUNDED FROM Item measurement TO SYNC..........");
            }
        }
    }


    private void handleToFindingSync() {
        if (idsListForSync != null && idsListForSync.size() > 0) {
            Map<String, Object> findingTables = SendDataHandler.getFindingData(ChooseDataActivity.this, idsListForSync);
            if (findingTables != null && findingTables.size() > 0) {
                updateSyncList(FEnumerations.E_SYNC_FINDING_TABLE, FEnumerations.E_SYNC_IN_PROCESS_STATUS);
                final Map<String, Object> inspectionData = new HashMap<String, Object>();
                final Map<String, Object> data = new HashMap<String, Object>();
                data.put("Data", new JSONObject(findingTables));
                data.put("ImageFiles", "");
                data.put("EnclosureFiles", "");
                data.put("TestReportFiles", "");
                data.put("Result", true);
                data.put("Message", "");
                data.put("MsgDetail", "");
                inspectionData.put("InspectionData", data);
                FslLog.d(TAG, " FINDING sync .........................................\n\n");
                SendDataHandler.sendData(ChooseDataActivity.this, inspectionData, "", new SendDataHandler.GetCallBackResult() {
                    @Override
                    public void onSuccess(JSONObject loginResponse) {
                        if (loginResponse.optBoolean("Result")) {
                            updateSyncList(FEnumerations.E_SYNC_FINDING_TABLE, FEnumerations.E_SYNC_SUCCESS_STATUS);
                            handleToQualityParameterSync();
                        } else {
                            updateSyncList(FEnumerations.E_SYNC_FINDING_TABLE, FEnumerations.E_SYNC_FAILED_STATUS);
                            String msg = loginResponse.optString("Message");
                            FslLog.e(TAG, FEnumerations.E_SYNC_FINDING_TABLE + " \n" + msg);
                        }
                    }

                    @Override
                    public void onError(VolleyError volleyError) {
                        updateSyncList(FEnumerations.E_SYNC_FINDING_TABLE, FEnumerations.E_SYNC_FAILED_STATUS);
                        requestVolleyError(volleyError);
                        handleToQualityParameterSync();//added by shekhar
                    }
                });
            } else {
                updateSyncList(FEnumerations.E_SYNC_FINDING_TABLE, FEnumerations.E_SYNC_SUCCESS_STATUS);
                handleToQualityParameterSync();
                FslLog.d(TAG, " NO DATA FOUNDED FROM Finding TO SYNC..........");
            }
        }
    }

    private void handleToQualityParameterSync() {
        if (idsListForSync != null && idsListForSync.size() > 0) {
            Map<String, Object> qualityTables = SendDataHandler.getQualityParameterData(ChooseDataActivity.this, idsListForSync);
            if (qualityTables != null && qualityTables.size() > 0) {
                updateSyncList(FEnumerations.E_SYNC_QUALITY_PARAMETER_TABLE, FEnumerations.E_SYNC_IN_PROCESS_STATUS);
                final Map<String, Object> inspectionData = new HashMap<String, Object>();
                final Map<String, Object> data = new HashMap<String, Object>();
                data.put("Data", new JSONObject(qualityTables));
                data.put("ImageFiles", "");
                data.put("EnclosureFiles", "");
                data.put("TestReportFiles", "");
                data.put("Result", true);
                data.put("Message", "");
                data.put("MsgDetail", "");
                inspectionData.put("InspectionData", data);
                FslLog.d(TAG, " QUALITY PARAMETER sync .........................................\n\n");
                SendDataHandler.sendData(ChooseDataActivity.this, inspectionData, "", new SendDataHandler.GetCallBackResult() {
                    @Override
                    public void onSuccess(JSONObject loginResponse) {
                        if (loginResponse.optBoolean("Result")) {
                            updateSyncList(FEnumerations.E_SYNC_QUALITY_PARAMETER_TABLE, FEnumerations.E_SYNC_SUCCESS_STATUS);
                            handleToFitnessCheckSync();
                        } else {
                            updateSyncList(FEnumerations.E_SYNC_QUALITY_PARAMETER_TABLE, FEnumerations.E_SYNC_FAILED_STATUS);
                            String msg = loginResponse.optString("Message");
                            FslLog.e(TAG, FEnumerations.E_SYNC_QUALITY_PARAMETER_TABLE + " \n" + msg);
                        }
                    }

                    @Override
                    public void onError(VolleyError volleyError) {
                        updateSyncList(FEnumerations.E_SYNC_QUALITY_PARAMETER_TABLE, FEnumerations.E_SYNC_FAILED_STATUS);
                        requestVolleyError(volleyError);
                        handleToFitnessCheckSync();//added by shekhar
                    }
                });
            } else {
                updateSyncList(FEnumerations.E_SYNC_QUALITY_PARAMETER_TABLE, FEnumerations.E_SYNC_SUCCESS_STATUS);
                handleToFitnessCheckSync();
                FslLog.d(TAG, " NO DATA FOUNDED FROM quality parameter TO SYNC..........");
            }
        }
    }

    private void handleToFitnessCheckSync() {
        if (idsListForSync != null && idsListForSync.size() > 0) {
            Map<String, Object> fitTables = SendDataHandler.getFitnessCheckData(ChooseDataActivity.this, idsListForSync);
            if (fitTables != null && fitTables.size() > 0) {
                updateSyncList(FEnumerations.E_SYNC_FITNESS_CHECK_TABLE, FEnumerations.E_SYNC_IN_PROCESS_STATUS);
                final Map<String, Object> inspectionData = new HashMap<String, Object>();
                final Map<String, Object> data = new HashMap<String, Object>();
                data.put("Data", new JSONObject(fitTables));
                data.put("ImageFiles", "");
                data.put("EnclosureFiles", "");
                data.put("TestReportFiles", "");
                data.put("Result", true);
                data.put("Message", "");
                data.put("MsgDetail", "");
                inspectionData.put("InspectionData", data);
                FslLog.d(TAG, " FITNESS CHECK sync .........................................\n\n");
                SendDataHandler.sendData(ChooseDataActivity.this, inspectionData, "", new SendDataHandler.GetCallBackResult() {
                    @Override
                    public void onSuccess(JSONObject loginResponse) {
                        if (loginResponse.optBoolean("Result")) {
                            updateSyncList(FEnumerations.E_SYNC_FITNESS_CHECK_TABLE, FEnumerations.E_SYNC_SUCCESS_STATUS);
                            handleToProductionStatusSync();
                        } else {
                            updateSyncList(FEnumerations.E_SYNC_FITNESS_CHECK_TABLE, FEnumerations.E_SYNC_FAILED_STATUS);
                            String msg = loginResponse.optString("Message");
                            FslLog.e(TAG, FEnumerations.E_SYNC_FITNESS_CHECK_TABLE + " \n" + msg);
                        }
                    }

                    @Override
                    public void onError(VolleyError volleyError) {
                        updateSyncList(FEnumerations.E_SYNC_FITNESS_CHECK_TABLE, FEnumerations.E_SYNC_FAILED_STATUS);
                        requestVolleyError(volleyError);
                        handleToProductionStatusSync();//added by shekhar
                    }
                });
            } else {
                updateSyncList(FEnumerations.E_SYNC_FITNESS_CHECK_TABLE, FEnumerations.E_SYNC_SUCCESS_STATUS);
                handleToProductionStatusSync();
                FslLog.d(TAG, " NO DATA FOUNDED FROM fitness check TO SYNC..........");
            }
        }
    }

    private void handleToProductionStatusSync() {
        if (idsListForSync != null && idsListForSync.size() > 0) {
            Map<String, Object> prodTables = SendDataHandler.getProductionStatusData(ChooseDataActivity.this, idsListForSync);
            if (prodTables != null && prodTables.size() > 0) {
                updateSyncList(FEnumerations.E_SYNC_PRODUCTION_STATUS_TABLE, FEnumerations.E_SYNC_IN_PROCESS_STATUS);
                final Map<String, Object> inspectionData = new HashMap<String, Object>();
                final Map<String, Object> data = new HashMap<String, Object>();
                data.put("Data", new JSONObject(prodTables));
                data.put("ImageFiles", "");
                data.put("EnclosureFiles", "");
                data.put("TestReportFiles", "");
                data.put("Result", true);
                data.put("Message", "");
                data.put("MsgDetail", "");
                inspectionData.put("InspectionData", data);
                FslLog.d(TAG, " PRODUCTION STATUS sync .........................................\n\n");
                SendDataHandler.sendData(ChooseDataActivity.this, inspectionData, "", new SendDataHandler.GetCallBackResult() {
                    @Override
                    public void onSuccess(JSONObject loginResponse) {
                        if (loginResponse.optBoolean("Result")) {
                            updateSyncList(FEnumerations.E_SYNC_PRODUCTION_STATUS_TABLE, FEnumerations.E_SYNC_SUCCESS_STATUS);
                            handleToIntimationSync();
                        } else {
                            updateSyncList(FEnumerations.E_SYNC_PRODUCTION_STATUS_TABLE, FEnumerations.E_SYNC_FAILED_STATUS);
                            String msg = loginResponse.optString("Message");
                            FslLog.e(TAG, FEnumerations.E_SYNC_PRODUCTION_STATUS_TABLE + " \n" + msg);
                        }
                    }

                    @Override
                    public void onError(VolleyError volleyError) {
                        updateSyncList(FEnumerations.E_SYNC_PRODUCTION_STATUS_TABLE, FEnumerations.E_SYNC_FAILED_STATUS);
                        requestVolleyError(volleyError);
                        handleToIntimationSync();//added by shekhar
                    }
                });
            } else {
                updateSyncList(FEnumerations.E_SYNC_PRODUCTION_STATUS_TABLE, FEnumerations.E_SYNC_SUCCESS_STATUS);
                handleToIntimationSync();
                FslLog.d(TAG, " NO DATA FOUNDED FROM  SYNC_PRODUCTION_STATUS_TABLE TO SYNC..........");
            }
        }
    }

    private void handleToIntimationSync() {
        if (idsListForSync != null && idsListForSync.size() > 0) {
            Map<String, Object> intTables = SendDataHandler.getIntimationData(ChooseDataActivity.this, idsListForSync);
            if (intTables != null && intTables.size() > 0) {
                updateSyncList(FEnumerations.E_SYNC_INTIMATION_TABLE, FEnumerations.E_SYNC_IN_PROCESS_STATUS);
                final Map<String, Object> inspectionData = new HashMap<String, Object>();
                final Map<String, Object> data = new HashMap<String, Object>();
                data.put("Data", new JSONObject(intTables));
                data.put("ImageFiles", "");
                data.put("EnclosureFiles", "");
                data.put("TestReportFiles", "");
                data.put("Result", true);
                data.put("Message", "");
                data.put("MsgDetail", "");
                inspectionData.put("InspectionData", data);
                FslLog.d(TAG, " INTIMATION sync .........................................\n\n");
                SendDataHandler.sendData(ChooseDataActivity.this, inspectionData, "", new SendDataHandler.GetCallBackResult() {
                    @Override
                    public void onSuccess(JSONObject loginResponse) {
                        if (loginResponse.optBoolean("Result")) {
                            updateSyncList(FEnumerations.E_SYNC_INTIMATION_TABLE, FEnumerations.E_SYNC_SUCCESS_STATUS);
                            handleToEnclosureSync();
                        } else {
                            updateSyncList(FEnumerations.E_SYNC_INTIMATION_TABLE, FEnumerations.E_SYNC_FAILED_STATUS);
                            String msg = loginResponse.optString("Message");
                            FslLog.e(TAG, FEnumerations.E_SYNC_INTIMATION_TABLE + " \n" + msg);
                        }
                    }

                    @Override
                    public void onError(VolleyError volleyError) {
                        updateSyncList(FEnumerations.E_SYNC_INTIMATION_TABLE, FEnumerations.E_SYNC_FAILED_STATUS);
                        requestVolleyError(volleyError);
                        handleToEnclosureSync();//added by shekhar
                    }
                });
            } else {
                updateSyncList(FEnumerations.E_SYNC_INTIMATION_TABLE, FEnumerations.E_SYNC_SUCCESS_STATUS);
                handleToSingleEnclosureSync();
                FslLog.d(TAG, " NO DATA FOUNDED FROM  SYNC_INTIMATION_TABLE TO SYNC..........");
            }
        }
    }


    private void handleToEnclosureSync() {
        if (idsListForSync != null && idsListForSync.size() > 0) {
            Map<String, Object> enclTables = SendDataHandler.getQREnclosureData(ChooseDataActivity.this, idsListForSync);
            if (enclTables != null && enclTables.size() > 0) {
                updateSyncList(FEnumerations.E_SYNC_ENCLOSURE_TABLE, FEnumerations.E_SYNC_IN_PROCESS_STATUS);
                final Map<String, Object> inspectionData = new HashMap<String, Object>();
                final Map<String, Object> data = new HashMap<String, Object>();
                data.put("Data", new JSONObject(enclTables));
                data.put("ImageFiles", "");

//                Map<String, Object> enclosureList = SendDataHandler.getEnclosureFileTableData(ChooseDataActivity.this, idsListForSync);
//                if (enclosureList != null) {
//                    data.put("EnclosureFiles", enclosureList.get("list"));
//                } else {
                data.put("EnclosureFiles", "");
//                }

                data.put("TestReportFiles", "");
                data.put("Result", true);
                data.put("Message", "");
                data.put("MsgDetail", "");
                inspectionData.put("InspectionData", data);
                FslLog.d(TAG, " ENCLOSURE sync .........................................\n\n");
                SendDataHandler.sendData(ChooseDataActivity.this,
                        inspectionData,
                        "",
                        new SendDataHandler.GetCallBackResult() {
                            @Override
                            public void onSuccess(JSONObject loginResponse) {
                                if (loginResponse.optBoolean("Result")) {
                                    handleToSingleEnclosureSync();
                                } else {
                                    updateSyncList(FEnumerations.E_SYNC_ENCLOSURE_TABLE, FEnumerations.E_SYNC_FAILED_STATUS);
                                    String msg = loginResponse.optString("Message");
                                    FslLog.e(TAG, FEnumerations.E_SYNC_ENCLOSURE_TABLE + " \n" + msg);
                                }
                            }

                            @Override
                            public void onError(VolleyError volleyError) {
                                updateSyncList(FEnumerations.E_SYNC_ENCLOSURE_TABLE, FEnumerations.E_SYNC_FAILED_STATUS);
                                requestVolleyError(volleyError);
                                handleToSingleEnclosureSync();//added by shekhar
                            }
                        });
            } else {
                handleToSingleEnclosureSync();
                FslLog.d(TAG, "NO DATA FOUNDED FROM SYNC ENCLOSURE TABLE TO SYNC........");
            }
        }
    }

    private void handleToSingleEnclosureSync() {
        MyWorkerThread mWorkerThread;
        if (idsListForSync != null && idsListForSync.size() > 0) {
            /*mWorkerThread = new MyWorkerThread(new Handler(), new MyWorkerThread.Callback() {
                @Override
                public void onImageUploaded(JSONObject result, int pos) {
                    FslLog.d(TAG, " response of sending enclosure  " + result + " pos : " + pos);
                    if ((idsListForSync.size() - 1) == pos) {
                        updateSyncList(FEnumerations.E_SYNC_ENCLOSURE_TABLE, FEnumerations.E_SYNC_SUCCESS_STATUS);
                        handleToDigitalsMultipleSync();
                    }
                }

                @Override
                public void onErrorUploaded(VolleyError volleyError, int pos) {
                    FslLog.d(TAG, " error of sending enclosure  " + volleyError + " pos : " + pos);
                    updateSyncList(FEnumerations.E_SYNC_ENCLOSURE_TABLE, FEnumerations.E_SYNC_FAILED_STATUS);
                    requestVolleyError(volleyError);
//                    if ((idsListForSync.size() - 1) == pos) {
//                        handleToDigitalsMultipleSync();
//                    }
                }
            });
            mWorkerThread.start();
            mWorkerThread.prepareHandler();
            Random random = new Random();*/

//            Map<String, Object> imgTables = SendDataHandler.getImagesTableData(ChooseDataActivity.this, idsListForSync);
//            if (imgTables != null && imgTables.size() > 0) {
            updateSyncList(FEnumerations.E_SYNC_ENCLOSURE_TABLE, FEnumerations.E_SYNC_IN_PROCESS_STATUS);
//                final Map<String, Object> inspectionData = new HashMap<String, Object>();
            final Map<String, Object> data = new HashMap<String, Object>();
//                data.put("Data", new JSONObject(imgTables));
            JSONArray imagesList = SendDataHandler.getEnclosureFileArrayData(ChooseDataActivity.this, idsListForSync);

            if (imagesList != null && imagesList.length() > 0) {
                data.put("FileType", FEnumerations.E_SEND_FILE_IMAGE_ENCLOSURE);
                for (int i = 0; i < imagesList.length(); i++) {
                    data.put("FileData", imagesList.optJSONObject(i));
//                    mWorkerThread.queueTask(data, i);
                    SendDataHandler sendDataHandler = new SendDataHandler();
                    sendDataHandler.handleRequest(data, i, new SendDataHandler.GetCallBackSendResult() {
                        @Override
                        public void onSuccess(JSONObject result, int postion) {
                            if (result.has("Result")) {
                                String pRowID = result.optString("Result");
                                ItemInspectionDetailHandler.updateQREnClosureToSync(ChooseDataActivity.this, pRowID);
                            }
                            FslLog.d(TAG, " response of sending image  " + result + " pos " + postion);
                            if ((idsListForSync.size() - 1) == postion) {
                                updateSyncList(FEnumerations.E_SYNC_ENCLOSURE_TABLE, FEnumerations.E_SYNC_SUCCESS_STATUS);
                                handleToDigitalsMultipleSync();
                            }
                        }

                        @Override
                        public void onError(VolleyError volleyError, int postion) {
                            FslLog.d(TAG, " error of sending image  " + volleyError + " pos " + postion);
                            updateSyncList(FEnumerations.E_SYNC_ENCLOSURE_TABLE, FEnumerations.E_SYNC_FAILED_STATUS);
                            requestVolleyError(volleyError);
                        }
                    });
                }
            } else {
                updateSyncList(FEnumerations.E_SYNC_ENCLOSURE_TABLE, FEnumerations.E_SYNC_SUCCESS_STATUS);
                handleToDigitalsMultipleSync();
                FslLog.d(TAG, " NO DATA FOUNDED FROM ENCLOSURE TO SYNC..........");
            }
        }
    }

    private void handleToDigitalsMultipleSync() {
        if (idsListForSync != null && idsListForSync.size() > 0) {
            Map<String, Object> digiTables = SendDataHandler.getDigitalsColumnFromMultipleData(ChooseDataActivity.this, idsListForSync);
            if (digiTables != null && digiTables.size() > 0) {
                updateSyncList(FEnumerations.E_SYNC_DIGITAL_UPLOAD_TABLE, FEnumerations.E_SYNC_IN_PROCESS_STATUS);
                final Map<String, Object> inspectionData = new HashMap<String, Object>();
                final Map<String, Object> data = new HashMap<String, Object>();
                data.put("Data", new JSONObject(digiTables));
                data.put("ImageFiles", "");
                data.put("EnclosureFiles", "");
                data.put("TestReportFiles", "");
                data.put("Result", true);
                data.put("Message", "");
                data.put("MsgDetail", "");
                inspectionData.put("InspectionData", data);
                FslLog.d(TAG, " DIGITAL sync .........................................\n\n");
                SendDataHandler.sendData(ChooseDataActivity.this, inspectionData, "", new SendDataHandler.GetCallBackResult() {
                    @Override
                    public void onSuccess(JSONObject loginResponse) {
                        if (loginResponse.optBoolean("Result")) {
                            updateSyncList(FEnumerations.E_SYNC_DIGITAL_UPLOAD_TABLE, FEnumerations.E_SYNC_SUCCESS_STATUS);
                            handleToFinalizeSync();
                        } else {
                            updateSyncList(FEnumerations.E_SYNC_DIGITAL_UPLOAD_TABLE, FEnumerations.E_SYNC_FAILED_STATUS);
                            String msg = loginResponse.optString("Message");
                            FslLog.e(TAG, FEnumerations.E_SYNC_DIGITAL_UPLOAD_TABLE + " \n" + msg);
                        }
                    }

                    @Override
                    public void onError(VolleyError volleyError) {
                        updateSyncList(FEnumerations.E_SYNC_DIGITAL_UPLOAD_TABLE, FEnumerations.E_SYNC_FAILED_STATUS);
                        requestVolleyError(volleyError);
                        handleToFinalizeSync();//added by shekhar
                    }
                });
            } else {
                updateSyncList(FEnumerations.E_SYNC_DIGITAL_UPLOAD_TABLE, FEnumerations.E_SYNC_SUCCESS_STATUS);
                handleToFinalizeSync();
                FslLog.d(TAG, " NO DATA FOUNDED FROM  SYNC_DIGITAL_UPLOAD_TABLE TO SYNC..........");
            }
        }
    }

    private void handleToFinalizeSync() {
        if (idsListForSync != null && idsListForSync.size() > 0) {
            Map<String, Object> fiTables = SendDataHandler.getSelectedInspectionIdsData(ChooseDataActivity.this, idsListForSync);
            if (fiTables != null && fiTables.size() > 0) {
                updateSyncList(FEnumerations.E_SYNC_FINALIZE_TABLE, FEnumerations.E_SYNC_IN_PROCESS_STATUS);
                final Map<String, Object> inspectionData = new HashMap<String, Object>();
                final Map<String, Object> data = new HashMap<String, Object>();
                data.put("Data", new JSONObject(fiTables));
                data.put("ImageFiles", "");
                data.put("EnclosureFiles", "");
                data.put("TestReportFiles", "");
                data.put("Result", true);
                data.put("Message", "");
                data.put("MsgDetail", "");
                inspectionData.put("InspectionData", data);
                FslLog.d(TAG, " FINALIZE sync .........................................\n\n");
                SendDataHandler.sendData(ChooseDataActivity.this, inspectionData, "", new SendDataHandler.GetCallBackResult() {
                    @Override
                    public void onSuccess(JSONObject loginResponse) {
                        if (loginResponse.optBoolean("Result")) {
                            updateSyncList(FEnumerations.E_SYNC_FINALIZE_TABLE, FEnumerations.E_SYNC_SUCCESS_STATUS);
                            if (idsListForSync != null && idsListForSync.size() > 0) {
                                for (int i = 0; i < idsListForSync.size(); i++) {
                                    ItemInspectionDetailHandler.updateFinalSync(ChooseDataActivity.this, idsListForSync.get(i));
                                }
                            }
//                            handleToSyncOtherDel();//comment by shekhar
                            handleToPkgAppearanceSync();
                        } else {
                            String msg = loginResponse.optString("Message");
                            FslLog.e(TAG, FEnumerations.E_SYNC_FINALIZE_TABLE + " \n" + msg);
                            updateSyncList(FEnumerations.E_SYNC_FINALIZE_TABLE, FEnumerations.E_SYNC_FAILED_STATUS);
                            handleToPkgAppearanceSync();
                        }
                    }

                    @Override
                    public void onError(VolleyError volleyError) {
                        updateSyncList(FEnumerations.E_SYNC_FINALIZE_TABLE, FEnumerations.E_SYNC_FAILED_STATUS);
                        handleToPkgAppearanceSync();
                        requestVolleyError(volleyError);
                    }
                });
            } else {
                FslLog.d(TAG, " NO DATA FOUNDED FROM  SYNC_DIGITAL_UPLOAD_TABLE TO SYNC..........");
            }
        }
    }

    private void handleToPkgAppearanceSync() {
        if (idsListForSync != null && idsListForSync.size() > 0) {
            Map<String, Object> digiTables = SendDataHandler.getPkgAppearanceData(ChooseDataActivity.this, idsListForSync);
            if (digiTables != null && digiTables.size() > 0) {
                updateSyncList(FEnumerations.E_SYNC_PKG_APPEARANCE, FEnumerations.E_SYNC_IN_PROCESS_STATUS);
                final Map<String, Object> inspectionData = new HashMap<String, Object>();
                final Map<String, Object> data = new HashMap<String, Object>();
                data.put("Data", new JSONObject(digiTables));
                data.put("ImageFiles", "");
                data.put("EnclosureFiles", "");
                data.put("TestReportFiles", "");
                data.put("Result", true);
                data.put("Message", "");
                data.put("MsgDetail", "");
                inspectionData.put("InspectionData", data);
                FslLog.d(TAG, " PACKAGING APPEARANCE sync .........................................\n\n");
                SendDataHandler.sendData(ChooseDataActivity.this, inspectionData, "", new SendDataHandler.GetCallBackResult() {
                    @Override
                    public void onSuccess(JSONObject loginResponse) {
                        if (loginResponse.optBoolean("Result")) {
                            updateSyncList(FEnumerations.E_SYNC_PKG_APPEARANCE, FEnumerations.E_SYNC_SUCCESS_STATUS);
                            handleToOnSiteBarcodeSync();
                        } else {
                            updateSyncList(FEnumerations.E_SYNC_PKG_APPEARANCE, FEnumerations.E_SYNC_FAILED_STATUS);
                            String msg = loginResponse.optString("Message");
                            FslLog.e(TAG, FEnumerations.E_SYNC_PKG_APPEARANCE + " \n" + msg);
                            handleToOnSiteBarcodeSync();
                        }
                    }

                    @Override
                    public void onError(VolleyError volleyError) {
                        updateSyncList(FEnumerations.E_SYNC_PKG_APPEARANCE, FEnumerations.E_SYNC_FAILED_STATUS);
                        requestVolleyError(volleyError);
                        handleToOnSiteBarcodeSync();
                    }
                });
            } else {
                updateSyncList(FEnumerations.E_SYNC_PKG_APPEARANCE, FEnumerations.E_SYNC_SUCCESS_STATUS);
                handleToOnSiteBarcodeSync();
                FslLog.d(TAG, " NO DATA FOUNDED FROM  PACKAGING APPEARANCE TO SYNC..........");
            }
        }
    }

    private void handleToOnSiteBarcodeSync() {
        if (idsListForSync != null && idsListForSync.size() > 0) {
            Map<String, Object> digiTables = SendDataHandler.getOnSiteDataData(ChooseDataActivity.this, idsListForSync);
            if (digiTables != null && digiTables.size() > 0) {
                updateSyncList(FEnumerations.E_SYNC_ON_SITE, FEnumerations.E_SYNC_IN_PROCESS_STATUS);
                final Map<String, Object> inspectionData = new HashMap<String, Object>();
                final Map<String, Object> data = new HashMap<String, Object>();
                data.put("Data", new JSONObject(digiTables));
                data.put("ImageFiles", "");
                data.put("EnclosureFiles", "");
                data.put("TestReportFiles", "");
                data.put("Result", true);
                data.put("Message", "");
                data.put("MsgDetail", "");
                inspectionData.put("InspectionData", data);
                FslLog.d(TAG, " ON SITE sync .........................................\n\n");
                SendDataHandler.sendData(ChooseDataActivity.this, inspectionData, "", new SendDataHandler.GetCallBackResult() {
                    @Override
                    public void onSuccess(JSONObject loginResponse) {
                        if (loginResponse.optBoolean("Result")) {
                            updateSyncList(FEnumerations.E_SYNC_ON_SITE, FEnumerations.E_SYNC_SUCCESS_STATUS);
                            handleToSampleCollectedSync();
                        } else {
                            updateSyncList(FEnumerations.E_SYNC_ON_SITE, FEnumerations.E_SYNC_FAILED_STATUS);
                            String msg = loginResponse.optString("Message");
                            FslLog.e(TAG, FEnumerations.E_SYNC_ON_SITE + " \n" + msg);
                            handleToSampleCollectedSync();
                        }
                    }

                    @Override
                    public void onError(VolleyError volleyError) {
                        updateSyncList(FEnumerations.E_SYNC_ON_SITE, FEnumerations.E_SYNC_FAILED_STATUS);
                        requestVolleyError(volleyError);
                    }
                });
            } else {
                updateSyncList(FEnumerations.E_SYNC_ON_SITE, FEnumerations.E_SYNC_SUCCESS_STATUS);
                handleToSampleCollectedSync();
                FslLog.d(TAG, " NO DATA FOUNDED FROM  ON SITE TO SYNC..........");
            }
        }
    }

    private void handleToSampleCollectedSync() {
        if (idsListForSync != null && idsListForSync.size() > 0) {
            Map<String, Object> digiTables = SendDataHandler.getSampleCollectedData(ChooseDataActivity.this, idsListForSync);
            if (digiTables != null && digiTables.size() > 0) {
                updateSyncList(FEnumerations.E_SYNC_SAMPLE_COLLECTED, FEnumerations.E_SYNC_IN_PROCESS_STATUS);
                final Map<String, Object> inspectionData = new HashMap<String, Object>();
                final Map<String, Object> data = new HashMap<String, Object>();
                data.put("Data", new JSONObject(digiTables));
                data.put("ImageFiles", "");
                data.put("EnclosureFiles", "");
                data.put("TestReportFiles", "");
                data.put("Result", true);
                data.put("Message", "");
                data.put("MsgDetail", "");
                inspectionData.put("InspectionData", data);
                FslLog.d(TAG, " SAMPLE COLLECTED sync .........................................\n\n");
                SendDataHandler.sendData(ChooseDataActivity.this, inspectionData, "", new SendDataHandler.GetCallBackResult() {
                    @Override
                    public void onSuccess(JSONObject loginResponse) {
                        if (loginResponse.optBoolean("Result")) {
                            updateSyncList(FEnumerations.E_SYNC_SAMPLE_COLLECTED, FEnumerations.E_SYNC_SUCCESS_STATUS);
                            handleToSyncOtherDel();//added by shekhar
                        } else {
                            updateSyncList(FEnumerations.E_SYNC_SAMPLE_COLLECTED, FEnumerations.E_SYNC_FAILED_STATUS);
                            String msg = loginResponse.optString("Message");
                            FslLog.e(TAG, FEnumerations.E_SYNC_ON_SITE + " \n" + msg);
                        }
                    }

                    @Override
                    public void onError(VolleyError volleyError) {
                        updateSyncList(FEnumerations.E_SYNC_ON_SITE, FEnumerations.E_SYNC_FAILED_STATUS);
                        requestVolleyError(volleyError);
                    }
                });
            } else {
                updateSyncList(FEnumerations.E_SYNC_ON_SITE, FEnumerations.E_SYNC_SUCCESS_STATUS);
                FslLog.d(TAG, " NO DATA FOUNDED FROM  SAMPLE COLLECTED TO SYNC..........");
            }
        }
    }


    private void handleToSyncOtherDel() {
        if (listedSyncIds != null && listedSyncIds.size() > 0 && idsListForSync.size() > 0) {
            syncDoneProcess.add(idsListForSync.get(0));
            listedSyncIds.remove(idsListForSync.get(0));
            idsListForSync.clear();
            handleToSendData();
        }

    }

    private void updateSyncList(String eSyncHeaderTable, int sts) {
        new Handler(Looper.getMainLooper()).post(() ->
                updateUI(eSyncHeaderTable,sts)
        );
    }
    private void updateUI(String eSyncHeaderTable, int sts){
        if (statusModalList != null && statusModalList.size() > 0) {
            int pos = -1;
            boolean isFound = false;
            for (int i = 0; i < statusModalList.size(); i++) {
                if (statusModalList.get(i).tableName.equals(eSyncHeaderTable)) {
                    pos = i;
                    isFound = true;
                    break;
                }
            }
            if (isFound && pos != -1) {
                statusModalList.get(pos).status = sts;
                if (syncStatusAdaptor != null) {
                    syncStatusAdaptor.notifyDataSetChanged();
                }
            }
        }
    }


    private void updateSyncTitleList(String eSyncHeaderTable, String sts) {
        if (statusModalList != null && statusModalList.size() > 0) {
            int pos = -1;
            boolean isFound = false;
            for (int i = 0; i < statusModalList.size(); i++) {
                if (statusModalList.get(i).tableName.equals(eSyncHeaderTable)) {
                    pos = i;
                    isFound = true;
                    break;
                }
            }
            if (isFound && pos != -1) {
                statusModalList.get(pos).title = sts;
                if (syncStatusAdaptor != null) {
                    syncStatusAdaptor.notifyDataSetChanged();
                }
            }

        }

    }

    private void handleToSynsStyle() {
        handleToStyleSync();
    }

    private void handleToStyleSync() {
        if (idsListForSync != null && idsListForSync.size() > 0) {
            Map<String, Object> param = StyleHandler.getStyleData(ChooseDataActivity.this, idsListForSync);
            if (param != null && param.size() > 0) {
                updateSyncList(FEnumerations.E_SYNC_STYLE, FEnumerations.E_SYNC_IN_PROCESS_STATUS);
                final Map<String, Object> inspectionData = new HashMap<String, Object>();
                final Map<String, Object> data = new HashMap<String, Object>();
                data.put("Data", new JSONObject(param));
                data.put("ImageFiles", "");
                data.put("EnclosureFiles", "");
                data.put("TestReportFiles", "");
                data.put("Result", true);
                data.put("Message", "");
                data.put("MsgDetail", "");
                inspectionData.put("InspectionData", data);

                FslLog.d(TAG, " style sync .........................................\n\n");

                SendDataHandler.sendStyleData(ChooseDataActivity.this, inspectionData, "", new SendDataHandler.GetCallBackResult() {
                    @Override
                    public void onSuccess(JSONObject loginResponse) {
                        if (loginResponse.optBoolean("Result")) {
                            updateSyncList(FEnumerations.E_SYNC_STYLE, FEnumerations.E_SYNC_SUCCESS_STATUS);
                            handleToStyleImageSync();
                        } else {
                            updateSyncList(FEnumerations.E_SYNC_STYLE, FEnumerations.E_SYNC_FAILED_STATUS);
                            String msg = loginResponse.optString("Message");
                            FslLog.e(TAG, FEnumerations.E_SYNC_STYLE + " \n" + msg);
                        }
                    }

                    @Override
                    public void onError(VolleyError volleyError) {
                        updateSyncList(FEnumerations.E_SYNC_STYLE, FEnumerations.E_SYNC_FAILED_STATUS);
                        requestVolleyError(volleyError);
                    }
                });
            } else {
                FslLog.d(TAG, " NO DATA FOUNDED FROM STYLE TO SYNC..........");
            }
        } else {
            FslLog.d(TAG, "No Selected style to sync...........");
        }

    }

    private void handleToStyleImageSync() {
        if (idsListForSync != null && idsListForSync.size() > 0) {
            Map<String, Object> imgTables = StyleHandler.getStyleImagesTableData(ChooseDataActivity.this, idsListForSync);
            if (imgTables != null && imgTables.size() > 0) {
                updateSyncList(FEnumerations.E_SYNC_IMAGES_TABLE, FEnumerations.E_SYNC_IN_PROCESS_STATUS);
                final Map<String, Object> inspectionData = new HashMap<String, Object>();
                final Map<String, Object> data = new HashMap<String, Object>();
                data.put("Data", new JSONObject(imgTables));
                data.put("ImageFiles", "");
                data.put("EnclosureFiles", "");
                data.put("TestReportFiles", "");
                data.put("Result", true);
                data.put("Message", "");
                data.put("MsgDetail", "");
                inspectionData.put("InspectionData", data);

                FslLog.d(TAG, " IMAGES sync .........................................\n\n");

                SendDataHandler.sendData(ChooseDataActivity.this, inspectionData, "", new SendDataHandler.GetCallBackResult() {
                    @Override
                    public void onSuccess(JSONObject loginResponse) {
                        if (loginResponse.optBoolean("Result")) {
//                            updateSyncList(FEnumerations.E_SYNC_IMAGES_TABLE, FEnumerations.E_SYNC_SUCCESS_STATUS);
                            handleToStyleSingleImageSync();

                        } else {
                            updateSyncList(FEnumerations.E_SYNC_IMAGES_TABLE, FEnumerations.E_SYNC_FAILED_STATUS);
                            String msg = loginResponse.optString("Message");
                            FslLog.e(TAG, FEnumerations.E_SYNC_IMAGES_TABLE + " \n" + msg);

//                            handleToWorkmanShipSync();
                        }
                    }

                    @Override
                    public void onError(VolleyError volleyError) {
                        updateSyncList(FEnumerations.E_SYNC_IMAGES_TABLE, FEnumerations.E_SYNC_FAILED_STATUS);
                        requestVolleyError(volleyError);
                    }
                });
            } else {
                handleToStyleSingleImageSync();
                FslLog.d(TAG, " NO DATA FOUNDED FROM Style IMAGES TO SYNC..........");
            }
        }
    }

    private void handleToStyleSingleImageSync() {

        updateSyncList(FEnumerations.E_SYNC_IMAGES_TABLE, FEnumerations.E_SYNC_IN_PROCESS_STATUS);
//                final Map<String, Object> inspectionData = new HashMap<String, Object>();
        final Map<String, Object> data = new HashMap<String, Object>();
//                data.put("Data", new JSONObject(imgTables));
        List<ImageModal> imagesList = StyleHandler.getStyleImagesForSync(ChooseDataActivity.this, idsListForSync);
        List<String> syncedList = new ArrayList<>();
        if (imagesList != null && imagesList.size() > 0) {
            data.put("FileType", FEnumerations.E_SEND_FILE_IMAGE_STYLE);
            for (int i = 0; i < imagesList.size(); i++) {
                ImageModal imageModal = imagesList.get(i);
                Map<String, Object> jsonObj = new HashMap<String, Object>();
//                    for (int j = 0; j < cursor.getColumnCount(); j++) {
//                    jsonObj.put("FileName", cursor.getString(cursor.getColumnIndex("ImageName")));
                jsonObj.put("pRowID", imageModal.pRowID);
                jsonObj.put("QRPOItemHdrID", imageModal.QRPOItemHdrID);
//                jsonObj.put("recUser", imageModal.U);//contentValues.put("recUser", userId);
                jsonObj.put("Length", imageModal.Length);
                jsonObj.put("FileName", imageModal.FileName);

                String base64 = null;
                BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                Bitmap bitmap = BitmapFactory.decodeFile(imageModal.fileContent, bmOptions);
                if (bitmap != null)
                    base64 = ImgToStringConverter.BitMapToStringT(BitmapUtil.CustomScaleBitmap(bitmap)); //ImgToStringConverter.encodeFileToBase64Binary(imageModal.fileContent);
                //                        byte[] compressed = GzipUtil.compress(base64);
//                        String result = Base64.encodeToString(compressed, Base64.DEFAULT);

                if (bitmap != null) {
                    FslLog.d(TAG, " recycle bitmap.. ");
                    bitmap.recycle();
                    bitmap = null;
                    System.gc();
                }

                jsonObj.put("fileContent", base64);

                data.put("FileData", new JSONObject(jsonObj));
//                    mWorkerThread.queueTask(data, i);
                SendDataHandler sendDataHandler = new SendDataHandler();
                sendDataHandler.handleStyleRequest(data, i, new SendDataHandler.GetCallBackSendResult() {
                    @Override
                    public void onSuccess(JSONObject result, int postion) {
                        if (result.has("Result")) {
                            String pRowID = result.optString("Result");
                            syncedList.add(pRowID);
                            StyleHandler.updateImageToSync(ChooseDataActivity.this, pRowID);
                            String str = syncedList.size() + "/" + imagesList.size();
                            updateSyncTitleList(FEnumerations.E_SYNC_IMAGES_TABLE, str);

                        }
                        FslLog.d(TAG, " response of sending image  " + result + " pos " + postion);
                        if (imagesList.size() == syncedList.size()) {
                            updateSyncList(FEnumerations.E_SYNC_IMAGES_TABLE, FEnumerations.E_SYNC_SUCCESS_STATUS);
                            handleToFinalizeStyleSync();

                        }
                    }

                    @Override
                    public void onError(VolleyError volleyError, int postion) {
                        FslLog.d(TAG, " error of sending image  " + volleyError + " pos " + postion);
                        updateSyncList(FEnumerations.E_SYNC_IMAGES_TABLE, FEnumerations.E_SYNC_FAILED_STATUS);
                        String str = syncedList.size() + "/" + imagesList.size() + " Internet disconnect please try again.";
                        updateSyncTitleList(FEnumerations.E_SYNC_IMAGES_TABLE, str);
                        requestVolleyError(volleyError);
                    }
                });
            }
        } else {
            updateSyncList(FEnumerations.E_SYNC_IMAGES_TABLE, FEnumerations.E_SYNC_SUCCESS_STATUS);
            FslLog.d(TAG, " NO DATA FOUNDED FROM style IMAGES TO SYNC..........");
            handleToFinalizeStyleSync();
        }

    }

    private void handleToFinalizeStyleSync() {
        if (idsListForSync != null && idsListForSync.size() > 0) {
            Map<String, Object> fiTables = SendDataHandler.getSelectedStyleIdsData(ChooseDataActivity.this, idsListForSync);
            if (fiTables != null && fiTables.size() > 0) {
                updateSyncList(FEnumerations.E_SYNC_FINALIZE_TABLE, FEnumerations.E_SYNC_IN_PROCESS_STATUS);
                final Map<String, Object> inspectionData = new HashMap<String, Object>();
                final Map<String, Object> data = new HashMap<String, Object>();
                data.put("Data", new JSONObject(fiTables));
                data.put("ImageFiles", "");
                data.put("EnclosureFiles", "");
                data.put("TestReportFiles", "");
                data.put("Result", true);
                data.put("Message", "");
                data.put("MsgDetail", "");
                inspectionData.put("InspectionData", data);
                FslLog.d(TAG, " FINALIZE sync .........................................\n\n");
                SendDataHandler.sendData(ChooseDataActivity.this, inspectionData, "", new SendDataHandler.GetCallBackResult() {
                    @Override
                    public void onSuccess(JSONObject loginResponse) {
                        if (loginResponse.optBoolean("Result")) {
                            updateSyncList(FEnumerations.E_SYNC_FINALIZE_TABLE, FEnumerations.E_SYNC_SUCCESS_STATUS);
                            updateStyleWhenSyncToServer(idsListForSync);

                        } else {
                            String msg = loginResponse.optString("Message");
                            FslLog.e(TAG, FEnumerations.E_SYNC_FINALIZE_TABLE + " \n" + msg);
                            updateSyncList(FEnumerations.E_SYNC_FINALIZE_TABLE, FEnumerations.E_SYNC_FAILED_STATUS);
                        }
                    }

                    @Override
                    public void onError(VolleyError volleyError) {
                        updateSyncList(FEnumerations.E_SYNC_FINALIZE_TABLE, FEnumerations.E_SYNC_FAILED_STATUS);
                        requestVolleyError(volleyError);
                    }
                });
            } else {
                FslLog.d(TAG, " NO DATA FOUNDED FROM   TO SYNC..........");
            }
        }
    }

    private void updateStyleWhenSyncToServer(List<String> idsListForSync) {
        StyleHandler.updateHologramSyncToServer(ChooseDataActivity.this, idsListForSync);
    }


    private void handleListToSync() {

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(ChooseDataActivity.this,
                DividerItemDecoration.VERTICAL));

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(ChooseDataActivity.this);
        recyclerView.setLayoutManager(mLayoutManager);


        statusModalList = new ArrayList<>();


    }

    //make sync status list for inspection
    private void viewStatusListOfSync() {
        statusModalList.clear();
//        List<String> tableList = SyncDataHandler.getTablesToSyncList(this);
        List<String> tableList = new ArrayList<>();
        final List<SysData22Modal> statusList = SysData22Handler.getSysData22ListAccToID(this,
                FEnumerations.PKG_APP_GEN_ID, FEnumerations.PKG_APP_MAIN_ID);
        for (int i = 0; i < statusList.size(); i++) {
            if (statusList.get(i).SubID.equals(FEnumerations.PKG_APP_SUB_ID)) {
                if (statusList.get(i).numVal2.equals("0")) {
                    tableList = SyncDataHandler.getTablesToSyncList(this);
                } else {
                    tableList = SyncDataHandler.getTablesToSyncListWithoutPkgApp(this);
                }
            }
        }
        if (tableList != null && tableList.size() > 0) {
            for (int i = 0; i < tableList.size(); i++) {
                StatusModal statusModal = new StatusModal();
                statusModal.tableName = tableList.get(i).trim();
                statusModalList.add(statusModal);
            }
        }
        if (syncStatusAdaptor == null) {
            syncStatusAdaptor = new SyncStatusAdaptor(ChooseDataActivity.this
                    , statusModalList);
            recyclerView.setAdapter(syncStatusAdaptor);
        } else {
            syncStatusAdaptor.notifyDataSetChanged();
        }
    }

    //make sync status list  style
    private void viewStatusListOfSyncOfStyleHologram() {
        statusModalList.clear();
        List<String> tableList = SyncDataHandler.getTablesToSyncStyleList(this);
        if (tableList != null && tableList.size() > 0) {
            for (int i = 0; i < tableList.size(); i++) {
                StatusModal statusModal = new StatusModal();
                statusModal.tableName = tableList.get(i).trim();
                statusModalList.add(statusModal);
            }
        }


        if (syncStatusAdaptor == null) {
            syncStatusAdaptor = new SyncStatusAdaptor(ChooseDataActivity.this
                    , statusModalList);
            recyclerView.setAdapter(syncStatusAdaptor);
        } else {
            syncStatusAdaptor.notifyDataSetChanged();
        }
    }

    private void getDataFromServerHandle() {
        showProgressDialog("Loading...");
        GetDataHandler.getData(ChooseDataActivity.this, new GetDataHandler.GetCallBackResult() {
            @Override
            public void onSuccess(JSONObject loginResponse) {

                hideDialog();
                Toast toast = ToastCompat.makeText(ChooseDataActivity.this, "Get sync data successfully", Toast.LENGTH_SHORT);
                GenUtils.safeToastShow(TAG, ChooseDataActivity.this, toast);
                startActivity(new Intent(ChooseDataActivity.this, InspectionListActivity.class));
            }

            @Override
            public void onError(VolleyError volleyError) {
                hideDialog();
            }
        });
    }

    private void handleChangePassword() {
        Intent intent = new Intent(ChooseDataActivity.this, ChangePassword.class);
        intent.putExtra("request", FEnumerations.REQUEST_FOR_CHANGE_PASSWORD);
        startActivity(intent);

    }


    private void handleLogOut() {
        UserSession userSession = new UserSession(ChooseDataActivity.this);
        userSession.clearDataOnLogOut();
        Intent intentLogOut = new Intent(ChooseDataActivity.this, LogInActivity.class);
        intentLogOut.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
                | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intentLogOut);
    }

    @Override
    public void onBackPressed() {
        /*if (this.findViewById(R.id.mainFlatFrofileContainer).getVisibility() == View.GONE) {
            Fragment notifyFragment = getSupportFragmentManager().findFragmentById(R.id.frameContainer);

            try {
                if (notifyFragment != null) {
                    SocietyLog.d(TAG, "FragmentToRemove: " + notifyFragment);
                    getSupportFragmentManager().beginTransaction().remove(notifyFragment).commitAllowingStateLoss();
                }
            } catch (Exception e) {
                SocietyLog.d(TAG, "Exception at time remove fragment of notify");
                e.printStackTrace();
            }


            findViewById(R.id.mainFlatFrofileContainer).setVisibility(View.VISIBLE);
            hideActiobIcon();
            return;
        }*/
        super.onBackPressed();
    }

    private void showActionIcon() {
        this.getSupportActionBar().setTitle("Notification");
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Drawable backArrow = getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp);
        backArrow.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP);
        this.getSupportActionBar().setHomeAsUpIndicator(backArrow);

    }

    private void hideActiobIcon() {
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        if (userSession == null)
            userSession = new UserSession(ChooseDataActivity.this);

        this.getSupportActionBar().setTitle("Welcome " + userSession.getLoginData().userProfileName);

    }


    private void showProgressDialog(String message) {

        if (loadingDialog == null)
            loadingDialog = new ProgressDialog(ChooseDataActivity.this);
//        loadingDialog.setTitle("Please wait");
        loadingDialog.setMessage(message);
        if (loadingDialog != null && !loadingDialog.isShowing()) {
//            loadingDialog.show();
            GenUtils.safeProgressDialogShow(TAG, ChooseDataActivity.this, loadingDialog);
        }

    }

    private void hideDialog() {
        if (loadingDialog != null && loadingDialog.isIndeterminate() && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.getDataSubmit:

                handleGetdata();

                break;
            case R.id.getStyleDataSubmit:
                handleGetStyledata();
                break;
            case R.id.sendStyleSubmit:
                mViewType = FEnumerations.E_VIEW_ONLY_SEND;
                handleHologramView();
                break;
            case R.id.sendSubmit:
                mViewType = FEnumerations.E_VIEW_SEND_AND_SYNC;
                handleView();
//                startActivity(new Intent(ChooseDataActivity.this, InspectionListActivity.class));


                break;

        }
    }

    private void handleGetStyledata() {
//        startActivity(new Intent(ChooseDataActivity.this, StyleList.class));

        showProgressDialog("Loading...");
        GetDataHandler.getStyleData(ChooseDataActivity.this, new GetDataHandler.GetCallBackResult() {
            @Override
            public void onSuccess(JSONObject loginResponse) {
                Toast toast = ToastCompat.makeText(ChooseDataActivity.this, "Get sync data successfully", Toast.LENGTH_SHORT);
                GenUtils.safeToastShow(TAG, ChooseDataActivity.this, toast);
                startActivity(new Intent(ChooseDataActivity.this, StyleList.class));
                hideDialog();

            }

            @Override
            public void onError(VolleyError volleyError) {
                hideDialog();
                boolean lIsErrorHandled = VolleyErrorHandler.errorHandler(ChooseDataActivity.this, volleyError);
                if (!lIsErrorHandled) {
                    if (volleyError == null
                            || volleyError.networkResponse == null) {
                        FslLog.d(TAG, " Could Not CAUGHT EXCEPTION ...................... bcos of volleyError AND networkResponse IS NULL..");
                    } else if (volleyError.networkResponse.statusCode == FStatusCode.HTTP_STATUS_CODE_INTERNAL_SERVER_ERROR) {
                        VolleyErrorHandler.handleInternalError(ChooseDataActivity.this, volleyError);
                        return;
                    } else if (volleyError.networkResponse.statusCode == 400) {
                        VolleyErrorHandler.handleInternaBadError(ChooseDataActivity.this, volleyError);
                    } else if (volleyError.networkResponse.statusCode == 404) {
                        NetworkUtil.showServerErrorForStageDialog(ChooseDataActivity.this, "Bad Request");
                    }

                }
            }
        });
    }

    private void handleGetdata() {
        if (!chkGetInspectionData.isChecked() && !chkGetMasterData.isChecked()) {
//            startActivity(new Intent(ChooseDataActivity.this, InspectionListActivity.class));
            //getDataFromServerHandle();
        } else if (chkGetMasterData.isChecked()) {
//            disableGetDataButton();
        } else if (chkGetInspectionData.isChecked()) {
//            enableGetDataButton();
        }
        getDataFromServerHandle();
    }

    /*private void disableGetDataButton() {
        getDataProgressBar.setVisibility(View.VISIBLE);

        getDataSubmit.setText("fetching...");
        getDataSubmit.setEnabled(false);
    }*/

   /* private void enableGetDataButton() {
        getDataProgressBar.setVisibility(View.GONE);
        getDataSubmit.setText("GET DATA");
        getDataSubmit.setEnabled(true);
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        switch (item.getItemId()) {
            case android.R.id.home:
                gotoBackPress();

        }

        return false;
    }

    private void requestVolleyError(VolleyError volleyError) {
        boolean lIsErrorHandled = VolleyErrorHandler.errorHandler(ChooseDataActivity.this, volleyError);
        if (!lIsErrorHandled) {
            if (volleyError == null
                    || volleyError.networkResponse == null) {
                FslLog.d(TAG, " Could Not CAUGHT EXCEPTION ...................... bcos of volleyError AND networkResponse IS NULL..");
            } else if (volleyError.networkResponse.statusCode == FStatusCode.HTTP_STATUS_CODE_INTERNAL_SERVER_ERROR) {
                VolleyErrorHandler.handleInternalError(ChooseDataActivity.this, volleyError);
                return;
            }

        }
    }

    private void updateStatusUI() {
        txtSyncCountSection.setText(syncDoneProcess.size() + "/" + totalIdsToSync);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("type", mViewType);
        outState.putInt("mSyncViewType", mSyncViewType);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            gotoBackPress();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void gotoBackPress() {
        setResult(RESULT_OK);
        finish();
    }
}

