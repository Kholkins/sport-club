package com.example.sportclub.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.example.sportclub.data.SportClubContract.MemberEntry;

public class SportClubContentProvider extends ContentProvider {

    SportClubOpenHelper dbOpenHelper;

    private static final int MEMBERS = 111;
    private static final int MEMBER_ID = 222;

    // Creates a UriMatcher object.
    private static final UriMatcher uriMatcher =
            new UriMatcher(UriMatcher.NO_MATCH);

    static {

        uriMatcher.addURI(SportClubContract.AUTHORITY,SportClubContract.PATH_MEMBERS, MEMBERS);
        uriMatcher.addURI(SportClubContract.AUTHORITY,SportClubContract.PATH_MEMBERS+"/#",MEMBER_ID);

    }

    @Override
    public boolean onCreate() {
        dbOpenHelper = new SportClubOpenHelper(getContext());
        return true;
    }

    @Override
    public Cursor query( Uri uri, String[] projection, String selection,  String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        Cursor cursor;

        int match = uriMatcher.match(uri);
        switch (match){
            case MEMBERS:
                cursor = db.query(MemberEntry._NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case MEMBER_ID:
                selection = MemberEntry.COLUMN_ID+"=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                cursor = db.query(MemberEntry._NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Can't query incorrect URI "+uri);
        }
        return cursor;
    }


    @Override
    public String getType( Uri uri) {
        int match = uriMatcher.match(uri);
        switch (match){
            case MEMBERS:
                return MemberEntry.CONTENT_MULTIPLE_ITEMS;
            case MEMBER_ID:
                return MemberEntry.CONTENT_SINGLE_ITEM;
            default:
                throw new IllegalArgumentException("Unknown URI "+uri);
        }
    }


    @Override
    public Uri insert( Uri uri,  ContentValues values) {

        String firstName = values.getAsString(MemberEntry.COLUMN_FIRST_NAME);
        if (firstName==null){
            throw new IllegalArgumentException("You have to input first name");
        }
        String lastName = values.getAsString(MemberEntry.COLUMN_LAST_NAME);
        if (lastName==null){
            throw new IllegalArgumentException("You have to input last name");
        }
        Integer gender = values.getAsInteger(MemberEntry.COLUMN_GENDER);
        if (gender == null || !(gender == MemberEntry.GENDER_UNKNOWN || gender ==
                MemberEntry.GENDER_MALE || gender == MemberEntry.GENDER_FEMALE)) {
            throw new IllegalArgumentException
                    ("You have to input correct gender");
        }
        String sport = values.getAsString(MemberEntry.COLUMN_SPORT);
        if (sport==null){
            throw new IllegalArgumentException("You have to input sport");
        }

        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        int match = uriMatcher.match(uri);
        switch (match){
            case MEMBERS:
                long id = db.insert(MemberEntry._NAME,null,values);
                if (id == -1){
                    Log.e("Insert method", "insert: failed "+uri );
                    return null;
                }
                ContentUris.withAppendedId(uri,id);
                break;
            default:
                throw new IllegalArgumentException("Can't query incorrect URI "+uri);
        }
        return null;
    }

    @Override
    public int delete( Uri uri,  String selection,  String[] selectionArgs) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();

        int match = uriMatcher.match(uri);
        switch (match){
            case MEMBERS:
                return db.delete(MemberEntry._NAME,selection,selectionArgs);
            case MEMBER_ID:
                selection = MemberEntry.COLUMN_ID+"=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                return db.delete(MemberEntry._NAME,selection,selectionArgs);
            default:
                throw new IllegalArgumentException("Can't query incorrect URI "+uri);
        }
    }

    @Override
    public int update( Uri uri, ContentValues values,  String selection,  String[] selectionArgs) {
        if (values.containsKey(MemberEntry.COLUMN_FIRST_NAME)) {
            String firstName = values.getAsString(MemberEntry.COLUMN_FIRST_NAME);
            if (firstName == null) {
                throw new IllegalArgumentException
                        ("You have to input first name");
            }
        }

        if (values.containsKey(MemberEntry.COLUMN_LAST_NAME)) {
            String lastName = values.getAsString(MemberEntry.COLUMN_LAST_NAME);
            if (lastName == null) {
                throw new IllegalArgumentException
                        ("You have to input last name");
            }
        }

        if (values.containsKey(MemberEntry.COLUMN_GENDER)) {
            Integer gender = values.getAsInteger(MemberEntry.COLUMN_GENDER);
            if (gender == null || !(gender == MemberEntry.GENDER_UNKNOWN || gender ==
                    MemberEntry.GENDER_MALE || gender == MemberEntry.GENDER_FEMALE)) {
                throw new IllegalArgumentException("You have to input correct gender");
            }
        }

        if (values.containsKey(MemberEntry.COLUMN_SPORT)) {
            String sport = values.getAsString(MemberEntry.COLUMN_SPORT);
            if (sport == null) {
                throw new IllegalArgumentException("You have to input sport");
            }
        }
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();

        int match = uriMatcher.match(uri);
        switch (match){
            case MEMBERS:
                return db.update(MemberEntry._NAME,values,selection,selectionArgs);
            case MEMBER_ID:
                selection = MemberEntry.COLUMN_ID+"=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                return db.update(MemberEntry._NAME,values,selection,selectionArgs);
            default:
                throw new IllegalArgumentException("Can't query incorrect URI "+uri);
        }

    }
}
