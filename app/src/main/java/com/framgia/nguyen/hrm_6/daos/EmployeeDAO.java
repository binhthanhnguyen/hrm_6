package com.framgia.nguyen.hrm_6.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import com.framgia.nguyen.hrm_6.db.DatabaseContract;
import com.framgia.nguyen.hrm_6.models.Department;
import com.framgia.nguyen.hrm_6.models.Employee;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nguyen on 2/23/16.
 */
public class EmployeeDAO extends DbContentProvider{
    private static EmployeeDAO sInstance = null;

    private EmployeeDAO(Context context) {
        super(context);
    }

    public static EmployeeDAO getInstance(Context context) {
        if (sInstance == null){
            sInstance = new EmployeeDAO(context);
        }
        return sInstance;
    }

    public boolean insert(Employee employee) throws SQLException {
        open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseContract.EmployeeTable.NAME, employee.getName());
        contentValues.put(DatabaseContract.EmployeeTable.DATE_OF_BIRTH, employee.getDateOfBirth());
        contentValues.put(DatabaseContract.EmployeeTable.PLACE_OF_BIRTH, employee.getPlaceOfBirth());
        contentValues.put(DatabaseContract.EmployeeTable.PHONE, employee.getPhone());
        contentValues.put(DatabaseContract.EmployeeTable.STATUS, employee.getStatus().code());
        contentValues.put(DatabaseContract.EmployeeTable.POSITION, employee.getPosition().code());
        contentValues.put(DatabaseContract.EmployeeTable.DEPARTMENT_ID, employee.getDepartmentId());
        try {
            mDatabase.insert(DatabaseContract.EmployeeTable.TABLE_NAME, null, contentValues);
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
        close();
        return true;
    }

    public Employee getEmployee(int employeeId) throws SQLException {
        open();
        Cursor cursor = mDatabase.query(DatabaseContract.DepartmentTable.TABLE_NAME, null,
                DatabaseContract.EmployeeTable.ID + " = ?",
                new String[]{String.valueOf(employeeId)}, null, null, null, null);
        if (cursor == null)
            cursor.moveToFirst();
        Employee employee = new Employee(cursor);
        close();
        return employee;
    }

    public List<Employee> findEmployeesByDepartmentId(int departmentId) {
        List<Employee> employees = new ArrayList<Employee>();
        open();
        Cursor cursor = mDatabase.query(DatabaseContract.EmployeeTable.TABLE_NAME, null,
                DatabaseContract.EmployeeTable.DEPARTMENT_ID + " = ?",
                new String[] {String.valueOf(departmentId)}, null, null, null, null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                employees.add(new Employee(cursor));
            }
        }
        cursor.close();
        close();
        return employees;
    }

    public List<Employee> getAllEmployees() throws SQLException {
        List<Employee> employees = new ArrayList<Employee>();
        open();
        Cursor cursor = mDatabase.query(DatabaseContract.EmployeeTable.TABLE_NAME, null, null, null, null, null, null );
        if (cursor.getCount() > 0){
            while (cursor.moveToNext()) {
                employees.add(new Employee(cursor));
            }
        }
        cursor.close();
        close();
        return employees;
    }

    public boolean update(Employee employee) throws SQLException {
        open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseContract.EmployeeTable.NAME, employee.getName());
        contentValues.put(DatabaseContract.EmployeeTable.DATE_OF_BIRTH, employee.getPlaceOfBirth());
        contentValues.put(DatabaseContract.EmployeeTable.PLACE_OF_BIRTH, employee.getDateOfBirth());
        contentValues.put(DatabaseContract.EmployeeTable.PHONE, employee.getPhone());
        contentValues.put(DatabaseContract.EmployeeTable.STATUS, employee.getStatus().code());
        contentValues.put(DatabaseContract.EmployeeTable.POSITION, employee.getPosition().code());
        contentValues.put(DatabaseContract.EmployeeTable.DEPARTMENT_ID, employee.getDepartmentId());

        try {
            mDatabase.update(DatabaseContract.EmployeeTable.TABLE_NAME, contentValues,
                    "id = ? ", new String[]{String.valueOf(employee.getId())});
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
        close();
        return true;
    }

    public boolean delete(Employee employee) throws SQLException {
        open();
        try {
            mDatabase.delete(DatabaseContract.EmployeeTable.TABLE_NAME, DatabaseContract.EmployeeTable.ID + " = ?", new String[]{String.valueOf(employee.getId())});
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        close();
        return true;
    }
}
