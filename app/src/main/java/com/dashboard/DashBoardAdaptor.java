package com.dashboard;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.buyereasefsl.R;
import com.util.GenUtils;

import java.util.List;


/**
 * Created by ADMIN on 10/25/2017.
 */

public class DashBoardAdaptor extends RecyclerView.Adapter<DashBoardAdaptor.ViewHolder> {

    Activity activity;
    List<DashboardModal> ModelList;
    private OnItemClickListener listener;
    public Animator mCurrentAnimator;


    public interface OnItemClickListener {
        void onItemClick(DashboardModal item);
    }

    public DashBoardAdaptor(Activity clsHomeActivity,
                            List<DashboardModal> DashboardModelList) {
        activity = clsHomeActivity;
        ModelList = DashboardModelList;

    }


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView txtComplianceLabel;
        ImageView icon;
        LinearLayout fixedCarPoolLayout;

        public ViewHolder(View v) {
            super(v);

            txtComplianceLabel = (TextView) v.findViewById(R.id.txtComplianceLabel);
            icon = (ImageView) v.findViewById(R.id.icon);
            fixedCarPoolLayout = (LinearLayout) v.findViewById(R.id.fixedCarPoolLayout);

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
                inflater.inflate(R.layout.dashboard_row, parent, false);
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
        final DashboardModal dashboardModal = ModelList.get(position);
        holder.txtComplianceLabel.setText(dashboardModal.name);

        holder.icon.setImageResource(dashboardModal.icon);

        if (!dashboardModal.IsVisible) {
            holder.fixedCarPoolLayout.setEnabled(false);
            holder.fixedCarPoolLayout.setBackgroundColor(GenUtils.getColorResource(activity,R.color.divider_under_line));
            holder.icon.setAlpha(.2f);
        }else {
            holder.icon.setAlpha(1f);
            holder.fixedCarPoolLayout.setBackgroundColor(GenUtils.getColorResource(activity,R.color.white));
        }

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
}
