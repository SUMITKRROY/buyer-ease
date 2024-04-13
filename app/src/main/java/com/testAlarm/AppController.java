package com.testAlarm;

import android.app.Application;
import android.text.TextUtils;



public class AppController extends Application {

    public static final String TAG = AppController.class
            .getSimpleName();

//    private RequestQueue mRequestQueue;
//    private ImageLoader mImageLoader;

    private static AppController mInstance;
    private LocationServiceHandle locationServiceHandle;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        locationServiceHandle = new LocationServiceHandle(mInstance);
        /*if (SocietyClientConfig.mExternalStorageDir == null) {
            SetInitiateStaticVariable.setInitiateStaticVariable(this);
        }*/
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

//    public RequestQueue getRequestQueue() {
//        if (mRequestQueue == null) {
//            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
//        }
//
//        return mRequestQueue;
//    }

//    public LocationServiceHandle getGoogleApiHelperInstance() {
//        return this.locationServiceHandle;
//    }

//    public static LocationServiceHandle getLocationServiceHandle() {
//        return getInstance().getGoogleApiHelperInstance();
//    }

//    public ImageLoader getImageLoader() {
//        getRequestQueue();
//        if (mImageLoader == null) {
//            mImageLoader = new ImageLoader(this.mRequestQueue,
//                    new LruBitmapCache());
//        }
//        return this.mImageLoader;
//    }

//    public <T> void addToRequestQueue(Request<T> req, String tag) {
//         set the default tag if tag is empty
//        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
//        getRequestQueue().add(req);
//    }

//    public <T> void addToRequestQueue(Request<T> req) {
//        req.setTag(TAG);
//        getRequestQueue().add(req);
//    }

//    public void cancelPendingRequests(Object tag) {
//        if (mRequestQueue != null) {
//            mRequestQueue.cancelAll(tag);
//        }
//    }
}