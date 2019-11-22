package id.co.hope.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.View;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetView;
import com.google.gson.Gson;

import id.co.hope.R;
import id.co.hope.data.Preferences;
import id.co.hope.data.model.UserModel;
import lib.almuwahhid.utils.LibUi;
import org.joda.time.LocalDate;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HopeFunction {

    public static Gson gson = new Gson();

    public static void setUserPreference(Context context, String sp){
        LibUi.setSPString(context, Preferences.USER_ACCESS, sp);
    }

    public static UserModel getUserPreference(Context context){
        if(LibUi.getSPString(context, Preferences.USER_ACCESS).equals("")){
            return null;
        } else {
            return gson.fromJson(LibUi.getSPString(context, Preferences.USER_ACCESS), UserModel.class);
        }
    }

    public static void logoutUser(Context context){
        LibUi.removeSPString(context, Preferences.USER_ACCESS);
    }

    public static boolean checkUserPreference(Context context){
        if(LibUi.getSPString(context, Preferences.USER_ACCESS).equals("")){
            return false;
        } else {
            return true;
        }
    }

    public static String parseTimestampToDate(String data){
        try {
            String begin = data.split(" ")[0];
            return begin.split("-")[2]+" "+ LibUi.monthName(Integer.valueOf(begin.split("-")[1])-1)+ " "+begin.split("-")[0];
        } catch (Exception e){
            return "";
        }
    }

    public static String parseTimestampToSimpleMonthYear(String data){
        try {
            String begin = data.split(" ")[0];
            return simpleMonthName(Integer.valueOf(begin.split("-")[1])-1)+ " "+begin.split("-")[0];
        } catch (Exception e){
            return "";
        }
    }
    public static String parseDateStringToDate(String data){
        try {
            return data.split("-")[2]+" "+simpleMonthName(Integer.valueOf(data.split("-")[1])-1)+ " "+data.split("-")[0];
        } catch (Exception e){
            return "";
        }
    }

    public static String parseDateToDate(String data){
        try {
            return data.split("-")[2];
        } catch (Exception e){
            return "";
        }
    }

    public static String parseDateToMonth(String data){
        try {
            return data.split("-")[1];
        } catch (Exception e){
            return "";
        }
    }

    public static String parseDateToYear(String data){
        try {
            return data.split("-")[0];
        } catch (Exception e){
            return "";
        }
    }
    public static String parseTimestampToSimpleFullDate(String data){
        try {
            String begin = data.split(" ")[0];
            return begin.split("-")[2]+" "+ simpleMonthName(Integer.valueOf(begin.split("-")[1])-1)+ " "+begin.split("-")[0].substring(2, 4);
        } catch (Exception e){
            return "";
        }
    }

    public static String parseTimestampToSimpleFullDateTime(String data){
        try {
            String begin = data.split(" ")[0];
            return begin.split("-")[2]+" "+ LibUi.monthName(Integer.valueOf(begin.split("-")[1])-1)+ " "+begin.split("-")[0].substring(2, 4)+" "+data.split(" ")[1];
        } catch (Exception e){
            return "";
        }
    }

    public static String parseDateToRealDate(String begin){
        try {
            return begin.split("-")[2]+" "+ LibUi.monthName(Integer.valueOf(begin.split("-")[1])-1)+ " "+begin.split("-")[0];
        } catch (Exception e){
            return "";
        }
    }

    public static String parseTimestampToSimpleDate(String data){
        try {
            String begin = data.split(" ")[0];
            return begin.split("-")[2];
        } catch (Exception e){
            return "";
        }
    }

    public static String simpleMonthName(int month) {
        String m = "";
        switch (month) {
            case 0:
                m = "Jan";
                break;
            case 1:
                m = "Feb";
                break;
            case 2:
                m = "Mar";
                break;
            case 3:
                m = "Apr";
                break;
            case 4:
                m = "Mei";
                break;
            case 5:
                m = "Jun";
                break;
            case 6:
                m = "Jul";
                break;
            case 7:
                m = "Aug";
                break;
            case 8:
                m = "Sept";
                break;
            case 9:
                m = "Okt";
                break;
            case 10:
                m = "Nov";
                break;
            case 11:
                m = "Des";
                break;
        }
        return m;
    }

    public static String isToday(String data) {

        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
            String str1 = data.split(" ")[0].replaceAll("-", "/");
            Date date1 = new LocalDate(formatter.parse(str1)).toDate();
            Date date2 = new LocalDate(new Date()).toDate();

            Log.d("isToday", "isToday: "+date1+" now "+date2);
            if (date1.compareTo(date2) < 0) {
                return "yesterday";
            } else if (date1.compareTo(date2) == 0) {
                return "today";
            } else {
                return "tomorrow";
            }

        } catch (ParseException e1) {
            e1.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static Bitmap getBitmapFromUri(Context context, Uri uri){
        Bitmap bitmap_image = null;
        try {
//            Log.d(TAG, "handleCropResult: "+uri);
            bitmap_image = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap_image;
    }

    public static void showIntroCase(Activity activity, View v, String title, String desc, boolean istint, final LibUi.OnEventChange onEventChange){
        TapTargetView.showFor(activity,                 // `this` is an Activity
                TapTarget.forView(v, title, desc)
                        // All options below are optional
                        .outerCircleColor(R.color.colorPrimary)      // Specify a color for the outer circle
                        .outerCircleAlpha(0.96f)            // Specify the alpha amount for the outer circle
                        .targetCircleColor(R.color.white)   // Specify a color for the target circle
                        .titleTextSize(20)                  // Specify the size (in sp) of the title text
                        .titleTextColor(R.color.white)      // Specify the color of the title text
                        .descriptionTextSize(14)            // Specify the size (in sp) of the description text
                        .descriptionTextColor(R.color.white)  // Specify the color of the description text
                        .textColor(R.color.white)            // Specify a color for both the title and description text
                        .textTypeface(Typeface.SANS_SERIF)  // Specify a typeface for the text
                        .dimColor(R.color.black)            // If set, will dim behind the view with 30% opacity of the given color
                        .drawShadow(true)                   // Whether to draw a drop shadow or not
                        .cancelable(false)                  // Whether tapping outside the outer circle dismisses the view
                        .tintTarget(istint)                   // Whether to tint the target view's color
                        .transparentTarget(true)           // Specify whether the target is transparent (displays the content underneath)
                        .targetRadius(60),                  // Specify the target radius (in dp)
                new TapTargetView.Listener() {          // The listener can listen for regular clicks, long clicks or cancels
                    @Override
                    public void onTargetClick(TapTargetView view) {
                        super.onTargetClick(view);      // This call is optional
                        if(onEventChange!=null)
                            onEventChange.onChange();
                    }
                });
    }

    public static boolean isPreLolipop(){
        return Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP;
    }

}
