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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.constant.FEnumerations;
import com.podetail.POItemDtl;
import com.util.GenUtils;

import java.util.ArrayList;
import java.util.List;

import me.drakeet.support.toast.ToastCompat;

/**
 * Created by ADMIN on 12/25/2017.
 */

public class ItemMeasurementAdaptor extends RecyclerView.Adapter<ItemMeasurementAdaptor.ViewHolder> {

    Activity activity;
    List<ItemMeasurementModal> ModelList;
    private OnItemClickListener listener;
    public Animator mCurrentAnimator;
    OnItemMeasurementClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(POItemDtl item);
    }

    POItemDtl mpoItemDtl;
    RecyclerView recyclerView;
    int mViewType;

    public ItemMeasurementAdaptor(Activity clsHomeActivity,
                                  POItemDtl poItemDtl,
                                  int viewtype,
                                  List<ItemMeasurementModal> itemMeasurementModals, RecyclerView mRecyclerView
            , OnItemMeasurementClickListener listener) {
        activity = clsHomeActivity;
        ModelList = itemMeasurementModals;
        mpoItemDtl = poItemDtl;
        mListener = listener;
        recyclerView = mRecyclerView;
        mViewType = viewtype;
    }


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
//        public TextView txtItemRef;

        ImageView txtFinding, iconDeleteWorkmanship;
        int position;
        TextView txtItemLength, txtItemHeight, txtItemWidth, txtItemSampleSize, description, attachmentCount;
        EditText toleranceRange;
        LinearLayout attachmentCountContainer;
        ImageView attachmentImage;

        public ViewHolder(View view) {
            super(view);

            txtItemLength = (TextView) view.findViewById(R.id.txtItemLength);
            txtItemHeight = (TextView) view.findViewById(R.id.txtItemHeight);
            txtItemWidth = (TextView) view.findViewById(R.id.txtItemWidth);
            txtItemSampleSize = (TextView) view.findViewById(R.id.txtItemSampleSize);
            toleranceRange = (EditText) view.findViewById(R.id.toleranceRange);
            description = (TextView) view.findViewById(R.id.description);

            attachmentCount = (TextView) view.findViewById(R.id.attachmentCount);
            attachmentImage = (ImageView) view.findViewById(R.id.attachmentImage);
            attachmentCountContainer = (LinearLayout) view.findViewById(R.id.attachmentCountContainer);
            txtFinding = (ImageView) view.findViewById(R.id.txtFinding);
            iconDeleteWorkmanship = (ImageView) view.findViewById(R.id.iconDeleteWorkmanship);

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
                inflater.inflate(R.layout.item_measurement_row, parent, false);
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


        final ItemMeasurementModal itemMeasurementModal = ModelList.get(position);

        holder.txtItemLength.setText(itemMeasurementModal.Dim_length + "");
        holder.txtItemHeight.setText(itemMeasurementModal.Dim_Height + "");
        holder.txtItemWidth.setText(itemMeasurementModal.Dim_Width + "");
        holder.txtItemSampleSize.setText(itemMeasurementModal.SampleSizeValue);
        holder.toleranceRange.setText(itemMeasurementModal.Tolerance_Range);
        holder.description.setText(itemMeasurementModal.ItemMeasurementDescr);

        if (TextUtils.isEmpty(itemMeasurementModal.SampleSizeValue) || itemMeasurementModal.SampleSizeValue.equals("null")) {
            holder.txtItemSampleSize.setText("Select Sample");
        }
        if (mViewType == FEnumerations.VIEW_TYPE_HISTORY) {
            holder.txtItemSampleSize.setVisibility(View.INVISIBLE);
            holder.iconDeleteWorkmanship.setVisibility(View.GONE);
            holder.toleranceRange.setEnabled(false);
            holder.toleranceRange.setClickable(false);
            holder.attachmentCountContainer.setVisibility(View.GONE);
        } else {
            holder.toleranceRange.setEnabled(true);
            holder.txtItemSampleSize.setVisibility(View.VISIBLE);
            holder.iconDeleteWorkmanship.setVisibility(View.VISIBLE);

            holder.txtItemLength.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(activity, AddItemMeasurement.class);
                    intent.putExtra("pos", position);
                    intent.putExtra("detail", GenUtils.serializeItemMeasurementModal(itemMeasurementModal));
                    intent.putExtra("poItemDtl", GenUtils.serializePOItemModal(mpoItemDtl));
                    intent.putExtra("type", FEnumerations.REQUEST_FOR_EDIT_ITEM_MEASUREMENT);
                    activity.startActivityForResult(intent, FEnumerations.REQUEST_FOR_EDIT_ITEM_MEASUREMENT);

                }
            });

            holder.txtItemHeight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(activity, AddItemMeasurement.class);
                    intent.putExtra("pos", position);
                    intent.putExtra("detail", GenUtils.serializeItemMeasurementModal(itemMeasurementModal));
                    intent.putExtra("poItemDtl", GenUtils.serializePOItemModal(mpoItemDtl));
                    intent.putExtra("type", FEnumerations.REQUEST_FOR_EDIT_ITEM_MEASUREMENT);
                    activity.startActivityForResult(intent, FEnumerations.REQUEST_FOR_EDIT_ITEM_MEASUREMENT);

                }
            });

            holder.txtItemWidth.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(activity, AddItemMeasurement.class);
                    intent.putExtra("pos", position);
                    intent.putExtra("detail", GenUtils.serializeItemMeasurementModal(itemMeasurementModal));
                    intent.putExtra("poItemDtl", GenUtils.serializePOItemModal(mpoItemDtl));
                    intent.putExtra("type", FEnumerations.REQUEST_FOR_EDIT_ITEM_MEASUREMENT);
                    activity.startActivityForResult(intent, FEnumerations.REQUEST_FOR_EDIT_ITEM_MEASUREMENT);

                }
            });

            holder.txtItemSampleSize.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(activity, AddItemMeasurement.class);
                    intent.putExtra("pos", position);
                    intent.putExtra("detail", GenUtils.serializeItemMeasurementModal(itemMeasurementModal));
                    intent.putExtra("poItemDtl", GenUtils.serializePOItemModal(mpoItemDtl));
                    intent.putExtra("type", FEnumerations.REQUEST_FOR_EDIT_ITEM_MEASUREMENT);
                    activity.startActivityForResult(intent, FEnumerations.REQUEST_FOR_EDIT_ITEM_MEASUREMENT);

                }
            });


            holder.txtFinding.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!TextUtils.isEmpty(itemMeasurementModal.SampleSizeValue) && !itemMeasurementModal.SampleSizeValue.equals("null")) {
                        Intent intent = new Intent(activity, ItemMeasurementFindingActivity.class);
                        intent.putExtra("pos", position);
                        intent.putExtra("pod", GenUtils.serializePOItemModal(mpoItemDtl));
                        intent.putExtra("detail", GenUtils.serializeItemMeasurementModal(itemMeasurementModal));
                        activity.startActivityForResult(intent, FEnumerations.REQUEST_FOR_ADD_ITEM_MEASUREMENT_FINDING);
                    } else {
                        Toast toast = ToastCompat.makeText(activity, "Select sample Size", Toast.LENGTH_SHORT);
                        GenUtils.safeToastShow("ItemMeasurementAdapter", activity, toast);
                    }
                }
            });
            holder.iconDeleteWorkmanship.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onDeleteClick(itemMeasurementModal, position);
                }
            });

            holder.toleranceRange.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
//                                final int position = v.getId();

                        final EditText Caption = (EditText) v;
                        if (!TextUtils.isEmpty(Caption.getText().toString())) {
                            ModelList.get(position).Tolerance_Range = Caption.getText().toString();


                            if (recyclerView != null && !recyclerView.isComputingLayout()) {
                                notifyDataSetChanged();
                            }
                        }
                    }
                }
            });

            if (itemMeasurementModal.listImages != null
                    && itemMeasurementModal.listImages.size() > 0) {
                holder.attachmentCount.setText(itemMeasurementModal.listImages.size() + "");
                holder.attachmentCountContainer.setVisibility(View.VISIBLE);
            } else {
                holder.attachmentCountContainer.setVisibility(View.GONE);
            }


            holder.attachmentCountContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (itemMeasurementModal.listImages != null
                            && itemMeasurementModal.listImages.size() > 0) {
                        ArrayList<String> list = new ArrayList<>();
                        for (int i = 0; i < itemMeasurementModal.listImages.size(); i++) {
                            list.add(itemMeasurementModal.listImages.get(i).selectedPicPath);
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


    public interface OnItemMeasurementClickListener {
        void onDeleteClick(ItemMeasurementModal itemMeasurementModal, int position);
    }

}