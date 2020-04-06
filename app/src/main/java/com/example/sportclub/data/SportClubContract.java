package com.example.sportclub.data;

import android.provider.BaseColumns;

public final class SportClubContract implements BaseColumns {
    private SportClubContract() {
    }

    public final static class MemberEntry{
        public static final String _NAME = "members";
        public static final String COLUMN_ID = BaseColumns._ID;
        public static final String COLUMN_FIRST_NAME = "firstName";
        public static final String COLUMN_LAST_NAME = "lastName";
        public static final String COLUMN_GENDER = "gender";
        public static final String COLUMN_GROUP = "group";

        public static final int GENDER_UNKNOWN = 0;
        public static final int GENDER_MALE = 1;
        public static final int GENDER_FEMALE = 2;
    }
}
