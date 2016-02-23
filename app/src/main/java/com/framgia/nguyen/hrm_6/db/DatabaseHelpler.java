package com.framgia.nguyen.hrm_6.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by nguyen on 2/23/16.
 */
public class DatabaseHelpler extends SQLiteOpenHelper{

    /**
     * DATABASE VERSION
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * TABLE STRINGS
     */
    private static final String TEXT_TYPE = " TEXT";
    private static final String DATE_TYPE = " DATE";
    private static final String DATETIME_TYPE = " DATETIME";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String COMMA = ", ";

    /**
     * SQL CREATE TABLE EMPLOYEE sentence
     */
    private static final String CREATE_EMPLOYEE_TABLE = "CREATE TABLE "
            + DatabaseContract.EmployeeTable.TABLE_NAME + " ("
            + DatabaseContract.EmployeeTable.NAME + TEXT_TYPE + COMMA
            + DatabaseContract.EmployeeTable.PLACE_OF_BIRTH + TEXT_TYPE + COMMA
            + DatabaseContract.EmployeeTable.DATE_OF_BIRTH + DATE_TYPE + COMMA
            + DatabaseContract.EmployeeTable.PHONE + TEXT_TYPE + COMMA
            + DatabaseContract.EmployeeTable.POSITION + INTEGER_TYPE + COMMA
            + DatabaseContract.EmployeeTable.STATUS + INTEGER_TYPE + COMMA
            + DatabaseContract.EmployeeTable.DEPARTMENT_ID + INTEGER_TYPE + " )";

    /**
     * SQL CREATE TABLE DEPARTMENT sentence
     */
    private static final String CREATE_DEPARTMENT_TABLE = "CREATE TABLE "
            + DatabaseContract.DepartmentTable.TABLE_NAME + " ("
            + DatabaseContract.DepartmentTable.NAME + TEXT_TYPE + COMMA
            + DatabaseContract.DepartmentTable.DESC + TEXT_TYPE + " )";

    /**
     * SQL DELETE TABLE SENTENCES
     */
    public static final String DROP_EMPLOYEE_TABLE = "DROP TABLE IF EXISTS "+ DatabaseContract.EmployeeTable.TABLE_NAME;
    public static final String DROP_DEPARTMENT_TABLE = "DROP TABLE IF EXISTS "+ DatabaseContract.DepartmentTable.TABLE_NAME;

    public DatabaseHelpler(Context context) {
        super(context, DatabaseContract.DB_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_EMPLOYEE_TABLE);
        db.execSQL(CREATE_DEPARTMENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_EMPLOYEE_TABLE);
        db.execSQL(DROP_DEPARTMENT_TABLE);
        onCreate(db);
    }
}
