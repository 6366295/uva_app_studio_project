package nl.uva.projecttds;

import android.opengl.Matrix;
import android.util.Log;

public class BulletLogic
{
    private static final String TAG = "MyGLRenderer";

    public int spawned, progress;
    public float initialX, initialY;
    public float posX, posY;
    public boolean hit;

    public BulletLogic()
    {
        posX = 10000;
        posY = 10000;
        spawned = 1;
        progress = 0;
    }

    public void animateBullet(BulletModel bulletModel, float[] mMVPMatrix, float selected,
                                 float playerX, float playerY, float speed)
    {
        Matrix.translateM(mMVPMatrix, 0, -360.0f, -1000.0f, 0);

        if(selected == 0 && spawned == 1)
        {
            initialX = playerX;
            initialY = playerY;
            posX = playerX;
            posY = playerY;

            spawned = 0;
        }
        else if((selected == 1 && spawned == 0) || (selected == 0 && spawned == 0))
        {
            progress -= speed;
            posY -= speed;

            if(hit == true)
            {
                posX = 10000;
                posY = 10000;
            }

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

            if(hit == false)
                bulletModel.draw(mMVPMatrix);
        }
    }

    public boolean bulletHit(EnemyLogic enemyLogic)
    {
        float enemyWidth = enemyLogic.width/2.0f;
        float enemyHeight = enemyLogic.height/2.0f;

        if(posX-10 < enemyLogic.posX+enemyWidth && posX+10 > enemyLogic.posX-enemyWidth &&
            posY-50 < enemyLogic.posY+enemyHeight && posY+50 > enemyLogic.posY-enemyHeight)
        {
            enemyLogic.hit += 1;
            return true;
            //Log.v(TAG, "x1=" + enemyLogic.posX);
            //Log.v(TAG, "y1=" + enemyLogic.posY);
            //Log.v(TAG, "x2=" + posX);
            //Log.v(TAG, "y2=" + posY);
        }
        else
        {
            return false;
        }
    }
}
