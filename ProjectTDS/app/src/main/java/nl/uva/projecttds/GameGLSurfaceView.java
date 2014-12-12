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
        gameRenderer.receiveMessage(new Handler()
        {
            public void handleMessage(Message msg)
            {
                if(msg.what == gameRenderer.GAME_OVER)
                {
                    // Start GameOverActivity
                    // And pass the high score to that activity
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
        float x, y, dx, dy;
        float touchMargin;
        float playerX, playerY;
        int isSelected, notSelected;

        // Get current touch coordinates
        x = e.getX();
        y = e.getY();

        isSelected = gameRenderer.playerLogic.IS_SELECTED;
        notSelected = gameRenderer.playerLogic.NOT_SELECTED;

        // Various touch actions
        switch (e.getAction())
        {
            // Set player as selected, if selected
            case MotionEvent.ACTION_DOWN:
                touchMargin = gameRenderer.playerLogic.TOUCH_MARGIN;
                playerX = gameRenderer.playerLogic.playerX;
                playerY = gameRenderer.playerLogic.playerY;

                if(x < (playerX + touchMargin) &&
                        x > (playerX - touchMargin) &&
                        y < (playerY + touchMargin) &&
                        y > (playerY - touchMargin))
                {
                    gameRenderer.playerLogic.selected = isSelected;

                    break;
                }

                break;
            // Set player as unselected, if screen is not touched
            case MotionEvent.ACTION_UP:
                // Deselect player
                gameRenderer.playerLogic.selected = notSelected;

                break;
            // When moving player, set coordinates for moving
            case MotionEvent.ACTION_MOVE:
                dx = x - previousX;
                dy = y - previousY;

                // Move only if player is selected
                if(gameRenderer.playerLogic.selected == isSelected)
                {
                    // Set same x position, if it's outside the border
                    if(gameRenderer.getPointX() + dx > 340 ||
                            gameRenderer.getPointX() + dx < -340)
                    {
                        gameRenderer.setPointX(gameRenderer.getPointX());
                    }
                    // Set new x position for translation matrix and player logic
                    else
                    {
                        gameRenderer.setPointX(gameRenderer.getPointX() + dx);
                        gameRenderer.playerLogic.playerX += dx;
                    }

                    // y position version
                    if(gameRenderer.getPointY() + dy > 100 ||
                            gameRenderer.getPointY() + dy < -900)
                    {
                        gameRenderer.setPointY(gameRenderer.getPointY());
                    }
                    else
                    {
                        gameRenderer.setPointY(gameRenderer.getPointY() + dy);
                        gameRenderer.playerLogic.playerY += dy;
                    }
                }

                // Render new scene
                requestRender();

                break;
        }

        previousX = x;
        previousY = y;

        return true;
    }
}