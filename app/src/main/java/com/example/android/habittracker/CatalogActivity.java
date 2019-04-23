package com.example.android.habittracker;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.android.habittracker.data.UserContract;
import com.example.android.habittracker.data.UserDbHelper;

public class CatalogActivity extends AppCompatActivity {

    public static final String TAG = CatalogActivity.class.getSimpleName();

    private UserDbHelper mDbHelper;

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });
        mDbHelper = new UserDbHelper(this);
    }

    @Override
    protected void onStart(){
        super.onStart();
        displayDatabaseInfo();
    }

    private void displayDatabaseInfo() {

        db = mDbHelper.getReadableDatabase();

        Cursor cursor = readCursor();
        try {

            TextView displayView = (TextView) findViewById(R.id.text_view_user);
            displayView.setText("Number of rows in users database table is: " + cursor.getCount());

            displayView.setText("\nThe users table contains "+cursor.getCount() + " users data.\n\n\n");
            displayView.append(UserContract.UserEntry._ID + "-"
                    + UserContract.UserEntry.COLUMN_USER_NAME + "-"
                    + UserContract.UserEntry.COLUMN_USER_MOBILE + "-"
                    + UserContract.UserEntry.COLUMN_USER_GENDER + "-"
                    + UserContract.UserEntry.COLUMN_USER_AGE);

            int idColumnIndex = cursor.getColumnIndex(UserContract.UserEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(UserContract.UserEntry.COLUMN_USER_NAME);
            int rollnoColumnIndex = cursor.getColumnIndex(UserContract.UserEntry.COLUMN_USER_MOBILE);
            int genderColumnIndex = cursor.getColumnIndex(UserContract.UserEntry.COLUMN_USER_GENDER);
            int ageColumnIndex = cursor.getColumnIndex(UserContract.UserEntry.COLUMN_USER_AGE);

            while (cursor.moveToNext()){
                Log.i(TAG,"Entered cursor section");
                int currentId = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                String currentRollNo = cursor.getString(rollnoColumnIndex);
                int currentGender = cursor.getInt(genderColumnIndex);
                int currentAge = cursor.getInt(ageColumnIndex);

                displayView.append("\n" + currentId + "-" + currentName + "-" + currentRollNo + "-"
                        + currentGender + "-" + currentAge);
            }

        } finally {
            cursor.close();
        }
    }

    public Cursor readCursor(){
        String[] projection ={UserContract.UserEntry._ID,
                UserContract.UserEntry.COLUMN_USER_NAME,
                UserContract.UserEntry.COLUMN_USER_MOBILE,
                UserContract.UserEntry.COLUMN_USER_GENDER,
                UserContract.UserEntry.COLUMN_USER_AGE};
        Cursor cursor = db.query(UserContract.UserEntry.TABLE_NAME, projection, null, null, null, null, null);

        return cursor;
    }

    private void insertUser(){

        SQLiteDatabase db1  = mDbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(UserContract.UserEntry.COLUMN_USER_NAME,"Rubeena");
        contentValues.put(UserContract.UserEntry.COLUMN_USER_MOBILE,"0123456789");
        contentValues.put(UserContract.UserEntry.COLUMN_USER_GENDER, UserContract.UserEntry.GENDER_FEMALE);
        contentValues.put(UserContract.UserEntry.COLUMN_USER_AGE,20);

        long newRowId = db1.insert(UserContract.UserEntry.TABLE_NAME,null,contentValues);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_insert_dummy_data:
                insertUser();
                displayDatabaseInfo();
                return true;
            case R.id.action_delete_all_entries:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
