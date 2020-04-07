package com.example.sportclub.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

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

                break;
            default:

        }
        return null;
    }


    @Override
    public String getType( Uri uri) {
        return null;
    }


    @Override
    public Uri insert( Uri uri,  ContentValues values) {
        return null;
    }

    @Override
    public int delete( Uri uri,  String selection,  String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update( Uri uri, ContentValues values,  String selection,  String[] selectionArgs) {
        return 0;
    }
}
