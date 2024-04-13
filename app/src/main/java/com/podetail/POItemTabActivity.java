package com.podetail;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.buyereasefsl.ItemInspectionDetail;
import com.buyereasefsl.R;
import com.constant.AppConfig;
import com.constant.FClientConstants;
import com.constant.FEnumerations;
import com.data.UserSession;
import com.inspection.InspectionModal;
import com.util.FslLog;
import com.util.GenUtils;
import com.util.SetInitiateStaticVariable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by ADMIN on 2/2/2018.
 */

public class POItemTabActivity extends AppCompatActivity implements POItemMultipleHandlerAdaptor.OnItemClickListener, POItemListAdaptor.OnItemClickListener {


    public ScrollableViewPager viewPager;
    ViewPagerAdapter adapter;
    public TabLayout tabLayout;
    InspectionModal inspectionModal;
    static POItemTabActivity poItemTabActivity;
    static int PICKED_UP = -1;
    String TAG = "POItemTabActivity";
    int _cartonTotal = 0, _cartonTotalPacked = 0, _cartonTotalAvalable = 0,
            _cartonTotalInspected = 0, _qualityTotalOrder = 0, _totalQualityAvailable = 0,
            _totalQualityAccepted = 0, _totalQualityShort = 0, _TotalCritical = 0,
            _TotalMajor = 0, _TotalMinor = 0,
            _CriticalDefectsAllowed = 0, _MajorDefectsAllowed = 0, _MinorDefectsAllowed = 0;
    int mInsLevel = FEnumerations.E_INSPECTION_MATERIAL_LEVEL;
    List<POItemDtl> pOItemDtlList;
    List<POItemDtl1> uniqueList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.poitem_tab_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SetInitiateStaticVariable.setInitiateStaticVariable(POItemTabActivity.this);
//        TextView companyName = (TextView) findViewById(R.id.companyName);
//        String st = GenUtils.truncate(new UserSession(POItemTabActivity.this).getCompanyName(), FClientConfig.COMPANY_TRUNC_LENGTH);
//        companyName.setText(st);
//        TextView appName = (TextView) findViewById(R.id.appName);
//        appName.setText(getResources().getString(R.string.app_name) + " " + GenUtils.getVersionInfo(VisitorNonGuardTabActivity.this));
        if (savedInstanceState != null) {
            inspectionModal = GenUtils.deSerializeInspectionModal(savedInstanceState.getString("detail"));
        } else {
            inspectionModal = GenUtils.deSerializeInspectionModal(getIntent().getStringExtra("detail"));
        }
        pOItemDtlList = new ArrayList<>();
//        pOItemDtlList.addAll(GenUtils.deSerializePOItemDtlList(getIntent().getStringExtra("poList")));
        List<POItemDtl> list = POItemDtlHandler.getItemList(this, inspectionModal.pRowID);
        pOItemDtlList.addAll(list);
        handleCalculate();
        poItemTabActivity = this;
        this.getSupportActionBar().setTitle(inspectionModal.pRowID);

        UserSession userSession = new UserSession(POItemTabActivity.this);
        userSession.putDeLNO(inspectionModal.pRowID);
        String insPectDt;
        if (TextUtils.isEmpty(inspectionModal.InspectionDt) || inspectionModal.InspectionDt.equalsIgnoreCase("null")) {
            insPectDt = AppConfig.getCurrntDateDDMMyy();
        } else {
            insPectDt = inspectionModal.InspectionDt;
        }
        userSession.putInspectionDt(insPectDt);

//        getList();
        handleToAQLFormula();

        tabSetup();

    }

    private void tabSetup() {
        // Setting ViewPager for each Tabs
        viewPager = (ScrollableViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        viewPager.setCanScroll(true);
        viewPager.setOffscreenPageLimit(adapter.getCount());
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }


    private void handleToAQLFormula() {

        if (inspectionModal.AQLFormula == 0) {
            mInsLevel = FEnumerations.E_INSPECTION_REPORT_LEVEL;
        } else {
            mInsLevel = FEnumerations.E_INSPECTION_MATERIAL_LEVEL;
        }
     /*   switch (inspectionModal.AQLFormula) {
            case 1:

                mInsLevel = FEnumerations.E_INSPECTION_REPORT_LEVEL;
//                inspectionAdaptor.updateLevel(mInsLevel);
                handleToUpdateTotal();
                break;
            case 2:

                mInsLevel = FEnumerations.E_INSPECTION_MATERIAL_LEVEL;
//                inspectionAdaptor.updateLevel(mInsLevel);
                handleToUpdateTotal();
                break;
        }*/
    }

    private void setupViewPager(ViewPager viewPager) {
        FslLog.d(TAG, " page stepup creating...");
        adapter = new ViewPagerAdapter(getSupportFragmentManager());


        POItemFragment poItemFragment = POItemFragment.newInstance();


//        mVisitorCheckInFragment.setArguments(bundle);
        adapter.addFragment(poItemFragment, "PO/Item");

        WorkmanshipFragment workmanshipFragment = WorkmanshipFragment.newInstance();
//        Bundle bundle1 = new Bundle();
//        mVisitorCheckOutFragment.setArguments(bundle);

        adapter.addFragment(workmanshipFragment, "Workmanship");
        viewPager.setAdapter(adapter);


        CartonFragment cartonFragment = CartonFragment.newInstance();
//        Bundle bundle1 = new Bundle();
//        mVisitorCheckOutFragment.setArguments(bundle);

        adapter.addFragment(cartonFragment, "Carton");
        viewPager.setAdapter(adapter);


        MoreDetailFragment moreDetailFragment = MoreDetailFragment.newInstance();
//        Bundle bundle1 = new Bundle();
//        mVisitorCheckOutFragment.setArguments(bundle);

        adapter.addFragment(moreDetailFragment, "More Detail");
        viewPager.setAdapter(adapter);


        QualityParameterFragment qualityParameterFragment = QualityParameterFragment.newInstance();
//        Bundle bundle1 = new Bundle();
//        mVisitorCheckOutFragment.setArguments(bundle);

        adapter.addFragment(qualityParameterFragment, "Quality Parameters");
        viewPager.setAdapter(adapter);


        EnclosureFragment enclosureFragment = EnclosureFragment.newInstance();
//        Bundle bundle1 = new Bundle();
//        mVisitorCheckOutFragment.setArguments(bundle);

        adapter.addFragment(enclosureFragment, "Enclosure");
        viewPager.setAdapter(adapter);

    }

    @Override
    public void onItemClick(POItemDtl item) {
        if (item != null) {

            Intent intent = new Intent(POItemTabActivity.this, ItemInspectionDetail.class);
            intent.putExtra("detail", GenUtils.serializePOItemModal(item));
            intent.putExtra("inspectionDetail", GenUtils.serializeInspectionModal(inspectionModal));

            startActivityForResult(intent, FEnumerations.RESULT_FOR_DETAIL_CODE);


        }
    }

    @Override
    public void onDeleteItemClick(POItemDtl item) {
        GenUtils.forConfirmationAlertDialog(POItemTabActivity.this,
                FClientConstants.TEXT_BUTTON_YES,
                FClientConstants.TEXT_BUTTON_NO
                , null, "Are you sure. Do you want to delete inspection", new GenUtils.AlertDialogClickListener() {
                    @Override
                    public void onPositiveButton() {

                        POItemDtlHandler.updateItemForDisable(POItemTabActivity.this, item);
//                        List<POItemDtl> list = POItemDtlHandler.getItemList(POItemTabActivity.this, inspectionModal.pRowID);
//                        if (list != null) {
//                            if (pOItemDtlList != null)
//                                pOItemDtlList.clear();
//                            else pOItemDtlList = new ArrayList<>();
////                            pOItemDtlList.add(getQualityLabel());
//                            pOItemDtlList.addAll(list);
//                        }
                        UpdateToRefreshUI();


                    }

                    @Override
                    public void onNegativeButton() {

                    }
                });
    }

    @Override
    public void onItemBackClick(POItemDtl1 item) {


        if (item != null) {
            int pos = -1;
            boolean isFound = false;
            for (int i = 0; i < pOItemDtlList.size(); i++) {
                if (item.QRPOItemHdrID.equals(pOItemDtlList.get(i).QRPOItemHdrID)) {
                    isFound = true;
                    pos = i;
                    break;

                }
            }
            if (isFound) {
                Intent intent = new Intent(POItemTabActivity.this, ItemInspectionDetail.class);
                intent.putExtra("detail", GenUtils.serializePOItemModal(pOItemDtlList.get(pos)));
                intent.putExtra("inspectionDetail", GenUtils.serializeInspectionModal(inspectionModal));

                startActivityForResult(intent, FEnumerations.RESULT_FOR_DETAIL_CODE);

            }
        }
    }


    public class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        public void removeFragment(int position) {
            mFragmentList.remove(position);
            mFragmentTitleList.remove(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {

//            return null;
            return mFragmentTitleList.get(position);

        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        switch (item.getItemId()) {
            case android.R.id.home:
                handleToGoBackActivity();
                return true;

            case R.id.submit:
                HandleToSaveAll();


                return true;


            case R.id.actionPrevious:

                handleToGoBackActivity();
                return true;


            case R.id.actionRedu:
                handleReduAction();
                handleToUpdateTotal();
                updateFragments();

                return true;
        }

        return false;
    }


    private void HandleToSaveAll() {

//        runOnUiThread(new Runnable() {
//            public void run() {
//                ToastCompat.makeText(getApplicationContext(), "update data successfully", Toast.LENGTH_SHORT).show();
//                GenUtils.safeToastShow(TAG, getApplicationContext(), toast);
//            }
//        });

        handleUpdateData();

//        handleQualityParameter();

        new Handler().postDelayed(new Runnable() {
            public void run() {
                GenUtils.showToastInThread(POItemTabActivity.this, "update data successfully");
            }
        }, 100);
    }

    private void handleUpdateData() {
        boolean status = false;
        for (int i = 0; i < pOItemDtlList.size(); i++) {
//            status = POItemDtlHandler.updatePOItemHdr(this, pOItemDtlList.get(i));
            status = POItemDtlHandler.updatePOItemDtl(this, pOItemDtlList.get(i));
        }

        for (int i = 0; i < uniqueList.size(); i++) {
//            Fragment fragment=adapter.getItem(2).getChildFragmentManager().getFragments();
//            if (fragment instanceof CartonFragment) {
            if (adapter != null) {
                CartonFragment f = (CartonFragment) adapter.getItem(2);
//                ((CartonFragment) fragment).inspectionAdaptor.notifyDataSetChanged();
                if (f != null && f.cartonFragment != null && f.cartonFragment.recyclerView != null) {
                    View view = f.cartonFragment.recyclerView.getChildAt(i);
                    if (view != null) {
                        EditText nameEditText = (EditText) view.findViewById(R.id.CartonPackedLabel);
                        String name = nameEditText.getText().toString();
                        if (!TextUtils.isEmpty(name)) {
                            uniqueList.get(i).CartonsPacked2 = Integer.parseInt(name);
                        } else {
                            uniqueList.get(i).CartonsPacked2 = 0;
                        }
                        EditText InspectedLabel = (EditText) view.findViewById(R.id.InspectedLabel);
                        String SInspectedLabel = InspectedLabel.getText().toString();
                        if (!TextUtils.isEmpty(SInspectedLabel)) {
                            uniqueList.get(i).CartonsInspected = Integer.parseInt(SInspectedLabel);
                        } else {
                            uniqueList.get(i).CartonsInspected = 0;
                        }
                    }
                }
            }
//            }


            status = POItemDtlHandler.updatePOItemHdrOfWorkmanshipAndCarton(this, uniqueList.get(i));
            status = POItemDtlHandler.updatePOItemDtlOfWorkmanshipAndCarton(this, uniqueList.get(i));
        }

        UpdateToRefreshUI();
    }

  /*  private void handleQualityParameter() {

        QualityParameterFragment f = (QualityParameterFragment) adapter.getItem(4);
        f.handleQualityParameter();

    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.po_detail_menu, menu);//Menu Resource, Menu

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == FEnumerations.RESULT_FOR_DETAIL_CODE
                || requestCode == FEnumerations.REQUEST_FOR_ADD_INTIMATION) {
            UpdateToRefreshUI();
        }
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }


    }

    private void updateFragments() {
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            if (fragment instanceof CartonFragment) {
                ((CartonFragment) fragment).inspectionAdaptor.notifyDataSetChanged();
                ((CartonFragment) fragment).handleToUpdateTotal();
            }
            if (fragment instanceof POItemFragment) {
                ((POItemFragment) fragment).poItemListAdaptor.notifyDataSetChanged();
                ((POItemFragment) fragment).handleToUpdateTotal();
            }
            if (fragment instanceof MoreDetailFragment) {
                ((MoreDetailFragment) fragment).inspectionAdaptor.notifyDataSetChanged();
            }
            if (fragment instanceof WorkmanshipFragment) {
                ((WorkmanshipFragment) fragment).inspectionAdaptor.notifyDataSetChanged();
                ((WorkmanshipFragment) fragment).handleToUpdateTotal();
            }
        }
    }

    private void UpdateToRefreshUI() {
        FslLog.d(TAG, "UPDATE TO REFRESH..........................");
        List<POItemDtl> list = POItemDtlHandler.getItemList(this, inspectionModal.pRowID);
        if (list != null) {
            if (pOItemDtlList != null)
                pOItemDtlList.clear();
            else pOItemDtlList = new ArrayList<>();

            pOItemDtlList.addAll(list);
            handleCalculate();


            handleToUpdateTotal();
            updateFragments();
        }

//        NetworkUtil.hideSoftKeyboard(POItemTabActivity.this);

    }

    public void handleToUpdateTotal() {
        _cartonTotal = 0;
        _cartonTotalPacked = 0;
        _cartonTotalAvalable = 0;
        _cartonTotalInspected = 0;
        _qualityTotalOrder = 0;
        _totalQualityAvailable = 0;
        _totalQualityAccepted = 0;
        _totalQualityShort = 0;
        _TotalCritical = 0;
        _TotalMajor = 0;
        _TotalMinor = 0;
        _CriticalDefectsAllowed = 0;
        _MajorDefectsAllowed = 0;
        _MinorDefectsAllowed = 0;

        if (pOItemDtlList != null && pOItemDtlList.size() > 0) {
            for (int i = 0; i < pOItemDtlList.size(); i++) {
//                _cartonTotal = _cartonTotal + pOItemDtlList.get(i).CartonsPacked;
//                _cartonTotalPacked = _cartonTotalPacked + pOItemDtlList.get(i).CartonsPacked;
//                _cartonTotalAvalable = _cartonTotalAvalable + pOItemDtlList.get(i).CartonsPacked;
//                _cartonTotalInspected = _cartonTotalInspected + pOItemDtlList.get(i).CartonsInspected;

                if (!TextUtils.isEmpty(pOItemDtlList.get(i).OrderQty) && !pOItemDtlList.get(i).OrderQty.equals("null")) {
                    float f = Float.valueOf(pOItemDtlList.get(i).OrderQty);
                    _qualityTotalOrder = _qualityTotalOrder + (int) f;
                }
                _totalQualityAvailable = _totalQualityAvailable + pOItemDtlList.get(i).AvailableQty;
                _totalQualityAccepted = _totalQualityAccepted + pOItemDtlList.get(i).AcceptedQty;
                _totalQualityShort = _totalQualityShort + pOItemDtlList.get(i).Short;

//                _TotalCritical = _TotalCritical + pOItemDtlList.get(i).CriticalDefect;
//                _TotalMajor = _TotalMajor + pOItemDtlList.get(i).MajorDefect;
//                _TotalMinor = _TotalMinor + pOItemDtlList.get(i).MinorDefect;
//                _CriticalDefectsAllowed = _CriticalDefectsAllowed + pOItemDtlList.get(i).CriticalDefectsAllowed;
//                _MajorDefectsAllowed = _MajorDefectsAllowed + pOItemDtlList.get(i).MajorDefectsAllowed;
//                _MinorDefectsAllowed = _MinorDefectsAllowed + pOItemDtlList.get(i).MinorDefectsAllowed;

            }
            if (uniqueList != null) {
                for (int j = 0; j < uniqueList.size(); j++) {
                    _cartonTotal = _cartonTotal + uniqueList.get(j).CartonsPacked2;
                    _cartonTotalPacked = _cartonTotalPacked + uniqueList.get(j).CartonsPacked2;
                    _cartonTotalAvalable = _cartonTotalAvalable + uniqueList.get(j).CartonsPacked2;
                    _cartonTotalInspected = _cartonTotalInspected + uniqueList.get(j).CartonsInspected;


                    _TotalCritical = _TotalCritical + pOItemDtlList.get(j).CriticalDefect;
                    _TotalMajor = _TotalMajor + pOItemDtlList.get(j).MajorDefect;
                    _TotalMinor = _TotalMinor + pOItemDtlList.get(j).MinorDefect;

                    _CriticalDefectsAllowed = _CriticalDefectsAllowed + pOItemDtlList.get(j).CriticalDefectsAllowed;
                    _MajorDefectsAllowed = _MajorDefectsAllowed + pOItemDtlList.get(j).MajorDefectsAllowed;
                    _MinorDefectsAllowed = _MinorDefectsAllowed + pOItemDtlList.get(j).MinorDefectsAllowed;
                }
            }
        }


    }

    private void handleReduAction() {
        changeOnReduAvailable();
    }

    private void changeOnReduAvailable() {
        if (pOItemDtlList != null && pOItemDtlList.size() > 0) {
            for (int i = 0; i < pOItemDtlList.size(); i++) {
                if (!TextUtils.isEmpty(pOItemDtlList.get(i).OrderQty) && !pOItemDtlList.get(i).OrderQty.equals("null")) {
                    float f = Float.valueOf(pOItemDtlList.get(i).OrderQty);
                    pOItemDtlList.get(i).AvailableQty = ((int) f) - pOItemDtlList.get(i).EarlierInspected;
                    pOItemDtlList.get(i).AcceptedQty = ((int) f) - pOItemDtlList.get(i).EarlierInspected;
//                    if (pOItemDtlList.get(i).POMasterPackQty > 0) {
//                        float or = Float.valueOf(pOItemDtlList.get(i).OrderQty);
//                        pOItemDtlList.get(i).CartonsPacked = (int) (or / pOItemDtlList.get(i).POMasterPackQty);
//                    }
                }
            }
            changeOnAvailable();
        }
    }

    public void changeOnAvailable() {

        if (pOItemDtlList != null && pOItemDtlList.size() > 0) {

            for (int j = 0; j < uniqueList.size(); j++) {
                int TAvailableQty = 0, TotalOrder = 0, QualityAccepted = 0;

                for (int i = 0; i < pOItemDtlList.size(); i++) {
                    if (uniqueList.get(j).QRPOItemHdrID.equals(pOItemDtlList.get(i).QRPOItemHdrID)) {
                        TAvailableQty = TAvailableQty + pOItemDtlList.get(i).AvailableQty;
                        float f = Float.parseFloat(pOItemDtlList.get(i).OrderQty);
                        TotalOrder = (int) (TotalOrder + f);
                        QualityAccepted = QualityAccepted + pOItemDtlList.get(i).AcceptedQty;
                    }
                }
                uniqueList.get(j).OrderQty = String.valueOf(TotalOrder);
                uniqueList.get(j).AvailableQty = TAvailableQty;
                uniqueList.get(j).AcceptedQty = QualityAccepted;
                POItemDtl1 pOItemDtl1 = uniqueList.get(j);
                String[] toInspDtl = POItemDtlHandler.getToInspect(POItemTabActivity.this, inspectionModal.InspectionLevel, pOItemDtl1.AvailableQty);
                if (toInspDtl != null) {

                    if (inspectionModal.QLMinor.equals("DEL0000013") || inspectionModal.QLMajor.equals("DEL0000013") || inspectionModal.ActivityID.equals("SYS0000001")) {
                        uniqueList.get(j).SampleSizeInspection = null /*+ "  " + toInspDtl[2]*/;

                        uniqueList.get(j).InspectedQty = 0;
                        uniqueList.get(j).AllowedinspectionQty = 0;

//                        String[] minorDefect = POItemDtlHandler.getDefectAccepted(POItemTabActivity.this, inspectionModal.QLMinor, sampleCode);
//                        if (minorDefect != null) {
                        uniqueList.get(j).MinorDefectsAllowed = 0;
//                        }

//                        String[] majorDefect = POItemDtlHandler.getDefectAccepted(POItemTabActivity.this, inspectionModal.QLMajor, sampleCode);
//                        if (majorDefect != null) {
                        uniqueList.get(j).MajorDefectsAllowed = 0;

                        uniqueList.get(j).CartonsPacked2 = 0;
                        uniqueList.get(j).CartonsPacked = 0;
//                        }
                    } else {
                        String sampleCode = toInspDtl[0];
                        uniqueList.get(j).SampleSizeInspection = toInspDtl[1] /*+ "  " + toInspDtl[2]*/;

                        uniqueList.get(j).InspectedQty = Integer.parseInt(toInspDtl[2]);
                        uniqueList.get(j).AllowedinspectionQty = Integer.parseInt(toInspDtl[2]);

                        String[] minorDefect = POItemDtlHandler.getDefectAccepted(POItemTabActivity.this, inspectionModal.QLMinor, sampleCode);
                        if (minorDefect != null) {
                            uniqueList.get(j).MinorDefectsAllowed = Integer.parseInt(minorDefect[1]);
                        } else {
                            uniqueList.get(j).MinorDefectsAllowed = 0;
                        }

                        String[] majorDefect = POItemDtlHandler.getDefectAccepted(POItemTabActivity.this, inspectionModal.QLMajor, sampleCode);
                        if (majorDefect != null) {
                            uniqueList.get(j).MajorDefectsAllowed = Integer.parseInt(majorDefect[1]);
                        } else {
                            uniqueList.get(j).MajorDefectsAllowed = 0;
                        }

                        if (uniqueList.get(j).POMasterPackQty > 0) {
                            float or = Float.valueOf(uniqueList.get(j).AcceptedQty); //previous calculate on order quantity  pOItemDtlList.get(i).OrderQty
                            uniqueList.get(j).CartonsPacked2 = (int) (or / uniqueList.get(j).POMasterPackQty);
                        }
                    }
                }
                /*if (uniqueList.get(j).POMasterPackQty > 0) {
                    float or = Float.valueOf(uniqueList.get(j).OrderQty);
                    uniqueList.get(j).CartonsPacked = (int) (or / uniqueList.get(j).POMasterPackQty);

                }*/
            }


            for (int i = 0; i < pOItemDtlList.size(); i++) {

                POItemDtl pOItemDtl = pOItemDtlList.get(i);
                String[] toInspDtl = POItemDtlHandler.getToInspect(POItemTabActivity.this, inspectionModal.InspectionLevel, pOItemDtl.AvailableQty);

                if (toInspDtl != null) {
                    if (inspectionModal.QLMinor.equals("DEL0000013") || inspectionModal.QLMajor.equals("DEL0000013") || inspectionModal.ActivityID.equals("SYS0000001")) {

                        pOItemDtlList.get(i).SampleSizeInspection = null;
                        pOItemDtlList.get(i).InspectedQty = 0;
                        pOItemDtlList.get(i).AllowedinspectionQty = 0;
//                        String[] minorDefect = POItemDtlHandler.getDefectAccepted(POItemTabActivity.this, inspectionModal.QLMinor, sampleCode);
//                        if (minorDefect != null) {
                        pOItemDtlList.get(i).MinorDefectsAllowed = 0;
//                        }
//                        String[] majorDefect = POItemDtlHandler.getDefectAccepted(POItemTabActivity.this, inspectionModal.QLMajor, sampleCode);
//                        if (majorDefect != null) {
                        pOItemDtlList.get(i).MajorDefectsAllowed = 0;
//                        }
                        pOItemDtlList.get(i).CartonsPacked2 = 0;
                        pOItemDtlList.get(i).CartonsPacked = 0;
                    } else {
                        String sampleCode = toInspDtl[0];
                        pOItemDtlList.get(i).SampleSizeInspection = toInspDtl[1] /*+ "  " + toInspDtl[2]*/;
                        pOItemDtlList.get(i).InspectedQty = Integer.parseInt(toInspDtl[2]);
                        pOItemDtlList.get(i).AllowedinspectionQty = Integer.parseInt(toInspDtl[2]);

                        String[] minorDefect = POItemDtlHandler.getDefectAccepted(POItemTabActivity.this, inspectionModal.QLMinor, sampleCode);
                        if (minorDefect != null) {
                            pOItemDtlList.get(i).MinorDefectsAllowed = Integer.parseInt(minorDefect[1]);
                        } else {
                            pOItemDtlList.get(i).MinorDefectsAllowed = 0;
                        }

                        String[] majorDefect = POItemDtlHandler.getDefectAccepted(POItemTabActivity.this, inspectionModal.QLMajor, sampleCode);
                        if (majorDefect != null) {
                            pOItemDtlList.get(i).MajorDefectsAllowed = Integer.parseInt(majorDefect[1]);
                        } else {
                            pOItemDtlList.get(i).MajorDefectsAllowed = 0;
                        }

                        if (pOItemDtlList.get(i).POMasterPackQty > 0) {
                            float or = Float.valueOf(pOItemDtlList.get(i).AcceptedQty);//previous calculate on order quantity  pOItemDtlList.get(i).OrderQty
                            pOItemDtlList.get(i).CartonsPacked2 = (int) (or / pOItemDtlList.get(i).POMasterPackQty);
                            pOItemDtlList.get(i).CartonsPacked = pOItemDtlList.get(i).CartonsPacked2;
                        }
                    }

                }

            }
        }
        updateFragments();

    }

    public void handleCalculate() {
//         Set<POItemDtl> set = new HashSet(pOItemDtlList);
        //create a new List from the Set


         /*for(int i=0;i<pOItemDtlList.size();i++){
             boolean isDistinct = false;
             for(int j=0;j<i;j++){
                 if(pOItemDtlList.get(i).QRPOItemHdrID == arr[j]){
                     isDistinct = true;
                     break;
                 }
             }
             if(!isDistinct){
                 System.out.print(arr[i]+" ");
             }
         }*/

        Set<POItemDtl> set = new TreeSet(new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                if (((POItemDtl) o1).QRPOItemHdrID.equalsIgnoreCase(((POItemDtl) o2).QRPOItemHdrID)) {
                    return 0;
                }
                return 1;
            }
        });
        set.addAll(pOItemDtlList);
        List<POItemDtl> lUNi = new ArrayList(set);
        if (uniqueList != null) {
            uniqueList.clear();
        } else {
            uniqueList = new ArrayList<>();
        }
//        uniqueList.addAll(lUNi);
        for (int k = 0; k < lUNi.size(); k++) {
            uniqueList.add(getCopyData(lUNi.get(k)));
        }
    }

    private POItemDtl1 getCopyData(POItemDtl poItemDtl) {
        POItemDtl1 poItemDtl1 = new POItemDtl1();
        poItemDtl1.pRowID = poItemDtl.pRowID;

        poItemDtl1.AllowedinspectionQty = poItemDtl.AllowedinspectionQty;
        poItemDtl1.SampleSizeInspection = poItemDtl.SampleSizeInspection;
        poItemDtl1.InspectedQty = poItemDtl.InspectedQty;
        poItemDtl1.POMasterPackQty = poItemDtl.POMasterPackQty;
        poItemDtl1.CriticalDefectsAllowed = poItemDtl.CriticalDefectsAllowed;
        poItemDtl1.MajorDefectsAllowed = poItemDtl.MajorDefectsAllowed;
        poItemDtl1.MinorDefectsAllowed = poItemDtl.MinorDefectsAllowed;
        poItemDtl1.CartonsPacked2 = poItemDtl.CartonsPacked2;
        poItemDtl1.CartonsPacked = poItemDtl.CartonsPacked;
        poItemDtl1.CartonsInspected = poItemDtl.CartonsInspected;

        poItemDtl1.QrItemID = poItemDtl.QrItemID;
        poItemDtl1.QRHdrID = poItemDtl.QRHdrID;
        poItemDtl1.QRPOItemHdrID = poItemDtl.QRPOItemHdrID;
        poItemDtl1.POItemDtlRowID = poItemDtl.POItemDtlRowID;
        poItemDtl1.SampleCodeID = poItemDtl.SampleCodeID;
        poItemDtl1.AvailableQty = poItemDtl.AvailableQty;
        poItemDtl1.AcceptedQty = poItemDtl.AcceptedQty;
        poItemDtl1.FurtherInspectionReqd = poItemDtl.FurtherInspectionReqd;
        poItemDtl1.Short = poItemDtl.Short;
        poItemDtl1.ShortStockQty = poItemDtl.ShortStockQty;
        poItemDtl1.AllowedCartonInspection = poItemDtl.AllowedCartonInspection;
        poItemDtl1.CriticalDefect = poItemDtl.CriticalDefect;
        poItemDtl1.MajorDefect = poItemDtl.MajorDefect;
        poItemDtl1.MinorDefect = poItemDtl.MinorDefect;
        poItemDtl1.recDirty = poItemDtl.recDirty;
        poItemDtl1.PONO = poItemDtl.PONO;
        poItemDtl1.ItemDescr = poItemDtl.ItemDescr;
        poItemDtl1.OrderQty = poItemDtl.OrderQty;
        poItemDtl1.EarlierInspected = poItemDtl.EarlierInspected;
        poItemDtl1.CustomerItemRef = poItemDtl.CustomerItemRef;
        poItemDtl1.HologramNo = poItemDtl.HologramNo;
        poItemDtl1.Digitals = poItemDtl.Digitals;
        poItemDtl1.testReportStatus = poItemDtl.testReportStatus;
        poItemDtl1.Barcode_InspectionResult = poItemDtl.Barcode_InspectionResult;
        poItemDtl1.ItemMeasurement_InspectionResult = poItemDtl.ItemMeasurement_InspectionResult;
        poItemDtl1.WorkmanShip_InspectionResult = poItemDtl.WorkmanShip_InspectionResult;
        poItemDtl1.PKG_Me_InspectionResult = poItemDtl.PKG_Me_InspectionResult;
        poItemDtl1.Overall_InspectionResult = poItemDtl.Overall_InspectionResult;
        poItemDtl1.IsImportant = poItemDtl.IsImportant;
        poItemDtl1.RetailPrice = poItemDtl.RetailPrice;


        return poItemDtl1;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("detail", GenUtils.serializeInspectionModal(inspectionModal));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            handleToGoBackActivity();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        handleToGoBackActivity();
        super.onBackPressed();
    }

    private void handleToGoBackActivity() {
        HandleToSaveAll();
        finish();
    }
}
