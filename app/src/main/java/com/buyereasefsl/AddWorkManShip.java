package com.buyereasefsl;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.General.DefectMasterModal;
import com.General.OnSIteModal;
import com.android.volley.VolleyError;
import com.constant.FEnumerations;
import com.dashboard.ChooseDataActivity;
import com.inspection.InspectionListActivity;
import com.podetail.POItemDtl;
import com.podetail.POItemDtlHandler;
import com.sync.GetDataHandler;
import com.util.FslLog;
import com.util.GenUtils;
import com.util.MultipleImageHandler;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.drakeet.support.toast.ToastCompat;

/**
 * Created by ADMIN on 11/17/2017.
 */

public class AddWorkManShip extends AppCompatActivity implements View.OnClickListener {

    int pos;
    String data;
    AutoCompleteTextView autoCompleteTextView1,editCode;
    EditText  editCritical, editMajor, editMinor, editComments;
    LinearLayout workmanshipImage1Container, workmanshipImage3Container;
    //ImageView workmanshipImage1, workmanshipImage3, workmanshipImage2;
    //    WorkManShipModel mWorkManShipModel;
    WorkManShipModel workManShipModel = new WorkManShipModel();
    int mViewType;
    TextView imgCount;
    String TAG = "AddWorkManShip";
    POItemDtl poItemDtl;
    String returnPRowID;
    ProgressDialog loadingDialog;
    ArrayList<DefectMasterModal> defectMasterModalArrayList=new ArrayList<>();
    ArrayList<String> defectMasterModalArrayListStr=new ArrayList<>();
    ArrayList<String> defectMasterModalArrayListCodeStr=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_workman_ship_form);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add Workmanship");

        editCode = (AutoCompleteTextView) findViewById(R.id.editCode);
        autoCompleteTextView1 = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
        editCritical = (EditText) findViewById(R.id.editCritical);
        editMajor = (EditText) findViewById(R.id.editMajor);
        editMinor = (EditText) findViewById(R.id.editMinor);
        editComments = (EditText) findViewById(R.id.editComments);
        imgCount = (TextView) findViewById(R.id.imgCount);
        workmanshipImage1Container = (LinearLayout) findViewById(R.id.workmanshipImage1Container);

        workmanshipImage3Container = (LinearLayout) findViewById(R.id.workmanshipImage3Container);


//        workmanshipImage1 = (ImageView) findViewById(R.id.workmanshipImage1);
//        workmanshipImage3 = (ImageView) findViewById(R.id.workmanshipImage3);
//        workmanshipImage2 = (ImageView) findViewById(R.id.workmanshipImage2);

//        pos = getIntent().getIntExtra("pos", -1);
        data = getIntent().getStringExtra("detail");
        mViewType = getIntent().getIntExtra("type", 0);
        poItemDtl = GenUtils.deSerializePOItemModal(getIntent().getStringExtra("podetail"));
//        updateAutoComplete();
        if (data != null) {
            updateUI(data);
        }


        workmanshipImage3Container.setOnClickListener(this);
        updateImageCount();

//        if(ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                == PackageManager.PERMISSION_DENIED ) {
//            //if you dont have required permissions ask for it (only required for API 23+)
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                    9);
//
//
//            onRequestPermissionsResult(9,
//                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, grantResults);
//        }

        workmanshipImage1Container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (workManShipModel.listImages != null && workManShipModel.listImages.size() > 0) {
                    ArrayList<String> list = new ArrayList<>();
                    for (int i = 0; i < workManShipModel.listImages.size(); i++) {
                        list.add(workManShipModel.listImages.get(i).selectedPicPath);
                    }
                    if (list.size() > 0) {
                        Bundle bundle = new Bundle();
                        bundle.putStringArrayList("galleryModels", list);
                        bundle.putInt("position", 0);
//                    FragmentManager fragmentManager =activity.
                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        SlideshowDialogFragment newFragment = SlideshowDialogFragment.newInstance();
                        newFragment.setArguments(bundle);
                        newFragment.show(ft, "slideshow");
                    }
                }
            }
        });
        defectMasterModalArrayList =  POItemDtlHandler.getDefectMasterList(this);
        /*if(defectMasterModalArrayList.size()==0){
            getDataFromServerHandle();
        }else {
            updateAutoComplete();
        }*/
        if(defectMasterModalArrayList.size()>0){
            updateAutoComplete();
            updateAutoCompleteCode();
        }
    }

    private void getDataFromServerHandle() {
        showProgressDialog("Loading...");
        GetDataHandler.getDefectMasterData(AddWorkManShip.this, new GetDataHandler.GetCallBackResult() {
            @Override
            public void onSuccess(JSONObject loginResponse) {

                hideDialog();
                Toast toast = ToastCompat.makeText(AddWorkManShip.this, "Get sync data successfully", Toast.LENGTH_SHORT);
                GenUtils.safeToastShow(TAG, AddWorkManShip.this, toast);
                getDefectMasterList();
            }

            @Override
            public void onError(VolleyError volleyError) {
                hideDialog();
            }
        });

    }
    private void getDefectMasterList(){
        defectMasterModalArrayList =  POItemDtlHandler.getDefectMasterList(this);
        if(defectMasterModalArrayList.size()>0){
            // add defect name from main list add in string list i.e defectMasterModalArrayListStr
            updateAutoComplete();
            updateAutoCompleteCode();
           /* for(int i=0;i<defectMasterModalArrayList.size();i++){
                defectMasterModalArrayListStr.add(defectMasterModalArrayList.get(i).DefectName);
            }

            if(defectMasterModalArrayListStr.size()>0){
                updateAutoComplete();
            }*/
        }
    }

    private void showProgressDialog(String message) {

        if (loadingDialog == null)
            loadingDialog = new ProgressDialog(AddWorkManShip.this);
//        loadingDialog.setTitle("Please wait");
        loadingDialog.setMessage(message);
        if (loadingDialog != null && !loadingDialog.isShowing()) {
//            loadingDialog.show();
            GenUtils.safeProgressDialogShow(TAG, AddWorkManShip.this, loadingDialog);
        }

    }

    private void hideDialog() {
        if (loadingDialog != null && loadingDialog.isIndeterminate() && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }

    }

    private void updateAutoComplete() {
//        final List<DefectMasterModal> defectMasterList = POItemDtlHandler.getDefectMasterList(this);
//        Log.e("autoCompleteTextView1","autoCompleteTextView1 DefectMasterList size="+defectMasterList.size());
//        List<String> list = ItemInspectionDetailHandler.getWorkmanShipDescriptionList(AddWorkManShip.this);

        for(int i=0;i<defectMasterModalArrayList.size();i++){
            defectMasterModalArrayListStr.add(defectMasterModalArrayList.get(i).DefectName);
        }
        Log.e("defectsize","defect size="+defectMasterModalArrayListStr.size());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, defectMasterModalArrayListStr);
        autoCompleteTextView1.setAdapter(adapter);
        autoCompleteTextView1.setThreshold(1);
        autoCompleteTextView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView<?> parent, View view, int position, long id) {
                Log.e("autoCompleteTextView1","autoCompleteTextView1 click="+defectMasterModalArrayListStr.get(position));
                String selectedItem = (String) parent.getItemAtPosition(position);
                for(int i=0;i<defectMasterModalArrayList.size();i++){
                    if(defectMasterModalArrayList.get(i).DefectName.equals(selectedItem)){
                        editCode.setText(defectMasterModalArrayList.get(i).Code);
                        break;
                    }
                }
            }
        });
    }

    private void updateAutoCompleteCode() {
//        final List<DefectMasterModal> defectMasterList = POItemDtlHandler.getDefectMasterList(this);
//        Log.e("autoCompleteTextView1","autoCompleteTextView1 DefectMasterList size="+defectMasterList.size());
//        List<String> list = ItemInspectionDetailHandler.getWorkmanShipDescriptionList(AddWorkManShip.this);

        for(int i=0;i<defectMasterModalArrayList.size();i++){
            defectMasterModalArrayListCodeStr.add(defectMasterModalArrayList.get(i).Code);
        }
        Log.e("defectsize","defect size="+defectMasterModalArrayListCodeStr.size());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, defectMasterModalArrayListCodeStr);
        editCode.setAdapter(adapter);
        editCode.setThreshold(1);
        editCode.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView<?> parent, View view, int position, long id) {
                Log.e("autoCompleteTextView1","autoCompleteTextView1 click="+defectMasterModalArrayListCodeStr.get(position));
//                Object item = parent.getItemAtPosition(position);
                String selectedItem = (String) parent.getItemAtPosition(position);
                for(int i=0;i<defectMasterModalArrayList.size();i++){
                    if(defectMasterModalArrayList.get(i).Code.equals(selectedItem)){
                        autoCompleteTextView1.setText(defectMasterModalArrayList.get(i).DefectName);
                        break;
                    }
                }
//
            }
        });
    }

    private void updateUI(String data) {

        workManShipModel = GenUtils.deSerializeWorkmanship(data);
        editCode.setText(workManShipModel.Code);
        autoCompleteTextView1.setText(workManShipModel.Description);
        editCritical.setText(workManShipModel.Critical + "");
        editMajor.setText(workManShipModel.Major + "");
        editMinor.setText(workManShipModel.Minor + "");
        editComments.setText(workManShipModel.Comments);

//        findViewById(R.id.imageContainer).setVisibility(View.GONE);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_workmanship, menu);//Menu Resource, Menu


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        switch (item.getItemId()) {
            case android.R.id.home:

                finish();
                return true;

            case R.id.submit:
                if (mViewType == FEnumerations.REQUEST_FOR_EDIT_WORKMANSHIP) {
                    onUpdateHandler();
                } else if (mViewType == FEnumerations.REQUEST_FOR_ADD_WORKMANSHIP) {
                    onSubmit();
                }
                handleSaveToGenerateId();
                ToastCompat.makeText(this, "Saved successfully", Toast.LENGTH_SHORT).show();
//                setRsult();
                return true;


        }

        return false;
    }

    private void onUpdateHandler() {
        String _code = editCode.getText().toString();
        String _description = autoCompleteTextView1.getText().toString();
        String _critical = editCritical.getText().toString();
        String _major = editMajor.getText().toString();
        String _minor = editMinor.getText().toString();
        String _comment = editComments.getText().toString();
        if (!_code.equals(workManShipModel.Code)) {
            workManShipModel.Code = _code;
        }
        if (!_description.equals(workManShipModel.Description)) {
            workManShipModel.Description = _description;
        }
        if (TextUtils.isEmpty(_critical) || _critical.equalsIgnoreCase("")) {
            workManShipModel.Critical = 0;
        } else {
            if (!_critical.equals(workManShipModel.Critical)) {
                workManShipModel.Critical = Integer.parseInt(_critical);
            }
        }
        if (TextUtils.isEmpty(_major) || _major.equalsIgnoreCase("")) {
            workManShipModel.Major = 0;
        } else {
            if (!_major.equals(workManShipModel.Major)) {
                workManShipModel.Major = Integer.parseInt(_major);
            }
        }
        if (TextUtils.isEmpty(_minor) || _minor.equalsIgnoreCase("")) {
            workManShipModel.Minor = 0;
        } else {
            if (!_minor.equals(workManShipModel.Minor)) {
                workManShipModel.Minor = Integer.parseInt(_minor);
            }
        }
        if (!_comment.equals(workManShipModel.Comments)) {
            workManShipModel.Comments = _comment;
        }


    }

    private void setRsult() {

        Intent intent = new Intent();
//        intent.putExtra("pos", pos);
        intent.putExtra("detail", GenUtils.serializeWorkmanship(workManShipModel));
        setResult(RESULT_OK, intent);
        finish();

    }

    private void onSubmit() {

        String _code = editCode.getText().toString();
        String _description = autoCompleteTextView1.getText().toString();
        String _critical = editCritical.getText().toString();
        String _major = editMajor.getText().toString();
        String _minor = editMinor.getText().toString();
        String _comment = editComments.getText().toString();

      /*  if (TextUtils.isEmpty(_code)) {
            editCode.setError("Code");
            editCode.requestFocus();
            return;
        }*/
        if (TextUtils.isEmpty(_description)) {
            autoCompleteTextView1.setError("Description");
            autoCompleteTextView1.requestFocus();
            return;
        }
//        if (TextUtils.isEmpty(_critical)) {
//            editCritical.setError("Critical");
//            editCritical.requestFocus();
//            return;
//        }
//        if (TextUtils.isEmpty(_major)) {
//
//        }
//        if (TextUtils.isEmpty(_minor)) {
//
//        }
//        if (TextUtils.isEmpty(_comment)) {
//
//        }
        if (TextUtils.isEmpty(workManShipModel.pRowID) || "null".equalsIgnoreCase(workManShipModel.pRowID))
            workManShipModel.pRowID = ItemInspectionDetailHandler.GeneratePK(AddWorkManShip.this, FEnumerations.E_TABLE_NAME_AuditBatchDetails);
        workManShipModel.Code = _code;
        workManShipModel.Description = _description;
        if (!TextUtils.isEmpty(_critical))
            workManShipModel.Critical = Integer.parseInt(_critical);
        if (!TextUtils.isEmpty(_major))
            workManShipModel.Major = Integer.parseInt(_major);
        if (!TextUtils.isEmpty(_minor))
            workManShipModel.Minor = Integer.parseInt(_minor);
        workManShipModel.Comments = _comment;


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
          /*  case R.id.workmanshipImage1Container:
//                String valueToReturn = R.id.workmanshipImage1 + "";
//                PhotoHandler.showDialog(AddWorkManShip.this, PhotoHandler.DOCUMENT, valueToReturn);

                break;

*/
            case R.id.workmanshipImage3Container:

                boolean status = handleSaveToGenerateId();
                if (status)
                    ImageHandler();
//                getDialog();
                break;
        }
    }

    private boolean handleSaveToGenerateId() {
        String _description = autoCompleteTextView1.getText().toString();
        if (TextUtils.isEmpty(_description)) {
            autoCompleteTextView1.setError("Description");
            autoCompleteTextView1.requestFocus();
            return false;
        } else {
            autoCompleteTextView1.setError(null);
        }

        workManShipModel.Description = _description;

        returnPRowID = ItemInspectionDetailHandler.updateWorkmanShip(this,
                poItemDtl.QRHdrID,
                poItemDtl.QRPOItemHdrID,
                poItemDtl.QrItemID,
                workManShipModel);
        workManShipModel.pRowID = returnPRowID;
        return true;
    }

    private void ImageHandler() {
        String valueToReturn2 = R.id.workmanshipImage3 + "";
        MultipleImageHandler.showDialog(AddWorkManShip.this, MultipleImageHandler.DOCUMENT, valueToReturn2);

    }

    private void ImageSelectHandler() {
        String valueToReturn2 = R.id.workmanshipImage3 + "";
        MultipleImageHandler.showDialog(AddWorkManShip.this, MultipleImageHandler.DOCUMENT, valueToReturn2, MultipleImageHandler.pickerViewer);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        FslLog.d("AddWorkManShip", "onActivityResult requestCode - " + requestCode + " resultCode - " + resultCode);
        Log.d("AddWorkManShip", "onActivityResult requestCode - " + requestCode + " resultCode - " + resultCode);

        if (requestCode == 5) {
            if (data != null) {
                String path = data.getDataString();
                FslLog.d("path", "path " + path);
                Log.d("AddWorkManShip", "path " + path);
            }
        } else {

            MultipleImageHandler.onActivityResult(AddWorkManShip.this,
                    requestCode, resultCode, data, new MultipleImageHandler.GetBitmap() {
                        @Override
                        public void onGetBitamp(Bitmap serverBitmap,
                                                ArrayList<String> imagePathArrayList,
                                                String valueReturned) {
                            int resId = Integer.valueOf(valueReturned);
                            if (resId == R.id.workmanshipImage3) {
//                            workmanshipImage1.setImageBitmap(deviceBitmap);
                                if (imagePathArrayList != null && imagePathArrayList.size() > 0) {
                                    for (int i = 0; i < imagePathArrayList.size(); i++) {
                                        DigitalsUploadModal digitalsUploadModal = new DigitalsUploadModal();
                                        digitalsUploadModal.selectedPicPath = imagePathArrayList.get(i);
                                        workManShipModel.listImages.add(digitalsUploadModal);
                                        handleToSaveDigital(digitalsUploadModal);

                                    }
                                    updateImageCount();
                                    ImageSelectHandler();
                                }

                            }
                        }
                    });
        }
        if (requestCode == 0
                && resultCode == RESULT_OK) {
        }
    }

   /* private void addAsDigitalImageFromWorkmanShip() {
        if (workManShipModel != null
                && workManShipModel.listImages != null
                && workManShipModel.listImages.size() > 0) {
            for (int i = 0; i < workManShipModel.listImages.size(); i++) {

                handleToSaveDigital(workManShipModel.listImages.get(i));
            }

        }

    }*/

    private void handleToSaveDigital(DigitalsUploadModal digitalsUploadModal) {

        if (TextUtils.isEmpty(digitalsUploadModal.title)) {
            digitalsUploadModal.title = workManShipModel.Description;
        }
        if (TextUtils.isEmpty(digitalsUploadModal.ImageExtn)
                || digitalsUploadModal.ImageExtn.equals("null")) {
            digitalsUploadModal.ImageExtn = FEnumerations.E_IMAGE_EXTN;
        }
        if (TextUtils.isEmpty(digitalsUploadModal.pRowID)
                || digitalsUploadModal.pRowID.equals("null")) {
            digitalsUploadModal.pRowID = ItemInspectionDetailHandler.GeneratePK(this, FEnumerations.E_TABLE_NAME_QRPOItemDtl_Image);
        }
        if (TextUtils.isEmpty(returnPRowID) || "null".equalsIgnoreCase(returnPRowID)) {
            handleSaveToGenerateId();
        }

        String digitalsRowId = updateDBWithImage(digitalsUploadModal);

        if (!TextUtils.isEmpty(digitalsRowId) && !TextUtils.isEmpty(returnPRowID)) {
            List<WorkManShipModel> dtlList = ItemInspectionDetailHandler.getDigitalsFromWorkmanShip(this, returnPRowID);
            if (dtlList != null && dtlList.size() > 0) {
                WorkManShipModel mWo = dtlList.get(0);
                if (!TextUtils.isEmpty(mWo.Digitals) && !mWo.Digitals.equals("null")) {
                    mWo.Digitals = mWo.Digitals + ", " + digitalsRowId;
                } else {
                    mWo.Digitals = digitalsRowId;
                }
                workManShipModel.Digitals = mWo.Digitals;
                ItemInspectionDetailHandler.updateDigitalFromWorkmanShip(this, mWo);
            }
        }

    }

    private String updateDBWithImage(DigitalsUploadModal digitalsUploadModal) {
        return ItemInspectionDetailHandler.updateImageFromQualityParameter(this, poItemDtl.QRHdrID, poItemDtl.QRPOItemHdrID,returnPRowID, digitalsUploadModal);

    }

    private void updateImageCount() {

        Log.d("AddWorkManShip", "Image added count " + workManShipModel.listImages.size());
        if (workManShipModel.listImages != null && workManShipModel.listImages.size() > 0) {
            imgCount.setText(workManShipModel.listImages.size() + "");
            workmanshipImage1Container.setVisibility(View.VISIBLE);
        } else {
            workmanshipImage1Container.setVisibility(View.GONE);
        }
    }

    public void getDialog() {
//        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
//        startActivityForResult(intent, REQUEST_CODE_OPEN_DIRECTORY);

        Intent chooser = new Intent(Intent.ACTION_PICK);
        Uri uri = Uri.parse(Environment.getDownloadCacheDirectory().getPath().toString());
        chooser.addCategory(Intent.CATEGORY_OPENABLE);
        chooser.setDataAndType(uri, "*/*");
// startActivity(chooser);
        try {
            startActivityForResult(chooser, 5);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast toast = ToastCompat.makeText(this, "Please install a File Manager.",
                    Toast.LENGTH_SHORT);
            GenUtils.safeToastShow(TAG, AddWorkManShip.this, toast);
        }
    }
}
