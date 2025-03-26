package com.database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


/**
 * Created by ADMIN on 8/10/2017.
 */


public class DBHelper extends SQLiteOpenHelper {
  /*  private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "OffLineInspectionDB.db";



    final String CREATE_TABLE_USER_MASTER = "CREATE TABLE UserMaster ( " +
            " pUserID TEXT, " +
            " ParentID TEXT, " +
            " LoginName TEXT, " +
            " Password TEXT, " +
            " UserName TEXT, " +
            " Email TEXT, " +
            " fCommunityID TEXT, " +
            " fContactID TEXT, " +
            " IsAdmin INTEGER, " +
            " LocId TEXT, " +
            " Desg TEXT, " +
            " Address TEXT, " +
            " City TEXT, " +
            " Zip TEXT, " +
            " State TEXT, " +
            " CountryID TEXT, " +
            " PhoneNo1 TEXT, " +
            " PhoneNo2 TEXT, " +
            " PhoneNo3 TEXT, " +
            " Fax TEXT, " +
            " WebURL TEXT, " +
            " PageSize TEXT, " +
            " DateFormat INTEGER, " +
            " HomePOGraph INTEGER, " +
            " HomePOGraphXValue TEXT, " +
            " HomePOGraphYValue TEXT, " +
            " SRView INTEGER, " +
            " POView INTEGER, " +
            " POView12 INTEGER, " +
            " QRView INTEGER, " +
            " InvoiceView INTEGER, " +
            " VesselView INTEGER, " +
            " ColumnPickerView INTEGER, " +
            " ApprovalPassword TEXT, " +
            " BirthDt TEXT, " +
            " AnniversaryDt TEXT, " +
            " JoiningDt TEXT, " +
            " recEnable INTEGER, " +
            " IsValidated INTEGER, " +
            " Last_Sync_Dt TEXT DEFAULT '1900-01-01 00:00:00', " +
            " CriticalDefectsAllowed INTEGER DEFAULT 0, " +
            " CompanyName TEXT, " +
            " QualityReportConfig Text, " +
            " PRIMARY KEY(pUserID) " +
            ");";

    final String CREATE_QR_FEEDBACK_HDR_TABLE = "CREATE TABLE QRFeedbackhdr  ( " +
            " pRowID [char], " +
            " LocID [char], " +
            " ActivityID [char], " +
            " CustomerID [char], " +
            " VendorID [char], " +
            " CompanyID [char], " +
            " QRID [char], " +
            " POType [varchar], " +
            " InspectorID [char], " +
            " InspectionDt Text, " +
            " VendorAddress TEXT, " +
            " VendorContact [varchar], " +
            " InspectionLevel [char], " +
            " QLMajor [char], " +
            " QLMinor [char], " +
            " SampleCodeID [char], " +
            " AvailableQty Real DEFAULT 0, " +
            " AllowedInspectionQty Real DEFAULT 0, " +
            " InspectedQty Real DEFAULT 0, " +
            " AcceptedQty Real DEFAULT 0, " +
            " ShortStockQty Real DEFAULT 0, " +
            " CartonsPacked [int] DEFAULT 0, " +
            " AllowedCartonInspection [int] DEFAULT 0, " +
            " CartonsInspected [int] DEFAULT 0, " +
            " CritialDefectsAllowed [int] DEFAULT 0, " +
            " MajorDefectsAllowed [int] DEFAULT 0, " +
            " MinorDefectsAllowed [int] DEFAULT 0, " +
            " CriticalDefect [int] DEFAULT 0, " +
            " MajorDefect [int] DEFAULT 0, " +
            " MinorDefect [int] DEFAULT 0, " +
            " Status [char], " +
            " StatusComments [varchar], " +
            " ReinspectionDt Text, " +
            " POQAStatusID [char], " +
            " POQAStatusChangeID [char], " +
            " POQAStatusChangeDescr [varchar], " +
            " QRParamFormat [int] DEFAULT 0, " +
            " AQLFormula [tinyint] DEFAULT 0, " +
            " AutoFillQty [tinyint] DEFAULT 1, " +
            " AutoFillAcceptedQty [tinyint] DEFAULT 1, " +
            " AutoFillCarton [tinyint] DEFAULT 1, " +
            " DefectListType [tinyint] DEFAULT 0, " +
            " RptType [tinyint] DEFAULT 0, " +
            " RptFormat [tinyint] DEFAULT 0, " +
            " RptDefectFormat [tinyint] DEFAULT 0, " +
            " TypeofInspection [char], " +
            " InspectionPlan [char], " +
            " SamplingPlan [char], " +
            " InspectionMode [smallint] DEFAULT 0, " +
            " InspectionVersion [smallint] DEFAULT 0, " +
            " VendorAcceptanceOn Text, " +
            " VendorAcceptanceComment [varchar], " +
            " VendorAcceptanceIntimation Text, " +
            " VendorAcceptancePrintFormate [varchar], " +
            " VendorAcceptanceHostAddress [varchar], " +
            " VendorAppectanceByID [char], " +
            " VendorAppectanceByName [varchar], " +
            " VendorAppectanceByEmail [varchar], " +
            " ArrivalTime [varchar], " +
            " InspStartTime [varchar], " +
            " CompleteTime [varchar], " +
            " InvoiceNo [varchar], " +
            " NextActivityID [char], " +
            " NextActivityPlanDate Text, " +
            " recApproveDt Text, " +
            " recApproveUser [char], " +
            " recAddDt Text, " +
            " recEnable [tinyint] DEFAULT 1, " +
            " recDirty [tinyint] DEFAULT 1, " +
            " recAddUser [char], " +
            " recUser [char], " +
            " recDt Text, " +
            " ediDt Text, " +
            " Customer TEXT, " +
            " Vendor TEXT, " +
            " Inspector TEXT, " +
            " TypeofInspectionDescr TEXT, " +
            " InspectionLevelDescr TEXT, " +
            " QLMajorDescr TEXT, " +
            " QLMinorDescr TEXT, " +
            " QR TEXT, " +
            " Activity TEXT, " +
            " SampleCodeDescr TEXT, " +
            " FactoryAddress TEXT, " +
            " AcceptedDt TEXT, " +
            " Factory TEXT, " +
            " Comments TEXT, " +
            " Last_Sync_Dt TEXT, " +
            " ProductionCompletionDt Text, " +
            " ProductionStatusRemark Text, " +
            " PRIMARY KEY(pRowID) " +
            ");";

    final String CREATE_QR_PO_ITEM_HDR_TABLE = "CREATE TABLE QRPOItemHdr ( " +
            " pRowID Text, " +
            " LocID Text, " +
            " QRHdrID Text, " +
            " ItemID Text, " +
            " BaseMaterialID Text, " +
            " BaseMaterial_AddOnInfo [varchar], " +
            " ColorID Text, " +
            " AllowedInspectionQty Real DEFAULT 0, " +
            " InspectedQty Real DEFAULT 0, " +
            " ShortStockQty Real DEFAULT 0, " +
            " CartonsPacked [int] DEFAULT 0, " +
            " AllowedCartonInspection [int] DEFAULT 0, " +
            " CartonsInspected [int] DEFAULT 0, " +
            " DefaultImageRowID Text, " +
            " Qty_Remark [varchar], " +
            " PKG_App_InspectionLevelID Text, " +
            " PKG_App_InspectionResultID Text, " +
            " PKG_App_Remark [varchar], " +
            " PKG_App_Pallet_SampleSizeID Text, " +
            " PKG_App_Pallet_SampleSizeValue [int], " +
            " PKG_App_Pallet_InspectionResultID Text, " +
            " PKG_App_Master_SampleSizeID Text, " +
            " PKG_App_Master_SampleSizeValue [int], " +
            " PKG_App_Master_InspectionResultID Text, " +
            " PKG_App_Inner_SampleSizeID Text, " +
            " PKG_App_Inner_SampleSizeValue [int], " +
            " PKG_App_Unit_SampleSizeID Text, " +
            " PKG_App_Unit_InspectionResultID Text, " +
            " PKG_App_ShippingMark_InspectionResultID Text, " +
            " PKG_Me_Remark [varchar], " +
            " PKG_Me_Pallet_SampleSizeID Text, " +
            " PKG_Me_Pallet_SampleSizeValue [int], " +
            " PKG_Me_Pallet_FindingL Real, " +
            " PKG_Me_Pallet_FindingH Real, " +
            " PKG_Me_Pallet_FindingWt Real, " +
            " PKG_Me_Pallet_FindingCBM Real, " +
            " PKG_Me_Pallet_FindingQty Real, " +
            " PKG_Me_Master_SampleSizeID Text, " +
            " PKG_Me_Master_SampleSizeValue [int], " +
            " PKG_Me_Master_FindingL Real, " +
            " PKG_Me_Master_FindingB Real, " +
            " PKG_Me_Master_FindingH Real, " +
            " PKG_Me_Master_FindingWt Real, " +
            " PKG_Me_Master_FindingCBM Real, " +
            " PKG_Me_Master_FindingQty Real, " +
            " PKG_Me_Master_InspectionResultID Text, " +
            " PKG_Me_Inner_SampleSizeID Text, " +
            " PKG_Me_Inner_SampleSizeValue [int], " +
            " PKG_Me_Inner_FindingL Real, " +
            " PKG_Me_Inner_FindingB Real, " +
            " PKG_Me_Inner_FindingH Real, " +
            " PKG_Me_Inner_FindingWt Real, " +
            " PKG_Me_Inner_FindingCBM Real, " +
            " PKG_Me_Inner_FindingQty Real, " +
            " PKG_Me_Inner_InspectionResultID Text, " +
            " PKG_Me_Unit_SampleSizeID Text, " +
            " PKG_Me_Unit_SampleSizeValue [int], " +
            " PKG_Me_Unit_FindingL Real, " +
            " PKG_Me_Unit_FindingB Real, " +
            " PKG_Me_Unit_FindingH Real, " +
            " PKG_Me_Unit_FindingWt Real,  " +
            " PKG_Me_Unit_FindingCBM Real, " +
            " PKG_Me_Unit_InspectionResultID Text, " +
            " Barcode_InspectionLevelID Text, " +
            " Barcode_InspectionResultID Text, " +
            " Barcode_Remark [varchar], " +
            " Barcode_Pallet_SampleSizeID Text, " +
            " Barcode_Pallet_SampleSizeValue [int], " +
            " Barcode_Pallet_Visual [varchar], " +
            " Barcode_Pallet_Scan [varchar], " +
            " Barcode_Pallet_InspectionResultID Text, " +
            " Barcode_Master_SampleSizeID Text, " +
            " Barcode_Master_SampleSizeValue [int], " +
            " Barcode_Master_Visual [varchar], " +
            " Barcode_Master_Scan [varchar], " +
            " Barcode_Master_InspectionResultID Text, " +
            " Barcode_Inner_SampleSizeID Text, " +
            " Barcode_Inner_SampleSizeValue [int], " +
            " Barcode_Inner_Visual [varchar], " +
            " Barcode_Inner_Scan [varchar], " +
            " Barcode_Inner_InspectionResultID Text, " +
            " Barcode_Unit_SampleSizeID Text, " +
            " Barcode_Unit_SampleSizeValue [int], " +
            " Barcode_Unit_Visual [varchar], " +
            " Barcode_Unit_Scan [varchar], " +
            " Barcode_Unit_InspectionResultID Text, " +
            " OnSiteTest_IsApplicable [tinyint], " +
            " OnSiteTest_InspectionResultID Text, " +
            " OnSiteTest_Remark [varchar], " +
            " WorkmanShip_QRInspectionLevel Text, " +
            "  WorkmanShip_SampleSizeID Text, " +
            " WorkmanShip_QLMajor Text, " +
            " WorkmanShip_QLMinor Text, " +
            " WorkmanShip_InspectionResultID Text, " +
            " WorkmanShip_Remark [varchar], " +
            " CriticalDefectsAllowed [int] DEFAULT 0, " +
            " MajorDefectsAllowed [int] DEFAULT 0, " +
            " MinorDefectsAllowed [int] DEFAULT 0, " +
            " CriticalDefect [int] DEFAULT 0, " +
            " MajorDefect [int] DEFAULT 0, " +
            " MinorDefect [int] DEFAULT 0, " +
            " CriticalDefectPieces [int] DEFAULT 0, " +
            " MajorDefectPieces [int] DEFAULT 0, " +
            " MinorDefectPieces [int] DEFAULT 0, " +
            " ItemMeasurement_InspectionResultID Text, " +
            " ItemMeasurement_Remark [varchar], " +
            " ProductSpecification_InspectionResultID Text, " +
            " ProductSpecification_Remark [varchar], " +
            " FabricInspection_InspectionResultID Text, " +
            " FabricInspection_Remark [varchar], " +
            " FabricInspectionInHouseQty [int] DEFAULT 0, " +
            " FabricInspectionDt Text, " +
            " Overall_InspectionResultID Text, " +
            " SampleCodeID Text, " +
            " recDirty [smallint] DEFAULT 1, " +
            " recEnable [smallint] DEFAULT 1, " +
            " recAddDt Text,  " +
            " recDt Text, " +
            " recUser Text, " +
            "  FabricInspectionDefectList_InspectionResultID Text, " +
            " FabricInspectionDefectList_Remark [varchar], " +
            " FabricInspectionDefectListDt Text, " +
            " TestReportStatus INTEGER DEFAULT 0, " +
            " PKG_Me_InspectionResultID TEXT, " +
            " PKG_Me_Pallet_Digitals Text, " +
            " PKG_Me_Master_Digitals Text, " +
            " PKG_Me_Inner_Digitals Text, " +
            " PKG_Me_Unit_Digitals Text, " +
            " PKG_Me_Pallet_InspectionResultID Text, " +
            " PKG_Me_Pallet_FindingB Numeric, " +
            " PKG_Me_InspectionLevelID Text, " +
            " BaseMatDescr Text, " +
            " PRIMARY KEY(pRowID) " +
            ");";


    final String CREATE_QR_PO_ITEM_Dtl_TABLE = "CREATE TABLE QRPOItemdtl ( " +
            " pRowID [char] NOT NULL, " +
            " LocID [char], " +
            " QRHdrID [char], " +
            " QRPOItemHdrID [char], " +
            " POItemDtlRowID [char], " +
            " SampleRqstHdrlRowID [char], " +
            " QualityDefectHdrID [char], " +
            " BaseMaterialID [char], " +
            " BaseMaterial_AddOnInfo [varchar], " +
            " POTnARowID [char], " +
            " SampleCodeID [char], " +
            " AvailableQty Real DEFAULT 0, " +
            " AllowedinspectionQty Real DEFAULT 0, " +
            " InspectedQty Real DEFAULT 0, " +
            " AcceptedQty Real DEFAULT 0, " +
            " FurtherInspectionReqd [tinyint] DEFAULT 0, " +
            " ShortStockQty Real DEFAULT 0, " +
            " PalletPackedQty [int] DEFAULT 0, " +
            " MasterPackedQty [int] DEFAULT 0, " +
            " InnerPackedQty [int] DEFAULT 0, " +
            " PackedQty Real DEFAULT 0, " +
            " UnpackedQty Real DEFAULT 0, " +
            " UnfinishedQty Real DEFAULT 0, " +
            " CartonsPacked [int] DEFAULT 0, " +
            " CartonsPacked2 [int] DEFAULT 0, " +
            " AllowedCartonInspection [int] DEFAULT 0, " +
            " CartonsInspected [int] DEFAULT 0, " +
            " CriticalDefectsAllowed [int] DEFAULT 0, " +
            " MajorDefectsAllowed [int] DEFAULT 0, " +
            " MinorDefectsAllowed [int] DEFAULT 0, " +
            " CriticalDefect [int] DEFAULT 0, " +
            " MajorDefect [int] DEFAULT 0, " +
            " MinorDefect [int] DEFAULT 0, " +
            " recAddUser [char], " +
            " recAddDt Text, " +
            " recEnable [tinyint] DEFAULT 1, " +
            " recDirty [tinyint] DEFAULT 1, " +
            " recUser [char], " +
            " recDt Text, " +
            " ediDt Text, " +
            " PONO TEXT, " +
            " BuyerPODt TEXT, " +
            " ItemDescr TEXT, " +
            " OrderQty TEXT DEFAULT 0, " +
            " EarlierInspected INTEGER DEFAULT 0, " +
            " POMasterPackQty INTEGER DEFAULT 0, " +
            " MR TEXT, " +
            " LR TEXT, " +
            " CustomerDepartment TEXT, " +
            " CustomerItemRef TEXT, " +
            " LatestDelDt TEXT, " +
            " HologramNo TEXT, " +
            " Hologram_ExpiryDt TEXT, " +
            " OPDescr Text, " +
            " OPWt Numeric, " +
            " OPCBM Numeric, " +
            " OPQty Numeric, " +
            " IPDimn Text, " +
            " IPWt Numeric, " +
            " IPCBM Numeric, " +
            " IPQty Numeric, " +
            " PalletDimn Text, " +
            " PalletWt Numeric, " +
            " PalletCBM Numeric, " +
            " PalletQty Numeric, " +
            " IDimn Text, " +
            " Weight Numeric, " +
            " CBM Numeric, " +
            " PRIMARY KEY(pRowID) " +
            ");";

    final String CREATE_QRAuditBatchDetails = "CREATE TABLE QRAuditBatchDetails ( " +
            " pRowID [char], " +
            " LocID [char](3), " +
            " QRHdrID [char], " +
            " QRPOItemHdrID [char], " +
            " QRPOItemDtlRowID [char], " +
            " ItemID [char], " +
            " ColorID [char], " +
            " DefectID [char], " +
            " DefectCode [varchar], " +
            " DefectName [varchar], " +
            " DCLTypeID [char], " +
            " DefectComments [varchar], " +
            " DefectDescription [varchar], " +
            " CriticalDefect [int] DEFAULT 0, " +
            " MajorDefect [int] DEFAULT 0, " +
            " MinorDefect [int] DEFAULT 0, " +
            " CriticalType [tinyint] DEFAULT 0, " +
            " MajorType [tinyint] DEFAULT 0, " +
            " MinorType [tinyint] DEFAULT 0, " +
            " SampleRqstCriticalRowID [char], " +
            " POItemHdrCriticalRowID [char], " +
            " Digitals [varchar], " +
            " recAdddt Text, " +
            " recDt Text, " +
            " recAddUser [char], " +
            " recUser [char], " +
            " BE_pRowID TEXT, " +
            " DigitalDsc TEXT, " +
            " recEnable INTEGER DEFAULT 1, " +
            " PRIMARY KEY(pRowID) " +
            ");";

    final String CREATE_QREnclosure = "CREATE TABLE QREnclosure ( " +
            " pRowID TEXT, " +
            " LocID TEXT, " +
            " IsMandatory INTEGER DEFAULT 0, " +
            " ContextID TEXT, " +
            " ContextType TEXT DEFAULT 71, " +
            " EnclType TEXT, " +
            " ImageName TEXT, " +
            " ImageExtn TEXT, " +
            " Title TEXT, " +
            " FileName TEXT, " +
            " FileDate TEXT, " +
            " Approve INTEGER, " +
            " ApproveDate TEXT, " +
            " ApprovedBy TEXT, " +
            " recDirty INTEGER DEFAULT 1, " +
            " recEnable INTEGER DEFAULT 1, " +
            " recUser TEXT, " +
            " recAddDt TEXT, " +
            " recDt TEXT, " +
            " ediDt TEXT, " +
            " numVal1 INTEGER DEFAULT 0, " +
            " FileSent INTEGER DEFAULT 0, " +
            " PRIMARY KEY(pRowID) " +
            ");";

    final String CREATE_QRInspectionHistory = "CREATE TABLE QRInspectionHistory ( " +
            " QRHdrID TEXT, " +
            " InspectionDt TEXT, " +
            " Status TEXT, " +
            " StatusDs TEXT, " +
            " ActivityDs TEXT, " +
            " QR TEXT, " +
            " Inspector TEXT, " +
            " QRPOItemHdrID TEXT, " +
            " RefQRHdrID TEXT, " +
            " RefQRPOItemHdrID TEXT " +
            ");";

    final String CREATE_QRPOIntimationDetails = "CREATE TABLE QRPOIntimationDetails ( " +
            " pRowID TEXT, " +
            " LocID TEXT, " +
            " QRHdrID TEXT, " +
            " Name TEXT, " +
            " EmailID TEXT, " +
            " ID TEXT, " +
            " IsLink INTEGER DEFAULT 0, " +
            " IsReport INTEGER DEFAULT 0, " +
            " recType INTEGER DEFAULT 0, " +
            " recEnable INTEGER DEFAULT 1, " +
            " recAddUser TEXT, " +
            " recAddDt TEXT, " +
            " recUser TEXT DEFAULT '', " +
            " recDt TEXT, " +
            " EDIDt TEXT, " +
            " IsHtmlLink INTEGER DEFAULT 0, " +
            " IsRcvApplicable INTEGER DEFAULT 0, " +
            " BE_pRowID TEXT, " +
            " IsSelected INTEGER DEFAULT 0, " +
            " PRIMARY KEY(pRowID) " +
            ");";


    final String CREATE_QRPOItemDtl_Image = "CREATE TABLE  QRPOItemDtl_Image  ( " +
            " pRowID Text, " +
            " LocID Text, " +
            " QRHdrID Text, " +
            " QRPOItemHdrID Text, " +
            " QRPOItemDtlID Text, " +
            " ItemID Text, " +
            " ColorID Text, " +
            " Title Text, " +
            " ImageName Text, " +
            " ImageExtn Text, " +
            " ImageSymbol Text, " +
            " ImageSqn Integer DEFAULT 0, " +
            " recEnable Integer DEFAULT 1, " +
            " recUser Text, " +
            " recAddDt Text, " +
            " recDt Text, " +
            " BE_pRowID TEXT, " +
            " FileSent INTEGER DEFAULT 0, " +
            " PRIMARY KEY(pRowID) " +
            ");";


    final String CREATE_QRPOItemDtl_ItemMeasurement = "CREATE TABLE QRPOItemDtl_ItemMeasurement ( " +
            " pRowID [char], " +
            " LocID [char], " +
            " QRHdrID [char], " +
            " QRPOItemHdrID [char], " +
            " QRPOItemDtlID [char], " +
            " ItemID [char], " +
            " ColorID [char], " +
            " ItemMeasurementDescr TEXT, " +
            " SampleSizeID [char], " +
            " SampleSizeValue [int] DEFAULT 0, " +
            " Finding [varchar], " +
            " InspectionResultID [char], " +
            " recEnable [tinyint] DEFAULT 1, " +
            " recUser [char], " +
            " recAddDt Text, " +
            " recDt Text, " +
            " Dim_Height REAL DEFAULT 0, " +
            " Dim_Width REAL DEFAULT 0, " +
            " Dim_Length REAL DEFAULT 0, " +
            " Tolerance_Range TEXT, " +
            " BE_pRowID TEXT, " +
            " Digitals TEXT, " +
            " PRIMARY KEY(pRowID) " +
            ");";

    final String CREATE_QRFindings = "CREATE TABLE QRFindings ( " +
            " pRowID TEXT, " +
            " LocID TEXT, " +
            " QrHdrID TEXT, " +
            " QRPOItemHdrID TEXT, " +
            " ItemID TEXT, " +
            " Descr TEXT, " +
            " recEnable INTEGER DEFAULT 1, " +
            " recDt TEXT, " +
            " recUser TEXT, " +
            " SampleSizeID TEXT, " +
            " ChangeCount INTEGER DEFAULT 0, " +
            " OLD_Height REAL DEFAULT 0, " +
            " OLD_Width REAL DEFAULT 0, " +
            " OLD_Length REAL DEFAULT 0, " +
            " New_Height REAL DEFAULT 0, " +
            " New_Width REAL DEFAULT 0, " +
            " New_Length REAL DEFAULT 0, " +
            " MeasurementID TEXT, " +
            " PRIMARY KEY(pRowID) " +
            ");";

    final String CREATE_TestReport = "CREATE TABLE TestReport ( " +
            " pRowID TEXT, " +
            " AcitvityID TEXT, " +
            " ActivityDescr TEXT, " +
            " TestDt TEXT, " +
            " ValidUpToDt TEXT, " +
            " ReportName TEXT, " +
            " Remarks TEXT, " +
            " IsExpired INTEGER DEFAULT 0, " +
            " FileExtn TEXT, " +
            " FileIcon TEXT, " +
            " QRPOItemHdrID TEXT, " +
            " QRHdrID TEXT, " +
            " ImagePathID TEXT " +
            ");";

    final String CREATE_QRQualiltyParameterFields = "CREATE TABLE QRQualiltyParameterFields ( " +
            " pRowID [char], " +
            " LocID [char], " +
            " QRHdrID [char], " +
            " QRPOItemDtlID [char], " +
            " ItemID [char], " +
            " ColorID [char], " +
            " QualityParameterID [char], " +
            " IsApplicable [smallint] DEFAULT 0, " +
            " Remarks [nvarchar], " +
            " recAddUser [char], " +
            " recAddDt Text, " +
            " recEnable [tinyint] DEFAULT 1, " +
            " recDirty [tinyint] DEFAULT 1, " +
            " recUser [char], " +
            " recDt Text, " +
            " ediDt Text, " +
            " OptionSelected [smallint] DEFAULT 0, " +
            " QRPOItemHdrID [char], " +
            " Digitals TEXT " +
            ");";

    final String CREATE_QRPOItemFitnessCheck = "CREATE TABLE QRPOItemFitnessCheck ( " +
            " pRowID [char], " +
            " LocID [char], " +
            " QRHdrID [char], " +
            " QRPOItemDtlID [char], " +
            " ItemID [char], " +
            " ColorID [char], " +
            " QRFitnessCheckID [char], " +
            " IsApplicable [smallint] DEFAULT 0, " +
            " Remarks [nvarchar], " +
            " recAddUser [char], " +
            " recAddDt Text, " +
            " recEnable [tinyint] DEFAULT 1, " +
            " recDirty [tinyint] DEFAULT 1, " +
            " recUser [char], " +
            " recDt Text, " +
            " OptionSelected [smallint] DEFAULT 0, " +
            " QRPOItemHdrID [char], " +
            " Digitals Text, " +
            " PRIMARY KEY(pRowID) " +
            ");";


    final String CREATE_TABLE_SYSDATA22 = "CREATE TABLE Sysdata22 ( " +
            " GenID TEXT, " +
            " MasterName TEXT DEFAULT '', " +
            " MainID TEXT DEFAULT '', " +
            " MainDescr TEXT DEFAULT '', " +
            " SubID TEXT DEFAULT '', " +
            " SubDescr TEXT DEFAULT '', " +
            " numVal1 INTEGER DEFAULT 0, " +
            " numVal2 INTEGER DEFAULT 0, " +
            " AddonInfo TEXT DEFAULT '', " +
            " MoreInfo TEXT DEFAULT '', " +
            " Priviledge INTEGER DEFAULT 0, " +
            " a INTEGER DEFAULT 0, " +
            " ModuleAccess TEXT DEFAULT '', " +
            " ModuleID TEXT DEFAULT '' " +
            ");";

    final String CREATE_TABLE_GenMst = "CREATE TABLE GenMst ( " +
            " pGenRowID TEXT, " +
            " LocID Text, " +
            " GenID Text, " +
            " MainID TEXT, " +
            " SubID TEXT, " +
            " Abbrv Text, " +
            " MainDescr Text, " +
            " SubDescr Text, " +
            " numVal1 Real, " +
            " numVal2 Real, " +
            " numVal3 Real, " +
            " numVal4 Real, " +
            " numVal5 Real, " +
            " numVal6 Real, " +
            " numval7 Real, " +
            " chrVal1 Text, " +
            " chrVal2 Text, " +
            " chrVal3 TEXT, " +
            " dtVal1 Text, " +
            " ImageName Text, " +
            " ImageExtn Text, " +
            " recDirty Integer, " +
            " recEnable Integer, " +
            " recUser TEXT, " +
            " recAddDt TEXT, " +
            " recDt TEXT, " +
            " EDIDt TEXT, " +
            " IsPrivilege Integer, " +
            " Last_Sync_Dt TEXT DEFAULT '1900-01-01 00:00:00', " +
            " PRIMARY KEY(pGenRowID) " +
            ");";

    //Defect master
    final String CREATE_TABLE_DEFECT_APPLICABLE = "CREATE TABLE DefectMaster ( " +
            " pRowID TEXT, " +
            " LocID TEXT, " +
            " DefectName TEXT, " +
            " Code TEXT, " +
            " DCLTypeID TEXT, " +
            " InspectionStage TEXT, " +
            " chkCritical INTEGER, " +
            " chkMajor INTEGER, " +
            " chkMinor INTEGER, " +
            " recEnable INTEGER, " +
            " recAdddt TEXT, " +
            " recDt TEXT, " +
            " recAddUser TEXT, " +
            " recUser TEXT, " +
            " PRIMARY KEY(pRowID) " +
            ");";

    final String CREATE_InsplvlHdr = "CREATE TABLE InsplvlHdr ( " +
            " pRowID TEXT, " +
            " LocID TEXT, " +
            " InspDescr TEXT DEFAULT '', " +
            " InspAbbrv TEXT DEFAULT '', " +
            " recDirty INTEGER DEFAULT 1, " +
            " recEnable INTEGER DEFAULT 1, " +
            " recUser TEXT DEFAULT '', " +
            " recDt TEXT DEFAULT 'DateTime(''now'',''localhost'')', " +
            " IsDefault INTEGER DEFAULT 0, " +
            " PRIMARY KEY(pRowID) " +
            ");";

    final String CREATE_InspLvlDtl = "CREATE TABLE  InspLvlDtl ( " +
            " pRowID TEXT, " +
            " LocID TEXT, " +
            " InspHdrID TEXT DEFAULT '', " +
            " SignDescr TEXT DEFAULT '', " +
            " BatchSize INTEGER DEFAULT 0, " +
            " SampleCode TEXT, " +
            " recDirty INTEGER DEFAULT 1, " +
            " recEnable INTEGER DEFAULT 1, " +
            " recUser TEXT DEFAULT '', " +
            " recAddUser TEXT DEFAULT '', " +
            " recAddDt TEXT DEFAULT 'Datetime(''now'',''localtime'')', " +
            " recDt TEXT DEFAULT 'Datetime(''now'',''localtime'')', " +
            " PRIMARY KEY(pRowID) " +
            ");";

    final String CREATE_QualityLevel = "CREATE TABLE QualityLevel ( " +
            " pRowID TEXT, " +
            " LocID TEXT, " +
            " QualityLevel TEXT DEFAULT '', " +
            " recDirty INTEGER DEFAULT 1, " +
            " recEnable INTEGER DEFAULT 1, " +
            " recUser TEXT DEFAULT '', " +
            " recAddDt TEXT DEFAULT 'DateTime(''now'',''localtime'')', " +
            " recDt TEXT DEFAULT 'DateTime(''now'',''localtime'')', " +
            " PRIMARY KEY(pRowID) " +
            ");";

    final String CREATE_QualityLevelDtl = "CREATE TABLE QualityLevelDtl ( " +
            " pRowID TEXT, " +
            " LocID TEXT, " +
            " QlHdrID TEXT DEFAULT '', " +
            " SampleCode TEXT DEFAULT '', " +
            " Accepted INTEGER DEFAULT 0, " +
            " recDirty INTEGER DEFAULT 1, " +
            " recEnable INTEGER DEFAULT 1, " +
            " recUser TEXT DEFAULT '', " +
            " recAddDt TEXT DEFAULT 'DateTime(''now'',''localtime'')', " +
            " recDt TEXT DEFAULT 'DateTime(''now'',''localtime'')', " +
            " PRIMARY KEY(pRowID) " +
            ");";

    final String CREATE_Enclosures = "CREATE TABLE  Enclosures ( " +
            " QRPOItemDtlID TEXT, " +
            " QRPOItemHdrID TEXT, " +
            " QRHdrID TEXT, " +
            " ContextType TEXT DEFAULT '', " +
            " SeqNo INTEGER DEFAULT 1, " +
            " ContextID TEXT DEFAULT '', " +
            " ContextDs TEXT, " +
            " ContextDs1 TEXT, " +
            " ContextDs2 TEXT, " +
            " ContextDs2a TEXT, " +
            " ContextDs3 TEXT, " +
            " EnclType TEXT DEFAULT '', " +
            " EnclFileType TEXT, " +
            " EnclRowID TEXT, " +
            " Title TEXT DEFAULT '', " +
            " ImageName TEXT, " +
            " FileIcon TEXT, " +
            " ImagePathID TEXT DEFAULT '' " +
            ");";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub

        db.execSQL(CREATE_TABLE_USER_MASTER);
        db.execSQL(CREATE_QR_FEEDBACK_HDR_TABLE);
        db.execSQL(CREATE_QR_PO_ITEM_HDR_TABLE);
        db.execSQL(CREATE_QR_PO_ITEM_Dtl_TABLE);
        db.execSQL(CREATE_QRAuditBatchDetails);
        db.execSQL(CREATE_QREnclosure);
        db.execSQL(CREATE_QRInspectionHistory);
        db.execSQL(CREATE_QRPOIntimationDetails);
        db.execSQL(CREATE_QRPOItemDtl_Image);
        db.execSQL(CREATE_QRPOItemDtl_ItemMeasurement);
        db.execSQL(CREATE_QRFindings);
        db.execSQL(CREATE_TestReport);
        db.execSQL(CREATE_QRQualiltyParameterFields);
        db.execSQL(CREATE_QRPOItemFitnessCheck);

        db.execSQL(CREATE_TABLE_SYSDATA22);
        db.execSQL(CREATE_TABLE_DEFECT_APPLICABLE);
        db.execSQL(CREATE_TABLE_GenMst);
        db.execSQL(CREATE_InsplvlHdr);
        db.execSQL(CREATE_InspLvlDtl);
        db.execSQL(CREATE_QualityLevel);
        db.execSQL(CREATE_QualityLevelDtl);
        db.execSQL(CREATE_Enclosures);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        *//*if (newVersion > oldVersion) {
            db.execSQL("ALTER TABLE " + NOTIFICATION_TABLE_NAME + " ADD COLUMN " + NOTIFICATION_TYPE + " INTEGER DEFAULT 0");
        }*//*

    }*/

    //The Android's default system path of your application database.
    private static String DB_PATH = "/data/data/com.buyereasefsl/databases/";

    private static String DB_NAME = "OffLineInspectionDB.db";

    private SQLiteDatabase myDataBase;

    private final Context myContext;

    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     * @param context
     */
    public DBHelper(Context context) {

        super(context, DB_NAME, null, 1);
        this.myContext = context;
    }

    /**
     * Creates a empty database on the system and rewrites it with your own database.
     * */
    public void createDataBase() throws IOException {

        boolean dbExist = checkDataBase();

        if(dbExist){
            //do nothing - database already exist
            /*if (android.os.Build.VERSION.SDK_INT >= 28) {
                this.getReadableDatabase();
                this.close();//added by shekhar
            }*/
        }else{

            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            this.getReadableDatabase();
            this.close();//added by shekhar

            try {

                copyDataBase();

            } catch (IOException e) {

                throw new Error("Error copying database");

            }
        }

    }

    /*@Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            db.disableWriteAheadLogging();
        }
    }*/

    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase(){

        SQLiteDatabase checkDB = null;

        try{
            String myPath = DB_PATH + DB_NAME;
            File file = new File(myPath);//added by shekhar
            if (file.exists() && !file.isDirectory()){ // added by shekhar
                Log.e("dbhelper","dbopen");
                checkDB = SQLiteDatabase.openDatabase(myPath, null,SQLiteDatabase.OPEN_READONLY);
            }else {
                Log.e("dbhelper","db can not open");
            }



        }catch(SQLiteException e){
            Log.e("dbhelper","exception db can not open");
            //database does't exist yet.

        }

        if(checkDB != null){

            checkDB.close();

        }

        return checkDB != null ? true : false;
    }

    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
    private void copyDataBase() throws IOException {

        //Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    public void openDataBase() throws SQLException {

        //Open the database
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

    }

    @Override
    public synchronized void close() {

        if(myDataBase != null)
            myDataBase.close();

        super.close();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // create style table
    public static void reCreateTable(SQLiteDatabase db) {
        createStyleTable(db);
        createStyleImageTable(db);
    }

    public static final String TB_STYLE_DETAIL_TABLE = "styleDeatil";
    public static final String TB_DEFECT_MASTER_TABLE = "DefectMaster";

    public static final String CL_ITEM_ID = "ItemID";
    public static final String CL_PROW_ID= "pRowID";
    public static final String CL_STYLE_REFERENCE_NO = "style_ref_no";
    public static final String CL_CODE = "IntroCode";
    public static final String CL_DESCRIPTION = "ItemDescr";
    public static final String CL_CUSTOMER_NAME = "CustomeName";
    public static final String CL_DEPARTMENT_NAME = "Department";
    public static final String CL_VENDOR = "VendorName";
    public static final String CL_HOLOGRAM_STATUS = "hologramStatus";
    public static final String CL_EDITABLE_DATE = "editable_date";
    public static final String CL_HOLOGRAM_NO = "HologramNo";
    public static final String CL_HOLOGRAM_ESTABLISH_DATE = "Hologram_EstablishDt";
    public static final String CL_HOLOGRAM_EXPIRY_DATE = "HologramExpiryDt";
    public static final String CL_SYNC = "synced";
    public static final String CL_REC_USER_ID = "recAddUser";
    public static final String CL_Holoram_User = "Holoram_User";
    public static final String CL_LOC_ID = "LocID";
    public static final String CL_REC_DATE = "recDt";

    private static void createStyleTable(SQLiteDatabase db) {
        String create = "CREATE TABLE IF NOT EXISTS " + TB_STYLE_DETAIL_TABLE + " ("
                + CL_PROW_ID + " TEXT PRIMARY KEY,"
                + CL_ITEM_ID + " TEXT ,"
                + CL_STYLE_REFERENCE_NO + " TEXT,"
                + CL_CODE + " TEXT,"
                + CL_DESCRIPTION + " TEXT,"
                + CL_CUSTOMER_NAME + " TEXT,"
                + CL_DEPARTMENT_NAME + " TEXT,"
                + CL_VENDOR + " TEXT,"
                + CL_HOLOGRAM_STATUS + " TEXT,"
                + CL_EDITABLE_DATE + " TEXT,"
                + CL_HOLOGRAM_NO + " TEXT,"
                + CL_Holoram_User + " TEXT,"
                + CL_LOC_ID + " TEXT,"
                + CL_HOLOGRAM_ESTABLISH_DATE + " TEXT,"
                + CL_REC_DATE + " TEXT,"
                + CL_SYNC + " INTEGER DEFAULT 0 ,"
                + CL_HOLOGRAM_EXPIRY_DATE + " TEXT" + ")";

        db.execSQL(create);
    }

    public static final String TB_STYLE_IMAGE_TABLE = "styleImage";
    public static final String CL_STYLE_PROW_ID = "ItemID";
    public static final String CL_STYLE_IMAGE = "style_image";
    public static final String CL_STYLE_IMAGE_EXT = "ImageExt";
    public static final String CL_IMAGE_PROW_ID = "pRowID";
    public static final String CL_HOLOGRAM_ID = "HologramID";


    private static void createStyleImageTable(SQLiteDatabase db) {
        String create = "CREATE TABLE IF NOT EXISTS " + TB_STYLE_IMAGE_TABLE + " ("
                + CL_IMAGE_PROW_ID + " TEXT PRIMARY KEY,"
                + CL_STYLE_IMAGE + " TEXT,"
                + CL_STYLE_IMAGE_EXT + " TEXT,"
                + CL_REC_USER_ID + " TEXT,"
                + CL_LOC_ID + " TEXT,"
                + CL_HOLOGRAM_ID + " TEXT,"
                + CL_SYNC + " INTEGER DEFAULT 0 ,"
                + CL_STYLE_PROW_ID + " TEXT" + ")";
        db.execSQL(create);
    }
}
