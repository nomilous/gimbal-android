package nomilous.gimbal.overlays;

import nomilous.Util;
import nomilous.gimbal.GimbalConfig;
import nomilous.gimbal.server.sensor.TouchServer;
import nomilous.gimbal.GimbalEvent;
import nomilous.gimbal.uplink.Uplink;
import nomilous.gimbal.menu.MenuActionGroup;
import nomilous.gimbal.menu.MenuAction;
import nomilous.gimbal.menu.Menu;

import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.graphics.Color;
import java.util.Hashtable;
import java.util.Enumeration;
import android.opengl.GLSurfaceView;
import android.view.View;
import android.widget.TextView;


public class GimbalUIOverlay extends GimbalOverlay

    implements Menu.SelectionHandler {

    private static class Action {

        public static final int CONNECT_VIEWPORT    = 1;
        public static final int DISCONNECT_VIEWPORT = 2;
        public static final int TOGGLE_HELP         = 3;
        public static final int EXIT                = 4; 

    }

    private MenuAction[] menuActions = new MenuAction[] {

        new MenuAction( Action.CONNECT_VIEWPORT, "connect", "Connect a viewport." ),
        new MenuAction( Action.DISCONNECT_VIEWPORT, "disconnect", "Disconnect all viewports.", false ),
        new MenuAction( Action.TOGGLE_HELP, "help", "Toggle tooltips." ),
        new MenuAction( Action.EXIT, "exit", "Exit the app." )

    };

    private boolean        already = false;
    private RelativeLayout overlay;
    private LayoutParams   overlayParams;
    private Hashtable      visualOverlays = new Hashtable();

    private Uplink uplink;
    private GimbalEvent.Publisher publisher;
    private TouchServer touchServer;

    public GimbalUIOverlay(Object android) { 
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
            createMenu();
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
        overlay = new RelativeLayout(context);
        overlay.setBackgroundColor(Color.argb(50,255,255,255));
        overlay.setLayoutParams(overlayParams);
        activity.addContentView(overlay, overlayParams);
    }

    private void createMenu() {

        Menu.Config config = new Menu.Config();
        config.selectionHandler = this;
        
        MenuActionGroup menu = Menu.create(overlay, config);

        publisher.subscribe( (GimbalEvent.Subscriber) menu );

        boolean enabled = false;

        for( int i = 0; i < menuActions.length; i++ )

            menu.add( menuActions[i] );

    }

    @Override
    public void onMenuShow() {

    }

    @Override
    public void onMenuHide() {

    }


    @Override
    public void onMenuSelection( MenuAction action ) {

        switch( action.code ) {

            case Action.CONNECT_VIEWPORT:
                break;

            case Action.DISCONNECT_VIEWPORT:
                break;

            case Action.TOGGLE_HELP:
                break;

            case Action.EXIT:
                break;

        }

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
