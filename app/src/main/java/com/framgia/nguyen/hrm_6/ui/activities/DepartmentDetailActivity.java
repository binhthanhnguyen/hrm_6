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

import java.util.List;

/**
 * Created by binh on 6/7/16.
 */
public class DepartmentDetailActivity extends AppCompatActivity {
    private static final String TAG = "DepartmentDetailActivity";

    private static final String EXTRA_DEPARTMENT = "EXTRA_DEPARTMENT";
    private List<Employee> mEmployees;
    private RecyclerView mRecyclerView;
    private Department mDepartment;

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
        EmployeeDAO employeeDAO = EmployeeDAO.getInstance(getApplicationContext());
        mEmployees = employeeDAO.findEmployeesByDepartmentId(mDepartment.getId());

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
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new EmployeeListAdapter(mEmployees));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
    }
}
