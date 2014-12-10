package nl.uva.projecttds;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


public class GameGLRenderer implements GLSurfaceView.Renderer
{
    private static final String TAG = "GameGLRenderer";
    private Handler handler = null;

    // mMVPMatrix is an abbreviation for "Model View Projection Matrix"
    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];

    private float pointX;
    private float pointY;

    public static final int GAME_OVER = 1;
    public static final int E_POINTS = 5;

    public PlayerModel playerModel;
    public BulletModel bulletModel;
    public EnemyModel enemyModel;

    public PlayerLogic playerLogic;
    public BulletLogic bulletLogic;
    public EnemyLogic enemyLogic;
    public EnemyLogic enemyLogic2;
    public EnemyLogic enemyLogic3;
    public EnemyLogic enemyLogic4;

    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config)
    {
        // Set the background frame color
        GLES20.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);

        playerModel = new PlayerModel();
        bulletModel = new BulletModel();
        enemyModel = new EnemyModel();

        playerLogic = new PlayerLogic();
        bulletLogic = new BulletLogic();
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
        // I do this for every object, because it's easier to know where each object is
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);
        // Draw player
        playerLogic.animatePlayer(mMVPMatrix, playerModel, pointX, pointY);

        // Draw bullet
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);
        bulletLogic.animateBullet(bulletModel, mMVPMatrix, playerLogic.selected, playerLogic.playerX, playerLogic.playerY, 50);

        // Draw enemy
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);
        enemyLogic.animateEnemy(enemyModel, mMVPMatrix, -50, 100, 10, 60, 60, 120);

        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);
        enemyLogic2.animateEnemy(enemyModel, mMVPMatrix, -50, 100, 10, 180, 60, 300);

        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);
        enemyLogic3.animateEnemy(enemyModel, mMVPMatrix, 770, 100, -10, 60, 60, -120);

        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);
        enemyLogic4.animateEnemy(enemyModel, mMVPMatrix, 770, 100, -10, 180, 60, -300);


        boolean a = bulletLogic.bulletHit(enemyLogic);
        if(a == true)
        {
            if(enemyLogic.hit == E_POINTS)
            {
                playerLogic.score += enemyLogic.score;
            }
            bulletLogic.hit = true;
        }
        a = bulletLogic.bulletHit(enemyLogic2);
        if(a == true)
        {
            if(enemyLogic2.hit == E_POINTS)
                playerLogic.score += enemyLogic2.score;
            bulletLogic.hit = true;
        }
        a = bulletLogic.bulletHit(enemyLogic3);
        if(a == true)
        {
            if(enemyLogic3.hit == E_POINTS)
                playerLogic.score += enemyLogic3.score;
            bulletLogic.hit = true;
        }
        a = bulletLogic.bulletHit(enemyLogic4);
        if(a == true)
        {
            if(enemyLogic4.hit == E_POINTS)
                playerLogic.score += enemyLogic4.score;
            bulletLogic.hit = true;
        }

        enemyLogic.checkIfHit(playerLogic);
        enemyLogic2.checkIfHit(playerLogic);
        enemyLogic3.checkIfHit(playerLogic);
        enemyLogic4.checkIfHit(playerLogic);

        if(playerLogic.hit == 1)
        {
            playerLogic.hp -= 1;
            playerLogic.hit = 0;
        }

        if(playerLogic.hp < 0)
        {
            if(handler!=null){
                // do calculation using GL handle
                int flag = GameGLRenderer.GAME_OVER;
                handler.dispatchMessage(Message.obtain(handler, flag, playerLogic.score, 0));
                // adds a message to the UI thread's message queue

                handler = null;

            }
        }

        if((enemyLogic.hit > E_POINTS || enemyLogic.hitplayer == true) &&
                (enemyLogic2.hit > E_POINTS || enemyLogic2.hitplayer == true) &&
                (enemyLogic3.hit > E_POINTS || enemyLogic3.hitplayer == true) &&
                (enemyLogic4.hit > E_POINTS || enemyLogic4.hitplayer == true))
        {
            enemyLogic.resetEnemy();
            enemyLogic2.resetEnemy();
            enemyLogic3.resetEnemy();
            enemyLogic4.resetEnemy();
        }
    }

    // Mandatory GLRenderer function
    // Maps 3D plane to 720x1184 pixels
    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height)
    {
        // Adjust the viewport based on geometry changes,
        // such as screen rotation
        GLES20.glViewport(0, 0, width, height);

        Matrix.orthoM(mProjectionMatrix, 0, 0, -720, 1184, 0, 0, 1);

    }

    // Function for loading shader
    public static int loadShader(int type, String shaderCode)
    {
        int shader = GLES20.glCreateShader(type);

        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }

    // Function for checking GLErrors
    public static void checkGlError(String glOperation)
    {
        int error;

        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR)
        {
            Log.e(TAG, glOperation + ": glError " + error);
            throw new RuntimeException(glOperation + ": glError " + error);
        }
    }

    // Handler for communicating with GameGLSurfaceView
    public void receiveMessage(Handler handler)
    {
        this.handler = handler;
    }

    // Functions for setting and getting touch coordinates
    public float getPointX()
    {
        return pointX;
    }

    public void setPointX(float x)
    {
        pointX = x;
    }

    public float getPointY()
    {
        return pointY;
    }

    public void setPointY(float y)
    {
        pointY = y;
    }
}