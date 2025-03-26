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

/**
 * Created by ADMIN on 2/2/2018.
 */

public class CartonFragment extends Fragment {

    public static CartonFragment newInstance() {
        CartonFragment homeFragment = new CartonFragment();
        return homeFragment;
    }

    CartonFragment cartonFragment;
    View yourFragmentUI;
    RecyclerView recyclerView;
    TextView CartonTotalPacked, cartonTotalAvalable,
            cartonTotalInspected ;
    POItemMultipleHandlerAdaptor inspectionAdaptor;
    String TAG="CartonFragment";
    //Overriden method onCreateView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        yourFragmentUI = inflater.inflate(R.layout.carton_fragment, container, false);
//        yourFragmentUI.findViewById(R.id.inputLayout).setVisibility(View.GONE);


        cartonFragment = this;
        cartonFragment.recyclerView = (RecyclerView) cartonFragment.yourFragmentUI.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(cartonFragment.getActivity(),
                DividerItemDecoration.VERTICAL));
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(cartonFragment.getActivity());
        recyclerView.setLayoutManager(mLayoutManager);

        CartonTotalPacked = (TextView)cartonFragment.yourFragmentUI. findViewById(R.id.CartonTotalPacked);
        cartonTotalAvalable = (TextView) cartonFragment.yourFragmentUI. findViewById(R.id.cartonTotalAvalable);

        cartonTotalInspected = (TextView) cartonFragment.yourFragmentUI. findViewById(R.id.cartonTotalInspected);
        FslLog.d(TAG, " carton fragment creating...");

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
        FslLog.d(TAG, " carton adaptor fragment creating...");
        if (inspectionAdaptor == null) {
            inspectionAdaptor = new POItemMultipleHandlerAdaptor(cartonFragment.getActivity()
                    , recyclerView, FEnumerations.VIEW_TYPE_ITEM_CARTON,
                    POItemTabActivity.poItemTabActivity.pOItemDtlList, POItemTabActivity.poItemTabActivity.uniqueList, POItemTabActivity.poItemTabActivity, POItemTabActivity.poItemTabActivity.inspectionModal.InspectionLevel,
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
        CartonTotalPacked.setText(POItemTabActivity.poItemTabActivity. _cartonTotalPacked + "");
        cartonTotalAvalable.setText(POItemTabActivity.poItemTabActivity._cartonTotalAvalable + "");
        cartonTotalInspected.setText(POItemTabActivity.poItemTabActivity._cartonTotalInspected + "");
    }
}
