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

    private final SocketIOClient client;
    private boolean assigned = false;
    private Context context;

    public GimbalController( Context context, final GimbalEventHandler handler, final String uri, final String viewportID ) {

        Util.info( String.format("Init controller with %s %s", uri, viewportID ));

        this.context = context;

        client = new SocketIOClient(

            URI.create( uri ),

            new SocketIOClient.Handler() {

                @Override
                public void onConnect() { /* BROKEN */ }

                @Override
                public void on(String event, JSONArray payload) {

                    Util.info(String.format("Got event %s: %s", event, payload.toString()));

                    if( event.equals( GimbalEventHandler.CONTROLLER_CONNECTED ) ) {

                        registerController( viewportID );

                    } else if( event.equals( GimbalEventHandler.ASSIGN_PRIMARY_VIEWPORT ) ) {

                        assignPrimaryViewport( handler, viewportID );

                    }

                    handler.gimbalEvent( event, payload );

                }

                @Override
                public void onDisconnect(int code, String reason) {}

                @Override
                public void onError(Exception error) {}

            }

        );



        Updates.subscribe( context, 

            Updates.ROTATION_UPDATE,

            new Subscriber() {

                @Override
                public void onMessage( int sensorEvent, Object payload ) {

                    switch( sensorEvent ) {

                        case Updates.ROTATION_UPDATE:

                            float[] orient = (float[]) payload;

                            try {

                                JSONArray xyz = new JSONArray();
                                xyz.put( orient[0] );
                                xyz.put( orient[1] );
                                xyz.put( orient[2] );

                                JSONObject orientation = new JSONObject();
                                orientation.put( "event:orient", xyz );

                                JSONArray message = new JSONArray();
                                message.put( orientation );

                                client.emit( GimbalEventHandler.UPDATE_VIEWPORTS, message );

                            } 

                            catch( Exception x ) {} 
                            break;

                    }

                }

            }

        );

    }

    public void connect() {

        client.connect();

    }

    private void registerController( String viewportID ) {

        Util.info( "Register controller for viewport:" + viewportID );

        try {

            JSONArray message = new JSONArray();
            message.put( viewportID );
            client.emit( "event:register:controller", message );

        } 

        catch( Exception e ) {}

    }

    private void assignPrimaryViewport( GimbalEventHandler handler, String viewportID ) {

        Util.info( "Assign controller to viewport:" + viewportID );
        assigned = true;

    }


}
