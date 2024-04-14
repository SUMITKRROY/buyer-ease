package com.podetail;

import android.app.Activity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.buyereasefsl.R;
import com.constant.FEnumerations;
import com.util.FslLog;
import com.util.GenUtils;

import java.util.List;

/**
 * Created by ADMIN on 2/2/2018.
 */

public class POItemMultipleHandlerAdaptor extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemBackClick(POItemDtl1 item);


    }


    private Activity activity;
    private List<POItemDtl1> data;
    //    private List<POItemDtl> searchList;
    //    public Animator mCurrentAnimator;
//    private int mShortAnimationDuration;
    int mViewType;
    String TAG = "POItemListAdaptor";
//    View yourFragmentUI;
//    private final int VIEW_TYPE_ITEM = 0;
//    private final int VIEW_TYPE_LOADING = 1;


    //    OnLoadMoreListener mOnLoadMoreListener;
    RecyclerView mRecyclerView;
    private boolean isLoading;
    private int visibleThreshold = 2;
    private int lastVisibleItem,
            totalItemCount;

    //    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
//        this.mOnLoadMoreListener = mOnLoadMoreListener;
//    }
    String pRowIdOfInspectLevel, pRowIdOfQualityMinorLevel, pRowIdOfQualityMajorLevel;

    int mInsLevel;
    List<POItemDtl> holeList;

    public POItemMultipleHandlerAdaptor(Activity a
            , RecyclerView recyclerView
            , int lViewType
            , List<POItemDtl> pOItemDtlList,
                                        List<POItemDtl1> uniList
            , OnItemClickListener m
            , String RowIdOfInspectLevel
            , String RowIdOfQualityMinorLevel
            , String RowIdOfQualityMajorLevel
            , int insLevel) {

        activity = a;
        mRecyclerView = recyclerView;

        holeList = pOItemDtlList;
        data = uniList;
//        this.yourFragmentUI = yourFragmentUI;
        mViewType = lViewType;
        this.listener = m;
//        this.searchList = new ArrayList<POItemDtl>();
//        this.searchList.addAll(visitorModelArrayList);
        pRowIdOfInspectLevel = RowIdOfInspectLevel;
        pRowIdOfQualityMinorLevel = RowIdOfQualityMinorLevel;
        pRowIdOfQualityMajorLevel = RowIdOfQualityMajorLevel;
        mInsLevel = insLevel;
        // Retrieve and cache the system's default "short" animation time.
//        mShortAnimationDuration = activity.getResources().getInteger(
//                android.R.integer.config_shortAnimTime);


        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
//        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                totalItemCount = linearLayoutManager.getItemCount();
//                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
//                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
//                    if (mOnLoadMoreListener != null) {
//                        mOnLoadMoreListener.onLoadMore();
//                    }
//                    isLoading = true;
//                }
//            }
//        });
    }

    @Override
    public int getItemViewType(int position) {
        if (mViewType == FEnumerations.VIEW_TYPE_ITEM_QUALITY) {
            return FEnumerations.VIEW_TYPE_ITEM_QUALITY;
        } else if (mViewType == FEnumerations.VIEW_TYPE_ITEM_WORKMAN_SHIP) {
            return FEnumerations.VIEW_TYPE_ITEM_WORKMAN_SHIP;
        } else if (mViewType == FEnumerations.VIEW_TYPE_ITEM_CARTON) {
            return FEnumerations.VIEW_TYPE_ITEM_CARTON;
        } else {
            return FEnumerations.VIEW_TYPE_MORE;
        }

//        return data.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    public void update(int viewType) {
        mViewType = viewType;

        notifyDataSetChanged();
    }

    public void updateLevel(int insLevel) {
        mInsLevel = insLevel;

        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        // create a new view
//        LayoutInflater inflater = LayoutInflater.from(
//                parent.getContext());
//        View v =
//                inflater.inflate(R.layout.visitor_row, parent, false);
//        // set the view's size, margins, paddings and layout parameters
//        ViewHolder vh = new ViewHolder(v);

        if (viewType == FEnumerations.VIEW_TYPE_ITEM_QUALITY) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quality_row, parent, false);
            return new QualityViewHolder(view);
        } else if (viewType == FEnumerations.VIEW_TYPE_ITEM_WORKMAN_SHIP) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inspect_workmanship_row, parent, false);
            return new WorkManShipViewHolder(view);
        } else if (viewType == FEnumerations.VIEW_TYPE_ITEM_CARTON) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.carton_detail_row, parent, false);
            return new CartonViewHolder(view);
        } else if (viewType == FEnumerations.VIEW_TYPE_MORE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.more_detail_row, parent, false);
            return new MoreDetailViewHolder(view);
        }
        return null;

//        return vh;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        /*if (mViewType == FEnumerations.VIEW_TYPE_ITEM_QUALITY) {
            if (holder instanceof QualityViewHolder) {
                final POItemDtl pOItemDtl = holeList.get(position);
                final QualityViewHolder qualityViewHolder = (QualityViewHolder) holder;
               *//* if (position == 0) {
                    qualityViewHolder.qualityPo.setText(pOItemDtl.POhdr);
                    qualityViewHolder.qualityItem.setText(pOItemDtl.Itemhdr);

                    qualityViewHolder.qualityOrderLabel1.setText(pOItemDtl.Orderhdr);
                    qualityViewHolder.qualityAvailableLabel1.setText(pOItemDtl.Availablehdr);
                    qualityViewHolder.qualityAcceptedLabel1.setText(pOItemDtl.Acceptedhdr);
                    qualityViewHolder.qualityShortLabel1.setText(pOItemDtl.Shorthdr);
//                    qualityViewHolder.qualityShortStockQtyLabel1.setText(pOItemDtl.ShortStockQtyhdr);
                    qualityViewHolder.qualityInspectLaterLabel1.setText(pOItemDtl.InspectLaterHr);

                    qualityViewHolder.qualityInspectedTillDateLabel1.setText(pOItemDtl.InspectedTillDatehdr);

                    qualityViewHolder.qualityAvailableLabel.setVisibility(View.GONE);
                    qualityViewHolder.qualityInspectedTillDateLabel.setVisibility(View.GONE);
                    qualityViewHolder.qualityOrderLabel.setVisibility(View.GONE);
                    qualityViewHolder.qualityAcceptedLabel.setVisibility(View.GONE);
                    qualityViewHolder.qualityShortLabel.setVisibility(View.GONE);
//                    qualityViewHolder.qualityShortStockQtyLabel.setVisibility(View.GONE);
                    qualityViewHolder.qualityInspectLaterLabel.setVisibility(View.GONE);

                    qualityViewHolder.qualityAvailableLabel1.setVisibility(View.VISIBLE);
                    qualityViewHolder.qualityInspectedTillDateLabel1.setVisibility(View.VISIBLE);
                    qualityViewHolder.qualityOrderLabel1.setVisibility(View.VISIBLE);
                    qualityViewHolder.qualityAcceptedLabel1.setVisibility(View.VISIBLE);
                    qualityViewHolder.qualityShortLabel1.setVisibility(View.VISIBLE);
//                    qualityViewHolder.qualityShortStockQtyLabel1.setVisibility(View.VISIBLE);
                    qualityViewHolder.qualityInspectLaterLabel1.setVisibility(View.VISIBLE);
                    qualityViewHolder.descriptionContainer.setVisibility(View.GONE);

                } else {*//*
//                    qualityViewHolder.qualityAvailableLabel1.setVisibility(View.GONE);
//                    qualityViewHolder.qualityOrderLabel1.setVisibility(View.GONE);
//                    qualityViewHolder.qualityInspectedTillDateLabel1.setVisibility(View.GONE);
//                    qualityViewHolder.qualityAcceptedLabel1.setVisibility(View.GONE);
//                    qualityViewHolder.qualityShortLabel1.setVisibility(View.GONE);
//                    qualityViewHolder.qualityShortStockQtyLabel1.setVisibility(View.GONE);
//                    qualityViewHolder.qualityInspectLaterLabel1.setVisibility(View.GONE);

                qualityViewHolder.descriptionContainer.setVisibility(View.VISIBLE);
                qualityViewHolder.qualityAvailableLabel.setVisibility(View.VISIBLE);
                qualityViewHolder.qualityInspectedTillDateLabel.setVisibility(View.VISIBLE);
                qualityViewHolder.qualityOrderLabel.setVisibility(View.VISIBLE);
                qualityViewHolder.qualityAcceptedLabel.setVisibility(View.VISIBLE);
                qualityViewHolder.qualityShortLabel.setVisibility(View.VISIBLE);
//                    qualityViewHolder.qualityShortStockQtyLabel.setVisibility(View.VISIBLE);
                qualityViewHolder.qualityInspectLaterLabel.setVisibility(View.VISIBLE);

                qualityViewHolder.qualityPo.setText(pOItemDtl.PONO);
                qualityViewHolder.qualityItem.setText(pOItemDtl.CustomerItemRef);
                qualityViewHolder.itenDescription.setText(pOItemDtl.ItemDescr);
                qualityViewHolder.qualityPo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        handleOnClick(position);
                    }
                });
                qualityViewHolder.qualityItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        handleOnClick(position);
                    }
                });
                qualityViewHolder.itenDescription.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        handleOnClick(position);
                    }
                });

                qualityViewHolder.qualityOrderLabel.setText(pOItemDtl.OrderQty);
                if (!TextUtils.isEmpty(pOItemDtl.OrderQty) && !pOItemDtl.OrderQty.equals("null")) {
                    float f = Float.valueOf(pOItemDtl.OrderQty);
                    qualityViewHolder.qualityOrderLabel.setText((int) f + "");

                    if ((int) f < pOItemDtl.AvailableQty) {
                        qualityViewHolder.qualityAvailableLabel.setTextColor(GenUtils.getColorResource(activity, R.color.red));
                    } else {
                        qualityViewHolder.qualityAvailableLabel.setTextColor(GenUtils.getColorResource(activity, R.color.colorBlack));
                    }

                    if (pOItemDtl.AvailableQty < pOItemDtl.AcceptedQty) {
                        qualityViewHolder.qualityAcceptedLabel.setTextColor(GenUtils.getColorResource(activity, R.color.red));
                    } else {
                        qualityViewHolder.qualityAcceptedLabel.setTextColor(GenUtils.getColorResource(activity, R.color.colorBlack));
                    }
                }
                if (pOItemDtl.Short < 0) {
                    qualityViewHolder.qualityShortLabel.setTextColor(GenUtils.getColorResource(activity, R.color.red));
                } else {
                    qualityViewHolder.qualityShortLabel.setTextColor(GenUtils.getColorResource(activity, R.color.colorBlack));
                }
                qualityViewHolder.qualityOrderLabel.setEnabled(false);
                qualityViewHolder.qualityAvailableLabel.setText(pOItemDtl.AvailableQty + "");
                qualityViewHolder.qualityAcceptedLabel.setText(pOItemDtl.AcceptedQty + "");
//                changeOnAccepted(position, pOItemDtl);
                qualityViewHolder.qualityShortLabel.setText(pOItemDtl.Short + "");
//                    qualityViewHolder.qualityShortStockQtyLabel.setText(pOItemDtl.ShortStockQty + "");

                qualityViewHolder.qualityInspectedTillDateLabel.setText(pOItemDtl.EarlierInspected + "");
                qualityViewHolder.qualityInspectedTillDateLabel.setEnabled(false);

                if (pOItemDtl.FurtherInspectionReqd == 0) {
                    qualityViewHolder.qualityInspectLaterLabel.setChecked(false);
                } else {
                    qualityViewHolder.qualityInspectLaterLabel.setChecked(true);
                }
                qualityViewHolder.qualityInspectLaterLabel.setTag(pOItemDtl);

                qualityViewHolder.qualityInspectLaterLabel.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v;

                        POItemDtl inspModal = (POItemDtl) cb.getTag();
                        if (cb.isChecked()) {
                            inspModal.FurtherInspectionReqd = 1;
                            data.get(position).FurtherInspectionReqd = 1;
                        } else {
                            inspModal.FurtherInspectionReqd = 0;
                            data.get(position).FurtherInspectionReqd = 0;
                        }
                    }
                });
                //we need to update adapter once we finish with editing
                qualityViewHolder.qualityAvailableLabel.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (!hasFocus) {
//                                final int position = v.getId();
                            final EditText Caption = (EditText) v;
                            if (!TextUtils.isEmpty(Caption.getText().toString())) {
//                                getAvailable(pOItemDtl);
                                pOItemDtl.AvailableQty = Integer.parseInt(Caption.getText().toString());
                                pOItemDtl.AcceptedQty = pOItemDtl.AvailableQty;
//                                changeOnAccepted(position, pOItemDtl);
//                                changeOnAvailable(position, pOItemDtl);

                                if (mRecyclerView != null && !mRecyclerView.isComputingLayout()) {
                                    notifyDataSetChanged();
                                    if (activity instanceof POItemListActivity) {
                                        ((POItemTabActivity) activity).handleToUpdateTotal();
                                        ((POItemTabActivity) activity).changeOnAvailable();
                                    }
                                }
                            }
                        } else if (hasFocus) {
                            qualityViewHolder.qualityAvailableLabel.setSelectAllOnFocus(true);
                            qualityViewHolder.qualityAvailableLabel.selectAll();
                        }
                    }
                });
                //we need to update adapter once we finish with editing
                qualityViewHolder.qualityAcceptedLabel.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (!hasFocus) {
//                                final int position = v.getId();
                            final EditText Caption = (EditText) v;
                            if (!TextUtils.isEmpty(Caption.getText().toString())) {
                                pOItemDtl.AcceptedQty = Integer.parseInt(Caption.getText().toString());

//                                changeOnAccepted(position, pOItemDtl);
                                if (mRecyclerView != null && !mRecyclerView.isComputingLayout()) {
                                    notifyDataSetChanged();
                                    if (activity instanceof POItemListActivity) {
                                        ((POItemTabActivity) activity).handleToUpdateTotal();
                                        ((POItemTabActivity) activity).changeOnAvailable();
                                    }
                                }
                            }
                        } else if (hasFocus) {
                            qualityViewHolder.qualityAcceptedLabel.setSelectAllOnFocus(true);
                            qualityViewHolder.qualityAcceptedLabel.selectAll();
                        }
                    }
                });
                qualityViewHolder.qualityAcceptedLabel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mRecyclerView != null && !mRecyclerView.isComputingLayout()) {
                            notifyDataSetChanged();
                            if (activity instanceof POItemListActivity) {
                                ((POItemTabActivity) activity).handleToUpdateTotal();
                                ((POItemTabActivity) activity).changeOnAvailable();
                            }
                        }
                    }
                });
                //we need to update adapter once we finish with editing
                qualityViewHolder.qualityShortLabel.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (!hasFocus) {
//                                final int position = v.getId();
                            final EditText Caption = (EditText) v;
                            if (!TextUtils.isEmpty(Caption.getText().toString())) {
                                data.get(position).Short = Integer.parseInt(Caption.getText().toString());
                            }
                        } else if (hasFocus) {
                            qualityViewHolder.qualityShortLabel.setSelectAllOnFocus(true);
                            qualityViewHolder.qualityShortLabel.selectAll();
                        }
                    }
                });
                //we need to update adapter once we finish with editing
                    *//*qualityViewHolder.qualityShortStockQtyLabel.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        public void onFocusChange(View v, boolean hasFocus) {
                            if (!hasFocus) {
//                                final int position = v.getId();
                                final EditText Caption = (EditText) v;
                                if (!TextUtils.isEmpty(Caption.getText().toString())) {
                                    data.get(position).ShortStockQty = Integer.parseInt(Caption.getText().toString());

                                }
                            } else if (hasFocus) {
                                qualityViewHolder.qualityShortStockQtyLabel.setSelectAllOnFocus(true);
                                qualityViewHolder.qualityShortStockQtyLabel.selectAll();
                            }
                        }
                    });*//*
                qualityViewHolder.deleteItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.onDeleteItemClick(pOItemDtl);
                    }
                });
            }


//            }
        } else*/
        if (mViewType == FEnumerations.VIEW_TYPE_ITEM_WORKMAN_SHIP) {
            if (holder instanceof WorkManShipViewHolder) {
                FslLog.d(TAG, " workmanship item creating... ");
                final POItemDtl1 POItemDtl = data.get(position);
                final WorkManShipViewHolder workManShipViewHolder = (WorkManShipViewHolder) holder;
        /*        if (position == 0) {
                    workManShipViewHolder.workPo.setText(POItemDtl.POhdr);
                    workManShipViewHolder.workmanItem.setText(POItemDtl.Itemhdr);

                    workManShipViewHolder.workmanshipToInspectionLabel.setText(POItemDtl.workmanshipToInspectionhdr);
//                    workManShipViewHolder.workmanshipInspectedLabel1.setText(POItemDtl.Inspectedhdr);
                    workManShipViewHolder.workmanshipCriticalLabel.setText(POItemDtl.Criticalhdr);
                    workManShipViewHolder.workmanshipMajorLabel.setText(POItemDtl.Majorhdr);
                    workManShipViewHolder.workmanshipMinorLabel.setText(POItemDtl.Minorhdr);

//                    workManShipViewHolder.workmanshipInspectedLabel1.setVisibility(View.VISIBLE);
                    workManShipViewHolder.workmanshipInspectedLabel.setVisibility(View.GONE);
                } else {*/
//                    workManShipViewHolder.workmanshipInspectedLabel1.setVisibility(View.GONE);
                workManShipViewHolder.workmanshipInspectedLabel.setVisibility(View.VISIBLE);
                String strPo = null;
                for (int i = 0; i < holeList.size(); i++) {
                    if (POItemDtl.QRPOItemHdrID.equals(holeList.get(i).QRPOItemHdrID)) {
                        if (TextUtils.isEmpty(strPo)) {
                            strPo = holeList.get(i).PONO;
                        } else {
                            strPo = strPo + "\n" + holeList.get(i).PONO;
                        }
                    }
                }

//                if (POItemDtl.IsImportant == 1) {
//                    workManShipViewHolder.iconImportant.setVisibility(View.VISIBLE);
//                } else {
//                    workManShipViewHolder.iconImportant.setVisibility(View.GONE);
//                }
                workManShipViewHolder.workPo.setText(strPo);
                workManShipViewHolder.workmanItem.setText(POItemDtl.CustomerItemRef);
                if (!TextUtils.isEmpty(POItemDtl.SampleSizeInspection) && !POItemDtl.SampleSizeInspection.equals("null")) {
                    workManShipViewHolder.workmanshipToInspectionLabel.setText(POItemDtl.SampleSizeInspection + " " + POItemDtl.AllowedinspectionQty);
                } else {
                    if (pRowIdOfQualityMinorLevel.equals("DEL0000013")
                            || pRowIdOfQualityMajorLevel.equals("DEL0000013")
                            || pRowIdOfInspectLevel.equals("SYS0000001")) {
                        workManShipViewHolder.workmanshipToInspectionLabel.setText(POItemDtl.AllowedinspectionQty + "");
                    } else {
                        String[] toInspDtl = POItemDtlHandler.getToInspect(activity, pRowIdOfInspectLevel, POItemDtl.AvailableQty);
                        if (toInspDtl != null) {
                            workManShipViewHolder.workmanshipToInspectionLabel.setText(toInspDtl[1] + " " + toInspDtl[2]);
                        } else {
                            workManShipViewHolder.workmanshipToInspectionLabel.setText(POItemDtl.AllowedinspectionQty + "");
                        }
                    }
                }
                workManShipViewHolder.workmanshipInspectedLabel.setText(POItemDtl.InspectedQty + "");
                if (mInsLevel == FEnumerations.E_INSPECTION_REPORT_LEVEL) {
                    workManShipViewHolder.workmanshipCriticalLabel.setText(POItemDtl.CriticalDefect + "");
                    workManShipViewHolder.workmanshipMajorLabel.setText(POItemDtl.MajorDefect + "");
                    workManShipViewHolder.workmanshipMinorLabel.setText(POItemDtl.MinorDefect + "");
                } else {
                    workManShipViewHolder.workmanshipCriticalLabel.setText(POItemDtl.CriticalDefect + "/" + POItemDtl.CriticalDefectsAllowed);
                    workManShipViewHolder.workmanshipMajorLabel.setText(POItemDtl.MajorDefect + "/" + POItemDtl.MajorDefectsAllowed);
                    workManShipViewHolder.workmanshipMinorLabel.setText(POItemDtl.MinorDefect + "/" + POItemDtl.MinorDefectsAllowed);
                    if (POItemDtl.CriticalDefect > POItemDtl.CriticalDefectsAllowed) {
                        workManShipViewHolder.workmanshipCriticalLabel.setTextColor(GenUtils.getColorResource(activity, R.color.red));
                    } else {
                        workManShipViewHolder.workmanshipCriticalLabel.setTextColor(GenUtils.getColorResource(activity, R.color.colorBlack));
                    }
                    if (POItemDtl.MajorDefect > POItemDtl.MajorDefectsAllowed) {
                        workManShipViewHolder.workmanshipMajorLabel.setTextColor(GenUtils.getColorResource(activity, R.color.red));
                    } else {
                        workManShipViewHolder.workmanshipMajorLabel.setTextColor(GenUtils.getColorResource(activity, R.color.colorBlack));
                    }
                    if (POItemDtl.MinorDefect > POItemDtl.MinorDefectsAllowed) {
                        workManShipViewHolder.workmanshipMinorLabel.setTextColor(GenUtils.getColorResource(activity, R.color.red));
                    } else {
                        workManShipViewHolder.workmanshipMinorLabel.setTextColor(GenUtils.getColorResource(activity, R.color.colorBlack));
                    }
                }
                workManShipViewHolder.workPo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        handleOnClick(position);
                    }
                });
                workManShipViewHolder.workmanItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        handleOnClick(position);
                    }
                });

                workManShipViewHolder.workmanshipCriticalLabel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        handleOnClick(position);
                    }
                });
                workManShipViewHolder.workmanshipMajorLabel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        handleOnClick(position);
                    }
                });
                workManShipViewHolder.workmanshipMinorLabel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        handleOnClick(position);
                    }
                });


                //we need to update adapter once we finish with editing
                workManShipViewHolder.workmanshipInspectedLabel.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (!hasFocus) {
//                                final int position = v.getId();
                            final EditText Caption = (EditText) v;
                            if (!TextUtils.isEmpty(Caption.getText().toString())) {
                                data.get(position).InspectedQty = Integer.parseInt(Caption.getText().toString());
                                if (mRecyclerView != null && !mRecyclerView.isComputingLayout()) {
                                    notifyDataSetChanged();
                                    if (activity instanceof POItemListActivity) {
                                        ((POItemTabActivity) activity).handleToUpdateTotal();
                                        ((POItemTabActivity) activity).changeOnAvailable();
                                    }
                                }
                            }
                        } else if (hasFocus) {
                            workManShipViewHolder.workmanshipInspectedLabel.setSelectAllOnFocus(true);
                            workManShipViewHolder.workmanshipInspectedLabel.selectAll();
                        }
                    }
                });
            }


//            }
        } else if (mViewType == FEnumerations.VIEW_TYPE_ITEM_CARTON)

        {
            if (holder instanceof CartonViewHolder) {
                FslLog.d(TAG, " carton item creating... ");
                final POItemDtl1 pOItemDtl = data.get(position);
                final CartonViewHolder cartonViewHolder = (CartonViewHolder) holder;
              /*  if (position == 0) {
                    cartonViewHolder.cartonPo.setText(pOItemDtl.POhdr);
                    cartonViewHolder.cartonItem.setText(pOItemDtl.Itemhdr);
                    cartonViewHolder.CartonLabel1.setText(pOItemDtl.cartonPackeddr);
                    cartonViewHolder.AvalableLabel.setText(pOItemDtl.cartonAvailable);
                    cartonViewHolder.InspectedLabel1.setText(pOItemDtl.cartonToInspectedhdr);

                    cartonViewHolder.CartonLabel1.setVisibility(View.VISIBLE);
                    cartonViewHolder.CartonPackedLabel.setVisibility(View.GONE);
                    cartonViewHolder.InspectedLabel1.setVisibility(View.VISIBLE);
                    cartonViewHolder.InspectedLabel.setVisibility(View.GONE);

                } else {*/
                cartonViewHolder.CartonLabel1.setVisibility(View.GONE);
                cartonViewHolder.CartonPackedLabel.setVisibility(View.VISIBLE);
                cartonViewHolder.InspectedLabel1.setVisibility(View.GONE);
                cartonViewHolder.InspectedLabel.setVisibility(View.VISIBLE);
                String strPo = null;
                for (int i = 0; i < holeList.size(); i++) {
                    if (pOItemDtl.QRPOItemHdrID.equals(holeList.get(i).QRPOItemHdrID)) {
                        if (TextUtils.isEmpty(strPo)) {
                            strPo = holeList.get(i).PONO;
                        } else {
                            strPo = strPo + "\n" + holeList.get(i).PONO;
                        }
                    }
                }
//                if (pOItemDtl.IsImportant == 1) {
//                    cartonViewHolder.iconImportant.setVisibility(View.VISIBLE);
//                } else {
//                    cartonViewHolder.iconImportant.setVisibility(View.GONE);
//                }
                cartonViewHolder.cartonPo.setText(strPo);
                cartonViewHolder.cartonItem.setText(pOItemDtl.CustomerItemRef);

                cartonViewHolder.cartonPo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        handleOnClick(position);
                    }
                });
                cartonViewHolder.cartonItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        handleOnClick(position);
                    }
                });

                cartonViewHolder.CartonPackedLabel.setText(pOItemDtl.CartonsPacked2 + "");
                pOItemDtl.CartonsPacked = pOItemDtl.CartonsPacked2;
                cartonViewHolder.AvalableLabel.setText(pOItemDtl.CartonsPacked + "");
                cartonViewHolder.InspectedLabel.setText(pOItemDtl.CartonsInspected + "");
                cartonViewHolder.AvalableLabel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                //we need to update adapter once we finish with editing
                cartonViewHolder.CartonPackedLabel.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (!hasFocus) {
//                                final int position = v.getId();
                            final EditText Caption = (EditText) v;
                            if (!TextUtils.isEmpty(Caption.getText().toString())) {
                                pOItemDtl.CartonsPacked2 = Integer.parseInt(Caption.getText().toString());
//                                changeOnCartonAvailable(position, pOItemDtl);

                                if (mRecyclerView != null && !mRecyclerView.isComputingLayout()) {
                                    notifyDataSetChanged();
                                    if (activity instanceof POItemListActivity) {
                                        ((POItemTabActivity) activity).handleToUpdateTotal();
                                        ((POItemTabActivity) activity).changeOnAvailable();
                                    }
                                }
                            }

                        } else if (hasFocus) {
                            cartonViewHolder.CartonPackedLabel.setSelectAllOnFocus(true);
                            cartonViewHolder.CartonPackedLabel.selectAll();
                        }
                    }
                });
                //we need to update adapter once we finish with editing
                cartonViewHolder.InspectedLabel.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (!hasFocus) {
//                                final int position = v.getId();

                            final EditText Caption = (EditText) v;
                            if (!TextUtils.isEmpty(Caption.getText().toString())) {
                                data.get(position).CartonsInspected = Integer.parseInt(Caption.getText().toString());


                                if (mRecyclerView != null && !mRecyclerView.isComputingLayout()) {
                                    notifyDataSetChanged();
                                    if (activity instanceof POItemListActivity) {
                                        ((POItemTabActivity) activity).handleToUpdateTotal();
                                        ((POItemTabActivity) activity).changeOnAvailable();
                                    }
                                }
                            }
                        } else if (hasFocus) {
                            cartonViewHolder.InspectedLabel.setSelectAllOnFocus(true);
                            cartonViewHolder.InspectedLabel.selectAll();
                        }
                    }
                });
//                }

//                cartonViewHolder.cartonMainContainer.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        handleOnClick(position);
//                    }
//                });
            }
        } else if (mViewType == FEnumerations.VIEW_TYPE_MORE)

        {

            if (holder instanceof MoreDetailViewHolder) {
                FslLog.d(TAG, " more item creating... ");
                final POItemDtl1 pOItemDtl = data.get(position);
                final MoreDetailViewHolder moreDetailViewHolder = (MoreDetailViewHolder) holder;

               /* if (position == 0) {
                    moreDetailViewHolder.morePo.setText(pOItemDtl.POhdr);
                    moreDetailViewHolder.moreItem.setText(pOItemDtl.Itemhdr);
                    moreDetailViewHolder.packagingMeasurementLabel.setText(pOItemDtl.packagingMeasurementhdr);
                    moreDetailViewHolder.barCodeLabel.setText(pOItemDtl.barCodehdr);
                    moreDetailViewHolder.digitalUploadedLabel.setText(pOItemDtl.digitalUploadedhdr);
                    moreDetailViewHolder.enclosuresUploadedLabel.setText(pOItemDtl.enclosuresUploadedhdr);
                    moreDetailViewHolder.taskReportsLabel.setText(pOItemDtl.taskReportshdr);
                    moreDetailViewHolder.measurementsLabel.setText(pOItemDtl.measurementshdr);
                    moreDetailViewHolder.samplePurposeLabel.setText(pOItemDtl.SamplePurposehdr);
                    moreDetailViewHolder.overAllInspectionResultLabel.setText(pOItemDtl.OverallInspectionResulthdr);
                    moreDetailViewHolder.hologramNoLabel.setText(pOItemDtl.HologramNohdr);
                } else {*/

                String strPo = null;
                for (int i = 0; i < holeList.size(); i++) {
                    if (pOItemDtl.QRPOItemHdrID.equals(holeList.get(i).QRPOItemHdrID)) {
                        if (TextUtils.isEmpty(strPo)) {
                            strPo = holeList.get(i).PONO;
                        } else {
                            strPo = strPo + "\n" + holeList.get(i).PONO;
                        }
                    }
                }
//                if (pOItemDtl.IsImportant == 1) {
//                    moreDetailViewHolder.iconImportant.setVisibility(View.VISIBLE);
//                } else {
//                    moreDetailViewHolder.iconImportant.setVisibility(View.GONE);
//                }
                moreDetailViewHolder.morePo.setText(strPo);
                moreDetailViewHolder.moreItem.setText(pOItemDtl.CustomerItemRef);

                moreDetailViewHolder.morePo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        handleOnClick(position);
                    }
                });
                moreDetailViewHolder.moreItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        handleOnClick(position);
                    }
                });

                moreDetailViewHolder.packagingMeasurementLabel.setText(pOItemDtl.PKG_Me_InspectionResult + "");
                moreDetailViewHolder.barCodeLabel.setText(pOItemDtl.Barcode_InspectionResult + "");
                moreDetailViewHolder.digitalUploadedLabel.setText(pOItemDtl.Digitals + "");
                moreDetailViewHolder.enclosuresUploadedLabel.setText(pOItemDtl.EnclCount + "");

                if (pOItemDtl.testReportStatus == 0) {
                    moreDetailViewHolder.taskReportsLabel.setText("-");
//                        moreDetailViewHolder.taskReportsLabel.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    moreDetailViewHolder.taskReportsLabel.setTextColor(GenUtils.getColorResource(activity, R.color.colorBlack));
                } else if (pOItemDtl.testReportStatus == 1) {
                    moreDetailViewHolder.taskReportsLabel.setText("Pending");
                    moreDetailViewHolder.taskReportsLabel.setTextColor(GenUtils.getColorResource(activity, R.color.colorBlack));
//                        moreDetailViewHolder.taskReportsLabel.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_cancel_black_24dp, 0, 0, 0);
                } else if (pOItemDtl.testReportStatus == 2) {
                    moreDetailViewHolder.taskReportsLabel.setText("Expired");
                    moreDetailViewHolder.taskReportsLabel.setTextColor(GenUtils.getColorResource(activity, R.color.red));
//                        moreDetailViewHolder.taskReportsLabel.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_cancel_black_24dp, 0, 0, 0);
                } else if (pOItemDtl.testReportStatus == 3) {
                    moreDetailViewHolder.taskReportsLabel.setText("Valid");
                    moreDetailViewHolder.taskReportsLabel.setTextColor(GenUtils.getColorResource(activity, R.color.green));
//                        moreDetailViewHolder.taskReportsLabel.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_done, 0, 0, 0);
                }


                moreDetailViewHolder.measurementsLabel.setText(pOItemDtl.ItemMeasurement_InspectionResult + "");//pOItemDtl.measurementshdr +
                moreDetailViewHolder.samplePurposeLabel.setText(pOItemDtl.SampleSizeDescr + "");
                moreDetailViewHolder.overAllInspectionResultLabel.setText(pOItemDtl.Overall_InspectionResult + "");

                String strHologramNo = null;
                for (int i = 0; i < holeList.size(); i++) {
                    if (pOItemDtl.QRPOItemHdrID.equals(holeList.get(i).QRPOItemHdrID)) {
                        if (TextUtils.isEmpty(strHologramNo)) {
                            strHologramNo = holeList.get(i).HologramNo;
                        }/* else {
                            strHologramNo = strHologramNo + "\n" + holeList.get(i).HologramNo;
                        }*/
                    }
                }


                moreDetailViewHolder.hologramNoLabel.setText(strHologramNo + "");
//                }
                moreDetailViewHolder.moreMainContainer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        handleOnClick(position);

                    }
                });

            }
        }
    }


    private void handleOnClick(int position) {

        listener.onItemBackClick(data.get(position));

    }

    public void setLoaded() {
        isLoading = false;
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }


    class QualityViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        LinearLayout qualityMainContainer;
        RelativeLayout descriptionContainer;
        TextView qualityPo, qualityItem;
        EditText
                qualityAvailableLabel,
                qualityOrderLabel,
                qualityInspectedTillDateLabel,
                qualityAcceptedLabel,
                qualityShortLabel;
        CheckBox qualityInspectLaterLabel;
        TextView
                itenDescription;


        public View layout;
        ImageView deleteItem;

        public QualityViewHolder(View view) {
            super(view);
//            layout = v;
            FslLog.d(TAG, " quality row view creating... ");
            qualityMainContainer = (LinearLayout) view.findViewById(R.id.qualityMainContainer);
            descriptionContainer = (RelativeLayout) view.findViewById(R.id.descriptionContainer);
            qualityPo = (TextView) view.findViewById(R.id.qualityPo);
            qualityItem = (TextView) view.findViewById(R.id.qualityItem);
            qualityOrderLabel = (EditText) view.findViewById(R.id.qualityOrderLabel);
            qualityInspectedTillDateLabel = (EditText) view.findViewById(R.id.qualityInspectedTillDateLabel);
            qualityAvailableLabel = (EditText) view.findViewById(R.id.qualityAvailableLabel);
            qualityAcceptedLabel = (EditText) view.findViewById(R.id.qualityAcceptedLabel);
            qualityShortLabel = (EditText) view.findViewById(R.id.qualityShortLabel);
//            qualityShortStockQtyLabel = (EditText) view.findViewById(R.id.qualityShortStockQtyLabel);
            qualityInspectLaterLabel = (CheckBox) view.findViewById(R.id.qualityInspectLaterLabel);

//            qualityOrderLabel1 = (TextView) view.findViewById(R.id.qualityOrderLabel1);
//            qualityInspectedTillDateLabel1 = (TextView) view.findViewById(R.id.qualityInspectedTillDateLabel1);
//            qualityAvailableLabel1 = (TextView) view.findViewById(R.id.qualityAvailableLabel1);
//            qualityAcceptedLabel1 = (TextView) view.findViewById(R.id.qualityAcceptedLabel1);
//            qualityShortLabel1 = (TextView) view.findViewById(R.id.qualityShortLabel1);
//            qualityShortStockQtyLabel1 = (TextView) view.findViewById(R.id.qualityShortStockQtyLabel1);
//            qualityInspectLaterLabel1 = (TextView) view.findViewById(R.id.qualityInspectLaterLabel1);
            itenDescription = (TextView) view.findViewById(R.id.itenDescription);
            deleteItem = (ImageView) view.findViewById(R.id.deleteItem);

        }
    }

    class WorkManShipViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView workPo, workmanItem,
                workmanshipToInspectionLabel,

        workmanshipCriticalLabel, workmanshipMajorLabel, workmanshipMinorLabel;
        EditText workmanshipInspectedLabel;

        LinearLayout workmanshipMainContainer;
        public View layout;
//        public ImageView iconImportant;

        public WorkManShipViewHolder(View view) {
            super(view);
            FslLog.d(TAG, " workmanship row view creating... ");
            workmanshipMainContainer = (LinearLayout) view.findViewById(R.id.workmanshipMainContainer);
            workPo = (TextView) view.findViewById(R.id.workPo);
            workmanItem = (TextView) view.findViewById(R.id.workmanItem);
            workmanshipToInspectionLabel = (TextView) view.findViewById(R.id.workmanshipToInspectionLabel);
//            workmanshipInspectedLabel1 = (TextView) view.findViewById(R.id.workmanshipInspectedLabel1);
            workmanshipInspectedLabel = (EditText) view.findViewById(R.id.workmanshipInspectedLabel);
            workmanshipCriticalLabel = (TextView) view.findViewById(R.id.workmanshipCriticalLabel);
            workmanshipMajorLabel = (TextView) view.findViewById(R.id.workmanshipMajorLabel);
            workmanshipMinorLabel = (TextView) view.findViewById(R.id.workmanshipMinorLabel);
//            iconImportant = (ImageView) view.findViewById(R.id.iconImportant);

        }
    }

    class CartonViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView cartonPo, cartonItem,
                CartonLabel1,
                AvalableLabel,
                InspectedLabel1;
        EditText CartonPackedLabel, InspectedLabel;

        public View layout;
        LinearLayout cartonMainContainer;
//        public ImageView iconImportant;

        public CartonViewHolder(View view) {
            super(view);
            FslLog.d(TAG, " carton row view creating... ");
            cartonMainContainer = (LinearLayout) view.findViewById(R.id.cartonMainContainer);
            cartonPo = (TextView) view.findViewById(R.id.cartonPo);
            cartonItem = (TextView) view.findViewById(R.id.cartonItem);
            CartonLabel1 = (TextView) view.findViewById(R.id.CartonLabel1);
            CartonPackedLabel = (EditText) view.findViewById(R.id.CartonPackedLabel);
            AvalableLabel = (TextView) view.findViewById(R.id.AvalableLabel);
            InspectedLabel1 = (TextView) view.findViewById(R.id.InspectedLabel1);
            InspectedLabel = (EditText) view.findViewById(R.id.InspectedLabel);
//            iconImportant = (ImageView) view.findViewById(R.id.iconImportant);
        }
    }

    class MoreDetailViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        LinearLayout moreMainContainer;
        TextView morePo, moreItem,
                packagingMeasurementLabel,
                barCodeLabel, digitalUploadedLabel,
                enclosuresUploadedLabel, taskReportsLabel, measurementsLabel, samplePurposeLabel, overAllInspectionResultLabel, hologramNoLabel;
//        public ImageView iconImportant;


        public MoreDetailViewHolder(View view) {
            super(view);
            FslLog.d(TAG, " more row view creating... ");
            moreMainContainer = (LinearLayout) view.findViewById(R.id.moreMainContainer);
            morePo = (TextView) view.findViewById(R.id.morePo);
            moreItem = (TextView) view.findViewById(R.id.moreItem);
            packagingMeasurementLabel = (TextView) view.findViewById(R.id.packagingMeasurementLabel);
            barCodeLabel = (TextView) view.findViewById(R.id.barCodeLabel);
            digitalUploadedLabel = (TextView) view.findViewById(R.id.digitalUploadedLabel);
            enclosuresUploadedLabel = (TextView) view.findViewById(R.id.enclosuresUploadedLabel);
            taskReportsLabel = (TextView) view.findViewById(R.id.taskReportsLabel);
            measurementsLabel = (TextView) view.findViewById(R.id.measurementsLabel);
            samplePurposeLabel = (TextView) view.findViewById(R.id.samplePurposeLabel);
            overAllInspectionResultLabel = (TextView) view.findViewById(R.id.overAllInspectionResultLabel);
            hologramNoLabel = (TextView) view.findViewById(R.id.hologramNoLabel);
//            iconImportant = (ImageView) view.findViewById(R.id.iconImportant);


        }

    }

    private int getAvailable(POItemDtl pOItemDtl) {

        return Integer.parseInt(pOItemDtl.OrderQty) - pOItemDtl.EarlierInspected;

    }

    private int getAccepted(POItemDtl pOItemDtl) {
        return pOItemDtl.AvailableQty;
    }

    private int getCartonToInspect(POItemDtl pOItemDtl) {
        int squareRoot = (int) Math.sqrt(pOItemDtl.CartonsPacked);
        return squareRoot;
    }

   /* private void changeOnAccepted(int pos, POItemDtl pOItemDtl) {
        float f = Float.valueOf(pOItemDtl.OrderQty);
        holeList.get(pos).Short = ((int) f) - (pOItemDtl.EarlierInspected + pOItemDtl.AcceptedQty);

    }*/

   /* private void changeOnAvailable(int pos, POItemDtl pOItemDtl) {

        if (pOItemDtl.POMasterPackQty > 0) {
            float or = Float.valueOf(pOItemDtl.OrderQty);
            holeList.get(pos).CartonsPacked = (int) (or / pOItemDtl.POMasterPackQty);
        }
        String[] toInspDtl = POItemDtlHandler.getToInspect(activity, pRowIdOfInspectLevel, pOItemDtl.AvailableQty);
        if (toInspDtl != null) {
            holeList.get(pos).SampleSizeInspection = toInspDtl[1] *//*+ "  " + toInspDtl[2]*//*;

            data.get(pos).InspectedQty = Integer.parseInt(toInspDtl[2]);

            String sampleCode = toInspDtl[0];

            String[] minorDefect = POItemDtlHandler.getDefectAccepted(activity, pRowIdOfQualityMinorLevel, sampleCode);
            if (minorDefect != null) {
                data.get(pos).MinorDefectsAllowed = Integer.parseInt(minorDefect[1]);
            }

            String[] majorDefect = POItemDtlHandler.getDefectAccepted(activity, pRowIdOfQualityMajorLevel, sampleCode);
            if (majorDefect != null) {
                data.get(pos).MajorDefectsAllowed = Integer.parseInt(majorDefect[1]);
            }
            if (pOItemDtl.POMasterPackQty > 0) {
                float or = Float.valueOf(pOItemDtl.OrderQty);
                data.get(pos).CartonsPacked = (int) (or / pOItemDtl.POMasterPackQty);
            }

        }
    }*/

   /* private void changeOnCartonAvailable(int pos, POItemDtl pOItemDtl) {
        data.get(pos).AllowedCartonInspection = getCartonToInspect(pOItemDtl);

    }*/

}
