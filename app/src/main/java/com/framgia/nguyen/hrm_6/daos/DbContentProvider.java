package com.framgia.nguyen.hrm_6.daos;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.framgia.nguyen.hrm_6.db.DatabaseHelper;

/**
 * Created by nguyen on 2/23/16.
 */
public abstract class DbContentProvider {
    protected SQLiteDatabase mDatabase;
    private DatabaseHelper mDatabaseHelper;

    public DbContentProvider(Context context){
        mDatabaseHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        mDatabase = mDatabaseHelper.getWritableDatabase();
    }

    public void close() {
        mDatabaseHelper.close();
    }
}
