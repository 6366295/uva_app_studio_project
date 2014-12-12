/* * *
 * Name: Mike Trieu
 * Student-ID: 6366295 / 10105093
 *
 * BulletLogic.java
 *
 * Logic of the bullet
 * */

package nl.uva.projecttds;

import android.opengl.Matrix;

public class BulletLogic
{
    public int spawned, progress;
    public float initialX, initialY;
    public float posX, posY;
    public boolean hit;

    public BulletLogic()
    {
        // Spawn position set to 10000,10000
        //   because it would hit enemy on spawn
        posX = 10000;
        posY = 10000;

        spawned = 1;
        progress = 0;
    }

    public void animateBullet(BulletModel bulletModel,
                              float[] mMVPMatrix, float selected,
                              float playerX, float playerY, float speed)
    {
        Matrix.translateM(mMVPMatrix, 0, -360.0f, -1000.0f, 0);

        // Spawn bullet at player position, when not spawned
        if(selected == 0 && spawned == 1)
        {
            initialX = playerX;
            initialY = playerY;
            posX = playerX;
            posY = playerY;

            spawned = 0;
        }
        // if spawned, travel straight
        else if((selected == 1 && spawned == 0) || (selected == 0 && spawned == 0))
        {
            progress -= speed;
            posY -= speed;

            // Set position to 10000 when it hit an enemy
            // This is to prevent multiple hits
            if(hit == true)
            {
                posX = 10000;
                posY = 10000;
            }

            // Despawn bullet when it is outside the screen
            // -1280 because screen is mapped 1184 no matter what resolution
            if(progress < -1280)
            {
                initialX = playerX;
                initialY = playerY;
                posX = playerX;
                posY = playerY;

                spawned = 1;
                progress = 0;
                hit = false;
            }

            Matrix.translateM(mMVPMatrix, 0, initialX, initialY, 0);
            Matrix.translateM(mMVPMatrix, 0, 0, progress, 0);

            // Draw bullet when it hit nothing
            if(hit == false)
                bulletModel.draw(mMVPMatrix);
        }
    }

    // Function to check if bullet hit something
    public boolean bulletHit(EnemyLogic enemyLogic)
    {
        float enemyWidth = enemyLogic.width/2.0f;
        float enemyHeight = enemyLogic.height/2.0f;

        // Check overlapping bounding boxes
        if(posX-10 < enemyLogic.posX+enemyWidth &&
                posX+10 > enemyLogic.posX-enemyWidth &&
                posY-50 < enemyLogic.posY+enemyHeight &&
                posY+50 > enemyLogic.posY-enemyHeight)
        {
            enemyLogic.hit += 1;
            return true;
        }
        else
        {
            return false;
        }
    }
}
