package nomilous.gimbal;

import android.app.Activity;
import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.graphics.Color;

import nomilous.Util;

class GimbalUIOverlay {

    private Activity       activity;
    private Context        context;
    private RelativeLayout overlay;
    private LayoutParams   overlayParams;
    private boolean        already = false;
    private boolean        still   = false;

    GimbalUIOverlay(Object android) {
        Util.debug("CREATE");
        this.context  = (Context) android;
        this.activity = (Activity) android;
    }

    public void start() {
        Util.debug("START");
        if(!already) {
            createVisualsOverlay();
            createControlsOverlay();
            activity.addContentView(overlay, overlayParams);
        }
        already = true;
    }

    public void restart() {
        Util.debug("RESTART");
    }

    public void resume() {
        Util.debug("RESUME");
    }

    public void pause() {
        Util.debug("PAUSE");
    }

    public void stop() {
        Util.debug("STOP");
    }

    public void destroy() {
        Util.debug("DESTROY");
    }

    private void createVisualsOverlay() {
        if( GimbalConfig.VISUAL_FEEDBACK == GimbalConfig.Option.NONE ) return;
    }

    private void createControlsOverlay() {
        overlay =       new RelativeLayout(context);
        overlayParams = new LayoutParams(
            LayoutParams.FILL_PARENT, 
            LayoutParams.FILL_PARENT
        );
        overlay.setBackgroundColor(Color.argb(50,255,255,255));
        overlayParams.setMargins(0, 0, 0, 0);
        overlay.setLayoutParams(overlayParams);
    }

}
