package com.util;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.constant.FClientConstants;
import com.constant.JsonKey;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import me.drakeet.support.toast.ToastCompat;


public class ServerErrorHandleStatus implements JsonKey {


    public static void onAuthenticationErrorStatus(final Context context, VolleyError volleyError) {
        try {
            String responseBody = new String(volleyError.networkResponse.data, "UTF-8");
            JSONObject jsonObject = new JSONObject(responseBody);
            FslLog.e("error  in body ::", responseBody);
            FslLog.e("error  onAuthenticationErrorStatus ::", "" + volleyError);
            Toast toast = ToastCompat.makeText(context, FClientConstants.TEXT_not_able_to_receive_response_from_server, Toast.LENGTH_LONG);
            GenUtils.safeToastShow("ServerErrorHandleStatus", context, toast);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
