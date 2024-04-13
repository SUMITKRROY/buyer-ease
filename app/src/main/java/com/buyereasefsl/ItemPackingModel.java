package com.buyereasefsl;

import java.util.ArrayList;

/**
 * Created by ADMIN on 11/15/2017.
 */

public class ItemPackingModel {
    public String pRowID;
    public String ItemID, ItemMeasurement_InspectionResultID, AllowedInspectionQty,
            InspectedQty, CartonsPacked, AllowedCartonInspection, CartonsInspected,
            ItemMeasurement_Remark, WorkmanShip_InspectionResultID,
            Overall_InspectionResultID;
    public int CriticalDefectPieces, MajorDefectPieces, MinorDefectPieces,
            CriticalDefectsAllowed, MajorDefectsAllowed, MinorDefectsAllowed;
    String WorkmanShip_Remark;
    public int AvailableQty, CriticalDefect;
    String PKG_Me_InspectionResultID, PKG_Me_InspectionLevelID, PKG_Me_Remark;
    String PKG_Me_Pallet_InspectionResultID, PKG_Me_Pallet_SampleSizeID, PKG_Me_Pallet_Digitals;
    String PKG_Me_Master_InspectionResultID, PKG_Me_Master_SampleSizeID, PKG_Me_Master_Digitals;
    String PKG_Me_Inner_InspectionResultID, PKG_Me_Inner_SampleSizeID, PKG_Me_Inner_Digitals;
    String PKG_Me_Unit_InspectionResultID, PKG_Me_Unit_SampleSizeID, PKG_Me_Unit_Digitals;

    public int PKG_Me_Pallet_SpecificationL, PKG_Me_Pallet_SpecificationB, PKG_Me_Pallet_SpecificationH, PKG_Me_Pallet_SpecificationWt, PKG_Me_Pallet_SpecificationCBM, PKG_Me_Pallet_SpecificationQty;
    public int PKG_Me_Pallet_FindingL,
            PKG_Me_Pallet_FindingB,
            PKG_Me_Pallet_FindingH,
            PKG_Me_Pallet_FindingWt,
            PKG_Me_Pallet_FindingCBM,
            PKG_Me_Pallet_FindingQty;

    public String PKG_Me_Unit_SpecificationL,
            PKG_Me_Unit_SpecificationB,
            PKG_Me_Unit_SpecificationH,
            PKG_Me_Unit_SpecificationWt,
            PKG_Me_Unit_SpecificationCBM,
            PKG_Me_Unit_SpecificationQty;
    public String UnitPackingFindingL,
            UnitPackingFindingB,
            UnitPackingFindingH,
            UnitPackingFindingWt,
            UnitPackingFindingCBM,
            UnitPackingFindingQuantity;
    ArrayList<String> unitPackingAttachmentList = new ArrayList<>();
    String _UnitPackingOverAllResult;
    public String PKG_Me_Inner_SpecificationL,
            PKG_Me_Inner_SpecificationB,
            PKG_Me_Inner_SpecificationH,
            PKG_Me_Inner_SpecificationWt,
            PKG_Me_Inner_SpecificationCBM,
            PKG_Me_Inner_SpecificationQty;
    public String InnerPackingFindingL,
            InnerPackingFindingB,
            InnerPackingFindingH,
            InnerPackingFindingWt,
            InnerPackingFindingCBM,
            InnerPackingFindingQuantity;
    ArrayList<String> innerPackingAttachmentList = new ArrayList<>();
    String _InnerPackingOverAllResult;
    public String PKG_Me_Master_SpecificationL,
            PKG_Me_Master_SpecificationB,
            PKG_Me_Master_SpecificationH,
            PKG_Me_Master_SpecificationWt,
            PKG_Me_Master_SpecificationCBM,
            PKG_Me_Master_SpecificationQty;
    public int PKG_Me_Master_FindingL,
            PKG_Me_Master_FindingB,
            PKG_Me_Master_FindingH,
            PKG_Me_Master_FindingWt,
            PKG_Me_Master_FindingCBM,
            PKG_Me_Master_FindingQty;

    ArrayList<String> masterPackingAttachmentList = new ArrayList<>();
    String _MasterPackingOverAllResult;
    public int IsInspectionDetailContainer;
    public int IsPackingMeasurementDetail;
    public int IsWorkmanshipVisualInspectionDetail;


}
