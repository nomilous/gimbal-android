package nomilous.gimbal.client;

import java.net.URI;
import org.json.JSONArray;
import org.json.JSONObject;

import com.codebutler.android_websockets.SocketIOClient;

import nomilous.Util;

public class GimbalController {

    private final SocketIOClient client;
    private boolean assigned = false;

    public GimbalController( final GimbalEventHandler handler, final String uri, final String viewportID ) {

        Util.info( String.format("Init controller with %s %s", uri, viewportID ));

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
