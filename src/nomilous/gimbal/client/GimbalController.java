package nomilous.gimbal.client;

import android.app.Activity;
import android.content.Context;

import java.net.URI;
import org.json.JSONArray;
import org.json.JSONObject;

import com.codebutler.android_websockets.SocketIOClient;

import nomilous.gimbal.server.Updates;
import nomilous.gimbal.client.Subscriber;
import nomilous.Util;

public class GimbalController {


    private Context context;
    private Activity activity;
    private GimbalEventHandler handler;

    private SocketIOClient client;
    private boolean connected = false;
    private boolean assigned = false;
    

    public GimbalController( Context context, Object handler ) {

        this.context = context;
        this.handler = (GimbalEventHandler) handler;
        this.activity = (Activity) handler;
    }

    public void connect( final String uri, final String viewportID ) {

        client = new SocketIOClient(

            URI.create( uri ),

            new SocketIOClient.Handler() {

                @Override
                public void onConnect() { /* BROKEN */ }

                @Override
                public void on(String event, JSONArray payload) {

                    Util.info(String.format("Got event %s: %s", event, payload.toString()));

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
                        activateSensors( handler, viewportID );

                    } else if( event.equals( GimbalEventHandler.DISCONNECT_OK ) ) {

                        //
                        // Server has released resources associated to this controller
                        //

                        assigned = false;
                        deActivateSensors();

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

        // Updates.subscribe( context, 

        //     Updates.ROTATION_UPDATE,

        //     new Subscriber() {

        //         @Override
        //         public void onMessage( int sensorEvent, Object payload ) {

        //             switch( sensorEvent ) {

        //                 case Updates.ROTATION_UPDATE:

        //                     float[] orient = (float[]) payload;

        //                     try {

        //                         JSONArray xyz = new JSONArray();
        //                         xyz.put( orient[0] );
        //                         xyz.put( orient[1] );
        //                         xyz.put( orient[2] );

        //                         JSONObject orientation = new JSONObject();
        //                         orientation.put( "event:orient", xyz );

        //                         JSONArray message = new JSONArray();
        //                         message.put( orientation );

        //                         client.emit( GimbalEventHandler.UPDATE_VIEWPORTS, message );

        //                     } 

        //                     catch( Exception x ) {} 
        //                     break;

        //             }

        //         }

        //     }

        // );


    }

    public void disconnect() {

        Util.info("Disconnecting controller");

        try {

            client.emit( "event:release:controller", new JSONArray() );

        } 

        catch( org.json.JSONException e ) {}


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

        Util.info( "Activating sensors" ); 

    }

    private void deActivateSensors() {

        Util.info( "De activating sensors" );

    }

    private void requestDisconnect() {

        // unused function

        try {

            client.emit( "request:socket:disconnect", new JSONArray() );

        }

        catch( org.json.JSONException e ) {}

    }

}
