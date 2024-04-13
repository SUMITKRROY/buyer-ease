package com.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.constant.FEnumerations;
import com.constant.JsonKey;
import com.login.UserData;
import com.util.FslLog;


/**
 * Created by ADMIN on 8/31/2017.
 */

public class UserSession implements JsonKey {

    SharedPreferences pref;

    //Editor for Shared preferences
    SharedPreferences.Editor editor;

    //Context
    Context _context;

    //Shared pref mode
    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = UserSession.class.getName();

    private static final String TAG = "UserSession";

    public UserSession(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();

    }


    public String getUserId() {
        return pref.getString(KEY_USER_ID, null);
    }

    public UserData getLoginData() {
        UserData userData = new UserData();
        userData.userId = pref.getString(KEY_USER_ID, null);
        userData.password = pref.getString(KEY_PASSWORD, null);
//        userData.community = pref.getString(KEY_COMMUNITY, null);


//        userData.KEY_ASSO_ID = pref.getString(KEY_ASSO_ID, null);
//        userData.KEY_ASSO_TYPE = pref.getString(KEY_ASSO_TYPE, null);

        userData.userProfileName = pref.getString(KEY_USER_NAME, null);
        userData.userProfilePic = pref.getString(KEY_USER_PIC, null);
//        userData.flatId = pref.getString(KEY_FLAT_ID, null);

//        userData._flatNo = pref.getString(KEY_FLAT_NO, null);


        return userData;
    }


    public void putPassword(String pass) {
        editor.putString(KEY_PASSWORD, pass);
        editor.commit();
    }

    public String getPassword() {
        return pref.getString(KEY_PASSWORD, null);
    }

    public void putCreateNewUser(int status) {
        editor.putInt("st", status);
        editor.commit();
    }

    public int getCreateNewUserStatus() {
        return pref.getInt("st", 0);
    }


    public void putSecureCode(String pass) {
        editor.putString(KEY_SECURE_CODE, pass);
        editor.commit();
    }

    public String getSecureCode() {
        return pref.getString(KEY_SECURE_CODE, null);
    }


    public String getCompanyName() {
        return pref.getString("", null);
    }


    public String getProfileName() {
        return pref.getString(KEY_USER_NAME, null);
    }

    public void putProfileName(String name) {
        editor.putString(KEY_USER_NAME, name);
        editor.commit();
    }

    public String getProfilePic() {
        return pref.getString(KEY_USER_PIC, null);
    }

    public void putProfilePic(String pic) {
        editor.putString(KEY_USER_PIC, pic);
        editor.commit();
    }




   /* public String getPrimaryEmail() {
        return pref.getString(KEY_PROFILE_PRIMARY_EMAIL, null);
    }

    public void putPrimaryEmail(String email) {
        editor.putString(KEY_PROFILE_PRIMARY_EMAIL, email);
        editor.commit();
    }*/

    /*public String getAlternateEmail() {
        return pref.getString(KEY_PROFILE_ALTERNATE_EMAIL, null);
    }

    public void putAlternateEmail(String email) {
        editor.putString(KEY_PROFILE_ALTERNATE_EMAIL, email);
        editor.commit();
    }*/

   /* public String getPrimaryMobile() {
        return pref.getString(KEY_PROFILE_PRIMARY_MOBILE, null);
    }

    public void putPrimaryMobile(String mobile) {
        editor.putString(KEY_PROFILE_PRIMARY_MOBILE, mobile);
        editor.commit();
    }*/

    /*public String getAlternateMobile() {
        return pref.getString(KEY_PROFILE_ALTERNATE_MOBILE, null);
    }

    public void putAlternateMobile(String mobile) {
        editor.putString(KEY_PROFILE_ALTERNATE_MOBILE, mobile);
        editor.commit();
    }*/

   /* public String getLandLineNo() {
        return pref.getString(KEY_PROFILE_LANDLINE_NO, null);
    }

    public void putLandLineNo(String landline) {
        editor.putString(KEY_PROFILE_LANDLINE_NO, landline);
        editor.commit();
    }*/

   /* public String getFamilyJson() {
        return pref.getString("family", null);
    }

    public void putFamilyJson(String family) {
        editor.putString("family", family);
        editor.commit();
    }*/


   /* public String getGender() {
        return pref.getString(KEY_PROFILE_GENDER, null);
    }

    public void putGender(String gen) {
        editor.putString(KEY_PROFILE_GENDER, gen);
        editor.commit();
    }*/

   /* public String getDOB() {
        return pref.getString(KEY_PROFILE_DOB, null);
    }

    public void putDOB(String landline) {
        editor.putString(KEY_PROFILE_DOB, landline);
        editor.commit();
    }*/


    /*public String getAddress() {
        return pref.getString(KEY_PROFILE_ADDRESS, null);
    }

    public void putAddress(String address) {
        editor.putString(KEY_PROFILE_ADDRESS, address);
        editor.commit();
    }*/

    /*public String getCity() {
        return pref.getString(KEY_PROFILE_CITY, null);
    }

    public void putCity(String city) {
        editor.putString(KEY_PROFILE_CITY, city);
        editor.commit();
    }*/

    public String getInspectionDt() {
        return pref.getString("inspectDr", null);
    }

    public void putInspectionDt(String inspectDr) {
        editor.putString("inspectDr", inspectDr);
        editor.commit();
    }

    public String getDeLNO() {
        return pref.getString("delNo", null);
    }

    public void putDeLNO(String delNo) {
        editor.putString("delNo", delNo);
        editor.commit();
    }

    public int getPendingNotifyCount() {
        return pref.getInt(KEY_NOTIFY_COUNT, 0);
    }

    public void putPendingNotifyCount(int count) {
        editor.putInt(KEY_NOTIFY_COUNT, count);
        editor.commit();
    }


    public UserData checkLoginData() {
        UserData userData = new UserData();
        userData.password = pref.getString(KEY_PASSWORD, null);
        userData.userProfileName = pref.getString(KEY_USER_NAME, null);
        userData.userId = pref.getString(KEY_USER_ID, null);
//        userData.community = pref.getString(KEY_COMMUNITY, null);
//        userData.flatId = pref.getString(KEY_FLAT_ID, null);
//        userData.KEY_ASSO_TYPE = pref.getString(KEY_ASSO_TYPE, null);

        return userData;
    }


    public void clearDataOnLogOut() {
        FslLog.d(TAG, "clear data.   on logout..........");
        editor.remove(KEY_USER_NAME);
        editor.remove(KEY_PASSWORD);
        putApiHitToSync(FEnumerations.E_FALSE);
//        putApiHitToMaidSync(FEnumerations.E_FALSE);
//        putApiHitToProfileSync(FEnumerations.E_FALSE);
//        putApiHitToFamilySync(FEnumerations.E_FALSE);
//        putApiHitToVehicleSync(FEnumerations.E_FALSE);
        editor.commit();

    }

    public void OnLogOut() {
        FslLog.d(TAG, "on logout..........");
        editor.putInt(KEY_IS_LOGOUT, 0);

        editor.commit();

    }

    public int IsLogOut() {
        return pref.getInt(KEY_IS_LOGOUT, 0);
    }

    public void OnLogIn() {
        FslLog.d(TAG, "on LogIn..........");
        editor.putInt(KEY_IS_LOGOUT, 1);

        editor.commit();

    }


    public int IsApiHitToSync() {
        return pref.getInt(KEY_SYNC_HIT, 0);
    }

    public void putApiHitToSync(int hit) {
        editor.putInt(KEY_SYNC_HIT, hit);
        editor.commit();
    }

    public Long getTimeToHit() {
        return pref.getLong(KEY_TIME_HIT_VISIT, 0);
    }

    public void putApiTimeHitToSync(Long hit) {
        editor.putLong(KEY_TIME_HIT_VISIT, hit);
        editor.commit();
    }

    public int IsApiHitToMaidSync() {
        return pref.getInt("isHitMaid", 0);
    }

    public void putApiHitToMaidSync(int hit) {
        editor.putInt("isHitMaid", hit);
        editor.commit();
    }


    public int IsApiHitToPorfileSync() {
        return pref.getInt("isHitProfile", 0);
    }

    public void putApiHitToProfileSync(int hit) {
        editor.putInt("isHitProfile", hit);
        editor.commit();
    }

    public int IsApiHitToFamilySync() {
        return pref.getInt("isHitFamily", 0);
    }

    public void putApiHitToFamilySync(int hit) {
        editor.putInt("isHitFamily", hit);
        editor.commit();
    }

    public int IsApiHitToVehicleSync() {
        return pref.getInt("isHitVehicle", 0);
    }

    public void putApiHitToVehicleSync(int hit) {
        editor.putInt("isHitVehicle", hit);
        editor.commit();
    }
}
