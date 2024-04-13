package com.buyereasefsl;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by ADMIN on 1/9/2018.
 */

public class IntimationAdaptor extends RecyclerView.Adapter<IntimationAdaptor.ViewHolder> {

    Activity activity;
    List<IntimationModal> ModelList;
    public Animator mCurrentAnimator;
    RecyclerView recyclerView;

    public IntimationAdaptor(Activity clsHomeActivity,
                             RecyclerView recyclerViewQualityParameter,
                             List<IntimationModal> digitalsUploadModals) {
        activity = clsHomeActivity;
        ModelList = digitalsUploadModals;
        recyclerView = recyclerViewQualityParameter;

    }


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
//        public TextView txtItemRef;


        int position;
        TextView txtEmailLabel, txtNameLabel;

        CheckBox chkSelectLabel, chkLinkLabel, chkReportLabel, chkHtmlLinkLabel, chkReceiveApplicableLabel;


        public ViewHolder(View view) {
            super(view);

            txtEmailLabel = (TextView) view.findViewById(R.id.txtEmailLabel);
            txtNameLabel = (TextView) view.findViewById(R.id.txtNameLabel);

            chkSelectLabel = (CheckBox) view.findViewById(R.id.chkSelectLabel);
            chkLinkLabel = (CheckBox) view.findViewById(R.id.chkLinkLabel);
            chkReportLabel = (CheckBox) view.findViewById(R.id.chkReportLabel);
            chkHtmlLinkLabel = (CheckBox) view.findViewById(R.id.chkHtmlLinkLabel);
            chkReceiveApplicableLabel = (CheckBox) view.findViewById(R.id.chkReceiveApplicableLabel);

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
                inflater.inflate(R.layout.intimation_row, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
//        final String name = values.get(position);


        final IntimationModal intimationModal = ModelList.get(position);


        holder.txtNameLabel.setText(intimationModal.Name);
        holder.txtEmailLabel.setText(intimationModal.EmailID);


        if (intimationModal.IsLink == 0) {
            holder.chkLinkLabel.setChecked(false);
        } else {
            holder.chkLinkLabel.setChecked(true);
        }
        if (intimationModal.IsReport == 0) {
            holder.chkReportLabel.setChecked(false);
        } else {
            holder.chkReportLabel.setChecked(true);
        }
        if (intimationModal.IsHtmlLink == 0) {
            holder.chkHtmlLinkLabel.setChecked(false);
        } else {
            holder.chkHtmlLinkLabel.setChecked(true);
        }
        if (intimationModal.IsRcvApplicable == 0) {
            holder.chkReceiveApplicableLabel.setChecked(false);
        } else {
            holder.chkReceiveApplicableLabel.setChecked(true);
        }

        if (intimationModal.IsSelected == 0) {
            holder.chkSelectLabel.setChecked(false);
        } else {
            holder.chkSelectLabel.setChecked(true);
        }


        holder.chkLinkLabel.setTag(intimationModal);
        holder.chkLinkLabel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                IntimationModal inspModal = (IntimationModal) cb.getTag();
                if (cb.isChecked()) {
                    inspModal.IsLink = 1; //cb.isChecked();
                    ModelList.get(position).IsLink = 1;
                } else {
                    inspModal.IsLink = 0; //cb.isChecked();
                    ModelList.get(position).IsLink = 0;

                }
            }
        });


        holder.chkReportLabel.setTag(intimationModal);
        holder.chkReportLabel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                IntimationModal inspModal = (IntimationModal) cb.getTag();
                if (cb.isChecked()) {
                    inspModal.IsReport = 1; //cb.isChecked();
                    ModelList.get(position).IsReport = 1;
                } else {
                    inspModal.IsReport = 0; //cb.isChecked();
                    ModelList.get(position).IsReport = 0;

                }
            }
        });

        holder.chkHtmlLinkLabel.setTag(intimationModal);
        holder.chkHtmlLinkLabel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                IntimationModal inspModal = (IntimationModal) cb.getTag();
                if (cb.isChecked()) {
                    inspModal.IsHtmlLink = 1; //cb.isChecked();
                    ModelList.get(position).IsHtmlLink = 1;
                } else {
                    inspModal.IsHtmlLink = 0; //cb.isChecked();
                    ModelList.get(position).IsHtmlLink = 0;

                }
            }
        });

        holder.chkReceiveApplicableLabel.setTag(intimationModal);
        holder.chkReceiveApplicableLabel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                IntimationModal inspModal = (IntimationModal) cb.getTag();
                if (cb.isChecked()) {
                    inspModal.IsRcvApplicable = 1; //cb.isChecked();
                    ModelList.get(position).IsRcvApplicable = 1;
                } else {
                    inspModal.IsRcvApplicable = 0; //cb.isChecked();
                    ModelList.get(position).IsRcvApplicable = 0;

                }
            }
        });


        holder.chkSelectLabel.setTag(intimationModal);
        holder.chkSelectLabel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                IntimationModal inspModal = (IntimationModal) cb.getTag();
                if (cb.isChecked()) {
                    inspModal.IsSelected = 1; //cb.isChecked();
                    ModelList.get(position).IsSelected = 1;
                } else {
                    inspModal.IsSelected = 0; //cb.isChecked();
                    ModelList.get(position).IsSelected = 0;
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return ModelList.size();
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private  ClickListener clickListener;

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


    public interface onWorkManShipClickListener {
        void onClick(int itemReportPosition, WorkManShipModel workManShipModel, int position);
    }


}