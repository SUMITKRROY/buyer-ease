package com.dashboard;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.buyereasefsl.ItemInspectionDetailHandler;
import com.buyereasefsl.R;
import com.constant.FClientConfig;
import com.constant.FEnumerations;
import com.constant.JsonKey;
import com.data.UserSession;
import com.hologram.StyleList;
import com.inspection.InspectionListActivity;
import com.login.ChangePassword;
import com.login.LogInActivity;
import com.login.LogInHandler;
import com.login.UserMaster;
import com.util.FslLog;
import com.util.GenUtils;
import com.util.SetInitiateStaticVariable;
import com.util.ShareHandler;

import java.util.ArrayList;
import java.util.List;

import static android.view.Gravity.END;

/**
 * Created by ADMIN on 11/3/2017.
 */

public class DashboardActivity extends AppCompatActivity implements JsonKey {


    String TAG = "DashboardActivity";
    //    UserSession userSession;
    static boolean active = false;
    TextView txtNotificationCount;
    RecyclerView recyclerView;
    List<DashboardModal> dashboardModalList;
    DashBoardAdaptor dashBoardAdaptor;
    ProgressDialog loadingDialog;

    ProgressBar progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_deshboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        FslLog.d(TAG,"DashboardActivity");
//        userSession = new UserSession(DashboardActivity.this);
//        getSupportActionBar().setTitle("Welcome " + userSession.getLoginData().userProfileName);
        UserMaster userMaster = LogInHandler.getUserMaster(DashboardActivity.this);
        getSupportActionBar().setTitle("Welcome " + userMaster.LoginName /*+ userSession.getProfileName()*/);
        TextView companyName = (TextView) findViewById(R.id.companyName);
        String st = GenUtils.truncate(new UserSession(DashboardActivity.this).getCompanyName(), FClientConfig.COMPANY_TRUNC_LENGTH);
        companyName.setText(st);
        SetInitiateStaticVariable.setInitiateStaticVariable(DashboardActivity.this);
        TextView appHeaderName = (TextView) findViewById(R.id.appHeaderName);

        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pInfo.versionName;
            appHeaderName.setText("Buyerease " + version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        //Anand delete itemMeasurement where pRowID is null
        ItemInspectionDetailHandler.deletItemMeasurementOnNullPRowID(DashboardActivity.this);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
//        LinearLayoutManager mLayoutManager = new LinearLayoutManager(DashboardActivity.this);
        GridLayoutManager mLayoutManager = new GridLayoutManager(DashboardActivity.this, 2);

        recyclerView.setLayoutManager(mLayoutManager);
        DividerItemDecoration dividerItemDecoration1 = new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration1);
        DividerItemDecoration dividerItemDecoration2 = new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.HORIZONTAL);
        recyclerView.addItemDecoration(dividerItemDecoration2);
        dashboardModalList = new ArrayList<>();


        recyclerView.addOnItemTouchListener(new DashBoardAdaptor.RecyclerTouchListener(getApplicationContext()
                , recyclerView, new DashBoardAdaptor.ClickListener() {
            @Override
            public void onClick(View view, int position) {

//                Intent intent = new Intent(DashboardActivity.this, ComplianceStatusActivity.class);
//                intent.putExtra("result", resultResponse.toString());
//                startActivity(intent);
                if (dashboardModalList != null) {
                    if (dashboardModalList.get(position).what == FEnumerations.E_VIEW_TYPE_INSPECTION) {
                        if (dashboardModalList.get(position).IsVisible) {
                            Intent intent = new Intent(DashboardActivity.this, InspectionListActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(DashboardActivity.this, "No right to access", Toast.LENGTH_SHORT).show();
                        }
                    } else if (dashboardModalList.get(position).what == FEnumerations.E_VIEW_TYPE_SYNC) {
                        if (dashboardModalList.get(position).IsVisible) {
                            Intent intent = new Intent(DashboardActivity.this, ChooseDataActivity.class);
                            intent.putExtra("mSyncViewType", FEnumerations.E_VIEW_TYPE_SYNC);
                            startActivity(intent);
                        } else {
                            Toast.makeText(DashboardActivity.this, "No right to access", Toast.LENGTH_SHORT).show();
                        }
                    } else if (dashboardModalList.get(position).what == FEnumerations.E_VIEW_TYPE_HOLOGRAM_STYLE) {
                        if (dashboardModalList.get(position).IsVisible) {
                            Intent intent = new Intent(DashboardActivity.this, ChooseDataActivity.class);
                            intent.putExtra("mSyncViewType", FEnumerations.E_VIEW_TYPE_HOLOGRAM_STYLE);
                            startActivity(intent);
                        } else {
                            Toast.makeText(DashboardActivity.this, "No right to access", Toast.LENGTH_SHORT).show();
                        }
                    } else if (dashboardModalList.get(position).what == FEnumerations.E_VIEW_TYPE_STYLE_LIST) {
                        if (dashboardModalList.get(position).IsVisible) {
                            Intent intent = new Intent(DashboardActivity.this, StyleList.class);
                            intent.putExtra("mSyncViewType", FEnumerations.E_VIEW_TYPE_STYLE_LIST);
                            startActivity(intent);
                        } else {
                            Toast.makeText(DashboardActivity.this, "No right to access", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        getLocalList();
    }


    private void getLocalList() {

//        for (int i = 0; i < 5; i++) {
        UserMaster userMaster = LogInHandler.getUserMaster(DashboardActivity.this);
        if (userMaster.userType == FEnumerations.E_USER_TYPE_ADMIN) {
            DashboardModal dashboardModal = new DashboardModal();
            dashboardModal.name = "Inspection";
            dashboardModal.icon = R.drawable.company;
            dashboardModal.what = FEnumerations.E_VIEW_TYPE_INSPECTION;
            dashboardModal.IsVisible = true;
            dashboardModalList.add(dashboardModal);

            DashboardModal dashboardModal3 = new DashboardModal();
            dashboardModal3.name = "Sync Style";
            dashboardModal3.icon = R.drawable.icon_download;
            dashboardModal3.IsVisible = true;
            dashboardModal3.what = FEnumerations.E_VIEW_TYPE_HOLOGRAM_STYLE;
            dashboardModalList.add(dashboardModal3);

            DashboardModal dashboardModal2 = new DashboardModal();
            dashboardModal2.name = "Sync Inspection";
            dashboardModal2.icon = R.drawable.ic_refresh;
            dashboardModal2.what = FEnumerations.E_VIEW_TYPE_SYNC;
            dashboardModal2.IsVisible = true;
            dashboardModalList.add(dashboardModal2);

            DashboardModal dashboardModal4 = new DashboardModal();
            dashboardModal4.name = "Style List";
            dashboardModal4.icon = R.drawable.icon_style;
            dashboardModal4.IsVisible = true;
            dashboardModal4.what = FEnumerations.E_VIEW_TYPE_STYLE_LIST;
            dashboardModalList.add(dashboardModal4);


        } else if (userMaster.userType == FEnumerations.E_USER_TYPE_QR) {
            DashboardModal dashboardModal = new DashboardModal();
            dashboardModal.name = "Inspection";
            dashboardModal.icon = R.drawable.company;
            dashboardModal.what = FEnumerations.E_VIEW_TYPE_INSPECTION;
            dashboardModal.IsVisible = true;
            dashboardModalList.add(dashboardModal);


            DashboardModal dashboardModal3 = new DashboardModal();
            dashboardModal3.name = "Sync Style";
            dashboardModal3.icon = R.drawable.icon_download;
            dashboardModal3.IsVisible = false;
            dashboardModal3.what = FEnumerations.E_VIEW_TYPE_HOLOGRAM_STYLE;
            dashboardModalList.add(dashboardModal3);

            DashboardModal dashboardModal2 = new DashboardModal();
            dashboardModal2.name = "Sync";
            dashboardModal2.icon = R.drawable.ic_refresh;
            dashboardModal2.what = FEnumerations.E_VIEW_TYPE_SYNC;
            dashboardModal2.IsVisible = true;
            dashboardModalList.add(dashboardModal2);

            DashboardModal dashboardModal4 = new DashboardModal();
            dashboardModal4.name = "Style List";
            dashboardModal4.icon = R.drawable.icon_style;
            dashboardModal4.IsVisible = false;
            dashboardModal4.what = FEnumerations.E_VIEW_TYPE_STYLE_LIST;
            dashboardModalList.add(dashboardModal4);
        } else if (userMaster.userType == FEnumerations.E_USER_TYPE_MR) {

            DashboardModal dashboardModal = new DashboardModal();
            dashboardModal.name = "Inspection";
            dashboardModal.icon = R.drawable.company;
            dashboardModal.what = FEnumerations.E_VIEW_TYPE_INSPECTION;
            dashboardModal.IsVisible = false;
            dashboardModalList.add(dashboardModal);


            DashboardModal dashboardModal3 = new DashboardModal();
            dashboardModal3.name = "Sync Style";
            dashboardModal3.icon = R.drawable.icon_download;
            dashboardModal3.what = FEnumerations.E_VIEW_TYPE_HOLOGRAM_STYLE;
            dashboardModal3.IsVisible = true;
            dashboardModalList.add(dashboardModal3);


            DashboardModal dashboardModal2 = new DashboardModal();
            dashboardModal2.name = "Sync";
            dashboardModal2.icon = R.drawable.ic_refresh;
            dashboardModal2.what = FEnumerations.E_VIEW_TYPE_SYNC;
            dashboardModal2.IsVisible = false;
            dashboardModalList.add(dashboardModal2);

            DashboardModal dashboardModal4 = new DashboardModal();
            dashboardModal4.name = "Style List";
            dashboardModal4.icon = R.drawable.icon_style;
            dashboardModal4.IsVisible = true;
            dashboardModal4.what = FEnumerations.E_VIEW_TYPE_STYLE_LIST;
            dashboardModalList.add(dashboardModal4);
        }

        setAdaptor();
    }

    private void setAdaptor() {
        if (dashBoardAdaptor == null) {
            dashBoardAdaptor = new DashBoardAdaptor(DashboardActivity.this
                    , dashboardModalList);
            recyclerView.setAdapter(dashBoardAdaptor);
        } else {
            dashBoardAdaptor.notifyDataSetChanged();
        }


    }


    private void handleChangePassword() {
        Intent intent = new Intent(DashboardActivity.this, ChangePassword.class);
        intent.putExtra("request", FEnumerations.REQUEST_FOR_CHANGE_PASSWORD);
        startActivity(intent);

    }


    private void handleLogOut() {
        UserSession userSession = new UserSession(DashboardActivity.this);
        userSession.OnLogOut();
        Intent intentLogOut = new Intent(DashboardActivity.this, LogInActivity.class);
        intentLogOut.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
                | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intentLogOut);
    }

    @Override
    public void onBackPressed() {
        /*if (this.findViewById(R.id.mainFlatFrofileContainer).getVisibility() == View.GONE) {
            Fragment notifyFragment = getSupportFragmentManager().findFragmentById(R.id.frameContainer);

            try {
                if (notifyFragment != null) {
                    SocietyLog.d(TAG, "FragmentToRemove: " + notifyFragment);
                    getSupportFragmentManager().beginTransaction().remove(notifyFragment).commitAllowingStateLoss();
                }
            } catch (Exception e) {
                SocietyLog.d(TAG, "Exception at time remove fragment of notify");
                e.printStackTrace();
            }


            findViewById(R.id.mainFlatFrofileContainer).setVisibility(View.VISIBLE);
            hideActiobIcon();
            return;
        }*/
        super.onBackPressed();
    }

    private void showActionIcon() {
        this.getSupportActionBar().setTitle("Notification");
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Drawable backArrow = getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp);
        backArrow.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP);
        this.getSupportActionBar().setHomeAsUpIndicator(backArrow);

    }

    private void hideActiobIcon() {
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
//        if (userSession == null)
//            userSession = new UserSession(DashboardActivity.this);
//
//        this.getSupportActionBar().setTitle("Welcome " + userSession.getLoginData().userProfileName);

    }


    private void showProgressDialog(String message) {

        if (loadingDialog == null)
            loadingDialog = new ProgressDialog(DashboardActivity.this);
//        loadingDialog.setTitle("Please wait");
        loadingDialog.setMessage(message);
        if (loadingDialog != null && !loadingDialog.isShowing()) {
//            loadingDialog.show();
            GenUtils.safeProgressDialogShow(TAG, DashboardActivity.this, loadingDialog);
        }

    }

    private void hideDialog() {
        if (loadingDialog != null && loadingDialog.isIndeterminate() && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_society, menu);//Menu Resource, Menu


        menu.findItem(R.id.userIcon).setVisible(true);
        MenuItem profileIconView = menu.findItem(R.id.userIcon);
        MenuItemCompat.setActionView(profileIconView, R.layout.menu_profile_circle);
        View view1 = MenuItemCompat.getActionView(profileIconView);
        ImageView imageView = (ImageView) view1.findViewById(R.id.iconProfile);


        imageView.setImageResource(R.drawable.default_governing);


        profileIconView.getActionView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPopupMenu(view);
            }
        });


        return true;
    }

    private void createPopupMenu(View view) {
        /*Context wrapper = new ContextThemeWrapper(activity, R.style.popupMenuStyle);*/

        PopupMenu popup = new PopupMenu(DashboardActivity.this, view, END);
        Menu menu = popup.getMenu();
        popup.getMenuInflater().inflate(R.menu.sub_dashboard_menu,
                menu);


        popup.show();
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.logOut:
                        handleLogOut();
                        break;
                    /*case R.id.sharelog:
                        ShareHandler.HandleLogShare(DashboardActivity.this);
                        break;*/
                    /*case R.id.changePassword:
                        handleChangePassword();
                        break;*/


                    default:
                        break;
                }

                return true;
            }
        });

    }
}
