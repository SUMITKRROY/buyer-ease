package com.buyereasefsl;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.constant.FEnumerations;
import com.inspection.InspectionModal;
import com.podetail.POItemDtl;
import com.util.FslLog;
import com.util.GenUtils;
import com.util.MultipleImageHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import me.drakeet.support.toast.ToastCompat;

/**
 * Created by ADMIN on 3/13/2018.
 */

public class AddQualityParameter extends AppCompatActivity implements View.OnClickListener {


    String data;
    EditText editTitle;
    ImageView digitalImage;
    //    DigitalsUploadModal digitalsUploadModal = new DigitalsUploadModal();
    POItemDtl poItemDtl;
    InspectionModal inspectionModal;
    QualityParameter qualityParameter;
    String _selectedTitle = null;
    TextView txtSelected;
    LinearLayout workmanshipImage1Container;
    String TAG = "AddQualityParameter";
    int mViewType = -1;
    Spinner spinnerStatus;
    LinearLayout spinnerContainer;
    String returnPRowID;
    LinearLayout imageContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_qualityparameter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editTitle = (EditText) findViewById(R.id.editTitle);
        txtSelected = (TextView) findViewById(R.id.txtSelected);
        spinnerContainer = (LinearLayout) findViewById(R.id.spinnerContainer);
        spinnerStatus = (Spinner) findViewById(R.id.spinnerStatus);
        imageContainer = (LinearLayout) findViewById(R.id.imageContainer);
        if (savedInstanceState != null) {
            qualityParameter = GenUtils.deSerializeQualityParameter(savedInstanceState.getString("qualityParameter"));
            mViewType = savedInstanceState.getInt("viewType", -1);
            returnPRowID = savedInstanceState.getString("returnPRowID", null);
            if (mViewType == FEnumerations.VIEW_TYPE_QUALITY_PARAMETER_DETAIL_LEVEL
                    || mViewType == FEnumerations.VIEW_TYPE_INTERNAL_TEST_LEVEL) {
                poItemDtl = GenUtils.deSerializePOItemModal(savedInstanceState.getString("detail"));
            } else {
                inspectionModal = GenUtils.deSerializeInspectionModal(savedInstanceState.getString("detail"));
            }
        } else {
            qualityParameter = GenUtils.deSerializeQualityParameter(getIntent().getStringExtra("qualityParameter"));
            mViewType = getIntent().getIntExtra("viewType", -1);

            if (mViewType == FEnumerations.VIEW_TYPE_QUALITY_PARAMETER_DETAIL_LEVEL
                    || mViewType == FEnumerations.VIEW_TYPE_INTERNAL_TEST_LEVEL) {
                poItemDtl = GenUtils.deSerializePOItemModal(getIntent().getStringExtra("detail"));
            } else {
                inspectionModal = GenUtils.deSerializeInspectionModal(getIntent().getStringExtra("detail"));
            }
            if (mViewType == FEnumerations.VIEW_TYPE_QUALITY_PARAMETER_DETAIL_LEVEL) {
                updateQualityParameter();
            } else if (mViewType == FEnumerations.VIEW_TYPE_INTERNAL_TEST_LEVEL) {
                updateInternalTest();
            } else if (mViewType == FEnumerations.VIEW_TYPE_QUALITY_PARAMETER_INSPECTION_LEVEL) {
                updateQualityParameterInspectionLevel();
            }
        }
        getSupportActionBar().setTitle(qualityParameter.MainDescr);
        workmanshipImage1Container = (LinearLayout) findViewById(R.id.workmanshipImage1Container);
        digitalImage = (ImageView) findViewById(R.id.digitalImage);


        digitalImage.setOnClickListener(this);

        if (qualityParameter.ImageRequired == 0) {
            imageContainer.setVisibility(View.GONE);
        } else {
            imageContainer.setVisibility(View.VISIBLE);
        }

        workmanshipImage1Container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (qualityParameter.imageAttachmentList != null && qualityParameter.imageAttachmentList.size() > 0) {

                    ArrayList<String> list = new ArrayList<>();
                    for (int i = 0; i < qualityParameter.imageAttachmentList.size(); i++) {
                        list.add(qualityParameter.imageAttachmentList.get(i).selectedPicPath);
                    }
                    if (list.size() > 0) {
                        Bundle bundle = new Bundle();
                        bundle.putStringArrayList("galleryModels", list);
                        bundle.putInt("position", 0);
//                      FragmentManager fragmentManager =activity.
                        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                        SlideshowDialogFragment newFragment = SlideshowDialogFragment.newInstance();
                        newFragment.setArguments(bundle);
                        newFragment.show(ft, "slideshow");
                    }
                }
            }
        });


        if (!TextUtils.isEmpty(qualityParameter.OptionValue)) {

            String[] spStr = qualityParameter.OptionValue.split(Pattern.quote(")S("), 0);
            if (spStr != null && spStr.length > 0) {
                String[] spStr1 = spStr[0].split(Pattern.quote("|"));
                ArrayList<String> itemsOptionArray = new ArrayList<>();
                for (String a : spStr1) {
                    itemsOptionArray.add(a);
                }
                qualityParameter.applicableLists = new ArrayList<>();
                for (int i = 0; i < itemsOptionArray.size(); i++) {
                    ApplicableList generalModel = new ApplicableList();
                    String[] arrSub = itemsOptionArray.get(i).split("~");
                    if (arrSub.length > 1) {
                        generalModel.title = arrSub[0].trim();
                        generalModel.value = Integer.parseInt(arrSub[1].trim());
                        qualityParameter.applicableLists.add(generalModel);
                    }
                }
                if (qualityParameter.applicableLists != null && qualityParameter.applicableLists.size() > 0) {
                    int selectPos = 0;
                    List<String> list = new ArrayList<>();
                    for (int i = 0; i < qualityParameter.applicableLists.size(); i++) {
                        list.add(qualityParameter.applicableLists.get(i).title);
                        if (qualityParameter.OptionSelected == qualityParameter.applicableLists.get(i).value) {
                            selectPos = i;
                        }
                    }
                    ArrayAdapter masterArrary = new ArrayAdapter(AddQualityParameter.this, R.layout.dialog_list_item, list);
                    masterArrary.setDropDownViewResource(R.layout.dialog_list_item);
                    //Setting the ArrayAdapter data on the Spinner
                    spinnerStatus.setAdapter(masterArrary);

                    spinnerStatus.setSelection(selectPos);
                    spinnerStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                        final int mPos = (int) view.getTag();
                            qualityParameter.OptionSelected = qualityParameter.applicableLists.get(i).value;
//                        packagePoItemDetalDetail.PKG_Me_Master_InspectionResultID = overAllResultStatusList.get(i).MainID;
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }
            } else {
                spinnerContainer.setVisibility(View.GONE);
            }

        } else {
            spinnerContainer.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(qualityParameter.Remarks) && !"null".equalsIgnoreCase(qualityParameter.Remarks))
            editTitle.setText(qualityParameter.Remarks);

        txtSelected.setText(qualityParameter.imageAttachmentList.size() + "");
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
               /* if (digitalsUploadModal.imageArray.size() > 0) {
                    onSubmit();
                } else {
                    ToastCompat toast = ToastCompat.makeText(this, "No digitals upload", Toast.LENGTH_SHORT);
                    GenUtils.safeToastShow(TAG, AddQualityParameter.this, toast);
                }*/
                onSubmit();
                return true;


        }

        return false;
    }

    private void onSubmit() {

        qualityParameter.Remarks = _selectedTitle = editTitle.getText().toString();

        /*if (TextUtils.isEmpty(_selectedTitle)) {
            Toast toast = ToastCompat.makeText(AddQualityParameter.this, "Enter title", Toast.LENGTH_SHORT);
            GenUtils.safeToastShow(TAG, AddQualityParameter.this, toast);
            return;
        }*/

       /* digitalsUploadModal.title = _selectedTitle;
        Intent intent = new Intent();
        intent.putExtra("detail", GenUtils.serializeDigitalModal(digitalsUploadModal));
        setResult(RESULT_OK, intent);
        finish();*/


        if (mViewType == FEnumerations.VIEW_TYPE_QUALITY_PARAMETER_DETAIL_LEVEL) {
            handleSaveQualityParameter();
        } else if (mViewType == FEnumerations.VIEW_TYPE_INTERNAL_TEST_LEVEL) {
            handleSaveFitness();
        } else if (mViewType == FEnumerations.VIEW_TYPE_QUALITY_PARAMETER_INSPECTION_LEVEL) {
            handleQualityParameterInspectionLevel();
        }

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.digitalImage:
                onQualityParameterImage();
                break;
        }
    }

    private void onQualityParameterImage() {
        updateQualityParameter();
        String valueToReturn = FEnumerations.REQUEST_FOR_QUALITY_PARAMETER_ATTACHMENT + "";
        MultipleImageHandler.showDialog(AddQualityParameter.this, MultipleImageHandler.DOCUMENT, valueToReturn);

    }

    private void onQualityParameterSelectImage() {
        String valueToReturn = FEnumerations.REQUEST_FOR_QUALITY_PARAMETER_ATTACHMENT + "";
        MultipleImageHandler.showDialog(AddQualityParameter.this, MultipleImageHandler.DOCUMENT, valueToReturn, MultipleImageHandler.pickerViewer);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        FslLog.d("AddWorkManShip", "onActivityResult requestCode - " + requestCode + " resultCode - " + resultCode);

        MultipleImageHandler.onActivityResult(AddQualityParameter.this,
                requestCode, resultCode, data, new MultipleImageHandler.GetBitmap() {
                    @Override
                    public void onGetBitamp(Bitmap serverBitmap,
                                            ArrayList<String> imagePathArrayList,
                                            String valueReturned) {
                        int resId = Integer.valueOf(valueReturned);

                        if (imagePathArrayList != null && imagePathArrayList.size() > 0) {

                            for (int i = 0; i < imagePathArrayList.size(); i++) {
                                DigitalsUploadModal asDigitalsUploadModal = new DigitalsUploadModal();
                                asDigitalsUploadModal.title = qualityParameter.MainDescr;
                                asDigitalsUploadModal.ImageExtn = FEnumerations.E_IMAGE_EXTN;
                                asDigitalsUploadModal.selectedPicPath = imagePathArrayList.get(i);
                                qualityParameter.imageAttachmentList.add(asDigitalsUploadModal);
                                if (mViewType == FEnumerations.VIEW_TYPE_QUALITY_PARAMETER_DETAIL_LEVEL) {
                                    handleQualityImage(asDigitalsUploadModal);
                                } else if (mViewType == FEnumerations.VIEW_TYPE_INTERNAL_TEST_LEVEL) {
                                    handleFitnessCheckImage(asDigitalsUploadModal);
                                } else if (mViewType == FEnumerations.VIEW_TYPE_QUALITY_PARAMETER_INSPECTION_LEVEL) {
                                    handleQualityInspectionLevelImage(asDigitalsUploadModal);
                                }

                            }

                        }
                        txtSelected.setText(qualityParameter.imageAttachmentList.size() + "");
                        onQualityParameterSelectImage();

                    }

                });
        if (requestCode == 0
                && resultCode == RESULT_OK) {
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("detail", GenUtils.serializePOItemModal(poItemDtl));
        outState.putString("qualityParameter", GenUtils.serializeQualityParameter(qualityParameter));
        outState.putInt("viewType", mViewType);
        outState.putString("returnPRowID", returnPRowID);
    }

    private void handleSaveQualityParameter() {

//        if (qualityParameterList != null) {
//            List<QualityParameter> list = ItemInspectionDetailHandler.getSavedQualityParameter(this, poItemDtl.QRHdrID, poItemDtl.QRPOItemHdrID);
//            for (int i = 0; i < qualityParameterList.size(); i++) {
//                for (int j = 0; j < qualityParameterListForSave.size(); j++) {
        if (qualityParameter.IsApplicable != 0) {
//            if (TextUtils.isEmpty(returnPRowID) || "null".equalsIgnoreCase(returnPRowID)){
//            updateQualityParameter();
//            if (!TextUtils.isEmpty(returnPRowID)) {
//                addASDigitalQualityParameter(qualityParameter);
//            }
            updateQualityParameter();
        } /*else {
                    for (int j = 0; j < list.size(); j++) {
                        if (list.get(j).QualityParameterID.equals(qualityParameterList.get(i).QualityParameterID)) {
                            String returnPRowID = ItemInspectionDetailHandler.updateQualityParameter(this, qualityParameterList.get(i), poItemDtl);
                            if (!TextUtils.isEmpty(returnPRowID)) {
                                addASDigitalQualityParameter(qualityParameterList.get(i), returnPRowID);
                            }
                        }
                    }*/
        Toast toast = ToastCompat.makeText(AddQualityParameter.this, "Save Successful", Toast.LENGTH_SHORT);
        GenUtils.safeToastShow(TAG, AddQualityParameter.this, toast);
    }

    public void handleQualityParameterInspectionLevel() {

//        if (qualityParameterList != null) {
//            List<QualityParameter> list = ItemInspectionDetailHandler.getSavedQualityParameter(qualityParameterFragment.getActivity(), POItemTabActivity.poItemTabActivity.inspectionModal.pRowID, null);
//            for (int i = 0; i < qualityParameterList.size(); i++) {
//                for (int j = 0; j < qualityParameterListForSave.size(); j++) {
        if (qualityParameter.IsApplicable != 0) {
            updateQualityParameterInspectionLevel();
//            if (!TextUtils.isEmpty(returnPRowID)) {
//                addASDigitalQualityParameterInspectionLevel(qualityParameter);
//            }
                /*} else {
                    for (int j = 0; j < list.size(); j++) {
                        if (list.get(j).QualityParameterID.equals(qualityParameterList.get(i).QualityParameterID)) {
                            String returnPRowID = ItemInspectionDetailHandler.updateQualityParameterForItemLevel(qualityParameterFragment.getActivity(), qualityParameterList.get(i), POItemTabActivity.poItemTabActivity.inspectionModal);
                            if (!TextUtils.isEmpty(returnPRowID)) {
                                addASDigitalQualityParameter(qualityParameterList.get(i), returnPRowID);
                            }
                        }
                    }
                }*/
//                ToastCompat.makeText(this, "update data successfully", Toast.LENGTH_SHORT).show();
//                }
//            }
        }

    }


    private void handleSaveFitness() {


//        if (internalTestList != null) {
//            List<QualityParameter> list = ItemInspectionDetailHandler.getListFitness(this, poItemDtl.QRHdrID, poItemDtl.QRPOItemHdrID);
//            for (int i = 0; i < internalTestList.size(); i++) {
        if (qualityParameter.IsApplicable != 0) {
            updateInternalTest();
//            if (!TextUtils.isEmpty(returnPRowID)) {
//                addASDigitalFitnessCheck(qualityParameter);
//            }
        } /*else {
                    for (int j = 0; j < list.size(); j++) {
                        if (list.get(j).QualityParameterID.equals(internalTestList.get(i).QualityParameterID)) {
                            String returnPRowID = ItemInspectionDetailHandler.updateFitness(this, internalTestList.get(i), poItemDtl);
                            if (!TextUtils.isEmpty(returnPRowID)) {
                                addASDigitalFitnessCheck(internalTestList.get(i), returnPRowID);
                            }
                        }
                    }
                }*/
//            }
//        }
        Toast toast = ToastCompat.makeText(AddQualityParameter.this, "Save Successful", Toast.LENGTH_SHORT);
        GenUtils.safeToastShow(TAG, AddQualityParameter.this, toast);

    }

  /*  private void addASDigitalFitnessCheck(QualityParameter qualityParameter) {
        if (qualityParameter != null
                && qualityParameter.imageAttachmentList != null
                && qualityParameter.imageAttachmentList.size() > 0) {
            for (int i = 0; i < qualityParameter.imageAttachmentList.size(); i++) {
                handleFitnessCheckImage(qualityParameter.imageAttachmentList.get(i));


            }
        }
    }*/

    private void handleFitnessCheckImage(DigitalsUploadModal digitalsUploadModal) {

        if (!TextUtils.isEmpty(digitalsUploadModal.pRowID) && digitalsUploadModal.pRowID.length() == 10) {

        } else {
            digitalsUploadModal.pRowID = ItemInspectionDetailHandler.GeneratePK(AddQualityParameter.this, FEnumerations.E_TABLE_NAME_QRPOItemDtl_Image);
        }

        String imgPRowID = updateDBWithImage(digitalsUploadModal);
        FslLog.d(TAG, " image saved ID " + imgPRowID);
        updateInternalTest();
        if (!TextUtils.isEmpty(imgPRowID) && !TextUtils.isEmpty(returnPRowID) && !"null".equalsIgnoreCase(returnPRowID)) {
            List<QualityParameter> dtlList = ItemInspectionDetailHandler.getListFitnessCheckAccordingRowId(this, returnPRowID);

            if (dtlList != null && dtlList.size() > 0) {
                QualityParameter mPo = dtlList.get(0);

                if (!TextUtils.isEmpty(mPo.Digitals) && !mPo.Digitals.equals("null")) {
                    mPo.Digitals = mPo.Digitals + ", " + imgPRowID;
                } else {
                    mPo.Digitals = imgPRowID;
                }
                qualityParameter.Digitals = mPo.Digitals;
                ItemInspectionDetailHandler.updateDigitalsFitnessCheck(this, mPo);
            }

        }

    }

   /* private void addASDigitalQualityParameterInspectionLevel(QualityParameter qualityParameter) {
        if (qualityParameter != null
                && qualityParameter.imageAttachmentList != null
                && qualityParameter.imageAttachmentList.size() > 0) {
            for (int i = 0; i < qualityParameter.imageAttachmentList.size(); i++) {
                handleQualityInspectionLevelImage(qualityParameter.imageAttachmentList.get(i));
            }
        }
    }*/

    private void handleQualityInspectionLevelImage(DigitalsUploadModal digitalsUploadModal) {

        if (!TextUtils.isEmpty(digitalsUploadModal.pRowID) && digitalsUploadModal.pRowID.length() == 10) {

        } else {
            digitalsUploadModal.pRowID = ItemInspectionDetailHandler.GeneratePK(AddQualityParameter.this, FEnumerations.E_TABLE_NAME_QRPOItemDtl_Image);
        }

        String imgPRowID = updateDBWithImageInspectionLevel(digitalsUploadModal);
        updateQualityParameterInspectionLevel();
        if (!TextUtils.isEmpty(imgPRowID) && !TextUtils.isEmpty(returnPRowID) && !"null".equalsIgnoreCase(returnPRowID)) {
            List<QualityParameter> dtlList = ItemInspectionDetailHandler.getListQualityParameterAccordingRowId(AddQualityParameter.this, returnPRowID);

            if (dtlList != null && dtlList.size() > 0) {
                QualityParameter mPo = dtlList.get(0);
                if (!TextUtils.isEmpty(mPo.Digitals) && !mPo.Digitals.equals("null")) {
                    mPo.Digitals = mPo.Digitals + ", " + imgPRowID;
                } else {
                    mPo.Digitals = imgPRowID;
                }
                qualityParameter.Digitals = mPo.Digitals;
                ItemInspectionDetailHandler.updateDigitalsQualityMeasurement(AddQualityParameter.this, mPo);
            }

        }

    }


   /* private void addASDigitalQualityParameter(QualityParameter qualityParameter) {
        if (qualityParameter != null
                && qualityParameter.imageAttachmentList != null
                && qualityParameter.imageAttachmentList.size() > 0) {
            for (int i = 0; i < qualityParameter.imageAttachmentList.size(); i++) {
                handleQualityImage(qualityParameter.imageAttachmentList.get(i));
            }
        }
    }*/

    private void handleQualityImage(DigitalsUploadModal digitalsUploadModal) {

        if (!TextUtils.isEmpty(digitalsUploadModal.pRowID) && digitalsUploadModal.pRowID.length() == 10) {

        } else {
            digitalsUploadModal.pRowID = ItemInspectionDetailHandler.GeneratePK(AddQualityParameter.this, FEnumerations.E_TABLE_NAME_QRPOItemDtl_Image);
        }

        String imgPRowID = updateDBWithImage(digitalsUploadModal);
        FslLog.d(TAG, " image saved ID " + imgPRowID);

        if (!TextUtils.isEmpty(imgPRowID) && !TextUtils.isEmpty(returnPRowID) && !"null".equalsIgnoreCase(returnPRowID)) {
            List<QualityParameter> dtlList = ItemInspectionDetailHandler.getListQualityParameterAccordingRowId(AddQualityParameter.this, returnPRowID);

            if (dtlList != null && dtlList.size() > 0) {
                QualityParameter mPo = dtlList.get(0);

                if (!TextUtils.isEmpty(mPo.Digitals) && !mPo.Digitals.equals("null")) {
                    mPo.Digitals = mPo.Digitals + ", " + imgPRowID;
                } else {
                    mPo.Digitals = imgPRowID;
                }
                qualityParameter.Digitals = mPo.Digitals;
                ItemInspectionDetailHandler.updateDigitalsQualityMeasurement(AddQualityParameter.this, mPo);
            }

        }

    }

    private void updateQualityParameterInspectionLevel() {
        returnPRowID = ItemInspectionDetailHandler.updateQualityParameterForItemLevel(this, qualityParameter, inspectionModal);
    }


    private void updateInternalTest() {
        returnPRowID = ItemInspectionDetailHandler.updateFitness(this, qualityParameter, poItemDtl);
        FslLog.d(TAG, " return update internal test param ID " + returnPRowID);
    }

    private void updateQualityParameter() {
//        if (TextUtils.isEmpty(returnPRowID) || "null".equalsIgnoreCase(returnPRowID)) {
        returnPRowID = ItemInspectionDetailHandler.updateQualityParameter(this, qualityParameter, poItemDtl);
        FslLog.d(TAG, " return update quality param ID " + returnPRowID);
//        }
    }

    private String updateDBWithImage(DigitalsUploadModal digitalsUploadModal) {
        return ItemInspectionDetailHandler.updateImageFromQualityParameter(this, poItemDtl.QRHdrID, poItemDtl.QRPOItemHdrID, returnPRowID, digitalsUploadModal);

    }

    private String updateDBWithImageInspectionLevel(DigitalsUploadModal digitalsUploadModal) {
        return ItemInspectionDetailHandler.updateImageFromQualityParameter(this, inspectionModal.pRowID, null, returnPRowID, digitalsUploadModal);

    }
}

