package nl.uva.projecttds;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;


public class GameGLSurfaceView extends GLSurfaceView
{
    private final GameGLRenderer gameRenderer;

    public GameGLSurfaceView(Context context)
    {
        super(context);

        // Create an OpenGL ES 2.0 context.
        setEGLContextClientVersion(2);

        // Set the Renderer for drawing on the GLSurfaceView
        gameRenderer = new GameGLRenderer();
        setRenderer(gameRenderer);
    }


    private float previousX;
    private float previousY;

    @Override
    public boolean onTouchEvent(MotionEvent e)
    {
        float x = e.getX();
        float y = e.getY();

        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // Set player as selected
                // TODO works only for 1280x720 sceen for now
                if(x < (gameRenderer.playerLogic.playerX + 120f) &&
                        x > (gameRenderer.playerLogic.playerX - 120f) &&
                        y < (gameRenderer.playerLogic.playerY + 120f) &&
                        y > (gameRenderer.playerLogic.playerY - 120f))
                {
                    gameRenderer.playerLogic.selected = 0;

                    break;
                }

                break;
            case MotionEvent.ACTION_UP:
                // Deselect player
                gameRenderer.playerLogic.selected = 1;

                break;
            case MotionEvent.ACTION_MOVE:
                float dx = x - previousX;
                float dy = y - previousY;

                // Move only if player is selected
                if(gameRenderer.playerLogic.selected == 0)
                {
                    // Set translation coordinates for translation matrix
                    gameRenderer.setPointX(gameRenderer.getPointX() + dx);
                    gameRenderer.setPointY(gameRenderer.getPointY() + dy);

                    // Set new player coordinates
                    gameRenderer.playerLogic.playerX += dx;
                    gameRenderer.playerLogic.playerY += dy;
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
