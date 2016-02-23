package com.framgia.nguyen.hrm_6.models;

import android.database.Cursor;

import com.framgia.nguyen.hrm_6.db.DatabaseContract;

/**
 * Created by nguyen on 2/23/16.
 */
public class Employee {
    private int mId;
    private String mName;
    private String mDateOfBirth;
    private String mPlaceOfBirth;
    private Position mPosition;
    private Status mStatus;
    private String mPhone;
    private int mDepartmentId;

    public Employee(Cursor cursor){
        this.mId = cursor.getInt(0);
        this.mName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.EmployeeTable.NAME));
        this.mDateOfBirth = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.EmployeeTable.DATE_OF_BIRTH));
        this.mPlaceOfBirth = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.EmployeeTable.PLACE_OF_BIRTH));
        this.mPhone = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.EmployeeTable.PHONE));
        this.mStatus = Status.parseStatus(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.EmployeeTable.STATUS)));
        this.mPosition = Position.parsePosition(cursor.getColumnIndexOrThrow(DatabaseContract.EmployeeTable.POSITION));
        this.mDepartmentId = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.EmployeeTable.DEPARTMENT_ID));
    }

    public Employee(int mId, String mDateOfBirth, String mName, String mPlaceOfBirth, Position mPosition, Status mStatus, String mPhone, int mDepartmentId) {
        this.mId = mId;
        this.mDateOfBirth = mDateOfBirth;
        this.mName = mName;
        this.mPlaceOfBirth = mPlaceOfBirth;
        this.mPosition = mPosition;
        this.mStatus = mStatus;
        this.mPhone = mPhone;
        this.mDepartmentId = mDepartmentId;
    }

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getDateOfBirth() {
        return mDateOfBirth;
    }

    public void setDateOfBirth(String mDateOfBirth) {
        this.mDateOfBirth = mDateOfBirth;
    }

    public String getPlaceOfBirth() {
        return mPlaceOfBirth;
    }

    public void setPlaceOfBirth(String mPlaceOfBirth) {
        this.mPlaceOfBirth = mPlaceOfBirth;
    }

    public Position getPosition() {
        return mPosition;
    }

    public void setPosition(Position mPosition) {
        this.mPosition = mPosition;
    }

    public Status getStatus() {
        return mStatus;
    }

    public void setStatus(Status mStatus) {
        this.mStatus = mStatus;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String mPhone) {
        this.mPhone = mPhone;
    }

    public int getDepartmentId() {
        return mDepartmentId;
    }

    public void setDepartmentId(int mDepartmentId) {
        this.mDepartmentId = mDepartmentId;
    }
}
