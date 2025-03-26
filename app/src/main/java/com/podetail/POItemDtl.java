package com.podetail;

import java.util.ArrayList;

/**
 * Created by ADMIN on 11/15/2017.
 */

public class POItemDtl {

    //header

    public String POhdr;
    public String Itemhdr;
    String Orderhdr;
    String InspectedTillDatehdr;
    String Availablehdr;
    String Acceptedhdr;
    String Shorthdr;
    String ShortStockQtyhdr;
    String InspectLaterHr;
    ;
    String workmanshipToInspectionhdr;
    String Inspectedhdr;
    String Criticalhdr;
    String Majorhdr;
    String Minorhdr;
    String cartonPackeddr;
    String cartonAvailable;
    String cartonToInspectedhdr;
    String packagingMeasurementhdr;
    String barCodehdr;
    String digitalUploadedhdr;
    String enclosuresUploadedhdr;
    String taskReportshdr;
    String measurementshdr;
    String SamplePurposehdr;
    String OverallInspectionResulthdr;
    String HologramNohdr;

    //column in po item hrd
    public String pRowID;
    //SampleCodeID both in table
    public int AllowedinspectionQty;
    public String SampleSizeInspection;
    public int InspectedQty;
    public int CriticalDefectsAllowed;
    public int MajorDefectsAllowed;
    public int MinorDefectsAllowed;
    public int CartonsPacked2;
    public int AllowedCartonInspection;//AllowedCartonInspection
    public int CartonsPacked;
    public int CartonsInspected;



    //   column in po item dtl
    // public String pRowID;
    public String QrItemID;
    public String QRHdrID;
    public String QRPOItemHdrID;
    public String POItemDtlRowID;
    public String SampleCodeID;
    public String LatestDelDt;//added by shekhar
    public int AvailableQty;
    public int AcceptedQty;
    public int FurtherInspectionReqd;
    public int Short;
    public int ShortStockQty;

    public int CriticalDefect;
    public int MajorDefect;
    public int MinorDefect;
    public int recDirty;
    public String PONO;
    public String ItemDescr;
    public String OrderQty;
    public int EarlierInspected;
    public String CustomerItemRef;
    public String HologramNo;

    public int POMasterPackQty;

    public String OPDescr;
    public double OPL;
    public double OPh;
    public double OPW;
    public double OPWt;
    public double OPCBM;
    public int OPQty;
    public String IPDimn;
    public double IPL;
    public double IPh;
    public double IPW;
    public double IPWt;
    public double IPCBM;
    public int IPQty;
    public String PalletDimn;
    public double PalletL;
    public double Palleth;
    public double PalletW;
    public double PalletWt;
    public double PalletCBM;
    public int PalletQty;
    public String IDimn;
    public int mapCountUnit;
    public int mapCountInner;
    public int mapCountMaster;
    public int mapCountPallet;
    public double UnitL;
    public double Unith;
    public double UnitW;
    public int Weight;
    public double CBM;
    public double RetailPrice;

    public String PKG_Me_InspectionResultID,
            PKG_Me_Master_InspectionResultID,
            PKG_Me_Pallet_InspectionResultID,
            PKG_Me_Unit_InspectionResultID,
            PKG_Me_Inner_InspectionResultID, PKG_Me_Remark,OnSiteTest_Remark,Qty_Remark;
//PKG_App_Unit_SampleSizeValue,PKG_App_shippingMark_SampleSizeValue,PKG_App_Inner_InspectionResultID// missing keys
    public String PKG_App_InspectionLevelID,PKG_App_InspectionResultID,
           PKG_App_Pallet_InspectionResultID, PKG_App_Pallet_SampleSizeID,PKG_App_Pallet_SampleSizeValue,
           PKG_App_Master_SampleSizeID,PKG_App_Master_SampleSizeValue,PKG_App_Master_InspectionResultID,
           PKG_App_Inner_SampleSizeID,PKG_App_Inner_SampleSizeValue,
           PKG_App_Unit_SampleSizeID,PKG_App_Unit_InspectionResultID,
            PKG_App_shippingMark_SampleSizeId,PKG_App_ShippingMark_InspectionResultID,
            PKG_App_Remark;
    public String OnSiteTest_InspectionResultID;

    public String WorkmanShip_InspectionResultID, WorkmanShip_Remark,
            ItemMeasurement_InspectionResultID,Overall_InspectionResultID, ItemMeasurement_Remark;


    public ArrayList<String> unitPackingAttachmentList = new ArrayList<>();
    public ArrayList<String> innerPackingAttachmentList = new ArrayList<>();
    public ArrayList<String> masterPackingAttachmentList = new ArrayList<>();
    public ArrayList<String> palletPackingAttachmentList = new ArrayList<>();

    public double PKG_Me_Inner_FindingL,
            PKG_Me_Inner_FindingB,
            PKG_Me_Inner_FindingH,
            PKG_Me_Inner_FindingWt,

    PKG_Me_Inner_FindingQty;
    public double PKG_Me_Unit_FindingL,
            PKG_Me_Unit_FindingB,
            PKG_Me_Unit_FindingH,
            PKG_Me_Unit_FindingWt,

    PKG_Me_Unit_FindingQty;

    public double PKG_Me_Pallet_FindingL,
            PKG_Me_Pallet_FindingB,
            PKG_Me_Pallet_FindingH,
            PKG_Me_Pallet_FindingWt,

    PKG_Me_Pallet_FindingQty;
    public double PKG_Me_Master_FindingL,
            PKG_Me_Master_FindingB,
            PKG_Me_Master_FindingH,
            PKG_Me_Master_FindingWt,

    PKG_Me_Master_FindingQty;
    public String PKG_Me_Master_SampleSizeID,
            PKG_Me_Pallet_SampleSizeID,
            PKG_Me_Unit_SampleSizeID,
            PKG_Me_Inner_SampleSizeID;
    //comment by shekhar
    /*public String PKG_Me_Master_FindingCBM = null,
            PKG_Me_Pallet_FindingCBM=null,
            PKG_Me_Unit_FindingCBM=null,
            PKG_Me_Inner_FindingCBM=null;*/
    public double PKG_Me_Master_FindingCBM ,
            PKG_Me_Pallet_FindingCBM,
            PKG_Me_Unit_FindingCBM,
            PKG_Me_Inner_FindingCBM;

    public int IsQuantityContainerVisible;
    public int IsWorkmanshipContainerVisible;
    public int IscartonDetailsContainerVisible;
    public int IsMoreDetailsContainerVisible;


    public String SampleSizeDescr;
    public String ItemID;

    public String QRItemBaseMaterialID;
    public String QRItemBaseMaterial_AddOnInfo;

    public String Barcode_InspectionResult;
    public String OnSiteTest_InspectionResult;
    public String ItemMeasurement_InspectionResult;
    public String WorkmanShip_InspectionResult;
    public String Overall_InspectionResult;
    public String PKG_Me_InspectionResult;

    public int Digitals;
    public int EnclCount;
    public int IsSelected;
    public int SizeBreakUP;
    public String ShipToBreakUP;
    public int testReportStatus;
    public int DuplicateFlag;

    public int IsHologramExpired;
    public int IsImportant;
    public int ItemRepeat;

    public String PKG_Me_Pallet_Digitals, PKG_Me_Master_Digitals, PKG_Me_Inner_Digitals, PKG_Me_Unit_Digitals;

    public String Barcode_InspectionLevelID;
    public String Barcode_InspectionResultID;
    public String Barcode_Remark;

    public String Barcode_Pallet_SampleSizeID;
    public int Barcode_Pallet_SampleSizeValue;
    public String Barcode_Pallet_Scan;
    public String Barcode_Pallet_Visual;
    public String Barcode_Pallet_InspectionResultID;
    public String Barcode_Master_SampleSizeID;
    public int Barcode_Master_SampleSizeValue;
    public String Barcode_Master_Visual;
    public String Barcode_Master_Scan;
    public String Barcode_Inner_SampleSizeID;
    public String Barcode_Master_InspectionResultID;
    public int Barcode_Inner_SampleSizeValue;
    public String Barcode_Inner_Visual;
    public String Barcode_Inner_Scan;
    public String Barcode_Inner_InspectionResultID;
    public String Barcode_Unit_SampleSizeID;
    public int Barcode_Unit_SampleSizeValue;
    public String Barcode_Unit_Visual;
    public String Barcode_Unit_Scan;
    public String Barcode_Unit_InspectionResultID;


}
