package com.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@SuppressLint("NewApi")
public class ImgToStringConverter extends AsyncTask<Void, Void, String> {

    Context mContext;
    Bitmap mBitmap;
    ImgToStringListener listener;

    public interface ImgToStringListener {
        void onImgToStringListener(String string);
    }


    public ImgToStringConverter(Context context, Bitmap bitmap, ImgToStringListener listener) {
        // TODO Auto-generated constructor stub
        mContext = context;
        mBitmap = bitmap;
        this.listener = listener;
    }

    @Override
    protected String doInBackground(Void... params) {
        // TODO Auto-generated method stub
        try {

            return BitMapToString(mBitmap);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
        listener.onImgToStringListener(result);


    }

    public String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();


        //bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);


        return temp;
    }
    public static String  BitMapToStringT(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();


        //bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);


        return temp;
    }
    public static String encodeFileToBase64Binary(String fileName)
            throws IOException {

        File file = new File(fileName);

        byte[] bytes = loadFile(file);
        byte[] encoded = Base64.encode(bytes, Base64.DEFAULT);
        String encodedString = new String(encoded);

        return encodedString;
    }


    private static byte[] loadFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);

        long length = file.length();
        if (length > Integer.MAX_VALUE) {
            // File is too large
        }
        byte[] bytes = new byte[(int) length];

        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
                && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
            offset += numRead;
        }

        if (offset < bytes.length) {
            throw new IOException("Could not completely read file " + file.getName());
        }

        is.close();
        return bytes;
    }
}




