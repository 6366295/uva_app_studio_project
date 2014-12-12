/* * *
 * Name: Mike Trieu
 * Student-ID: 6366295 / 10105093
 *
 * PlayerLogic.java
 *
 * Logic of player
 * */

package nl.uva.projecttds;

import android.opengl.Matrix;


public class PlayerLogic
{
    public static final int IS_SELECTED = 0;
    public static final int NOT_SELECTED = 1;
    public static final float TOUCH_MARGIN = 120f;

    public int selected, score, hit, hp;
    public float playerX, playerY;

    public PlayerLogic()
    {
        selected = NOT_SELECTED;

        // Initial position
        playerX = 360f;
        playerY = 1000f;

        // Initial score and hit points
        score = 0;
        hp = 5;

        hit = 0;
    }

    // Move player object to touchscreen location
    public void animatePlayer(float [] mMVPMatrix,
                              PlayerModel playerModel,
                              float pointX, float pointY)
    {
        Matrix.translateM(mMVPMatrix, 0, pointX, pointY, 0);
        playerModel.draw(mMVPMatrix);
    }
}
