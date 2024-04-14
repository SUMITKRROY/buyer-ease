package com.buyereasefsl;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import com.General.GeneralMasterHandler;
import com.General.GeneralModel;
import com.General.SampleModal;
import com.constant.FEnumerations;
import com.podetail.POItemDtl;
import com.podetail.POItemDtlHandler;
import com.util.FslLog;
import com.util.GenUtils;
import com.util.MultipleImageHandler;

import java.util.ArrayList;
import java.util.List;

import me.drakeet.support.toast.ToastCompat;

/**
 * Created by ADMIN on 12/25/2017.
 */

public class AddItemMeasurement extends AppCompatActivity implements View.OnClickListener {

    int pos;

    EditText editLength, editHeight, editWidth, editDescription;
    Spinner spinnerSampleSize, spinnerOverAllResult;
    //    LinearLayout workmanshipImage1Container, workmanshipImage2Container, workmanshipImage3Container;
//    ImageView workmanshipImage1, workmanshipImage3, workmanshipImage2;

    ItemMeasurementModal itemMeasurementModal = new ItemMeasurementModal();
    int mViewType;
    RelativeLayout findingContainer;
    POItemDtl poItemDtl;
    LinearLayout workmanshipImage1Container, workmanshipImage3Container;
    TextView imgCount;
    String TAG = "AddItemMeasurement";
    String returnPRowID;
    String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item_measurement);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add Measurement");

        editLength = (EditText) findViewById(R.id.editLength);
        editDescription = (EditText) findViewById(R.id.editDescription);
        editHeight = (EditText) findViewById(R.id.editHeight);
        editWidth = (EditText) findViewById(R.id.editWidth);

        spinnerSampleSize = (Spinner) findViewById(R.id.spinnerSampleSize);
        spinnerOverAllResult = (Spinner) findViewById(R.id.spinnerOverAllResult);
        findingContainer = (RelativeLayout) findViewById(R.id.findingContainer);
        workmanshipImage1Container = (LinearLayout) findViewById(R.id.workmanshipImage1Container);
        workmanshipImage3Container = (LinearLayout) findViewById(R.id.workmanshipImage3Container);
        imgCount = (TextView) findViewById(R.id.imgCount);
        findingContainer.setOnClickListener(this);
        if (savedInstanceState != null) {
            returnPRowID = savedInstanceState.getString("returnPRowID", returnPRowID);
            data = savedInstanceState.getString("detail");
            mViewType = savedInstanceState.getInt("type", 0);
            poItemDtl = GenUtils.deSerializePOItemModal(savedInstanceState.getString("poItemDtl"));
        } else {
            data = getIntent().getStringExtra("detail");
            mViewType = getIntent().getIntExtra("type", 0);
            poItemDtl = GenUtils.deSerializePOItemModal(getIntent().getStringExtra("poItemDtl"));
            if (data != null) {
                updateUI(data);
            }
        }
        handleSpinner();
        workmanshipImage1Container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemMeasurementModal.listImages != null && itemMeasurementModal.listImages.size() > 0) {

                    ArrayList<String> list = new ArrayList<>();
                    for (int i = 0; i < itemMeasurementModal.listImages.size(); i++) {
                        list.add(itemMeasurementModal.listImages.get(i).selectedPicPath);
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

        workmanshipImage3Container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ImageHandler();
            }
        });
    }

    private void handleSpinner() {

        List<String> statsList = new ArrayList<>();
        final List<GeneralModel> overAllResultStatusList = GeneralMasterHandler.getGeneralList(this, FEnumerations.E_OVERALL_RESULT_STATUS_GEN_ID);
        if (overAllResultStatusList != null && overAllResultStatusList.size() > 0) {
//            itemMeasurementModal.InspectionResultID = overAllResultStatusList.get(0).MainID;
            int selectedPos = 0;
            for (int i = 0; i < overAllResultStatusList.size(); i++) {
                statsList.add(overAllResultStatusList.get(i).MainDescr);
                if (overAllResultStatusList.get(i).pGenRowID.equals(itemMeasurementModal.InspectionResultID)) {
                    selectedPos = i;
                }
            }

            //Creating the ArrayAdapter instance having the country list
            ArrayAdapter resultArrary = new ArrayAdapter(this, R.layout.dialog_list_item, statsList);
            resultArrary.setDropDownViewResource(R.layout.dialog_list_item);
            //Setting the ArrayAdapter data on the Spinner
            spinnerOverAllResult.setAdapter(resultArrary);
            spinnerOverAllResult.setSelection(selectedPos);
            spinnerOverAllResult.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    itemMeasurementModal.InspectionResultID = overAllResultStatusList.get(i).pGenRowID;
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }

        final List<SampleModal> sampleModals = POItemDtlHandler.getSampleSizeList(this);
        List<String> sampleList = new ArrayList<>();
        if (sampleModals != null && sampleModals.size() > 0) {
//            itemMeasurementModal.SampleSizeID = sampleModals.get(0).SampleCode;
//            itemMeasurementModal.SampleSizeValue = sampleModals.get(0).SampleVal;
            int selectedPos = 0;
            for (int i = 0; i < sampleModals.size(); i++) {
                sampleList.add(sampleModals.get(i).MainDescr + "(" + sampleModals.get(i).SampleVal + ")");
                if (sampleModals.get(i).SampleCode.equals(itemMeasurementModal.SampleSizeID)) {
                    selectedPos = i;
                }
            }

            //Creating the ArrayAdapter instance having the country list
            ArrayAdapter sampleArrary = new ArrayAdapter(this, R.layout.dialog_list_item, sampleList);
            sampleArrary.setDropDownViewResource(R.layout.dialog_list_item);
            //Setting the ArrayAdapter data on the Spinner
            spinnerSampleSize.setAdapter(sampleArrary);
            spinnerSampleSize.setSelection(selectedPos);
            if (selectedPos == 0) {
                itemMeasurementModal.SampleSizeID = sampleModals.get(0).SampleCode;
                itemMeasurementModal.SampleSizeValue = sampleModals.get(0).SampleVal;
            }
            spinnerSampleSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    itemMeasurementModal.SampleSizeID = sampleModals.get(i).SampleCode;
                    itemMeasurementModal.SampleSizeValue = sampleModals.get(i).SampleVal;
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }

    }

    private void updateUI(String data) {

        itemMeasurementModal = GenUtils.deSerializeItemMeasurement(data);
        editLength.setText(itemMeasurementModal.Dim_length + "");
        editHeight.setText(itemMeasurementModal.Dim_Height + "");
        editWidth.setText(itemMeasurementModal.Dim_Width + "");
        editDescription.setText(itemMeasurementModal.ItemMeasurementDescr);
        updateImageCount();
//        editMinor.setText(mWorkManShipModel.Minor);
//        editComments.setText(mWorkManShipModel.Comments);
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

                if (mViewType == FEnumerations.REQUEST_FOR_EDIT_ITEM_MEASUREMENT) {
                    onUpdateHandler();
                } else if (mViewType == FEnumerations.REQUEST_FOR_ADD_ITEM_MEASUREMENT) {
                    onSubmit();
                }
                if (!TextUtils.isEmpty(itemMeasurementModal.ItemMeasurementDescr)) { /* itemMeasurementModal.Dim_length > 0 || itemMeasurementModal.Dim_Height > 0 || itemMeasurementModal.Dim_Width > 0*/
                    handleToupdateItemMeasurement();
//                    ItemInspectionDetailHandler.updateItemMeasurement(this, itemMeasurementModal, poItemDtl);
//                    addAsDigitalImageFromItemmeasurement();

                } else {
                    editDescription.setError("Fill description");
                    editDescription.requestFocus();
                    Toast toast = ToastCompat.makeText(AddItemMeasurement.this, "Please fill description", Toast.LENGTH_SHORT);
                    GenUtils.safeToastShow(TAG, AddItemMeasurement.this, toast);
                }

                return true;


        }

        return false;
    }

    private void handleToupdateItemMeasurement() {
        if (TextUtils.isEmpty(itemMeasurementModal.pRowID) || itemMeasurementModal.pRowID.equalsIgnoreCase("null")) {
            itemMeasurementModal.pRowID = ItemInspectionDetailHandler.GeneratePK(AddItemMeasurement.this, FEnumerations.E_TABLE_NAME_ItemMeasurement);
            FslLog.d(TAG, " Generate pRowID of item measurement bcos add new item");
        } else {
            FslLog.d(TAG, " No need to Generate pRowID of item measurement bcos update item");
        }

        returnPRowID = ItemInspectionDetailHandler.updateItemMeasurement(this, itemMeasurementModal, poItemDtl);
        itemMeasurementModal.pRowID = returnPRowID;
    }

    private void onUpdateHandler() {
        String _l = editLength.getText().toString();
        String _description = editDescription.getText().toString();
        String _h = editHeight.getText().toString();
        String _w = editWidth.getText().toString();
        if (!TextUtils.isEmpty(_l)) {
            itemMeasurementModal.Dim_length = Double.parseDouble(_l);
            itemMeasurementModal.OLD_Length = Double.parseDouble(_l);
        }
        if (!TextUtils.isEmpty(_h)) {
            itemMeasurementModal.Dim_Height = Double.parseDouble(_h);
            itemMeasurementModal.OLD_Height = Double.parseDouble(_h);
        }
        if (!TextUtils.isEmpty(_w)) {
            itemMeasurementModal.Dim_Width = Double.parseDouble(_w);
            itemMeasurementModal.OLD_Width = Double.parseDouble(_w);
        }
        itemMeasurementModal.ItemMeasurementDescr = _description;

    }

    private void onSubmit() {

        String _l = editLength.getText().toString();
        String _description = editDescription.getText().toString();
        String _h = editHeight.getText().toString();
        String _w = editWidth.getText().toString();

        if (!TextUtils.isEmpty(_l))
            itemMeasurementModal.Dim_length = Double.parseDouble(_l);
        if (!TextUtils.isEmpty(_h))
            itemMeasurementModal.Dim_Height = Double.parseDouble(_h);
        if (!TextUtils.isEmpty(_w))
            itemMeasurementModal.Dim_Width = Double.parseDouble(_w);
        itemMeasurementModal.ItemMeasurementDescr = _description;
        Toast toast = ToastCompat.makeText(AddItemMeasurement.this, "Save Successful", Toast.LENGTH_SHORT);
        GenUtils.safeToastShow(TAG, AddItemMeasurement.this, toast);

    }

    private void setResult() {


        Intent intent = new Intent();
        intent.putExtra("detail", GenUtils.serializeItemMeasurementModal(itemMeasurementModal));
        setResult(RESULT_OK, intent);
        finish();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        FslLog.d("AddWorkManShip", "onActivityResult requestCode - " + requestCode + " resultCode - " + resultCode);
        if (requestCode == FEnumerations.REQUEST_FOR_ADD_ITEM_MEASUREMENT_FINDING && resultCode == RESULT_OK) {
            if (data != null) {
                ItemMeasurementModal mItemMeasurementModal = GenUtils.deSerializeItemMeasurement(data.getStringExtra("detail"));
                if (mItemMeasurementModal != null) {
                    itemMeasurementModal = mItemMeasurementModal;
                }
            }
        } else {

            MultipleImageHandler.onActivityResult(AddItemMeasurement.this,
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
                                        itemMeasurementModal.listImages.add(digitalsUploadModal);
                                        handleToUpdateImage(digitalsUploadModal);
                                    }
                                    updateImageCount();
                                    ImageSelectHandler();
                                }

                            }
                        }
                    });
        }
    }

    private void updateImageCount() {
        if (itemMeasurementModal.listImages != null && itemMeasurementModal.listImages.size() > 0) {
            imgCount.setText(itemMeasurementModal.listImages.size() + "");
            workmanshipImage1Container.setVisibility(View.VISIBLE);
        } else {
            workmanshipImage1Container.setVisibility(View.GONE);
        }
    }


    @Override
    public void onClick(View view) {

        if (mViewType == FEnumerations.REQUEST_FOR_EDIT_ITEM_MEASUREMENT) {
            onUpdateHandler();
        } else if (mViewType == FEnumerations.REQUEST_FOR_ADD_ITEM_MEASUREMENT) {
            onSubmit();
        }
        if (itemMeasurementModal.Dim_length > 0 || itemMeasurementModal.Dim_Height > 0 || itemMeasurementModal.Dim_Width > 0) {
            if (!TextUtils.isEmpty(itemMeasurementModal.SampleSizeValue) && !itemMeasurementModal.SampleSizeValue.equals("null")) {

//                ItemInspectionDetailHandler.updateItemMeasurement(this, itemMeasurementModal, poItemDtl);

                handleToupdateItemMeasurement();

//                addAsDigitalImageFromItemmeasurement();

                Intent intent = new Intent(AddItemMeasurement.this, ItemMeasurementFindingActivity.class);
                intent.putExtra("pod", GenUtils.serializePOItemModal(poItemDtl));
                intent.putExtra("detail", GenUtils.serializeItemMeasurementModal(itemMeasurementModal));
                startActivityForResult(intent, FEnumerations.REQUEST_FOR_ADD_ITEM_MEASUREMENT_FINDING);

            } else {
                Toast toast = ToastCompat.makeText(AddItemMeasurement.this, "Select sample size", Toast.LENGTH_SHORT);
                GenUtils.safeToastShow(TAG, AddItemMeasurement.this, toast);
            }
        } else {
            Toast toast = ToastCompat.makeText(AddItemMeasurement.this, "Please fill dimension", Toast.LENGTH_SHORT);
            GenUtils.safeToastShow(TAG, AddItemMeasurement.this, toast);
        }
    }

    private void ImageHandler() {
        itemMeasurementModal.ItemMeasurementDescr = editDescription.getText().toString();
        if (TextUtils.isEmpty(itemMeasurementModal.ItemMeasurementDescr)) {
            editDescription.setError("Fill description");
            editDescription.requestFocus();
            Toast toast = ToastCompat.makeText(AddItemMeasurement.this, "Please fill description", Toast.LENGTH_SHORT);
            GenUtils.safeToastShow(TAG, AddItemMeasurement.this, toast);
            return;
        }
        handleToupdateItemMeasurement();
        String valueToReturn2 = R.id.workmanshipImage3 + "";
        MultipleImageHandler.showDialog(AddItemMeasurement.this, MultipleImageHandler.DOCUMENT, valueToReturn2);

    }

    private void ImageSelectHandler() {
        String valueToReturn2 = R.id.workmanshipImage3 + "";
        MultipleImageHandler.showDialog(AddItemMeasurement.this, MultipleImageHandler.DOCUMENT, valueToReturn2, MultipleImageHandler.pickerViewer);

    }

    /*private void addAsDigitalImageFromItemmeasurement() {
        if (itemMeasurementModal != null
                && itemMeasurementModal.listImages != null
                && itemMeasurementModal.listImages.size() > 0) {
            for (int i = 0; i < itemMeasurementModal.listImages.size(); i++) {
                handleToUpdateImage(itemMeasurementModal.listImages.get(i));
            }

        }

    }*/

    private void handleToUpdateImage(DigitalsUploadModal digitalsUploadModal) {

        if (TextUtils.isEmpty(digitalsUploadModal.title)) {
            digitalsUploadModal.title = itemMeasurementModal.ItemMeasurementDescr;
        }
        if (TextUtils.isEmpty(digitalsUploadModal.ImageExtn)
                || digitalsUploadModal.ImageExtn.equals("null")) {
            digitalsUploadModal.ImageExtn = FEnumerations.E_IMAGE_EXTN;
        }
        if (TextUtils.isEmpty(digitalsUploadModal.pRowID)
                || digitalsUploadModal.pRowID.equals("null")) {
            digitalsUploadModal.pRowID = ItemInspectionDetailHandler.GeneratePK(AddItemMeasurement.this, FEnumerations.E_TABLE_NAME_QRPOItemDtl_Image);
        }
        String digitalsRowId = updateDBWithImage(digitalsUploadModal);
        if (!TextUtils.isEmpty(digitalsRowId)
                && !TextUtils.isEmpty(returnPRowID)
                && !"null".equalsIgnoreCase(returnPRowID)) {
            List<ItemMeasurementModal> dtlList = ItemInspectionDetailHandler.getDigitalsFromItemMeasurementShip(AddItemMeasurement.this, returnPRowID);
            if (dtlList != null && dtlList.size() > 0) {
                ItemMeasurementModal mWo = dtlList.get(0);
                if (!TextUtils.isEmpty(mWo.Digitals)
                        && !mWo.Digitals.equals("null")) {
                    mWo.Digitals = mWo.Digitals + ", " + digitalsRowId;
                } else {
                    mWo.Digitals = digitalsRowId;
                }
                itemMeasurementModal.Digitals = mWo.Digitals;
                ItemInspectionDetailHandler.updateDigitalFromItemmeasurement(AddItemMeasurement.this, mWo);
            }
        }
    }

    private String updateDBWithImage(DigitalsUploadModal digitalsUploadModal) {
        return ItemInspectionDetailHandler.updateImageFromQualityParameter(this, poItemDtl.QRHdrID, poItemDtl.QRPOItemHdrID,returnPRowID, digitalsUploadModal);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("detail", data);
        outState.putString("poItemDtl", GenUtils.serializePOItemModal(poItemDtl));
        outState.putInt("viewType", mViewType);
        outState.putString("returnPRowID", returnPRowID);
    }
}
