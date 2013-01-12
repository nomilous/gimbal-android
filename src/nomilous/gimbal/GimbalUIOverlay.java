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
    }

    public void start() {
        Util.debug("START");
        if(!already) {
            overlayParams = new LayoutParams(
                LayoutParams.FILL_PARENT, 
                LayoutParams.FILL_PARENT
            );
            overlayParams.setMargins(0, 0, 0, 0);
            createVisualsOverlay();
            //createControlsOverlay();
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
            GimbalGL10Overlay overlay = (GimbalGL10Overlay) visualOverlays.get(e.nextElement());
            overlay.getView().onResume();
        }
    }

    public void pause() {
        Util.debug("PAUSE");
        Enumeration<String> e = visualOverlays.keys();
        while(e.hasMoreElements()) {
            GimbalGL10Overlay overlay = (GimbalGL10Overlay) visualOverlays.get(e.nextElement());
            overlay.getView().onPause();
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
        if( GimbalConfig.VISUAL_FEEDBACK == GimbalConfig.Option.GL10 ) {
            for( int i = 0; i < 10; i++ ) {



                //
                // Quick experiment:
                //
                // - 10 independant GL surfaces overlaid
                // - obviously very slow (but still - im surprised its possible)
                //



                GimbalGL10Overlay visualOverlay = new GimbalGL10Overlay((Object)activity);
                visualOverlay.rotationSpeed(1.0f + (float)i / 10 );
                visualOverlays.put(GimbalConfig.Option.GL10 + i, visualOverlay);
                activity.addContentView(visualOverlay.view(), overlayParams);
            }
        }

    }

    private void createControlsOverlay() {
        overlay =       new RelativeLayout(context);
        overlay.setBackgroundColor(Color.argb(50,255,255,255));
        overlay.setLayoutParams(overlayParams);
        activity.addContentView(overlay, overlayParams);
    }

}
