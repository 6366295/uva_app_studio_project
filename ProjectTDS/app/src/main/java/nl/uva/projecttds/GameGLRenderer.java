package nl.uva.projecttds;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;
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
        //Matrix.translateM(mMVPMatrix, 0, 0, -0.9f, 0);

        // Draw player
        Matrix.translateM(mMVPMatrix, 0, pointX, pointY, 0);
        playerModel.draw(mMVPMatrix);

        if(playerLogic.selected == 0 || bulletLogic.spawned == 0 || bulletLogic.spawned2 == 0)
        {
            if(bulletLogic.spawned == 1 && playerLogic.selected == 0)
            {
                bulletLogic.posX = playerLogic.playerX;
                bulletLogic.posY = playerLogic.playerY;
                bulletLogic.spawned = 0;
            }

            if(bulletLogic.spawned == 0)
            {

                bulletLogic.progress += -100;

                if(bulletLogic.progress < -1280)
                {
                    bulletLogic.spawned = 1;
                    bulletLogic.progress = 0;
                }
                else
                {
                    Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);
                    Matrix.translateM(mMVPMatrix, 0, -360.0f, -1000.0f, 0);
                    Matrix.translateM(mMVPMatrix, 0, bulletLogic.posX, bulletLogic.posY, 0);
                    Matrix.translateM(mMVPMatrix, 0, 0, bulletLogic.progress, 0);
                    bulletModel.draw(mMVPMatrix);
                }
            }

            if(bulletLogic.progress < -200 || bulletLogic.spawned2 == 0) {
                if (bulletLogic.spawned2 == 1 && playerLogic.selected == 0) {
                    bulletLogic.posX2 = playerLogic.playerX;
                    bulletLogic.posY2 = playerLogic.playerY;
                    bulletLogic.spawned2 = 0;
                }

                if (bulletLogic.spawned2 == 0) {

                    bulletLogic.progress2 += -100;

                    if (bulletLogic.progress2 < -1280) {
                        bulletLogic.spawned2 = 1;
                        bulletLogic.progress2 = 0;
                    } else {
                        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);
                        Matrix.translateM(mMVPMatrix, 0, -360.0f, -1000.0f, 0);
                        Matrix.translateM(mMVPMatrix, 0, bulletLogic.posX2, bulletLogic.posY2, 0);
                        Matrix.translateM(mMVPMatrix, 0, 0, bulletLogic.progress2, 0);
                        bulletModel.draw(mMVPMatrix);
                    }
                }
            }
        }
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
