/* * *
 * Name: Mike Trieu
 * Student-ID: 6366295 / 10105093
 *
 * GameActivity.java
 *
 * Create and set/display GLSurfaceView
 * */

package nl.uva.projecttds;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;


public class GameActivity extends Activity
{
    private static GLSurfaceView gameGLSurfaceView;

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

        gameGLSurfaceView = new GameGLSurfaceView(this);
        setContentView(gameGLSurfaceView);

        FrameLayout rootLayout = (FrameLayout)findViewById(android.R.id.content);
        View.inflate(this, R.layout.game_interface, rootLayout);
    }

    @Override
    public void onPause()
    {
        super.onPause();

        // Pause gameGLSurfaceView rendering thread
        gameGLSurfaceView.onPause();
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

        // Resume gameGLSurfaceView rendering thread
        gameGLSurfaceView.onResume();
    }


    // Disable back button
    @Override
    public void onBackPressed()
    {
    }
}
