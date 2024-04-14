package com.podetail;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.General.EnclosureModal;
import com.General.InsLvHdrHandler;
import com.General.InsLvHdrModal;
import com.General.QualityLevelHandler;
import com.General.QualityLevelModal;
import com.General.SysData22Handler;
import com.General.SysData22Modal;
import com.buyereasefsl.IntimationActivity;
import com.buyereasefsl.ItemInspectionDetailHandler;
import com.buyereasefsl.R;
import com.constant.FEnumerations;
import com.inspection.InspectionListHandler;
import com.inspection.InspectionModal;
import com.util.FslLog;
import com.util.GenUtils;
import com.util.NetworkUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import me.drakeet.support.toast.ToastCompat;

/**
 * Created by ADMIN on 11/14/2017.
 */

public class POItemListActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout task_details, inspectionDetail, inspDtContainer;

    Button btnGoToPoDetail;

    RecyclerView recyclerView;
    List<POItemDtl> pOItemDtlList;
//    POItemListAdaptor inspectionAdaptor;

//    LinearLayout qualityTab, workManShipTab, cartonTab, moreDeatailTab,
//            qualityParametersTab, productionStatusTab, poEnclosureTab;
//    TextView txtQuality, txtWorkmanShip, txtCarton, txtMoreDetail,
//            txtProductionStatus, txtQualityParameters, txtPoEnclosure;

    TextView txtInspectionLevel, txtQualityLevel, txtQualityLevelMinor, txtTypeofInspection, txtStatus;
    TextView txtLocation;
    TextView txtArrivalTime, txtStartTimeLevel, txtCompleteTime;
    private int mInspStartTimeHour = -1, mInspStartTimeMinute = -1;
    private int mArrivalTimeHour = -1, mArrivalTimeMinute = -1;
    private int mCompleteTimeHour = -1, mCompleteTimeMinute = -1;
    TextView txtInspectionDate;
    String pRowID;
    InspectionModal inspectionModal;
    TextView txtCustomer, txtVendor, txtQualityResource, txtActivity;
    String pRowIdOfInspectLevel = null,
            pRowIdOfQualityMinorLevel = null,
            pRowIdOfQualityMajorLevel = null,
            pRowIdOfStatus;
    int mYear, cYear;
    int mMonth, cMonth;
    int mDay, cDay;
    String TAG = "POItemListActivity";


//    private static final int PICKFILE_RESULT_CODE = 1;


    LinearLayout completionDateContainer;
    TextView txtCompletionDate;
    EditText editProductionStatus;
    String _productionStatusDate = null, _productionStatusRemark;
    int vMenuType = FEnumerations.E_MENU_PO_VIEW_TYPE_HEADER;


    EditText editPoDetailRemark, vendorContactPerson;
    ProgressDialog loadingDialog;
    LinearLayout qualityTotalContainer, workmanshipTotalContainer, cartonTotalContainer;

    RadioGroup radioGroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inspection_form);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        inspectionModal = GenUtils.deSerializeInspectionModal(getIntent().getStringExtra("detail"));

        pRowID = inspectionModal.pRowID;
        task_details = (LinearLayout) findViewById(R.id.task_details);
        inspectionDetail = (LinearLayout) findViewById(R.id.inspectionDetail);


        btnGoToPoDetail = (Button) findViewById(R.id.btnGoToPoDetail);
        this.getSupportActionBar().setTitle(inspectionModal.pRowID);
        basicDetailHandler();
        findViewById(R.id.arrowLayoutDetail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (task_details.getVisibility() == View.VISIBLE) {
                    task_details.setVisibility(View.GONE);
                    ((ImageView) findViewById(R.id.expendArrowDetail)).setImageResource(R.drawable.ic_expand_more_black_24dp);
                } else {
                    task_details.setVisibility(View.VISIBLE);
                    ((ImageView) findViewById(R.id.expendArrowDetail)).setImageResource(R.drawable.ic_expand_less_black_24dp);
                }

            }
        });
        findViewById(R.id.inspectionDetailContainer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inspectionDetail.getVisibility() == View.VISIBLE) {
                    inspectionDetail.setVisibility(View.GONE);
                    ((ImageView) findViewById(R.id.expendArrowInspectionDetail)).setImageResource(R.drawable.ic_expand_more_black_24dp);
                } else {
                    inspectionDetail.setVisibility(View.VISIBLE);
                    ((ImageView) findViewById(R.id.expendArrowInspectionDetail)).setImageResource(R.drawable.ic_expand_less_black_24dp);
                }
            }
        });
        btnGoToPoDetail.setOnClickListener(this);


    /*    qualityTab = (LinearLayout) findViewById(R.id.qualityTab);
        workManShipTab = (LinearLayout) findViewById(R.id.workManShipTab);
        cartonTab = (LinearLayout) findViewById(R.id.cartonTab);
        moreDeatailTab = (LinearLayout) findViewById(R.id.moreDeatailTab);
        qualityParametersTab = (LinearLayout) findViewById(R.id.qualityParametersTab);
        productionStatusTab = (LinearLayout) findViewById(R.id.productionStatusTab);
        poEnclosureTab = (LinearLayout) findViewById(R.id.poEnclosureTab);
        txtQuality = (TextView) findViewById(R.id.txtQuality);
        txtWorkmanShip = (TextView) findViewById(R.id.txtWorkmanShip);
        txtCarton = (TextView) findViewById(R.id.txtCarton);
        txtMoreDetail = (TextView) findViewById(R.id.txtMoreDetail);
        txtProductionStatus = (TextView) findViewById(R.id.txtProductionStatus);
        txtQualityParameters = (TextView) findViewById(R.id.txtQualityParameters);
        txtPoEnclosure = (TextView) findViewById(R.id.txtPoEnclosure);

        qualityTab.setOnClickListener(this);
        workManShipTab.setOnClickListener(this);
        cartonTab.setOnClickListener(this);
        moreDeatailTab.setOnClickListener(this);
        productionStatusTab.setOnClickListener(this);
        qualityParametersTab.setOnClickListener(this);
        poEnclosureTab.setOnClickListener(this);*/


//        setAdaptor();
//        handleTotalPoDetail();
//        handleToUpdateTotal();
        getList();

//        handleConpletionStatus();
//        handleUploadsFileHandle();

        handleRemark();
        handleSpinner();

        handleToSyncImportanat();

    }

    private void handleToSyncImportanat() {
        if (!TextUtils.isEmpty(pRowID)) {
            List<EnclosureModal> lEnclosureList = ItemInspectionDetailHandler.getSyncEnclosureList(POItemListActivity.this, pRowID);
            if (lEnclosureList != null && lEnclosureList.size() > 0) {
                for (int i = 0; i < lEnclosureList.size(); i++) {
                    ItemInspectionDetailHandler.getUpdateDownloadStaus(POItemListActivity.this,
                            lEnclosureList.get(i));
                }
            } else {
                FslLog.e(TAG, "NO IMPORTANT DOC STATUS FOUND *********");
            }
        }

    }

    private void getList() {
        pOItemDtlList = new ArrayList<>();
        pOItemDtlList.addAll(POItemDtlHandler.getItemList(this, inspectionModal.pRowID));

    }

    private void handleRemark() {
        editPoDetailRemark = (EditText) findViewById(R.id.editPoDetailRemark);
        if (!TextUtils.isEmpty(inspectionModal.Comments) && !inspectionModal.Comments.equals("null")) {
            editPoDetailRemark.setText(inspectionModal.Comments);
        }
    }


  /*  private void handleConpletionStatus() {
        completionDateContainer = (LinearLayout) findViewById(R.id.completionDateContainer);
        txtCompletionDate = (TextView) findViewById(R.id.txtCompletionDate);
        editProductionStatus = (EditText) findViewById(R.id.editProductionStatus);
//        mYear = calendar.get(Calendar.YEAR);
//        mMonth = calendar.get(Calendar.MONTH);
//        mDay = calendar.get(Calendar.DAY_OF_MONTH);

        // get a date to represent "today"
        Calendar calendar = Calendar.getInstance();
        final Date today = calendar.getTime();
        FslLog.d(TAG, "today:    " + today);
        completionDateContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(POItemListActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
//Nitin store previous selected value and then show same date
//on reopening calendar
                                cYear = year;
                                cMonth = monthOfYear;
                                cDay = dayOfMonth;
                                // set day of month , month and year value in the edit text
                                String getDate = dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year;
                                String sentDt = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;

                                SimpleDateFormat getFormatDate = new SimpleDateFormat("dd/MM/yyyy");
                                SimpleDateFormat myFormat = new SimpleDateFormat("dd MMM yyyy");
                                try {
                                    String reformattedStr = myFormat.format(getFormatDate.parse(getDate));
                                    txtCompletionDate.setText(reformattedStr);
                                    _productionStatusDate = sentDt;
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, cYear, cMonth, cDay);
                datePickerDialog.getDatePicker().setMinDate(today.getTime());
//                datePickerDialog.getDatePicker().setMaxDate(future.getTime());
                datePickerDialog.show();
            }
        });


    }*/


    private void basicDetailHandler() {
        inspDtContainer = (LinearLayout) findViewById(R.id.inspDtContainer);
        txtInspectionDate = (TextView) findViewById(R.id.txtInspectionDate);
        txtCustomer = (TextView) findViewById(R.id.txtCustomer);
        txtVendor = (TextView) findViewById(R.id.txtVendor);
        txtQualityResource = (TextView) findViewById(R.id.txtQualityResource);
        txtActivity = (TextView) findViewById(R.id.txtActivity);
        vendorContactPerson = (EditText) findViewById(R.id.vendorContactPerson);

        txtCustomer.setText(inspectionModal.Customer);
        txtVendor.setText(inspectionModal.Vendor);
        txtQualityResource.setText(inspectionModal.Inspector);
        txtActivity.setText(inspectionModal.Activity);
        vendorContactPerson.setText(inspectionModal.VendorContact);
        handleInspDt();
    }

    private void handleInspDt() {

//        mYear = calendar.get(Calendar.YEAR);
//        mMonth = calendar.get(Calendar.MONTH);
//        mDay = calendar.get(Calendar.DAY_OF_MONTH);

        // get a date to represent "today"
        txtInspectionDate.setText(inspectionModal.InspectionDt);
        Calendar calendar = Calendar.getInstance();
        final Date today = calendar.getTime();
        FslLog.d(TAG, "today:    " + today);
        inspDtContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(POItemListActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
//Nitin store previous selected value and then show same date
//on reopening calendar
                                mYear = year;
                                mMonth = monthOfYear;
                                mDay = dayOfMonth;
                                // set day of month , month and year value in the edit text
                                String getDate = dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year;
                                String hr = year + ":" + (monthOfYear + 1) + ":" + dayOfMonth;

                                SimpleDateFormat getFormatDate = new SimpleDateFormat("dd/MM/yyyy");
                                SimpleDateFormat myFormat = new SimpleDateFormat("dd MMM yyyy");
                                try {
                                    String reformattedStr = myFormat.format(getFormatDate.parse(getDate));
                                    txtInspectionDate.setText(reformattedStr);
//                                    mRideDate = reformattedStr;
                                    inspectionModal.InspectionDt = reformattedStr;
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMinDate(today.getTime());
//                datePickerDialog.getDatePicker().setMaxDate(future.getTime());
                datePickerDialog.show();
            }
        });

    }

    private void handleSpinner() {
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioGroup.setClickable(false);
        radioGroup.setEnabled(false);
        if (inspectionModal.AQLFormula == 0) {
            ((RadioButton) findViewById(R.id.radioReportLvl)).setChecked(true);
        } else {
            ((RadioButton) findViewById(R.id.radioMaterialLevel)).setChecked(true);
        }
      /*
        switch (inspectionModal.AQLFormula) {

            case 2:
                ((RadioButton) findViewById(R.id.radioReportLvl)).setChecked(true);
//                mInsLevel = FEnumerations.E_INSPECTION_REPORT_LEVEL;
//                inspectionAdaptor.updateLevel(mInsLevel);
//                handleToUpdateTotal();
                break;
            case 1:
                ((RadioButton) findViewById(R.id.radioMaterialLevel)).setChecked(true);
//                mInsLevel = FEnumerations.E_INSPECTION_MATERIAL_LEVEL;
//                inspectionAdaptor.updateLevel(mInsLevel);
//                handleToUpdateTotal();
                break;
        }*/

        /*radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    // Check which radio button was clicked
                    switch (checkedId) {
                        case R.id.radioReportLvl:
                            mInsLevel = FEnumerations.E_INSPECTION_REPORT_LEVEL;
                            inspectionAdaptor.updateLevel(mInsLevel);
                            handleToUpdateTotal();
                            break;
                        case R.id.radioMaterialLevel:
                            mInsLevel = FEnumerations.E_INSPECTION_MATERIAL_LEVEL;
                            inspectionAdaptor.updateLevel(mInsLevel);
                            handleToUpdateTotal();
                            break;

                    }
                }

            }
        });*/
        txtInspectionLevel = (TextView) findViewById(R.id.txtInspectionLevel);
        txtQualityLevel = (TextView) findViewById(R.id.txtQualityLevel);
        txtQualityLevelMinor = (TextView) findViewById(R.id.txtQualityLevelMinor);
        txtTypeofInspection = (TextView) findViewById(R.id.txtTypeofInspection);
        txtStatus = (TextView) findViewById(R.id.txtStatus);
        txtLocation = (TextView) findViewById(R.id.txtLocation);
        txtArrivalTime = (TextView) findViewById(R.id.txtArrivalTime);
        txtStartTimeLevel = (TextView) findViewById(R.id.txtStartTimeLevel);
        txtCompleteTime = (TextView) findViewById(R.id.txtCompleteTime);

        txtInspectionLevel.setOnClickListener(this);
        txtQualityLevel.setOnClickListener(this);
        txtQualityLevelMinor.setOnClickListener(this);
        txtTypeofInspection.setOnClickListener(this);
        txtStatus.setOnClickListener(this);
        txtLocation.setOnClickListener(this);
        setFirstTimeValueInSpinnerAndTimer();
        handleArrivalTime();
        handleStartTime();
        handleCompleteTime();
    }

    private void setFirstTimeValueInSpinnerAndTimer() {

        txtInspectionLevel.setText("Select Inspection Level");
        txtQualityLevel.setText("Select Quality Level");
        txtStatus.setText("Select Status");
        txtLocation.setText(inspectionModal.Factory);
        if (!TextUtils.isEmpty(inspectionModal.InspectionLevel) && !inspectionModal.InspectionLevel.equals("null")) {
            final List<InsLvHdrModal> insLvHdrModals = InsLvHdrHandler.getDataAccordingToParticularList(this, inspectionModal.InspectionLevel);
            if (insLvHdrModals != null && insLvHdrModals.size() > 0) {
                txtInspectionLevel.setText(insLvHdrModals.get(0).InspAbbrv);
            }
            pRowIdOfInspectLevel = inspectionModal.InspectionLevel;
        } else {
            final List<InsLvHdrModal> insLvHdrModals = InsLvHdrHandler.getInsLvHdrList(this);
            if (insLvHdrModals != null && insLvHdrModals.size() > 0) {

                txtInspectionLevel.setText(insLvHdrModals.get(0).InspAbbrv);
                pRowIdOfInspectLevel = insLvHdrModals.get(0).pRowID;
                inspectionModal.InspectionLevel = pRowIdOfInspectLevel;
                inspectionModal.InspectionLevelDescr = insLvHdrModals.get(0).InspAbbrv;
            }
        }

        if ((!TextUtils.isEmpty(inspectionModal.QLMajor) && !inspectionModal.QLMajor.equals("null"))) {
            final List<QualityLevelModal> qualityMinorLevelList = QualityLevelHandler.getDataAccordingToParticularList(this, inspectionModal.QLMajor);
            if (qualityMinorLevelList != null && qualityMinorLevelList.size() > 0) {
                txtQualityLevel.setText(qualityMinorLevelList.get(0).QualityLevel);
            }
            pRowIdOfQualityMajorLevel = inspectionModal.QLMajor;
        } else {
            final List<QualityLevelModal> qualityLevelList = QualityLevelHandler.getQualityLevelList(this);
            if (qualityLevelList != null && qualityLevelList.size() > 0) {
                txtQualityLevel.setText(qualityLevelList.get(0).QualityLevel);
                pRowIdOfQualityMajorLevel = qualityLevelList.get(0).pRowID;
                inspectionModal.QLMajor = pRowIdOfQualityMajorLevel;
                inspectionModal.QLMajorDescr = qualityLevelList.get(0).QualityLevel;

            }
        }

        if ((!TextUtils.isEmpty(inspectionModal.QLMinor) && !inspectionModal.QLMinor.equals("null"))) {
            final List<QualityLevelModal> qualityMajorLevelList = QualityLevelHandler.getDataAccordingToParticularList(this, inspectionModal.QLMinor);
            if (qualityMajorLevelList != null && qualityMajorLevelList.size() > 0) {
                txtQualityLevelMinor.setText(qualityMajorLevelList.get(0).QualityLevel);
            }
            pRowIdOfQualityMinorLevel = inspectionModal.QLMinor;
        } else {
            final List<QualityLevelModal> qualityLevelList = QualityLevelHandler.getQualityLevelList(this);
            if (qualityLevelList != null && qualityLevelList.size() > 0) {

                txtQualityLevelMinor.setText(qualityLevelList.get(0).QualityLevel);
                pRowIdOfQualityMinorLevel = qualityLevelList.get(0).pRowID;
                inspectionModal.QLMinor = pRowIdOfQualityMinorLevel;
                inspectionModal.QLMinorDescr = qualityLevelList.get(0).QualityLevel;

            }
        }


        if (!TextUtils.isEmpty(inspectionModal.Status)) {
            final List<SysData22Modal> statusList = SysData22Handler.getSysData22ListAccToID(this, FEnumerations.E_STATUS_GEN_ID, inspectionModal.Status);
            if (statusList != null && statusList.size() > 0) {
                txtStatus.setText(statusList.get(0).MainDescr);
            }
        } else {
            final List<SysData22Modal> statusList = SysData22Handler.getSysData22List(this, FEnumerations.E_STATUS_GEN_ID);
            if (statusList != null && statusList.size() > 0) {
                txtStatus.setText(statusList.get(0).MainDescr);
                pRowIdOfStatus = statusList.get(0).MainID;
                inspectionModal.Status = pRowIdOfStatus;
            }
        }

        if (inspectionModal.ArrivalTime != null && !inspectionModal.ArrivalTime.equals("null")) {


            String timme = inspectionModal.ArrivalTime;
            String[] time = timme.split(":");
            mArrivalTimeHour = Integer.parseInt(time[0].trim());
            mArrivalTimeMinute = Integer.parseInt(time[1].trim());
            txtArrivalTime.setText(GenUtils.get12HourTimeFormatStr(mArrivalTimeHour, mArrivalTimeMinute));
        }
        if (inspectionModal.InspStartTime != null && !inspectionModal.InspStartTime.equals("null")) {
            String timme = inspectionModal.InspStartTime;
            String[] time = timme.split(":");
            mInspStartTimeHour = Integer.parseInt(time[0].trim());
            mInspStartTimeMinute = Integer.parseInt(time[1].trim());
            txtStartTimeLevel.setText(GenUtils.get12HourTimeFormatStr(mInspStartTimeHour, mInspStartTimeMinute));
        }

        if (inspectionModal.CompleteTime != null && !inspectionModal.CompleteTime.equals("null")) {
            String timme = inspectionModal.CompleteTime;
            String[] time = timme.split(":");
            mCompleteTimeHour = Integer.parseInt(time[0].trim());
            mCompleteTimeMinute = Integer.parseInt(time[1].trim());
            txtCompleteTime.setText(GenUtils.get12HourTimeFormatStr(mCompleteTimeHour, mCompleteTimeMinute));
        }
    }

    private void handleCompleteTime() {

        txtCompleteTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Calendar currentTime = Calendar.getInstance();
                int hour = currentTime.get(Calendar.HOUR_OF_DAY);
                int minute = currentTime.get(Calendar.MINUTE);
//                if (mCompleteTimeHour > -1
//                        && mCompleteTimeMinute > -1) {
//                    hour = mCompleteTimeHour;
//                    minute = mCompleteTimeMinute;
//                }
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(POItemListActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        mCompleteTimeHour = selectedHour;
                        mCompleteTimeMinute = selectedMinute;
                        txtCompleteTime.setText(GenUtils.get12HourTimeFormatStr(selectedHour, selectedMinute));

                        String hh = "" + selectedHour;
                        String mm = "" + selectedMinute;
                        if (selectedHour < 10) {
                            hh = "0" + selectedHour;
                        }
                        if (selectedMinute < 10) {
                            mm = "0" + selectedMinute;
                        }
                        inspectionModal.CompleteTime = hh + ":" + mm;

//                        _visitTime = "T" + hh + ":" + mm + ":00";


                    }
                }, hour, minute, true);

                mTimePicker.show();
            }
        });
    }

    private void handleStartTime() {

        txtStartTimeLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Calendar currentTime = Calendar.getInstance();
                int hour = currentTime.get(Calendar.HOUR_OF_DAY);
                int minute = currentTime.get(Calendar.MINUTE);
//                if (mInspStartTimeHour > -1
//                        && mInspStartTimeMinute > -1) {
//                    hour = mInspStartTimeHour;
//                    minute = mInspStartTimeMinute;
//                }
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(POItemListActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        mInspStartTimeHour = selectedHour;
                        mInspStartTimeMinute = selectedMinute;
                        txtStartTimeLevel.setText(GenUtils.get12HourTimeFormatStr(selectedHour, selectedMinute));

                        String hh = "" + selectedHour;
                        String mm = "" + selectedMinute;
                        if (selectedHour < 10) {
                            hh = "0" + selectedHour;
                        }
                        if (selectedMinute < 10) {
                            mm = "0" + selectedMinute;
                        }
                        inspectionModal.InspStartTime = hh + ":" + mm;
//                        _visitTime = "T" + hh + ":" + mm + ":00";


                    }
                }, hour, minute, true);

                mTimePicker.show();
            }
        });
    }

    private void handleArrivalTime() {

        txtArrivalTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Calendar currentTime = Calendar.getInstance();
                int hour = currentTime.get(Calendar.HOUR_OF_DAY);
                int minute = currentTime.get(Calendar.MINUTE);
//                if (mArrivalTimeHour > -1
//                        && mArrivalTimeMinute > -1) {
//                    hour = mArrivalTimeHour;
//                    minute = mArrivalTimeMinute;
//                }
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(POItemListActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        mArrivalTimeHour = selectedHour;
                        mArrivalTimeMinute = selectedMinute;
                        txtArrivalTime.setText(GenUtils.get12HourTimeFormatStr(selectedHour, selectedMinute));

                        String hh = "" + selectedHour;
                        String mm = "" + selectedMinute;
                        if (selectedHour < 10) {
                            hh = "0" + selectedHour;
                        }
                        if (selectedMinute < 10) {
                            mm = "0" + selectedMinute;
                        }
                        inspectionModal.ArrivalTime = hh + ":" + mm;

//                        _visitTime = "T" + hh + ":" + mm + ":00";


                    }
                }, hour, minute, true);

                mTimePicker.show();

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == FEnumerations.REQUEST_FOR_ADD_INTIMATION) {
            UpdateToRefreshUI();
        }
        if (requestCode == FEnumerations.RESULT_PO_ITEM) {
            UpdateToRefreshUI();
        }
    }

    private void UpdateToRefreshUI() {
        FslLog.d(TAG, "UPDATE TO REFRESH..........................");
        List<POItemDtl> list = POItemDtlHandler.getItemList(this, pRowID);
        if (list != null) {
            if (pOItemDtlList != null)
                pOItemDtlList.clear();
            else pOItemDtlList = new ArrayList<>();

            pOItemDtlList.addAll(list);
        }

        NetworkUtil.hideSoftKeyboard(POItemListActivity.this);
//        inspectionAdaptor.update(FEnumerations.VIEW_TYPE_ITEM_QUALITY);
//        setAdaptor();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnGoToPoDetail:
                showSecondScreen();
                break;
           /* case R.id.qualityTab:
//                if (inspectionAdaptor != null) {
//
////                    inspectionAdaptor.update(FEnumerations.VIEW_TYPE_ITEM_QUALITY);
//                }


                break;*/


            case R.id.txtInspectionLevel:
                handleInspectionLevelSpinner();
                break;

            case R.id.txtQualityLevel:
                handleQualityLevelSpinner();
                break;

            case R.id.txtQualityLevelMinor:
                handleQualityLevelMinorSpinner();
                break;

            case R.id.txtTypeofInspection:
                handleTypeofInspectionSpinner();
                break;

            case R.id.txtStatus:
                handleStatusSpinner();
                break;
            case R.id.txtLocation:
                handleInsLocationSpinner();

                break;

        }
    }


    private void handleInsLocationSpinner() {
        final List<String> list = new ArrayList<String>();
//        final String insp = "AQL M-";
//        for (int i = 0; i < 5; i++) {
        list.add(inspectionModal.Factory);
//        }
        GenUtils.showListDialog(POItemListActivity.this,
                "Select Location", list,
                new GenUtils.ListDialogClickListener() {
                    @Override
                    public void onItemSelectedPos(int selectedItem) {

                        txtLocation.setText(list.get(selectedItem));
                    }

                    @Override
                    public void onCancel() {

                    }
                });

    }

    private void handleStatusSpinner() {
        final List<SysData22Modal> statusList = SysData22Handler.getSysData22List(this, FEnumerations.E_STATUS_GEN_ID);
        if (statusList != null && statusList.size() > 0) {

            List<String> list = new ArrayList<String>();

            for (int i = 0; i < statusList.size(); i++) {
                list.add(statusList.get(i).MainDescr);
            }
            GenUtils.showListDialog(POItemListActivity.this,
                    "Select Status", list,
                    new GenUtils.ListDialogClickListener() {
                        @Override
                        public void onItemSelectedPos(int selectedItem) {

                            txtStatus.setText(statusList.get(selectedItem).MainDescr);
                            pRowIdOfStatus = statusList.get(selectedItem).MainID;
                            inspectionModal.Status = pRowIdOfStatus;
                        }

                        @Override
                        public void onCancel() {

                        }
                    });
        }
    }

    private void handleTypeofInspectionSpinner() {
        List<String> list = new ArrayList<String>();
        final String insp = "Inspection-";
        for (int i = 0; i < 2; i++) {
            list.add(insp + i);
        }
        GenUtils.showListDialog(POItemListActivity.this,
                "Select Type Of Inspection", list,
                new GenUtils.ListDialogClickListener() {
                    @Override
                    public void onItemSelectedPos(int selectedItem) {

                        txtTypeofInspection.setText(insp + selectedItem);
                    }

                    @Override
                    public void onCancel() {

                    }
                });
    }

    private void handleQualityLevelMinorSpinner() {
        final List<QualityLevelModal> qualityLevelList = QualityLevelHandler.getQualityLevelList(this);
        if (qualityLevelList != null && qualityLevelList.size() > 0) {
            List<String> list = new ArrayList<String>();

            for (int i = 0; i < qualityLevelList.size(); i++) {
                list.add(qualityLevelList.get(i).QualityLevel);
            }
            GenUtils.showListDialog(POItemListActivity.this,
                    "Select Quality Level Minor", list,
                    new GenUtils.ListDialogClickListener() {
                        @Override
                        public void onItemSelectedPos(int selectedItem) {
                            txtQualityLevelMinor.setText(qualityLevelList.get(selectedItem).QualityLevel);
                            pRowIdOfQualityMinorLevel = qualityLevelList.get(selectedItem).pRowID;

                            inspectionModal.QLMinor = pRowIdOfQualityMinorLevel;

                            inspectionModal.QLMinorDescr = qualityLevelList.get(selectedItem).QualityLevel;

                            onChangeInspectionLevel();
                        }

                        @Override
                        public void onCancel() {

                        }
                    });
        }
    }

    private void handleQualityLevelSpinner() {
        final List<QualityLevelModal> qualityLevelList = QualityLevelHandler.getQualityLevelList(this);
        if (qualityLevelList != null && qualityLevelList.size() > 0) {
            txtQualityLevel.setText(qualityLevelList.get(0).QualityLevel);
            List<String> list = new ArrayList<String>();

            for (int i = 0; i < qualityLevelList.size(); i++) {
                list.add(qualityLevelList.get(i).QualityLevel);
            }
            GenUtils.showListDialog(POItemListActivity.this,
                    "Select Quality Level", list,
                    new GenUtils.ListDialogClickListener() {
                        @Override
                        public void onItemSelectedPos(int selectedItem) {

                            txtQualityLevel.setText(qualityLevelList.get(selectedItem).QualityLevel);
                            pRowIdOfQualityMajorLevel = qualityLevelList.get(selectedItem).pRowID;
                            inspectionModal.QLMajor = pRowIdOfQualityMajorLevel;
                            inspectionModal.QLMajorDescr = qualityLevelList.get(selectedItem).QualityLevel;
                            onChangeInspectionLevel();
                        }

                        @Override
                        public void onCancel() {

                        }
                    });
        }
    }

    private void handleInspectionLevelSpinner() {
        final List<InsLvHdrModal> insLvHdrModals = InsLvHdrHandler.getInsLvHdrList(this);
        if (insLvHdrModals != null && insLvHdrModals.size() > 0) {

            List<String> list = new ArrayList<String>();

            for (int i = 0; i < insLvHdrModals.size(); i++) {
                list.add(insLvHdrModals.get(i).InspAbbrv);
            }
            GenUtils.showListDialog(POItemListActivity.this,
                    "Select Inspection Level", list,
                    new GenUtils.ListDialogClickListener() {
                        @Override
                        public void onItemSelectedPos(int selectedItem) {

                            txtInspectionLevel.setText(insLvHdrModals.get(selectedItem).InspAbbrv);
                            pRowIdOfInspectLevel = insLvHdrModals.get(selectedItem).pRowID;

                            inspectionModal.InspectionLevel = pRowIdOfInspectLevel;
                            inspectionModal.InspectionLevelDescr = insLvHdrModals.get(0).InspAbbrv;
                            onChangeInspectionLevel();
                        }

                        @Override
                        public void onCancel() {

                        }
                    });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);//Menu Resource, Menu


//        }

        return true;
    }


    private void updateMenu() {

        invalidateOptionsMenu();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            handleToGoBackActivity();


        }
        return super.onKeyDown(keyCode, event);
    }

    private void handleToGoBackActivity() {
        handleTosave();
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onBackPressed() {
        handleToGoBackActivity();
        super.onBackPressed();
    }

    private void showFirstScreen() {
        NetworkUtil.hideSoftKeyboard(POItemListActivity.this);
        updateMenu();
    }

    private void showSecondScreen() {
        if (pRowIdOfInspectLevel != null
                && pRowIdOfQualityMinorLevel != null
                && pRowIdOfQualityMajorLevel != null) {

            vMenuType = FEnumerations.E_MENU_PO_VIEW_TYPE_DTL;
            updateMenu();
//            handleUpdateInspHdr();
//            handleUpdateData();
            handleTosave();

            NetworkUtil.hideSoftKeyboard(POItemListActivity.this);
            Intent intent = new Intent(POItemListActivity.this, POItemTabActivity.class);
            intent.putExtra("detail", GenUtils.serializeInspectionModal(inspectionModal));
//            intent.putExtra("poList", GenUtils.serializePOItemDtlModeList(pOItemDtlList));
            startActivityForResult(intent, FEnumerations.RESULT_PO_ITEM);
        }
//        setAdaptor();


    }

    private void handleONsave() {

        handleUpdateInspHdrOnSave();
        handleUpdateData();
    }

    private void handleTosave() {

        handleUpdateInspHdr();
        handleUpdateData();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        switch (item.getItemId()) {
            case android.R.id.home:

//                handleTosave();
                handleToGoBackActivity();
                return false;

            case R.id.submitHdr:
//                handleUpdateInspHdr();
//                handleUpdateData();

                handleONsave();
                ToastCompat toast = ToastCompat.makeText(this, "update data successfully", Toast.LENGTH_SHORT);
                GenUtils.safeToastShow(TAG, POItemListActivity.this, toast);
                return true;
        }

        return false;
    }


    public void onChangeInspectionLevel() {
        if (pRowIdOfInspectLevel != null) {
            for (int i = 0; i < pOItemDtlList.size(); i++) {

                if (inspectionModal.QLMinor.equals("DEL0000013") || inspectionModal.QLMajor.equals("DEL0000013") || inspectionModal.ActivityID.equals("SYS0000001")) {
                    String[] toInspDtl = POItemDtlHandler.getToInspect(this, pRowIdOfInspectLevel, pOItemDtlList.get(i).AvailableQty);
                    if (toInspDtl != null) {
                        pOItemDtlList.get(i).SampleSizeInspection = null/* + "  " + toInspDtl[2]*/;
                        pOItemDtlList.get(i).InspectedQty = 0;
                        pOItemDtlList.get(i).AllowedinspectionQty = 0;
                        String sampleCode = toInspDtl[0];

//                        String[] minorDefect = POItemDtlHandler.getDefectAccepted(this, pRowIdOfQualityMinorLevel, sampleCode);
//                        if (minorDefect != null && minorDefect.length > 0) {
                        pOItemDtlList.get(i).MinorDefectsAllowed = 0;
//                        }

//                        String[] majorDefect = POItemDtlHandler.getDefectAccepted(this, pRowIdOfQualityMajorLevel, sampleCode);
//                        if (majorDefect != null && majorDefect.length > 0) {
                        pOItemDtlList.get(i).MajorDefectsAllowed = 0;
//                        }
                    }
                } else {

                    String[] toInspDtl = POItemDtlHandler.getToInspect(this, pRowIdOfInspectLevel, pOItemDtlList.get(i).AvailableQty);
                    if (toInspDtl != null) {
                        pOItemDtlList.get(i).SampleSizeInspection = toInspDtl[1]/* + "  " + toInspDtl[2]*/;
                        pOItemDtlList.get(i).InspectedQty = Integer.parseInt(toInspDtl[2]);
                        pOItemDtlList.get(i).AllowedinspectionQty = Integer.parseInt(toInspDtl[2]);
                        String sampleCode = toInspDtl[0];

                        String[] minorDefect = POItemDtlHandler.getDefectAccepted(this, pRowIdOfQualityMinorLevel, sampleCode);
                        if (minorDefect != null && minorDefect.length > 0) {
                            pOItemDtlList.get(i).MinorDefectsAllowed = Integer.parseInt(minorDefect[1]);
                        } else {
                            pOItemDtlList.get(i).MinorDefectsAllowed = 0;
                        }

                        String[] majorDefect = POItemDtlHandler.getDefectAccepted(this, pRowIdOfQualityMajorLevel, sampleCode);
                        if (majorDefect != null && majorDefect.length > 0) {
                            pOItemDtlList.get(i).MajorDefectsAllowed = Integer.parseInt(majorDefect[1]);
                        } else {
                            pOItemDtlList.get(i).MajorDefectsAllowed = 0;
                        }
                    }
                }
            }
        }
    }


    private void handleUpdateInspHdr() {
        String _remark = editPoDetailRemark.getText().toString();
        inspectionModal.VendorContact = vendorContactPerson.getText().toString();
        if (!TextUtils.isEmpty(_remark)) {
            inspectionModal.Comments = _remark;
        }

        if (!TextUtils.isEmpty(inspectionModal.Status) && !inspectionModal.Status.equals("null")) {
            int mStatus = Integer.parseInt(inspectionModal.Status.trim());
            if (mStatus == 0 || mStatus == 10 || mStatus == 30) {
                boolean status = InspectionListHandler.updatePOItemhdr(this, inspectionModal);
                if (status) {
//                    ToastCompat.makeText(this, "update data successfully", Toast.LENGTH_SHORT).show();
                }
            }
            /*if (mStatus >= 40)*/
            else {
               /* Intent intent = new Intent(POItemListActivity.this, IntimationActivity.class);
                intent.putExtra("detail", GenUtils.serializeInspectionModal(inspectionModal));
                startActivityForResult(intent, FEnumerations.REQUEST_FOR_ADD_INTIMATION);*/
                boolean status = InspectionListHandler.updatePOItemhdr(this, inspectionModal);
                if (status) {
//                    ToastCompat.makeText(this, "update data successfully", Toast.LENGTH_SHORT).show();
                }

            }
        } else {
//            ToastCompat.makeText(POItemListActivity.this, "Select Status", Toast.LENGTH_SHORT).show();
        }


    }

    private void handleUpdateInspHdrOnSave() {
        String _remark = editPoDetailRemark.getText().toString();
        inspectionModal.VendorContact = vendorContactPerson.getText().toString();
        if (!TextUtils.isEmpty(_remark)) {
            inspectionModal.Comments = _remark;
        }

        if (!TextUtils.isEmpty(inspectionModal.Status) && !inspectionModal.Status.equals("null")) {
            int mStatus = Integer.parseInt(inspectionModal.Status.trim());
            if (mStatus == 0 || mStatus == 10 || mStatus == 30) {
                boolean status = InspectionListHandler.updatePOItemhdr(this, inspectionModal);
                if (status) {
//                    ToastCompat.makeText(this, "update data successfully", Toast.LENGTH_SHORT).show();
                }
            }
            /*if (mStatus >= 40)*/
            else {
                Intent intent = new Intent(POItemListActivity.this, IntimationActivity.class);
                intent.putExtra("detail", GenUtils.serializeInspectionModal(inspectionModal));
                startActivityForResult(intent, FEnumerations.REQUEST_FOR_ADD_INTIMATION);
                boolean status = InspectionListHandler.updatePOItemhdr(this, inspectionModal);
                if (status) {
//                    ToastCompat.makeText(this, "update data successfully", Toast.LENGTH_SHORT).show();
                }

            }
        } else {
//            ToastCompat.makeText(POItemListActivity.this, "Select Status", Toast.LENGTH_SHORT).show();
        }


    }

    private void handleUpdateData() {
        boolean status = false;
        for (int i = 0; i < pOItemDtlList.size(); i++) {
            status = POItemDtlHandler.updatePOItemHdrOnInspection(this, pOItemDtlList.get(i));
            status = POItemDtlHandler.updatePOItemDtlOnInspection(this, pOItemDtlList.get(i));
        }
    }

    private void handlePRoductionStatus() {
        _productionStatusRemark = editProductionStatus.getText().toString();
        if (TextUtils.isEmpty(_productionStatusDate)) {
            Toast toast = ToastCompat.makeText(POItemListActivity.this, "Pickup date", Toast.LENGTH_SHORT);
            GenUtils.safeToastShow(TAG, POItemListActivity.this, toast);
            return;
        }
        if (TextUtils.isEmpty(_productionStatusRemark)) {
            editProductionStatus.setError("Remark");
            editProductionStatus.requestFocus();
            Toast toast = ToastCompat.makeText(POItemListActivity.this, "Enter remark", Toast.LENGTH_SHORT);
            GenUtils.safeToastShow(TAG, POItemListActivity.this, toast);
            return;
        }
        ItemInspectionDetailHandler.updateCompletionProductionStatus(POItemListActivity.this, _productionStatusRemark, _productionStatusDate, inspectionModal);
        Toast toast = ToastCompat.makeText(this, "update data successfully", Toast.LENGTH_SHORT);
        GenUtils.safeToastShow(TAG, POItemListActivity.this, toast);
    }


    private void showProgressDialog(String message) {

        if (loadingDialog == null)
            loadingDialog = new ProgressDialog(POItemListActivity.this);
//        loadingDialog.setTitle("Please wait");
        loadingDialog.setMessage(message);
        if (loadingDialog != null && !loadingDialog.isShowing()) {
//            loadingDialog.show();
            GenUtils.safeProgressDialogShow(TAG, POItemListActivity.this, loadingDialog);
        }

    }

    private void hideDialog() {
        if (loadingDialog != null && loadingDialog.isIndeterminate() && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }

    }
}
