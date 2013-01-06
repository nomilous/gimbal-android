package nomilous.gimbal.client;

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
    private GimbalEventHandler handler;

    private SocketIOClient client;
    private boolean connected = false;
    private boolean assigned = false;
    

    public GimbalController( Context context, final GimbalEventHandler handler ) {

        this.context = context;
        this.handler = handler;
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

    private void initializeController( String primaryViewportID ) {

        Util.info( "Register controller with primary viewport: " + primaryViewportID );

        try {

            JSONArray message = new JSONArray();
            message.put( primaryViewportID );
            client.emit( "event:register:controller", message );

        } 

        catch( Exception e ) {}

    }

    private void activateSensors( GimbalEventHandler handler, String viewportID ) {

        Util.info( "Activating sensors" );
        

    }


}
