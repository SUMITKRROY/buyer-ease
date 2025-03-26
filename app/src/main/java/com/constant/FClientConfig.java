package com.constant;

/**
 * Created by ADMIN on 8/31/2017.
 */

public class FClientConfig {
    public static final int LogFileMaxSizeInKB = 6000;
    public static String mExternalStorageDir = null;

    public static final int volleyHitShortWaitTime = 10000;//to be used only when can't wait for long example for roadAPI when ending ride
    public static final int volleyHitWaitTime = 30000*10;
    public static final int thirdParty_dependent_timeout = 60000;
    /**
     * The default number of retries
     */
    public static final String testReportFolder = "testReport";
    public static final String enclosureFolder = "enclosure";


    public static final int DEFAULT_MAX_RETRIES = 0;
    public static final int DOCUMENT_IMAGE_SIZE = 480;
    public static final int PP_X_AXIS_MIN_PIXEL_SIZE = 100;//240
    public static final int COMPANY_TRUNC_LENGTH = 15;

    public static final String LOC_ID = "DEL";
    public static final int RESEND_BUTTON_DELAY = 30;// in seconds
    public static final int COUNT_DOWN_TIMER_VALUE = 1;// in seconds

    public static final String MODULE_VISITOR_MANAGEMENT = "Visitor";
    public static final String MODULE_STAFF_MANAGEMENT = "Staff";
    public static final String MODULE_MAINTENANCE_MANAGEMENT = "Maintenance";
    public static final String MODULE_FLAT_MANAGEMENT = "Flat";
    public static final String MODULE_NOTICE_BOARD = "Notice Board";
    public static final String MODULE_NOTIFICATIONS = "Notification";
    public static final String MODULE_INVENTORY = "Inventory";
    public static final String MODULE_VENDOR_MANAGEMENT = "Vendor";

    public static final String MODULE_COMPLAINT_MANAGEMENT = "Complaint";
    public static final String MODULE_ACCOUNTING = "My bills";
    public static final String MODULE_REVENUE_GENERATION = "Revenue Generation";
    public static final String MODULE_PARKING_MANAGEMENT = "Parking";
    public static final String MODULE_SERVANTS_MAID_ENTRY = "Maid entry";
    public static final String MODULE_ADDRESS_BOOK = "Address Book";
    public static final String SERVER_ERROE_RESPONSE_DATA_NOT_FOUND = "Data Not Found";

    public static final String OTP_SMS_STRING_TO_MATCH = "is your One Time Password to verify mobile number";
    public static final int MIN_OTP_LEN = 4;

}
