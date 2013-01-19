package nomilous.gimbal.uplink;

import nomilous.Util;
import nomilous.gimbal.uplink.GimbalUplinkHandler;
import nomilous.gimbal.GimbalEvent;

import org.json.JSONArray;
import org.json.JSONObject;

public class DefaultUplinkHandler extends GimbalUplinkHandler {

    @Override
    public void onConnect() {
        Util.debug( "CONNECT" );
    }

    @Override
    public void on(String event, JSONArray payload) {
        Util.debug( String.format( 
            "RECEIVED %s: %s", 
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

    @Override
    public void onTouchEvent( GimbalEvent.Touch event ) {
        Util.debug( "send touch event to server" );
    }

}
