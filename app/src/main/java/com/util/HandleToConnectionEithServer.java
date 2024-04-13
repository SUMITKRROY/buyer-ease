package com.util;

//import com.android.volley.DefaultRetryPolicy;
//import com.android.volley.Request;
//import com.android.volley.Response;
//import com.android.volley.RetryPolicy;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.JsonObjectRequest;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.constant.FClientConfig;

import org.json.JSONObject;


/**
 * Created by ADMIN on 9/27/2017.
 */

public class HandleToConnectionEithServer {
    static String TAG = "HandleToConnectionEithServer";

    public void setRequest(String mUrl, JSONObject mJsonObject, final CallBackResult mOnResultCallBack) {
        FslLog.d(TAG, " set Request URL : " + mUrl);
        FslLog.d(TAG, " set Request json PARAM  : " + mJsonObject);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                mUrl, mJsonObject,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        FslLog.d(TAG, " setRequest response from server " + response.toString());
                        mOnResultCallBack.onSuccess(response);

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                mOnResultCallBack.onError(error);
                if (error != null) {
                    FslLog.d(TAG, "Error : " + error.getMessage());
                }
                FslLog.d(TAG, "Error : " + error);
            }
        });

        int socketTimeout = FClientConfig.volleyHitWaitTime;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonObjReq.setRetryPolicy(policy);
        if (AppController.getInstance() != null) {
            AppController.getInstance().addToRequestQueue(jsonObjReq, TAG);
        } else {
            FslLog.d(TAG, " Check app instance ");
        }
    }

    /*public static void setRequestUsingSoap(*//*String mUrl, JSONObject mJsonObject, final CallBackResult mOnResultCallBack*//*) {

        String soap_string =
                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" +
                        "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" " +
                        "xmlns:ns1=\"http://schemas.xmlsoap.org/soap/http\" " +
                        "xmlns:soap=\"http://schemas.xmlsoap.org/wsdl/soap/\" " +
                        "xmlns:wsdl=\"http://schemas.xmlsoap.org/wsdl/\" " +
                        "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" " +
                        "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" >\n" +
                        "<SOAP-ENV:Body>\n" +

                        *//** Open Body **//*
                        "<params>\n" +
                        "<username>" + "Anand" + "</username>\n" +
                        "<password>" + "1234" + "</email>\n" +
                        "</params>\n" +
                        */

    /**
     * Close Body
     **//*

                        "</SOAP-ENV:Body></SOAP-ENV:Envelope>";

        String url = "http://buyerease.co.in/OfflineService/OfflineInspectionService.svc/AuthenticateUser";
        final MediaType MEDIA_TYPE =
                MediaType.parse("application/xml");
        final OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(MEDIA_TYPE,
                soap_string);

        final Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Content-Type", "application/xml")
//                .addHeader("Authorization", "Your Token")
                .addHeader("cache-control", "no-cache")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String mMessage = e.getMessage().toString();
                FslLog.w(TAG + " failure Response ", mMessage);
                //call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response)
                    throws IOException {

                String mMessage = response.body().string();
                FslLog.w(TAG, " Response " + response);
                FslLog.w(TAG, " mMessage " + mMessage);
                if (response.isSuccessful()) {
                    try {
                        JSONObject json = new JSONObject(mMessage);
                        final String serverResponse = json.getString("Your Index");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
*/
    public interface CallBackResult {
        public void onSuccess(JSONObject resultResponse);

        public void onError(VolleyError volleyError);


    }

}
