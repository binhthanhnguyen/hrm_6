package com.framgia.nguyen.hrm_6.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import com.framgia.nguyen.hrm_6.R;
import com.framgia.nguyen.hrm_6.daos.EmployeeDAO;
import com.framgia.nguyen.hrm_6.models.Department;
import com.framgia.nguyen.hrm_6.models.Employee;
import com.framgia.nguyen.hrm_6.ui.adapters.EmployeeListAdapter;
import com.framgia.nguyen.hrm_6.ui.widget.recyclerview.DividerItemDecoration;
import com.framgia.nguyen.hrm_6.ui.widget.recyclerview.EndlessRecyclerViewScrollListener;

import java.util.List;

/**
 * Created by binh on 6/7/16.
 */
public class DepartmentDetailActivity extends AppCompatActivity {
    private static final String TAG = "DepartmentDetailActivity";
    public static final int PER_PAGE = 30;

    private static final String EXTRA_DEPARTMENT = "EXTRA_DEPARTMENT";
    private List<Employee> mEmployees;
    private RecyclerView mRecyclerView;
    private Department mDepartment;
    private EmployeeDAO mEmployeeDAO;

    public static Intent newIntent(Context context, Department department) {
        Intent intent = new Intent(context, DepartmentDetailActivity.class);
        intent.putExtra(EXTRA_DEPARTMENT, department);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        mDepartment = (Department) intent.getSerializableExtra(EXTRA_DEPARTMENT);
        mEmployeeDAO = EmployeeDAO.getInstance(getApplicationContext());
        mEmployees = mEmployeeDAO.findEmployeesByDepartmentId(mDepartment.getId(), 0, PER_PAGE);

        setupView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
            default: return super.onOptionsItemSelected(item);
        }
    }

    private void setupView() {
        TextView textName = (TextView) findViewById(R.id.text_name);
        textName.setText(mDepartment.getName());
        TextView textDesc = (TextView) findViewById(R.id.text_desc);
        textDesc.setText(mDepartment.getDesc());

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        final EmployeeListAdapter employeeListAdapter = new EmployeeListAdapter(mEmployees);
        mRecyclerView.setAdapter(employeeListAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                mEmployees.addAll(mEmployeeDAO.findEmployeesByDepartmentId(mDepartment.getId(), totalItemsCount, PER_PAGE));
                employeeListAdapter.notifyItemRangeChanged(totalItemsCount, mEmployees.size() -1);
            }
        });
    }
}
