package com.buyereasefsl;

import android.app.Activity;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.podetail.POItemDtl;
import com.util.GenUtils;

import java.util.List;

/**
 * Created by ADMIN on 12/25/2017.
 */

public class ItemMeasurementFindingAdaptor extends RecyclerView.Adapter<ItemMeasurementFindingAdaptor.ViewHolder> {

    Activity activity;
    List<ItemMeasurementModal> ModelList;
    RecyclerView mRecyclerView;

    public interface OnItemClickListener {
        void onItemClick(POItemDtl item);
    }

    ItemMeasurementModal mItemMeasurementModal;

    public ItemMeasurementFindingAdaptor(Activity clsHomeActivity, RecyclerView recyclerView,
                                         List<ItemMeasurementModal> itemMeasurementModals, ItemMeasurementModal itemMeasurementModal) {
        activity = clsHomeActivity;
        ModelList = itemMeasurementModals;
        mRecyclerView = recyclerView;
        mItemMeasurementModal = itemMeasurementModal;
    }


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
//        public TextView txtItemRef;

        TextView txtItemSN;

        EditText txtItemLength, txtItemHeight, txtItemWidth;


        public ViewHolder(View view) {
            super(view);

            txtItemSN = (TextView) view.findViewById(R.id.txtItemSN);
            txtItemLength = (EditText) view.findViewById(R.id.txtItemLength);
            txtItemHeight = (EditText) view.findViewById(R.id.txtItemHeight);
            txtItemWidth = (EditText) view.findViewById(R.id.txtItemWidth);


        }
    }


    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.finding_row, parent, false);
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

        holder.txtItemSN.setText(position + 1 + "");
        holder.txtItemLength.setText(itemMeasurementModal.Dim_length + "");
        holder.txtItemHeight.setText(itemMeasurementModal.Dim_Height + "");
        holder.txtItemWidth.setText(itemMeasurementModal.Dim_Width + "");

        holder.txtItemLength.setTextColor(GenUtils.getColorResource(activity, R.color.colorBlack));
        holder.txtItemHeight.setTextColor(GenUtils.getColorResource(activity, R.color.colorBlack));
        holder.txtItemWidth.setTextColor(GenUtils.getColorResource(activity, R.color.colorBlack));
        if (itemMeasurementModal.Dim_length > mItemMeasurementModal.Dim_length) {
            holder.txtItemLength.setTextColor(GenUtils.getColorResource(activity, R.color.red));
        }
        if (itemMeasurementModal.Dim_Height > mItemMeasurementModal.Dim_Height) {
            holder.txtItemHeight.setTextColor(GenUtils.getColorResource(activity, R.color.red));
        }
        if (itemMeasurementModal.Dim_Width > mItemMeasurementModal.Dim_Width) {
            holder.txtItemWidth.setTextColor(GenUtils.getColorResource(activity, R.color.red));
        }
        holder.txtItemLength.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
//                                final int position = v.getId();
                    final EditText edt = (EditText) v;
                    ModelList.get(position).Dim_length = Double.parseDouble(edt.getText().toString());


                    if (mRecyclerView != null && !mRecyclerView.isComputingLayout()) {
                        notifyDataSetChanged();
                    }

                }
            }
        });

        holder.txtItemHeight.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
//                                final int position = v.getId();
                    final EditText Caption = (EditText) v;
                    ModelList.get(position).Dim_Height = Double.parseDouble(Caption.getText().toString());


                    if (mRecyclerView != null && !mRecyclerView.isComputingLayout()) {
                        notifyDataSetChanged();
                    }

                }
            }
        });

        holder.txtItemWidth.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
//                                final int position = v.getId();
                    final EditText Caption = (EditText) v;
                    ModelList.get(position).Dim_Width = Double.parseDouble(Caption.getText().toString());


                    if (mRecyclerView != null && !mRecyclerView.isComputingLayout()) {
                        notifyDataSetChanged();
                    }

                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return ModelList.size();
    }


}