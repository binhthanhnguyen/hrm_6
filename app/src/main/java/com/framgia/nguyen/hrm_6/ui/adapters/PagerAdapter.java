package com.framgia.nguyen.hrm_6.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.framgia.nguyen.hrm_6.ui.fragments.DepartmentListFragment;
import com.framgia.nguyen.hrm_6.ui.fragments.EmployeeListFragment;

/**
 * Created by binh on 6/6/16.
 */
public class PagerAdapter extends FragmentPagerAdapter {
    private static final int NUM_ITEMS = 2;

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return DepartmentListFragment.newInstance();
            case 1:
                return EmployeeListFragment.newInstance();
            default:
                return DepartmentListFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0: return "Departments";
            case 1: return "Employees";
            default: return "Departments";
        }
    }
}
