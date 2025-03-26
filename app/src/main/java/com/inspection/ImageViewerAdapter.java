package com.inspection;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.buyereasefsl.R;

import java.io.File;
import java.util.ArrayList;

public class ImageViewerAdapter extends RecyclerView.Adapter<ImageViewerAdapter.ViewHolder> {

    private Context context;
    private ArrayList<String> imagePaths;
    
    public ImageViewerAdapter(Context context, ArrayList<String> imagePaths) {
        this.context = context;
        this.imagePaths = imagePaths;
    }
    
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_fullscreen_image, parent, false);
        return new ViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String imagePath = imagePaths.get(position);
        File imageFile = new File(imagePath);
        
        if (!imageFile.exists()) {
            Log.e("ImageViewerAdapter", "Image file does not exist: " + imagePath);
        }
        
        Glide.with(context)
            .load(imageFile)
            .error(android.R.drawable.ic_dialog_alert)
            .into(holder.imageView);
    }
    
    @Override
    public int getItemCount() {
        return imagePaths.size();
    }
    
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.fullScreenImageView);
        }
    }
} 