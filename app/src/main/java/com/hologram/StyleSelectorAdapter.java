package com.hologram;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.util.DateUtils;
import com.util.FslLog;
import com.util.GenUtils;
import com.buyereasefsl.R;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class StyleSelectorAdapter extends RecyclerView.Adapter<StyleSelectorAdapter.ViewHolder> {

    Activity activity;
    List<HologramModal> ModelList;
    private OnItemClickListener listener;
    public Animator mCurrentAnimator;

    private ArrayList<HologramModal> searchList;

    public interface OnItemClickListener {
        void onItemClick(HologramModal item);
    }

    RecyclerView mRecyclerView;
    String TAG = "StyleSelectorAdapter";

    public StyleSelectorAdapter(Activity clsHomeActivity, RecyclerView recyclerView,
                                List<HologramModal> InspectionModalModelList, OnItemClickListener mL) {
        activity = clsHomeActivity;
        ModelList = InspectionModalModelList;
        mRecyclerView = recyclerView;
        listener = mL;

        this.searchList = new ArrayList<HologramModal>();
        this.searchList.addAll(InspectionModalModelList);
    }


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView styleNo, customerName, departmentName, vendorName,
                itenDescription, txtHologramNo, txtExpiryDate;
        ImageView txtStatus;
        LinearLayout rowContainer, hologramContainer;
        CheckBox chkToSync;

        public ViewHolder(View v) {
            super(v);

            styleNo = (TextView) v.findViewById(R.id.styleNo);
            customerName = (TextView) v.findViewById(R.id.customerName);
            departmentName = (TextView) v.findViewById(R.id.departmentName);
            vendorName = (TextView) v.findViewById(R.id.vendorName);
            itenDescription = (TextView) v.findViewById(R.id.itenDescription);

            txtHologramNo = (TextView) v.findViewById(R.id.txtHologramNo);
            txtExpiryDate = (TextView) v.findViewById(R.id.txtExpiryDate);

            rowContainer = (LinearLayout) v.findViewById(R.id.rowContainer);
            hologramContainer = (LinearLayout) v.findViewById(R.id.hologramContainer);
            chkToSync = (CheckBox) v.findViewById(R.id.chkToSync);
            txtStatus = (ImageView) v.findViewById(R.id.txtStatus);
        }
    }


    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.style_row, parent, false);
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
        final HologramModal hologramModal = ModelList.get(position);
        holder.styleNo.setText(hologramModal.code);
        holder.customerName.setText(hologramModal.customerName);
        holder.departmentName.setText(hologramModal.department);
        holder.vendorName.setText(hologramModal.vendor);
        if (!TextUtils.isEmpty(hologramModal.description) && !"null".equalsIgnoreCase(hologramModal.description)) {
            holder.itenDescription.setText(hologramModal.description.trim());
        }

        holder.rowContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(hologramModal);
            }
        });

        if (!TextUtils.isEmpty(hologramModal.hologram_no) && !"null".equalsIgnoreCase(hologramModal.hologram_no)) {
            holder.txtHologramNo.setVisibility(View.VISIBLE);
            holder.txtHologramNo.setText("Hologram No : " + hologramModal.hologram_no);
        } else {
            holder.txtHologramNo.setText("Hologram No : ");
        }
        FslLog.e(TAG, " hologramExpiry Dt :   " + hologramModal.hologram_expiry_date);
        if (!TextUtils.isEmpty(hologramModal.hologram_expiry_date) && !"null".equalsIgnoreCase(hologramModal.hologram_expiry_date)) {
            holder.txtExpiryDate.setText("Expiry Date : " + DateUtils.getStringDateToTodayAlert(hologramModal.hologram_expiry_date));
        } else {
            holder.txtExpiryDate.setText("Expiry Date : ");
        }
        holder.chkToSync.setChecked(hologramModal.IsCheckedToSync);
        holder.chkToSync.setTag(hologramModal);
        holder.chkToSync.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                HologramModal inspModal = (HologramModal) cb.getTag();
                inspModal.IsCheckedToSync = cb.isChecked();
                ModelList.get(position).IsCheckedToSync = cb.isChecked();
            }
        });
        if (hologramModal.synced == 0) {
//            holder.txtStatus.setText("Pending");
            holder.txtStatus.setImageResource(R.drawable.ic_cancel_black_24dp);
            holder.txtStatus.setColorFilter(GenUtils.getColorResource(activity, R.color.colorBlack), android.graphics.PorterDuff.Mode.SRC_IN);
        } else if (hologramModal.synced == 1) {
//            holder.txtStatus.setText("Updated but Not synced ");
            holder.txtStatus.setImageResource(R.drawable.ic_done);
            holder.txtStatus.setColorFilter(GenUtils.getColorResource(activity, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.SRC_IN);
        } else if (hologramModal.synced == 2) {
//            holder.txtStatus.setText("Updated and Synced");
            holder.txtStatus.setImageResource(R.drawable.icon_double_tick);
            holder.txtStatus.setColorFilter(GenUtils.getColorResource(activity, R.color.colorTeal), android.graphics.PorterDuff.Mode.SRC_IN);
        }
        holder.itenDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(hologramModal);
            }
        });
        holder.styleNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(hologramModal);
            }
        });
        holder.customerName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(hologramModal);
            }
        });
        holder.departmentName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(hologramModal);
            }
        });
        holder.hologramContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(hologramModal);
            }
        });
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

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        ModelList.clear();
        if (charText.length() == 0) {
            ModelList.addAll(searchList);
        } else {
            for (HologramModal wp : searchList) {
                if (wp.code.toLowerCase(Locale.getDefault()).contains(charText)
                        || wp.vendor.toLowerCase(Locale.getDefault()).contains(charText)
                        || wp.customerName.toLowerCase(Locale.getDefault()).contains(charText)
                        || wp.department.toLowerCase(Locale.getDefault()).contains(charText)
                        || wp.description.toLowerCase(Locale.getDefault()).contains(charText)
                        ) {
                    ModelList.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
}