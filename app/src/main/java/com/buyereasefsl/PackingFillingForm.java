package com.buyereasefsl;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.constant.FEnumerations;
import com.podetail.POItemDtl;
import com.util.GenUtils;

/**
 * Created by ADMIN on 11/17/2017.
 */

public class PackingFillingForm extends AppCompatActivity implements View.OnClickListener {

    int pos;
    String data;
    EditText editL, editW, editH, editWt, editCBM;

    POItemDtl itemReportModel;

    int mViewType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.packing_form);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Find package");

        editL = (EditText) findViewById(R.id.editL);
        editW = (EditText) findViewById(R.id.editW);
        editH = (EditText) findViewById(R.id.editH);
        editWt = (EditText) findViewById(R.id.editWt);
        editCBM = (EditText) findViewById(R.id.editCBM);


        pos = getIntent().getIntExtra("pos", -1);
        mViewType = getIntent().getIntExtra("fill", 0);
        data = getIntent().getStringExtra("detail");
        if (data != null) {
            updateUI(data);
            handleUI();
        } else {
            finish();
        }


    }

    private void handleUI() {
        if (mViewType == FEnumerations.REQUEST_FOR_UNIT_PACKING_FILL) {
            if (itemReportModel.PKG_Me_Unit_FindingL != 0)
                editL.setText(itemReportModel.PKG_Me_Unit_FindingL + "");
            if (itemReportModel.PKG_Me_Unit_FindingH != 0)
                editW.setText(itemReportModel.PKG_Me_Unit_FindingH + "");
            if (itemReportModel.PKG_Me_Unit_FindingH != 0)
                editH.setText(itemReportModel.PKG_Me_Unit_FindingH + "");
            if (itemReportModel.PKG_Me_Unit_FindingWt != 0)
                editWt.setText(itemReportModel.PKG_Me_Unit_FindingWt + "");
            if (itemReportModel.PKG_Me_Unit_FindingCBM != 0)
                editCBM.setText(itemReportModel.PKG_Me_Unit_FindingCBM + "");

            //comment by shekhar
            /*if (!TextUtils.isEmpty(itemReportModel.PKG_Me_Unit_FindingCBM) && !itemReportModel.PKG_Me_Unit_FindingCBM.equals("null"))
                editCBM.setText(itemReportModel.PKG_Me_Unit_FindingCBM + "");*/
        } else if (mViewType == FEnumerations.REQUEST_FOR_INNER_PACKING_FILL) {
            if (itemReportModel.PKG_Me_Inner_FindingL != 0)
                editL.setText(itemReportModel.PKG_Me_Inner_FindingL + "");
            if (itemReportModel.PKG_Me_Inner_FindingB != 0)
                editW.setText(itemReportModel.PKG_Me_Inner_FindingB + "");
            if (itemReportModel.PKG_Me_Inner_FindingH != 0)
                editH.setText(itemReportModel.PKG_Me_Inner_FindingH + "");
            if (itemReportModel.PKG_Me_Inner_FindingWt != 0)
                editWt.setText(itemReportModel.PKG_Me_Inner_FindingWt + "");
            if (itemReportModel.PKG_Me_Inner_FindingCBM != 0)
                editWt.setText(itemReportModel.PKG_Me_Inner_FindingCBM + "");
            //comment by shekhar
            /*if (!TextUtils.isEmpty(itemReportModel.PKG_Me_Inner_FindingCBM) && !itemReportModel.PKG_Me_Inner_FindingCBM.equals("null"))
                editCBM.setText(itemReportModel.PKG_Me_Inner_FindingCBM + "");*/
        } else if (mViewType == FEnumerations.REQUEST_FOR_MASTER_PACKING_FILL) {
            if (itemReportModel.PKG_Me_Master_FindingL != 0)
                editL.setText(itemReportModel.PKG_Me_Master_FindingL + "");
            if (itemReportModel.PKG_Me_Master_FindingB != 0)
                editW.setText(itemReportModel.PKG_Me_Master_FindingB + "");
            if (itemReportModel.PKG_Me_Master_FindingH != 0)
                editH.setText(itemReportModel.PKG_Me_Master_FindingH + "");
            if (itemReportModel.PKG_Me_Master_FindingWt != 0)
                editWt.setText(itemReportModel.PKG_Me_Master_FindingWt + "");
            if (itemReportModel.PKG_Me_Master_FindingCBM != 0)
                editWt.setText(itemReportModel.PKG_Me_Master_FindingCBM + "");
            /*if (!TextUtils.isEmpty(itemReportModel.PKG_Me_Master_FindingCBM) && !itemReportModel.PKG_Me_Master_FindingCBM.equals("null"))
                editCBM.setText(itemReportModel.PKG_Me_Master_FindingCBM + "");*/
        } else if (mViewType == FEnumerations.REQUEST_FOR_PALLET_PACKING_FILL) {
            if (itemReportModel.PKG_Me_Pallet_FindingL != 0)
                editL.setText(itemReportModel.PKG_Me_Pallet_FindingL + "");
            if (itemReportModel.PKG_Me_Pallet_FindingB != 0)
                editW.setText(itemReportModel.PKG_Me_Pallet_FindingB + "");
            if (itemReportModel.PKG_Me_Pallet_FindingH != 0)
                editH.setText(itemReportModel.PKG_Me_Pallet_FindingH + "");
            if (itemReportModel.PKG_Me_Pallet_FindingWt != 0)
                editWt.setText(itemReportModel.PKG_Me_Pallet_FindingWt + "");
            if (itemReportModel.PKG_Me_Pallet_FindingCBM != 0)
                editWt.setText(itemReportModel.PKG_Me_Pallet_FindingCBM + "");
            //commnet by shekhar
            /*if (!TextUtils.isEmpty(itemReportModel.PKG_Me_Pallet_FindingCBM) && !itemReportModel.PKG_Me_Pallet_FindingCBM.equals("null"))
                editCBM.setText(itemReportModel.PKG_Me_Pallet_FindingCBM + "");*/
//            editCBM.setText(itemReportModel.PKG_Me_Pallet_FindingCBM + "");
        }

    }

    private void updateUI(String data) {

        itemReportModel = GenUtils.deSerializeItemReportModal(data);

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

                onSubmit();
                return true;


        }

        return false;
    }

    private void onSubmit() {

        String _l = editL.getText().toString().trim();
        String _b = editW.getText().toString().trim();
        String _h = editH.getText().toString().trim();
        String _wt = editWt.getText().toString().trim();
        String _cbm = editCBM.getText().toString().trim();

        if (mViewType == FEnumerations.REQUEST_FOR_UNIT_PACKING_FILL) {
            if (!TextUtils.isEmpty(_l))
                itemReportModel.PKG_Me_Unit_FindingL = Integer.parseInt(_l);
            if (!TextUtils.isEmpty(_b))
                itemReportModel.PKG_Me_Unit_FindingB = Integer.parseInt(_b);
            if (!TextUtils.isEmpty(_h))
                itemReportModel.PKG_Me_Unit_FindingH = Integer.parseInt(_h);
            if (!TextUtils.isEmpty(_wt))
                itemReportModel.PKG_Me_Unit_FindingWt = Integer.parseInt(_wt);
            if (!TextUtils.isEmpty(_cbm))
                itemReportModel.PKG_Me_Unit_FindingCBM = Integer.parseInt(_cbm);
        } else if (mViewType == FEnumerations.REQUEST_FOR_INNER_PACKING_FILL) {
            if (!TextUtils.isEmpty(_l))
                itemReportModel.PKG_Me_Inner_FindingL = Integer.parseInt(_l);
            if (!TextUtils.isEmpty(_b))
                itemReportModel.PKG_Me_Inner_FindingB = Integer.parseInt(_b);
            if (!TextUtils.isEmpty(_h))
                itemReportModel.PKG_Me_Inner_FindingH = Integer.parseInt(_h);
            if (!TextUtils.isEmpty(_wt))
                itemReportModel.PKG_Me_Inner_FindingWt = Integer.parseInt(_wt);
            if (!TextUtils.isEmpty(_cbm))
                itemReportModel.PKG_Me_Inner_FindingCBM = Integer.parseInt(_cbm);
        } else if (mViewType == FEnumerations.REQUEST_FOR_MASTER_PACKING_FILL) {
            if (!TextUtils.isEmpty(_l))
                itemReportModel.PKG_Me_Master_FindingL = Integer.parseInt(_l);
            if (!TextUtils.isEmpty(_b))
                itemReportModel.PKG_Me_Master_FindingB = Integer.parseInt(_b);
            if (!TextUtils.isEmpty(_h))
                itemReportModel.PKG_Me_Master_FindingH = Integer.parseInt(_h);
            if (!TextUtils.isEmpty(_wt))
                itemReportModel.PKG_Me_Master_FindingWt = Integer.parseInt(_wt);
            if (!TextUtils.isEmpty(_cbm))
                itemReportModel.PKG_Me_Master_FindingCBM = Integer.parseInt(_cbm);
        } else if (mViewType == FEnumerations.REQUEST_FOR_PALLET_PACKING_FILL) {
            if (!TextUtils.isEmpty(_l))
                itemReportModel.PKG_Me_Pallet_FindingL = Integer.parseInt(_l);
            if (!TextUtils.isEmpty(_b))
                itemReportModel.PKG_Me_Pallet_FindingB = Integer.parseInt(_b);
            if (!TextUtils.isEmpty(_h))
                itemReportModel.PKG_Me_Pallet_FindingH = Integer.parseInt(_h);
            if (!TextUtils.isEmpty(_wt))
                itemReportModel.PKG_Me_Pallet_FindingWt = Integer.parseInt(_wt);
            if (!TextUtils.isEmpty(_cbm))
                itemReportModel.PKG_Me_Pallet_FindingCBM = Integer.parseInt(_cbm);
        }

        Intent intent = new Intent();
        intent.putExtra("detail", GenUtils.serializeItemReportModal(itemReportModel));
        setResult(RESULT_OK, intent);
        finish();

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
           /* case R.id.workmanshipImage1Container:
                String valueToReturn = R.id.workmanshipImage1 + "";
                PhotoHandler.showDialog(PackingFillingForm.this, PhotoHandler.DOCUMENT, valueToReturn);

                break;

            case R.id.workmanshipImage2Container:
                String valueToReturn1 = R.id.workmanshipImage2 + "";
                PhotoHandler.showDialog(PackingFillingForm.this, PhotoHandler.DOCUMENT, valueToReturn1);

                break;

            case R.id.workmanshipImage3Container:
                String valueToReturn2 = R.id.workmanshipImage3 + "";
                PhotoHandler.showDialog(PackingFillingForm.this, PhotoHandler.DOCUMENT, valueToReturn2);

                break;*/
        }
    }


}
