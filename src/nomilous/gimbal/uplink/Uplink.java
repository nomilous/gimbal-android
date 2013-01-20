package nomilous.gimbal.uplink;

import nomilous.Util;
import nomilous.gimbal.GimbalConfig;
import nomilous.gimbal.GimbalEvent;
import nomilous.gimbal.uplink.GimbalUplink.Protocol;

import com.codebutler.android_websockets.SocketIOClient;
import android.content.Context;
import android.view.WindowManager;
import android.view.Display;
import android.graphics.Point;
import org.json.JSONArray;
import org.json.JSONObject;
import java.net.URI;

public abstract class Uplink extends GimbalEvent.Server

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

    final public void disconnect(String viewportID) {
        doDisconnect(viewportID);
    }



    public abstract void onStartClient(JSONArray payload);
    final public void startClient(JSONArray payload) {

        try {
            JSONArray message = getRegisterPayload();
            Util.debug(String.format( 

                "SENDING %s payload:%s",
                Protocol.REGISTER_CONTROLLER,
                message.toString()


            ));
            client.emit(Protocol.REGISTER_CONTROLLER, message);
        } catch( org.json.JSONException x ) {}

        onStartClient(payload);
    }


    public abstract void onRegisterController(JSONArray payload);
    final public void registerController(JSONArray payload) {
        Util.debug(String.format(

            "Uplink.registerController() with payload %s",
            payload.toString()

        ));
        onRegisterController(payload);
    }


    public abstract void onReleaseController(JSONArray payload);
    final public void releaseController(JSONArray payload) {
        Util.debug(String.format(

            "Uplink.releaseController() with payload %s",
            payload.toString()

        ));
        onReleaseController(payload);
    }


    private JSONArray getRegisterPayload() throws org.json.JSONException {

        //
        // com.codebutler.android_websockets.SocketIOClient insists on an
        // org.json.JSONArray as payload for emit
        // 
        // TODO: find or make a marriage between socket.io and google.gson
        //       for android... 
        //     
        //

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        int width  = 0;
        int height = 0;
        int depth  = 0;  // touch screens are quite shallow

        try {

            Point size = new Point();
            display.getSize(size);
            width  = size.x;
            height = size.y;

        } catch( java.lang.NoSuchMethodError x ) {

            //
            // targeting ICS, but running on Gingerbread
            // 
            // fallback
            //

            width  = display.getWidth();
            height = display.getHeight();

        }

        JSONArray inputCube = new JSONArray();
        inputCube.put(width);
        inputCube.put(height);
        inputCube.put(depth);

        JSONObject nestedIntoJSONArray = new JSONObject();
        nestedIntoJSONArray.put("input_cube", inputCube);
        nestedIntoJSONArray.put("primary_viewport", primaryViewportID);


        JSONArray message = new JSONArray();
        message.put( nestedIntoJSONArray );
        return message;
    }

    private JSONArray getReleasePayload(String viewportID) throws org.json.JSONException {

        JSONObject nestedIntoJSONArray = new JSONObject();
        nestedIntoJSONArray.put("viewport_id", viewportID);
        nestedIntoJSONArray.put("primary_viewport", primaryViewportID);

        JSONArray message = new JSONArray();
        message.put( nestedIntoJSONArray );
        return message;

    }

    private void doDisconnect(String viewportID) {
        try {
            JSONArray message = getReleasePayload(viewportID);
            Util.debug(String.format( 

                "SENDING %s payload:%s",
                Protocol.RELEASE_CONTROLLER,
                message.toString()


            ));
            client.emit(Protocol.RELEASE_CONTROLLER, message);
        } catch( org.json.JSONException x ) {}
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

                    else if( event.equals(Protocol.REGISTER_CONTROLLER_OK ) ) {

                        uplink.registerController(payload);
                        return;

                    }

                    else if( event.equals(Protocol.RELEASE_CONTROLLER_OK ) ) {

                        uplink.releaseController(payload);
                        return;

                    }

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
