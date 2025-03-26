package com.util;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
    // format 24hre ex. 12:12 , 17:15
    private static String HOUR_FORMAT = "HH:mm";
    private static String TAG = "DateUtils";

    private DateUtils() {
    }

    public static String getCurrentHour() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdfHour = new SimpleDateFormat(HOUR_FORMAT, Locale.US);
        String hour = sdfHour.format(cal.getTime());
        return hour;
    }

    /**
     * @param target hour to check
     * @param start  interval start
     * @param end    interval end
     * @return true    true if the given hour is between
     */
    public static boolean isHourInInterval(String target, String start, String end) {
        return ((target.compareTo(start) >= 0)
                && (target.compareTo(end) <= 0));
    }

    public static boolean isNowInInterval(String start, String end) {
        return DateUtils.isHourInInterval
                (DateUtils.getCurrentHour(), start, end);
    }

    public static String getDate(String strCurrentDate) {
        if (TextUtils.isEmpty(strCurrentDate)) {
            return "";
        }
//        String strCurrentDate = "2016-07-29 14:41:50.0";
        FslLog.d(TAG, "dt date current " + strCurrentDate);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
        Date newDate = null;
        String date = null;
        try {
            newDate = format.parse(strCurrentDate);
            format = new SimpleDateFormat("dd MMM yyyy", Locale.US);
            date = format.format(newDate);
            FslLog.d(TAG, "dt date format" + date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

/*        format = new SimpleDateFormat("dd MMM yyyy", Locale.US);
        String date = format.format(newDate);
        FslLog.d("dt date formate", date);*/
        return date;
    }

    public static String getTime(String strCurrentDate) {
//        String strCurrentDate = "2016-07-29 14:41:50.0";
        FslLog.d(TAG, "dt date current" + strCurrentDate);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
        Date newDate = null;
        String time = null;
        try {
            newDate = format.parse(strCurrentDate);
            format = new SimpleDateFormat("h:mm:ss a", Locale.US);
            time = format.format(newDate);
            FslLog.d(TAG, "dt time format" + time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

/*        format = new SimpleDateFormat("h:mm:ss a", Locale.US);
        String time = format.format(newDate);
        FslLog.d("dt time formate", time);*/
        return time;
    }

    public static String getDateWeekDay(String strCurrentDate) {
//        String strCurrentDate = "dd MMM yyyy";
        FslLog.d(TAG, "dt date current " + strCurrentDate);
        SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy", Locale.US);
        Date newDate = null;
        String date = null;
        try {
            newDate = format.parse(strCurrentDate);
            format = new SimpleDateFormat("E dd MMM yyyy", Locale.US);
            date = format.format(newDate);
            FslLog.d(TAG, "dt date format" + date);
        } catch (ParseException e) {
            FslLog.e(TAG, "ParseException to changing format");
            e.printStackTrace();
        }

        return date;
    }

    public static String getOnlyDate(String strCurrentDate) {
//        String strCurrentDate = "2016-07-29 14:41:50.0";
        FslLog.d(TAG, "dt date current " + strCurrentDate);
        SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
        Date newDate = null;
        String date = null;
        try {
            newDate = format.parse(strCurrentDate);
            format = new SimpleDateFormat("dd", Locale.US);
            date = format.format(newDate);
            FslLog.d(TAG, "dt date format" + date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    public static String getOnlyMonthAndYear(String strCurrentDate) {
//        String strCurrentDate = "2016-07-29 14:41:50.0";
        FslLog.d(TAG, "dt date current " + strCurrentDate);
        SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
        Date newDate = null;
        String date = null;
        try {
            newDate = format.parse(strCurrentDate);
            format = new SimpleDateFormat("MMM yyyy", Locale.US);
            date = format.format(newDate);
            FslLog.d(TAG, "dt date format" + date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

/*        format = new SimpleDateFormat("dd MMM yyyy", Locale.US);
        String date = format.format(newDate);
        FslLog.d("dt date formate", date);*/
        return date;
    }

    public static String getDateWeekDayWithYear(String strCurrentDate) {
//        String strCurrentDate = "dd MMM yyyy";
        FslLog.d(TAG, "dt date current " + strCurrentDate);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        Date newDate = null;
        String date = null;
        try {
            newDate = format.parse(strCurrentDate);
            format = new SimpleDateFormat("E dd MMM yyyy", Locale.US);
            date = format.format(newDate);
            FslLog.d(TAG, "dt date format" + date);
        } catch (ParseException e) {
            FslLog.e(TAG, "ParseException to changing format");
            e.printStackTrace();
        }

        return date;
    }

    public static String getFullWeekDayDateWithOutYear(String strCurrentDate) {
        FslLog.d(TAG, "dt date current " + strCurrentDate);
        SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy", Locale.US);
        Date newDate = null;
        String date = null;
        try {
            newDate = format.parse(strCurrentDate);
            format = new SimpleDateFormat("EEEE, dd MMMM", Locale.US);
            date = format.format(newDate);
            FslLog.d(TAG, "dt date format" + date);
        } catch (ParseException e) {
            FslLog.e(TAG, "ParseException to changing format");
            e.printStackTrace();
        }

        return date;
    }

    public static Date getDateFromString(String strCurrentDate) {
//        String strCurrentDate = "dd MMM yyyy";
        FslLog.d(TAG, "dt date current " + strCurrentDate);
        SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy", Locale.US);
        Date date = null;

        try {
            date = format.parse(strCurrentDate);
            FslLog.d(TAG, "dt date format" + date);
        } catch (ParseException e) {
            FslLog.e(TAG, "ParseException to changing format");
            e.printStackTrace();
        }

        return date;
    }

    public static String getStringDateToTodayAlert(String strCurrentDate) {
//        String strCurrentDate = "dd MMM yyyy";
        FslLog.d(TAG, "dt date current " + strCurrentDate);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
        Date newDate = null;
        String date = null;
        try {
            newDate = format.parse(strCurrentDate);
            format = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
            date = format.format(newDate);
            FslLog.d(TAG, "dt date format" + date);
        } catch (ParseException e) {
            FslLog.e(TAG, "ParseException to changing format");
            e.printStackTrace();
            date=strCurrentDate;
        }

        return date;
    }

    public static Date getDateWithTimeFromString(String strCurrentDate) {
//        String strCurrentDate = "dd MMM yyyy h:mm a";
        FslLog.d(TAG, "dt date current " + strCurrentDate);
        SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy h:mm a", Locale.US);
        Date date = null;

        try {
            date = format.parse(strCurrentDate);
            FslLog.d(TAG, "dt date format" + date);
        } catch (ParseException e) {
            FslLog.e(TAG, "ParseException to changing format");
            e.printStackTrace();
        }

        return date;
    }

    public static boolean isSameDay(Calendar cal1, Calendar cal2) {
        if (cal1 == null || cal2 == null) {
            throw new IllegalArgumentException("The dates must not be null");
        }
        return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) &&
                cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR));
    }

    public static boolean isSameDay(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            throw new IllegalArgumentException("The dates must not be null");
        }
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return isSameDay(cal1, cal2);
    }
}
