package com.statusbar;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;

import com.statusbar.util.StatusBarHelper;

/**
 * Utils for status bar
 * Created by qiu on 3/29/16.
 */
public class StatusBarCompat {


    /**
     * set statusBarColor
     * @param statusColor color
     * @param alpha       0 - 255
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setStatusBarColor(Activity activity, int statusColor, int alpha) {
        setStatusBarColor(activity, StatusBarHelper.calculateStatusBarColor(statusColor, alpha));
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setStatusBarColor(Activity activity, int statusColor) {
        StatusBarCompatLollipop.setStatusBarColor(activity, statusColor);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void translucentStatusBar(Activity activity) {
        translucentStatusBar(activity, false);
    }

    /**
     * change to full screen mode
     *
     * @param hideStatusBarBackground hide status bar alpha Background when SDK > 21, true if hide it
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void translucentStatusBar(Activity activity, boolean hideStatusBarBackground) {
        StatusBarCompatLollipop.translucentStatusBar(activity, hideStatusBarBackground);
    }

    @TargetApi(Build.VERSION_CODES.M)
    public static void setStatusBarLightMode(Activity activity) {
        StatusBarMarshmallow.setStatusBarLightMode(activity);
    }

    @TargetApi(Build.VERSION_CODES.M)
    public static void setStatusBarDarkMode(Activity activity) {
        StatusBarMarshmallow.setStatusBarDarkMode(activity);
    }
}
