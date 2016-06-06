package com.framgia.nguyen.hrm_6.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.framgia.nguyen.hrm_6.R;
import com.framgia.nguyen.hrm_6.daos.DepartmentDAO;
import com.framgia.nguyen.hrm_6.models.Department;
import com.framgia.nguyen.hrm_6.models.Employee;

/**
 * Created by binh on 6/9/16.
 */
public class EmployeeDetailActivity extends AppCompatActivity {
    private static final String TAG = "EmployeeDetailActivity";

    private static final String EXTRA_EMPLOYEE = "EXTRA_EMPLOYEE";
    private Employee mEmployee;

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
        mEmployee = (Employee) getIntent().getSerializableExtra(EXTRA_EMPLOYEE);
        setupView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    private void setupView() {
        TextView textName = (TextView) findViewById(R.id.text_name);
        textName.setText(mEmployee.getName());

        TextView textPosition = (TextView) findViewById(R.id.text_position);
        textPosition.setText(mEmployee.getPosition().desc());

        TextView textStatus = (TextView) findViewById(R.id.text_status);
        textStatus.setText(mEmployee.getStatus().desc());

        TextView textDepartment = (TextView) findViewById(R.id.text_department);
        Department department = DepartmentDAO.getInstance(getApplicationContext()).getDepartment(mEmployee.getDepartmentId());
        String departmentName = department.getName();
        textDepartment.setText(departmentName);

        TextView textPhone = (TextView) findViewById(R.id.text_phone);
        textPhone.setText(mEmployee.getPhone());

        TextView textAddress = (TextView) findViewById(R.id.text_address);
        textAddress.setText(mEmployee.getPlaceOfBirth());

        TextView textDateOfBirth = (TextView) findViewById(R.id.text_date_of_birth);
        textDateOfBirth.setText(mEmployee.getDateOfBirth());
    }
}
