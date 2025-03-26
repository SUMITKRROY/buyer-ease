package com.hologram;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.buyereasefsl.R;

import com.buyereasefsl.SlideshowDialogFragment;
import com.constant.FClientConfig;
import com.constant.FEnumerations;
import com.data.UserSession;
import com.google.android.material.textfield.TextInputLayout;
import com.util.DateUtils;
import com.util.FslLog;
import com.util.GenUtils;
import com.util.MultipleImageHandler;
import com.util.SetInitiateStaticVariable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HologramHandler extends AppCompatActivity implements View.OnClickListener, TextWatcher, HologramImageAdapter.CallBackClick {

    EditText editHologramNo;
    private TextInputLayout input_layout_no;
    private Button btnSubmit;
    LinearLayout hologramEstablishDateContainer, hologramExpiryDateContainer;
    TextView txtHologramEstablishDate, txtHologramExpiryDate, txtPickImage, txtCount, txtSelectedHologram;
    String _hologramEstablishDate, _hologramExpiryDate;
    int fYear, fMonth, fDay, mYear, mMonth, mDay;
    String TAG = "HologramHandler";

    HologramModal hologramModal;
    List<HologramModal> lSelectedStyle = null;
    ArrayList<String> imageList = new ArrayList<>();
    ProgressDialog loadingDialog;
    int mViewType = -1;
    RecyclerView recyclerViewImages;
    private HologramImageAdapter mImageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hologram_form);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView companyName = (TextView) findViewById(R.id.companyName);
        String st = GenUtils.truncate(new UserSession(HologramHandler.this).getCompanyName(), FClientConfig.COMPANY_TRUNC_LENGTH);
        companyName.setText(st);
        SetInitiateStaticVariable.setInitiateStaticVariable(HologramHandler.this);
        String str = null;
        if (getIntent().getBooleanExtra("IsMultiple", false)) {
            mViewType = 2;
            lSelectedStyle = GenUtils.deSerializeHologramList(getIntent().getStringExtra("list"));
            hologramModal = lSelectedStyle.get(0);
            for (int i = 0; i < lSelectedStyle.size(); i++) {
                if (i == 0) {
                    if (!TextUtils.isEmpty(lSelectedStyle.get(i).code) && !"null".equalsIgnoreCase(lSelectedStyle.get(i).code)) {
                        str = lSelectedStyle.get(i).code;
                    }
                } else {
                    if (TextUtils.isEmpty(str)) {
                        if (!TextUtils.isEmpty(lSelectedStyle.get(i).code) && !"null".equalsIgnoreCase(lSelectedStyle.get(i).code)) {
                            str = lSelectedStyle.get(i).code;
                        }
                    } else {
                        str = str + ", " + lSelectedStyle.get(i).code;
                    }
                }
            }
        } else {
            mViewType = 1;
            hologramModal = GenUtils.deSerializeStyle(getIntent().getStringExtra("style"));
            str = hologramModal.code;
        }
//        if (!TextUtils.isEmpty(str)) {
//            getSupportActionBar().setTitle("" + str);
//        } else {
        getSupportActionBar().setTitle("Update Hologram");
//        }

        hologramEstablishDateContainer = (LinearLayout) findViewById(R.id.hologramEstablishDateContainer);
        hologramExpiryDateContainer = (LinearLayout) findViewById(R.id.hologramExpiryDateContainer);
        hologramEstablishDateContainer.setOnClickListener(this);
        hologramExpiryDateContainer.setOnClickListener(this);
        input_layout_no = (TextInputLayout) findViewById(R.id.input_layout_no);
        txtSelectedHologram = (TextView) findViewById(R.id.txtSelectedHologram);
        if (!TextUtils.isEmpty(str)) {
            txtSelectedHologram.setText("You have selected style ( " + str + " ) to update hologram");
            txtSelectedHologram.setVisibility(View.VISIBLE);
        } else {
            txtSelectedHologram.setVisibility(View.GONE);
        }
        editHologramNo = (EditText) findViewById(R.id.editHologramNo);
        editHologramNo.addTextChangedListener(this);
        txtHologramEstablishDate = (TextView) findViewById(R.id.txtHologramEstablishDate);
        txtHologramExpiryDate = (TextView) findViewById(R.id.txtHologramExpiryDate);
        txtPickImage = (TextView) findViewById(R.id.txtPickImage);
        txtCount = (TextView) findViewById(R.id.txtCount);
        txtPickImage.setOnClickListener(this);
        txtCount.setOnClickListener(this);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);


        recyclerViewImages = (RecyclerView) findViewById(R.id.recyclerViewImages);
        recyclerViewImages.setHasFixedSize(true);
        LinearLayoutManager cmLayoutManager = new LinearLayoutManager(HologramHandler.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewImages.setLayoutManager(cmLayoutManager);


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();
            }
        });

        /*if (hologramModal == null) {
            FslLog.d(TAG, " STYLE OBJECT IS NULL WHY ?????????????????????? ");
            Toast.makeText(HologramHandler.this, "Couldn't not find style", Toast.LENGTH_SHORT).show();
            finish();
        }*/
//        if (mViewType == 1) {
        updateUI();
        updateImageUi();
//        }

    }

    private void updateUI() {
        if (hologramModal != null) {
            if (!TextUtils.isEmpty(hologramModal.hologram_no) && !"null".equalsIgnoreCase(hologramModal.hologram_no)) {
                editHologramNo.setText(hologramModal.hologram_no);
            }
            if (!TextUtils.isEmpty(hologramModal.hologram_expiry_date) && !"null".equalsIgnoreCase(hologramModal.hologram_expiry_date)) {
                txtHologramExpiryDate.setText(DateUtils.getStringDateToTodayAlert(hologramModal.hologram_expiry_date));
                _hologramExpiryDate = DateUtils.getStringDateToTodayAlert(hologramModal.hologram_expiry_date);
            }
            if (!TextUtils.isEmpty(hologramModal.hologram_establish_date) && !"null".equalsIgnoreCase(hologramModal.hologram_establish_date)) {
                txtHologramEstablishDate.setText(DateUtils.getStringDateToTodayAlert(hologramModal.hologram_establish_date));
                _hologramEstablishDate = DateUtils.getStringDateToTodayAlert(hologramModal.hologram_establish_date);
            }

            if (hologramModal.imagesList != null && hologramModal.imagesList.size() > 0) {
                imageList.addAll(hologramModal.imagesList);
            }
            updateImageCount();
        }
    }

    private void updateImageUi() {

        if (mImageAdapter == null) {
            mImageAdapter = new HologramImageAdapter(HologramHandler.this,
                    recyclerViewImages, 0,
                    imageList, this);
            recyclerViewImages.setAdapter(mImageAdapter);
        } else {
            mImageAdapter.notifyDataSetChanged();
        }
        if (imageList != null && imageList.size() > 0) {
            findViewById(R.id.imageContainer).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.imageContainer).setVisibility(View.GONE);
        }

    }

    private void submitForm() {
        if (!validateName()) {
            return;
        }
        if (TextUtils.isEmpty(_hologramEstablishDate)) {
            Toast.makeText(HologramHandler.this, "Select Hologram Establish Date", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(_hologramExpiryDate)) {
            Toast.makeText(HologramHandler.this, "Select Hologram Expiry Date", Toast.LENGTH_SHORT).show();
            return;
        }
        HologramModal mHologramModal = new HologramModal();
        mHologramModal.hologram_no = editHologramNo.getText().toString();
        mHologramModal.hologram_establish_date = _hologramEstablishDate;
        mHologramModal.hologram_expiry_date = _hologramExpiryDate;
        showProgressDialog("Submitting...");
        Thread t = new Thread(new Runnable() {
            public void run() {
                if (mViewType == 1 && hologramModal != null) {
                    StyleHandler.updateHologram(HologramHandler.this, mHologramModal, hologramModal.ItemID);
                    handleUi(true);
                } else if (mViewType == 2 && lSelectedStyle != null && lSelectedStyle.size() > 0) {
                    StyleHandler.updateMultipleHologram(HologramHandler.this, mHologramModal, lSelectedStyle);
                    handleUi(true);
                } else {
                    handleUi(false);
                }

            }
        });
        t.start();

    }

    private void handleUi(boolean status) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (status) {
                    Toast.makeText(getApplicationContext(), "Style updated successfully.", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();
                    hideDialog();
                } else {
                    hideDialog();
                    Toast.makeText(getApplicationContext(), "Couldn't add hologram", Toast.LENGTH_SHORT).show();
                }
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
                return false;
        }
        return false;
    }

    private boolean validateName() {
        if (editHologramNo.getText().toString().trim().isEmpty()) {
            input_layout_no.setError(getString(R.string.enter_hologram));
            requestFocus(editHologramNo);
            return false;
        } else {
            input_layout_no.setErrorEnabled(false);
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.hologramEstablishDateContainer:
                handleEstablishDate();
                break;
            case R.id.hologramExpiryDateContainer:
                handleExpiryDate();
                break;
            case R.id.txtPickImage:
                handleToPickImage();
                break;
            case R.id.txtCount:
                handleToShowSelectedImage();
                break;
        }
    }

    private void handleToShowSelectedImage() {
        if (hologramModal.imagesList != null && hologramModal.imagesList.size() > 0) {
            ArrayList<String> list = new ArrayList<>();
            for (int i = 0; i < hologramModal.imagesList.size(); i++) {
                list.add(hologramModal.imagesList.get(i));
            }
            if (list.size() > 0) {
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("galleryModels", list);
                bundle.putInt("position", 0);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                SlideshowDialogFragment newFragment = SlideshowDialogFragment.newInstance();
                newFragment.setArguments(bundle);
                newFragment.show(ft, "slideshow");
            }
        }
    }

    private void handleToPickImage() {
        String valueToReturn = R.id.txtPickImage + "";
        MultipleImageHandler.showDialog(HologramHandler.this, MultipleImageHandler.DOCUMENT, valueToReturn);
    }

    private void ImageAgainHandler() {
        String valueToReturn2 = R.id.txtPickImage + "";
        MultipleImageHandler.showDialog(HologramHandler.this, MultipleImageHandler.DOCUMENT, valueToReturn2, MultipleImageHandler.pickerViewer);


    }

    private void handleExpiryDate() {
        Calendar calendar = Calendar.getInstance();
        final Date today = calendar.getTime();
        FslLog.d(TAG, "today:    " + today);
        if (mYear == 0)
            mYear = calendar.get(Calendar.YEAR);

        if (mMonth == 0)
            mMonth = calendar.get(Calendar.MONTH);

        if (mDay == 0)
            mDay = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(HologramHandler.this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
//store previous selected value and then show same date
//on reopening calendar
                        mYear = year;
                        mMonth = monthOfYear;
                        mDay = dayOfMonth;
                        // set day of month , month and year value in the edit text
                        String getDate = dayOfMonth + "/"
                                + (monthOfYear + 1) + "/" + year;
                        String hr = year + ":" + (monthOfYear + 1) + ":" + dayOfMonth;

                        SimpleDateFormat getFormatDate = new SimpleDateFormat("dd/MM/yyyy");
                        SimpleDateFormat myFormat = new SimpleDateFormat("dd-MMM-yyyy");
                        try {
                            String reformattedStr = myFormat.format(getFormatDate.parse(getDate));
//                            DateUtils.getDate()
                            _hologramExpiryDate = reformattedStr;//getDate;

                            txtHologramExpiryDate.setText(reformattedStr);

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMinDate(today.getTime());
//        datePickerDialog.getDatePicker().setMaxDate(today.getTime());
        datePickerDialog.show();
    }

    private void handleEstablishDate() {
        Calendar calendar = Calendar.getInstance();
        final Date today = calendar.getTime();
        FslLog.d(TAG, "today:    " + today);
        if (fYear == 0)
            fYear = calendar.get(Calendar.YEAR);

        if (fMonth == 0)
            fMonth = calendar.get(Calendar.MONTH);

        if (fDay == 0)
            fDay = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(HologramHandler.this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
//store previous selected value and then show same date
//on reopening calendar
                        fYear = year;
                        fMonth = monthOfYear;
                        fDay = dayOfMonth;
                        // set day of month , month and year value in the edit text
                        String getDate = dayOfMonth + "/"
                                + (monthOfYear + 1) + "/" + year;
                        String hr = year + ":" + (monthOfYear + 1) + ":" + dayOfMonth;

                        SimpleDateFormat getFormatDate = new SimpleDateFormat("dd/MM/yyyy");
                        SimpleDateFormat myFormat = new SimpleDateFormat("dd-MMM-yyyy");
                        try {
                            String reformattedStr = myFormat.format(getFormatDate.parse(getDate));
//                            DateUtils.getDate()
                            _hologramEstablishDate = reformattedStr;//getDate;

                            txtHologramEstablishDate.setText(reformattedStr);

                            if (TextUtils.isEmpty(_hologramExpiryDate)) {
                                int nextY = year + 1;
                                String getExpDate = dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + nextY;
                                String expiryDateStr = myFormat.format(getFormatDate.parse(getExpDate));
                                _hologramExpiryDate = expiryDateStr;
                                txtHologramExpiryDate.setText(expiryDateStr);
                            }


                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }, fYear, fMonth, fDay);
//        datePickerDialog.getDatePicker().setMinDate(today.getTime());
        datePickerDialog.getDatePicker().setMaxDate(today.getTime());
        datePickerDialog.show();
    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        validateName();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        FslLog.d(TAG, "onActivityResult requestCode - " + requestCode + " resultCode - " + resultCode);

        MultipleImageHandler.onActivityResult(HologramHandler.this,
                requestCode, resultCode, data, new MultipleImageHandler.GetBitmap() {
                    @Override
                    public void onGetBitamp(Bitmap serverBitmap,
                                            ArrayList<String> imagePathArrayList,
                                            String valueReturned,
                                            boolean isGallery) {
                        int resId = Integer.valueOf(valueReturned);
                        imageList.addAll(imagePathArrayList);
                        if (mViewType == 1 && hologramModal != null) {
                            StyleHandler.insertImage(HologramHandler.this, imagePathArrayList, FEnumerations.E_IMAGE_EXTN, hologramModal.ItemID);
                        } else if (mViewType == 2 && lSelectedStyle != null && lSelectedStyle.size() > 0) {
                            StyleHandler.insertMultipleStyleImage(HologramHandler.this, imagePathArrayList, FEnumerations.E_IMAGE_EXTN, lSelectedStyle);
                        }

                        updateImageCount();
                        if(!isGallery){
                            ImageAgainHandler();
                        }
                        updateImageUi();

                    }

                });

    }

    private void updateImageCount() {
        txtCount.setText(imageList.size() + "");
    }

    private void showProgressDialog(String message) {

        if (loadingDialog == null)
            loadingDialog = new ProgressDialog(HologramHandler.this);
//        loadingDialog.setTitle("Please wait");
        loadingDialog.setMessage(message);
        if (loadingDialog != null && !loadingDialog.isShowing()) {
//            loadingDialog.show();
            GenUtils.safeProgressDialogShow(TAG, HologramHandler.this, loadingDialog);
        }

    }

    private void hideDialog() {
        if (loadingDialog != null && loadingDialog.isIndeterminate() && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }

    }

    @Override
    public void onRemove(String path, int position) {
        if (imageList != null) {
            if (lSelectedStyle != null && lSelectedStyle.size() > 0) {
                StyleHandler.deleteMultipleImage(HologramHandler.this, path, lSelectedStyle);
            } else if (hologramModal != null) {
                StyleHandler.deleteImage(HologramHandler.this, path, hologramModal.ItemID);
            }

            imageList.remove(path);
            updateImageCount();
            updateImageUi();
        }
    }
}
