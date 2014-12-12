/* * *
 * Name: Mike Trieu
 * Student-ID: 6366295 / 10105093
 *
 * GameOverActivity.java
 *
 * Displays final score
 * Enters that score and player name to database
 * Also contains functions for the buttons in activity_game_over.xml
 * */

package nl.uva.projecttds;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class GameOverActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        TextView scoreView;
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

        setContentView(R.layout.activity_game_over);

        // Receive score from GameGlSurfaceView.java
        Intent intent = getIntent();
        String message = intent.getStringExtra(GameGLSurfaceView.EXTRA_MESSAGE);

        // Display high score
        scoreView = (TextView) findViewById(R.id.score_view);
        scoreView.setText(message);
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


    // Disable back button
    @Override
    public void onBackPressed()
    {
    }

    // Save high score and player name
    public void saveButton(View view)
    {
        Button saveButton, replayButton, mainButton;
        HighScoreDatabase dbHelper;
        SQLiteDatabase db;
        TextView score;
        EditText name;
        long newRowId;

        // Get database
        dbHelper = new HighScoreDatabase(this);

        db = dbHelper.getWritableDatabase();

        score = (TextView) findViewById(R.id.score_view);
        name = (EditText) findViewById(R.id.editText);

        // Insert player name and high score to database
        ContentValues values = new ContentValues();
        values.put(
                HighScoreDatabase.COLUMN_NAME_NAME, name.getText().toString()
        );
        values.put(
                HighScoreDatabase.COLUMN_NAME_SCORE, score.getText().toString()
        );

        newRowId = db.insert(
                HighScoreDatabase.TABLE_NAME,
                HighScoreDatabase.COLUMN_NAME_NAME,
                values);

        // Display and hide some buttons
        saveButton = (Button) findViewById(R.id.save_score);
        replayButton = (Button) findViewById(R.id.replay);
        mainButton = (Button) findViewById(R.id.main);

        saveButton.setVisibility(View.GONE);
        name.setVisibility(View.GONE);

        replayButton.setVisibility(View.VISIBLE);
        mainButton.setVisibility(View.VISIBLE);
    }

    // Replay game
    public void replayButton(View view)
    {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
        finish();
    }

    // Go to MainActivity
    public void mainButton(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
