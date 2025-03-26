package com.buyereasefsl;

import java.util.ArrayList;

/**
 * Created by ADMIN on 11/16/2017.
 */

public class WorkManShipModel {

    public String pRowID;

    String Code;
    String Description;
    int Critical;
    int Major;
    int Minor;
    String Comments;
    String Digitals;
    String Activity;
    String InspectionDate;
    String InspectionId;
    ArrayList<DigitalsUploadModal> listImages = new ArrayList<>();
}
