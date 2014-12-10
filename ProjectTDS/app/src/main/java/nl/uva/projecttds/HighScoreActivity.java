package nl.uva.projecttds;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class HighScoreActivity extends ListActivity
{
    private ArrayList<String> results = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        View decorView;
        int uiOptions;

        // Set screen to fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Dim navigation bar
        decorView = getWindow().getDecorView();
        uiOptions = View.SYSTEM_UI_FLAG_LOW_PROFILE;
        decorView.setSystemUiVisibility(uiOptions);

        //setContentView(R.layout.activity_high_score);

        HighScoreDatabase dbHelper = new HighScoreDatabase(this);

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                HighScoreDatabase.COLUMN_NAME_NAME,
                HighScoreDatabase.COLUMN_NAME_SCORE
        };

        String sortOrder =
                HighScoreDatabase.COLUMN_NAME_SCORE + " DESC";

        Cursor c = db.query(
                HighScoreDatabase.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        if (c != null ) {
            if  (c.moveToFirst()) {
                do {
                    String name = c.getString(c.getColumnIndex(HighScoreDatabase.COLUMN_NAME_NAME));
                    int score = c.getInt(c.getColumnIndex(HighScoreDatabase.COLUMN_NAME_SCORE));
                    results.add("Name: " + name + ", Score: " + score);
                }while (c.moveToNext());
            }
        }

        TextView tView = new TextView(this);
        tView.setText("High Scores");
        getListView().addHeaderView(tView);

        setListAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, results));
        getListView().setTextFilterEnabled(true);
    }

    @Override
    public void onResume()
    {
        super.onResume();

        View decorView;
        int uiOptions;

        // Dim navigation bar when app resumes
        decorView = getWindow().getDecorView();
        uiOptions = View.SYSTEM_UI_FLAG_LOW_PROFILE;
        decorView.setSystemUiVisibility(uiOptions);
    }
}