package nl.uva.projecttds;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


public class GameGLRenderer implements GLSurfaceView.Renderer
{
    private static final String TAG = "MyGLRenderer";

    public PlayerModel playerModel;
    public PlayerLogic playerLogic;
    public BulletModel bulletModel;
    public BulletLogic bulletLogic;
    public EnemyModel enemyModel;
    public EnemyLogic enemyLogic;
    public EnemyLogic enemyLogic2;
    public EnemyLogic enemyLogic3;
    public EnemyLogic enemyLogic4;

    // mMVPMatrix is an abbreviation for "Model View Projection Matrix"
    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];

    private float pointX;
    private float pointY;

    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config)
    {
        // Set the background frame color
        GLES20.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);

        playerModel = new PlayerModel();
        playerLogic = new PlayerLogic();
        bulletModel = new BulletModel();
        bulletLogic = new BulletLogic();
        enemyModel = new EnemyModel();
        enemyLogic = new EnemyLogic();
        enemyLogic2 = new EnemyLogic();
        enemyLogic3 = new EnemyLogic();
        enemyLogic4 = new EnemyLogic();
    }

    @Override
    public void onDrawFrame(GL10 unused)
    {
        // Draw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        // Set the camera position (View matrix)
        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, -1, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

        // Calculate the projection and view transformation
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);

        // Draw player
        playerLogic.animatePlayer(mMVPMatrix, playerModel, pointX, pointY);

        boolean a = bulletLogic.bulletHit(enemyLogic);
        if(a == true)
        {
            if(enemyLogic.hit == 9)
            {
                playerLogic.score += enemyLogic.score;
            }
            bulletLogic.hit = true;
        }
        a = bulletLogic.bulletHit(enemyLogic2);
        if(a == true)
        {
            if(enemyLogic2.hit == 9)
                playerLogic.score += enemyLogic2.score;
            bulletLogic.hit = true;
        }
        a = bulletLogic.bulletHit(enemyLogic3);
        if(a == true)
        {
            if(enemyLogic3.hit == 9)
                playerLogic.score += enemyLogic3.score;
            bulletLogic.hit = true;
        }
        a = bulletLogic.bulletHit(enemyLogic4);
        if(a == true)
        {
            if(enemyLogic4.hit == 9)
                playerLogic.score += enemyLogic4.score;
            bulletLogic.hit = true;
        }

        enemyLogic.checkIfHit(playerLogic);
        enemyLogic2.checkIfHit(playerLogic);
        enemyLogic3.checkIfHit(playerLogic);
        enemyLogic4.checkIfHit(playerLogic);

        Log.v(TAG, "hp=" + playerLogic.hp);
        if(playerLogic.hit == 1)
        {
            playerLogic.hp -= 1;
            playerLogic.hit = 0;
        }

        if((enemyLogic.hit > 9 || enemyLogic.hitplayer == true) && (enemyLogic2.hit > 9 || enemyLogic2.hitplayer == true) &&
                (enemyLogic3.hit > 9 || enemyLogic3.hitplayer == true) && (enemyLogic4.hit > 9 || enemyLogic4.hitplayer == true))
        {
            enemyLogic.resetEnemy();
            enemyLogic2.resetEnemy();
            enemyLogic3.resetEnemy();
            enemyLogic4.resetEnemy();
        }

        Log.v(TAG, "score=" + playerLogic.score);
        //Log.v(TAG, "enemx=" + enemyLogic.posX);
        //Log.v(TAG, "enemy=" + enemyLogic.posY);
        //Log.v(TAG, "bulletx=" + bulletLogic.posX);
        //Log.v(TAG, "bullety=" + bulletLogic.posY);
        //Log.v(TAG, "playerx=" + playerLogic.playerX);
        //Log.v(TAG, "playery=" + playerLogic.playerY);

        // Draw bullet
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);
        bulletLogic.animateBullet(bulletModel, mMVPMatrix, playerLogic.selected, playerLogic.playerX, playerLogic.playerY, 50);

        // Draw enemy
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);
        enemyLogic.width = 100;
        enemyLogic.height = 100;
        enemyLogic.animateEnemy(enemyModel, mMVPMatrix, -50, 100, 10, 60, 60, 120);

        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);
        enemyLogic2.width = 100;
        enemyLogic2.height = 100;
        enemyLogic2.animateEnemy(enemyModel, mMVPMatrix, -50, 100, 10, 180, 60, 300);

        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);
        enemyLogic3.width = 100;
        enemyLogic3.height = 100;
        enemyLogic3.animateEnemy(enemyModel, mMVPMatrix, 770, 100, -10, 60, 60, -120);

        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);
        enemyLogic4.width = 100;
        enemyLogic4.height = 100;
        enemyLogic4.animateEnemy(enemyModel, mMVPMatrix, 770, 100, -10, 180, 60, -300);
    }

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height)
    {
        // Adjust the viewport based on geometry changes,
        // such as screen rotation
        GLES20.glViewport(0, 0, width, height);

        //Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 1, 7);
        Matrix.orthoM(mProjectionMatrix, 0, 0, -width, height, 0, 0, 1);

    }


    public static int loadShader(int type, String shaderCode)
    {
        int shader = GLES20.glCreateShader(type);

        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }

    public static void checkGlError(String glOperation)
    {
        int error;

        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR)
        {
            Log.e(TAG, glOperation + ": glError " + error);
            throw new RuntimeException(glOperation + ": glError " + error);
        }
    }

    public float getPointX() {
        return pointX;
    }

    public void setPointX(float x) {
        pointX = x;
    }

    public float getPointY() {
        return pointY;
    }

    public void setPointY(float y) {
        pointY = y;
    }
}
