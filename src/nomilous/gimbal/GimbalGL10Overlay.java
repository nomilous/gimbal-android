package nomilous.gimbal;

import android.opengl.GLSurfaceView;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.opengl.GLU;
import android.graphics.PixelFormat;

import nomilous.Util;

class GimbalGL10Overlay extends GimbalOverlay {

    private GLSurfaceView surfaceView;

    GimbalGL10Overlay(Object android) {
        super(android);
    }

    public GLSurfaceView view() {
        Util.debug("GimbalGL10Overlay.view()");
        surfaceView = new GLSurfaceView(context);
        surfaceView.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        surfaceView.getHolder().setFormat(PixelFormat.TRANSLUCENT);
        surfaceView.setZOrderOnTop(true);
        surfaceView.setRenderer( new GLSurfaceView.Renderer() {

            private MeshCube cube = new MeshCube(1, 1);
            private float angle1 = 0.0f;
            private float angle2 = 0.0f;
            private float speed = 2.0f;

            @Override
            public void onDrawFrame(GL10 gl) {
                //gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
                gl.glMatrixMode(GL10.GL_MODELVIEW);
                //gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
                gl.glLoadIdentity();
                gl.glTranslatef(0.0f, 0.0f, -2.0f);
                gl.glRotatef(-90, 1.0f, 0.0f, 0.0f);
                gl.glRotatef(angle1 += speed, 0.0f, 0.0f, 1.0f);
                gl.glRotatef(angle1 += speed, 0.0f, 1.0f, 1.0f);
                gl.glRotatef(angle2 -= angle1 / 100, 1.0f, 0.0f, 0.0f);
                cube.draw(gl);
            }
                
            @Override
            public void onSurfaceChanged(GL10 gl, int width, int height) {
                if (height == 0) height = 1;
                float aspect = (float)width / height;
                gl.glViewport(0, 0, width, height);
                gl.glMatrixMode(GL10.GL_PROJECTION);
                gl.glLoadIdentity();
                GLU.gluPerspective(gl, 70, aspect, 0.1f, 1000.f);
                gl.glMatrixMode(GL10.GL_MODELVIEW);
                gl.glLoadIdentity();
            }

            @Override
            public void onSurfaceCreated(GL10 gl, EGLConfig config) {   
                gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
                gl.glClearDepthf(1.0f);             
                gl.glEnable(GL10.GL_DEPTH_TEST);
                gl.glDepthFunc(GL10.GL_LEQUAL);
                gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
                gl.glShadeModel(GL10.GL_SMOOTH);
                gl.glDisable(GL10.GL_DITHER);
            }

        } );

        return surfaceView;
    }

    public GLSurfaceView getView() {
        return surfaceView;
    }

}
