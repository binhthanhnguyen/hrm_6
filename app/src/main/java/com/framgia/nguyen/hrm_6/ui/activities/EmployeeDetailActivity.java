package com.framgia.nguyen.hrm_6.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.framgia.nguyen.hrm_6.R;
import com.framgia.nguyen.hrm_6.daos.DepartmentDAO;
import com.framgia.nguyen.hrm_6.daos.EmployeeDAO;
import com.framgia.nguyen.hrm_6.models.Department;
import com.framgia.nguyen.hrm_6.models.Employee;

/**
 * Created by binh on 6/9/16.
 */
public class EmployeeDetailActivity extends AppCompatActivity {
    private static final String TAG = "EmployeeDetailActivity";

    private static final String EXTRA_EMPLOYEE = "EXTRA_EMPLOYEE";
    private Employee mEmployee;
    private TextView mTextName;
    private TextView mTextDepartment;
    private TextView mTextPosition;
    private TextView mTextStatus;
    private TextView mTextPhone;
    private TextView mTextAddress;
    private TextView mTextDateOfBirth;

    public static Intent newIntent(Context context, Employee employee) {
        Intent intent = new Intent(context, EmployeeDetailActivity.class);
        intent.putExtra(EXTRA_EMPLOYEE, employee);
        context.startActivity(intent);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setupView();
        mEmployee = (Employee) getIntent().getSerializableExtra(EXTRA_EMPLOYEE);
        updateUi();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.action_edit:
                EmployeeActivity.newIntent(this, mEmployee);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mEmployee = EmployeeDAO.getInstance(this).getEmployee(mEmployee.getId());
        if (mEmployee == null) {
            finish();
        } else {
            updateUi();
        }
    }

    private void setupView() {
        mTextName = (TextView) findViewById(R.id.text_name);
        mTextPosition = (TextView) findViewById(R.id.text_position);
        mTextStatus = (TextView) findViewById(R.id.text_status);
        mTextDepartment = (TextView) findViewById(R.id.text_department);
        mTextPhone = (TextView) findViewById(R.id.text_phone);
        mTextAddress = (TextView) findViewById(R.id.text_address);
        mTextDateOfBirth = (TextView) findViewById(R.id.text_date_of_birth);
    }

    private void updateUi() {
        mTextName.setText(mEmployee.getName());
        mTextPosition.setText(mEmployee.getPosition().desc());
        mTextStatus.setText(mEmployee.getStatus().desc());
        Department department = DepartmentDAO.getInstance(getApplicationContext()).getDepartment(mEmployee.getDepartmentId());
        String departmentName = department.getName();
        mTextDepartment.setText(departmentName);
        mTextPhone.setText(mEmployee.getPhone());
        mTextAddress.setText(mEmployee.getPlaceOfBirth());
        mTextDateOfBirth.setText(mEmployee.getDateOfBirth());
    }
}
