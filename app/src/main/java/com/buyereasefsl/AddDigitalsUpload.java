package com.buyereasefsl;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.widget.Toolbar;
import com.constant.FEnumerations;
import com.podetail.POItemDtl;
import com.util.FslLog;
import com.util.GenUtils;
import com.util.MultipleImageHandler;

import java.util.ArrayList;
import java.util.List;

import me.drakeet.support.toast.ToastCompat;

/**
 * Created by ADMIN on 12/22/2017.
 */

public class AddDigitalsUpload extends AppCompatActivity implements View.OnClickListener {

    int pos;
    String data;
    EditText editTitle, editDropDownTitle;
    //    LinearLayout digitalImageContainer;
    ImageView digitalImage;
    //    WorkManShipModel mWorkManShipModel;
    DigitalsUploadModal digitalsUploadModal = new DigitalsUploadModal();
    POItemDtl poItemDtl;
    String _selectedTitle = null;
    TextView txtSelected;
    LinearLayout workmanshipImage1Container;
    String TAG = "AddDigitalsUpload";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_digitals_upload_form);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add Digitals");
        editDropDownTitle = (EditText) findViewById(R.id.editDropDownTitle);
        editTitle = (EditText) findViewById(R.id.editTitle);
//        editDescription = (EditText) findViewById(R.id.editDescription);
        txtSelected = (TextView) findViewById(R.id.txtSelected);
        poItemDtl = GenUtils.deSerializePOItemModal(getIntent().getStringExtra("detail"));

//        digitalImageContainer = (LinearLayout) findViewById(R.id.digitalImageContainer);
        workmanshipImage1Container = (LinearLayout) findViewById(R.id.workmanshipImage1Container);
        digitalImage = (ImageView) findViewById(R.id.digitalImage);

        List<DigitalsUploadModal> drpList = ItemInspectionDetailHandler.getImageTitle(AddDigitalsUpload.this, poItemDtl.QRHdrID, poItemDtl.QRPOItemHdrID);

        final List<String> list = new ArrayList<String>();
        if (drpList != null && drpList.size() > 0) {
            findViewById(R.id.titleWrapper).setVisibility(View.GONE);
            editDropDownTitle.setText(drpList.get(0).title);
            _selectedTitle = drpList.get(0).title;
            for (int i = 0; i < drpList.size(); i++) {
                list.add(drpList.get(i).title);
                if (i == drpList.size() - 1) {
                    list.add("Other");
                }
            }
        } else {
            editDropDownTitle.setVisibility(View.GONE);
        }
        editDropDownTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GenUtils.showListDialog(AddDigitalsUpload.this,
                        "Select Title", list,
                        new GenUtils.ListDialogClickListener() {
                            @Override
                            public void onItemSelectedPos(int selectedItem) {
                                editDropDownTitle.setText(list.get(selectedItem));
                                if (selectedItem == list.size() - 1) {
                                    _selectedTitle = null;
                                    findViewById(R.id.titleWrapper).setVisibility(View.VISIBLE);
                                } else {
                                    _selectedTitle = list.get(selectedItem);
                                    findViewById(R.id.titleWrapper).setVisibility(View.GONE);
                                }
                            }

                            @Override
                            public void onCancel() {

                            }
                        });
            }
        });


        digitalImage.setOnClickListener(this);

        workmanshipImage1Container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (digitalsUploadModal.imageArray != null && digitalsUploadModal.imageArray.size() > 0) {

                    ArrayList<String> list = new ArrayList<>();
                    for (int i = 0; i < digitalsUploadModal.imageArray.size(); i++) {
                        list.add(digitalsUploadModal.imageArray.get(i));
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
                if (digitalsUploadModal.imageArray.size() > 0
                        ) {
                    onSubmit();
                } else {
                    ToastCompat toast = ToastCompat.makeText(this, "No digitals upload", Toast.LENGTH_SHORT);
                    GenUtils.safeToastShow(TAG, AddDigitalsUpload.this, toast);
                }
                return true;


        }

        return false;
    }

    private void onSubmit() {
        if (TextUtils.isEmpty(_selectedTitle)) {
            _selectedTitle = editTitle.getText().toString();
        }
        if (TextUtils.isEmpty(_selectedTitle)) {
            Toast toast = ToastCompat.makeText(AddDigitalsUpload.this, "Enter title", Toast.LENGTH_SHORT);
            GenUtils.safeToastShow(TAG, AddDigitalsUpload.this, toast);
            return;
        }
//        String _description = editDescription.getText().toString();

//        if (TextUtils.isEmpty(_title)) {
//            editTitle.setError("Title");
//            editTitle.requestFocus();
//            return;
//        }
//        if (TextUtils.isEmpty(_description)) {
//            editDescription.setError("Description");
//            editDescription.requestFocus();
//            return;
//        }


        digitalsUploadModal.title = _selectedTitle;
//        digitalsUploadModal.Description = _description;
//        digitalsUploadModal.pRowID = ItemInspectionDetailHandler.GeneratePK(AddDigitalsUpload.this, FEnumerations.E_TABLE_NAME_QRPOItemDtl_Image);
        Intent intent = new Intent();
//        intent.putExtra("pos", pos);
        intent.putExtra("detail", GenUtils.serializeDigitalModal(digitalsUploadModal));
        setResult(RESULT_OK, intent);
        finish();

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.digitalImage:
                ImageHandler();
                break;


        }
    }

    private void ImageHandler() {

        String valueToReturn = R.id.workmanshipImage1 + "";
        MultipleImageHandler.showDialog(AddDigitalsUpload.this, MultipleImageHandler.DOCUMENT, valueToReturn);

    }

    private void ImageAgainHandler() {

//        String valueToReturn = R.id.workmanshipImage1 + "";
//        MultipleImageHandler.showDialog(AddDigitalsUpload.this, PhotoHandler.DOCUMENT, valueToReturn);

        String valueToReturn2 = R.id.workmanshipImage1 + "";
        MultipleImageHandler.showDialog(AddDigitalsUpload.this, MultipleImageHandler.DOCUMENT, valueToReturn2, MultipleImageHandler.pickerViewer);


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        FslLog.d(TAG, "onActivityResult requestCode - " + requestCode + " resultCode - " + resultCode);

        MultipleImageHandler.onActivityResult(AddDigitalsUpload.this,
                requestCode, resultCode, data, new MultipleImageHandler.GetBitmap() {
                    @Override
                    public void onGetBitamp(Bitmap serverBitmap,
                                            ArrayList<String> imagePathArrayList,
                                            String valueReturned) {
                        int resId = Integer.valueOf(valueReturned);

                        if (imagePathArrayList != null && imagePathArrayList.size() == 1) {
//                            digitalImage.setImageBitmap(serverBitmap);
//                            txtSelected.setVisibility(View.VISIBLE);
//                            digitalImage.setVisibility(View.VISIBLE);
//                            txtSelected.setText(imagePathArrayList.size() + " Images Selected");
                        } else {
//                            digitalImage.setVisibility(View.GONE);
                            txtSelected.setVisibility(View.VISIBLE);

                        }
                        for (int i = 0; i < imagePathArrayList.size(); i++) {
                            digitalsUploadModal.imageArray.add(imagePathArrayList.get(i));
                            digitalsUploadModal.ImageExtn = FEnumerations.E_IMAGE_EXTN;
                        }
                        txtSelected.setText(digitalsUploadModal.imageArray.size() + "");
                        ImageAgainHandler();


//                    workmanshipImage1 = serverBitmap;
//                        digitalImage.setAlpha(1.0f);

                    }

                });
        if (requestCode == 0
                && resultCode == RESULT_OK) {
        }
    }


}
