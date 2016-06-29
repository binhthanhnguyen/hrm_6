package com.framgia.nguyen.hrm_6.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.framgia.nguyen.hrm_6.R;
import com.framgia.nguyen.hrm_6.daos.DepartmentDAO;
import com.framgia.nguyen.hrm_6.models.Department;
import com.framgia.nguyen.hrm_6.ui.adapters.DepartmentListAdapter;
import com.framgia.nguyen.hrm_6.ui.dialogs.DepartmentDialogFragment;
import com.framgia.nguyen.hrm_6.ui.widget.recyclerview.DividerItemDecoration;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by binh on 6/6/16.
 */
public class DepartmentListFragment extends Fragment {
    private static final String TAG = "DepartmentListFragment";
    private static final String DEPARTMENT_DIALOG_FRAGMENT_TAG = "DEPARTMENT_DIALOG_FRAGMENT_TAG";

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
        setHasOptionsMenu(true);
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.department_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_new_department) {
            DepartmentDialogFragment dialogFragment = new DepartmentDialogFragment();
            dialogFragment.show(getFragmentManager(), DEPARTMENT_DIALOG_FRAGMENT_TAG);
            dialogFragment.setOnNewDepartmentListener(new DepartmentDialogFragment.OnNewDepartmentListener() {
                @Override
                public void onSave(String name, String description) {
                    if (DepartmentDAO.getInstance(getContext()).insert(new Department(name, description))) {
                        Toast.makeText(getContext(), R.string.message_create_success, Toast.LENGTH_LONG).show();
                        mDepartments.clear();
                        mDepartments.addAll(DepartmentDAO.getInstance(getContext()).getAllDepartments());
                        mRecyclerView.getAdapter().notifyDataSetChanged();
                    } else {
                        Toast.makeText(getContext(), R.string.message_create_fail, Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupAdapter() {
        if (isAdded()) mRecyclerView.setAdapter(new DepartmentListAdapter(mDepartments));
    }
}
