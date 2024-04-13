package com.buyereasefsl;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
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
import android.widget.ViewFlipper;

import com.General.EnclosureModal;
import com.General.GeneralMasterHandler;
import com.General.GeneralModel;
import com.General.InsLvHdrHandler;
import com.General.InsLvHdrModal;
import com.General.OnSIteModal;
import com.General.SampleCollectedModal;
import com.General.SampleModal;
import com.General.SysData22Handler;
import com.General.SysData22Modal;
import com.constant.FClientConstants;
import com.constant.FEnumerations;
import com.inspection.InspectionModal;
import com.podetail.POItemDtl;
import com.podetail.POItemDtlHandler;
import com.podetail.POItemListActivity;
import com.podetail.POItemPkgAppDetail;
import com.util.DateUtils;
import com.util.FslLog;
import com.util.GenUtils;
import com.util.MultipleImageHandler;
import com.util.NetworkUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import me.drakeet.support.toast.ToastCompat;

/**
 * Created by ADMIN on 11/15/2017.
 */

public class ItemInspectionDetail extends AppCompatActivity
        implements View.OnClickListener,
        WorkManShipAdaptor.onWorkManShipClickListener,
        QualityParameterAdaptor.ClickListener,
        DigitalUploadAdaptor.onClickListener,
        ItemMeasurementAdaptor.OnItemMeasurementClickListener {


    RecyclerView recyclerView, recyclerViewWorkmanshipHistory;
    //List<ItemPackingModel> itemReportModels;
    WorkManShipAdaptor workManShipAdaptor, workManShipHistoryAdaptor;
    TextView activity, workmanHistoryDate, inspectionID, inspectionItemID, itemHistoryDate, itemactivity;

    ViewFlipper viewFlipper;
    TextView txtPackingMeasurementDetail, txtWorkmanshipVisualInspectionDetail,
            txtItemMeasurement, txtDigitalsUploaded,
            txtTestReports, txtEnclosure, txtHistory, txtProductionStatus, txtQualityParameters, txtPackagingAppearance, txtAddBarcode, txtOnSiteTest,
            txtItemQty, txtSampleCollected;
    LinearLayout WorkmanshipVisualInspectionDetail, PackingMeasurementDetail,
            workManShipListContainer, itemMeasurementDetail,
            DigitalsUploadedDetail, TestReportsDetail, EnclosureDetail, HistoryDetail,
            qualityParametersTab, productionStatusTab, packagingAppearanceLL/*,pkg_app_divider_ll*/, addBarcodeLL, onSiteTestLL, itemQtyLL, itemSampleCollectedLL;
    List<WorkManShipModel> workManShipModels, workManShipHistoryModels;
    LinearLayout packingFillContainer, innerPackingFillContainer, masterPackingFillContainer, palletPackingFillContainer
            /* ,  cPalletPackContainer, cMasterPackContainer, cInnerPackContainer*/;
    //ItemPackingModel packagePoItemDetalDetail = new ItemPackingModel();
    ImageView masterPackingImagePicker, unitPackingImagePicker, innerPackingImagePicker, palletPackingImagePicker;
    ImageView unitPackingIPkgAppImagePicker, shippingPackingIPkgAppImagePicker, innerPackingIPkgAppImagePicker,
            masterPackingIPkgAppImagePicker, palletPackingIPkgAppImagePicker;

    ImageView palletBarcodeImagePicker,masterBarcodeImagePicker,innerBarcodeImagePicker,unitBarcodeImagePicker;
    TextView  unitBarcodeAttachmentCount,innerBarcodeAttachmentCount,masterBarcodeAttachmentCount,palletBarcodeAttachmentCount;
    TextView unitAttachmentCount, innerAttachmentCount, masterAttachmentCount, palletAttachmentCount;
    Spinner spinnerOverAllResult, spinnerMasterPackingOverAllResult, spinnerInnerPackingOverAllResult,
            spinnerUnitPackingOverAllResult, spinnerPalletPackingOverAllResult, spinnerInspectionLevel,
            spinnerSampleSizeUnitPacking, spinnerSampleSizeInnerPacking, spinnerSampleSizeMasterPacking,
            spinnerSampleSizePalletPacking, workmanshipOverAllResultContainer,
            spinnerItemMeasurementOverAllResult, spinnerHoleOverAllResult,
            spinnerPjgAppearOverAllResult, spinnerSampleSizeUnitPkgAppearPacking, spinnerOverAllUnitPackingPkgAppear,
            spinnerSampleSizeShippingMarkPkgAppearPacking, spinnerOverAllShippingMarkPkgAppear,
            spinnerSampleSizeInnerPackingPkgAppearPacking, spinnerOverAllInnerPackingPkgAppear,
            spinnerSampleSizeMasterPackingPkgAppearPacking, spinnerOverAllMasterPackingPkgAppear,
            spinnerSampleSizePalletPackingPkgAppearPacking, spinnerOverAllPalletPackingPkgAppear;

    LinearLayout PkgAppearDescHdrLL,
            llPkgAppearDesc1, llPkgAppearDesc2,
            llPkgAppearDesc3, llPkgAppearDesc4,
            llPkgAppearDesc5, llPkgAppearDesc6,
            llPkgAppearDesc7, llPkgAppearDesc8,
            llPkgAppearDesc9;

    //packaging Appearance
    TextView txtPkgAppearDesc1, txtPkgAppearDesc2,
            txtPkgAppearDesc3, txtPkgAppearDesc4, txtPkgAppearDesc5,
            txtPkgAppearDesc6, txtPkgAppearDesc7, txtPkgAppearDesc8,
            txtPkgAppearDesc9, editPackingAppearRemark;
    Spinner spinnerSampleSizePkgAppearDesc1, spinnerOverAllPkgAppearDesc1,
            spinnerSampleSizePkgAppearDesc2, spinnerOverAllPkgAppearDesc2,
            spinnerSampleSizePkgAppearDesc3, spinnerOverAllPkgAppearDesc3,
            spinnerSampleSizePkgAppearDesc4, spinnerOverAllPkgAppearDesc4,
            spinnerSampleSizePkgAppearDesc5, spinnerOverAllPkgAppearDesc5,
            spinnerSampleSizePkgAppearDesc6, spinnerOverAllPkgAppearDesc6,
            spinnerSampleSizePkgAppearDesc7, spinnerOverAllPkgAppearDesc7,
            spinnerSampleSizePkgAppearDesc8, spinnerOverAllPkgAppearDesc8,
            spinnerSampleSizePkgAppearDesc9, spinnerOverAllPkgAppearDesc9;

    Spinner spinnerSampleSizeUnitPackingBarcode, spinnerSampleSizeInnerPackingBarcode,
            txtResultSpinnerUnitPackingBarcode, txtResultSpinnerInnerPackingBarcode,
            spinnerSampleSizeMasterPackingBarcode, txtResultSpinnerMasterPackingBarcode,
            spinnerSampleSizePalletPackingBarcode, txtResultSpinnerPalletPackingBarcode,
            spinnerOverAllResultBarcode, spinnerItemQtyOverAllResult;
    EditText editPackingBarcodeRemark, txtVisulLabelBarcode, txtScanLabelBarcode,
            txtVisulLabelInnerPackingBarcode, txtScanLabelInnerPackingBarcode,
            txtVisulLabelMasterPackingBarcode, txtScanLabelMasterPackingBarcode,
            txtVisulLabelPalletPackingBarcode, txtScanLabelPalletPackingBarcode, editItemQtyRemark;
    TextView txtSpecificationLabelBarcode, txtSpecificationLabelInnerPackingBarcode,
            txtSpecificationLabelMasterPackingBarcode,
            txtSpecificationLabelPalletPackingBarcode, txtDelivaryDate, txtShipVia, txtOerderQty, txtAvlblQty, txtAcceptedQty;

    TextView txtUnitSL, txtUnitSB, txtUnitSH, txtUnitSWt,
            txtUnitSCBM, txtUnitSQuantity, txtUnitFindingsQuantity,
            txtInputTypeinner, txtInputTypeUnit, txtInputTypeMaster, txtInputTypePallet;
    EditText txtUnitFindingsL, txtUnitFindingsB, txtUnitFindingsH,
            txtUnitFindingsWt, txtUnitFindingsCBM;

    TextView txtInnerPackingL, txtInnerPackingB, txtInnerPackingH,
            txtInnerPackingWt, txtInnerPackingCBM, txtInnerPackingQuantity, txtInnerPackingFindingsQuantity;
    EditText txtInnerPackingFindingsL, txtInnerPackingFindingsB,
            txtInnerPackingFindingsH, txtInnerPackingFindingsWt,
            txtInnerPackingFindingsCBM;

    TextView txtPalletPackingL, txtPalletPackingB, txtPalletPackingH,
            txtPalletPackingWt, txtPalletPackingCBM, txtPalletPackingQuantity, txtPalletPackingFindingsQuantity;
    EditText txtPalletPackingFindingsL, txtPalletPackingFindingsB,
            txtPalletPackingFindingsH, txtPalletPackingFindingsWt,
            txtPalletPackingFindingsCBM;

    TextView txtMasterPackingL, txtMasterPackingB, txtMasterPackingH,
            txtMasterPackingWt, txtMasterPackingCBM, txtMasterPackingQuantity, txtMasterPackingFindingsQuantity;
    EditText txtMasterPackingFindingsL, txtMasterPackingFindingsB,
            txtMasterPackingFindingsH, txtMasterPackingFindingsWt,
            txtMasterPackingFindingsCBM;
    TextView txtTotalCritical, txtTotalMajor, txtTotalMinor,
            txtPermissibleMinor, txtPermissibleMajor, txtPermissibleCritical;

    //On-Site
    Spinner spinnerOnSiteOverAllResult,
            spinnerOnSiteDesc1, spinnerSampleSizeOnSiteDesc1, spinnerOnSiteOverAllDesc1,
            spinnerOnSiteDesc2, spinnerSampleSizeOnSiteDesc2, spinnerOnSiteOverAllDesc2,
            spinnerOnSiteDesc3, spinnerSampleSizeOnSiteDesc3, spinnerOnSiteOverAllDesc3,
            spinnerOnSiteDesc4, spinnerSampleSizeOnSiteDesc4, spinnerOnSiteOverAllDesc4,
            spinnerOnSiteDesc5, spinnerSampleSizeOnSiteDesc5, spinnerOnSiteOverAllDesc5,
            spinnerOnSiteDesc6, spinnerSampleSizeOnSiteDesc6, spinnerOnSiteOverAllDesc6,
            spinnerOnSiteDesc7, spinnerSampleSizeOnSiteDesc7, spinnerOnSiteOverAllDesc7,
            spinnerOnSiteDesc8, spinnerSampleSizeOnSiteDesc8, spinnerOnSiteOverAllDesc8,
            spinnerOnSiteDesc9, spinnerSampleSizeOnSiteDesc9, spinnerOnSiteOverAllDesc9,
            spinnerOnSiteDesc10, spinnerSampleSizeOnSiteDesc10, spinnerOnSiteOverAllDesc10;
    ImageView addOnSiteDescIv, addSampleDataIv;
    LinearLayout llOnSiteDesc1,
            llOnSiteDesc2,
            llOnSiteDesc3,
            llOnSiteDesc4,
            llOnSiteDesc5,
            llOnSiteDesc6,
            llOnSiteDesc7,
            llOnSiteDesc8,
            llOnSiteDesc9,
            llOnSiteDesc10;
    TextView txtOnSiteDesc1,
            txtOnSiteDesc2,
            txtOnSiteDesc3,
            txtOnSiteDesc4,
            txtOnSiteDesc5,
            txtOnSiteDesc6,
            txtOnSiteDesc7,
            txtOnSiteDesc8,
            txtOnSiteDesc9,
            txtOnSiteDesc10;
    EditText editOnSiteRemark;

    LinearLayout llSampleCollected, llSampleCollected1, llSampleCollected2,
            llSampleCollected3, llSampleCollected4;
    TextView txtSampleCollectedPurpose, txtSampleCollectedPurpose1, txtSampleCollectedPurpose2,
            txtSampleCollectedPurpose3, txtSampleCollectedPurpose4;
    EditText txtSampleCollectedNumber, txtSampleCollectedNumber1, txtSampleCollectedNumber2,
            txtSampleCollectedNumber3, txtSampleCollectedNumber4;
    TextView txtSampleCollectedRemove, txtSampleCollectedRemove1, txtSampleCollectedRemove2,
            txtSampleCollectedRemove3, txtSampleCollectedRemove4;

    LinearLayout digitalsUploadListContainer;
    RecyclerView recyclerViewDigitalUploads;
    List<DigitalsUploadModal> digitalsUploadModals = new ArrayList<>();
    DigitalUploadAdaptor digitalUploadAdaptor;

    RecyclerView recyclerViewHistory;
    List<InspectionModal> historyInspectionModalList;
    HistoryAdaptor historyAdaptor;

    RecyclerView recyclerViewEnclosure;
    List<EnclosureModal> enclosureModalList;
    EnclosureAdaptor enclosureAdaptor;

    RecyclerView recyclerViewTestReport;
    List<TestReportModal> testReportModals;
    TestReportAdaptor testReportAdaptor;

    RecyclerView recyclerViewItemMeasurement, recyclerViewItemHistory;
    List<ItemMeasurementModal> itemMeasurementModalList, itemMeasurementHistoryModalList;
    ItemMeasurementAdaptor itemMeasurementAdaptor, itemMeasurementHistoryAdaptor;


    POItemDtl poItemDtl;
    InspectionModal inspectionModal;
    POItemDtl packagePoItemDetalDetail = new POItemDtl();
    OnSIteModal onSIteModal = new OnSIteModal();
    SampleCollectedModal sampleCollectedModal = new SampleCollectedModal();
    POItemPkgAppDetail pOItemPkgAppDetail = new POItemPkgAppDetail();


    LinearLayout itemMeasurementRemarkContainer, packingRemarkContainer, workmanShipRemarkContainer;
    EditText editPackingRemark, editWorkmanshipRemark, editItemMeasurementRemark;
    String _WorkmanshipRemark, _ItemMeasurementRemark;

    RecyclerView recyclerViewQualityParameter, recyclerViewInternalTest;
    QualityParameterAdaptor qualityParameterAdaptor, internalTestAdaptor;
    private List<QualityParameter> qualityParameterList, internalTestList/*, internalTestListForSave*/;
    //  private List<QualityParameter> qualityParameterListForSave;

    static int _qualityParameterAttachmentPos = -1;
    static int _iternalTestAttachmentPos = -1;

    String TAG = "ItemInspectionDetail";
    ProgressDialog loadingDialog;
    Boolean spinnerTouched;
    Boolean spinnerOnSiteSampleSizeTouched;
    Boolean spinnerOnSiteOverAllTouched;
    Boolean spinnerPkgAppeOverAllTouched;
    Boolean spinnerPkgAppSampleTouched;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inspection_for_item_row);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("Item Details");

        poItemDtl = GenUtils.deSerializePOItemModal(getIntent().getStringExtra("detail"));
        inspectionModal = GenUtils.deSerializeInspectionModal(getIntent().getStringExtra("inspectionDetail"));

        Log.e("itemqty",poItemDtl.OrderQty);
        getSupportActionBar().setSubtitle("Item No.(" + poItemDtl.CustomerItemRef + " )");
        FslLog.d(TAG, " retail price " + poItemDtl.RetailPrice);
        TextView retailPrice = (TextView) findViewById(R.id.retailPrice);
        retailPrice.setText("Retail Price : " + poItemDtl.RetailPrice);
        if (poItemDtl.RetailPrice > 0) {
            retailPrice.setVisibility(View.VISIBLE);
        } else {
            retailPrice.setVisibility(View.GONE);
        }
        viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);
        workManShipListContainer = (LinearLayout) findViewById(R.id.workManShipListContainer);
        WorkmanshipVisualInspectionDetail = (LinearLayout) findViewById(R.id.WorkmanshipVisualInspectionDetail);
        PackingMeasurementDetail = (LinearLayout) findViewById(R.id.PackingMeasurementDetail);
        itemMeasurementDetail = (LinearLayout) findViewById(R.id.itemMeasurementDetail);
        DigitalsUploadedDetail = (LinearLayout) findViewById(R.id.DigitalsUploadedDetail);
        TestReportsDetail = (LinearLayout) findViewById(R.id.TestReportsDetail);
        EnclosureDetail = (LinearLayout) findViewById(R.id.EnclosureDetail);
        HistoryDetail = (LinearLayout) findViewById(R.id.HistoryDetail);
        qualityParametersTab = (LinearLayout) findViewById(R.id.qualityParametersTab);
        productionStatusTab = (LinearLayout) findViewById(R.id.productionStatusTab);

        packagingAppearanceLL = (LinearLayout) findViewById(R.id.packagingAppearanceLL);
//        pkg_app_divider_ll = (LinearLayout) findViewById(R.id.pkg_app_divider_ll);
        addBarcodeLL = (LinearLayout) findViewById(R.id.addBarcodeLL);
        onSiteTestLL = (LinearLayout) findViewById(R.id.onSiteTestLL);
        itemQtyLL = (LinearLayout) findViewById(R.id.itemQtyLL);
        itemSampleCollectedLL = (LinearLayout) findViewById(R.id.itemSampleCollectedLL);

//        intimationTab = (LinearLayout) findViewById(R.id.intimationTab);

        txtDigitalsUploaded = (TextView) findViewById(R.id.txtDigitalsUploaded);
        txtTestReports = (TextView) findViewById(R.id.txtTestReports);
        txtEnclosure = (TextView) findViewById(R.id.txtEnclosure);
        txtHistory = (TextView) findViewById(R.id.txtHistory);
        txtProductionStatus = (TextView) findViewById(R.id.txtProductionStatus);
        txtQualityParameters = (TextView) findViewById(R.id.txtQualityParameters);
        txtItemMeasurement = (TextView) findViewById(R.id.txtItemMeasurement);

        txtPackagingAppearance = (TextView) findViewById(R.id.txtPackagingAppearance);
        txtAddBarcode = (TextView) findViewById(R.id.txtAddBarcode);
        txtOnSiteTest = (TextView) findViewById(R.id.txtOnSiteTest);
        txtItemQty = (TextView) findViewById(R.id.txtItemQty);
        txtSampleCollected = (TextView) findViewById(R.id.txtSampleCollected);

        editOnSiteRemark = (EditText) findViewById(R.id.editOnSiteRemark);
        editPackingAppearRemark = (EditText) findViewById(R.id.editPackingAppearRemark);
        editItemQtyRemark = (EditText) findViewById(R.id.editItemQtyRemark);
        /*txtDelivaryDate = (TextView) findViewById(R.id.txtDelivaryDate);
        txtShipVia = (TextView) findViewById(R.id.txtShipVia);
        txtOerderQty = (TextView) findViewById(R.id.txtOerderQty);
        txtAvlblQty = (TextView) findViewById(R.id.txtAvlblQty);
        txtAcceptedQty = (TextView) findViewById(R.id.txtAcceptedQty);*/
//        txtIntimation = (TextView) findViewById(R.id.txtIntimation);
        WorkmanshipVisualInspectionDetail.setOnClickListener(this);
        PackingMeasurementDetail.setOnClickListener(this);
        itemMeasurementDetail.setOnClickListener(this);
        DigitalsUploadedDetail.setOnClickListener(this);
        TestReportsDetail.setOnClickListener(this);
        EnclosureDetail.setOnClickListener(this);
        HistoryDetail.setOnClickListener(this);
        qualityParametersTab.setOnClickListener(this);
        productionStatusTab.setOnClickListener(this);

        packagingAppearanceLL.setOnClickListener(this);
        addBarcodeLL.setOnClickListener(this);
        onSiteTestLL.setOnClickListener(this);
        itemQtyLL.setOnClickListener(this);
        itemSampleCollectedLL.setOnClickListener(this);
//        intimationTab.setOnClickListener(this);
        txtPackingMeasurementDetail = (TextView) findViewById(R.id.txtPackingMeasurementDetail);
        txtWorkmanshipVisualInspectionDetail = (TextView) findViewById(R.id.txtWorkmanshipVisualInspectionDetail);
        initBarcodeTab();
        initItemQty();
        initSampleCollectedTab();

        handleToInitiatePackingMeasurement();//check for testing

        handleToInitiateWorkManShip();

        handleToInitiateItemMeasurement();

        handleToInitiateDigitalUpload();

        handleToInitiateEnclosure();

        handleToInitiateTestReport();

        handleToInitiateHistory();

        handleToInitiateQualityParameter();

        handleToInitiateInternalTest();

        intPkgAppTab();
//        handleToInitiatePackagingAppearance();//created by shekhar

        handleBarcodeTab();//created by shekhar done
        initializeOnSiteTab();//created by shekhar
//        handleOnSiteTab();//created by shekhar
//        handleSampleCollectedTab();//created by shekhar//done

        handleHoleOverAllResult();
        getPoListItem();//creted by shekhar
        handleItemQty();//created by shekhar//done
//        checkPkgAppShowHideStatus();//created by shekhar//done
        showFirstTab();
    }

    private void checkPkgAppShowHideStatus() {

        /*null= Item & Quantity
        30	* Packaging Appearance=0~Show|7~Hide
        31	* Packaging Measurement=0~Show & Always Print|1~Show, but print if specified|7~Hide
        33	* Barcodes=0~Show|7~Hide
        34	* OnSite Test=0~Show & Always Print|1~Show, but print if specified|7~Hide
        35	* Workmanship=0~Show & Always Print|1~Show, but print if specified|2~Show, Do not print|7~Hide
        36	* Sample Purpose=0~Show & Always Print|1~Show, but print if specified|7~Hide
        37	* Item Measurement=0~Show & Always Print|1~Show, but print if specified|7~Hide
        39	* Enclosure=0~Show|7~Hide=0~Show & Always Print|1~Show, but print if specified|7~Hide
        null=History
        88	Item Specific Quality Parameters=0~Not Applicable|1~Applicable(Quality Parameters)
        95	Internal Test (Lighting & Lamp)=0~Show & Print|2~Show|7~Hide
        38	* Digital=0~Show & Always Print|1~Show, but print if specified|7~Hide
        40	* Test Report=0~Show|7~Hide*/
        final List<SysData22Modal> statusList = SysData22Handler.getSysData22ListAccToID(this,
                FEnumerations.PKG_APP_GEN_ID, FEnumerations.PKG_APP_MAIN_ID);
        for (int i = 0; i < statusList.size(); i++) {
            if (statusList.get(i).SubID.equals(FEnumerations.PKG_APP_SUB_ID)) {
                if (statusList.get(i).numVal2.equals("0")) {
                    packagingAppearanceLL.setVisibility(View.VISIBLE);
//                    pkg_app_divider_ll.setVisibility(View.VISIBLE);
                } else {
                    packagingAppearanceLL.setVisibility(View.GONE);
//                    pkg_app_divider_ll.setVisibility(View.GONE);
                }
            }else if (statusList.get(i).SubID.equals(FEnumerations.PKG_MEASURMENT_SUB_ID)) {
                if (statusList.get(i).numVal2.equals("0")
                    || statusList.get(i).numVal2.equals("1")) {
                    PackingMeasurementDetail.setVisibility(View.VISIBLE);
                } else {
                    PackingMeasurementDetail.setVisibility(View.GONE);
                }
            }else if (statusList.get(i).SubID.equals(FEnumerations.BARCODE_SUB_ID)) {
                if (statusList.get(i).numVal2.equals("0")) {
                    addBarcodeLL.setVisibility(View.VISIBLE);
                } else {
                    addBarcodeLL.setVisibility(View.GONE);
                }

            }else if (statusList.get(i).SubID.equals(FEnumerations.ON_SITE_TEST_SUB_ID)) {
                if (statusList.get(i).numVal2.equals("0")
                        || statusList.get(i).numVal2.equals("1")) {
                    onSiteTestLL.setVisibility(View.VISIBLE);
                } else {
                    onSiteTestLL.setVisibility(View.GONE);
                }

            }else if (statusList.get(i).SubID.equals(FEnumerations.WORKMANSHIP_SUB_ID)) {
                if (statusList.get(i).numVal2.equals("0")
                        || statusList.get(i).numVal2.equals("1")) {
                    WorkmanshipVisualInspectionDetail.setVisibility(View.VISIBLE);
                } else {
                    WorkmanshipVisualInspectionDetail.setVisibility(View.GONE);
                }

            }else if (statusList.get(i).SubID.equals(FEnumerations.SAMPLE_PURPOSE_SUB_ID)) {
                if (statusList.get(i).numVal2.equals("0")
                        || statusList.get(i).numVal2.equals("1")) {
                    itemSampleCollectedLL.setVisibility(View.VISIBLE);
                } else {
                    itemSampleCollectedLL.setVisibility(View.GONE);
                }

            }else if (statusList.get(i).SubID.equals(FEnumerations.ITEM_MEASURMENT_SUB_ID)) {
                if (statusList.get(i).numVal2.equals("0")
                        || statusList.get(i).numVal2.equals("1")) {
                    itemMeasurementDetail.setVisibility(View.VISIBLE);
                } else {
                    itemMeasurementDetail.setVisibility(View.GONE);
                }

            }else if (statusList.get(i).SubID.equals(FEnumerations.DIGITAL_UPLOAD_SUB_ID)) {
                if (statusList.get(i).numVal2.equals("0")
                        || statusList.get(i).numVal2.equals("1")) {
                    DigitalsUploadedDetail.setVisibility(View.VISIBLE);
                } else {
                    DigitalsUploadedDetail.setVisibility(View.GONE);
                }

            }else if (statusList.get(i).SubID.equals(FEnumerations.ENCLOSURE_SUB_ID)) {
                if (statusList.get(i).numVal2.equals("0")
                        || statusList.get(i).numVal2.equals("1")) {
                    EnclosureDetail.setVisibility(View.VISIBLE);
                } else {
                    EnclosureDetail.setVisibility(View.GONE);
                }

            }else if (statusList.get(i).SubID.equals(FEnumerations.TEST_REPORT_SUB_ID)) {
                if (statusList.get(i).numVal2.equals("0")) {
                    TestReportsDetail.setVisibility(View.VISIBLE);
                } else {
                    TestReportsDetail.setVisibility(View.GONE);
                }

            }/*else if (statusList.get(i).SubID.equals(FEnumerations.QUALITY_PARAMETER_SUB_ID)) {
                if (statusList.get(i).numVal2.equals("0")) {
                    qualityParametersTab.setVisibility(View.VISIBLE);
                } else {
                    qualityParametersTab.setVisibility(View.GONE);
                }

            }*/else if (statusList.get(i).SubID.equals(FEnumerations.INTERNAL_TEST_SUB_ID)) {
                if (statusList.get(i).numVal2.equals("0")
                        || statusList.get(i).numVal2.equals("1")) {
                    productionStatusTab.setVisibility(View.VISIBLE);
                } else {
                    productionStatusTab.setVisibility(View.GONE);
                }

            }
        }
    }

    private void getPoListItem() {
        poItemDtl.LatestDelDt = POItemDtlHandler.getPOListItemLatestDelDate(this, inspectionModal.pRowID, poItemDtl);
//        poItemDtl.LatestDelDt = POItemDtlHandler.getPOListItemLatestDelDate(this, inspectionModal.QRHdrID, poItemDtl);
        Log.e("LatestDelDt","LatestDelDt="+poItemDtl.LatestDelDt);
        /*List<POItemDtl> listPoItem = POItemDtlHandler.getPOListItemLatestDelDate(this, inspectionModal.pRowID,poItemDtl);
        if (listPoItem.size() > 0) {
            for (int i = 0; i < listPoItem.size(); i++) {
                if (poItemDtl.pRowID.equals(listPoItem.get(i).pRowID)) {
                    poItemDtl = listPoItem.get(i);
                    Log.e("date2", poItemDtl.LatestDelDt);
                }
            }
        }*/
    }

    private void handleItemQty() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            Log.e("OrderQty",poItemDtl.OrderQty);
//            Log.e("DATE", "date==" + poItemDtl.LatestDelDt);   //previous date object parsed
            if(poItemDtl.LatestDelDt!=null){
                Date date = format.parse(poItemDtl.LatestDelDt);
                System.out.println(date);
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
                // use UTC as timezone
//                sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
                Log.e("DATE", sdf.format(date));   //previous date object parsed
                txtDelivaryDate.setText(sdf.format(date));
            }else {
                txtDelivaryDate.setText("N/A");
            }
//            editItemQtyRemark = (EditText) findViewById(R.id.editItemQtyRemark);
            txtOerderQty.setText(poItemDtl.OrderQty);
            txtAvlblQty.setText(poItemDtl.AvailableQty + "");
            txtAcceptedQty.setText(poItemDtl.AcceptedQty + "");
            txtShipVia.setText("");

//            Log.e("OrderQtyyyy",txtOerderQty.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

    private void initSampleCollectedTab() {
        addSampleDataIv = (ImageView) findViewById(R.id.addSampleDataIv);
        llSampleCollected = (LinearLayout) findViewById(R.id.llSampleCollected);
        llSampleCollected1 = (LinearLayout) findViewById(R.id.llSampleCollected1);
        llSampleCollected2 = (LinearLayout) findViewById(R.id.llSampleCollected2);
        llSampleCollected3 = (LinearLayout) findViewById(R.id.llSampleCollected3);
        llSampleCollected4 = (LinearLayout) findViewById(R.id.llSampleCollected4);

        txtSampleCollectedPurpose = (TextView) findViewById(R.id.txtSampleCollectedPurpose);
        txtSampleCollectedPurpose1 = (TextView) findViewById(R.id.txtSampleCollectedPurpose1);
        txtSampleCollectedPurpose2 = (TextView) findViewById(R.id.txtSampleCollectedPurpose2);
        txtSampleCollectedPurpose3 = (TextView) findViewById(R.id.txtSampleCollectedPurpose3);
        txtSampleCollectedPurpose4 = (TextView) findViewById(R.id.txtSampleCollectedPurpose4);

        txtSampleCollectedNumber = (EditText) findViewById(R.id.txtSampleCollectedNumber);
        txtSampleCollectedNumber1 = (EditText) findViewById(R.id.txtSampleCollectedNumber1);
        txtSampleCollectedNumber2 = (EditText) findViewById(R.id.txtSampleCollectedNumber2);
        txtSampleCollectedNumber3 = (EditText) findViewById(R.id.txtSampleCollectedNumber3);
        txtSampleCollectedNumber4 = (EditText) findViewById(R.id.txtSampleCollectedNumber4);

        txtSampleCollectedRemove = (TextView) findViewById(R.id.txtSampleCollectedRemove);
        txtSampleCollectedRemove1 = (TextView) findViewById(R.id.txtSampleCollectedRemove1);
        txtSampleCollectedRemove2 = (TextView) findViewById(R.id.txtSampleCollectedRemove2);
        txtSampleCollectedRemove3 = (TextView) findViewById(R.id.txtSampleCollectedRemove3);
        txtSampleCollectedRemove4 = (TextView) findViewById(R.id.txtSampleCollectedRemove4);
    }

    private void handleSampleCollectedMaster() {
        final List<GeneralModel> newMasterList = GeneralMasterHandler.
                getGeneralList(this, FEnumerations.SAMPLE_COLLECTED_OVERALL_RESULT_STATUS_GEN_ID);
        final List<SampleCollectedModal> sampleCollectedList = POItemDtlHandler.getSampleCollectedList(this);
        final List<GeneralModel> overAllResultStatusList = GeneralMasterHandler.
                getGeneralList(this, FEnumerations.SAMPLE_COLLECTED_OVERALL_RESULT_STATUS_GEN_ID);
        if (overAllResultStatusList != null && overAllResultStatusList.size() > 0) {

            List<String> list = new ArrayList<String>();

            for (int i = 0; i < overAllResultStatusList.size(); i++) {
                list.add(overAllResultStatusList.get(i).MainDescr);
            }
            GenUtils.showListDialog(ItemInspectionDetail.this,
                    "Select Inspection Level", list,
                    new GenUtils.ListDialogClickListener() {
                        @Override
                        public void onItemSelectedPos(int selectedItem) {
                            if (sampleCollectedList.size() > 0) {
                                for (int i = 0; i < sampleCollectedList.size(); i++) {
                                    for (int k = 0; k < overAllResultStatusList.size(); k++) {
                                        if (sampleCollectedList.get(i).pRowID.equals(overAllResultStatusList.get(k).pGenRowID)) {
                                            overAllResultStatusList.remove(k);
                                            k--;
                                        }
                                    }
                                }
                                Log.e("newMasterList", "newMasterList size=" + newMasterList.size());
                                Log.e("newMasterList", "overAllResultStatusList size=" + overAllResultStatusList.size());
                                Log.e("newMasterList", "sample selected master item=" + newMasterList.get(selectedItem).pGenRowID);
                                if (overAllResultStatusList.size() > 0) {
                                    for (int i = 0; i < overAllResultStatusList.size(); i++) {
                                        if (overAllResultStatusList.get(i).pGenRowID.equals(newMasterList.get(selectedItem).pGenRowID)) {
//                                            sampleCollectedModal.pRowID = newMasterList.get(selectedItem).pGenRowID;
                                            sampleCollectedModal.pRowID = ItemInspectionDetailHandler.GeneratePK(ItemInspectionDetail.this, FEnumerations.E_TABLE_NAME_QRPOItemDtl_Sample_Purpose);
                                            ;
                                            sampleCollectedModal.SamplePurposeID = newMasterList.get(selectedItem).pGenRowID;
//                                            sampleCollectedModal.LocID = newMasterList.get(selectedItem).LocID;
                                            sampleCollectedModal.SampleNumber = 0;
                                            insertSampleCollected(sampleCollectedModal);
                                            handleSampleCollectedTab();
                                            break;
                                        }
                                    }
                                }
                            } else {
//                                sampleCollectedModal.pRowID = overAllResultStatusList.get(selectedItem).pGenRowID;
                                sampleCollectedModal.pRowID = ItemInspectionDetailHandler.GeneratePK(ItemInspectionDetail.this, FEnumerations.E_TABLE_NAME_QRPOItemDtl_Sample_Purpose);
                                sampleCollectedModal.SamplePurposeID = overAllResultStatusList.get(selectedItem).pGenRowID;
//                                sampleCollectedModal.LocID = newMasterList.get(selectedItem).LocID;
                                sampleCollectedModal.SampleNumber = 0;
                                insertSampleCollected(sampleCollectedModal);
                                handleSampleCollectedTab();
                            }
                        }

                        @Override
                        public void onCancel() {
                        }
                    });
        }
    }

    private void handleSampleCollectedTab() {

        addSampleDataIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleSampleCollectedMaster();
            }
        });
        llSampleCollected.setVisibility(View.GONE);
        llSampleCollected1.setVisibility(View.GONE);
        llSampleCollected2.setVisibility(View.GONE);
        llSampleCollected3.setVisibility(View.GONE);
        llSampleCollected4.setVisibility(View.GONE);
        final List<SampleCollectedModal> sampleCollectedList = POItemDtlHandler.getSampleCollectedList(this);
        final List<GeneralModel> masterSampleCollectedLists = GeneralMasterHandler.
                getGeneralList(this, FEnumerations.SAMPLE_COLLECTED_OVERALL_RESULT_STATUS_GEN_ID);
        if (sampleCollectedList != null && sampleCollectedList.size() > 0) {
            for (int i = 0; i < sampleCollectedList.size(); i++) {

                if (i == 0) {
                    for (int k = 0; k < masterSampleCollectedLists.size(); k++) {
                        if (sampleCollectedList.get(i).SamplePurposeID.equals(masterSampleCollectedLists.get(k).pGenRowID)) {
                            llSampleCollected.setVisibility(View.VISIBLE);
                            txtSampleCollectedPurpose.setText(masterSampleCollectedLists.get(k).MainDescr);
                            txtSampleCollectedNumber.setText("" + sampleCollectedList.get(i).SampleNumber);
                        }
                    }

                } else if (i == 1) {
                    for (int k = 0; k < masterSampleCollectedLists.size(); k++) {
                        if (sampleCollectedList.get(i).SamplePurposeID.equals(masterSampleCollectedLists.get(k).pGenRowID)) {
                            llSampleCollected1.setVisibility(View.VISIBLE);
                            txtSampleCollectedPurpose1.setText(masterSampleCollectedLists.get(k).MainDescr);
                            txtSampleCollectedNumber1.setText("" + sampleCollectedList.get(i).SampleNumber);
                        }
                    }
                } else if (i == 2) {
                    for (int k = 0; k < masterSampleCollectedLists.size(); k++) {
                        if (sampleCollectedList.get(i).SamplePurposeID.equals(masterSampleCollectedLists.get(k).pGenRowID)) {
                            llSampleCollected2.setVisibility(View.VISIBLE);
                            txtSampleCollectedPurpose2.setText(masterSampleCollectedLists.get(i).MainDescr);
                            txtSampleCollectedNumber2.setText("" + sampleCollectedList.get(i).SampleNumber);
                        }
                    }
                } else if (i == 3) {
                    for (int k = 0; k < masterSampleCollectedLists.size(); k++) {
                        if (sampleCollectedList.get(i).SamplePurposeID.equals(masterSampleCollectedLists.get(k).pGenRowID)) {
                            llSampleCollected3.setVisibility(View.VISIBLE);
                            txtSampleCollectedPurpose3.setText(masterSampleCollectedLists.get(i).MainDescr);
                            txtSampleCollectedNumber3.setText("" + sampleCollectedList.get(i).SampleNumber);
                        }
                    }
                } else if (i == 4) {
                    for (int k = 0; k < masterSampleCollectedLists.size(); k++) {
                        if (sampleCollectedList.get(i).SamplePurposeID.equals(masterSampleCollectedLists.get(k).pGenRowID)) {
                            llSampleCollected4.setVisibility(View.VISIBLE);
                            txtSampleCollectedPurpose4.setText(masterSampleCollectedLists.get(i).MainDescr);
                            txtSampleCollectedNumber4.setText("" + sampleCollectedList.get(i).SampleNumber);
                        }
                    }
                }
            }
        } else {
            llSampleCollected.setVisibility(View.GONE);
            llSampleCollected1.setVisibility(View.GONE);
            llSampleCollected2.setVisibility(View.GONE);
            llSampleCollected3.setVisibility(View.GONE);
            llSampleCollected4.setVisibility(View.GONE);
        }
        txtSampleCollectedRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//              boolean isDeleted=  POItemDtlHandler.deleteSampleCollected(getApplicationContext(),sampleCollectedList.get(0).pRowID);
                if (POItemDtlHandler.deleteSampleCollected(getApplicationContext(), sampleCollectedList.get(0).pRowID)) {
                    handleSampleCollectedTab();
                }
            }
        });
        txtSampleCollectedRemove1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//              boolean isDeleted=  POItemDtlHandler.deleteSampleCollected(getApplicationContext(),sampleCollectedList.get(0).pRowID);
                if (POItemDtlHandler.deleteSampleCollected(getApplicationContext(), sampleCollectedList.get(1).pRowID)) {
                    handleSampleCollectedTab();
                }
            }
        });
        txtSampleCollectedRemove2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//              boolean isDeleted=  POItemDtlHandler.deleteSampleCollected(getApplicationContext(),sampleCollectedList.get(0).pRowID);
                if (POItemDtlHandler.deleteSampleCollected(getApplicationContext(), sampleCollectedList.get(2).pRowID)) {
                    handleSampleCollectedTab();
                }
            }
        });
        txtSampleCollectedRemove3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//              boolean isDeleted=  POItemDtlHandler.deleteSampleCollected(getApplicationContext(),sampleCollectedList.get(0).pRowID);
                if (POItemDtlHandler.deleteSampleCollected(getApplicationContext(), sampleCollectedList.get(3).pRowID)) {
                    handleSampleCollectedTab();
                }
            }
        });
        txtSampleCollectedRemove4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//              boolean isDeleted=  POItemDtlHandler.deleteSampleCollected(getApplicationContext(),sampleCollectedList.get(0).pRowID);
                if (POItemDtlHandler.deleteSampleCollected(getApplicationContext(), sampleCollectedList.get(4).pRowID)) {
                    handleSampleCollectedTab();
                }
            }
        });
    }

    private void handleOnSiteDesc() {
        final List<GeneralModel> newMasterList = GeneralMasterHandler.
                getGeneralList(this, FEnumerations.ONSITE_OVERALL_RESULT_STATUS_GEN_ID);
        final List<OnSIteModal> onSiteList = POItemDtlHandler.getOnSiteList(this);
        final List<GeneralModel> overAllResultStatusList = GeneralMasterHandler.
                getGeneralList(this, FEnumerations.ONSITE_OVERALL_RESULT_STATUS_GEN_ID);
        if (overAllResultStatusList != null && overAllResultStatusList.size() > 0) {

            List<String> list = new ArrayList<String>();

            for (int i = 0; i < overAllResultStatusList.size(); i++) {
                list.add(overAllResultStatusList.get(i).MainDescr);
            }
            GenUtils.showListDialog(ItemInspectionDetail.this,
                    "Select Inspection Level", list,
                    new GenUtils.ListDialogClickListener() {
                        @Override
                        public void onItemSelectedPos(int selectedItem) {
                            if (onSiteList.size() > 0) {
                                for (int i = 0; i < onSiteList.size(); i++) {
                                    for (int k = 0; k < overAllResultStatusList.size(); k++) {
                                        if (onSiteList.get(i).pRowID.equals(overAllResultStatusList.get(k).pGenRowID)) {
                                            overAllResultStatusList.remove(k);
                                            k--;
                                        }
                                    }
                                }
                                Log.e("newMasterList", "newMasterList size=" + newMasterList.size());
                                Log.e("newMasterList", "overAllResultStatusList size=" + overAllResultStatusList.size());
                                Log.e("newMasterList", "on site selected master item=" + newMasterList.get(selectedItem).pGenRowID);
                                Log.e("newMasterList", "on site selected master item=" + newMasterList.get(selectedItem).LocID);
                                if (overAllResultStatusList.size() > 0) {
                                    for (int i = 0; i < overAllResultStatusList.size(); i++) {
                                        if (overAllResultStatusList.get(i).pGenRowID.equals(newMasterList.get(selectedItem).pGenRowID)) {
                                            onSIteModal.pRowID = ItemInspectionDetailHandler.GeneratePK(ItemInspectionDetail.this, FEnumerations.E_TABLE_NAME_QRPOItemDtl_OnSite_Test);
                                            onSIteModal.InspectionLevelID = null;
                                            onSIteModal.InspectionResultID = null;
                                            onSIteModal.OnSiteTestID = overAllResultStatusList.get(selectedItem).pGenRowID;
                                            onSIteModal.SampleSizeID = null;
                                            onSIteModal.SampleSizeValue = null;
                                            insertOnSite(onSIteModal);
                                            handleOnSiteTab();
                                            break;
                                        }
                                    }
                                }
                            } else {
//                                onSIteModal.pRowID = overAllResultStatusList.get(selectedItem).pGenRowID;
                                onSIteModal.pRowID = ItemInspectionDetailHandler.GeneratePK(ItemInspectionDetail.this, FEnumerations.E_TABLE_NAME_QRPOItemDtl_OnSite_Test);
                                onSIteModal.OnSiteTestID = overAllResultStatusList.get(selectedItem).pGenRowID;
                                insertOnSite(onSIteModal);
                                handleOnSiteTab();
                            }
                        }

                        @Override
                        public void onCancel() {

                        }
                    });
        }
    }

    private void initializeOnSiteTab() {
        spinnerOnSiteOverAllResult = (Spinner) findViewById(R.id.spinnerOnSiteOverAllResult);
        addOnSiteDescIv = (ImageView) findViewById(R.id.addOnSiteDescIv);
        spinnerOnSiteDesc1 = (Spinner) findViewById(R.id.spinnerOnSiteDesc1);
        spinnerOnSiteDesc2 = (Spinner) findViewById(R.id.spinnerOnSiteDesc2);
        spinnerOnSiteDesc3 = (Spinner) findViewById(R.id.spinnerOnSitePkgAppearDesc3);
        spinnerOnSiteDesc4 = (Spinner) findViewById(R.id.spinnerOnSitePkgAppearDesc4);
        spinnerOnSiteDesc5 = (Spinner) findViewById(R.id.spinnerOnSitePkgAppearDesc5);
        spinnerOnSiteDesc6 = (Spinner) findViewById(R.id.spinnerOnSitePkgAppearDesc6);
        spinnerOnSiteDesc7 = (Spinner) findViewById(R.id.spinnerOnSitePkgAppearDesc7);
        spinnerOnSiteDesc8 = (Spinner) findViewById(R.id.spinnerOnSitePkgAppearDesc8);
        spinnerOnSiteDesc9 = (Spinner) findViewById(R.id.spinnerOnSitePkgAppearDesc9);
        spinnerOnSiteDesc10 = (Spinner) findViewById(R.id.spinnerOnSitePkgAppearDesc10);

        spinnerSampleSizeOnSiteDesc1 = (Spinner) findViewById(R.id.spinnerSampleSizeOnSiteDesc1);
        spinnerSampleSizeOnSiteDesc2 = (Spinner) findViewById(R.id.spinnerSampleSizeOnSiteDesc2);
        spinnerSampleSizeOnSiteDesc3 = (Spinner) findViewById(R.id.spinnerSampleSizeOnSiteDesc3);
        spinnerSampleSizeOnSiteDesc4 = (Spinner) findViewById(R.id.spinnerSampleSizeOnSiteDesc4);
        spinnerSampleSizeOnSiteDesc5 = (Spinner) findViewById(R.id.spinnerSampleSizeOnSiteDesc5);
        spinnerSampleSizeOnSiteDesc6 = (Spinner) findViewById(R.id.spinnerSampleSizeOnSiteDesc6);
        spinnerSampleSizeOnSiteDesc7 = (Spinner) findViewById(R.id.spinnerSampleSizeOnSiteDesc7);
        spinnerSampleSizeOnSiteDesc8 = (Spinner) findViewById(R.id.spinnerSampleSizeOnSiteDesc8);
        spinnerSampleSizeOnSiteDesc9 = (Spinner) findViewById(R.id.spinnerSampleSizeOnSiteDesc9);
        spinnerSampleSizeOnSiteDesc10 = (Spinner) findViewById(R.id.spinnerSampleSizeOnSiteDesc10);

        spinnerOnSiteOverAllDesc1 = (Spinner) findViewById(R.id.spinnerOnSiteOverAllDesc1);
        spinnerOnSiteOverAllDesc2 = (Spinner) findViewById(R.id.spinnerOnSiteOverAllDesc2);
        spinnerOnSiteOverAllDesc3 = (Spinner) findViewById(R.id.spinnerOnSiteOverAllDesc3);
        spinnerOnSiteOverAllDesc4 = (Spinner) findViewById(R.id.spinnerOnSiteOverAllDesc4);
        spinnerOnSiteOverAllDesc5 = (Spinner) findViewById(R.id.spinnerOnSiteOverAllDesc5);
        spinnerOnSiteOverAllDesc6 = (Spinner) findViewById(R.id.spinnerOnSiteOverAllDesc6);
        spinnerOnSiteOverAllDesc7 = (Spinner) findViewById(R.id.spinnerOnSiteOverAllDesc7);
        spinnerOnSiteOverAllDesc8 = (Spinner) findViewById(R.id.spinnerOnSiteOverAllDesc8);
        spinnerOnSiteOverAllDesc9 = (Spinner) findViewById(R.id.spinnerOnSiteOverAllDesc9);
        spinnerOnSiteOverAllDesc10 = (Spinner) findViewById(R.id.spinnerOnSiteOverAllDesc10);

        llOnSiteDesc1 = (LinearLayout) findViewById(R.id.llOnSiteDesc1);
        llOnSiteDesc2 = (LinearLayout) findViewById(R.id.llOnSiteDesc2);
        llOnSiteDesc3 = (LinearLayout) findViewById(R.id.llOnSiteDesc3);
        llOnSiteDesc4 = (LinearLayout) findViewById(R.id.llOnSiteDesc4);
        llOnSiteDesc5 = (LinearLayout) findViewById(R.id.llOnSiteDesc5);
        llOnSiteDesc6 = (LinearLayout) findViewById(R.id.llOnSiteDesc6);
        llOnSiteDesc7 = (LinearLayout) findViewById(R.id.llOnSiteDesc7);
        llOnSiteDesc8 = (LinearLayout) findViewById(R.id.llOnSiteDesc8);
        llOnSiteDesc9 = (LinearLayout) findViewById(R.id.llOnSiteDesc9);
        llOnSiteDesc10 = (LinearLayout) findViewById(R.id.llOnSiteDesc10);


        txtOnSiteDesc1 = (TextView) findViewById(R.id.txtOnSiteDesc1);
        txtOnSiteDesc2 = (TextView) findViewById(R.id.txtOnSiteDesc2);
        txtOnSiteDesc3 = (TextView) findViewById(R.id.txtOnSiteDesc3);
        txtOnSiteDesc4 = (TextView) findViewById(R.id.txtOnSiteDesc4);
        txtOnSiteDesc5 = (TextView) findViewById(R.id.txtOnSiteDesc5);
        txtOnSiteDesc6 = (TextView) findViewById(R.id.txtOnSiteDesc6);
        txtOnSiteDesc7 = (TextView) findViewById(R.id.txtOnSiteDesc7);
        txtOnSiteDesc8 = (TextView) findViewById(R.id.txtOnSiteDesc8);
        txtOnSiteDesc9 = (TextView) findViewById(R.id.txtOnSiteDesc9);
        txtOnSiteDesc10 = (TextView) findViewById(R.id.txtOnSiteDesc10);
    }

    private void handleDescriptionSpinner(List<OnSIteModal> onSiteList) {

        final List<InsLvHdrModal> insLvHdrModals = InsLvHdrHandler.getInsLvHdrList(this);

        Log.e("onsite list", "onSiteList=" + onSiteList.size());
        Log.e("insLvHdrModals list", "insLvHdrModals=" + insLvHdrModals.size());


        if (insLvHdrModals != null && insLvHdrModals.size() > 0) {
            List<String> list = new ArrayList<String>();

            for (int i = 0; i < insLvHdrModals.size(); i++) {
                list.add(insLvHdrModals.get(i).InspAbbrv);
            }
            //Creating the ArrayAdapter instance having the country list
            ArrayAdapter masterArrary = new ArrayAdapter(this, R.layout.dialog_list_item, list);
            masterArrary.setDropDownViewResource(R.layout.dialog_list_item);


            spinnerOnSiteDesc1.setOnTouchListener((v, me) -> {
                spinnerTouched = true;
                v.performClick();
                return false;
            });
            spinnerOnSiteDesc2.setOnTouchListener((v, me) -> {
                spinnerTouched = true;
                v.performClick();
                return false;
            });
            spinnerOnSiteDesc3.setOnTouchListener((v, me) -> {
                spinnerTouched = true;
                v.performClick();
                return false;
            });
            spinnerOnSiteDesc4.setOnTouchListener((v, me) -> {
                spinnerTouched = true;
                v.performClick();
                return false;
            });
            spinnerOnSiteDesc5.setOnTouchListener((v, me) -> {
                spinnerTouched = true;
                v.performClick();
                return false;
            });

            spinnerOnSiteDesc6.setOnTouchListener((v, me) -> {
                spinnerTouched = true;
                v.performClick();
                return false;
            });
            spinnerOnSiteDesc7.setOnTouchListener((v, me) -> {
                spinnerTouched = true;
                v.performClick();
                return false;
            });
            spinnerOnSiteDesc8.setOnTouchListener((v, me) -> {
                spinnerTouched = true;
                v.performClick();
                return false;
            });
            spinnerOnSiteDesc9.setOnTouchListener((v, me) -> {
                spinnerTouched = true;
                v.performClick();
                return false;
            });
            spinnerOnSiteDesc10.setOnTouchListener((v, me) -> {
                spinnerTouched = true;
                v.performClick();
                return false;
            });

            spinnerOnSiteDesc1.setAdapter(masterArrary);
            spinnerOnSiteDesc1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (onSiteList != null && onSiteList.size() > 0) {
                        if (spinnerTouched) {
                            spinnerTouched = false;
                            onSIteModal.InspectionLevelID = insLvHdrModals.get(i).pRowID;
                            onSIteModal.pRowID = onSiteList.get(0).pRowID;
                            Log.e("onItemSelected", "" + insLvHdrModals.get(i).pRowID);
                            Log.e("onItemSelected", "" + onSiteList.get(0).pRowID);
                            updateOnSite(onSIteModal);
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
            spinnerOnSiteDesc2.setAdapter(masterArrary);
//            spinnerOverAllMasterPackingPkgAppear.setSelection(selectedMPos);
            spinnerOnSiteDesc2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (onSiteList != null && onSiteList.size() > 0) {
                        if (spinnerTouched) {
                            spinnerTouched = false;
                            onSIteModal.InspectionLevelID = insLvHdrModals.get(i).pRowID;
                            onSIteModal.pRowID = onSiteList.get(1).pRowID;
                            updateOnSite(onSIteModal);
                        }
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
            spinnerOnSiteDesc3.setAdapter(masterArrary);
//            spinnerOverAllMasterPackingPkgAppear.setSelection(selectedMPos);
            spinnerOnSiteDesc3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (onSiteList != null && onSiteList.size() > 0) {
                        if (spinnerTouched) {
                            spinnerTouched = false;
                            onSIteModal.InspectionLevelID = insLvHdrModals.get(i).pRowID;
                            onSIteModal.pRowID = onSiteList.get(2).pRowID;
                            updateOnSite(onSIteModal);
                        }
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
            spinnerOnSiteDesc4.setAdapter(masterArrary);
//            spinnerOverAllMasterPackingPkgAppear.setSelection(selectedMPos);
            spinnerOnSiteDesc4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (onSiteList != null && onSiteList.size() > 0) {
                        if (spinnerTouched) {
                            spinnerTouched = false;
                            onSIteModal.InspectionLevelID = insLvHdrModals.get(i).pRowID;
                            onSIteModal.pRowID = onSiteList.get(3).pRowID;
                            updateOnSite(onSIteModal);
                        }
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
            spinnerOnSiteDesc5.setAdapter(masterArrary);
//            spinnerOverAllMasterPackingPkgAppear.setSelection(selectedMPos);
            spinnerOnSiteDesc5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (onSiteList != null && onSiteList.size() > 0) {
                        if (spinnerTouched) {
                            spinnerTouched = false;
                            onSIteModal.InspectionLevelID = insLvHdrModals.get(i).pRowID;
                            onSIteModal.pRowID = onSiteList.get(4).pRowID;
                            updateOnSite(onSIteModal);
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
            spinnerOnSiteDesc6.setAdapter(masterArrary);
//            spinnerOverAllMasterPackingPkgAppear.setSelection(selectedMPos);
            spinnerOnSiteDesc6.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (onSiteList != null && onSiteList.size() > 0) {
                        if (spinnerTouched) {
                            spinnerTouched = false;
                            onSIteModal.InspectionLevelID = insLvHdrModals.get(i).pRowID;
                            onSIteModal.pRowID = onSiteList.get(5).pRowID;
                            updateOnSite(onSIteModal);
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
            spinnerOnSiteDesc7.setAdapter(masterArrary);
//            spinnerOverAllMasterPackingPkgAppear.setSelection(selectedMPos);
            spinnerOnSiteDesc7.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (onSiteList != null && onSiteList.size() > 0) {
                        if (spinnerTouched) {
                            spinnerTouched = false;
                            onSIteModal.InspectionLevelID = insLvHdrModals.get(i).pRowID;
                            onSIteModal.pRowID = onSiteList.get(6).pRowID;
                            updateOnSite(onSIteModal);
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
            spinnerOnSiteDesc8.setAdapter(masterArrary);
//            spinnerOverAllMasterPackingPkgAppear.setSelection(selectedMPos);
            spinnerOnSiteDesc8.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (onSiteList != null && onSiteList.size() > 0) {
                        if (spinnerTouched) {
                            spinnerTouched = false;
                            onSIteModal.InspectionLevelID = insLvHdrModals.get(i).pRowID;
                            onSIteModal.pRowID = onSiteList.get(7).pRowID;
                            updateOnSite(onSIteModal);
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
            spinnerOnSiteDesc9.setAdapter(masterArrary);
//            spinnerOverAllMasterPackingPkgAppear.setSelection(selectedMPos);
            spinnerOnSiteDesc9.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (onSiteList != null && onSiteList.size() > 0) {
                        if (spinnerTouched) {
                            spinnerTouched = false;
                            onSIteModal.InspectionLevelID = insLvHdrModals.get(i).pRowID;
                            onSIteModal.pRowID = onSiteList.get(8).pRowID;
                            updateOnSite(onSIteModal);
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });

            spinnerOnSiteDesc10.setAdapter(masterArrary);
//            spinnerOverAllMasterPackingPkgAppear.setSelection(selectedMPos);
            spinnerOnSiteDesc10.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (onSiteList != null && onSiteList.size() > 0) {
                        if (spinnerTouched) {
                            spinnerTouched = false;
                            onSIteModal.InspectionLevelID = insLvHdrModals.get(i).pRowID;
                            onSIteModal.pRowID = onSiteList.get(9).pRowID;
                            updateOnSite(onSIteModal);
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });

            ///////////////////////
            if (insLvHdrModals != null && insLvHdrModals.size() > 0
                    && onSiteList != null && onSiteList.size() > 0) {

                for (int i = 0; i < onSiteList.size(); i++) {
                    if (i == 0) {
                        for (int j = 0; j < insLvHdrModals.size(); j++) {
                            if (onSiteList.get(i).InspectionLevelID != null &&
                                    onSiteList.get(i).InspectionLevelID.equals(insLvHdrModals.get(j).pRowID)) {
                                for (int k = 0; k < list.size(); k++) {
                                    if (insLvHdrModals.get(j).InspAbbrv.equals(list.get(k))) {
                                        spinnerOnSiteDesc1.setSelection(k);
                                    }
                                }
                            }
                        }
                    } else if (i == 1) {
                        for (int j = 0; j < insLvHdrModals.size(); j++) {
                            if (onSiteList.get(i).InspectionLevelID != null &&
                                    onSiteList.get(i).InspectionLevelID.equals(insLvHdrModals.get(j).pRowID)) {

                                for (int k = 0; k < list.size(); k++) {
                                    if (insLvHdrModals.get(j).InspAbbrv.equals(list.get(k))) {
                                        spinnerOnSiteDesc2.setSelection(k);
                                    }
                                }
                            }
                        }
                    } else if (i == 2) {
                        for (int j = 0; j < insLvHdrModals.size(); j++) {
                            if (onSiteList.get(i).InspectionLevelID != null &&
                                    onSiteList.get(i).InspectionLevelID.equals(insLvHdrModals.get(j).pRowID)) {
                                for (int k = 0; k < list.size(); k++) {
                                    if (insLvHdrModals.get(j).InspAbbrv.equals(list.get(k))) {
                                        spinnerOnSiteDesc3.setSelection(k);
                                    }
                                }
                            }
                        }
                    } else if (i == 3) {
                        for (int j = 0; j < insLvHdrModals.size(); j++) {
                            if (onSiteList.get(i).InspectionLevelID != null &&
                                    onSiteList.get(i).InspectionLevelID.equals(insLvHdrModals.get(j).pRowID)) {
                                for (int k = 0; k < list.size(); k++) {
                                    if (insLvHdrModals.get(j).InspAbbrv.equals(list.get(k))) {
                                        spinnerOnSiteDesc4.setSelection(k);
                                    }
                                }
                            }
                        }
                    } else if (i == 4) {
                        for (int j = 0; j < insLvHdrModals.size(); j++) {
                            if (onSiteList.get(i).InspectionLevelID != null &&
                                    onSiteList.get(i).InspectionLevelID.equals(insLvHdrModals.get(j).pRowID)) {
                                for (int k = 0; k < list.size(); k++) {
                                    if (insLvHdrModals.get(j).InspAbbrv.equals(list.get(k))) {
                                        spinnerOnSiteDesc5.setSelection(k);
                                    }
                                }
                            }
                        }
                    } else if (i == 5) {
                        for (int j = 0; j < insLvHdrModals.size(); j++) {
                            if (onSiteList.get(i).InspectionLevelID != null &&
                                    onSiteList.get(i).InspectionLevelID.equals(insLvHdrModals.get(j).pRowID)) {
                                for (int k = 0; k < list.size(); k++) {
                                    if (insLvHdrModals.get(j).InspAbbrv.equals(list.get(k))) {
                                        spinnerOnSiteDesc6.setSelection(k);
                                    }
                                }
                            }
                        }
                    } else if (i == 6) {
                        for (int j = 0; j < insLvHdrModals.size(); j++) {
                            if (onSiteList.get(i).InspectionLevelID != null &&
                                    onSiteList.get(i).InspectionLevelID.equals(insLvHdrModals.get(j).pRowID)) {
                                for (int k = 0; k < list.size(); k++) {
                                    if (insLvHdrModals.get(j).InspAbbrv.equals(list.get(k))) {
                                        spinnerOnSiteDesc7.setSelection(k);
                                    }
                                }
                            }
                        }
                    } else if (i == 7) {
                        for (int j = 0; j < insLvHdrModals.size(); j++) {
                            if (onSiteList.get(i).InspectionLevelID != null &&
                                    onSiteList.get(i).InspectionLevelID.equals(insLvHdrModals.get(j).pRowID)) {
                                for (int k = 0; k < list.size(); k++) {
                                    if (insLvHdrModals.get(j).InspAbbrv.equals(list.get(k))) {
                                        spinnerOnSiteDesc8.setSelection(k);
                                    }
                                }
                            }
                        }
                    } else if (i == 8) {
                        for (int j = 0; j < insLvHdrModals.size(); j++) {
                            if (onSiteList.get(i).InspectionLevelID != null &&
                                    onSiteList.get(i).InspectionLevelID.equals(insLvHdrModals.get(j).pRowID)) {
                                for (int k = 0; k < list.size(); k++) {
                                    if (insLvHdrModals.get(j).InspAbbrv.equals(list.get(k))) {
                                        spinnerOnSiteDesc9.setSelection(k);
                                    }
                                }
                            }
                        }
                    } else if (i == 9) {
                        for (int j = 0; j < insLvHdrModals.size(); j++) {
                            if (onSiteList.get(i).InspectionLevelID != null &&
                                    onSiteList.get(i).InspectionLevelID.equals(insLvHdrModals.get(j).pRowID)) {
                                for (int k = 0; k < list.size(); k++) {
                                    if (insLvHdrModals.get(j).InspAbbrv.equals(list.get(k))) {
                                        spinnerOnSiteDesc10.setSelection(k);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    private void handleOnSiteTab() {
        spinnerTouched = false;
        addOnSiteDescIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleOnSiteDesc();
            }
        });
        handlePackaging();
        updateOverAllResultOnsite();
        final List<OnSIteModal> onSiteList = POItemDtlHandler.getOnSiteList(this);
        handleDescriptionSpinner(onSiteList);
        handleSampleSizeSpinners(onSiteList);
        handleOnSiteOverAllResult(onSiteList);

        final List<GeneralModel> overAllResultStatusList = GeneralMasterHandler.getGeneralList(this, FEnumerations.ONSITE_OVERALL_RESULT_STATUS_GEN_ID);
        if (overAllResultStatusList != null && overAllResultStatusList.size() > 0) {
            if (onSiteList != null && onSiteList.size() > 0) {
                for (int i = 0; i < onSiteList.size(); i++) {
                    if (i == 0) {
//                    llOnSiteDesc1.setVisibility(View.VISIBLE);
//                    txtOnSiteDesc1.setText(onSiteList.get(i).OnSiteTestID);
                        for (int j = 0; j < overAllResultStatusList.size(); j++) {
                            if (onSiteList.get(i).OnSiteTestID.equals(overAllResultStatusList.get(j).pGenRowID)) {
                                llOnSiteDesc1.setVisibility(View.VISIBLE);
                                txtOnSiteDesc1.setText(overAllResultStatusList.get(i).MainDescr);
                            }
                        }
                    } else if (i == 1) {
//                    llOnSiteDesc2.setVisibility(View.VISIBLE);
//                    txtOnSiteDesc2.setText(onSiteList.get(i).OnSiteTestID);
                        for (int j = 0; j < overAllResultStatusList.size(); j++) {
                            if (onSiteList.get(i).OnSiteTestID.equals(overAllResultStatusList.get(j).pGenRowID)) {
                                llOnSiteDesc2.setVisibility(View.VISIBLE);
                                txtOnSiteDesc2.setText(overAllResultStatusList.get(i).MainDescr);
                            }
                        }
                        llOnSiteDesc2.setVisibility(View.VISIBLE);
                        txtOnSiteDesc2.setText(onSiteList.get(i).OnSiteTestID);
                    } else if (i == 2) {
                        for (int j = 0; j < overAllResultStatusList.size(); j++) {
                            if (onSiteList.get(i).OnSiteTestID.equals(overAllResultStatusList.get(j).pGenRowID)) {
                                llOnSiteDesc3.setVisibility(View.VISIBLE);
                                txtOnSiteDesc3.setText(overAllResultStatusList.get(i).MainDescr);
                            }
                        }
//                    llOnSiteDesc3.setVisibility(View.VISIBLE);
//                    txtOnSiteDesc3.setText(onSiteList.get(i).OnSiteTestID);
                    } else if (i == 3) {
                        for (int j = 0; j < overAllResultStatusList.size(); j++) {
                            if (onSiteList.get(i).OnSiteTestID.equals(overAllResultStatusList.get(j).pGenRowID)) {
                                llOnSiteDesc4.setVisibility(View.VISIBLE);
                                txtOnSiteDesc4.setText(overAllResultStatusList.get(i).MainDescr);
                            }
                        }
//                    llOnSiteDesc4.setVisibility(View.VISIBLE);
//                    txtOnSiteDesc4.setText(onSiteList.get(i).OnSiteTestID);
                    } else if (i == 4) {
                        for (int j = 0; j < overAllResultStatusList.size(); j++) {
                            if (onSiteList.get(i).OnSiteTestID.equals(overAllResultStatusList.get(j).pGenRowID)) {
                                llOnSiteDesc5.setVisibility(View.VISIBLE);
                                txtOnSiteDesc5.setText(overAllResultStatusList.get(i).MainDescr);
                            }
                        }
//                    llOnSiteDesc5.setVisibility(View.VISIBLE);
//                    txtOnSiteDesc5.setText(onSiteList.get(i).OnSiteTestID);
                    } else if (i == 5) {
                        for (int j = 0; j < overAllResultStatusList.size(); j++) {
                            if (onSiteList.get(i).OnSiteTestID.equals(overAllResultStatusList.get(j).pGenRowID)) {
                                llOnSiteDesc6.setVisibility(View.VISIBLE);
                                txtOnSiteDesc6.setText(overAllResultStatusList.get(i).MainDescr);
                            }
                        }
//                    llOnSiteDesc6.setVisibility(View.VISIBLE);
//                    txtOnSiteDesc6.setText(onSiteList.get(i).OnSiteTestID);
                    } else if (i == 6) {
                        for (int j = 0; j < overAllResultStatusList.size(); j++) {
                            if (onSiteList.get(i).OnSiteTestID.equals(overAllResultStatusList.get(j).pGenRowID)) {
                                llOnSiteDesc7.setVisibility(View.VISIBLE);
                                txtOnSiteDesc7.setText(overAllResultStatusList.get(i).MainDescr);
                            }
                        }
//                    llOnSiteDesc7.setVisibility(View.VISIBLE);
//                    txtOnSiteDesc7.setText(onSiteList.get(i).OnSiteTestID);
                    } else if (i == 7) {
                        for (int j = 0; j < overAllResultStatusList.size(); j++) {
                            if (onSiteList.get(i).OnSiteTestID.equals(overAllResultStatusList.get(j).pGenRowID)) {
                                llOnSiteDesc8.setVisibility(View.VISIBLE);
                                txtOnSiteDesc8.setText(overAllResultStatusList.get(i).MainDescr);
                            }
                        }
//                    llOnSiteDesc8.setVisibility(View.VISIBLE);
//                    txtOnSiteDesc8.setText(onSiteList.get(i).OnSiteTestID);
                    } else if (i == 8) {
                        for (int j = 0; j < overAllResultStatusList.size(); j++) {
                            if (onSiteList.get(i).OnSiteTestID.equals(overAllResultStatusList.get(j).pGenRowID)) {
                                llOnSiteDesc9.setVisibility(View.VISIBLE);
                                txtOnSiteDesc9.setText(overAllResultStatusList.get(i).MainDescr);
                            }
                        }
//                    llOnSiteDesc9.setVisibility(View.VISIBLE);
//                    txtOnSiteDesc9.setText(onSiteList.get(i).OnSiteTestID);
                    } else if (i == 9) {
                        for (int j = 0; j < overAllResultStatusList.size(); j++) {
                            if (onSiteList.get(i).OnSiteTestID.equals(overAllResultStatusList.get(j).pGenRowID)) {
                                llOnSiteDesc10.setVisibility(View.VISIBLE);
                                txtOnSiteDesc10.setText(overAllResultStatusList.get(i).MainDescr);
                            }
                        }
//                    llOnSiteDesc10.setVisibility(View.VISIBLE);
//                    txtOnSiteDesc10.setText(onSiteList.get(i).OnSiteTestID);
                    }
                }
            }

        }
    }

    private void handleOnSiteOverAllResult(List<OnSIteModal> onSiteList) {
        spinnerOnSiteOverAllTouched = true;
        List<String> statusList1 = new ArrayList<>();
        final List<GeneralModel> overAllResultStatusLists = GeneralMasterHandler.getGeneralList(this, FEnumerations.E_OVERALL_RESULT_STATUS_GEN_ID);


        if (overAllResultStatusLists != null && overAllResultStatusLists.size() > 0) {
            for (int i = 0; i < overAllResultStatusLists.size(); i++) {
                statusList1.add(overAllResultStatusLists.get(i).MainDescr);
            }

            //Creating the ArrayAdapter instance having the country list
            ArrayAdapter masterArrary = new ArrayAdapter(this, R.layout.dialog_list_item, statusList1);
            masterArrary.setDropDownViewResource(R.layout.dialog_list_item);

            //Setting the ArrayAdapter data on the Spinner
            spinnerOnSiteOverAllDesc1.setAdapter(masterArrary);

            spinnerOnSiteOverAllDesc1.setOnTouchListener((v, me) -> {
                spinnerOnSiteOverAllTouched = true;
                v.performClick();
                return false;
            });
            spinnerOnSiteOverAllDesc2.setOnTouchListener((v, me) -> {
                spinnerOnSiteOverAllTouched = true;
                v.performClick();
                return false;
            });
            spinnerOnSiteOverAllDesc3.setOnTouchListener((v, me) -> {
                spinnerOnSiteOverAllTouched = true;
                v.performClick();
                return false;
            });
            spinnerOnSiteOverAllDesc4.setOnTouchListener((v, me) -> {
                spinnerOnSiteOverAllTouched = true;
                v.performClick();
                return false;
            });
            spinnerOnSiteOverAllDesc5.setOnTouchListener((v, me) -> {
                spinnerOnSiteOverAllTouched = true;
                v.performClick();
                return false;
            });
            spinnerOnSiteOverAllDesc6.setOnTouchListener((v, me) -> {
                spinnerOnSiteOverAllTouched = true;
                v.performClick();
                return false;
            });
            spinnerOnSiteOverAllDesc7.setOnTouchListener((v, me) -> {
                spinnerOnSiteOverAllTouched = true;
                v.performClick();
                return false;
            });
            spinnerOnSiteOverAllDesc8.setOnTouchListener((v, me) -> {
                spinnerOnSiteOverAllTouched = true;
                v.performClick();
                return false;
            });
            spinnerOnSiteOverAllDesc9.setOnTouchListener((v, me) -> {
                spinnerOnSiteOverAllTouched = true;
                v.performClick();
                return false;
            });
            spinnerOnSiteOverAllDesc10.setOnTouchListener((v, me) -> {
                spinnerOnSiteOverAllTouched = true;
                v.performClick();
                return false;
            });

            spinnerOnSiteOverAllDesc1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (!spinnerOnSiteOverAllTouched) {
                        return;
                    }
                    spinnerOnSiteOverAllTouched = false;
                    onSIteModal.InspectionResultID = overAllResultStatusLists.get(i).pGenRowID;
                    onSIteModal.pRowID = onSiteList.get(0).pRowID;
                    if (onSiteList.get(0).InspectionLevelID != null) {
                        onSIteModal.InspectionLevelID = onSiteList.get(0).InspectionLevelID;
                    }
                    if (onSiteList.get(0).SampleSizeID != null) {
                        onSIteModal.SampleSizeID = onSiteList.get(0).SampleSizeID;
                        onSIteModal.SampleSizeValue = onSiteList.get(0).SampleSizeValue;
                    }

                    updateOnSite(onSIteModal);
                    updateOverAllResultOnsite();//comment for testing by shekhar
                    handleOverAllResult();//comment for testing by shekhar

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
            spinnerOnSiteOverAllDesc2.setAdapter(masterArrary);
            spinnerOnSiteOverAllDesc2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (!spinnerOnSiteOverAllTouched) {
                        return;
                    }
                    spinnerOnSiteOverAllTouched = false;
                    onSIteModal.InspectionResultID = overAllResultStatusLists.get(i).pGenRowID;
                    updateOnSite(onSIteModal);
                    updateOverAllResultOnsite();//comment for testing by shekhar
                    handleOverAllResult();//comment for testing by shekhar
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
            spinnerOnSiteOverAllDesc3.setAdapter(masterArrary);
            spinnerOnSiteOverAllDesc3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (!spinnerOnSiteOverAllTouched) {
                        return;
                    }
                    spinnerOnSiteOverAllTouched = false;
                    onSIteModal.InspectionResultID = overAllResultStatusLists.get(i).pGenRowID;
                    updateOnSite(onSIteModal);
                    updateOverAllResultOnsite();//comment for testing by shekhar
                    handleOverAllResult();//comment for testing by shekhar

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
            spinnerOnSiteOverAllDesc4.setAdapter(masterArrary);
            spinnerOnSiteOverAllDesc4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (!spinnerOnSiteOverAllTouched) {
                        return;
                    }
                    spinnerOnSiteOverAllTouched = false;
                    onSIteModal.InspectionResultID = overAllResultStatusLists.get(i).pGenRowID;
                    updateOnSite(onSIteModal);
                    updateOverAllResultOnsite();//comment for testing by shekhar
                    handleOverAllResult();//comment for testing by shekhar

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
            spinnerOnSiteOverAllDesc5.setAdapter(masterArrary);
            spinnerOnSiteOverAllDesc5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (!spinnerOnSiteOverAllTouched) {
                        return;
                    }
                    spinnerOnSiteOverAllTouched = false;
                    onSIteModal.InspectionResultID = overAllResultStatusLists.get(i).pGenRowID;
                    updateOnSite(onSIteModal);
                    updateOverAllResultOnsite();//comment for testing by shekhar
                    handleOverAllResult();//comment for testing by shekhar

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
            spinnerOnSiteOverAllDesc6.setAdapter(masterArrary);
            spinnerOnSiteOverAllDesc6.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (!spinnerOnSiteOverAllTouched) {
                        return;
                    }
                    spinnerOnSiteOverAllTouched = false;
                    onSIteModal.InspectionResultID = overAllResultStatusLists.get(i).pGenRowID;
                    updateOnSite(onSIteModal);
                    updateOverAllResultOnsite();//comment for testing by shekhar
                    handleOverAllResult();//comment for testing by shekhar

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
            spinnerOnSiteOverAllDesc7.setAdapter(masterArrary);
            spinnerOnSiteOverAllDesc7.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (!spinnerOnSiteOverAllTouched) {
                        return;
                    }
                    spinnerOnSiteOverAllTouched = false;
                    onSIteModal.InspectionResultID = overAllResultStatusLists.get(i).pGenRowID;
                    updateOnSite(onSIteModal);
                    updateOverAllResultOnsite();//comment for testing by shekhar
                    handleOverAllResult();//comment for testing by shekhar

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
            spinnerOnSiteOverAllDesc8.setAdapter(masterArrary);
            spinnerOnSiteOverAllDesc8.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (!spinnerOnSiteOverAllTouched) {
                        return;
                    }
                    spinnerOnSiteOverAllTouched = false;
                    onSIteModal.InspectionResultID = overAllResultStatusLists.get(i).pGenRowID;
                    updateOnSite(onSIteModal);
                    updateOverAllResultOnsite();//comment for testing by shekhar
                    handleOverAllResult();//comment for testing by shekhar

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
            spinnerOnSiteOverAllDesc9.setAdapter(masterArrary);
            spinnerOnSiteOverAllDesc9.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (!spinnerOnSiteOverAllTouched) {
                        return;
                    }
                    spinnerOnSiteOverAllTouched = false;
                    onSIteModal.InspectionResultID = overAllResultStatusLists.get(i).pGenRowID;
                    updateOnSite(onSIteModal);
                    updateOverAllResultOnsite();//comment for testing by shekhar
                    handleOverAllResult();//comment for testing by shekhar

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
            spinnerOnSiteOverAllDesc10.setAdapter(masterArrary);
            spinnerOnSiteOverAllDesc10.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (!spinnerOnSiteOverAllTouched) {
                        return;
                    }
                    spinnerOnSiteOverAllTouched = false;
                    onSIteModal.InspectionResultID = overAllResultStatusLists.get(i).pGenRowID;
                    updateOnSite(onSIteModal);
                    updateOverAllResultOnsite();//comment for testing by shekhar
                    handleOverAllResult();//comment for testing by shekhar
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
        }

        if (overAllResultStatusLists != null && overAllResultStatusLists.size() > 0
                && onSiteList != null && onSiteList.size() > 0) {

            for (int i = 0; i < onSiteList.size(); i++) {
                if (i == 0) {
                    for (int j = 0; j < overAllResultStatusLists.size(); j++) {
                        if (onSiteList.get(i).InspectionResultID != null &&
                                onSiteList.get(i).InspectionResultID.equals(overAllResultStatusLists.get(j).pGenRowID)) {
                            for (int k = 0; k < statusList1.size(); k++) {
                                if (overAllResultStatusLists.get(j).MainDescr.equals(statusList1.get(k))) {
                                    spinnerOnSiteOverAllDesc1.setSelection(k);
                                }
                            }
                        }
                    }
                } else if (i == 1) {
                    for (int j = 0; j < overAllResultStatusLists.size(); j++) {
                        if (onSiteList.get(i).InspectionResultID != null &&
                                onSiteList.get(i).InspectionResultID.equals(overAllResultStatusLists.get(j).pGenRowID)) {
                            for (int k = 0; k < statusList1.size(); k++) {
                                if (overAllResultStatusLists.get(j).MainDescr.equals(statusList1.get(k))) {
                                    spinnerOnSiteOverAllDesc2.setSelection(k);
                                }
                            }
                        }
                    }
                } else if (i == 2) {
                    for (int j = 0; j < overAllResultStatusLists.size(); j++) {
                        if (onSiteList.get(i).InspectionResultID != null &&
                                onSiteList.get(i).InspectionResultID.equals(overAllResultStatusLists.get(j).pGenRowID)) {
                            for (int k = 0; k < statusList1.size(); k++) {
                                if (overAllResultStatusLists.get(j).MainDescr.equals(statusList1.get(k))) {
                                    spinnerOnSiteOverAllDesc3.setSelection(k);
                                }
                            }
                        }
                    }
                } else if (i == 3) {
                    for (int j = 0; j < overAllResultStatusLists.size(); j++) {
                        if (onSiteList.get(i).InspectionResultID != null &&
                                onSiteList.get(i).InspectionResultID.equals(overAllResultStatusLists.get(j).pGenRowID)) {
                            for (int k = 0; k < statusList1.size(); k++) {
                                if (overAllResultStatusLists.get(j).MainDescr.equals(statusList1.get(k))) {
                                    spinnerOnSiteOverAllDesc4.setSelection(k);
                                }
                            }
                        }
                    }
                } else if (i == 4) {
                    for (int j = 0; j < overAllResultStatusLists.size(); j++) {
                        if (onSiteList.get(i).InspectionResultID != null &&
                                onSiteList.get(i).InspectionResultID.equals(overAllResultStatusLists.get(j).pGenRowID)) {
                            for (int k = 0; k < statusList1.size(); k++) {
                                if (overAllResultStatusLists.get(j).MainDescr.equals(statusList1.get(k))) {
                                    spinnerOnSiteOverAllDesc5.setSelection(k);
                                }
                            }
                        }
                    }
                } else if (i == 5) {
                    for (int j = 0; j < overAllResultStatusLists.size(); j++) {
                        if (onSiteList.get(i).InspectionResultID != null &&
                                onSiteList.get(i).InspectionResultID.equals(overAllResultStatusLists.get(j).pGenRowID)) {
                            for (int k = 0; k < statusList1.size(); k++) {
                                if (overAllResultStatusLists.get(j).MainDescr.equals(statusList1.get(k))) {
                                    spinnerOnSiteOverAllDesc6.setSelection(k);
                                }
                            }
                        }
                    }
                } else if (i == 6) {
                    for (int j = 0; j < overAllResultStatusLists.size(); j++) {
                        if (onSiteList.get(i).InspectionResultID != null &&
                                onSiteList.get(i).InspectionResultID.equals(overAllResultStatusLists.get(j).pGenRowID)) {
                            for (int k = 0; k < statusList1.size(); k++) {
                                if (overAllResultStatusLists.get(j).MainDescr.equals(statusList1.get(k))) {
                                    spinnerOnSiteOverAllDesc7.setSelection(k);
                                }
                            }
                        }
                    }
                } else if (i == 7) {
                    for (int j = 0; j < overAllResultStatusLists.size(); j++) {
                        if (onSiteList.get(i).InspectionResultID != null &&
                                onSiteList.get(i).InspectionResultID.equals(overAllResultStatusLists.get(j).pGenRowID)) {
                            for (int k = 0; k < statusList1.size(); k++) {
                                if (overAllResultStatusLists.get(j).MainDescr.equals(statusList1.get(k))) {
                                    spinnerOnSiteOverAllDesc8.setSelection(k);
                                }
                            }
                        }
                    }
                } else if (i == 8) {
                    for (int j = 0; j < overAllResultStatusLists.size(); j++) {
                        if (onSiteList.get(i).InspectionResultID != null &&
                                onSiteList.get(i).InspectionResultID.equals(overAllResultStatusLists.get(j).pGenRowID)) {
                            for (int k = 0; k < statusList1.size(); k++) {
                                if (overAllResultStatusLists.get(j).MainDescr.equals(statusList1.get(k))) {
                                    spinnerOnSiteOverAllDesc9.setSelection(k);
                                }
                            }
                        }
                    }
                } else if (i == 9) {
                    for (int j = 0; j < overAllResultStatusLists.size(); j++) {
                        if (onSiteList.get(i).InspectionResultID != null &&
                                onSiteList.get(i).InspectionResultID.equals(overAllResultStatusLists.get(j).pGenRowID)) {
                            for (int k = 0; k < statusList1.size(); k++) {
                                if (overAllResultStatusLists.get(j).MainDescr.equals(statusList1.get(k))) {
                                    spinnerOnSiteOverAllDesc10.setSelection(k);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void handleSampleSizeSpinners(List<OnSIteModal> onSiteList) {
        spinnerOnSiteSampleSizeTouched = false;
        final List<SampleModal> sampleModals = POItemDtlHandler.getSampleSizeList(this);

        List<String> sampleList = new ArrayList<>();
        if (sampleModals != null && sampleModals.size() > 0) {
            for (int i = 0; i < sampleModals.size(); i++) {
                sampleList.add(sampleModals.get(i).MainDescr + "(" + sampleModals.get(i).SampleVal + ")");
            }

            ArrayAdapter sampleUnitArray = new ArrayAdapter(this, R.layout.dialog_list_item, sampleList);
            sampleUnitArray.setDropDownViewResource(R.layout.dialog_list_item);

            spinnerSampleSizeOnSiteDesc1.setOnTouchListener((v, me) -> {
                spinnerOnSiteSampleSizeTouched = true;
                v.performClick();
                return false;
            });
            spinnerSampleSizeOnSiteDesc2.setOnTouchListener((v, me) -> {
                spinnerOnSiteSampleSizeTouched = true;
                v.performClick();
                return false;
            });
            spinnerSampleSizeOnSiteDesc3.setOnTouchListener((v, me) -> {
                spinnerOnSiteSampleSizeTouched = true;
                v.performClick();
                return false;
            });
            spinnerSampleSizeOnSiteDesc4.setOnTouchListener((v, me) -> {
                spinnerOnSiteSampleSizeTouched = true;
                v.performClick();
                return false;
            });
            spinnerSampleSizeOnSiteDesc5.setOnTouchListener((v, me) -> {
                spinnerOnSiteSampleSizeTouched = true;
                v.performClick();
                return false;
            });

            spinnerSampleSizeOnSiteDesc6.setOnTouchListener((v, me) -> {
                spinnerOnSiteSampleSizeTouched = true;
                v.performClick();
                return false;
            });
            spinnerSampleSizeOnSiteDesc7.setOnTouchListener((v, me) -> {
                spinnerOnSiteSampleSizeTouched = true;
                v.performClick();
                return false;
            });
            spinnerSampleSizeOnSiteDesc8.setOnTouchListener((v, me) -> {
                spinnerOnSiteSampleSizeTouched = true;
                v.performClick();
                return false;
            });
            spinnerSampleSizeOnSiteDesc9.setOnTouchListener((v, me) -> {
                spinnerOnSiteSampleSizeTouched = true;
                v.performClick();
                return false;
            });
            spinnerSampleSizeOnSiteDesc10.setOnTouchListener((v, me) -> {
                spinnerOnSiteSampleSizeTouched = true;
                v.performClick();
                return false;
            });

            //Setting the ArrayAdapter data on the Spinner
            spinnerSampleSizeOnSiteDesc1.setAdapter(sampleUnitArray);
            spinnerSampleSizeOnSiteDesc1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (onSiteList != null && onSiteList.size() > 0) {
                        if (!spinnerOnSiteSampleSizeTouched) {
                            return;
                        }
                        spinnerOnSiteSampleSizeTouched = false;
                        onSIteModal.pRowID = onSiteList.get(0).pRowID;
                        if (onSiteList.get(0).InspectionLevelID != null) {
                            onSIteModal.InspectionLevelID = onSiteList.get(0).InspectionLevelID;
                        }
                        onSIteModal.SampleSizeID = sampleModals.get(i).SampleCode;
                        onSIteModal.SampleSizeValue = sampleModals.get(i).SampleVal;
                        updateOnSite(onSIteModal);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            spinnerSampleSizeOnSiteDesc2.setAdapter(sampleUnitArray);
            spinnerSampleSizeOnSiteDesc2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (onSiteList != null && onSiteList.size() > 0) {
                        if (!spinnerOnSiteSampleSizeTouched) {
                            return;
                        }
                        spinnerOnSiteSampleSizeTouched = false;
                        onSIteModal.pRowID = onSiteList.get(1).pRowID;
                        if (onSiteList.get(1).InspectionLevelID != null) {
                            onSIteModal.InspectionLevelID = onSiteList.get(1).InspectionLevelID;
                        }
                        onSIteModal.SampleSizeID = sampleModals.get(i).SampleCode;
                        onSIteModal.SampleSizeValue = sampleModals.get(i).SampleVal;
                        updateOnSite(onSIteModal);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            spinnerSampleSizeOnSiteDesc3.setAdapter(sampleUnitArray);
            spinnerSampleSizeOnSiteDesc3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (onSiteList != null && onSiteList.size() > 0) {
                        if (!spinnerOnSiteSampleSizeTouched) {
                            return;
                        }
                        spinnerOnSiteSampleSizeTouched = false;
                        onSIteModal.pRowID = onSiteList.get(2).pRowID;
                        if (onSiteList.get(2).InspectionLevelID != null) {
                            onSIteModal.InspectionLevelID = onSiteList.get(2).InspectionLevelID;
                        }
                        onSIteModal.SampleSizeID = sampleModals.get(i).SampleCode;
                        onSIteModal.SampleSizeValue = sampleModals.get(i).SampleVal;
                        updateOnSite(onSIteModal);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            spinnerSampleSizeOnSiteDesc4.setAdapter(sampleUnitArray);
            spinnerSampleSizeOnSiteDesc4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (onSiteList != null && onSiteList.size() > 0) {
                        if (!spinnerOnSiteSampleSizeTouched) {
                            return;
                        }
                        spinnerOnSiteSampleSizeTouched = false;
                        onSIteModal.pRowID = onSiteList.get(3).pRowID;
                        if (onSiteList.get(3).InspectionLevelID != null) {
                            onSIteModal.InspectionLevelID = onSiteList.get(3).InspectionLevelID;
                        }
                        onSIteModal.SampleSizeID = sampleModals.get(i).SampleCode;
                        onSIteModal.SampleSizeValue = sampleModals.get(i).SampleVal;
                        updateOnSite(onSIteModal);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            spinnerSampleSizeOnSiteDesc5.setAdapter(sampleUnitArray);
            spinnerSampleSizeOnSiteDesc5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (onSiteList != null && onSiteList.size() > 0) {
                        if (!spinnerOnSiteSampleSizeTouched) {
                            return;
                        }
                        spinnerOnSiteSampleSizeTouched = false;
                        onSIteModal.pRowID = onSiteList.get(4).pRowID;
                        if (onSiteList.get(4).InspectionLevelID != null) {
                            onSIteModal.InspectionLevelID = onSiteList.get(4).InspectionLevelID;
                        }
                        onSIteModal.SampleSizeID = sampleModals.get(i).SampleCode;
                        onSIteModal.SampleSizeValue = sampleModals.get(i).SampleVal;
                        updateOnSite(onSIteModal);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            spinnerSampleSizeOnSiteDesc6.setAdapter(sampleUnitArray);
//            spinnerSampleSizeUnitPkgAppearPacking.setSelection(selectedUnitPos);//commented by shekjhar
            spinnerSampleSizeOnSiteDesc6.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (onSiteList != null && onSiteList.size() > 0) {
                        if (!spinnerOnSiteSampleSizeTouched) {
                            return;
                        }
                        spinnerOnSiteSampleSizeTouched = false;
                        onSIteModal.pRowID = onSiteList.get(5).pRowID;
                        if (onSiteList.get(5).InspectionLevelID != null) {
                            onSIteModal.InspectionLevelID = onSiteList.get(5).InspectionLevelID;
                        }
                        onSIteModal.SampleSizeID = sampleModals.get(i).SampleCode;
                        onSIteModal.SampleSizeValue = sampleModals.get(i).SampleVal;
                        updateOnSite(onSIteModal);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            spinnerSampleSizeOnSiteDesc7.setAdapter(sampleUnitArray);
            spinnerSampleSizeOnSiteDesc7.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (onSiteList != null && onSiteList.size() > 0) {
                        if (!spinnerOnSiteSampleSizeTouched) {
                            return;
                        }
                        spinnerOnSiteSampleSizeTouched = false;
                        onSIteModal.pRowID = onSiteList.get(6).pRowID;
                        if (onSiteList.get(6).InspectionLevelID != null) {
                            onSIteModal.InspectionLevelID = onSiteList.get(6).InspectionLevelID;
                        }
                        onSIteModal.SampleSizeID = sampleModals.get(i).SampleCode;
                        onSIteModal.SampleSizeValue = sampleModals.get(i).SampleVal;
                        updateOnSite(onSIteModal);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            spinnerSampleSizeOnSiteDesc8.setAdapter(sampleUnitArray);
            spinnerSampleSizeOnSiteDesc8.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (onSiteList != null && onSiteList.size() > 0) {
                        if (!spinnerOnSiteSampleSizeTouched) {
                            return;
                        }
                        spinnerOnSiteSampleSizeTouched = false;
                        onSIteModal.pRowID = onSiteList.get(7).pRowID;
                        if (onSiteList.get(7).InspectionLevelID != null) {
                            onSIteModal.InspectionLevelID = onSiteList.get(7).InspectionLevelID;
                        }
                        onSIteModal.SampleSizeID = sampleModals.get(i).SampleCode;
                        onSIteModal.SampleSizeValue = sampleModals.get(i).SampleVal;
                        updateOnSite(onSIteModal);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            spinnerSampleSizeOnSiteDesc9.setAdapter(sampleUnitArray);
            spinnerSampleSizeOnSiteDesc9.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (onSiteList != null && onSiteList.size() > 0) {
                        if (!spinnerOnSiteSampleSizeTouched) {
                            return;
                        }
                        spinnerOnSiteSampleSizeTouched = false;
                        onSIteModal.pRowID = onSiteList.get(8).pRowID;
                        if (onSiteList.get(8).InspectionLevelID != null) {
                            onSIteModal.InspectionLevelID = onSiteList.get(8).InspectionLevelID;
                        }
                        onSIteModal.SampleSizeID = sampleModals.get(i).SampleCode;
                        onSIteModal.SampleSizeValue = sampleModals.get(i).SampleVal;
                        updateOnSite(onSIteModal);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            spinnerSampleSizeOnSiteDesc10.setAdapter(sampleUnitArray);
            spinnerSampleSizeOnSiteDesc10.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (onSiteList != null && onSiteList.size() > 0) {
                        if (!spinnerOnSiteSampleSizeTouched) {
                            return;
                        }
                        spinnerOnSiteSampleSizeTouched = false;
                        onSIteModal.pRowID = onSiteList.get(9).pRowID;
                        if (onSiteList.get(9).InspectionLevelID != null) {
                            onSIteModal.InspectionLevelID = onSiteList.get(9).InspectionLevelID;
                        }
                        onSIteModal.SampleSizeID = sampleModals.get(i).SampleCode;
                        onSIteModal.SampleSizeValue = sampleModals.get(i).SampleVal;
                        updateOnSite(onSIteModal);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }
        if (onSiteList != null && onSiteList.size() > 0) {

            for (int i = 0; i < onSiteList.size(); i++) {
                if (i == 0) {
                    for (int j = 0; j < sampleModals.size(); j++) {
                        if (onSiteList.get(i).SampleSizeID != null &&
                                onSiteList.get(i).SampleSizeID.equals(sampleModals.get(j).SampleCode)) {
                            for (int k = 0; k < sampleList.size(); k++) {
                                String valStr = sampleModals.get(j).MainDescr + "(" + sampleModals.get(j).SampleVal + ")";
                                if (valStr.equals(sampleList.get(k))) {
                                    spinnerSampleSizeOnSiteDesc1.setSelection(k);
                                }
                            }
                        }
                    }
                } else if (i == 1) {
                    for (int j = 0; j < sampleModals.size(); j++) {
                        if (onSiteList.get(i).SampleSizeID != null &&
                                onSiteList.get(i).SampleSizeID.equals(sampleModals.get(j).SampleCode)) {
                            for (int k = 0; k < sampleList.size(); k++) {
                                String valStr = sampleModals.get(j).MainDescr + "(" + sampleModals.get(j).SampleVal + ")";
                                if (valStr.equals(sampleList.get(k))) {
                                    spinnerSampleSizeOnSiteDesc2.setSelection(k);
                                }
                            }
                        }
                    }
                } else if (i == 2) {
                    for (int j = 0; j < sampleModals.size(); j++) {
                        if (onSiteList.get(i).SampleSizeID != null &&
                                onSiteList.get(i).SampleSizeID.equals(sampleModals.get(j).SampleCode)) {
                            for (int k = 0; k < sampleList.size(); k++) {
                                String valStr = sampleModals.get(j).MainDescr + "(" + sampleModals.get(j).SampleVal + ")";
                                if (valStr.equals(sampleList.get(k))) {
                                    spinnerSampleSizeOnSiteDesc3.setSelection(k);
                                }
                            }
                        }
                    }
                } else if (i == 3) {
                    for (int j = 0; j < sampleModals.size(); j++) {
                        if (onSiteList.get(i).SampleSizeID != null &&
                                onSiteList.get(i).SampleSizeID.equals(sampleModals.get(j).SampleCode)) {
                            for (int k = 0; k < sampleList.size(); k++) {
                                String valStr = sampleModals.get(j).MainDescr + "(" + sampleModals.get(j).SampleVal + ")";
                                if (valStr.equals(sampleList.get(k))) {
                                    spinnerSampleSizeOnSiteDesc4.setSelection(k);
                                }
                            }
                        }
                    }
                } else if (i == 4) {
                    for (int j = 0; j < sampleModals.size(); j++) {
                        if (onSiteList.get(i).SampleSizeID != null &&
                                onSiteList.get(i).SampleSizeID.equals(sampleModals.get(j).SampleCode)) {
                            for (int k = 0; k < sampleList.size(); k++) {
                                String valStr = sampleModals.get(j).MainDescr + "(" + sampleModals.get(j).SampleVal + ")";
                                if (valStr.equals(sampleList.get(k))) {
                                    spinnerSampleSizeOnSiteDesc5.setSelection(k);
                                }
                            }
                        }
                    }
                } else if (i == 5) {
                    for (int j = 0; j < sampleModals.size(); j++) {
                        if (onSiteList.get(i).SampleSizeID != null &&
                                onSiteList.get(i).SampleSizeID.equals(sampleModals.get(j).SampleCode)) {
                            for (int k = 0; k < sampleList.size(); k++) {
                                String valStr = sampleModals.get(j).MainDescr + "(" + sampleModals.get(j).SampleVal + ")";
                                if (valStr.equals(sampleList.get(k))) {
                                    spinnerSampleSizeOnSiteDesc6.setSelection(k);
                                }
                            }
                        }
                    }
                } else if (i == 6) {
                    for (int j = 0; j < sampleModals.size(); j++) {
                        if (onSiteList.get(i).SampleSizeID != null &&
                                onSiteList.get(i).SampleSizeID.equals(sampleModals.get(j).SampleCode)) {
                            for (int k = 0; k < sampleList.size(); k++) {
                                String valStr = sampleModals.get(j).MainDescr + "(" + sampleModals.get(j).SampleVal + ")";
                                if (valStr.equals(sampleList.get(k))) {
                                    spinnerSampleSizeOnSiteDesc7.setSelection(k);
                                }
                            }
                        }
                    }
                } else if (i == 7) {
                    for (int j = 0; j < sampleModals.size(); j++) {
                        if (onSiteList.get(i).SampleSizeID != null &&
                                onSiteList.get(i).SampleSizeID.equals(sampleModals.get(j).SampleCode)) {
                            for (int k = 0; k < sampleList.size(); k++) {
                                String valStr = sampleModals.get(j).MainDescr + "(" + sampleModals.get(j).SampleVal + ")";
                                if (valStr.equals(sampleList.get(k))) {
                                    spinnerSampleSizeOnSiteDesc8.setSelection(k);
                                }
                            }
                        }
                    }
                } else if (i == 8) {
                    for (int j = 0; j < sampleModals.size(); j++) {
                        if (onSiteList.get(i).SampleSizeID != null &&
                                onSiteList.get(i).SampleSizeID.equals(sampleModals.get(j).SampleCode)) {
                            for (int k = 0; k < sampleList.size(); k++) {
                                String valStr = sampleModals.get(j).MainDescr + "(" + sampleModals.get(j).SampleVal + ")";
                                if (valStr.equals(sampleList.get(k))) {
                                    spinnerSampleSizeOnSiteDesc9.setSelection(k);
                                }
                            }
                        }
                    }
                } else if (i == 9) {
                    for (int j = 0; j < sampleModals.size(); j++) {
                        if (onSiteList.get(i).SampleSizeID != null &&
                                onSiteList.get(i).SampleSizeID.equals(sampleModals.get(j).SampleCode)) {
                            for (int k = 0; k < sampleList.size(); k++) {
                                String valStr = sampleModals.get(j).MainDescr + "(" + sampleModals.get(j).SampleVal + ")";
                                if (valStr.equals(sampleList.get(k))) {
                                    spinnerSampleSizeOnSiteDesc10.setSelection(k);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void initItemQty(){
        txtDelivaryDate = (TextView) findViewById(R.id.txtDelivaryDate);
        txtShipVia = (TextView) findViewById(R.id.txtShipVia);
        txtOerderQty = (TextView) findViewById(R.id.txtOerderQty);
        txtAvlblQty = (TextView) findViewById(R.id.txtAvlblQty);
        txtAcceptedQty = (TextView) findViewById(R.id.txtAcceptedQty);
    }
    private void initBarcodeTab() {
        spinnerOverAllResultBarcode = (Spinner) findViewById(R.id.spinnerOverAllResultBarcode);
        spinnerSampleSizeUnitPackingBarcode = (Spinner) findViewById(R.id.spinnerSampleSizeUnitPackingBarcode);
        spinnerSampleSizeInnerPackingBarcode = (Spinner) findViewById(R.id.spinnerSampleSizeInnerPackingBarcode);
        spinnerSampleSizeMasterPackingBarcode = (Spinner) findViewById(R.id.spinnerSampleSizeMasterPackingBarcode);
        spinnerSampleSizePalletPackingBarcode = (Spinner) findViewById(R.id.spinnerSampleSizePalletPackingBarcode);

        txtResultSpinnerUnitPackingBarcode = (Spinner) findViewById(R.id.txtResultSpinnerUnitPackingBarcode);
        txtResultSpinnerInnerPackingBarcode = (Spinner) findViewById(R.id.txtResultSpinnerInnerPackingBarcode);
        txtResultSpinnerMasterPackingBarcode = (Spinner) findViewById(R.id.txtResultSpinnerMasterPackingBarcode);
        txtResultSpinnerPalletPackingBarcode = (Spinner) findViewById(R.id.txtResultSpinnerPalletPackingBarcode);


        editPackingBarcodeRemark = (EditText) findViewById(R.id.editPackingBarcodeRemark);
        txtVisulLabelBarcode = (EditText) findViewById(R.id.txtVisulLabelBarcode);
        txtScanLabelBarcode = (EditText) findViewById(R.id.txtScanLabelBarcode);
        txtVisulLabelInnerPackingBarcode = (EditText) findViewById(R.id.txtVisulLabelInnerPackingBarcode);
        txtScanLabelInnerPackingBarcode = (EditText) findViewById(R.id.txtScanLabelInnerPackingBarcode);
        txtVisulLabelMasterPackingBarcode = (EditText) findViewById(R.id.txtVisulLabelMasterPackingBarcode);
        txtScanLabelMasterPackingBarcode = (EditText) findViewById(R.id.txtScanLabelMasterPackingBarcode);
        txtVisulLabelPalletPackingBarcode = (EditText) findViewById(R.id.txtVisulLabelPalletPackingBarcode);
        txtScanLabelPalletPackingBarcode = (EditText) findViewById(R.id.txtScanLabelPalletPackingBarcode);


        txtSpecificationLabelBarcode = (TextView) findViewById(R.id.txtSpecificationLabelBarcode);
        txtSpecificationLabelInnerPackingBarcode = (TextView) findViewById(R.id.txtSpecificationLabelInnerPackingBarcode);
        txtSpecificationLabelMasterPackingBarcode = (TextView) findViewById(R.id.txtSpecificationLabelMasterPackingBarcode);
        txtSpecificationLabelPalletPackingBarcode = (TextView) findViewById(R.id.txtSpecificationLabelPalletPackingBarcode);
    }

    private void handleBarcodeTab() {

        handlePackaging();

        handleSampleSizeBarcodeSpinner();

        handleOverAllResultBarcodeSpinner();

        updateOverResultBarcode();
        handleBarcodeVisual();
        updateBarcodeUI();
    }

    private void handleBarcodeVisual() {
        //set unit visual
        if (packagePoItemDetalDetail.Barcode_Unit_Visual != null &&
                !TextUtils.isEmpty(packagePoItemDetalDetail.Barcode_Unit_Visual)
                && !packagePoItemDetalDetail.Barcode_Unit_Visual.equals("null")) {
            txtVisulLabelBarcode.setText(packagePoItemDetalDetail.Barcode_Unit_Visual);
            txtSpecificationLabelBarcode.setText(packagePoItemDetalDetail.Barcode_Unit_Visual);
        }

        //set inner visual
        if (packagePoItemDetalDetail.Barcode_Inner_Visual != null &&
                !TextUtils.isEmpty(packagePoItemDetalDetail.Barcode_Inner_Visual)
                && !packagePoItemDetalDetail.Barcode_Inner_Visual.equals("null")) {
            txtVisulLabelInnerPackingBarcode.setText(packagePoItemDetalDetail.Barcode_Inner_Visual);
            txtSpecificationLabelInnerPackingBarcode.setText(packagePoItemDetalDetail.Barcode_Inner_Visual);
        }
        //set master visual
        if (packagePoItemDetalDetail.Barcode_Master_Visual != null &&
                !TextUtils.isEmpty(packagePoItemDetalDetail.Barcode_Master_Visual)
                && !packagePoItemDetalDetail.Barcode_Master_Visual.equals("null")) {
            txtVisulLabelMasterPackingBarcode.setText(packagePoItemDetalDetail.Barcode_Master_Visual);
            txtSpecificationLabelMasterPackingBarcode.setText(packagePoItemDetalDetail.Barcode_Master_Visual);
        }
        //set pallet visual
        if (packagePoItemDetalDetail.Barcode_Pallet_Visual != null &&
                !TextUtils.isEmpty(packagePoItemDetalDetail.Barcode_Pallet_Visual)
                && !packagePoItemDetalDetail.Barcode_Pallet_Visual.equals("null")) {
            txtVisulLabelPalletPackingBarcode.setText(packagePoItemDetalDetail.Barcode_Pallet_Visual);
            txtSpecificationLabelPalletPackingBarcode.setText(packagePoItemDetalDetail.Barcode_Pallet_Visual);
        }

        //set unt scan
        if (packagePoItemDetalDetail.Barcode_Unit_Scan != null &&
                !TextUtils.isEmpty(packagePoItemDetalDetail.Barcode_Unit_Scan)
                && !packagePoItemDetalDetail.Barcode_Unit_Scan.equals("null")) {
            txtScanLabelBarcode.setText(packagePoItemDetalDetail.Barcode_Unit_Scan);
        }

        //set unt scan
        if (packagePoItemDetalDetail.Barcode_Unit_Scan != null &&
                !TextUtils.isEmpty(packagePoItemDetalDetail.Barcode_Unit_Scan)
                && !packagePoItemDetalDetail.Barcode_Unit_Scan.equals("null")) {
            txtScanLabelBarcode.setText(packagePoItemDetalDetail.Barcode_Unit_Scan);
        }
        //set inner scan
        if (packagePoItemDetalDetail.Barcode_Inner_Scan != null &&
                !TextUtils.isEmpty(packagePoItemDetalDetail.Barcode_Inner_Scan)
                && !packagePoItemDetalDetail.Barcode_Inner_Scan.equals("null")) {
            txtScanLabelInnerPackingBarcode.setText(packagePoItemDetalDetail.Barcode_Inner_Scan);
        }
        //set master scan
        if (packagePoItemDetalDetail.Barcode_Master_Scan != null &&
                !TextUtils.isEmpty(packagePoItemDetalDetail.Barcode_Master_Scan)
                && !packagePoItemDetalDetail.Barcode_Master_Scan.equals("null")) {
            txtScanLabelMasterPackingBarcode.setText(packagePoItemDetalDetail.Barcode_Master_Scan);
        }
        //set pallet scan
        if (packagePoItemDetalDetail.Barcode_Pallet_Scan != null &&
                !TextUtils.isEmpty(packagePoItemDetalDetail.Barcode_Pallet_Scan)
                && !packagePoItemDetalDetail.Barcode_Pallet_Scan.equals("null")) {
            txtScanLabelPalletPackingBarcode.setText(packagePoItemDetalDetail.Barcode_Pallet_Scan);
        }


    }

    private void handleOverAllResultBarcodeSpinner() {

        List<String> statusList = new ArrayList<>();
        final List<GeneralModel> overAllResultStatusList = GeneralMasterHandler.getGeneralList(this, FEnumerations.E_OVERALL_RESULT_STATUS_GEN_ID);
        if (overAllResultStatusList != null && overAllResultStatusList.size() > 0) {
            int selectedMPos = 0, selectedIPos = 0, selectedUPos = 0, selectedPPos = 0, selectedOPos = 0;
            for (int i = 0; i < overAllResultStatusList.size(); i++) {
                statusList.add(overAllResultStatusList.get(i).MainDescr);
                if (overAllResultStatusList.get(i).pGenRowID.equals(packagePoItemDetalDetail.Barcode_Master_InspectionResultID)) {
                    selectedMPos = i;
                }
                if (overAllResultStatusList.get(i).pGenRowID.equals(packagePoItemDetalDetail.Barcode_Inner_InspectionResultID)) {
                    selectedIPos = i;
                }
                if (overAllResultStatusList.get(i).pGenRowID.equals(packagePoItemDetalDetail.Barcode_Unit_InspectionResultID)) {
                    selectedUPos = i;
                }
                if (overAllResultStatusList.get(i).pGenRowID.equals(packagePoItemDetalDetail.Barcode_Pallet_InspectionResultID)) {
                    selectedPPos = i;
                }
                if (overAllResultStatusList.get(i).pGenRowID.equals(packagePoItemDetalDetail.Barcode_InspectionResultID)) {
                    selectedOPos = i;
                }

            }


            //Creating the ArrayAdapter instance having the country list
            ArrayAdapter masterArrary = new ArrayAdapter(this, R.layout.dialog_list_item, statusList);
            masterArrary.setDropDownViewResource(R.layout.dialog_list_item);
            //Setting the ArrayAdapter data on the Spinner
            txtResultSpinnerMasterPackingBarcode.setAdapter(masterArrary);
            txtResultSpinnerMasterPackingBarcode.setSelection(selectedMPos);
            txtResultSpinnerMasterPackingBarcode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    packagePoItemDetalDetail.Barcode_Master_InspectionResultID = overAllResultStatusList.get(i).pGenRowID;
                    if (packagePoItemDetalDetail.Barcode_Master_InspectionResultID.equals(FEnumerations.E_OVER_ALL_FAIL_RESULT)) {
                        packagePoItemDetalDetail.Barcode_InspectionResultID = packagePoItemDetalDetail.Barcode_Master_InspectionResultID;
                        updateOverResultBarcode();
                        handleOverAllResult();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });


            ArrayAdapter innerArrary = new ArrayAdapter(this, R.layout.dialog_list_item, statusList);
            innerArrary.setDropDownViewResource(R.layout.dialog_list_item);
            //Setting the ArrayAdapter data on the Spinner

            txtResultSpinnerInnerPackingBarcode.setAdapter(innerArrary);
            txtResultSpinnerInnerPackingBarcode.setSelection(selectedIPos);
            txtResultSpinnerInnerPackingBarcode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    packagePoItemDetalDetail.Barcode_Inner_InspectionResultID = overAllResultStatusList.get(i).pGenRowID;
                    if (packagePoItemDetalDetail.Barcode_Inner_InspectionResultID.equals(FEnumerations.E_OVER_ALL_FAIL_RESULT)) {
                        packagePoItemDetalDetail.Barcode_InspectionResultID = packagePoItemDetalDetail.Barcode_Inner_InspectionResultID;
                        updateOverResultBarcode();
                        handleOverAllResult();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });


            ArrayAdapter unitArrary = new ArrayAdapter(this, R.layout.dialog_list_item, statusList);
            unitArrary.setDropDownViewResource(R.layout.dialog_list_item);
            //Setting the ArrayAdapter data on the Spinner

            txtResultSpinnerUnitPackingBarcode.setAdapter(unitArrary);
            txtResultSpinnerUnitPackingBarcode.setSelection(selectedUPos);
            txtResultSpinnerUnitPackingBarcode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    packagePoItemDetalDetail.Barcode_Unit_InspectionResultID = overAllResultStatusList.get(i).pGenRowID;
                    if (packagePoItemDetalDetail.Barcode_Unit_InspectionResultID.equals(FEnumerations.E_OVER_ALL_FAIL_RESULT)) {
                        packagePoItemDetalDetail.Barcode_InspectionResultID = packagePoItemDetalDetail.Barcode_Unit_InspectionResultID;
                        updateOverResultBarcode();
                        handleOverAllResult();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            ArrayAdapter palletArrary = new ArrayAdapter(this, R.layout.dialog_list_item, statusList);
            palletArrary.setDropDownViewResource(R.layout.dialog_list_item);
            //Setting the ArrayAdapter data on the Spinner

            txtResultSpinnerPalletPackingBarcode.setAdapter(palletArrary);
            txtResultSpinnerPalletPackingBarcode.setSelection(selectedPPos);
            txtResultSpinnerPalletPackingBarcode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    packagePoItemDetalDetail.Barcode_Pallet_InspectionResultID = overAllResultStatusList.get(i).pGenRowID;
                    if (packagePoItemDetalDetail.Barcode_Pallet_InspectionResultID.equals(FEnumerations.E_OVER_ALL_FAIL_RESULT)) {
                        packagePoItemDetalDetail.Barcode_InspectionResultID = packagePoItemDetalDetail.Barcode_Pallet_InspectionResultID;
                        updateOverResultBarcode();
                        handleOverAllResult();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            updateOverResultBarcode();
        }
    }

    private void handleSampleSizeBarcodeSpinner() {
        final List<SampleModal> sampleModals = POItemDtlHandler.getSampleSizeList(this);
        List<String> sampleList = new ArrayList<>();
        if (sampleModals != null && sampleModals.size() > 0) {

            int selectedUnitPos = 0, selectedInnerPos = 0, selectedMasterPos = 0, selectedPalletPos = 0, selectedOPos = 0;
            for (int i = 0; i < sampleModals.size(); i++) {
                sampleList.add(sampleModals.get(i).MainDescr + "(" + sampleModals.get(i).SampleVal + ")");
                if (sampleModals.get(i).SampleCode.equals(packagePoItemDetalDetail.Barcode_Master_SampleSizeID)) {
                    selectedMasterPos = i;
                }
                if (sampleModals.get(i).SampleCode.equals(packagePoItemDetalDetail.Barcode_Inner_SampleSizeID)) {
                    selectedInnerPos = i;
                }
                if (sampleModals.get(i).SampleCode.equals(packagePoItemDetalDetail.Barcode_Unit_SampleSizeID)) {
                    selectedUnitPos = i;
                }
                if (sampleModals.get(i).SampleCode.equals(packagePoItemDetalDetail.Barcode_Pallet_SampleSizeID)) {
                    selectedPalletPos = i;
                }
                if (sampleModals.get(i).SampleCode.equals(packagePoItemDetalDetail.Barcode_InspectionLevelID)) {
                    selectedOPos = i;
                }

            }

            //Creating the ArrayAdapter instance having the country list
            ArrayAdapter sampleUnitArrary = new ArrayAdapter(this, R.layout.dialog_list_item, sampleList);
            sampleUnitArrary.setDropDownViewResource(R.layout.dialog_list_item);
            //Setting the ArrayAdapter data on the Spinner
            spinnerSampleSizeUnitPackingBarcode.setAdapter(sampleUnitArrary);
            spinnerSampleSizeUnitPackingBarcode.setSelection(selectedUnitPos);
            spinnerSampleSizeUnitPackingBarcode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    packagePoItemDetalDetail.Barcode_Unit_SampleSizeID = sampleModals.get(i).SampleCode;
//                    itemMeasurementModal.SampleSizeValue = sampleModals.get(i).SampleVal;
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });


            //Creating the ArrayAdapter instance having the country list
            ArrayAdapter sampleInnerArrary = new ArrayAdapter(this, R.layout.dialog_list_item, sampleList);
            sampleInnerArrary.setDropDownViewResource(R.layout.dialog_list_item);
            //Setting the ArrayAdapter data on the Spinner
            spinnerSampleSizeInnerPackingBarcode.setAdapter(sampleInnerArrary);
            spinnerSampleSizeInnerPackingBarcode.setSelection(selectedInnerPos);
            spinnerSampleSizeInnerPackingBarcode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    packagePoItemDetalDetail.Barcode_Inner_SampleSizeID = sampleModals.get(i).SampleCode;
//                    itemMeasurementModal.SampleSizeValue = sampleModals.get(i).SampleVal;
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });


            //Creating the ArrayAdapter instance having the country list
            ArrayAdapter sampleMasterArrary = new ArrayAdapter(this, R.layout.dialog_list_item, sampleList);
            sampleMasterArrary.setDropDownViewResource(R.layout.dialog_list_item);
            //Setting the ArrayAdapter data on the Spinner
            spinnerSampleSizeMasterPackingBarcode.setAdapter(sampleMasterArrary);
            spinnerSampleSizeMasterPackingBarcode.setSelection(selectedMasterPos);
            spinnerSampleSizeMasterPackingBarcode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    packagePoItemDetalDetail.Barcode_Master_SampleSizeID = sampleModals.get(i).SampleCode;
//                    itemMeasurementModal.SampleSizeID = sampleModals.get(i).SampleCode;
//                    itemMeasurementModal.SampleSizeValue = sampleModals.get(i).SampleVal;
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            //Creating the ArrayAdapter instance having the country list
            ArrayAdapter samplePalletArrary = new ArrayAdapter(this, R.layout.dialog_list_item, sampleList);
            samplePalletArrary.setDropDownViewResource(R.layout.dialog_list_item);
            //Setting the ArrayAdapter data on the Spinner
            spinnerSampleSizePalletPackingBarcode.setAdapter(samplePalletArrary);
            spinnerSampleSizePalletPackingBarcode.setSelection(selectedPalletPos);
            spinnerSampleSizePalletPackingBarcode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    packagePoItemDetalDetail.Barcode_Pallet_SampleSizeID = sampleModals.get(i).SampleCode;
//                    itemMeasurementModal.SampleSizeValue = sampleModals.get(i).SampleVal;
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }
    }

    private void handleHoleOverAllResult() {
        if (spinnerHoleOverAllResult == null)
            spinnerHoleOverAllResult = (Spinner) findViewById(R.id.spinnerHoleOverAllResult);

        List<String> statusList = new ArrayList<>();
        final List<GeneralModel> overAllResultStatusList = GeneralMasterHandler.getGeneralList(this, FEnumerations.E_OVERALL_RESULT_STATUS_GEN_ID);
        if (overAllResultStatusList != null && overAllResultStatusList.size() > 0) {
            int selectedMPos = 0;
            for (int i = 0; i < overAllResultStatusList.size(); i++) {
                statusList.add(overAllResultStatusList.get(i).MainDescr);
                if (overAllResultStatusList.get(i).pGenRowID.equals(packagePoItemDetalDetail.Overall_InspectionResultID)) {
                    selectedMPos = i;
                }

            }


            //Creating the ArrayAdapter instance having the country list
            ArrayAdapter masterArrary = new ArrayAdapter(this, R.layout.dialog_list_item, statusList);
            masterArrary.setDropDownViewResource(R.layout.dialog_list_item);
            //Setting the ArrayAdapter data on the Spinner
            spinnerHoleOverAllResult.setAdapter(masterArrary);
            spinnerHoleOverAllResult.setSelection(selectedMPos);
            spinnerHoleOverAllResult.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    packagePoItemDetalDetail.Overall_InspectionResultID = overAllResultStatusList.get(i).pGenRowID;
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }
    }

    private void showFirstTab() {
//        showQualityParameterTab();
        showItemQtyTab();
    }

    private void showItemQtyTab() {

        WorkmanshipVisualInspectionDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
        txtWorkmanshipVisualInspectionDetail.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

        PackingMeasurementDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
        txtPackingMeasurementDetail.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

        itemMeasurementDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
        txtItemMeasurement.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

        DigitalsUploadedDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
        txtDigitalsUploaded.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

        TestReportsDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
        txtTestReports.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

        EnclosureDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
        txtEnclosure.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

        HistoryDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
        txtHistory.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

        productionStatusTab.setBackgroundColor(getResources().getColor(R.color.white));
        txtProductionStatus.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

        qualityParametersTab.setBackgroundColor(getResources().getColor(R.color.white));
        txtQualityParameters.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

        packagingAppearanceLL.setBackgroundColor(getResources().getColor(R.color.white));
        txtPackagingAppearance.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

        addBarcodeLL.setBackgroundColor(getResources().getColor(R.color.white));
        txtAddBarcode.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

        onSiteTestLL.setBackgroundColor(getResources().getColor(R.color.white));
        txtOnSiteTest.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

        itemQtyLL.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        txtItemQty.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));

        itemSampleCollectedLL.setBackgroundColor(getResources().getColor(R.color.white));
        txtSampleCollected.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));
        viewFlipper.setDisplayedChild(12);
//        handleItemQty();
    }


    private void handleToInitiateInternalTest() {


        recyclerViewInternalTest = (RecyclerView) findViewById(R.id.recyclerViewInternalTest);
        recyclerViewInternalTest.setHasFixedSize(true);
        recyclerViewInternalTest.addItemDecoration(new DividerItemDecoration(ItemInspectionDetail.this,
                DividerItemDecoration.VERTICAL));

        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(ItemInspectionDetail.this);
        recyclerViewInternalTest.setLayoutManager(mLayoutManager);
        updatInternalTestUi();
    }

    private void handleToInitiateQualityParameter() {

        recyclerViewQualityParameter = (RecyclerView) findViewById(R.id.recyclerViewQualityParameter);
        recyclerViewQualityParameter.setHasFixedSize(true);
        recyclerViewQualityParameter.addItemDecoration(new DividerItemDecoration(ItemInspectionDetail.this,
                DividerItemDecoration.VERTICAL));

        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(ItemInspectionDetail.this);
        recyclerViewQualityParameter.setLayoutManager(mLayoutManager);
        updateQualityParameterUi();
    }

    private void remarkHandle() {
        packingRemarkContainer = (LinearLayout) findViewById(R.id.packingRemarkContainer);
        editPackingRemark = (EditText) findViewById(R.id.editPackingRemark);
        itemMeasurementRemarkContainer = (LinearLayout) findViewById(R.id.itemMeasurementRemarkContainer);
        workmanShipRemarkContainer = (LinearLayout) findViewById(R.id.workmanShipRemarkContainer);
        editWorkmanshipRemark = (EditText) findViewById(R.id.editWorkmanshipRemark);
        editItemMeasurementRemark = (EditText) findViewById(R.id.editItemMeasurementRemark);

    }

    private void intPkgAppTab() {
        spinnerPjgAppearOverAllResult = (Spinner) findViewById(R.id.spinnerPjgAppearOverAllResult);
        spinnerSampleSizeUnitPkgAppearPacking = (Spinner) findViewById(R.id.spinnerSampleSizeUnitPkgAppearPacking);
        spinnerOverAllUnitPackingPkgAppear = (Spinner) findViewById(R.id.spinnerOverAllUnitPackingPkgAppear);
        spinnerSampleSizeShippingMarkPkgAppearPacking = (Spinner) findViewById(R.id.spinnerSampleSizeShippingMarkPkgAppearPacking);
        spinnerOverAllShippingMarkPkgAppear = (Spinner) findViewById(R.id.spinnerOverAllShippingMarkPkgAppear);
        spinnerSampleSizeInnerPackingPkgAppearPacking = (Spinner) findViewById(R.id.spinnerSampleSizeInnerPackingPkgAppearPacking);
        spinnerOverAllInnerPackingPkgAppear = (Spinner) findViewById(R.id.spinnerOverAllInnerPackingPkgAppear);
        spinnerSampleSizeMasterPackingPkgAppearPacking = (Spinner) findViewById(R.id.spinnerSampleSizeMasterPackingPkgAppearPacking);
        spinnerOverAllMasterPackingPkgAppear = (Spinner) findViewById(R.id.spinnerOverAllMasterPackingPkgAppear);
        spinnerSampleSizePalletPackingPkgAppearPacking = (Spinner) findViewById(R.id.spinnerSampleSizePalletPackingPkgAppearPacking);
        spinnerOverAllPalletPackingPkgAppear = (Spinner) findViewById(R.id.spinnerOverAllPalletPackingPkgAppear);


        spinnerSampleSizePkgAppearDesc1 = (Spinner) findViewById(R.id.spinnerSampleSizePkgAppearDesc1);
        spinnerSampleSizePkgAppearDesc2 = (Spinner) findViewById(R.id.spinnerSampleSizePkgAppearDesc2);
        spinnerSampleSizePkgAppearDesc3 = (Spinner) findViewById(R.id.spinnerSampleSizePkgAppearDesc3);
        spinnerSampleSizePkgAppearDesc4 = (Spinner) findViewById(R.id.spinnerSampleSizePkgAppearDesc4);
        spinnerSampleSizePkgAppearDesc5 = (Spinner) findViewById(R.id.spinnerSampleSizePkgAppearDesc5);
        spinnerSampleSizePkgAppearDesc6 = (Spinner) findViewById(R.id.spinnerSampleSizePkgAppearDesc6);
        spinnerSampleSizePkgAppearDesc7 = (Spinner) findViewById(R.id.spinnerSampleSizePkgAppearDesc7);
        spinnerSampleSizePkgAppearDesc8 = (Spinner) findViewById(R.id.spinnerSampleSizePkgAppearDesc8);
        spinnerSampleSizePkgAppearDesc9 = (Spinner) findViewById(R.id.spinnerSampleSizePkgAppearDesc9);


        spinnerOverAllPkgAppearDesc1 = (Spinner) findViewById(R.id.spinnerOverAllPkgAppearDesc1);
        spinnerOverAllPkgAppearDesc2 = (Spinner) findViewById(R.id.spinnerOverAllPkgAppearDesc2);
        spinnerOverAllPkgAppearDesc3 = (Spinner) findViewById(R.id.spinnerOverAllPkgAppearDesc3);
        spinnerOverAllPkgAppearDesc4 = (Spinner) findViewById(R.id.spinnerOverAllPkgAppearDesc4);
        spinnerOverAllPkgAppearDesc5 = (Spinner) findViewById(R.id.spinnerOverAllPkgAppearDesc5);
        spinnerOverAllPkgAppearDesc6 = (Spinner) findViewById(R.id.spinnerOverAllPkgAppearDesc6);
        spinnerOverAllPkgAppearDesc7 = (Spinner) findViewById(R.id.spinnerOverAllPkgAppearDesc7);
        spinnerOverAllPkgAppearDesc8 = (Spinner) findViewById(R.id.spinnerOverAllPkgAppearDesc8);
        spinnerOverAllPkgAppearDesc9 = (Spinner) findViewById(R.id.spinnerOverAllPkgAppearDesc9);


        PkgAppearDescHdrLL = (LinearLayout) findViewById(R.id.PkgAppearDescHdrLL);
        llPkgAppearDesc1 = (LinearLayout) findViewById(R.id.llPkgAppearDesc1);
        llPkgAppearDesc2 = (LinearLayout) findViewById(R.id.llPkgAppearDesc2);
        llPkgAppearDesc3 = (LinearLayout) findViewById(R.id.llPkgAppearDesc3);
        llPkgAppearDesc4 = (LinearLayout) findViewById(R.id.llPkgAppearDesc4);
        llPkgAppearDesc5 = (LinearLayout) findViewById(R.id.llPkgAppearDesc5);
        llPkgAppearDesc6 = (LinearLayout) findViewById(R.id.llPkgAppearDesc6);
        llPkgAppearDesc7 = (LinearLayout) findViewById(R.id.llPkgAppearDesc7);
        llPkgAppearDesc8 = (LinearLayout) findViewById(R.id.llPkgAppearDesc8);
        llPkgAppearDesc9 = (LinearLayout) findViewById(R.id.llPkgAppearDesc9);


        txtPkgAppearDesc1 = (TextView) findViewById(R.id.txtPkgAppearDesc1);
        txtPkgAppearDesc2 = (TextView) findViewById(R.id.txtPkgAppearDesc2);
        txtPkgAppearDesc3 = (TextView) findViewById(R.id.txtPkgAppearDesc3);
        txtPkgAppearDesc4 = (TextView) findViewById(R.id.txtPkgAppearDesc4);
        txtPkgAppearDesc5 = (TextView) findViewById(R.id.txtPkgAppearDesc5);
        txtPkgAppearDesc6 = (TextView) findViewById(R.id.txtPkgAppearDesc6);
        txtPkgAppearDesc7 = (TextView) findViewById(R.id.txtPkgAppearDesc7);
        txtPkgAppearDesc8 = (TextView) findViewById(R.id.txtPkgAppearDesc8);
        txtPkgAppearDesc9 = (TextView) findViewById(R.id.txtPkgAppearDesc9);
    }

    private void handleToInitiatePackagingAppearance() {

        handlePackaging();

        handlePackagingAppearanceOverAllResultSpinner();

        handlePackagingAppearanceSimpleOverAllResultDescSpinner();

        updateOverResultPackagingAppearance();

        updatePackagingAppearanceUI();

    }

    private void updateOverAllResultOnsite() {
        List<String> statusList = new ArrayList<>();
        final List<GeneralModel> overAllResultStatusList = GeneralMasterHandler.getGeneralList(this, FEnumerations.E_OVERALL_RESULT_STATUS_GEN_ID);
        if (overAllResultStatusList != null && overAllResultStatusList.size() > 0) {
            int selectedOPos = 0;
            for (int i = 0; i < overAllResultStatusList.size(); i++) {
                statusList.add(overAllResultStatusList.get(i).MainDescr);

                if (overAllResultStatusList.get(i).pGenRowID.equals(packagePoItemDetalDetail.OnSiteTest_InspectionResultID)) {
                    selectedOPos = i;
                }
            }
            ArrayAdapter overAllArrary = new ArrayAdapter(this, R.layout.dialog_list_item, statusList);
            overAllArrary.setDropDownViewResource(R.layout.dialog_list_item);
            //Setting the ArrayAdapter data on the Spinner

            spinnerOnSiteOverAllResult.setAdapter(overAllArrary);
            spinnerOnSiteOverAllResult.setSelection(selectedOPos);
            spinnerOnSiteOverAllResult.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    packagePoItemDetalDetail.OnSiteTest_InspectionResultID = overAllResultStatusList.get(i).pGenRowID;
                    handleOverAllResult();//comment by shekhar
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }
    }

    private void updateOverResultPackagingAppearance() {
        List<String> statusList = new ArrayList<>();
        final List<GeneralModel> overAllResultStatusList = GeneralMasterHandler.getGeneralList(this, FEnumerations.E_OVERALL_RESULT_STATUS_GEN_ID);
        if (overAllResultStatusList != null && overAllResultStatusList.size() > 0) {
            int selectedOPos = 0;
            for (int i = 0; i < overAllResultStatusList.size(); i++) {
                statusList.add(overAllResultStatusList.get(i).MainDescr);

                if (overAllResultStatusList.get(i).pGenRowID.equals(packagePoItemDetalDetail.PKG_App_InspectionResultID)) {
                    selectedOPos = i;
                }
            }
            ArrayAdapter overAllArrary = new ArrayAdapter(this, R.layout.dialog_list_item, statusList);
            overAllArrary.setDropDownViewResource(R.layout.dialog_list_item);
            //Setting the ArrayAdapter data on the Spinner

            spinnerPjgAppearOverAllResult.setAdapter(overAllArrary);
            spinnerPjgAppearOverAllResult.setSelection(selectedOPos);
            spinnerPjgAppearOverAllResult.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    packagePoItemDetalDetail.PKG_App_InspectionResultID = overAllResultStatusList.get(i).pGenRowID;
                    handleOverAllResult();//comment by shekhar
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }
    }

    private void handleToInitiatePackingMeasurement() {

//        cInnerPackContainer = (LinearLayout) findViewById(R.id.cInnerPackContainer);
//        cMasterPackContainer = (LinearLayout) findViewById(R.id.cMasterPackContainer);
//        cPalletPackContainer = (LinearLayout) findViewById(R.id.cPalletPackContainer);

        txtInputTypeinner = (TextView) findViewById(R.id.txtInputTypeinner);
        txtInputTypeUnit = (TextView) findViewById(R.id.txtInputTypeUnit);
        txtInputTypeMaster = (TextView) findViewById(R.id.txtInputTypeMaster);
        txtInputTypePallet = (TextView) findViewById(R.id.txtInputTypePallet);


        txtUnitSL = (TextView) findViewById(R.id.txtUnitSL);
        txtUnitSB = (TextView) findViewById(R.id.txtUnitSB);
        txtUnitSH = (TextView) findViewById(R.id.txtUnitSH);
        txtUnitSWt = (TextView) findViewById(R.id.txtUnitSWt);
        txtUnitSCBM = (TextView) findViewById(R.id.txtUnitSCBM);
        txtUnitSQuantity = (TextView) findViewById(R.id.txtUnitSQuantity);
        txtUnitFindingsL = (EditText) findViewById(R.id.txtUnitFindingsL);
        txtUnitFindingsB = (EditText) findViewById(R.id.txtUnitFindingsB);
        txtUnitFindingsH = (EditText) findViewById(R.id.txtUnitFindingsH);

        txtUnitFindingsWt = (EditText) findViewById(R.id.txtUnitFindingsWt);
        txtUnitFindingsCBM = (EditText) findViewById(R.id.txtUnitFindingsCBM);
        txtUnitFindingsQuantity = (TextView) findViewById(R.id.txtUnitFindingsQuantity);

        txtInnerPackingL = (TextView) findViewById(R.id.txtInnerPackingL);
        txtInnerPackingB = (TextView) findViewById(R.id.txtInnerPackingB);
        txtInnerPackingH = (TextView) findViewById(R.id.txtInnerPackingH);
        txtInnerPackingWt = (TextView) findViewById(R.id.txtInnerPackingWt);
        txtInnerPackingCBM = (TextView) findViewById(R.id.txtInnerPackingCBM);
        txtInnerPackingQuantity = (TextView) findViewById(R.id.txtInnerPackingQuantity);
        txtInnerPackingFindingsL = (EditText) findViewById(R.id.txtInnerPackingFindingsL);
        txtInnerPackingFindingsB = (EditText) findViewById(R.id.txtInnerPackingFindingsB);
        txtInnerPackingFindingsH = (EditText) findViewById(R.id.txtInnerPackingFindingsH);
        txtInnerPackingFindingsWt = (EditText) findViewById(R.id.txtInnerPackingFindingsWt);
        txtInnerPackingFindingsCBM = (EditText) findViewById(R.id.txtInnerPackingFindingsCBM);
        txtInnerPackingFindingsQuantity = (TextView) findViewById(R.id.txtInnerPackingFindingsQuantity);

        txtMasterPackingL = (TextView) findViewById(R.id.txtMasterPackingL);
        txtMasterPackingB = (TextView) findViewById(R.id.txtMasterPackingB);
        txtMasterPackingH = (TextView) findViewById(R.id.txtMasterPackingH);
        txtMasterPackingWt = (TextView) findViewById(R.id.txtMasterPackingWt);
        txtMasterPackingCBM = (TextView) findViewById(R.id.txtMasterPackingCBM);
        txtMasterPackingQuantity = (TextView) findViewById(R.id.txtMasterPackingQuantity);
        txtMasterPackingFindingsL = (EditText) findViewById(R.id.txtMasterPackingFindingsL);
        txtMasterPackingFindingsB = (EditText) findViewById(R.id.txtMasterPackingFindingsB);
        txtMasterPackingFindingsH = (EditText) findViewById(R.id.txtMasterPackingFindingsH);
        txtMasterPackingFindingsWt = (EditText) findViewById(R.id.txtMasterPackingFindingsWt);
        txtMasterPackingFindingsCBM = (EditText) findViewById(R.id.txtMasterPackingFindingsCBM);
        txtMasterPackingFindingsQuantity = (TextView) findViewById(R.id.txtMasterPackingFindingsQuantity);

        txtPalletPackingL = (TextView) findViewById(R.id.txtPalletPackingL);
        txtPalletPackingB = (TextView) findViewById(R.id.txtPalletPackingB);
        txtPalletPackingH = (TextView) findViewById(R.id.txtPalletPackingH);
        txtPalletPackingWt = (TextView) findViewById(R.id.txtPalletPackingWt);
        txtPalletPackingCBM = (TextView) findViewById(R.id.txtPalletPackingCBM);
        txtPalletPackingQuantity = (TextView) findViewById(R.id.txtPalletPackingQuantity);
        txtPalletPackingFindingsL = (EditText) findViewById(R.id.txtPalletPackingFindingsL);
        txtPalletPackingFindingsB = (EditText) findViewById(R.id.txtPalletPackingFindingsB);
        txtPalletPackingFindingsH = (EditText) findViewById(R.id.txtPalletPackingFindingsH);
        txtPalletPackingFindingsWt = (EditText) findViewById(R.id.txtPalletPackingFindingsWt);
        txtPalletPackingFindingsCBM = (EditText) findViewById(R.id.txtPalletPackingFindingsCBM);
        txtPalletPackingFindingsQuantity = (TextView) findViewById(R.id.txtPalletPackingFindingsQuantity);


        masterPackingImagePicker = (ImageView) findViewById(R.id.masterPackingImagePicker);
        unitPackingImagePicker = (ImageView) findViewById(R.id.unitPackingImagePicker);
        innerPackingImagePicker = (ImageView) findViewById(R.id.innerPackingImagePicker);
        palletPackingImagePicker = (ImageView) findViewById(R.id.palletPackingImagePicker);

        unitPackingIPkgAppImagePicker = (ImageView) findViewById(R.id.unitPackingIPkgAppImagePicker);
        shippingPackingIPkgAppImagePicker = (ImageView) findViewById(R.id.shippingPackingIPkgAppImagePicker);
        innerPackingIPkgAppImagePicker = (ImageView) findViewById(R.id.innerPackingIPkgAppImagePicker);
        masterPackingIPkgAppImagePicker = (ImageView) findViewById(R.id.masterPackingIPkgAppImagePicker);
        palletPackingIPkgAppImagePicker = (ImageView) findViewById(R.id.palletPackingIPkgAppImagePicker);


        unitBarcodeImagePicker = (ImageView) findViewById(R.id.unitBarcodeImagePicker);
        innerBarcodeImagePicker = (ImageView) findViewById(R.id.innerBarcodeImagePicker);
        masterBarcodeImagePicker = (ImageView) findViewById(R.id.masterBarcodeImagePicker);
        palletBarcodeImagePicker = (ImageView) findViewById(R.id.palletBarcodeImagePicker);

        unitBarcodeAttachmentCount = (TextView) findViewById(R.id.unitBarcodeAttachmentCount);
        innerBarcodeAttachmentCount = (TextView) findViewById(R.id.innerBarcodeAttachmentCount);
        masterBarcodeAttachmentCount = (TextView) findViewById(R.id.masterBarcodeAttachmentCount);
        palletBarcodeAttachmentCount = (TextView) findViewById(R.id.unitBarcodeAttachmentCount);


        packingFillContainer = (LinearLayout) findViewById(R.id.packingFillContainer);
        innerPackingFillContainer = (LinearLayout) findViewById(R.id.innerPackingFillContainer);
        masterPackingFillContainer = (LinearLayout) findViewById(R.id.masterPackingFillContainer);
        palletPackingFillContainer = (LinearLayout) findViewById(R.id.palletPackingFillContainer);
        spinnerOverAllResult = (Spinner) findViewById(R.id.spinnerOverAllResult);
        spinnerMasterPackingOverAllResult = (Spinner) findViewById(R.id.spinnerMasterPackingOverAllResult);
        spinnerInnerPackingOverAllResult = (Spinner) findViewById(R.id.spinnerInnerPackingOverAll);
        spinnerUnitPackingOverAllResult = (Spinner) findViewById(R.id.spinnerUnitPackingOverAllResult);
        spinnerPalletPackingOverAllResult = (Spinner) findViewById(R.id.spinnerPalletPackingOverAllResult);

        spinnerInspectionLevel = (Spinner) findViewById(R.id.spinnerInspectionLevel);
        spinnerSampleSizeUnitPacking = (Spinner) findViewById(R.id.spinnerSampleSizeUnitPacking);
        spinnerSampleSizeInnerPacking = (Spinner) findViewById(R.id.spinnerSampleSizeInnerPacking);
        spinnerSampleSizeMasterPacking = (Spinner) findViewById(R.id.spinnerSampleSizeMasterPacking);
        spinnerSampleSizePalletPacking = (Spinner) findViewById(R.id.spinnerSampleSizePalletPacking);


        remarkHandle();
        handlePackaging();

        handlePackingOverAllResultSpinner();


        masterPackingImagePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onMasterPackImage();
            }
        });
        //added by shekhar
        unitPackingIPkgAppImagePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onUnitPackageAppearImage();

            }
        });
        shippingPackingIPkgAppImagePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onShippingPackageAppearImage();

            }
        });
        innerPackingIPkgAppImagePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onInnerPackageAppearImage();

            }
        });
        masterPackingIPkgAppImagePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onMasterPackageAppearImage();

            }
        });
        palletPackingIPkgAppImagePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPalletPackageAppearImage();

            }
        });




        unitBarcodeImagePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onUnitBarcodeImage();

            }
        });
        innerBarcodeImagePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onInnerBarcodeImage();

            }
        });
        masterBarcodeImagePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onMasterBarcodeImage();

            }
        });
        palletBarcodeImagePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPalletBarcodeImage();

            }
        });


        unitPackingImagePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onUnitPackImage();

            }
        });
        innerPackingImagePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onInnerPackImage();

            }
        });
        palletPackingImagePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPalletPackImage();

            }
        });
        unitAttachmentCount = (TextView) findViewById(R.id.unitAttachmentCount);
        innerAttachmentCount = (TextView) findViewById(R.id.innerAttachmentCount);
        masterAttachmentCount = (TextView) findViewById(R.id.masterAttachmentCount);
        palletAttachmentCount = (TextView) findViewById(R.id.palletAttachmentCount);

        unitAttachmentCount.setOnClickListener(this);
        innerAttachmentCount.setOnClickListener(this);
        masterAttachmentCount.setOnClickListener(this);
        palletAttachmentCount.setOnClickListener(this);

        updatePackingUI();


    }

    private void onPalletPackImage() {
        String valueToReturn = FEnumerations.REQUEST_FOR_PALLET_PACKING_ATTACHMENT + "";
        MultipleImageHandler.showDialog(ItemInspectionDetail.this, MultipleImageHandler.DOCUMENT, valueToReturn);
    }

    private void onInnerPackImage() {
        String valueToReturn = FEnumerations.REQUEST_FOR_INNER_PACKING_ATTACHMENT + "";
        MultipleImageHandler.showDialog(ItemInspectionDetail.this, MultipleImageHandler.DOCUMENT, valueToReturn);

    }

    private void onUnitPackImage() {

        String valueToReturn = FEnumerations.REQUEST_FOR_UNIT_PACKING_ATTACHMENT + "";
        MultipleImageHandler.showDialog(ItemInspectionDetail.this, MultipleImageHandler.DOCUMENT, valueToReturn);
    }

    private void onUnitPackageAppearImage() {

        String valueToReturn = FEnumerations.REQUEST_FOR_UNIT_PKG_APP_ATTACHMENT + "";
        MultipleImageHandler.showDialog(ItemInspectionDetail.this, MultipleImageHandler.DOCUMENT, valueToReturn);
    }

    private void onShippingPackageAppearImage() {

        String valueToReturn = FEnumerations.REQUEST_FOR_SHIPPING_PKG_APP_ATTACHMENT + "";
        MultipleImageHandler.showDialog(ItemInspectionDetail.this, MultipleImageHandler.DOCUMENT, valueToReturn);
    }

    private void onInnerPackageAppearImage() {

        String valueToReturn = FEnumerations.REQUEST_FOR_INNER_PKG_APP_ATTACHMENT + "";
        MultipleImageHandler.showDialog(ItemInspectionDetail.this, MultipleImageHandler.DOCUMENT, valueToReturn);
    }

    private void onMasterPackageAppearImage() {

        String valueToReturn = FEnumerations.REQUEST_FOR_MASTER_PKG_APP_ATTACHMENT + "";
        MultipleImageHandler.showDialog(ItemInspectionDetail.this, MultipleImageHandler.DOCUMENT, valueToReturn);
    }

    private void onPalletPackageAppearImage() {

        String valueToReturn = FEnumerations.REQUEST_FOR_PALLET_PKG_APP_ATTACHMENT + "";
        MultipleImageHandler.showDialog(ItemInspectionDetail.this, MultipleImageHandler.DOCUMENT, valueToReturn);
    }

    private void onUnitBarcodeImage() {

        String valueToReturn = FEnumerations.REQUEST_FOR_UNIT_BARCODE_ATTACHMENT + "";
        MultipleImageHandler.showDialog(ItemInspectionDetail.this, MultipleImageHandler.DOCUMENT, valueToReturn);
    }

    private void onInnerBarcodeImage() {

        String valueToReturn = FEnumerations.REQUEST_FOR_INNER_BARCODE_ATTACHMENT + "";
        MultipleImageHandler.showDialog(ItemInspectionDetail.this, MultipleImageHandler.DOCUMENT, valueToReturn);
    }

    private void onMasterBarcodeImage() {

        String valueToReturn = FEnumerations.REQUEST_FOR_MASTER_BARCODE_ATTACHMENT + "";
        MultipleImageHandler.showDialog(ItemInspectionDetail.this, MultipleImageHandler.DOCUMENT, valueToReturn);
    }
    private void onPalletBarcodeImage() {

        String valueToReturn = FEnumerations.REQUEST_FOR_PALLET_BARCODE_ATTACHMENT + "";
        MultipleImageHandler.showDialog(ItemInspectionDetail.this, MultipleImageHandler.DOCUMENT, valueToReturn);
    }

    private void onMasterPackImage() {
        String valueToReturn = FEnumerations.REQUEST_FOR_MASTER_PACKING_ATTACHMENT + "";
        MultipleImageHandler.showDialog(ItemInspectionDetail.this, MultipleImageHandler.DOCUMENT, valueToReturn);
    }

  /*  private void onQualityParameterImage() {
        String valueToReturn = FEnumerations.REQUEST_FOR_QUALITY_PARAMETER_ATTACHMENT + "";
        MultipleImageHandler.showDialog(ItemInspectionDetail.this, MultipleImageHandler.DOCUMENT, valueToReturn);

    }

    private void onQualityParameterSelectImage() {
        String valueToReturn = FEnumerations.REQUEST_FOR_QUALITY_PARAMETER_ATTACHMENT + "";
        MultipleImageHandler.showDialog(ItemInspectionDetail.this, MultipleImageHandler.DOCUMENT, valueToReturn, MultipleImageHandler.pickerViewer);

    }*/

    private void updatePackagingAppearanceUI() {

        if (packagePoItemDetalDetail.IPQty > 0) {
            findViewById(R.id.InnerPackingPkgAppearCv).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.InnerPackingPkgAppearCv).setVisibility(View.GONE);
        }
        if (packagePoItemDetalDetail.OPQty > 0) {
            findViewById(R.id.MasterPackingPkgAppearCv).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.MasterPackingPkgAppearCv).setVisibility(View.GONE);
        }
        if (packagePoItemDetalDetail.PalletQty > 0) {
            findViewById(R.id.PalletPackingPkgAppearCv).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.PalletPackingPkgAppearCv).setVisibility(View.GONE);
        }
    }

    private void updateBarcodeUI() {

        if (packagePoItemDetalDetail.IPQty > 0) {
            findViewById(R.id.barcodeInnerPackingCv).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.barcodeInnerPackingCv).setVisibility(View.GONE);
        }
        if (packagePoItemDetalDetail.OPQty > 0) {
            findViewById(R.id.barcodeMasterPackingCv).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.barcodeMasterPackingCv).setVisibility(View.GONE);
        }
        if (packagePoItemDetalDetail.PalletQty > 0) {
            findViewById(R.id.barcodePalletPackingCv).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.barcodePalletPackingCv).setVisibility(View.GONE);
        }
    }

    private void updatePackingUI() {

        if (packagePoItemDetalDetail.IPQty > 0) {
            findViewById(R.id.cInnerPackContainer).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.cInnerPackContainer).setVisibility(View.GONE);
        }
        if (packagePoItemDetalDetail.OPQty > 0) {
            findViewById(R.id.cMasterPackContainer).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.cMasterPackContainer).setVisibility(View.GONE);
        }
        if (packagePoItemDetalDetail.PalletQty > 0) {
            findViewById(R.id.cPalletPackContainer).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.cPalletPackContainer).setVisibility(View.GONE);
        }


        if (packagePoItemDetalDetail.mapCountInner == 0) {
            txtInputTypeinner.setText("In inch");
        } else {
            txtInputTypeinner.setText("In cm");
        }
        if (packagePoItemDetalDetail.mapCountInner == 0) {
            txtInputTypeUnit.setText("In inch");
        } else {
            txtInputTypeUnit.setText("In cm");
        }
        if (packagePoItemDetalDetail.mapCountInner == 0) {
            txtInputTypeMaster.setText("In inch");
        } else {
            txtInputTypeMaster.setText("In cm");
        }
        if (packagePoItemDetalDetail.mapCountInner == 0) {
            txtInputTypePallet.setText("In inch");
        } else {
            txtInputTypePallet.setText("In cm");
        }


        txtUnitSL.setText(packagePoItemDetalDetail.UnitL + "");
        txtUnitSB.setText(packagePoItemDetalDetail.UnitW + "");
        txtUnitSH.setText(packagePoItemDetalDetail.Unith + "");
        txtUnitSWt.setText(packagePoItemDetalDetail.Weight + "");
        txtUnitSCBM.setText(packagePoItemDetalDetail.CBM + "");
        txtUnitSQuantity.setText("");
        txtUnitFindingsL.setText(packagePoItemDetalDetail.PKG_Me_Unit_FindingL + "");
        txtUnitFindingsB.setText(packagePoItemDetalDetail.PKG_Me_Unit_FindingB + "");
        txtUnitFindingsH.setText(packagePoItemDetalDetail.PKG_Me_Unit_FindingH + "");
        txtUnitFindingsWt.setText(packagePoItemDetalDetail.PKG_Me_Unit_FindingWt + "");
        //added by shekhar
        txtUnitFindingsCBM.setText(packagePoItemDetalDetail.PKG_Me_Unit_FindingCBM + "");
        /*if (!TextUtils.isEmpty(packagePoItemDetalDetail.PKG_Me_Unit_FindingCBM) &&
                !packagePoItemDetalDetail.PKG_Me_Unit_FindingCBM.equals("null")){
            txtUnitFindingsCBM.setText(packagePoItemDetalDetail.PKG_Me_Unit_FindingCBM + "");
        }*/


        txtUnitFindingsQuantity.setText(packagePoItemDetalDetail.PKG_Me_Unit_FindingQty + "");

        txtUnitFindingsL.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));
        if (packagePoItemDetalDetail.UnitL > 0) {
            if (packagePoItemDetalDetail.PKG_Me_Unit_FindingL != packagePoItemDetalDetail.UnitL) {
                txtUnitFindingsL.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.red));
            }
        }

        txtUnitFindingsB.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));
        if (packagePoItemDetalDetail.UnitW > 0) {
            if (packagePoItemDetalDetail.PKG_Me_Unit_FindingB != packagePoItemDetalDetail.UnitW) {
                txtUnitFindingsB.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.red));
            }
        }

        txtUnitFindingsH.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));
        if (packagePoItemDetalDetail.Unith > 0) {
            if (packagePoItemDetalDetail.PKG_Me_Unit_FindingH != packagePoItemDetalDetail.Unith) {
                txtUnitFindingsH.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.red));
            }
        }

        txtUnitFindingsWt.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));
        if (packagePoItemDetalDetail.Weight > 0) {
            if (packagePoItemDetalDetail.PKG_Me_Unit_FindingWt != packagePoItemDetalDetail.Weight) {
                txtUnitFindingsWt.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.red));
            }
        }
        txtUnitFindingsCBM.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));
        if (packagePoItemDetalDetail.CBM > 0) {
            //added by shekhar
            /*if (!TextUtils.isEmpty(packagePoItemDetalDetail.PKG_Me_Unit_FindingCBM) && !packagePoItemDetalDetail.PKG_Me_Unit_FindingCBM.equals("null")) {
                float f = Float.parseFloat(packagePoItemDetalDetail.PKG_Me_Unit_FindingCBM);
                if (f != packagePoItemDetalDetail.Weight) {
                    txtUnitFindingsCBM.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.red));
                }
            }*/
            if (packagePoItemDetalDetail.PKG_Me_Unit_FindingCBM != packagePoItemDetalDetail.Weight) {
                txtUnitFindingsCBM.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.red));
            }
        }


        txtInnerPackingL.setText(packagePoItemDetalDetail.IPL + "");
        txtInnerPackingB.setText(packagePoItemDetalDetail.IPW + "");
        txtInnerPackingH.setText(packagePoItemDetalDetail.IPh + "");
        txtInnerPackingWt.setText(packagePoItemDetalDetail.IPWt + "");

        txtInnerPackingCBM.setText(packagePoItemDetalDetail.IPCBM + "");
        txtInnerPackingQuantity.setText(packagePoItemDetalDetail.IPQty + "");
        txtInnerPackingFindingsL.setText(packagePoItemDetalDetail.PKG_Me_Inner_FindingL + "");
        txtInnerPackingFindingsB.setText(packagePoItemDetalDetail.PKG_Me_Inner_FindingB + "");
        txtInnerPackingFindingsH.setText(packagePoItemDetalDetail.PKG_Me_Inner_FindingH + "");
        txtInnerPackingFindingsWt.setText(packagePoItemDetalDetail.PKG_Me_Inner_FindingWt + "");

        //comment by shekhar
//        if (!TextUtils.isEmpty(packagePoItemDetalDetail.PKG_Me_Inner_FindingCBM) && !packagePoItemDetalDetail.PKG_Me_Inner_FindingCBM.equals("null"))
        txtInnerPackingFindingsCBM.setText(packagePoItemDetalDetail.PKG_Me_Inner_FindingCBM + "");

        txtInnerPackingFindingsQuantity.setText(packagePoItemDetalDetail.PKG_Me_Inner_FindingQty + "");

        txtInnerPackingFindingsCBM.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));
        if (packagePoItemDetalDetail.IPCBM > 0) {

            //comment by shekhar
            /*if (!TextUtils.isEmpty(packagePoItemDetalDetail.PKG_Me_Unit_FindingCBM) && !packagePoItemDetalDetail.PKG_Me_Unit_FindingCBM.equals("null")) {
                float f = Float.parseFloat(packagePoItemDetalDetail.PKG_Me_Unit_FindingCBM);
                if (f != packagePoItemDetalDetail.IPCBM) {
                    txtInnerPackingFindingsCBM.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.red));
                }
            }*/
            if (packagePoItemDetalDetail.PKG_Me_Unit_FindingCBM != packagePoItemDetalDetail.IPCBM) {
                txtInnerPackingFindingsCBM.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.red));
            }
        }

        txtInnerPackingFindingsWt.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));
        if (packagePoItemDetalDetail.IPWt > 0) {
            if (packagePoItemDetalDetail.PKG_Me_Inner_FindingWt != packagePoItemDetalDetail.IPWt) {
                txtInnerPackingFindingsWt.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.red));
            }
        }

        txtInnerPackingFindingsH.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));
        if (packagePoItemDetalDetail.IPh > 0) {
            if (packagePoItemDetalDetail.PKG_Me_Inner_FindingH != packagePoItemDetalDetail.IPh) {
                txtInnerPackingFindingsH.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.red));
            }
        }

        txtInnerPackingFindingsB.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));
        if (packagePoItemDetalDetail.IPW > 0) {
            if (packagePoItemDetalDetail.PKG_Me_Inner_FindingB != packagePoItemDetalDetail.IPW) {
                txtInnerPackingFindingsB.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.red));
            }
        }

        txtInnerPackingFindingsL.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));
        if (packagePoItemDetalDetail.IPL > 0) {
            if (packagePoItemDetalDetail.PKG_Me_Inner_FindingL != packagePoItemDetalDetail.IPL) {
                txtInnerPackingFindingsL.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.red));
            }
        }

        txtMasterPackingL.setText(packagePoItemDetalDetail.OPL + "");
        txtMasterPackingB.setText(packagePoItemDetalDetail.OPW + "");
        txtMasterPackingH.setText(packagePoItemDetalDetail.OPh + "");
        txtMasterPackingWt.setText(packagePoItemDetalDetail.OPWt + "");
        txtMasterPackingCBM.setText(packagePoItemDetalDetail.OPCBM + "");
        txtMasterPackingQuantity.setText(packagePoItemDetalDetail.OPQty + "");


        txtMasterPackingFindingsL.setText(packagePoItemDetalDetail.PKG_Me_Master_FindingL + "");
        txtMasterPackingFindingsB.setText(packagePoItemDetalDetail.PKG_Me_Master_FindingB + "");
        txtMasterPackingFindingsH.setText(packagePoItemDetalDetail.PKG_Me_Master_FindingH + "");
        txtMasterPackingFindingsWt.setText(packagePoItemDetalDetail.PKG_Me_Master_FindingWt + "");
        //comment by shekhar
//        if (!TextUtils.isEmpty(packagePoItemDetalDetail.PKG_Me_Master_FindingCBM) && !packagePoItemDetalDetail.PKG_Me_Master_FindingCBM.equals("null"))
        txtMasterPackingFindingsCBM.setText(packagePoItemDetalDetail.PKG_Me_Master_FindingCBM + "");
        txtMasterPackingFindingsQuantity.setText(packagePoItemDetalDetail.PKG_Me_Master_FindingQty + "");


        txtMasterPackingFindingsCBM.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));
        if (packagePoItemDetalDetail.OPCBM > 0) {
            //comment by shekhar
            /*if (!TextUtils.isEmpty(packagePoItemDetalDetail.PKG_Me_Master_FindingCBM) && !packagePoItemDetalDetail.PKG_Me_Master_FindingCBM.equals("null")) {
                float f = Float.parseFloat(packagePoItemDetalDetail.PKG_Me_Master_FindingCBM);
                if (f != packagePoItemDetalDetail.OPCBM) {
                    txtMasterPackingFindingsCBM.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.red));
                }
            }*/
            if (packagePoItemDetalDetail.PKG_Me_Master_FindingCBM != packagePoItemDetalDetail.OPCBM) {
                txtMasterPackingFindingsCBM.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.red));
            }
        }

        txtMasterPackingFindingsWt.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));
        if (packagePoItemDetalDetail.OPWt > 0) {
            if (packagePoItemDetalDetail.PKG_Me_Master_FindingWt != packagePoItemDetalDetail.OPWt) {
                txtMasterPackingFindingsWt.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.red));
            }
        }

        txtMasterPackingFindingsH.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));
        if (packagePoItemDetalDetail.OPh > 0) {
            if (packagePoItemDetalDetail.PKG_Me_Master_FindingH != packagePoItemDetalDetail.OPh) {
                txtMasterPackingFindingsH.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.red));
            }
        }

        txtMasterPackingFindingsB.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));
        if (packagePoItemDetalDetail.OPW > 0) {
            if (packagePoItemDetalDetail.PKG_Me_Master_FindingB != packagePoItemDetalDetail.OPW) {
                txtMasterPackingFindingsB.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.red));
            }
        }

        txtMasterPackingFindingsL.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));
        if (packagePoItemDetalDetail.OPL > 0) {
            if (packagePoItemDetalDetail.PKG_Me_Master_FindingL != packagePoItemDetalDetail.OPL) {
                txtMasterPackingFindingsL.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.red));
            }
        }


        txtPalletPackingL.setText(packagePoItemDetalDetail.PalletL + "");
        txtPalletPackingB.setText(packagePoItemDetalDetail.PalletW + "");
        txtPalletPackingH.setText(packagePoItemDetalDetail.Palleth + "");
        txtPalletPackingWt.setText(packagePoItemDetalDetail.PalletWt + "");
        txtPalletPackingCBM.setText(packagePoItemDetalDetail.PalletCBM + "");
        txtPalletPackingQuantity.setText(packagePoItemDetalDetail.PalletQty + "");
        txtPalletPackingFindingsL.setText(packagePoItemDetalDetail.PKG_Me_Pallet_FindingL + "");
        txtPalletPackingFindingsB.setText(packagePoItemDetalDetail.PKG_Me_Pallet_FindingB + "");
        txtPalletPackingFindingsH.setText(packagePoItemDetalDetail.PKG_Me_Pallet_FindingH + "");
        txtPalletPackingFindingsWt.setText(packagePoItemDetalDetail.PKG_Me_Pallet_FindingWt + "");
        //comment by shekhar
//        if (!TextUtils.isEmpty(packagePoItemDetalDetail.PKG_Me_Pallet_FindingCBM) && !packagePoItemDetalDetail.PKG_Me_Pallet_FindingCBM.equals("null"))
        txtPalletPackingFindingsCBM.setText(packagePoItemDetalDetail.PKG_Me_Pallet_FindingCBM + "");

        txtPalletPackingFindingsQuantity.setText(packagePoItemDetalDetail.PKG_Me_Pallet_FindingQty + "");

        txtPalletPackingFindingsCBM.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));
        if (packagePoItemDetalDetail.PalletCBM > 0) {
            //comment by shekhar
            /*if (!TextUtils.isEmpty(packagePoItemDetalDetail.PKG_Me_Pallet_FindingCBM) && !packagePoItemDetalDetail.PKG_Me_Pallet_FindingCBM.equals("null")) {
                float f = Float.parseFloat(packagePoItemDetalDetail.PKG_Me_Pallet_FindingCBM);
                if (f != packagePoItemDetalDetail.PalletCBM) {
                    txtPalletPackingFindingsCBM.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.red));
                }
            }*/
            if (packagePoItemDetalDetail.PKG_Me_Pallet_FindingCBM != packagePoItemDetalDetail.PalletCBM) {
                txtPalletPackingFindingsCBM.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.red));
            }
        }

        txtPalletPackingFindingsWt.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));
        if (packagePoItemDetalDetail.PalletWt > 0) {
            if (packagePoItemDetalDetail.PKG_Me_Pallet_FindingWt != packagePoItemDetalDetail.PalletWt) {
                txtPalletPackingFindingsWt.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.red));
            }
        }

        txtPalletPackingFindingsH.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));
        if (packagePoItemDetalDetail.Palleth > 0) {
            if (packagePoItemDetalDetail.PKG_Me_Pallet_FindingH != packagePoItemDetalDetail.Palleth) {
                txtPalletPackingFindingsH.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.red));
            }
        }

        txtPalletPackingFindingsB.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));
        if (packagePoItemDetalDetail.PalletW > 0) {
            if (packagePoItemDetalDetail.PKG_Me_Pallet_FindingB != packagePoItemDetalDetail.PalletW) {
                txtPalletPackingFindingsB.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.red));
            }
        }

        txtPalletPackingFindingsL.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));
        if (packagePoItemDetalDetail.PalletL > 0) {
            if (packagePoItemDetalDetail.PKG_Me_Pallet_FindingL != packagePoItemDetalDetail.PalletL) {
                txtPalletPackingFindingsL.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.red));
            }
        }


        if (!TextUtils.isEmpty(packagePoItemDetalDetail.PKG_Me_Remark) && !packagePoItemDetalDetail.PKG_Me_Remark.equals("null")) {
            editPackingRemark.setText(packagePoItemDetalDetail.PKG_Me_Remark);
        }

        if (!TextUtils.isEmpty(packagePoItemDetalDetail.WorkmanShip_Remark) && !packagePoItemDetalDetail.WorkmanShip_Remark.equals("null")) {
            editWorkmanshipRemark.setText(packagePoItemDetalDetail.WorkmanShip_Remark);
        }
        if (!TextUtils.isEmpty(packagePoItemDetalDetail.ItemMeasurement_Remark) && !packagePoItemDetalDetail.ItemMeasurement_Remark.equals("null")) {
            editItemMeasurementRemark.setText(packagePoItemDetalDetail.ItemMeasurement_Remark);
        }

        //added by shekhar
        if (!TextUtils.isEmpty(packagePoItemDetalDetail.PKG_App_Remark) && !packagePoItemDetalDetail.PKG_App_Remark.equals("null")) {
            editPackingAppearRemark.setText(packagePoItemDetalDetail.PKG_App_Remark);
        }
        if (!TextUtils.isEmpty(packagePoItemDetalDetail.OnSiteTest_Remark) && !packagePoItemDetalDetail.OnSiteTest_Remark.equals("null")) {
            editOnSiteRemark.setText(packagePoItemDetalDetail.OnSiteTest_Remark);
        }
        if (!TextUtils.isEmpty(packagePoItemDetalDetail.Qty_Remark) && !packagePoItemDetalDetail.Qty_Remark.equals("null")) {
            editItemQtyRemark.setText(packagePoItemDetalDetail.Qty_Remark);
        }
        handleItemQty();
        checkPkgAppShowHideStatus();
        /*txtOerderQty.setText(poItemDtl.OrderQty);
        txtAvlblQty.setText(poItemDtl.AvailableQty+"");
        txtAcceptedQty.setText(poItemDtl.AcceptedQty+"");*/
        if (!TextUtils.isEmpty(packagePoItemDetalDetail.Barcode_Remark) && !packagePoItemDetalDetail.Barcode_Remark.equals("null")) {
            editPackingBarcodeRemark.setText(packagePoItemDetalDetail.Barcode_Remark);
        }


        if (!TextUtils.isEmpty(packagePoItemDetalDetail.PKG_Me_Pallet_Digitals) && !packagePoItemDetalDetail.PKG_Me_Pallet_Digitals.equals("null")) {
            String[] spStr = packagePoItemDetalDetail.PKG_Me_Pallet_Digitals.split(",", 0);
            packagePoItemDetalDetail.palletPackingAttachmentList.clear();
            if (spStr != null && spStr.length > 0) {
                for (int i = 0; i < spStr.length; i++) {
                    String rowId = spStr[i];
                    if (!TextUtils.isEmpty(rowId)) {
                        DigitalsUploadModal digitalsUploadModal = ItemInspectionDetailHandler.getImage(ItemInspectionDetail.this, rowId);
                        if (digitalsUploadModal != null) {
                            packagePoItemDetalDetail.palletPackingAttachmentList.add(digitalsUploadModal.selectedPicPath);
                        }
                    }
                }

            }


        }
        if (!TextUtils.isEmpty(packagePoItemDetalDetail.PKG_Me_Master_Digitals) && !packagePoItemDetalDetail.PKG_Me_Master_Digitals.equals("null")) {
            String[] spStr = packagePoItemDetalDetail.PKG_Me_Master_Digitals.split(",", 0);
            packagePoItemDetalDetail.masterPackingAttachmentList.clear();
            if (spStr != null && spStr.length > 0) {
                for (int i = 0; i < spStr.length; i++) {
                    String rowId = spStr[i];
                    if (!TextUtils.isEmpty(rowId)) {
                        DigitalsUploadModal digitalsUploadModal = ItemInspectionDetailHandler.getImage(ItemInspectionDetail.this, rowId);
                        if (digitalsUploadModal != null) {
                            packagePoItemDetalDetail.masterPackingAttachmentList.add(digitalsUploadModal.selectedPicPath);
                        }
                    }
                }

            }
        }
        if (!TextUtils.isEmpty(packagePoItemDetalDetail.PKG_Me_Inner_Digitals) && !packagePoItemDetalDetail.PKG_Me_Inner_Digitals.equals("null")) {
            String[] spStr = packagePoItemDetalDetail.PKG_Me_Inner_Digitals.split(",", 0);
            packagePoItemDetalDetail.innerPackingAttachmentList.clear();
            if (spStr != null && spStr.length > 0) {
                for (int i = 0; i < spStr.length; i++) {
                    String rowId = spStr[i];
                    if (!TextUtils.isEmpty(rowId)) {
                        DigitalsUploadModal digitalsUploadModal = ItemInspectionDetailHandler.getImage(ItemInspectionDetail.this, rowId);
                        if (digitalsUploadModal != null) {
                            packagePoItemDetalDetail.innerPackingAttachmentList.add(digitalsUploadModal.selectedPicPath);
                        }
                    }
                }

            }
        }
        if (!TextUtils.isEmpty(packagePoItemDetalDetail.PKG_Me_Unit_Digitals) && !packagePoItemDetalDetail.PKG_Me_Unit_Digitals.equals("null")) {
            String[] spStr = packagePoItemDetalDetail.PKG_Me_Unit_Digitals.split(",", 0);
            packagePoItemDetalDetail.unitPackingAttachmentList.clear();
            if (spStr != null && spStr.length > 0) {
                for (int i = 0; i < spStr.length; i++) {
                    String rowId = spStr[i];
                    if (!TextUtils.isEmpty(rowId)) {
                        DigitalsUploadModal digitalsUploadModal = ItemInspectionDetailHandler.getImage(ItemInspectionDetail.this, rowId);
                        if (digitalsUploadModal != null) {
                            packagePoItemDetalDetail.unitPackingAttachmentList.add(digitalsUploadModal.selectedPicPath);
                        }
                    }
                }

            }
        }

        masterAttachmentCount.setText("" + packagePoItemDetalDetail.masterPackingAttachmentList.size());
        palletAttachmentCount.setText("" + packagePoItemDetalDetail.palletPackingAttachmentList.size());
        innerAttachmentCount.setText("" + packagePoItemDetalDetail.innerPackingAttachmentList.size());
        unitAttachmentCount.setText("" + packagePoItemDetalDetail.unitPackingAttachmentList.size());
    }

    private void onSubmitPackage() {

        String _ul = txtUnitFindingsL.getText().toString().trim();
        String _ub = txtUnitFindingsB.getText().toString().trim();
        String _uh = txtUnitFindingsH.getText().toString().trim();
        String _uw = txtUnitFindingsWt.getText().toString().trim();
        String _ucbm = txtUnitFindingsCBM.getText().toString().trim();
        String _il = txtInnerPackingFindingsL.getText().toString().trim();
        String _ib = txtInnerPackingFindingsB.getText().toString().trim();
        String _ih = txtInnerPackingFindingsH.getText().toString().trim();
        String _iw = txtInnerPackingFindingsWt.getText().toString().trim();
        String _icbm = txtInnerPackingFindingsCBM.getText().toString().trim();
        String _ml = txtMasterPackingFindingsL.getText().toString().trim();
        String _mb = txtMasterPackingFindingsB.getText().toString().trim();
        String _mh = txtMasterPackingFindingsH.getText().toString().trim();
        String _mwt = txtMasterPackingFindingsWt.getText().toString().trim();
        String _mcbm = txtMasterPackingFindingsCBM.getText().toString().trim();

        String _pl = txtPalletPackingFindingsL.getText().toString().trim();
        String _pb = txtPalletPackingFindingsB.getText().toString().trim();
        String _ph = txtPalletPackingFindingsH.getText().toString().trim();
        String _pwt = txtPalletPackingFindingsWt.getText().toString().trim();
        String _pcbm = txtPalletPackingFindingsCBM.getText().toString().trim();


        if (TextUtils.isEmpty(_ul)) {
            packagePoItemDetalDetail.PKG_Me_Unit_FindingL = 0;
        } else {
            packagePoItemDetalDetail.PKG_Me_Unit_FindingL = Double.parseDouble(_ul);
        }

        if (TextUtils.isEmpty(_ub)) {
            packagePoItemDetalDetail.PKG_Me_Unit_FindingB = 0;
        } else {
            packagePoItemDetalDetail.PKG_Me_Unit_FindingB = Double.parseDouble(_ub);
        }
        if (TextUtils.isEmpty(_uh)) {
            packagePoItemDetalDetail.PKG_Me_Unit_FindingH = 0;
        } else {
            packagePoItemDetalDetail.PKG_Me_Unit_FindingH = Double.parseDouble(_uh);
        }
        if (TextUtils.isEmpty(_uw)) {
            packagePoItemDetalDetail.PKG_Me_Unit_FindingWt = 0;
        } else {
            packagePoItemDetalDetail.PKG_Me_Unit_FindingWt = Double.parseDouble(_uw);
        }

        //commnet by shekhar
        /*packagePoItemDetalDetail.PKG_Me_Unit_FindingCBM = _ucbm;
        if (TextUtils.isEmpty(_ucbm)) {
            packagePoItemDetalDetail.PKG_Me_Unit_FindingCBM = null;
        }*/
        if (TextUtils.isEmpty(_mcbm)) {
            packagePoItemDetalDetail.PKG_Me_Unit_FindingCBM = 0;
        } else {
            packagePoItemDetalDetail.PKG_Me_Unit_FindingCBM = Double.parseDouble(_ucbm);
        }

        if (TextUtils.isEmpty(_il)) {
            packagePoItemDetalDetail.PKG_Me_Inner_FindingL = 0;
        } else {
            packagePoItemDetalDetail.PKG_Me_Inner_FindingL = Double.parseDouble(_il);
        }
        if (TextUtils.isEmpty(_ib)) {
            packagePoItemDetalDetail.PKG_Me_Inner_FindingB = 0;
        } else {
            packagePoItemDetalDetail.PKG_Me_Inner_FindingB = Double.parseDouble(_ib);
        }
        if (TextUtils.isEmpty(_ih)) {
            packagePoItemDetalDetail.PKG_Me_Inner_FindingH = 0;
        } else {
            packagePoItemDetalDetail.PKG_Me_Inner_FindingH = Double.parseDouble(_ih);
        }
        if (TextUtils.isEmpty(_iw)) {
            packagePoItemDetalDetail.PKG_Me_Inner_FindingWt = 0;
        } else {
            packagePoItemDetalDetail.PKG_Me_Inner_FindingWt = Double.parseDouble(_iw);
        }

        //commnet by shekhar
        /*packagePoItemDetalDetail.PKG_Me_Inner_FindingCBM = _icbm;
        if (TextUtils.isEmpty(_icbm)) {
            packagePoItemDetalDetail.PKG_Me_Inner_FindingCBM = null;
        }*/
        if (TextUtils.isEmpty(_mcbm)) {
            packagePoItemDetalDetail.PKG_Me_Inner_FindingCBM = 0;
        } else {
            packagePoItemDetalDetail.PKG_Me_Inner_FindingCBM = Double.parseDouble(_icbm);
        }

        if (TextUtils.isEmpty(_ml)) {
            packagePoItemDetalDetail.PKG_Me_Master_FindingL = 0;
        } else {
            packagePoItemDetalDetail.PKG_Me_Master_FindingL = Double.parseDouble(_ml);
        }
        if (TextUtils.isEmpty(_mb)) {
            packagePoItemDetalDetail.PKG_Me_Master_FindingB = 0;
        } else {
            packagePoItemDetalDetail.PKG_Me_Master_FindingB = Double.parseDouble(_mb);
        }
        if (TextUtils.isEmpty(_mh)) {
            packagePoItemDetalDetail.PKG_Me_Master_FindingH = 0;
        } else {
            packagePoItemDetalDetail.PKG_Me_Master_FindingH = Double.parseDouble(_mh);
        }
        if (TextUtils.isEmpty(_mwt)) {
            packagePoItemDetalDetail.PKG_Me_Master_FindingWt = 0;
        } else {
            packagePoItemDetalDetail.PKG_Me_Master_FindingWt = Double.parseDouble(_mwt);
        }

        //comment by shekhar
        /*packagePoItemDetalDetail.PKG_Me_Master_FindingCBM = _mcbm;
        if (TextUtils.isEmpty(_mcbm)) {
            packagePoItemDetalDetail.PKG_Me_Master_FindingCBM = null;
        }*/
        if (TextUtils.isEmpty(_mcbm)) {
            packagePoItemDetalDetail.PKG_Me_Master_FindingCBM = 0;
        } else {
            packagePoItemDetalDetail.PKG_Me_Master_FindingCBM = Double.parseDouble(_mcbm);
        }


        if (TextUtils.isEmpty(_pl)) {
            packagePoItemDetalDetail.PKG_Me_Pallet_FindingL = 0;
        } else {
            packagePoItemDetalDetail.PKG_Me_Pallet_FindingL = Double.parseDouble(_pl);
        }
        if (TextUtils.isEmpty(_pb)) {
            packagePoItemDetalDetail.PKG_Me_Pallet_FindingB = 0;
        } else {
            packagePoItemDetalDetail.PKG_Me_Pallet_FindingB = Double.parseDouble(_pb);
        }
        if (TextUtils.isEmpty(_ph)) {
            packagePoItemDetalDetail.PKG_Me_Pallet_FindingH = 0;
        } else {
            packagePoItemDetalDetail.PKG_Me_Pallet_FindingH = Double.parseDouble(_ph);
        }
        if (TextUtils.isEmpty(_pwt)) {
            packagePoItemDetalDetail.PKG_Me_Pallet_FindingWt = 0;
        } else {
            packagePoItemDetalDetail.PKG_Me_Pallet_FindingWt = Double.parseDouble(_pwt);
        }

        //comment by shekhar
        /*packagePoItemDetalDetail.PKG_Me_Pallet_FindingCBM = _pcbm;
        if (TextUtils.isEmpty(_pcbm)) {
            packagePoItemDetalDetail.PKG_Me_Pallet_FindingCBM = null;
        }*/
        if (TextUtils.isEmpty(_mcbm)) {
            packagePoItemDetalDetail.PKG_Me_Pallet_FindingCBM = 0;
        } else {
            packagePoItemDetalDetail.PKG_Me_Pallet_FindingCBM = Double.parseDouble(_pcbm);
        }


    }

    private void handlePkgAppDescOverAllResult(List<GeneralModel> overAllResultStatusListsss, List<POItemPkgAppDetail> pkgAppList) {
        spinnerPkgAppeOverAllTouched = false;
        List<String> statusList1 = new ArrayList<>();
        final List<GeneralModel> overAllResultStatusList = GeneralMasterHandler.getGeneralList(this,
                FEnumerations.E_OVERALL_RESULT_STATUS_GEN_ID);
        if (overAllResultStatusList != null && overAllResultStatusList.size() > 0) {
            for (int i = 0; i < overAllResultStatusList.size(); i++) {
                statusList1.add(overAllResultStatusList.get(i).MainDescr);
            }

            //Creating the ArrayAdapter instance having the country list
            ArrayAdapter masterArrary = new ArrayAdapter(this, R.layout.dialog_list_item, statusList1);
            masterArrary.setDropDownViewResource(R.layout.dialog_list_item);

            //Setting the ArrayAdapter data on the Spinner
            spinnerOverAllPkgAppearDesc1.setAdapter(masterArrary);

            spinnerOverAllPkgAppearDesc1.setOnTouchListener((v, me) -> {
                spinnerPkgAppeOverAllTouched = true;
                v.performClick();
                return false;
            });
            spinnerOverAllPkgAppearDesc2.setOnTouchListener((v, me) -> {
                spinnerPkgAppeOverAllTouched = true;
                v.performClick();
                return false;
            });
            spinnerOverAllPkgAppearDesc3.setOnTouchListener((v, me) -> {
                spinnerPkgAppeOverAllTouched = true;
                v.performClick();
                return false;
            });
            spinnerOverAllPkgAppearDesc4.setOnTouchListener((v, me) -> {
                spinnerPkgAppeOverAllTouched = true;
                v.performClick();
                return false;
            });
            spinnerOverAllPkgAppearDesc5.setOnTouchListener((v, me) -> {
                spinnerPkgAppeOverAllTouched = true;
                v.performClick();
                return false;
            });
            spinnerOverAllPkgAppearDesc6.setOnTouchListener((v, me) -> {
                spinnerPkgAppeOverAllTouched = true;
                v.performClick();
                return false;
            });
            spinnerOverAllPkgAppearDesc7.setOnTouchListener((v, me) -> {
                spinnerPkgAppeOverAllTouched = true;
                v.performClick();
                return false;
            });
            spinnerOverAllPkgAppearDesc8.setOnTouchListener((v, me) -> {
                spinnerPkgAppeOverAllTouched = true;
                v.performClick();
                return false;
            });
            spinnerOverAllPkgAppearDesc9.setOnTouchListener((v, me) -> {
                spinnerPkgAppeOverAllTouched = true;
                v.performClick();
                return false;
            });


//            spinnerOverAllMasterPackingPkgAppear.setSelection(selectedMPos);
            spinnerOverAllPkgAppearDesc1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (!spinnerPkgAppeOverAllTouched) {
                        return;
                    }
                    spinnerPkgAppeOverAllTouched = false;
                    pOItemPkgAppDetail.pRowID = pkgAppList.get(0).pRowID;
                    pOItemPkgAppDetail.InspectionResultID = overAllResultStatusList.get(i).pGenRowID;
//                    pOItemPkgAppDetail.recUser = overAllResultStatusList.get(i).recUser;
//                    pOItemPkgAppDetail.LocID = overAllResultStatusList.get(i).LocID;
//                    pOItemPkgAppDetail.DescrID = overAllResultStatusList.get(i).GenID;
                    updatePkgAppearance(pOItemPkgAppDetail);

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });

            //Setting the ArrayAdapter data on the Spinner
            spinnerOverAllPkgAppearDesc2.setAdapter(masterArrary);
//            spinnerOverAllMasterPackingPkgAppear.setSelection(selectedMPos);
            spinnerOverAllPkgAppearDesc2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (!spinnerPkgAppeOverAllTouched) {
                        return;
                    }
                    spinnerPkgAppeOverAllTouched = false;
//                    pOItemPkgAppDetail.pRowID = overAllResultStatusList.get(i).pGenRowID;
                    pOItemPkgAppDetail.pRowID = pkgAppList.get(1).pRowID;
                    pOItemPkgAppDetail.InspectionResultID = overAllResultStatusList.get(i).pGenRowID;
//                    pOItemPkgAppDetail.recUser = overAllResultStatusList.get(i).recUser;
//                    pOItemPkgAppDetail.DescrID = overAllResultStatusList.get(i).GenID;
                    updatePkgAppearance(pOItemPkgAppDetail);

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });

            //Setting the ArrayAdapter data on the Spinner
            spinnerOverAllPkgAppearDesc3.setAdapter(masterArrary);
//            spinnerOverAllMasterPackingPkgAppear.setSelection(selectedMPos);
            spinnerOverAllPkgAppearDesc3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (!spinnerPkgAppeOverAllTouched) {
                        return;
                    }
                    spinnerPkgAppeOverAllTouched = false;
//                    pOItemPkgAppDetail.pRowID = overAllResultStatusList.get(i).pGenRowID;
                    pOItemPkgAppDetail.pRowID = pkgAppList.get(2).pRowID;
                    pOItemPkgAppDetail.InspectionResultID = overAllResultStatusList.get(i).pGenRowID;
//                    pOItemPkgAppDetail.recUser = overAllResultStatusList.get(i).recUser;
//                    pOItemPkgAppDetail.DescrID = overAllResultStatusList.get(i).GenID;
                    updatePkgAppearance(pOItemPkgAppDetail);

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
            //Setting the ArrayAdapter data on the Spinner
            spinnerOverAllPkgAppearDesc4.setAdapter(masterArrary);
//            spinnerOverAllMasterPackingPkgAppear.setSelection(selectedMPos);
            spinnerOverAllPkgAppearDesc4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (!spinnerPkgAppeOverAllTouched) {
                        return;
                    }
                    spinnerPkgAppeOverAllTouched = false;
//                    pOItemPkgAppDetail.pRowID = overAllResultStatusList.get(i).pGenRowID;
                    pOItemPkgAppDetail.pRowID = pkgAppList.get(3).pRowID;
                    pOItemPkgAppDetail.InspectionResultID = overAllResultStatusList.get(i).pGenRowID;
//                    pOItemPkgAppDetail.recUser = overAllResultStatusList.get(i).recUser;
//                    pOItemPkgAppDetail.DescrID = overAllResultStatusList.get(i).GenID;
                    updatePkgAppearance(pOItemPkgAppDetail);

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
            //Setting the ArrayAdapter data on the Spinner
            spinnerOverAllPkgAppearDesc5.setAdapter(masterArrary);
//            spinnerOverAllMasterPackingPkgAppear.setSelection(selectedMPos);
            spinnerOverAllPkgAppearDesc5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (!spinnerPkgAppeOverAllTouched) {
                        return;
                    }
                    spinnerPkgAppeOverAllTouched = false;
//                    pOItemPkgAppDetail.pRowID = overAllResultStatusList.get(i).pGenRowID;
                    pOItemPkgAppDetail.pRowID = pkgAppList.get(4).pRowID;
                    pOItemPkgAppDetail.InspectionResultID = overAllResultStatusList.get(i).pGenRowID;
//                    pOItemPkgAppDetail.recUser = overAllResultStatusList.get(i).recUser;
//                    pOItemPkgAppDetail.DescrID = overAllResultStatusList.get(i).GenID;
                    updatePkgAppearance(pOItemPkgAppDetail);

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
            //Setting the ArrayAdapter data on the Spinner
            spinnerOverAllPkgAppearDesc6.setAdapter(masterArrary);
//            spinnerOverAllMasterPackingPkgAppear.setSelection(selectedMPos);
            spinnerOverAllPkgAppearDesc6.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (!spinnerPkgAppeOverAllTouched) {
                        return;
                    }
                    spinnerPkgAppeOverAllTouched = false;
//                    pOItemPkgAppDetail.pRowID = overAllResultStatusList.get(i).pGenRowID;
                    pOItemPkgAppDetail.pRowID = pkgAppList.get(5).pRowID;
                    pOItemPkgAppDetail.InspectionResultID = overAllResultStatusList.get(i).pGenRowID;
//                    pOItemPkgAppDetail.recUser = overAllResultStatusList.get(i).recUser;
//                    pOItemPkgAppDetail.DescrID = overAllResultStatusList.get(i).GenID;
                    updatePkgAppearance(pOItemPkgAppDetail);

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
            //Setting the ArrayAdapter data on the Spinner
            spinnerOverAllPkgAppearDesc7.setAdapter(masterArrary);
//            spinnerOverAllMasterPackingPkgAppear.setSelection(selectedMPos);
            spinnerOverAllPkgAppearDesc7.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (!spinnerPkgAppeOverAllTouched) {
                        return;
                    }
                    spinnerPkgAppeOverAllTouched = false;
//                    pOItemPkgAppDetail.pRowID = overAllResultStatusList.get(i).pGenRowID;
                    pOItemPkgAppDetail.pRowID = pkgAppList.get(6).pRowID;
                    pOItemPkgAppDetail.InspectionResultID = overAllResultStatusList.get(i).pGenRowID;
//                    pOItemPkgAppDetail.recUser = overAllResultStatusList.get(i).recUser;
//                    pOItemPkgAppDetail.DescrID = overAllResultStatusList.get(i).GenID;
                    updatePkgAppearance(pOItemPkgAppDetail);

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
            //Setting the ArrayAdapter data on the Spinner
            spinnerOverAllPkgAppearDesc8.setAdapter(masterArrary);
//            spinnerOverAllMasterPackingPkgAppear.setSelection(selectedMPos);
            spinnerOverAllPkgAppearDesc8.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (!spinnerPkgAppeOverAllTouched) {
                        return;
                    }
                    spinnerPkgAppeOverAllTouched = false;
//                    pOItemPkgAppDetail.pRowID = overAllResultStatusList.get(i).pGenRowID;
                    pOItemPkgAppDetail.pRowID = pkgAppList.get(7).pRowID;
                    pOItemPkgAppDetail.InspectionResultID = overAllResultStatusList.get(i).pGenRowID;
//                    pOItemPkgAppDetail.recUser = overAllResultStatusList.get(i).recUser;
//                    pOItemPkgAppDetail.DescrID = overAllResultStatusList.get(i).GenID;
                    updatePkgAppearance(pOItemPkgAppDetail);

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
            //Setting the ArrayAdapter data on the Spinner
            spinnerOverAllPkgAppearDesc9.setAdapter(masterArrary);
//            spinnerOverAllMasterPackingPkgAppear.setSelection(selectedMPos);
            spinnerOverAllPkgAppearDesc9.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (!spinnerPkgAppeOverAllTouched) {
                        return;
                    }
                    spinnerPkgAppeOverAllTouched = false;
//                    pOItemPkgAppDetail.pRowID = overAllResultStatusList.get(i).pGenRowID;
                    pOItemPkgAppDetail.pRowID = pkgAppList.get(8).pRowID;
                    pOItemPkgAppDetail.InspectionResultID = overAllResultStatusList.get(i).pGenRowID;
//                    pOItemPkgAppDetail.recUser = overAllResultStatusList.get(i).recUser;
//                    pOItemPkgAppDetail.DescrID = overAllResultStatusList.get(i).GenID;
                    updatePkgAppearance(pOItemPkgAppDetail);

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });

        }

        if (overAllResultStatusList != null && overAllResultStatusList.size() > 0
                && pkgAppList != null && pkgAppList.size() > 0) {

            for (int i = 0; i < pkgAppList.size(); i++) {
                if (i == 0) {
                    for (int j = 0; j < overAllResultStatusList.size(); j++) {
                        if (pkgAppList.get(i).InspectionResultID != null &&
                                pkgAppList.get(i).InspectionResultID.equals(overAllResultStatusList.get(j).pGenRowID)) {
                            for (int k = 0; k < statusList1.size(); k++) {
                                if (overAllResultStatusList.get(j).MainDescr.equals(statusList1.get(k))) {
                                    spinnerOverAllPkgAppearDesc1.setSelection(k);
                                }
                            }
                        }
                    }
                } else if (i == 1) {
                    for (int j = 0; j < overAllResultStatusList.size(); j++) {
                        if (pkgAppList.get(i).InspectionResultID != null &&
                                pkgAppList.get(i).InspectionResultID.equals(overAllResultStatusList.get(j).pGenRowID)) {
                            for (int k = 0; k < statusList1.size(); k++) {
                                if (overAllResultStatusList.get(j).MainDescr.equals(statusList1.get(k))) {
                                    spinnerOverAllPkgAppearDesc2.setSelection(k);
                                }
                            }
                        }
                    }
                } else if (i == 2) {
                    for (int j = 0; j < overAllResultStatusList.size(); j++) {
                        if (pkgAppList.get(i).InspectionResultID != null &&
                                pkgAppList.get(i).InspectionResultID.equals(overAllResultStatusList.get(j).pGenRowID)) {
                            for (int k = 0; k < statusList1.size(); k++) {
                                if (overAllResultStatusList.get(j).MainDescr.equals(statusList1.get(k))) {
                                    spinnerOverAllPkgAppearDesc3.setSelection(k);
                                }
                            }
                        }
                    }
                } else if (i == 3) {
                    for (int j = 0; j < overAllResultStatusList.size(); j++) {
                        if (pkgAppList.get(i).InspectionResultID != null &&
                                pkgAppList.get(i).InspectionResultID.equals(overAllResultStatusList.get(j).pGenRowID)) {
                            for (int k = 0; k < statusList1.size(); k++) {
                                if (overAllResultStatusList.get(j).MainDescr.equals(statusList1.get(k))) {
                                    spinnerOverAllPkgAppearDesc4.setSelection(k);
                                }
                            }
                        }
                    }
                } else if (i == 4) {
                    for (int j = 0; j < overAllResultStatusList.size(); j++) {
                        if (pkgAppList.get(i).InspectionResultID != null &&
                                pkgAppList.get(i).InspectionResultID.equals(overAllResultStatusList.get(j).pGenRowID)) {
                            for (int k = 0; k < statusList1.size(); k++) {
                                if (overAllResultStatusList.get(j).MainDescr.equals(statusList1.get(k))) {
                                    spinnerOverAllPkgAppearDesc5.setSelection(k);
                                }
                            }
                        }
                    }
                } else if (i == 5) {
                    for (int j = 0; j < overAllResultStatusList.size(); j++) {
                        if (pkgAppList.get(i).InspectionResultID != null &&
                                pkgAppList.get(i).InspectionResultID.equals(overAllResultStatusList.get(j).pGenRowID)) {
                            for (int k = 0; k < statusList1.size(); k++) {
                                if (overAllResultStatusList.get(j).MainDescr.equals(statusList1.get(k))) {
                                    spinnerOverAllPkgAppearDesc6.setSelection(k);
                                }
                            }
                        }
                    }
                } else if (i == 6) {
                    for (int j = 0; j < overAllResultStatusList.size(); j++) {
                        if (pkgAppList.get(i).InspectionResultID != null &&
                                pkgAppList.get(i).InspectionResultID.equals(overAllResultStatusList.get(j).pGenRowID)) {
                            for (int k = 0; k < statusList1.size(); k++) {
                                if (overAllResultStatusList.get(j).MainDescr.equals(statusList1.get(k))) {
                                    spinnerOverAllPkgAppearDesc7.setSelection(k);
                                }
                            }
                        }
                    }
                } else if (i == 7) {
                    for (int j = 0; j < overAllResultStatusList.size(); j++) {
                        if (pkgAppList.get(i).InspectionResultID != null &&
                                pkgAppList.get(i).InspectionResultID.equals(overAllResultStatusList.get(j).pGenRowID)) {
                            for (int k = 0; k < statusList1.size(); k++) {
                                if (overAllResultStatusList.get(j).MainDescr.equals(statusList1.get(k))) {
                                    spinnerOverAllPkgAppearDesc8.setSelection(k);
                                }
                            }
                        }
                    }
                } else if (i == 8) {
                    for (int j = 0; j < overAllResultStatusList.size(); j++) {
                        if (pkgAppList.get(i).InspectionResultID != null &&
                                pkgAppList.get(i).InspectionResultID.equals(overAllResultStatusList.get(j).pGenRowID)) {
                            for (int k = 0; k < statusList1.size(); k++) {
                                if (overAllResultStatusList.get(j).MainDescr.equals(statusList1.get(k))) {
                                    spinnerOverAllPkgAppearDesc9.setSelection(k);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void handlePackagingAppearanceSimpleOverAllResultDescSpinner() {

        final List<POItemPkgAppDetail> pkgAppList = POItemDtlHandler.getPKGAPPList(this);
        final List<GeneralModel> overAllResultStatusLists = GeneralMasterHandler.getGeneralList(this,
                FEnumerations.PACKAGE_APPEARANCE_OVERALL_RESULT_STATUS_GEN_ID);
        final List<GeneralModel> overAllResultStatusListPkgAppGenMstList = GeneralMasterHandler.getGeneralList(this,
                FEnumerations.E_OVERALL_RESULT_STATUS_GEN_ID);
        final List<SampleModal> sampleModals = POItemDtlHandler.getSampleSizeList(this);
        /*if (pkgAppList != null && pkgAppList.isEmpty()) {
            POItemDtlHandler.insertPOItemDtlPKGAppDetails(this, pOItemPkgAppDetail);
        }*/

        if (overAllResultStatusLists != null && overAllResultStatusLists.size() > 0) {
            PkgAppearDescHdrLL.setVisibility(View.VISIBLE);
            for (int i = 0; i < overAllResultStatusLists.size(); i++) {
                if (i == 0) {
                    llPkgAppearDesc1.setVisibility(View.VISIBLE);
                    txtPkgAppearDesc1.setText(overAllResultStatusLists.get(i).MainDescr);
                    if (pkgAppList != null && pkgAppList.size() > 0) {
                        /*for (int k = 0; k < pkgAppList.size(); k++) {
                            if (pkgAppList.get(k).pRowID.equals(overAllResultStatusLists.get(i).pGenRowID)) {
                                pOItemPkgAppDetail.pRowID = overAllResultStatusLists.get(i).pGenRowID;
                                POItemDtlHandler.insertPOItemDtlPKGAppDetails(this, pOItemPkgAppDetail,poItemDtl);
                            }
                        }*/
                    } else {
                        if (sampleModals != null && sampleModals.size() > 0) {
                            pOItemPkgAppDetail.SampleSizeID = sampleModals.get(0).SampleCode;
                            pOItemPkgAppDetail.SampleSizeValue = sampleModals.get(0).SampleVal;
                        }
                        if (overAllResultStatusListPkgAppGenMstList != null && overAllResultStatusListPkgAppGenMstList.size() > 0) {
                            pOItemPkgAppDetail.InspectionResultID = overAllResultStatusListPkgAppGenMstList.get(0).pGenRowID;
                        }
//                        pOItemPkgAppDetail.pRowID = overAllResultStatusLists.get(i).pGenRowID;
                        pOItemPkgAppDetail.pRowID = ItemInspectionDetailHandler.GeneratePK(ItemInspectionDetail.this, FEnumerations.E_TABLE_NAME_QRPOItemDtl_PKG_App_Details);
                        POItemDtlHandler.insertPOItemDtlPKGAppDetails(this, pOItemPkgAppDetail, poItemDtl);
                    }

                } else if (i == 1) {
                    llPkgAppearDesc2.setVisibility(View.VISIBLE);
                    txtPkgAppearDesc2.setText(overAllResultStatusLists.get(i).MainDescr);

                    if (pkgAppList != null && pkgAppList.size() > 0) {
                        /*for (int k = 0; k < pkgAppList.size(); k++) {
                            if (pkgAppList.get(k).pRowID.equals(overAllResultStatusLists.get(i).pGenRowID)) {
                                pOItemPkgAppDetail.pRowID = overAllResultStatusLists.get(i).pGenRowID;
                                POItemDtlHandler.insertPOItemDtlPKGAppDetails(this, pOItemPkgAppDetail,poItemDtl);
                            }
                        }*/
                    } else {
                        pOItemPkgAppDetail.pRowID = overAllResultStatusLists.get(i).pGenRowID;
                        if (sampleModals != null && sampleModals.size() > 0) {
                            pOItemPkgAppDetail.SampleSizeID = sampleModals.get(0).SampleCode;
                            pOItemPkgAppDetail.SampleSizeValue = sampleModals.get(0).SampleVal;
                        }
                        if (overAllResultStatusListPkgAppGenMstList != null && overAllResultStatusListPkgAppGenMstList.size() > 0) {
                            pOItemPkgAppDetail.InspectionResultID = overAllResultStatusListPkgAppGenMstList.get(0).pGenRowID;
                        }
                        pOItemPkgAppDetail.pRowID = ItemInspectionDetailHandler.GeneratePK(ItemInspectionDetail.this, FEnumerations.E_TABLE_NAME_QRPOItemDtl_PKG_App_Details);
                        POItemDtlHandler.insertPOItemDtlPKGAppDetails(this, pOItemPkgAppDetail, poItemDtl);
                    }
                } else if (i == 2) {
                    llPkgAppearDesc3.setVisibility(View.VISIBLE);
                    txtPkgAppearDesc3.setText(overAllResultStatusLists.get(i).MainDescr);
                    if (pkgAppList != null && pkgAppList.size() > 0) {
                        /*for (int k = 0; k < pkgAppList.size(); k++) {
                            if (pkgAppList.get(k).pRowID.equals(overAllResultStatusLists.get(i).pGenRowID)) {
                                pOItemPkgAppDetail.pRowID = overAllResultStatusLists.get(i).pGenRowID;
                                POItemDtlHandler.insertPOItemDtlPKGAppDetails(this, pOItemPkgAppDetail,poItemDtl);
                            }
                        }*/
                    } else {
                        pOItemPkgAppDetail.pRowID = overAllResultStatusLists.get(i).pGenRowID;
                        if (sampleModals != null && sampleModals.size() > 0) {
                            pOItemPkgAppDetail.SampleSizeID = sampleModals.get(0).SampleCode;
                            pOItemPkgAppDetail.SampleSizeValue = sampleModals.get(0).SampleVal;
                        }
                        if (overAllResultStatusListPkgAppGenMstList != null && overAllResultStatusListPkgAppGenMstList.size() > 0) {
                            pOItemPkgAppDetail.InspectionResultID = overAllResultStatusListPkgAppGenMstList.get(0).pGenRowID;
                        }
                        pOItemPkgAppDetail.pRowID = ItemInspectionDetailHandler.GeneratePK(ItemInspectionDetail.this, FEnumerations.E_TABLE_NAME_QRPOItemDtl_PKG_App_Details);
                        POItemDtlHandler.insertPOItemDtlPKGAppDetails(this, pOItemPkgAppDetail, poItemDtl);
                    }
                } else if (i == 3) {
                    llPkgAppearDesc4.setVisibility(View.VISIBLE);
                    txtPkgAppearDesc4.setText(overAllResultStatusLists.get(i).MainDescr);
                    if (pkgAppList != null && pkgAppList.size() > 0) {
                        /*for (int k = 0; k < pkgAppList.size(); k++) {
                            if (pkgAppList.get(k).pRowID.equals(overAllResultStatusLists.get(i).pGenRowID)) {
                                pOItemPkgAppDetail.pRowID = overAllResultStatusLists.get(i).pGenRowID;
                                POItemDtlHandler.insertPOItemDtlPKGAppDetails(this, pOItemPkgAppDetail,poItemDtl);
                            }
                        }*/
                    } else {
                        pOItemPkgAppDetail.pRowID = overAllResultStatusLists.get(i).pGenRowID;
                        if (sampleModals != null && sampleModals.size() > 0) {
                            pOItemPkgAppDetail.SampleSizeID = sampleModals.get(0).SampleCode;
                            pOItemPkgAppDetail.SampleSizeValue = sampleModals.get(0).SampleVal;
                        }
                        if (overAllResultStatusListPkgAppGenMstList != null && overAllResultStatusListPkgAppGenMstList.size() > 0) {
                            pOItemPkgAppDetail.InspectionResultID = overAllResultStatusListPkgAppGenMstList.get(0).pGenRowID;
                        }
                        pOItemPkgAppDetail.pRowID = ItemInspectionDetailHandler.GeneratePK(ItemInspectionDetail.this, FEnumerations.E_TABLE_NAME_QRPOItemDtl_PKG_App_Details);
                        POItemDtlHandler.insertPOItemDtlPKGAppDetails(this, pOItemPkgAppDetail, poItemDtl);
                    }
                } else if (i == 4) {
                    llPkgAppearDesc5.setVisibility(View.VISIBLE);
                    txtPkgAppearDesc5.setText(overAllResultStatusLists.get(i).MainDescr);
                    if (pkgAppList != null && pkgAppList.size() > 0) {
                       /* for (int k = 0; k < pkgAppList.size(); k++) {
                            if (pkgAppList.get(k).pRowID.equals(overAllResultStatusLists.get(i).pGenRowID)) {
                                pOItemPkgAppDetail.pRowID = overAllResultStatusLists.get(i).pGenRowID;
                                POItemDtlHandler.insertPOItemDtlPKGAppDetails(this, pOItemPkgAppDetail,poItemDtl);
                            }
                        }*/
                    } else {
                        pOItemPkgAppDetail.pRowID = overAllResultStatusLists.get(i).pGenRowID;
                        if (sampleModals != null && sampleModals.size() > 0) {
                            pOItemPkgAppDetail.SampleSizeID = sampleModals.get(0).SampleCode;
                            pOItemPkgAppDetail.SampleSizeValue = sampleModals.get(0).SampleVal;
                        }
                        if (overAllResultStatusListPkgAppGenMstList != null && overAllResultStatusListPkgAppGenMstList.size() > 0) {
                            pOItemPkgAppDetail.InspectionResultID = overAllResultStatusListPkgAppGenMstList.get(0).pGenRowID;
                        }
                        pOItemPkgAppDetail.pRowID = ItemInspectionDetailHandler.GeneratePK(ItemInspectionDetail.this, FEnumerations.E_TABLE_NAME_QRPOItemDtl_PKG_App_Details);
                        POItemDtlHandler.insertPOItemDtlPKGAppDetails(this, pOItemPkgAppDetail, poItemDtl);
                    }
                } else if (i == 5) {
                    llPkgAppearDesc6.setVisibility(View.VISIBLE);
                    txtPkgAppearDesc6.setText(overAllResultStatusLists.get(i).MainDescr);
                    if (pkgAppList != null && pkgAppList.size() > 0) {
                        /*for (int k = 0; k < pkgAppList.size(); k++) {
                            if (pkgAppList.get(k).pRowID.equals(overAllResultStatusLists.get(i).pGenRowID)) {
                                pOItemPkgAppDetail.pRowID = overAllResultStatusLists.get(i).pGenRowID;
                                POItemDtlHandler.insertPOItemDtlPKGAppDetails(this, pOItemPkgAppDetail,poItemDtl);
                            }
                        }*/
                    } else {
                        pOItemPkgAppDetail.pRowID = overAllResultStatusLists.get(i).pGenRowID;
                        if (sampleModals != null && sampleModals.size() > 0) {
                            pOItemPkgAppDetail.SampleSizeID = sampleModals.get(0).SampleCode;
                            pOItemPkgAppDetail.SampleSizeValue = sampleModals.get(0).SampleVal;
                        }
                        if (overAllResultStatusListPkgAppGenMstList != null && overAllResultStatusListPkgAppGenMstList.size() > 0) {
                            pOItemPkgAppDetail.InspectionResultID = overAllResultStatusListPkgAppGenMstList.get(0).pGenRowID;
                        }
                        pOItemPkgAppDetail.pRowID = ItemInspectionDetailHandler.GeneratePK(ItemInspectionDetail.this, FEnumerations.E_TABLE_NAME_QRPOItemDtl_PKG_App_Details);
                        POItemDtlHandler.insertPOItemDtlPKGAppDetails(this, pOItemPkgAppDetail, poItemDtl);
                    }
                } else if (i == 6) {
                    llPkgAppearDesc7.setVisibility(View.VISIBLE);
                    txtPkgAppearDesc7.setText(overAllResultStatusLists.get(i).MainDescr);
                    if (pkgAppList != null && pkgAppList.size() > 0) {
                        /*for (int k = 0; k < pkgAppList.size(); k++) {
                            if (pkgAppList.get(k).pRowID.equals(overAllResultStatusLists.get(i).pGenRowID)) {
                                pOItemPkgAppDetail.pRowID = overAllResultStatusLists.get(i).pGenRowID;
                                POItemDtlHandler.insertPOItemDtlPKGAppDetails(this, pOItemPkgAppDetail,poItemDtl);
                            }
                        }*/
                    } else {
                        pOItemPkgAppDetail.pRowID = overAllResultStatusLists.get(i).pGenRowID;
                        if (sampleModals != null && sampleModals.size() > 0) {
                            pOItemPkgAppDetail.SampleSizeID = sampleModals.get(0).SampleCode;
                            pOItemPkgAppDetail.SampleSizeValue = sampleModals.get(0).SampleVal;
                        }
                        if (overAllResultStatusListPkgAppGenMstList != null && overAllResultStatusListPkgAppGenMstList.size() > 0) {
                            pOItemPkgAppDetail.InspectionResultID = overAllResultStatusListPkgAppGenMstList.get(0).pGenRowID;
                        }
                        pOItemPkgAppDetail.pRowID = ItemInspectionDetailHandler.GeneratePK(ItemInspectionDetail.this, FEnumerations.E_TABLE_NAME_QRPOItemDtl_PKG_App_Details);
                        POItemDtlHandler.insertPOItemDtlPKGAppDetails(this, pOItemPkgAppDetail, poItemDtl);
                    }
                } else if (i == 7) {
                    llPkgAppearDesc8.setVisibility(View.VISIBLE);
                    txtPkgAppearDesc8.setText(overAllResultStatusLists.get(i).MainDescr);
                    if (pkgAppList != null && pkgAppList.size() > 0) {
                        /*for (int k = 0; k < pkgAppList.size(); k++) {
                            if (pkgAppList.get(k).pRowID.equals(overAllResultStatusLists.get(i).pGenRowID)) {
                                pOItemPkgAppDetail.pRowID = overAllResultStatusLists.get(i).pGenRowID;
                                POItemDtlHandler.insertPOItemDtlPKGAppDetails(this, pOItemPkgAppDetail,poItemDtl);
                            }
                        }*/
                    } else {
                        pOItemPkgAppDetail.pRowID = overAllResultStatusLists.get(i).pGenRowID;
                        if (sampleModals != null && sampleModals.size() > 0) {
                            pOItemPkgAppDetail.SampleSizeID = sampleModals.get(0).SampleCode;
                            pOItemPkgAppDetail.SampleSizeValue = sampleModals.get(0).SampleVal;
                        }
                        if (overAllResultStatusListPkgAppGenMstList != null && overAllResultStatusListPkgAppGenMstList.size() > 0) {
                            pOItemPkgAppDetail.InspectionResultID = overAllResultStatusListPkgAppGenMstList.get(0).pGenRowID;
                        }
                        pOItemPkgAppDetail.pRowID = ItemInspectionDetailHandler.GeneratePK(ItemInspectionDetail.this, FEnumerations.E_TABLE_NAME_QRPOItemDtl_PKG_App_Details);
                        POItemDtlHandler.insertPOItemDtlPKGAppDetails(this, pOItemPkgAppDetail, poItemDtl);
                    }
                } else if (i == 8) {
                    llPkgAppearDesc9.setVisibility(View.VISIBLE);
                    txtPkgAppearDesc9.setText(overAllResultStatusLists.get(i).MainDescr);
//
                    if (pkgAppList != null && pkgAppList.size() > 0) {
                        /*for (int k = 0; k < pkgAppList.size(); k++) {
                            if (pkgAppList.get(k).pRowID.equals(overAllResultStatusLists.get(i).pGenRowID)) {
                                pOItemPkgAppDetail.pRowID = overAllResultStatusLists.get(i).pGenRowID;
                                POItemDtlHandler.insertPOItemDtlPKGAppDetails(this, pOItemPkgAppDetail,poItemDtl);
                            }
                        }*/
                    } else {
                        pOItemPkgAppDetail.pRowID = overAllResultStatusLists.get(i).pGenRowID;
                        if (sampleModals != null && sampleModals.size() > 0) {
                            pOItemPkgAppDetail.SampleSizeID = sampleModals.get(0).SampleCode;
                            pOItemPkgAppDetail.SampleSizeValue = sampleModals.get(0).SampleVal;
                        }
                        if (overAllResultStatusListPkgAppGenMstList != null && overAllResultStatusListPkgAppGenMstList.size() > 0) {
                            pOItemPkgAppDetail.InspectionResultID = overAllResultStatusListPkgAppGenMstList.get(0).pGenRowID;
                        }
                        pOItemPkgAppDetail.pRowID = ItemInspectionDetailHandler.GeneratePK(ItemInspectionDetail.this, FEnumerations.E_TABLE_NAME_QRPOItemDtl_PKG_App_Details);
                        POItemDtlHandler.insertPOItemDtlPKGAppDetails(this, pOItemPkgAppDetail, poItemDtl);
                    }
                }
            }
        }

        final List<POItemPkgAppDetail> pkgAppNewList = POItemDtlHandler.getPKGAPPList(this);
        handlePkgAppDescOverAllResult(overAllResultStatusLists, pkgAppNewList);

        spinnerPkgAppSampleTouched = false;

        List<String> sampleList = new ArrayList<>();
        if (sampleModals != null && sampleModals.size() > 0) {
            for (int i = 0; i < sampleModals.size(); i++) {
                sampleList.add(sampleModals.get(i).MainDescr + "(" + sampleModals.get(i).SampleVal + ")");
            }

            ArrayAdapter sampleUnitArrary = new ArrayAdapter(this, R.layout.dialog_list_item, sampleList);
            sampleUnitArrary.setDropDownViewResource(R.layout.dialog_list_item);

            spinnerSampleSizePkgAppearDesc1.setOnTouchListener((v, me) -> {
                spinnerPkgAppSampleTouched = true;
                v.performClick();
                return false;
            });
            spinnerSampleSizePkgAppearDesc2.setOnTouchListener((v, me) -> {
                spinnerPkgAppSampleTouched = true;
                v.performClick();
                return false;
            });
            spinnerSampleSizePkgAppearDesc3.setOnTouchListener((v, me) -> {
                spinnerPkgAppSampleTouched = true;
                v.performClick();
                return false;
            });
            spinnerSampleSizePkgAppearDesc4.setOnTouchListener((v, me) -> {
                spinnerPkgAppSampleTouched = true;
                v.performClick();
                return false;
            });
            spinnerSampleSizePkgAppearDesc5.setOnTouchListener((v, me) -> {
                spinnerPkgAppSampleTouched = true;
                v.performClick();
                return false;
            });
            spinnerSampleSizePkgAppearDesc6.setOnTouchListener((v, me) -> {
                spinnerPkgAppSampleTouched = true;
                v.performClick();
                return false;
            });
            spinnerSampleSizePkgAppearDesc7.setOnTouchListener((v, me) -> {
                spinnerPkgAppSampleTouched = true;
                v.performClick();
                return false;
            });
            spinnerSampleSizePkgAppearDesc8.setOnTouchListener((v, me) -> {
                spinnerPkgAppSampleTouched = true;
                v.performClick();
                return false;
            });
            spinnerSampleSizePkgAppearDesc9.setOnTouchListener((v, me) -> {
                spinnerPkgAppSampleTouched = true;
                v.performClick();
                return false;
            });

            //Setting the ArrayAdapter data on the Spinner
            spinnerSampleSizePkgAppearDesc1.setAdapter(sampleUnitArrary);
//            spinnerSampleSizeUnitPkgAppearPacking.setSelection(selectedUnitPos);//commented by shekjhar
            spinnerSampleSizePkgAppearDesc1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (!spinnerPkgAppSampleTouched) {
                        return;
                    }
                    spinnerPkgAppSampleTouched = false;
                    pOItemPkgAppDetail.pRowID = pkgAppNewList.get(0).pRowID;
                    pOItemPkgAppDetail.SampleSizeID = sampleModals.get(i).SampleCode;
                    pOItemPkgAppDetail.SampleSizeValue = sampleModals.get(i).SampleVal;
                    updatePkgAppearance(pOItemPkgAppDetail);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            spinnerSampleSizePkgAppearDesc2.setAdapter(sampleUnitArrary);
//            spinnerSampleSizeUnitPkgAppearPacking.setSelection(selectedUnitPos);//commented by shekjhar
            spinnerSampleSizePkgAppearDesc2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (!spinnerPkgAppSampleTouched) {
                        return;
                    }
                    spinnerPkgAppSampleTouched = false;
                    pOItemPkgAppDetail.pRowID = pkgAppNewList.get(1).pRowID;
                    pOItemPkgAppDetail.SampleSizeID = sampleModals.get(i).SampleCode;
                    pOItemPkgAppDetail.SampleSizeValue = sampleModals.get(i).SampleVal;
                    updatePkgAppearance(pOItemPkgAppDetail);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            spinnerSampleSizePkgAppearDesc3.setAdapter(sampleUnitArrary);
//            spinnerSampleSizeUnitPkgAppearPacking.setSelection(selectedUnitPos);//commented by shekjhar
            spinnerSampleSizePkgAppearDesc3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (!spinnerPkgAppSampleTouched) {
                        return;
                    }
                    spinnerPkgAppSampleTouched = false;
//                    packagePoItemDetalDetail.PKG_Me_Unit_SampleSizeID = sampleModals.get(i).SampleCode;
                    pOItemPkgAppDetail.pRowID = pkgAppNewList.get(2).pRowID;
                    pOItemPkgAppDetail.SampleSizeID = sampleModals.get(i).SampleCode;
                    pOItemPkgAppDetail.SampleSizeValue = sampleModals.get(i).SampleVal;
                    updatePkgAppearance(pOItemPkgAppDetail);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            spinnerSampleSizePkgAppearDesc4.setAdapter(sampleUnitArrary);
//            spinnerSampleSizeUnitPkgAppearPacking.setSelection(selectedUnitPos);//commented by shekjhar
            spinnerSampleSizePkgAppearDesc4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (!spinnerPkgAppSampleTouched) {
                        return;
                    }
                    spinnerPkgAppSampleTouched = false;
//                    packagePoItemDetalDetail.PKG_Me_Unit_SampleSizeID = sampleModals.get(i).SampleCode;
                    pOItemPkgAppDetail.pRowID = pkgAppNewList.get(3).pRowID;
                    pOItemPkgAppDetail.SampleSizeID = sampleModals.get(i).SampleCode;
                    pOItemPkgAppDetail.SampleSizeValue = sampleModals.get(i).SampleVal;
                    updatePkgAppearance(pOItemPkgAppDetail);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            spinnerSampleSizePkgAppearDesc5.setAdapter(sampleUnitArrary);
//            spinnerSampleSizeUnitPkgAppearPacking.setSelection(selectedUnitPos);//commented by shekjhar
            spinnerSampleSizePkgAppearDesc5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (!spinnerPkgAppSampleTouched) {
                        return;
                    }
                    spinnerPkgAppSampleTouched = false;
//                    packagePoItemDetalDetail.PKG_Me_Unit_SampleSizeID = sampleModals.get(i).SampleCode;
                    pOItemPkgAppDetail.pRowID = pkgAppNewList.get(4).pRowID;
                    pOItemPkgAppDetail.SampleSizeID = sampleModals.get(i).SampleCode;
                    pOItemPkgAppDetail.SampleSizeValue = sampleModals.get(i).SampleVal;
                    updatePkgAppearance(pOItemPkgAppDetail);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            spinnerSampleSizePkgAppearDesc6.setAdapter(sampleUnitArrary);
//            spinnerSampleSizeUnitPkgAppearPacking.setSelection(selectedUnitPos);//commented by shekjhar
            spinnerSampleSizePkgAppearDesc6.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (!spinnerPkgAppSampleTouched) {
                        return;
                    }
                    spinnerPkgAppSampleTouched = false;
//                    packagePoItemDetalDetail.PKG_Me_Unit_SampleSizeID = sampleModals.get(i).SampleCode;
                    pOItemPkgAppDetail.pRowID = pkgAppNewList.get(5).pRowID;
                    pOItemPkgAppDetail.SampleSizeID = sampleModals.get(i).SampleCode;
                    pOItemPkgAppDetail.SampleSizeValue = sampleModals.get(i).SampleVal;
                    updatePkgAppearance(pOItemPkgAppDetail);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            spinnerSampleSizePkgAppearDesc7.setAdapter(sampleUnitArrary);
//            spinnerSampleSizeUnitPkgAppearPacking.setSelection(selectedUnitPos);//commented by shekjhar
            spinnerSampleSizePkgAppearDesc7.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (!spinnerPkgAppSampleTouched) {
                        return;
                    }
                    spinnerPkgAppSampleTouched = false;
//                    packagePoItemDetalDetail.PKG_Me_Unit_SampleSizeID = sampleModals.get(i).SampleCode;
                    pOItemPkgAppDetail.pRowID = pkgAppNewList.get(6).pRowID;
                    pOItemPkgAppDetail.SampleSizeID = sampleModals.get(i).SampleCode;
                    pOItemPkgAppDetail.SampleSizeValue = sampleModals.get(i).SampleVal;
                    updatePkgAppearance(pOItemPkgAppDetail);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            spinnerSampleSizePkgAppearDesc8.setAdapter(sampleUnitArrary);
//            spinnerSampleSizeUnitPkgAppearPacking.setSelection(selectedUnitPos);//commented by shekjhar
            spinnerSampleSizePkgAppearDesc8.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (!spinnerPkgAppSampleTouched) {
                        return;
                    }
                    spinnerPkgAppSampleTouched = false;
//                    packagePoItemDetalDetail.PKG_Me_Unit_SampleSizeID = sampleModals.get(i).SampleCode;
                    pOItemPkgAppDetail.pRowID = pkgAppNewList.get(7).pRowID;
                    pOItemPkgAppDetail.SampleSizeID = sampleModals.get(i).SampleCode;
                    pOItemPkgAppDetail.SampleSizeValue = sampleModals.get(i).SampleVal;
                    updatePkgAppearance(pOItemPkgAppDetail);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            spinnerSampleSizePkgAppearDesc9.setAdapter(sampleUnitArrary);
//            spinnerSampleSizeUnitPkgAppearPacking.setSelection(selectedUnitPos);//commented by shekjhar
            spinnerSampleSizePkgAppearDesc9.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (!spinnerPkgAppSampleTouched) {
                        return;
                    }
                    spinnerPkgAppSampleTouched = false;
//                    packagePoItemDetalDetail.PKG_Me_Unit_SampleSizeID = sampleModals.get(i).SampleCode;
                    pOItemPkgAppDetail.pRowID = pkgAppNewList.get(8).pRowID;
                    pOItemPkgAppDetail.SampleSizeID = sampleModals.get(i).SampleCode;
                    pOItemPkgAppDetail.SampleSizeValue = sampleModals.get(i).SampleVal;
                    updatePkgAppearance(pOItemPkgAppDetail);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            if (pkgAppNewList != null && pkgAppNewList.size() > 0) {

                for (int i = 0; i < pkgAppNewList.size(); i++) {
                    if (i == 0) {
                        for (int j = 0; j < sampleModals.size(); j++) {
                            if (pkgAppNewList.get(i).SampleSizeID != null &&
                                    pkgAppNewList.get(i).SampleSizeID.equals(sampleModals.get(j).SampleCode)) {
                                for (int k = 0; k < sampleList.size(); k++) {
                                    String valStr = sampleModals.get(j).MainDescr + "(" + sampleModals.get(j).SampleVal + ")";
                                    if (valStr.equals(sampleList.get(k))) {
                                        spinnerSampleSizePkgAppearDesc1.setSelection(k);
                                    }
                                }
                            }
                        }
                    } else if (i == 1) {
                        for (int j = 0; j < sampleModals.size(); j++) {
                            if (pkgAppNewList.get(i).SampleSizeID != null &&
                                    pkgAppNewList.get(i).SampleSizeID.equals(sampleModals.get(j).SampleCode)) {
                                for (int k = 0; k < sampleList.size(); k++) {
                                    String valStr = sampleModals.get(j).MainDescr + "(" + sampleModals.get(j).SampleVal + ")";
                                    if (valStr.equals(sampleList.get(k))) {
                                        spinnerSampleSizePkgAppearDesc2.setSelection(k);
                                    }
                                }
                            }
                        }
                    } else if (i == 2) {
                        for (int j = 0; j < sampleModals.size(); j++) {
                            if (pkgAppNewList.get(i).SampleSizeID != null &&
                                    pkgAppNewList.get(i).SampleSizeID.equals(sampleModals.get(j).SampleCode)) {
                                for (int k = 0; k < sampleList.size(); k++) {
                                    String valStr = sampleModals.get(j).MainDescr + "(" + sampleModals.get(j).SampleVal + ")";
                                    if (valStr.equals(sampleList.get(k))) {
                                        spinnerSampleSizePkgAppearDesc3.setSelection(k);
                                    }
                                }
                            }
                        }
                    } else if (i == 3) {
                        for (int j = 0; j < sampleModals.size(); j++) {
                            if (pkgAppNewList.get(i).SampleSizeID != null &&
                                    pkgAppNewList.get(i).SampleSizeID.equals(sampleModals.get(j).SampleCode)) {
                                for (int k = 0; k < sampleList.size(); k++) {
                                    String valStr = sampleModals.get(j).MainDescr + "(" + sampleModals.get(j).SampleVal + ")";
                                    if (valStr.equals(sampleList.get(k))) {
                                        spinnerSampleSizePkgAppearDesc4.setSelection(k);
                                    }
                                }
                            }
                        }
                    } else if (i == 4) {
                        for (int j = 0; j < sampleModals.size(); j++) {
                            if (pkgAppNewList.get(i).SampleSizeID != null &&
                                    pkgAppNewList.get(i).SampleSizeID.equals(sampleModals.get(j).SampleCode)) {
                                for (int k = 0; k < sampleList.size(); k++) {
                                    String valStr = sampleModals.get(j).MainDescr + "(" + sampleModals.get(j).SampleVal + ")";
                                    if (valStr.equals(sampleList.get(k))) {
                                        spinnerSampleSizePkgAppearDesc5.setSelection(k);
                                    }
                                }
                            }
                        }
                    } else if (i == 5) {
                        for (int j = 0; j < sampleModals.size(); j++) {
                            if (pkgAppNewList.get(i).SampleSizeID != null &&
                                    pkgAppNewList.get(i).SampleSizeID.equals(sampleModals.get(j).SampleCode)) {
                                for (int k = 0; k < sampleList.size(); k++) {
                                    String valStr = sampleModals.get(j).MainDescr + "(" + sampleModals.get(j).SampleVal + ")";
                                    if (valStr.equals(sampleList.get(k))) {
                                        spinnerSampleSizePkgAppearDesc6.setSelection(k);
                                    }
                                }
                            }
                        }
                    } else if (i == 6) {
                        for (int j = 0; j < sampleModals.size(); j++) {
                            if (pkgAppNewList.get(i).SampleSizeID != null &&
                                    pkgAppNewList.get(i).SampleSizeID.equals(sampleModals.get(j).SampleCode)) {
                                for (int k = 0; k < sampleList.size(); k++) {
                                    String valStr = sampleModals.get(j).MainDescr + "(" + sampleModals.get(j).SampleVal + ")";
                                    if (valStr.equals(sampleList.get(k))) {
                                        spinnerSampleSizePkgAppearDesc7.setSelection(k);
                                    }
                                }
                            }
                        }
                    } else if (i == 7) {
                        for (int j = 0; j < sampleModals.size(); j++) {
                            if (pkgAppNewList.get(i).SampleSizeID != null &&
                                    pkgAppNewList.get(i).SampleSizeID.equals(sampleModals.get(j).SampleCode)) {
                                for (int k = 0; k < sampleList.size(); k++) {
                                    String valStr = sampleModals.get(j).MainDescr + "(" + sampleModals.get(j).SampleVal + ")";
                                    if (valStr.equals(sampleList.get(k))) {
                                        spinnerSampleSizePkgAppearDesc8.setSelection(k);
                                    }
                                }
                            }
                        }
                    } else if (i == 8) {
                        for (int j = 0; j < sampleModals.size(); j++) {
                            if (pkgAppNewList.get(i).SampleSizeID != null &&
                                    pkgAppNewList.get(i).SampleSizeID.equals(sampleModals.get(j).SampleCode)) {
                                for (int k = 0; k < sampleList.size(); k++) {
                                    String valStr = sampleModals.get(j).MainDescr + "(" + sampleModals.get(j).SampleVal + ")";
                                    if (valStr.equals(sampleList.get(k))) {
                                        spinnerSampleSizePkgAppearDesc9.setSelection(k);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void updatePkgAppearance(POItemPkgAppDetail pOItemPkgAppDetail) {
        final List<POItemPkgAppDetail> pkgAppList = POItemDtlHandler.getPKGAPPList(this);
        for (int i = 0; i < pkgAppList.size(); i++) {
            if (pkgAppList.get(i).pRowID.equals(pOItemPkgAppDetail.pRowID)) {
                POItemDtlHandler.updatePOItemDtlPKGAppDetails(this, pOItemPkgAppDetail);
            }
        }
    }

    private void insertSampleCollected(SampleCollectedModal sampleCollectedModal) {
        POItemDtlHandler.insertSampleCollected(this, sampleCollectedModal, poItemDtl);
    }

    private void insertOnSite(OnSIteModal onSIteModalItem) {
        POItemDtlHandler.insertOnSIte(this, onSIteModalItem, poItemDtl);
    }

    private void updateSampleCollectedTab() {
        final List<SampleCollectedModal> sampleCollectedList = POItemDtlHandler.getSampleCollectedList(this);

        if (sampleCollectedList != null && sampleCollectedList.size() > 0) {
            for (int i = 0; i < sampleCollectedList.size(); i++) {
                if (i == 0) {
                    sampleCollectedModal.pRowID = sampleCollectedList.get(i).pRowID;
                    sampleCollectedModal.SamplePurposeID = sampleCollectedList.get(i).SamplePurposeID;
                    sampleCollectedModal.SampleNumber = Integer.parseInt(txtSampleCollectedNumber.getText().toString());
                    POItemDtlHandler.updateSampleCollected(this, sampleCollectedModal);
                } else if (i == 1) {
                    sampleCollectedModal.pRowID = sampleCollectedList.get(i).pRowID;
                    sampleCollectedModal.SamplePurposeID = sampleCollectedList.get(i).SamplePurposeID;
                    sampleCollectedModal.SampleNumber = Integer.parseInt(txtSampleCollectedNumber1.getText().toString());
                    POItemDtlHandler.updateSampleCollected(this, sampleCollectedModal);
                } else if (i == 2) {
                    sampleCollectedModal.pRowID = sampleCollectedList.get(i).pRowID;
                    sampleCollectedModal.SamplePurposeID = sampleCollectedList.get(i).SamplePurposeID;
                    sampleCollectedModal.SampleNumber = Integer.parseInt(txtSampleCollectedNumber2.getText().toString());
                    POItemDtlHandler.updateSampleCollected(this, sampleCollectedModal);
                } else if (i == 3) {
                    sampleCollectedModal.pRowID = sampleCollectedList.get(i).pRowID;
                    sampleCollectedModal.SamplePurposeID = sampleCollectedList.get(i).SamplePurposeID;
                    sampleCollectedModal.SampleNumber = Integer.parseInt(txtSampleCollectedNumber3.getText().toString());
                    POItemDtlHandler.updateSampleCollected(this, sampleCollectedModal);
                } else if (i == 4) {
                    sampleCollectedModal.pRowID = sampleCollectedList.get(i).pRowID;
                    sampleCollectedModal.SamplePurposeID = sampleCollectedList.get(i).SamplePurposeID;
                    sampleCollectedModal.SampleNumber = Integer.parseInt(txtSampleCollectedNumber4.getText().toString());
                    POItemDtlHandler.updateSampleCollected(this, sampleCollectedModal);
                }

            }
        }
    }

    private void updateOnSite(OnSIteModal onSIteModalItem) {

        boolean isUpdateData = false;
        if (onSIteModalItem.pRowID != null) {
            final List<GeneralModel> onSiteMasterList = GeneralMasterHandler.getGeneralList(this,
                    FEnumerations.ONSITE_OVERALL_RESULT_STATUS_GEN_ID);
            Log.e("onSiteMasterList", "onSiteMasterList list" + onSiteMasterList.size());
            POItemDtlHandler.updateOnSIte(this, onSIteModalItem);
           /* if (onSiteMasterList != null && onSiteMasterList.size() > 0) {
                for (int i = 0; i < onSiteMasterList.size(); i++) {
//                    Log.e("onSiteMasterList", "onSiteMasterList list pGenRowID=" + onSiteMasterList.get(i).pGenRowID);
//                    Log.e("onSIteModalItem", "onSIteModalItem pRowID=" + onSIteModalItem.pRowID);
//                    Log.e("onSIteModalItem", "onSIteModalItem InspectionLevelID= " + onSIteModalItem.InspectionLevelID);
//                    Log.e("onSIteModalItem", "onSIteModalItem SampleSizeID= " + onSIteModalItem.SampleSizeID);
//                    Log.e("onSIteModalItem", "onSIteModalItem SampleSizeValue= " + onSIteModalItem.SampleSizeValue);
//                    Log.e("onSIteModalItem", "onSIteModalItem InspectionResultID= " + onSIteModalItem.InspectionResultID);
                    if (onSIteModalItem.pRowID.equals(onSiteMasterList.get(i).pGenRowID)) {
                        //need to update
//                        isUpdateData = true;


                        POItemDtlHandler.updateOnSIte(this, onSIteModalItem);
                    }
                }
            }*/
        }
        handleOnSiteRemark();
    }

    private void handlePackagingAppearanceOverAllResultSpinner() {


        List<String> statusList = new ArrayList<>();
        final List<GeneralModel> overAllResultStatusList = GeneralMasterHandler.
                getGeneralList(this, FEnumerations.E_OVERALL_RESULT_STATUS_GEN_ID);
        if (overAllResultStatusList != null && overAllResultStatusList.size() > 0) {
            int selectedMPos = 0, selectedIPos = 0, selectedUPos = 0, selectedPPos = 0, selectedOPos = 0;
            for (int i = 0; i < overAllResultStatusList.size(); i++) {
                statusList.add(overAllResultStatusList.get(i).MainDescr);
                if (overAllResultStatusList.get(i).pGenRowID.equals(packagePoItemDetalDetail.PKG_Me_Master_InspectionResultID)) {
                    selectedMPos = i;
                }
                if (overAllResultStatusList.get(i).pGenRowID.equals(packagePoItemDetalDetail.PKG_Me_Inner_InspectionResultID)) {
                    selectedIPos = i;
                }
                if (overAllResultStatusList.get(i).pGenRowID.equals(packagePoItemDetalDetail.PKG_Me_Unit_InspectionResultID)) {
                    selectedUPos = i;
                }
                if (overAllResultStatusList.get(i).pGenRowID.equals(packagePoItemDetalDetail.PKG_Me_Pallet_InspectionResultID)) {
                    selectedPPos = i;
                }
                if (overAllResultStatusList.get(i).pGenRowID.equals(packagePoItemDetalDetail.PKG_App_ShippingMark_InspectionResultID)) {
                    selectedOPos = i;
                }
            }


            //Creating the ArrayAdapter instance having the country list
            ArrayAdapter masterArrary = new ArrayAdapter(this, R.layout.dialog_list_item, statusList);
            masterArrary.setDropDownViewResource(R.layout.dialog_list_item);
            //Setting the ArrayAdapter data on the Spinner
            spinnerOverAllMasterPackingPkgAppear.setAdapter(masterArrary);
            spinnerOverAllMasterPackingPkgAppear.setSelection(selectedMPos);
            spinnerOverAllMasterPackingPkgAppear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    packagePoItemDetalDetail.PKG_App_Master_InspectionResultID = overAllResultStatusList.get(i).pGenRowID;
                    if (packagePoItemDetalDetail.PKG_App_Master_InspectionResultID.equals(FEnumerations.E_OVER_ALL_FAIL_RESULT)) {
                        packagePoItemDetalDetail.PKG_App_InspectionResultID = packagePoItemDetalDetail.PKG_App_Master_InspectionResultID;
                        updateOverResultPackagingAppearance();//comment for testing by shekhar
                        handleOverAllResult();//comment for testing by shekhar
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });


            ArrayAdapter innerArrary = new ArrayAdapter(this, R.layout.dialog_list_item, statusList);
            innerArrary.setDropDownViewResource(R.layout.dialog_list_item);
            //Setting the ArrayAdapter data on the Spinner

            spinnerOverAllInnerPackingPkgAppear.setAdapter(innerArrary);
            spinnerOverAllInnerPackingPkgAppear.setSelection(selectedIPos);
            spinnerOverAllInnerPackingPkgAppear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                   /* packagePoItemDetalDetail.PKG_App_Inner_InspectionResultID = overAllResultStatusList.get(i).pGenRowID;
                    if (packagePoItemDetalDetail.PKG_App_Inner_InspectionResultID.equals(FEnumerations.E_OVER_ALL_FAIL_RESULT)) {
                        packagePoItemDetalDetail.PKG_App_InspectionResultID = packagePoItemDetalDetail.PKG_App_Inner_InspectionResultID;
                        updateOverResultPackagingAppearance();
                        handleOverAllResult();
                    }*/
                    //need to be change
                    packagePoItemDetalDetail.PKG_Me_Inner_InspectionResultID = overAllResultStatusList.get(i).pGenRowID;
                    if (packagePoItemDetalDetail.PKG_Me_Inner_InspectionResultID.equals(FEnumerations.E_OVER_ALL_FAIL_RESULT)) {
                        packagePoItemDetalDetail.PKG_App_InspectionResultID = packagePoItemDetalDetail.PKG_Me_Inner_InspectionResultID;
                        updateOverResultPackagingAppearance();
                        handleOverAllResult();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });


            ArrayAdapter unitArrary = new ArrayAdapter(this, R.layout.dialog_list_item, statusList);
            unitArrary.setDropDownViewResource(R.layout.dialog_list_item);
            //Setting the ArrayAdapter data on the Spinner

            spinnerOverAllUnitPackingPkgAppear.setAdapter(unitArrary);
            spinnerOverAllUnitPackingPkgAppear.setSelection(selectedUPos);
            spinnerOverAllUnitPackingPkgAppear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    packagePoItemDetalDetail.PKG_App_Unit_InspectionResultID = overAllResultStatusList.get(i).pGenRowID;
                    if (packagePoItemDetalDetail.PKG_App_Unit_InspectionResultID.equals(FEnumerations.E_OVER_ALL_FAIL_RESULT)) {
                        packagePoItemDetalDetail.PKG_App_InspectionResultID = packagePoItemDetalDetail.PKG_App_Unit_InspectionResultID;
                        updateOverResultPackagingAppearance();//comment for testing by shekhar
                        handleOverAllResult();//comment for testing by shekhar
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            ArrayAdapter palletArrary = new ArrayAdapter(this, R.layout.dialog_list_item, statusList);
            palletArrary.setDropDownViewResource(R.layout.dialog_list_item);
            //Setting the ArrayAdapter data on the Spinner

            spinnerOverAllPalletPackingPkgAppear.setAdapter(palletArrary);
            spinnerOverAllPalletPackingPkgAppear.setSelection(selectedPPos);
            spinnerOverAllPalletPackingPkgAppear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    packagePoItemDetalDetail.PKG_App_Pallet_InspectionResultID = overAllResultStatusList.get(i).pGenRowID;
                    if (packagePoItemDetalDetail.PKG_App_Pallet_InspectionResultID.equals(FEnumerations.E_OVER_ALL_FAIL_RESULT)) {
                        packagePoItemDetalDetail.PKG_App_InspectionResultID = packagePoItemDetalDetail.PKG_App_Pallet_InspectionResultID;
                        updateOverResultPackagingAppearance();//comment for testing by shekhar
                        handleOverAllResult();//comment for testing by shekhar
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            ArrayAdapter shippingArrary = new ArrayAdapter(this, R.layout.dialog_list_item, statusList);
            palletArrary.setDropDownViewResource(R.layout.dialog_list_item);
            //Setting the ArrayAdapter data on the Spinner

            spinnerOverAllShippingMarkPkgAppear.setAdapter(shippingArrary);
            spinnerOverAllShippingMarkPkgAppear.setSelection(selectedOPos);
            spinnerOverAllShippingMarkPkgAppear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    packagePoItemDetalDetail.PKG_App_ShippingMark_InspectionResultID = overAllResultStatusList.get(i).pGenRowID;
                    if (packagePoItemDetalDetail.PKG_App_ShippingMark_InspectionResultID.equals(FEnumerations.E_OVER_ALL_FAIL_RESULT)) {
                        packagePoItemDetalDetail.PKG_Me_InspectionResultID = packagePoItemDetalDetail.PKG_App_ShippingMark_InspectionResultID;
                        updateOverResultPackagingAppearance();//comment for testing by shekhar
                        handleOverAllResult();//comment for testing by shekhar
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            updateOverResultPackagingAppearance();//comment for testing by shekhar
        }

        final List<SampleModal> sampleModals = POItemDtlHandler.getSampleSizeList(this);
        List<String> sampleList = new ArrayList<>();
        if (sampleModals != null && sampleModals.size() > 0) {

            int selectedUnitPos = 0, selectedInnerPos = 0, selectedMasterPos = 0, selectedPalletPos = 0, selectedOPos = 0;
            for (int i = 0; i < sampleModals.size(); i++) {
                sampleList.add(sampleModals.get(i).MainDescr + "(" + sampleModals.get(i).SampleVal + ")");
                if (sampleModals.get(i).SampleCode.equals(packagePoItemDetalDetail.PKG_App_Master_SampleSizeID)) {
                    selectedMasterPos = i;
                }
                if (sampleModals.get(i).SampleCode.equals(packagePoItemDetalDetail.PKG_App_Inner_SampleSizeID)) {
                    selectedInnerPos = i;
                }
                if (sampleModals.get(i).SampleCode.equals(packagePoItemDetalDetail.PKG_App_Unit_SampleSizeID)) {
                    selectedUnitPos = i;
                }
                if (sampleModals.get(i).SampleCode.equals(packagePoItemDetalDetail.PKG_App_Pallet_SampleSizeID)) {
                    selectedPalletPos = i;
                }
                if (sampleModals.get(i).SampleCode.equals(packagePoItemDetalDetail.PKG_App_shippingMark_SampleSizeId)) {
                    selectedOPos = i;
                }

            }

            ArrayAdapter sampleUnitArrary = new ArrayAdapter(this, R.layout.dialog_list_item, sampleList);
            sampleUnitArrary.setDropDownViewResource(R.layout.dialog_list_item);
            spinnerSampleSizeUnitPkgAppearPacking.setAdapter(sampleUnitArrary);
            spinnerSampleSizeUnitPkgAppearPacking.setSelection(selectedUnitPos);
            spinnerSampleSizeUnitPkgAppearPacking.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    packagePoItemDetalDetail.PKG_App_Unit_SampleSizeID = sampleModals.get(i).SampleCode;
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });


            ArrayAdapter sampleInnerArrary = new ArrayAdapter(this, R.layout.dialog_list_item, sampleList);
            sampleInnerArrary.setDropDownViewResource(R.layout.dialog_list_item);
            spinnerSampleSizeInnerPackingPkgAppearPacking.setAdapter(sampleInnerArrary);
            spinnerSampleSizeInnerPackingPkgAppearPacking.setSelection(selectedInnerPos);
            spinnerSampleSizeInnerPackingPkgAppearPacking.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    packagePoItemDetalDetail.PKG_App_Inner_SampleSizeID = sampleModals.get(i).SampleCode;
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });


            ArrayAdapter sampleMasterArrary = new ArrayAdapter(this, R.layout.dialog_list_item, sampleList);
            sampleMasterArrary.setDropDownViewResource(R.layout.dialog_list_item);
            spinnerSampleSizeMasterPackingPkgAppearPacking.setAdapter(sampleMasterArrary);
            spinnerSampleSizeMasterPackingPkgAppearPacking.setSelection(selectedMasterPos);
            spinnerSampleSizeMasterPackingPkgAppearPacking.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    packagePoItemDetalDetail.PKG_App_Master_SampleSizeID = sampleModals.get(i).SampleCode;
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            ArrayAdapter samplePalletArrary = new ArrayAdapter(this, R.layout.dialog_list_item, sampleList);
            samplePalletArrary.setDropDownViewResource(R.layout.dialog_list_item);
            spinnerSampleSizePalletPackingPkgAppearPacking.setAdapter(samplePalletArrary);
            spinnerSampleSizePalletPackingPkgAppearPacking.setSelection(selectedPalletPos);
            spinnerSampleSizePalletPackingPkgAppearPacking.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    packagePoItemDetalDetail.PKG_App_Pallet_SampleSizeID = sampleModals.get(i).SampleCode;
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            ArrayAdapter sampleShippingArrary = new ArrayAdapter(this, R.layout.dialog_list_item, sampleList);
            samplePalletArrary.setDropDownViewResource(R.layout.dialog_list_item);
            spinnerSampleSizeShippingMarkPkgAppearPacking.setAdapter(sampleShippingArrary);
            spinnerSampleSizeShippingMarkPkgAppearPacking.setSelection(selectedOPos);
            spinnerSampleSizeShippingMarkPkgAppearPacking.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    packagePoItemDetalDetail.PKG_App_shippingMark_SampleSizeId = sampleModals.get(i).SampleCode;
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }

    }

    /*private void handlePackagingAppearanceDescData() {
        final List<GeneralModel> overAllResultStatusList = GeneralMasterHandler.getGeneralList(this, FEnumerations.E_OVERALL_RESULT_STATUS_GEN_ID);
        if (overAllResultStatusList != null && overAllResultStatusList.size() > 0) {
            for (int i = 0; i < overAllResultStatusList.size(); i++) {
                if (overAllResultStatusList.get(i).GenID.equals("550")) {
                    if (i == 0) {
                        txtPkgAppearDesc1.setVisibility(View.VISIBLE);
                        txtPkgAppearDesc1.setText(overAllResultStatusList.get(i).MainDescr);
                    } else if (i == 1) {
                        txtPkgAppearDesc2.setVisibility(View.VISIBLE);
                        txtPkgAppearDesc2.setText(overAllResultStatusList.get(i).MainDescr);
                    } else if (i == 2) {
                        txtPkgAppearDesc3.setVisibility(View.VISIBLE);
                        txtPkgAppearDesc3.setText(overAllResultStatusList.get(i).MainDescr);
                    } else if (i == 3) {
                        txtPkgAppearDesc4.setVisibility(View.VISIBLE);
                        txtPkgAppearDesc4.setText(overAllResultStatusList.get(i).MainDescr);
                    } else if (i == 4) {
                        txtPkgAppearDesc5.setVisibility(View.VISIBLE);
                        txtPkgAppearDesc5.setText(overAllResultStatusList.get(i).MainDescr);
                    } else if (i == 5) {
                        txtPkgAppearDesc6.setVisibility(View.VISIBLE);
                        txtPkgAppearDesc6.setText(overAllResultStatusList.get(i).MainDescr);
                    } else if (i == 6) {
                        txtPkgAppearDesc7.setVisibility(View.VISIBLE);
                        txtPkgAppearDesc7.setText(overAllResultStatusList.get(i).MainDescr);
                    } else if (i == 7) {
                        txtPkgAppearDesc8.setVisibility(View.VISIBLE);
                        txtPkgAppearDesc8.setText(overAllResultStatusList.get(i).MainDescr);
                    } else if (i == 8) {
                        txtPkgAppearDesc9.setVisibility(View.VISIBLE);
                        txtPkgAppearDesc9.setText(overAllResultStatusList.get(i).MainDescr);
                    }
                }
            }
        }
    }*/

    private void handlePackingOverAllResultSpinner() {


        List<String> statusList = new ArrayList<>();
        final List<GeneralModel> overAllResultStatusList = GeneralMasterHandler.getGeneralList(this, FEnumerations.E_OVERALL_RESULT_STATUS_GEN_ID);
        if (overAllResultStatusList != null && overAllResultStatusList.size() > 0) {
            int selectedMPos = 0, selectedIPos = 0, selectedUPos = 0, selectedPPos = 0, selectedOPos = 0;
            for (int i = 0; i < overAllResultStatusList.size(); i++) {
                statusList.add(overAllResultStatusList.get(i).MainDescr);
                if (overAllResultStatusList.get(i).pGenRowID.equals(packagePoItemDetalDetail.PKG_Me_Master_InspectionResultID)) {
                    selectedMPos = i;
                }
                if (overAllResultStatusList.get(i).pGenRowID.equals(packagePoItemDetalDetail.PKG_Me_Inner_InspectionResultID)) {
                    selectedIPos = i;
                }
                if (overAllResultStatusList.get(i).pGenRowID.equals(packagePoItemDetalDetail.PKG_Me_Unit_InspectionResultID)) {
                    selectedUPos = i;
                }
                if (overAllResultStatusList.get(i).pGenRowID.equals(packagePoItemDetalDetail.PKG_Me_Pallet_InspectionResultID)) {
                    selectedPPos = i;
                }
                if (overAllResultStatusList.get(i).pGenRowID.equals(packagePoItemDetalDetail.PKG_Me_InspectionResultID)) {
                    selectedOPos = i;
                }
            }


            //Creating the ArrayAdapter instance having the country list
            ArrayAdapter masterArrary = new ArrayAdapter(this, R.layout.dialog_list_item, statusList);
            masterArrary.setDropDownViewResource(R.layout.dialog_list_item);
            //Setting the ArrayAdapter data on the Spinner
            spinnerMasterPackingOverAllResult.setAdapter(masterArrary);
            spinnerMasterPackingOverAllResult.setSelection(selectedMPos);
            spinnerMasterPackingOverAllResult.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    packagePoItemDetalDetail.PKG_Me_Master_InspectionResultID = overAllResultStatusList.get(i).pGenRowID;
                    if (packagePoItemDetalDetail.PKG_Me_Master_InspectionResultID.equals(FEnumerations.E_OVER_ALL_FAIL_RESULT)) {
                        packagePoItemDetalDetail.PKG_Me_InspectionResultID = packagePoItemDetalDetail.PKG_Me_Master_InspectionResultID;
                        updateOverResultPacking();
                        handleOverAllResult();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });


            ArrayAdapter innerArrary = new ArrayAdapter(this, R.layout.dialog_list_item, statusList);
            innerArrary.setDropDownViewResource(R.layout.dialog_list_item);
            //Setting the ArrayAdapter data on the Spinner

            spinnerInnerPackingOverAllResult.setAdapter(innerArrary);
            spinnerInnerPackingOverAllResult.setSelection(selectedIPos);
            spinnerInnerPackingOverAllResult.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    packagePoItemDetalDetail.PKG_Me_Inner_InspectionResultID = overAllResultStatusList.get(i).pGenRowID;
                    if (packagePoItemDetalDetail.PKG_Me_Inner_InspectionResultID.equals(FEnumerations.E_OVER_ALL_FAIL_RESULT)) {
                        packagePoItemDetalDetail.PKG_Me_InspectionResultID = packagePoItemDetalDetail.PKG_Me_Inner_InspectionResultID;
                        updateOverResultPacking();
                        handleOverAllResult();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });


            ArrayAdapter unitArrary = new ArrayAdapter(this, R.layout.dialog_list_item, statusList);
            unitArrary.setDropDownViewResource(R.layout.dialog_list_item);
            //Setting the ArrayAdapter data on the Spinner

            spinnerUnitPackingOverAllResult.setAdapter(unitArrary);
            spinnerUnitPackingOverAllResult.setSelection(selectedUPos);
            spinnerUnitPackingOverAllResult.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    packagePoItemDetalDetail.PKG_Me_Unit_InspectionResultID = overAllResultStatusList.get(i).pGenRowID;
                    if (packagePoItemDetalDetail.PKG_Me_Unit_InspectionResultID.equals(FEnumerations.E_OVER_ALL_FAIL_RESULT)) {
                        packagePoItemDetalDetail.PKG_Me_InspectionResultID = packagePoItemDetalDetail.PKG_Me_Unit_InspectionResultID;
                        updateOverResultPacking();
                        handleOverAllResult();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            ArrayAdapter palletArrary = new ArrayAdapter(this, R.layout.dialog_list_item, statusList);
            palletArrary.setDropDownViewResource(R.layout.dialog_list_item);
            //Setting the ArrayAdapter data on the Spinner

            spinnerPalletPackingOverAllResult.setAdapter(palletArrary);
            spinnerPalletPackingOverAllResult.setSelection(selectedPPos);
            spinnerPalletPackingOverAllResult.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    packagePoItemDetalDetail.PKG_Me_Pallet_InspectionResultID = overAllResultStatusList.get(i).pGenRowID;
                    if (packagePoItemDetalDetail.PKG_Me_Pallet_InspectionResultID.equals(FEnumerations.E_OVER_ALL_FAIL_RESULT)) {
                        packagePoItemDetalDetail.PKG_Me_InspectionResultID = packagePoItemDetalDetail.PKG_Me_Pallet_InspectionResultID;
                        updateOverResultPacking();
                        handleOverAllResult();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });


            updateOverResultPacking();


        }

        List<String> inspectionLevelList = new ArrayList<>();

//        if (!TextUtils.isEmpty(inspectionModal.InspectionLevel) && !inspectionModal.InspectionLevel.equals("null")) {
//            final List<InsLvHdrModal> insLvHdrModals = InsLvHdrHandler.getDataAccordingToParticularList(this, inspectionModal.InspectionLevel);
//            if (insLvHdrModals != null && insLvHdrModals.size() > 0) {
//                txtInspectionLevel.setText(insLvHdrModals.get(0).InspAbbrv);
//            }
//            pRowIdOfInspectLevel = inspectionModal.InspectionLevel;
//        } else {
        final List<InsLvHdrModal> insLvHdrModals = InsLvHdrHandler.getInsLvHdrList(this);
        if (insLvHdrModals != null && insLvHdrModals.size() > 0) {
            for (int j = 0; j < insLvHdrModals.size(); j++) {
                inspectionLevelList.add(insLvHdrModals.get(j).InspAbbrv);
            }

            ArrayAdapter inspAllArrary = new ArrayAdapter(this, R.layout.dialog_list_item, statusList);
            inspAllArrary.setDropDownViewResource(R.layout.dialog_list_item);
            //Setting the ArrayAdapter data on the Spinner

            spinnerInspectionLevel.setAdapter(inspAllArrary);
            spinnerInspectionLevel.setSelection(0);
            spinnerInspectionLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                    packagePoItemDetalDetail.Ins = overAllResultStatusList.get(i).MainID;
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }


//        , , ,
//

        final List<SampleModal> sampleModals = POItemDtlHandler.getSampleSizeList(this);
        List<String> sampleList = new ArrayList<>();
        if (sampleModals != null && sampleModals.size() > 0) {

            int selectedUnitPos = 0, selectedInnerPos = 0, selectedMasterPos = 0, selectedPalletPos = 0;
            for (int i = 0; i < sampleModals.size(); i++) {
                sampleList.add(sampleModals.get(i).MainDescr + "(" + sampleModals.get(i).SampleVal + ")");
                if (sampleModals.get(i).SampleCode.equals(packagePoItemDetalDetail.PKG_Me_Master_SampleSizeID)) {
                    selectedMasterPos = i;
                }
                if (sampleModals.get(i).SampleCode.equals(packagePoItemDetalDetail.PKG_Me_Inner_SampleSizeID)) {
                    selectedInnerPos = i;
                }
                if (sampleModals.get(i).SampleCode.equals(packagePoItemDetalDetail.PKG_Me_Unit_SampleSizeID)) {
                    selectedUnitPos = i;
                }
                if (sampleModals.get(i).SampleCode.equals(packagePoItemDetalDetail.PKG_Me_Pallet_SampleSizeID)) {
                    selectedPalletPos = i;
                }

            }

            //Creating the ArrayAdapter instance having the country list
            ArrayAdapter sampleUnitArrary = new ArrayAdapter(this, R.layout.dialog_list_item, sampleList);
            sampleUnitArrary.setDropDownViewResource(R.layout.dialog_list_item);
            //Setting the ArrayAdapter data on the Spinner
            spinnerSampleSizeUnitPacking.setAdapter(sampleUnitArrary);
            spinnerSampleSizeUnitPacking.setSelection(selectedUnitPos);
            spinnerSampleSizeUnitPacking.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    packagePoItemDetalDetail.PKG_Me_Unit_SampleSizeID = sampleModals.get(i).SampleCode;
//                    itemMeasurementModal.SampleSizeValue = sampleModals.get(i).SampleVal;
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });


            //Creating the ArrayAdapter instance having the country list
            ArrayAdapter sampleInnerArrary = new ArrayAdapter(this, R.layout.dialog_list_item, sampleList);
            sampleInnerArrary.setDropDownViewResource(R.layout.dialog_list_item);
            //Setting the ArrayAdapter data on the Spinner
            spinnerSampleSizeInnerPacking.setAdapter(sampleInnerArrary);
            spinnerSampleSizeInnerPacking.setSelection(selectedInnerPos);
            spinnerSampleSizeInnerPacking.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    packagePoItemDetalDetail.PKG_Me_Inner_SampleSizeID = sampleModals.get(i).SampleCode;
//                    itemMeasurementModal.SampleSizeValue = sampleModals.get(i).SampleVal;
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });


            //Creating the ArrayAdapter instance having the country list
            ArrayAdapter sampleMasterArrary = new ArrayAdapter(this, R.layout.dialog_list_item, sampleList);
            sampleMasterArrary.setDropDownViewResource(R.layout.dialog_list_item);
            //Setting the ArrayAdapter data on the Spinner
            spinnerSampleSizeMasterPacking.setAdapter(sampleMasterArrary);
            spinnerSampleSizeMasterPacking.setSelection(selectedMasterPos);
            spinnerSampleSizeMasterPacking.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    packagePoItemDetalDetail.PKG_Me_Master_SampleSizeID = sampleModals.get(i).SampleCode;
//                    itemMeasurementModal.SampleSizeID = sampleModals.get(i).SampleCode;
//                    itemMeasurementModal.SampleSizeValue = sampleModals.get(i).SampleVal;
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            //Creating the ArrayAdapter instance having the country list
            ArrayAdapter samplePalletArrary = new ArrayAdapter(this, R.layout.dialog_list_item, sampleList);
            samplePalletArrary.setDropDownViewResource(R.layout.dialog_list_item);
            //Setting the ArrayAdapter data on the Spinner
            spinnerSampleSizePalletPacking.setAdapter(samplePalletArrary);
            spinnerSampleSizePalletPacking.setSelection(selectedPalletPos);
            spinnerSampleSizePalletPacking.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    packagePoItemDetalDetail.PKG_Me_Pallet_SampleSizeID = sampleModals.get(i).SampleCode;
//                    itemMeasurementModal.SampleSizeValue = sampleModals.get(i).SampleVal;
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }

    }

    private void updateOverResultBarcode() {
        List<String> statusList = new ArrayList<>();
        final List<GeneralModel> overAllResultStatusList = GeneralMasterHandler.getGeneralList(this, FEnumerations.E_OVERALL_RESULT_STATUS_GEN_ID);
        if (overAllResultStatusList != null && overAllResultStatusList.size() > 0) {
            int selectedOPos = 0;
            for (int i = 0; i < overAllResultStatusList.size(); i++) {
                statusList.add(overAllResultStatusList.get(i).MainDescr);

                if (overAllResultStatusList.get(i).pGenRowID.equals(packagePoItemDetalDetail.Barcode_InspectionResultID)) {
                    selectedOPos = i;
                }
            }
            ArrayAdapter overAllArrary = new ArrayAdapter(this, R.layout.dialog_list_item, statusList);
            overAllArrary.setDropDownViewResource(R.layout.dialog_list_item);
            //Setting the ArrayAdapter data on the Spinner

            spinnerOverAllResultBarcode.setAdapter(overAllArrary);
            spinnerOverAllResultBarcode.setSelection(selectedOPos);
            spinnerOverAllResultBarcode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    packagePoItemDetalDetail.Barcode_InspectionResultID = overAllResultStatusList.get(i).pGenRowID;
                    handleOverAllResult();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }
    }

    private void updateOverResultPacking() {
        List<String> statusList = new ArrayList<>();
        final List<GeneralModel> overAllResultStatusList = GeneralMasterHandler.getGeneralList(this, FEnumerations.E_OVERALL_RESULT_STATUS_GEN_ID);
        if (overAllResultStatusList != null && overAllResultStatusList.size() > 0) {
            int selectedOPos = 0;
            for (int i = 0; i < overAllResultStatusList.size(); i++) {
                statusList.add(overAllResultStatusList.get(i).MainDescr);

                if (overAllResultStatusList.get(i).pGenRowID.equals(packagePoItemDetalDetail.PKG_Me_InspectionResultID)) {
                    selectedOPos = i;
                }
            }
            ArrayAdapter overAllArrary = new ArrayAdapter(this, R.layout.dialog_list_item, statusList);
            overAllArrary.setDropDownViewResource(R.layout.dialog_list_item);
            //Setting the ArrayAdapter data on the Spinner

            spinnerOverAllResult.setAdapter(overAllArrary);
            spinnerOverAllResult.setSelection(selectedOPos);
            spinnerOverAllResult.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    packagePoItemDetalDetail.PKG_Me_InspectionResultID = overAllResultStatusList.get(i).pGenRowID;
                    handleOverAllResult();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }
    }

    private void handlerToShowAttachment(ArrayList<String> imagesList) {

        if (imagesList.size() > 0) {
            Bundle bundle = new Bundle();
            bundle.putStringArrayList("galleryModels", imagesList);
            bundle.putInt("position", 0);
//                    FragmentManager fragmentManager =activity.
            FragmentTransaction ft = ((FragmentActivity) this)
                    .getSupportFragmentManager().beginTransaction();
            SlideshowDialogFragment newFragment = SlideshowDialogFragment.newInstance();
            newFragment.setArguments(bundle);
            newFragment.show(ft, "slideshow");
        }
    }

    private void handleToInitiateWorkManShip() {

        workmanshipOverAllResultContainer = (Spinner) findViewById(R.id.spinnerWorkmanshipOverAllResult);
        txtTotalCritical = (TextView) findViewById(R.id.txtTotalCritical);
        txtTotalMajor = (TextView) findViewById(R.id.txtTotalMajor);
        txtTotalMinor = (TextView) findViewById(R.id.txtTotalMinor);
        txtPermissibleCritical = (TextView) findViewById(R.id.txtPermissibleCritical);
        txtPermissibleMajor = (TextView) findViewById(R.id.txtPermissibleMajor);
        txtPermissibleMinor = (TextView) findViewById(R.id.txtPermissibleMinor);
        findViewById(R.id.addContainer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ItemInspectionDetail.this, AddWorkManShip.class);
                intent.putExtra("podetail", GenUtils.serializePOItemModal(poItemDtl));
                intent.putExtra("type", FEnumerations.REQUEST_FOR_ADD_WORKMANSHIP);
                startActivityForResult(intent, FEnumerations.REQUEST_FOR_ADD_WORKMANSHIP);

            }
        });

        List<String> statusList = new ArrayList<>();
        final List<GeneralModel> overAllResultStatusList = GeneralMasterHandler.getGeneralList(this, FEnumerations.E_OVERALL_RESULT_STATUS_GEN_ID);
        if (overAllResultStatusList != null && overAllResultStatusList.size() > 0) {
            int selectedMPos = 0;
            for (int i = 0; i < overAllResultStatusList.size(); i++) {
                statusList.add(overAllResultStatusList.get(i).MainDescr);
                if (overAllResultStatusList.get(i).pGenRowID.equals(packagePoItemDetalDetail.WorkmanShip_InspectionResultID)) {
                    selectedMPos = i;
                }

            }


            //Creating the ArrayAdapter instance having the country list
            ArrayAdapter masterArrary = new ArrayAdapter(this, R.layout.dialog_list_item, statusList);
            masterArrary.setDropDownViewResource(R.layout.dialog_list_item);
            //Setting the ArrayAdapter data on the Spinner
            workmanshipOverAllResultContainer.setAdapter(masterArrary);
            workmanshipOverAllResultContainer.setSelection(selectedMPos);
            workmanshipOverAllResultContainer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    packagePoItemDetalDetail.WorkmanShip_InspectionResultID = overAllResultStatusList.get(i).pGenRowID;
                    handleOverAllResult();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
//        recyclerView.addItemDecoration(new DividerItemDecoration(ComplianceList.this,
//                DividerItemDecoration.VERTICAL));

        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(ItemInspectionDetail.this);
        recyclerView.setLayoutManager(mLayoutManager);
        activity = (TextView) findViewById(R.id.activity);
        workmanHistoryDate = (TextView) findViewById(R.id.workmanHistoryDate);
        inspectionID = (TextView) findViewById(R.id.inspectionID);
        recyclerViewWorkmanshipHistory = (RecyclerView) findViewById(R.id.recyclerViewWorkmanshipHistory);
        recyclerViewWorkmanshipHistory.setHasFixedSize(true);
//        recyclerView.addItemDecoration(new DividerItemDecoration(ComplianceList.this,
//                DividerItemDecoration.VERTICAL));

        // use a linear layout manager
        LinearLayoutManager mLayoutManager1 = new LinearLayoutManager(ItemInspectionDetail.this);
        recyclerViewWorkmanshipHistory.setLayoutManager(mLayoutManager1);


        updateWorkmanshipUi();
        updateTotalWorkmanship();


        updateWorkmanshipHistoryUi();
    }

    private void updatInternalTestUi() {

        List<QualityParameter> list = ItemInspectionDetailHandler.getListFitnessCheck(this, poItemDtl.QRHdrID, poItemDtl.QRPOItemHdrID);
        if (internalTestList != null) {
            internalTestList.clear();
        } else {
            internalTestList = new ArrayList<>();
        }
        internalTestList.addAll(list);

//        if (internalTestListForSave != null) {
//            internalTestListForSave.clear();
//        } else {
//            internalTestListForSave = new ArrayList<>();
//        }
//        internalTestListForSave.addAll(list);


        /*recyclerViewInternalTest.addOnItemTouchListener(new QualityParameterAdaptor.RecyclerTouchListener(getApplicationContext()
                , recyclerViewInternalTest, new QualityParameterAdaptor.ClickListener() {
            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));*/
        setInternalTestAdaptor();
    }

    private void updateQualityParameterUi() {
      /*  List<QualityParameter> list = ItemInspectionDetailHandler.getListQualityParameter(this, inspectionModal, poItemDtl.QRHdrID, poItemDtl.QRPOItemHdrID);
        if (qualityParameterList != null) {
            qualityParameterList.clear();
        } else {
            qualityParameterList = new ArrayList<>();
        }
        qualityParameterList.addAll(list);*/
//        if (qualityParameterListForSave != null) {
//            qualityParameterListForSave.clear();
//        } else {
//            qualityParameterListForSave = new ArrayList<>();
//        }
//        qualityParameterListForSave = list;


       /* recyclerViewQualityParameter.addOnItemTouchListener(new QualityParameterAdaptor.RecyclerTouchListener(getApplicationContext()
                , recyclerViewQualityParameter, new QualityParameterAdaptor.ClickListener() {
            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));*/
        setQualityParameterAdaptor();
    }

    private void setInternalTestAdaptor() {
        if (internalTestAdaptor == null) {
            internalTestAdaptor = new QualityParameterAdaptor(
                    ItemInspectionDetail.this,
                    recyclerViewInternalTest
                    , internalTestList
                    , FEnumerations.E_ADAPTOR_VIEW_TYPE_INTERNAL
                    , this);
            recyclerViewInternalTest.setAdapter(internalTestAdaptor);
        } else {
            internalTestAdaptor.notifyDataSetChanged();
        }
    }

    private void setQualityParameterAdaptor() {

        List<QualityParameter> list = ItemInspectionDetailHandler.getListQualityParameter(this, inspectionModal, poItemDtl.QRHdrID, poItemDtl.QRPOItemHdrID);
        if (qualityParameterList != null) {
            qualityParameterList.clear();
        } else {
            qualityParameterList = new ArrayList<>();
        }
        qualityParameterList.addAll(list);

        if (qualityParameterAdaptor == null) {
            qualityParameterAdaptor = new QualityParameterAdaptor(ItemInspectionDetail.this, recyclerViewQualityParameter
                    , qualityParameterList
                    , FEnumerations.E_ADAPTOR_VIEW_TYPE_QUALITY_PARAMETER, this);
            recyclerViewQualityParameter.setAdapter(qualityParameterAdaptor);
        } else {
            qualityParameterAdaptor.notifyDataSetChanged();
        }
    }

    private void updateWorkmanshipUi() {

        recyclerView.addOnItemTouchListener(new WorkManShipAdaptor.RecyclerTouchListener(getApplicationContext()
                , recyclerView, new WorkManShipAdaptor.ClickListener() {
            @Override
            public void onClick(View view, int position) {
//                Intent intent = new Intent(ItemInspectionDetail.this, AddWorkManShip.class);
//                intent.putExtra("detail", GenUtils.serializeWorkmanship(workManShipModels.get(position)));
//                intent.putExtra("type", FEnumerations.REQUEST_FOR_EDIT_WORKMANSHIP);
//                startActivityForResult(intent, FEnumerations.REQUEST_FOR_EDIT_WORKMANSHIP);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        setAdaptor();
    }

    private void updateWorkmanshipHistoryUi() {

        recyclerViewWorkmanshipHistory.addOnItemTouchListener(new WorkManShipAdaptor.RecyclerTouchListener(getApplicationContext()
                , recyclerViewWorkmanshipHistory, new WorkManShipAdaptor.ClickListener() {
            @Override
            public void onClick(View view, int position) {
//                Intent intent = new Intent(ItemInspectionDetail.this, AddWorkManShip.class);
//                intent.putExtra("detail", GenUtils.serializeWorkmanship(workManShipModels.get(position)));
//                intent.putExtra("type", FEnumerations.REQUEST_FOR_EDIT_WORKMANSHIP);
//                startActivityForResult(intent, FEnumerations.REQUEST_FOR_EDIT_WORKMANSHIP);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        List<WorkManShipModel> list = ItemInspectionDetailHandler.getWorkmanShipHistory(this, poItemDtl.QRPOItemHdrID);
        if (workManShipHistoryModels != null) {
            workManShipHistoryModels.clear();
        } else {
            workManShipHistoryModels = new ArrayList<>();
        }
        workManShipHistoryModels.addAll(list);
        if (workManShipHistoryModels != null && workManShipHistoryModels.size() > 0) {
            activity.setText(workManShipHistoryModels.get(0).Activity);
            inspectionID.setText(workManShipHistoryModels.get(0).pRowID);
            workmanHistoryDate.setText(DateUtils.getDate(workManShipHistoryModels.get(0).InspectionDate));
            findViewById(R.id.workmanshipHistoryContainer).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.workmanshipHistoryContainer).setVisibility(View.GONE);
        }
        if (workManShipHistoryAdaptor == null) {
            workManShipHistoryAdaptor = new WorkManShipAdaptor(ItemInspectionDetail.this
                    , poItemDtl
                    , workManShipHistoryModels,
                    FEnumerations.VIEW_TYPE_HISTORY, this);
            recyclerViewWorkmanshipHistory.setAdapter(workManShipHistoryAdaptor);
        } else {
            workManShipHistoryAdaptor.notifyDataSetChanged();
        }
    }

    private void handleToInitiateItemMeasurement() {
        spinnerItemMeasurementOverAllResult = (Spinner) findViewById(R.id.spinnerItemMeasurementOverAllResult);
        findViewById(R.id.addItemMeasurementContainer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ItemInspectionDetail.this, AddItemMeasurement.class);
                intent.putExtra("poItemDtl", GenUtils.serializePOItemModal(poItemDtl));
                intent.putExtra("type", FEnumerations.REQUEST_FOR_ADD_ITEM_MEASUREMENT);
                startActivityForResult(intent, FEnumerations.REQUEST_FOR_ADD_ITEM_MEASUREMENT);

            }
        });

        updateItemMeasurementSpinner();

        recyclerViewItemMeasurement = (RecyclerView) findViewById(R.id.recyclerViewItemMeasurement);
        recyclerViewItemMeasurement.setHasFixedSize(true);
        recyclerViewItemMeasurement.addItemDecoration(new DividerItemDecoration(ItemInspectionDetail.this,
                DividerItemDecoration.VERTICAL));

        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(ItemInspectionDetail.this);
        recyclerViewItemMeasurement.setLayoutManager(mLayoutManager);
        itemMeasurementModalList = new ArrayList<>();
        List<ItemMeasurementModal> lItemMeasurement = ItemInspectionDetailHandler.getItemMeasurementList(this, poItemDtl.QRHdrID, poItemDtl.QRPOItemHdrID);

        if (lItemMeasurement != null) {
            itemMeasurementModalList.clear();
            itemMeasurementModalList.addAll(lItemMeasurement);
        }


        recyclerViewItemMeasurement.addOnItemTouchListener(new ItemMeasurementAdaptor.RecyclerTouchListener(getApplicationContext()
                , recyclerViewItemMeasurement, new ItemMeasurementAdaptor.ClickListener() {
            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        recyclerViewItemHistory = (RecyclerView) findViewById(R.id.recyclerViewItemHistory);
        recyclerViewItemHistory.setHasFixedSize(true);
        recyclerViewItemHistory.addItemDecoration(new DividerItemDecoration(ItemInspectionDetail.this,
                DividerItemDecoration.VERTICAL));

        // use a linear layout manager
        LinearLayoutManager mLayoutManager1 = new LinearLayoutManager(ItemInspectionDetail.this);
        recyclerViewItemHistory.setLayoutManager(mLayoutManager1);
        itemMeasurementHistoryModalList = new ArrayList<>();
        List<ItemMeasurementModal> lItemMeasurementHistory = ItemInspectionDetailHandler.getItemMeasurementHistoryList(this, poItemDtl.QRPOItemHdrID);

        if (lItemMeasurementHistory != null) {
            itemMeasurementHistoryModalList.clear();
            itemMeasurementHistoryModalList.addAll(lItemMeasurementHistory);
        }
        itemactivity = (TextView) findViewById(R.id.itemactivity);
        itemHistoryDate = (TextView) findViewById(R.id.itemHistoryDate);
        inspectionItemID = (TextView) findViewById(R.id.inspectionItemID);

        if (itemMeasurementHistoryModalList != null && itemMeasurementHistoryModalList.size() > 0) {
            itemactivity.setText(itemMeasurementHistoryModalList.get(0).Activity);
            inspectionItemID.setText(itemMeasurementHistoryModalList.get(0).pRowID);
            itemHistoryDate.setText(DateUtils.getDate(itemMeasurementHistoryModalList.get(0).InspectionDate));
//            findViewById(R.id.workmanshipHistoryContainer).setVisibility(View.VISIBLE);
        }
        recyclerViewItemHistory.addOnItemTouchListener(new ItemMeasurementAdaptor.RecyclerTouchListener(getApplicationContext()
                , recyclerViewItemHistory, new ItemMeasurementAdaptor.ClickListener() {
            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        getOverAllStatusOfItemMeasurement();

        setItemMeasurementAdaptor();
        setItemMeasurementHistoryAdaptor();
    }

    private void getOverAllStatusOfItemMeasurement() {
        if (itemMeasurementModalList != null && itemMeasurementModalList.size() > 0) {
            for (int i = 0; i < itemMeasurementModalList.size(); i++) {
                if (itemMeasurementModalList.get(i).InspectionResultID.equals(FEnumerations.E_OVER_ALL_FAIL_RESULT)) {
                    packagePoItemDetalDetail.ItemMeasurement_InspectionResultID = itemMeasurementModalList.get(i).InspectionResultID;
                    break;
                }
            }
            updateItemMeasurementSpinner();
            handleOverAllResult();
        }
    }

    private void updateItemMeasurementSpinner() {
        List<String> statusList = new ArrayList<>();
        final List<GeneralModel> overAllResultStatusList = GeneralMasterHandler.getGeneralList(this, FEnumerations.E_OVERALL_RESULT_STATUS_GEN_ID);
        if (overAllResultStatusList != null && overAllResultStatusList.size() > 0) {
            int selectedMPos = 0;
            for (int i = 0; i < overAllResultStatusList.size(); i++) {
                statusList.add(overAllResultStatusList.get(i).MainDescr);
                if (overAllResultStatusList.get(i).pGenRowID.equals(packagePoItemDetalDetail.ItemMeasurement_InspectionResultID)) {
                    selectedMPos = i;
                }

            }


            //Creating the ArrayAdapter instance having the country list
            ArrayAdapter masterArrary = new ArrayAdapter(this, R.layout.dialog_list_item, statusList);
            masterArrary.setDropDownViewResource(R.layout.dialog_list_item);
            //Setting the ArrayAdapter data on the Spinner
            spinnerItemMeasurementOverAllResult.setAdapter(masterArrary);
            spinnerItemMeasurementOverAllResult.setSelection(selectedMPos);
            spinnerItemMeasurementOverAllResult.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    packagePoItemDetalDetail.ItemMeasurement_InspectionResultID = overAllResultStatusList.get(i).pGenRowID;
                    handleOverAllResult();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }
    }

    private void setItemMeasurementAdaptor() {


//        if (itemMeasurementModalList == null || itemMeasurementModalList.size() == 0) {
//            findViewById(R.id.itemMeasurementListContainer).setVisibility(View.GONE);
//        } else {
//            findViewById(R.id.itemMeasurementListContainer).setVisibility(View.VISIBLE);
//        }

        if (itemMeasurementAdaptor == null) {
            itemMeasurementAdaptor = new ItemMeasurementAdaptor(ItemInspectionDetail.this
                    , poItemDtl
                    , FEnumerations.VIEW_TYPE_NORMAL
                    , itemMeasurementModalList
                    , recyclerViewItemMeasurement
                    , this);
            recyclerViewItemMeasurement.setAdapter(itemMeasurementAdaptor);
        } else {
            itemMeasurementAdaptor.notifyDataSetChanged();
        }


    }

    private void setItemMeasurementHistoryAdaptor() {


        if (itemMeasurementHistoryModalList == null || itemMeasurementHistoryModalList.size() == 0) {
            findViewById(R.id.itemHistoryContainer).setVisibility(View.GONE);
        } else {
            findViewById(R.id.itemHistoryContainer).setVisibility(View.VISIBLE);
        }

        if (itemMeasurementHistoryAdaptor == null) {
            itemMeasurementHistoryAdaptor = new ItemMeasurementAdaptor(ItemInspectionDetail.this
                    , poItemDtl
                    , FEnumerations.VIEW_TYPE_HISTORY
                    , itemMeasurementHistoryModalList
                    , recyclerViewItemHistory
                    , this);
            recyclerViewItemHistory.setAdapter(itemMeasurementHistoryAdaptor);
        } else {
            itemMeasurementHistoryAdaptor.notifyDataSetChanged();
        }


    }

    private void handleToInitiateDigitalUpload() {
        digitalsUploadListContainer = (LinearLayout) findViewById(R.id.digitalsUploadListContainer);
        findViewById(R.id.addDigitalsUploadContainer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ItemInspectionDetail.this, AddDigitalsUpload.class);
                intent.putExtra("detail", GenUtils.serializePOItemModal(poItemDtl));
                startActivityForResult(intent, FEnumerations.REQUEST_FOR_ADD_DIGITALS_UPLOAD);

            }
        });

        recyclerViewDigitalUploads = (RecyclerView) findViewById(R.id.recyclerViewDigitalUploads);
        recyclerViewDigitalUploads.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(ItemInspectionDetail.this);
        recyclerViewDigitalUploads.setLayoutManager(mLayoutManager);


        updateDigitalUi();
    }

    private void updateDigitalUi() {

      /*  List<DigitalsUploadModal> list = ItemInspectionDetailHandler.getDigitalsUploadList(this, poItemDtl.QRHdrID, poItemDtl.QRPOItemHdrID);
        if (list != null && list.size() > 0) {
            if (digitalsUploadModals != null) {
                digitalsUploadModals.clear();
                digitalsUploadModals.addAll(list);
            }

        }*/


        setDigitalUploadAdaptor();
    }

    private void handleToInitiateHistory() {

        recyclerViewHistory = (RecyclerView) findViewById(R.id.recyclerViewHistory);
        recyclerViewHistory.setHasFixedSize(true);
        recyclerViewItemMeasurement.addItemDecoration(new DividerItemDecoration(ItemInspectionDetail.this,
                DividerItemDecoration.VERTICAL));

        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(ItemInspectionDetail.this);
        recyclerViewHistory.setLayoutManager(mLayoutManager);
        historyInspectionModalList = new ArrayList<>();
        List<InspectionModal> lis = ItemInspectionDetailHandler.getHistoryList(this, poItemDtl.QRHdrID, poItemDtl.QRPOItemHdrID);
        recyclerViewHistory.addOnItemTouchListener(new HistoryAdaptor.RecyclerTouchListener(getApplicationContext()
                , recyclerViewHistory,
                new HistoryAdaptor.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        if (historyInspectionModalList != null && historyInspectionModalList.size() > 0) {

                            ItemInspectionDetailHandler.toRedirectWebView(ItemInspectionDetail.this, historyInspectionModalList.get(position));
                        }
                    }

                    @Override
                    public void onLongClick(View view, int position) {

                    }
                }));


        historyInspectionModalList.addAll(lis);
        setHistoryAdaptor();
    }

    private void handleToInitiateTestReport() {

        recyclerViewTestReport = (RecyclerView) findViewById(R.id.recyclerViewTestReport);
        recyclerViewTestReport.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(ItemInspectionDetail.this);
        recyclerViewTestReport.setLayoutManager(mLayoutManager);
        testReportModals = new ArrayList<>();
        testReportModals.addAll(ItemInspectionDetailHandler.getTestReportList(this, poItemDtl.QRHdrID, poItemDtl.QRPOItemHdrID));

        setTestReportAdaptor();
    }

    private void handleToInitiateEnclosure() {

        recyclerViewEnclosure = (RecyclerView) findViewById(R.id.recyclerViewEnclosure);
        recyclerViewEnclosure.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(ItemInspectionDetail.this);
        recyclerViewEnclosure.setLayoutManager(mLayoutManager);
        enclosureModalList = new ArrayList<>();

        List<EnclosureModal> lis = ItemInspectionDetailHandler.getEnclosureList(this, poItemDtl.QRHdrID, poItemDtl.QRPOItemHdrID);
        enclosureModalList.addAll(lis);
        setEnclosureAdaptor();

    }

    private void setTestReportAdaptor() {


        if (testReportAdaptor == null) {
            testReportAdaptor = new TestReportAdaptor(ItemInspectionDetail.this
                    , testReportModals);
            recyclerViewTestReport.setAdapter(testReportAdaptor);
        } else {
            testReportAdaptor.notifyDataSetChanged();
        }


    }

    private void setEnclosureAdaptor() {


        if (enclosureAdaptor == null) {
            enclosureAdaptor = new EnclosureAdaptor(ItemInspectionDetail.this
                    , enclosureModalList);
            recyclerViewEnclosure.setAdapter(enclosureAdaptor);
        } else {
            enclosureAdaptor.notifyDataSetChanged();
        }


    }

    private void setHistoryAdaptor() {


        if (historyAdaptor == null) {
            historyAdaptor = new HistoryAdaptor(ItemInspectionDetail.this
                    , historyInspectionModalList);
            recyclerViewHistory.setAdapter(historyAdaptor);
        } else {
            historyAdaptor.notifyDataSetChanged();
        }


    }

    private void handlePackaging() {

        List<POItemDtl> packDetailList = ItemInspectionDetailHandler.getPackagingMeasurementList(this, poItemDtl.QRHdrID, poItemDtl.QRPOItemHdrID);
        List<POItemDtl> packFindingList = ItemInspectionDetailHandler.getPackagingFindingMeasurementList(this, poItemDtl.ItemID, poItemDtl.QRPOItemHdrID);
        List<POItemDtl> packList = ItemInspectionDetailHandler.capyFindingDataToSpecification(packDetailList, packFindingList);
        if (packList != null && packList.size() > 0) {
            packagePoItemDetalDetail = packList.get(0);
        }


    }

    private void setDigitalUploadAdaptor() {

        List<DigitalsUploadModal> list = ItemInspectionDetailHandler.getDigitalsUploadList(this, poItemDtl.QRHdrID, poItemDtl.QRPOItemHdrID);
        if (list != null && list.size() > 0) {
            if (digitalsUploadModals != null) {
                digitalsUploadModals.clear();
                digitalsUploadModals.addAll(list);
            }

        }

        if (digitalsUploadModals != null && digitalsUploadModals.size() > 0) {
            digitalsUploadListContainer.setVisibility(View.VISIBLE);
        } else {
            digitalsUploadListContainer.setVisibility(View.GONE);
        }
        if (digitalUploadAdaptor == null) {
            digitalUploadAdaptor = new DigitalUploadAdaptor(ItemInspectionDetail.this
                    , digitalsUploadModals, this);
            recyclerViewDigitalUploads.setAdapter(digitalUploadAdaptor);
        } else {
            digitalUploadAdaptor.notifyDataSetChanged();
        }


    }

    private void setAdaptor() {
        List<WorkManShipModel> list = ItemInspectionDetailHandler.getWorkmanShip(this, poItemDtl.QRHdrID, poItemDtl.QRPOItemHdrID, poItemDtl.QrItemID);
        if (workManShipModels != null) {
            workManShipModels.clear();
        } else {
            workManShipModels = new ArrayList<>();
        }
        workManShipModels.addAll(list);
        if (workManShipModels != null && workManShipModels.size() > 0) {
            workManShipListContainer.setVisibility(View.VISIBLE);
        } else {
            workManShipListContainer.setVisibility(View.GONE);
        }
        if (workManShipAdaptor == null) {
            workManShipAdaptor = new WorkManShipAdaptor(ItemInspectionDetail.this
                    , poItemDtl, workManShipModels,
                    FEnumerations.VIEW_TYPE_NORMAL
                    , this);
            recyclerView.setAdapter(workManShipAdaptor);
        } else {
            workManShipAdaptor.notifyDataSetChanged();
        }


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.WorkmanshipVisualInspectionDetail:
                WorkmanshipVisualInspectionDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorPrimary));
                txtWorkmanshipVisualInspectionDetail.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));

                PackingMeasurementDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
                txtPackingMeasurementDetail.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                itemMeasurementDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
                txtItemMeasurement.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                DigitalsUploadedDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
                txtDigitalsUploaded.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                TestReportsDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
                txtTestReports.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                EnclosureDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
                txtEnclosure.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                HistoryDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
                txtHistory.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                productionStatusTab.setBackgroundColor(getResources().getColor(R.color.white));
                txtProductionStatus.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                qualityParametersTab.setBackgroundColor(getResources().getColor(R.color.white));
                txtQualityParameters.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));


                packagingAppearanceLL.setBackgroundColor(getResources().getColor(R.color.white));
                txtPackagingAppearance.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                addBarcodeLL.setBackgroundColor(getResources().getColor(R.color.white));
                txtAddBarcode.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                onSiteTestLL.setBackgroundColor(getResources().getColor(R.color.white));
                txtOnSiteTest.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                itemQtyLL.setBackgroundColor(getResources().getColor(R.color.white));
                txtItemQty.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                itemSampleCollectedLL.setBackgroundColor(getResources().getColor(R.color.white));
                txtSampleCollected.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                itemSampleCollectedLL.setBackgroundColor(getResources().getColor(R.color.white));
                txtSampleCollected.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

//                intimationTab.setBackgroundColor(getResources().getColor(R.color.white));
//                txtIntimation.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                viewFlipper.setDisplayedChild(1);
                NetworkUtil.hideSoftKeyboard(ItemInspectionDetail.this);
                handleToSaved(false);
                break;
            case R.id.PackingMeasurementDetail:
                WorkmanshipVisualInspectionDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
                txtWorkmanshipVisualInspectionDetail.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                PackingMeasurementDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorPrimary));
                txtPackingMeasurementDetail.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));

                itemMeasurementDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
                txtItemMeasurement.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                DigitalsUploadedDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
                txtDigitalsUploaded.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                TestReportsDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
                txtTestReports.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                EnclosureDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
                txtEnclosure.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                HistoryDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
                txtHistory.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                productionStatusTab.setBackgroundColor(getResources().getColor(R.color.white));
                txtProductionStatus.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                qualityParametersTab.setBackgroundColor(getResources().getColor(R.color.white));
                txtQualityParameters.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                packagingAppearanceLL.setBackgroundColor(getResources().getColor(R.color.white));
                txtPackagingAppearance.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                addBarcodeLL.setBackgroundColor(getResources().getColor(R.color.white));
                txtAddBarcode.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                onSiteTestLL.setBackgroundColor(getResources().getColor(R.color.white));
                txtOnSiteTest.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                itemQtyLL.setBackgroundColor(getResources().getColor(R.color.white));
                txtItemQty.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                itemSampleCollectedLL.setBackgroundColor(getResources().getColor(R.color.white));
                txtSampleCollected.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

//                intimationTab.setBackgroundColor(getResources().getColor(R.color.white));
//                txtIntimation.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));


                viewFlipper.setDisplayedChild(0);

                NetworkUtil.hideSoftKeyboard(ItemInspectionDetail.this);
                handleToSaved(false);
                break;
            case R.id.itemMeasurementDetail:

                WorkmanshipVisualInspectionDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
                txtWorkmanshipVisualInspectionDetail.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                PackingMeasurementDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
                txtPackingMeasurementDetail.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                itemMeasurementDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorPrimary));
                txtItemMeasurement.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));

                DigitalsUploadedDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
                txtDigitalsUploaded.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                TestReportsDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
                txtTestReports.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                EnclosureDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
                txtEnclosure.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                HistoryDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
                txtHistory.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                productionStatusTab.setBackgroundColor(getResources().getColor(R.color.white));
                txtProductionStatus.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                qualityParametersTab.setBackgroundColor(getResources().getColor(R.color.white));
                txtQualityParameters.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                packagingAppearanceLL.setBackgroundColor(getResources().getColor(R.color.white));
                txtPackagingAppearance.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                addBarcodeLL.setBackgroundColor(getResources().getColor(R.color.white));
                txtAddBarcode.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                onSiteTestLL.setBackgroundColor(getResources().getColor(R.color.white));
                txtOnSiteTest.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                itemQtyLL.setBackgroundColor(getResources().getColor(R.color.white));
                txtItemQty.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                itemSampleCollectedLL.setBackgroundColor(getResources().getColor(R.color.white));
                txtSampleCollected.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

//                intimationTab.setBackgroundColor(getResources().getColor(R.color.white));
//                txtIntimation.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));


                viewFlipper.setDisplayedChild(2);
                NetworkUtil.hideSoftKeyboard(ItemInspectionDetail.this);
                handleToSaved(false);
                break;

            case R.id.DigitalsUploadedDetail:

                WorkmanshipVisualInspectionDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
                txtWorkmanshipVisualInspectionDetail.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                PackingMeasurementDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
                txtPackingMeasurementDetail.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));


                itemMeasurementDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
                txtItemMeasurement.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));


                DigitalsUploadedDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorPrimary));
                txtDigitalsUploaded.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));


                TestReportsDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
                txtTestReports.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                EnclosureDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
                txtEnclosure.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                HistoryDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
                txtHistory.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                productionStatusTab.setBackgroundColor(getResources().getColor(R.color.white));
                txtProductionStatus.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                qualityParametersTab.setBackgroundColor(getResources().getColor(R.color.white));
                txtQualityParameters.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                packagingAppearanceLL.setBackgroundColor(getResources().getColor(R.color.white));
                txtPackagingAppearance.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                addBarcodeLL.setBackgroundColor(getResources().getColor(R.color.white));
                txtAddBarcode.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                onSiteTestLL.setBackgroundColor(getResources().getColor(R.color.white));
                txtOnSiteTest.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                itemQtyLL.setBackgroundColor(getResources().getColor(R.color.white));
                txtItemQty.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                itemSampleCollectedLL.setBackgroundColor(getResources().getColor(R.color.white));
                txtSampleCollected.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

//                intimationTab.setBackgroundColor(getResources().getColor(R.color.white));
//                txtIntimation.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                viewFlipper.setDisplayedChild(3);

                updateDigitalUi();
                NetworkUtil.hideSoftKeyboard(ItemInspectionDetail.this);
                handleToSaved(false);
                break;

            case R.id.TestReportsDetail:

                WorkmanshipVisualInspectionDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
                txtWorkmanshipVisualInspectionDetail.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                PackingMeasurementDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
                txtPackingMeasurementDetail.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                itemMeasurementDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
                txtItemMeasurement.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                DigitalsUploadedDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
                txtDigitalsUploaded.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));


                TestReportsDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorPrimary));
                txtTestReports.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));

                EnclosureDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
                txtEnclosure.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                HistoryDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
                txtHistory.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                productionStatusTab.setBackgroundColor(getResources().getColor(R.color.white));
                txtProductionStatus.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                qualityParametersTab.setBackgroundColor(getResources().getColor(R.color.white));
                txtQualityParameters.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                packagingAppearanceLL.setBackgroundColor(getResources().getColor(R.color.white));
                txtPackagingAppearance.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                addBarcodeLL.setBackgroundColor(getResources().getColor(R.color.white));
                txtAddBarcode.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                onSiteTestLL.setBackgroundColor(getResources().getColor(R.color.white));
                txtOnSiteTest.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                itemQtyLL.setBackgroundColor(getResources().getColor(R.color.white));
                txtItemQty.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                itemSampleCollectedLL.setBackgroundColor(getResources().getColor(R.color.white));
                txtSampleCollected.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));
//                intimationTab.setBackgroundColor(getResources().getColor(R.color.white));
//                txtIntimation.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                viewFlipper.setDisplayedChild(4);
                NetworkUtil.hideSoftKeyboard(ItemInspectionDetail.this);
                break;
            case R.id.EnclosureDetail:

                WorkmanshipVisualInspectionDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
                txtWorkmanshipVisualInspectionDetail.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                PackingMeasurementDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
                txtPackingMeasurementDetail.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                itemMeasurementDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
                txtItemMeasurement.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                DigitalsUploadedDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
                txtDigitalsUploaded.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                TestReportsDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
                txtTestReports.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                EnclosureDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorPrimary));
                txtEnclosure.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));

                HistoryDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
                txtHistory.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));
                productionStatusTab.setBackgroundColor(getResources().getColor(R.color.white));
                txtProductionStatus.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                qualityParametersTab.setBackgroundColor(getResources().getColor(R.color.white));
                txtQualityParameters.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                packagingAppearanceLL.setBackgroundColor(getResources().getColor(R.color.white));
                txtPackagingAppearance.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                addBarcodeLL.setBackgroundColor(getResources().getColor(R.color.white));
                txtAddBarcode.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                onSiteTestLL.setBackgroundColor(getResources().getColor(R.color.white));
                txtOnSiteTest.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                itemQtyLL.setBackgroundColor(getResources().getColor(R.color.white));
                txtItemQty.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                itemSampleCollectedLL.setBackgroundColor(getResources().getColor(R.color.white));
                txtSampleCollected.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));
//                intimationTab.setBackgroundColor(getResources().getColor(R.color.white));
//                txtIntimation.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));


                viewFlipper.setDisplayedChild(5);
                NetworkUtil.hideSoftKeyboard(ItemInspectionDetail.this);
                break;
            case R.id.HistoryDetail:

                WorkmanshipVisualInspectionDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
                txtWorkmanshipVisualInspectionDetail.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                PackingMeasurementDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
                txtPackingMeasurementDetail.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                itemMeasurementDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
                txtItemMeasurement.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                DigitalsUploadedDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
                txtDigitalsUploaded.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                TestReportsDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
                txtTestReports.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                EnclosureDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
                txtEnclosure.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                HistoryDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorPrimary));
                txtHistory.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
                productionStatusTab.setBackgroundColor(getResources().getColor(R.color.white));
                txtProductionStatus.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                qualityParametersTab.setBackgroundColor(getResources().getColor(R.color.white));
                txtQualityParameters.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                packagingAppearanceLL.setBackgroundColor(getResources().getColor(R.color.white));
                txtPackagingAppearance.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                addBarcodeLL.setBackgroundColor(getResources().getColor(R.color.white));
                txtAddBarcode.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                onSiteTestLL.setBackgroundColor(getResources().getColor(R.color.white));
                txtOnSiteTest.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                itemQtyLL.setBackgroundColor(getResources().getColor(R.color.white));
                txtItemQty.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                itemSampleCollectedLL.setBackgroundColor(getResources().getColor(R.color.white));
                txtSampleCollected.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));
//                intimationTab.setBackgroundColor(getResources().getColor(R.color.white));
//                txtIntimation.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));


                viewFlipper.setDisplayedChild(6);
                NetworkUtil.hideSoftKeyboard(ItemInspectionDetail.this);
                break;
            case R.id.qualityParametersTab:

                showQualityParameterTab();
                handleToSaved(false);
                NetworkUtil.hideSoftKeyboard(ItemInspectionDetail.this);
                break;
            case R.id.productionStatusTab:

                WorkmanshipVisualInspectionDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
                txtWorkmanshipVisualInspectionDetail.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                PackingMeasurementDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
                txtPackingMeasurementDetail.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                itemMeasurementDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
                txtItemMeasurement.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                DigitalsUploadedDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
                txtDigitalsUploaded.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                TestReportsDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
                txtTestReports.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                EnclosureDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
                txtEnclosure.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                HistoryDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
                txtHistory.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                productionStatusTab.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                txtProductionStatus.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));

                qualityParametersTab.setBackgroundColor(getResources().getColor(R.color.white));
                txtQualityParameters.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                packagingAppearanceLL.setBackgroundColor(getResources().getColor(R.color.white));
                txtPackagingAppearance.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                addBarcodeLL.setBackgroundColor(getResources().getColor(R.color.white));
                txtAddBarcode.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                onSiteTestLL.setBackgroundColor(getResources().getColor(R.color.white));
                txtOnSiteTest.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                itemQtyLL.setBackgroundColor(getResources().getColor(R.color.white));
                txtItemQty.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                itemSampleCollectedLL.setBackgroundColor(getResources().getColor(R.color.white));
                txtSampleCollected.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));
//                intimationTab.setBackgroundColor(getResources().getColor(R.color.white));
//                txtIntimation.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                viewFlipper.setDisplayedChild(8);
                NetworkUtil.hideSoftKeyboard(ItemInspectionDetail.this);
                break;

            case R.id.packagingAppearanceLL:

                WorkmanshipVisualInspectionDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
                txtWorkmanshipVisualInspectionDetail.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                PackingMeasurementDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
                txtPackingMeasurementDetail.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                itemMeasurementDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
                txtItemMeasurement.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                DigitalsUploadedDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
                txtDigitalsUploaded.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                TestReportsDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
                txtTestReports.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                EnclosureDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
                txtEnclosure.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                HistoryDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
                txtHistory.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                productionStatusTab.setBackgroundColor(getResources().getColor(R.color.white));
                txtProductionStatus.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                qualityParametersTab.setBackgroundColor(getResources().getColor(R.color.white));
                txtQualityParameters.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                packagingAppearanceLL.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                txtPackagingAppearance.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));

                addBarcodeLL.setBackgroundColor(getResources().getColor(R.color.white));
                txtAddBarcode.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                onSiteTestLL.setBackgroundColor(getResources().getColor(R.color.white));
                txtOnSiteTest.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                itemQtyLL.setBackgroundColor(getResources().getColor(R.color.white));
                txtItemQty.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                itemSampleCollectedLL.setBackgroundColor(getResources().getColor(R.color.white));
                txtSampleCollected.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));
//                intimationTab.setBackgroundColor(getResources().getColor(R.color.white));
//                txtIntimation.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                viewFlipper.setDisplayedChild(9);
                NetworkUtil.hideSoftKeyboard(ItemInspectionDetail.this);
                handleToInitiatePackagingAppearance();
                break;
            case R.id.addBarcodeLL:

                WorkmanshipVisualInspectionDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
                txtWorkmanshipVisualInspectionDetail.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                PackingMeasurementDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
                txtPackingMeasurementDetail.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                itemMeasurementDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
                txtItemMeasurement.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                DigitalsUploadedDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
                txtDigitalsUploaded.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                TestReportsDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
                txtTestReports.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                EnclosureDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
                txtEnclosure.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                HistoryDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
                txtHistory.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                productionStatusTab.setBackgroundColor(getResources().getColor(R.color.white));
                txtProductionStatus.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                qualityParametersTab.setBackgroundColor(getResources().getColor(R.color.white));
                txtQualityParameters.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                packagingAppearanceLL.setBackgroundColor(getResources().getColor(R.color.white));
                txtPackagingAppearance.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                addBarcodeLL.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                txtAddBarcode.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));

                onSiteTestLL.setBackgroundColor(getResources().getColor(R.color.white));
                txtOnSiteTest.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                itemQtyLL.setBackgroundColor(getResources().getColor(R.color.white));
                txtItemQty.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                itemSampleCollectedLL.setBackgroundColor(getResources().getColor(R.color.white));
                txtSampleCollected.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));
//                intimationTab.setBackgroundColor(getResources().getColor(R.color.white));
//                txtIntimation.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                viewFlipper.setDisplayedChild(10);
                NetworkUtil.hideSoftKeyboard(ItemInspectionDetail.this);
                break;

            case R.id.onSiteTestLL:

                WorkmanshipVisualInspectionDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
                txtWorkmanshipVisualInspectionDetail.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                PackingMeasurementDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
                txtPackingMeasurementDetail.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                itemMeasurementDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
                txtItemMeasurement.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                DigitalsUploadedDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
                txtDigitalsUploaded.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                TestReportsDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
                txtTestReports.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                EnclosureDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
                txtEnclosure.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                HistoryDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
                txtHistory.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                productionStatusTab.setBackgroundColor(getResources().getColor(R.color.white));
                txtProductionStatus.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                qualityParametersTab.setBackgroundColor(getResources().getColor(R.color.white));
                txtQualityParameters.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                packagingAppearanceLL.setBackgroundColor(getResources().getColor(R.color.white));
                txtPackagingAppearance.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                addBarcodeLL.setBackgroundColor(getResources().getColor(R.color.white));
                txtAddBarcode.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                onSiteTestLL.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                txtOnSiteTest.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));

                itemQtyLL.setBackgroundColor(getResources().getColor(R.color.white));
                txtItemQty.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                itemSampleCollectedLL.setBackgroundColor(getResources().getColor(R.color.white));
                txtSampleCollected.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));
                viewFlipper.setDisplayedChild(11);
                NetworkUtil.hideSoftKeyboard(ItemInspectionDetail.this);
                handleOnSiteTab();
                break;

            case R.id.itemQtyLL:

                /*WorkmanshipVisualInspectionDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
                txtWorkmanshipVisualInspectionDetail.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                PackingMeasurementDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
                txtPackingMeasurementDetail.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                itemMeasurementDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
                txtItemMeasurement.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                DigitalsUploadedDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
                txtDigitalsUploaded.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                TestReportsDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
                txtTestReports.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                EnclosureDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
                txtEnclosure.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                HistoryDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
                txtHistory.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                productionStatusTab.setBackgroundColor(getResources().getColor(R.color.white));
                txtProductionStatus.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                qualityParametersTab.setBackgroundColor(getResources().getColor(R.color.white));
                txtQualityParameters.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                packagingAppearanceLL.setBackgroundColor(getResources().getColor(R.color.white));
                txtPackagingAppearance.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                addBarcodeLL.setBackgroundColor(getResources().getColor(R.color.white));
                txtAddBarcode.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                onSiteTestLL.setBackgroundColor(getResources().getColor(R.color.white));
                txtOnSiteTest.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                itemQtyLL.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                txtItemQty.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));

                itemSampleCollectedLL.setBackgroundColor(getResources().getColor(R.color.white));
                txtSampleCollected.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));
                viewFlipper.setDisplayedChild(12);*/
                showItemQtyTab();
                NetworkUtil.hideSoftKeyboard(ItemInspectionDetail.this);

                break;
            case R.id.itemSampleCollectedLL:

                WorkmanshipVisualInspectionDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
                txtWorkmanshipVisualInspectionDetail.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                PackingMeasurementDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
                txtPackingMeasurementDetail.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                itemMeasurementDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
                txtItemMeasurement.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                DigitalsUploadedDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
                txtDigitalsUploaded.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                TestReportsDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
                txtTestReports.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                EnclosureDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
                txtEnclosure.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                HistoryDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
                txtHistory.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                productionStatusTab.setBackgroundColor(getResources().getColor(R.color.white));
                txtProductionStatus.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                qualityParametersTab.setBackgroundColor(getResources().getColor(R.color.white));
                txtQualityParameters.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                packagingAppearanceLL.setBackgroundColor(getResources().getColor(R.color.white));
                txtPackagingAppearance.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                addBarcodeLL.setBackgroundColor(getResources().getColor(R.color.white));
                txtAddBarcode.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                onSiteTestLL.setBackgroundColor(getResources().getColor(R.color.white));
                txtOnSiteTest.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                itemQtyLL.setBackgroundColor(getResources().getColor(R.color.white));
                txtItemQty.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

                itemSampleCollectedLL.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                txtSampleCollected.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));

                viewFlipper.setDisplayedChild(13);
                NetworkUtil.hideSoftKeyboard(ItemInspectionDetail.this);
                handleSampleCollectedTab();//created by shekhar//done
                break;
            case R.id.innerAttachmentCount:
                handlerToShowAttachment(packagePoItemDetalDetail.innerPackingAttachmentList);
                break;

            case R.id.unitAttachmentCount:
                handlerToShowAttachment(packagePoItemDetalDetail.unitPackingAttachmentList);
                break;

            case R.id.masterAttachmentCount:
                handlerToShowAttachment(packagePoItemDetalDetail.masterPackingAttachmentList);
                break;
            case R.id.palletAttachmentCount:
                handlerToShowAttachment(packagePoItemDetalDetail.palletPackingAttachmentList);
                break;

        }
    }

    private void showQualityParameterTab() {

        WorkmanshipVisualInspectionDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
        txtWorkmanshipVisualInspectionDetail.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

        PackingMeasurementDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
        txtPackingMeasurementDetail.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

        itemMeasurementDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
        txtItemMeasurement.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

        DigitalsUploadedDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
        txtDigitalsUploaded.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

        TestReportsDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
        txtTestReports.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

        EnclosureDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
        txtEnclosure.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

        HistoryDetail.setBackgroundColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));
        txtHistory.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

        productionStatusTab.setBackgroundColor(getResources().getColor(R.color.white));
        txtProductionStatus.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

        qualityParametersTab.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        txtQualityParameters.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.white));

        packagingAppearanceLL.setBackgroundColor(getResources().getColor(R.color.white));
        txtPackagingAppearance.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

        addBarcodeLL.setBackgroundColor(getResources().getColor(R.color.white));
        txtAddBarcode.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

        onSiteTestLL.setBackgroundColor(getResources().getColor(R.color.white));
        txtOnSiteTest.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));

        itemQtyLL.setBackgroundColor(getResources().getColor(R.color.white));
        txtItemQty.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));
//        intimationTab.setBackgroundColor(getResources().getColor(R.color.white));
//        txtIntimation.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));


        viewFlipper.setDisplayedChild(7);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.item_insp_menu, menu);//Menu Resource, Menu


       /* MenuItem item = menu.findItem(R.id.spinner);
        Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);

        List<String> statusList = new ArrayList<>();
        final List<GeneralModel> overAllResultList = GeneralMasterHandler.getGeneralList(this, FEnumerations.E_OVERALL_RESULT_STATUS_GEN_ID);
        if (overAllResultList != null && overAllResultList.size() > 0) {
            int selectedMPos = 0;
            for (int i = 0; i < overAllResultList.size(); i++) {
                statusList.add(overAllResultList.get(i).MainDescr);
                if (overAllResultList.get(i).pGenRowID.equals(packagePoItemDetalDetail.PKG_Me_Master_InspectionResultID)) {
                    selectedMPos = i;
                }
            }

            ArrayAdapter masterArrary = new ArrayAdapter(this, android.R.layout.simple_spinner_item, statusList);
            masterArrary.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            //Setting the ArrayAdapter data on the Spinner
            spinner.setAdapter(masterArrary);
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
//                statusList, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setSelection(selectedMPos);
            spinner.setAdapter(masterArrary);
        }*/
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            if (viewFlipper.getDisplayedChild() == 1) {
//                showFirstScreen();
//                return true;
//            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {

//        if (viewFlipper.getDisplayedChild() == 1) {
//
//            showFirstScreen();
//            return;
//        }

        super.onBackPressed();
    }

//    private void showFirstScreen() {
//
//
//        if (viewFlipper.getDisplayedChild() == 2) {
//            viewFlipper.setDisplayedChild(1);
//        } else if (viewFlipper.getDisplayedChild() == 1) {
//            viewFlipper.setDisplayedChild(0);
//        }
//        invalidateOptionsMenu();
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        switch (item.getItemId()) {
            case android.R.id.home:

                finish();
                return false;
            case R.id.packSave:
                showProgressDialog("Saving...");
                handleToSaved(true);

                return false;
        }

        return false;
    }

    private void handleToSaved(boolean temp) {

        if (temp) {
            showProgressDialog("Waiting...");
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    handlePacking();

                    handleWorkManship();
                    handleItemMeasurement();
//                    handleSaveQualityParameter();
//                    handleSaveFitness();
                    updatePackingUI();

                    //added by shekhar
                    updatePkgAppearance(pOItemPkgAppDetail);
//                    updateOnSite(onSIteModal);
                    updateSampleCollectedTab();
                    handleOnSiteRemark();
                    handlePkgAppearance();
                    handleItemQtyRemark();
                    updateBarCodeRemark();
                    ///////////
                    Toast toast = ToastCompat.makeText(ItemInspectionDetail.this, "Saved successfully", Toast.LENGTH_SHORT);
                    GenUtils.safeToastShow(TAG, ItemInspectionDetail.this, toast);

                    hideDialog();
                }
            }, 1000);

        } else {
            handlePacking();
            handleWorkManship();
            handleItemMeasurement();
//            handleSaveQualityParameter();
//            handleSaveFitness();
            updatePackingUI();
//            hideDialog();
        }
    }

  /*  private void handleDigitalUpload() {

        for (int i = 0; i < digitalsUploadModals.size(); i++) {

            if (digitalUploadAdaptor != null) {

                if (recyclerViewDigitalUploads != null) {
                    View view = recyclerViewDigitalUploads.getChildAt(i);
                    if (view != null) {
                        EditText txtTitle = (EditText) view.findViewById(R.id.txtTitle);
                        String title = txtTitle.getText().toString();
                        ItemInspectionDetailHandler.updateImageTitle(ItemInspectionDetail.this, title, digitalsUploadModals.get(i).pRowID);

                    }
                }
            }
        }
    }*/


   /* private void handleSaveFitness() {


        if (internalTestList != null) {
            List<QualityParameter> list = ItemInspectionDetailHandler.getListFitness(this, poItemDtl.QRHdrID, poItemDtl.QRPOItemHdrID);
            for (int i = 0; i < internalTestList.size(); i++) {
                if (internalTestList.get(i).IsApplicable != 0) {
                    String returnPRowID = ItemInspectionDetailHandler.updateFitness(this, internalTestList.get(i), poItemDtl);
                    if (!TextUtils.isEmpty(returnPRowID)) {
                        addASDigitalFitnessCheck(internalTestList.get(i), returnPRowID);
                    }
                } else {
                    for (int j = 0; j < list.size(); j++) {
                        if (list.get(j).QualityParameterID.equals(internalTestList.get(i).QualityParameterID)) {
                            String returnPRowID = ItemInspectionDetailHandler.updateFitness(this, internalTestList.get(i), poItemDtl);
                            if (!TextUtils.isEmpty(returnPRowID)) {
                                addASDigitalFitnessCheck(internalTestList.get(i), returnPRowID);
                            }
                        }
                    }
                }
            }
        }

    }*/

   /* private void handleSaveQualityParameter() {

        if (qualityParameterList != null) {
            List<QualityParameter> list = ItemInspectionDetailHandler.getSavedQualityParameter(this, poItemDtl.QRHdrID, poItemDtl.QRPOItemHdrID);
            for (int i = 0; i < qualityParameterList.size(); i++) {
//                for (int j = 0; j < qualityParameterListForSave.size(); j++) {
                if (qualityParameterList.get(i).IsApplicable != 0) {
                    String returnPRowID = ItemInspectionDetailHandler.updateQualityParameter(this, qualityParameterList.get(i), poItemDtl);
                    if (!TextUtils.isEmpty(returnPRowID)) {
                        addASDigitalQualityParameter(qualityParameterList.get(i), returnPRowID);
                    }
                } else {
                    for (int j = 0; j < list.size(); j++) {
                        if (list.get(j).QualityParameterID.equals(qualityParameterList.get(i).QualityParameterID)) {
                            String returnPRowID = ItemInspectionDetailHandler.updateQualityParameter(this, qualityParameterList.get(i), poItemDtl);
                            if (!TextUtils.isEmpty(returnPRowID)) {
                                addASDigitalQualityParameter(qualityParameterList.get(i), returnPRowID);
                            }
                        }
                    }
                }
//                }
            }
        }


    }*/

    private void handlePacking() {
        onSubmitPackage();
        String _packingRemark = editPackingRemark.getText().toString();
        packagePoItemDetalDetail.PKG_Me_Remark = _packingRemark;
        ItemInspectionDetailHandler.updatePackagingFindingMeasurementList(this, packagePoItemDetalDetail);

    }

    private void handlePkgAppearance() {
//        onSubmitPackage();
        String pkgAppRemark = editPackingAppearRemark.getText().toString();
        packagePoItemDetalDetail.PKG_App_Remark = pkgAppRemark;
        ItemInspectionDetailHandler.updatePackagingFindingMeasurementList(this, packagePoItemDetalDetail);

    }

    private void handleOnSiteRemark() {
        String pkgAppRemark = editOnSiteRemark.getText().toString();
        packagePoItemDetalDetail.OnSiteTest_Remark = pkgAppRemark;
        ItemInspectionDetailHandler.updatePackagingFindingMeasurementList(this, packagePoItemDetalDetail);

    }

    private void updateBarCodeRemark() {
//        String pkgAppRemark = editPackingBarcodeRemark.getText().toString();
        packagePoItemDetalDetail.Barcode_Remark = editPackingBarcodeRemark.getText().toString();
        ;

        packagePoItemDetalDetail.Barcode_Unit_Visual = txtVisulLabelBarcode.getText().toString();
        packagePoItemDetalDetail.Barcode_Inner_Visual = txtVisulLabelInnerPackingBarcode.getText().toString();
        packagePoItemDetalDetail.Barcode_Master_Visual = txtVisulLabelMasterPackingBarcode.getText().toString();
        packagePoItemDetalDetail.Barcode_Pallet_Visual = txtVisulLabelPalletPackingBarcode.getText().toString();

        packagePoItemDetalDetail.Barcode_Unit_Scan = txtScanLabelBarcode.getText().toString();
        packagePoItemDetalDetail.Barcode_Inner_Scan = txtScanLabelInnerPackingBarcode.getText().toString();
        packagePoItemDetalDetail.Barcode_Master_Scan = txtScanLabelMasterPackingBarcode.getText().toString();
        packagePoItemDetalDetail.Barcode_Pallet_Scan = txtScanLabelPalletPackingBarcode.getText().toString();

        ItemInspectionDetailHandler.updatePackagingFindingMeasurementList(this, packagePoItemDetalDetail);

    }

    private void handleItemQtyRemark() {
        String pkgAppRemark = editItemQtyRemark.getText().toString();
        packagePoItemDetalDetail.Qty_Remark = pkgAppRemark;
        ItemInspectionDetailHandler.updatePackagingFindingMeasurementList(this, packagePoItemDetalDetail);

    }

    private void handleItemMeasurement() {
        String _itemMeasurementRemark = editItemMeasurementRemark.getText().toString();
//        if (!TextUtils.isEmpty(packagePoItemDetalDetail.ItemMeasurement_Remark)) {
        if (!_itemMeasurementRemark.equals(packagePoItemDetalDetail.ItemMeasurement_Remark)) {
            packagePoItemDetalDetail.ItemMeasurement_Remark = _itemMeasurementRemark;
            ItemInspectionDetailHandler.updateItemMeasurementRemark(this, packagePoItemDetalDetail);
        }
//        }
      /*  if (itemMeasurementModalList != null && itemMeasurementModalList.size() > 0) {
            for (int i = 0; i < itemMeasurementModalList.size(); i++) {
                ItemInspectionDetailHandler.updateItemMeasurement(this, itemMeasurementModalList.get(i), poItemDtl);
                addAsDigitalImageFromItemmeasurement(itemMeasurementModalList.get(i));
            }
        }*/

    }

  /*  private void addAsDigitalImageFromItemmeasurement(ItemMeasurementModal mItemMeasrmentModal) {
        if (mItemMeasrmentModal != null
                && mItemMeasrmentModal.listImages != null
                && mItemMeasrmentModal.listImages.size() > 0) {
            for (int i = 0; i < mItemMeasrmentModal.listImages.size(); i++) {
                if (TextUtils.isEmpty(mItemMeasrmentModal.listImages.get(i).title)) {
                    mItemMeasrmentModal.listImages.get(i).title = mItemMeasrmentModal.ItemMeasurementDescr;
                }
                if (TextUtils.isEmpty(mItemMeasrmentModal.listImages.get(i).ImageExtn)
                        || mItemMeasrmentModal.listImages.get(i).ImageExtn.equals("null")) {
                    mItemMeasrmentModal.listImages.get(i).ImageExtn = FEnumerations.E_IMAGE_EXTN;
                }
                if (TextUtils.isEmpty(mItemMeasrmentModal.listImages.get(i).pRowID)
                        || mItemMeasrmentModal.listImages.get(i).pRowID.equals("null")) {
                    mItemMeasrmentModal.listImages.get(i).pRowID = ItemInspectionDetailHandler.GeneratePK(ItemInspectionDetail.this, FEnumerations.E_TABLE_NAME_QRPOItemDtl_Image);
                }
                String digitalsRowId = updateDBWithImage(mItemMeasrmentModal.listImages.get(i));
                if (!TextUtils.isEmpty(digitalsRowId)) {
                    List<ItemMeasurementModal> dtlList = ItemInspectionDetailHandler.getDigitalsFromItemMeasurementShip(ItemInspectionDetail.this, mItemMeasrmentModal.pRowID);
                    if (dtlList != null && dtlList.size() > 0) {
                        ItemMeasurementModal mWo = dtlList.get(0);
//                        mWo.pRowID = wRowID;
                        if (!TextUtils.isEmpty(mWo.Digitals) && !mWo.Digitals.equals("null")) {
                            mWo.Digitals = mWo.Digitals + ", " + digitalsRowId;
                        } else {
                            mWo.Digitals = digitalsRowId;
                        }
                        ItemInspectionDetailHandler.updateDigitalFromItemmeasurement(ItemInspectionDetail.this, mWo);
                    }
                }
            }

        }

    }*/


    private void handleWorkManship() {
        String _workmanshipRemark = editWorkmanshipRemark.getText().toString();

        if (!_workmanshipRemark.equals(packagePoItemDetalDetail.WorkmanShip_Remark)) {
            packagePoItemDetalDetail.WorkmanShip_Remark = _workmanshipRemark;
            ItemInspectionDetailHandler.updateWorkmanshipRemark(this, packagePoItemDetalDetail);
        }
        ItemInspectionDetailHandler.updateWorkmanshipOverAllResult(this, packagePoItemDetalDetail);
       /* if (workManShipModels != null && workManShipModels.size() > 0) {
            for (int i = 0; i < workManShipModels.size(); i++) {
                String wRowID = ItemInspectionDetailHandler.updateWorkmanShip(this,
                        poItemDtl.QRHdrID,
                        poItemDtl.QRPOItemHdrID,
                        poItemDtl.QrItemID,
                        workManShipModels.get(i));
                addAsDigitalImageFromWorkmanShip(workManShipModels.get(i), wRowID);

            }
        }*/


        updateTotalWorkmanship();

    }

    private void updateTotalWorkmanship() {

        List<WorkManShipModel> lWorkDefect = ItemInspectionDetailHandler.getWorkmanShip(this, poItemDtl.QRHdrID, poItemDtl.QRPOItemHdrID, poItemDtl.QrItemID);
        if (lWorkDefect != null && lWorkDefect.size() > 0) {
            int totalMinorDef = 0, totalMajorDef = 0, totalCriticalDef = 0;
            for (int i = 0; i < lWorkDefect.size(); i++) {
                totalMinorDef = totalMinorDef + lWorkDefect.get(i).Minor;
                totalMajorDef = totalMajorDef + lWorkDefect.get(i).Major;
                totalCriticalDef = totalCriticalDef + lWorkDefect.get(i).Critical;
            }
            poItemDtl.CriticalDefect = totalCriticalDef;
            poItemDtl.MajorDefect = totalMajorDef;
            poItemDtl.MinorDefect = totalMinorDef;

        } else {
            poItemDtl.CriticalDefect = 0;
            poItemDtl.MajorDefect = 0;
            poItemDtl.MinorDefect = 0;
        }
        ItemInspectionDetailHandler.updateDefect(ItemInspectionDetail.this, poItemDtl);

        int totalCriticalDef = poItemDtl.CriticalDefect;
        int totalMajorDef = poItemDtl.MajorDefect;
        int totalMinorDef = poItemDtl.MinorDefect;

        txtTotalCritical.setText(totalCriticalDef + "");
        txtTotalMajor.setText(totalMajorDef + "");
        txtTotalMinor.setText(totalMinorDef + "");
        if (totalCriticalDef > poItemDtl.CriticalDefectsAllowed) {
            txtTotalCritical.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.red));
        } else {
            txtTotalCritical.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));
        }
        if (totalMajorDef > poItemDtl.MajorDefectsAllowed) {
            txtTotalMajor.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.red));
        } else {
            txtTotalMajor.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));
        }
        if (totalMinorDef > poItemDtl.MinorDefectsAllowed) {
            txtTotalMinor.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.red));
        } else {
            txtTotalMinor.setTextColor(GenUtils.getColorResource(ItemInspectionDetail.this, R.color.colorBlack));
        }
        txtPermissibleMinor.setText(poItemDtl.MinorDefectsAllowed + "");
        txtPermissibleMajor.setText(poItemDtl.MajorDefectsAllowed + "");
        txtPermissibleCritical.setText(poItemDtl.CriticalDefectsAllowed + "");
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("ItenReport request ", requestCode + " result " + resultCode);
        if (requestCode == FEnumerations.REQUEST_FOR_ADD_WORKMANSHIP
                || requestCode == FEnumerations.REQUEST_FOR_EDIT_WORKMANSHIP /*&& resultCode == RESULT_OK*/) {
//            if (data != null) {
//                int pos = data.getIntExtra("pos", -1);
//                if (workManShipModels != null) {
//                    WorkManShipModel workManShipModel = GenUtils.deSerializeWorkmanship(data.getStringExtra("detail"));

//                    if (workManShipModel != null) {
                        /*int mPos = -1;
                        boolean isFound = false;
                        if (workManShipModels.size() > 0) {
                            for (int i = 0; i < workManShipModels.size(); i++) {
                                if (workManShipModels.get(i).pRowID.equals(workManShipModel.pRowID)) {
                                    mPos = i;
                                    isFound = true;

                                } *//*else {
                                    addAsDigitalImageFromWorkmanShip(workManShipModel);
                                    workManShipModels.add(workManShipModel);
                                }*//*
                            }
                        }
                        if (isFound) {
                            workManShipModels.set(mPos, workManShipModel);
                        } else {
                            workManShipModels.add(workManShipModel);
                        }*/
//                        handleWorkManship();

            setAdaptor();
//                    }
//                }
//            }
        } else if (requestCode == FEnumerations.REQUEST_FOR_ADD_DIGITALS_UPLOAD
                && resultCode == RESULT_OK) {
            if (data != null) {
//                int pos = data.getIntExtra("pos", -1);
                if (digitalsUploadModals != null) {
                    DigitalsUploadModal digitalsUploadModal = GenUtils.deSerializeDigitalUpload(data.getStringExtra("detail"));
                    if (digitalsUploadModal != null
                            && digitalsUploadModal.imageArray != null
                            && digitalsUploadModal.imageArray.size() > 0) {
                        digitalsUploadModals.add(digitalsUploadModal);
                        for (int i = 0; i < digitalsUploadModal.imageArray.size(); i++) {
                            DigitalsUploadModal mD;
                            mD = digitalsUploadModal;
                            mD.ImageExtn = FEnumerations.E_IMAGE_EXTN;
                            mD.selectedPicPath = digitalsUploadModal.imageArray.get(i);

                            mD.pRowID = digitalsUploadModal.pRowID = ItemInspectionDetailHandler.GeneratePK(ItemInspectionDetail.this, FEnumerations.E_TABLE_NAME_QRPOItemDtl_Image);
                            updateDBWithImage(digitalsUploadModal);
                        }


                        setDigitalUploadAdaptor();
                    }
                }
            }
        } else if ((requestCode == FEnumerations.REQUEST_FOR_ADD_ITEM_MEASUREMENT
                || requestCode == FEnumerations.REQUEST_FOR_EDIT_ITEM_MEASUREMENT)
            /*&& resultCode == RESULT_OK*/) {
//            if (data != null) {
            if (itemMeasurementModalList != null) {

                List<ItemMeasurementModal> lItemMsrment = ItemInspectionDetailHandler.getItemMeasurementList(this, poItemDtl.QRHdrID, poItemDtl.QRPOItemHdrID);

                if (lItemMsrment != null) {
                    itemMeasurementModalList.clear();
                    itemMeasurementModalList.addAll(lItemMsrment);
                }
                setItemMeasurementAdaptor();
                getOverAllStatusOfItemMeasurement();
            }
//                }
//            }
        } else if ((requestCode == FEnumerations.VIEW_TYPE_QUALITY_PARAMETER_DETAIL_LEVEL)) {
            setQualityParameterAdaptor();
        } else if ((requestCode == FEnumerations.VIEW_TYPE_INTERNAL_TEST_LEVEL)) {
            updatInternalTestUi();
        }

        /*else if (requestCode == FEnumerations.REQUEST_FOR_ADD_ITEM_MEASUREMENT_FINDING && resultCode == RESULT_OK)

        {
            if (data != null) {
//                int pos = data.getIntExtra("pos", -1);
                if (itemMeasurementModalList != null) {
                    ItemMeasurementModal itemMeasurementModal = GenUtils.deSerializeItemMeasurement(data.getStringExtra("detail"));
                    int pos = data.getIntExtra("pos", -1);
                    if (itemMeasurementModal != null && pos != -1
                            *//*&& itemMeasurementModal.listImages != null
                            && itemMeasurementModal.listImages.size() > 0*//*) {
                        itemMeasurementModalList.set(pos, itemMeasurementModal);
                        setItemMeasurementAdaptor();
                    }
                }
            }
        }*/ /*else if (requestCode == FEnumerations.REQUEST_FOR_ADD_PACKING_FILL && resultCode == RESULT_OK) {
            if (data != null) {
//                int pos = data.getIntExtra("pos", -1);
                if (packagePoItemDetalDetail != null) {
                    POItemDtl model = GenUtils.deSerializeItemModal(data.getStringExtra("detail"));
                    if (packagePoItemDetalDetail != null) {
                        packagePoItemDetalDetail = model;
                        updatePackingUI();
                    }
                }
            }

        }*/
        else {
            MultipleImageHandler.onActivityResult(ItemInspectionDetail.this,
                    requestCode, resultCode, data, new MultipleImageHandler.GetBitmap() {
                        @Override
                        public void onGetBitamp(Bitmap serverBitmap,
                                                ArrayList<String> imagePathArrayList,
                                                String valueReturned) {
                            int resId = Integer.valueOf(valueReturned);
                            if (resId == FEnumerations.REQUEST_FOR_INNER_PACKING_ATTACHMENT) {
//                                workmanshipImage1.setImageBitmap(deviceBitmap);
                                addASDigitalInnerPack(imagePathArrayList);
                                packagePoItemDetalDetail.innerPackingAttachmentList.addAll(imagePathArrayList);
                                innerAttachmentCount.setText("" + packagePoItemDetalDetail.innerPackingAttachmentList.size());
//                                        workManShipModel.listImages.add(stringImage2);
                                onInnerPackImage();


                            } else if (resId == FEnumerations.REQUEST_FOR_UNIT_PACKING_ATTACHMENT) {
//                                workmanshipImage2.setImageBitmap(deviceBitmap);

                                addASDigitalUnitPack(imagePathArrayList);
//                                        workManShipModel.listImages.add(stringImage2);
                                packagePoItemDetalDetail.unitPackingAttachmentList.addAll(imagePathArrayList);
                                unitAttachmentCount.setText("" + packagePoItemDetalDetail.unitPackingAttachmentList.size());
                                onUnitPackImage();


                            } else if (resId == FEnumerations.REQUEST_FOR_MASTER_PACKING_ATTACHMENT) {
//                                workmanshipImage3.setImageBitmap(deviceBitmap);

                                addASDigitalMaster(imagePathArrayList);
//                                        workManShipModel.listImages.add(stringImage2);
                                packagePoItemDetalDetail.masterPackingAttachmentList.addAll(imagePathArrayList);
                                masterAttachmentCount.setText("" + packagePoItemDetalDetail.masterPackingAttachmentList.size());
                                onMasterPackImage();


                            } else if (resId == FEnumerations.REQUEST_FOR_PALLET_PACKING_ATTACHMENT) {
//                                workmanshipImage3.setImageBitmap(deviceBitmap);

                                addASDigitalPlannet(imagePathArrayList);
//                                        workManShipModel.listImages.add(stringImage2);
                                packagePoItemDetalDetail.palletPackingAttachmentList.addAll(imagePathArrayList);
                                palletAttachmentCount.setText("" + packagePoItemDetalDetail.palletPackingAttachmentList.size());
                                onPalletPackImage();


                            }/* else if (resId == FEnumerations.REQUEST_FOR_QUALITY_PARAMETER_ATTACHMENT) {
//                                workmanshipImage3.setImageBitmap(deviceBitmap);


                                if (qualityParameterList != null && qualityParameterList.size() > 0 && _qualityParameterAttachmentPos != -1) {
                                    if (imagePathArrayList != null && imagePathArrayList.size() > 0) {
                                        for (int i = 0; i < imagePathArrayList.size(); i++) {
                                            DigitalsUploadModal asDigitalsUploadModal = new DigitalsUploadModal();
                                            asDigitalsUploadModal.title = qualityParameterList.get(_qualityParameterAttachmentPos).MainDescr;
                                            asDigitalsUploadModal.ImageExtn = FEnumerations.E_IMAGE_EXTN;
                                            asDigitalsUploadModal.selectedPicPath = imagePathArrayList.get(i);
//                                                    asDigitalsUploadModal.pRowID = ItemInspectionDetailHandler.GeneratePK(ItemInspectionDetail.this, FEnumerations.E_TABLE_NAME_QRPOItemDtl_Image);
                                            qualityParameterList.get(_qualityParameterAttachmentPos).imageAttachmentList.add(asDigitalsUploadModal);
                                        }
                                    }
                                }
                                if (qualityParameterAdaptor != null) {
                                    qualityParameterAdaptor.notifyDataSetChanged();
                                }
                                onQualityParameterSelectImage();
//                                _qualityParameterAttachmentPos = -1;


                            }*/ else if (resId == FEnumerations.REQUEST_FOR_InternalTest_ATTACHMENT) {

                                if (internalTestList != null && internalTestList.size() > 0 && _iternalTestAttachmentPos != -1) {
                                    if (imagePathArrayList != null && imagePathArrayList.size() > 0) {
                                        for (int i = 0; i < imagePathArrayList.size(); i++) {
                                            DigitalsUploadModal asDigitalsUploadModal = new DigitalsUploadModal();
                                            asDigitalsUploadModal.title = internalTestList.get(_iternalTestAttachmentPos).MainDescr;
                                            asDigitalsUploadModal.ImageExtn = FEnumerations.E_IMAGE_EXTN;
                                            asDigitalsUploadModal.selectedPicPath = imagePathArrayList.get(i);
//                                                    asDigitalsUploadModal.pRowID = ItemInspectionDetailHandler.GeneratePK(ItemInspectionDetail.this, FEnumerations.E_TABLE_NAME_QRPOItemDtl_Image);
                                            internalTestList.get(_iternalTestAttachmentPos).imageAttachmentList.add(asDigitalsUploadModal);
                                        }
                                    }
                                }
                                if (internalTestAdaptor != null) {
                                    internalTestAdaptor.notifyDataSetChanged();
                                }
                                onInternalTestSelectImage();
                            } else if (resId == FEnumerations.REQUEST_FOR_UNIT_PKG_APP_ATTACHMENT) {//created by shekhar

                                addASDigitalPkgAppear(imagePathArrayList, "Unit pack appearance");
                                //need to set the image count
//                                packagePoItemDetalDetail.unitPackingAttachmentList.addAll(imagePathArrayList);
//                                unitAttachmentCount.setText("" + packagePoItemDetalDetail.unitPackingAttachmentList.size());
//                                onUnitPackageAppearImage();
                            } else if (resId == FEnumerations.REQUEST_FOR_SHIPPING_PKG_APP_ATTACHMENT) {//created by shekhar

                                addASDigitalPkgAppear(imagePathArrayList, "Shipping pack appearance");
                                //need to set the image count
//                                packagePoItemDetalDetail.unitPackingAttachmentList.addAll(imagePathArrayList);
//                                unitAttachmentCount.setText("" + packagePoItemDetalDetail.unitPackingAttachmentList.size());
//                                onUnitPackageAppearImage();
                            } else if (resId == FEnumerations.REQUEST_FOR_INNER_PKG_APP_ATTACHMENT) {//created by shekhar

                                addASDigitalPkgAppear(imagePathArrayList, "Inner pack appearance");
                                //need to set the image count
//                                packagePoItemDetalDetail.unitPackingAttachmentList.addAll(imagePathArrayList);
//                                unitAttachmentCount.setText("" + packagePoItemDetalDetail.unitPackingAttachmentList.size());
//                                onUnitPackageAppearImage();
                            } else if (resId == FEnumerations.REQUEST_FOR_MASTER_PKG_APP_ATTACHMENT) {//created by shekhar

                                addASDigitalPkgAppear(imagePathArrayList, "Master pack appearance");
                                //need to set the image count
//                                packagePoItemDetalDetail.unitPackingAttachmentList.addAll(imagePathArrayList);
//                                unitAttachmentCount.setText("" + packagePoItemDetalDetail.unitPackingAttachmentList.size());
//                                onUnitPackageAppearImage();
                            } else if (resId == FEnumerations.REQUEST_FOR_PALLET_PKG_APP_ATTACHMENT) {//created by shekhar

                                addASDigitalPkgAppear(imagePathArrayList, "Pallet pack appearance");
                                //need to set the image count
//                                packagePoItemDetalDetail.unitPackingAttachmentList.addAll(imagePathArrayList);
//                                unitAttachmentCount.setText("" + packagePoItemDetalDetail.unitPackingAttachmentList.size());
//                                onUnitPackageAppearImage();
                            }
                            else if (resId == FEnumerations.REQUEST_FOR_UNIT_BARCODE_ATTACHMENT) {//created by shekhar

                                addASDigitalPkgAppear(imagePathArrayList, "Unit Barcode");
                                //need to set the image count
//                                packagePoItemDetalDetail.unitPackingAttachmentList.addAll(imagePathArrayList);
//                                unitAttachmentCount.setText("" + packagePoItemDetalDetail.unitPackingAttachmentList.size());
//                                onUnitPackageAppearImage();
                            }
                            else if (resId == FEnumerations.REQUEST_FOR_INNER_BARCODE_ATTACHMENT) {//created by shekhar

                                addASDigitalPkgAppear(imagePathArrayList, "Inner Barcode");
                                //need to set the image count
//                                packagePoItemDetalDetail.unitPackingAttachmentList.addAll(imagePathArrayList);
//                                unitAttachmentCount.setText("" + packagePoItemDetalDetail.unitPackingAttachmentList.size());
//                                onUnitPackageAppearImage();
                            }
                            else if (resId == FEnumerations.REQUEST_FOR_MASTER_BARCODE_ATTACHMENT) {//created by shekhar

                                addASDigitalPkgAppear(imagePathArrayList, "Master Barcode");
                                //need to set the image count
//                                packagePoItemDetalDetail.unitPackingAttachmentList.addAll(imagePathArrayList);
//                                unitAttachmentCount.setText("" + packagePoItemDetalDetail.unitPackingAttachmentList.size());
//                                onUnitPackageAppearImage();
                            }
                            else if (resId == FEnumerations.REQUEST_FOR_PALLET_BARCODE_ATTACHMENT) {//created by shekhar

                                addASDigitalPkgAppear(imagePathArrayList, "Pallet Barcode");
                                //need to set the image count
//                                packagePoItemDetalDetail.unitPackingAttachmentList.addAll(imagePathArrayList);
//                                unitAttachmentCount.setText("" + packagePoItemDetalDetail.unitPackingAttachmentList.size());
//                                onUnitPackageAppearImage();
                            }
                        }
                    });

        } /*else {
            PhotoHandler.onActivityResult(ItemInspectionDetail.this,
                    requestCode, resultCode, data, new PhotoHandler.GetBitmap() {
                        @Override
                        public void onGetBitamp(Bitmap serverBitmap,
                                                Bitmap deviceBitmap,
                                                String valueReturned) {
                            int resId = Integer.valueOf(valueReturned);
                            if (resId == R.id.workmanshipImage1) {
//                                workmanshipImage1.setImageBitmap(deviceBitmap);
//                                new ImgToStringConverter(getApplicationContext()
//                                        , deviceBitmap, new ImgToStringConverter.ImgToStringListener() {
//                                    @Override
//                                    public void onImgToStringListener(String stringImage2) {
//                                        workManShipModel.listImages.add(stringImage2);
//                                    }
//                                }).execute();


                            } else if (resId == R.id.workmanshipImage2) {
//                                workmanshipImage2.setImageBitmap(deviceBitmap);
//                                new ImgToStringConverter(getApplicationContext()
//                                        , deviceBitmap, new ImgToStringConverter.ImgToStringListener() {
//                                    @Override
//                                    public void onImgToStringListener(String stringImage2) {
//                                        workManShipModel.listImages.add(stringImage2);
//                                    }
//                                }).execute();
//
//                                workmanshipImage2.setAlpha(1.0f);

                            } else if (resId == R.id.workmanshipImage3) {
//                                workmanshipImage3.setImageBitmap(deviceBitmap);
//                                new ImgToStringConverter(getApplicationContext()
//                                        , deviceBitmap, new ImgToStringConverter.ImgToStringListener() {
//                                    @Override
//                                    public void onImgToStringListener(String stringImage2) {
//                                        workManShipModel.listImages.add(stringImage2);
//                                    }
//                                }).execute();
//
//                                workmanshipImage3.setAlpha(1.0f);

                            }
                        }
                    });
            if (requestCode == 0
                    && resultCode == RESULT_OK) {
            }
        }*/

    }

    private String updateDBWithImage(DigitalsUploadModal digitalsUploadModal) {
        return ItemInspectionDetailHandler.updateImage(this, poItemDtl.QRHdrID, poItemDtl.QRPOItemHdrID, digitalsUploadModal);

    }

   /* private void addAsDigitalImageFromWorkmanShip(WorkManShipModel mWorkManShipModel, String wRowID) {
        if (mWorkManShipModel != null
                && mWorkManShipModel.listImages != null
                && mWorkManShipModel.listImages.size() > 0) {
            for (int i = 0; i < mWorkManShipModel.listImages.size(); i++) {
//                DigitalsUploadModal asDigitalsUploadModal = new DigitalsUploadModal();
//                asDigitalsUploadModal.Description = mWorkManShipModel.Description;
//                asDigitalsUploadModal.title = mWorkManShipModel.Description;
//                asDigitalsUploadModal.listImages = mWorkManShipModel.listImages.get(i).listImages;
//                asDigitalsUploadModal.pRowID = ItemInspectionDetailHandler.GeneratePK(ItemInspectionDetail.this, FEnumerations.E_TABLE_NAME_QRPOItemDtl_Image);
//                digitalsUploadModals.add(asDigitalsUploadModal);
                if (TextUtils.isEmpty(mWorkManShipModel.listImages.get(i).title)) {
                    mWorkManShipModel.listImages.get(i).title = mWorkManShipModel.Description;
                }
                if (TextUtils.isEmpty(mWorkManShipModel.listImages.get(i).ImageExtn)
                        || mWorkManShipModel.listImages.get(i).ImageExtn.equals("null")) {
                    mWorkManShipModel.listImages.get(i).ImageExtn = FEnumerations.E_IMAGE_EXTN;
                }
                if (TextUtils.isEmpty(mWorkManShipModel.listImages.get(i).pRowID)
                        || mWorkManShipModel.listImages.get(i).pRowID.equals("null")) {
                    mWorkManShipModel.listImages.get(i).pRowID = ItemInspectionDetailHandler.GeneratePK(ItemInspectionDetail.this, FEnumerations.E_TABLE_NAME_QRPOItemDtl_Image);
                }
                String digitalsRowId = updateDBWithImage(mWorkManShipModel.listImages.get(i));
                if (!TextUtils.isEmpty(digitalsRowId)) {
                    List<WorkManShipModel> dtlList = ItemInspectionDetailHandler.getDigitalsFromWorkmanShip(ItemInspectionDetail.this, wRowID);
                    if (dtlList != null && dtlList.size() > 0) {
                        WorkManShipModel mWo = dtlList.get(0);
//                        mWo.pRowID = wRowID;
                        if (!TextUtils.isEmpty(mWo.Digitals) && !mWo.Digitals.equals("null")) {
                            mWo.Digitals = mWo.Digitals + ", " + digitalsRowId;
                        } else {
                            mWo.Digitals = digitalsRowId;
                        }
                        ItemInspectionDetailHandler.updateDigitalFromWorkmanShip(ItemInspectionDetail.this, mWo);
                    }
                }
            }

        }

    }*/

    private void addASDigitalInnerPack(ArrayList<String> imagePathArrayList) {
        if (imagePathArrayList != null && imagePathArrayList.size() > 0) {
            for (int i = 0; i < imagePathArrayList.size(); i++) {
                DigitalsUploadModal asDigitalsUploadModal = new DigitalsUploadModal();
                asDigitalsUploadModal.selectedPicPath = imagePathArrayList.get(i);
                asDigitalsUploadModal.title = "Inner pack";
                asDigitalsUploadModal.ImageExtn = FEnumerations.E_IMAGE_EXTN;
                asDigitalsUploadModal.pRowID = ItemInspectionDetailHandler.GeneratePK(ItemInspectionDetail.this, FEnumerations.E_TABLE_NAME_QRPOItemDtl_Image);
                String imgPRowID = updateDBWithImage(asDigitalsUploadModal);
                if (!TextUtils.isEmpty(imgPRowID)) {
                    List<POItemDtl> dtlList = ItemInspectionDetailHandler.getDigitalsPackagingMeasurement(ItemInspectionDetail.this, poItemDtl.QRHdrID, poItemDtl.QRPOItemHdrID);
                    if (dtlList != null && dtlList.size() > 0) {
                        POItemDtl mPo = dtlList.get(0);

                        if (!TextUtils.isEmpty(mPo.PKG_Me_Inner_Digitals) && !mPo.PKG_Me_Inner_Digitals.equals("null")) {
                            mPo.PKG_Me_Inner_Digitals = mPo.PKG_Me_Inner_Digitals + ", " + imgPRowID;
                        } else {
                            mPo.PKG_Me_Inner_Digitals = imgPRowID;
                        }
                        ItemInspectionDetailHandler.updateDigitalsInnerPackagingMeasurement(ItemInspectionDetail.this, mPo);
                    }
                }
            }
        }
    }

    private void addASDigitalUnitPack(ArrayList<String> imagePathArrayList) {


        if (imagePathArrayList != null && imagePathArrayList.size() > 0) {
            for (int i = 0; i < imagePathArrayList.size(); i++) {
                DigitalsUploadModal asDigitalsUploadModal = new DigitalsUploadModal();
                asDigitalsUploadModal.selectedPicPath = imagePathArrayList.get(i);
                asDigitalsUploadModal.title = "Unit pack";
                asDigitalsUploadModal.ImageExtn = FEnumerations.E_IMAGE_EXTN;
                asDigitalsUploadModal.pRowID = ItemInspectionDetailHandler.GeneratePK(ItemInspectionDetail.this, FEnumerations.E_TABLE_NAME_QRPOItemDtl_Image);
                String imgPRowID = updateDBWithImage(asDigitalsUploadModal);
                if (!TextUtils.isEmpty(imgPRowID)) {
                    List<POItemDtl> dtlList = ItemInspectionDetailHandler.getDigitalsPackagingMeasurement(ItemInspectionDetail.this, poItemDtl.QRHdrID, poItemDtl.QRPOItemHdrID);
                    if (dtlList != null && dtlList.size() > 0) {
                        POItemDtl mPo = dtlList.get(0);

                        if (!TextUtils.isEmpty(mPo.PKG_Me_Unit_Digitals) && !mPo.PKG_Me_Unit_Digitals.equals("null")) {
                            mPo.PKG_Me_Unit_Digitals = mPo.PKG_Me_Unit_Digitals + ", " + imgPRowID;
                        } else {
                            mPo.PKG_Me_Unit_Digitals = imgPRowID;
                        }
                        ItemInspectionDetailHandler.updateDigitalsUnitPackagingMeasurement(ItemInspectionDetail.this, mPo);
                    }
                }
            }
        }
    }

    private void addASDigitalPkgAppear(ArrayList<String> imagePathArrayList, String title) {


        if (imagePathArrayList != null && imagePathArrayList.size() > 0) {
            for (int i = 0; i < imagePathArrayList.size(); i++) {
                DigitalsUploadModal asDigitalsUploadModal = new DigitalsUploadModal();
                asDigitalsUploadModal.selectedPicPath = imagePathArrayList.get(i);
                asDigitalsUploadModal.title = title;
                asDigitalsUploadModal.ImageExtn = FEnumerations.E_IMAGE_EXTN;
                asDigitalsUploadModal.pRowID = ItemInspectionDetailHandler.GeneratePK(ItemInspectionDetail.this, FEnumerations.E_TABLE_NAME_QRPOItemDtl_Image);
                String imgPRowID = updateDBWithImage(asDigitalsUploadModal);

                /*if (!TextUtils.isEmpty(imgPRowID)) {
                    List<POItemDtl> dtlList = ItemInspectionDetailHandler.getDigitalsPackagingMeasurement(
                            ItemInspectionDetail.this, poItemDtl.QRHdrID, poItemDtl.QRPOItemHdrID);

                    if (dtlList != null && dtlList.size() > 0) {
                        POItemDtl mPo = dtlList.get(0);

                        if (!TextUtils.isEmpty(mPo.PKG_Me_Unit_Digitals) && !mPo.PKG_Me_Unit_Digitals.equals("null")) {
                            mPo.PKG_Me_Unit_Digitals = mPo.PKG_Me_Unit_Digitals + ", " + imgPRowID;
                        } else {
                            mPo.PKG_Me_Unit_Digitals = imgPRowID;
                        }

                        ItemInspectionDetailHandler.updateDigitalsUnitPackagingMeasurement(
                                ItemInspectionDetail.this, mPo);
                    }
                }*/
            }
            Toast.makeText(this,"Attachment added successfully",Toast.LENGTH_SHORT).show();
        }
    }

    private void addASDigitalMaster(ArrayList<String> imagePathArrayList) {
        if (imagePathArrayList != null && imagePathArrayList.size() > 0) {
            for (int i = 0; i < imagePathArrayList.size(); i++) {
                DigitalsUploadModal asDigitalsUploadModal = new DigitalsUploadModal();
                asDigitalsUploadModal.selectedPicPath = imagePathArrayList.get(i);
                asDigitalsUploadModal.title = "Master pack";
                asDigitalsUploadModal.ImageExtn = FEnumerations.E_IMAGE_EXTN;
                asDigitalsUploadModal.pRowID = ItemInspectionDetailHandler.GeneratePK(ItemInspectionDetail.this, FEnumerations.E_TABLE_NAME_QRPOItemDtl_Image);
                String imgPRowID = updateDBWithImage(asDigitalsUploadModal);
                if (!TextUtils.isEmpty(imgPRowID)) {
                    List<POItemDtl> dtlList = ItemInspectionDetailHandler.getDigitalsPackagingMeasurement(ItemInspectionDetail.this, poItemDtl.QRHdrID, poItemDtl.QRPOItemHdrID);
                    if (dtlList != null && dtlList.size() > 0) {
                        POItemDtl mPo = dtlList.get(0);

                        if (!TextUtils.isEmpty(mPo.PKG_Me_Master_Digitals) && !mPo.PKG_Me_Master_Digitals.equals("null")) {
                            mPo.PKG_Me_Master_Digitals = mPo.PKG_Me_Master_Digitals + ", " + imgPRowID;
                        } else {
                            mPo.PKG_Me_Master_Digitals = imgPRowID;
                        }
                        ItemInspectionDetailHandler.updateDigitalsMasterPackagingMeasurement(ItemInspectionDetail.this, mPo);
                    }
                }
            }
        }
    }

    private void addASDigitalPlannet(ArrayList<String> imagePathArrayList) {
        if (imagePathArrayList != null && imagePathArrayList.size() > 0) {
            for (int i = 0; i < imagePathArrayList.size(); i++) {
                DigitalsUploadModal asDigitalsUploadModal = new DigitalsUploadModal();
                asDigitalsUploadModal.title = "Pallet pack";
                asDigitalsUploadModal.ImageExtn = FEnumerations.E_IMAGE_EXTN;
                asDigitalsUploadModal.selectedPicPath = imagePathArrayList.get(i);
                asDigitalsUploadModal.pRowID = ItemInspectionDetailHandler.GeneratePK(ItemInspectionDetail.this, FEnumerations.E_TABLE_NAME_QRPOItemDtl_Image);
                String imgPRowID = updateDBWithImage(asDigitalsUploadModal);
                if (!TextUtils.isEmpty(imgPRowID)) {
                    List<POItemDtl> dtlList = ItemInspectionDetailHandler.getDigitalsPackagingMeasurement(ItemInspectionDetail.this, poItemDtl.QRHdrID, poItemDtl.QRPOItemHdrID);

                    if (dtlList != null && dtlList.size() > 0) {
                        POItemDtl mPo = dtlList.get(0);

                        if (!TextUtils.isEmpty(mPo.PKG_Me_Pallet_Digitals) && !mPo.PKG_Me_Pallet_Digitals.equals("null")) {
                            mPo.PKG_Me_Pallet_Digitals = mPo.PKG_Me_Pallet_Digitals + ", " + imgPRowID;
                        } else {
                            mPo.PKG_Me_Pallet_Digitals = imgPRowID;
                        }
                        ItemInspectionDetailHandler.updateDigitalsPlannetPackagingMeasurement(ItemInspectionDetail.this, mPo);
                    }

                }
            }
        }
    }


   /* private void addASDigitalQualityParameter(QualityParameter qualityParameter, String returnPRowID) {
        if (qualityParameter != null
                && qualityParameter.imageAttachmentList != null
                && qualityParameter.imageAttachmentList.size() > 0) {
            for (int i = 0; i < qualityParameter.imageAttachmentList.size(); i++) {


                if (TextUtils.isEmpty(qualityParameter.imageAttachmentList.get(i).pRowID)
                        || qualityParameter.imageAttachmentList.get(i).pRowID.equals("null")) {
                    qualityParameter.imageAttachmentList.get(i).pRowID = ItemInspectionDetailHandler.GeneratePK(ItemInspectionDetail.this, FEnumerations.E_TABLE_NAME_QRPOItemDtl_Image);
                }

                String imgPRowID = updateDBWithImage(qualityParameter.imageAttachmentList.get(i));
                if (!TextUtils.isEmpty(imgPRowID)) {
                    List<QualityParameter> dtlList = ItemInspectionDetailHandler.getListQualityParameterAccordingRowId(ItemInspectionDetail.this, returnPRowID);

                    if (dtlList != null && dtlList.size() > 0) {
                        QualityParameter mPo = dtlList.get(0);

                        if (!TextUtils.isEmpty(mPo.Digitals) && !mPo.Digitals.equals("null")) {
                            mPo.Digitals = mPo.Digitals + ", " + imgPRowID;
                        } else {
                            mPo.Digitals = imgPRowID;
                        }
                        ItemInspectionDetailHandler.updateDigitalsQualityMeasurement(ItemInspectionDetail.this, mPo);
                    }

                }
            }
        }
    }*/

    /*private void addASDigitalFitnessCheck(QualityParameter qualityParameter, String returnPRowID) {
        if (qualityParameter != null
                && qualityParameter.imageAttachmentList != null
                && qualityParameter.imageAttachmentList.size() > 0) {
            for (int i = 0; i < qualityParameter.imageAttachmentList.size(); i++) {


                if (TextUtils.isEmpty(qualityParameter.imageAttachmentList.get(i).pRowID)
                        || qualityParameter.imageAttachmentList.get(i).pRowID.equals("null")) {
                    qualityParameter.imageAttachmentList.get(i).pRowID = ItemInspectionDetailHandler.GeneratePK(ItemInspectionDetail.this, FEnumerations.E_TABLE_NAME_QRPOItemDtl_Image);
                }

                String imgPRowID = updateDBWithImage(qualityParameter.imageAttachmentList.get(i));
                if (!TextUtils.isEmpty(imgPRowID)) {
                    List<QualityParameter> dtlList = ItemInspectionDetailHandler.getListFitnessCheckAccordingRowId(ItemInspectionDetail.this, returnPRowID);

                    if (dtlList != null && dtlList.size() > 0) {
                        QualityParameter mPo = dtlList.get(0);

                        if (!TextUtils.isEmpty(mPo.Digitals) && !mPo.Digitals.equals("null")) {
                            mPo.Digitals = mPo.Digitals + ", " + imgPRowID;
                        } else {
                            mPo.Digitals = imgPRowID;
                        }
                        ItemInspectionDetailHandler.updateDigitalsFitnessCheck(ItemInspectionDetail.this, mPo);
                    }

                }
            }
        }
    }*/

    private void handleSubmit() {
        _WorkmanshipRemark = editWorkmanshipRemark.getText().toString();
        _ItemMeasurementRemark = editItemMeasurementRemark.getText().toString();

    }

   /* @Override
    public void onQualityClick(View view, int position) {
        _qualityParameterAttachmentPos = position;
        onQualityParameterImage();
    }*/

    @Override
    public void onQualityClick(QualityParameter qualityParameter) {
        Intent intent = new Intent(ItemInspectionDetail.this, AddQualityParameter.class);
        intent.putExtra("detail", GenUtils.serializePOItemModal(poItemDtl));
        intent.putExtra("qualityParameter", GenUtils.serializeQualityParameter(qualityParameter));
        intent.putExtra("viewType", FEnumerations.VIEW_TYPE_QUALITY_PARAMETER_DETAIL_LEVEL);
        startActivityForResult(intent, FEnumerations.VIEW_TYPE_QUALITY_PARAMETER_DETAIL_LEVEL);
    }

   /* @Override
    public void onClickImageTestReport(View view, int position) {
        _iternalTestAttachmentPos = position;
        onInternalTestImage();
    }*/

    @Override
    public void onClickImageTestReport(QualityParameter qualityParameter) {
        Intent intent = new Intent(ItemInspectionDetail.this, AddQualityParameter.class);
        intent.putExtra("detail", GenUtils.serializePOItemModal(poItemDtl));
        intent.putExtra("qualityParameter", GenUtils.serializeQualityParameter(qualityParameter));
        intent.putExtra("viewType", FEnumerations.VIEW_TYPE_INTERNAL_TEST_LEVEL);
        startActivityForResult(intent, FEnumerations.VIEW_TYPE_INTERNAL_TEST_LEVEL);
    }

    private void onInternalTestImage() {
        String valueToReturn = FEnumerations.REQUEST_FOR_InternalTest_ATTACHMENT + "";
        MultipleImageHandler.showDialog(ItemInspectionDetail.this, MultipleImageHandler.DOCUMENT, valueToReturn);
    }

    private void onInternalTestSelectImage() {
        String valueToReturn = FEnumerations.REQUEST_FOR_InternalTest_ATTACHMENT + "";
        MultipleImageHandler.showDialog(ItemInspectionDetail.this, MultipleImageHandler.DOCUMENT, valueToReturn, MultipleImageHandler.pickerViewer);

    }


    @Override
    public void onNoApplicableClickOnQuality(QualityParameter mQualityParameter) {
        FslLog.d(TAG, " updating on No Applicable  QualityParameter");
        ItemInspectionDetailHandler.updateQualityParameter(this, mQualityParameter, poItemDtl);
    }

    @Override
    public void onNoApplicableClickOnInternalTest(QualityParameter mQualityParameter) {
        FslLog.d(TAG, " updating on No Applicable  InternalTest");
        ItemInspectionDetailHandler.updateFitness(this, mQualityParameter, poItemDtl);
    }


    @Override
    public void onClickForEdit(DigitalsUploadModal mDigitalsUploadModal, int position) {
        GenUtils.forEditableConfirmationAlertDialog(ItemInspectionDetail.this,
                FClientConstants.ACTION_OK, FClientConstants.TEXT_BUTTON_CANCEL,
                "Edit Title", mDigitalsUploadModal.title, new GenUtils.AlertDialogCallBackClickListener() {
                    @Override
                    public void onPositiveButton(String resultSring) {
                        if (!TextUtils.isEmpty(resultSring)) {
                            ItemInspectionDetailHandler.updateImageTitle(ItemInspectionDetail.this, resultSring, mDigitalsUploadModal.pRowID);
                            updateUIOnEdit();
                        }

                    }

                    @Override
                    public void onNegativeButton() {

                    }
                });
    }

    @Override
    public void onDigitalCancelClick(DigitalsUploadModal digitalsUploadModal, int position) {
        ItemInspectionDetailHandler.deleteDigitalUploaded(ItemInspectionDetail.this, digitalsUploadModal.pRowID);
        if (digitalsUploadModals != null && digitalsUploadModals.size() > 0) {
            digitalsUploadModals.remove(position);
        }
        if (digitalUploadAdaptor != null) {
            digitalUploadAdaptor.notifyDataSetChanged();
        }
        updateUIOnDelete();
    }

    @Override
    public void onDeleteClick(WorkManShipModel workManShipModel, int position) {

        if (!TextUtils.isEmpty(workManShipModel.Digitals) && !workManShipModel.Digitals.equals("null")) {
            String[] spStr = workManShipModel.Digitals.split(",");
            if (spStr != null && spStr.length > 0) {
                for (int j = 0; j < spStr.length; j++) {
                    String rowId = spStr[j];
                    if (!TextUtils.isEmpty(rowId)) {
                        ItemInspectionDetailHandler.deleteDigitalUploaded(ItemInspectionDetail.this, rowId);
                    }
                }

            }

        }
        ItemInspectionDetailHandler.deleteWorkmanship(ItemInspectionDetail.this, workManShipModel.pRowID);
        updateWorkmanshipUi();
        updateTotalWorkmanship();
    }

    @Override
    public void onDeleteClick(ItemMeasurementModal itemMeasurementModal, int position) {

        if (itemMeasurementModal != null) {

            List<ItemMeasurementModal> lMsr = ItemInspectionDetailHandler.getFindingItemMeasurementList(ItemInspectionDetail.this, itemMeasurementModal);
            if (lMsr != null && lMsr.size() > 0) {
                for (int i = 0; i < lMsr.size(); i++) {
                    ItemInspectionDetailHandler.deletFindingItemMeasurement(ItemInspectionDetail.this, lMsr.get(i).pRowIDForFinding);
                }
            }
            ItemInspectionDetailHandler.deletItemmeasurement(ItemInspectionDetail.this, itemMeasurementModal.pRowID);
            List<ItemMeasurementModal> lItemMeasurement = ItemInspectionDetailHandler.getItemMeasurementList(this, poItemDtl.QRHdrID, poItemDtl.QRPOItemHdrID);

            if (lItemMeasurement != null) {
                itemMeasurementModalList.clear();
                itemMeasurementModalList.addAll(lItemMeasurement);
            }
            setItemMeasurementAdaptor();

        }
    }

    private void showProgressDialog(String message) {

        if (loadingDialog == null)
            loadingDialog = new ProgressDialog(ItemInspectionDetail.this);
//        loadingDialog.setTitle("Please wait");
        loadingDialog.setMessage(message);
        if (loadingDialog != null && !loadingDialog.isShowing()) {
//            loadingDialog.show();
            GenUtils.safeProgressDialogShow(TAG, ItemInspectionDetail.this, loadingDialog);
        }

    }

    private void hideDialog() {
        if (loadingDialog != null && loadingDialog.isIndeterminate() && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }

    }

    private void updateUIOnDelete() {
        showProgressDialog("Waiting...");
        new Handler().postDelayed(new Runnable() {
            public void run() {
                handlePackaging();
                updatePackingUI();
                setAdaptor();
                updateDigitalUi();
                setQualityParameterAdaptor();
                hideDialog();
            }
        }, 1000);


    }

    private void updateUIOnEdit() {
        showProgressDialog("Waiting...");
        new Handler().postDelayed(new Runnable() {
            public void run() {
                handlePackaging();
                updatePackingUI();
                setAdaptor();
                updateDigitalUi();
                setQualityParameterAdaptor();
                hideDialog();
            }
        }, 1000);
    }

    private void handleOverAllResult() {

        if (packagePoItemDetalDetail.ItemMeasurement_InspectionResultID != null && packagePoItemDetalDetail.ItemMeasurement_InspectionResultID.equals(FEnumerations.E_OVER_ALL_FAIL_RESULT)) {
            packagePoItemDetalDetail.Overall_InspectionResultID = packagePoItemDetalDetail.ItemMeasurement_InspectionResultID;
        } else if (packagePoItemDetalDetail.WorkmanShip_InspectionResultID != null && packagePoItemDetalDetail.WorkmanShip_InspectionResultID.equals(FEnumerations.E_OVER_ALL_FAIL_RESULT)) {
            packagePoItemDetalDetail.Overall_InspectionResultID = packagePoItemDetalDetail.WorkmanShip_InspectionResultID;
        } else if (packagePoItemDetalDetail.PKG_Me_InspectionResultID != null && packagePoItemDetalDetail.PKG_Me_InspectionResultID.equals(FEnumerations.E_OVER_ALL_FAIL_RESULT)) {
            packagePoItemDetalDetail.Overall_InspectionResultID = packagePoItemDetalDetail.PKG_Me_InspectionResultID;
        } else if (packagePoItemDetalDetail.ItemMeasurement_InspectionResultID != null && packagePoItemDetalDetail.ItemMeasurement_InspectionResultID.equals(FEnumerations.E_OVER_ALL_HOLD_RESULT)) {
            packagePoItemDetalDetail.Overall_InspectionResultID = packagePoItemDetalDetail.ItemMeasurement_InspectionResultID;
        } else if (packagePoItemDetalDetail.WorkmanShip_InspectionResultID != null && packagePoItemDetalDetail.WorkmanShip_InspectionResultID.equals(FEnumerations.E_OVER_ALL_HOLD_RESULT)) {
            packagePoItemDetalDetail.Overall_InspectionResultID = packagePoItemDetalDetail.WorkmanShip_InspectionResultID;
        } else if (packagePoItemDetalDetail.PKG_Me_InspectionResultID != null && packagePoItemDetalDetail.PKG_Me_InspectionResultID.equals(FEnumerations.E_OVER_ALL_HOLD_RESULT)) {
            packagePoItemDetalDetail.Overall_InspectionResultID = packagePoItemDetalDetail.PKG_Me_InspectionResultID;
        } else if (packagePoItemDetalDetail.PKG_Me_InspectionResultID != null && packagePoItemDetalDetail.PKG_Me_InspectionResultID.equals(FEnumerations.E_OVER_ALL_DESC_RESULT)) {
            packagePoItemDetalDetail.Overall_InspectionResultID = packagePoItemDetalDetail.PKG_Me_InspectionResultID;
        } else if (packagePoItemDetalDetail.OnSiteTest_InspectionResultID != null && packagePoItemDetalDetail.OnSiteTest_InspectionResultID.equals(FEnumerations.E_OVER_ALL_DESC_RESULT)) {
            packagePoItemDetalDetail.Overall_InspectionResultID = packagePoItemDetalDetail.OnSiteTest_InspectionResultID;
        } else if (packagePoItemDetalDetail.Barcode_InspectionResultID != null && packagePoItemDetalDetail.Barcode_InspectionResultID.equals(FEnumerations.E_OVER_ALL_DESC_RESULT)) {
            packagePoItemDetalDetail.Overall_InspectionResultID = packagePoItemDetalDetail.Barcode_InspectionResultID;
        } else if (packagePoItemDetalDetail.PKG_App_InspectionResultID != null && packagePoItemDetalDetail.PKG_App_InspectionResultID.equals(FEnumerations.E_OVER_ALL_DESC_RESULT)) {
            packagePoItemDetalDetail.Overall_InspectionResultID = packagePoItemDetalDetail.PKG_App_InspectionResultID;
        }
        handleHoleOverAllResult();

    }
}
