package com.inspection;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.General.SysData22Handler;
import com.General.SysData22Modal;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.buyereasefsl.R;
import com.constant.FEnumerations;

import java.util.List;

/**
 * Created by ADMIN on 12/14/2017.
 */

public class InspectionListAdaptor extends RecyclerView.Adapter<InspectionListAdaptor.ViewHolder> {

    Activity activity;
    List<InspectionModal> ModelList;
    private OnItemClickListener listener;
    public Animator mCurrentAnimator;


    public interface OnItemClickListener {
        void onItemClick(InspectionModal item);
        void onClickItem(View view, String ProwId);
    }

    RecyclerView mRecyclerView;

    public InspectionListAdaptor(Activity clsHomeActivity, RecyclerView recyclerView,
                                 List<InspectionModal> InspectionModalModelList, OnItemClickListener mL) {
        activity = clsHomeActivity;
        ModelList = InspectionModalModelList;
        mRecyclerView = recyclerView;
        listener = mL;
    }


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView txtID, txtDate, inspectionActivity, txtVendor,
                txtCustomer, txtQR, txtInspector,
                txtVendorContact, txtVendorAddress, txtStatus, txtPONO,txtItemId,tvMore;
        CheckBox chkToSync;
        LinearLayout inspectionDetailContainer;
        ImageView iconImportant;

        public ViewHolder(View v) {
            super(v);

            txtID = (TextView) v.findViewById(R.id.txtID);
            txtPONO = (TextView) v.findViewById(R.id.txtPONO);
            txtItemId = (TextView) v.findViewById(R.id.txtItemId);
            txtDate = (TextView) v.findViewById(R.id.txtDate);
            inspectionActivity = (TextView) v.findViewById(R.id.inspectionActivity);
            txtVendor = (TextView) v.findViewById(R.id.txtVendor);
            txtCustomer = (TextView) v.findViewById(R.id.txtCustomer);
            txtQR = (TextView) v.findViewById(R.id.txtQR);
            txtInspector = (TextView) v.findViewById(R.id.txtInspector);
            txtVendorContact = (TextView) v.findViewById(R.id.txtVendorContact);
            txtVendorAddress = (TextView) v.findViewById(R.id.txtVendorAddress);
            txtStatus = (TextView) v.findViewById(R.id.txtStatus);
            tvMore = (TextView) v.findViewById(R.id.tvMore);
            chkToSync = (CheckBox) v.findViewById(R.id.chkToSync);
            inspectionDetailContainer = (LinearLayout) v.findViewById(R.id.inspectionDetailContainer);
            iconImportant = (ImageView) v.findViewById(R.id.iconImportant);
        }
    }


    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.inspection_row, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
//        final String name = values.get(position);
        final InspectionModal inspectionModal = ModelList.get(position);
        holder.txtID.setText(inspectionModal.pRowID);
        holder.inspectionActivity.setText(inspectionModal.Activity);
        holder.txtVendor.setText(inspectionModal.Vendor);
        holder.txtCustomer.setText(inspectionModal.Customer);
        holder.txtQR.setText(inspectionModal.QR);
        holder.txtInspector.setText(inspectionModal.Inspector);
        holder.txtVendorContact.setText(inspectionModal.VendorContact);
        holder.txtVendorAddress.setText(inspectionModal.VendorAddress);
        holder.txtDate.setText(inspectionModal.InspectionDt);
        holder.txtPONO.setText(inspectionModal.POListed);
        holder.txtItemId.setText(inspectionModal.ItemListId);
        Log.e("InspectionsListAdapter","inspectionModal.ItemListId=="+inspectionModal.ItemListId);
        holder.chkToSync.setChecked(inspectionModal.IsCheckedToSync);
        holder.chkToSync.setTag(inspectionModal);

        if(holder.txtItemId.getText().toString().length()>=50){
            holder.tvMore.setVisibility(View.VISIBLE);
        }else {
            holder.tvMore.setVisibility(View.GONE);
        }
        if (inspectionModal.IsImportant == 1) {
            holder.iconImportant.setVisibility(View.VISIBLE);
            DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(holder.iconImportant);
            Glide.with(activity.getApplicationContext()).load(R.raw.icon_gif_test).into(imageViewTarget);
        } else {
            holder.iconImportant.setVisibility(View.GONE);
        }

        holder.chkToSync.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;

                InspectionModal inspModal = (InspectionModal) cb.getTag();

                inspModal.IsCheckedToSync = cb.isChecked();
                ModelList.get(position).IsCheckedToSync = cb.isChecked();
            }
        });

        holder.inspectionDetailContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(inspectionModal);
            }
        });
        holder.tvMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClickItem(view,inspectionModal.pRowID);
            }
        });
        if (!TextUtils.isEmpty(inspectionModal.Status)) {
            final List<SysData22Modal> statusList = SysData22Handler.getSysData22ListAccToID(activity, FEnumerations.E_STATUS_GEN_ID, inspectionModal.Status);
            if (statusList != null && statusList.size() > 0) {
                holder.txtStatus.setText(statusList.get(0).MainDescr);
            }
        }


    }


    @Override
    public int getItemCount() {
        return ModelList.size();
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context,
                                     final RecyclerView recyclerView,
                                     final ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }
}