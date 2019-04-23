package com.example.android.habittracker.data;

import android.provider.BaseColumns;

/**
 * Created by Dell on 6/28/2017.
 */

public final class UserContract {

    private UserContract() {
    }

    public static final class UserEntry implements BaseColumns {


        public static final String TABLE_NAME = "users";

        public static final String _ID = BaseColumns._ID;

        public static final String COLUMN_USER_NAME = "name";

        public static final String COLUMN_USER_MOBILE = "mobile_no";

        public static final String COLUMN_USER_GENDER = "gender";

        public static final String COLUMN_USER_AGE = "age";

        public static final int GENDER_UNKNOWN = 0;

        public static final int GENDER_MALE = 1;

        public static final int GENDER_FEMALE = 2;
    }
}
