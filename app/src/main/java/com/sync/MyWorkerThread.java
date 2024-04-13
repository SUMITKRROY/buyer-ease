package com.sync;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;

import com.android.volley.VolleyError;
import com.util.FslLog;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by ADMIN on 1/22/2018.
 */

public class MyWorkerThread extends HandlerThread {

    private Handler mWorkerHandler;
    private Handler mResponseHandler;
    private static final String TAG = MyWorkerThread.class.getSimpleName();
    private Map<Integer, Object> mRequestMap = new HashMap<Integer, Object>();
    private Callback mCallback;

    public interface Callback {
        public void onImageUploaded(JSONObject pos, int status);

        public void onErrorUploaded(VolleyError volleyError, int pos);
    }

    public MyWorkerThread(Handler responseHandler, Callback callback) {
        super(TAG);
        mResponseHandler = responseHandler;
        mCallback = callback;
    }

    public void queueTask(Map<String, Object> jsonObject, int pos) {
        mRequestMap.put(pos, jsonObject);
        FslLog.i(TAG, jsonObject + " added to the queue");
        mWorkerHandler.obtainMessage(pos, jsonObject)
                .sendToTarget();
    }

    /*public void prepareHandler() {
        mWorkerHandler = new Handler(getLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Map<String, Object> mapJ = (Map<String, Object>) msg.obj;
                int position = msg.what;
//                String side = msg.what == MyActivity.LEFT_SIDE ? "left side" : "right side";
//                FslLog.i(TAG, String.format("Processing %s, %s", mRequestMap.get(imageView), side));
                handleRequest(mapJ, position);
                try {
                    msg.recycle(); //it can work in some situations
                } catch (IllegalStateException e) {
                    mWorkerHandler.removeMessages(msg.what); //if recycle doesnt work we do it manually
                }
                return true;
            }
        });
    }*/

   /* private void handleRequest(final Map<String, Object> jsonMap, int pos) {
//        Map<String, Object> mapJ = (Map<String, Object>) mRequestMap.get(pos);
        FslLog.d(TAG, "SINGLE IMAGES sync .........................................\n\n");
        SendDataHandler sendDataHandler = new SendDataHandler();
        sendDataHandler.sendData(jsonMap, "", new SendDataHandler.GetCallBackResult() {
            @Override
            public void onSuccess(JSONObject result) {

                mCallback.onImageUploaded(result, pos);
            }

            @Override
            public void onError(VolleyError volleyError) {
                mCallback.onErrorUploaded(volleyError, pos);
//                updateSyncList(FEnumerations.E_SYNC_IMAGES_TABLE, FEnumerations.E_SYNC_FAILED_STATUS);
//                requestVolleyError(volleyError);
            }
        });
        mRequestMap.remove(pos);

    }*/
}
