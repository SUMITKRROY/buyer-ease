package com.podetail;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.buyereasefsl.R;
import com.constant.FEnumerations;
import com.util.FslLog;

/**
 * Created by ADMIN on 2/2/2018.
 */

public class POItemFragment extends Fragment {

    public static POItemFragment newInstance() {
        POItemFragment homeFragment = new POItemFragment();
        return homeFragment;
    }

    POItemFragment poItemFragment;
    View yourFragmentUI;
    RecyclerView recyclerView;
    TextView qualityTotalOrder, totalQualityAvailable,
            totalQualityAccepted, totalQualityShort;
     POItemListAdaptor poItemListAdaptor;
String TAG="POItemFragment";
    //Overriden method onCreateView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        yourFragmentUI = inflater.inflate(R.layout.po_item_fragment, container, false);
//        yourFragmentUI.findViewById(R.id.inputLayout).setVisibility(View.GONE);


        poItemFragment = this;
        poItemFragment.recyclerView = (RecyclerView) poItemFragment.yourFragmentUI.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(poItemFragment.getActivity(),
                DividerItemDecoration.VERTICAL));
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(poItemFragment.getActivity());
        recyclerView.setLayoutManager(mLayoutManager);


        qualityTotalOrder = (TextView) poItemFragment.yourFragmentUI.findViewById(R.id.qualityTotalOrder);
        totalQualityAvailable = (TextView) poItemFragment.yourFragmentUI.findViewById(R.id.totalQualityAvailable);

        totalQualityAccepted = (TextView) poItemFragment.yourFragmentUI.findViewById(R.id.totalQualityAccepted);
        totalQualityShort = (TextView) poItemFragment.yourFragmentUI.findViewById(R.id.totalQualityShort);

        FslLog.d(TAG, " Po item fragment creating...");
        setHasOptionsMenu(true);

        setAdaptor();
        handleToUpdateTotal();
        return yourFragmentUI;


    }


    public void setAdaptor() {
//        if (pRowIdOfInspectLevel != null
//                && pRowIdOfQualityMinorLevel != null
//                && pRowIdOfQualityMajorLevel != null) {
        FslLog.d(TAG, " Po item fragment adaptor creating...");
        if (poItemListAdaptor == null) {
            poItemListAdaptor = new POItemListAdaptor(poItemFragment.getActivity()
                    , recyclerView, FEnumerations.VIEW_TYPE_ITEM_QUALITY,
                    POItemTabActivity.poItemTabActivity.pOItemDtlList,
                    POItemTabActivity.poItemTabActivity,
                    POItemTabActivity.poItemTabActivity.inspectionModal.InspectionLevel,
                    POItemTabActivity.poItemTabActivity.inspectionModal.QLMinor,
                    POItemTabActivity.poItemTabActivity.inspectionModal.QLMajor
                    , POItemTabActivity.poItemTabActivity.mInsLevel);

//            inspectionAdaptor = new POItemMultipleHandlerAdaptor(poItemFragment.getActivity()
//                    , recyclerView, FEnumerations.VIEW_TYPE_ITEM_QUALITY,
//                    POItemTabActivity.poItemTabActivity.pOItemDtlList, POItemTabActivity.poItemTabActivity.uniqueList, POItemTabActivity.poItemTabActivity, POItemTabActivity.poItemTabActivity.inspectionModal.InspectionLevel,
//                    POItemTabActivity.poItemTabActivity.inspectionModal.QLMinor,  POItemTabActivity.poItemTabActivity.inspectionModal.QLMajor
//                    , POItemTabActivity.poItemTabActivity.mInsLevel);
            recyclerView.setAdapter(poItemListAdaptor);
        } else {
            poItemListAdaptor.notifyDataSetChanged();
        }
//        } else {
//            if (pRowIdOfInspectLevel != null) {
//                ToastCompat.makeText(this, "Select inspection level", Toast.LENGTH_SHORT).show();
//            } else if (pRowIdOfQualityMinorLevel != null) {
//                ToastCompat.makeText(this, "Select quality level", Toast.LENGTH_SHORT).show();
//            } else if (pRowIdOfQualityMajorLevel != null) {
//                ToastCompat.makeText(this, "Select quality level", Toast.LENGTH_SHORT).show();
//            }
//        }

    }

    public void handleToUpdateTotal() {


        qualityTotalOrder.setText(POItemTabActivity.poItemTabActivity._qualityTotalOrder + "");
        totalQualityAvailable.setText(POItemTabActivity.poItemTabActivity._totalQualityAvailable + "");

        totalQualityAccepted.setText(POItemTabActivity.poItemTabActivity._totalQualityAccepted + "");
        totalQualityShort.setText(POItemTabActivity.poItemTabActivity._totalQualityShort + "");


    }


}
