package com.login;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.SQLException;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.buyereasefsl.R;
import com.constant.FClientConfig;
import com.constant.FClientConstants;
import com.constant.FEnumerations;
import com.constant.FStatusCode;
import com.constant.JsonKey;
import com.dashboard.DashboardActivity;
import com.dashboard.SyncDataHandler;
import com.data.DeviceSession;
import com.data.UserSession;
import com.database.DBHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sync.GetDataHandler;
import com.testJsoParse.Employee;
import com.util.FslLog;
import com.util.GenUtils;
import com.util.NetworkUtil;
import com.util.VolleyErrorHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.drakeet.support.toast.ToastCompat;

//import com.android.volley.VolleyError;
//import com.google.firebase.analytics.FirebaseAnalytics;
//import com.google.firebase.iid.FirebaseInstanceId;


public class LogInActivity extends AppCompatActivity implements JsonKey {
    private EditText editTextUserName;
    private EditText editTextPassword;
    private EditText communitycode11;

    private static final int MY_CAMERA_REQUEST_CODE = 100;
    private static final int REQUEST_WRITE_PERMISSION = 786;
    private static final int MULTIPLE_PERMISSION = 785;

    Button login;


    private static LogInActivity mInstance;
    ProgressDialog loadingDialog;
    String Community, UserID, DataFor, FilterString;
    String TAG = "LogInActivity";
    //    private FirebaseAnalytics mFirebaseAnalytics;
    String companyName = null;
    String abbrv = null;
    TextView txtForgot, txtNewUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        // Obtain the FirebaseAnalytics instance.
//        Crashlytics.logException(new Exception("Test"));

        setContentView(R.layout.content_society);


//        requestCameraPermission();
//        fileReadWrite();

//        deviceid = AppConfig.getUniqueDeviceID(this);
        editTextUserName = (EditText) findViewById(R.id.userName);
        editTextPassword = (EditText) findViewById(R.id.password);
        communitycode11 = (EditText) findViewById(R.id.community);
        txtForgot = (TextView) findViewById(R.id.txtForgot);
        txtNewUser = (TextView) findViewById(R.id.txtNewUser);
//        ClearNotify.clearNotify(LogInActivity.this);


        txtForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LogInActivity.this, ChangePassword.class);
                intent.putExtra("request", FEnumerations.REQUEST_FOR_FORGOT_PASSWORD);
                startActivity(intent);
            }
        });

        if(checkAndRequestPermissions()){
            File appDir = getApplicationContext().getExternalFilesDir(null);

            if (appDir != null) {
                FClientConfig.mExternalStorageDir = appDir.getAbsolutePath();
            } else {
                ToastCompat.makeText(LogInActivity.this, "Not able to access application directory. " +
                        "You may need to restart your phone. " +
                        "If problem persists write to support", Toast.LENGTH_LONG).show();
                ToastCompat.makeText(LogInActivity.this, "Not able to access application directory. " +
                        "You may need to restart your phone. " +
                        "If problem persists write to support", Toast.LENGTH_LONG).show();

                finishAffinity();
                return;
            }
            DeviceSession deviceSession = new DeviceSession(this);
            deviceSession.saveStaticVariable(KEY_mExternalStorageDir, FClientConfig.mExternalStorageDir);
            createDBHandle();
            handleLogin();
        }



        //Anand test for soap request
//        HandleToConnectionEithServer.setRequestUsingSoap();
        //SendDataHandler.getUserMasterJson(LogInActivity.this);
//Anand test Gson parsing
//        testParse();

        //delete data
//        handleToDelete();
    }

    /*private void requestCameraPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
        }

    }

    private void fileReadWrite() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);
            }
        }
    }*/

    private void handleToDelete() {
        //clear data from all table in db
        List<String> tableList = SyncDataHandler.getTablesToDelete(this);
        for (int i = 0; i < tableList.size(); i++) {
            SyncDataHandler.deleteRecordFromAllTAble(LogInActivity.this, tableList.get(i));
        }
    }

    private void createDBHandle() {

//        DBHelper mydb = new DBHelper(LogInActivity.this);
//        SQLiteDatabase db = mydb.getWritableDatabase();

        DBHelper myDbHelper = new DBHelper(this);

        try {

            myDbHelper.createDataBase();

        } catch (IOException ioe) {

            throw new Error("Unable to create database");

        }

        try {

            myDbHelper.openDataBase();

        } catch (SQLException sqle) {

            throw sqle;

        }
    }

    private void testParse() {

        // Get Gson object
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonString = "{\"empID\":100,\"name\":\"David\",\"permanent\":false,\"address\":{\"street\":\"BTM 1st Stage\",\"city\":\"Bangalore\",\"zipcode\":560100},\"phoneNumbers\":[123456,987654],\"role\":\"Manager\",\"cities\":[\"Los Angeles\",\"New York\"],\"properties\":{\"age\":\"28 years\",\"salary\":\"1000 Rs\"}}";
        // parse json string to object
        Employee emp1 = gson.fromJson(jsonString, Employee.class);

        //  parse json string to array
        //  List<Post> posts = Arrays.asList(gson.fromJson(response, Post[].class));
        //  Log.i("PostActivity", posts.size() + " posts loaded.");
        //  for (Post post : posts) {
        //   Log.i("PostActivity", post.ID + ": " + post.title);
        // }

        // print object data
        FslLog.d(TAG, "Employee Object\n\n" + emp1);
    }
      /*  txtNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentNewUser = new Intent(LogInActivity.this, LoginActivity.class);
                intentNewUser.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
                        | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentNewUser);
            }
        });*/


       /* findViewById(R.id.txtExistUser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                ((TextView) findViewById(R.id.txtNewUser)).setBackground(getResources().getDrawable(R.drawable.remove_border));
                ((TextView) findViewById(R.id.txtNewUser)).setTextColor(GenUtils.getColorResource(LogInActivity.this, R.color.colorTeal));
                findViewById(R.id.txtNewUser).setPadding(GenUtils.convertDpToPixel(8, LogInActivity.this)
                        , GenUtils.convertDpToPixel(8, LogInActivity.this), GenUtils.convertDpToPixel(8, LogInActivity.this)
                        , GenUtils.convertDpToPixel(8, LogInActivity.this));
                ((TextView) findViewById(R.id.txtExistUser)).setBackground(getResources().getDrawable(R.drawable.btn_background_press));
                ((TextView) findViewById(R.id.txtExistUser)).setTextColor(GenUtils.getColorResource(LogInActivity.this, R.color.white));
                findViewById(R.id.txtExistUser).setPadding(GenUtils.convertDpToPixel(8, LogInActivity.this)
                        , GenUtils.convertDpToPixel(8, LogInActivity.this), GenUtils.convertDpToPixel(8, LogInActivity.this)
                        , GenUtils.convertDpToPixel(8, LogInActivity.this));
//                mainFilpperView.setDisplayedChild(0);

//                Intent intentNewUser = new Intent(LogInActivity.this, LogInActivity.class);
//                intentNewUser.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
//                        | Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intentNewUser);
            }
        });*/
      /*  findViewById(R.id.txtNewUser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                findViewById(R.id.txtNewUser).setBackground(getResources().getDrawable(R.drawable.btn_background_press));
                ((TextView) findViewById(R.id.txtNewUser)).setTextColor(GenUtils.getColorResource(LogInActivity.this, R.color.white));

                findViewById(R.id.txtExistUser).setBackground(getResources().getDrawable(R.drawable.remove_border));
                ((TextView) findViewById(R.id.txtExistUser)).setTextColor(GenUtils.getColorResource(LogInActivity.this, R.color.colorTeal));
                findViewById(R.id.txtExistUser).setPadding(GenUtils.convertDpToPixel(8, LogInActivity.this)
                        , GenUtils.convertDpToPixel(8, LogInActivity.this), GenUtils.convertDpToPixel(8, LogInActivity.this)
                        , GenUtils.convertDpToPixel(8, LogInActivity.this));
                findViewById(R.id.txtNewUser).setPadding(GenUtils.convertDpToPixel(8, LogInActivity.this)
                        , GenUtils.convertDpToPixel(8, LogInActivity.this), GenUtils.convertDpToPixel(8, LogInActivity.this)
                        , GenUtils.convertDpToPixel(8, LogInActivity.this));


                Intent intentNewUser = new Intent(LogInActivity.this, LoginActivity.class);
                intentNewUser.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
                        | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentNewUser);
            }
        });

    }
*/
   /* private void handleToDeleteTable() {
        deleteDatabase(DBHelper.DATABASE_NAME);
        SocietyLog.d(TAG, " query to deleting data base at time of login ");
    }*/

    private void handleLogin() {
        login = (Button) findViewById(R.id.login);
        login.setText("LOGIN");
        final UserSession userSession = new UserSession(LogInActivity.this);
        UserMaster userMaster = LogInHandler.getUserMaster(LogInActivity.this);
        if (userMaster != null && !TextUtils.isEmpty(userMaster.LoginName)
                && !TextUtils.isEmpty(userMaster.Password) && userSession.IsLogOut() == 1) {
            shartWithoutGard();
        } else {
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String usernameNo = editTextUserName.getText().toString();
                    String password = editTextPassword.getText().toString();
                    if (usernameNo.length() == 0) {
                        editTextUserName.setError("User Name");
                        editTextUserName.requestFocus();
                        ToastCompat.makeText(getApplicationContext(), "User Name", Toast.LENGTH_LONG).show();
                    } else if (password.length() == 0) {
                        editTextPassword.setError("Password");
                        editTextPassword.requestFocus();
                        ToastCompat.makeText(getApplicationContext(), "Password", Toast.LENGTH_LONG).show();
                    } else {
                        UserMaster userMaster = LogInHandler.getUserMaster(LogInActivity.this);
                        if (userMaster != null && userMaster.LoginName.equals(usernameNo) && userMaster.Password.equals(password)) {
                            shartWithoutGard();

                        } else {

                            if (!NetworkUtil.isNetworkAvailable(LogInActivity.this)) {
                                ToastCompat.makeText(LogInActivity.this,
                                        FClientConstants.TEXT_ERR_INTERNET_CONNECTION_NOT_AVAILABLE, Toast.LENGTH_LONG).show();
                                return;
                            }

                            if (NetworkUtil.isNetworkAvailable(LogInActivity.this)) {
                                showProgressDialog("Loading...");
                                handlelogin(usernameNo, password);


                            } else {
                                ToastCompat.makeText(getApplicationContext(), "Please check internet connection", Toast.LENGTH_LONG).show();
                            }
                        }

                    }
                }
            });
//        }
        }
    }


    private void handlelogin(final String username,
                             final String password) {

        showProgressDialog("Loading...");
        LogInHandler.getLogInMaster(LogInActivity.this,
                username,
                password,
                new LogInHandler.GetLoginResult() {
                    @Override
                    public void onSuccess(JSONObject loginResponse) {
                        try {
                            if (loginResponse != null && loginResponse.has("Failed")) {
                                hideDialog();
                                String Failed = loginResponse.optString("Failed").trim();
                                if (!Failed.contentEquals(FClientConfig.SERVER_ERROE_RESPONSE_DATA_NOT_FOUND)) {
                                    GenUtils.forInfoAlertDialog(LogInActivity.this,
                                            FClientConstants.ACTION_OK,
                                            "Temporary Error",
                                            Failed, new GenUtils.AlertDialogClickListener() {
                                                @Override
                                                public void onPositiveButton() {

                                                }

                                                @Override
                                                public void onNegativeButton() {

                                                }
                                            });
                                }
                                return;
                            }
                            if (!loginResponse.optBoolean("Result")) {

                                hideDialog();
                                String msg = loginResponse.optString("Message");
                                GenUtils.forInfoAlertDialog(LogInActivity.this,
                                        FClientConstants.ACTION_OK,
                                        "",
                                        msg, new GenUtils.AlertDialogClickListener() {
                                            @Override
                                            public void onPositiveButton() {

                                            }

                                            @Override
                                            public void onNegativeButton() {

                                            }
                                        });
                            } else {
                                JSONArray logJsonArray = loginResponse.optJSONArray("Data");
                                int status = 0;
                                if (logJsonArray != null) {
                                    FslLog.d(TAG, " login response  " + logJsonArray.length());
                                    for (int i = 0; i < logJsonArray.length(); i++) {
                                        JSONObject jsonObject = logJsonArray.getJSONObject(i);
                                        if (jsonObject != null) {
                                            UserMaster userMaster = LogInHandler.getUserMaster(LogInActivity.this);
                                            if (userMaster != null && userMaster.LoginName.equals(username) && userMaster.Password.equals(password)) {

                                            } else {
                                                handleToDelete();
                                            }
                                            status = GetDataHandler.updateOrInsertUserMaster(LogInActivity.this, jsonObject);
                                        }
                                    }
                                }
                                hideDialog();
                                if (status != 0) {
                                    shartWithoutGard();
                                } else {
                                    ToastCompat.makeText(LogInActivity.this, "Failed to login", Toast.LENGTH_SHORT).show();
                                }
                            }
                        } catch (JSONException e) {
                            FslLog.e(TAG, " JSONException at time of login");
                            hideDialog();
//                            errorHandleToJsonParse();
                            e.printStackTrace();
                            return;
                        }

                    }

                    @Override
                    public void onError(VolleyError volleyError) {
                        hideDialog();
                        boolean lIsErrorHandled = VolleyErrorHandler.errorHandler(LogInActivity.this, volleyError);
                        if (!lIsErrorHandled) {
                            if (volleyError == null
                                    || volleyError.networkResponse == null) {
                                FslLog.d(TAG, " Could Not CAUGHT EXCEPTION ...................... bcos of volleyError AND networkResponse IS NULL..");
                            } else if (volleyError.networkResponse.statusCode == FStatusCode.HTTP_STATUS_CODE_INTERNAL_SERVER_ERROR) {
                                VolleyErrorHandler.handleInternalError(LogInActivity.this, volleyError);
                                return;
                            }

                        }
                    }
                });

    }

    private void shartWithoutGard() {
        UserSession userSession = new UserSession(LogInActivity.this);
        userSession.OnLogIn();
        startActivity(new Intent(LogInActivity.this, DashboardActivity.class));
        finish();
    }


    private void showProgressDialog(String message) {

        if (loadingDialog == null)
            loadingDialog = new ProgressDialog(LogInActivity.this);
//        loadingDialog.setTitle("Please wait");
        loadingDialog.setMessage(message);
        if (loadingDialog != null && !loadingDialog.isShowing()) {
//            loadingDialog.show();
            GenUtils.safeProgressDialogShow(TAG, LogInActivity.this, loadingDialog);
        }

    }

    private void hideDialog() {
        if (loadingDialog != null && loadingDialog.isIndeterminate() && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }

    }

    /*@Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }else if (requestCode == REQUEST_WRITE_PERMISSION ) {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "file write permission granted", Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(this, "file write permission denied", Toast.LENGTH_LONG).show();
            }

        }
    }*/
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, "Permission callback called-------");
        switch (requestCode) {
            case MULTIPLE_PERMISSION: {

                Map<String, Integer> perms = new HashMap<>();
                // Initialize the map with both permissions
                perms.put(Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.ACCESS_FINE_LOCATION, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED);
                // Fill with actual results from user
                if (grantResults.length > 0) {
                    for (int i = 0; i < permissions.length; i++)
                        perms.put(permissions[i], grantResults[i]);
                    // Check for both permissions
                    if (perms.get(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                            && perms.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        Log.d(TAG, "sms & location services permission granted");
                        // process the normal flow
                        //else any one or both the permissions are not granted
//                        Toast.makeText(this, "permission granted", Toast.LENGTH_LONG).show();

                        File appDir = getApplicationContext().getExternalFilesDir(null);

                        if (appDir != null) {
                            FClientConfig.mExternalStorageDir = appDir.getAbsolutePath();
                        } else {
                            ToastCompat.makeText(LogInActivity.this, "Not able to access application directory. " +
                                    "You may need to restart your phone. " +
                                    "If problem persists write to support", Toast.LENGTH_LONG).show();
                            ToastCompat.makeText(LogInActivity.this, "Not able to access application directory. " +
                                    "You may need to restart your phone. " +
                                    "If problem persists write to support", Toast.LENGTH_LONG).show();

                            finishAffinity();
                            return;
                        }
                        DeviceSession deviceSession = new DeviceSession(this);
                        deviceSession.saveStaticVariable(KEY_mExternalStorageDir, FClientConfig.mExternalStorageDir);
                        createDBHandle();
                        handleLogin();

                    } else {
                        Log.d(TAG, "Some permissions are not granted ask again ");
                        //permission is denied (this is the first time, when "never ask again" is not checked) so ask again explaining the usage of permission
//                        // shouldShowRequestPermissionRationale will return true
                        //show the dialog or snackbar saying its necessary and try again otherwise proceed with setup.
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                                Manifest.permission.CAMERA) ||
                                ActivityCompat.shouldShowRequestPermissionRationale(this,
                                        Manifest.permission.ACCESS_FINE_LOCATION) ||
                                ActivityCompat.shouldShowRequestPermissionRationale(this,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
                                ActivityCompat.shouldShowRequestPermissionRationale(this,
                                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                            showDialogOK("Read-Write and Camera Permission required for this app",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which) {
                                                case DialogInterface.BUTTON_POSITIVE:
                                                    checkAndRequestPermissions();
                                                    break;
                                                case DialogInterface.BUTTON_NEGATIVE:
                                                    // proceed with logic by disabling the related features or quit the app.
                                                    break;
                                            }
                                        }
                                    });
                        }
                        //permission is denied (and never ask again is  checked)
                        //shouldShowRequestPermissionRationale will return false
                        else {
                            Toast.makeText(this, "Go to settings and enable permissions", Toast.LENGTH_LONG).show();
                            //                            //proceed with logic by disabling the related features or quit the app.
                        }
                    }
                }
            }
        }

    }

    private void showDialogOK(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", okListener)
                .create()
                .show();
    }

    private boolean checkAndRequestPermissions() {
        int permissionSendMessage = ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS);
        int locationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (locationPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (permissionSendMessage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (permissionSendMessage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (permissionSendMessage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),
                    MULTIPLE_PERMISSION);
            return false;
        }
        return true;
    }
}
