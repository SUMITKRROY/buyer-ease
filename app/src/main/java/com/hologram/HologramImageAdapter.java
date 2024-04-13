package com.hologram;

import android.animation.Animator;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.buyereasefsl.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class HologramImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Activity activity;
    List<String> ModelList;
    private CallBackClick listener;
    public Animator mCurrentAnimator;


    private ArrayList<String> searchList;
    RecyclerView mRecyclerView;
    int mViewType;

    public HologramImageAdapter(Activity clsHomeActivity, RecyclerView recyclerView,
                                int viewType,
                                List<String> lList, CallBackClick mL) {
        activity = clsHomeActivity;
        ModelList = lList;
        mRecyclerView = recyclerView;
        listener = mL;
        mViewType = viewType;
        this.searchList = new ArrayList<String>();
        this.searchList.addAll(ModelList);
    }

   /* @Override
    public int getItemViewType(int position) {
        if (mViewType == FEnumerations.VIEW_PRODUCT_V_LIST) {
            return FEnumerations.VIEW_PRODUCT_V_LIST;
        } else {
            return FEnumerations.VIEW_PRODUCT_H_LISt;
        }

    }*/

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


       /* if (viewType == FEnumerations.VIEW_PRODUCT_V_LIST) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_row, parent, false);
            return new VerticalViewHolder(view);
        } else if (viewType == FEnumerations.VIEW_PRODUCT_H_LISt) {*/
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hologram_image_row, parent, false);
        return new HorizontalViewHolder(view);
//        }
//        return null;

//        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof HorizontalViewHolder) {
            final String path = ModelList.get(position);
            final HorizontalViewHolder horizontalViewHolder = (HorizontalViewHolder) holder;

            if (!TextUtils.isEmpty(path) && ! "null".equalsIgnoreCase(path)) {
                Glide.with(activity)
                        .load(new File(path))
                        .override(56, 56)
                        .placeholder(activity.getResources().getDrawable(R.drawable.default_image_large))
                        .into(horizontalViewHolder.hologramImage);
            }
            horizontalViewHolder.iconImageCancel.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    listener.onRemove(path, position);
                }
            });

        }


    }


    class HorizontalViewHolder extends RecyclerView.ViewHolder {

        ImageView hologramImage, iconImageCancel;


        public HorizontalViewHolder(View view) {
            super(view);
            hologramImage = (ImageView) view.findViewById(R.id.hologramImage);
            iconImageCancel = (ImageView) view.findViewById(R.id.iconImageCancel);
        }
    }

    @Override
    public int getItemCount() {
        return ModelList.size();
    }


    public interface CallBackClick {
        public void onRemove(String productModel, int position);

    }
}