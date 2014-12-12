/* * *
 * Name: Mike Trieu
 * Student-ID: 6366295 / 10105093
 *
 * HighScoreActivity.java
 *
 * Displays a list of high scores from a database
 * */

package nl.uva.projecttds;

import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.TypedValue;
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

        HighScoreDatabase dbHelper;
        SQLiteDatabase db;
        TextView titleView;
        String sortOrder;
        Cursor c;
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

        // Get database
        dbHelper = new HighScoreDatabase(this);
        db = dbHelper.getReadableDatabase();

        // Select needed columns from query
        String[] projection = {
                HighScoreDatabase.COLUMN_NAME_NAME,
                HighScoreDatabase.COLUMN_NAME_SCORE
        };

        // Specify order of returning query
        sortOrder =
                HighScoreDatabase.COLUMN_NAME_SCORE + " DESC";

        // Actual query
        c = db.query(
                HighScoreDatabase.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );

        // Read Cursor c, put read name and score in ArrayList
        if (c != null )
        {
            if  (c.moveToFirst())
            {
                do
                {
                    String name = c.getString(c.getColumnIndex(
                            HighScoreDatabase.COLUMN_NAME_NAME
                    ));
                    int score = c.getInt(c.getColumnIndex(
                            HighScoreDatabase.COLUMN_NAME_SCORE
                    ));

                    results.add("Name: " + name + ", Score: " + score);
                }
                while (c.moveToNext());
            }
        }

        // Display list of high scores
        titleView = new TextView(this);
        titleView.setText("High Scores (Press back button to go back)");
        titleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);

        getListView().addHeaderView(titleView);

        setListAdapter(new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, results
        ));

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