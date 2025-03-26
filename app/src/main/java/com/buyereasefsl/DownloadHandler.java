package com.buyereasefsl;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.constant.FClientConfig;
import com.constant.FEnumerations;
import com.util.FslLog;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by ADMIN on 1/10/2018.
 */

public class DownloadHandler extends AsyncTask<String, Integer, String> {
    String TAG = "DownloadHandler";
    Context mContext;

    String fileExt;
    CallBackToDownloadResult callBackToDownloadResult;
    ProgressDialog mProgressDialog;
    int mType;
    String mFileName;

    public DownloadHandler(final Context context,  String fileName,
                           String nfileExt,
                           int type,
                           CallBackToDownloadResult mcallBackToDownloadResult) {
        mContext = context;

        fileExt = nfileExt;
        mFileName = fileName;
        mType = type;
        callBackToDownloadResult = mcallBackToDownloadResult;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // Create progress dialog
        mProgressDialog = new ProgressDialog(mContext);
        // Set your progress dialog Title
//        mProgressDialog.setTitle("Progress Bar Tutorial");
        // Set your progress dialog Message
        mProgressDialog.setMessage("Downloading, Please Wait!");
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setMax(100);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        // Show progress dialog
        mProgressDialog.show();
    }

    @Override
    protected String doInBackground(String... Url) {
        String fileName = null;
        try {
            URL url = new URL(Url[0]);
            URLConnection connection = url.openConnection();
            connection.connect();

            // Detect the file lenghth
            int fileLength = connection.getContentLength();

            // Locate storage location
//            String filepath = Environment.getExternalStorageDirectory() .getPath();
            String folderName = null;
            if (mType == FEnumerations.E_DOWNLOAD_TEST_REPORT) {
                folderName = FClientConfig.testReportFolder;
            } else if (mType == FEnumerations.E_DOWNLOAD_ENCLOSURE) {
                folderName = FClientConfig.enclosureFolder;
            }
            if (TextUtils.isEmpty(folderName)) {
                FslLog.d(TAG, " FOLDER NAME is NULL ......???????????????");
                return null;
            }

            File newFile = new File(FClientConfig.mExternalStorageDir + File.separator + folderName);
            boolean success = true;
            if (!newFile.exists()) {
                newFile.mkdirs();
                FslLog.d(TAG, folderName + " new folder created");
            } else {
                FslLog.d(TAG, folderName + " folder is already folder created");
            }

            // Download the file
            InputStream input = new BufferedInputStream(url.openStream());
            fileName = mFileName + fileExt;
            String filePath = newFile.getPath() + "/" + fileName;

            // Save the downloaded file
            OutputStream output = new FileOutputStream(filePath);

            byte data[] = new byte[1024];
            long total = 0;
            int count;
            while ((count = input.read(data)) != -1) {
                total += count;
                // Publish the progress
                publishProgress((int) (total * 100 / fileLength));
                output.write(data, 0, count);
            }

            // Close connection
            output.flush();
            output.close();
            input.close();
        } catch (Exception e) {
            // Error Log
            FslLog.e(TAG, "Error " + e.getMessage());
            e.printStackTrace();
        }
        return fileName;
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        super.onProgressUpdate(progress);
        // Update the progress dialog
        mProgressDialog.setProgress(progress[0]);
        // Dismiss the progress dialog
        //mProgressDialog.dismiss();
    }

    protected void onPostExecute(String result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
        mProgressDialog.dismiss();
        callBackToDownloadResult.onSuccess(result);


    }


    public interface CallBackToDownloadResult {
        public void onSuccess(String result);


    }
}
