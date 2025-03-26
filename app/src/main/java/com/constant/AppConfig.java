package com.constant;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by ADMIN on 8/10/2017.
 */

public final class AppConfig {
    public static SharedPreferences sharedpreferences;
    Context context;
    // Server user login url
    //For API
  //  public static String IP_ADDRESS_LIVE = "http://125.63.65.42/";
    public static String IP_ADDRESS_DOWNLOAD = "http://buyerease.co.in/buyerease/";
    //"http://103.151.107.158/BuyereaseMSCP/";
//    public static String IP_ADDRESS_DOWNLOAD = "http://103.205.66.3/buyerease/";//"http://103.151.107.158/BuyereaseMSCP/";
   //  public static String IP_ADDRESS_DOWNLOAD = "http://www.buyerease.co.in/buyerease/";  //
//    public static String IP_ADDRESS_DOWNLOAD = "http://bms.swades-sourcing.com/buyereasebh/";
   // public static String IP_ADDRESS_DOWNLOAD = "http://103.205.66.3/buyerease/";

    //for live
//     public static String IP_ADDRESS_LIVE = "http://buyerease.co.in/";
     public static String IP_ADDRESS_LIVE = "http://buyerease.co.in/";

//     public static String IP_ADDRESS_LIVE = "http://103.205.66.3/";//"http://buyerease.co.in/";//"http://103.151.107.158/";

   //public static String IP_ADDRESS_DOWNLOAD = "https://bms.indianinc.com/BuyerEaseBh/";

   // public static String IP_ADDRESS_LIVE = "http://14.99.58.67/";
   // public static String IP_ADDRESS_DOWNLOAD = "http://14.99.58.67/Buyereasebh/";

   /* public static String IP_ADDRESS_DOWNLOAD = "http://103.205.66.3/buyerease/";
    public static String IP_ADDRESS_LIVE = "http://103.205.66.3/";*/
    public static String IP_ADDRESS = IP_ADDRESS_LIVE;


    public static String BASE_ADDRESS_DOWNLOAD = "Helps/DownloadFile.aspx?FileName=output/";

    //    public static String BASE_URL_TEST = "BuyereaseWebAPI/api/";
    public static String BASE_URL_LIVE = "BuyereaseWebAPI/api/";

    public static String BASE_URL = BASE_URL_LIVE;
//"http://192.168.0.224/BuyereaseWebAPI/api/Quality/GetQualityInspection"

    public static String URL_LOGIN = IP_ADDRESS + BASE_URL + "Account/AuthenticateUser";
    public static String URL_GET_DATA = IP_ADDRESS + BASE_URL + "Quality/GetQualityInspection";
    public static String URL_SEND_DATA = IP_ADDRESS + BASE_URL + "Quality/SendQualityInspection";
    public static String URL_SEND_FILE = IP_ADDRESS + BASE_URL + "Quality/SendFile";
    public static String URL_OTP = IP_ADDRESS + BASE_URL + "account/AuthenticateUser";

    //http://103.117.156.104/BuyereaseWebAPI/api/quality/GetDefectMaster
    public static String URL_DEFECT_MASTER = IP_ADDRESS + BASE_URL + "quality/GetDefectMaster";

    public static String URL_SEND_STYLE_DATA = URL_SEND_DATA;
    public static String URL_SEND_STYLE_FILE = URL_SEND_FILE;

//    public static String URL_GET_STYLE_DATA = IP_ADDRESS + BASE_URL + "Quality/GetStyle";

    public static String URL_GET_STYLE_DATA = IP_ADDRESS + BASE_URL + "Quality/GetStyles";

    public static String URL_FOR_CHANGE_PASSWORD = IP_ADDRESS + BASE_URL + "Account/ChangePassword";
    public static String URL_FOR_FORGOT_PASSWORD = IP_ADDRESS + BASE_URL + "Account/ForgotPassword";


    public static String URL_FOR_PROFILE_SYNC = IP_ADDRESS + BASE_URL + "Associate/UpdateProfile";


    public static String URL_DOWNLOAD_BASE_URL = IP_ADDRESS_DOWNLOAD + BASE_ADDRESS_DOWNLOAD;// IP_ADDRESS_DOWNLOAD_DEMO

    public static String URL_HISTORY = IP_ADDRESS + BASE_URL + "Quality/Encrypt";

    public static String URL_HISTORY_DETAIL = IP_ADDRESS_DOWNLOAD + "QR/InspectionReportEntryViaHtml.aspx?Session=";

    public static String URL_UPDATE_READ_STATUS = IP_ADDRESS + BASE_URL + "Quality/DownloadStatus";

    public static final boolean isOnline(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }

        }
        return false;

    }

    public static final String getUniqueDeviceID(Context context) {
        return android.provider.Settings.Secure.getString(
                context.getContentResolver(),
                android.provider.Settings.Secure.ANDROID_ID);
    }

    public static String getCurrntDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String current_date = sdf.format(new Date());
        return current_date;
    }

    public static String getCurrntDateOnly() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String current_date = sdf.format(new Date());
        return current_date;
    }

    public static String getCurrntDateDDMMyy() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        String current_date = sdf.format(new Date());
        return current_date;
    }

    public static String getCurrentTime() {
        String date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(Calendar.getInstance().getTime());

//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String currentTime = sdf.format(new Date());
        return date;
    }


}
