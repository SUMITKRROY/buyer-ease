package com.hologram;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.buyereasefsl.R;

import com.constant.FClientConfig;
import com.constant.FClientConstants;
import com.constant.FEnumerations;
import com.dashboard.ChooseDataActivity;
import com.data.UserSession;
import com.util.FslLog;
import com.util.GenUtils;
import com.util.SetInitiateStaticVariable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class StyleList extends AppCompatActivity implements StyleSelectorAdapter.OnItemClickListener, TextWatcher {
    RecyclerView recyclerView;
    List<HologramModal> hologramModalList;
    StyleSelectorAdapter styleSelectorAdapter;
    EditText editSearchProduct;
    String TAG = "StyleList";
    ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.style_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Select Style");
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView companyName = (TextView) findViewById(R.id.companyName);
        String st = GenUtils.truncate(new UserSession(StyleList.this).getCompanyName(), FClientConfig.COMPANY_TRUNC_LENGTH);
        companyName.setText(st);
        SetInitiateStaticVariable.setInitiateStaticVariable(StyleList.this);

        editSearchProduct = (EditText) findViewById(R.id.editSearchProduct);
        editSearchProduct.addTextChangedListener(this);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(StyleList.this);
//        GridLayoutManager mLayoutManager = new GridLayoutManager(InspectionListActivity.this, 2);
//        recyclerView.addItemDecoration(new DividerItemDecoration(this,
//                DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(mLayoutManager);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        hologramModalList = new ArrayList<>();
        getLocalList();
    }

    private void getLocalList() {


        progressBar.setVisibility(View.VISIBLE);
        if (hologramModalList == null)
            hologramModalList = new ArrayList<>();
        else hologramModalList.clear();

        Thread t = new Thread(new Runnable() {
            public void run() {
                hologramModalList.addAll(StyleHandler.getStyleList(StyleList.this, ""));
                setAdaptor();
            }
        });

        t.start();

    }

    private void setAdaptor() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                handleUi();
            }
        });

    }

    private void handleUi() {

        progressBar.setVisibility(View.GONE);
        if (styleSelectorAdapter == null) {
            styleSelectorAdapter = new StyleSelectorAdapter(StyleList.this, recyclerView
                    , hologramModalList, this);
            recyclerView.setAdapter(styleSelectorAdapter);
        } else {
            styleSelectorAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClick(HologramModal mHologramModal) {
//        Thread t = new Thread(new Runnable() {
//            public void run() {
        handleonClickContainOneHologram(mHologramModal);
//            }
//        });
//        t.start();
    }

    private void handleonClickContainOneHologram(HologramModal mHologramModal) {
        Intent intent = new Intent(StyleList.this, HologramHandler.class);
        intent.putExtra("style", GenUtils.serializeStyle(mHologramModal));
        startActivityForResult(intent, FEnumerations.REQUEST_FOR_ADD_HOLOGRAM);
    }

    public void handleMultipleSelectedHologram() {
        progressBar.setVisibility(View.VISIBLE);
        Thread t = new Thread(new Runnable() {
            public void run() {
                if (hologramModalList != null && hologramModalList.size() > 0) {
                    ArrayList<HologramModal> selectedList = new ArrayList<>();
                    for (int i = 0; i < hologramModalList.size(); i++) {
                        if (hologramModalList.get(i).IsCheckedToSync) {
                            selectedList.add(hologramModalList.get(i));
                        }
                    }
                    List<HologramModal> lStyle = null;
                    if (selectedList.size() > 0) {
                        lStyle = checkList(selectedList);
                        FslLog.e(TAG, " selected list :  " + lStyle.size());
                    }
                    handleToSelected(selectedList, lStyle);
                } else {
                    if (progressBar != null)
                        progressBar.setVisibility(View.GONE);
                }
            }
        });
        t.start();
    }

    public List<HologramModal> checkList(ArrayList<HologramModal> selectedList) {
        Set<HologramModal> set = new TreeSet(new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {

                if ((TextUtils.isEmpty(((HologramModal) o1).hologram_no)
                        || "null".equalsIgnoreCase(((HologramModal) o1).hologram_no))
                        && (TextUtils.isEmpty(((HologramModal) o2).hologram_no)
                        || "null".equalsIgnoreCase(((HologramModal) o2).hologram_no))
                        ) {
                    return 0;
                } else if (((HologramModal) o1).hologram_no.equalsIgnoreCase(((HologramModal) o2).hologram_no)) {
                    return 0;
                }
                return 1;
            }
        });
        set.addAll(selectedList);
        List<HologramModal> lUNi = new ArrayList(set);
//        uniqueList.addAll(lUNi);
        return lUNi;

    }

    private void handleToSelected(ArrayList<HologramModal> selectedList, List<HologramModal> lStyle) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
                if (selectedList.size() == 0) {
                    Toast.makeText(StyleList.this, "Select to add  hologram on multiple style", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (lStyle != null && lStyle.size() > 1) {
                    GenUtils.forInfoAlertDialog(StyleList.this, FClientConstants.ACTION_OK,
                            "Alert",
                            "You couldn't add hologram on multiple style Because you have already different hologram.",
                            new GenUtils.AlertDialogClickListener() {
                                @Override
                                public void onPositiveButton() {

                                }

                                @Override
                                public void onNegativeButton() {

                                }
                            });

                    return;
                }

                if (selectedList.size() == 1) {
                    handleonClickContainOneHologram(selectedList.get(0));
                } else {
                    Intent intent = new Intent(StyleList.this, HologramHandler.class);
                    intent.putExtra("IsMultiple", true);
                    intent.putExtra("list", GenUtils.serializeHologramList(selectedList));
                    startActivityForResult(intent, FEnumerations.REQUEST_FOR_ADD_HOLOGRAM);
                }


            }
        });


    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        handleToGetSearchBy(charSequence.toString());
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    private void handleToGetSearchBy(String strSearch) {
        if (styleSelectorAdapter != null)
            styleSelectorAdapter.filter(strSearch);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        FslLog.d(TAG, "onActivityResult requestCode - " + requestCode + " resultCode - " + resultCode);
        if ((requestCode == FEnumerations.REQUEST_FOR_ADD_HOLOGRAM || requestCode == 22) && resultCode == RESULT_OK) {
            refreshUI();
        }

    }

    private void refreshUI() {
        FslLog.d(TAG, "Call to refresh UI...");
        progressBar.setVisibility(View.VISIBLE);

        Thread t = new Thread(new Runnable() {
            public void run() {
                List<HologramModal> lSearchStyle = StyleHandler.getStyleList(StyleList.this, "");
                if (lSearchStyle != null) {
                    hologramModalList.clear();
                    hologramModalList.addAll(lSearchStyle);
                }
                setAdaptor();
            }
        });

        t.start();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.style_sync, menu);//Menu Resource, Menu
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();

                break;
            case R.id.submit:
                handleSubmitToSync();
                return true;
            case R.id.submitAdd:
                handleMultipleSelectedHologram();
                return true;
        }

        return false;
    }

    private void handleSubmitToSync() {
        Thread t = new Thread(new Runnable() {
            public void run() {
                if (hologramModalList != null && hologramModalList.size() > 0) {
                    ArrayList<String> idsList = new ArrayList<>();
                    for (int i = 0; i < hologramModalList.size(); i++) {
                        if (hologramModalList.get(i).IsCheckedToSync) {
                            idsList.add(hologramModalList.get(i).ItemID);
                        }
                    }
                    updateUiToSync(idsList);
                }
            }
        });
        t.start();
    }

    private void updateUiToSync(ArrayList<String> idsList) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (idsList != null && idsList.size() > 0) {
                    GenUtils.forConfirmationAlertDialog(StyleList.this,
                            FClientConstants.TEXT_BUTTON_YES,
                            FClientConstants.TEXT_BUTTON_NO
                            , null,
                            "Are you sure. Do you want to sync " + idsList.size() + " Hologram "
                            , new GenUtils.AlertDialogClickListener() {
                                @Override
                                public void onPositiveButton() {
                                    Intent intent = new Intent(StyleList.this, ChooseDataActivity.class);
                                    intent.putExtra("type", FEnumerations.E_VIEW_ONLY_SEND);
                                    intent.putExtra("mSyncViewType", FEnumerations.E_VIEW_TYPE_HOLOGRAM_STYLE);
                                    intent.putStringArrayListExtra("list", idsList);
                                    startActivityForResult(intent, 22);

                                }

                                @Override
                                public void onNegativeButton() {

                                }
                            });
                } else {
                    Toast.makeText(StyleList.this, "Not selected to sync", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
