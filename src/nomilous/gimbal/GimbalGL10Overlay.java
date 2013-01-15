package nomilous.gimbal;

import android.opengl.GLSurfaceView;
import android.graphics.PixelFormat;

import nomilous.Util;

class GimbalGL10Overlay extends GimbalOverlay {

    private GLSurfaceView surfaceView;
    private GimbalGL10Renderer renderer;

    GimbalGL10Overlay(Object android, GimbalGL10Renderer renderer) {
        super(android);
        this.renderer = renderer;
        Util.debug("START GimbalGL10Overlay");
    }

    public GLSurfaceView view() {
        Util.debug("GimbalGL10Overlay.view()");
        surfaceView = new GLSurfaceView(context);
        surfaceView.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        surfaceView.getHolder().setFormat(PixelFormat.TRANSLUCENT);
        surfaceView.setZOrderOnTop(true);
        surfaceView.setRenderer(renderer); 
        return surfaceView;
    }
    
    public GLSurfaceView getView() {
        return surfaceView;
    }

}
