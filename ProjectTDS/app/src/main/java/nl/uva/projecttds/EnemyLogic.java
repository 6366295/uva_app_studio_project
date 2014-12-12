/* * *
 * Name: Mike Trieu
 * Student-ID: 6366295 / 10105093
 *
 * EnemyLogic.java
 *
 * Logic of enemies
 * */

package nl.uva.projecttds;

import android.opengl.Matrix;

public class EnemyLogic
{
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

    public void animateEnemy(EnemyModel enemyModel,
                             float[] mMVPMatrix,
                             float initialX, float initialY,
                             float speed,
                             float delay1, float delay2,
                             float xlim,
                             int eHP)
    {
        Matrix.translateM(mMVPMatrix, 0, -360, -1000, 0);

        // Set position to -10000 when it hit an player
        //  or bullet hits enemy more than 5 times
        // This is to prevent multiple hits
        if(hit >= eHP || hitplayer == true)
        {
            posX = -10000;
            posY = -10000;
        }

        // Delay spawning by delay1
        if(animationDelay < delay1)
        {
            animationDelay += 1;
        }
        else if(animationDelay == delay1)
        {
            // Set spawn location
            if(spawned == 1)
            {
                posX = initialX;
                posY = initialY;

                spawned = 0;
            }
            // Animate enemy
            else if(spawned == 0)
            {
                Matrix.translateM(mMVPMatrix, 0, initialX, initialY, 0);

                // Travel horizontally
                if(progressX != xlim) {
                    progressX += speed;
                    posX += speed;
                }
                // Travel vertically, when xlim distance is reached
                else if(progressX == xlim)
                {
                    // Delay animation by delay2
                    if(animationDelay2 < delay2)
                    {
                        animationDelay2 += 1;
                    }
                    else if(animationDelay2 == delay2)
                    {
                        progressY += Math.abs(speed);
                        posY += Math.abs(speed);

                        // Despawn when enemy is off screen
                        // 1280 because screen is mapped 1184 no
                        //   matter what resolution
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

                // Only draw enemy when it's still alive
                if(hit < eHP && hitplayer == false)
                    enemyModel.draw(mMVPMatrix);
            }
        }
    }

    // Function to reset enemy
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

    // Function to check if enemy hits player
    public void checkIfHit(PlayerLogic playerLogic)
    {
        float enemyWidth = width/2.0f;
        float enemyHeight = height/2.0f;

        if(playerLogic.playerX-75 < posX+enemyWidth &&
                playerLogic.playerX+75 > posX-enemyWidth &&
                playerLogic.playerY-75 < posY+enemyHeight &&
                playerLogic.playerY+75 > posY-enemyHeight)
        {
            playerLogic.hit += 1;
            hitplayer = true;
        }
    }
}
