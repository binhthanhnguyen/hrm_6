package com.framgia.nguyen.hrm_6.db;

/**
 * Created by nguyen on 2/23/16.
 */
public class DatabaseContract {
    public static final String DB_NAME = "hrm.db";

    public abstract class EmployeeTable {
        public static final String TABLE_NAME = "employees";

        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String DATE_OF_BIRTH = "date_of_birth";
        public static final String PLACE_OF_BIRTH = "place_of_birth";
        public static final String POSITION = "position";
        public static final String STATUS = "status";
        public static final String PHONE = "phone";
        public static final String DEPARTMENT_ID = "department_id";

    }

    public abstract class DepartmentTable {
        public static final String TABLE_NAME = "departments";

        public static final String ID = "id";
        public static final String NAME = "name";
        public static final String DESC = "description";

    }
}
