package com.buyereasefsl;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.constant.JsonKey;
import com.util.FslLog;
import com.util.SetInitiateStaticVariable;


public class SupportFeature extends AppCompatActivity implements JsonKey {
    private WebView webview;
    private static String TAG = null;
    private ProgressDialog progressDialog;
    private boolean isViewed = false;
    String title;
    String url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help_webview);
        Toolbar actionBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(actionBar);
        if (savedInstanceState != null) {
            SetInitiateStaticVariable.setInitiateStaticVariable(SupportFeature.this);
            url = savedInstanceState.getString("url");
            isViewed = savedInstanceState.getBoolean("isViewed");
            title = savedInstanceState.getString("title");
        } else {
            title = getIntent().getStringExtra("title");
            url = getIntent().getStringExtra("url");
            Uri webpage = Uri.parse(url);

            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                webpage = Uri.parse("http://" + url);
            }
            url = String.valueOf(webpage);
        }
        TAG = title;

        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        webview = (WebView) findViewById(R.id.helpWebView);

        WebSettings settings = webview.getSettings();
        settings.setJavaScriptEnabled(true);
//        settings.setUserAgentString(GetUserAgent.getUserAgentString(SupportFeature.this));
        webview.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
//
//
//        settings.setUseWideViewPort(true);
//        settings.setLoadWithOverviewMode(true);

//        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        showProgressDialog();

        webview.setWebViewClient(new WebViewClient() {
            @TargetApi(Build.VERSION_CODES.N)
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                FslLog.i(TAG, "Processing webview url click..." + request.getUrl());
                FslLog.i(TAG, "Title on loading " + view.getTitle());
                isViewed = true;
                url = request.getUrl().toString();
                redirectUrl(view, url);
                return true;
            }


            public boolean shouldOverrideUrlLoading(WebView view, String request) {
                FslLog.i(TAG, "ShouldOverrideUrlLoading Processing webview url click..." + request);
                FslLog.i(TAG, "Title on Load " + view.getTitle());
//                view.loadUrl(request);
                url = request;
                isViewed = true;
                redirectUrl(view, url);

                return true;
            }

            public void onPageFinished(WebView view, String url) {
                FslLog.i(TAG, "Finished loading URL: " + url);
                dismissProgressDialog();
//                SocietyLog.i(TAG, "Title on finish " + view.getTitle());
//                if (isViewed)
//                    if (view.getTitle() != null) {
//                        title = view.getTitle();
//                        getSupportActionBar().setTitle(title);
//                    }
            }

            /*
                 * Added in API level 23 replacing :-
                 *
                 * onReceivedError(WebView view, int errorCode, String description, String failingUrl)
                */


            public void onReceivedError(WebView view, WebResourceRequest request,
                                        WebResourceError error) {
                if (error != null) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        FslLog.e(TAG, "onReceivedError: WebView Error code " + error.getErrorCode());
                    }

                }

                super.onReceivedError(view, request, error);
            }

            /*
              Added in API level 23
            */


            public void onReceivedHttpError(WebView view,
                                            WebResourceRequest request, WebResourceResponse errorResponse) {
                if (errorResponse != null) {


                    FslLog.e(TAG, "onReceivedHttpError: WebView Error status code " + errorResponse.getStatusCode());
                }
                super.onReceivedHttpError(view, request, errorResponse);
            }

            public void onReceivedError(WebView view, int errorCode, String description, String
                    failingUrl) {


                FslLog.e(TAG, "WebView Error code " + errorCode + ", description " + description);
            }

        });
        FslLog.d(TAG, url);
        if (url != null)
            webview.loadUrl(url);

    }


    private void redirectUrl(WebView view, String url) {

        view.loadUrl(url);


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("url", url);
        outState.putBoolean("isViewed", isViewed);
        outState.putString("title", title);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;

        }
        return false;
    }

    private void showProgressDialog() {
        if (!SupportFeature.this.isFinishing())
            if (progressDialog == null) {
                progressDialog = new ProgressDialog(SupportFeature.this) {
                    @Override
                    public void onBackPressed() {
//                    webview.setWebViewClient(null);
                        dismissProgressDialog();
                        finish();
                    }
                };
                progressDialog.setMessage("loading...");
                progressDialog.setIndeterminate(false);
                progressDialog.setCancelable(false);
            }
        if (progressDialog != null && !progressDialog.isShowing())
            progressDialog.show();
    }

    private void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            dismissProgressDialog();
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
