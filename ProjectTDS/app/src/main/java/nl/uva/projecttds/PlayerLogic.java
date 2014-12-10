package nl.uva.projecttds;

import android.opengl.Matrix;


public class PlayerLogic
{
    public static final int IS_SELECTED = 0;
    public static final int NOT_SELECTED = 1;

    public int selected, score, hit, hp;
    public float playerX, playerY;

    public PlayerLogic()
    {
        selected = NOT_SELECTED;
        playerX = 360f;
        playerY = 1000f;

        score = 0;
        hit = 0;
        hp = 5;
    }

    public void animatePlayer(float [] mMVPMatrix, PlayerModel playerModel, float pointX, float pointY)
    {
        Matrix.translateM(mMVPMatrix, 0, pointX, pointY, 0);
        playerModel.draw(mMVPMatrix);
    }
}
