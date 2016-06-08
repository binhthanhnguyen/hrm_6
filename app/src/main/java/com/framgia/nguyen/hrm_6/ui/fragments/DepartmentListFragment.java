package com.framgia.nguyen.hrm_6.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.framgia.nguyen.hrm_6.R;
import com.framgia.nguyen.hrm_6.daos.DepartmentDAO;
import com.framgia.nguyen.hrm_6.models.Department;
import com.framgia.nguyen.hrm_6.ui.adapters.DepartmentListAdapter;
import com.framgia.nguyen.hrm_6.ui.widget.recyclerview.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by binh on 6/6/16.
 */
public class DepartmentListFragment extends Fragment {
    private static final String TAG = "DepartmentListFragment";

    private RecyclerView mRecyclerView;
    private List<Department> mDepartments = new ArrayList<>();

    public static DepartmentListFragment newInstance() {
        DepartmentListFragment fragment = new DepartmentListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        DepartmentDAO departmentDAO = DepartmentDAO.getInstance(getContext());
        mDepartments  = departmentDAO.getAllDepartments();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_department_list, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        RecyclerView.ItemDecoration itemDecoration = new  DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST);
        mRecyclerView.addItemDecoration(itemDecoration);
        setupAdapter();
        return view;
    }

    private void setupAdapter() {
        if (isAdded()) mRecyclerView.setAdapter(new DepartmentListAdapter(mDepartments));
    }
}
