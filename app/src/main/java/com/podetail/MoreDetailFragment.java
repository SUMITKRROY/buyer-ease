package com.podetail;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.buyereasefsl.R;
import com.constant.FEnumerations;
import com.util.FslLog;

/**
 * Created by ADMIN on 2/2/2018.
 */

public class MoreDetailFragment extends Fragment {

    public static MoreDetailFragment newInstance() {
        MoreDetailFragment homeFragment = new MoreDetailFragment();
        return homeFragment;
    }

    MoreDetailFragment moreDetailFragment;
    View yourFragmentUI;
    RecyclerView recyclerView;
    POItemMultipleHandlerAdaptor inspectionAdaptor;
    String TAG = "MoreDetailFragment";

    //Overriden method onCreateView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        yourFragmentUI = inflater.inflate(R.layout.more_fragment, container, false);
//        yourFragmentUI.findViewById(R.id.inputLayout).setVisibility(View.GONE);


        moreDetailFragment = this;
        moreDetailFragment.recyclerView = (RecyclerView) moreDetailFragment.yourFragmentUI.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(moreDetailFragment.getActivity(),
                DividerItemDecoration.VERTICAL));
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(moreDetailFragment.getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        FslLog.d(TAG, " more  fragment creating...");
        setHasOptionsMenu(true);
        setAdaptor();
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
        FslLog.d(TAG, " more adaptor fragment creating...");
        if (inspectionAdaptor == null) {
            inspectionAdaptor = new POItemMultipleHandlerAdaptor(moreDetailFragment.getActivity()
                    , recyclerView, FEnumerations.VIEW_TYPE_MORE,
                    POItemTabActivity.poItemTabActivity.pOItemDtlList,
                    POItemTabActivity.poItemTabActivity.uniqueList,
                    POItemTabActivity.poItemTabActivity,
                    POItemTabActivity.poItemTabActivity.inspectionModal.InspectionLevel,
                    POItemTabActivity.poItemTabActivity.inspectionModal.QLMinor,
                    POItemTabActivity.poItemTabActivity.inspectionModal.QLMajor
                    , POItemTabActivity.poItemTabActivity.mInsLevel);
            recyclerView.setAdapter(inspectionAdaptor);
        } else {
            inspectionAdaptor.notifyDataSetChanged();
        }


    }
}
