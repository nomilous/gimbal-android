package nomilous.gimbal;

import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.graphics.Color;
import java.util.Hashtable;
import java.util.Enumeration;
import android.opengl.GLSurfaceView;

import nomilous.Util;

class GimbalUIOverlay extends GimbalOverlay {

    private boolean        already = false;
    private RelativeLayout overlay;
    private LayoutParams   overlayParams;
    private Hashtable      visualOverlays = new Hashtable();

    GimbalUIOverlay(Object android) {  
        super(android);
        Util.debug("CONSTRUCT GimbalUIOverlay");
    }

    public void start() {
        Util.debug("START GimbalUIOverlay");
        if(!already) {
            overlayParams = new LayoutParams(
                LayoutParams.FILL_PARENT, 
                LayoutParams.FILL_PARENT
            );
            overlayParams.setMargins(0, 0, 0, 0);
            createVisualsOverlay();
            createControlsOverlay();
        }
        already = true;
    }

    public void restart() {
        Util.debug("RESTART");
    }

    public void resume() {
        Util.debug("RESUME");
        Enumeration<String> e = visualOverlays.keys();
        while(e.hasMoreElements()) {
            try {
                GimbalGL10Overlay overlay = (GimbalGL10Overlay) visualOverlays.get(e.nextElement());
                overlay.getView().onResume();
            } catch (java.lang.ClassCastException x) {}
            try {
                GimbalCameraOverlay overlay = (GimbalCameraOverlay) visualOverlays.get(e.nextElement());
                //overlay.getView().onResume();
            } catch (java.lang.ClassCastException x) {}
        }
    }

    public void pause() {
        Util.debug("PAUSE");
        Enumeration<String> e = visualOverlays.keys();
        while(e.hasMoreElements()) {
            try {
                GimbalGL10Overlay overlay = (GimbalGL10Overlay) visualOverlays.get(e.nextElement());
                overlay.getView().onPause();
            } catch (java.lang.ClassCastException x) {}
            try {
                GimbalCameraOverlay overlay = (GimbalCameraOverlay) visualOverlays.get(e.nextElement());
                //overlay.getView().onPause();
            } catch (java.lang.ClassCastException x) {}
        }
    }

    public void stop() {
        Util.debug("STOP");
    }

    public void destroy() {
        Util.debug("DESTROY");
    }

    private void createVisualsOverlay() {
        if( GimbalConfig.VISUAL_FEEDBACK == GimbalConfig.Option.NONE ) return;
        int enable = 0;

        enable = GimbalConfig.VISUAL_FEEDBACK & GimbalConfig.Option.CAMERA;
        if( enable == GimbalConfig.Option.CAMERA ) {
            GimbalCameraOverlay visualOverlay = new GimbalCameraOverlay((Object)activity);
            visualOverlays.put(GimbalConfig.Option.CAMERA, visualOverlay);
            activity.addContentView(visualOverlay.view(), overlayParams);
        }

        enable = GimbalConfig.VISUAL_FEEDBACK & GimbalConfig.Option.GL10;
        if( enable == GimbalConfig.Option.GL10 ) {
            GimbalGL10Overlay visualOverlay = new GimbalGL10Overlay((Object)activity);
            visualOverlays.put(GimbalConfig.Option.GL10, visualOverlay);
            activity.addContentView(visualOverlay.view(), overlayParams);
        }

    }

    private void createControlsOverlay() {
        overlay =       new RelativeLayout(context);
        overlay.setBackgroundColor(Color.argb(50,255,255,255));
        overlay.setLayoutParams(overlayParams);
        activity.addContentView(overlay, overlayParams);
    }

}
