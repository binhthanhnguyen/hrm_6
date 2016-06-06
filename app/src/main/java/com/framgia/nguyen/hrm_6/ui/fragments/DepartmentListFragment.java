package com.framgia.nguyen.hrm_6.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.framgia.nguyen.hrm_6.R;

/**
 * Created by binh on 6/6/16.
 */
public class DepartmentListFragment extends Fragment {
    private static final String TAG = "DepartmentListFragment";
    private RecyclerView mRecyclerView;

    public static DepartmentListFragment newInstance() {
        DepartmentListFragment fragment = new DepartmentListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_department_list, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        return view;
    }
}
