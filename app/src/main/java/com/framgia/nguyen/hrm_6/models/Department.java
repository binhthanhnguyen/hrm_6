package com.framgia.nguyen.hrm_6.models;

import android.database.Cursor;

import com.framgia.nguyen.hrm_6.db.DatabaseContract;

import java.io.Serializable;

/**
 * Created by nguyen on 2/23/16.
 */
public class Department implements Serializable{
    private int mId;
    private String mName;
    private String mDesc;

    public Department(Cursor cursor){
        this.mId = cursor.getInt(0);
        this.mName = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.DepartmentTable.NAME));
        this.mDesc = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.DepartmentTable.DESC));
    }

    public Department(int mId, String mName) {
        this.mId = mId;
        this.mName = mName;
    }

    public Department(int mId, String mName, String mDesc) {
        this(mId,mName);
        this.mDesc = mDesc;
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

    public String getDesc() {
        return mDesc;
    }

    public void setDesc(String mDesc) {
        this.mDesc = mDesc;
    }

    @Override
    public String toString() {
        return mName;
    }
}
