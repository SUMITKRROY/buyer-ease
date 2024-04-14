package com.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.MessageQueue;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.buyereasefsl.DigitalsUploadModal;
import com.buyereasefsl.ItemMeasurementModal;
import com.buyereasefsl.QualityParameter;
import com.buyereasefsl.R;
import com.buyereasefsl.WorkManShipModel;
import com.constant.FClientConfig;
import com.database.DBHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hologram.HologramModal;
import com.inspection.InspectionModal;
import com.podetail.POItemDtl;

import java.io.File;
import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.util.List;

import me.drakeet.support.toast.ToastCompat;


/**
 * Created by Anand on 10-09-2016.
 */
public class GenUtils {

    public static int MINIMUM_VALID_WORD_LENGTH = 4;
    public static int MINIMUM_WORDS_IN_A_VALID_TEXT_STRING = 2;
    static String TAG = "GenUtils";
    private static boolean erro = false;

    public static String truncate(String str, int len) {
        FslLog.d(TAG, " string for  truncate  ..." + str);
        if (!TextUtils.isEmpty(str)) {
            if (str.length() > len) {
                return str.substring(0, len) + "...";
            } else {
                return str;
            }
        } else {
            return "";
        }
    }

    public static Spanned getHtmlText(String pText) {
        if (Build.VERSION.SDK_INT >= 24) {
            return Html.fromHtml(pText, Html.FROM_HTML_MODE_LEGACY); // for 24 api and more
        } else {
            return Html.fromHtml(pText); // or for older api
        }
    }

    public static final int getColorResource(Context context, int id) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= Build.VERSION_CODES.M) {
            return ContextCompat.getColor(context, id);
        } else {
            return context.getResources().getColor(id);
        }
    }

    public static final void getBackgroundDrawable(View view, Context context, int id) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackgroundDrawable(ContextCompat.getDrawable(context, id));
        } else {
            view.setBackground(ContextCompat.getDrawable(context, id));
        }
    }

   /* public static boolean setTitleTextStyle(Context context, Toolbar actionBar) {
        for (int i = 0; i < actionBar.getChildCount(); i++) {
            View view = actionBar.getChildAt(i);
            if (view != null
                    && view instanceof TextView) {
                TextView titleText = (TextView) view;
                if (titleText.getText().equals(actionBar.getTitle())) {
                    titleText.setTypeface((C.getRobotoLightFont(context)));
                    titleText.setAllCaps(true);
                    return true;
                }
            }
        }

        return false;
    }*/

    public static void forInfoAlertDialog(final Context context
            , String action
            , String title
            , String message
            , final AlertDialogClickListener clickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AppCompatAlertDialogStyle);
//        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);//, R.style.AppCompatAlertDialogStyle);
        if (!TextUtils.isEmpty(title))
            builder.setTitle(title);
        builder.setMessage(message);
        //alertDialog.setCancelable(false);
        builder.setPositiveButton(action, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
                if (clickListener != null)
                    clickListener.onPositiveButton();
            }
        });


//        alertDialog.show();
        if (Initialization.alertDialog != null) {
            if (Initialization.alertDialog.isShowing())
                GenUtils.safeAlertDialogDismiss(TAG, context, Initialization.alertDialog);
            //                Initialization.alertDialog.dismiss();
            Initialization.alertDialog = null;
        }
        Initialization.alertDialog = builder.create();
//        Initialization.alertDialog.show();
        GenUtils.safeAlertDialogShow(TAG, context, Initialization.alertDialog);
    }


    public static void forSingleButtonAlertDialog(final Context context
            , String posaction
            , String title
            , String message
            , final AlertDialogClickListener clickListener) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AppCompatAlertDialogStyle);
        if (!TextUtils.isEmpty(title))
            builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(posaction, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
                if (clickListener != null)
                    clickListener.onPositiveButton();
            }
        });

        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                dialog.dismiss();
                if (clickListener != null)
                    clickListener.onNegativeButton();
            }
        });

        if (Initialization.alertDialog != null) {
            if (Initialization.alertDialog.isShowing())
                GenUtils.safeAlertDialogDismiss(TAG, context, Initialization.alertDialog);
            //                Initialization.alertDialog.dismiss();
            Initialization.alertDialog = null;
        }
        Initialization.alertDialog = builder.create();
//        Initialization.alertDialog.show();
        GenUtils.safeAlertDialogShow(TAG, context, Initialization.alertDialog);
//        alertDialog.show();
//        alertDialog.show();
    }

    public static void forConfirmationAlertDialog(final WeakReference<Activity> activityWeakReference
            , String posaction
            , String negaction
            , String title
            , String message
            , final AlertDialogClickListener clickListener) {

        AlertDialog.Builder builder = new AlertDialog.Builder(activityWeakReference.get(), R.style.AppCompatAlertDialogStyle);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(posaction, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
                Initialization.alertDialog = null;
                if (clickListener != null)
                    clickListener.onPositiveButton();
            }
        });

        builder.setNegativeButton(negaction, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
                Initialization.alertDialog = null;
                if (clickListener != null)
                    clickListener.onNegativeButton();
            }
        });
//        safeAlertDialogShow("forConfirmationAlertDialog", (Activity) context, builder);

        if (Initialization.alertDialog != null
                && Initialization.activityWeakReference != null
                && Initialization.activityWeakReference.get() != null
                && !Initialization.activityWeakReference.get().isFinishing()) {
            if (Initialization.alertDialog.isShowing()) {
                Initialization.alertDialog.dismiss();
                Initialization.alertDialog = null;
            }
            Initialization.alertDialog = null;
        }
        Initialization.alertDialog = builder.create();
        Initialization.activityWeakReference = activityWeakReference;
//        Initialization.alertDialog.show();
        GenUtils.safeAlertDialogShow(TAG, activityWeakReference.get(), Initialization.alertDialog);
//        alertDialog.show();
    }

    public static void showListDialog(final Context context
            , String title
            , final List<String> itemsList
            , final ListDialogClickListener clickListener) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                R.layout.dialog_list_item, itemsList);

        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(title)
                .setAdapter(adapter, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // TODO: user specific action
                        clickListener.onItemSelectedPos(which);
                        dialog.dismiss();
                    }
                });
        builder.setCancelable(true);
        builder.create();
        builder.show();
    }

  /*  public static void showListWithIconDialog(final Activity context
            , String title
            , final List<String> itemsList
            , Integer[] iconList
            , String actionName
            , final ListDialogItemClickListener clickListener) {
        CustomListAdapter adapter = new CustomListAdapter(context,
                itemsList, iconList);

        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(title)
                .setAdapter(adapter, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO: user specific action
                        if (clickListener != null)
                            clickListener.onItemSelected(which);
                        dialog.dismiss();
                    }
                });
        builder.setPositiveButton(actionName, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
                if (clickListener != null)
                    clickListener.onCancel();
            }
        });
        builder.setCancelable(true);
        builder.create();
        builder.show();
    }*/


    public static void forConfirmationAlertDialog(final Context context
            , String posaction
            , String negaction
            , String title
            , String message
            , final AlertDialogClickListener clickListener) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AppCompatAlertDialogStyle);
        if (!TextUtils.isEmpty(title))
            builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(posaction, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
                Initialization.alertDialog = null;
                if (clickListener != null)
                    clickListener.onPositiveButton();
            }
        });

        builder.setNegativeButton(negaction, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
                Initialization.alertDialog = null;
                if (clickListener != null)
                    clickListener.onNegativeButton();
            }
        });
//        safeAlertDialogShow("forConfirmationAlertDialog", (Activity) context, builder);

        if (Initialization.alertDialog != null) {
            if (Initialization.alertDialog.isShowing())
                GenUtils.safeAlertDialogDismiss(TAG, context, Initialization.alertDialog);
            //                Initialization.alertDialog.dismiss();
            Initialization.alertDialog = null;
        }
        Initialization.alertDialog = builder.create();
//        Initialization.alertDialog.show();
        GenUtils.safeAlertDialogShow(TAG, context, Initialization.alertDialog);
//        alertDialog.show();
    }


    public static void forEditableConfirmationAlertDialog(final Context context
            , String posaction
            , String negaction
            , String title
            , String msg
            , final AlertDialogCallBackClickListener clickListener) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AppCompatAlertDialogStyle);
        if (!TextUtils.isEmpty(title))
            builder.setTitle(title);


        final EditText edittext = new EditText(context);
        edittext.setText(msg);
        builder.setView(edittext);
        builder.setPositiveButton(posaction, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //What ever you want to do with the value
                String YouEditTextValue = edittext.getText().toString();
                dialog.dismiss();
                Initialization.alertDialog = null;
                if (clickListener != null)
                    clickListener.onPositiveButton(YouEditTextValue);
            }
        });

        builder.setNegativeButton(negaction, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
                Initialization.alertDialog = null;
                if (clickListener != null)
                    clickListener.onNegativeButton();
            }
        });
//        safeAlertDialogShow("forConfirmationAlertDialog", (Activity) context, builder);

        if (Initialization.alertDialog != null) {
            if (Initialization.alertDialog.isShowing())
                GenUtils.safeAlertDialogDismiss(TAG, context, Initialization.alertDialog);
            //                Initialization.alertDialog.dismiss();
            Initialization.alertDialog = null;
        }
        Initialization.alertDialog = builder.create();
//        Initialization.alertDialog.show();
        GenUtils.safeAlertDialogShow(TAG, context, Initialization.alertDialog);
//        alertDialog.show();
    }


    public static int convertDpToPixel(int dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        int px = Math.round(dp * (metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    public static boolean isWhiteSpaceOnly(String stringToCheck) {

        int len = stringToCheck.length();

        for (int i = 0; i < len; i++) {
            char tempChar = stringToCheck.charAt(i);
            if (!Character.isSpaceChar(tempChar)) {
                return false;
            }
        }

        return true;
    }

    public static boolean isPotentiallyValidText(String stringToCheck) {

        int len = stringToCheck.length();
        int wordLenCounter = 0;
        int validWordsCounter = 0;

        for (int i = 0; i < len; i++) {
            char tempChar = stringToCheck.charAt(i);
            if (Character.isLetter(tempChar)) {
                wordLenCounter++;
            } else {
                if (wordLenCounter >= MINIMUM_VALID_WORD_LENGTH) {
                    validWordsCounter++;
                }
                wordLenCounter = 0;
            }
        }

        //to count last word
        if (wordLenCounter >= MINIMUM_VALID_WORD_LENGTH) {
            validWordsCounter++;
        }

        FslLog.d("GenUtils.isPotentiallyValidText", "No of valid words is " + validWordsCounter);

        if (validWordsCounter >= MINIMUM_WORDS_IN_A_VALID_TEXT_STRING)
            return true;
        else
            return false;
    }

    public static boolean isMeaningfulComment(String stringToCheck) {

        if (isWhiteSpaceOnly(stringToCheck)) {
            return false;
        }

        if (TextUtils.isDigitsOnly(stringToCheck)) {
            return false;
        }

        return isPotentiallyValidText(stringToCheck);
    }

    public static String get12HourTimeFormatStr(int hour, int mins) {
        String selectedHourStr, selectedMinStr, am_pmstr;
        if (hour > 11) {
            if (hour > 12)
                selectedHourStr = String.valueOf(hour - 12);
            else
                selectedHourStr = String.valueOf(hour);
            am_pmstr = " PM";
        } else {
            selectedHourStr = String.valueOf(hour);
            am_pmstr = " AM";
        }

        if (mins > 9)
            selectedMinStr = String.valueOf(mins);
        else
            selectedMinStr = "0" + mins;

        String timeStr = selectedHourStr + ":" + selectedMinStr + am_pmstr;

        return timeStr;
    }

    public static String get12HourTimeFormatStrWithSmallAmOrPm(int hour, int mins) {
        String selectedHourStr, selectedMinStr, am_pmstr;
        if (hour > 11) {
            if (hour > 12)
                selectedHourStr = String.valueOf(hour - 12);
            else
                selectedHourStr = String.valueOf(hour);
            am_pmstr = " pm";
        } else {
            selectedHourStr = String.valueOf(hour);
            am_pmstr = " am";
        }

        if (mins > 9)
            selectedMinStr = String.valueOf(mins);
        else
            selectedMinStr = "0" + mins;

        String timeStr = selectedHourStr + ":" + selectedMinStr + am_pmstr;

        return timeStr;
    }

    public static boolean isItAMTime(int hour, int mins) {
        boolean isItAMTime = true;
        if (hour > 11) {
            isItAMTime = false;
        }
        return isItAMTime;
    }

    /*public static String makeAStringWithVariables(String pInputString, String[] pValues ) {
        String lPattern = "<x>";
        String lProcessedString = "";
        String lTempStr = pInputString;
        int Index = 0;
        int ValuesCounter = 0;

        while (true) {
            Index = lTempStr.indexOf(lPattern);
            if (Index == -1){
                break;
            } else if ( ValuesCounter < pValues.length){
                lProcessedString = lProcessedString
                                    + lTempStr.substring(0,(Index-1))
                                    + pValues[ValuesCounter];

                int beginIndex = (Index + lPattern.length() - 1);
                if (lTempStr.length() > beginIndex ) {
                    lTempStr = lTempStr.substring(beginIndex, lTempStr.length());
                    ValuesCounter++;
                } else
                    break;
            }
        }

        return lProcessedString;
    }*/


    public static void hideSoftKeyBoard(Activity activity) {
        View view = activity.getCurrentFocus();

        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (view != null)
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public interface AlertDialogClickListener {
        void onPositiveButton();

        void onNegativeButton();
    }

    public interface AlertDialogCallBackClickListener {
        void onPositiveButton(String resultSring);

        void onNegativeButton();
    }

    public interface ListDialogClickListener {
        void onItemSelectedPos(int selectedItem);

        void onCancel();
    }

    public interface ListDialogItemClickListener {
        void onItemSelected(int pos);

        void onCancel();
    }

    //Anand - need to avoid  java.lang.IllegalArgumentException
    //on dialog dismiss is called when activity is already closed
    public static void safeDismiss(String TAG, Activity activity, ProgressDialog progressDialog) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                if (!activity.isFinishing()
                        && !activity.isDestroyed()) {
                    if (progressDialog != null
                            && progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                }
            } else {
                if (!activity.isFinishing()) {
                    if (progressDialog != null
                            && progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                }
            }
        } catch (Exception e) {
            FslLog.e(TAG, "could not dismiss " + e.toString());
            e.printStackTrace();
        }
    }

    public static void safeProgressDialogShow(String TAG, Context context, ProgressDialog pDialog) {
        if (context != null) {
            try {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    if (!((Activity) context).isFinishing()
                            && !((Activity) context).isDestroyed()) {
                        if (pDialog != null) {
                            pDialog.show();
                        }
                    } else {
                        FslLog.e(TAG, "Activity isFinishing or destroyed");
                    }
                } else {
                    if (!((Activity) context).isFinishing()) {
                        if (pDialog != null) {
                            pDialog.show();
                        }
                    }
                }

            } catch (Exception e) {
                FslLog.e(TAG, "could not show " + e.toString());
                e.printStackTrace();
            }
        } else {
            FslLog.e(TAG, "could not show progress dialog because context is NULL ");
        }
    }

    public static void safeAlertDialogShow(String TAG, Context context, AlertDialog alertDialog) {
        if (context != null) {
            try {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    if (!((Activity) context).isFinishing()
                            && !((Activity) context).isDestroyed()) {
                        if (alertDialog != null) {
                            alertDialog.show();
                        }
                    } else {
                        FslLog.e(TAG, "Activity isFinishing or destroyed");
                    }
                } else {
                    if (!((Activity) context).isFinishing()) {
                        if (alertDialog != null) {
                            alertDialog.show();
                        }
                    }
                }

            } catch (Exception e) {
                FslLog.e(TAG, "could not show " + e.toString());
                e.printStackTrace();
            }
        } else {
            FslLog.e(TAG, "could not show dialog because context is NULL ");
        }
    }

    public static void safeAlertDialogDismiss(String TAG, Context context, AlertDialog alertDialog) {
        if (context != null) {
            try {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    if (!((Activity) context).isFinishing()
                            && !((Activity) context).isDestroyed()) {
                        if (alertDialog != null && alertDialog.isShowing()) {
                            alertDialog.dismiss();
                        }
                    } else {
                        FslLog.e(TAG, "Activity isFinishing or destroyed");
                    }
                } else {
                    if (!((Activity) context).isFinishing() && alertDialog.isShowing()) {
                        if (alertDialog != null) {
                            alertDialog.dismiss();
                        }
                    }
                }

            } catch (Exception e) {
                FslLog.e(TAG, "could not dismiss " + e.toString());
                e.printStackTrace();
            }
        } else {
            FslLog.e(TAG, "could not dismiss dialog because context is NULL ");
        }
    }

    public static boolean isAlphaNumeric(String str) {
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (!Character.isDigit(c) && !Character.isLetter(c))
                return false;
        }

        return true;
    }

    public static boolean isAlphaNumericSpace(String str) {
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (!Character.isDigit(c) && !Character.isLetter(c) && !Character.isSpaceChar(c))
                return false;
        }

        return true;
    }

    public static boolean isOnlyAlphabets(String str) {
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (!Character.isLetter(c))
                return false;
        }

        return true;
    }

    public static String removeAllSpaces(String str) {
        char[] charArray = str.toCharArray();
        StringBuffer newStr = new StringBuffer("");

        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (!Character.isSpaceChar(c))
                newStr.append(c);
        }

        return newStr.toString();
    }

    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public final static boolean isValidDomainName(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.DOMAIN_NAME.matcher(target).matches();
    }

    public final static String addPlusSignInPhoneNum(String number) {

        FslLog.d(TAG, "Phone number received: " + number);

        String phoneNumber = number.trim();

        int indexOfPlus = phoneNumber.indexOf('+');
        int indexOf91 = phoneNumber.indexOf("91");

        if (indexOfPlus == -1
                && indexOf91 == 0) {
            phoneNumber = "+" + phoneNumber;
        }

        FslLog.d(TAG, "Phone number modified: " + phoneNumber);

        return phoneNumber;
    }

    public final static String removePlusSignInPhoneNum(String number) {

        FslLog.d(TAG, "Phone number received: " + number);

        String phoneNumber = number.trim();

        int indexOfPlus = phoneNumber.indexOf('+');

        if (indexOfPlus > -1) {
            phoneNumber = phoneNumber.substring(indexOfPlus + 1);
        }

        FslLog.d(TAG, "Phone number modified: " + phoneNumber);

        return phoneNumber;
    }

    public static boolean isValidNameOfString(String str) {

        boolean isCorrectWord = true;
        if (TextUtils.isEmpty(str)) {
            isCorrectWord = false;
        } else {
            if (!str.matches("[a-zA-Z ]+")) {
                isCorrectWord = false;
            }
            /*for (int k = 0; k < Character.codePointCount(str, 0, str.length()); k++) {
                int c = str.codePointAt(k);
                if (!str.matches("[a-zA-Z ]+")) {
                    isCorrectWord = false;
                    break;
                }
                //Anand check for hindi
                //else if (c >= 0x0900 && c <= 0x0960) {
                    //isHindi = true;
                    //break;
                //}
            }*/
        }
        return isCorrectWord;
    }

    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (dpWidth / 180);
        return noOfColumns;
    }

    public static File getFile(String folderName, String fielname) {
        File tempFil = new File(FClientConfig.mExternalStorageDir + "/" + folderName, fielname);
        String m_imagePath = tempFil.getAbsolutePath();
        File file = new File(m_imagePath);
        return file;
    }

    public static void grantAllUriPermissions(Context context, Intent intent, Uri uri) {
        List<ResolveInfo> resInfoList = context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo resolveInfo : resInfoList) {
            String packageName = resolveInfo.activityInfo.packageName;
            context.grantUriPermission(packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
    }

    public static void showToastInThread(final Context context, final String str) {
        try {
            Looper.prepare();
        } catch (Exception e) {
            erro = true;
        }
        MessageQueue queue = Looper.myQueue();
        queue.addIdleHandler(new MessageQueue.IdleHandler() {
            int mReqCount = 0;

            @Override
            public boolean queueIdle() {
                if (++mReqCount == 2) {
                    if (!erro) {
                        Looper.myLooper().quit();
                    }
                    erro = false;
                    return false;
                } else
                    return true;
            }
        });
        ToastCompat.makeText(context, str, Toast.LENGTH_LONG).show();
//        Looper.loop();
        if (!erro) {
            Looper.loop();
        }
    }

    public static void safeToastShow(String TAG, Context context, ToastCompat alertDialog) {
        if (context != null) {
            try {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    if (!((Activity) context).isFinishing()
                            && !((Activity) context).isDestroyed()) {
                        if (alertDialog != null) {
                            alertDialog.show();
                        }
                    } else {
                        FslLog.e(TAG, "Activity isFinishing or destroyed");
                    }
                } else {
                    if (!((Activity) context).isFinishing()) {
                        if (alertDialog != null) {
                            alertDialog.show();
                        }
                    }
                }

            } catch (Exception e) {
                FslLog.e(TAG, "could not show " + e.toString());
                e.printStackTrace();
            }
        } else {
            FslLog.e(TAG, "could not show dialog because context is NULL ");
        }
    }

    public static void safeToastShow(String TAG, Context context, Toast alertDialog) {
        if (context != null) {
            try {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    if (!((Activity) context).isFinishing()
                            && !((Activity) context).isDestroyed()) {
                        if (alertDialog != null) {
                            new Handler().postDelayed(new Runnable() {
                                public void run() {
                                    alertDialog.show();
                                }
                            }, 100);

                        }
                    } else {
                        FslLog.e(TAG, "Activity isFinishing or destroyed");
                    }
                } else {
                    if (!((Activity) context).isFinishing()) {
                        if (alertDialog != null) {
                            alertDialog.show();
                        }
                    }
                }

            } catch (Exception e) {
                FslLog.e(TAG, "could not show " + e.toString());
                e.printStackTrace();
            }
        } else {
            FslLog.e(TAG, "could not show dialog because context is NULL ");
        }
    }

    public static Uri getUri(Context context, File file) {
        Uri path;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            path = Uri.fromFile(file);
            FslLog.d(TAG, " uri generated by Build.VERSION.SDK_INT < Build.VERSION_CODES.N :: " + path);
        } else {
            path = FileProvider.getUriForFile(context, "com.buyereasefsl", file);
            FslLog.d(TAG, " uri generated by com.buyereasefsl.fileprovider :: " + path);
        }
        return path;
    }

    public static boolean isStringInt(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    public static String serializeInspectionModal(InspectionModal inspectionModal) {
        return new Gson().toJson(inspectionModal);
    }

    public static InspectionModal deSerializeInspectionModal(String json) {
        return new Gson().fromJson(json, InspectionModal.class);
    }

    public static String serializeItemReportModal(POItemDtl itemPackingModel) {
        return new Gson().toJson(itemPackingModel);
    }

    public static POItemDtl deSerializeItemReportModal(String json) {
        return new Gson().fromJson(json, POItemDtl.class);
    }

    public static String serializeDigitalModal(DigitalsUploadModal digitalsUploadModal) {
        return new Gson().toJson(digitalsUploadModal);
    }

    public static String serializeItemMeasurementModal(ItemMeasurementModal itemMeasurementModal) {
        return new Gson().toJson(itemMeasurementModal);
    }

    public static ItemMeasurementModal deSerializeItemMeasurement(String json) {
        return new Gson().fromJson(json, ItemMeasurementModal.class);
    }

    public static WorkManShipModel deSerializeWorkmanship(String json) {
        return new Gson().fromJson(json, WorkManShipModel.class);
    }


    public static String serializeWorkmanship(WorkManShipModel workManShipModel) {
        return new Gson().toJson(workManShipModel);
    }

    public static POItemDtl deSerializePOItemModal(String json) {
        return new Gson().fromJson(json, POItemDtl.class);
    }

    public static String serializePOItemModal(POItemDtl poItemDtl) {
        return new Gson().toJson(poItemDtl);
    }

    public static DigitalsUploadModal deSerializeDigitalUpload(String json) {
        return new Gson().fromJson(json, DigitalsUploadModal.class);
    }

    public static QualityParameter deSerializeQualityParameter(String json) {
        return new Gson().fromJson(json, QualityParameter.class);
    }

    public static String serializeQualityParameter(QualityParameter qualityParameter) {

        return new Gson().toJson(qualityParameter);
    }

    public static HologramModal deSerializeStyle(String json) {
        return new Gson().fromJson(json, HologramModal.class);
    }

    public static String serializeStyle(HologramModal hologramModal) {

        return new Gson().toJson(hologramModal);
    }

    public static List<POItemDtl> deSerializePOItemDtlList(String json) {
        Type listType = new TypeToken<List<POItemDtl>>() {
        }.getType();

        List<POItemDtl> arrayFromJson = new Gson().fromJson(json, listType);

        return arrayFromJson;
    }

    public static List<HologramModal> deSerializeHologramList(String json) {
        Type listType = new TypeToken<List<HologramModal>>() {
        }.getType();
        List<HologramModal> arrayFromJson = new Gson().fromJson(json, listType);
        return arrayFromJson;
    }

    public static String serializeHologramList(List<HologramModal> pList) {
        return new Gson().toJson(pList);
    }


    public static boolean columnExistsInTable(Context mContext, String table, String columnToCheck) {
        DBHelper dbHelper = new DBHelper(mContext);
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        Cursor cursor = null;
        try {
            //query a row. don't acquire db lock
            cursor = database.rawQuery("SELECT * FROM " + table + " LIMIT 0", null);
            // getColumnIndex()  will return the index of the column
            //in the table if it exists, otherwise it will return -1
            if (cursor.getColumnIndex(columnToCheck) != -1) {
                //great, the column exists

                return true;
            } else {
                //sorry, the column does not exist
                return false;
            }

        } catch (SQLiteException Exp) {
            //Something went wrong with SQLite.
            //If the table exists and your query was good,
            //the problem is likely that the column doesn't exist in the table.
            return false;
        } finally {
            //close the db  if you no longer need it
//            if (db != null) db.close();
            //close the cursor
            if (cursor != null) cursor.close();
            if (database != null) database.close();
        }
    }

    public static Cursor getColumnExistsInTable(Context mContext, String table) {
        DBHelper dbHelper = new DBHelper(mContext);
        SQLiteDatabase database = dbHelper.getReadableDatabase();

        Cursor cursor = null;
        try {
            //query a row. don't acquire db lock
            cursor = database.rawQuery("SELECT * FROM " + table + " LIMIT 0", null);
            // getColumnIndex()  will return the index of the column
            //in the table if it exists, otherwise it will return -1


        } catch (SQLiteException Exp) {
            //Something went wrong with SQLite.
            //If the table exists and your query was good,
            //the problem is likely that the column doesn't exist in the table.

        }
        if (database != null) database.close();
        return cursor;
        /*finally {
            //close the db  if you no longer need it
//            if (db != null) db.close();
            //close the cursor
            if (cursor != null) cursor.close();
            if (database != null) database.close();
        }*/
    }

}
