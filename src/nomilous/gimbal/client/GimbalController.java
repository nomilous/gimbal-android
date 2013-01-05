package nomilous.gimbal.client;

import java.net.URI;
import org.json.JSONArray;
import org.json.JSONObject;

import com.codebutler.android_websockets.SocketIOClient;

import nomilous.Util;

public class GimbalController {

    private final SocketIOClient client;

    public GimbalController( String uri, String viewportID ) {

        Util.info( String.format("Init controller with %s %s", uri, viewportID ));

        client = new SocketIOClient(

            URI.create( uri ),

            new SocketIOClient.Handler() {

                @Override
                public void onConnect() { /* BROKEN */ }

                @Override
                public void on(String event, JSONArray arguments) {
                    Util.info(String.format("Got event %s: %s", event, arguments.toString()));
                }

                @Override
                public void onDisconnect(int code, String reason) {}

                @Override
                public void onError(Exception error) {}

            }

        );

        client.connect();

    }

}
