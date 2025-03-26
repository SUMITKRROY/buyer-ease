package com.sizeQty;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.buyereasefsl.R;
import com.podetail.POItemDtl;
import com.podetail.POItemDtlHandler;
import com.sync.SendDataHandler;
import com.util.GenUtils;

import java.util.ArrayList;
import java.util.List;
public class SizeQtyActivity extends AppCompatActivity implements SizeQtyAdapter.OnItemClickListenerSize {
    private RecyclerView recyclerView;
    private SizeQtyAdapter sizeQtyAdapter;
    private List<SizeQtyModel> sizeQtyModelList;
    private TextView textAvailableQty,textAcceptedQty,saveQtyTV,textShortQty;
    private ImageView img_back;
    private POItemDtl item;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.size_qty_layout);
        recyclerView = findViewById(R.id.sizeQtyRecyclerView);
        textAvailableQty = findViewById(R.id.textAvailableQty);
        textShortQty = findViewById(R.id.textShortQty);
        textAcceptedQty = findViewById(R.id.textAcceptedQty);
        saveQtyTV = findViewById(R.id.saveQtyTV);
        img_back = findViewById(R.id.img_back);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Intent intent = getIntent();
        sizeQtyModelList = (ArrayList<SizeQtyModel>) intent.getSerializableExtra("sizeList");
        item = GenUtils.deSerializePOItemModal(getIntent().getStringExtra("detail"));
        getTotalQty(sizeQtyModelList);
        Log.e("SizeQtyActivity","SizeQtyActivity sizeQtyModelList size=="+sizeQtyModelList.size());
        // Set up the adapter
        sizeQtyAdapter = new SizeQtyAdapter(sizeQtyModelList,this,this);
        recyclerView.setAdapter(sizeQtyAdapter);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                handleToGoBackActivity();
                onBackPressed();
            }
        });

        saveQtyTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSaveClick();
            }
        });
    }




    public void onSaveClick() {
        List<SizeQtyModel> updatedSizeQtyModelList = sizeQtyAdapter.getItemList();
        int totalAcceptedQty = 0;
        int totalAvailableQty = 0;
        int shortQty = 0;
        for(int i=0;i<updatedSizeQtyModelList.size();i++){
            SizeQtyModel sizeQtyModel = new SizeQtyModel();
            totalAcceptedQty+=updatedSizeQtyModelList.get(i).AcceptedQty;
            totalAvailableQty+=updatedSizeQtyModelList.get(i).AvailableQty;
            shortQty += updatedSizeQtyModelList.get(i).OrderQty - (updatedSizeQtyModelList.get(i).EarlierInspected+updatedSizeQtyModelList.get(i).AcceptedQty);
            sizeQtyModel.AcceptedQty =updatedSizeQtyModelList.get(i).AcceptedQty;
            sizeQtyModel.AvailableQty =updatedSizeQtyModelList.get(i).AvailableQty;
            sizeQtyModel.EarlierInspected=updatedSizeQtyModelList.get(i).EarlierInspected;
            sizeQtyModel.OrderQty=updatedSizeQtyModelList.get(i).OrderQty;
            sizeQtyModel.POID=updatedSizeQtyModelList.get(i).POID;
            sizeQtyModel.QRPOItemDtlID=updatedSizeQtyModelList.get(i).QRPOItemDtlID;
            sizeQtyModel.QRPOItemHdrID=updatedSizeQtyModelList.get(i).QRPOItemHdrID;
            sizeQtyModel.SizeGroupDescr=updatedSizeQtyModelList.get(i).SizeGroupDescr;
            sizeQtyModel.SizeID=updatedSizeQtyModelList.get(i).SizeID;
            Log.d("SizeQtyActivity","sizeQtyModel.POID==: " + sizeQtyModel.POID);
            Log.d("SizeQtyActivity","sizeQtyModel.QRPOItemHdrID==: " + sizeQtyModel.QRPOItemHdrID);
            Log.d("SizeQtyActivity","sizeQtyModel.QRPOItemDtlID==: " + sizeQtyModel.QRPOItemDtlID);
            SizeQtyModelHandler.insertSizeQty(this,sizeQtyModel);
        }
        textAvailableQty.setText("Available "+String.valueOf(totalAvailableQty));
        textAcceptedQty.setText("Accepted "+String.valueOf(totalAcceptedQty));
        if(shortQty < 0){
            shortQty = 0;
        }
        textShortQty.setText("Short Qty "+String.valueOf(shortQty));
        item.AcceptedQty = totalAcceptedQty;
        item.ShortStockQty = (int) Float.parseFloat(item.OrderQty) - item.AcceptedQty;
        item.AvailableQty = totalAvailableQty;

        //update previous activity total available and accepted QTY poitem
        boolean status = POItemDtlHandler.updateItemForQty(SizeQtyActivity.this, item);
        Log.d("SizeQtyActivity","POItemDtlHandler==: " + status);
        Toast.makeText(this, "Quantity Updated Successfully", Toast.LENGTH_SHORT).show();
        List<SizeQtyModel> sizeQtyModelList1 = SizeQtyModelHandler.getSizeQtyList(this,item.pRowID);
        sizeQtyAdapter.setItemList(sizeQtyModelList1);
    }

    /*
    * This function not in use
     */
    @Override
    public void onItemClick(int position) {//available
        SizeQtyModel selectedItem = sizeQtyModelList.get(position);
        Log.d("SizeQtyActivity","Selected: " + selectedItem.AvailableQty);
        Log.d("SizeQtyActivity","Selected: " + selectedItem.AcceptedQty);
        SizeQtyModelHandler.insertSizeQty(this,selectedItem);
        Toast.makeText(this, "Quantity Updated Successfully " + selectedItem.SizeGroupDescr, Toast.LENGTH_SHORT).show();
        List<SizeQtyModel> sizeQtyModelList1 = SizeQtyModelHandler.getSizeQtyList(SizeQtyActivity.this,item.pRowID);
//        getTotalAvailableQty(sizeQtyModelList1);
    }

    /*
     * This function not in use
     */
    @Override
    public void onItemAcceptedClick(int position) {
        SizeQtyModel selectedItem = sizeQtyModelList.get(position);
        Log.d("SizeQtyActivity","Selected: " + selectedItem.AvailableQty);
        Log.d("SizeQtyActivity","Selected: " + selectedItem.AcceptedQty);
        if(selectedItem.AcceptedQty <= selectedItem.AvailableQty){
            SizeQtyModelHandler.insertSizeQty(this,selectedItem);
            Toast.makeText(this, "Quantity Updated Successfully " + selectedItem.SizeGroupDescr, Toast.LENGTH_SHORT).show();
            List<SizeQtyModel> sizeQtyModelList1 = SizeQtyModelHandler.getSizeQtyList(SizeQtyActivity.this,item.pRowID);
//            getTotalAcceptedQty(sizeQtyModelList1);
        }else {
            Toast.makeText(this, "You cannot enter a value greater than the available quantity", Toast.LENGTH_SHORT).show();
        }
    }

    private void getTotalQty(List<SizeQtyModel> sizeQtyModelList1) {
        int totalAvailableQty = 0;
        int totalAcceptedQty = 0;
        int shortQty = 0;
        for (int i = 0; i < sizeQtyModelList1.size(); i++) {
            totalAvailableQty += sizeQtyModelList1.get(i).AvailableQty;
            totalAcceptedQty += sizeQtyModelList1.get(i).AcceptedQty;
            shortQty += sizeQtyModelList1.get(i).OrderQty - (sizeQtyModelList1.get(i).EarlierInspected+sizeQtyModelList1.get(i).AcceptedQty);
        }
        Log.d("SizeQtyActivity", "totalAvailableQty: " + totalAvailableQty);
        Log.d("SizeQtyActivity", "totalAcceptedQty: " + totalAcceptedQty);
        textAvailableQty.setText("Available \n"+String.valueOf(totalAvailableQty));
        textAcceptedQty.setText("Accepted \n"+String.valueOf(totalAcceptedQty));
        if(shortQty < 0){
            shortQty = 0;
        }
        textShortQty.setText("Short Qty \n"+String.valueOf(shortQty));
    }
}
