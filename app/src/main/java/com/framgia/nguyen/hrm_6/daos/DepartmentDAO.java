package com.framgia.nguyen.hrm_6.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.framgia.nguyen.hrm_6.db.DatabaseContract;
import com.framgia.nguyen.hrm_6.db.DatabaseHelpler;
import com.framgia.nguyen.hrm_6.models.Department;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nguyen on 2/23/16.
 */
public class DepartmentDAO extends DbContentProvider{
    private static DepartmentDAO sInstance = null;

    private DepartmentDAO(Context context){
        super(context);
    }

    public static DepartmentDAO getInstance(Context context){
        if (sInstance == null){
            sInstance = new DepartmentDAO(context.getApplicationContext());
        }
        return  sInstance;
    }

    public boolean insert(Department department) throws SQLException{
        open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseContract.DepartmentTable.NAME, department.getName());
        contentValues.put(DatabaseContract.DepartmentTable.DESC, department.getDesc());
        try {
            mDatabase.insert(DatabaseContract.DepartmentTable.TABLE_NAME, null, contentValues);
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
        close();
        return true;
    }

    public Department getDeparment(int departmentId) throws SQLException {
        Cursor cursor = mDatabase.query(DatabaseContract.DepartmentTable.TABLE_NAME,
                new String[]{"id", DatabaseContract.DepartmentTable.NAME, DatabaseContract.DepartmentTable.DESC}, "id = ?",
                new String[]{String.valueOf(departmentId)}, null, null, null, null);
        if (cursor == null)
            cursor.moveToFirst();
        Department department = new Department(cursor);
        return department;
    }

    public List<Department> getAllDepartments() throws SQLException{
        List<Department> departments = new ArrayList<Department>();
        Cursor cursor = mDatabase.query(DatabaseContract.DepartmentTable.TABLE_NAME, null, null, null, null, null, null );
        if (cursor.getCount() > 0){
            while (cursor.moveToNext()) {
                departments.add(new Department(cursor));
            }
        }
        cursor.close();
        return departments;
    }

    public boolean update(Department department) throws SQLException{
        open();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseContract.DepartmentTable.NAME, department.getName());
        contentValues.put(DatabaseContract.DepartmentTable.DESC, department.getDesc());
        try {
            mDatabase.update(DatabaseContract.DepartmentTable.TABLE_NAME, contentValues,
                    "id = ? ", new String[]{String.valueOf(department.getId())});
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        close();
        return true;

    }

    public boolean delete(Department department) throws SQLException{
        open();
        try {
            mDatabase.delete(DatabaseContract.DepartmentTable.TABLE_NAME, "id = ?", new String[]{String.valueOf(department.getId())});
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        close();
        return true;
    }
}
