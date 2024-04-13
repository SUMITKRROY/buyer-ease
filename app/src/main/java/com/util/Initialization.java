package com.util;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;

import java.lang.ref.WeakReference;
import java.util.Stack;


public class Initialization {

    public static final int REQUEST_FOR_MAID_ATTENDANCE = 1015;


    public static final int REQUEST_A_LOCATION = 900;
    public static final int SOURCE_REQUEST = 1001;
    public static final int DESTINATION_REQUEST = 1002;
    public static final int REQUEST_FOR_RATING = 1003;
    public static final int REQUEST_FOR_PROFILE = 1004;
    public static final int carSelectedResultCode = 1005;
    public static final int REQUEST_FOR_HOME_ADDRESS = 1006;
    public static final int REQUEST_FOR_WORK_ADDRESS = 1007;
    public static final int REQUEST_FOR_EDIT_HOME_ADDRESS = 1008;
    public static final int REQUEST_FOR_EDIT_WORK_ADDRESS = 1009;
    public static final int GPS_ENABLE_REQUEST = 1010;
    public static final int GPS_ENABLE_REQUEST_ON_PICKUP = 1011;
    public static final int REQUEST_FOR_RIDE_CANCEL = 1012;
    public static final int REQUEST_FOR_RIDE_CANCEL_ON_GCM = 1013;
    public static final int MY_PERMISSION_ACCESS_COARSE_LOCATION = 20;
    public static final int MY_PERMISSION_ACCESS_FINE_LOCATION = 21;
    public static final int REQUEST_FOR_PAYMENT = 1014;


    public static final int REQUEST_FOR_ENABLE_PAYMENT = 1015;
    public static final int REQUEST_FOR_BUYING_CREDITS = 1016;

    public static final int REQUEST_TO_POST_A_RIDE = 1101;
    //Anand
    public static final int REQUEST_TO_SELECT_A_ROUTE = 1102;

    public static final int REQUEST_FOR_CANCEL_SEAT = 1103;
    public static final int REQUEST_FOR_CANCEL_RIDE = 1104;
    public static final int REQUEST_FOR_EDIT_ROUTE = 1105;

    public static final int REQUEST_FOR_CHOOSE_RIDE_OPTIONS = 1106;
    public static final int RESULT_FOR_CHOOSE_RIDE_OPTIONS = 1107;
    public static final int REQUEST_FOR_PENDING_PAYMENT = 1018;
    public static final int REQUEST_FOR_OLD_POSTED_RIDES = 1019;

    public static boolean isAlreadyCancelRide;
    //    public static boolean isFirstTime;

    public static boolean isRunningProcess;
    public static boolean isRestartApp;
    public static boolean isRideRequested;
    public static boolean isSaveAsAddress;
    //    public static boolean isNewUser;
    public static boolean isGPSYES = true;

//refreshing search to get route JSON from direction API

    //    public static ArrayList<LatLng> listStepOfRouteOnStartRide=new ArrayList<>();
    public static int summationOfDistance, totalDistance, onePercentOfTotalDistance;
    public static Stack<Fragment> fragmentStackToCurrentFragmentResume;

    public enum REQUEST_STATE {
        UNKNOWN_SATE,
        SOURCE_STATE,
        DESTINATION_STATE
    }

    public enum SAVED_ADDRESSES {
        NON,
        HOME,
        WORK,
        HOMEWORK
    }


    public static SAVED_ADDRESSES saved_address;

    public static void setSavedAddress(SAVED_ADDRESSES savedAddress) {
        saved_address = savedAddress;
    }

    public static SAVED_ADDRESSES getSavedAddress() {
        return saved_address;
    }

    public static String getSavedAddressName() {
        switch (saved_address) {
            case NON:
                return "SAVED_ADDRESSES.NON";
            case HOME:
                return "SAVED_ADDRESSES.HOME";
            case WORK:
                return "SAVED_ADDRESSES.WORK";
            case HOMEWORK:
                return "SAVED_ADDRESSES.HOMEWORK";
        }

        return null;
    }

    public static String getRequestCodeName(int requestCode) {
        switch (requestCode) {
            case REQUEST_A_LOCATION:
                return "REQUEST_A_LOCATION";
            case SOURCE_REQUEST:
                return "SOURCE_REQUEST";
            case DESTINATION_REQUEST:
                return "DESTINATION_REQUEST";
            case REQUEST_FOR_RATING:
                return "REQUEST_FOR_RATING";
            case REQUEST_FOR_PROFILE:
                return "REQUEST_FOR_PROFILE";
            case REQUEST_FOR_HOME_ADDRESS:
                return "REQUEST_FOR_HOME_ADDRESS";
            case REQUEST_FOR_WORK_ADDRESS:
                return "REQUEST_FOR_WORK_ADDRESS";
            case REQUEST_FOR_EDIT_HOME_ADDRESS:
                return "REQUEST_FOR_EDIT_HOME_ADDRESS";
            case REQUEST_FOR_EDIT_WORK_ADDRESS:
                return "REQUEST_FOR_EDIT_WORK_ADDRESS";
            case REQUEST_TO_SELECT_A_ROUTE:
                return "REQUEST_TO_SELECT_A_ROUTE";
        }

        return null;
    }

    public static AlertDialog alertDialog;
    public static WeakReference<Activity> activityWeakReference;
}
