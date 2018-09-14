package com.len.smartstatusbar;

import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;

import com.statusbar.StatusBarCompat;

/**
 * com.york.yorkbbs.BaseActivity
 *
 * @author 王少雷 <br/>
 * create at 2015年3月25日 下午5:23:01
 */
public class BaseAppCompatActivity extends AppCompatActivity {

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        setStatusBar(getResources().getColor(R.color.status_bar_light_white));
    }

    protected void setStatusBar(@ColorInt int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            StatusBarCompat.setStatusBarColor(this, color);
            if (color == getResources().getColor(R.color.status_bar_light_gray) || color == getResources().getColor(R.color.status_bar_light_white)) {
                StatusBarCompat.setStatusBarLightMode(this);
            } else {
                StatusBarCompat.setStatusBarDarkMode(this);
            }
        } else {
            StatusBarCompat.setStatusBarColor(this, color, 80);
        }
    }

}
