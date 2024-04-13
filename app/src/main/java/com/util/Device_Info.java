package com.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.text.format.Formatter;
import android.util.DisplayMetrics;

import com.constant.FEnumerations;
import com.data.DeviceSession;

import java.lang.reflect.Field;



import static android.content.Context.WIFI_SERVICE;


/******
 * On startup app shall check is Appid is available or not,
 * app shall extract following device information
 *******/

public class Device_Info {
    String regid;
    private static String TAG = "Device_Info";

    public interface OnDeviceInfoListener {
        void onDeviceInfoLisner(String devices_Unique_Id, String device_OS_type, String device_OS_version, String app_version, int App_versionCode, String device_model_no, float device_Screen_size, String device_screen_resolution, int device_screen_density, String gCM_client_registration_id);
    }

    OnDeviceInfoListener onDeviceInfoListener;

    public void setOnDeviceInfoListener(OnDeviceInfoListener onDeviceInfoListener) {
        this.onDeviceInfoListener = onDeviceInfoListener;
    }

    public String Devices_Unique_Id;
    public String Device_OS_type;
    public String Device_OS_version;
    public String App_version;
    public int App_versionCode;
    public String Device_model_no;
    public float Device_Screen_size;
    public String Device_screen_resolution;
    public int Device_screen_density;//pahse2
    public String GCM_client_registration_id;//phase2
    Context context;

    public Device_Info(Context context) {
        this.context = context;
    }

    public Device_Info(Context context, OnDeviceInfoListener onDeviceInfoListener) {
        super();

        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        FslLog.i(TAG, "DisplayMetrics are\n" + "density:" + dm.density
                + "\ndensityDpi:" + dm.densityDpi
                + "\nscaledDensity:" + dm.scaledDensity
                + "\nheightPixels:" + dm.heightPixels
                + "\nwidthPixels:" + dm.widthPixels
                + "\nxdpi:" + dm.xdpi
                + "\nydpi:" + dm.ydpi);

        this.context = context;
        this.onDeviceInfoListener = onDeviceInfoListener;
        Devices_Unique_Id = storeDevice_Info(context);
        Device_OS_type = "Android";//VERSION_CODES.KITKAT + "";
                  /* if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.GINGERBREAD) {
                         // only for gingerbread and newer versions
			    	}*/
        Device_OS_version = Build.VERSION.SDK_INT + "";
        App_version = getAppVersion(context);
        App_versionCode = getAppVersionCode(context);
        Device_model_no = getDeviceName();
        Device_Screen_size = getDeviceSize();
        Device_screen_resolution = getDeviceResolution();

        if ((context.getResources().getDisplayMetrics().density + "").contentEquals(FEnumerations.DEVICE_DENSITY_LDPI))
            Device_screen_density = FEnumerations.E_DEVICE_DENSITY_LDPI;
        else if ((context.getResources().getDisplayMetrics().density + "").contentEquals(FEnumerations.DEVICE_DENSITY_MDPI))
            Device_screen_density = FEnumerations.E_DEVICE_DENSITY_MDPI;
        else if ((context.getResources().getDisplayMetrics().density + "").contentEquals(FEnumerations.DEVICE_DENSITY_HDPI))
            Device_screen_density = FEnumerations.E_DEVICE_DENSITY_HDPI;
        else if ((context.getResources().getDisplayMetrics().density + "").contentEquals(FEnumerations.DEVICE_DENSITY_XHDPI))
            Device_screen_density = FEnumerations.E_DEVICE_DENSITY_XHDPI;
        else if ((context.getResources().getDisplayMetrics().density + "").contentEquals(FEnumerations.DEVICE_DENSITY_XXHDPI))
            Device_screen_density = FEnumerations.E_DEVICE_DENSITY_XXHDPI;
        else if ((context.getResources().getDisplayMetrics().density + "").contentEquals(FEnumerations.DEVICE_DENSITY_XXXHDPI))
            Device_screen_density = FEnumerations.E_DEVICE_DENSITY_XXXHDPI;
        else {

            if ((context.getResources().getDisplayMetrics().density) <= FEnumerations.DEVICE_DENSITY_LDPI_VALUE) {
                Device_screen_density = FEnumerations.E_DEVICE_DENSITY_LDPI;
            } else if ((context.getResources().getDisplayMetrics().density) > FEnumerations.DEVICE_DENSITY_LDPI_VALUE
                    && (context.getResources().getDisplayMetrics().density <= FEnumerations.DEVICE_DENSITY_MDPI_VALUE)) {
                Device_screen_density = FEnumerations.E_DEVICE_DENSITY_MDPI;
            } else if ((context.getResources().getDisplayMetrics().density > FEnumerations.DEVICE_DENSITY_MDPI_VALUE)
                    && (context.getResources().getDisplayMetrics().density <= FEnumerations.DEVICE_DENSITY_HDPI_VALUE)) {
                Device_screen_density = FEnumerations.E_DEVICE_DENSITY_HDPI;
            } else if ((context.getResources().getDisplayMetrics().density > FEnumerations.DEVICE_DENSITY_HDPI_VALUE)
                    && (context.getResources().getDisplayMetrics().density <= FEnumerations.DEVICE_DENSITY_XHDPI_VALUE)) {
                Device_screen_density = FEnumerations.E_DEVICE_DENSITY_XHDPI;
            } else if ((context.getResources().getDisplayMetrics().density > FEnumerations.DEVICE_DENSITY_XHDPI_VALUE)
                    && (context.getResources().getDisplayMetrics().density <= FEnumerations.DEVICE_DENSITY_XXHDPI_VALUE)) {
                Device_screen_density = FEnumerations.E_DEVICE_DENSITY_XXHDPI;
            } else if ((context.getResources().getDisplayMetrics().density > FEnumerations.DEVICE_DENSITY_XXHDPI_VALUE)
                    && (context.getResources().getDisplayMetrics().density <= FEnumerations.DEVICE_DENSITY_XXXHDPI_VALUE)) {
                Device_screen_density = FEnumerations.E_DEVICE_DENSITY_XXXHDPI;
            }
        }


        //regID="APA91bEqkwi0FBOaz2DX5YRTthxnNAnaLRMAKwT0SBTMv-zVhZEhVdNKVYHesPaawpvQz_JqEFWdrhkj4g6CbVXbtrUI_79lzsb-81CNZAOEw-YnBJrwIOS6P531Jv6hBk_PScAZvqxY_XAvHKyVQOvbVm0lIVO33w";


//		   getRegId();
        getFCMId();

    }

    private void getFCMId() {
        DeviceSession deviceSession = new DeviceSession(context);
//        GCM_client_registration_id = deviceSession.getDeviceInitialData().KEY_GCM_CLIENT_REGISTRATION_ID;
        onDeviceInfoListener.onDeviceInfoLisner(Devices_Unique_Id, Device_OS_type, Device_OS_version, App_version, App_versionCode, Device_model_no, Device_Screen_size, Device_screen_resolution, Device_screen_density, GCM_client_registration_id);

    }

    public String storeDevice_Info(Context context) {

//        boolean lDeviceIdPermissionFlag = true;
        String imeiNo = "";

      /*  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                lDeviceIdPermissionFlag = false;
            }
        }*/
        imeiNo = getUniqueID();
       /* if (lDeviceIdPermissionFlag) {

            SocietyLog.i("Device_Info", "fetching device ids");

            TelephonyManager TM = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

            // IMEI No.
            imeiNo = TM.getDeviceId(); //READ_PHONE_STATE permission is required

            // IMSI No.
            String imsiNo = TM.getSubscriberId();

            // SIM Serial No.
            String simSerialNo = TM.getSimSerialNumber();

        }*/

        FslLog.i("Device_Info", "imeiNo" + imeiNo);

        return imeiNo;
        // return android.provider.Settings.System.getString(context.getContentResolver(),Settings.Secure.ANDROID_ID);
    }

    public String getUniqueID() {
        String myAndroidDeviceId = "";
        boolean lDeviceIdPermissionFlag = true;


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                lDeviceIdPermissionFlag = false;
            }
        }

//        TelephonyManager mTelephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//        if (mTelephony.getDeviceId() != null) {
//            myAndroidDeviceId = mTelephony.getDeviceId();
//        } else {
        myAndroidDeviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
//        }
        return myAndroidDeviceId;
    }

    public String getDeviceIp() {
        String ipAddress = null;
        try {
            WifiManager wifiManager = (WifiManager) context.getSystemService(WIFI_SERVICE);
            ipAddress = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ipAddress;
    }

    public String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }


    private String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }

    public String getDeviceResolution() {
        DisplayMetrics display = context.getResources().getDisplayMetrics();
        int width = display.widthPixels;
        int height = display.heightPixels;
        return width + "X" + height;
    }

    public float getDeviceSize() {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        int dens = dm.densityDpi;
        double wi = (double) width / (double) dens;
        double hi = (double) height / (double) dens;
        double x = Math.pow(wi, 2);
        double y = Math.pow(hi, 2);
        double screenInches = Math.sqrt(x + y);
        return (float) screenInches;
    }

    // it also used in MainActivity
    public static String getAppVersion(Context mcontext) {
        PackageInfo pInfo = null;
        try {
            pInfo = mcontext.getPackageManager().getPackageInfo(mcontext.getPackageName(), 0);
        } catch (NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        FslLog.d(TAG, "App version Name " + pInfo.versionName);

        return pInfo.versionName;
    }

    // it also used in MainActivity
    public static int getAppVersionCode(Context mcontext) {
        PackageInfo pInfo = null;
        try {
            pInfo = mcontext.getPackageManager().getPackageInfo(mcontext.getPackageName(), 0);
        } catch (NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        FslLog.d(TAG, "App version code " + pInfo.versionCode);
        return pInfo.versionCode;
    }

    //...
    public String AndroidDeviceOsName() {
        StringBuilder builder = new StringBuilder();
        builder.append("android : ").append(Build.VERSION.RELEASE);

        Field[] fields = Build.VERSION_CODES.class.getFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            int fieldValue = -1;

            try {
                fieldValue = field.getInt(new Object());
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

            if (fieldValue == Build.VERSION.SDK_INT) {
                builder.append(" : ").append(fieldName).append(" : ");
                builder.append("sdk=").append(fieldValue);
                return "Android " + fieldName;
            }
        }

        FslLog.d("AndroidDeviceOsName", "OS: " + builder.toString());
        return "Android Unknown";
    }


///////////


}
