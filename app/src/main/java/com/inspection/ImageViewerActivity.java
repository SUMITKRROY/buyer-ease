package com.inspection;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.buyereasefsl.R;

import java.util.ArrayList;

public class ImageViewerActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private TextView txtCounter;
    private ImageButton btnClose;
    private ArrayList<String> imagePaths;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer);
        
        viewPager = findViewById(R.id.viewPager);
        txtCounter = findViewById(R.id.txtCounter);
        btnClose = findViewById(R.id.btnClose);
        
        imagePaths = getIntent().getStringArrayListExtra("IMAGES");
        
        // Set up adapter
        ImageViewerAdapter adapter = new ImageViewerAdapter(this, imagePaths);
        viewPager.setAdapter(adapter);
        
        updateCounter(0);
        
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                updateCounter(position);
            }
        });
        
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    
    private void updateCounter(int position) {
        txtCounter.setText(String.format("%d/%d", position + 1, imagePaths.size()));
    }
} 