package com.buyereasefsl;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.util.ChangeOpacity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ADMIN on 12/20/2017.
 */

public class DigitalUploadAdaptor extends RecyclerView.Adapter<DigitalUploadAdaptor.ViewHolder> {

    Activity activity;
    List<DigitalsUploadModal> ModelList;
    private onClickListener mListener;
    public Animator mCurrentAnimator;
//    onWorkManShipClickListener mListener;

//    public interface OnItemClickListener {
//        void onItemClick(POItemDtl item);
//    }

    public DigitalUploadAdaptor(Activity clsHomeActivity,
                                List<DigitalsUploadModal> digitalsUploadModals, onClickListener listener) {
        activity = clsHomeActivity;
        ModelList = digitalsUploadModals;
        mListener = listener;
    }


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
//        public TextView txtItemRef;

        ImageView attachmentImage, iconCancel;
        int position;
        TextView txtDescription;
        TextView txtTitle;
//        LinearLayout attachmentCountContainer;


        public ViewHolder(View view) {
            super(view);

            txtTitle = (TextView) view.findViewById(R.id.txtTitle);
            txtDescription = (TextView) view.findViewById(R.id.txtDescription);

            attachmentImage = (ImageView) view.findViewById(R.id.attachmentImage);
//            attachmentCountContainer = (LinearLayout) view.findViewById(R.id.attachmentCountContainer);

            iconCancel = (ImageView) view.findViewById(R.id.iconCancel);
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
                inflater.inflate(R.layout.digitalsupload_row, parent, false);
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


        final DigitalsUploadModal workManShipModel = ModelList.get(position);

        if (!TextUtils.isEmpty(workManShipModel.title) && !workManShipModel.title.equals("null"))
            holder.txtTitle.setText(workManShipModel.title);
        else {
            holder.txtTitle.setText("-");
        }

//        if (!TextUtils.isEmpty(workManShipModel.Description) && !workManShipModel.Description.equals("null"))
//            holder.txtDescription.setText(workManShipModel.Description);
//        else {
//            holder.txtDescription.setText("-");
//        }


       /* holder.txtTitle.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
//                                final int position = v.getId();
                    final EditText Caption = (EditText) v;
                    if (!TextUtils.isEmpty(Caption.getText().toString())) {
                        workManShipModel.title = Caption.getText().toString();
                    }
                }
            }
        });*/

        holder.txtTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClickForEdit(workManShipModel,position);
            }
        });

        holder.iconCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onDigitalCancelClick(workManShipModel, position);
            }
        });

//        if (workManShipModel.listImages != null
//                && workManShipModel.listImages.size() > 0) {
//
//            holder.attachmentCountContainer.setVisibility(View.VISIBLE);
//        } else {
//            holder.attachmentCountContainer.setVisibility(View.GONE);
//        }
        if (!TextUtils.isEmpty(workManShipModel.selectedPicPath) && !workManShipModel.selectedPicPath.equals("null")) {
//            Uri uri = Uri.fromFile(new File(workManShipModel.selectedPicPath));
//            holder.attachmentImage.setImageURI(uri);
//            MultipleImageHandler.startFeather(activity, uri, new MultipleImageHandler.GetBitmap() {
//                @Override
//                public void onGetBitamp(Bitmap serverBitmap, ArrayList<String> imagePathArrayList, String valueReturned) {


//            byte[] decodedString = Base64.decode(workManShipModel.selectedPicPath, Base64.DEFAULT);

            Glide.with(activity)
                    .load(new File(workManShipModel.selectedPicPath))
                    .override(50,50)
                    .placeholder(activity.getResources().getDrawable(R.drawable.default_image_large))
                    .into(holder.attachmentImage);


            final ArrayList<String> list = new ArrayList<>();
            list.add(workManShipModel.selectedPicPath);


            holder.attachmentImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (workManShipModel.selectedPicPath != null
                            /*&& workManShipModel.listImages.size() > 0*/) {
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
//                        }
//                    });
                }
            });
        } else {
            String downloadUrl = "" /*AppConfig.URL_DOWNLOAD_BASE_URL +
                    "Images/" + workManShipModel.ImageName + "&PathToken=" + workManShipModel.ImagePathID*/;

         /*   Glide.with(activity)
                    .load(downloadUrl)
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.attachmentImage);*/

            Glide.with(activity)
                    .load(downloadUrl)
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            holder.attachmentImage.setImageDrawable(ChangeOpacity.getFadedImage(activity, R.drawable.default_image_large));
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            return false;
                        }
                    })
                    .into(holder.attachmentImage);
        }


    }

    @Override
    public int getItemCount() {
        return ModelList.size();
    }

    /*public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

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
    }*/

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }


    public interface onClickListener {
        void onClickForEdit(DigitalsUploadModal mDigitalsUploadModal,int position);
        void onDigitalCancelClick(DigitalsUploadModal workManShipModel, int position);
    }


}