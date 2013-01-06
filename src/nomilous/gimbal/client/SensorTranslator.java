package nomilous.gimbal.client;

import org.json.JSONArray;
import org.json.JSONObject;
import com.codebutler.android_websockets.SocketIOClient;

public class SensorTranslator {


    public final int TILT_MODE = 1;

    // 
    // TILT_MODE
    // 
    // - A calibration sequence sets up a zeropoint vector with surrounding deadzone.
    // 
    // - Orrientation updates sent to the server are arc deltas from the zeropoint 
    //   on the pitch/yaw/roll axii, and are only sent if they arc beyond the deadzone.
    //

    private SocketIOClient client;

    public SensorTranslator() {}

    public void configure( SocketIOClient client, JSONArray config ) {

        this.client = client;

        //
        // TODO: Configure mode
        // TODO: Initiate calibration sequence
        //

    }

    public void handleSensorEvent( int eventCode, Object payload ) {

        if( client == null ) return;

        //
        // TODO: A huge big pile of trig!
        // 
        // (unless android provides... )
        //

        switch( eventCode ) {

            case SensorSubscriber.ROTATION_UPDATE:

                try {

                    JSONArray xyz = new JSONArray();
                    xyz.put( ((float[])payload)[0] );
                    xyz.put( ((float[])payload)[1] );
                    xyz.put( ((float[])payload)[2] );

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
