package com.buyereasefsl;

import java.util.ArrayList;

/**
 * Created by ADMIN on 12/25/2017.
 */

public class ItemMeasurementModal {


    String pRowID;
    String QRHdrID;
    String QRPOItemHdrID;
    double Dim_Height;
    double Dim_length;
    double Dim_Width;
    String SampleSizeValue;
    String SampleSizeID;
    String InspectionResultID;
    String Tolerance_Range;
    String ItemMeasurementDescr;
    String Digitals;

    double OLD_Height, OLD_Width, OLD_Length;

    String pRowIDForFinding;
    String Activity, InspectionDate;
    ArrayList<DigitalsUploadModal> listImages = new ArrayList<>();
}
