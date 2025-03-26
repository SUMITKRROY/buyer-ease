package com.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.FrameLayout;


public class BitmapUtil {
    public static Bitmap createFixedSizeBitmap(Bitmap srcBmp, int targetWidht, int targetHeight) {
        int srcWidth = srcBmp.getWidth();
        int srcHeight = srcBmp.getHeight();
        float scaleX = ((float) targetWidht / (float) srcWidth);
        float scaleY = ((float) targetHeight / (float) srcHeight);
        return Bitmap.createScaledBitmap(srcBmp, (int) ((float) srcWidth * scaleX), (int) ((float) srcHeight * scaleY), true);
    }

    public static Bitmap createSquaredBitmap(Bitmap srcBmp) {
        int dim = Math.min(srcBmp.getWidth(), srcBmp.getHeight());
        Bitmap dstBmp = Bitmap.createBitmap(dim, dim, Config.ARGB_8888);
        Canvas canvas = new Canvas(dstBmp);
        canvas.drawBitmap(srcBmp, (dim - srcBmp.getWidth()) / 2, (dim - srcBmp.getHeight()) / 2, null);
        return dstBmp;
    }

    public static Bitmap getCropResizedBimap(Bitmap srcBmp, int targetWidht, int targetHeight) {
        Bitmap squareBmp = createSquaredBitmap(srcBmp);
        Bitmap dstBmp = createFixedSizeBitmap(squareBmp, targetWidht, targetHeight);
        return dstBmp;

    }

    // added to handle to scale down square image
    public static Bitmap SquareScaleBitmap(Bitmap bitmap, int desiredmaxSize) {

        FslLog.d("desired max Size :", "" + desiredmaxSize);

        Bitmap deviceBitmap;
        int scaleWidth;
        int scaleHeight;

        if (bitmap.getWidth() > desiredmaxSize || bitmap.getHeight() > desiredmaxSize) {
            if (bitmap.getWidth() > bitmap.getHeight()) {
                scaleWidth = desiredmaxSize;
                scaleHeight = desiredmaxSize * bitmap.getHeight() / bitmap.getWidth();
            } else {
                scaleWidth = desiredmaxSize * bitmap.getWidth() / bitmap.getHeight();
                scaleHeight = desiredmaxSize;
            }

            deviceBitmap = Bitmap.createScaledBitmap(bitmap, scaleWidth, scaleHeight, true);
        } else {
            deviceBitmap = bitmap;
        }
        FslLog.d("After scaled width :" + deviceBitmap.getWidth(), "height :" + deviceBitmap.getHeight());
//		SocietyLog.i("scaled byte1", "" + deviceBitmap.getByteCount());
        FslLog.d("scaled byte2", "" + deviceBitmap.getByteCount());//getRowBytes() * deviceBitmap.getHeight());

        return deviceBitmap;
    }


    public static Bitmap CustomScaleBitmap(Bitmap bitmap) {


        int desiredmaxSize = 1500;

        FslLog.d("desired max Size :", "" + desiredmaxSize);

        Bitmap deviceBitmap;
        int scaleWidth;
        int scaleHeight;

        if (bitmap.getWidth() > desiredmaxSize || bitmap.getHeight() > desiredmaxSize) {
            if (bitmap.getWidth() > bitmap.getHeight()) {
                scaleWidth = desiredmaxSize;
                scaleHeight = desiredmaxSize * bitmap.getHeight() / bitmap.getWidth();
            } else {
                scaleWidth = desiredmaxSize * bitmap.getWidth() / bitmap.getHeight();
                scaleHeight = desiredmaxSize;
            }
            try {
                deviceBitmap = Bitmap.createScaledBitmap(bitmap, scaleWidth, scaleHeight, true);
            } catch (OutOfMemoryError e) {
                FslLog.d(" Btmap util : ", " OutOfMemoryError (No sufficient RAM to process) at time of createScaledBitmap");
                System.gc();
                deviceBitmap = Bitmap.createScaledBitmap(bitmap, scaleWidth, scaleHeight, true);
            }

        } else {
            deviceBitmap = bitmap;
        }
        FslLog.d("After scaled width :" + deviceBitmap.getWidth(), "height :" + deviceBitmap.getHeight());
//		FslLog.i("scaled byte1", "" + deviceBitmap.getByteCount());
        FslLog.d("scaled byte2", "" + deviceBitmap.getByteCount());//getRowBytes() * deviceBitmap.getHeight());

        return deviceBitmap;
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    // Convert a view to bitmap
    public static Bitmap createDrawableFromView(Context context, View view) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        view.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT));
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        return bitmap;
    }

    public static int getTimeFromValue(int val) {

        int intVal = val;
        int mod = intVal % 60;

        int minuteVal = intVal / 60;

        if (mod != 0)
            minuteVal = minuteVal + 1;

        //FslLog.d("converst duration Val in minute from direction api :   ", ""+minuteVal);
        return minuteVal;
    }

}
