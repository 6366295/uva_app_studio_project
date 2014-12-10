package nl.uva.projecttds;

import android.opengl.Matrix;
import android.util.Log;

public class EnemyLogic
{
    private static final String TAG = "MyGLRenderer";

    public float posX, posY;
    public float width, height;
    public int progressX, progressY;
    public int animationDelay, animationDelay2;
    public int spawned, hit;
    public int score;
    public boolean hitplayer;

    public EnemyLogic()
    {
        posX = -10000;
        posY = -10000;

        progressX = 0;
        progressY = 0;

        width = 100;
        height = 100;

        hit = 0;
        score = 100;
        hitplayer = false;

        spawned = 1;
        animationDelay = 0;
        animationDelay2 = 0;
    }

    public void animateEnemy(EnemyModel enemyModel, float[] mMVPMatrix, float initialX, float initialY, float speed, float delay1, float delay2, float xlim)
    {
        Matrix.translateM(mMVPMatrix, 0, -360, -1000, 0);
        Log.v(TAG, "hit=" + hit);

        if(hit > 9 || hitplayer == true)
        {
            posX = -10000;
            posY = -10000;
        }

        if(animationDelay < delay1)
        {
            animationDelay += 1;
        }
        else if(animationDelay == delay1)
        {
            if(spawned == 1)
            {
                posX = initialX;
                posY = initialY;

                spawned = 0;
            }
            else if(spawned == 0)
            {
                Matrix.translateM(mMVPMatrix, 0, initialX, initialY, 0);

                if(progressX != xlim) {
                    progressX += speed;
                    posX += speed;
                }
                else if(progressX == xlim)
                {
                    if(animationDelay2 < delay2)
                    {
                        animationDelay2 += 1;
                    }
                    else if(animationDelay2 == delay2)
                    {
                        progressY += Math.abs(speed);
                        posY += Math.abs(speed);

                        if(progressY > 1280)
                        {
                            spawned = 1;

                            animationDelay = 0;
                            animationDelay2 = 0;

                            progressX = 0;
                            progressY = 0;
                        }
                    }
                }

                Matrix.translateM(mMVPMatrix, 0, progressX, progressY, 0);

                if(hit < 10 && hitplayer == false)
                    enemyModel.draw(mMVPMatrix);
            }
        }
    }

    public void resetEnemy()
    {
        spawned = 1;

        animationDelay = 0;
        animationDelay2 = 0;

        progressX = 0;
        progressY = 0;

        hit = 0;
        hitplayer = false;
    }

    public void checkIfHit(PlayerLogic playerLogic)
    {
        float enemyWidth = width/2.0f;
        float enemyHeight = height/2.0f;

        if(playerLogic.playerX-75 < posX+enemyWidth && playerLogic.playerX+75 > posX-enemyWidth &&
                playerLogic.playerY-75 < posY+enemyHeight && playerLogic.playerY+75 > posY-enemyHeight)
        {
            Log.v(TAG, "PLAYERHITTTT=");
            playerLogic.hit += 1;
            hitplayer = true;
        }
    }
}
