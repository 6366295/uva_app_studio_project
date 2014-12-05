package nl.uva.projecttds;

import android.opengl.Matrix;
import android.util.Log;

public class PlayerLogic
{
    private static final String TAG = "MyGLRenderer";

    public int selected, score, hit, hp;
    public float playerX, playerY;

    public PlayerLogic()
    {
        selected = 1;
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
