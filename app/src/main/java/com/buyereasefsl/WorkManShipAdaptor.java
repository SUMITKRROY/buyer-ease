package com.buyereasefsl;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.constant.FEnumerations;
import com.podetail.POItemDtl;
import com.util.GenUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ADMIN on 11/15/2017.
 */

public class WorkManShipAdaptor extends RecyclerView.Adapter<WorkManShipAdaptor.ViewHolder> {

    Activity activity;
    List<WorkManShipModel> ModelList;
    private OnItemClickListener listener;
    public Animator mCurrentAnimator;
    onWorkManShipClickListener mListener;
    int mViewType;
    POItemDtl poItemDtl;

    public interface OnItemClickListener {
        void onItemClick(POItemDtl item);
    }

    public WorkManShipAdaptor(Activity clsHomeActivity,
                              POItemDtl mpoItemDtl, List<WorkManShipModel> workManShipModels,
                              int viewType,
                              onWorkManShipClickListener listener) {
        activity = clsHomeActivity;
        ModelList = workManShipModels;
        mListener = listener;
        mViewType = viewType;
        poItemDtl = mpoItemDtl;
    }


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
//        public TextView txtItemRef;

        ImageView attachmentImage, iconDeleteWorkmanship;
        int position;
        TextView txtCode, txtCritical, txtMajor, txtMinor, txtTotal, description, attachmentCount;
        LinearLayout attachmentCountContainer, topContainer, descriptionContainer;


        public ViewHolder(View view) {
            super(view);

            txtCode = (TextView) view.findViewById(R.id.txtCode);
            txtCritical = (TextView) view.findViewById(R.id.txtCritical);
            txtMajor = (TextView) view.findViewById(R.id.txtMajor);
            txtMinor = (TextView) view.findViewById(R.id.txtMinor);
            txtTotal = (TextView) view.findViewById(R.id.txtTotal);
            description = (TextView) view.findViewById(R.id.description);

            attachmentCount = (TextView) view.findViewById(R.id.attachmentCount);
            attachmentImage = (ImageView) view.findViewById(R.id.attachmentImage);
            attachmentCountContainer = (LinearLayout) view.findViewById(R.id.attachmentCountContainer);
            topContainer = (LinearLayout) view.findViewById(R.id.topContainer);
            descriptionContainer = (LinearLayout) view.findViewById(R.id.descriptionContainer);
            iconDeleteWorkmanship = (ImageView) view.findViewById(R.id.iconDeleteWorkmanship);
//            txtItemRef = (TextView) v.findViewById(R.id.txtItemRef);

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
                inflater.inflate(R.layout.workmanship_row, parent, false);
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


        final WorkManShipModel workManShipModel = ModelList.get(position);
        if (!TextUtils.isEmpty(workManShipModel.Code) && !workManShipModel.Code.equals("null"))
            holder.txtCode.setText(workManShipModel.Code);
        else {
            holder.txtCode.setText("-");
        }

        if (!TextUtils.isEmpty(workManShipModel.Description) && !workManShipModel.Description.equals("null"))
            holder.description.setText(workManShipModel.Description);
        else {
            holder.description.setText("-");
        }

        if (workManShipModel.Critical != 0)
            holder.txtCritical.setText(workManShipModel.Critical + "");
        else {
            holder.txtCritical.setText("-");
        }


        if (workManShipModel.Major != 0)
            holder.txtMajor.setText(workManShipModel.Major + "");
        else {
            holder.txtMajor.setText("-");
        }

        if (workManShipModel.Minor != 0)
            holder.txtMinor.setText(workManShipModel.Minor + "");
        else {
            holder.txtMinor.setText("-");
        }

        holder.txtTotal.setText((workManShipModel.Minor + workManShipModel.Major + workManShipModel.Critical) + "");

        if (mViewType == FEnumerations.VIEW_TYPE_HISTORY) {
            holder.iconDeleteWorkmanship.setVisibility(View.GONE);
            holder.attachmentCountContainer.setVisibility(View.GONE);
        } else {
            if (workManShipModel.listImages != null
                    && workManShipModel.listImages.size() > 0) {
                holder.attachmentCount.setText(workManShipModel.listImages.size() + "");
                holder.attachmentCountContainer.setVisibility(View.VISIBLE);
            } else {
                holder.attachmentCountContainer.setVisibility(View.GONE);
            }


            holder.attachmentCountContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (workManShipModel.listImages != null
                            && workManShipModel.listImages.size() > 0) {
                        ArrayList<String> list = new ArrayList<>();
                        for (int i = 0; i < workManShipModel.listImages.size(); i++) {
                            list.add(workManShipModel.listImages.get(i).selectedPicPath);
                        }
                        if (list.size() > 0) {
                            Bundle bundle = new Bundle();
                            bundle.putStringArrayList("galleryModels", list);
                            bundle.putInt("position", 0);
//                    FragmentManager fragmentManager =activity.
                            FragmentTransaction ft = ((FragmentActivity) activity)
                                    .getSupportFragmentManager().beginTransaction();
                            SlideshowDialogFragment newFragment = SlideshowDialogFragment.newInstance();
                            newFragment.setArguments(bundle);
                            newFragment.show(ft, "slideshow");
                        }
                    }
                }
            });

            holder.topContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(activity, AddWorkManShip.class);
                    intent.putExtra("podetail", GenUtils.serializePOItemModal(poItemDtl));
                    intent.putExtra("detail", GenUtils.serializeWorkmanship(workManShipModel));
                    intent.putExtra("type", FEnumerations.REQUEST_FOR_EDIT_WORKMANSHIP);
                    activity.startActivityForResult(intent, FEnumerations.REQUEST_FOR_EDIT_WORKMANSHIP);
                }
            });
            holder.descriptionContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(activity, AddWorkManShip.class);
                    intent.putExtra("podetail", GenUtils.serializePOItemModal(poItemDtl));
                    intent.putExtra("detail", GenUtils.serializeWorkmanship(workManShipModel));
                    intent.putExtra("type", FEnumerations.REQUEST_FOR_EDIT_WORKMANSHIP);
                    activity.startActivityForResult(intent, FEnumerations.REQUEST_FOR_EDIT_WORKMANSHIP);
                }
            });
            holder.iconDeleteWorkmanship.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onDeleteClick(workManShipModel, position);
                }
            });
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


    public interface onWorkManShipClickListener {
        void onDeleteClick(WorkManShipModel workManShipModel, int position);
    }


}