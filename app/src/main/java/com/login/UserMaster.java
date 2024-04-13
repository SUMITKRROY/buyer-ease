package com.login;

import com.constant.FEnumerations;

/**
 * Created by ADMIN on 12/27/2017.
 */

public class UserMaster {

    public String pUserID, ParentID, LoginName, Password, UserName, Email, fCommunityID, fContactID;
    public int IsAdmin;
    public String LocId, Desg, Address, City, Zip, State, CountryID, PhoneNo1, PhoneNo2, PhoneNo3, Fax, WebURL, PageSize;
    public int DateFormat, HomePOGraph;
    public String HomePOGraphXValue, HomePOGraphYValue;
    public int SRView, POView, POView12, QRView, InvoiceView, VesselView, ColumnPickerView;
    public String ApprovalPassword, BirthDt, AnniversaryDt, JoiningDt;
    public int recEnable, IsValidated;
    public String Last_Sync_Dt;
    public int CriticalDefectsAllowed;
    public String CompanyName;
    public String QualityReportConfig;
    public int userType ;//= FEnumerations.E_USER_TYPE_ADMIN
}
