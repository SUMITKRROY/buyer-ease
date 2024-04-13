package com.util;

import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;
import android.location.LocationManager;
import android.provider.Settings;
import android.text.TextUtils;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;


public class GPSDialog {

    public static String TAG = "GPSDialog";


    public static void checkEnableAndShowDialog(final Activity activity,
                                                final LocationRequest mLocationRequest,
                                                GoogleApiClient mGoogleApiClient, final OnGpsStatus onGpsStatus) {

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest);
        builder.setAlwaysShow(true); //this is the key ingredient

        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                final LocationSettingsStates state = result.getLocationSettingsStates();
                FslLog.d(TAG, "Result of GPS enable dialog status code - " + status.getStatusCode());
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:

                        onGpsStatus.onSuccess();
                        //handle on success

                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                        try {

                            status.startResolutionForResult(activity, Initialization.GPS_ENABLE_REQUEST_ON_PICKUP);


                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        FslLog.d(TAG, "check dialog");
                        // Location settings are not satisfied. However, we have no way to fix the
                        // settings so we won't show the dialog.
                        break;
                    case LocationSettingsStatusCodes.CANCELED:
                        FslLog.d(TAG, "check dialog");
                        break;
                    /*No need to check*/
                  /*  case LocationSettingsStatusCodes.SERVICE_DISABLED:
                        FslLog.d("check", "dialog");
                        break;*/
                }
            }
        });
    }


    public static boolean isGpsEnable(Context context) {
        String locationProviders;
        boolean isGPSEnabledLocMgr = false;
        boolean isGPSEnabledLocProviders = false;


        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        isGPSEnabledLocMgr = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        FslLog.d(TAG, isGPSEnabledLocMgr + ": GPS status using LocationManager technique");

        //if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
        locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        isGPSEnabledLocProviders = !TextUtils.isEmpty(locationProviders);
        FslLog.d(TAG, isGPSEnabledLocProviders + ": GPS status using locationProviders technique: " + locationProviders);
        //} else
        //isGPSEnabledLocProviders = isGPSEnabledLocMgr;

        if (isGPSEnabledLocMgr
                && isGPSEnabledLocProviders)
            return true;
        else
            return isGPSEnabledLocProviders;
    }


    public interface OnGpsStatus {
        void onSuccess();
    }

}
