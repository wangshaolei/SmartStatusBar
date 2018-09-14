package com.len.smartstatusbar;

import android.os.Bundle;

import com.bottombar.BottomBarItem;
import com.bottombar.BottomBarLayout;
import com.bottombar.NoTouchViewPager;
import com.statusbar.StatusBarCompat;

public class MainActivity extends BaseAppCompatActivity implements BottomBarLayout.OnItemSelectedListener {

    private BottomBarLayout bottomBarLayout;
    private NoTouchViewPager noTouchViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initTab();
    }

    private void initView() {
        noTouchViewPager = findViewById(R.id.viewPager);
        bottomBarLayout = findViewById(R.id.bottom_bar);
        bottomBarLayout.setOnItemSelectedListener(this);
    }

    private void initTab() {
        int tabCount = bottomBarLayout.getChildCount();
        noTouchViewPager.setAdapter(new AppTabPagerAdapter(getSupportFragmentManager(), tabCount));
        noTouchViewPager.setOffscreenPageLimit(tabCount);
        bottomBarLayout.setViewPager(noTouchViewPager);
    }


    @Override
    public void onItemSelected(BottomBarItem bottomBarItem, int previousPosition, int currentPosition) {
        switch (currentPosition) {
            case 0:
                setStatusBar(getResources().getColor(R.color.status_bar_light_white));
                break;
            case 1:
                setStatusBar(getResources().getColor(R.color.colorAccent));
                break;
            case 2:
                StatusBarCompat.translucentStatusBar(this, true);
                break;
        }
    }
}
