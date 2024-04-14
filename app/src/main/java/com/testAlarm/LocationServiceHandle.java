package com.testAlarm;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;

import com.util.FslLog;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;


/**
 * Created by ADMIN on 9/8/2017.
 */

public class LocationServiceHandle implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = "LocationServiceHandle";
    private Context context;
    private GoogleApiClient mGoogleApiClient;
    private ConnectionListener connectionListener;
    private Bundle connectionBundle;
    LocationRequest mLocationRequest;
    Intent mFusedPendingIntent;
    PendingIntent pendingIntentTogGettingUpdatedLocation;

    public LocationServiceHandle(Context context) {
        this.context = context;
        buildGoogleApiClient();
        createLocationUpdate();
        connect();
    }

    public LocationRequest createLocationUpdate() {
//        locationServiceHandle = new LocationServiceHandle(context);

        if (mLocationRequest == null) {
            FslLog.d(TAG, "create LocationRequest...");
            mLocationRequest = new LocationRequest();
            mLocationRequest.setInterval(10000);
            mLocationRequest.setFastestInterval(60000);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        } else {
            FslLog.d(TAG, "LocationRequest already created ...");
        }
        return mLocationRequest;

    }

    public GoogleApiClient getGoogleApiClient() {
        return this.mGoogleApiClient;
    }

    public void setConnectionListener(ConnectionListener connectionListener) {
        this.connectionListener = connectionListener;
        if (this.connectionListener != null && isConnected()) {
            connectionListener.onConnected(connectionBundle, mGoogleApiClient);
        }
    }

    public void connect() {
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    public void disconnect() {
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    public boolean isConnected() {
        return mGoogleApiClient != null && mGoogleApiClient.isConnected();
    }

    private void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();

    }

    @Override
    public void onConnected(Bundle bundle) {
        connectionBundle = bundle;
        FslLog.d(TAG, "googleApiClient.onConnected()");
        startLocationUpdateUsingFusedApi();
        if (connectionListener != null) {
            connectionListener.onConnected(bundle, mGoogleApiClient);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        FslLog.d(TAG, "onConnectionSuspended: googleApiClient.connect()");
        mGoogleApiClient.connect();
        if (connectionListener != null) {
            connectionListener.onConnectionSuspended(i);
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        FslLog.d(TAG, "onConnectionFailed: connectionResult = " + connectionResult);
        if (connectionListener != null) {
            connectionListener.onConnectionFailed(connectionResult);
        }
    }

    public interface ConnectionListener {
        void onConnectionFailed(ConnectionResult connectionResult);

        void onConnectionSuspended(int i);

        void onConnected(Bundle bundle, GoogleApiClient googleApiClient);
    }

    public void startLocationUpdateUsingFusedApi() {
        if (mGoogleApiClient != null) {
            if (mGoogleApiClient.isConnected()) {
                Location lastLocation = null;
                //On startLocationUpdate also get last known location
                try {
                    FslLog.d(TAG, "getting last location using FusedLocationApi");
                    lastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                } catch (SecurityException e) {
                    e.printStackTrace();
                    FslLog.e(TAG, "SecurityException: " + e.toString());
                    return;
                }


                try {
                    if (mFusedPendingIntent == null) {
                        FslLog.d(TAG, "mFusedPendingIntent created");
                        mFusedPendingIntent = new Intent(context, LocationFusedPendingIntentService.class);
                    }

                    if (pendingIntentTogGettingUpdatedLocation == null) {
                        FslLog.d(TAG, "pendingIntentTogGettingUpdatedLocation created");
                        pendingIntentTogGettingUpdatedLocation = PendingIntent
                                .getService(context, 0, mFusedPendingIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    }

                    LocationServices.FusedLocationApi
                            .requestLocationUpdates(mGoogleApiClient, mLocationRequest, pendingIntentTogGettingUpdatedLocation);

                    // LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
                } catch (SecurityException e) {
                    e.printStackTrace();
                    FslLog.e(TAG, "SecurityException: " + e.toString());
                    return;
                }
                FslLog.d(TAG, "FusedLocationApi Location update STARTED ..............: ");

            } else {
                FslLog.d(TAG, "GoogleClient not connected, connecting");
                mGoogleApiClient.connect();
            }
        }
    }
}