package com.framgia.nguyen.hrm_6.daos;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.framgia.nguyen.hrm_6.db.DatabaseHelpler;

/**
 * Created by nguyen on 2/23/16.
 */
public abstract class DbContentProvider {
    protected SQLiteDatabase mDatabase;
    private DatabaseHelpler mDatabaseHelpler;

    public DbContentProvider(Context context){
        mDatabaseHelpler = new DatabaseHelpler(context);
    }

    public void open() throws SQLException {
        mDatabase = mDatabaseHelpler.getWritableDatabase();
    }

    public void close() {
        mDatabaseHelpler.close();
    }
}
