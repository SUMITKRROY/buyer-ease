package com.testAlarm;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.buyereasefsl.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.util.FslLog;
import com.util.GenUtils;
import com.util.Initialization;

import me.drakeet.support.toast.ToastCompat;

/**
 * Created by ADMIN on 11/22/2017.
 */

public class TestClass extends AppCompatActivity {
    String TAG = "TestClass";
    private boolean isPermAlreadyRequested = false;
    private LocationServiceHandle locationServiceHandle;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scheduleAlarm();
//        if (locationServiceHandle == null)
//            locationServiceHandle = new LocationServiceHandle(this);

        checkPlayServicesInstalled();
        handleLocation();
    }

    // Setup a recurring alarm every half hour
    public void scheduleAlarm() {
        // Construct an intent that will execute the AlarmReceiver
        Intent intent = new Intent(getApplicationContext(), MyAlarmReceiver.class);
        // Create a PendingIntent to be triggered when the alarm goes off
        final PendingIntent pIntent = PendingIntent.getBroadcast(this, MyAlarmReceiver.REQUEST_CODE,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        // Setup periodic alarm every every half hour from this point onwards
        long firstMillis = System.currentTimeMillis(); // alarm is set right away
        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        // First parameter is the type: ELAPSED_REALTIME, ELAPSED_REALTIME_WAKEUP, RTC_WAKEUP
        // Interval can be INTERVAL_FIFTEEN_MINUTES, INTERVAL_HALF_HOUR, INTERVAL_HOUR, INTERVAL_DAY
//        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, firstMillis,
//                AlarmManager.INTERVAL_HALF_HOUR, pIntent);

//        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, firstMillis, 1 * 60 * 1000,
//                pIntent);// AlarmManager.INTERVAL_HALF_HOUR,

        alarm.setRepeating(AlarmManager.RTC_WAKEUP, firstMillis, 1 * 60 * 1000,
                pIntent);// AlarmManager.INTERVAL_HALF_HOUR,

//        alarm.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
//                AlarmManager.INTERVAL_FIFTEEN_MINUTES, AlarmManager.INTERVAL_FIFTEEN_MINUTES, pIntent);

    }

    public void cancelAlarm() {
        Intent intent = new Intent(getApplicationContext(), MyAlarmReceiver.class);
        final PendingIntent pIntent = PendingIntent.getBroadcast(this, MyAlarmReceiver.REQUEST_CODE,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarm.cancel(pIntent);
    }

    private void handleLocation() {

        if (ActivityCompat.checkSelfPermission(TestClass.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (!isPermAlreadyRequested) {
                ActivityCompat.requestPermissions(TestClass.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        Initialization.MY_PERMISSION_ACCESS_FINE_LOCATION);
                isPermAlreadyRequested = true;
            }
        } else {
            handleGps();

        }


    }

    private void handleGps() {

        if (GPSDialog.isGpsEnable(TestClass.this)) {
//            ToastCompat.makeText(AddPreVisitor.this, "gps enabled", Toast.LENGTH_SHORT).show();
//            handleAfterPermissionGranded();

        } else {
//            if (AppController.getLocationServiceHandle().isConnected())
            if (locationServiceHandle != null && locationServiceHandle.isConnected()) {
                {
                    GoogleApiClient mGoogleApiClient = locationServiceHandle.getGoogleApiClient();

                    LocationRequest mLocationRequest = locationServiceHandle.createLocationUpdate();

                    GPSDialog.checkEnableAndShowDialog(TestClass.this
                            , mLocationRequest
                            , mGoogleApiClient
                            , new GPSDialog.OnGpsStatus() {
                                @Override
                                public void onSuccess() {
//                                ToastCompat.makeText(AddPreVisitor.this, "Gps enabled", Toast.LENGTH_SHORT).show();
                                }
                            });

                }
            }
        }
    }

    private void handleAfterPermissionGranded() {

//        locationServiceHandle.startLocationUpdateUsingFusedApi();


       /* if (locationServiceHandle != null) {
            locationServiceHandle.setConnectionListener(new LocationServiceHandle.ConnectionListener() {
                @Override
                public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                }

                @Override
                public void onConnectionSuspended(int i) {

                }


                @Override
                public void onConnected(Bundle bundle, GoogleApiClient googleApiClient) {
                    //this function will call whenever google api connected or already connected when setting listener
                    //You are connected do what ever you want
                    //Like i get last known location
                    if (ActivityCompat.checkSelfPermission(TestClass.this,
                            Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                            && ActivityCompat.checkSelfPermission(TestClass.this,
                            Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    locationServiceHandle.startLocationUpdateUsingFusedApi();
                    Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
                    if (location != null) {
                        FslLog.d(TAG, "location ...................." + location.getLatitude() + ", " + location.getLongitude());
                    } else {
                        FslLog.d(TAG, "location .................... null");
                    }
                }
            });
        }*/
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        FslLog.d(TAG, "onRequestPermissionsResult " + "requestCode : " + requestCode + ", permissions: " + permissions + ", grantResults: " + grantResults);
//        if (requestCode == Initialization.MY_PERMISSION_ACCESS_COARSE_LOCATION) {
        String lPermissionMsg = "Following Permissions needed to use the  App:\nLocation - for ride matching and tracking\n";

        if (requestCode == Initialization.MY_PERMISSION_ACCESS_FINE_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                FslLog.d(TAG, " permissions ACCESS_FINE_LOCATION granted");

                //Anand handle here after granted permission
//                startLocationUpdateAfterPermissionGranted();

                handleGps();

                isPermAlreadyRequested = false;
            } else {
                FslLog.d(TAG, " permissions ACCESS_FINE_LOCATION denied");
                showPermissionDeniedDialog(TestClass.this, "Permission Denied"
                        , lPermissionMsg);
            }
        }

    }

    public static void showPermissionDeniedDialog(final Context context
            , String title
            , String pMessage) {
        String mtitle = "Permission Denied";
        if (title != null) {
            mtitle = title;
        }

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        alertDialog.setTitle(mtitle);
        alertDialog.setMessage(pMessage);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Exit", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                ((Activity) context).finishAffinity();
            }
        });
        alertDialog.show();
    }


    public boolean checkPlayServicesInstalled() {

        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int resultCode = googleAPI.isGooglePlayServicesAvailable(TestClass.this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (googleAPI.isUserResolvableError(resultCode)) {
                googleAPI.getErrorDialog(TestClass.this, resultCode,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                //if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                FslLog.e(TAG, "checkPlayServicesInstalled:" + " play services not available");
/*                } else {
                    Log.e(TAG, "checkPlayServicesInstalled:" + " play services not available");
                }*/

                Toast toast = ToastCompat.makeText(TestClass.this,
                        "Google Play Service is not installed",
                        Toast.LENGTH_SHORT);
                GenUtils.safeToastShow(TAG, TestClass.this, toast);
                finishAffinity();
            }

            return false;
        }

        //if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
        FslLog.d(TAG, "checkPlayServicesInstalled:" + " Google Play Services available");
/*        } else {
            Log.d(TAG, "checkPlayServicesInstalled:" + " Google Play Services available");
        }*/

        return true;

    }
}