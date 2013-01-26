package nomilous.gimbal;

import nomilous.Util;
import nomilous.gimbal.overlays.*;
import nomilous.gimbal.GimbalConfig;
import nomilous.gimbal.server.sensor.TouchServer;
import nomilous.gimbal.GimbalEvent;
import nomilous.gimbal.menu.MenuActionGroup;
import nomilous.gimbal.menu.MenuAction;
import nomilous.gimbal.menu.Menu;
import nomilous.gimbal.viewport.ViewportEventHandler;
import nomilous.gimbal.viewport.ViewportController;
import nomilous.gimbal.viewport.Viewport;

import android.app.Activity;
import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.graphics.Color;
import android.graphics.Point;
import java.util.Hashtable;
import java.util.Enumeration;
import android.opengl.GLSurfaceView;
import android.view.View;
import android.widget.TextView;
import android.view.WindowManager;
import android.view.Display;
import com.google.zxing.integration.android.IntentIntegrator;


public class GimbalUIController extends GimbalOverlay 

    implements 

        ViewportEventHandler

{

    private String primaryViewportID;  // TODO: remove,  (viewportController)

    private static class Action {

        public static final int CONNECT_VIEWPORT    = 0;
        public static final int DISCONNECT_VIEWPORT = 1;
        public static final int TOGGLE_HELP         = 2;
        public static final int EXIT                = 3; 

    }

    private MenuActionGroup menu;

    private MenuAction[] menuActions = new MenuAction[] {

        new MenuAction( Action.CONNECT_VIEWPORT, "connect", "Connect a viewport." ),
        new MenuAction( Action.DISCONNECT_VIEWPORT, "disconnect", "Disconnect all viewports.", false ),
        new MenuAction( Action.TOGGLE_HELP, "help", "Toggle tooltips." ),
        new MenuAction( Action.EXIT, "exit", "Exit the app." )

    };


    private ViewportController viewportController;

    @Override
    public int[] getInputCube() {

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        int width  = 0;
        int height = 0;

        try {

            Point size = new Point();
            display.getSize(size);
            width  = size.x;
            height = size.y;

        } catch( java.lang.NoSuchMethodError x ) {

            width  = display.getWidth();
            height = display.getHeight();

        }

        return new int[] {width, height, 0};
    }

    @Override 
    public void onViewportRegistered(Viewport viewport) {

        menuActions[Action.CONNECT_VIEWPORT].enabled = false;
        menuActions[Action.DISCONNECT_VIEWPORT].enabled = true;
        menu.hide();
        visualsOverlay.onMenuHide(menu);

    }

    @Override 
    public void onViewportReleased(Viewport viewport) {

        menuActions[Action.CONNECT_VIEWPORT].enabled = true;
        menuActions[Action.DISCONNECT_VIEWPORT].enabled = false;
        menu.refresh();

    }


    private RelativeLayout rootLayout;
    private LayoutParams   rootLayoutParams = new LayoutParams(
            LayoutParams.FILL_PARENT, 
            LayoutParams.FILL_PARENT
    );

    //private GimbalCameraOverlay cameraOverlay;
    private GimbalGLOverlay visualsOverlay;
    

    private GimbalEvent.Publisher publisher;


    private TouchServer touchServer;


    public GimbalUIController(Object android) {

        super(android);

        Util.debug("CONSTRUCT GimbalUIOverlay");
        
        publisher = new GimbalEvent.Publisher();
        viewportController = new ViewportController(context, publisher, this);
        rootLayoutParams.setMargins(0, 0, 0, 0);

    }


    public void connectViewport(final String uri, final String viewportID) {

        //
        // called on response to scanning a QR code 
        // displayed on an available viewport
        //

        this.primaryViewportID = viewportID;

        viewportController.connect(uri, viewportID);
        
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
        
        menu = Menu.create(rootLayout, config);

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
                scanViewportQrCode();
                break;

            case Action.DISCONNECT_VIEWPORT:
                disconnectViewport(primaryViewportID);
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

    private void scanViewportQrCode() {
        final Activity scanResultHandler = activity;
        IntentIntegrator qrScan = new IntentIntegrator(scanResultHandler);
        qrScan.initiateScan();
    }

    private void disconnectViewport(String viewportID) {
        viewportController.disconnect(viewportID);
    }



}
