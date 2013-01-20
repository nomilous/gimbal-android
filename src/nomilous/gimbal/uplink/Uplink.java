package nomilous.gimbal.uplink;

import nomilous.Util;
import nomilous.gimbal.GimbalConfig;
import nomilous.gimbal.GimbalEvent;
import nomilous.gimbal.uplink.GimbalUplink.Protocol;

import com.codebutler.android_websockets.SocketIOClient;
import android.content.Context;
import org.json.JSONArray;
import org.json.JSONObject;
import java.net.URI;

public class Uplink extends GimbalEvent.Server

    implements GimbalUplink.Protocol {

    private SocketIOClient client;
    private String primaryViewportID;

    public Uplink(Context context,  GimbalEvent.Publisher publisher) {
        super(context, publisher);
    }

    final public void connect(final String uri, final String viewportID) {
        primaryViewportID = viewportID;
        doConnect(uri, viewportID);
    }

    public void onStartClient(JSONArray payload) {}  // abstract later...
    final public void startClient(JSONArray payload) {

        try {
            client.emit( Protocol.REGISTER_CONTROLLER, 
                getRegisterPayload()
            );
        } catch( org.json.JSONException x ) {}

        onStartClient(payload);
    }


    public void onRegisterController(JSONArray payload) {} // abstract later...
    final public void registerController(JSONArray payload) {
        onRegisterController(payload);
    }




    private JSONArray getRegisterPayload() {
        JSONArray message = new JSONArray();
        message.put( primaryViewportID );
        return message;
    }

    private void doConnect(final String uri, final String viewportID) {

        final Uplink uplink = this;

        client = new SocketIOClient(   
            URI.create(uri),
            new SocketIOClient.Handler() { 

                @Override
                public void onConnect() {

                    /* 
                     * BROKEN
                     * 
                     * does not get called by com.codebutler.android_websockets.SocketIOClient
                     * 
                     */

                    Util.debug( "\n\n\n\n\n\nCONNECT\n\n\n\n\n\n" );
                }

                @Override
                public void on(String event, JSONArray payload) {

                    if( event.equals( Protocol.CLIENT_START ) ) {

                        uplink.startClient(payload);
                        return;

                    }

                    // else if( event.equals(Protocol.REGISTER_CONTROLLER_OK ) ) {

                    //     uplink.registerController(payload);
                    //     return;

                    // }

                    Util.debug( String.format( 
                        "\n\n\nRECEIVED UNHANDLED %s: %s\n\n\n", 
                        event, payload.toString()
                    )); 

                }



                @Override
                public void onDisconnect(int code, String reason) {
                    Util.debug( String.format( 
                        "DISCONNECT %s: %s", 
                        code, reason
                    ));
                }


                @Override
                public void onError(Exception error) {
                    Util.debug( String.format( 
                        "ERROR %s", error.toString()
                    ));
                }

            }

        );
        client.connect();
    }

}
