package nomilous.gimbal.client;

import java.net.URI;
import org.json.JSONArray;
import org.json.JSONObject;

import com.codebutler.android_websockets.SocketIOClient;

import nomilous.Util;

public class GimbalController {

    private final SocketIOClient client;
    private boolean assigned = false;

    public GimbalController( final String uri, final String viewportID ) {

        Util.info( String.format("Init controller with %s %s", uri, viewportID ));

        client = new SocketIOClient(

            URI.create( uri ),

            new SocketIOClient.Handler() {

                @Override
                public void onConnect() { /* BROKEN */ }

                @Override
                public void on(String event, JSONArray arguments) {

                    Util.info(String.format("Got event %s: %s", event, arguments.toString()));

                    if( event.equals("event:client:start") ) 

                        registerController( viewportID );

                    else if( event.equals("event:register:controller:ok") ) 

                        assignController( viewportID );


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

    private void assignController( String viewportID ) {

        Util.info( "Assign controller to viewport:" + viewportID );

        assigned = true;

    }


}
