package com.example.sportclub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.sax.StartElementListener;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.example.sportclub.data.SportClubContract.MemberEntry;

public class MainActivity extends AppCompatActivity {

    TextView textViewData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewData = findViewById(R.id.textViewData);

        FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddMemberActivity.class);
                startActivity(intent);
            }
        });
    }

    private void displayData(){
        String[] projection = {
                MemberEntry.COLUMN_ID,
                MemberEntry.COLUMN_FIRST_NAME,
                MemberEntry.COLUMN_LAST_NAME,
                MemberEntry.COLUMN_GENDER,
                MemberEntry.COLUMN_SPORT
        };
        Cursor cursor = getContentResolver().query(MemberEntry.CONTENT_URI,projection,null ,null,null);

        textViewData.setText("All members/n/n");
        textViewData.append(MemberEntry.COLUMN_ID +" "+
                MemberEntry.COLUMN_FIRST_NAME +" "+
                MemberEntry.COLUMN_LAST_NAME +" "+
                MemberEntry.COLUMN_GENDER +" "+
                MemberEntry.COLUMN_SPORT);

        int idIndex = cursor.getColumnIndex(MemberEntry.COLUMN_ID);
        int idFirstName = cursor.getColumnIndex(MemberEntry.COLUMN_FIRST_NAME);
        int idLastName = cursor.getColumnIndex(MemberEntry.COLUMN_LAST_NAME);
        int idGender = cursor.getColumnIndex(MemberEntry.COLUMN_GENDER);
        int idSport = cursor.getColumnIndex(MemberEntry.COLUMN_SPORT);
    }
}
