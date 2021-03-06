package nomilous.gimbal.overlays;

import nomilous.Util;
import nomilous.gimbal.GimbalGLRenderer;
import nomilous.gimbal.menu.*;

import android.opengl.GLSurfaceView;
import android.graphics.PixelFormat;

public class GimbalGLOverlay extends GimbalOverlay {

    private GLSurfaceView surfaceView;
    private GimbalGLRenderer renderer;

    public GimbalGLOverlay(Object android, GimbalGLRenderer renderer) {
        super(android);
        this.renderer = renderer;
        Util.debug("START GimbalGLOverlay");
    }

    public GLSurfaceView view() {
        Util.debug("GimbalGLOverlay.view()");
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

    @Override
    public void onMenuShow(MenuActionGroup menu) {
        renderer.onMenuShow(menu);
    }

    @Override
    public void onMenuHide(MenuActionGroup menu) {
        renderer.onMenuHide(menu);
    }

    @Override
    public void onMenuSelection(MenuAction action) {
        renderer.onMenuSelection(action);
    }

}
