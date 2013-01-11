package nomilous.gimbal;

import android.opengl.GLSurfaceView;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import nomilous.Util;

class GimbalGL10Overlay extends GimbalOverlay {

    GimbalGL10Overlay(Object android) {
        super(android);
    }

    public GLSurfaceView view() {
        Util.debug("GimbalGL10Overlay.view()");
        GLSurfaceView surfaceView = new GLSurfaceView(context);
        surfaceView.setRenderer( new GLSurfaceView.Renderer() {

            @Override
            public void onDrawFrame(GL10 gl) {}

            @Override
            public void onSurfaceChanged(GL10 gl, int width, int height) {}

            @Override
            public void onSurfaceCreated(GL10 gl, EGLConfig config) {}


        } );

        return surfaceView;
    }

}
