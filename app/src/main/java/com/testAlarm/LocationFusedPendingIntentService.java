package com.testAlarm;

import android.app.IntentService;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;

import com.util.FslLog;
import com.google.android.gms.location.LocationResult;

import java.util.List;


public class LocationFusedPendingIntentService extends IntentService {

    String TAG = "LocationPendingIntent";

    public LocationFusedPendingIntentService() {
        super("GetUpdatedLocationIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        //Anand - This may be null if the service is being restarted after its process has gone away
        //See google documentation for IntentService.onHandleIntent
        if (intent == null) {
            FslLog.e(TAG, "Location update intent is null. How come ?");
            return;
        }
        Bundle bundle = intent.getExtras();

        if (bundle == null) {
            return;
        }
        Location lastLocation = null;
        if (LocationResult.hasResult(intent)) {
            LocationResult locationResult = LocationResult.extractResult(intent);

            List<Location> locationList = locationResult.getLocations();
            lastLocation = locationResult.getLastLocation();

            if (locationList != null) {
                //Log.d(TAG, "No. of locations " + locationList.size());
                FslLog.d(TAG, "No. of locations " + locationList.size());
            }
        }
//        Location location = bundle.getParcelable("com.google.android.location.LOCATION");
//
//        if (location == null) {
//            return;
//        }
        //Location location = intent.getParcelableExtra(LocationClient.KEY_LOCATION_CHANGED);
        if (lastLocation != null) {
            FslLog.d(TAG, "FusedLocationApi: New location update: " + lastLocation.getLatitude() + ", " + lastLocation.getLongitude());

            //our location based code
        }
    }

    /*@Override
    public void onDestroy() {
        super.onDestroy();
        if (MainMapFragment.mapFragmentInstance != null) {
            MainMapFragment.mapFragmentInstance.stopLocationUpdate();
        }
        stopSelf();
    }*/
}
