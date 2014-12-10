/* * *
 * Name: Mike Trieu
 * Student-ID: 6366295 / 10105093
 *
 * GameGLSurfaceView.java
 *
 * Create and set/display GLRenderer
 * Also handles display input
 * */

package nl.uva.projecttds;

import android.content.Context;
import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;


public class GameGLSurfaceView extends GLSurfaceView
{
    private final GameGLRenderer gameRenderer;

    public final static String EXTRA_MESSAGE = "nl.uva.projecttds.MESSAGE";

    public GameGLSurfaceView(final Context context)
    {
        super(context);

        // Create an OpenGL ES 2.0 context.
        setEGLContextClientVersion(2);

        // Set the Renderer for drawing on the GLSurfaceView
        gameRenderer = new GameGLRenderer();
        setRenderer(gameRenderer);

        // Create handler to handle messages from GameGLRenderer
        // In this case: when the player is dead and send highscore
        gameRenderer.receiveMessage(new Handler() {
            public void handleMessage(Message msg) {
                if (msg.what == gameRenderer.GAME_OVER) {
                    // Start GameOverActivity and pass the highscore to that activity
                    Intent intent = new Intent(context, GameOverActivity.class);
                    intent.putExtra(EXTRA_MESSAGE, String.valueOf(msg.arg1));
                    context.startActivity(intent);
                }
            }
        });
    }

    // Function for TouchEvents
    private float previousX;
    private float previousY;

    @Override
    public boolean onTouchEvent(MotionEvent e)
    {
        // Get current touch coordinates
        float x = e.getX();
        float y = e.getY();

        // Various touch actions
        switch (e.getAction())
        {
            // Set player as selected, if selected
            case MotionEvent.ACTION_DOWN:
                if(x < (gameRenderer.playerLogic.playerX + 120f) &&
                        x > (gameRenderer.playerLogic.playerX - 120f) &&
                        y < (gameRenderer.playerLogic.playerY + 120f) &&
                        y > (gameRenderer.playerLogic.playerY - 120f))
                {
                    gameRenderer.playerLogic.selected = gameRenderer.playerLogic.IS_SELECTED;

                    break;
                }

                break;
            // Set player as unselected, if screen is not touched
            case MotionEvent.ACTION_UP:
                // Deselect player
                gameRenderer.playerLogic.selected = gameRenderer.playerLogic.NOT_SELECTED;

                break;
            // When moving player, set coordinates for moving
            case MotionEvent.ACTION_MOVE:
                float dx = x - previousX;
                float dy = y - previousY;

                // Move only if player is selected
                if(gameRenderer.playerLogic.selected == 0)
                {
                    // Set translation coordinates for translation matrix
                    if(gameRenderer.getPointX() + dx > 340 || gameRenderer.getPointX() + dx < -340)
                    {
                        gameRenderer.setPointX(gameRenderer.getPointX());
                    }
                    else
                    {
                        gameRenderer.setPointX(gameRenderer.getPointX() + dx);
                        gameRenderer.playerLogic.playerX += dx;
                    }

                    if(gameRenderer.getPointY() + dy > 100 || gameRenderer.getPointY() + dy < -800)
                    {
                        gameRenderer.setPointY(gameRenderer.getPointY());
                    }
                    else
                    {
                        gameRenderer.setPointY(gameRenderer.getPointY() + dy);
                        gameRenderer.playerLogic.playerY += dy;
                    }
                }

                // Render
                requestRender();

                break;
        }

        previousX = x;
        previousY = y;

        return true;
    }
}