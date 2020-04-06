package com.example.sportclub.data;

public final class SportClubContract {
    private SportClubContract() {
    }

    public final static class MemberEntry{
        public static final String _NAME = "members";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_FIRST_NAME = "firstName";
        public static final String COLUMN_LAST_NAME = "lastName";
        public static final String COLUMN_GENDER = "gender";
        public static final String COLUMN_GROUP = "group";

        public static final int GENDER_UNKNOWN = 0;
        public static final int GENDER_MALE = 1;
        public static final int GENDER_FEMALE = 2;
    }
}
