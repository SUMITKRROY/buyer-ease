package com.buyereasefsl;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.inspection.InspectionModal;
import com.util.GenUtils;

import java.util.ArrayList;
import java.util.List;

import me.drakeet.support.toast.ToastCompat;

/**
 * Created by ADMIN on 1/11/2018.
 */

public class IntimationActivity extends AppCompatActivity {

    RecyclerView recyclerViewIntimation;
    List<IntimationModal> intimationModalList;
    IntimationAdaptor intimationAdaptor;
    TextView editName, editEmail;
    Button intimationSubmit;
    String TAG = "IntimationActivity";
    InspectionModal inspectionModal;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intimation_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Intimation Details");
        inspectionModal = GenUtils.deSerializeInspectionModal(getIntent().getStringExtra("detail"));
        handleToIntimation();

    }

    private void handleToIntimation() {

        editName = (EditText) findViewById(R.id.editName);
        editEmail = (EditText) findViewById(R.id.editEmail);
        intimationSubmit = (Button) findViewById(R.id.intimationSubmit);

        recyclerViewIntimation = (RecyclerView) findViewById(R.id.recyclerViewIntimation);
        recyclerViewIntimation.setHasFixedSize(true);
        recyclerViewIntimation.addItemDecoration(new DividerItemDecoration(IntimationActivity.this,
                DividerItemDecoration.VERTICAL));

        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(IntimationActivity.this);
        recyclerViewIntimation.setLayoutManager(mLayoutManager);
        updatIntimationUi();
        handleAddIntimation();
    }

    private void handleAddIntimation() {

        intimationSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _n = editName.getText().toString();
                String _e = editEmail.getText().toString();
                if (TextUtils.isEmpty(_n)) {
                    editName.setError("Name");
                    editName.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(_e)) {
                    editEmail.setError("Email");
                    editEmail.requestFocus();
                    return;
                }
                IntimationModal mIntimatn = new IntimationModal();
                mIntimatn.Name = _n;
                mIntimatn.EmailID = _e;
                mIntimatn.QRHdrID = inspectionModal.pRowID;
                mIntimatn.recType = 100;
                mIntimatn.pRowID = ItemInspectionDetailHandler.GeneratePK(IntimationActivity.this, "QRPOIntimationDetails");

                if (intimationModalList == null) {
                    intimationModalList = new ArrayList<>();
                }
                ItemInspectionDetailHandler.updateOrInsertQRPOIntimationDetails(IntimationActivity.this, mIntimatn);
                intimationModalList.add(mIntimatn);
                setIntimationAdaptor();
                editName.setText("");
                editEmail.setText("");
            }
        });

    }

    private void updatIntimationUi() {
        List<IntimationModal> list = ItemInspectionDetailHandler.getIntimationList(this, inspectionModal.pRowID);
        if (intimationModalList != null) {
            intimationModalList.clear();
        } else {
            intimationModalList = new ArrayList<>();
        }
        intimationModalList.addAll(list);

        recyclerViewIntimation.addOnItemTouchListener(new IntimationAdaptor.RecyclerTouchListener(getApplicationContext()
                , recyclerViewIntimation, new IntimationAdaptor.ClickListener() {
            @Override
            public void onClick(View view, int position) {

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        setIntimationAdaptor();
    }

    private void setIntimationAdaptor() {
        if (intimationAdaptor == null) {
            intimationAdaptor = new IntimationAdaptor(IntimationActivity.this, recyclerViewIntimation
                    , intimationModalList);
            recyclerViewIntimation.setAdapter(intimationAdaptor);
        } else {
            intimationAdaptor.notifyDataSetChanged();
        }
    }

    private void handleUpdateIntimation() {

        if (intimationModalList != null && intimationModalList.size() > 0) {
            for (int i = 0; i < intimationModalList.size(); i++) {
                ItemInspectionDetailHandler.updateOrInsertQRPOIntimationDetails(this, intimationModalList.get(i));
            }
        }

        Toast toast = ToastCompat.makeText(IntimationActivity.this, "Saved successfully", Toast.LENGTH_SHORT);
        GenUtils.safeToastShow(TAG, IntimationActivity.this, toast);
        finish();
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

                handleUpdateIntimation();
                return true;


        }

        return false;
    }


}
