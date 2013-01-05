package nomilous.gimbal.client;

import java.net.URI;
import org.json.JSONArray;
import org.json.JSONObject;

import com.codebutler.android_websockets.SocketIOClient;

import nomilous.Util;

public class GimbalController {

    private final SocketIOClient client;

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
                    
                    if( event.equals("event:client:start") ) {

                        registerController( viewportID );

                    }


                }

                @Override
                public void onDisconnect(int code, String reason) {}

                @Override
                public void onError(Exception error) {}

            }

        );

    }

    public void registerController( String viewportID ) {

        Util.info( "Register controller for viewport:" + viewportID );

        try {

            JSONArray message = new JSONArray();
            message.put( viewportID );
            client.emit( "event:register:controller", message );

        } 

        catch( Exception e ) {}

    }

    public void connect() {

        client.connect();

    }

}
