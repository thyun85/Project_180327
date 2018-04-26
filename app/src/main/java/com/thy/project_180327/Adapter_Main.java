package com.thy.project_180327;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by alofo on 2018-03-27.
 */

public class Adapter_Main extends FragmentPagerAdapter {

    String[] titles = {"전체", "공연장", "박물관", "도서관", "미술관"};

    public Adapter_Main(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        switch (position){
            case 0:
                fragment = new Fragment_All();
                break;

            case 1:
                fragment = new Fragment_ConcertHall();
                break;

            case 2:
                fragment = new Fragment_Museum();
                break;

            case 3:
                fragment = new Fragment_Library();
                break;

            case 4:
                fragment = new Fragment_ArtGallery();
                break;
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
