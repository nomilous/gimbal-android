package nomilous.gimbal;

import nomilous.Util;
import nomilous.gimbal.server.sensor.TouchServer;
import nomilous.gimbal.GimbalEvent;
import nomilous.gimbal.uplink.Uplink;

import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.graphics.Color;
import java.util.Hashtable;
import java.util.Enumeration;
import android.opengl.GLSurfaceView;
import android.view.View;


class GimbalUIOverlay extends GimbalOverlay {

    private boolean        already = false;
    private RelativeLayout overlay;
    private LayoutParams   overlayParams;
    private Hashtable      visualOverlays = new Hashtable();

    private Uplink uplink;
    private GimbalEvent.Publisher publisher;
    private TouchServer touchServer;

    GimbalUIOverlay(Object android) {  
        super(android);
        Util.debug("CONSTRUCT GimbalUIOverlay");
        publisher = new GimbalEvent.Publisher();
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
            createServers();
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

            //
            // getting lazy here...
            //
            // TODO: make an interface to 
            //

            try {
                try {
                    GimbalGL10Overlay overlay = (GimbalGL10Overlay) visualOverlays.get(e.nextElement());
                    overlay.getView().onResume();
                } catch (java.lang.ClassCastException x) {}
                try {
                    GimbalCameraOverlay overlay = (GimbalCameraOverlay) visualOverlays.get(e.nextElement());
                    //overlay.getView().onResume();
                } catch (java.lang.ClassCastException x) {}
            } catch (java.util.NoSuchElementException x) {} 
        }
        startServers();
    }

    public void pause() {
        Util.debug("PAUSE");
        Enumeration<String> e = visualOverlays.keys();
        while(e.hasMoreElements()) {
            try {
                try {
                    GimbalGL10Overlay overlay = (GimbalGL10Overlay) visualOverlays.get(e.nextElement());
                    overlay.getView().onPause();
                } catch (java.lang.ClassCastException x) {}
                try {
                    GimbalCameraOverlay overlay = (GimbalCameraOverlay) visualOverlays.get(e.nextElement());
                    //overlay.getView().onPause();
                } catch (java.lang.ClassCastException x) {}
            } catch (java.util.NoSuchElementException x) {} 
        }
    }

    public void stop() {
        Util.debug("STOP");
        stopServers();
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

            //
            // CameraOverlay is not a managed child view. 
            // Perhaps bang it back to pattern later... (it if proves important)
            //

            //visualOverlays.put(GimbalConfig.Option.CAMERA, visualOverlay);
            //activity.addContentView(visualOverlay.view(), overlayParams);
            visualOverlay.view();
            visualOverlay.start();
        }

        enable = GimbalConfig.VISUAL_FEEDBACK & GimbalConfig.Option.GL10;
        if( enable == GimbalConfig.Option.GL10 ) {
            GimbalGL10Overlay visualOverlay = new GimbalGL10Overlay(activity, GimbalConfig.GL10_FEEDBACK);
            visualOverlays.put(GimbalConfig.Option.GL10, visualOverlay);
            activity.addContentView(visualOverlay.view(), overlayParams);
            publisher.subscribe( (GimbalEvent.Subscriber) GimbalConfig.GL10_FEEDBACK);
        }

    }

    private void createControlsOverlay() {
        overlay =       new RelativeLayout(context);
        overlay.setBackgroundColor(Color.argb(50,255,255,255));
        overlay.setLayoutParams(overlayParams);
        activity.addContentView(overlay, overlayParams);
    }

    private void createServers() {
        touchServer = new TouchServer(context, publisher, (View)overlay);
        uplink      = new Uplink(context, publisher);
    }

    private void startServers() {
        touchServer.startServer();
        uplink.startServer();
    }

    private void stopServers() {
        touchServer.stopServer();
        uplink.stopServer();
    }

}
