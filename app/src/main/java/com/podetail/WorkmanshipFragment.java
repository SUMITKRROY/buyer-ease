package com.podetail;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.buyereasefsl.R;
import com.constant.FEnumerations;
import com.util.FslLog;
import com.util.GenUtils;

/**
 * Created by ADMIN on 2/2/2018.
 */

public class WorkmanshipFragment extends Fragment {

    public static WorkmanshipFragment newInstance() {
        WorkmanshipFragment homeFragment = new WorkmanshipFragment();
        return homeFragment;
    }

    WorkmanshipFragment workmanshipFragment;
    View yourFragmentUI;
    RecyclerView recyclerView;
    TextView txtTotalCritical,
            txtTotalMajor, txtTotalMinor;
    POItemMultipleHandlerAdaptor inspectionAdaptor;
    String TAG="WorkmanshipFragment";
    //Overriden method onCreateView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        yourFragmentUI = inflater.inflate(R.layout.workmanship_fragment, container, false);
//        yourFragmentUI.findViewById(R.id.inputLayout).setVisibility(View.GONE);


        workmanshipFragment = this;
        workmanshipFragment.recyclerView = (RecyclerView) workmanshipFragment.yourFragmentUI.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(workmanshipFragment.getActivity(),
                DividerItemDecoration.VERTICAL));
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(workmanshipFragment.getActivity());
        recyclerView.setLayoutManager(mLayoutManager);

        txtTotalCritical = (TextView) workmanshipFragment.yourFragmentUI.findViewById(R.id.txtTotalCritical);
        txtTotalMajor = (TextView) workmanshipFragment.yourFragmentUI.findViewById(R.id.txtTotalMajor);
        txtTotalMinor = (TextView) workmanshipFragment.yourFragmentUI.findViewById(R.id.txtTotalMinor);
        FslLog.d(TAG, " workman ship fragment creating...");
        setHasOptionsMenu(true);
        setAdaptor();
        handleToUpdateTotal();
        return yourFragmentUI;


    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        // TODO Auto-generated method stub
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (inspectionAdaptor != null)
                inspectionAdaptor.notifyDataSetChanged();
        }
    }
    public void setAdaptor() {
//        if (pRowIdOfInspectLevel != null
//                && pRowIdOfQualityMinorLevel != null
//                && pRowIdOfQualityMajorLevel != null) {
        FslLog.d(TAG, " workman ship adaptor fragment creating...");
        if (inspectionAdaptor == null) {
            inspectionAdaptor = new POItemMultipleHandlerAdaptor(workmanshipFragment.getActivity()
                    , recyclerView, FEnumerations.VIEW_TYPE_ITEM_WORKMAN_SHIP,
                    POItemTabActivity.poItemTabActivity.pOItemDtlList,
                    POItemTabActivity.poItemTabActivity.uniqueList, POItemTabActivity.poItemTabActivity, POItemTabActivity.poItemTabActivity.inspectionModal.InspectionLevel,
                    POItemTabActivity.poItemTabActivity.inspectionModal.QLMinor,  POItemTabActivity.poItemTabActivity.inspectionModal.QLMajor
                    , POItemTabActivity.poItemTabActivity.mInsLevel);
            recyclerView.setAdapter(inspectionAdaptor);
        } else {
            inspectionAdaptor.notifyDataSetChanged();
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


        if (POItemTabActivity.poItemTabActivity.mInsLevel == FEnumerations.E_INSPECTION_REPORT_LEVEL) {
            txtTotalCritical.setText(POItemTabActivity.poItemTabActivity._TotalCritical + "/" + POItemTabActivity.poItemTabActivity._CriticalDefectsAllowed);
            txtTotalMajor.setText(POItemTabActivity.poItemTabActivity._TotalMajor + "/" + POItemTabActivity.poItemTabActivity._MajorDefectsAllowed);
            txtTotalMinor.setText(POItemTabActivity.poItemTabActivity._TotalMinor + "/" + POItemTabActivity.poItemTabActivity._MinorDefectsAllowed);
            if (POItemTabActivity.poItemTabActivity._TotalCritical > POItemTabActivity.poItemTabActivity._CriticalDefectsAllowed) {
                txtTotalCritical.setTextColor(GenUtils.getColorResource(workmanshipFragment.getActivity(), R.color.red));
            } else {
                txtTotalCritical.setTextColor(GenUtils.getColorResource(workmanshipFragment.getActivity(), R.color.colorBlack));
            }
            if (POItemTabActivity.poItemTabActivity._TotalMajor > POItemTabActivity.poItemTabActivity._MajorDefectsAllowed) {
                txtTotalMajor.setTextColor(GenUtils.getColorResource(workmanshipFragment.getActivity(), R.color.red));
            } else {
                txtTotalMajor.setTextColor(GenUtils.getColorResource(workmanshipFragment.getActivity(), R.color.colorBlack));
            }
            if (POItemTabActivity.poItemTabActivity._TotalMinor > POItemTabActivity.poItemTabActivity._MinorDefectsAllowed) {
                txtTotalMinor.setTextColor(GenUtils.getColorResource(workmanshipFragment.getActivity(), R.color.red));
            } else {
                txtTotalMinor.setTextColor(GenUtils.getColorResource(workmanshipFragment.getActivity(), R.color.colorBlack));
            }

        } else {
            txtTotalCritical.setText(POItemTabActivity.poItemTabActivity._TotalCritical + "");
            txtTotalMajor.setText(POItemTabActivity.poItemTabActivity._TotalMajor + "");
            txtTotalMinor.setText(POItemTabActivity.poItemTabActivity._TotalMinor + "");
        }


    }
}
