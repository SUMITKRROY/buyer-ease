package com.buyereasefsl;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.podetail.POItemDtl;
import com.util.FslLog;
import com.util.GenUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by ADMIN on 12/25/2017.
 */

public class ItemMeasurementFindingActivity extends AppCompatActivity {

    RecyclerView recyclerViewItemMeasurement;
    List<ItemMeasurementModal> itemMeasurementModalList;
    ItemMeasurementFindingAdaptor itemMeasurementFindingAdaptor;

    int pos = -1;
    ItemMeasurementModal mItemMeasurementModal;
    String TAG = "ItemMeasurementFindingActivity";
    POItemDtl poItemDtl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.measurement_finding_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Finding Details");

        pos = getIntent().getIntExtra("pos", -1);
        mItemMeasurementModal = GenUtils.deSerializeItemMeasurement(getIntent().getStringExtra("detail"));
        poItemDtl = GenUtils.deSerializePOItemModal(getIntent().getStringExtra("pod"));
        recyclerViewItemMeasurement = (RecyclerView) findViewById(R.id.recyclerViewItemMeasurement);
        recyclerViewItemMeasurement.setHasFixedSize(true);
        recyclerViewItemMeasurement.addItemDecoration(new DividerItemDecoration(ItemMeasurementFindingActivity.this,
                DividerItemDecoration.VERTICAL));

        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(ItemMeasurementFindingActivity.this);
        recyclerViewItemMeasurement.setLayoutManager(mLayoutManager);

        itemMeasurementModalList = new ArrayList<>();
        if (!TextUtils.isEmpty(mItemMeasurementModal.SampleSizeValue) && !mItemMeasurementModal.SampleSizeValue.equals("null")) {
            int itemSize = Integer.parseInt(mItemMeasurementModal.SampleSizeValue);

            List<ItemMeasurementModal> lMsr = ItemInspectionDetailHandler.getFindingItemMeasurementList(ItemMeasurementFindingActivity.this, mItemMeasurementModal);
            FslLog.d(TAG, " NEW find Sample size " + itemSize + " AND got sample size IS " + lMsr.size());
            if (lMsr.size() == itemSize) {
                FslLog.d(TAG, " Got find Sample size same So copy the data... ");
                itemMeasurementModalList.addAll(lMsr);
            } else {
                for (int j = 0; j < lMsr.size(); j++) {
                    ItemInspectionDetailHandler.deletFindingItemMeasurement(ItemMeasurementFindingActivity.this, lMsr.get(j).pRowIDForFinding);
                }
                for (int i = 0; i < itemSize; i++) {
                    ItemMeasurementModal itemMeasurementModal = new ItemMeasurementModal();
                    itemMeasurementModal.pRowID = mItemMeasurementModal.pRowID;
                    itemMeasurementModal.QRHdrID = mItemMeasurementModal.QRHdrID;
                    itemMeasurementModal.QRPOItemHdrID = mItemMeasurementModal.QRPOItemHdrID;
                    itemMeasurementModal.Dim_length = mItemMeasurementModal.Dim_length;
                    itemMeasurementModal.Dim_Height = mItemMeasurementModal.Dim_Height;
                    itemMeasurementModal.Dim_Width = mItemMeasurementModal.Dim_Width;
                    itemMeasurementModal.SampleSizeValue = mItemMeasurementModal.SampleSizeValue;
                    itemMeasurementModal.SampleSizeID = mItemMeasurementModal.SampleSizeID;
                    itemMeasurementModal.InspectionResultID = mItemMeasurementModal.InspectionResultID;
                    itemMeasurementModal.Tolerance_Range = mItemMeasurementModal.Tolerance_Range;
                    itemMeasurementModal.ItemMeasurementDescr = mItemMeasurementModal.ItemMeasurementDescr;
                    itemMeasurementModal.Digitals = mItemMeasurementModal.Digitals;
                    itemMeasurementModal.listImages = mItemMeasurementModal.listImages;
                    itemMeasurementModalList.add(itemMeasurementModal);
                }
            }

        }
        setItemMeasurementAdaptor();

    }

    private void setItemMeasurementAdaptor() {
        if (itemMeasurementFindingAdaptor == null) {
            itemMeasurementFindingAdaptor = new ItemMeasurementFindingAdaptor(ItemMeasurementFindingActivity.this, recyclerViewItemMeasurement
                    , itemMeasurementModalList, mItemMeasurementModal);
            recyclerViewItemMeasurement.setAdapter(itemMeasurementFindingAdaptor);
        } else {
            itemMeasurementFindingAdaptor.notifyDataSetChanged();
        }
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

        if (itemMeasurementModalList != null && itemMeasurementModalList.size() > 0) {
            int minIndexOfHeight = findMinIndexOfHeight(itemMeasurementModalList);
            int maxIndexOfHeight = findMaxIndexOfHeight(itemMeasurementModalList);

            int minIndexOfLength = findMinIndexOfLength(itemMeasurementModalList);
            int maxIndexOfLength = findMaxIndexOfLength(itemMeasurementModalList);

            int minIndexOfWidth = findMinIndexOfWidth(itemMeasurementModalList);
            int maxIndexOfWidth = findMaxIndexOfWidth(itemMeasurementModalList);

            FslLog.d(TAG, "minLength : " + itemMeasurementModalList.get(minIndexOfLength).Dim_length + " maxLength : " + itemMeasurementModalList.get(maxIndexOfLength).Dim_length
                    + "\n " + "minHeight : " + itemMeasurementModalList.get(minIndexOfHeight).Dim_Height + " maxHeight : " + itemMeasurementModalList.get(maxIndexOfHeight).Dim_Height +
                    "\n" + "minWidth : " + itemMeasurementModalList.get(minIndexOfWidth).Dim_Width + " maxWidth : " + itemMeasurementModalList.get(maxIndexOfWidth).Dim_Width);

            String sToll = mItemMeasurementModal.Dim_length + "(" + itemMeasurementModalList.get(minIndexOfLength).Dim_length + "-" + itemMeasurementModalList.get(maxIndexOfLength).Dim_length + "), "
                    + mItemMeasurementModal.Dim_Height + "(" + itemMeasurementModalList.get(minIndexOfHeight).Dim_Height + "-" + itemMeasurementModalList.get(maxIndexOfHeight).Dim_Height + "), "
                    + mItemMeasurementModal.Dim_Width + "(" + itemMeasurementModalList.get(minIndexOfWidth).Dim_Width + "-" + itemMeasurementModalList.get(maxIndexOfWidth).Dim_Width + ")";

            FslLog.d(TAG, "tolerance : " + sToll);
            mItemMeasurementModal.Tolerance_Range = sToll;
        }
        if (itemMeasurementModalList != null && itemMeasurementModalList.size() > 0) {
            for (int i = 0; i < itemMeasurementModalList.size(); i++) {
                if (TextUtils.isEmpty(itemMeasurementModalList.get(i).pRowIDForFinding) || itemMeasurementModalList.get(i).pRowIDForFinding.equals("null")) {
                    itemMeasurementModalList.get(i).pRowIDForFinding = ItemInspectionDetailHandler.GeneratePK(ItemMeasurementFindingActivity.this, "QRFindings");
                }
                ItemInspectionDetailHandler.updateFindingItemMeasurement(ItemMeasurementFindingActivity.this, itemMeasurementModalList.get(i), poItemDtl);
            }
        }

        Intent intent = new Intent();
        intent.putExtra("pos", pos);
        intent.putExtra("detail", GenUtils.serializeItemMeasurementModal(mItemMeasurementModal));
        setResult(RESULT_OK, intent);
        finish();
    }

    public static <T extends Comparable<T>> int findMinIndexOfHeight(final List<ItemMeasurementModal> xs) {
        int minIndex;
        if (xs.isEmpty()) {
            minIndex = -1;
        } else {
            final ListIterator<ItemMeasurementModal> itr = xs.listIterator();
            ItemMeasurementModal min = itr.next(); // first element as the current minimum
            minIndex = itr.previousIndex();
            while (itr.hasNext()) {
                final ItemMeasurementModal curr = itr.next();
                if (curr.Dim_Height < min.Dim_Height) {
                    min = curr;
                    minIndex = itr.previousIndex();
                }
            }
        }
        return minIndex;
    }

    public static <T extends Comparable<T>> int findMaxIndexOfHeight(final List<ItemMeasurementModal> xs) {
        int maxIndex;
        if (xs.isEmpty()) {
            maxIndex = -1;
        } else {
            final ListIterator<ItemMeasurementModal> itr = xs.listIterator();
            ItemMeasurementModal min = itr.next(); // first element as the current minimum
            maxIndex = itr.previousIndex();
            while (itr.hasNext()) {
                final ItemMeasurementModal curr = itr.next();
                if (curr.Dim_Height > min.Dim_Height) {
                    min = curr;
                    maxIndex = itr.previousIndex();
                }
            }
        }
        return maxIndex;
    }

    public static <T extends Comparable<T>> int findMinIndexOfLength(final List<ItemMeasurementModal> xs) {
        int minIndex;
        if (xs.isEmpty()) {
            minIndex = -1;
        } else {
            final ListIterator<ItemMeasurementModal> itr = xs.listIterator();
            ItemMeasurementModal min = itr.next(); // first element as the current minimum
            minIndex = itr.previousIndex();
            while (itr.hasNext()) {
                final ItemMeasurementModal curr = itr.next();
                if (curr.Dim_length < min.Dim_length) {
                    min = curr;
                    minIndex = itr.previousIndex();
                }
            }
        }
        return minIndex;
    }

    public static <T extends Comparable<T>> int findMaxIndexOfLength(final List<ItemMeasurementModal> xs) {
        int maxIndex;
        if (xs.isEmpty()) {
            maxIndex = -1;
        } else {
            final ListIterator<ItemMeasurementModal> itr = xs.listIterator();
            ItemMeasurementModal min = itr.next(); // first element as the current minimum
            maxIndex = itr.previousIndex();
            while (itr.hasNext()) {
                final ItemMeasurementModal curr = itr.next();
                if (curr.Dim_length > min.Dim_length) {
                    min = curr;
                    maxIndex = itr.previousIndex();
                }
            }
        }
        return maxIndex;
    }

    public static <T extends Comparable<T>> int findMinIndexOfWidth(final List<ItemMeasurementModal> xs) {
        int minIndex;
        if (xs.isEmpty()) {
            minIndex = -1;
        } else {
            final ListIterator<ItemMeasurementModal> itr = xs.listIterator();
            ItemMeasurementModal min = itr.next(); // first element as the current minimum
            minIndex = itr.previousIndex();
            while (itr.hasNext()) {
                final ItemMeasurementModal curr = itr.next();
                if (curr.Dim_Width < min.Dim_Width) {
                    min = curr;
                    minIndex = itr.previousIndex();
                }
            }
        }
        return minIndex;
    }

    public static <T extends Comparable<T>> int findMaxIndexOfWidth(final List<ItemMeasurementModal> xs) {
        int maxIndex;
        if (xs.isEmpty()) {
            maxIndex = -1;
        } else {
            final ListIterator<ItemMeasurementModal> itr = xs.listIterator();
            ItemMeasurementModal min = itr.next(); // first element as the current minimum
            maxIndex = itr.previousIndex();
            while (itr.hasNext()) {
                final ItemMeasurementModal curr = itr.next();
                if (curr.Dim_Width > min.Dim_Width) {
                    min = curr;
                    maxIndex = itr.previousIndex();
                }
            }
        }
        return maxIndex;
    }


}
