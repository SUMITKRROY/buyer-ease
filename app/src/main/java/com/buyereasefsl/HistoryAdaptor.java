package com.buyereasefsl;

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
import android.widget.TextView;

import com.google.gson.Gson;
import com.inspection.InspectionModal;
import com.podetail.POItemDtl;
import com.util.DateUtils;

import java.util.List;

/**
 * Created by ADMIN on 12/22/2017.
 */

public class HistoryAdaptor extends RecyclerView.Adapter<HistoryAdaptor.ViewHolder> {

    Activity activity;
    List<InspectionModal> ModelList;
//    private OnItemClickListener listener;
    public Animator mCurrentAnimator;
//    onItemClickListener mListener;

//    public interface OnItemClickListener {
//        void onItemClick(POItemDtl item);
//    }

    public HistoryAdaptor(Activity clsHomeActivity,
                          List<InspectionModal> inspectionModalList/*, onWorkManShipClickListener listener*/) {
        activity = clsHomeActivity;
        ModelList = inspectionModalList;
//        mListener = listener;
    }


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
//        public TextView txtItemRef;


        int position;
        TextView txtInspectionNo, txtInspectionDate, txtInspectionActivity, txtInspectionQR, txtInspector;
//        LinearLayout attachmentCountContainer;


        public ViewHolder(View view) {
            super(view);

            txtInspectionNo = (TextView) view.findViewById(R.id.txtInspectionNo);
            txtInspectionDate = (TextView) view.findViewById(R.id.txtInspectionDate);
            txtInspectionActivity = (TextView) view.findViewById(R.id.txtInspectionActivity);
            txtInspectionQR = (TextView) view.findViewById(R.id.txtInspectionQR);
            txtInspector = (TextView) view.findViewById(R.id.txtInspector);


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
                inflater.inflate(R.layout.history_row, parent, false);
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
        holder.txtInspectionNo.setText(inspectionModal.QRHdrID);
        if (!TextUtils.isEmpty(inspectionModal.InspectionDt)) {
            holder.txtInspectionDate.setText(DateUtils.getDate(inspectionModal.InspectionDt));
        }else {
            holder.txtInspectionDate.setText("-");
        }
        holder.txtInspectionActivity.setText(inspectionModal.Activity);
        holder.txtInspectionQR.setText(inspectionModal.QR);
        holder.txtInspector.setText(inspectionModal.Inspector);


    }

    @Override
    public int getItemCount() {
        return ModelList.size();
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
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

//
//    public interface onItemClickListener {
//        void onClick(int itemReportPosition, InspectionModal workManShipModel, int position);
//    }


}
