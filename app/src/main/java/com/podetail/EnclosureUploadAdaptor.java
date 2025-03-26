package com.podetail;

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
import android.widget.TextView;

import com.General.EnclosureModal;
import com.buyereasefsl.R;

import java.util.List;

/**
 * Created by ADMIN on 1/6/2018.
 */

public class EnclosureUploadAdaptor extends RecyclerView.Adapter<EnclosureUploadAdaptor.ViewHolder> {

    Activity activity;
    List<EnclosureModal> ModelList;
    //    private OnItemClickListener listener;
    public Animator mCurrentAnimator;
    OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int pos);
    }

    public EnclosureUploadAdaptor(Activity clsHomeActivity,
                                  List<EnclosureModal> inspectionModalList, OnItemClickListener onItemClickListener) {
        activity = clsHomeActivity;
        ModelList = inspectionModalList;
        mListener = onItemClickListener;
    }


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
//        public TextView txtItemRef;


        int position;
        TextView txtFileName;
        ImageView CancelIcon;
//        LinearLayout attachmentCountContainer;


        public ViewHolder(View view) {
            super(view);

            txtFileName = (TextView) view.findViewById(R.id.txtFileName);
            CancelIcon = (ImageView) view.findViewById(R.id.CancelIcon);
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
                inflater.inflate(R.layout.enclosure_upload_row, parent, false);
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


        final EnclosureModal enclosureModal = ModelList.get(position);
        holder.txtFileName.setText(enclosureModal.ImageName);

        holder.CancelIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onItemClick(position);
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
