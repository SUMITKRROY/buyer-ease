package com.constant;

/**
 * Created by ADMIN on 8/31/2017.
 */

public class FEnumerations {


    public static final int PERMISSION_REQUEST = 100;
    public static final int READ_SMS_PERMISSION = 101;
    public static final int CAMERA_PERMISSION = 102;
    public static final int CALL_PERMISSION = 103;
    public static final int READ_STORAGE_PERMISSION = 104;
    public static final int PHOTO_PERMISSION = 105;
    public static final int SAVE_CONTACT_PERMISSION = 106;

    public static final int SAVE_DOC_PERMISSION = 107;

    public static final int RESULT_READ_STORAGE_DENIED = 8;
    public static final int RESULT_CAMERA_DENIED = 9;
    public static final int RESULT_READ_CONTACTS = 10;
    public static final int RESULT_WRITE_CONTACTS = 11;

    public static final int RESULT_CAMERA = 1;
    public static final int RESULT_GALLERY = 2;

    public static final String E_ASSOCIATE_TYPE_OF_GARD = "0";
    public static final int E_SYNC_IN_BACKGROUND = 1;
    public static final int E_SYNC_NOT_IN_BACKGROUND = 0;

    public static final int E_OPT_SEND = 1;
    public static final int E_OPT_RESEND = 2;
    public static final int E_OPT_SUBMIT = 3;

    /*device density by anand*/
    public static final String DEVICE_DENSITY_LDPI = "0.75";

    public static final String DEVICE_DENSITY_MDPI = "1.0";

    public static final String DEVICE_DENSITY_HDPI = "1.5";

    public static final String DEVICE_DENSITY_XHDPI = "2.0";

    public static final String DEVICE_DENSITY_XXHDPI = "3.0";

    public static final String DEVICE_DENSITY_XXXHDPI = "4.0";


    public static final int E_DEVICE_DENSITY_LDPI = 1; //0.75

    public static final int E_DEVICE_DENSITY_MDPI = 2;//1.0

    public static final int E_DEVICE_DENSITY_HDPI = 3;//1.5

    public static final int E_DEVICE_DENSITY_XHDPI = 4;//2.0

    public static final int E_DEVICE_DENSITY_XXHDPI = 5;//3.0

    public static final int E_DEVICE_DENSITY_XXXHDPI = 6;//4.0

    /*standard device density values in float*/
    public static final float DEVICE_DENSITY_LDPI_VALUE = 0.75f;

    public static final float DEVICE_DENSITY_MDPI_VALUE = 1.0f;

    public static final float DEVICE_DENSITY_HDPI_VALUE = 1.5f;

    public static final float DEVICE_DENSITY_XHDPI_VALUE = 2.0f;

    public static final float DEVICE_DENSITY_XXHDPI_VALUE = 3.0f;

    public static final float DEVICE_DENSITY_XXXHDPI_VALUE = 4.0f;

    public static final int RESULT_LOAD_IMAGE = 21;
    public static final int IMAGE_CAPTURE = 22;
    public static final int PICKFILE_RESULT_CODE = 23;
    public static final int REQUEST_FOR_PROFILE_ADD = 1;
    public static final int REQUEST_FOR_PROFILE_EDIT = 2;
    public static final int RESULT_FOR_DETAIL_CODE = 33;
    public static final int RESULT_PO_ITEM = 21;
    public static final int RESULT_SIZE_QTY = 89;

    public static final int REQUEST_FOR_CHANGE_PASSWORD = 1;
    public static final int REQUEST_FOR_FORGOT_PASSWORD = 2;

    public static final int E_REQUEST_FOR_ALERT = 1;
    public static final int E_REQUEST_FOR_TODAY_ALERT = 2;


    public static final int E_TRUE = 1;
    public static final int E_FALSE = 0;


    public static final int VIEW_TYPE_ITEM_QUALITY = 1;
    public static final int VIEW_TYPE_ITEM_WORKMAN_SHIP = 2;
    public static final int VIEW_TYPE_ITEM_CARTON = 3;
    public static final int VIEW_TYPE_MORE = 4;

    public static final int REQUEST_FOR_INNER_PACKING_FILL = 1;
    public static final int REQUEST_FOR_ADD_PACKING_FILL = 2;
    public static final int REQUEST_FOR_MASTER_PACKING_FILL = 3;
    public static final int REQUEST_FOR_PALLET_PACKING_FILL = 7;
    public static final int REQUEST_FOR_ADD_WORKMANSHIP = 4;
    public static final int REQUEST_FOR_UNIT_PACKING_FILL = 5;
    public static final int REQUEST_FOR_ADD_DIGITALS_UPLOAD = 6;
    public static final int REQUEST_FOR_ADD_ITEM_MEASUREMENT = 8;

    public static final int REQUEST_FOR_ADD_ITEM_MEASUREMENT_FINDING = 9;
    public static final int REQUEST_FOR_EDIT_WORKMANSHIP = 10;
    public static final int REQUEST_FOR_EDIT_ITEM_MEASUREMENT = 11;
    public static final int REQUEST_FOR_ADD_INTIMATION = 22;
    public static final int REQUEST_FOR_ADD_HOLOGRAM = 33;

    public static final int REQUEST_FOR_MASTER_PACKING_ATTACHMENT = 1;
    public static final int REQUEST_FOR_INNER_PACKING_ATTACHMENT = 2;
    public static final int REQUEST_FOR_UNIT_PACKING_ATTACHMENT = 3;
    public static final int REQUEST_FOR_PALLET_PACKING_ATTACHMENT = 4;
    public static final int REQUEST_FOR_QUALITY_PARAMETER_ATTACHMENT = 5;
    public static final int REQUEST_FOR_InternalTest_ATTACHMENT = 6;
    public static final int REQUEST_FOR_ENCLOSURE_ATTACHMENT = 6;
    public static final int REQUEST_FOR_UNIT_PKG_APP_ATTACHMENT = 131;
    public static final int REQUEST_FOR_SHIPPING_PKG_APP_ATTACHMENT = 132;
    public static final int REQUEST_FOR_INNER_PKG_APP_ATTACHMENT = 133;
    public static final int REQUEST_FOR_MASTER_PKG_APP_ATTACHMENT = 134;
    public static final int REQUEST_FOR_PALLET_PKG_APP_ATTACHMENT = 135;
    public static final int REQUEST_FOR_UNIT_BARCODE_ATTACHMENT = 136;
    public static final int REQUEST_FOR_INNER_BARCODE_ATTACHMENT = 137;
    public static final int REQUEST_FOR_MASTER_BARCODE_ATTACHMENT = 138;
    public static final int REQUEST_FOR_PALLET_BARCODE_ATTACHMENT = 139;
    public static final int REQUEST_FOR_ON_SITE_ATTACHMENT = 140;
    public static final String E_STATUS_GEN_ID = "36";
    public static final String E_OVERALL_RESULT_STATUS_GEN_ID = "530";
    public static final String PACKAGE_APPEARANCE_OVERALL_RESULT_STATUS_GEN_ID = "550";
    public static final String ONSITE_OVERALL_RESULT_STATUS_GEN_ID = "545";
    public static final String SAMPLE_COLLECTED_OVERALL_RESULT_STATUS_GEN_ID = "540";


    public static final int VIEW_TYPE_QUALITY_PARAMETER_DETAIL_LEVEL = 5;
    public static final int VIEW_TYPE_QUALITY_PARAMETER_INSPECTION_LEVEL = 6;
    public static final int VIEW_TYPE_INTERNAL_TEST_LEVEL = 7;


    public static int E_VIEW_ONLY_SEND = 1;
    public static int E_VIEW_ONLY_GET = 2;
    public static int E_VIEW_ONLY_SYNC = 3;
    public static int E_VIEW_SEND_AND_SYNC = 4;

    public static String E_IMAGE_EXTN = "jpg";

    //added by shekhar////////////////

    /*null= Item & Quantity
        30	* Packaging Appearance=0~Show|7~Hide
        31	* Packaging Measurement=0~Show & Always Print|1~Show, but print if specified|7~Hide
        33	* Barcodes=0~Show|7~Hide
        34	* OnSite Test=0~Show & Always Print|1~Show, but print if specified|7~Hide
        35	* Workmanship=0~Show & Always Print|1~Show, but print if specified|2~Show, Do not print|7~Hide
        36	* Sample Purpose
        37	* Item Measurement=0~Show & Always Print|1~Show, but print if specified|7~Hide
        39	* Enclosure=0~Show|7~Hide=0~Show & Always Print|1~Show, but print if specified|7~Hide
        null=History
        88	Item Specific Quality Parameters=0~Not Applicable|1~Applicable(Quality Parameters)
        95	Internal Test (Lighting & Lamp)=0~Show & Print|2~Show|7~Hide
        38	* Digital=0~Show & Always Print|1~Show, but print if specified|7~Hide
        40	* Test Report=0~Show|7~Hide*/
    public static final String PKG_APP_GEN_ID = "79";
    public static final String PKG_APP_MAIN_ID = "51";

    public static final String PKG_APP_SUB_ID = "30";
    public static final String PKG_MEASURMENT_SUB_ID = "31";
    public static final String BARCODE_SUB_ID = "33";
    public static final String ON_SITE_TEST_SUB_ID = "34";
    public static final String WORKMANSHIP_SUB_ID = "35";
    public static final String SAMPLE_PURPOSE_SUB_ID = "36";
    public static final String ITEM_MEASURMENT_SUB_ID = "37";
    public static final String DIGITAL_UPLOAD_SUB_ID = "38";
    public static final String ENCLOSURE_SUB_ID = "39";
    public static final String TEST_REPORT_SUB_ID = "40";
    public static final String QUALITY_PARAMETER_SUB_ID = "88";
    public static final String INTERNAL_TEST_SUB_ID = "95";

    ///////////

    /////////////////table Name

    public static final String E_TABLE_QRPOItemHdr = "Table0";
    public static final String E_TABLE_QRPOItemDtl = "Table1";
    public static final String E_TABLE_QRPOItemDtl_Image = "Table2";
    public static final String E_TABLE_QRPOIntimationDetails = "Table3";
    public static final String E_TABLE_GenMst = "Table4";
    public static final String E_TABLE_SysData22 = "Table5";
    public static final String E_TABLE_QualityLevel = "Table6";
    public static final String E_TABLE_QualityLevelDtl = "Table7";
    public static final String E_TABLE_InsplvlHdr = "Table8";
    public static final String E_TABLE_InspLvlDtl = "Table9";
    public static final String E_TABLE_Enclosures = "Table10";
    public static final String E_TABLE_QRInspectionHistory = "Table11";
    public static final String E_TABLE_TestReport = "Table12";

    public static final String E_TABLE_UserMaster_UpdateCriticalAllowed = "Table13";

    public static final String E_TABLE_ItemMeasurement = "Table14";
    public static final String E_TABLE_AuditBatchDetails = "Table15";
    public static final String E_TABLE_Size_Quantity = "Table16";
//    public static final String E_TABLE_QRFeedBackHdr = "Table16";


    public static final String E_TABLE_NAME_QRPOItemHdr = "QRPOItemHdr";
    public static final String E_TABLE_NAME_QRPOItemDtl = "QRPOItemDtl";
    public static final String E_TABLE_NAME_QRPOItemDtl_Image = "QRPOItemDtl_Image";

    public static final String E_TABLE_NAME_QRPOItemDtl_Sample_Purpose = "QRPOItemDtl_Sample_Purpose";//added by shekhar
    public static final String E_TABLE_NAME_QRPOItemDtl_OnSite_Test = "QRPOItemDtl_OnSite_Test";//added by shekhar
    public static final String E_TABLE_NAME_QRPOItemDtl_PKG_App_Details = "QRPOItemDtl_PKG_App_Details";//added by shekhar

    public static final String E_TABLE_NAME_QRPOIntimationDetails = "QRPOIntimationDetails";
    public static final String E_TABLE_NAME_GenMst = "GenMst";
    public static final String E_TABLE_NAME_SysData22 = "SysData22";
    public static final String E_TABL_NAMEE_QualityLevel = "QualityLevel";
    public static final String E_TABLE_NAME_QualityLevelDtl = "QualityLevelDtl";
    public static final String E_TABLE_NAME_InsplvlHdr = "InsplvlHdr";
    public static final String E_TABLE_NAME_InspLvlDtl = "InspLvlDtl";
    public static final String E_TABLE_NAME_Enclosures = "Enclosures";
    public static final String E_TABLE_NAME_QRInspectionHistory = "QRInspectionHistory";
    public static final String E_TABLE_NAME_TestReport = "TestReport";
    public static final String E_TABLE_NAME_ItemMeasurement = "QRPOItemDtl_ItemMeasurement";
    public static final String E_TABLE_NAME_AuditBatchDetails = "QRAuditBatchDetails";
    public static final String E_TABLE_NAME_QRFindings = "QRFindings";

    public static final String E_SYNC_HEADER_TABLE = "HEADER";
    public static final String E_SYNC_SIZE_QUANTITY_TABLE = "SIZE QUANTITY";
    public static final String E_SYNC_IMAGES_TABLE = "IMAGE";
    public static final String E_SYNC_WORKMANSHIP_TABLE = "WORKMANSHIP";
    public static final String E_SYNC_ITEM_MEASUREMENT_TABLE = "ITEM MEASUREMENT";
    public static final String E_SYNC_FINDING_TABLE = "FINDING";
    public static final String E_SYNC_QUALITY_PARAMETER_TABLE = "QUALITY PARAMETER";
    public static final String E_SYNC_FITNESS_CHECK_TABLE = "FITNESS CHECK";
    public static final String E_SYNC_PRODUCTION_STATUS_TABLE = "PRODUCTION STATUS";
    public static final String E_SYNC_INTIMATION_TABLE = "INTIMATION";
    public static final String E_SYNC_ENCLOSURE_TABLE = "ENCLOSURE";
    public static final String E_SYNC_DIGITAL_UPLOAD_TABLE = "DIGITAL";
    public static final String E_SYNC_FINALIZE_TABLE = "FINALIZE";
    public static final String E_SYNC_STYLE = "STYLE";
    public static final String E_SYNC_PKG_APPEARANCE = "PACKAGING APPEARANCE";
    public static final String E_SYNC_ON_SITE = "ON SITE";
    public static final String E_SYNC_SAMPLE_COLLECTED = "SAMPLE COLLECTED";


    public static final int E_SYNC_PENDING_STATUS = 0;
    public static final int E_SYNC_IN_PROCESS_STATUS = 1;
    public static final int E_SYNC_SUCCESS_STATUS = 2;
    public static final int E_SYNC_FAILED_STATUS = 3;


    //    menu
    public static final int E_MENU_PO_VIEW_TYPE_HEADER = 1;
    public static final int E_MENU_PO_VIEW_TYPE_DTL = 2;
    public static final int E_MENU_PO_VIEW_TYPE_PARAMETER_QUALITY = 3;
    public static final int E_MENU_PO_VIEW_TYPE_PRODUCTION_STATUS = 4;
    public static final int E_MENU_PO_VIEW_TYPE_ENCLOSURE = 5;

    public static final int E_DOWNLOAD_TEST_REPORT = 1;
    public static final int E_DOWNLOAD_ENCLOSURE = 2;

    public static final int E_ADAPTOR_VIEW_TYPE_QUALITY_PARAMETER = 1;
    public static final int E_ADAPTOR_VIEW_TYPE_INTERNAL = 2;

    public static final int E_INSPECTION_REPORT_LEVEL = 1;
    public static final int E_INSPECTION_MATERIAL_LEVEL = 2;

    public static final String E_OVER_ALL_FAIL_RESULT = "DEL0002717";
    public static final String E_OVER_ALL_HOLD_RESULT = "DEL0002773";
    public static final String E_OVER_ALL_DESC_RESULT = "DEL0002770";

    public static final int VIEW_TYPE_HISTORY = 2;
    public static final int VIEW_TYPE_NORMAL = 1;

    public static final int E_VIEW_TYPE_INSPECTION = 1;
    public static final int E_VIEW_TYPE_SYNC = 2;
    public static final int E_VIEW_TYPE_HOLOGRAM_STYLE = 3;
    public static final int E_VIEW_TYPE_STYLE_LIST = 4;


    public static final int E_USER_TYPE_ADMIN = 1;
    public static final int E_USER_TYPE_QR = 2;
    public static final int E_USER_TYPE_MR = 3;

    public static final int E_SEND_FILE_IMAGE_STYLE = 2;
    public static final int E_SEND_FILE_IMAGE_INSPECTION = 0;
    public static final int E_SEND_FILE_IMAGE_ENCLOSURE = 1;

}
