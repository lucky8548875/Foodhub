package com.hilagangluzon.foodhub.Customer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.hilagangluzon.foodhub.Classes.User;
import com.hilagangluzon.foodhub.R;

public class Dashboard extends AppCompatActivity {

    public static String loggedInUserId;
    public static User loggedInUserInfo;
    private ViewPager viewPager;
    private BottomNavigationView navigation;
    private static final int NUM_PAGES = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Declare the bottom navigation view
        navigation = findViewById(R.id.navigation);

        // Set the bottom navigation listener
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // Instantiate the viewPager
        viewPager = findViewById(R.id.viewPager);

        // Declare the viewPager
        final PagerAdapter pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        final BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

                switch (position) {
                    case 0:
                        navigation.setSelectedItemId(R.id.navigation_menu); break;
                    case 1:
                        navigation.setSelectedItemId(R.id.navigation_transactions); break;
                    case 2:
                        navigation.setSelectedItemId(R.id.navigation_profile); break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position){
                case 0: return new MenuFragment();
                case 1: return new TransactionsFragment();
                case 2: return new ProfileFragment();
            }
            return new TransactionsFragment();
        }



        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

    // NavigationItemListener
    /**
     * Contains the conditions on switching viewPager pages thru the bottom navigation menu
     */

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.navigation_menu:
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_transactions:
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_profile:
                    viewPager.setCurrentItem(2);
                    return true;
            }
            return false;



        }
    };

}
