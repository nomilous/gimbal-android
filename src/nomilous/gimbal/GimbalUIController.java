package nomilous.gimbal;

import nomilous.Util;
import nomilous.gimbal.overlays.*;
import nomilous.gimbal.GimbalConfig;
import nomilous.gimbal.server.sensor.TouchServer;
import nomilous.gimbal.GimbalEvent;
import nomilous.gimbal.uplink.GimbalUplink;
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


public class GimbalUIController extends GimbalOverlay {

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


    private RelativeLayout rootLayout;
    private LayoutParams   rootLayoutParams = new LayoutParams(
            LayoutParams.FILL_PARENT, 
            LayoutParams.FILL_PARENT
    );

    //private GimbalCameraOverlay cameraOverlay;
    private GimbalGLOverlay     visualsOverlay;
    

    private GimbalEvent.Publisher publisher;
    private GimbalUplink          uplink = GimbalConfig.UPLINK;
    private TouchServer           touchServer;


    public GimbalUIController(Object android) {

        super(android);

        Util.debug("CONSTRUCT GimbalUIOverlay");
        publisher = new GimbalEvent.Publisher();
        rootLayoutParams.setMargins(0, 0, 0, 0);

    }


    private boolean started = false;
    public  void    start() {
        if( !started ) {
            Util.debug("START GimbalUIOverlay");
            createRootLayout();
            createVisualsOverlay();
            createMenu();
            createServers();
        }
        started = true;
    }

    public void restart() {
        Util.debug("TODO - RESTART");
    }

    public void resume() {
        Util.debug("TODO - RESUME");
        startServers();
    }

    public void pause() {
        Util.debug("TODO - PAUSE");
    }

    public void stop() {
        Util.debug("TODO - STOP");
        stopServers();
    }

    public void destroy() {
        Util.debug("TODO - DESTROY");
    }

    private void createRootLayout() {

        rootLayout = new RelativeLayout(context);
        rootLayout.setBackgroundColor(Color.argb(50,255,255,255));
        rootLayout.setLayoutParams(rootLayoutParams);
        activity.addContentView(rootLayout, rootLayoutParams);

    }

    private void createVisualsOverlay() {

        // cameraOverlay = new GimbalCameraOverlay(activity);
        // cameraOverlay.view();
        // cameraOverlay.start();

        visualsOverlay = new GimbalGLOverlay(activity, GimbalConfig.GL_RENDERER);
        publisher.subscribe(GimbalConfig.GL_RENDERER);
        activity.addContentView(visualsOverlay.view(), rootLayoutParams);

    }

    private void createMenu() {

        Menu.Config config = new Menu.Config();
        config.selectionHandler = this;
        
        MenuActionGroup menu = Menu.create(rootLayout, config);

        publisher.subscribe( (GimbalEvent.Subscriber) menu );

        boolean enabled = false;

        for( int i = 0; i < menuActions.length; i++ )

            menu.add( menuActions[i] );

    }

    @Override
    public void onMenuShow(MenuActionGroup menu) {
        visualsOverlay.onMenuShow(menu);
    }

    @Override
    public void onMenuHide(MenuActionGroup menu) {
        visualsOverlay.onMenuHide(menu);
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
        touchServer = new TouchServer(context, publisher, (View)rootLayout);
    }

    private void startServers() {
        touchServer.startServer();
    }

    private void stopServers() {
        touchServer.stopServer();
    }

}
