package com.len.smartstatusbar;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.len.smartstatusbar.fragment.StatusBarDarkModeFragment;
import com.len.smartstatusbar.fragment.StatusBarLightModeFragment;
import com.len.smartstatusbar.fragment.TranslucentFragment;

public class AppTabPagerAdapter extends FragmentPagerAdapter {

    private int size;

    public AppTabPagerAdapter(FragmentManager fm, int size) {
        super(fm);
        this.size = size;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return StatusBarLightModeFragment.newInstance();
        } else if (position == 1) {
            return StatusBarDarkModeFragment.newInstance();
        } else if (position == 2) {
            return TranslucentFragment.newInstance();
        }
        return StatusBarLightModeFragment.newInstance();
    }

    @Override
    public int getCount() {
        return size;
    }
}
