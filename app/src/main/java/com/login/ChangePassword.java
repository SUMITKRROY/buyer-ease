package com.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.buyereasefsl.R;
import com.constant.AppConfig;
import com.constant.FClientConfig;
import com.constant.FClientConstants;
import com.constant.FEnumerations;
import com.constant.FStatusCode;
import com.constant.JsonKey;
import com.data.UserSession;
import com.google.android.material.textfield.TextInputLayout;
import com.util.FslLog;
import com.util.GenUtils;
import com.util.NetworkUtil;
import com.util.VolleyErrorHandler;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import me.drakeet.support.toast.ToastCompat;


/**
 * Created by ADMIN on 9/25/2017.
 */

public class ChangePassword extends AppCompatActivity implements JsonKey, View.OnClickListener {

    String TAG = "ChangePassword";
    ImageView profileImage;
    EditText editOldPassword, new_password_detail, editConfirmPassword, editEmailId, editMobile, editCommunity;
    TextInputLayout fConfirmPassword;

    String cOldPassword;
    String cNewPassword;
    String cConfirmPassword;
    String cEmail, cMobile, cCommunity;

    int requestType;
    Button btnCancel, btnSubmit;
    ProgressDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_password);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView companyName = (TextView) findViewById(R.id.companyName);
//        String st = GenUtils.truncate(new UserSession(ChangePassword.this).getCompanyName(), FClientConfig.COMPANY_TRUNC_LENGTH);
//        companyName.setText(st);


        new_password_detail = (EditText) findViewById(R.id.new_password_detail);
        editConfirmPassword = (EditText) findViewById(R.id.editConfirmPassword);
        editEmailId = (EditText) findViewById(R.id.editEmailId);
        fConfirmPassword = (TextInputLayout) findViewById(R.id.fConfirmPassword);
        editMobile = (EditText) findViewById(R.id.editMobile);
        editCommunity = (EditText) findViewById(R.id.editCommunity);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        if (savedInstanceState != null) {
            requestType = savedInstanceState.getInt("request");
        } else {
            requestType = getIntent().getIntExtra("request", 0);
        }


        if (requestType == FEnumerations.REQUEST_FOR_CHANGE_PASSWORD) {
            getSupportActionBar().setTitle("Change Password");
            handleUIChange();

        } else if (requestType == FEnumerations.REQUEST_FOR_FORGOT_PASSWORD) {
            getSupportActionBar().setTitle("Forgot Password");
            fConfirmPassword.setHint("Enter Email");
            handleOnForgotPassword();

        }


    }


    private void handleUIChange() {

        findViewById(R.id.fOldPassword).setVisibility(View.VISIBLE);
        findViewById(R.id.fnew_password_detail).setVisibility(View.VISIBLE);
        findViewById(R.id.fConfirmPassword).setVisibility(View.VISIBLE);
        findViewById(R.id.fEmail).setVisibility(View.GONE);
        findViewById(R.id.fMobile).setVisibility(View.GONE);
        findViewById(R.id.fCommunity).setVisibility(View.GONE);
        findViewById(R.id.orDividerContainer).setVisibility(View.GONE);
    }

    private void handleOnForgotPassword() {

        findViewById(R.id.fOldPassword).setVisibility(View.GONE);
        findViewById(R.id.fnew_password_detail).setVisibility(View.GONE);
        findViewById(R.id.fConfirmPassword).setVisibility(View.GONE);
        findViewById(R.id.fEmail).setVisibility(View.VISIBLE);
        findViewById(R.id.fMobile).setVisibility(View.VISIBLE);
        findViewById(R.id.fCommunity).setVisibility(View.VISIBLE);
        findViewById(R.id.orDividerContainer).setVisibility(View.VISIBLE);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.edit_profile, menu);//Menu Resource, Menu

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;

        } /*else if (item.getItemId() == R.id.submit) {

            handleSubmit();

            return true;

        }*/
        return false;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent result) {
        super.onActivityResult(requestCode, resultCode, result);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        FslLog.d(TAG, " onSaveInstanceState");
        outState.putInt("request", requestType);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnCancel:


                break;
            case R.id.btnSubmit:
                if (requestType == FEnumerations.REQUEST_FOR_CHANGE_PASSWORD) {
                    handleChangePassword();
                } else if (requestType == FEnumerations.REQUEST_FOR_FORGOT_PASSWORD) {
                    handleForgotPassword();
                }
                break;
        }
    }

    private void handleForgotPassword() {
        cEmail = editEmailId.getText().toString();
        cMobile = editMobile.getText().toString();
        cCommunity = editCommunity.getText().toString();

        if (TextUtils.isEmpty(cCommunity)) {
            editCommunity.setError("Enter Community");
            editCommunity.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(cEmail) && TextUtils.isEmpty(cMobile)) {
            Toast toast = ToastCompat.makeText(ChangePassword.this, "Enter Mobile or Email", Toast.LENGTH_SHORT);
            GenUtils.safeToastShow(TAG, ChangePassword.this, toast);
            return;
        }

        if (!NetworkUtil.isNetworkAvailable(ChangePassword.this)) {
            NetworkUtil.showNetworkErrorDialog(ChangePassword.this);
            return;
        }
        String url = AppConfig.URL_FOR_FORGOT_PASSWORD;

//        UserSession userSession = new UserSession(ChangePassword.this);
//        UserData userData = userSession.getLoginData();

        final Map<String, String> params = new HashMap<String, String>();

        if (!TextUtils.isEmpty(cEmail)) {
            params.put("Email", cEmail);
            params.put("Mode", "0");
        }
        if (!TextUtils.isEmpty(cMobile)) {
            params.put("Mobile", cMobile);
            params.put("Mode", "1");
        }

        if (!NetworkUtil.isNetworkAvailable(ChangePassword.this)) {
            Toast toast = ToastCompat.makeText(ChangePassword.this,
                    FClientConstants.TEXT_ERR_INTERNET_CONNECTION_NOT_AVAILABLE, Toast.LENGTH_LONG);
            GenUtils.safeToastShow(TAG, ChangePassword.this, toast);
            return;
        }

        showProgressDialog("Loading");
        LogInHandler.handlerToChangePassword(ChangePassword.this
                , url
                , new JSONObject(params)
                , new LogInHandler.GetLoginResult() {
                    @Override
                    public void onSuccess(JSONObject loginResponse) {
                        hideDialog();
                        if (loginResponse.has("Success")) {
                            String msg = loginResponse.optString("Success");
                            Toast toast = ToastCompat.makeText(ChangePassword.this, msg, Toast.LENGTH_SHORT);
                            GenUtils.safeToastShow(TAG, ChangePassword.this, toast);
                        } else {
                            if (loginResponse.has("Failed")) {
                                String Failed = loginResponse.optString("Failed").trim();
                                if (!Failed.contentEquals(FClientConfig.SERVER_ERROE_RESPONSE_DATA_NOT_FOUND)) {
                                    GenUtils.forInfoAlertDialog(ChangePassword.this,
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
                        }

                        finish();
                    }

                    @Override
                    public void onError(VolleyError volleyError) {
                        hideDialog();
                        Toast toast = ToastCompat.makeText(ChangePassword.this, "Couldn't send email", Toast.LENGTH_SHORT);
                        GenUtils.safeToastShow(TAG, ChangePassword.this, toast);
                        boolean lIsErrorHandled = VolleyErrorHandler.errorHandler(ChangePassword.this, volleyError);
                        if (!lIsErrorHandled) {
                            if (volleyError == null
                                    || volleyError.networkResponse == null) {
                                FslLog.d(TAG, " Could Not CAUGHT EXCEPTION ...................... bcos of volleyError AND networkResponse IS NULL..");
                            } else if (volleyError.networkResponse.statusCode == FStatusCode.HTTP_STATUS_CODE_INTERNAL_SERVER_ERROR) {
                                VolleyErrorHandler.handleInternalError(ChangePassword.this, volleyError);
                                return;
                            }

                        }

                        finish();
                    }
                });


    }

    private void handleChangePassword() {

        cOldPassword = editOldPassword.getText().toString();
        cNewPassword = new_password_detail.getText().toString();
        cConfirmPassword = editConfirmPassword.getText().toString();
        if (TextUtils.isEmpty(cOldPassword)) {
            editOldPassword.setError("Enter Old Password");
            editOldPassword.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(cNewPassword)) {
            new_password_detail.setError("Enter New Password");
            new_password_detail.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(cConfirmPassword)) {
            editConfirmPassword.setError("Enter Invalid Confirm Password");
            editConfirmPassword.requestFocus();
            return;
        }
        final UserSession userSession = new UserSession(ChangePassword.this);
        if (!userSession.getPassword().equals(cOldPassword)) {
            editOldPassword.setError("Invalid Old Password");
            editOldPassword.requestFocus();
            return;
        }
        if (!NetworkUtil.isNetworkAvailable(ChangePassword.this)) {
            NetworkUtil.showNetworkErrorDialog(ChangePassword.this);
            return;
        }


        String url = AppConfig.URL_FOR_CHANGE_PASSWORD;


        UserData userData = userSession.getLoginData();

        final Map<String, String> params = new HashMap<String, String>();

        params.put(KEY_USER_ID, userData.userId);
//        params.put(KEY_ASSO_ID, userData.KEY_ASSO_ID);
//        params.put(KEY_ASSO_TYPE, userData.KEY_ASSO_TYPE);
        params.put("NewPassword", cNewPassword);
        params.put("OldPassword", userData.password);

        if (!NetworkUtil.isNetworkAvailable(ChangePassword.this)) {
            ToastCompat.makeText(ChangePassword.this,
                    FClientConstants.TEXT_ERR_INTERNET_CONNECTION_NOT_AVAILABLE, Toast.LENGTH_LONG).show();
            return;
        }

        showProgressDialog("Loading");
        LogInHandler.handlerToChangePassword(ChangePassword.this
                , url
                , new JSONObject(params)
                , new LogInHandler.GetLoginResult() {
                    @Override
                    public void onSuccess(JSONObject loginResponse) {
                        hideDialog();
                        if (loginResponse.has("Success")) {
                            String msg = loginResponse.optString("Success");
                            userSession.putPassword(cNewPassword);
                            ToastCompat.makeText(ChangePassword.this, msg, Toast.LENGTH_SHORT).show();
                        } else {
                            if (loginResponse.has("Failed")) {
                                String Failed = loginResponse.optString("Failed").trim();
                                if (!Failed.contentEquals(FClientConfig.SERVER_ERROE_RESPONSE_DATA_NOT_FOUND)) {
                                    GenUtils.forInfoAlertDialog(ChangePassword.this,
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
                        }
                        finish();
                    }

                    @Override
                    public void onError(VolleyError volleyError) {
                        hideDialog();
                        ToastCompat.makeText(ChangePassword.this, "Your password couldn.t change", Toast.LENGTH_SHORT).show();
                        boolean lIsErrorHandled = VolleyErrorHandler.errorHandler(ChangePassword.this, volleyError);
                        if (!lIsErrorHandled) {
                            if (volleyError == null
                                    || volleyError.networkResponse == null) {
                                FslLog.d(TAG, " Could Not CAUGHT EXCEPTION ...................... bcos of volleyError AND networkResponse IS NULL..");
                            } else if (volleyError.networkResponse.statusCode == FStatusCode.HTTP_STATUS_CODE_INTERNAL_SERVER_ERROR) {
                                VolleyErrorHandler.handleInternalError(ChangePassword.this, volleyError);
                                return;
                            }

                        }
                        finish();
                    }
                });
    }

    private void showProgressDialog(String message) {

        if (loadingDialog == null)
            loadingDialog = new ProgressDialog(ChangePassword.this);
//        loadingDialog.setTitle("Please wait");
        loadingDialog.setMessage(message);
        if (loadingDialog != null && !loadingDialog.isShowing())
            loadingDialog.show();

    }

    private void hideDialog() {
        if (loadingDialog != null && loadingDialog.isIndeterminate() && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
            loadingDialog = null;
        }

    }
}

