/* * *
 * Name: Mike Trieu
 * Student-ID: 6366295 / 10105093
 *
 * MainActivity.java
 *
 * Contains functions for the two buttons in activity_main.xml
 * */

package nl.uva.projecttds;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


public class MainActivity extends Activity
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

        setContentView(R.layout.activity_main);
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

    // Go to game
    public void gameButton(View view)
    {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
        finish();
    }

    // Go to high score list
    public void scoreButton(View view)
    {
        Intent intent = new Intent(this, HighScoreActivity.class);
        startActivity(intent);
    }
}
