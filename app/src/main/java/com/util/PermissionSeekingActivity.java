package com.util;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.constant.FClientConstants;
import com.constant.FEnumerations;

import java.util.ArrayList;
import java.util.List;




/**
 * Created by Anand on 19-09-2016.
 */
public class PermissionSeekingActivity extends AppCompatActivity {

    private int mPermissionRequired = -1;
    private String mUserMessage = null;
    private static final String TAG = "PermissionSeekingActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPermissionRequired = getIntent().getIntExtra(FClientConstants.PERMISSION_INTENT, -1);
        mUserMessage = getIntent().getStringExtra(FClientConstants.PERMISSION_USER_MESSAGE);
        FslLog.d(TAG, "Permission Required is " + mPermissionRequired);
        getUserPermission();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        FslLog.e(TAG, "Backpressed");
        Intent returnIntent = new Intent();
        returnIntent.putExtra(FClientConstants.PERMISSION_INTENT, mPermissionRequired);
        setResult(RESULT_CANCELED, returnIntent);
        finish();
    }

    private boolean getUserPermission() {
        boolean lPermissionAvailable = true;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            final List<String> permissionsList = new ArrayList<String>();

            switch (mPermissionRequired) {
                case FEnumerations.READ_SMS_PERMISSION: {

                    if (ContextCompat.checkSelfPermission(PermissionSeekingActivity.this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
                        permissionsList.add(Manifest.permission.RECEIVE_SMS);
                        lPermissionAvailable = false;
                    } else {
                        FslLog.d(TAG, "Manifest.permission.RECEIVE_SMS already granted");
                    }

                    if (ContextCompat.checkSelfPermission(PermissionSeekingActivity.this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
                        permissionsList.add(Manifest.permission.READ_SMS);
                        lPermissionAvailable = false;
                    } else {
                        FslLog.d(TAG, "Manifest.permission.READ_SMS already granted");
                    }

                    if (!lPermissionAvailable
                            && permissionsList.size() > 0) {
                        //inform user
                        if (mUserMessage == null)
                            mUserMessage = "Need your permission to read OTP SMS";
                        //anand to avoid overlay detected
//                        ToastCompat.makeText(PermissionSeekingActivity.this, mUserMessage, Toast.LENGTH_LONG).show();
                        ActivityCompat.requestPermissions(PermissionSeekingActivity.this,
                                permissionsList.toArray(new String[permissionsList.size()]),
                                FEnumerations.PERMISSION_REQUEST);
                        FslLog.d(TAG, "Read sms permission requested");
                    }
                }
                break;

                case FEnumerations.CAMERA_PERMISSION: {

                    if (ContextCompat.checkSelfPermission(PermissionSeekingActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        permissionsList.add(Manifest.permission.CAMERA);
                        lPermissionAvailable = false;
                    } else {
                        FslLog.d(TAG, "Manifest.permission.CAMERA already granted");
                    }

                    if (!lPermissionAvailable
                            && permissionsList.size() > 0) {
                        //inform user
                        //anand to avoid overlay detected
//                        ToastCompat.makeText(PermissionSeekingActivity.this, "Need your permission to use Camera to take photo", Toast.LENGTH_LONG).show();
                        ActivityCompat.requestPermissions(PermissionSeekingActivity.this,
                                permissionsList.toArray(new String[permissionsList.size()]),
                                FEnumerations.PERMISSION_REQUEST);
                        FslLog.d(TAG, "Manifest.permission.CAMERA requested");
                    }
                }
                break;

                case FEnumerations.SAVE_DOC_PERMISSION: {

                    if (ContextCompat.checkSelfPermission(PermissionSeekingActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        permissionsList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
                        lPermissionAvailable = false;
                    } else {
                        FslLog.d(TAG, "Manifest.permission.READ_EXTERNAL_STORAGE already granted");
                    }
                    if (ContextCompat.checkSelfPermission(PermissionSeekingActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        permissionsList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                        lPermissionAvailable = false;
                    } else {
                        FslLog.d(TAG, "Manifest.permission.WRITE_EXTERNAL_STORAGE already granted");
                    }
                    if (!lPermissionAvailable
                            && permissionsList.size() > 0) {
                        //inform user
                        //anand to avoid overlay detected
//
                        ActivityCompat.requestPermissions(PermissionSeekingActivity.this,
                                permissionsList.toArray(new String[permissionsList.size()]),
                                FEnumerations.PERMISSION_REQUEST);
                        FslLog.d(TAG, "Manifest.permission.READ_EXTERNAL_STORAGE & WRITE_EXTERNAL_STORAGE requested");
                    }
                }
                break;

                case FEnumerations.PHOTO_PERMISSION: {

                    if (ContextCompat.checkSelfPermission(PermissionSeekingActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        permissionsList.add(Manifest.permission.READ_EXTERNAL_STORAGE);
                        lPermissionAvailable = false;
                    } else {
                        FslLog.d(TAG, "Manifest.permission.READ_EXTERNAL_STORAGE already granted");
                    }
                    if (ContextCompat.checkSelfPermission(PermissionSeekingActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        permissionsList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                        lPermissionAvailable = false;
                    } else {
                        FslLog.d(TAG, "Manifest.permission.WRITE_EXTERNAL_STORAGE already granted");
                    }

                    if (ContextCompat.checkSelfPermission(PermissionSeekingActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        permissionsList.add(Manifest.permission.CAMERA);
                        lPermissionAvailable = false;
                    } else {
                        FslLog.d(TAG, "Manifest.permission.CAMERA already granted");
                    }

                    if (!lPermissionAvailable
                            && permissionsList.size() > 0) {
                        //inform user
                        //anand to avoid overlay detected
//                        ToastCompat.makeText(PermissionSeekingActivity.this, "Need your permission to use Camera or pick from Gallery", Toast.LENGTH_LONG).show();
                        ActivityCompat.requestPermissions(PermissionSeekingActivity.this,
                                permissionsList.toArray(new String[permissionsList.size()]),
                                FEnumerations.PERMISSION_REQUEST);
                        FslLog.d(TAG, "Manifest.permission.CAMERA & READ_EXTERNAL_STORAGE requested");
                    }
                }
                break;

                case FEnumerations.CALL_PERMISSION: {

                    if (ContextCompat.checkSelfPermission(PermissionSeekingActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        permissionsList.add(Manifest.permission.CALL_PHONE);
                        lPermissionAvailable = false;
                    } else {
                        FslLog.d(TAG, "Manifest.permission.CALL_PHONE already granted");
                    }

                    if (!lPermissionAvailable
                            && permissionsList.size() > 0) {
                        //inform user
                        //anand to avoid overlay detected

                        ActivityCompat.requestPermissions(PermissionSeekingActivity.this, permissionsList.toArray(new String[permissionsList.size()]),
                                FEnumerations.PERMISSION_REQUEST);
                        FslLog.d(TAG, "Manifest.permission.CALL_PHONE requested");
                    }
                }
                break;
                case FEnumerations.SAVE_CONTACT_PERMISSION: {

                    if (ContextCompat.checkSelfPermission(PermissionSeekingActivity.this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                        permissionsList.add(Manifest.permission.READ_CONTACTS);
                        lPermissionAvailable = false;
                    } else {
                        FslLog.d(TAG, "Manifest.permission.READ_CONTACTS already granted");
                    }

                    if (ContextCompat.checkSelfPermission(PermissionSeekingActivity.this, Manifest.permission.WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                        permissionsList.add(Manifest.permission.WRITE_CONTACTS);
                        lPermissionAvailable = false;
                    } else {
                        FslLog.d(TAG, "Manifest.permission.WRITE_CONTACTS already granted");
                    }

                    if (!lPermissionAvailable
                            && permissionsList.size() > 0) {
                        ActivityCompat.requestPermissions(PermissionSeekingActivity.this,
                                permissionsList.toArray(new String[permissionsList.size()]),
                                FEnumerations.PERMISSION_REQUEST);
                        FslLog.d(TAG, "Manifest.permission.WRITE_CONTACTS & READ_CONTACTS requested");
                    }
                }
                break;
            }
        }

        return lPermissionAvailable;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        // returning the same permission type which was requested by the activity
        //for permission
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Intent returnIntent = new Intent();
        returnIntent.putExtra(FClientConstants.PERMISSION_INTENT, mPermissionRequired);

        FslLog.d(TAG, "onRequestPermissionsResult received");
        if (requestCode == FEnumerations.PERMISSION_REQUEST) {
            switch (mPermissionRequired) {

                case FEnumerations.READ_SMS_PERMISSION: {

                    FslLog.d(TAG, "Read SMS permission result received");
                    int lAnyPermissionGrantedCount = 0;

                    for (int i = 0; i < permissions.length; i++) {

                        if (permissions[i].equals(Manifest.permission.READ_SMS)) {

                            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                                lAnyPermissionGrantedCount++;
                            }
                        }

                        if (permissions[i].equals(Manifest.permission.RECEIVE_SMS)) {

                            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                                lAnyPermissionGrantedCount++;
                            }
                        }
                    }

                    if (lAnyPermissionGrantedCount == 2) {
                        FslLog.d(TAG, "Read SMS permission granted");
                        setResult(RESULT_OK, returnIntent);
                        finish();

                    } else {
                        FslLog.d(TAG, "Read SMS permission NOT granted");
                        setResult(RESULT_CANCELED, returnIntent);
                        finish();
                    }
                }
                break;

                case FEnumerations.CAMERA_PERMISSION: {

                    FslLog.d(TAG, "Manifest.permission.CAMERA result received");
                    int lAnyPermissionGrantedCount = 0;

                    for (int i = 0; i < permissions.length; i++) {

                        if (permissions[i].equals(Manifest.permission.CAMERA)) {

                            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                                lAnyPermissionGrantedCount++;
                            }
                        }
                    }

                    if (lAnyPermissionGrantedCount == 1) {
                        FslLog.d(TAG, "Manifest.permission.CAMERA granted");
                        setResult(RESULT_OK, returnIntent);
                        finish();
                    } else {
                        FslLog.d(TAG, "Manifest.permission.CAMERA not granted");
                        setResult(FEnumerations.RESULT_CAMERA_DENIED, returnIntent);//RESULT_CANCELED);
                        finish();
                    }
                }
                break;

                case FEnumerations.PHOTO_PERMISSION: {

                    FslLog.d(TAG, "Manifest.permission.CAMERA & READ_EXTERNAL_STORAGE result received");
                    boolean isCameraPermissionGranted = true;
                    boolean isReadStoragePermissionGranted = true;

                    for (int i = 0; i < permissions.length; i++) {

                        if (permissions[i].equals(Manifest.permission.CAMERA)) {

                            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                                isCameraPermissionGranted = false;
                            }
                        }

                        if (permissions[i].equals(Manifest.permission.READ_EXTERNAL_STORAGE)) {

                            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                                isReadStoragePermissionGranted = false;
                            }
                        }
                        if (permissions[i].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                                isReadStoragePermissionGranted = false;
                            }
                        }


                    }

                    if (isCameraPermissionGranted && isReadStoragePermissionGranted) {
                        FslLog.d(TAG, "Manifest.permission.CAMERA & READ_EXTERNAL_STORAGE granted");
                        setResult(RESULT_OK, returnIntent);
                        finish();
                    } else if (isCameraPermissionGranted && !isReadStoragePermissionGranted) {
                        FslLog.d(TAG, "Manifest.permission.CAMERA granted but not READ_EXTERNAL_STORAGE");
                        setResult(FEnumerations.RESULT_READ_STORAGE_DENIED, returnIntent);
                        finish();
                    } else if (!isCameraPermissionGranted && isReadStoragePermissionGranted) {
                        FslLog.d(TAG, "Manifest.permission.READ_EXTERNAL_STORAGE granted but not CAMERA");
                        setResult(FEnumerations.RESULT_CAMERA_DENIED, returnIntent);
                        finish();
                    } else {
                        FslLog.d(TAG, "Manifest.permission.CAMERA & READ_EXTERNAL_STORAGE not granted");
                        setResult(RESULT_CANCELED, returnIntent);
                        finish();
                    }
                }
                break;


                case FEnumerations.SAVE_DOC_PERMISSION: {

                    FslLog.d(TAG, "Manifest.permission.READ_EXTERNAL_STORAGE & WRITE_EXTERNAL_STORAGE result received");
                    boolean isWriteStorageGranted = true;
                    boolean isReadStoragePermissionGranted = true;

                    for (int i = 0; i < permissions.length; i++) {

                        if (permissions[i].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                                isWriteStorageGranted = false;
                            }
                        }

                        if (permissions[i].equals(Manifest.permission.READ_EXTERNAL_STORAGE)) {

                            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                                isReadStoragePermissionGranted = false;
                            }
                        }
                    }

                    if (isWriteStorageGranted && isReadStoragePermissionGranted) {
                        FslLog.d(TAG, "Manifest.permission.WRITE_EXTERNAL_STORAGE & READ_EXTERNAL_STORAGE granted");
                        setResult(RESULT_OK, returnIntent);
                        finish();
                    } else if (isWriteStorageGranted && !isReadStoragePermissionGranted) {
                        FslLog.d(TAG, "Manifest.permission.WRITE_EXTERNAL_STORAGE granted but not READ_EXTERNAL_STORAGE");
                        setResult(FEnumerations.RESULT_READ_STORAGE_DENIED, returnIntent);
                        finish();
                    } else if (!isWriteStorageGranted && isReadStoragePermissionGranted) {
                        FslLog.d(TAG, "Manifest.permission.READ_EXTERNAL_STORAGE granted but not WRITE_EXTERNAL_STORAGE");
                        setResult(FEnumerations.RESULT_CAMERA_DENIED, returnIntent);
                        finish();
                    } else {
                        FslLog.d(TAG, "Manifest.permission.WRITE_EXTERNAL_STORAGE & READ_EXTERNAL_STORAGE not granted");
                        setResult(RESULT_CANCELED, returnIntent);
                        finish();
                    }
                }
                break;

                case FEnumerations.CALL_PERMISSION: {

                    FslLog.d(TAG, "Manifest.permission.CALL_PHONE result received");
                    int lAnyPermissionGrantedCount = 0;

                    for (int i = 0; i < permissions.length; i++) {

                        if (permissions[i].equals(Manifest.permission.CALL_PHONE)) {

                            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                                lAnyPermissionGrantedCount++;
                            }
                        }
                    }

                    if (lAnyPermissionGrantedCount == 1) {
                        FslLog.d(TAG, "Manifest.permission.CALL_PHONE granted");
                        setResult(RESULT_OK, returnIntent);
                        finish();
                    } else {
                        FslLog.d(TAG, "Manifest.permission.CALL_PHONE NOT granted");
                        setResult(RESULT_CANCELED, returnIntent);
                        finish();
                    }


                }
                break;
                case FEnumerations.SAVE_CONTACT_PERMISSION: {

                    FslLog.d(TAG, "Manifest.permission.WRITE_CONTACT & READ_CONTACT result received");
                    boolean isWriteContactsPermissionGranted = true;
                    boolean isReadContactsPermissionGranted = true;

                    for (int i = 0; i < permissions.length; i++) {

                        if (permissions[i].equals(Manifest.permission.WRITE_CONTACTS)) {

                            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                                isWriteContactsPermissionGranted = false;
                            }
                        }

                        if (permissions[i].equals(Manifest.permission.READ_CONTACTS)) {

                            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                                isReadContactsPermissionGranted = false;
                            }
                        }
                    }

                    if (isWriteContactsPermissionGranted && isReadContactsPermissionGranted) {
                        FslLog.d(TAG, "Manifest.permission.WRITE_CONTACTS & READ_CONTACTS granted");
                        setResult(RESULT_OK, returnIntent);
                        finish();
                    } else if (isWriteContactsPermissionGranted && !isReadContactsPermissionGranted) {
                        FslLog.d(TAG, "Manifest.permission.WRITE_CONTACTS granted but not READ_CONTACTS");
                        setResult(FEnumerations.RESULT_READ_CONTACTS, returnIntent);
                        finish();
                    } else if (!isWriteContactsPermissionGranted && isReadContactsPermissionGranted) {
                        FslLog.d(TAG, "Manifest.permission.READ_CONTACTS granted but not WRITE_CONTACTS");
                        setResult(FEnumerations.RESULT_WRITE_CONTACTS, returnIntent);
                        finish();
                    } else {
                        FslLog.d(TAG, "Manifest.permission.WRITE & READ_CONTACTS not granted");
                        setResult(RESULT_CANCELED, returnIntent);
                        finish();
                    }
                }
                break;

            }
        }
    }

    public static boolean checkPermission(Activity currentActivity, int permissionCode) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            switch (permissionCode) {
                case FEnumerations.READ_SMS_PERMISSION: {

                    if (ContextCompat.checkSelfPermission(currentActivity, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
                        FslLog.d(TAG, "Manifest.permission.RECEIVE_SMS NOT available right now");
                        return false;
                    }

                    if (ContextCompat.checkSelfPermission(currentActivity, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
                        FslLog.d(TAG, "Manifest.permission.READ_SMS NOT granted");
                        return false;
                    }

                    FslLog.d(TAG, "Read SMS permission granted");
                    return true;
                }

                case FEnumerations.CAMERA_PERMISSION: {

                    if (ContextCompat.checkSelfPermission(currentActivity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        FslLog.d(TAG, "Manifest.permission.CAMERA NOT granted");
                        return false;
                    }

                    FslLog.d(TAG, "Manifest.permission.CAMERA permission granted");
                    return true;
                }

                case FEnumerations.READ_STORAGE_PERMISSION: {

                    if (ContextCompat.checkSelfPermission(currentActivity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        FslLog.d(TAG, "Manifest.permission.READ_EXTERNAL_STORAGE NOT granted");
                        return false;
                    }

                    FslLog.d(TAG, "Manifest.permission.READ_EXTERNAL_STORAGE permission granted");
                    return true;
                }

                case FEnumerations.PHOTO_PERMISSION: {

                    if (ContextCompat.checkSelfPermission(currentActivity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        FslLog.d(TAG, "Manifest.permission.CAMERA NOT granted");
                        return false;
                    }

                    if (ContextCompat.checkSelfPermission(currentActivity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        FslLog.d(TAG, "Manifest.permission.READ_EXTERNAL_STORAGE NOT granted");
                        return false;
                    }
                    if (ContextCompat.checkSelfPermission(currentActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        FslLog.d(TAG, "Manifest.permission.WRITE_EXTERNAL_STORAGE NOT granted");
                        return false;
                    }



                    FslLog.d(TAG, "Manifest.permission.CAMERA & READ_EXTERNAL_STORAGE permission granted");
                    return true;
                }

                case FEnumerations.SAVE_DOC_PERMISSION: {

                    if (ContextCompat.checkSelfPermission(currentActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        FslLog.d(TAG, "Manifest.permission.WRITE_EXTERNAL_STORAGE NOT granted");
                        return false;
                    }

                    if (ContextCompat.checkSelfPermission(currentActivity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        FslLog.d(TAG, "Manifest.permission.READ_EXTERNAL_STORAGE NOT granted");
                        return false;
                    }

                    FslLog.d(TAG, "Manifest.permission.WRITE_EXTERNAL_STORAGE & READ_EXTERNAL_STORAGE permission granted");
                    return true;
                }

                case FEnumerations.CALL_PERMISSION: {

                    if (ContextCompat.checkSelfPermission(currentActivity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        FslLog.d(TAG, "Manifest.permission.CALL_PHONE NOT granted");
                        return false;
                    }

                    FslLog.d(TAG, "Manifest.permission.CALL_PHONE permission granted");
                    return true;
                }
                case FEnumerations.SAVE_CONTACT_PERMISSION: {

                    if (ContextCompat.checkSelfPermission(currentActivity, Manifest.permission.WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                        FslLog.d(TAG, "Manifest.permission.WRITE_CONTACTS NOT granted");
                        return false;
                    }

                    if (ContextCompat.checkSelfPermission(currentActivity, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                        FslLog.d(TAG, "Manifest.permission.READ_CONTACTS NOT granted");
                        return false;
                    }

                    FslLog.d(TAG, "Manifest.permission.WRITE_CONTACTS & READ_CONTACTS permission granted");
                    return true;
                }
            }
        }

        return true;
    }

    public interface PermissionResultListener {
        void onPermissionResult(boolean isGranted);
    }
}
