package com.inspection;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.General.DefectMasterModal;
import com.android.volley.VolleyError;
import com.buyereasefsl.AddWorkManShip;
import com.buyereasefsl.ItemInspectionDetailHandler;
import com.buyereasefsl.R;
import com.constant.AppConfig;
import com.constant.FClientConfig;
import com.constant.FClientConstants;
import com.constant.FEnumerations;
import com.dashboard.ChooseDataActivity;
import com.data.UserSession;
import com.podetail.POItemDtl;
import com.podetail.POItemDtl1;
import com.podetail.POItemDtlHandler;
import com.podetail.POItemListActivity;
import com.sync.GetDataHandler;
import com.util.CustomListViewDialog;
import com.util.FslLog;
import com.util.GenUtils;
import com.util.ItemDataAdapter;
import com.util.SetInitiateStaticVariable;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.drakeet.support.toast.ToastCompat;

public class InspectionListActivity extends AppCompatActivity implements InspectionListAdaptor.OnItemClickListener,
        ItemDataAdapter.RecyclerViewItemClickListener{
    String TAG = "InspectionListActivity";
    UserSession userSession;
    static boolean active = false;
    TextView txtNotificationCount;
    RecyclerView recyclerView;
    List<InspectionModal> inspectionModalList;
    InspectionListAdaptor inspectionListAdaptor;
    ProgressDialog loadingDialog;
    SearchView searchView;

    boolean IsDefaultOpenList = true;
    //added by shekhr
    ArrayList<DefectMasterModal> defectMasterModalArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        searchView = (SearchView) findViewById(R.id.search_by_del_id);
        searchView.setQueryHint("Search By DEL Id");
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Inspections");
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        FslLog.d(TAG, " current date formate " + AppConfig.getCurrntDateDDMMyy());
//        findViewById(R.id.layoutInspectionList).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(InspectionListActivity.this, POItemListActivity.class));
//            }
//        });


        TextView companyName = (TextView) findViewById(R.id.companyName);
        String st = GenUtils.truncate(new UserSession(InspectionListActivity.this).getCompanyName(), FClientConfig.COMPANY_TRUNC_LENGTH);
        companyName.setText(st);
        SetInitiateStaticVariable.setInitiateStaticVariable(InspectionListActivity.this);


        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(InspectionListActivity.this);
//        GridLayoutManager mLayoutManager = new GridLayoutManager(InspectionListActivity.this, 2);

        recyclerView.setLayoutManager(mLayoutManager);
//        DividerItemDecoration dividerItemDecoration1 = new DividerItemDecoration(recyclerView.getContext(),
//                DividerItemDecoration.VERTICAL);
//        recyclerView.addItemDecoration(dividerItemDecoration1);
//        DividerItemDecoration dividerItemDecoration2 = new DividerItemDecoration(recyclerView.getContext(),
//                DividerItemDecoration.HORIZONTAL);
//        recyclerView.addItemDecoration(dividerItemDecoration2);
        inspectionModalList = new ArrayList<>();


  /*      recyclerView.addOnItemTouchListener(new InspectionListAdaptor.RecyclerTouchListener(getApplicationContext()
                , recyclerView, new InspectionListAdaptor.ClickListener() {
            @Override
            public void onClick(View view, int position) {

//                Intent intent = new Intent(DashboardActivity.this, ComplianceStatusActivity.class);
//                intent.putExtra("result", resultResponse.toString());
//                startActivity(intent);

                if (inspectionModalList != null && position != -1) {

//                    getDetail(inspectionModalList.get(position).name);
                    Intent intent = new Intent(InspectionListActivity.this, POItemListActivity.class);
                    intent.putExtra("detail", serialize(inspectionModalList.get(position)));
                    startActivityForResult(intent, 1);
                }


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
*/

        getLocalList(null);

//        String sUri = "content://com.android.externalstorage.documents/tree/29F4-5A7D%3AAndroid%2Fdata";
//        Uri myUri = Uri.parse(sUri);
//        File BaseNewFile = new File(myUri.getPath());//3/storage/3C70-E899
//        File newFile = new File(BaseNewFile.getAbsolutePath(), "/DEL");
//        if (!newFile.exists()) {
//            newFile.mkdirs();
//            FslLog.d(TAG, "DEL new folder created ");
//        } else {
//            FslLog.d(TAG, "DEL  folder is already created.. ");
//        }

//        getApplicationContext().grantUriPermission(getCallingPackage(),
//                myUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);

      /*  Intent  intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        GenUtils.grantAllUriPermissions(this, intent, myUri);
//        this.grantUriPermission("com.buyereasefsl", myUri, intent);

        ContentResolver contentResolver = getContentResolver();
//        contentResolver.releasePersistableUriPermission(myUri,Intent.FLAG_GRANT_READ_URI_PERMISSION);
        Uri docUri = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            docUri = DocumentsContract.buildDocumentUriUsingTree(myUri,
                    DocumentsContract.getTreeDocumentId(myUri));
            try {
                Uri directoryUri = DocumentsContract
                        .createDocument(contentResolver, docUri, DocumentsContract.Document.MIME_TYPE_DIR, "DEL");
                FslLog.d(TAG, " file directory is created" + directoryUri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }*/
        defectMasterModalArrayList = POItemDtlHandler.getDefectMasterList(this);
        if (defectMasterModalArrayList.size() == 0) {
            getDataFromServerHandle();
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (IsDefaultOpenList) {
                    Log.e(TAG, "search query" + query);
                    FslLog.d(TAG, "search query" + query);
                    getLocalList(query);
                } else {
                    getSyncedList(query);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //    adapter.getFilter().filter(newText);
                if(newText.isEmpty()){
                    getLocalList(null);
                }
                return false;
            }
        });

        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setIconified(false);
            }
        });
    }

    private void getDataFromServerHandle() {
        showProgressDialog("Defect Master Loading...");
        GetDataHandler.getDefectMasterData(InspectionListActivity.this, new GetDataHandler.GetCallBackResult() {
            @Override
            public void onSuccess(JSONObject loginResponse) {

                hideDialog();
                Toast toast = ToastCompat.makeText(InspectionListActivity.this, "Defect Master data sync successfully", Toast.LENGTH_SHORT);
                GenUtils.safeToastShow(TAG, InspectionListActivity.this, toast);
            }

            @Override
            public void onError(VolleyError volleyError) {
                hideDialog();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        switch (item.getItemId()) {
            case android.R.id.home:

                finish();

                break;

            case R.id.checkedAll:
                handleAllSync();
                return true;
            case R.id.unCheckedAll:
                handleUnChecked();
                return true;
            case R.id.submit:
                handleSubmitToSync();

                return true;
            case R.id.submitRemove:
                handleRemoveInspection();
                return true;
            case R.id.openInspection:
                handleToOpenInspection();
                return true;
            case R.id.closeInspection:
                handleToClosedInspection();
                return true;

        }

        return false;
    }

    private void handleToClosedInspection() {
        getSyncedList(null);

    }

    private void handleToOpenInspection() {

        getLocalList(null);
    }


    private void handleRemoveInspection() {

        GenUtils.forConfirmationAlertDialog(InspectionListActivity.this,
                FClientConstants.TEXT_BUTTON_YES,
                FClientConstants.TEXT_BUTTON_NO
                , null, "Are you sure. Do you want to delete inspection", new GenUtils.AlertDialogClickListener() {
                    @Override
                    public void onPositiveButton() {
                        showProgressDialog("Deleting...");
                        if (inspectionModalList != null && inspectionModalList.size() > 0) {
                            for (int i = 0; i < inspectionModalList.size(); i++) {
                                if (inspectionModalList.get(i).IsCheckedToSync) {
                                    handleDeleteInspection(inspectionModalList.get(i));
                                }
                            }
                            getLocalList(null);
                            hideDialog();
                        }

                    }

                    @Override
                    public void onNegativeButton() {

                    }
                });


    }


    private void handleSubmitToSync() {

        if (inspectionModalList != null && inspectionModalList.size() > 0) {
            ArrayList<String> idsList = new ArrayList<>();
            for (int i = 0; i < inspectionModalList.size(); i++) {
                if (inspectionModalList.get(i).IsCheckedToSync) {
                    idsList.add(inspectionModalList.get(i).pRowID);
                    if (!IsDefaultOpenList) {
                        ItemInspectionDetailHandler.updateImageToMakeAgainNotSync(InspectionListActivity.this, inspectionModalList.get(i).pRowID);
                    }
                }
            }
            if (idsList != null && idsList.size() > 0) {
                GenUtils.forConfirmationAlertDialog(InspectionListActivity.this,
                        FClientConstants.TEXT_BUTTON_YES,
                        FClientConstants.TEXT_BUTTON_NO
                        , null, "Are you sure. Do you want to sync " + idsList.size() + " inspection", new GenUtils.AlertDialogClickListener() {
                            @Override
                            public void onPositiveButton() {

                                for (int i = 0; i < idsList.size(); i++) {
                                    if (!IsDefaultOpenList) {
                                        ItemInspectionDetailHandler.updateImageToMakeAgainNotSync(InspectionListActivity.this, idsList.get(i));
                                    }
                                }

                                Intent intent = new Intent(InspectionListActivity.this, ChooseDataActivity.class);
                                intent.putExtra("type", FEnumerations.E_VIEW_ONLY_SYNC);
                                intent.putExtra("mSyncViewType", FEnumerations.E_VIEW_TYPE_SYNC);
                                intent.putStringArrayListExtra("list", idsList);
                                startActivityForResult(intent, 11);
                            }

                            @Override
                            public void onNegativeButton() {

                            }
                        });


            }
        }


    }

    private void handleDeleteInspection(InspectionModal inspectionModal) {

        InspectionListHandler.deleteInspectionRecordFromAllTAble(InspectionListActivity.this, inspectionModal.pRowID);

    }

    private void handleAllSync() {
        if (inspectionModalList != null && inspectionModalList.size() > 0) {
            for (int i = 0; i < inspectionModalList.size(); i++) {
                inspectionModalList.get(i).IsCheckedToSync = true;
            }
            if (inspectionListAdaptor != null)
                inspectionListAdaptor.notifyDataSetChanged();
        }


    }

    private void handleUnChecked() {
        if (inspectionModalList != null && inspectionModalList.size() > 0) {
            for (int i = 0; i < inspectionModalList.size(); i++) {
                inspectionModalList.get(i).IsCheckedToSync = false;
            }
            if (inspectionListAdaptor != null)
                inspectionListAdaptor.notifyDataSetChanged();
        }


    }

    private void getLocalList(String searchStr) {
        if (InspectionListHandler.getInspectionList(this, searchStr).size() > 0) {
            IsDefaultOpenList = true;

            if (inspectionModalList == null)
                inspectionModalList = new ArrayList<>();
            else inspectionModalList.clear();
            inspectionModalList.addAll(InspectionListHandler.getInspectionList(this, searchStr));
            setAdaptor();
        } else {
            Toast toast = ToastCompat.makeText(InspectionListActivity.this, "Inspection did not find", Toast.LENGTH_SHORT);
            GenUtils.safeToastShow(TAG, InspectionListActivity.this, toast);
        }
    }

    private void getSyncedList(String searchStr) {
        if (InspectionListHandler.getSyncedInspectionList(this, searchStr).size() > 0) {
            IsDefaultOpenList = false;
            if (inspectionModalList == null)
                inspectionModalList = new ArrayList<>();
            else inspectionModalList.clear();
            inspectionModalList.addAll(InspectionListHandler.getSyncedInspectionList(this, searchStr));
            setAdaptor();
        } else {
            Toast toast = ToastCompat.makeText(InspectionListActivity.this, "Inspection did not find", Toast.LENGTH_SHORT);
            GenUtils.safeToastShow(TAG, InspectionListActivity.this, toast);
        }

    }

    private void setAdaptor() {
        if (inspectionListAdaptor == null) {
            inspectionListAdaptor = new InspectionListAdaptor(InspectionListActivity.this, recyclerView
                    , inspectionModalList, this);
            recyclerView.setAdapter(inspectionListAdaptor);
        } else {
            inspectionListAdaptor.notifyDataSetChanged();
        }


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 || requestCode == 11) {
            FslLog.d(TAG, "IsDefaultOpenList=" + IsDefaultOpenList);
            Log.d(TAG, "IsDefaultOpenList=" + IsDefaultOpenList);
            if (IsDefaultOpenList) {
                getLocalList(null);
            } else {
                getSyncedList(null);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.insp_menu, menu);//Menu Resource, Menu


        return true;
    }

    private void showProgressDialog(String message) {

        if (loadingDialog == null)
            loadingDialog = new ProgressDialog(InspectionListActivity.this);
//        loadingDialog.setTitle("Please wait");
        loadingDialog.setMessage(message);
        if (loadingDialog != null && !loadingDialog.isShowing()) {
//            loadingDialog.show();
            GenUtils.safeProgressDialogShow(TAG, InspectionListActivity.this, loadingDialog);
        }

    }

    private void hideDialog() {
        if (loadingDialog != null && loadingDialog.isIndeterminate() && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }

    }

    @Override
    public void onItemClick(InspectionModal item) {
        showProgressDialog("Loading...");
        Intent intent = new Intent(InspectionListActivity.this, POItemListActivity.class);
        intent.putExtra("detail", GenUtils.serializeInspectionModal(item));
        hideDialog();
        startActivityForResult(intent, 101);
    }

    CustomListViewDialog customDialog;
    @Override
    public void onClickItem(View view, String ProwId) {
        if(view.getId()==R.id.tvMore){
            List<String> itemIdList;
            itemIdList = InspectionListHandler.getItemList(this,ProwId);
            Log.e("InspectionListActivity","itemlist"+itemIdList);
            ItemDataAdapter dataAdapter = new ItemDataAdapter(itemIdList, this);
            customDialog = new CustomListViewDialog(this, dataAdapter);
            customDialog.show();
            customDialog.setCanceledOnTouchOutside(false);
        }
    }
    @Override
    public void clickOnItem(String data) {
        if (customDialog != null){
            customDialog.dismiss();
        }
    }
}
