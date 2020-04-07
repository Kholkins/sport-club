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
                Toast.makeText(getContext(),"Incorrect URI", Toast.LENGTH_LONG).show();
                throw new IllegalArgumentException("Can't query incorrect URI "+uri);
        }
        return cursor;
    }


    @Override
    public String getType( Uri uri) {
        return null;
    }


    @Override
    public Uri insert( Uri uri,  ContentValues values) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        int match = uriMatcher.match(uri);
        switch (match){
            case MEMBERS:
                long id = db.insert(MemberEntry._NAME,null,values);
                if (id == -1){
                    Log.e("Insert method", "insert: failed "+uri );
                }
                break;
            default:
                Toast.makeText(getContext(),"Incorrect URI", Toast.LENGTH_LONG).show();
                throw new IllegalArgumentException("Can't query incorrect URI "+uri);
        }
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
