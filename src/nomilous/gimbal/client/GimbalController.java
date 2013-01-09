package nomilous.gimbal.client;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.EditText;

import java.net.URI;
import org.json.JSONArray;
import org.json.JSONObject;

import com.codebutler.android_websockets.SocketIOClient;

import nomilous.gimbal.client.SensorSubscriber;
import nomilous.gimbal.server.sensor.OrientationServer;
import nomilous.gimbal.server.sensor.LocationServer;
import nomilous.gimbal.server.sensor.TouchServer;
import nomilous.gimbal.server.sensor.KeypadServer;

import nomilous.Util;

public class GimbalController implements SensorSubscriber {

    private Context context;
    private Activity activity;
    private GimbalEventHandler handler;

    private SocketIOClient client;
    private boolean connected = false;
    private boolean assigned = false;  // in control of at least 1 viewport
    private boolean active = false;    // sensors are active
    private boolean exitting = false;  // set on destroy

    private OrientationServer orientationServer;
    private LocationServer locationServer;
    private TouchServer touchServer;
    private KeypadServer keypadServer;
    private SensorTranslator translator;
    

    public GimbalController( Context context, Object handler, View v, View e ) {

        this.context = context;
        this.handler = (GimbalEventHandler) handler;
        this.activity = (Activity) handler;

        orientationServer = new OrientationServer(context, this);
        locationServer = new LocationServer(context, this);
        touchServer = new TouchServer(context, this, v);
        keypadServer = new KeypadServer(context, this, e);

        translator = new SensorTranslator();

    }

    public void connect( final String uri, final String viewportID ) {

        client = new SocketIOClient(

            URI.create( uri ),

            new SocketIOClient.Handler() {

                @Override
                public void onConnect() { /* BROKEN */ }

                @Override
                public void on(String event, JSONArray payload) {

                    Util.info(String.format("RECEIVED %s: %s", event, payload.toString()));

                    if( event.equals( GimbalEventHandler.CONTROLLER_CONNECTED ) ) {

                        //
                        // Have established connection to server
                        //

                        connected = true;
                        initializeController( viewportID );

                    } else if( event.equals( GimbalEventHandler.ASSIGN_PRIMARY_VIEWPORT ) ) {

                        //
                        // Have got primary viewport
                        //

                        assigned = true;
                        translator.configure( client, payload );
                        activateSensors( handler, viewportID );

                    } else if( event.equals( GimbalEventHandler.DISCONNECT_OK ) ) {

                        //
                        // Server has released resources associated to this controller
                        //

                        assigned = false;       //
                        connected = false;      // BUT:
                                                //
                                                // - The socket is still connected (See issue below)
                                                // 
                                                // - A new socket will be created 
                                                //   when a call is made to connect.
                                                //
                        deActivateSensors();

                        if( exitting ) 

                            //
                            // Notify activity (handler) can now destroy
                            //

                            handler.gimbalEvent( "EXIT", payload );





                        // 
                        // GRUMBLES!!!
                        // 
                        // Can't initiate a clean disconnect from the clientside...
                        // 
                        // 
                        // E/AndroidRuntime(  530): FATAL EXCEPTION: Thread-12
                        // E/AndroidRuntime(  530): java.lang.NullPointerException
                        // E/AndroidRuntime(  530):    at com.codebutler.android_websockets.SocketIOClient.cleanup(SocketIOClient.java:183)
                        // E/AndroidRuntime(  530):    at com.codebutler.android_websockets.SocketIOClient.access$000(SocketIOClient.java:22)
                        // E/AndroidRuntime(  530):    at com.codebutler.android_websockets.SocketIOClient$2.onError(SocketIOClient.java:152)
                        // E/AndroidRuntime(  530):    at com.codebutler.android_websockets.WebSocketClient$1.run(WebSocketClient.java:132)
                        // E/AndroidRuntime(  530):    at java.lang.Thread.run(Thread.java:1019)
                        //
                        // I tried to catch that null pointer inside com.codebutler.android_websockets.WebSocketClient$1.run(WebSocketClient.java:132)
                        // 
                        // no luck...
                        // 
                        // 

                        // activity.runOnUiThread(new Runnable() {
                        //     @Override
                        //     public void run() {


                                // try {
                                //     client.disconnect();
                                // } catch( java.io.IOException e ) { }



                        //     }
                        // });

                        // 
                        // ask server to initiate disconnect
                        // 

                        // requestDisconnect();

                        //
                        // same problem when server disconnectes... 
                        //

                        //////// SOCKET STAYS CONNECTED...
                        //////// DISCONNECTED ONLY ON APPLICATION LAYER
                        //////// ie. unassociated to viewports


                    }

                    handler.gimbalEvent( event, payload );

                }

                @Override
                public void onDisconnect(int code, String reason) {}

                @Override
                public void onError(Exception error) {}

            }

        );

        client.connect();

    }

    public void destroy() {

        Util.info("Starting exit sequence");
        exitting = true;

        if( connected ) 

            //
            // Have a connection to viewport, disconnect
            //

            disconnect();


        else {

            deActivateSensors(); // just incase
            handler.gimbalEvent( "EXIT", "" );

        }

    }

    public void disconnect() {

        Util.info("Disconnecting controller");

        try {

            client.emit( "event:release:controller", new JSONArray() );

        } 

        catch( org.json.JSONException e ) {

            Util.error( e.toString() );

        }
        catch( java.lang.NullPointerException e ) {

            Util.error( e.toString() );
            
        }


    }

    public void onSensorEvent( int eventCode, Object payload ) {

        translator.handleSensorEvent( eventCode, payload );

    }

    public void startKeypad() {

        keypadServer.startKeypad();

    }

    private void initializeController( String primaryViewportID ) {

        Util.info( "Register controller with primary viewport: " + primaryViewportID );

        try {

            JSONArray message = new JSONArray();
            message.put( primaryViewportID );
            client.emit( "event:register:controller", message );

        } 

        catch( org.json.JSONException e ) {}

    }

    private void activateSensors( GimbalEventHandler handler, String viewportID ) {

        Util.info("Activate sensors");

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                
                orientationServer.startServer();
                locationServer.startServer();
                touchServer.startServer();
                keypadServer.startServer();
                active = true;

            }
        });

    }

    private void deActivateSensors() {

        Util.info("Terminate sensors");

        orientationServer.stopServer();
        locationServer.stopServer();
        touchServer.stopServer();
        keypadServer.stopServer();

        active = false;

    }

    private void requestDisconnect() {

        // unused function

        try {

            client.emit( "request:socket:disconnect", new JSONArray() );

        }

        catch( org.json.JSONException e ) {}

    }

}
