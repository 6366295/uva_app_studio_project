/* * *
 * Name: Mike Trieu
 * Student-ID: 6366295 / 10105093
 *
 * GameOverActivity.java
 *
 * Displays highscore
 * */

package nl.uva.projecttds;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
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

        // Receive intent with highscore string
        Intent intent = getIntent();
        String message = intent.getStringExtra(GameGLSurfaceView.EXTRA_MESSAGE);

        // Set highscore string to a text view
        TextView score = (TextView) findViewById(R.id.score_view);
        score.setText(message);
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

    // Function for button in xml
    public void saveButton(View view)
    {
        HighScoreDatabase dbHelper = new HighScoreDatabase(this);

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        TextView score = (TextView) findViewById(R.id.score_view);
        EditText name = (EditText) findViewById(R.id.editText);

        ContentValues values = new ContentValues();
        values.put(HighScoreDatabase.COLUMN_NAME_NAME, name.getText().toString());
        values.put(HighScoreDatabase.COLUMN_NAME_SCORE, score.getText().toString());

        long newRowId = db.insert(
                HighScoreDatabase.TABLE_NAME,
                HighScoreDatabase.COLUMN_NAME_NAME,
                values);

        Button button = (Button) findViewById(R.id.save_score);
        Button button2 = (Button) findViewById(R.id.replay);
        Button button3 = (Button) findViewById(R.id.main);
        button.setVisibility(View.GONE);
        name.setVisibility(View.GONE);

        button2.setVisibility(View.VISIBLE);
        button3.setVisibility(View.VISIBLE);
    }

    // Function for button in xml
    public void replayButton(View view)
    {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    // Function for button in xml
    public void mainButton(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
