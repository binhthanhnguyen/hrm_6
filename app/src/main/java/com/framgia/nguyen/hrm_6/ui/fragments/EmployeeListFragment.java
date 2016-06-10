package com.framgia.nguyen.hrm_6.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by binh on 6/6/16.
 */
public class EmployeeListFragment extends Fragment {
    private static final String TAG = "EmployeeListFragment";

    public static EmployeeListFragment newInstance() {
        EmployeeListFragment fragment = new EmployeeListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
