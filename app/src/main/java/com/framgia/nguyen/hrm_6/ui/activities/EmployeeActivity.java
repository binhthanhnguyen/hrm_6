package com.framgia.nguyen.hrm_6.ui.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.framgia.nguyen.hrm_6.R;
import com.framgia.nguyen.hrm_6.daos.DepartmentDAO;
import com.framgia.nguyen.hrm_6.daos.EmployeeDAO;
import com.framgia.nguyen.hrm_6.models.Department;
import com.framgia.nguyen.hrm_6.models.Employee;
import com.framgia.nguyen.hrm_6.models.Position;
import com.framgia.nguyen.hrm_6.models.Status;
import com.framgia.nguyen.hrm_6.ui.dialogs.DeleteEmployeeDialogFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by binh on 6/21/16.
 */
public class EmployeeActivity extends AppCompatActivity {
    private static final String TAG = "EmployeeActivity";
    public static final int DATE_PICKER_DIALOG_ID = 999;
    public static final String CONFIRM_DIALOG_TAG = "CONFIRM_DIALOG_TAG";
    public static final int NEW_EMPLOYEE_REQUEST = 0;
    public static final String EXTRA_DEPARTMENT_ID = "EXTRA_DEPARTMENT_ID";
    public static final String EXTRA_EMPLOYEE = "EXTRA_EMPLOYEE";

    private TextView mTextDateOfBirth;
    private EditText mEditName;
    private EditText mEditPhone;
    private EditText mEditPlaceOfBirth;
    private Button mButtonDelete;
    private int mDepartmentId;
    private Position mPosition;
    private Status mStatus;
    private Employee mEmployee;
    private boolean mEditMode;
    private int mYear;
    private int mMonth;
    private int mDay;

    public static Intent newIntent(Context context, int departmentId) {
        Intent intent = new Intent(context, EmployeeActivity.class);
        intent.putExtra(EXTRA_DEPARTMENT_ID, departmentId);
        ((Activity) context).startActivityForResult(intent, NEW_EMPLOYEE_REQUEST);
        return intent;
    }

    public static Intent newIntent(Context context, Employee employee) {
        Intent intent = new Intent(context, EmployeeActivity.class);
        intent.putExtra(EXTRA_EMPLOYEE, employee);
        context.startActivity(intent);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);
        setupView();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_cancel);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.employee_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_done:
                saveEmployee();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == DATE_PICKER_DIALOG_ID)
            return new DatePickerDialog(this, dateSetListener, mYear, mMonth, mDay);
        return null;
    }

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            mTextDateOfBirth.setText(new StringBuilder().append(mMonth + 1)
                    .append("-").append(mDay).append("-").append(mYear));
        }
    };

    private void setupView() {
        Intent intent = getIntent();
        mDepartmentId = intent.getIntExtra(EXTRA_DEPARTMENT_ID, 0);
        mEmployee = (Employee) intent.getSerializableExtra(EXTRA_EMPLOYEE);
        if (mEmployee != null) mEditMode = true;

        Spinner spinnerDepartment = (Spinner) findViewById(R.id.spinner_department);
        final List<Department> departments = DepartmentDAO.getInstance(this).getAllDepartments();
        ArrayAdapter<Department> departmentAdapter = new ArrayAdapter<Department>(this,
                android.R.layout.simple_spinner_item, departments);
        departmentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDepartment.setAdapter(departmentAdapter);
        spinnerDepartment.setSelection(mDepartmentId - 1);
        spinnerDepartment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mDepartmentId = departments.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner spinnerPosition = (Spinner) findViewById(R.id.spinner_position);
        final Position[] positions = Position.values();
        ArrayAdapter<Position> positionAdapter = new ArrayAdapter<Position>(this, android.R.layout.simple_spinner_item, positions);
        positionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPosition.setAdapter(positionAdapter);
        spinnerPosition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mPosition = positions[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner spinnerStatus = (Spinner) findViewById(R.id.spinner_status);
        final Status[] statuses = Status.values();
        ArrayAdapter<Status> statusAdapter = new ArrayAdapter<Status>(this, android.R.layout.simple_spinner_item, statuses);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(statusAdapter);
        spinnerStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mStatus = statuses[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mTextDateOfBirth = (TextView) findViewById(R.id.text_date_of_birth);
        mTextDateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCreateDialog(DATE_PICKER_DIALOG_ID).show();
            }
        });

        mEditName = (EditText) findViewById(R.id.edit_name);
        mEditPhone = (EditText) findViewById(R.id.edit_phone);
        mEditPlaceOfBirth = (EditText) findViewById(R.id.edit_place_of_birth);
        mButtonDelete = (Button) findViewById(R.id.button_delete);
        mButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteEmployeeDialogFragment dialogFragment = DeleteEmployeeDialogFragment.newInstance(mEmployee.getId());
                dialogFragment.show(getSupportFragmentManager(), CONFIRM_DIALOG_TAG);
            }
        });

        Calendar calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);

        if (mEditMode) {
            mButtonDelete.setVisibility(View.VISIBLE);
            mEditName.setText(mEmployee.getName());
            mEditPhone.setText(mEmployee.getPhone());
            mEditPlaceOfBirth.setText(mEmployee.getPlaceOfBirth());
            mTextDateOfBirth.setText(mEmployee.getDateOfBirth());
            spinnerDepartment.setSelection(mEmployee.getDepartmentId() -1);
            spinnerPosition.setSelection(mEmployee.getPosition().code());
            spinnerStatus.setSelection(mEmployee.getStatus().code());
            if (!mEmployee.getDateOfBirth().isEmpty()) {
                String date[] = mEmployee.getDateOfBirth().trim().split("-");
                mMonth = Integer.parseInt(date[0]) - 1;
                mDay = Integer.parseInt(date[1]);
                mYear = Integer.parseInt(date[2]);
            }
        }
    }

    private void saveEmployee() {
        String dateOfBirth = mTextDateOfBirth.getText().toString();
        String name = mEditName.getText().toString().trim();
        String placeOfBirth = mEditPlaceOfBirth.getText().toString().trim();
        String phone = mEditPhone.getText().toString().trim();
        if (!name.isEmpty()) {
            if (mEditMode) {
                mEmployee.setName(name);
                mEmployee.setDateOfBirth(dateOfBirth);
                mEmployee.setDepartmentId(mDepartmentId);
                mEmployee.setPosition(mPosition);
                mEmployee.setStatus(mStatus);
                mEmployee.setPhone(phone);
                mEmployee.setPlaceOfBirth(placeOfBirth);
                EmployeeDAO.getInstance(this).update(mEmployee);
                Toast.makeText(this, R.string.message_update_success, Toast.LENGTH_LONG).show();
                finish();
            } else {
                Employee employee = new Employee(dateOfBirth, name, placeOfBirth, mPosition, mStatus, phone, mDepartmentId);
                EmployeeDAO.getInstance(this).insert(employee);
                Toast.makeText(this, R.string.message_add_success, Toast.LENGTH_LONG).show();
                finish();
            }
        } else {
            Toast.makeText(this, R.string.message_name_empty, Toast.LENGTH_LONG).show();
            onBackPressed();
        }
    }
}
