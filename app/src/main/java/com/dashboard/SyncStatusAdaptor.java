package com.dashboard;

import android.animation.Animator;
import android.app.Activity;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.buyereasefsl.R;
import com.constant.FEnumerations;
import com.util.GenUtils;

import java.util.List;

/**
 * Created by ADMIN on 12/27/2017.
 */

public class SyncStatusAdaptor extends RecyclerView.Adapter<SyncStatusAdaptor.ViewHolder> {

    Activity activity;
    List<StatusModal> ModelList;
    private DashBoardAdaptor.OnItemClickListener listener;
    public Animator mCurrentAnimator;


    public interface OnItemClickListener {
        void onItemClick(DashboardModal item);
    }

    public SyncStatusAdaptor(Activity clsHomeActivity,
                             List<StatusModal> DashboardModelList) {
        activity = clsHomeActivity;
        ModelList = DashboardModelList;

    }


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView txtTableNameLabel, message;
        ImageView icon;
        ProgressBar progressBar;


        public ViewHolder(View v) {
            super(v);

            txtTableNameLabel = (TextView) v.findViewById(R.id.txtTableNameLabel);
            progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
            icon = (ImageView) v.findViewById(R.id.icon);
            message = (TextView) v.findViewById(R.id.message);

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
                inflater.inflate(R.layout.sync_row, parent, false);
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
        final StatusModal dashboardModal = ModelList.get(position);
        holder.txtTableNameLabel.setText(dashboardModal.tableName);
        if (!TextUtils.isEmpty(dashboardModal.title)) {
            holder.message.setText(dashboardModal.title);
            holder.message.setVisibility(View.VISIBLE);
        } else {
            holder.message.setVisibility(View.GONE);
        }
        if (dashboardModal.status == FEnumerations.E_SYNC_IN_PROCESS_STATUS) {
//            holder.icon.setImageResource(dashboardModal.icon);
            holder.progressBar.setVisibility(View.VISIBLE);
            holder.icon.setVisibility(View.GONE);
        } else if (dashboardModal.status == FEnumerations.E_SYNC_SUCCESS_STATUS) {
            holder.progressBar.setVisibility(View.GONE);
            holder.icon.setVisibility(View.VISIBLE);
            holder.icon.setImageResource(R.drawable.ic_done);
            holder.icon.setColorFilter(GenUtils.getColorResource(activity, R.color.colorTeal), android.graphics.PorterDuff.Mode.SRC_IN);
        } else if (dashboardModal.status == FEnumerations.E_SYNC_FAILED_STATUS) {
            holder.progressBar.setVisibility(View.GONE);
            holder.icon.setVisibility(View.VISIBLE);
            holder.icon.setImageResource(R.drawable.ic_cancel_black_24dp);
            holder.icon.setColorFilter(GenUtils.getColorResource(activity, R.color.red), android.graphics.PorterDuff.Mode.SRC_IN);
        } else if (dashboardModal.status == FEnumerations.E_SYNC_PENDING_STATUS) {
            holder.progressBar.setVisibility(View.GONE);
            holder.icon.setVisibility(View.VISIBLE);
            holder.icon.setImageResource(R.drawable.ic_cancel_black_24dp);
            holder.icon.setColorFilter(GenUtils.getColorResource(activity, R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_IN);
        }


    }

    @Override
    public int getItemCount() {
        return ModelList.size();
    }


    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }
}