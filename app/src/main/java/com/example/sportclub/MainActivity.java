package com.example.sportclub;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.example.sportclub.data.SportClubContract.MemberEntry;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    ListView listViewData;

    private static final int MEMBER_LOADER =123;
    MemberCursorAdapter memberCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewData = findViewById(R.id.listViewData);

        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddMemberActivity.class);
                startActivity(intent);
            }
        });

        memberCursorAdapter = new MemberCursorAdapter(this,null, false);
        listViewData.setAdapter(memberCursorAdapter);

        getSupportLoaderManager().initLoader(MEMBER_LOADER,null,this);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {

        String[] projection = {
                MemberEntry.COLUMN_ID,
                MemberEntry.COLUMN_FIRST_NAME,
                MemberEntry.COLUMN_LAST_NAME,
                MemberEntry.COLUMN_GENDER,
                MemberEntry.COLUMN_SPORT
        };
        CursorLoader cursorLoader = new CursorLoader(this,MemberEntry.CONTENT_URI,projection,null ,null,null);

        return cursorLoader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        memberCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        memberCursorAdapter.swapCursor(null);
    }
}
