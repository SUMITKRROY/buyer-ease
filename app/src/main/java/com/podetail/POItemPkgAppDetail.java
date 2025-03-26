package com.podetail;

import java.util.ArrayList;

/**
 * Created by ADMIN on 12/21/2017.
 */

public class POItemPkgAppDetail {

    public String pRowID;
    public String LocID;
    public String QRHdrID;
    public String QRPOItemHdrID;
    public String QRPOItemDtlID;
    public String ItemID;
    public String DescrID;
    public String SampleSizeID;
    public String SampleSizeValue;
    public String InspectionResultID;
    public String recUser;
/*
add by Sumit Roy on 26/03/2025
 */
    // Attachment lists for different packaging types
    public ArrayList<String> unitPkgAppAttachmentList;
    public ArrayList<String> shippingPkgAppAttachmentList;
    public ArrayList<String> innerPkgAppAttachmentList;
    public ArrayList<String> masterPkgAppAttachmentList;
    public ArrayList<String> palletPkgAppAttachmentList;

    public POItemPkgAppDetail() {
        // Initialize attachment lists
        unitPkgAppAttachmentList = new ArrayList<>();
        shippingPkgAppAttachmentList = new ArrayList<>();
        innerPkgAppAttachmentList = new ArrayList<>();
        masterPkgAppAttachmentList = new ArrayList<>();
        palletPkgAppAttachmentList = new ArrayList<>();
    }
}
